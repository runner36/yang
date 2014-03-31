package com.winchannel.order.model;

import java.util.Date;

/**
 * OrderSap entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class OrderSap implements java.io.Serializable {

	// Fields

	private Long id;
	private String orderCode;//客户意向单号
	private Date orderDate;//订单日期
	private String custWillCode;//订单号
	private String custName;//客户名称
	private String custCode;//客户编码
	private String dateFreeze;//帐期冻结
	private String quotaFreeze;//帐额冻结
	private String priceFreeze;//价格冻结
	private String childClass;//子品类
	private String skuName;//物料名称
	private String skuCode;//物料号
	private Double orderQua;//客户订货数量
	private Double stockQua;//库存满足数量
	private String stockName;//库存名称
	private Double stockNoQua;//库存未满足数量
	private Double planGoodsQua;//按箱计交货数量
	private Double noGoodsQua;//按箱计未交货数量
	private Double factGoodsQua;//按箱计实际交货数量
	private Double noAccountQua;//未过账数量
	private Double closeQua;//关闭数量
	private Double sysBillQua;//按箱计开票数量
	private Double price;//单价
	private String innnerOrderCode;//内部订单号
	private String saleOrg;//销售地区
	private String province;//省
	private Integer status;//状态
	private Date createdDate;
	private Long createdByid;
	private String createdByname;
	private Date updatedDate;
	private Long updatedByid;
	private String updatedByname;
	private Double sapOrderQua;//SAP订单数量
	private Double confirmQua;//确认数量
	private Double noConfirmQua;//按箱计未确认数量
	private String refuseMemo;//拒绝原因描述
	private String useMemo;//使用原因描述
	
	private String prodBigClass;
	private String factoryName;
	private Double khyqsl;
	private Double xydjsl;
	private Double jgdjsl;
	private Double nfkimgC;
	private Date dataDownLoadDate;

	// Constructors

	/** default constructor */
	public OrderSap() {
	}


	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderCode() {
		return this.orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Date getOrderDate() {
		return this.orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getCustWillCode() {
		return this.custWillCode;
	}

	public void setCustWillCode(String custWillCode) {
		this.custWillCode = custWillCode;
	}

	public String getCustName() {
		return this.custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustCode() {
		return this.custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public String getDateFreeze() {
		return this.dateFreeze;
	}

	public void setDateFreeze(String dateFreeze) {
		this.dateFreeze = dateFreeze;
	}

	public String getQuotaFreeze() {
		return this.quotaFreeze;
	}

	public void setQuotaFreeze(String quotaFreeze) {
		this.quotaFreeze = quotaFreeze;
	}

	public String getPriceFreeze() {
		return this.priceFreeze;
	}

	public void setPriceFreeze(String priceFreeze) {
		this.priceFreeze = priceFreeze;
	}

	public String getChildClass() {
		return this.childClass;
	}

	public void setChildClass(String childClass) {
		this.childClass = childClass;
	}

	public String getSkuName() {
		return this.skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public String getSkuCode() {
		return this.skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public Double getOrderQua() {
		return this.orderQua;
	}

	public void setOrderQua(Double orderQua) {
		this.orderQua = orderQua;
	}

	public Double getStockQua() {
		return this.stockQua;
	}

	public void setStockQua(Double stockQua) {
		this.stockQua = stockQua;
	}

	public String getStockName() {
		return this.stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public Double getStockNoQua() {
		return this.stockNoQua;
	}

	public void setStockNoQua(Double stockNoQua) {
		this.stockNoQua = stockNoQua;
	}

	public Double getPlanGoodsQua() {
		return this.planGoodsQua;
	}

	public void setPlanGoodsQua(Double planGoodsQua) {
		this.planGoodsQua = planGoodsQua;
	}

	public Double getNoGoodsQua() {
		return this.noGoodsQua;
	}

	public void setNoGoodsQua(Double noGoodsQua) {
		this.noGoodsQua = noGoodsQua;
	}

	public Double getFactGoodsQua() {
		return this.factGoodsQua;
	}

	public void setFactGoodsQua(Double factGoodsQua) {
		this.factGoodsQua = factGoodsQua;
	}

	public Double getNoAccountQua() {
		return this.noAccountQua;
	}

	public void setNoAccountQua(Double noAccountQua) {
		this.noAccountQua = noAccountQua;
	}

	public Double getCloseQua() {
		return this.closeQua;
	}

	public void setCloseQua(Double closeQua) {
		this.closeQua = closeQua;
	}

	public Double getSysBillQua() {
		return this.sysBillQua;
	}

	public void setSysBillQua(Double sysBillQua) {
		this.sysBillQua = sysBillQua;
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getInnnerOrderCode() {
		return this.innnerOrderCode;
	}

	public void setInnnerOrderCode(String innnerOrderCode) {
		this.innnerOrderCode = innnerOrderCode;
	}

	public String getSaleOrg() {
		return this.saleOrg;
	}

	public void setSaleOrg(String saleOrg) {
		this.saleOrg = saleOrg;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Double getSapOrderQua() {
		return this.sapOrderQua;
	}

	public void setSapOrderQua(Double sapOrderQua) {
		this.sapOrderQua = sapOrderQua;
	}

	public Double getConfirmQua() {
		return this.confirmQua;
	}

	public void setConfirmQua(Double confirmQua) {
		this.confirmQua = confirmQua;
	}

	public Double getNoConfirmQua() {
		return this.noConfirmQua;
	}

	public void setNoConfirmQua(Double noConfirmQua) {
		this.noConfirmQua = noConfirmQua;
	}

	public String getRefuseMemo() {
		return this.refuseMemo;
	}

	public void setRefuseMemo(String refuseMemo) {
		this.refuseMemo = refuseMemo;
	}


	public String getUseMemo() {
		return useMemo;
	}


	public void setUseMemo(String useMemo) {
		this.useMemo = useMemo;
	}


	public String getProdBigClass() {
		return prodBigClass;
	}


	public void setProdBigClass(String prodBigClass) {
		this.prodBigClass = prodBigClass;
	}


	public String getFactoryName() {
		return factoryName;
	}


	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}


	public Double getKhyqsl() {
		return khyqsl;
	}


	public void setKhyqsl(Double khyqsl) {
		this.khyqsl = khyqsl;
	}


	public Double getXydjsl() {
		return xydjsl;
	}


	public void setXydjsl(Double xydjsl) {
		this.xydjsl = xydjsl;
	}


	public Double getJgdjsl() {
		return jgdjsl;
	}


	public void setJgdjsl(Double jgdjsl) {
		this.jgdjsl = jgdjsl;
	}


	public Double getNfkimgC() {
		return nfkimgC;
	}


	public void setNfkimgC(Double nfkimgC) {
		this.nfkimgC = nfkimgC;
	}


	public Date getDataDownLoadDate() {
		return dataDownLoadDate;
	}


	public void setDataDownLoadDate(Date dataDownLoadDate) {
		this.dataDownLoadDate = dataDownLoadDate;
	}

}