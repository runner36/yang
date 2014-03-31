package com.winchannel.message.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

/**
 * BaseOpinion entity.
 * 
 * @author caishang
 */

public class BaseOpinions implements java.io.Serializable {

	// Fields

	private Long id;
	private String title;
	private String content;
	private Long isdel;
	private Date createdBy;
	private String created;
	private String updated;
	private Date updatedBy;
	private Set fileItems = new HashSet(0);
	private Set baseAuthorities = new HashSet(0);

	// Constructors

	/** default constructor */
	public BaseOpinions() {
	}

	/** full constructor */
	public BaseOpinions(String title, String content, Long isdel,
			Date createdBy, String created, String updated, Date updatedBy,
			Set fileItems, Set baseAuthorities) {
		this.title = title;
		this.content = content;
		this.isdel = isdel;
		this.createdBy = createdBy;
		this.created = created;
		this.updated = updated;
		this.updatedBy = updatedBy;
		this.fileItems = fileItems;
		this.baseAuthorities = baseAuthorities;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
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

	public Long getIsdel() {
		return this.isdel;
	}

	public void setIsdel(Long isdel) {
		this.isdel = isdel;
	}

	public Date getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Date createdBy) {
		this.createdBy = createdBy;
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

	public Date getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(Date updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Set getFileItems() {
		return this.fileItems;
	}
	
	public boolean isFileItemsEmpty() {
		return CollectionUtils.isEmpty(fileItems);
	}
	
	public void setFileItems(Set fileItems) {
		this.fileItems = fileItems;
	}

	public Set getBaseAuthorities() {
		return this.baseAuthorities;
	}

	public void setBaseAuthorities(Set baseAuthorities) {
		this.baseAuthorities = baseAuthorities;
	}

}