package io.renren.modules.performance.dao;

import io.renren.modules.performance.entity.PerformanceCaseReportsEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 性能测试报告文件表
 * 
 * @author mike.liu
 * @email sunlightcs@gmail.com
 * @date 2019-12-05 15:27:51
 */
@Mapper
public interface PerformanceCaseReportsDao extends BaseMapper<PerformanceCaseReportsEntity> {
	
}