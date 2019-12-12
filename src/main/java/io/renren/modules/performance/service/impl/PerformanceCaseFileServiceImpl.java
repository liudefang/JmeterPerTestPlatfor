package io.renren.modules.performance.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.performance.dao.PerformanceCaseFileDao;
import io.renren.modules.performance.entity.PerformanceCaseFileEntity;
import io.renren.modules.performance.service.PerformanceCaseFileService;


@Service("performanceCaseFileService")
public class PerformanceCaseFileServiceImpl extends ServiceImpl<PerformanceCaseFileDao, PerformanceCaseFileEntity> implements PerformanceCaseFileService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PerformanceCaseFileEntity> page = this.page(
                new Query<PerformanceCaseFileEntity>().getPage(params),
                new QueryWrapper<PerformanceCaseFileEntity>()
        );

        return new PageUtils(page);
    }

}