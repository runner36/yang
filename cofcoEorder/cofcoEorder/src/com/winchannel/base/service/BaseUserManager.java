package com.winchannel.base.service;

import com.winchannel.base.model.BaseUser;
import com.winchannel.core.dao.HibernateEntityDao;
import com.winchannel.core.exception.BusinessException;

public class BaseUserManager extends HibernateEntityDao<BaseUser> {
	
	private BaseSecurityManager baseSecurityManager;
	
	public void setBaseSecurityManager(BaseSecurityManager baseSecurityManager) {
		this.baseSecurityManager = baseSecurityManager;
	}
	
	public void save(Object object) {
		if (!this.isUnique(object, "userAccount")) {
			throw new BusinessException("base", "mess.accountDupl");
		}
		BaseUser user = (BaseUser) object;
		if (user.getUserId() == null && user.getUserPassword() != null) {
			//初始化时不验证密码 2011-08-30 luobin update 
			//baseSecurityManager.setUserPass(user, user.getUserPassword());
			baseSecurityManager.setEncryptPass(user, user.getUserPassword());
		}
		super.save(object);
	}
	
	public String resetPass(Long userId) {
		return baseSecurityManager.resetPass(userId);
	}

}
