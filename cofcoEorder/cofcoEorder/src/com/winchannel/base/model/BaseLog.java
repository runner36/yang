package com.winchannel.base.model;

import java.util.Date;

/**
 * BaseLog generated by MyEclipse Persistence Tools
 */

public class BaseLog implements java.io.Serializable {

	// Fields

	private String logId;

	private BaseUser baseUser;

	private String userName;

	private String resUri;

	private String resName;

	private Date startTime;

	private Date endTime;

	private Long usedTime;

	private String clientIp;

	private String clientName;

	// Constructors

	/** default constructor */
	public BaseLog() {
	}

	/** full constructor */
	public BaseLog(BaseUser baseUser, String userName, String resUri, String resName, Date startTime, Date endTime,
			Long usedTime, String clientIp, String clientName) {
		this.baseUser = baseUser;
		this.userName = userName;
		this.resUri = resUri;
		this.resName = resName;
		this.startTime = startTime;
		this.endTime = endTime;
		this.usedTime = usedTime;
		this.clientIp = clientIp;
		this.clientName = clientName;
	}

	// Property accessors

	public String getLogId() {
		return this.logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public BaseUser getBaseUser() {
		return this.baseUser;
	}

	public void setBaseUser(BaseUser baseUser) {
		this.baseUser = baseUser;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getResUri() {
		return this.resUri;
	}

	public void setResUri(String resUri) {
		this.resUri = resUri;
	}

	public String getResName() {
		return this.resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	public Date getStartTime() {
		return this.startTime;
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

	public Long getUsedTime() {
		return this.usedTime;
	}

	public void setUsedTime(Long usedTime) {
		this.usedTime = usedTime;
	}

	public String getClientIp() {
		return this.clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getClientName() {
		return this.clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

}