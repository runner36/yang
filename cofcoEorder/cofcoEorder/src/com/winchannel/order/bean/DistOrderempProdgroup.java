package com.winchannel.order.bean;

import java.util.Date;

/**
 * 
 * 用于存放MdmDistEmpProdgroup查询的数据
 * 
 */
public class DistOrderempProdgroup implements java.io.Serializable{
	
	private Long id;
	private Long distId;
	private Long empId;
	private Long dictItemId;
	private Date effectiveTime;
	private Date expiryTime;
	private String factoryDelivery;
	private String memo1;
	private String memo2;
	private String memo3;
	private Date createdDate;
	private Long createdByid;
	private String createdByname;
	private Date updatedDate;
	private Long updatedByid;
	private String updatedByname;
	
	
	
	public DistOrderempProdgroup() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getDistId() {
		return distId;
	}
	public void setDistId(Long distId) {
		this.distId = distId;
	}
	public Long getEmpId() {
		return empId;
	}
	public void setEmpId(Long empId) {
		this.empId = empId;
	}
	public Long getDictItemId() {
		return dictItemId;
	}
	public void setDictItemId(Long dictItemId) {
		this.dictItemId = dictItemId;
	}
	public Date getEffectiveTime() {
		return effectiveTime;
	}
	public void setEffectiveTime(Date effectiveTime) {
		this.effectiveTime = effectiveTime;
	}
	public Date getExpiryTime() {
		return expiryTime;
	}
	public void setExpiryTime(Date expiryTime) {
		this.expiryTime = expiryTime;
	}
	public String getFactoryDelivery() {
		return factoryDelivery;
	}
	public void setFactoryDelivery(String factoryDelivery) {
		this.factoryDelivery = factoryDelivery;
	}
	public String getMemo1() {
		return memo1;
	}
	public void setMemo1(String memo1) {
		this.memo1 = memo1;
	}
	public String getMemo2() {
		return memo2;
	}
	public void setMemo2(String memo2) {
		this.memo2 = memo2;
	}
	public String getMemo3() {
		return memo3;
	}
	public void setMemo3(String memo3) {
		this.memo3 = memo3;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Long getCreatedByid() {
		return createdByid;
	}
	public void setCreatedByid(Long createdByid) {
		this.createdByid = createdByid;
	}
	public String getCreatedByname() {
		return createdByname;
	}
	public void setCreatedByname(String createdByname) {
		this.createdByname = createdByname;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public Long getUpdatedByid() {
		return updatedByid;
	}
	public void setUpdatedByid(Long updatedByid) {
		this.updatedByid = updatedByid;
	}
	public String getUpdatedByname() {
		return updatedByname;
	}
	public void setUpdatedByname(String updatedByname) {
		this.updatedByname = updatedByname;
	}
	
	
	 
}
