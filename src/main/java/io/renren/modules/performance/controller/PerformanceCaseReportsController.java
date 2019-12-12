package io.renren.modules.performance.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.performance.entity.PerformanceCaseReportsEntity;
import io.renren.modules.performance.service.PerformanceCaseReportsService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



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
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("performance:performancecasereports:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = performanceCaseReportsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{reportId}")
    @RequiresPermissions("performance:performancecasereports:info")
    public R info(@PathVariable("reportId") Long reportId){
		PerformanceCaseReportsEntity performanceCaseReports = performanceCaseReportsService.getById(reportId);

        return R.ok().put("performanceCaseReports", performanceCaseReports);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("performance:performancecasereports:save")
    public R save(@RequestBody PerformanceCaseReportsEntity performanceCaseReports){
		performanceCaseReportsService.save(performanceCaseReports);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("performance:performancecasereports:update")
    public R update(@RequestBody PerformanceCaseReportsEntity performanceCaseReports){
		performanceCaseReportsService.updateById(performanceCaseReports);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("performance:performancecasereports:delete")
    public R delete(@RequestBody Long[] reportIds){
		performanceCaseReportsService.removeByIds(Arrays.asList(reportIds));

        return R.ok();
    }

}
