package com.winchannel.base.model;

import java.util.Date;

/**
 * BasePasswordLog entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class BasePasswordLog implements java.io.Serializable {

	// Fields

	private Long passId;
	private String userAccount;
	private String userPassword;
	private String createdBy;
	private Date created;

	// Constructors

	/** default constructor */
	public BasePasswordLog() {
	}

	/** minimal constructor */
	public BasePasswordLog(Long passId) {
		this.passId = passId;
	}

	/** full constructor */
	public BasePasswordLog(Long passId, String userAccount, String userPassword, String createdBy, Date created) {
		this.passId = passId;
		this.userAccount = userAccount;
		this.userPassword = userPassword;
		this.createdBy = createdBy;
		this.created = created;
	}

	// Property accessors

	public Long getPassId() {
		return this.passId;
	}

	public void setPassId(Long passId) {
		this.passId = passId;
	}

	public String getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getUserPassword() {
		return this.userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

}