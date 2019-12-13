package io.renren.modules.performance.service.impl;

import io.renren.modules.performance.entity.PerformanceCaseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.performance.dao.PerformanceCaseFileDao;
import io.renren.modules.performance.entity.PerformanceCaseFileEntity;
import io.renren.modules.performance.service.PerformanceCaseFileService;
import org.springframework.web.multipart.MultipartFile;


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

    @Override
    public PerformanceCaseFileEntity queryObject(Long fileId) {
        return queryObject(fileId);
    }

    @Override
    public List<PerformanceCaseFileEntity> queryList(Map<String, Object> map) {
        return null;
    }

    @Override
    public List<PerformanceCaseFileEntity> queryList(Long caseId) {
        return null;
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return 0;
    }

    @Override
    public void save(MultipartFile file, String filePath, PerformanceCaseEntity stressCase, PerformanceCaseFileEntity stressCaseFile) {

    }

    @Override
    public void update(PerformanceCaseFileEntity stressTestFile) {

    }

    @Override
    public void updateStatusBatch(PerformanceCaseFileEntity stressTestFile) {

    }

    @Override
    public void deleteBatch(Object[] fileIds) {

    }

    @Override
    public String run(Long[] fileIds) {
        return null;
    }

    @Override
    public void stop(Long[] fileIds) {

    }

    @Override
    public void stopAll() {

    }

    @Override
    public void stopAllNow() {

    }

    @Override
    public void synchronizeFile(Long[] fileIds) {

    }

    @Override
    public String getFilePath(PerformanceCaseFileEntity stressTestFile) {
        return null;
    }

    @Override
    public String runSingle(Long fileId) {
        return null;
    }

    @Override
    public void stopSingle(Long fileId) {

    }


}