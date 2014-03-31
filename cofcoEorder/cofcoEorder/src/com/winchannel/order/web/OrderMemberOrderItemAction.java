package com.winchannel.order.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.LazyValidatorForm;

import com.winchannel.base.conf.BaseConfigurator;
import com.winchannel.base.model.BaseEmployee;
import com.winchannel.base.model.BaseRole;
import com.winchannel.base.model.BaseUser;
import com.winchannel.base.model.BaseUserRole;
import com.winchannel.base.service.BaseEmployeeManager;
import com.winchannel.base.service.BaseOrgManager;
import com.winchannel.base.service.BaseRoleManager;
import com.winchannel.base.service.BaseUserManager;
import com.winchannel.base.service.BaseUserRoleManager;
import com.winchannel.core.utils.DateUtils;
import com.winchannel.core.utils.ECPage;
import com.winchannel.core.utils.Page;
import com.winchannel.core.utils.StringUtils;
import com.winchannel.core.web.StrutsEntityAction;
import com.winchannel.mdm.distributor.model.MdmDistributor;
import com.winchannel.mdm.distributor.service.MdmDistributorManager;
import com.winchannel.mdm.product.service.MdmProductManager;
import com.winchannel.mdm.util.i18n.BeanMessage;
import com.winchannel.order.model.MdmDistEmpProdgroup;
import com.winchannel.order.model.OrderInfo;
import com.winchannel.order.model.OrderItem;
import com.winchannel.order.service.MdmDistEmpProdgroupManager;
import com.winchannel.order.service.OrderInfoManager;
import com.winchannel.order.service.OrderItemManager;
import com.winchannel.task.bean.SmsInfoBean;
import com.winchannel.task.service.SmsSendManager;

public class OrderMemberOrderItemAction extends	StrutsEntityAction<OrderInfo, OrderInfoManager>  {	
	
	private OrderInfoManager orderInfoManager;
	private MdmDistEmpProdgroupManager mdmDistEmpProdgroupManager;
	private OrderItemManager orderItemManager;
	private SmsSendManager smsSendManager;
	
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
	
	public OrderItemManager getOrderItemManager() {
		return orderItemManager;
	}
	public void setOrderItemManager(OrderItemManager orderItemManager) {
		this.orderItemManager = orderItemManager;
	}
	public SmsSendManager getSmsSendManager() {
		return smsSendManager;
	}
	public void setSmsSendManager(SmsSendManager smsSendManager) {
		this.smsSendManager = smsSendManager;
	}
	
	private String formNum(String val) {
		String[] temp = val.split("\\.");
		if (temp.length == 2) {
			val = temp[0] + "." + ((temp[1] + "000").substring(0, 3));
		} else {
			val = val + ".000";
		}
		return val;
	}
	protected void init() {
		//this.setFirstQuery(false);
	}
	/**
	 * 初始化一些列表参数及查询参数
	 */
	protected void onInitList(ActionForm form, HttpServletRequest request, List list, Page page) {
		
	}
	
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		ECPage page = new ECPage(request, this.getPageName());
		page.put(Page.AUTH_PREFIX, this.getAuthPrefix());
		onInitPage(form, request, page);
		if ("1".equals(request.getParameter("first"))) {
			page.put("status_","20");
		}
		page.put("empId", getCurrentUser(request).getBaseEmployee().getEmpId().toString());		
		List list = null;
		if (!isFirstQuery() && "1".equals(request.getParameter("first"))) {
			list = new ArrayList();
		}
		else {
			list = doListEntity(page);
		}
		
		initList(form, request, list, page);
		HttpSession session = request.getSession();
		session.setAttribute("actionPath", "orderMemberOrderItem.do?method=list");
		return page.initForward(mapping.findForward(LIST));
	}

	
	protected List doListEntity(Page page) {
		return entityManager.queryOrderItemListForOrderMember(page);
	}
	
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		if (isCancelled(request)) {
			return mapping.findForward(EDIT);
		}
		if (!isTokenValid(request)) {
			saveError(request, "mess.duplSubmit");
			return mapping.findForward(EDIT);
		}
		
		ActionMessages errors = form.validate(mapping, request);
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return mapping.findForward(EDIT);
		}
		
		OrderInfo orderInfo = null;
		if (StringUtils.isNotBlank(request.getParameter(idName))) {
			orderInfo = doGetEntity(form, request);
			if (orderInfo == null) {
				saveError(request, "entity.missing");
				resetToken(request);
				return mapping.findForward(EDIT);
			}else{
				if(request.getParameter("status")!=null){
					orderInfo.setStatus(new Integer(request.getParameter("status")));
				}
				if(request.getParameter("memoApp")!=null){
					orderInfo.setMemoApp(request.getParameter("memoApp"));
				}
				
				orderInfo.setMemoOrder(request.getParameter("memoOrder"));
				orderInfoManager.save(orderInfo);
				request.setAttribute("orderInfo", orderInfo);
				
				String items = request.getParameter("orderItems");
				JSONObject obj = JSONObject.fromObject(items);
				JSONArray array = obj.getJSONArray("orderItemInfo");
				int size = array.size();
				JSONObject temp = null;
				// 回传页面
				List<OrderItemVO> voList = new ArrayList<OrderItemVO>();
				OrderItemVO vo = null;
				for (int i = 0; i < size; i++) {
					vo = new OrderItemVO();
					temp = (JSONObject) array.get(i);
					OrderItem orderItem=orderItemManager.findUniqueEntity(" from OrderItem  where id=?", new Long(temp.getString("id")));
					orderItem.setMemoOrder(temp.getString("memoOrder"));
					orderItemManager.save(orderItem);
					
					vo.setId(orderItem.getId());
					if (orderItem.getMdmProduct() != null) {
						vo.setProdCode(orderItem.getMdmProduct().getProdCode());
						vo.setProdName(orderItem.getMdmProduct().getProdName());
					}					
					vo.setProdNum(formNum(orderItem.getQuantity().toString()));
					vo.setProdPrice(formNum(orderItem.getTaxPrice().toString()));
					vo.setProdAmount(formNum(orderItem.getAmount().toString()));
					vo.setProdMemo(orderItem.getMemo());
					vo.setProdUnit(orderItem.getUnit());
					vo.setMemoOrder(orderItem.getMemoOrder());
					voList.add(vo);
				}
				request.setAttribute("voList", voList);
				
				if(request.getParameter("status")!=null && (orderInfo.getStatus()==30||orderInfo.getStatus()==40)){
					if(orderInfo.getMdmDistributorLinkman().getIsSms()==1){
						SmsInfoBean smsInfo=new SmsInfoBean();
						smsInfo.setStatus(String.valueOf(orderInfo.getStatus().intValue()));
						smsInfo.setCustName(orderInfo.getCustName());
						smsInfo.setOrderCode(orderInfo.getOrderCode());
						smsInfo.setOrderNumber(orderInfo.getQuantity().toString());
						smsInfo.setPhoneNum(orderInfo.getMdmDistributorLinkman().getLinkmanPhonenum());
						smsInfo.setSalesName(orderInfo.getIndustryName());				
						try {
							smsSendManager.sendMsg(smsInfo);
						} catch (Exception e) {
							log.error("短信发送失败!订单号为："+orderInfo.getOrderCode(), e);
							doMessage(request, "订单审批成功！订单号：" + orderInfo.getOrderCode()+"<BR> 短信发送失败！");
							return mapping.findForward(SUCCESS);
						}
					}									
				}
				this.saveDirectlyMessage(request, "订单审批成功!");
				return mapping.findForward(SUCCESS);
			}
		} 
		
		
		
		resetToken(request);
		return getForward(mapping, request, getSuccessView());
		}
	
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		saveToken(request);
		OrderInfo orderInfo = new OrderInfo();
		orderInfo = orderInfoManager.get(Long.valueOf(request.getParameter("orderId")));
		request.setAttribute("orderInfo", orderInfo);
		
		List<OrderItemVO> voList = new ArrayList<OrderItemVO>();
		List<OrderItem> itemList = orderInfo.getOrderItems();
		int size = itemList.size();
		OrderItemVO vo = null;
		for (int i = 0; i < size; i++) {
			vo = new OrderItemVO();
			vo.setId(itemList.get(i).getId());
			if (itemList.get(i).getMdmProduct() != null) {
				vo.setProdCode(itemList.get(i).getMdmProduct().getProdCode());
				vo.setProdName(itemList.get(i).getMdmProduct().getProdName());
			}
			
			vo.setProdNum(formNum(itemList.get(i).getQuantity().toString()));
			vo.setProdPrice(formNum(itemList.get(i).getTaxPrice().toString()));
			vo.setProdAmount(formNum(itemList.get(i).getAmount().toString()));
			vo.setProdMemo(itemList.get(i).getMemo());
			vo.setProdUnit(itemList.get(i).getUnit());
			vo.setMemoOrder(itemList.get(i).getMemoOrder());
			voList.add(vo);
		}
		request.setAttribute("voList", voList);
	        
		return mapping.findForward(EDIT);
	}
	/**
	 * @编辑-注入
	 * 
	 */
	protected void onInitEntity(ActionForm form, HttpServletRequest request, OrderInfo orderInfo) {
		orderInfo.setStatus(Integer.valueOf(request.getParameter("orderInfo.status")));
	}

}
