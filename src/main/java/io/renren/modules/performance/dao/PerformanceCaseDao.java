package io.renren.modules.performance.dao;

import io.renren.modules.performance.entity.PerformanceCaseEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 性能测试用例表
 * 
 * @author mike.liu
 * @email sunlightcs@gmail.com
 * @date 2019-12-05 15:27:50
 */
@Mapper
public interface PerformanceCaseDao extends BaseMapper<PerformanceCaseEntity> {
	
}
