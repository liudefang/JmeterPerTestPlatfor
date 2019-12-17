package io.renren.modules.performance.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.performance.entity.PerformanceCaseEntity;

import java.util.List;
import java.util.Map;

/**
 * 性能测试用例表
 *
 * @author mike.liu
 * @email sunlightcs@gmail.com
 * @date 2019-12-05 15:27:50
 */
public interface PerformanceCaseService  {

    //PageUtils queryPage(Map<String, Object> params);

    /**
     * 根据ID，查询性能测试用例
     */
    PerformanceCaseEntity queryObject(Long caseId);

    /**
     * 查询性能测试用例列表
     */
    List<PerformanceCaseEntity> queryList(Map<String, Object> map);

    /**
     * 查询总数
     */
    int queryTotal(Map<String, Object> map);

    /**
     * 保存性能测试用例
     */
    void save(PerformanceCaseEntity perTestCase);

    /**
     * 更新性能测试用例
     */


    void update(PerformanceCaseEntity perTestCase);

    /**
     * 批量删除
     */
    void deleteBatch(Long[] caseIds);

    /**
     * 批量更新性能测试用例信息
     */
    int updateBatch(Long[] caseIds, int status);

}

