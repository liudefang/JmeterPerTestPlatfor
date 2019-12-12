package io.renren.modules.performance.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.performance.dao.PerformanceSlaveDao;
import io.renren.modules.performance.entity.PerformanceSlaveEntity;
import io.renren.modules.performance.service.PerformanceSlaveService;


@Service("performanceSlaveService")
public class PerformanceSlaveServiceImpl extends ServiceImpl<PerformanceSlaveDao, PerformanceSlaveEntity> implements PerformanceSlaveService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PerformanceSlaveEntity> page = this.page(
                new Query<PerformanceSlaveEntity>().getPage(params),
                new QueryWrapper<PerformanceSlaveEntity>()
        );

        return new PageUtils(page);
    }

}