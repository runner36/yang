package com.winchannel.order.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.winchannel.base.model.BaseDictItem;
import com.winchannel.mdm.distributor.model.MdmDistributor;

/**
 * MdmDistributorAddress entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MdmDistributorAddress implements java.io.Serializable {

	// Fields

	private Long id;
	private MdmDistributor mdmDistributor;
	private Long status;
	private String shiptoCode;
	private String shiptoName;
	private String shiptoAdd;
	private String contact;
	private String tel;
	private String mobile;
	private Date createdDate;
	private Long createdByid;
	private String createdByname;
	private Date updatedDate;
	private Long updatedByid;
	private String updatedByname;
//	private Set orderInfos = new HashSet(0);
	private BaseDictItem baseDictItem;
	private String factoryDelivery;

	// Constructors

	/** default constructor */
	public MdmDistributorAddress() {
	}

	/** minimal constructor */
	public MdmDistributorAddress(MdmDistributor mdmDistributor,
			String shiptoCode, String shiptoName, String shiptoAdd,
			Date createdDate, Long createdByid, String createdByname) {
		this.mdmDistributor = mdmDistributor;
		this.shiptoCode = shiptoCode;
		this.shiptoName = shiptoName;
		this.shiptoAdd = shiptoAdd;
		this.createdDate = createdDate;
		this.createdByid = createdByid;
		this.createdByname = createdByname;
	}

	/** full constructor */
	public MdmDistributorAddress(MdmDistributor mdmDistributor, Long status,
			String shiptoCode, String shiptoName, String shiptoAdd,
			String contact, String tel, String mobile, Date createdDate,
			Long createdByid, String createdByname, Date updatedDate,
			Long updatedByid, String updatedByname, Set orderInfos, String factoryDelivery) {
		this.mdmDistributor = mdmDistributor;
		this.status = status;
		this.shiptoCode = shiptoCode;
		this.shiptoName = shiptoName;
		this.shiptoAdd = shiptoAdd;
		this.contact = contact;
		this.tel = tel;
		this.mobile = mobile;
		this.createdDate = createdDate;
		this.createdByid = createdByid;
		this.createdByname = createdByname;
		this.updatedDate = updatedDate;
		this.updatedByid = updatedByid;
		this.updatedByname = updatedByname;
		this.factoryDelivery = factoryDelivery;
	//	this.orderInfos = orderInfos;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MdmDistributor getMdmDistributor() {
		return this.mdmDistributor;
	}

	public void setMdmDistributor(MdmDistributor mdmDistributor) {
		this.mdmDistributor = mdmDistributor;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getShiptoCode() {
		return this.shiptoCode;
	}

	public void setShiptoCode(String shiptoCode) {
		this.shiptoCode = shiptoCode;
	}

	public String getShiptoName() {
		return this.shiptoName;
	}

	public void setShiptoName(String shiptoName) {
		this.shiptoName = shiptoName;
	}

	public String getShiptoAdd() {
		return this.shiptoAdd;
	}

	public void setShiptoAdd(String shiptoAdd) {
		this.shiptoAdd = shiptoAdd;
	}

	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public BaseDictItem getBaseDictItem() {
		return baseDictItem;
	}

	public void setBaseDictItem(BaseDictItem baseDictItem) {
		this.baseDictItem = baseDictItem;
	}

	public String getFactoryDelivery() {
		return factoryDelivery;
	}

	public void setFactoryDelivery(String factoryDelivery) {
		this.factoryDelivery = factoryDelivery;
	}
	
	
//
//	public Set getOrderInfos() {
//		return this.orderInfos;
//	}
//
//	public void setOrderInfos(Set orderInfos) {
//		this.orderInfos = orderInfos;
//	}

}