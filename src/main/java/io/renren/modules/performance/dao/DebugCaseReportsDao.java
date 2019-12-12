package io.renren.modules.performance.dao;

import io.renren.modules.performance.entity.DebugCaseReportsEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 调试/接口测试报告文件表
 * 
 * @author mike.liu
 * @email sunlightcs@gmail.com
 * @date 2019-12-05 15:27:50
 */
@Mapper
public interface DebugCaseReportsDao extends BaseMapper<DebugCaseReportsEntity> {
	
}
