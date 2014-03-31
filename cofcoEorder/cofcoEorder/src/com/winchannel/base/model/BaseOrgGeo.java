package com.winchannel.base.model;

/**
 * BaseOrgGeo generated by MyEclipse Persistence Tools
 */

public class BaseOrgGeo implements java.io.Serializable {

	// Fields

	private Long orgGeoId;

	private BaseOrg baseOrg;

	private BaseDictItem baseDictItem;

	// Constructors

	/** default constructor */
	public BaseOrgGeo() {
	}

	/** full constructor */
	public BaseOrgGeo(BaseOrg baseOrg, BaseDictItem baseDictItem) {
		this.baseOrg = baseOrg;
		this.baseDictItem = baseDictItem;
	}

	// Property accessors

	public Long getOrgGeoId() {
		return this.orgGeoId;
	}

	public void setOrgGeoId(Long orgGeoId) {
		this.orgGeoId = orgGeoId;
	}

	public BaseOrg getBaseOrg() {
		return this.baseOrg;
	}

	public void setBaseOrg(BaseOrg baseOrg) {
		this.baseOrg = baseOrg;
	}

	public BaseDictItem getBaseDictItem() {
		return this.baseDictItem;
	}

	public void setBaseDictItem(BaseDictItem baseDictItem) {
		this.baseDictItem = baseDictItem;
	}

}