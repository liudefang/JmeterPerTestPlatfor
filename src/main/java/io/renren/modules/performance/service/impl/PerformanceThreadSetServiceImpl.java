package io.renren.modules.performance.service.impl;

import io.renren.modules.performance.entity.PerformanceCaseFileEntity;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Override
    public List<PerformanceThreadSetEntity> queryNotSetList() {
        return queryNotSetList();
    }

    @Override
    public PerformanceThreadSetEntity queryObject(String setId) {
        return queryObject(setId);
    }

    @Override
    public List<PerformanceThreadSetEntity> queryList(Map<String, Object> map) {
        return queryList(map);
    }

    @Override
    public List<PerformanceThreadSetEntity> queryListByFileId(Long fileId) {
        return queryListByFileId(fileId);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return queryTotal(map);
    }

    @Override
    public void update(PerformanceThreadSetEntity perTestThreadSet) {
        update(perTestThreadSet);

    }

    @Override
    public void delete(Long setId) {
        delete(setId);

    }

    @Override
    public void deleteBatch(String[] setIds) {
        deleteBatch(setIds);

    }

    @Override
    public void saveBath(List<PerformanceThreadSetEntity> perTestThreadSetList) {
        saveBatch(perTestThreadSetList);

    }
    /**
     * 获取脚本的线程组配置数据入库。
     * @throws DocumentException
     */

    @Override
    public void jmxSaveNodes(String filePath, PerformanceCaseFileEntity perTestFile) throws DocumentException {


    }

    @Override
    public void synchronizeJmx(Long fileId) throws DocumentException {

    }

}