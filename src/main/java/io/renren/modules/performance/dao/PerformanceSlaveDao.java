package io.renren.modules.performance.dao;

import io.renren.modules.performance.entity.PerformanceSlaveEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 性能测试分布式节点表
 * 
 * @author mike.liu
 * @email sunlightcs@gmail.com
 * @date 2019-12-05 15:27:50
 */
@Mapper
public interface PerformanceSlaveDao extends BaseMapper<PerformanceSlaveEntity> {
	
}
