package com.winchannel.base.model;

import java.util.Date;

/**
 * BaseDataLog generated by MyEclipse Persistence Tools
 */

public class BaseDataLog implements java.io.Serializable {

	// Fields

	private String dataLogId;

	private BaseUser baseUser;

	private String userName;

	private String tableName;

	private String columnName;

	private String oldValue;

	private String newValue;

	private Date updateTime;

	// Constructors

	/** default constructor */
	public BaseDataLog() {
	}

	/** full constructor */
	public BaseDataLog(BaseUser baseUser, String userName, String tableName,
			String columnName, String oldValue, String newValue, Date updateTime) {
		this.baseUser = baseUser;
		this.userName = userName;
		this.tableName = tableName;
		this.columnName = columnName;
		this.oldValue = oldValue;
		this.newValue = newValue;
		this.updateTime = updateTime;
	}

	// Property accessors

	public String getDataLogId() {
		return this.dataLogId;
	}

	public void setDataLogId(String dataLogId) {
		this.dataLogId = dataLogId;
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

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnName() {
		return this.columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getOldValue() {
		return this.oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public String getNewValue() {
		return this.newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}