package io.renren.modules.performance.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.performance.entity.PerformanceCaseReportsEntity;

import java.util.Map;

/**
 * 性能测试报告文件表
 *
 * @author mike.liu
 * @email sunlightcs@gmail.com
 * @date 2019-12-05 15:27:51
 */
public interface PerformanceCaseReportsService extends IService<PerformanceCaseReportsEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

