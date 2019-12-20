package io.renren.modules.performance.controller;

import java.io.File;
import java.util.*;

import io.renren.common.exception.RRException;
import io.renren.common.utils.DateUtils;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.performance.entity.PerformanceCaseFileEntity;
import io.renren.modules.performance.service.PerformanceCaseFileService;
import io.renren.modules.performance.utils.PerformanceTestUtils;
import io.renren.modules.performance.utils.QueryList;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.renren.modules.performance.entity.PerformanceCaseEntity;
import io.renren.modules.performance.service.PerformanceCaseService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


/**
 * 性能测试用例
 *
 * @author mike.liu
 * @date 2019-12-05 15:27:50
 */
@RestController
@RequestMapping("performance/performancecase")
public class PerformanceCaseController {
    @Autowired
    private PerformanceCaseService performanceCaseService;

    @Autowired
    private PerformanceCaseFileService performanceCaseFileService;

    @Autowired
    private PerformanceTestUtils performanceTestUtils;

    /**
     * 性能测试用例列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("performance:performancecase:list")
    public R list(@RequestParam Map<String, Object> params){
        //查询列表数据
        QueryList query = new QueryList(PerformanceTestUtils.filterParms(params));
        List<PerformanceCaseEntity> perTestList = performanceCaseService.queryList(query);
        int total = performanceCaseService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(perTestList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }


    /**
     * 性能测试用例信息
     */
    @RequestMapping("/info/{caseId}")
    @RequiresPermissions("performance:performancecase:info")
    public R info(@PathVariable("caseId") Long caseId){
		PerformanceCaseEntity performanceCase = performanceCaseService.queryObject(caseId);

        return R.ok().put("performanceCase", performanceCase);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("performance:performancecase:save")
    public R save(@RequestBody PerformanceCaseEntity performanceCase){
        ValidatorUtils.validateEntity(performanceCase);
        // 生成用例时即生成用例的文件夹名，上传附件时才会将此名称落地成为文件夹。
        if (StringUtils.isEmpty(performanceCase.getCaseDir())) {
            Date caseAddTime = new Date();
            String caseAddTimeStr = DateUtils.format(caseAddTime, DateUtils.DATE_TIME_PATTERN_4DIR);
            //random使用时间种子的随机数,避免了轻度并发造成文件夹重名.
            String caseFilePath = caseAddTimeStr + new Random(System.nanoTime()).nextInt(1000);
            performanceCase.setCaseDir(caseFilePath);
        }

        performanceCaseService.save(performanceCase);

        return R.ok();
    }

    /**
     * 修改性能测试用例
     */
    @RequestMapping("/update")
    @RequiresPermissions("performance:performancecase:update")
    public R update(@RequestBody PerformanceCaseEntity performanceCase){
        ValidatorUtils.validateEntity(performanceCase);

		performanceCaseService.update(performanceCase);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("performance:performancecase:delete")
    public R delete(@RequestBody Long[] caseIds){
        // 先删除其下的脚本文件。
        for (Long caseId : caseIds) {
            List<PerformanceCaseFileEntity> fileList = performanceCaseFileService.queryList(caseId);
            if(!fileList.isEmpty()){ //判断是否有关联脚本文件
                ArrayList fileIdList = new ArrayList();
                for (PerformanceCaseFileEntity stressTestFile : fileList) {
                    fileIdList.add(stressTestFile.getFileId());
                }
                performanceCaseFileService.deleteBatch(fileIdList.toArray());
            }
        }
        // 后删除用例
        if (caseIds.length > 0) {
            performanceCaseService.deleteBatch(caseIds);
        }
        return R.ok();
    }

    /**
     * 上传文件
     */
    @PostMapping("/upload")
    @RequiresPermissions("performance:performancecase:upload")
    public R upload(@RequestParam("file") MultipartFile multipartFile, MultipartHttpServletRequest request) {
        if (multipartFile.isEmpty()) {
            throw new RRException("上传文件不能为空");
        }

        String originName = multipartFile.getOriginalFilename();
        //用例文件名可以是汉字
        //用例的参数化文件不允许包含汉字，避免Linux系统读取文件报错
        String suffix = originName.substring(originName.lastIndexOf("."));
        if(!".jmx".equalsIgnoreCase(suffix) && originName.length() != originName.getBytes().length){
            return R.ok().put("error", "非脚本文件名不能包含汉字");
        }

        String caseId = request.getParameter("caseId");
        System.out.println("用例的id信息:" + caseId);
        // 允许文件名不同但是文件内容相同，因为不同的文件名对应不同的用例
        PerformanceCaseEntity performanceCase = performanceCaseService.queryObject(Long.valueOf(caseId));
        // 主节点master的用于保存JMeter用例以及文件的地址
        String casePath = performanceTestUtils.getCasePath();

        Map<String, Object> query = new HashMap<>();
        if (2 == performanceTestUtils.ReplaceFileKey()) {
            // caseId加入搜索条件，允许在不同用例间上传同名文件
            query.put("caseId", caseId);

        }
        query.put("originName", originName);
        // fileList中最多有一条记录
        List<PerformanceCaseFileEntity> fileList = performanceCaseFileService.queryList(query);
        //数据库中已经存在同名文件
        if (!fileList.isEmpty()) {
            // 不允许上传同名文件
            if (0 == performanceTestUtils.ReplaceFileKey()) {
                //throw new RRException("系统中已经存在此文件记录！不允许上传同名文件！");
                return R.ok().put("error","系统中已经存在此文件记录！不允许上传同名文件！");
            } else {// 允许上传同名文件方式是覆盖。
                for (PerformanceCaseFileEntity stressCaseFile : fileList) {
                    // 就算是不同用例下，也不允许上传同名文件。
                    if (performanceTestUtils.ReplaceFileKey() != 2
                            && Long.valueOf(caseId) != stressCaseFile.getCaseId()) {
                        //throw new RRException("其他用例已经包含此同名文件！");
                        return R.ok().put("error","其他用例已经包含此同名文件！");
                    }
                    // 目的是从名称上严格区分脚本。而同名脚本不同项目模块甚至标签
                    String filePath = casePath + File.separator + stressCaseFile.getFileName();
                    performanceCaseFileService.save(multipartFile, filePath, performanceCase, stressCaseFile);
                }
            }
        } else {// 新上传文件
            PerformanceCaseFileEntity stressCaseFile = new PerformanceCaseFileEntity();
            stressCaseFile.setOriginName(originName);

            //主节点master文件夹名称
            //主节点master会根据stressCase的添加时间及随机数生成唯一的文件夹,用来保存用例文件及参数化文件.
            //从节点slave会默认使用$JMETER_HOME/bin/stressTest 来存储参数化文件
            //master的文件分开放(web页面操作无感知),slave的参数化文件统一放.
            Date caseAddTime = performanceCase.getAddTime();
            String caseAddTimeStr = DateUtils.format(caseAddTime, DateUtils.DATE_TIME_PATTERN_4DIR);
            String caseFilePath;
            if (StringUtils.isEmpty(performanceCase.getCaseDir())) {
                //random使用时间种子的随机数,避免了轻度并发造成文件夹重名.
                caseFilePath = caseAddTimeStr + new Random(System.nanoTime()).nextInt(1000);
                performanceCase.setCaseDir(caseFilePath);
            } else {
                caseFilePath = performanceCase.getCaseDir();
            }

            String filePath;
            if (".jmx".equalsIgnoreCase(suffix)) {
                String jmxRealName = "case" + caseAddTimeStr +
                        new Random(System.nanoTime()).nextInt(1000) + suffix;
                stressCaseFile.setFileName(caseFilePath + File.separator + jmxRealName);
                filePath = casePath + File.separator + caseFilePath + File.separator + jmxRealName;
            } else {
                stressCaseFile.setFileName(caseFilePath + File.separator + originName);
                filePath = casePath + File.separator + caseFilePath + File.separator + originName;
            }

            //保存文件信息
            stressCaseFile.setCaseId(Long.valueOf(caseId));
            performanceCaseFileService.save(multipartFile, filePath, performanceCase, stressCaseFile);
        }

        return R.ok();

    }

}
