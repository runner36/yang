package com.winchannel.base.service;

import com.winchannel.base.model.BaseResource;
import com.winchannel.core.dao.HibernateEntityDao;
import com.winchannel.core.exception.BusinessException;

public class BaseResourceManager extends HibernateEntityDao<BaseResource> {
	
	public void save(Object object) {
		if (!this.isUnique(object, "resCode")) {
			throw new BusinessException("base","common.codeDuplication");
		}
		super.save(object);
	}
	
}
