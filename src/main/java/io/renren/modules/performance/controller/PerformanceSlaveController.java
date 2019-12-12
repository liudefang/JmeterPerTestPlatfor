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

import io.renren.modules.performance.entity.PerformanceSlaveEntity;
import io.renren.modules.performance.service.PerformanceSlaveService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 性能测试分布式节点表
 *
 * @author mike.liu
 * @email sunlightcs@gmail.com
 * @date 2019-12-05 15:27:50
 */
@RestController
@RequestMapping("performance/performanceslave")
public class PerformanceSlaveController {
    @Autowired
    private PerformanceSlaveService performanceSlaveService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("performance:performanceslave:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = performanceSlaveService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{slaveId}")
    @RequiresPermissions("performance:performanceslave:info")
    public R info(@PathVariable("slaveId") Long slaveId){
		PerformanceSlaveEntity performanceSlave = performanceSlaveService.getById(slaveId);

        return R.ok().put("performanceSlave", performanceSlave);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("performance:performanceslave:save")
    public R save(@RequestBody PerformanceSlaveEntity performanceSlave){
		performanceSlaveService.save(performanceSlave);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("performance:performanceslave:update")
    public R update(@RequestBody PerformanceSlaveEntity performanceSlave){
		performanceSlaveService.updateById(performanceSlave);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("performance:performanceslave:delete")
    public R delete(@RequestBody Long[] slaveIds){
		performanceSlaveService.removeByIds(Arrays.asList(slaveIds));

        return R.ok();
    }

}
