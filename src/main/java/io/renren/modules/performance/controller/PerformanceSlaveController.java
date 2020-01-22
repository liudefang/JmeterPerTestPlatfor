package io.renren.modules.performance.controller;

import io.renren.common.annotation.SysLog;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.performance.entity.PerformanceSlaveEntity;
import io.renren.modules.performance.service.PerformanceSlaveService;
import io.renren.modules.performance.utils.PerformanceTestUtils;
import io.renren.modules.performance.utils.QueryList;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * 性能测试分布式节点管理
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
        // 查询列表数据
        QueryList query = new QueryList(PerformanceTestUtils.filterParms(params));
        List<PerformanceSlaveEntity> perTestList = performanceSlaveService.queryList(query);
        int total = performanceSlaveService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(perTestList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }

    /**
     * 分布式节点可用列表
     */
    @RequestMapping("/list/enable")
    @RequiresPermissions("performance:performanceslave:slaveList")
    public R listForStatus(@RequestParam Map<String, Object> params) {
        // 查询列表数据
        QueryList query = new QueryList(PerformanceTestUtils.filterParms(params));
        query.put("status", PerformanceTestUtils.ENABLE);
        List<PerformanceSlaveEntity> perTestList = performanceSlaveService.queryList(query);
        int total = performanceSlaveService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(perTestList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }

    /**
     * 分布式节点可用数量
     */
    @RequestMapping("/list/enableTotal")
    @RequiresPermissions("performance:performanceslave:slaveList")
    public R listEnableTotal() {
        Map<String, Object> query = new HashMap<String, Object>();
        query.put("status", PerformanceTestUtils.ENABLE);
        int total = performanceSlaveService.queryTotal(query);

        return R.ok().put("total", total);
    }

    /**
     * 性能测试分布式节点信息
     */
    @RequestMapping("/info/{slaveId}")
    @RequiresPermissions("performance:performanceslave:info")
    public R info(@PathVariable("slaveId") Long slaveId){
		PerformanceSlaveEntity performanceSlave = performanceSlaveService.queryObject(slaveId);

        return R.ok().put("performanceSlave", performanceSlave);
    }

    /**
     * 保存性能测试分布式节点
     */
    @RequestMapping("/save")
    @RequiresPermissions("performance:performanceslave:save")
    public R save(@RequestBody PerformanceSlaveEntity performanceSlave){
        ValidatorUtils.validateEntity(performanceSlave);

		performanceSlaveService.save(performanceSlave);

        return R.ok();
    }

    /**
     * 修改性能测试分布式节点信息
     */
    @RequestMapping("/update")
    @RequiresPermissions("performance:performanceslave:update")
    public R update(@RequestBody PerformanceSlaveEntity performanceSlave){
        ValidatorUtils.validateEntity(performanceSlave);

		performanceSlaveService.update(performanceSlave);

        return R.ok();
    }

    /**
     * 删除性能测试分布式节点
     */
    @RequestMapping("/delete")
    @RequiresPermissions("performance:performanceslave:delete")
    public R delete(@RequestBody Long[] slaveIds){
		performanceSlaveService.deleteBatch(slaveIds);

        return R.ok();
    }

    /**
     * 切换性能测试分布式节点状态
     */
    @SysLog("切换性能测试分布式节点状态")
    @RequestMapping("/batchUpdateStatus")
    @RequiresPermissions("performance:performanceslave:slaveStatusUpdate")
    public R batchUpdateStatus(@RequestParam(value = "slaveIds[]") List<Long> slaveIds,
                               @RequestParam(value = "status") Integer status) {
        for (Long slaveId : slaveIds){
            performanceSlaveService.updateBatchStatus(slaveId, status);
        }
        return R.ok();
    }

    /**
     * 手工强制切换性能测试分布式节点状态
     */
    @SysLog("强制切换性能测试分布式节点状态")
    @RequestMapping("/batchUpdateStatusForce")
    @RequiresPermissions("performance:performanceslave:slaveStatusUpdateForce")
    public R batchUpdateStatusForce(@RequestParam(value = "slaveIds[]") List<Long> slaveIds,
                                    @RequestParam(value = "status") Integer status) {
        performanceSlaveService.updateBatchStatusForce(slaveIds, status);
        return R.ok();
    }

    /**
     * 重启已经启动的性能测试分布式节点（停止状态的分布式节点不变）
     */
    @SysLog("重启已经启动的性能测试分布式节点")
    @RequestMapping("/batchRestart")
    @RequiresPermissions("performance:performanceslave:slaveRestart")
    public R batchRestart(@RequestParam(value = "slaveIds[]") List<Long> slaveIds) {

        for (Long slaveId : slaveIds) {
            performanceSlaveService.restartSingle(slaveId);
        }
        return R.ok();
    }

    /**
     * 校准当前各节点状态（以前台页面状态为准，校准后台进程）
     */
    @SysLog("校准当前各节点状态")
    @RequestMapping("/batchReload")
    public R batchReload() {
        performanceSlaveService.batchReloadStatus();
        return R.ok();
    }

}
