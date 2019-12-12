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

import io.renren.modules.performance.entity.PerformanceThreadSetEntity;
import io.renren.modules.performance.service.PerformanceThreadSetService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 线程组管理
 *
 * @author mike.liu
 * @email sunlightcs@gmail.com
 * @date 2019-12-05 15:27:50
 */
@RestController
@RequestMapping("performance/performancethreadset")
public class PerformanceThreadSetController {
    @Autowired
    private PerformanceThreadSetService performanceThreadSetService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("performance:performancethreadset:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = performanceThreadSetService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{setId}")
    @RequiresPermissions("performance:performancethreadset:info")
    public R info(@PathVariable("setId") String setId){
		PerformanceThreadSetEntity performanceThreadSet = performanceThreadSetService.getById(setId);

        return R.ok().put("performanceThreadSet", performanceThreadSet);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("performance:performancethreadset:save")
    public R save(@RequestBody PerformanceThreadSetEntity performanceThreadSet){
		performanceThreadSetService.save(performanceThreadSet);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("performance:performancethreadset:update")
    public R update(@RequestBody PerformanceThreadSetEntity performanceThreadSet){
		performanceThreadSetService.updateById(performanceThreadSet);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("performance:performancethreadset:delete")
    public R delete(@RequestBody String[] setIds){
		performanceThreadSetService.removeByIds(Arrays.asList(setIds));

        return R.ok();
    }

}
