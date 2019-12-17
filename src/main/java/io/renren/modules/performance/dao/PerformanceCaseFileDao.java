package io.renren.modules.performance.dao;

import io.renren.modules.performance.entity.PerformanceCaseFileEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 性能测试用例文件表
 * 
 * @author mike.liu
 * @email sunlightcs@gmail.com
 * @date 2019-12-05 15:27:50
 */
@Mapper
public interface PerformanceCaseFileDao extends BaseMapper<PerformanceCaseFileEntity> {
    int deleteBatchByCaseIds(Object[] id);

    List<PerformanceCaseFileEntity> queryListForDelete(Map<String, Object> map);

    PerformanceCaseFileEntity queryObjectForClone(Map<String, Object> map);

    int updateStatusBatch(Map<String, Object> map);
	
}
