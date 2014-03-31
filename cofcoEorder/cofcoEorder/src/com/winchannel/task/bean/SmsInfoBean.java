/**
 * @author     lixin
 * @datetime   2011-11-1上午11:33:11
 * @file_name  SmsInfoBean.java
 */
package com.winchannel.task.bean;

/**
 * 
 * @author lixin 短信信息表
 */
public class SmsInfoBean {
	/**
	 * 手机号
	 */
	private String phoneNum;
	/**
	 * 订单状态
	 */
	private String status;
	/**
	 * 客户名称
	 */
	private String custName;
	/**
	 * 订单编号
	 */
	private String orderCode;
	/**
	 * 订单数量
	 */
	private String orderNumber;
	/**
	 * 销售代表名称
	 */
	private String salesName;
	/**
	 * 短信内容
	 */
	private String message;

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getSalesName() {
		return salesName;
	}

	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
