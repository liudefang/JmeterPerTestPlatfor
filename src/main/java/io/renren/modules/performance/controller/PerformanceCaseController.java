package io.renren.modules.performance.controller;

import java.util.*;

import io.renren.common.exception.RRException;
import io.renren.modules.oss.cloud.OSSFactory;
import io.renren.modules.oss.entity.SysOssEntity;
import io.renren.modules.performance.entity.PerformanceCaseFileEntity;
import io.renren.modules.performance.service.PerformanceCaseFileService;
import io.renren.modules.performance.utils.PerformanceTestUtils;
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
 * @email sunlightcs@gmail.com
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
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("performance:performancecase:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = performanceCaseService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{caseId}")
    @RequiresPermissions("performance:performancecase:info")
    public R info(@PathVariable("caseId") Long caseId){
		PerformanceCaseEntity performanceCase = performanceCaseService.getById(caseId);

        return R.ok().put("performanceCase", performanceCase);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("performance:performancecase:save")
    public R save(@RequestBody PerformanceCaseEntity performanceCase){
		performanceCaseService.save(performanceCase);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("performance:performancecase:update")
    public R update(@RequestBody PerformanceCaseEntity performanceCase){
		performanceCaseService.updateById(performanceCase);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("performance:performancecase:delete")
    public R delete(@RequestBody Long[] caseIds){
		performanceCaseService.removeByIds(Arrays.asList(caseIds));

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

        String caseId = request.getParameter("caseIds");
        // 允许文件名不同但是文件内容相同，因为不同的文件名对应不同的用例
        PerformanceCaseEntity performanceCase = performanceCaseService.queryObject(Long.valueOf(caseId));
        // 主节点master的用于保存JMeter用例以及文件的地址
        String casePath = performanceTestUtils.getCasePath();

        Map<String, Object> query = new HashMap<>();
        if (2 == performanceTestUtils.ReplaceFileKey()) {
            // caseId加入搜索条件，允许在不同用例间上传同名文件
            query.put("caseId", caseId);

        }
//        query.put("originName", originName);
//        // fileList中最多有一条记录
//        //List<PerformanceCaseFileEntity> fileList = performanceCaseFileService.queryList(query);
//
//
//       //String url = OSSFactory.build().uploadSuffix(MultipartFile.getBytes(), suffix);
//
//        //保存文件信息
//        PerformanceCaseFileEntity fileEntity = new PerformanceCaseFileEntity();
//        //fileEntity.setUrl(url);
//        fileEntity.setCreateDate(new Date());
//        performanceCaseFileService.save(fileEntity);

        //return R.ok().put("url", url);
        return null;

    }

}
