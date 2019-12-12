package io.renren.modules.performance.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.performance.dao.DebugCaseReportsDao;
import io.renren.modules.performance.entity.DebugCaseReportsEntity;
import io.renren.modules.performance.service.DebugCaseReportsService;


@Service("debugCaseReportsService")
public class DebugCaseReportsServiceImpl extends ServiceImpl<DebugCaseReportsDao, DebugCaseReportsEntity> implements DebugCaseReportsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DebugCaseReportsEntity> page = this.page(
                new Query<DebugCaseReportsEntity>().getPage(params),
                new QueryWrapper<DebugCaseReportsEntity>()
        );

        return new PageUtils(page);
    }

}