package io.renren.modules.performance.service.impl;

import io.renren.common.exception.RRException;
import io.renren.common.utils.PageUtils;
import io.renren.modules.performance.dao.PerformanceSlaveDao;
import io.renren.modules.performance.entity.PerformanceSlaveEntity;
import io.renren.modules.performance.service.PerformanceSlaveService;
import io.renren.modules.performance.utils.PerformanceTestUtils;
import io.renren.modules.performance.utils.SSH2Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


@Service("performanceSlaveService")
public class PerformanceSlaveServiceImpl implements PerformanceSlaveService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PerformanceSlaveDao performanceSlaveDao;

    @Autowired
    private PerformanceTestUtils perTestUtils;


    @Override
    public PerformanceSlaveEntity queryObject(Long slaveId) {
        return performanceSlaveDao.queryObject(slaveId);
    }

    @Override
    public List<PerformanceSlaveEntity> queryList(Map<String, Object> map) {
        return performanceSlaveDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return performanceSlaveDao.queryTotal(map);
    }

    @Override
    public void save(PerformanceSlaveEntity perTestSlave) {
        performanceSlaveDao.save(perTestSlave);

    }

    @Override
    public void update(PerformanceSlaveEntity perTestSlave) {
        performanceSlaveDao.update(perTestSlave);

    }

    @Override
    public void deleteBatch(Long[] slaveIds) {
        performanceSlaveDao.deleteBatch(slaveIds);

    }

    /**
     * 批量切换节点的状态：真正去执行Slave节点机启动脚本。
     */
    @Override
    @Async("asyncServiceExecutor")
    public void updateBatchStatus(Long slaveId, Integer status) {
        //当前是向所有的分布式节点推送这个，阻塞操作+轮询，并非多线程，因为本地同步网卡会是瓶颈。
        //采用了先给同一个节点机传送多个文件的方式，因为数据库的连接消耗优于节点机的链接消耗
        PerformanceSlaveEntity slave = queryObject(slaveId);
        // 跳过本机节点
        if(!"127.0.0.01".equals(slave.getIp().trim())){
            //更新数据库为进行中
            slave.setStatus(PerformanceTestUtils.PROGRESSING);
            update(slave);

            try {
                runOrDownSlave(slave, status);
            } catch (RRException e) {
                // 更新为异常状态（这里异步更新需要）
                slave.setStatus(PerformanceTestUtils.RUN_ERROR);
                update(slave);
                throw e;
            }
        }

        // 更新数据库
        slave.setStatus(status);
        update(slave);

    }

    /**
     * 手工批量切换节点的状态（应用于无法远程连接节点的情况）：仅更新数据库字段
     */
    @Override
    public void updateBatchStatusForce(List<Long> slaveIds, Integer status) {
        // 使用for循环传统写法， 直接更新数据库
        for (Long slaveId : slaveIds) {
            PerformanceSlaveEntity slave = queryObject(slaveId);
            // 更新数据库
            slave.setStatus(status);
            update(slave);
        }

    }

    /**
     * 重启节点
     * @param slaveId
     */
    @Override
    public void restartSingle(Long slaveId) {
        PerformanceSlaveEntity slave = queryObject(slaveId);

        // 跳过本机节点和 已经禁用的节点
        if (!"127.0.0.1".equals(slave.getIp().trim()) && !PerformanceTestUtils.DISABLE.equals(slave.getStatus())) {
            // 更新数据库为进行中
            slave.setStatus(PerformanceTestUtils.PROGRESSING);
            update(slave);
        }
        try {
            runOrDownSlave(slave, PerformanceTestUtils.DISABLE);
            // 更新数据库为已经禁用
            slave.setStatus(PerformanceTestUtils.DISABLE);
            update(slave);

            runOrDownSlave(slave, PerformanceTestUtils.ENABLE);
            // 更新数据库为已经启用
            slave.setStatus(PerformanceTestUtils.ENABLE);
            update(slave);
        } catch (RRException e) {
            slave.setStatus(PerformanceTestUtils.RUN_ERROR);
            update(slave);
            throw e;
        }


    }


    /**
     * 启动/停止单节点
     *
     */
    private void runOrDownSlave(PerformanceSlaveEntity slave, Integer status) {
        SSH2Utils ssh2Util = new SSH2Utils(slave.getIp(), slave.getUserName(),
                slave.getPasswd(), Integer.parseInt(slave.getSshPort()));
        // 如果是启用节点
        if (PerformanceTestUtils.ENABLE.equals(status)) {
            // 启动前先检查进程，避免重复启动导致端口占用
            String psStr = ssh2Util.runCommand("ps -efww|grep -w 'jmeter-server'|grep -v grep|cut -c 9-15");
            if(psStr.equals("")) throw new RRException(slave.getSlaveName() + " 节点机连接失败!");
            if(!psStr.equals("null")) {
                //本身已经是启用状态，由进行中更新为启动状态（这是异步更新需要）
                slave.setStatus(PerformanceTestUtils.ENABLE);
                update(slave);
                if(PerformanceTestUtils.ENABLE.equals(slave.getStatus())) {
                    logger.error(slave.getSlaveName() + " 已经启动不要重复启动！-----------------------------");
                } else {
                    logger.error(slave.getSlaveName() + " 节点机进程已存在，请先校准！-----------------------");
                }
            }
            // 避免跨系统的问题，远端由于都时linux服务器，则文件分隔符统一为/，不然同步文件会报错。
            String jmeterServer = slave.getHomeDir() + "/bin/jmeter-server";
            String md5Str = ssh2Util.runCommand("md5sum " + jmeterServer + " | cut -d ' ' -f1");
            if (!checkMD5(md5Str)){
                throw new RRException(slave.getSlaveName() + " 执行遇到问题！找不到jmeter-server启动文件！" );
            }
            // 首先创建目录，会遇到重复创建
            ssh2Util.runCommand("mkdir " + slave.getHomeDir() + "/bin/perTestCase");
            // 启动节点
            String enableResult = ssh2Util.runCommand(
                    "cd " + slave.getHomeDir() + "/bin/perTestCase" + "\n" +
                            "sh " + "../jmeter-server -Djava.rmi.server.hostname=" + slave.getIp());

            logger.error(enableResult);

            if (!enableResult.contains("remote") && !enableResult.contains("Using local port")){
                // Using local port 表示固定本地端口（默认jmeter-server的本地端口是动态的）
                throw new RRException(slave.getSlaveName() + " jmeter-server启动节点失败！请先尝试在节点机命令执行");
            }

        }
        // 禁用节点
        if (PerformanceTestUtils.DISABLE.equals(status)){
            // 禁用远程节点，当前是直接kill掉
            //kill掉就不用判断结果了，不抛异常即OK
            //考虑到网络的操作容易失败，执行2次kill
            ssh2Util.runCommand("ps -efww|grep -w 'jmeter-server'|grep -v grep|cut -c 9-18|xargs kill -9");
            perTestUtils.pause(2000);
            ssh2Util.runCommand("ps -efww|grep -w 'jmeter-server'|grep -v grep|cut -c 9-18|xargs kill -9");
        }
    }

    /**
     * 使用正则表达式校验MD5合法性
     */
    public boolean checkMD5(String md5Str) {
        return Pattern.matches("^([a-fA-F0-9]{32})$", md5Str);
    }
    @Override
    public void batchReloadStatus() {
        //当前是向所有的分布式节点推送这个，阻塞操作+轮询，并非多线程，因为本地同步网卡会是瓶颈。
        //先处理已禁用的进程
        Map query = new HashMap<>();
        query.put("status", PerformanceTestUtils.DISABLE);
        List<PerformanceSlaveEntity> perTestSlaveList = performanceSlaveDao.queryList(query);
        for (PerformanceSlaveEntity slave : perTestSlaveList) {
            // 本机配置IP为127.0.0.1， 没配置localhost
            if ("127.0.0.1".equals(slave.getIp().trim())){
                continue;
            } else {
                SSH2Utils ssh2Util = new SSH2Utils(slave.getIp(), slave.getUserName(),
                        slave.getPasswd(), Integer.parseInt(slave.getSshPort()));
                // 界面状态显示禁用，重置过程就是杀死节点已存在的进程
                ssh2Util.runCommand("ps -efww|grep -w 'jmeter-server'|grep -v grep|cut -c 9-15|xargs kill -9");
            }
        }
        // 在处理已启用的进程
        query.put("status", PerformanceTestUtils.ENABLE);
        List<PerformanceSlaveEntity> perTestSlaveList2 = performanceSlaveDao.queryList(query);
        for (PerformanceSlaveEntity slave : perTestSlaveList2) {
            // 本机配置IP为127.0.0.1， 没配置localhost
            if ("127.0.0.1".equals(slave.getIp().trim())){
                continue;
            } else {
                SSH2Utils ssh2Util = new SSH2Utils(slave.getIp(), slave.getUserName(),
                        slave.getPasswd(), Integer.parseInt(slave.getSshPort()));
                //界面状态显示启用，后台不存在进程就远程启动
                String psStr = ssh2Util.runCommand("ps -efww|grep -w 'jmeter-server'|grep -v grep|cut -c 9-15");
                if(psStr.equals("null")){
                    runOrDownSlave(slave, 1);
                }
            }

        }

    }

}
