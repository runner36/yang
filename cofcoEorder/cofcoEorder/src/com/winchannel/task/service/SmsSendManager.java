package com.winchannel.task.service;

import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.winchannel.core.dao.HibernateBaseDao;
import com.winchannel.core.utils.JdbcProxy;
import com.winchannel.task.bean.SmsInfoBean;

/**
 * @author lixin
 * @datetime 2011-11-1上午11:23:48
 * @file_name SmsSendManager.java
 */
public class SmsSendManager extends HibernateBaseDao {
	private static Logger log = LogManager.getLogger(SmsSendManager.class);
	private  Map smstemplate=new java.util.HashMap();

	public static Logger getLog() {
		return log;
	}

	public static void setLog(Logger log) {
		SmsSendManager.log = log;
	}

	 

	public Map getSmstemplate() {
		return smstemplate;
	}

	public   void setSmstemplate(Map smstemplates) {
		smstemplate = smstemplates;
	}

	/**
	 * 插入短信接口表
	 * 
	 * @param phoneNum
	 * @param msg
	 * @throws Exception 
	 */
	private void insertSms(String phoneNum, String msg) throws Exception {

		JdbcProxy dbSms = null;
		StringBuffer sql = new StringBuffer("");
		try {
		 
			
			dbSms = new JdbcProxy("smsTemplate");
			sql.delete(0, sql.length());
		//	sql.append(" INSERT smsinfo ( sysid,telphones,message)"); 
			sql.append(" INSERT sms_fsdx ( sms_tfid,sms_sjh,sms_nr,sms_date,sms_zt)");
			sql.append(" VALUES ( 'B001','").append(phoneNum+"','").append(msg+"' ,getdate(),'0')");
			dbSms.executeUpdate(sql.toString());
		} catch (Throwable ex) {
			log.error(" 短信发送失败：" +  ex.getMessage(), ex);
			throw new Exception("ERR message send err:"+ex.getMessage());
		} finally {
			if (dbSms != null) {
				dbSms.close();
			}
		}
	}

	/**
	 * 
	 * @param smsInfo
	 *            根据status 获取短信内容，并替换
	 */
	private void getSmsMsg(SmsInfoBean smsInfo) {
		String tmpMsg = smstemplate.get(smsInfo.getStatus()).toString();
		tmpMsg=tmpMsg.replace("#salesName", smsInfo.getSalesName());
		tmpMsg=tmpMsg.replace("#custName", smsInfo.getCustName());
		tmpMsg=tmpMsg.replace("#orderCode", smsInfo.getOrderCode());
		tmpMsg=tmpMsg.replace("#orderNumber", smsInfo.getOrderNumber());
		smsInfo.setMessage(tmpMsg);
	}

	

	/**
	 * 发送短信
	 * 
	 * @param smsInfo
	 * @throws Exception
	 */
	public void sendMsg(SmsInfoBean smsInfo) throws Exception {
		if (smsInfo == null) {
			throw new Exception("ERR SmsInfoBean is null.");
		}
		if (smsInfo.getPhoneNum() == null) {
			throw new Exception("ERR phoneNum is null.");
		}
		if ("".equals(smsInfo.getPhoneNum().trim())) {
			throw new Exception("ERR phoneNum is null.");
		}
		this.getSmsMsg(smsInfo);
		if (smsInfo.getMessage() == null) {
			throw new Exception("ERR message is null; May be due to status err.");
		}
		if ("".equals(smsInfo.getMessage().trim())) {
			throw new Exception("ERR message is null; May be due to status err.");
		}
		insertSms(smsInfo.getPhoneNum(), smsInfo.getMessage());

	}

	public static void main(String[] args) {
		SmsInfoBean s = new SmsInfoBean();

	}
}
