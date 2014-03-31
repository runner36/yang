package com.winchannel.core.bean;

public class JdbcBean {
	private String jdbcBeanId;
	private String insertSql;
	private String maxIdSql;
	private String idStr;
	public String getJdbcBeanId() {
		return jdbcBeanId;
	}
	public void setJdbcBeanId(String jdbcBeanId) {
		this.jdbcBeanId = jdbcBeanId;
	}
	public String getInsertSql() {
		return insertSql;
	}
	public void setInsertSql(String insertSql) {
		this.insertSql = insertSql;
	}
	public String getMaxIdSql() {
		return maxIdSql;
	}
	public void setMaxIdSql(String maxIdSql) {
		this.maxIdSql = maxIdSql;
	}
	public String getIdStr() {
		return idStr;
	}
	public void setIdStr(String idStr) {
		this.idStr = idStr;
	}
	
	
}
