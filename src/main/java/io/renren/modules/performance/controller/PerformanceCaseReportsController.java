package io.renren.modules.performance.controller;

import io.renren.common.annotation.SysLog;
import io.renren.common.exception.RRException;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.performance.entity.PerformanceCaseReportsEntity;
import io.renren.modules.performance.service.PerformanceCaseReportsService;
import io.renren.modules.performance.utils.PerformanceTestUtils;
import io.renren.modules.performance.utils.QueryList;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * 性能测试报告文件表
 *
 * @author mike.liu
 * @email sunlightcs@gmail.com
 * @date 2019-12-05 15:27:51
 */
@RestController
@RequestMapping("performance/performancecasereports")
public class PerformanceCaseReportsController {
    @Autowired
    private PerformanceCaseReportsService performanceCaseReportsService;

    /**
     * 测试报告列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("performance:performancecasereports:list")
    public R list(@RequestParam Map<String, Object> params){
        // 查询列表数据
        QueryList query = new QueryList(PerformanceTestUtils.filterParms(params));
        List<PerformanceCaseReportsEntity> perTestList = performanceCaseReportsService.queryList(query);
        int total = performanceCaseReportsService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(perTestList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }


    /**
     * 查询具体测试报告信息
     */
    @RequestMapping("/info/{reportId}")
    @RequiresPermissions("performance:performancecasereports:info")
    public R info(@PathVariable("reportId") Long reportId){
		PerformanceCaseReportsEntity performanceCaseReports = performanceCaseReportsService.queryObject(reportId);

        return R.ok().put("performanceCaseReports", performanceCaseReports);
    }
    

    /**
     * 修改性能测试用例报告
     */
    @RequestMapping("/update")
    @RequiresPermissions("performance:performancecasereports:update")
    public R update(@RequestBody PerformanceCaseReportsEntity performanceCaseReports){
        ValidatorUtils.validateEntity(performanceCaseReports);
		performanceCaseReportsService.update(performanceCaseReports);

        return R.ok();
    }

    /**
     * 删除指定测试报告及文件
     */
    @RequestMapping("/delete")
    @RequiresPermissions("performance:performancecasereports:delete")
    public R delete(@RequestBody Long[] reportIds){
		performanceCaseReportsService.deleteBatch(reportIds);

        return R.ok();
    }

    /**
     * 删除指定测试报告的测试结果文件，目的是避免占用空间太大
     */
    @SysLog("删除性能测试报告结果文件")
    @RequestMapping("/deleteCsv")
    @RequiresPermissions("performance:performancecasereports:reportDeleteCsv")
    public R deleteCsv(@RequestBody Long[] reportIds) {
        performanceCaseReportsService.deleteBatchCsv(reportIds);
        return R.ok();
    }

    /**
     * 生成测试报告及文件
     */
    @SysLog("生成性能测试报告")
    @RequestMapping("/createReport")
    @RequiresPermissions("performance:performancecasereports:reportCreate")
    public R createReport(@RequestBody Long[] reportIds) {
        for (Long reportId : reportIds) {
            PerformanceCaseReportsEntity stressTestReport = performanceCaseReportsService.queryObject(reportId);

            // 首先判断，如果file_size为0或者空，说明没有结果文件，直接报错打断。
            // if (stressTestReport.getFileSize() == 0L || stressTestReport.getFileSize() == null) {
            //     throw new RRException("找不到测试结果文件，无法生成测试报告！");
            // }
            //如果测试报告文件目录已经存在，说明生成过测试报告，直接打断
            if (PerformanceTestUtils.RUN_SUCCESS.equals(stressTestReport.getStatus())) {
                throw new RRException("已经存在测试报告不要重复创建！");
            }
            performanceCaseReportsService.createReport(stressTestReport);
        }
        return R.ok();
    }


    /**
     * 下载测试报告zip包
     */
    @SysLog("下载测试报告zip包")
    @RequestMapping("/downloadReport/{reportId}")
    @RequiresPermissions("performance:performancecasereports:reportDownLoad")
    public ResponseEntity<InputStreamResource> downloadReport(@PathVariable("reportId") Long reportId) throws IOException {
        PerformanceCaseReportsEntity reportsEntity = performanceCaseReportsService.queryObject(reportId);
        FileSystemResource zipFile = performanceCaseReportsService.getZipFile(reportsEntity);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache,no-store,must-revalidate");
        headers.add("Content-Disposition",
                "attachment;filename=" + reportsEntity.getOriginName() + ".zip");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.setContentType(MediaType.parseMediaType("application/octet-stream"));

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(zipFile.contentLength())
                .body(new InputStreamResource(zipFile.getInputStream()));
    }
}
