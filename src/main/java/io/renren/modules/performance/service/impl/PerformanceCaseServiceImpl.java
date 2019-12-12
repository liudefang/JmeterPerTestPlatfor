package io.renren.modules.performance.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.performance.dao.PerformanceCaseDao;
import io.renren.modules.performance.entity.PerformanceCaseEntity;
import io.renren.modules.performance.service.PerformanceCaseService;


@Service("performanceCaseService")
public class PerformanceCaseServiceImpl extends ServiceImpl<PerformanceCaseDao, PerformanceCaseEntity> implements PerformanceCaseService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PerformanceCaseEntity> page = this.page(
                new Query<PerformanceCaseEntity>().getPage(params),
                new QueryWrapper<PerformanceCaseEntity>()
        );

        return new PageUtils(page);
    }

}