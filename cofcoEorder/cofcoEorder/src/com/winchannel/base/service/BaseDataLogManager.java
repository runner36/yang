package com.winchannel.base.service;

import java.util.Date;

import com.winchannel.base.model.BaseDataLog;
import com.winchannel.core.dao.HibernateEntityDao;

public class BaseDataLogManager extends HibernateEntityDao<BaseDataLog> {
	
	public void saveLog(String userName, String tableName, String columnName, String oldValue, String newValue) {
		BaseDataLog log = new BaseDataLog();
		log.setUserName(userName);
		log.setTableName(tableName);
		log.setColumnName(columnName);
		log.setOldValue(oldValue);
		log.setNewValue(newValue);
		log.setUpdateTime(new Date());
		this.save(log);
	}
	
}
