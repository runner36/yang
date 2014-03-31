package com.winchannel.core.service;

import com.winchannel.core.bean.FtpBean;
import com.winchannel.core.bean.JdbcBean;
import com.winchannel.core.bean.MailBean;


public class FilesBuilderInfo {
	
	private String zipFileName;
	private String reportName;
	private String beforeSql;
	private String afterSql;
	
	private String type;
	private String delimiter;
	private String name;
	private String zipName;
	private boolean createHeader;
	private int[] groupFields;
	private String ignoreFields;
	private String sourceDir;
	private String targetDir;
	private String headLine;
	
	private FtpBean ftpBean;
	private MailBean mailBean;
	private JdbcBean jdbcBean;
	
	private String fileCoding;
	
	private String tempDir;
	
	public String getTempDir() {
		return tempDir;
	}
	public void setTempDir(String tempDir) {
		this.tempDir = tempDir;
	}
	public String getAfterSql() {
		return afterSql;
	}
	public void setAfterSql(String afterSql) {
		this.afterSql = afterSql;
	}
	public String getBeforeSql() {
		return beforeSql;
	}
	public void setBeforeSql(String beforeSql) {
		this.beforeSql = beforeSql;
	}
	public boolean isCreateHeader() {
		return createHeader;
	}
	public void setCreateHeader(boolean createHeader) {
		this.createHeader = createHeader;
	}
	public String getDelimiter() {
		return delimiter;
	}
	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}
	public int[] getGroupFields() {
		return groupFields;
	}
	public void setGroupFields(int[] groupFields) {
		this.groupFields = groupFields;
	}
	public String getIgnoreFields() {
		return ignoreFields;
	}
	public void setIgnoreFields(String ignoreFields) {
		this.ignoreFields = ignoreFields;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public String getSourceDir() {
		return sourceDir;
	}
	public void setSourceDir(String sourceDir) {
		this.sourceDir = sourceDir;
	}
	public String getTargetDir() {
		return targetDir;
	}
	public void setTargetDir(String targetDir) {
		this.targetDir = targetDir;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getZipFileName() {
		return zipFileName;
	}
	public void setZipFileName(String zipFileName) {
		this.zipFileName = zipFileName;
	}
	public String getZipName() {
		return zipName;
	}
	public void setZipName(String zipName) {
		this.zipName = zipName;
	}
	public String getHeadLine() {
		return headLine;
	}
	public void setHeadLine(String headLine) {
		this.headLine = headLine;
	}
	public FtpBean getFtpBean() {
		return ftpBean;
	}
	public void setFtpBean(FtpBean ftpBean) {
		this.ftpBean = ftpBean;
	}
	public MailBean getMailBean() {
		return mailBean;
	}
	public void setMailBean(MailBean mailBean) {
		this.mailBean = mailBean;
	}
	public JdbcBean getJdbcBean() {
		return jdbcBean;
	}
	public void setJdbcBean(JdbcBean jdbcBean) {
		this.jdbcBean = jdbcBean;
	}
	public String getFileCoding() {
		return fileCoding;
	}
	public void setFileCoding(String fileCoding) {
		this.fileCoding = fileCoding;
	}
	
	
}
