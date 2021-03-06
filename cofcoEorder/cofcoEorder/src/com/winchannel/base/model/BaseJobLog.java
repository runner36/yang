package com.winchannel.base.model;

import java.util.Date;

/**
 * BaseJobLog generated by MyEclipse Persistence Tools
 */

public class BaseJobLog implements java.io.Serializable {

	// Fields

	private Long jobLogId;

	// private BaseJob baseJob;

	private String baseJob;

	private Date startTime;

	private Date endTime;

	private String isComplete;

	private String execResult;

	private String errorInfo;

	private String updatedBy;

	private Date updated;

	private String remark;

	private String accessory;

	// Constructors

	/** default constructor */
	public BaseJobLog() {
	}

	// Property accessors

	public Long getJobLogId() {
		return this.jobLogId;
	}

	public void setJobLogId(Long jobLogId) {
		this.jobLogId = jobLogId;
	}

	// public BaseJob getBaseJob() {
	// return this.baseJob;
	// }
	//
	// public void setBaseJob(BaseJob baseJob) {
	// this.baseJob = baseJob;
	// }

	public Date getStartTime() {
		return this.startTime;
	}

	public String getBaseJob() {
		return baseJob;
	}

	public void setBaseJob(String baseJob) {
		this.baseJob = baseJob;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getIsComplete() {
		return this.isComplete;
	}

	public void setIsComplete(String isComplete) {
		this.isComplete = isComplete;
	}

	public String getExecResult() {
		return this.execResult;
	}

	public void setExecResult(String execResult) {
		this.execResult = execResult;
	}

	public String getErrorInfo() {
		return this.errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdated() {
		return this.updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAccessory() {
		return this.accessory;
	}

	public void setAccessory(String accessory) {
		this.accessory = accessory;
	}

}