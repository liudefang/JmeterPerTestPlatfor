package io.renren.modules.performance.service;

import io.renren.modules.performance.entity.PerformanceCaseReportsEntity;
import org.springframework.core.io.FileSystemResource;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 性能测试报告
 *
 * @author mike.liu
 * @email sunlightcs@gmail.com
 * @date 2019-12-05 15:27:51
 */
public interface PerformanceCaseReportsService  {

    //PageUtils queryPage(Map<String, Object> params);
    /**
     * 根据ID，查找文件
     */
    PerformanceCaseReportsEntity queryObject(Long reportId);

    /**
     * 查询文件列表
     */
    List<PerformanceCaseReportsEntity> queryList(Map<String, Object> map);

    /**
     * 查询总数
     */
    int queryTotal(Map<String, Object> map);

    /**
     * 保存性能测试用例文件
     */
    void save(PerformanceCaseReportsEntity perTestCaseReports);

    /**
     * 更新性能测试用例信息
     */
    void update(PerformanceCaseReportsEntity perTestCaseReports);

    /**
     * 批量删除
     */
    void deleteBatch(Long[] reportIds);

    /**
     * 批量删除测试报告的来源CSV文件
     */
    void deleteBatchCsv(Long[] reportIds);

    /**
     * 生成测试报告
     */
    void createReport(Long[] reportIds);

    /**
     * 生成测试报告
     */
    void createReport(PerformanceCaseReportsEntity perTestReports);

    /**
     * 批量删除测试报告的来源CSV文件
     */
    void deleteReportCSV(PerformanceCaseReportsEntity perTestReports);

    FileSystemResource getZipFile(PerformanceCaseReportsEntity reportsEntity) throws IOException;

}

