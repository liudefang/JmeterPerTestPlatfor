package io.renren.modules.performance.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.performance.entity.PerformanceCaseEntity;
import io.renren.modules.performance.entity.PerformanceCaseFileEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 性能测试用例文件表
 *
 * @author mike.liu
 * @email sunlightcs@gmail.com
 * @date 2019-12-05 15:27:50
 */
public interface PerformanceCaseFileService extends IService<PerformanceCaseFileEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 根据ID，查询文件
     */
    PerformanceCaseFileEntity queryObject(Long fileId);

    /**
     * 查询文件列表
     */
    List<PerformanceCaseFileEntity> queryList(Map<String, Object> map);

    /**
     * 查询文件列表
     */
    List<PerformanceCaseFileEntity> queryList(Long caseId);

    /**
     * 查询总数
     */
    int queryTotal(Map<String, Object> map);

    /**
     * 保存性能测试用例文件
     */
   // void save(PerformanceCaseFileEntity stressCaseFile);

    /**
     * 保存性能测试用例文件
     */
    void save(MultipartFile file, String filePath, PerformanceCaseEntity stressCase, PerformanceCaseFileEntity stressCaseFile);

    /**
     * 更新性能测试用例信息
     */
    void update(PerformanceCaseFileEntity stressTestFile);

    /**
     * 更新性能测试用例信息
     */
   // void update(PerformanceCaseFileEntity stressTestFile, StressTestReportsEntity stressTestReports);

    /**
     * 更新性能测试用例信息
     */
    //void update(MultipartFile file, String filePath, StressTestEntity stressCase, StressTestFileEntity stressCaseFile);

    /**
     * 批量更新性能测试用例状态
     */
    void updateStatusBatch(PerformanceCaseFileEntity stressTestFile);

    /**
     * 批量删除
     */
    void deleteBatch(Object[] fileIds);

    /**
     * 立即执行
     */
    String run(Long[] fileIds);

    /**
     * 立即停止
     */
    void stop(Long[] fileIds);

    /**
     * 停止运行
     */
    void stopAll();

    /**
     * 立即停止运行
     */
    void stopAllNow();

    /**
     * 获取轮询监控结果
     */
    //JmeterStatEntity getJmeterStatEntity(Long fileId);

    /**
     * 同步参数化文件到节点机
     */
    void synchronizeFile(Long[] fileIds);

    /**
     * 获取文件路径，是文件的真实绝对路径
     */
    String getFilePath(PerformanceCaseFileEntity stressTestFile);

    /**
     * 相同进程内执行的脚本，可以使用这个方法停止
     */
   // void stopLocal(Long fileId, JmeterRunEntity jmeterRunEntity);

    /**
     * 启动单个脚本
     */
    String runSingle(Long fileId);

    /**
     * 某个进程内执行的脚本，可以使用这个方法停止
     */
    void stopSingle(Long fileId);



}

