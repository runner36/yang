package com.winchannel.base.service;

import java.util.List;

import com.winchannel.base.model.BaseAuth;
import com.winchannel.base.model.BaseResource;
import com.winchannel.base.model.BaseRole;
import com.winchannel.core.dao.HibernateEntityDao;
import com.winchannel.core.utils.StringUtils;

public class BaseAuthManager extends HibernateEntityDao<BaseAuth> {
	
	public List<BaseResource> getResourceByRoleId(Long roleId) {
		return this.findEntity(BaseResource.class, "select auth.baseResource from BaseAuth auth where auth.baseRole.roleId=?", roleId);
	}
	
	public String getResIdByRoleId(Long roleId) {
		List<BaseResource> resources = getResourceByRoleId(roleId);
		String resId = "";
		for (BaseResource res : resources) {
			if (!resId.equals("")) {
				resId += ",";
			}
			resId += res.getResId();
			
		}
		return resId;
	}
	
	public void deleteByRoleId(Long roleId) {
		this.deleteAll("from BaseAuth where baseRole.roleId=?", roleId);
	}
	
	public void saveResourcesOfRole(BaseRole baseRole, String resId) {
		deleteByRoleId(baseRole.getRoleId());
		
		if (StringUtils.isNotBlank(resId)) {
			String[] resIds = resId.split(",");
			for (String id : resIds) {
				BaseAuth auth = new BaseAuth();
				auth.setBaseRole(baseRole);
				
				BaseResource res = new BaseResource();
				res.setResId(new Long(id));
				auth.setBaseResource(res);
				save(auth);
			}
		}
	}
	
}
