package io.renren.modules.performance.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.performance.entity.DebugCaseReportsEntity;

import java.util.Map;

/**
 * 调试/接口测试报告文件表
 *
 * @author mike.liu
 * @email sunlightcs@gmail.com
 * @date 2019-12-05 15:27:50
 */
public interface DebugCaseReportsService extends IService<DebugCaseReportsEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

