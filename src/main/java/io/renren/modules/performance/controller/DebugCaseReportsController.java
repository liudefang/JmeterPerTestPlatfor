package io.renren.modules.performance.controller;

import io.renren.common.annotation.SysLog;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.performance.entity.DebugCaseReportsEntity;
import io.renren.modules.performance.service.DebugCaseReportsService;
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
 * 调试/接口测试报告文件表
 *
 * @author mike.liu
 * @email sunlightcs@gmail.com
 * @date 2019-12-05 15:27:50
 */
@RestController
@RequestMapping("performance/debugcasereports")
public class DebugCaseReportsController {
    @Autowired
    private DebugCaseReportsService debugCaseReportsService;

    /**
     * 测试报告列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("performance:debugcasereports:list")
    public R list(@RequestParam Map<String, Object> params){
        //查询列表数据
        QueryList query = new QueryList(PerformanceTestUtils.filterParms(params));
        List<DebugCaseReportsEntity> perReportList = debugCaseReportsService.queryList(query);
        int total = debugCaseReportsService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(perReportList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }


    /**
     * 查询具体测试报告信息
     */
    @RequestMapping("/info/{reportId}")
    @RequiresPermissions("performance:debugcasereports:info")
    public R info(@PathVariable("reportId") Long reportId){
		DebugCaseReportsEntity debugCaseReports = debugCaseReportsService.queryObject(reportId);

        return R.ok().put("debugCaseReports", debugCaseReports);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("performance:debugcasereports:save")
    public R save(@RequestBody DebugCaseReportsEntity debugCaseReports){
		debugCaseReportsService.save(debugCaseReports);

        return R.ok();
    }

    /**
     * 修改测试报告数据
     */
    @RequestMapping("/update")
    @RequiresPermissions("performance:debugcasereports:update")
    public R update(@RequestBody DebugCaseReportsEntity debugCaseReports){
        ValidatorUtils.validateEntity(debugCaseReports);
		debugCaseReportsService.update(debugCaseReports);

        return R.ok();
    }

    /**
     * 删除指定测试报告以及文件
     */
    @SysLog("删除指定测试报告以及文件")
    @RequestMapping("/delete")
    @RequiresPermissions("performance:debugcasereports:delete")
    public R delete(@RequestBody Long[] reportIds){
		debugCaseReportsService.deleteBatch(reportIds);

        return R.ok();
    }

    /**
     * 删除指定测试报告的测试结果文件，目的是避免占用空间太大
     */
    @SysLog("删除调试报告结果文件")
    @RequestMapping("/deleteJtl")
    @RequiresPermissions("performance:debugcasereports:reportDeleteJtl")
    public R deleteJtl(@RequestBody Long[] reportIds) {
        debugCaseReportsService.deleteBatchJtl(reportIds);
        return R.ok();
    }

    /**
     * 生成测试报告及文件
     */
    @SysLog("生成调试测试报告")
    @RequestMapping("/createReport")
    @RequiresPermissions("performance:debugcasereports:reportCreate")
    public R createReport(@RequestBody Long[] reportIds) {
        for (Long reportId : reportIds) {
            DebugCaseReportsEntity debugTestReport = debugCaseReportsService.queryObject(reportId);
            debugTestReport.setStatus(PerformanceTestUtils.RUNNING);
            debugCaseReportsService.update(debugTestReport);
            // 异步生成调试报告
            debugCaseReportsService.createReport(reportId);
        }
        return R.ok();
    }


    /**
     * 下载测试报告
     */
    @SysLog("下载调试测试报告")
    @RequestMapping("/downloadReport/{reportId}")
    @RequiresPermissions("performance:debugcasereports:reportDownLoad")
    public ResponseEntity<InputStreamResource> downloadReport(@PathVariable("reportId") Long reportId) throws IOException {
        DebugCaseReportsEntity reportsEntity = debugCaseReportsService.queryObject(reportId);
        FileSystemResource fileResource = debugCaseReportsService.getReportFile(reportsEntity);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache,no-store,must-revalidate");
        headers.add("Content-Disposition",
                "attachment;filename=" + reportsEntity.getOriginName() + ".html");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.setContentType(MediaType.parseMediaType("application/octet-stream"));

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(fileResource.contentLength())
                .body(new InputStreamResource(fileResource.getInputStream()));
    }
}
