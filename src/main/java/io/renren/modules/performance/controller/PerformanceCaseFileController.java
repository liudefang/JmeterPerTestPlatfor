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
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("performance:performancecasefile:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = performanceCaseFileService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{fileId}")
    @RequiresPermissions("performance:performancecasefile:info")
    public R info(@PathVariable("fileId") Long fileId){
		PerformanceCaseFileEntity performanceCaseFile = performanceCaseFileService.getById(fileId);

        return R.ok().put("performanceCaseFile", performanceCaseFile);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("performance:performancecasefile:save")
    public R save(@RequestBody PerformanceCaseFileEntity performanceCaseFile){
		performanceCaseFileService.save(performanceCaseFile);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("performance:performancecasefile:update")
    public R update(@RequestBody PerformanceCaseFileEntity performanceCaseFile){
		performanceCaseFileService.updateById(performanceCaseFile);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("performance:performancecasefile:delete")
    public R delete(@RequestBody Long[] fileIds){
		performanceCaseFileService.removeByIds(Arrays.asList(fileIds));

        return R.ok();
    }

}
