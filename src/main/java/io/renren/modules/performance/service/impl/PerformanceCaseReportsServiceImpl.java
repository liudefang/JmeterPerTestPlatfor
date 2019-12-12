package io.renren.modules.performance.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.performance.dao.PerformanceCaseReportsDao;
import io.renren.modules.performance.entity.PerformanceCaseReportsEntity;
import io.renren.modules.performance.service.PerformanceCaseReportsService;


@Service("performanceCaseReportsService")
public class PerformanceCaseReportsServiceImpl extends ServiceImpl<PerformanceCaseReportsDao, PerformanceCaseReportsEntity> implements PerformanceCaseReportsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PerformanceCaseReportsEntity> page = this.page(
                new Query<PerformanceCaseReportsEntity>().getPage(params),
                new QueryWrapper<PerformanceCaseReportsEntity>()
        );

        return new PageUtils(page);
    }

}