package com.winchannel.order.model;

import java.util.Date;

import com.winchannel.base.model.BaseDictItem;
import com.winchannel.mdm.distributor.model.MdmDistributor;

/**
 * MdmDistributorLinkman entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MdmDistributorLinkman implements java.io.Serializable {

	// Fields

	private Long id;
	private MdmDistributor mdmDistributor;
	private BaseDictItem baseDictItem;
	private Long status;
	private String linkmanName;
	private String linkmanPhonenum;
	private String linkmanTel;
	private Date createdDate;
	private Long createdByid;
	private String createdByname;
	private Date updatedDate;
	private Long updatedByid;
	private String updatedByname;
	private Long isSms;

	// Constructors

	/** default constructor */
	public MdmDistributorLinkman() {
	}

	/** minimal constructor */
	public MdmDistributorLinkman(MdmDistributor mdmDistributor,
			BaseDictItem baseDictItem, Long status, String linkmanName,
			String linkmanPhonenum, Date createdDate, Long createdByid,
			String createdByname) {
		this.mdmDistributor = mdmDistributor;
		this.baseDictItem = baseDictItem;
		this.status = status;
		this.linkmanName = linkmanName;
		this.linkmanPhonenum = linkmanPhonenum;
		this.createdDate = createdDate;
		this.createdByid = createdByid;
		this.createdByname = createdByname;
	}

	/** full constructor */
	public MdmDistributorLinkman(MdmDistributor mdmDistributor,
			BaseDictItem baseDictItem, Long status, String linkmanName,
			String linkmanPhonenum, String linkmanTel, Date createdDate,
			Long createdByid, String createdByname, Date updatedDate,
			Long updatedByid, String updatedByname, Long isSms) {
		this.mdmDistributor = mdmDistributor;
		this.baseDictItem = baseDictItem;
		this.status = status;
		this.linkmanName = linkmanName;
		this.linkmanPhonenum = linkmanPhonenum;
		this.linkmanTel = linkmanTel;
		this.createdDate = createdDate;
		this.createdByid = createdByid;
		this.createdByname = createdByname;
		this.updatedDate = updatedDate;
		this.updatedByid = updatedByid;
		this.updatedByname = updatedByname;
		this.isSms = isSms;
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

	public BaseDictItem getBaseDictItem() {
		return this.baseDictItem;
	}

	public void setBaseDictItem(BaseDictItem baseDictItem) {
		this.baseDictItem = baseDictItem;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getLinkmanName() {
		return this.linkmanName;
	}

	public void setLinkmanName(String linkmanName) {
		this.linkmanName = linkmanName;
	}

	public String getLinkmanPhonenum() {
		return this.linkmanPhonenum;
	}

	public void setLinkmanPhonenum(String linkmanPhonenum) {
		this.linkmanPhonenum = linkmanPhonenum;
	}

	public String getLinkmanTel() {
		return this.linkmanTel;
	}

	public void setLinkmanTel(String linkmanTel) {
		this.linkmanTel = linkmanTel;
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

	public Long getIsSms() {
		return this.isSms;
	}

	public void setIsSms(Long isSms) {
		this.isSms = isSms;
	}

}