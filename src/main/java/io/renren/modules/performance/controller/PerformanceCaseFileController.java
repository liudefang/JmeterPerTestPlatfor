package io.renren.modules.performance.controller;

import io.renren.common.annotation.SysLog;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.performance.entity.PerformanceCaseFileEntity;
import io.renren.modules.performance.jmeter.JmeterStatEntity;
import io.renren.modules.performance.service.PerformanceCaseFileService;
import io.renren.modules.performance.utils.PerformanceTestUtils;
import io.renren.modules.performance.utils.QueryList;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;



/**
 * 性能测试用例文件表
 *
 * @author mike.liu
 * @email sunlightcs@gmail.com
 * @date 2019-12-05 15:27:50
 */
@RestController
@RequestMapping("performance/performancecasefile")
public class PerformanceCaseFileController {
    @Autowired
    private PerformanceCaseFileService performanceCaseFileService;

    /**
     * 参数化文件，用例文件列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("performance:performancecasefile:list")
    public R list(@RequestParam Map<String, Object> params){
        QueryList query = new QueryList(PerformanceTestUtils.filterParms(params));
        List<PerformanceCaseFileEntity> jobList = performanceCaseFileService.queryList(query);
        int total = performanceCaseFileService.queryTotal(query);

        PageUtils pageUtils = new PageUtils(jobList, total, query.getLimit(), query.getPage());


        return R.ok().put("page", pageUtils);
    }


    /**
     * 查询具体文件信息
     */
    @RequestMapping("/info/{fileId}")
    @RequiresPermissions("performance:performancecasefile:info")
    public R info(@PathVariable("fileId") Long fileId){
		PerformanceCaseFileEntity performanceCaseFile = performanceCaseFileService.queryObject(fileId);

        return R.ok().put("performanceCaseFile", performanceCaseFile);
    }


    /**
     * 修改性能测试用例脚本文件
     */
    @SysLog("修改性能测试用例脚本文件")
    @RequestMapping("/fileUpdate")
    @RequiresPermissions("performance:performancecasefile:fileUpdate")
    public R update(@RequestBody PerformanceCaseFileEntity performanceCaseFile){
        ValidatorUtils.validateEntity(performanceCaseFile);

        if(performanceCaseFile.getFileIdList() != null && performanceCaseFile.getFileIdList().length > 0){
            performanceCaseFileService.updateStatusBatch(performanceCaseFile);
        } else {
            performanceCaseFileService.update(performanceCaseFile);
        }

        return R.ok();
    }

    /**
     * 删除指定文件
     */
    @SysLog("删除指定文件")
    @RequestMapping("/delete")
    @RequiresPermissions("performance:performancecasefile:fileDelete")
    public R delete(@RequestBody Long[] fileIds){
		performanceCaseFileService.deleteBatch(fileIds);

        return R.ok();
    }

    /**
     * 立即执行性能测试脚本
     */
    @SysLog("立即执行性能测试脚本")
    @RequestMapping("/runOnce")
    @RequiresPermissions("performance:performancecasefile:runOnce")
    public R run(@RequestBody Long[] fileIds){
        return R.ok(performanceCaseFileService.run(fileIds));
    }

    /**
     * 立即执行性能测试脚本(带slave参数)
     */
    @SysLog("立即执行性能测试脚本")
    @RequestMapping("/runOnce/{slaveIds}")
    @RequiresPermissions("performance:performancecasefile:runOnce")
    public R runRSlave(@RequestBody Long[] fileIds,@PathVariable("slaveIds") Long[] slaveIds){
        performanceCaseFileService.setSlaveId(slaveIds);
        return R.ok(performanceCaseFileService.run(fileIds));
    }

    /**
     * 立即停止性能测试脚本，仅有使用api方式时，才可以单独停止
     */
    @SysLog("立即停止性能测试用例脚本文件")
    @RequestMapping("/stopOnce")
    @RequiresPermissions("performance:performancecasefile:stopOnce")
    public R stop(@RequestBody Long[] fileIds){
        performanceCaseFileService.stop(fileIds, false);
        return R.ok();
    }

    /**
     * 停止性能测试用例
     */
    @SysLog("停止执行性能测试用例脚本")
    @RequestMapping("/stopAll")
    @RequiresPermissions("performance:performancecasefile:stopAll")
    public R stopAll() {
        performanceCaseFileService.stopAll(false);
        return R.ok();
    }

    /**
     * 立即停止性能测试用例，如果是脚本方式运行希望是杀掉进程（节点机+主节点)
     */
    @SysLog("停止执行性能测试用例脚本")
    @RequestMapping("/stopAllNow")
    @RequiresPermissions("performance:performancecasefile:stopAllNow")
    public R stopAllNow(@RequestBody Long[] fileIds) {
        performanceCaseFileService.stopAllNow(fileIds);
        return R.ok();
    }

    /**
     * 定时查询执行结果
     * 只有在本地执行性能测试时，才会被调用
     * 不要求权限校验了，频繁操作不用每次都调用数据库
     */
    @RequestMapping("/statInfo/{fileId}")
    public R statInfo(@PathVariable("fileId") Long fileId){
        // 频繁不是特别高，可以是new一个对象
        JmeterStatEntity jmeterStatEntity = performanceCaseFileService.getJmeterStatEntity(fileId);
        return R.ok().put("statInfo", jmeterStatEntity);
    }

    /**
     * 将参数化文件同步到指定分布式slave节点机的指定目录下
     */
    @SysLog("将参数化文件同步到指定分布式slave节点机的指定目录")
    @RequestMapping("/synchronizeFile")
    @RequiresPermissions("performance:performancecasefile:synchronizeFile")
    public R synchronizeFile(@RequestBody Long[] fileIds) {
        performanceCaseFileService.synchronizeFile(fileIds);
        return R.ok();
    }

    /**
     * 下载文件
     */
    @RequestMapping("/downloadFile/{fileId}")
    @RequiresPermissions("performance:performancecasefile:fileDownLoad")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable("fileId") Long fileId) throws IOException {
        PerformanceCaseFileEntity performanceCaseFile = performanceCaseFileService.queryObject(fileId);
        FileSystemResource fileSystemResource = new FileSystemResource(performanceCaseFileService.getFilePath(performanceCaseFile));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache,no-store,must-revalidate");
        headers.add("Content-Disposition",
                "attachment;filename=" + performanceCaseFile.getOriginName());
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.setContentType(MediaType.parseMediaType("application/octet-stream"));

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(fileSystemResource.contentLength())
                .body(new InputStreamResource(fileSystemResource.getInputStream()));
    }



}
