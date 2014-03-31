package com.winchannel.mdm.conversion.model;

import com.winchannel.base.model.BaseDictItem;

public class MdmUnitConversion implements java.io.Serializable{
private Long convId;
private String prodCode;
private String remark;
private BaseDictItem convUnit1Id;
private Long convUnit1Val;
private BaseDictItem convUnit2Id;
private double convUnit2Val;
public Long getConvId() {
	return convId;
}
public void setConvId(Long convId) {
	this.convId = convId;
}
public String getProdCode() {
	return prodCode;
}
public void setProdCode(String prodCode) {
	this.prodCode = prodCode;
}
public String getRemark() {
	return remark;
}
public void setRemark(String remark) {
	this.remark = remark;
}

public Long getConvUnit1Val() {
	return convUnit1Val;
}
public void setConvUnit1Val(Long convUnit1Val) {
	this.convUnit1Val = convUnit1Val;
}
public double getConvUnit2Val() {
	return convUnit2Val;
}
public void setConvUnit2Val(double convUnit2Val) {
	this.convUnit2Val = convUnit2Val;
}
public BaseDictItem getConvUnit1Id() {
	return convUnit1Id;
}
public void setConvUnit1Id(BaseDictItem convUnit1Id) {
	this.convUnit1Id = convUnit1Id;
}
public BaseDictItem getConvUnit2Id() {
	return convUnit2Id;
}
public void setConvUnit2Id(BaseDictItem convUnit2Id) {
	this.convUnit2Id = convUnit2Id;
}


}
