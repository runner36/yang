package com.winchannel.order.model;

import java.util.Date;

/**
 * OrderSuperAbolishedLog entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class OrderSuperAbolishedLog implements java.io.Serializable {

	// Fields

	private Long id;
	private OrderInfo orderInfo;
	private String memo;
	private Integer oldStatus;
	private Integer newStatus;
	private Long createdByid;
	private Date createdDate;
	private String createdByname;


	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OrderInfo getOrderInfo() {
		return this.orderInfo;
	}

	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getOldStatus() {
		return this.oldStatus;
	}

	public void setOldStatus(Integer oldStatus) {
		this.oldStatus = oldStatus;
	}

	public Integer getNewStatus() {
		return this.newStatus;
	}

	public void setNewStatus(Integer newStatus) {
		this.newStatus = newStatus;
	}

	public Long getCreatedByid() {
		return this.createdByid;
	}

	public void setCreatedByid(Long createdByid) {
		this.createdByid = createdByid;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedByname() {
		return this.createdByname;
	}

	public void setCreatedByname(String createdByname) {
		this.createdByname = createdByname;
	}

}