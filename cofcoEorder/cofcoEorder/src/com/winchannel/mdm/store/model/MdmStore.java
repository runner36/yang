package com.winchannel.mdm.store.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.winchannel.base.model.BaseDictItem;
import com.winchannel.base.model.BaseOrg;
import com.winchannel.mdm.distributor.model.MdmDistributor;
import com.winchannel.mdm.store.bean.StoreOrgCheck;

/**
 * MdmStore entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MdmStore implements java.io.Serializable {

	// Fields

	private Long storeId;
	private BaseDictItem storeChannel;
	private BaseDictItem storeOther;
	private BaseDictItem storeGeo;
	private MdmStore mdmStore;
	private BaseOrg baseOrg;
	private MdmDistributor mdmDistributor;
	private BaseDictItem storeCorp;
	private BaseDictItem storeNature;
	private BaseDictItem storeType;
	private String storeName;
	private String storeAlias;
	private String storeCode;
	private String extCode;
	private String subCode;
	private Long levelCode;
	private String storeAddr;
	private String linkman;
	private String linktel;
	private String memo1;
	private String memo2;
	private String memo3;
	private String memo4;
	private String memo5;
	private String memo6;
	private String remark;
	private String state;
	private Long sort;
	private String createdBy;
	private Date created;
	private String updatedBy;
	private Date updated;

	private Date firstItemDate;
	private Date lastItemDate;
	private Long storeLevel;
	private String storeRelationState;

	private Set mdmStores = new HashSet(0);

	private StoreOrgCheck storeOrgCheck;
	private String zipCode;

	// Constructors

	/** default constructor */
	public MdmStore() {
	}

	/** minimal constructor */
	public MdmStore(Long storeId) {
		this.storeId = storeId;
	}

	// Property accessors

	public Long getStoreId() {
		return this.storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public MdmStore getMdmStore() {
		return mdmStore;
	}

	public void setMdmStore(MdmStore mdmStore) {
		this.mdmStore = mdmStore;
	}

	public BaseOrg getBaseOrg() {
		return baseOrg;
	}

	public void setBaseOrg(BaseOrg baseOrg) {
		this.baseOrg = baseOrg;
	}

	public MdmDistributor getMdmDistributor() {
		return mdmDistributor;
	}

	public void setMdmDistributor(MdmDistributor mdmDistributor) {
		this.mdmDistributor = mdmDistributor;
	}

	public BaseDictItem getStoreChannel() {
		return storeChannel;
	}

	public void setStoreChannel(BaseDictItem storeChannel) {
		this.storeChannel = storeChannel;
	}

	public BaseDictItem getStoreOther() {
		return storeOther;
	}

	public void setStoreOther(BaseDictItem storeOther) {
		this.storeOther = storeOther;
	}

	public BaseDictItem getStoreGeo() {
		return storeGeo;
	}

	public void setStoreGeo(BaseDictItem storeGeo) {
		this.storeGeo = storeGeo;
	}

	public BaseDictItem getStoreCorp() {
		return storeCorp;
	}

	public void setStoreCorp(BaseDictItem storeCorp) {
		this.storeCorp = storeCorp;
	}

	public BaseDictItem getStoreNature() {
		return storeNature;
	}

	public void setStoreNature(BaseDictItem storeNature) {
		this.storeNature = storeNature;
	}

	public BaseDictItem getStoreType() {
		return storeType;
	}

	public void setStoreType(BaseDictItem storeType) {
		this.storeType = storeType;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStoreAlias() {
		return storeAlias;
	}

	public void setStoreAlias(String storeAlias) {
		this.storeAlias = storeAlias;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getExtCode() {
		return extCode;
	}

	public void setExtCode(String extCode) {
		this.extCode = extCode;
	}

	public String getSubCode() {
		return subCode;
	}

	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}

	public Long getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(Long levelCode) {
		this.levelCode = levelCode;
	}

	public String getStoreAddr() {
		return storeAddr;
	}

	public void setStoreAddr(String storeAddr) {
		this.storeAddr = storeAddr;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getLinktel() {
		return linktel;
	}

	public void setLinktel(String linktel) {
		this.linktel = linktel;
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

	public String getMemo4() {
		return memo4;
	}

	public void setMemo4(String memo4) {
		this.memo4 = memo4;
	}

	public String getMemo5() {
		return memo5;
	}

	public void setMemo5(String memo5) {
		this.memo5 = memo5;
	}

	public String getMemo6() {
		return memo6;
	}

	public void setMemo6(String memo6) {
		this.memo6 = memo6;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public Set getMdmStores() {
		return mdmStores;
	}

	public void setMdmStores(Set mdmStores) {
		this.mdmStores = mdmStores;
	}

	public Date getFirstItemDate() {
		return firstItemDate;
	}

	public void setFirstItemDate(Date firstItemDate) {
		this.firstItemDate = firstItemDate;
	}

	public Date getLastItemDate() {
		return lastItemDate;
	}

	public void setLastItemDate(Date lastItemDate) {
		this.lastItemDate = lastItemDate;
	}

	public Long getStoreLevel() {
		return storeLevel;
	}

	public void setStoreLevel(Long storeLevel) {
		this.storeLevel = storeLevel;
	}

	public String getStoreRelationState() {
		return storeRelationState;
	}

	public void setStoreRelationState(String storeRelationState) {
		this.storeRelationState = storeRelationState;
	}

	public StoreOrgCheck getStoreOrgCheck() {
		return storeOrgCheck;
	}

	public void setStoreOrgCheck(StoreOrgCheck storeOrgCheck) {
		this.storeOrgCheck = storeOrgCheck;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

}