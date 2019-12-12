package io.renren.modules.performance.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.performance.entity.PerformanceThreadSetEntity;

import java.util.Map;

/**
 * 线程组管理
 *
 * @author mike.liu
 * @email sunlightcs@gmail.com
 * @date 2019-12-05 15:27:50
 */
public interface PerformanceThreadSetService extends IService<PerformanceThreadSetEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

