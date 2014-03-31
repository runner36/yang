package com.winchannel.mdm.product.model;

public class MdmProductForOrder extends MdmProduct {
	private Long coutUintID;
	private String countUintName;
	private String brandCode;
	private Long prodSTRUId;
	
	public Long getProdSTRUId() {
		return prodSTRUId;
	}
	public void setProdSTRUId(Long prodSTRUId) {
		this.prodSTRUId = prodSTRUId;
	}
	public Long getCoutUintID() {
		return coutUintID;
	}
	public void setCoutUintID(Long coutUintID) {
		this.coutUintID = coutUintID;
	}
	public String getCountUintName() {
		return countUintName;
	}
	public void setCountUintName(String countUintName) {
		this.countUintName = countUintName;
	}
	public String getBrandCode() {
		return brandCode;
	}
	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}
	
	
}
