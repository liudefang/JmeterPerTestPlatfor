package io.renren.modules.performance.service.impl;

import com.google.gson.internal.$Gson$Types;
import io.renren.common.exception.RRException;
import io.renren.modules.performance.utils.PerformanceTestUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.performance.dao.PerformanceCaseDao;
import io.renren.modules.performance.entity.PerformanceCaseEntity;
import io.renren.modules.performance.service.PerformanceCaseService;
import org.springframework.transaction.annotation.Transactional;


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

    @Override
    public PerformanceCaseEntity queryObject(Long caseId) {
        return queryObject(caseId);
    }

    @Override
    public List<PerformanceCaseEntity> queryList(Map<String, Object> map) {
        return queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return queryTotal(map);
    }

    @Override
    @Transactional
    public void deleteBatch(Long[] caseIds){
        for (Long caseId : caseIds){
            // 先删除所属文件
            PerformanceCaseEntity perTestCase = queryObject(caseId);
            String casePath = PerformanceTestUtils.getCasePath();
            String caseFilePath = casePath + File.separator + perTestCase.getCaseDir();
            try {
                FileUtils.forceDelete(new File(caseFilePath));
            } catch (FileNotFoundException e){

            } catch (IOException e) {
                throw new RRException("删除文件异常失败", e);
            }
        }
    }


}