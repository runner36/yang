package com.winchannel.order.model;

import java.math.BigDecimal;
import java.util.Date;

import com.winchannel.base.model.BaseDictItem;
import com.winchannel.mdm.product.model.MdmProduct;

/**
 * OrderItem entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MdmProdGroupDeliveryDays implements java.io.Serializable {

	// Fields

	private Long id;
	private BaseDictItem itemProdStru;
	private Long deliveryDays;
	private String createdBy;
	private Date created;
	private String updatedBy;
	private Date updated;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BaseDictItem getItemProdStru() {
		return itemProdStru;
	}
	public void setItemProdStru(BaseDictItem itemProdStru) {
		this.itemProdStru = itemProdStru;
	}
	public Long getDeliveryDays() {
		return deliveryDays;
	}
	public void setDeliveryDays(Long deliveryDays) {
		this.deliveryDays = deliveryDays;
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
	

}