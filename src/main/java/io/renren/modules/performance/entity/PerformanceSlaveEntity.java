package io.renren.modules.performance.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 性能测试分布式节点表
 * 
 * @author mike.liu
 * @email sunlightcs@gmail.com
 * @date 2019-12-05 15:27:50
 */
@Data
@TableName("test_performance_slave")
public class PerformanceSlaveEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long slaveId;
	/**
	 * 子节点名称
	 */
	private String slaveName;
	/**
	 * ip地址
	 */
	private String ip;
	/**
	 * Jmeter链接端口号
	 */
	private Integer jmeterPort;
	/**
	 * 节点机用户名
	 */
	private String userName;
	/**
	 * 节点机密码
	 */
	private String passwd;
	/**
	 * ssh链接端口号
	 */
	private Integer sshPort;
	/**
	 * 子节点的Jmeter路径
	 */
	private String homeDir;
	/**
	 * 状态  0：禁用   1：正常
	 */
	private Integer status;
	/**
	 * slave节点机的权重，取值在1-99999，slave的权重可以大于或者小于master
	 */
	private Integer weight;
	/**
	 * 创建时间
	 */
	private Date addTime;
	/**
	 * 提交用户id
	 */
	private Long addBy;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 修改用户id
	 */
	private Long updateBy;

}
