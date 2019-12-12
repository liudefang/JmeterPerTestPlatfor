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

import io.renren.modules.performance.entity.DebugCaseReportsEntity;
import io.renren.modules.performance.service.DebugCaseReportsService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



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
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("performance:debugcasereports:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = debugCaseReportsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{reportId}")
    @RequiresPermissions("performance:debugcasereports:info")
    public R info(@PathVariable("reportId") Long reportId){
		DebugCaseReportsEntity debugCaseReports = debugCaseReportsService.getById(reportId);

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
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("performance:debugcasereports:update")
    public R update(@RequestBody DebugCaseReportsEntity debugCaseReports){
		debugCaseReportsService.updateById(debugCaseReports);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("performance:debugcasereports:delete")
    public R delete(@RequestBody Long[] reportIds){
		debugCaseReportsService.removeByIds(Arrays.asList(reportIds));

        return R.ok();
    }

}
