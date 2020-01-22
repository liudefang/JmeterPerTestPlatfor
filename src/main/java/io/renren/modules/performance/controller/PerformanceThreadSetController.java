package io.renren.modules.performance.controller;

import io.renren.common.annotation.SysLog;
import io.renren.common.utils.R;
import io.renren.modules.performance.entity.PerformanceThreadSetEntity;
import io.renren.modules.performance.service.PerformanceThreadSetService;
import io.renren.modules.performance.utils.PerformanceTestUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 线程组管理
 *
 * @author mike.liu
 * @email sunlightcs@gmail.com
 * @date 2019-12-05 15:27:50
 */
@RestController
@RequestMapping("performance/performancethreadset")
public class PerformanceThreadSetController {
    @Autowired
    private PerformanceThreadSetService performanceThreadSetService;

    @Autowired
    private PerformanceTestUtils performanceTestUtils;

    /**
     * 所有配置列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("performance:performancethreadset:list")
    public List<PerformanceThreadSetEntity> list(){
        List<PerformanceThreadSetEntity> testStressThreadSetList = performanceThreadSetService.queryList(new HashMap<String, Object>());

        return testStressThreadSetList;
    }

    /**
     * 指定脚本的配置列表
     */
    @RequestMapping("/list/{fileIds}")
    @RequiresPermissions("performance:performancethreadset:fileList")
    public List<PerformanceThreadSetEntity> list(@PathVariable("fileIds") String[] fileIds){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("fileids", Arrays.asList(fileIds));
        List<PerformanceThreadSetEntity> testStressThreadSetList = performanceThreadSetService.queryList(map);

        return testStressThreadSetList;
    }

    /**
     * 选择配置项(添加、修改配置项)
     */
    @RequestMapping("/select")
    @RequiresPermissions("performance:performancethreadset")
    public R select(){
        //查询列表数据
        List<PerformanceThreadSetEntity> perTestThreadSetList = performanceThreadSetService.queryNotSetList();

        //添加顶级菜单
        PerformanceThreadSetEntity root = new PerformanceThreadSetEntity();
        root.setSetId("0");
        root.setName("一级菜单");
        root.setParentId("-1");
        root.setOpen(true);
        perTestThreadSetList.add(root);

        return R.ok().put("perTestThreadSetList", perTestThreadSetList);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{setId}")
    @RequiresPermissions("performance:performancethreadset:info")
    public R info(@PathVariable("setId") String setId){
		PerformanceThreadSetEntity performanceThreadSet = performanceThreadSetService.queryObject(setId);

        return R.ok().put("performanceThreadSet", performanceThreadSet);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("performance:performancethreadset:save")
    public R save(@RequestBody PerformanceThreadSetEntity performanceThreadSet){
		performanceThreadSetService.save(performanceThreadSet);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("performance:performancethreadset:update")
    public R update(@RequestBody PerformanceThreadSetEntity performanceThreadSet){
		performanceThreadSetService.update(performanceThreadSet);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("performance:performancethreadset:delete")
    public R delete(@RequestBody String[] setIds){
		performanceThreadSetService.deleteBatch(setIds);

        return R.ok();
    }

    /**
     * 将线程组配置同步到对应脚本文件中。
     * @throws DocumentException
     */
    @SysLog("将线程组配置同步到对应脚本文件中")
    @RequestMapping("/synchronizeJmx")
    @RequiresPermissions("performance:performancethreadset:synchronizeJmx")
    public R synchronizeJmx(@RequestBody Long fileId) throws DocumentException {
        performanceThreadSetService.synchronizeJmx(fileId);
        return R.ok();
    }

}
