package io.renren.modules.performance.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.performance.entity.PerformanceCaseFileEntity;

import java.util.Map;

/**
 * 性能测试用例文件表
 *
 * @author mike.liu
 * @email sunlightcs@gmail.com
 * @date 2019-12-05 15:27:50
 */
public interface PerformanceCaseFileService extends IService<PerformanceCaseFileEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

