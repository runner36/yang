package com.winchannel.order.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.winchannel.base.model.BaseDictItem;
import com.winchannel.base.model.BaseEmployee;
import com.winchannel.base.model.BaseUser;
import com.winchannel.mdm.distributor.model.MdmDistributor;


/**
 * MdmStore entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class OrderAgreementLog implements java.io.Serializable {  
    private Long id;
    private MdmDistributor mdmDistributor;
    private BaseUser baseUser;
    private Integer status;
    private BaseEmployee baseEmployee;
    private Date createDate;
    private String createdByName;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public MdmDistributor getMdmDistributor() {
		return mdmDistributor;
	}
	public void setMdmDistributor(MdmDistributor mdmDistributor) {
		this.mdmDistributor = mdmDistributor;
	}
	public BaseUser getBaseUser() {
		return baseUser;
	}
	public void setBaseUser(BaseUser baseUser) {
		this.baseUser = baseUser;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public BaseEmployee getBaseEmployee() {
		return baseEmployee;
	}
	public void setBaseEmployee(BaseEmployee baseEmployee) {
		this.baseEmployee = baseEmployee;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCreatedByName() {
		return createdByName;
	}
	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

    
}