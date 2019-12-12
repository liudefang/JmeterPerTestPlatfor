package io.renren.modules.performance.dao;

import io.renren.modules.performance.entity.PerformanceThreadSetEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 线程组管理
 * 
 * @author mike.liu
 * @email sunlightcs@gmail.com
 * @date 2019-12-05 15:27:50
 */
@Mapper
public interface PerformanceThreadSetDao extends BaseMapper<PerformanceThreadSetEntity> {
	
}
