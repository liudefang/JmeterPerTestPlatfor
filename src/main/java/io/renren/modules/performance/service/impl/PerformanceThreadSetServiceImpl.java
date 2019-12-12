package io.renren.modules.performance.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.performance.dao.PerformanceThreadSetDao;
import io.renren.modules.performance.entity.PerformanceThreadSetEntity;
import io.renren.modules.performance.service.PerformanceThreadSetService;


@Service("performanceThreadSetService")
public class PerformanceThreadSetServiceImpl extends ServiceImpl<PerformanceThreadSetDao, PerformanceThreadSetEntity> implements PerformanceThreadSetService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PerformanceThreadSetEntity> page = this.page(
                new Query<PerformanceThreadSetEntity>().getPage(params),
                new QueryWrapper<PerformanceThreadSetEntity>()
        );

        return new PageUtils(page);
    }

}