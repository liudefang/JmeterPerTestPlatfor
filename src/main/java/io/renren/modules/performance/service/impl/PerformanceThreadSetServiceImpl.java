package io.renren.modules.performance.service.impl;

import io.renren.modules.performance.dao.PerformanceCaseFileDao;
import io.renren.modules.performance.entity.PerformanceCaseFileEntity;
import io.renren.modules.performance.utils.PerformanceTestUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.performance.dao.PerformanceThreadSetDao;
import io.renren.modules.performance.entity.PerformanceThreadSetEntity;
import io.renren.modules.performance.service.PerformanceThreadSetService;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.*;

import javax.xml.parsers.SAXParser;
import javax.xml.transform.sax.SAXResult;


@Service("performanceThreadSetService")
public class PerformanceThreadSetServiceImpl implements PerformanceThreadSetService {
    @Autowired
    private PerformanceThreadSetDao perTestThreadSetDao;
    @Autowired
    private PerformanceCaseFileDao performanceCaseFileDao;
    @Autowired
    private PerformanceTestUtils performanceTestUtils;


    @Override
    public List<PerformanceThreadSetEntity> queryNotSetList() {
        return perTestThreadSetDao.queryNotSetList();
    }

    @Override
    public PerformanceThreadSetEntity queryObject(String setId) {
        return perTestThreadSetDao.queryObject(setId);
    }

    @Override
    public List<PerformanceThreadSetEntity> queryList(Map<String, Object> map) {
        return perTestThreadSetDao.queryList(map);
    }

    @Override
    public List<PerformanceThreadSetEntity> queryListByFileId(Long fileId) {
        return perTestThreadSetDao.queryListByFileId(fileId);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return perTestThreadSetDao.queryTotal(map);
    }

    @Override
    public void save(PerformanceThreadSetEntity perTestThreadSet) {
        perTestThreadSetDao.save(perTestThreadSet);

    }

    @Override
    public void update(PerformanceThreadSetEntity perTestThreadSet) {
        perTestThreadSetDao.update(perTestThreadSet);

    }

    @Override
    public void delete(Long setId) {
        perTestThreadSetDao.delete(setId);

    }

    @Override
    public void deleteBatch(String[] setIds) {
        perTestThreadSetDao.deleteBatch(setIds);

    }

    @Override
    public void saveBath(List<PerformanceThreadSetEntity> perTestThreadSetList) {
        perTestThreadSetDao.saveBatch(perTestThreadSetList);

    }
    /**
     * 获取脚本的线程组配置数据入库。
     * @throws DocumentException
     */

    int k;//线程组配置编号（确保入库与同步脚本的相对位置顺序）
    String uuid_p="";//线程组主键ID编号

    public void jmxSaveNodes(String filePath, PerformanceCaseFileEntity perTestFile) throws DocumentException {
        SAXReader reader = new SAXReader();
        File file = new File(filePath);
        Long fileId = perTestFile.getFileId();
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        Document document = reader.read(file);
        Element root = document.getRootElement();
        List<PerformanceThreadSetEntity> threadSetEntityList = new ArrayList<>();
        PerformanceThreadSetEntity performanceThreadSetEntity = null;
        performanceThreadSetEntity = setSThreadSetEntity(
                uuid,
                "0",
                perTestFile.getOriginName(),
                "运行模式",
                "",
                0,
                "所属用例:"+perTestFile.getCaseName()+";  对应脚本:" + perTestFile.getFileName(),
                0,
                fileId);
        threadSetEntityList.add(performanceThreadSetEntity);
        getNodes(root,fileId,uuid,threadSetEntityList);     // 从根节点开始遍历所有节点
        saveBath(threadSetEntityList);
    }

    /**
     * 从指定节点开始，递归遍历所有子节点
     * @param fileId
     * @throws DocumentException
     */
    public void getNodes(Element node, Long fileId, String uuid, List<PerformanceThreadSetEntity> threadSetEntityList){
        if(node.getName().equals("TestPlan")){      // jmx脚本的测试计划属值
            List<Element> listElem = node.elements("boolProp");
            String TestPlanValue = "";
            for(Element TestPlanElem:listElem){
                if(TestPlanElem.getTextTrim().equals("true")) TestPlanValue += TestPlanElem.attributeValue("name")+";";
            }
            // add by wuxc 如果没有boolProp属性为true，则value放空，否则上传脚本无法成功
            if(TestPlanValue.length()>0) {
                threadSetEntityList.get(0).setValue(TestPlanValue.substring(0,TestPlanValue.length()-1));
            }

        }
        PerformanceThreadSetEntity performanceThreadSetEntity = null;
        // 当前节点的名称，文本内容和属性
        List<Attribute> listAttr = node.attributes();       // 当前节点的所有属性的list
        String keyValueStr = "ThreadGroup.on_sample_error;ThreadGroup.num_threads;"
                + "Threads initial delay;Start users count;Start users count burst;"
                + "Start users period;Stop users count;Stop users period;flighttime;rampUp;"
                + "LoopController.loops;ThreadGroup.ramp_time;ThreadGroup.scheduler;"
                + "ThreadGroup.duration;ThreadGroup.delay";
        String namesStr = "取样器报错后动作;线程组线程数;"
                + "初始等待时间(s);每次启动线程数;初次启动线程数;"
                + "每次启动线程数间隔时间(s);每次停止线程数;每次停止线程数间隔时间(s);峰值压力持续时间(s);线程启动完毕时间/加压时间(s);"
                + "循环次数(-1为永远循环);Ramp-Up Period(s);开启调度器;"
                + "调度持续时间(s);调度启动延迟(s)";
        String[] keyStr = keyValueStr.split(";");
        String[] nameStr = namesStr.split(";");
        attrfor: for(Attribute attr:listAttr){  // 遍历当前节点的所有属性
            String keyName=attr.getValue();     // 属性的值
            if(keyName.equals("ThreadGroup.on_sample_error")){      // jmx脚本的线程组属性值
                uuid_p = UUID.randomUUID().toString().replaceAll("-", "");
                performanceThreadSetEntity = setSThreadSetEntity(
                        uuid_p,
                        uuid,node.getParent().attribute(2).getValue(),
                        "enabled",
                        node.getParent().attributeValue("enabled"),
                        1,
                        "线程组类别:"+node.getParent().getName(),
                        ++k,
                        fileId
                );
                threadSetEntityList.add(performanceThreadSetEntity);
                System.out.println("---------线程组"+k+"-----------");
                if(node.getParent().getName().endsWith("UltimateThreadGroup")){//jp@gc - Ultimate Thread Group类别
                    //UltimateThreadGroup线程组太特殊，不适合循环逐个配置
                    List<Element> elistElem = node.getParent().elements().get(0).elements("collectionProp");
                    for(Element elem:elistElem){
                        String skeyName = elem.attributeValue("name");
                        String skeyValue="";
                        List<Element> slistElem = elem.elements("stringProp");
                        for(Element selem:slistElem){
                            skeyValue+=selem.getTextTrim()+";";
                        }
                        performanceThreadSetEntity = setSThreadSetEntity(
                                UUID.randomUUID().toString().replaceAll("-", ""),
                                uuid_p,
                                "线程计划"+skeyName,
                                skeyName,
                                skeyValue.substring(0,skeyValue.length() - 1),
                                2,
                                "配置项",
                                ++k,
                                fileId
                        );
                        threadSetEntityList.add(performanceThreadSetEntity);
                    }
                }
            }
            for (int i = 0 ; i <keyStr.length; i++) {  // jmx脚本的线程组具体配置项
                if(keyName.equals(keyStr[i]) && !node.getParent().getName().equals("LoopController") &&
                        (!node.getName().equals("intProp") || (node.getName().equals("intProp") &&
                                node.getParent().getParent().getName().equals("ThreadGroup")))){
                    performanceThreadSetEntity = setSThreadSetEntity(
                            UUID.randomUUID().toString().replaceAll("-", ""),
                            uuid_p,
                            nameStr[i],
                            keyName,
                            node.getTextTrim(),
                            2,
                            "配置项",
                            ++k,
                            fileId
                    );
                    threadSetEntityList.add(performanceThreadSetEntity);
                    System.out.println(nameStr[i]+":"+keyName+"="+node.getTextTrim()+"\n");
                    break attrfor;
                }
            }

        }

        // 递归遍历当前节点所有的子节点
        List<Element> listElement = node.elements();    // 所有一级子节点的list
        for(Element e:listElement) {    // 遍历所有一级子节点
            getNodes(e, fileId, uuid, threadSetEntityList);     // 递归

        }

    }

    /**
     * 填充线程组配置信息数据
     */
    public PerformanceThreadSetEntity setSThreadSetEntity(
            String setId, String parentId, String name,
            String key, String value, int type,
            String explain, int orderNum, Long fileId
    ){
        PerformanceThreadSetEntity performanceThreadSetEntity = new PerformanceThreadSetEntity();
        performanceThreadSetEntity.setSetId(setId);
        performanceThreadSetEntity.setParentId(parentId);
        performanceThreadSetEntity.setName(name);
        performanceThreadSetEntity.setKey(key);
        performanceThreadSetEntity.setValue(value);
        performanceThreadSetEntity.setType(type);
        performanceThreadSetEntity.setExplain(explain);
        performanceThreadSetEntity.setOrderNum(orderNum);
        performanceThreadSetEntity.setFileId(fileId);
        return performanceThreadSetEntity;
    }

    /**
     * 从库中获取线程组配置并同步到指定脚本
     * @param fileId
     * @throws DocumentException
     */

    @Override
    @Transactional
    public void synchronizeJmx(Long fileId) throws DocumentException {
        SAXReader reader = new SAXReader();
        PerformanceCaseFileEntity perTestFile = performanceCaseFileDao.queryObject(fileId);
        File file = new File(performanceTestUtils.getCasePath()+File.separator+perTestFile.getFileName());
        Document document = reader.read(file);
        Element root = document.getRootElement();
        List<PerformanceThreadSetEntity> performanceThreadSetEntityList = queryListByFileId(fileId);
        setNodes(root, performanceThreadSetEntityList);
        XMLWriter writer = null;
        try {
            writer = new XMLWriter(new FileOutputStream(file));

        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        try {
            writer.write(document);
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    /**
     * 从指定节点开始，递归遍历所有子节点
     */
    public void setNodes(Element node, List<PerformanceThreadSetEntity> performanceThreadSetEntityList) {
        String attrNameValue = node.attributeValue("name"); // 获取属性名为name对应的值
        String attrParentValue = null;  // 获取父节点属性名为name所对应的值
        if (node.getParent() != null) {
            attrParentValue = node.getParent().attributeValue("name");
        }
        Entity:
        for (PerformanceThreadSetEntity performanceThreadSetEntity : performanceThreadSetEntityList) {
            if (performanceThreadSetEntity.getType() == 0) {
                String[] testPlanValues = performanceThreadSetEntity.getValue().split(";|; ");
                Values:
                for (int i = 0; i < testPlanValues.length; i++) {
                    if (attrNameValue != null && attrNameValue.equals(testPlanValues[i])) {
                        node.setText("true");
                        if (i == testPlanValues.length - 1) {
                            // 修改完的配置项就移出循环，避免重复操作
                            performanceThreadSetEntityList.remove(performanceThreadSetEntity);
                            break Entity;
                        }
                        break Values;
                    }
                }
            } else if (performanceThreadSetEntity.getType() == 1 && node.getName().endsWith("ThreadGroup")) {
                node.attribute(performanceThreadSetEntity.getKey()).setValue(performanceThreadSetEntity.getValue());
                // 修改完的配置项移出循环，避免重复操作
                performanceThreadSetEntityList.remove(performanceThreadSetEntity);
                break;
            } else {
                if (attrParentValue != null && attrParentValue.equals(performanceThreadSetEntity.getKey())) {
                    // jp@gc - Ultimate Thread Group的配置项值修改
                    List<Element> listElem = node.getParent().elements("stringProp");
                    String[] ultiThreadGroupValues = performanceThreadSetEntity.getValue().split(";|; ");
                    ValueU:
                    for (int i = 0; i < ultiThreadGroupValues.length; i++) {
                        if (listElem.get(i).equals(node)) {
                            node.setText(ultiThreadGroupValues[i]);
                            if (i == ultiThreadGroupValues.length - 1) {
                                // 修改完的配置项就移出循环，避免重复操作
                                performanceThreadSetEntityList.remove(performanceThreadSetEntity);
                                break Entity;
                            }
                            break ValueU;
                        }
                    }
                }

                if (attrNameValue != null && attrNameValue.equals(performanceThreadSetEntity.getKey()) &&
                        !node.getName().equals("collectionProp") && !node.getParent().getName().equals("LoopController")) {
                    String getValue = performanceThreadSetEntity.getValue();
                    String nodeName = node.getName();
                    if (nodeName.equals("intProp") && !node.getParent().getParent().getName().equals("ThreadGroup")) {
                        // 非ThreadGroup线程组的【循环次数】配置项都忽略不设置
                        break;
                    }
                    if (nodeName.equals("intProp") && !getValue.equals("-1")) {
                        // ThreadGroup线程组的【循环次数】配置项
                        node.setName("stringProp");
                    }
                    if (nodeName.equals("stringProp") && getValue.equals("-1")) {
                        //ThreadGroup线程组的【循环次数】配置项
                        node.setName("intProp");
                    }
                    node.setText(getValue);
                    //普通配置项值修改，修改完的配置项就移出循环，避免重复操作
                    performanceThreadSetEntityList.remove(performanceThreadSetEntity);
                }
            }
        }
        // 递归遍历当前节点所有的子节点
        List<Element> listElement = node.elements();        // 所有一级子节点的list
        for (Element e : listElement) {
            // 遍历所有子节点
            // 递归
            setNodes(e, performanceThreadSetEntityList);
        }
    }


}
