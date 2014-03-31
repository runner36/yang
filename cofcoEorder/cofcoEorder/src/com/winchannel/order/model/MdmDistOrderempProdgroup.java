package com.winchannel.order.model;

import java.util.Date;

import com.winchannel.base.model.BaseDictItem;
import com.winchannel.base.model.BaseEmployee;
import com.winchannel.base.model.BaseOrg;
import com.winchannel.mdm.distributor.model.MdmDistributor;

/**
 * MdmDistOrderempProdgroup entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MdmDistOrderempProdgroup implements java.io.Serializable {

	// Fields

	private Long id;
	private BaseOrg baseOrg;
	private BaseDictItem baseDictItem;
	private BaseEmployee baseEmployee;
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

	// Constructors

	/** default constructor */
	public MdmDistOrderempProdgroup() {
	}

	/** minimal constructor */
	public MdmDistOrderempProdgroup(BaseOrg baseOrg,
			BaseDictItem baseDictItem, BaseEmployee baseEmployee,
			Date effectiveTime, Date createdDate, Long createdByid,
			String createdByname) {
		this.baseOrg = baseOrg;
		this.baseDictItem = baseDictItem;
		this.baseEmployee = baseEmployee;
		this.effectiveTime = effectiveTime;
		this.createdDate = createdDate;
		this.createdByid = createdByid;
		this.createdByname = createdByname;
	}

	/** full constructor */
	public MdmDistOrderempProdgroup(BaseOrg baseOrg,
			BaseDictItem baseDictItem, BaseEmployee baseEmployee,
			Date effectiveTime, Date expiryTime, String factoryDelivery,
			String memo1, String memo2, String memo3, Date createdDate,
			Long createdByid, String createdByname, Date updatedDate,
			Long updatedByid, String updatedByname) {
		this.baseOrg = baseOrg;
		this.baseDictItem = baseDictItem;
		this.baseEmployee = baseEmployee;
		this.effectiveTime = effectiveTime;
		this.expiryTime = expiryTime;
		this.factoryDelivery = factoryDelivery;
		this.memo1 = memo1;
		this.memo2 = memo2;
		this.memo3 = memo3;
		this.createdDate = createdDate;
		this.createdByid = createdByid;
		this.createdByname = createdByname;
		this.updatedDate = updatedDate;
		this.updatedByid = updatedByid;
		this.updatedByname = updatedByname;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public BaseDictItem getBaseDictItem() {
		return this.baseDictItem;
	}

	public void setBaseDictItem(BaseDictItem baseDictItem) {
		this.baseDictItem = baseDictItem;
	}

	public BaseEmployee getBaseEmployee() {
		return this.baseEmployee;
	}

	public void setBaseEmployee(BaseEmployee baseEmployee) {
		this.baseEmployee = baseEmployee;
	}

	public Date getEffectiveTime() {
		return this.effectiveTime;
	}

	public void setEffectiveTime(Date effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public Date getExpiryTime() {
		return this.expiryTime;
	}

	public void setExpiryTime(Date expiryTime) {
		this.expiryTime = expiryTime;
	}

	public String getFactoryDelivery() {
		return this.factoryDelivery;
	}

	public void setFactoryDelivery(String factoryDelivery) {
		this.factoryDelivery = factoryDelivery;
	}

	public String getMemo1() {
		return this.memo1;
	}

	public void setMemo1(String memo1) {
		this.memo1 = memo1;
	}

	public String getMemo2() {
		return this.memo2;
	}

	public void setMemo2(String memo2) {
		this.memo2 = memo2;
	}

	public String getMemo3() {
		return this.memo3;
	}

	public void setMemo3(String memo3) {
		this.memo3 = memo3;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getCreatedByid() {
		return this.createdByid;
	}

	public void setCreatedByid(Long createdByid) {
		this.createdByid = createdByid;
	}

	public String getCreatedByname() {
		return this.createdByname;
	}

	public void setCreatedByname(String createdByname) {
		this.createdByname = createdByname;
	}

	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getUpdatedByid() {
		return this.updatedByid;
	}

	public void setUpdatedByid(Long updatedByid) {
		this.updatedByid = updatedByid;
	}

	public String getUpdatedByname() {
		return this.updatedByname;
	}

	public void setUpdatedByname(String updatedByname) {
		this.updatedByname = updatedByname;
	}

	public BaseOrg getBaseOrg() {
		return baseOrg;
	}

	public void setBaseOrg(BaseOrg baseOrg) {
		this.baseOrg = baseOrg;
	}
	

}