package io.renren.modules.performance.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.performance.entity.PerformanceSlaveEntity;

import java.util.Map;

/**
 * 性能测试分布式节点表
 *
 * @author mike.liu
 * @email sunlightcs@gmail.com
 * @date 2019-12-05 15:27:50
 */
public interface PerformanceSlaveService extends IService<PerformanceSlaveEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

