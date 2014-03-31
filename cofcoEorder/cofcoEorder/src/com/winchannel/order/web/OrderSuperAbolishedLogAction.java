package com.winchannel.order.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.LazyValidatorForm;

import com.winchannel.base.model.BaseDictItem;
import com.winchannel.base.model.BaseEmployee;
import com.winchannel.core.utils.DateUtils;
import com.winchannel.core.utils.StringUtils;
import com.winchannel.core.web.StrutsEntityAction;
import com.winchannel.mdm.distributor.model.MdmDistributor;
import com.winchannel.order.model.MdmDistEmpProdgroup;
import com.winchannel.order.model.MdmDistributorAddress;
import com.winchannel.order.model.MdmDistributorLinkman;
import com.winchannel.order.model.OrderInfo;
import com.winchannel.order.model.OrderSuperAbolishedLog;
import com.winchannel.order.service.MdmDistEmpProdgroupManager;
import com.winchannel.order.service.OrderInfoManager;
import com.winchannel.order.service.OrderSuperAbolishedLogManager;

/**
 * @author shijingru
 * @超级管理员权限维护订单状态
 */
public class OrderSuperAbolishedLogAction extends	StrutsEntityAction<OrderSuperAbolishedLog, OrderSuperAbolishedLogManager> {	
	private OrderSuperAbolishedLogManager orderSuperAbolishedLogManager;
	private OrderInfoManager orderInfoManager;
	private MdmDistEmpProdgroupManager mdmDistEmpProdgroupManager;

	/**
	 * 根据订单编号查看订单明细
	 * 
	 * @param request
	 * @param message
	 */
	public ActionForward getOrderCode(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		String orderCode = request.getParameter("orderCode");
		OrderInfo orderInfo= this.orderInfoManager.getJSONOrderInfoByCode(orderCode);
		if(null != orderInfo){
			//订单明细
			List<OrderItemVO> items= orderInfoManager.getJSONOrderItemByCode(orderCode);
			//json
			Map<String,Object> jsonMap = new HashMap<String,Object>();
			jsonMap.put("orderInfo", orderInfo);
			jsonMap.put("items", items);
			
			this.renderText(response, JSONObject.fromObject(jsonMap).toString());
		}else{
			this.renderText(response, "");
		}
		return null;
	}
	

	/**
	 * 编辑页查看
	 * 
	 */			
	protected void onInitForm(ActionForm form, HttpServletRequest request, OrderSuperAbolishedLog orderSuperAbolishedLog) {
		LazyValidatorForm OrderSuperAbolishedLogForm = (LazyValidatorForm) form;
		if (StringUtils.isNotBlank(request.getParameter("id"))) {
			OrderSuperAbolishedLog orderSuper = this.entityManager.get(new Long(request.getParameter("id")));
			OrderSuperAbolishedLogForm.set("orderCode", orderSuper.getOrderInfo().getOrderCode());
		}
	}

	
	/**
	 * 
	 * 新增和修改
	 * 
	 */
	protected void onInitEntity(ActionForm form, HttpServletRequest request, OrderSuperAbolishedLog orderSuperAbolishedLog) {
		
		BaseEmployee employee = this.getCurrentUser(request).getBaseEmployee();
		orderSuperAbolishedLog.setCreatedDate(DateUtils.getDate());
		orderSuperAbolishedLog.setCreatedByname(employee.getEmpName());
		orderSuperAbolishedLog.setCreatedByid(employee.getEmpId());
		//原来状态
		OrderInfo orderInfo = orderInfoManager.findUniqueEntity("from OrderInfo where  orderCode=? ", request.getParameter("orderCode"));
		orderSuperAbolishedLog.setOldStatus(orderInfo.getStatus());
		//更新状态
		Integer newStatus = Integer.valueOf(request.getParameter("newStatus"));
		orderInfo.setStatus(newStatus);
		//保存订单状态
		orderInfoManager.save(orderInfo);
		
		OrderSuperAbolishedLog orderSuper = new OrderSuperAbolishedLog();
		orderSuper = this.entityManager.findUniqueEntity("from OrderSuperAbolishedLog where  orderInfo.orderId=? ", orderInfo.getOrderId());
		if(null != orderSuper){
			orderSuperAbolishedLogManager.delete(orderSuper);
		}
		orderSuperAbolishedLog.setOrderInfo(orderInfo);
		//保存客户订单状态调整(管理员)信息
		orderSuperAbolishedLogManager.save(orderSuperAbolishedLog);
	}

	
	/**
	 * view
	 * 
	 */			
	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		OrderSuperAbolishedLog orderSuper = this.entityManager.get(new Long(request.getParameter("id")));
		request.setAttribute("orderSuperAbolishedLog", orderSuper);
		OrderInfo orderInfo = new OrderInfo();
		orderInfo = orderInfoManager.get(Long.valueOf(orderSuper.getOrderInfo().getOrderId()));
		request.setAttribute("orderInfo", orderInfo);
		
		return mapping.findForward(VIEW);
	}
	
	public OrderSuperAbolishedLogManager getOrderSuperAbolishedLogManager() {
		return orderSuperAbolishedLogManager;
	}

	public void setOrderSuperAbolishedLogManager(
			OrderSuperAbolishedLogManager orderSuperAbolishedLogManager) {
		this.orderSuperAbolishedLogManager = orderSuperAbolishedLogManager;
	}

	public OrderInfoManager getOrderInfoManager() {
		return orderInfoManager;
	}

	public void setOrderInfoManager(OrderInfoManager orderInfoManager) {
		this.orderInfoManager = orderInfoManager;
	}

	public MdmDistEmpProdgroupManager getMdmDistEmpProdgroupManager() {
		return mdmDistEmpProdgroupManager;
	}

	public void setMdmDistEmpProdgroupManager(
			MdmDistEmpProdgroupManager mdmDistEmpProdgroupManager) {
		this.mdmDistEmpProdgroupManager = mdmDistEmpProdgroupManager;
	}

	
	
}
