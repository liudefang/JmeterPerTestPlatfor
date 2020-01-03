package io.renren.modules.performance.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.renren.common.annotation.SysLog;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.performance.utils.PerformanceTestUtils;
import io.renren.modules.performance.utils.QueryList;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.performance.entity.PerformanceCaseFileEntity;
import io.renren.modules.performance.service.PerformanceCaseFileService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 性能测试用例文件表
 *
 * @author mike.liu
 * @email sunlightcs@gmail.com
 * @date 2019-12-05 15:27:50
 */
@RestController
@RequestMapping("performance/performancecasefile")
public class PerformanceCaseFileController {
    @Autowired
    private PerformanceCaseFileService performanceCaseFileService;

    /**
     * 参数化文件，用例文件列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("performance:performancecasefile:list")
    public R list(@RequestParam Map<String, Object> params){
        QueryList query = new QueryList(PerformanceTestUtils.filterParms(params));
        List<PerformanceCaseFileEntity> jobList = performanceCaseFileService.queryList(query);
        int total = performanceCaseFileService.queryTotal(query);

        PageUtils pageUtils = new PageUtils(jobList, total, query.getLimit(), query.getPage());


        return R.ok().put("page", pageUtils);
    }


    /**
     * 查询具体文件信息
     */
    @RequestMapping("/info/{fileId}")
    @RequiresPermissions("performance:performancecasefile:info")
    public R info(@PathVariable("fileId") Long fileId){
		PerformanceCaseFileEntity performanceCaseFile = performanceCaseFileService.queryObject(fileId);

        return R.ok().put("performanceCaseFile", performanceCaseFile);
    }


    /**
     * 修改性能测试用例脚本文件
     */
    @SysLog("修改性能测试用例脚本文件")
    @RequestMapping("/fileUpdate")
    @RequiresPermissions("performance:performancecasefile:fileUpdate")
    public R update(@RequestBody PerformanceCaseFileEntity performanceCaseFile){
        ValidatorUtils.validateEntity(performanceCaseFile);

        if(performanceCaseFile.getFileIdList() != null && performanceCaseFile.getFileIdList().length > 0){
            performanceCaseFileService.updateStatusBatch(performanceCaseFile);
        } else {
            performanceCaseFileService.update(performanceCaseFile);
        }

        return R.ok();
    }

    /**
     * 删除指定文件
     */
    @SysLog("删除指定文件")
    @RequestMapping("/delete")
    @RequiresPermissions("performance:performancecasefile:fileDelete")
    public R delete(@RequestBody Long[] fileIds){
		performanceCaseFileService.deleteBatch(fileIds);

        return R.ok();
    }

}
