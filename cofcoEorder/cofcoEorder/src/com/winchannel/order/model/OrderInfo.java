package com.winchannel.order.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.winchannel.base.model.BaseDictItem;
import com.winchannel.base.model.BaseEmployee;
import com.winchannel.mdm.distributor.model.MdmDistributor;


/**
 * MdmStore entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class OrderInfo implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long orderId;
    private MdmDistributor mdmDistributor;
    private MdmDistributorAddress distributorAddress;
    private MdmDistributorLinkman mdmDistributorLinkman;
    private String orderCode;
    private Date orderCreateDate;
    private Date orderDate;
    private Date shiptoDate;
    private Date orderApproveDate;
    private String memo;
    private String memoOrder;
    private String memoApp;
//    private String shiptoAdd;
    private Integer status;
    private List<OrderItem> orderItems = new ArrayList<OrderItem>();
    private BaseDictItem invoiceType; //开票类型
    private BaseDictItem paymentType; //付款方式
    private String paymentName;   
    private BaseDictItem modeOfTransportType;//运输方式
    private String modeOfTransportName;   
    private BigDecimal quantity; //订单总数量
    private BigDecimal amount;//订单总金额
    private String invoiceName;       //开票客户名称
    private String custCode;
    private String custName;
    private String custLinkman;
    private String custTell;
    private String shiptoCode;
    private String shiptoName;
    private String shiptoTell;
    private String shiptoLinkman;
    private String industryName;
    private String industryMobile;
    private BaseDictItem prodGroup;
    private BaseEmployee industryEmp;
	
    public BigDecimal getQuantity() {
	return quantity;
    }
    public void setQuantity(BigDecimal quantity) {
	this.quantity = quantity;
    }
    public BigDecimal getAmount() {
	return amount;
    }
    public void setAmount(BigDecimal amount) {
	this.amount = amount;
    }
    
    public BaseDictItem getPaymentType() {
	return paymentType;
    }
    public void setPaymentType(BaseDictItem paymentType) {
	this.paymentType = paymentType;
    }
    public String getPaymentName() {
	return paymentName;
    }
    public void setPaymentName(String paymentName) {
	this.paymentName = paymentName;
    }
    public BaseDictItem getModeOfTransportType() {
	    return modeOfTransportType;
    }
    public void setModeOfTransportType(BaseDictItem modeOfTransportType) {
	this.modeOfTransportType = modeOfTransportType;
    }
    public String getModeOfTransportName() {
	return modeOfTransportName;
    }
    public void setModeOfTransportName(String modeOfTransportName) {
	this.modeOfTransportName = modeOfTransportName;
    }
    
    public Long getOrderId() {
        return orderId;
    }
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
   
    public MdmDistributor getMdmDistributor() {
        return mdmDistributor;
    }
    public void setMdmDistributor(MdmDistributor mdmDistributor) {
        this.mdmDistributor = mdmDistributor;
    }
    public MdmDistributorAddress getDistributorAddress() {
        return distributorAddress;
    }
    public void setDistributorAddress(MdmDistributorAddress distributorAddress) {
        this.distributorAddress = distributorAddress;
    }
    public MdmDistributorLinkman getMdmDistributorLinkman() {
        return mdmDistributorLinkman;
    }
    public void setMdmDistributorLinkman(MdmDistributorLinkman mdmDistributorLinkman) {
        this.mdmDistributorLinkman = mdmDistributorLinkman;
    }
    public String getOrderCode() {
        return orderCode;
    }
    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
    public Date getOrderCreateDate() {
        return orderCreateDate;
    }
    public void setOrderCreateDate(Date orderCreateDate) {
        this.orderCreateDate = orderCreateDate;
    }
    public Date getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
    public Date getShiptoDate() {
        return shiptoDate;
    }
    public void setShiptoDate(Date shiptoDate) {
        this.shiptoDate = shiptoDate;
    }
    public Date getOrderApproveDate() {
        return orderApproveDate;
    }
    public void setOrderApproveDate(Date orderApproveDate) {
        this.orderApproveDate = orderApproveDate;
    }
    public String getInvoiceName() {
        return invoiceName;
    }
    public void setInvoiceName(String invoiceName) {
        this.invoiceName = invoiceName;
    }
    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
    
    public BaseDictItem getInvoiceType() {
        return invoiceType;
    }
    public void setInvoiceType(BaseDictItem invoiceType) {
        this.invoiceType = invoiceType;
    }
    public String getCustCode() {
        return custCode;
    }
    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }
    public String getCustName() {
        return custName;
    }
    public void setCustName(String custName) {
        this.custName = custName;
    }
    public String getCustLinkman() {
        return custLinkman;
    }
    public void setCustLinkman(String custLinkman) {
        this.custLinkman = custLinkman;
    }
    public String getCustTell() {
        return custTell;
    }
    public void setCustTell(String custTell) {
        this.custTell = custTell;
    }
    public String getShiptoCode() {
        return shiptoCode;
    }
    public void setShiptoCode(String shiptoCode) {
        this.shiptoCode = shiptoCode;
    }
    public String getShiptoName() {
        return shiptoName;
    }
    public void setShiptoName(String shiptoName) {
        this.shiptoName = shiptoName;
    }
    public String getShiptoLinkman() {
        return shiptoLinkman;
    }
    public void setShiptoLinkman(String shiptoLinkman) {
        this.shiptoLinkman = shiptoLinkman;
    }
    public String getShiptoTell() {
        return shiptoTell;
    }
    public void setShiptoTell(String shiptoTell) {
        this.shiptoTell = shiptoTell;
    }
    public String getIndustryName() {
        return industryName;
    }
    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }
    public String getIndustryMobile() {
        return industryMobile;
    }
    public void setIndustryMobile(String industryMobile) {
        this.industryMobile = industryMobile;
    }
    public BaseDictItem getProdGroup() {
        return prodGroup;
    }
    public void setProdGroup(BaseDictItem prodGroup) {
        this.prodGroup = prodGroup;
    }
    public BaseEmployee getIndustryEmp() {
        return industryEmp;
    }
    public void setIndustryEmp(BaseEmployee industryEmp) {
        this.industryEmp = industryEmp;
    }
    public String getMemoOrder() {
    	return memoOrder;
    }
    public void setMemoOrder(String memoOrder) {
    	this.memoOrder = memoOrder;
    }
	public String getMemoApp() {
		return memoApp;
	}
	public void setMemoApp(String memoApp) {
		this.memoApp = memoApp;
	}
    
}