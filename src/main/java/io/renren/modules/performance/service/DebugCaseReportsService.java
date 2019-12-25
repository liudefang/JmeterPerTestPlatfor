package io.renren.modules.performance.service;

import io.renren.modules.performance.entity.DebugCaseReportsEntity;
import org.springframework.core.io.FileSystemResource;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 调试/接口测试报告文件表
 *
 * @author mike.liu
 * @email sunlightcs@gmail.com
 * @date 2019-12-05 15:27:50
 */
public interface DebugCaseReportsService {

    /**
     * 根据ID，查询文件
     */
    DebugCaseReportsEntity queryObject(Long reportId);

    /**
     * 查询文件列表
     */
    List<DebugCaseReportsEntity> queryList(Map<String, Object> map);

    /**
     * 查询总数
     */
    int queryTotal(Map<String, Object> map);

    /**
     * 保存性能测试用例文件
     */
    void save(DebugCaseReportsEntity debugCaseReports);

    /**
     * 更新性能测试用例信息
     */
    void update(DebugCaseReportsEntity debugCaseReports);

    /**
     * 批量删除
     */
    void deleteBatch(Long[] reportIds);

    /**
     * 批量删除测试报告的来源CSV文件
     */
    void deleteBatchJtl(Long[] reportIds);

    /**
     * 生成测试报告
     */
    void createReport(Long[] reportIds);

    /**
     * 生成测试报告
     */
    void createReport(Long reportId);

    /**
     * 批量删除测试报告的来源JTL文件
     */
    void deleteReportJTL(DebugCaseReportsEntity debugCaseReports);


    FileSystemResource getReportFile(DebugCaseReportsEntity reportsEntity) throws IOException;
}

