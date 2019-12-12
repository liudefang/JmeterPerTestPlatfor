package io.renren.modules.performance.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.performance.entity.PerformanceCaseEntity;

import java.util.Map;

/**
 * 性能测试用例表
 *
 * @author mike.liu
 * @email sunlightcs@gmail.com
 * @date 2019-12-05 15:27:50
 */
public interface PerformanceCaseService extends IService<PerformanceCaseEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 根据ID，查询性能测试用例
     */
    PerformanceCaseEntity queryObject(Long caseId);
}

