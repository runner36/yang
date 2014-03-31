package com.winchannel.base.conf;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class BaseConfigurator {
	
	public static final String FILE_NAME = BaseConfigurator.class.getResource("/config/base.properties").getFile();
	
	private static BaseConfigurator instance;
	
	private int passLen;
	private int passLowerNum;
	private String passLowerChars;
	private int passUpperNum;
	private String passUpperChars;
	private int passDigitNum;
	private String passDigitChars;
	private int passSpecialNum;
	private String passSpecialChars;
	private int passChangeCycle;
	private boolean passEncryption;
	private String passEncryKey = "winchannel!@#$%^";
	private int loginFailureNum;
	
	private int passChangePrompt;
	private boolean passFirstChange;
	
	private boolean baseLog;
	private boolean dataLog;
	
	private String loginUri;
	private String ignoreUri;
	private boolean uriAuth;
	
	private String defaultHome;
	
	private long uploadFileTimeout=3600000;	
	private long checkUpFileTimeoutInterval=60000;
	
	private int beforeUserNum;
	private boolean beforeAvailable;
	private boolean recordLoginCount;
	private String createDistUserdefaultRoleName;
	private boolean distSame;
	private String waitToDealOrderCoutRefreshInterval;
	
	private String orderAgreementLogUrlAll;
	private String orderAgreementLogUrl;
	
	public static BaseConfigurator getInstance() {
		if (instance == null) {
			instance = new BaseConfigurator();
		}
		return instance;
	}
	
	private BaseConfigurator() {
		init();
	}
	
	public void init() {
		Properties props = new Properties();
		try {
			InputStream ips = new BufferedInputStream(new FileInputStream(FILE_NAME));
			props.load(ips);
			this.passLen = Integer.parseInt(props.getProperty("password.rule.length"));
			this.passLowerNum = Integer.parseInt(props.getProperty("password.rule.lowercase.number"));
			this.passLowerChars = props.getProperty("password.rule.lowercase.chars");
			this.passUpperNum = Integer.parseInt(props.getProperty("password.rule.uppercase.number"));
			this.passUpperChars = props.getProperty("password.rule.uppercase.chars");
			this.passDigitNum = Integer.parseInt(props.getProperty("password.rule.digit.number"));
			this.passDigitChars = props.getProperty("password.rule.digit.chars");
			this.passSpecialNum = Integer.parseInt(props.getProperty("password.rule.special.number"));
			this.passSpecialChars = props.getProperty("password.rule.special.chars");
			this.passChangeCycle = Integer.parseInt(props.getProperty("password.changeCycle"));
			this.passEncryption = Boolean.parseBoolean(props.getProperty("password.encryption"));
			this.loginFailureNum = Integer.parseInt(props.getProperty("login.failureNum"));
			this.createDistUserdefaultRoleName=props.getProperty("createDistUser.defaultRoleName");
			this.passChangePrompt = Integer.parseInt(props.getProperty("password.changePrompt"));
			this.passFirstChange = Boolean.parseBoolean(props.getProperty("password.firstChange"));
			
			this.baseLog = Boolean.parseBoolean(props.getProperty("log.baseLog"));
			this.dataLog = Boolean.parseBoolean(props.getProperty("log.dataLog"));
			this.loginUri = props.getProperty("security.uri.login");
			this.ignoreUri = props.getProperty("security.uri.ignore");
			this.uriAuth = Boolean.parseBoolean(props.getProperty("security.uri.auth"));
			
			this.defaultHome = props.getProperty("system.defaultHome");
			this.uploadFileTimeout=Long.parseLong(props.getProperty("uploadFileTimeout"))*60*1000;
			this.checkUpFileTimeoutInterval=Long.parseLong(props.getProperty("checkUpFileTimeoutInterval"))*60*1000;

			this.beforeUserNum = Integer.parseInt(props.getProperty("password.beforeUserNum"));
			this.beforeAvailable = Boolean.parseBoolean(props.getProperty("password.beforeAvailable"));
			this.recordLoginCount = Boolean.parseBoolean(props.getProperty("login.recordLoginCount"));
			
			this.distSame = Boolean.parseBoolean(props.getProperty("storeMapping.distSame"));
			this.waitToDealOrderCoutRefreshInterval=props.getProperty("waitToDealOrderCoutRefreshInterval");
			
			this.orderAgreementLogUrlAll=props.getProperty("orderAgreementLogAll.url");
			this.orderAgreementLogUrl=props.getProperty("orderAgreementLog.url");
			ips.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
 	}

	public String getCreateDistUserdefaultRoleName() {
		return createDistUserdefaultRoleName;
	}

	public void setCreateDistUserdefaultRoleName(
			String createDistUserdefaultRoleName) {
		this.createDistUserdefaultRoleName = createDistUserdefaultRoleName;
	}

	public int getPassChangeCycle() {
		return passChangeCycle;
	}

	public String getWaitToDealOrderCoutRefreshInterval() {
		return waitToDealOrderCoutRefreshInterval;
	}

	public String getPassDigitChars() {
		return passDigitChars;
	}

	public int getPassDigitNum() {
		return passDigitNum;
	}

	public String getPassEncryKey() {
		return passEncryKey;
	}

	public boolean isPassEncryption() {
		return passEncryption;
	}

	public int getPassLen() {
		return passLen;
	}

	public String getPassLowerChars() {
		return passLowerChars;
	}

	public int getPassLowerNum() {
		return passLowerNum;
	}

	public String getPassSpecialChars() {
		return passSpecialChars;
	}

	public int getPassSpecialNum() {
		return passSpecialNum;
	}

	public String getPassUpperChars() {
		return passUpperChars;
	}

	public int getPassUpperNum() {
		return passUpperNum;
	}

	public int getLoginFailureNum() {
		return loginFailureNum;
	}

	public boolean isBaseLog() {
		return baseLog;
	}

	public boolean isDataLog() {
		return dataLog;
	}

	public String getLoginUri() {
		return loginUri;
	}

	public String getIgnoreUri() {
		return ignoreUri;
	}

	public boolean isUriAuth() {
		return uriAuth;
	}

	public int getPassChangePrompt() {
		return passChangePrompt;
	}

	public boolean isPassFirstChange() {
		return passFirstChange;
	}

	public String getDefaultHome() {
		return defaultHome;
	}

	public long getUploadFileTimeout() {
		return uploadFileTimeout;
	}

	public long getCheckUpFileTimeoutInterval() {
		return checkUpFileTimeoutInterval;
	}

	public void setDefaultHome(String defaultHome) {
		this.defaultHome = defaultHome;
	}

	public int getBeforeUserNum() {
		return beforeUserNum;
	}

	public void setBeforeUserNum(int beforeUserNum) {
		this.beforeUserNum = beforeUserNum;
	}

	public boolean isBeforeAvailable() {
		return beforeAvailable;
	}

	public void setBeforeAvailable(boolean beforeAvailable) {
		this.beforeAvailable = beforeAvailable;
	}

	public boolean isRecordLoginCount() {
		return recordLoginCount;
	}

	public void setRecordLoginCount(boolean recordLoginCount) {
		this.recordLoginCount = recordLoginCount;
	}

	public boolean isDistSame() {
		return distSame;
	}

	public void setDistSame(boolean distSame) {
		this.distSame = distSame;
	}

	
	public String getOrderAgreementLogUrlAll() {
		return orderAgreementLogUrlAll;
	}

	public void setOrderAgreementLogUrlAll(String orderAgreementLogUrlAll) {
		this.orderAgreementLogUrlAll = orderAgreementLogUrlAll;
	}

	public String getOrderAgreementLogUrl() {
		return orderAgreementLogUrl;
	}

	public void setOrderAgreementLogUrl(String orderAgreementLogUrl) {
		this.orderAgreementLogUrl = orderAgreementLogUrl;
	}
	
}
