package com.winchannel.order.web;

public class OrderItemVO {
	private Long id;
    private String prodCode;
    private String prodName;
    private String prodNum;
    private String prodPrice;
    private String prodAmount;
    private String prodMemo;
    private String prodUnit;
    private String memoOrder;
    
    public String getMemoOrder() {
		return memoOrder;
	}
	public void setMemoOrder(String memoOrder) {
		this.memoOrder = memoOrder;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
    public String getProdUnit() {
		return prodUnit;
	}
	public void setProdUnit(String prodUnit) {
		this.prodUnit = prodUnit;
	}
	public String getProdCode() {
        return prodCode;
    }
    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }
    public String getProdName() {
        return prodName;
    }
    public void setProdName(String prodName) {
        this.prodName = prodName;
    }
    public String getProdNum() {
        return prodNum;
    }
    public void setProdNum(String prodNum) {
        this.prodNum = prodNum;
    }
    public String getProdPrice() {
        return prodPrice;
    }
    public void setProdPrice(String prodPrice) {
        this.prodPrice = prodPrice;
    }
    public String getProdAmount() {
        return prodAmount;
    }
    public void setProdAmount(String prodAmount) {
        this.prodAmount = prodAmount;
    }
    public String getProdMemo() {
        return prodMemo;
    }
    public void setProdMemo(String prodMemo) {
        this.prodMemo = prodMemo;
    }
    
}
