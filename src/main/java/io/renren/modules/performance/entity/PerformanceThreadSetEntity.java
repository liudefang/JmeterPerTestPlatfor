package io.renren.modules.performance.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 线程组管理
 * 
 * @author mike.liu
 * @email sunlightcs@gmail.com
 * @date 2019-12-05 15:27:50
 */
@Data
@TableName("test_performance_thread_set")
public class PerformanceThreadSetEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String setId;
	/**
	 * 所属ID，一级配置为0
	 */
	private String parentId;
	/**
	 * 配置名称
	 */
	private String name;
	/**
	 * 配置项
	 */
	private String key;
	/**
	 * 配置内容
	 */
	private String value;
	/**
	 * 类型   0：脚本   1：线程组   2：配置
	 */
	private Integer type;
	/**
	 * 配置说明
	 */
	private String explain;
	/**
	 * 排序
	 */
	private Integer orderNum;
	/**
	 * 所属脚本文件ID
	 */
	private Long fileId;

}
