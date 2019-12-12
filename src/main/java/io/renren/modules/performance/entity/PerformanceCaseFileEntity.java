package io.renren.modules.performance.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 性能测试用例文件表
 * 
 * @author mike.liu
 * @email sunlightcs@gmail.com
 * @date 2019-12-05 15:27:50
 */
@Data
@TableName("test_performance_case_file")
public class PerformanceCaseFileEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long fileId;
	/**
	 * 所关联的用例
	 */
	private Long caseId;
	/**
	 * 所关联的同步过的slave子节点
	 */
	private Long slaveId;
	/**
	 * 上传的文件名带后缀
	 */
	private String originName;
	/**
	 * 防止汉字编码错误实际保存的文件名带后缀
	 */
	private String fileName;
	/**
	 * 文件的MD5对于参数化文件有效主要用于节点的参数化文件校验
	 */
	private String fileMd5;
	/**
	 * 状态  0：初始状态  1：正在运行  2：成功执行  3：运行出现异常   -1：不被搜索出来
	 */
	private Integer status;
	/**
	 * 状态  0：保存测试报告原始文件  1：不需要测试报告
	 */
	private Integer reportStatus;
	/**
	 * 状态  0：需要前端监控  1：不需要前端监控
	 */
	private Integer webchartStatus;
	/**
	 * 状态  0：关闭debug  1：开始debug调试模式
	 */
	private Integer debugStatus;
	/**
	 * 期间，执行时间，单位秒，脚本执行多久停止，0代表永远执行
	 */
	private Integer duration;
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

	private Long id;
	//URL地址
	private String url;
	//创建时间
	private Date createDate;

	public void setUrl(String url) {
		this.url = url;
	}

	public void setCreateDate(Date date) {
		this.createDate = date;
	}
}
