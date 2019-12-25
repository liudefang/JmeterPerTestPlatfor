package io.renren.modules.performance.service.impl;

import io.renren.common.exception.RRException;
import io.renren.modules.performance.dao.DebugCaseReportsDao;
import io.renren.modules.performance.entity.DebugCaseReportsEntity;
import io.renren.modules.performance.service.DebugCaseReportsService;
import io.renren.modules.performance.utils.PerformanceTestUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@Service("debugCaseReportsService")
public class DebugCaseReportsServiceImpl implements DebugCaseReportsService {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private DebugCaseReportsDao debugCaseReportsDao;

    @Autowired
    private PerformanceTestUtils performanceTestUtils;

    @Override
    public DebugCaseReportsEntity queryObject(Long reportId) {
        return debugCaseReportsDao.queryObject(reportId);
    }

    @Override
    public List<DebugCaseReportsEntity> queryList(Map<String, Object> map) {
        return debugCaseReportsDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return debugCaseReportsDao.queryTotal(map);
    }

    @Override
    public void save(DebugCaseReportsEntity debugCaseReports) {
        debugCaseReportsDao.save(debugCaseReports);

    }

    @Override
    public void update(DebugCaseReportsEntity debugCaseReports) {
        debugCaseReportsDao.update(debugCaseReports);

    }

    @Override
    public void deleteBatch(Long[] reportIds) {
        Arrays.asList(reportIds).stream().forEach(reportId ->{
            DebugCaseReportsEntity debugTestReport = queryObject(reportId);
            String casePath = performanceTestUtils.getCasePath();
            String reportName = debugTestReport.getReportName();
            // jtl结果文件路径
            String jtlPath = casePath + File.separator + reportName;
            // 调试报告文件
            String reportPath = jtlPath.substring(0, jtlPath.lastIndexOf(".")) + ".html";
            try {
                // 删除测试报告
                FileUtils.forceDelete(new File(reportPath));
            } catch (FileNotFoundException e) {
                logger.error("要删除的测试报告文件找不到(删除成功)  " + e.getMessage());
            } catch (IOException e) {
                throw new RRException("删除测试报告文件异常失败", e);
            }
            deleteReportJTL(debugTestReport);
            performanceTestUtils.deleteJmxDir(reportPath);
        });
        debugCaseReportsDao.deleteBatch(reportIds);

    }


    /**
     * 仅删除测试结果文件
     */
    @Override
    public void deleteBatchJtl(Long[] reportIds) {
        Arrays.asList(reportIds).stream().forEach(reportId -> {
            DebugCaseReportsEntity debugTestReport = queryObject(reportId);
            deleteReportJTL(debugTestReport);

            // 更新数据库，目的是不允许生成测试报告
            // 删除了Jtl文件，同时报告没有生成过，那这个压测其实已经废了。
            debugTestReport.setFileSize(0L);
            update(debugTestReport);
        });

    }

    /**
     * 采用异步线程池来实现。
     */
    @Override
    @Transactional
    @Async("asyncServiceExecutor")
    public void createReport(Long[] reportIds) {
        for (Long reportId : reportIds){
            createReport(reportId);
        }

    }

    /**
     * 采用异步线程池来实现。
     */
    @Override
    @Transactional
    @Async("asyncServiceExecutor")
    public void createReport(Long reportId) {
        DebugCaseReportsEntity debugTestReport = queryObject(reportId);
        // 首先判断，如果file_size为0或者空，说明没有结果文件，直接报错打断
        if (debugTestReport.getFileSize() == 0L || debugTestReport.getFileSize() == null) {
            throw new RRException("找不到调试测试结果文件，无法生成测试报告！");
        }

        String casePath = performanceTestUtils.getCasePath();
        String reportName = debugTestReport.getReportName();

        //jtl结果文件路径
        String jtlPath = casePath + File.separator + reportName;
        //测试报告文件
        String reportPath = jtlPath.substring(0, jtlPath.lastIndexOf(".")) + ".html";

        //如果测试报告文件目录已经存在，说明生成过测试报告，直接打断
        File reportDir = new File(reportPath);
        if (reportDir.exists()) {
            throw new RRException("已经存在测试报告不要重复创建！");
        }

        Source srcJtl = new StreamSource(new File(jtlPath));
        Result destResult = new StreamResult(reportDir);
        Source xsltSource;
        try {
            xsltSource = new StreamSource(ResourceUtils.getURL(PerformanceTestUtils.xslFilePath).toURI().toASCIIString());
        } catch (FileNotFoundException | URISyntaxException e) {
            throw new RRException("xsl文件加载失败!", e);
        }
        try {
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer(xsltSource);
            transformer.transform(srcJtl, destResult);
        } catch (Exception e) {
            //保存状态，执行出现异常
            debugTestReport.setStatus(PerformanceTestUtils.RUN_ERROR);
            update(debugTestReport);
            throw new RRException("执行生成测试报告脚本异常！", e);
        }
        //设置开始执行命令生成报告
        debugTestReport.setStatus(PerformanceTestUtils.RUN_SUCCESS);
        update(debugTestReport);

    }

    @Override
    public void deleteReportJTL(DebugCaseReportsEntity debugCaseReports) {

        String casePath = performanceTestUtils.getCasePath();
        String reportName =debugCaseReports.getReportName();

        //jtl 结果文件路径
        String jtlPath = casePath + File.separator + reportName;

        //为了FileNotFoundException，找不到说明已经删除
        try {
            FileUtils.forceDelete(new File(jtlPath));
        } catch (FileNotFoundException e) {
            logger.error("要删除的测试报告来源文件找不到(删除成功)  " + e.getMessage());
        } catch (IOException e) {
            throw new RRException("删除测试报告来源文件异常失败", e);
        }
    }

    @Override
    public FileSystemResource getReportFile(DebugCaseReportsEntity debugTestReport) throws IOException {
        String casePath = performanceTestUtils.getCasePath();
        String reportName = debugTestReport.getReportName();

        //jtl结果文件路径
        String jtlPath = casePath + File.separator + reportName;
        // 测试报告文件
        String reportPath = jtlPath.substring(0, jtlPath.lastIndexOf(".")) + ".html";
        FileSystemResource reportFileResource = new FileSystemResource(reportPath);
        return reportFileResource;
    }
}