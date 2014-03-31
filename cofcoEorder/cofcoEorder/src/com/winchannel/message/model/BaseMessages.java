package com.winchannel.message.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * BaseMessage entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class BaseMessages implements java.io.Serializable {

	// Fields

	private Long newId;
	private String title;
	private String content;
	private Long type;
	private Long isdel;
	private Date createdby;
	private String created;
	private String updated;
	private Date updatedby;
	private Set baseAuthorities = new HashSet(0);
	private Set fileItems = new HashSet(0);

	// Constructors

	/** default constructor */
	public BaseMessages() {
	}

	/** full constructor */
	public BaseMessages(String title, String content, Long type, Long isdel,
			Date createdby, String created, String updated, Date updatedby,
			Set baseAuthorities, Set fileItems) {
		this.title = title;
		this.content = content;
		this.type = type;
		this.isdel = isdel;
		this.createdby = createdby;
		this.created = created;
		this.updated = updated;
		this.updatedby = updatedby;
		this.baseAuthorities = baseAuthorities;
		this.fileItems = fileItems;
	}

	// Property accessors



	public String getTitle() {
		return this.title;
	}

	public Long getNewId() {
		return newId;
	}

	public void setNewId(Long newId) {
		this.newId = newId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getType() {
		return this.type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Long getIsdel() {
		return this.isdel;
	}

	public void setIsdel(Long isdel) {
		this.isdel = isdel;
	}

	public Date getCreatedby() {
		return this.createdby;
	}

	public void setCreatedby(Date createdby) {
		this.createdby = createdby;
	}

	public String getCreated() {
		return this.created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getUpdated() {
		return this.updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public Date getUpdatedby() {
		return this.updatedby;
	}

	public void setUpdatedby(Date updatedby) {
		this.updatedby = updatedby;
	}

	public Set getBaseAuthorities() {
		return this.baseAuthorities;
	}

	public void setBaseAuthorities(Set baseAuthorities) {
		this.baseAuthorities = baseAuthorities;
	}

	public Set getFileItems() {
		return this.fileItems;
	}

	public void setFileItems(Set fileItems) {
		this.fileItems = fileItems;
	}

}