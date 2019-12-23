package io.renren.modules.performance.service.impl;

import io.renren.common.exception.RRException;
import io.renren.modules.performance.dao.*;
import io.renren.modules.performance.entity.DebugCaseReportsEntity;
import io.renren.modules.performance.entity.PerformanceCaseEntity;
import io.renren.modules.performance.entity.PerformanceCaseFileEntity;
import io.renren.modules.performance.entity.PerformanceCaseReportsEntity;
import io.renren.modules.performance.handler.FileExecuteResultHandler;
import io.renren.modules.performance.jmeter.JmeterListenToTest;
import io.renren.modules.performance.jmeter.JmeterResultCollector;
import io.renren.modules.performance.jmeter.JmeterRunEntity;
import io.renren.modules.performance.jmeter.engine.LocalStandardJMeterEngine;
import io.renren.modules.performance.jmeter.runner.LocalDistributedRunner;
import io.renren.modules.performance.service.PerformanceCaseFileService;
import io.renren.modules.performance.service.PerformanceThreadSetService;
import io.renren.modules.performance.utils.PerformanceTestUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.jmeter.JMeter;
import org.apache.jmeter.config.CSVDataSet;
import org.apache.jmeter.engine.JMeterEngine;
import org.apache.jmeter.engine.JMeterEngineException;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.services.FileServer;
import org.apache.jmeter.threads.RemoteThreadsListenerTestElement;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jorphan.collections.HashTree;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;



@Service("performanceCaseFileService")
public class PerformanceCaseFileServiceImpl implements PerformanceCaseFileService {

    Logger logger = LoggerFactory.getLogger(getClass());

    private static final String JAVA_CLASS_PATH = "java.class.path";
    private static final String CLASSPATH_SEPARATOR = File.pathSeparator;

    private static final String OS_NAME = System.getProperty("os.name");// $NON-NLS-1$

    private static final String OS_NAME_LC = OS_NAME.toLowerCase(java.util.Locale.ENGLISH);

    //private static final String JMETER_INSTALLATION_DIRECTORY;

    /**
     * 增加了一个static代码块，本身是从Jmeter的NewDriver源码中复制过来的。
     * Jmeter的api中是删掉了这部分代码的，需要从Jmeter源码中才能看到。
     * 由于源码中bug的修复很多，我也就原封保留了。
     *
     * 这段代码块的意义在于，通过Jmeter_Home的地址，找到Jmeter要加载的jar包的目录。
     * 将这些jar包中的方法的class_path，放置到JAVA_CLASS_PATH系统变量中。
     * 而Jmeter在遇到参数化的函数表达式的时候，会从JAVA_CLASS_PATH系统变量中来找到这些对应关系。
     * 而Jmeter的插件也是一个原理，来找到这些对应关系。
     * 其中配置文件还包含了这些插件的过滤配置，默认是.functions. 的必须，.gui.的非必须。
     * 配置key为  classfinder.functions.notContain
     *
     * 带来的影响：
     * 让程序和Jmeter_home外部的联系更加耦合了，这样master必带Jmeter_home才可以。
     * 不仅仅是测试报告的生成了。
     * 同时，需要在pom文件中引用ApacheJMeter_functions，这其中才包含了参数化所用的函数的实现类。
     *
     * 自己修改：
     * 1. 可以将class_path直接拼接字符串的形式添加到系统变量中，不过如果Jmeter改了命名，则这边也要同步修改很麻烦。
     * 2. 修改Jmeter源码，将JAVA_CLASS_PATH系统变量这部分的查找改掉。在CompoundVariable 类的static块中。
     *    ClassFinder.findClassesThatExtend 方法。
     *
     * 写成static代码块，也是因为类加载（第一次请求时），才会初始化并初始化一次。这也是符合逻辑的。
     */
    @Autowired
    private PerformanceCaseFileDao performanceCaseFileDao;

    @Autowired
    private PerformanceCaseDao performanceCaseDao;

    @Autowired
    private PerformanceTestUtils performanceTestUtils;

    @Autowired
    private PerformanceThreadSetDao performanceThreadSetDao;

    @Autowired
    private PerformanceThreadSetService performanceThreadSet;

    @Autowired
    private DebugCaseReportsDao debugCaseReportsDao;

    @Autowired
    private PerformanceCaseReportsDao perTestCaseRortsDao;


    @Override
    public PerformanceCaseFileEntity queryObject(Long fileId) {
        return performanceCaseFileDao.queryObject(fileId);
    }

    @Override
    public List<PerformanceCaseFileEntity> queryList(Map<String, Object> map) {
        return performanceCaseFileDao.queryList(map);
    }

    @Override
    public List<PerformanceCaseFileEntity> queryList(Long caseId) {
        Map query = new HashMap<>();
        query.put("caseId", caseId.toString());
        return performanceCaseFileDao.queryList(query);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return performanceCaseFileDao.queryTotal(map);
    }

    @Override
    public void save(PerformanceCaseFileEntity perTestCaseFile) {
        performanceCaseFileDao.save(perTestCaseFile);
    }


    /**
     * 保存文件以及入库
     */

    @Override
    @Transactional
    public void save(MultipartFile file, String filePath, PerformanceCaseEntity perTestCase, PerformanceCaseFileEntity perTestCaseFile) {
        // 保存文件放这里,是因为有事务.
        // 保存数据放在最前,因为当前文件重名校验是根据数据库异常得到
        try {
            String fileMd5 = DigestUtils.md5Hex(file.getBytes());
            perTestCaseFile.setFileMd5(fileMd5);
        } catch (IOException e){
            throw new RRException("获取上传文件的MD5失败", e);
        }
        if(perTestCaseFile.getFileId() != null && perTestCaseFile.getFileId() > 0L) {
            // 替换文件，同时修改添加时间，便于前端显示。
            perTestCaseFile.setAddTime(new Date());
            update(perTestCaseFile);
        } else {
            // 保存文件，同时解决第一次保存文件时实体没有写入用例名称
            perTestCaseFile.setCaseName(perTestCase.getCaseName());
            save(perTestCaseFile);
        }
        // 肯定存在已有的用例信息
        performanceCaseDao.update(perTestCase);
        performanceTestUtils.saveFile(file, filePath);
        // 对jmx脚本将线程组配置信息入库(默认不入库)
        if(performanceTestUtils.isGetThreadGroup() && filePath.substring(filePath.length()-3).equals("jmx")){
            try {
                // 入库前清理已有配置项
                performanceThreadSetDao.deleteByFileId(perTestCaseFile.getFileId());
                performanceThreadSet.jmxSaveNodes(filePath, perTestCaseFile);
            } catch (DocumentException e){
                e.printStackTrace();
            }
        }

    }

    @Override
    public void update(PerformanceCaseFileEntity perTestFile) {
        performanceCaseFileDao.update(perTestFile);

    }

    @Override
    @Transactional
    public void update(PerformanceCaseFileEntity stressTestFile, PerformanceCaseReportsEntity perTestReports) {
        update(stressTestFile);
        if(perTestReports != null){
            if (perTestReports instanceof DebugCaseReportsEntity){
                debugCaseReportsDao.update((DebugCaseReportsEntity) perTestReports);
            } else {
                perTestCaseRortsDao.update(perTestReports);

            }
        }

    }

    /**
     * 更新用例文件以及入库
     */
    @Override
    @Transactional
    public void update(MultipartFile file, String filePath, PerformanceCaseEntity perTestCase, PerformanceCaseFileEntity perTestCaseFile) {
        try {
            String fileMd5 = DigestUtils.md5Hex(file.getBytes());
            perTestCaseFile.setFileMd5(fileMd5);
        } catch (IOException e) {
            throw new RRException("获取上传文件的MD5失败!", e);
        }
        update(perTestCaseFile);
        performanceCaseDao.update(perTestCase);
        performanceTestUtils.saveFile(file, filePath);

    }

    /**
     * 批量更新性能测试用例状态
     * @param perTestFile
     */
    @Override
    public void updateStatusBatch(PerformanceCaseFileEntity perTestFile) {
        Map<String, Object> map = new HashMap<>();
        map.put("fileIdList", perTestFile.getFileIdList());
        map.put("reportStatus", perTestFile.getReportStatus());
        map.put("webchartStatus", perTestFile.getWebchartStatus());
        map.put("debugStatus", perTestFile.getDebugStatus());
        map.put("duration", perTestFile.getDuration());
        performanceCaseFileDao.updateStatusBatch(map);

    }

    /**
     * 批量删除
     * 删除所有缓存 + 方法只要调用即删除所有缓存
     * @param fileIds
     */
    @Override
    @Transactional
    public void deleteBatch(Object[] fileIds) {
        Arrays.asList(fileIds).stream().forEach(fileId -> {
            PerformanceCaseFileEntity perTestFile = queryObject((Long) fileId);
            String casePath = performanceTestUtils.getCasePath();
            String FilePath = casePath + File.separator + perTestFile.getFileName();

            String jmxDir = FilePath.substring(0, FilePath.lastIndexOf("."));
            File jmxDirFile = new File(jmxDir);
            try {
                FileUtils.forceDelete(new File(FilePath));
            } catch (FileNotFoundException e) {
                logger.error("要删除的文件找不到(删除成功) " + e.getMessage());
            } catch (IOException e) {
                throw new RRException("删除文件异常失败", e);
            }
            try {
                if (FileUtils.sizeOf(jmxDirFile) == 0L){
                    FileUtils.forceDelete(jmxDirFile);
                }
            } catch (FileNotFoundException | IllegalArgumentException e) {
                logger.error("要删除的jmx文件夹找不到(删除成功) " + e.getMessage());
            } catch (IOException e) {
                throw new RRException("删除jmx文件夹异常失败", e);
            }

            // 删除缓存
            PerformanceTestUtils.samplingStatCalculator4File.remove(fileId);
            PerformanceTestUtils.jMeterEntity4file.remove(fileId);

            //删除远程节点的同步文件，如果远程节点比较多，网络不好，执行时间会比较长
            deleteSlaveFile((Long) fileId);
        });

        performanceCaseFileDao.deleteBatch(fileIds);
        performanceThreadSetDao.deleteBatchByFileIds(fileIds);  // 删除脚本关联的线程组配置信息

    }

    /**
     * 接口是支持批量运行的，但是强烈不建议这样做
     * @param fileIds
     * @return
     */
    @Override
    public String run(Long[] fileIds) {
        StringBuffer sb = new StringBuffer();
        for (Long fileId : fileIds) {
            sb.append(runSingle(fileId));
        }
        return sb.toString();
    }

    /**
     * 停止
     * @param fileIds
     */
    @Override
    public void stop(Long[] fileIds) {
        Arrays.asList(fileIds).stream().forEach(fileId -> {
            stopSingle(fileId);
        });

    }

    /**
     * 脚本的启动都是新的线程，其中的SQL是不和启动是同一个事务的。
     * 同理，也不会回滚这一事务。
     */
    @Override
    public void stopSingle(Long fileId){
        if (performanceTestUtils.isUseJmeterScript()){
            throw new RRException("Jmeter脚本启动不支持单独停止，请使用全部停止！")
        } else {
            Map<Long, JmeterRunEntity> jMeterEntity4file = PerformanceTestUtils.jMeterEntity4file;
        }
    }


    @Override
    public void stopAll(Long[] fileIds) {

    }

    @Override
    public void stopAll() {

    }

    @Override
    public void stopAllNow() {

    }

    @Override
    public void synchronizeFile(Long[] fileIds) {

    }

    @Override
    public String getFilePath(PerformanceCaseFileEntity stressTestFile) {
        return null;
    }

    @Override
    public void stopLocal(Long fileId, JmeterRunEntity jmeterRunEntity) {

    }

    /**
     * 脚本的启动都是新的线程，其中的SQL是不和启动时同一个事务的
     * 同理，也不会回滚这一事务
     * @param fileId
     */
    @Override
    public String runSingle(Long fileId) {
        PerformanceCaseFileEntity perTestFile = queryObject(fileId);
        if (PerformanceTestUtils.RUNNING.equals(perTestFile.getStatus())) {
            throw new RRException("脚本正在运行")
        }
        String casePath = performanceTestUtils.getCasePath();
        String fileName = perTestFile.getFileName();
        String filePath = casePath + File.separator + fileName;

        // 测试结果文件路径
        // jmx用例文件夹对应的相对路径名，如：20191204092125178/case20191204092144570
        String jmxDir = fileName.substring(0, fileName.lastIndexOf("."));
        String suffix = PerformanceTestUtils.NEED_DEBUG.equals(perTestFile.getDebugStatus()) ? "jtl" : "csv";
        // 测试结果文件csv文件的名称,如case20180504172207607_4444.csv
        String csvName = jmxDir.substring(jmxDir.lastIndexOf(File.separator) + 1) + PerformanceTestUtils.getSuffix4() + "." + suffix;
        // 测试结果文件csv文件的真实路径，如D:\E\stressTestCases\20180504172207568\case20180504172207607\case20180504172207607_4444.csv
        String csvPath = casePath + File.separator + jmxDir + File.separator + csvName;
        String fileOriginName = perTestFile.getOriginName();
        String reportOirginName = fileOriginName.substring(0, fileOriginName.lastIndexOf(".")) + "_" + PerformanceTestUtils.getSuffix4();

        File csvFile = new File(csvPath);
        File jmxFile = new File(filePath);

        PerformanceCaseReportsEntity perTestReports = null;
        if(PerformanceTestUtils.NEED_REPORT.equals(perTestFile.getReportStatus())) {
            // 如果是需要调试的情况下
            if ( PerformanceTestUtils.NEED_DEBUG.equals(perTestFile.getDebugStatus())) {
                perTestReports = new DebugCaseReportsEntity();
            } else {
                perTestReports = new PerformanceCaseReportsEntity();
            }

            // 保存测试报告performanceCaseReportsDao
            perTestReports.setCaseId(perTestFile.getCaseId());
            perTestReports.setFileId(fileId);
            perTestReports.setOriginName(reportOirginName);
            perTestReports.setReportName(jmxDir + File.separator + csvName);
            perTestReports.setFile(csvFile);
        }

        Map map = new HashMap();
        map.put("jmxFile", jmxFile);
        map.put("csvFile", csvFile);

        String slaveStr = getSlaveIPPort();
        // slaveStr用来做脚本是否是分布式执行的判断，不入库
        perTestFile.setSlaveStr(slaveStr);

        try {
            if (performanceTestUtils.isUseJmeterScript()) {
                excuteJmeterRunByScript(perTestFile, perTestReports, map);
            } else {
                excuteJmeterRunByScript(perTestFile, perTestReports, map);
            }
        } catch (RRException e) {
            // 解决BUG：分布式情况下禁止调试，却无法在前端提示。修复后会提示用户“请关闭调试模式！”
            return e.getMsg();
        }

        // 保存文件在执行状态，用于前台提示以及后端查看排序
        // 脚本基本执行无异常，才会保存状态入库
        perTestFile.setStatus(PerformanceTestUtils.RUNNING);
        update(perTestFile);

        if(perTestReports != null){
            // 将调试测试报告放到调试的数据表中记录
                if (perTestReports instanceof DebugCaseReportsEntity) {
                    debugCaseReportsDao.save((DebugCaseReportsEntity)perTestReports);
                } else {
                perTestCaseRortsDao.save(perTestReports);
            }
        }

        if(StringUtils.isNotEmpty(slaveStr)) {
            if (checkSlaveLocal()){
                return "分布式压测开始! 节点机为: " + slaveStr + ",Local节点" + " 共" + (slaveStr.split(",").length + 1) +" 个节点";
            }
            return "分布式压测开始！节点机为：" + slaveStr + "  共 " + slaveStr.split(",").length + " 个节点";
        }
        return "master主机压测开始!";

    }
    /**
     * 执行Jmeter的脚本文件，采用Apache的commons-exec来执行。
     */
    public void excuteJmeterRunByScript(PerformanceCaseFileEntity perTestFile, PerformanceCaseReportsEntity perTestReports,
                                        Map map) {
        // 由于调试模式需要动态修改测试结果保存样式为JTL，而性能测试报告需要的格式为CSV（压测系统默认模式）。
        // 所以调试模式下如果运行脚本则无法生成测试报告，造成系统功能缺失。
        // 为避免后续问题，jmx如果使用了调试，则无法使用脚本方式运行。
        if (PerformanceTestUtils.NEED_DEBUG.equals(perTestFile.getDebugStatus())) {
            throw new RRException("不支持在分布式slave节点调试，请关闭调试模式！");
        }

        String jmeterHomeBin = performanceTestUtils.getJmeterHomeBin();
        String jmeterExc = performanceTestUtils.getJmeterExc();
        CommandLine cmdLine = new CommandLine(jmeterHomeBin + File.separator + jmeterExc);
        // 设置参数， -n 命令行模式
        cmdLine.addArgument("-n");
        // -t 设置JMX脚本路径
        cmdLine.addArgument("-t");
        cmdLine.addArgument("${jmxFile}");

        String slaveStr = perTestFile.getSlaveStr();
        if (StringUtils.isNotEmpty(slaveStr)){
            cmdLine.addArgument("-R");
            cmdLine.addArgument(slaveStr);
        }

        if(PerformanceTestUtils.NEED_REPORT.equals(perTestFile.getReportStatus())) {
            cmdLine.addArgument("-l");
            cmdLine.addArgument("${csvFile}");
        }

        // 指定需要执行的jmx脚本
        cmdLine.setSubstitutionMap(map);

        DefaultExecutor executor = new DefaultExecutor();

        try {
            // 非阻塞方式运行脚本，不耽误前端的操作
            // 流操作在executor执行源码中已经关闭
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
            PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream, errorStream);
            // 设置成功输入以及错误输出，用于追查问题，打印日志
            executor.setStreamHandler(streamHandler);
            // 自定义的钩子程序
            FileExecuteResultHandler resultHandler = new FileExecuteResultHandler(perTestFile, perTestReports,
                    this, outputStream, errorStream);

            // 执行脚本命令
            executor.execute(cmdLine, resultHandler);
        } catch (IOException e) {
            // 保存状态，执行出现异常
            perTestFile.setStatus(PerformanceTestUtils.RUN_ERROR);
            update(perTestFile);
            if(perTestReports != null) {
                perTestCaseRortsDao.save(perTestReports);
            }
            throw new RRException("执行启动脚本异常!", e);
        }

    }

    /**
     * 本地执行Jmeter的脚本文件，采用Apache-Jmeter-Api来执行。
     */
    public void excuteJmeterRunLocal(PerformanceCaseFileEntity stressTestFile, PerformanceCaseReportsEntity stressTestReports, Map map) throws RuntimeException {
        File jmxFile = (File) map.get("jmxFile");
        File csvFile = (File) map.get("csvFile");

        try {
            String slaveStr = stressTestFile.getSlaveStr();

            performanceTestUtils.setJmeterProperties();
            if (PerformanceTestUtils.NEED_DEBUG.equals(stressTestFile.getDebugStatus())) {
                if (StringUtils.isNotEmpty(slaveStr)) {//分布式的方式启动
                    throw new RRException("不支持在分布式slave节点调试，请关闭调试模式！");
                }
                performanceTestUtils.setJmeterOutputFormat();
            }

            // 默认的FileServer.getFileServer().setBaseForScript(jmxFile); 方法无法支持单进程内
            // 的多脚本的同时进行，是Jmeter源生的对单进程内多执行脚本支持就不好。
            // 主要原因是参数化文件会混淆，设置脚本base目录时会判断当前进程内是否存在正在使用的文件。
            // 而FileServer是单例的（不支持并发的单例），对其无法修复。
            if (!PerformanceTestUtils.checkExistRunningScript()) {
                FileServer.getFileServer().setBaseForScript(jmxFile);
            } else {
                // 改变脚本名称
                FileServer.getFileServer().setScriptName(jmxFile.getName());
                // 通过反射改变base名称
                Field baseField = FileServer.getFileServer().getClass().getDeclaredField("base");
                // 设置允许访问
                baseField.setAccessible(true);
                baseField.set(FileServer.getFileServer(), jmxFile.getAbsoluteFile().getParentFile());
            }

            HashTree jmxTree = SaveService.loadTree(jmxFile);
            JMeter.convertSubTree(jmxTree);

            JmeterResultCollector jmeterResultCollector = null;
            // 如果不要监控也不要测试报告，则不加自定义的Collector到文件里，让性能最大化。
            if (PerformanceTestUtils.NEED_REPORT.equals(stressTestFile.getReportStatus())
                    || PerformanceTestUtils.NEED_WEB_CHART.equals(stressTestFile.getWebchartStatus())) {
                // 添加收集观察监听程序。
                // 具体情况的区分在其程序内做分别，原因是情况较多，父子类的实现不现实。
                // 使用自定义的Collector，用于前端绘图的数据收集和日志收集等。
                jmeterResultCollector = new JmeterResultCollector(stressTestFile);

                // 对调试模式的处理，让结果文件保存为xml格式
                if (PerformanceTestUtils.NEED_DEBUG.equals(stressTestFile.getDebugStatus())) {
                    jmeterResultCollector.getSaveConfig().setAsXml(true);
                }

                jmeterResultCollector.setFilename(csvFile.getPath());
                jmxTree.add(jmxTree.getArray()[0], jmeterResultCollector);
            }

            // 增加程序执行结束的监控
            // engines 为null停止脚本后不会直接停止远程client的JVM进程。
            // reportGenerator 为null停止后脚本后不会直接生成测试报告。
            jmxTree.add(jmxTree.getArray()[0], new JmeterListenToTest(null,
                    null, this, stressTestFile.getFileId()));

            // Used for remote notification of threads start/stop,see BUG 54152
            // Summariser uses this feature to compute correctly number of threads
            // when NON GUI mode is used
            jmxTree.add(jmxTree.getArray()[0], new RemoteThreadsListenerTestElement());

            // 在内存中保留启动信息使用。
            List<JMeterEngine> engines = new LinkedList<>();
            JmeterRunEntity jmeterRunEntity = new JmeterRunEntity();
            jmeterRunEntity.setperTestFile(stressTestFile);
            jmeterRunEntity.setStressTestReports(stressTestReports);
            jmeterRunEntity.setJmeterResultCollector(jmeterResultCollector);
            jmeterRunEntity.setEngines(engines);
            jmeterRunEntity.setRunStatus(PerformanceTestUtils.RUNNING);

            // 查找脚本文件所用到的所有的参数化文件的名称，当前只查找了CSVData参数化类型的。
            // 将其放入到内存中，为了脚本结束之后关闭这些参数化文件。
            // 后续可能还会有其他文件能得到引用（如文件下载的测试等等），可能还需要扩充此方法。
            ArrayList<String> fileAliaList = new ArrayList<>();
            for (HashTree lht : jmxTree.values()) {
                fillFileAliaList(fileAliaList, lht);
            }
            jmeterRunEntity.setFileAliaList(fileAliaList);

            jmxTree = fixHashTreeDuration(jmxTree, stressTestFile);

            PerformanceTestUtils.jMeterEntity4file.put(stressTestFile.getFileId(), jmeterRunEntity);
            if (StringUtils.isNotEmpty(slaveStr)) {//分布式的方式启动
                java.util.StringTokenizer st = new java.util.StringTokenizer(slaveStr, ",");//$NON-NLS-1$
                List<String> hosts = new LinkedList<>();
                while (st.hasMoreElements()) {
                    hosts.add((String) st.nextElement());
                }
                LocalDistributedRunner localDistributedRunner = new LocalDistributedRunner();
                localDistributedRunner.setStdout(System.out); // NOSONAR
                localDistributedRunner.setStdErr(System.err); // NOSONAR
                localDistributedRunner.init(hosts, jmxTree, getSlaveAddrWeight());
                engines.addAll(localDistributedRunner.getEngines());
                localDistributedRunner.start();

                // 如果配置了，则将本机节点也增加进去
                // 当前只有本地运行的方式支持本机master节点的添加
                if (checkSlaveLocal()) {
                    // JMeterEngine 本身就是线程，启动即为异步执行，resultCollector会监听保存csv文件。
                    JMeterEngine engine = new LocalStandardJMeterEngine(stressTestFile);
                    engine.configure(jmxTree);
                    engine.runTest();
                    // Object engine = engineRun(stressTestFile, jmxTree);
                    engines.add((JMeterEngine) engine);
                }
            } else {//本机运行
                // JMeterEngine 本身就是线程，启动即为异步执行，resultCollector会监听保存csv文件。
                // Object engine = engineRun(stressTestFile, jmxTree);
                JMeterEngine engine = new LocalStandardJMeterEngine(stressTestFile);
                engine.configure(jmxTree);
                engine.runTest();
                engines.add((JMeterEngine) engine);
            }
        } catch (IOException | JMeterEngineException e) {
            throw new RRException("本地执行启动脚本文件异常！", e);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RRException("本地执行启动脚本反射功能时异常！", e);
        }
    }

    /**
     * 将当前脚本所用到的文件保存起来。
     * 目前已知的最常用的有CSVDataSet类型的。
     */
    public ArrayList fillFileAliaList(ArrayList<String> fileAliaList, HashTree hashTree){
        for (Object os : hashTree.keySet()) {
            if(os instanceof CSVDataSet){
                // filename 通过查看源码，没有发现变化的地方，所以让其成为关键字
                String filename = ((CSVDataSet) os).getPropertyAsString("filename");
                fileAliaList.add(filename);
            } else if(os instanceof ThreadGroup){
                fillFileAliaList(fileAliaList, hashTree.get(os));
            }

        }
        return fileAliaList;
    }

    /**
     * 增加脚本默认执行时间的添加（只针对永远循环执行的线程组）。
     * 目的是避免脚本忘了停止，执行时间过长，造成灾难性后果。
     */
    public HashTree fixHashTreeDuration(HashTree jmxTree, PerformanceCaseFileEntity perTestFile) {
        if(performanceTestUtils.isScriptSchedulerDurationEffect() && perTestFile.getDuration() > 0) {
            for (HashTree item : jmxTree.values()){
                Set treeKeys = item.keySet();
                for (Object key : treeKeys) {
                    if (key instanceof ThreadGroup && ((ThreadGroup) key).getDuration() == 0){
                        ((ThreadGroup) key).setProperty(ThreadGroup.SCHEDULER, true);
                        ((ThreadGroup) key).setProperty(ThreadGroup.DURATION, perTestFile.getDuration());
                    }

                }
            }
        }
        return jmxTree;
    }




}