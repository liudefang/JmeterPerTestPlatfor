package io.renren.modules.performance.service.impl;

import com.sun.org.apache.xalan.internal.utils.ConfigurationError;
import io.renren.common.exception.RRException;
import io.renren.modules.performance.dao.PerformanceCaseReportsDao;
import io.renren.modules.performance.entity.PerformanceCaseReportsEntity;
import io.renren.modules.performance.jmeter.report.LocalReportGenerator;
import io.renren.modules.performance.service.PerformanceCaseReportsService;
import io.renren.modules.performance.utils.PerformanceTestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.jmeter.report.config.ConfigurationException;
import org.apache.jmeter.report.dashboard.GenerationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


@Service("performanceCaseReportsService")
public class PerformanceCaseReportsServiceImpl implements PerformanceCaseReportsService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PerformanceCaseReportsDao performanceCaseReportsDao;

    @Autowired
    private PerformanceTestUtils performanceTestUtils;

    @Override
    public PerformanceCaseReportsEntity queryObject(Long reportId) {
        return performanceCaseReportsDao.queryObject(reportId);
    }


    @Override
    public List<PerformanceCaseReportsEntity> queryList(Map<String, Object> map) {
        return performanceCaseReportsDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return performanceCaseReportsDao.queryTotal(map);
    }

    @Override
    public void save(PerformanceCaseReportsEntity perTestCaseReports) {
        performanceCaseReportsDao.save(perTestCaseReports);

    }

    @Override
    public void update(PerformanceCaseReportsEntity perTestCaseReports) {
        performanceCaseReportsDao.update(perTestCaseReports);

    }

    @Override
    @Transactional
    public void deleteBatch(Long[] reportIds) {
        Arrays.asList(reportIds).stream().forEach(reportId -> {
            PerformanceCaseReportsEntity perTestReport = queryObject(reportId);
            String casePath = performanceTestUtils.getCasePath();
            String reportName = perTestReport.getReportName();
            //csv结果文件路径
            String csvPath = casePath + File.separator + reportName;
            //测试报告文件目录
            String reportPath = csvPath.substring(0, csvPath.lastIndexOf("."));
            try {
                FileUtils.forceDelete(new File(reportPath));
            } catch (FileNotFoundException e) {
                logger.error("要删除的测试报告文件夹找不到(删除成功)" + e.getMessage());
            } catch (IOException e){
                throw new RRException("删除测试报告文件夹异常失败", e);
            }
            deleteReportCSV(perTestReport);
            deleteReportZip(perTestReport);
            performanceTestUtils.deleteJmxDir(reportPath);
        });
        performanceCaseReportsDao.deleteBatch(reportIds);

    }

    @Override
    public void deleteBatchCsv(Long[] reportIds) {
        Arrays.asList(reportIds).stream().forEach(reportId -> {
            PerformanceCaseReportsEntity perTestReport = queryObject(reportId);
            deleteReportCSV(perTestReport);

            // 更新数据库，目的是不允许生成测试报告
            // 删除CSV文件，同时报告生成没有生成过，那这个压测其实已经作废
            perTestReport.setFileSize(0L);
            update(perTestReport);
        });

    }

    /**
     * 实际上Jmeter自身的生成测试报告无法批量进行，命令行会报错，跟这里是否异步执行没关系。
     * 默认是自己本进程内实现生成测试报告。
     */
    @Override
    @Transactional
    public void createReport(Long[] reportIds) {
        for (Long reportId : reportIds){
            PerformanceCaseReportsEntity pertestReport = queryObject(reportId);
            createReport(pertestReport);
        }

    }

    /**
     * 生成测试报告
     */
    @Override
    public void createReport(PerformanceCaseReportsEntity perTestReports) {
        String casePath = performanceTestUtils.getCasePath();
        String reportName = perTestReports.getReportName();

        // csv结果文件路径
        String csvPath = casePath + File.separator + reportName;
        // 测试报告文件目录
        String reportPathDir = csvPath.substring(0, csvPath.lastIndexOf("."));

        // 修复csv文件
        fixReportFile(csvPath);

        // 设置开始执行命令生成报告
        perTestReports.setStatus(PerformanceTestUtils.RUNNING);
        update(perTestReports);

        if(performanceTestUtils.isUseJmeterScript()) {
            generateReportLocal(perTestReports, csvPath, reportPathDir);
        } else {
            generateReportByScript(perTestReports, csvPath, reportPathDir);
        }

    }

    /**
     * 使用本进程多线程生成测试报告
     */
    public void generateReportLocal(PerformanceCaseReportsEntity perTestReport, String csvPath, String reportPathDir) {
        performanceTestUtils.setJmeterProperties();
        LocalReportGenerator generator = null;
        try {
            generator = new LocalReportGenerator(csvPath, null);
            generator.generate(reportPathDir);
            perTestReport.setStatus(PerformanceTestUtils.RUN_SUCCESS);
            update(perTestReport);
        } catch (GenerationException | ConfigurationException e) {
            // 保存状态，执行出现异常
            perTestReport.setStatus(PerformanceTestUtils.RUN_ERROR);
            update(perTestReport);
            throw new RRException("执行生成测试报告脚本异常", e);
        }
    }

    @Override
    public void deleteReportCSV(PerformanceCaseReportsEntity perTestReports) {
        String casePath = performanceTestUtils.getCasePath();
        String reportName = perTestReports.getReportName();
        // csv结果文件路径
        String csvPath = casePath + File.separator + reportName;

        // 为了FileNotFoundException，找不到说明已经删除
        try {
            FileUtils.forceDelete(new File(csvPath));
        } catch (FileNotFoundException e){
            logger.error("要删除的测试报告来源文件找不到(删除成功)  " + e.getMessage());
        } catch (IOException e) {
            throw new RRException("删除测试报告来源文件异常失败", e);
        }

    }

    public void deleteReportZip(PerformanceCaseReportsEntity perTestReports){
        String casePath = performanceTestUtils.getCasePath();
        String reportName = perTestReports.getReportName();
        // csv结果文件路径
        String csvPath = casePath + File.separator + reportName;
        // 测试报告文件目录
        String reportPathDir = csvPath.substring(0, csvPath.lastIndexOf("."));
        // zip文件名
        String reportZipPath = reportPathDir + ".zip";

        // 为了FileNotFoundException，找不到说明已经删除
        try {
            FileUtils.forceDelete(new File(reportZipPath));
        } catch (FileNotFoundException e) {
            logger.error("要删除的测试报告zip文件找不到(删除成功)  " + e.getMessage());
        } catch (IOException e) {
            throw new RRException("删除测试报告zip文件异常失败", e);
        }

    }

    /**
     * 获取zip文件
     * @param reportsEntity
     * @return
     * @throws IOException
     */
    @Override
    public FileSystemResource getZipFile(PerformanceCaseReportsEntity reportsEntity) throws IOException {
        String casePath = performanceTestUtils.getCasePath();
        String reportName = reportsEntity.getReportName();
        // csv结果文件路径
        String csvPath = casePath + File.separator + reportName;
        // 测试报告文件目录
        String reportPathDir = csvPath.substring(0, csvPath.lastIndexOf("."));

        // 如果测试报告文件目录不存在，直接打断
        File reportDir = new File(reportPathDir);
        if(!reportDir.exists()) {
            throw new RRException("请先生成测试报告!");
        }

        // zip文件名
        String reportZipPath = reportPathDir + ".zip";

        FileSystemResource zipFileResource = new FileSystemResource(reportZipPath);

        if (!zipFileResource.exists()) {
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(reportZipPath), Charset.forName("GBK"));
            try {
                File zipFile = new File(reportPathDir);
                zip(out, zipFile, zipFile.getName());
            } finally {
                out.close();
            }
        }
        return zipFileResource;
    }

    /**
     * 递归打包文件夹/文件
     */
    public void zip(ZipOutputStream zOut, File file, String name) throws IOException{
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            name += "/";
            zOut.putNextEntry(new ZipEntry(name));
            for (File listFile : listFiles) {
                zip(zOut, listFile, name + listFile.getName());
            }
        } else {
            FileInputStream in = new FileInputStream(file);
            try {
                zOut.putNextEntry(new ZipEntry(name));
                byte[] buffer = new byte[8192];
                int len;
                while ((len = in.read(buffer)) > 0) {
                    zOut.write(buffer, 0, len);
                    zOut.flush();
                }
            } finally {
                in.close();
            }
        }
    }

}