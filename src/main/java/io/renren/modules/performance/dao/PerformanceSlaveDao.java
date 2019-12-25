package io.renren.modules.performance.dao;

import io.renren.modules.performance.entity.PerformanceSlaveEntity;
import io.renren.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 性能测试分布式节点表
 * 
 * @author mike.liu
 * @email sunlightcs@gmail.com
 * @date 2019-12-05 15:27:50
 */
@Mapper
public interface PerformanceSlaveDao extends BaseDao<PerformanceSlaveEntity> {

    /**
     * 批量更新
     */
    int updateBatch(Map<String, Object> map);
}
