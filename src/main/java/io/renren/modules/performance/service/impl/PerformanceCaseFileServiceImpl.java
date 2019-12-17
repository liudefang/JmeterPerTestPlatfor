package io.renren.modules.performance.service.impl;

import io.renren.common.exception.RRException;
import io.renren.modules.performance.dao.PerformanceCaseDao;
import io.renren.modules.performance.entity.PerformanceCaseEntity;
import io.renren.modules.performance.service.PerformanceCaseService;
import io.renren.modules.performance.utils.PerformanceTestUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.performance.dao.PerformanceCaseFileDao;
import io.renren.modules.performance.entity.PerformanceCaseFileEntity;
import io.renren.modules.performance.service.PerformanceCaseFileService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service("performanceCaseFileService")
public class PerformanceCaseFileServiceImpl extends ServiceImpl<PerformanceCaseFileDao, PerformanceCaseFileEntity> implements PerformanceCaseFileService {

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

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PerformanceCaseFileEntity> page = this.page(
                new Query<PerformanceCaseFileEntity>().getPage(params),
                new QueryWrapper<PerformanceCaseFileEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PerformanceCaseFileEntity queryObject(Long fileId) {
        return queryObject(fileId);
    }

    @Override
    public List<PerformanceCaseFileEntity> queryList(Map<String, Object> map) {
        return queryList(map);
    }

    @Override
    public List<PerformanceCaseFileEntity> queryList(Long caseId) {
        Map query = new HashMap<>();
        query.put("caseId", caseId.toString());
        return queryList(query);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return queryTotal(map);
    }


    /**
     * 保存文件以及入库
     */

    @Override
    @Transactional
    public void save(MultipartFile file, String filePath, PerformanceCaseEntity stressCase, PerformanceCaseFileEntity stressCaseFile) {
        // 保存文件放这里,是因为有事务.
        // 保存数据放在最前,因为当前文件重名校验是根据数据库异常得到
        try {
            String fileMd5 = DigestUtils.md5Hex(file.getBytes());
            stressCaseFile.setFileMd5(fileMd5);
        } catch (IOException e){
            throw new RRException("获取上传文件的MD5失败", e);
        }
        if(stressCaseFile.getFileId() != null && stressCaseFile.getFileId() > 0L) {
            // 替换文件，同时修改添加时间，便于前端显示。
            stressCaseFile.setAddTime(new Date());
            update(stressCaseFile);
        } else {
            // 保存文件，同时解决第一次保存文件时实体没有写入用例名称
            stressCaseFile.setCaseName(stressCase.getCaseName());
            save(stressCaseFile);
        }
        // 肯定存在已有的用例信息
        PerformanceCaseService.update(stressCase);
        PerformanceTestUtils.saveFile(file, filePath);
        // 对jmx脚本将线程组配置信息入库(默认不入库)
        if(PerformanceTestUtils.isGetThreadGroup() && filePath.substring(filePath.length()-3).equals("jmx")){
            // 入库前清理已有配置项

        }
    }

    @Override
    public void update(PerformanceCaseFileEntity perTestFile) {
        update(perTestFile);

    }

    @Override
    public void update(MultipartFile file, String filePath, PerformanceCaseEntity perTestCase, PerformanceCaseFileEntity perTestCaseFile) {
        try {
            String fileMd5 = DigestUtils.md5Hex(file.getBytes());
            perTestCaseFile.setFileMd5(fileMd5);
        } catch (IOException e) {
            throw new RRException("获取上传文件的MD5失败!", e);
        }
        update(perTestCaseFile);
        PerformanceCaseService.update(perTestCase);
        PerformanceTestUtils.saveFile(file, filePath);

    }

    @Override
    public void updateStatusBatch(PerformanceCaseFileEntity perTestFile) {
        Map<String, Object> map = new HashMap<>();
        map.put("fileIdList", perTestFile.getFileIdList());
        map.put("reportStatus", perTestFile.getReportStatus());



    }

    @Override
    public void deleteBatch(Object[] fileIds) {

    }

    @Override
    public String run(Long[] fileIds) {
        return null;
    }

    @Override
    public void stop(Long[] fileIds) {

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
    public String runSingle(Long fileId) {
        return null;
    }

    @Override
    public void stopSingle(Long fileId) {

    }


}