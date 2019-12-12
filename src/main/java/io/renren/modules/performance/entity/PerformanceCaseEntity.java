package io.renren.modules.performance.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 性能测试用例表
 * 
 * @author mike.liu
 * @email sunlightcs@gmail.com
 * @date 2019-12-05 15:27:50
 */
@Data
@TableName("test_performance_case")
public class PerformanceCaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long caseId;
	/**
	 * 用例名
	 */
	private String caseName;
	/**
	 * 所属项目
	 */
	private String project;
	/**
	 * 所属模块
	 */
	private String module;
	/**
	 * 状态  0：禁用   1：正常
	 */
	private Integer status;
	/**
	 * 拥有者名字
	 */
	private String operator;
	/**
	 * 描述
	 */
	private String remark;
	/**
	 * 优先级用于过滤
	 */
	private Integer priority;
	/**
	 * master节点保存用例信息的文件夹
	 */
	private String caseDir;
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
