package io.renren.modules.performance.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

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
	 * 主键id
	 */
	@TableId
	private Long fileId;

	/**
	 * 批量更新时，用于更新的fileId集合
	 */
	private Long[] fileIdList;

	/**
	 * 所关联的用例
	 */
	private Long caseId;


	/**
	 * 用例名称
	 */
	private String caseName;

	/**
	 * 所关联的同步过的slave子节点
	 */
	private Long slaveId;
	/**
	 * 上传的文件名
	 */
	private String originName;
	/**
	 * 用例保存文件名，唯一化
	 */
	private String fileName;
	/**
	 * 文件的MD5对于参数化文件有效主要用于节点的参数化文件校验
	 */
	private String fileMd5;
	/**
	 * 状态  0：初始状态  1：正在运行  2：成功执行  3：运行出现异常   -1：不被搜索出来
	 */
	private Integer status = 0;
	/**
	 * 状态  0：保存测试报告原始文件  1：不需要测试报告
	 * 默认0
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
	private Integer duration = 14400;
	/**
	 * 创建时间
	 */
	private Date addTime;
	/**
	 * 提交用户id
	 */
	private String addBy;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 修改用户id
	 */
	private String updateBy;

	/**
	 * 分布式节点的字符串，可以用来判断是否使用分布式。
	 * 不入库。
	 */
	private String slaveStr;


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

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public Long getCaseId() {
		return caseId;
	}

	public void setCaseId(Long caseId) {
		this.caseId = caseId;
	}

	public String getOriginName() {
		return originName;
	}

	public void setOriginName(String originName) {
		this.originName = originName;
	}

	public String getAddBy() {
		return addBy;
	}

	public void setAddBy(String addBy) {
		this.addBy = addBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getReportStatus() {
		return reportStatus;
	}

	public void setReportStatus(Integer reportStatus) {
		this.reportStatus = reportStatus;
	}

	public Integer getWebchartStatus() {
		return webchartStatus;
	}

	public void setWebchartStatus(Integer webchartStatus) {
		this.webchartStatus = webchartStatus;
	}

	public String getSlaveStr() {
		return slaveStr;
	}

	public void setSlaveStr(String slaveStr) {
		this.slaveStr = slaveStr;
	}

	public String getFileMd5() {
		return fileMd5;
	}

	public void setFileMd5(String fileMd5) {
		this.fileMd5 = fileMd5;
	}

	public Long getSlaveId() {
		return slaveId;
	}

	public void setSlaveId(Long slaveId) {
		this.slaveId = slaveId;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public Integer getDebugStatus() {
		return debugStatus;
	}
	public void setDebugStatus(Integer debugStatus) {
		this.debugStatus = debugStatus;
	}

	public Long[] getFileIdList() {
		return fileIdList;
	}

	public void setFileIdList(Long[] fileIdList) {
		this.fileIdList = fileIdList;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	/**
	 * 深度clone
	 */
	public PerformanceCaseFileEntity clone() {
		PerformanceCaseFileEntity clone = new PerformanceCaseFileEntity();
		clone.setCaseId(this.getCaseId());
		clone.setStatus(this.getStatus());
		clone.setFileMd5(this.getFileMd5());
		clone.setFileName(this.getFileName());
		clone.setOriginName(this.getOriginName());
		clone.setSlaveStr(this.getSlaveStr());
		clone.setReportStatus(this.getReportStatus());
		clone.setWebchartStatus(this.getWebchartStatus());
		clone.setDebugStatus(this.getDebugStatus());
		clone.setSlaveId(this.getSlaveId());
		clone.setFileIdList(this.getFileIdList());
		return clone;
	}
}
