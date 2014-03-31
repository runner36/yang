package com.winchannel.mdm.product.model;

import java.util.Date;

import com.winchannel.base.model.BaseDictItem;

/**
 * MdmProduct entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MdmProduct implements java.io.Serializable {

	// Fields

	private Long prodId;
	private BaseDictItem itemBrand;
	private BaseDictItem itemOther;
	private BaseDictItem itemType;
	private BaseDictItem itemPack;
	private BaseDictItem prodVolehUnit;
	private BaseDictItem prodSTRU;

	private String prodName;
	private String prodAlias;
	private String prodNameEn;
	private String prodCode;
	private String extCode;
	private String prodBarcode;
	private String prodPcBarcode;
	private String prodUnit;
	private String prodSpec;
	private Double prodPrice;
	private Double prodWeight;
	private Double prodMinWeight;
	private Double prodContent;
	private Double prodMinContent;
	private Double prodVolum;
	private String prodType;
	private BaseDictItem memo1;
	private BaseDictItem memo2;
	private BaseDictItem memo3;
	private BaseDictItem memo4;
	private BaseDictItem memo5;
	private BaseDictItem memo6;
	private String remark;
	private String state;
	private Long sort;
	private String createdBy;
	private Date created;
	private String updatedBy;
	private Date updated;
	private BaseDictItem prodBaseUnit;
	private BaseDictItem prodCountUnit;
	private BaseDictItem prodPriceUnit;
	private Long parentProdId;
	//产品类型
	private BaseDictItem dictProdType;
	private BaseDictItem prodweightUnit;

	// Constructors

	/** default constructor */
	public MdmProduct() {
	}

	/** minimal constructor */
	public MdmProduct(Long prodId) {
		this.prodId = prodId;
	}

	// Property accessors

	public Long getProdId() {
		return this.prodId;
	}

	public void setProdId(Long prodId) {
		this.prodId = prodId;
	}

	public BaseDictItem getProdVolehUnit() {
		return prodVolehUnit;
	}

	public void setProdVolehUnit(BaseDictItem prodVolehUnit) {
		this.prodVolehUnit = prodVolehUnit;
	}

	public Double getProdVolum() {
		return prodVolum;
	}

	public void setProdVolum(Double prodVolum) {
		this.prodVolum = prodVolum;
	}

	public String getProdName() {
		return this.prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName.replace("\"", "").replace("\'", "");
	}

	public String getProdAlias() {
		return this.prodAlias;
	}

	public void setProdAlias(String prodAlias) {
		this.prodAlias = prodAlias;
	}

	public String getProdNameEn() {
		return this.prodNameEn;
	}

	public void setProdNameEn(String prodNameEn) {
		this.prodNameEn = prodNameEn;
	}

	public String getProdCode() {
		return this.prodCode;
	}

	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}

	public String getExtCode() {
		return this.extCode;
	}

	public void setExtCode(String extCode) {
		this.extCode = extCode;
	}

	public String getProdBarcode() {
		return this.prodBarcode;
	}

	public void setProdBarcode(String prodBarcode) {
		this.prodBarcode = prodBarcode;
	}

	public String getProdPcBarcode() {
		return this.prodPcBarcode;
	}

	public void setProdPcBarcode(String prodPcBarcode) {
		this.prodPcBarcode = prodPcBarcode;
	}

	public String getProdUnit() {
		return this.prodUnit;
	}

	public void setProdUnit(String prodUnit) {
		this.prodUnit = prodUnit;
	}

	public String getProdSpec() {
		return this.prodSpec;
	}

	public void setProdSpec(String prodSpec) {
		this.prodSpec = prodSpec;
	}

	public Double getProdPrice() {
		return this.prodPrice;
	}

	public void setProdPrice(Double prodPrice) {
		this.prodPrice = prodPrice;
	}

	public Double getProdWeight() {
		return this.prodWeight;
	}

	public void setProdWeight(Double prodWeight) {
		this.prodWeight = prodWeight;
	}

	public Double getProdMinWeight() {
		return this.prodMinWeight;
	}

	public void setProdMinWeight(Double prodMinWeight) {
		this.prodMinWeight = prodMinWeight;
	}

	public Double getProdContent() {
		return this.prodContent;
	}

	public void setProdContent(Double prodContent) {
		this.prodContent = prodContent;
	}

	public Double getProdMinContent() {
		return this.prodMinContent;
	}

	public void setProdMinContent(Double prodMinContent) {
		this.prodMinContent = prodMinContent;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Long getSort() {
		return this.sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdated() {
		return this.updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public BaseDictItem getItemBrand() {
		return itemBrand;
	}

	public void setItemBrand(BaseDictItem itemBrand) {
		this.itemBrand = itemBrand;
	}

	public BaseDictItem getItemOther() {
		return itemOther;
	}

	public void setItemOther(BaseDictItem itemOther) {
		this.itemOther = itemOther;
	}

	public BaseDictItem getItemType() {
		return itemType;
	}

	public void setItemType(BaseDictItem itemType) {
		this.itemType = itemType;
	}

	public BaseDictItem getItemPack() {
		return itemPack;
	}

	public void setItemPack(BaseDictItem itemPack) {
		this.itemPack = itemPack;
	}

	public BaseDictItem getProdSTRU() {
		return prodSTRU;
	}

	public void setProdSTRU(BaseDictItem prodSTRU) {
		this.prodSTRU = prodSTRU;
	}

	public String getProdType() {
		return prodType;
	}

	public void setProdType(String prodType) {
		this.prodType = prodType;
	}

	public BaseDictItem getProdBaseUnit() {
		return prodBaseUnit;
	}

	public void setProdBaseUnit(BaseDictItem prodBaseUnit) {
		this.prodBaseUnit = prodBaseUnit;
	}

	public Long getParentProdId() {
		return parentProdId;
	}

	public void setParentProdId(Long parentProdId) {
		this.parentProdId = parentProdId;
	}

	public BaseDictItem getProdCountUnit() {
		return prodCountUnit;
	}

	public void setProdCountUnit(BaseDictItem prodCountUnit) {
		this.prodCountUnit = prodCountUnit;
	}

	public BaseDictItem getProdPriceUnit() {
		return prodPriceUnit;
	}

	public void setProdPriceUnit(BaseDictItem prodPriceUnit) {
		this.prodPriceUnit = prodPriceUnit;
	}

	public BaseDictItem getDictProdType() {
		return dictProdType;
	}

	public void setDictProdType(BaseDictItem dictProdType) {
		this.dictProdType = dictProdType;
	}

	public BaseDictItem getProdweightUnit() {
		return prodweightUnit;
	}

	public void setProdweightUnit(BaseDictItem prodweightUnit) {
		this.prodweightUnit = prodweightUnit;
	}

	public BaseDictItem getMemo1() {
		return memo1;
	}

	public void setMemo1(BaseDictItem memo1) {
		this.memo1 = memo1;
	}

	public BaseDictItem getMemo2() {
		return memo2;
	}

	public void setMemo2(BaseDictItem memo2) {
		this.memo2 = memo2;
	}

	public BaseDictItem getMemo3() {
		return memo3;
	}

	public void setMemo3(BaseDictItem memo3) {
		this.memo3 = memo3;
	}

	public BaseDictItem getMemo4() {
		return memo4;
	}

	public void setMemo4(BaseDictItem memo4) {
		this.memo4 = memo4;
	}

	public BaseDictItem getMemo5() {
		return memo5;
	}

	public void setMemo5(BaseDictItem memo5) {
		this.memo5 = memo5;
	}

	public BaseDictItem getMemo6() {
		return memo6;
	}

	public void setMemo6(BaseDictItem memo6) {
		this.memo6 = memo6;
	}
	
	
}