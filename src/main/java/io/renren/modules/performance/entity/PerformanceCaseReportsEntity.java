package io.renren.modules.performance.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 性能测试报告文件表
 * 
 * @author mike.liu
 * @email sunlightcs@gmail.com
 * @date 2019-12-05 15:27:51
 */
@Data
@TableName("test_performance_case_reports")
public class PerformanceCaseReportsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long reportId;
	/**
	 * 所关联的用例
	 */
	private Long caseId;
	/**
	 * 所关联的用例文件
	 */
	private Long fileId;
	/**
	 * 测试报告名称
	 */
	private String originName;
	/**
	 * 避免跨系统编码错误，实际文件存储的报告名，名称和测试报告文件夹名称一致
	 */
	private String reportName;
	/**
	 * 测试结果文件大小
	 */
	private Long fileSize;
	/**
	 * 状态  0：初始状态  1：正在运行  2：成功执行  3：运行出现异常
	 */
	private Integer status;
	/**
	 * 描述
	 */
	private String remark;
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
