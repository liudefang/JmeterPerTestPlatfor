package io.renren.modules.performance.handler;

import io.renren.modules.performance.entity.PerformanceCaseFileEntity;
import io.renren.modules.performance.entity.PerformanceCaseReportsEntity;
import io.renren.modules.performance.service.PerformanceCaseFileService;
import io.renren.modules.performance.utils.PerformanceTestUtils;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.io.FileUtils;

import java.io.ByteArrayOutputStream;

/**
 * 执行的钩子程序。
 * Created by zyanycall@gmail.com on 15:50.
 */
public class FileExecuteResultHandler extends FileResultHandler {

    // file对象
    private PerformanceCaseFileEntity perTestFile;

    // report对象
    private PerformanceCaseReportsEntity perTestReports;

    private PerformanceCaseFileService perTestFileService;

    public FileExecuteResultHandler(PerformanceCaseFileEntity perTestFile, PerformanceCaseReportsEntity perTestReports,
                                    PerformanceCaseFileService perTestFileService,
                                    ByteArrayOutputStream outputStream, ByteArrayOutputStream errorStream) {
        super(outputStream, errorStream);
        this.perTestFile = perTestFile;
        this.perTestReports = perTestReports;
        this.perTestFileService = perTestFileService;
    }

    /**
     * jmx脚本执行成功会走到这里
     * 重写父类方法，增加入库及日志打印
     */
    @Override
    public void onProcessComplete(final int exitValue) {
        perTestFile.setStatus(PerformanceTestUtils.RUN_SUCCESS);
        if (perTestReports != null && perTestReports.getFile().exists()) {
            perTestReports.setFileSize(FileUtils.sizeOf(perTestReports.getFile()));
        }
        perTestFileService.update(perTestFile, perTestReports);
        super.onProcessComplete(exitValue);
        //保存状态，执行完毕
    }

    /**
     * jmx脚本执行失败会走到这里
     * 重写父类方法，增加入库及日志打印
     */
    @Override
    public void onProcessFailed(final ExecuteException e) {
        if (perTestFile != null) {
            perTestFile.setStatus(PerformanceTestUtils.RUN_ERROR);
            perTestFileService.update(perTestFile);
        }
        super.onProcessFailed(e);
    }
}
