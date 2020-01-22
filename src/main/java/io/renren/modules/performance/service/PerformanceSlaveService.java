package io.renren.modules.performance.service;

import io.renren.common.utils.PageUtils;
import io.renren.modules.performance.entity.PerformanceSlaveEntity;

import java.util.List;
import java.util.Map;

/**
 * 性能测试分布式节点表
 *
 * @author mike.liu
 * @email sunlightcs@gmail.com
 * @date 2019-12-05 15:27:50
 */
public interface PerformanceSlaveService  {

    /**
     * 根据ID，查询子节点信息
     */
    PerformanceSlaveEntity queryObject(Long slaveId);

    /**
     * 查询子节点列表
     */
    List<PerformanceSlaveEntity> queryList(Map<String, Object> map);

    /**
     * 查询总数
     */
    int queryTotal(Map<String, Object> map);

    /**
     * 保存
     */
    void save(PerformanceSlaveEntity perTestSlave);

    /**
     * 更新
     */
    void update(PerformanceSlaveEntity perTestSlave);

    /**
     * 批量删除
     */
    void deleteBatch(Long[] slaveIds);

    /**
     * 批量更新状态
     */
    void updateBatchStatus(Long slaveId, Integer status);

    /**
     * 手工强制批量更新状态
     */
    void updateBatchStatusForce(List<Long> slaveIds, Integer status);

    /**
     * 重启节点
     */
    void restartSingle(Long slaveId);

    /**
     * 根据后台节点进程状态，批量刷新节点状态
     */
    void batchReloadStatus();

}

