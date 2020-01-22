package io.renren.modules.performance.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.performance.entity.PerformanceCaseFileEntity;
import io.renren.modules.performance.entity.PerformanceThreadSetEntity;
import org.dom4j.DocumentException;

import java.util.List;
import java.util.Map;

/**
 * 线程组管理
 *
 * @author mike.liu
 * @email sunlightcs@gmail.com
 * @date 2019-12-05 15:27:50
 */
public interface PerformanceThreadSetService {

    /**
     * 获取不包含配置项的列表
     */
    List<PerformanceThreadSetEntity> queryNotSetList();

    PerformanceThreadSetEntity queryObject(String setId);

    List<PerformanceThreadSetEntity> queryList(Map<String, Object> map);

    //以文件编号查询脚本的线程组配置信息
    List<PerformanceThreadSetEntity> queryListByFileId(Long fileId);

    int queryTotal(Map<String, Object> map);

    void save(PerformanceThreadSetEntity perTestThreadSet);

    void update(PerformanceThreadSetEntity perTestThreadSet);

    void delete(Long setId);

    void deleteBatch(String[] setIds);

    void saveBath(List <PerformanceThreadSetEntity> perTestThreadSetList);

    /**
     * 从指定脚本获取线程组配置并入库
     */
    void jmxSaveNodes(String filePath, PerformanceCaseFileEntity perTestFile) throws DocumentException;

    /**
     * 从库中获取线程组配置并同步到指定脚本
     */
    void synchronizeJmx(Long fileId) throws DocumentException;



}

