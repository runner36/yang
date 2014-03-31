package com.winchannel.order.model;

import java.math.BigDecimal;

import com.winchannel.mdm.product.model.MdmProduct;

/**
 * OrderItem entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class OrderItem implements java.io.Serializable {

	// Fields

	private Long id;
	private OrderInfo orderInfo;
	private MdmProduct mdmProduct;
	private Double quantity;
	private Double taxPrice;
	private BigDecimal amount;
	private String memo;
	private String unit;
	private String memoOrder;

	// Constructors

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getMemoOrder() {
		return memoOrder;
	}

	public void setMemoOrder(String memoOrder) {
		this.memoOrder = memoOrder;
	}

	/** default constructor */
	public OrderItem() {
	}

	/** minimal constructor */
	public OrderItem(Double quantity, Double taxPrice, BigDecimal amount) {
		this.quantity = quantity;
		this.taxPrice = taxPrice;
		this.amount = amount;
	}

	/** full constructor */
	public OrderItem(OrderInfo orderInfo, MdmProduct mdmProduct,
			Double quantity, Double taxPrice, BigDecimal amount, String memo) {
		this.orderInfo = orderInfo;
		this.mdmProduct = mdmProduct;
		this.quantity = quantity;
		this.taxPrice = taxPrice;
		this.amount = amount;
		this.memo = memo;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OrderInfo getOrderInfo() {
		return this.orderInfo;
	}

	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}

	public MdmProduct getMdmProduct() {
		return this.mdmProduct;
	}

	public void setMdmProduct(MdmProduct mdmProduct) {
		this.mdmProduct = mdmProduct;
	}

	public Double getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getTaxPrice() {
		return this.taxPrice;
	}

	public void setTaxPrice(Double taxPrice) {
		this.taxPrice = taxPrice;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}