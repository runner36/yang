package com.winchannel.base.bean;

import com.winchannel.base.model.BaseDataAuth;
import com.winchannel.base.model.BaseUser;

public class UserInfo {
	
	private BaseUser baseUser;

	private String orgIds;

	private String orgNames;

	private String orgSubCodes;
	
	private String roleIds;

	private String roleNames;

	private BaseDataAuth dataAuth;
	
	public UserInfo() {
		
	}

	public UserInfo(BaseUser baseUser, String orgIds, String orgNames, String orgSubCodes, String roleIds, String roleNames, BaseDataAuth dataAuth) {
		super();
		this.baseUser = baseUser;
		this.orgIds = orgIds;
		this.orgNames = orgNames;
		this.orgSubCodes = orgSubCodes;
		this.roleIds = roleIds;
		this.roleNames = roleNames;
		this.dataAuth = dataAuth;
	}

	public BaseUser getBaseUser() {
		return baseUser;
	}

	public void setBaseUser(BaseUser baseUser) {
		this.baseUser = baseUser;
	}

	public BaseDataAuth getDataAuth() {
		return dataAuth;
	}

	public void setDataAuth(BaseDataAuth dataAuth) {
		this.dataAuth = dataAuth;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	public String getOrgIds() {
		return orgIds;
	}

	public void setOrgIds(String orgIds) {
		this.orgIds = orgIds;
	}

	public String getOrgNames() {
		return orgNames;
	}

	public void setOrgNames(String orgNames) {
		this.orgNames = orgNames;
	}

	public String getOrgSubCodes() {
		return orgSubCodes;
	}

	public void setOrgSubCodes(String orgSubCodes) {
		this.orgSubCodes = orgSubCodes;
	}

	
}
