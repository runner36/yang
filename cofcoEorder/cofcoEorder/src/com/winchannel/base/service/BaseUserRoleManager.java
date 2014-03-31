package com.winchannel.base.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.winchannel.base.model.BaseRole;
import com.winchannel.base.model.BaseUser;
import com.winchannel.base.model.BaseUserRole;
import com.winchannel.core.dao.HibernateEntityDao;

public class BaseUserRoleManager extends HibernateEntityDao<BaseUserRole> {
	
	public List<BaseRole> getRoleByUserId(Long userId) {
		return this.findEntity(BaseRole.class, "select ur.baseRole from BaseUserRole ur where ur.baseUser.userId=?", userId);
	}
	
	public String getRoleIdByUserId(Long userId) {
		List<BaseRole> roles = getRoleByUserId(userId);
		String roleId = "";
		for (BaseRole role : roles) {
			if (!roleId.equals("")) {
				roleId += ",";
			}
			roleId += role.getRoleId();
			
		}
		return roleId;
	}
	
	public void deleteByUserId(Long userId) {
		this.deleteAll("from BaseUserRole where baseUser.userId=?", userId);
	}
	
	public void saveRolesOfUser(BaseUser baseUser, String roleId) {
		deleteByUserId(baseUser.getUserId());
		
		if (StringUtils.isNotBlank(roleId)) {
			String[] roleIds = roleId.split(",");
			for (String id : roleIds) {
				BaseUserRole userRole = new BaseUserRole();
				userRole.setBaseUser(baseUser);
				
				BaseRole role = new BaseRole();
				role.setRoleId(new Long(id));
				
				userRole.setBaseRole(role);
				save(userRole);
			}
		}
	}
}
