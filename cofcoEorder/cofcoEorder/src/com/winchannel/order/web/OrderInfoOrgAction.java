package com.winchannel.order.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winchannel.base.model.BaseDictItem;
import com.winchannel.base.model.BaseEmployee;
import com.winchannel.base.model.BaseUser;
import com.winchannel.base.utils.Constants;
import com.winchannel.core.utils.ECPage;
import com.winchannel.core.utils.Page;
import com.winchannel.core.web.StrutsEntityAction;
import com.winchannel.mdm.distributor.model.MdmDistributor;
import com.winchannel.mdm.distributor.service.MdmDistributorManager;
import com.winchannel.mdm.product.service.MdmProductManager;
import com.winchannel.order.model.MdmDistEmpProdgroup;
import com.winchannel.order.model.MdmDistributorAddress;
import com.winchannel.order.model.MdmDistributorLinkman;
import com.winchannel.order.model.OrderInfo;
import com.winchannel.order.service.MdmDistEmpProdgroupManager;
import com.winchannel.order.service.MdmDistributorAddressManager;
import com.winchannel.order.service.OrderInfoManager;
import com.winchannel.order.service.OrderItemManager;

public class OrderInfoOrgAction extends
		StrutsEntityAction<OrderInfo, OrderInfoManager> {
	private OrderInfoManager orderInfoManager;
	private OrderItemManager orderItemManager;
	private MdmProductManager mdmProductManager;
	private MdmDistEmpProdgroupManager mdmDistEmpProdgroupManager;
	private MdmDistributorManager mdmDistributorManager;
	private MdmDistributorAddressManager mdmDistributorAddressManager;
	
	
	public MdmDistributorManager getMdmDistributorManager() {
		return mdmDistributorManager;
	}

	public void setMdmDistributorManager(MdmDistributorManager mdmDistributorManager) {
		this.mdmDistributorManager = mdmDistributorManager;
	}

	public MdmDistributorAddressManager getMdmDistributorAddressManager() {
		return mdmDistributorAddressManager;
	}

	public void setMdmDistributorAddressManager(
			MdmDistributorAddressManager mdmDistributorAddressManager) {
		this.mdmDistributorAddressManager = mdmDistributorAddressManager;
	}

	public OrderInfoManager getOrderInfoManager() {
		return orderInfoManager;
	}

	public void setOrderInfoManager(OrderInfoManager orderInfoManager) {
		this.orderInfoManager = orderInfoManager;
	}

	public OrderItemManager getOrderItemManager() {
		return orderItemManager;
	}

	public void setOrderItemManager(OrderItemManager orderItemManager) {
		this.orderItemManager = orderItemManager;
	}

	public MdmProductManager getMdmProductManager() {
		return mdmProductManager;
	}

	public void setMdmProductManager(MdmProductManager mdmProductManager) {
		this.mdmProductManager = mdmProductManager;
	}

	public MdmDistEmpProdgroupManager getMdmDistEmpProdgroupManager() {
		return mdmDistEmpProdgroupManager;
	}

	public void setMdmDistEmpProdgroupManager(
			MdmDistEmpProdgroupManager mdmDistEmpProdgroupManager) {
		this.mdmDistEmpProdgroupManager = mdmDistEmpProdgroupManager;
	}

	// 订单列表（区域）
//	public ActionForward list(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response) {
//		ECPage page = new ECPage(request);
//		page.put(Page.AUTH_PREFIX, this.getAuthPrefix());
//		onInitPage(form, request, page);
//
//		String subCode = getCurrentOrgSubCode(request);
//		List<OrderInfo> list = (List<OrderInfo>) orderInfoManager.query(page,
//				subCode);
//
//		initList(form, request, list, page);
//		return mapping.findForward(LIST);
//
//	}

	/**
	 * 初始化一些列表参数及查询参数
	 */
	protected void onInitList(ActionForm form, HttpServletRequest request, List list, Page page) {
		
	}
	
	// 订单列表（区域）
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		ECPage page = new ECPage(request, this.getPageName());
		page.put(Page.AUTH_PREFIX, this.getAuthPrefix());
		onInitPage(form, request, page);
		
		String subCode = getCurrentOrgSubCode(request);
		page.put("subCode", subCode);
		
		List list = null;
		if (!isFirstQuery() && "1".equals(request.getParameter("first"))) {
			list = new ArrayList();
		}
		else {
			list = doListEntity(page);
		}
		
		initList(form, request, list, page);
		return page.initForward(mapping.findForward(LIST));
	}

	
	protected List doListEntity(Page page) {
		return orderInfoManager.queryOrderListForOrg(page);
	}
	
	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		OrderInfo orderInfo = new OrderInfo();
		orderInfo = orderInfoManager.get(Long.valueOf(request.getParameter("orderId")));
		request.setAttribute("orderInfo", orderInfo);
		
		// 根据登录人获得客户信息
		MdmDistributor mdist =orderInfo.getMdmDistributor();
		request.setAttribute("dist", mdist);

		// 得到送货信息
		MdmDistributorAddress address = new MdmDistributorAddress();
		address = orderInfo.getDistributorAddress();
		
		// 得到业代信息
		List<MdmDistEmpProdgroup> empGroupList = mdmDistEmpProdgroupManager.getMdmDistEmpProdgroupByProdgroup(mdist.getDistId(), orderInfo.getDistributorAddress().getBaseDictItem().getDictItemId());
		BaseEmployee empl = new BaseEmployee();
		if (empGroupList != null && empGroupList.size() > 0) {
			empl = empGroupList.get(0).getBaseEmployee();
			request.setAttribute("distEmpName", empl.getEmpName());
			request.setAttribute("distEmpTel", empl.getOfficePhone());
		}
	    //回传页面
	    request.setAttribute("distEmp", empl);
		
	    Long brandId = orderInfo.getDistributorAddress().getBaseDictItem().getDictItemId();
	    // 发票类型
	    BaseDictItem invoiceType = new BaseDictItem();
	    invoiceType = orderInfo.getInvoiceType();
		request.setAttribute("invoiceType", invoiceType);
		// 付款方式
		BaseDictItem paymentType = new BaseDictItem();
		paymentType = orderInfo.getPaymentType();
		request.setAttribute("paymentType", paymentType);
		// 运输方式
		BaseDictItem modeOfTransportType = new BaseDictItem();
		modeOfTransportType = orderInfo.getModeOfTransportType();
		request.setAttribute("modeOfTransportType", modeOfTransportType);
		// 经销商联系人

//		List<MdmDistributorLinkman> distLinks = orderInfoManager.findEntity(MdmDistributorLinkman.class, "from MdmDistributorLinkman where mdmDistributor.distId= " + mdist.getDistId()
//				+ " and  baseDictItem.dictItemId=" + brandId + " and status=1");
		MdmDistributorLinkman distLinks = orderInfo.getMdmDistributorLinkman();
		request.setAttribute("distLink", distLinks);


		request.setAttribute("itemBrandId", brandId);
		request.setAttribute("address", address);

	        
		return mapping.findForward(VIEW);
	}

}
