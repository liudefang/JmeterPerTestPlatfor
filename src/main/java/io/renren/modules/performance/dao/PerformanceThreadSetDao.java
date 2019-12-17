package io.renren.modules.performance.dao;

import io.renren.modules.performance.entity.PerformanceThreadSetEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 线程组管理
 * 
 * @author mike.liu
 * @email sunlightcs@gmail.com
 * @date 2019-12-05 15:27:50
 */
@Mapper
public interface PerformanceThreadSetDao extends BaseMapper<PerformanceThreadSetEntity> {
    /**
     * 按文件编号删除线程组配置项
     */
    int deleteByFileId(Object id);

    /**
     * 按文件编号批量删除线程组配置项
     */
    int deleteBatchByFileIds(Object[] id);

    /**
     * 获取不包含配置项的菜单列表
     */
    List<PerformanceThreadSetEntity> queryNotSetList();

    /**
     * 获取脚本文件编号下的线程组配置信息
     */
    List<PerformanceThreadSetEntity> queryListByFileId(Object id);
	
}
