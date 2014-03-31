package com.winchannel.base.model;

import java.util.HashSet;
import java.util.Set;

/**
 * BaseResource generated by MyEclipse Persistence Tools
 */

public class BaseResource implements java.io.Serializable {

	// Fields

	private Long resId;

	private BaseDictItem baseDictItem;

	private String resCode;

	private String resName;

	private String resUri;

	private String remark;

	private String state;

	private Long sort;

	private Set baseAuths = new HashSet(0);

	private Set baseMenus = new HashSet(0);

	// Constructors

	/** default constructor */
	public BaseResource() {
	}

	/** full constructor */
	public BaseResource(BaseDictItem baseDictItem, String resCode, String resName, String resUri, String remark,
			String state, Long sort, Set baseAuths, Set baseMenus) {
		this.baseDictItem = baseDictItem;
		this.resCode = resCode;
		this.resName = resName;
		this.resUri = resUri;
		this.remark = remark;
		this.state = state;
		this.sort = sort;
		this.baseAuths = baseAuths;
		this.baseMenus = baseMenus;
	}

	// Property accessors

	public Long getResId() {
		return this.resId;
	}

	public void setResId(Long resId) {
		this.resId = resId;
	}

	public BaseDictItem getBaseDictItem() {
		return this.baseDictItem;
	}

	public void setBaseDictItem(BaseDictItem baseDictItem) {
		this.baseDictItem = baseDictItem;
	}

	public String getResCode() {
		return this.resCode;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode;
	}

	public String getResName() {
		return this.resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	public String getResUri() {
		return this.resUri;
	}

	public void setResUri(String resUri) {
		this.resUri = resUri;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Long getSort() {
		return this.sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	public Set getBaseAuths() {
		return this.baseAuths;
	}

	public void setBaseAuths(Set baseAuths) {
		this.baseAuths = baseAuths;
	}

	public Set getBaseMenus() {
		return this.baseMenus;
	}

	public void setBaseMenus(Set baseMenus) {
		this.baseMenus = baseMenus;
	}

}