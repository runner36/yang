package com.winchannel.base.service;

import java.util.List;

import com.winchannel.base.model.BaseDataAuth;
import com.winchannel.core.dao.HibernateEntityDao;

public class BaseDataAuthManager extends HibernateEntityDao<BaseDataAuth> {
	
	public List<BaseDataAuth> getAll() {
		return this.findEntity("from BaseDataAuth where state='1' order by sort");
	}

}
