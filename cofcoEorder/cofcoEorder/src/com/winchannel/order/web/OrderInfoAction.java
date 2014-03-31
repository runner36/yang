		package com.winchannel.order.web;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.winchannel.core.utils.DateUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.LazyValidatorForm;

import com.winchannel.base.model.BaseDictItem;
import com.winchannel.base.model.BaseEmployee;
import com.winchannel.base.model.BaseUser;
import com.winchannel.base.utils.Constants;
import com.winchannel.core.exception.BusinessException;
import com.winchannel.core.utils.ECPage;
import com.winchannel.core.utils.Page;
import com.winchannel.core.utils.StringUtils;
import com.winchannel.core.web.StrutsEntityAction;
import com.winchannel.base.utils.DateUtility;
import com.winchannel.mdm.distributor.model.MdmDistributor;
import com.winchannel.mdm.distributor.service.MdmDistributorManager;
import com.winchannel.mdm.product.model.MdmProduct;
import com.winchannel.order.bean.DistEmpProdgroup;
import com.winchannel.order.model.MdmDistEmpProdgroup;
import com.winchannel.order.model.MdmDistributorAddress;
import com.winchannel.order.model.MdmDistributorLinkman;
import com.winchannel.order.model.MdmProdGroupDeliveryDays;
import com.winchannel.order.model.OrderInfo;
import com.winchannel.order.model.OrderItem;
import com.winchannel.order.service.MdmDistEmpProdgroupManager;
import com.winchannel.order.service.MdmDistributorAddressManager;
import com.winchannel.order.service.MdmProdGroupDeliveryDaysManager;
import com.winchannel.order.service.OrderInfoManager;
import com.winchannel.task.bean.SmsInfoBean;
import com.winchannel.task.service.SmsSendManager;

public class OrderInfoAction extends StrutsEntityAction<OrderInfo, OrderInfoManager> {
	private final static String STORE_PREFIX = "store_";
	private final static String STARTDATE_PREFIX = "startDate_";
	private final static String SPLIT = "_";

	private OrderInfoManager orderInfoManager;
	private MdmDistributorManager mdmDistributorManager;
	private MdmDistributorAddressManager mdmDistributorAddressManager;
	private MdmDistEmpProdgroupManager mdmDistEmpProdgroupManager;
	private SmsSendManager smsSendManager;
	private MdmProdGroupDeliveryDaysManager mdmProdGroupDeliveryDaysManager;
	
	
	public MdmProdGroupDeliveryDaysManager getMdmProdGroupDeliveryDaysManager() {
		return mdmProdGroupDeliveryDaysManager;
	}

	public void setMdmProdGroupDeliveryDaysManager(
			MdmProdGroupDeliveryDaysManager mdmProdGroupDeliveryDaysManager) {
		this.mdmProdGroupDeliveryDaysManager = mdmProdGroupDeliveryDaysManager;
	}

	public void setSmsSendManager(SmsSendManager smsSendManager) {
		this.smsSendManager = smsSendManager;
	}

	public MdmDistributorManager getMdmDistributorManager() {
		return mdmDistributorManager;
	}

	public void setMdmDistributorManager(MdmDistributorManager mdmDistributorManager) {
		this.mdmDistributorManager = mdmDistributorManager;
	}

	public OrderInfoManager getOrderInfoManager() {
		return orderInfoManager;
	}

	public void setOrderInfoManager(OrderInfoManager orderInfoManager) {
		this.orderInfoManager = orderInfoManager;
	}

	public void setMdmDistributorAddressManager(MdmDistributorAddressManager mdmDistributorAddressManager) {
		this.mdmDistributorAddressManager = mdmDistributorAddressManager;
	}

	public void setMdmDistEmpProdgroupManager(MdmDistEmpProdgroupManager mdmDistEmpProdgroupManager) {
		this.mdmDistEmpProdgroupManager = mdmDistEmpProdgroupManager;
	}

	protected void init() {
		//this.setFirstQuery(false);
	}

	

	/**
	 * 订单制作首页
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward initMake(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		return getForward(mapping, request, "index");
	}

	/**
	 * 订单制作初始化页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward editSp(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		// 根据登录人获得客户信息
		saveToken(request);
		BaseUser userInfo = (BaseUser) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		Long empId = null;
		if (userInfo != null && userInfo.getBaseEmployee() != null) {
			empId = userInfo.getBaseEmployee().getEmpId();
		}
		MdmDistributor mdist = mdmDistributorManager.getMdmDistributorByEmployeeId(empId);
		request.setAttribute("dist", mdist);
		if (mdist == null) {
			doMessage(request, "您无权访问！");
			return getForward(mapping, request, "index");
		}
		String brandId = request.getParameter("itemBrandId");
		if (brandId == null || "".equals(brandId.trim())) {
			doMessage(request, "请选择物料组！");
			return getForward(mapping, request, "index");
		}
		//送货日期 默认为第二天
		Date currDate = DateUtils.getDate();
		Date shipDate=new Date(currDate.getTime()+ 86400000);
		request.setAttribute("shiptoDate", DateUtils.format(shipDate, "yyyy-MM-dd"));
		// 发票类型
		List<BaseDictItem> invoiceTypeList = orderInfoManager.findEntity(BaseDictItem.class, "from BaseDictItem where baseDict.dictId = 'invoiceType' ");
		if (invoiceTypeList.size()>0) {
			//request.setAttribute("invoiceType", invoiceTypeList.get(0).getDictItemId());
			if(mdist.getInvoiceType()!=null)
			request.setAttribute("invoiceType", mdist.getInvoiceType().getDictItemId());
		}
		request.setAttribute("invoiceTypes", invoiceTypeList);
		
		// 客户联系人信息
		List<MdmDistributorLinkman> distLinks = orderInfoManager.findEntity(MdmDistributorLinkman.class, "from MdmDistributorLinkman where mdmDistributor.distId= " + mdist.getDistId()
				+ " and  baseDictItem.dictItemId=" + brandId + " and status=1");
		if(distLinks.size()>0){
		    request.setAttribute("distLink", distLinks.get(0));
		}
		request.setAttribute("distLinks", distLinks);
		
		// 付款方式
		List<BaseDictItem> paymentTypes = orderInfoManager.findEntity(BaseDictItem.class, "from BaseDictItem where baseDict.dictId = 'PAYMENT' ");
		if (invoiceTypeList.size() > 0) {
			request.setAttribute("paymentType", paymentTypes.get(0).getDictItemId());
		}
		request.setAttribute("paymentTypes", paymentTypes);

		// 得到送货信息
		List<MdmDistributorAddress> addressList = mdmDistributorAddressManager.getMdmDistributorAddressByDistProdgroup(mdist.getDistId(), Long.valueOf(brandId));
		MdmDistributorAddress address = new MdmDistributorAddress();
		if (addressList.size() > 0) {
			address = addressList.get(0);
		}
		request.setAttribute("address", address);

		// 运输方式
		List<BaseDictItem> modeOfTransportType = orderInfoManager.findEntity(BaseDictItem.class, "from BaseDictItem where state='1' and baseDict.dictId = 'MODE_OF_TRANSPORT' order by sort asc ");
		if (modeOfTransportType.size() > 0) {
			request.setAttribute("modeOfTransportType", modeOfTransportType.get(0).getDictItemId());
		}
		request.setAttribute("modeOfTransportTypes", modeOfTransportType);
		
		// 得到业代信息
		List<MdmDistEmpProdgroup> empGroupList = mdmDistEmpProdgroupManager.getMdmDistEmpProdgroupByProdgroup(mdist.getDistId(), Long.valueOf(brandId));
		BaseEmployee empl = new BaseEmployee();
		if (empGroupList != null && empGroupList.size() > 0) {
			empl = empGroupList.get(0).getBaseEmployee();
			request.setAttribute("industryEmp", empl);
			request.setAttribute("distEmpId", empl.getEmpId());
			request.setAttribute("distEmpName", empl.getEmpName());
			request.setAttribute("distEmpTel", empl.getMobilePhone());
		}


		request.setAttribute("itemBrandId", brandId);
		

		return getForward(mapping, request, "makeInfo");
	}

	/**
	 * 订单制作保存
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward saveSp(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		if (isCancelled(request)) {
			return getForward(mapping, request, getSuccessView());
		}
		if (!isTokenValid(request)) {
			saveError(request, "mess.duplSubmit");
			return getForward(mapping, request, "index");
		}
		
		LazyValidatorForm cForm = (LazyValidatorForm) form; 
		// 根据登录人获得客户信息
		BaseUser userInfo = (BaseUser) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		Long empId = null;
		if (userInfo != null && userInfo.getBaseEmployee() != null) {
			empId = userInfo.getBaseEmployee().getEmpId();
		}
		//当前登录用户所属的经销商
		MdmDistributor mdist = mdmDistributorManager.getMdmDistributorByEmployeeId(empId);
		if (mdist == null) {
			doMessage(request, "您无权访问！");
			return getForward(mapping, request, "index");
		}
		
		
		String brandId = request.getParameter("itemBrandId");
		if (brandId == null || "".equals(brandId.trim())) {
			doMessage(request, "请选择物料组！");
			return getForward(mapping, request, "index");
		}
		request.setAttribute("itemBrandId", brandId);
		// 产品组、物料组选择
		BaseDictItem groupItem = orderInfoManager.findUniqueEntity(BaseDictItem.class, "from BaseDictItem where dictItemId = ? ", Long.valueOf(brandId));

		//根据订单品类，找到对应    组织  品类下的经销商（针对多个经销商用户使用 同一账号，进行的调整）
		List<MdmDistributor> mdists = mdmDistributorManager.find("from MdmDistributor where distCode like ?", mdist.getDistCode()+"%");
		for (int i = 0; i < mdists.size(); i++) {
			if(mdists.get(i).getBaseOrg()!=null){
				if(mdists.get(i).getBaseOrg().getBaseOrg()!=null){
					String name = mdists.get(i).getBaseOrg().getBaseOrg().getOrgName();
					if(name.contains(groupItem.getItemName()))
						mdist = mdists.get(i);
				}
			}
		}
		
		request.setAttribute("dist", mdist);
		
		// 发票类型
		List<BaseDictItem> invoiceTypeList = orderInfoManager.findEntity(BaseDictItem.class, "from BaseDictItem where baseDict.dictId = 'invoiceType' ");
		if (invoiceTypeList.size() > 0) {
			request.setAttribute("invoiceTypes", invoiceTypeList);
		}
		
		// 送货地址信息
		String shiptoId = request.getParameter("shiptoId");
		// String shiptoCode = request.getParameter("shiptoCode");
		MdmDistributorAddress address = mdmDistributorAddressManager.findUniqueEntity("from MdmDistributorAddress where id = ? ", Long.valueOf(shiptoId));
		request.setAttribute("address", address);
		
		
		
		
		
		// 客户联系人信息
		List<MdmDistributorLinkman> distLinks = orderInfoManager.findEntity(MdmDistributorLinkman.class, "from MdmDistributorLinkman where mdmDistributor.distId= " + mdist.getDistId()
				+ " and  baseDictItem.dictItemId=" + brandId + " and status=1");
		request.setAttribute("distLinks", distLinks);
		
		// 付款方式
		List<BaseDictItem> paymentTypes = orderInfoManager.findEntity(BaseDictItem.class, "from BaseDictItem where baseDict.dictId = 'PAYMENT' ");
		request.setAttribute("paymentTypes", paymentTypes);
		
		// 运输方式
		List<BaseDictItem> modeOfTransportTypes = orderInfoManager.findEntity(BaseDictItem.class, "from BaseDictItem where baseDict.dictId = 'MODE_OF_TRANSPORT' order by sort asc ");
		request.setAttribute("modeOfTransportTypes", modeOfTransportTypes);
		
		// 得到业代信息
		String distEmpId = request.getParameter("distEmpId")==null?"":request.getParameter("distEmpId");
		request.setAttribute("distEmpId",request.getParameter("distEmpId"));
		request.setAttribute("distEmpName", request.getParameter("distEmpName"));
		request.setAttribute("distEmpTel", request.getParameter("distEmpTel"));

		
		String items = request.getParameter("orderItems");
		JSONObject obj = JSONObject.fromObject(items);
		JSONArray array = obj.getJSONArray("dataInfo");
		int size = array.size();
		JSONObject temp = null;

		// 回传页面
		List<OrderItemVO> voList = new ArrayList<OrderItemVO>();
		OrderItemVO vo = null;
		for (int i = 0; i < size; i++) {
			vo = new OrderItemVO();
			temp = (JSONObject) array.get(i);
			vo.setProdCode(temp.getString("prodCode"));
			vo.setProdName(temp.getString("prodName"));
			vo.setProdNum(temp.getString("prodNum"));
			vo.setProdPrice(temp.getString("prodPrice"));
			vo.setProdAmount(temp.getString("prodAmount"));
			vo.setProdMemo(temp.getString("prodMemo"));
			vo.setProdUnit(temp.getString("prodUnit"));
			voList.add(vo);
		}
		request.setAttribute("voList", voList);

		String memo = request.getParameter("memo");
		request.setAttribute("memo", memo);

		String orderDate = request.getParameter("orderDate");
		String shiptoDate = request.getParameter("queryDate");
		request.setAttribute("orderDate", orderDate);
		request.setAttribute("shiptoDate", shiptoDate);
		Date oDate = null;
		try {
			oDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(orderDate);			
		} catch (Exception e) {
			doMessage(request, "订货日期格式不正确！");
			return getForward(mapping, request, "makeInfo");
		}
		Date sDate = null;
		try {
			sDate = new SimpleDateFormat("yyyy-MM-dd").parse(shiptoDate);
		} catch (Exception e) {
			doMessage(request, "送货日期格式不正确！");
			return getForward(mapping, request, "makeInfo");
		}
		if("".equals(distEmpId)){
		    doMessage(request, "业代不能为空！");
		    return getForward(mapping, request, "index");
		}
		BaseEmployee emp = orderInfoManager.findUniqueEntity(BaseEmployee.class, "from BaseEmployee where empId = ? ", Long.valueOf(distEmpId));
		

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < size; i++) {
			temp = (JSONObject) array.get(i);
			sb.append("'").append(temp.get("prodCode").toString()).append("',");
		}
		sb.deleteCharAt(sb.length() - 1);
		// 获得产品信息
		List<MdmProduct> prodList = orderInfoManager.findEntity(MdmProduct.class, "from MdmProduct where prodCode in (" + sb.toString() + ")");

		Map<String, MdmProduct> map = new HashMap<String, MdmProduct>();
		if (prodList.size() > 0) {
			for (MdmProduct objs : prodList) {
				map.put(objs.getProdCode(), objs);
			}
		}

		String orderId = request.getParameter("orderId") == null ? "" : request.getParameter("orderId").toString();
		List<OrderItem> itemList = new ArrayList<OrderItem>();
		OrderItem item = null;
		OrderInfo orderInfo = new OrderInfo();
		if (!"".equals(orderId)) {
			orderInfo = orderInfoManager.findUniqueEntity("from OrderInfo where orderId = ? ", Long.valueOf(orderId));
			itemList = orderInfo.getOrderItems();
			itemList.removeAll(itemList);
		}
		orderInfo.setIndustryEmp(emp);
		orderInfo.setCustCode(mdist.getDistCode());
 		orderInfo.setCustName(mdist.getDistName());
		orderInfo.setDistributorAddress(address);
		//客户联系人
 		MdmDistributorLinkman distLink=orderInfoManager.findUniqueEntity(MdmDistributorLinkman.class,"from MdmDistributorLinkman where id= "+ request.getParameter("distLink"));
		orderInfo.setMdmDistributorLinkman(distLink);
 		orderInfo.setCustTell(distLink.getLinkmanPhonenum());
		orderInfo.setCustLinkman(distLink.getLinkmanName());
		request.setAttribute("distLink", distLink);
		

		orderInfo.setInvoiceName(mdist.getDistName());
		//发票类型
		String typeId = request.getParameter("invoiceType");
		if (typeId != null && !"".equals(typeId.trim())) {
			BaseDictItem typeItem = orderInfoManager.findUniqueEntity(BaseDictItem.class, "from BaseDictItem where dictItemId = ? ", Long.valueOf(typeId));
			orderInfo.setInvoiceType(typeItem);
			request.setAttribute("invoiceType", typeId);
		}

		orderInfo.setMdmDistributor(mdist);
		orderInfo.setMemo(memo);
		//订单总金额
		BigDecimal amount=new BigDecimal(0);
		BigDecimal tmp = new BigDecimal(0);

		  
		//订单总数量
		BigDecimal quan=new BigDecimal(0);
		for (int i = 0; i < size; i++) {
			item = new OrderItem();
			temp = (JSONObject) array.get(i);
			item.setMdmProduct(map.get(temp.get("prodCode")));
			item.setQuantity(Double.valueOf(temp.get("prodNum").toString()));
			item.setTaxPrice(Double.valueOf(temp.get("prodPrice").toString()));
			
			item.setAmount(new BigDecimal(temp.get("prodAmount").toString()));
			item.setMemo(temp.get("prodMemo").toString());
			item.setUnit(temp.get("prodUnit").toString());
			item.setOrderInfo(orderInfo);
			itemList.add(item);
		
			tmp=new BigDecimal(temp.getString("prodAmount"));
			amount=amount.add(tmp);
			quan=quan.add(new BigDecimal(temp.getString("prodNum")))   ;
		}
		
		orderInfo.setQuantity((quan));
		orderInfo.setAmount(  (amount));
		
		
		orderInfo.setOrderItems(itemList);

		orderInfo.setShiptoCode(address.getShiptoCode());
		orderInfo.setShiptoDate(sDate);
		orderInfo.setShiptoLinkman(address.getContact());
		orderInfo.setShiptoName(address.getShiptoName());
		orderInfo.setShiptoTell(address.getTel());
		
		
		
		 //付款方式 paymentType paymentName
		BaseDictItem paymentType=this.baseDictManager.findUniqueEntity(BaseDictItem.class, "from BaseDictItem where dictItemId = ? ", Long.valueOf(cForm.get("paymentType").toString()));
		orderInfo.setPaymentType(paymentType);
		orderInfo.setPaymentName(paymentType.getItemName());
		request.setAttribute("paymentType", request.getParameter("paymentType"));
			
		 //运输方式 modeOfTransportType modeOfTransportName
		BaseDictItem modeOfTransportType=this.baseDictManager.findUniqueEntity(BaseDictItem.class, "from BaseDictItem where dictItemId = ? ", Long.valueOf(cForm.get("modeOfTransportType").toString()));
		orderInfo.setModeOfTransportType(modeOfTransportType);
		orderInfo.setModeOfTransportName(modeOfTransportType.getItemName());
		request.setAttribute("modeOfTransportType", request.getParameter("modeOfTransportType"));
		
		
		orderInfo.setIndustryMobile(request.getParameter("distEmpTel"));
		orderInfo.setIndustryName(request.getParameter("distEmpName"));
		
		//添加订单创建时间  liuguangshuai 2012-9-8
		orderInfo.setOrderCreateDate(oDate);
		orderInfo.setOrderDate(oDate);
		
		// 订单状态：10：'未提交' 20：'请订单中心接收' 30：'已接收' 40：'订单有误已作废'
		String tempFlag = request.getParameter("tempFlag");
		if (tempFlag != null) {
			orderInfo.setStatus(10);
		} else {
			orderInfo.setStatus(20);
		}

		orderInfo.setProdGroup(groupItem);
		
		
		// save();
		try {
			orderInfoManager.save(orderInfo);
			
		} catch (BusinessException e) {
			doMessage(request, e.getMessage());
			return getForward(mapping, request, "index");
		}
		doMessage(request, "保存成功！订单号：" + orderInfo.getOrderCode());
		request.setAttribute("orderId", orderInfo.getOrderId());
		request.setAttribute("orderCode", orderInfo.getOrderCode());

		if (tempFlag != null) {
			doMessage(request, "保存成功！订单号：" + orderInfo.getOrderCode());
			return getForward(mapping, request, "makeInfo");
		} else {
			if(orderInfo.getStatus().intValue()>10){
				SmsInfoBean smsInfo=new SmsInfoBean();
				smsInfo.setStatus(String.valueOf(orderInfo.getStatus().intValue()));
				smsInfo.setCustName(orderInfo.getCustName());
				smsInfo.setOrderCode(orderInfo.getOrderCode());
				smsInfo.setOrderNumber(orderInfo.getQuantity().toString());
				smsInfo.setPhoneNum(orderInfo.getIndustryMobile());
				smsInfo.setSalesName(orderInfo.getIndustryName());
				try {
					smsSendManager.sendMsg(smsInfo);
				} catch (Exception e) {
					doMessage(request, "发送成功！订单号：" + orderInfo.getOrderCode()+"<BR> 短信发送失败！");
					 
					return getForward(mapping, request, "index");
				}
			}
			doMessage(request, "发送成功！订单号：" + orderInfo.getOrderCode());
			return getForward(mapping, request, "index");
		}

	}

	protected void onInitForm(ActionForm form, HttpServletRequest request, OrderInfo OrderInfo) {
		LazyValidatorForm form1 = (LazyValidatorForm) form;

		if (OrderInfo.getMdmDistributor() != null) {
			form1.set("distId", OrderInfo.getMdmDistributor().getDistId().toString());
			form1.set("distName", OrderInfo.getMdmDistributor().getDistName());
		}
	}

	/**
	 * 订单制作列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward listSp(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		// 根据登录人获得客户信息
		ECPage page = new ECPage(request, this.getPageName());
		// page.put(Page.AUTH_PREFIX, this.getAuthPrefix());

		BaseUser userInfo = (BaseUser) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		Long empId = null;
		if (userInfo != null && userInfo.getBaseEmployee() != null) {
			empId = userInfo.getBaseEmployee().getEmpId();
		} 
		MdmDistributor mdist = mdmDistributorManager.getMdmDistributorByEmployeeId(empId);
		request.setAttribute("dist", mdist);
		if (mdist == null) {
			doMessage(request, "您无权访问！");
			return getForward(mapping, request, "index");
		}
		
		
		//根据订单品类，找到对应    组织  品类下的经销商（针对多个经销商用户使用 同一账号，进行的调整）
		List<MdmDistributor> mdists = mdmDistributorManager.find("from MdmDistributor where distCode like ?", mdist.getDistCode()+"%");
		List<String> mdistIds = new ArrayList<String>();
		for(MdmDistributor m:mdists){
			mdistIds.add(m.getDistId().toString());
		}
//				for (int i = 0; i < mdists.size(); i++) {
//					if(mdists.get(i).getBaseOrg()!=null){
//						if(mdists.get(i).getBaseOrg().getBaseOrg()!=null){
//							String name = mdists.get(i).getBaseOrg().getBaseOrg().getOrgName();
//							if(name.contains(groupItem.getItemName()))
//								mdist = mdists.get(i);
//						}
//					}
//				}

		List list = null;
		if (!isFirstQuery() && "1".equals(request.getParameter("first"))) {
			list = new ArrayList();
		} else {
			if (mdist != null) {
//				page.put("$eq_mdmDistributor_distId", mdist.getDistId().toString());
				page.put("$in_mdmDistributor_distId",StringUtils.join(mdistIds.toArray(),","));
				page.put(Page.SORT, "orderDate desc");
				
				onInitPage(form, request, page);
				list = doListEntity(page);
				initList(form, request, list, page);
			}
		}

		return page.initForward(mapping.findForward("listSp"));
	}

	/**
	 * 查看订单明细
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		// 根据登录人获得客户信息
		String orderId = request.getParameter("orderId") == null ? "" : request.getParameter("orderId");
		if ("".equals(orderId)) {
			doMessage(request, "订单错误！");
			listSp(mapping, form, request, response);
			return getForward(mapping, request, "listSp");
		}

		OrderInfo orderInfo = orderInfoManager.findUniqueEntity("from OrderInfo where orderId = ? ", Long.valueOf(orderId));

		request.setAttribute("orderInfo", orderInfo);

		List<OrderItemVO> voList = new ArrayList<OrderItemVO>();
		List<OrderItem> itemList = orderInfo.getOrderItems();
		int size = itemList.size();
		 
		OrderItemVO vo = null;
		BigDecimal money=null;
		for (int i = 0; i < size; i++) {
			vo = new OrderItemVO();
			if (itemList.get(i).getMdmProduct() != null) {
				vo.setProdCode(itemList.get(i).getMdmProduct().getProdCode());
				vo.setProdName(itemList.get(i).getMdmProduct().getProdName());
			}
			
//			vo.setProdNum(formNum(itemList.get(i).getQuantity().toString()));
//			vo.setProdPrice(formNum(itemList.get(i).getTaxPrice().toString()));
//			vo.setProdAmount(formNum(itemList.get(i).getAmount().toString()));
			money=new BigDecimal(itemList.get(i).getQuantity());
			money= money.setScale(3, BigDecimal.ROUND_HALF_UP);
			vo.setProdNum(money.toString());
			money=new BigDecimal(itemList.get(i).getTaxPrice());
			money= money.setScale(3, BigDecimal.ROUND_HALF_UP);
			vo.setProdPrice(money.toString());
			money= itemList.get(i).getAmount() ;
			money= money.setScale(3, BigDecimal.ROUND_HALF_UP);
			vo.setProdAmount(money.toString());
			
			
			vo.setProdMemo(itemList.get(i).getMemo());
			vo.setProdUnit(itemList.get(i).getUnit());
			voList.add(vo);
		}
		request.setAttribute("voList", voList);
		return getForward(mapping, request, "view");
	}

	/**
	 * 订单修改重发初始化页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward updateInit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		saveToken(request);
		String orderId = request.getParameter("orderId") == null ? "" : request.getParameter("orderId").toString();
		if ("".equals(orderId.trim())) {
			doMessage(request, "订单错误！");
			listSp(mapping, form, request, response);
			return getForward(mapping, request, "listSp");
		}
		OrderInfo orderInfo = null;
		OrderInfo order = orderInfoManager.findUniqueEntity("from OrderInfo where orderId = ? ", Long.valueOf(orderId));
		if (order == null) {
			doMessage(request, "订单不存在！");
			listSp(mapping, form, request, response);
			return getForward(mapping, request, "listSp");
		}
		
		//如果状态打回，则复制订单
		if (order.getStatus() == 10) {
			request.setAttribute("orderId", orderId);
			orderInfo = order;
		} else {
			OrderInfo info = new OrderInfo();
			info.setAmount(order.getAmount());
			info.setCustCode(order.getMdmDistributor().getDistCode());
			info.setCustName(order.getMdmDistributor().getDistName());
			info.setMdmDistributorLinkman(order.getMdmDistributorLinkman());
			info.setCustLinkman(order.getMdmDistributorLinkman().getLinkmanName());
			
			info.setCustTell(order.getMdmDistributorLinkman().getLinkmanPhonenum());
			info.setDistributorAddress(order.getDistributorAddress());
			info.setIndustryEmp(order.getIndustryEmp());
			if(order.getIndustryEmp()!=null){
				order.setIndustryMobile(order.getIndustryEmp().getMobilePhone());
				order.setIndustryName(order.getIndustryEmp().getEmpName());
			}
			
			
			info.setInvoiceType(order.getInvoiceType());
			info.setInvoiceName(order.getInvoiceType().getItemName());
			
			info.setMdmDistributor(order.getMdmDistributor());
			info.setMemo(order.getMemo());
			info.setProdGroup(order.getProdGroup());
			info.setShiptoCode(order.getDistributorAddress().getShiptoCode());
			//info.setShiptoDate(new Date());
			info.setShiptoDate(order.getShiptoDate());
			info.setShiptoLinkman(order.getDistributorAddress().getContact());
			info.setShiptoName(order.getDistributorAddress().getShiptoName());
			info.setShiptoTell(order.getDistributorAddress().getTel());
			
			info.setPaymentType(order.getPaymentType());
			info.setModeOfTransportType(order.getModeOfTransportType());
			//info.setStatus(10);

			List<OrderItem> items = new ArrayList<OrderItem>();
			List<OrderItem> oitems = order.getOrderItems();
			OrderItem item = null;
			for (OrderItem oi : oitems) {
				item = new OrderItem();
				item.setAmount(oi.getAmount());
				item.setMdmProduct(oi.getMdmProduct());
				item.setMemo(oi.getMemo());
				item.setUnit(oi.getUnit());
				item.setQuantity(oi.getQuantity());
				item.setTaxPrice(oi.getTaxPrice());
				item.setOrderInfo(info);
				items.add(item);
			}
			info.setOrderItems(items);
		//	orderInfoManager.save(info);
			orderInfo = info;
		}

		// 根据登录人获得客户信息
		BaseUser userInfo = (BaseUser) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		Long empId = null;
		if (userInfo != null && userInfo.getBaseEmployee() != null) {
			empId = userInfo.getBaseEmployee().getEmpId();
		}
		MdmDistributor mdist = mdmDistributorManager.getMdmDistributorByEmployeeId(empId);

		
		request.setAttribute("orderCode", orderInfo.getOrderCode());
		request.setAttribute("dist", mdist);
		request.setAttribute("address", orderInfo.getDistributorAddress());
		//送货日期 默认为第二天
		Date currDate = DateUtils.getDate();
		Date shipDate=new Date(currDate.getTime()+ 86400000);
		request.setAttribute("shiptoDate", DateUtils.format(shipDate, "yyyy-MM-dd"));
		request.setAttribute("shiptoDate", DateUtils.format(shipDate, "yyyy-MM-dd"));
				
		// 发票类型
		List<BaseDictItem> invoiceTypeList = orderInfoManager.findEntity(BaseDictItem.class, "from BaseDictItem where baseDict.dictId = 'invoiceType' ");
		if (orderInfo.getInvoiceType() != null) {
			request.setAttribute("invoiceType", orderInfo.getInvoiceType().getDictItemId());
		}
		request.setAttribute("invoiceTypes", invoiceTypeList);
		
		// 客户联系人信息
		List<MdmDistributorLinkman> distLinks = orderInfoManager.findEntity(MdmDistributorLinkman.class, "from MdmDistributorLinkman where mdmDistributor.distId= " + mdist.getDistId()
				+ " and  baseDictItem.dictItemId=" + orderInfo.getProdGroup().getDictItemId() + " and status=1");
		request.setAttribute("distLinks", distLinks);
		request.setAttribute("distLink", orderInfo.getMdmDistributorLinkman());
		
		
		// 付款方式
		List<BaseDictItem> paymentTypes = orderInfoManager.findEntity(BaseDictItem.class, "from BaseDictItem where baseDict.dictId = 'PAYMENT' ");
		if(orderInfo.getPaymentType()!=null){
		    request.setAttribute("paymentType", orderInfo.getPaymentType().getDictItemId());
		}
		request.setAttribute("paymentTypes", paymentTypes);
		
		// 运输方式
		List<BaseDictItem> modeOfTransportType = orderInfoManager.findEntity(BaseDictItem.class, "from BaseDictItem where state='1' and baseDict.dictId = 'MODE_OF_TRANSPORT' order by sort asc ");
		if (orderInfo.getModeOfTransportType()!=null) {
			request.setAttribute("modeOfTransportType", orderInfo.getModeOfTransportType().getDictItemId());
		}
		request.setAttribute("modeOfTransportTypes", modeOfTransportType);
		
		request.setAttribute("orderInfo",orderInfo);
		if (orderInfo.getProdGroup() != null) {
			request.setAttribute("itemBrandId", orderInfo.getProdGroup().getDictItemId());
		}
		
		//业代联系人信息
		request.setAttribute("distEmpId", orderInfo.getIndustryEmp()==null?null:orderInfo.getIndustryEmp().getEmpId());
		request.setAttribute("distEmpName", orderInfo.getIndustryEmp().getEmpName());
		request.setAttribute("distEmpTel", orderInfo.getIndustryEmp().getMobilePhone());
		
		request.setAttribute("memo",orderInfo.getMemo());

		// 回传页面
		List<OrderItemVO> voList = new ArrayList<OrderItemVO>();
		List<OrderItem> itemList = orderInfo.getOrderItems();
		int size = itemList.size();
		OrderItemVO vo = null;
		BigDecimal money=null;
		for (int i = 0; i < size; i++) {
			vo = new OrderItemVO();
			if (itemList.get(i).getMdmProduct() != null) {
				vo.setProdCode(itemList.get(i).getMdmProduct().getProdCode());
				vo.setProdName(itemList.get(i).getMdmProduct().getProdName());
			}

//			vo.setProdNum(formNum(itemList.get(i).getQuantity().toString()));
//			vo.setProdPrice(formNum(itemList.get(i).getTaxPrice().toString()));
//			vo.setProdAmount(formNum(itemList.get(i).getAmount().toString()));
			money=new BigDecimal(itemList.get(i).getQuantity());
			money= money.setScale(3, BigDecimal.ROUND_HALF_UP);
			vo.setProdNum(money.toString());
			money=new BigDecimal(itemList.get(i).getTaxPrice());
			money= money.setScale(3, BigDecimal.ROUND_HALF_UP);
			vo.setProdPrice(money.toString());
			money= itemList.get(i).getAmount() ;
			money= money.setScale(3, BigDecimal.ROUND_HALF_UP);
			vo.setProdAmount(money.toString());
			vo.setProdMemo(itemList.get(i).getMemo());
			vo.setProdUnit(itemList.get(i).getUnit());
			voList.add(vo);
		}
		request.setAttribute("voList", voList);
		
		return getForward(mapping, request, "update");
	}

	/**
	 * 订单制作重发保存
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward updateSp(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		if (isCancelled(request)) {
			return getForward(mapping, request, getSuccessView());
		}
		if (!isTokenValid(request)) {
			saveError(request, "mess.duplSubmit");
			return getForward(mapping, request, "listSp");
		}
		// 根据登录人获得客户信息
		BaseUser userInfo = (BaseUser) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		Long empId = null;
		if (userInfo != null && userInfo.getBaseEmployee() != null) {
			empId = userInfo.getBaseEmployee().getEmpId();
		}
		MdmDistributor mdist = mdmDistributorManager.getMdmDistributorByEmployeeId(empId);
		if (mdist == null) {
			doMessage(request, "您无权访问！");
			return getForward(mapping, request, "listSp");
		}
		request.setAttribute("dist", mdist);
		// 送货地址信息
		String shiptoId = request.getParameter("shiptoId");
		MdmDistributorAddress address = mdmDistributorAddressManager.findUniqueEntity("from MdmDistributorAddress where id = ? ", Long.valueOf(shiptoId));
		request.setAttribute("address", address);
		String brandId = request.getParameter("itemBrandId");
		if (brandId == null || "".equals(brandId.trim())) {
			doMessage(request, "请选择物料组！");
			return getForward(mapping, request, "listSp");
		}
		request.setAttribute("itemBrandId", brandId);
		
		
		// 发票类型
		request.setAttribute("invoiceType",request.getAttribute("invoiceType"));
		List<BaseDictItem> invoiceTypeList = orderInfoManager.findEntity(BaseDictItem.class, "from BaseDictItem where baseDict.dictId = 'invoiceType' ");
		request.setAttribute("invoiceTypes", invoiceTypeList);
		
		
		// 客户联系人信息
		MdmDistributorLinkman distLink=orderInfoManager.findUniqueEntity(MdmDistributorLinkman.class,"from MdmDistributorLinkman where id= "+ request.getParameter("distLink"));
		request.setAttribute("distLink", distLink);
		List<MdmDistributorLinkman> distLinks = orderInfoManager.findEntity(MdmDistributorLinkman.class, "from MdmDistributorLinkman where mdmDistributor.distId= " + mdist.getDistId()
				+ " and  baseDictItem.dictItemId=" + brandId + " and status=1");
		request.setAttribute("distLinks", distLinks);
		
		// 付款方式
		request.setAttribute("paymentType", request.getParameter("paymentType"));
		List<BaseDictItem> paymentTypes = orderInfoManager.findEntity(BaseDictItem.class, "from BaseDictItem where baseDict.dictId = 'PAYMENT' ");
		request.setAttribute("paymentTypes", paymentTypes);
		
		// 运输方式
		request.setAttribute("modeOfTransportType",request.getParameter("modeOfTransportType"));
		List<BaseDictItem> modeOfTransportTypes = orderInfoManager.findEntity(BaseDictItem.class, "from BaseDictItem where baseDict.dictId = 'MODE_OF_TRANSPORT' order by sort asc ");
		request.setAttribute("modeOfTransportTypes", modeOfTransportTypes);
		
		// 得到业代信息
		String distEmpId = request.getParameter("distEmpId")==null?"":request.getParameter("distEmpId");
		request.setAttribute("distEmpName", request.getParameter("distEmpName"));
		request.setAttribute("distEmpTel", request.getParameter("distEmpTel"));

		String memo = request.getParameter("memo");
		request.setAttribute("memo", memo);
		
		//订单项
		String items = request.getParameter("orderItems");
		JSONObject obj = JSONObject.fromObject(items);
		JSONArray array = obj.getJSONArray("dataInfo");
		int size = array.size();
		JSONObject temp = null;

		// 回传页面
		List<OrderItemVO> voList = new ArrayList<OrderItemVO>();
		OrderItemVO vo = null;
		for (int i = 0; i < size; i++) {
			vo = new OrderItemVO();
			temp = (JSONObject) array.get(i);
			vo.setProdCode(temp.getString("prodCode"));
			vo.setProdName(temp.getString("prodName"));
			vo.setProdUnit(temp.getString("prodUnit"));
			vo.setProdNum(temp.getString("prodNum"));
			vo.setProdPrice(temp.getString("prodPrice"));
			vo.setProdAmount(temp.getString("prodAmount"));
			vo.setProdMemo(temp.getString("prodMemo"));
			voList.add(vo);
		}
		request.setAttribute("voList", voList);

		

		String orderDate = request.getParameter("orderDate");
		String shiptoDate = request.getParameter("queryDate");
		request.setAttribute("orderDate", orderDate);
		request.setAttribute("shiptoDate", shiptoDate);
		Date oDate = null;
		try {
			oDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(orderDate);
		} catch (Exception e) {
			doMessage(request, "订货日期格式不正确！");
			return getForward(mapping, request, "update");
		}
		Date sDate = null;
		try {
			sDate = new SimpleDateFormat("yyyy-MM-dd").parse(shiptoDate);			
		} catch (Exception e) {
			doMessage(request, "送货日期格式不正确！");
			return getForward(mapping, request, "update");
		}
		
		if("".equals(distEmpId)){
		    doMessage(request, "业代信息不能为空！");
		    return getForward(mapping, request, "update");
		}

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < size; i++) {
			temp = (JSONObject) array.get(i);
			sb.append("'").append(temp.get("prodCode").toString()).append("',");
		}
		sb.deleteCharAt(sb.length() - 1);
		// 获得产品信息
		List<MdmProduct> prodList = orderInfoManager.findEntity(MdmProduct.class, "from MdmProduct where prodCode in (" + sb.toString() + ")");

		Map<String, MdmProduct> map = new HashMap<String, MdmProduct>();
		if (prodList.size() > 0) {
			for (MdmProduct objs : prodList) {
				map.put(objs.getProdCode(), objs);
			}
		}

		String orderId = request.getParameter("orderId") == null ? "" : request.getParameter("orderId").toString();
		List<OrderItem> itemList = new ArrayList<OrderItem>();
		OrderItem item = null;
		OrderInfo orderInfo = new OrderInfo();
		if (!"".equals(orderId)) {
			orderInfo = orderInfoManager.findUniqueEntity("from OrderInfo where orderId = ? ", Long.valueOf(orderId));
			itemList = orderInfo.getOrderItems();
			itemList.removeAll(itemList);
		}
		orderInfo.setMdmDistributor(mdist);
		orderInfo.setCustCode(mdist.getDistCode());
		orderInfo.setCustName(mdist.getDistName());
		
		//发票类型
		String typeId = request.getParameter("invoiceType");
		if (typeId != null && !"".equals(typeId.trim())) {
			BaseDictItem typeItem = orderInfoManager.findUniqueEntity(BaseDictItem.class, "from BaseDictItem where dictItemId = ? ", Long.valueOf(typeId));
			orderInfo.setInvoiceType(typeItem);
			request.setAttribute("invoiceType", typeId);
		}
		
		//付款方式
		BaseDictItem paymentType=this.baseDictManager.findUniqueEntity(BaseDictItem.class, "from BaseDictItem where dictItemId = ? ", Long.valueOf(request.getParameter("paymentType")));
		orderInfo.setPaymentType(paymentType);
		orderInfo.setPaymentName(paymentType.getItemName());
		
		//客户联系人
		orderInfo.setMdmDistributorLinkman(distLink);
		orderInfo.setCustTell(distLink.getLinkmanPhonenum());
		orderInfo.setCustLinkman(distLink.getLinkmanName());
		
		//送货地址
		orderInfo.setDistributorAddress(address);
		orderInfo.setShiptoCode(address.getShiptoCode());
		orderInfo.setShiptoLinkman(address.getContact());
		orderInfo.setShiptoName(address.getShiptoName());
		orderInfo.setShiptoTell(address.getTel());
		
		//运输方式
		BaseDictItem modeOfTransportType=this.baseDictManager.findUniqueEntity(BaseDictItem.class, "from BaseDictItem where dictItemId = ? ", Long.valueOf(request.getParameter("modeOfTransportType")));
		orderInfo.setModeOfTransportType(modeOfTransportType);
		orderInfo.setModeOfTransportName(modeOfTransportType.getItemName());

		//业代
		BaseEmployee emp = orderInfoManager.findUniqueEntity(BaseEmployee.class, "from BaseEmployee where empId = ? ", Long.valueOf(distEmpId));
		orderInfo.setIndustryEmp(emp);
		orderInfo.setIndustryMobile(request.getParameter("distEmpTel"));
		orderInfo.setIndustryName(request.getParameter("distEmpName"));
		request.setAttribute("industryEmp", emp);
		request.setAttribute("distEmpId", emp.getEmpId());
		request.setAttribute("distEmpName", emp.getEmpName());
		request.setAttribute("distEmpTel", emp.getMobilePhone());
		
		orderInfo.setMemo(memo);
		orderInfo.setInvoiceName(mdist.getDistName());
		 
		//订单项
		BigDecimal quantity=new BigDecimal(0),amount = new BigDecimal(0);
		 
		for (int i = 0; i < size; i++) {
			item = new OrderItem();
			temp = (JSONObject) array.get(i);
			item.setMdmProduct(map.get(temp.get("prodCode")));
			item.setUnit(temp.getString("prodUnit").toString());
			item.setQuantity(Double.valueOf(temp.get("prodNum").toString()));
			item.setTaxPrice(Double.valueOf(temp.get("prodPrice").toString()));
			item.setAmount(new BigDecimal(temp.get("prodAmount").toString()));
			item.setMemo(temp.get("prodMemo").toString());
			item.setOrderInfo(orderInfo);
			quantity=quantity.add(new BigDecimal(temp.getString("prodNum")));
			amount=amount.add( new BigDecimal(temp.getString("prodAmount")));
			 
			itemList.add(item);
		}
		orderInfo.setQuantity(   (quantity));
		orderInfo.setAmount(   (amount));
		orderInfo.setOrderItems(itemList);
		orderInfo.setShiptoDate(sDate);

		orderInfo.setOrderDate(oDate);
		
		// 订单状态：10：'未提交' 20：'请订单中心接收' 30：'已接收' 40：'订单有误已作废'
		String tempFlag = request.getParameter("tempFlag");
		if (tempFlag != null) {
			orderInfo.setStatus(10);
		} else {
			orderInfo.setStatus(20);
		}

		// 产品组、物料组选择
		BaseDictItem groupItem = orderInfoManager.findUniqueEntity(BaseDictItem.class, "from BaseDictItem where dictItemId = ? ", Long.valueOf(brandId));
		orderInfo.setProdGroup(groupItem);

		// save();
		try {
			orderInfoManager.save(orderInfo);
			 
		} catch (BusinessException e) {
			doMessage(request, e.getMessage());
			return getForward(mapping, request, "listSp");
		}
		doMessage(request, "保存成功！订单号：" + orderInfo.getOrderCode());
		request.setAttribute("orderId", orderInfo.getOrderId());
		request.setAttribute("orderCode", orderInfo.getOrderCode());

		if (tempFlag != null) {
			doMessage(request, "保存成功！订单号：" + orderInfo.getOrderCode());
			return getForward(mapping, request, "update");
		} else {
			if(orderInfo.getStatus().intValue()>10){
				SmsInfoBean smsInfo=new SmsInfoBean();
				smsInfo.setStatus(String.valueOf(orderInfo.getStatus().intValue()));
				smsInfo.setCustName(orderInfo.getCustName());
				smsInfo.setOrderCode(orderInfo.getOrderCode());
				smsInfo.setOrderNumber(orderInfo.getQuantity().toString());
				smsInfo.setPhoneNum(orderInfo.getIndustryMobile());
				smsInfo.setSalesName(orderInfo.getIndustryName());
				try {
					smsSendManager.sendMsg(smsInfo);
				} catch (Exception e) {
					doMessage(request, "发送成功！订单号：" + orderInfo.getOrderCode()+"<BR> 短信发送失败！");
					 
					return getForward(mapping, request, "index");
				}
			}
			doMessage(request, "发送成功！订单号：" + orderInfo.getOrderCode());
			return getForward(mapping, request, "listSp");
		}

	}

	protected void onInitEntity(ActionForm form, HttpServletRequest request, OrderInfo orderInfo) {

	}

	private List<String[]> parseParams(HttpServletRequest request) {
		Enumeration<?> parameters = request.getParameterNames();
		List<String[]> params = new ArrayList<String[]>();
		while (parameters.hasMoreElements()) {
			String name = (String) parameters.nextElement();
			if (name.startsWith(STORE_PREFIX)) {
				String value = request.getParameter(name);
				if (StringUtils.isNotBlank(value)) {
					String[] arr = value.split(SPLIT);
					if (arr.length == 2) {
						String date = request.getParameter(STARTDATE_PREFIX + arr[0]);
						if (StringUtils.isNotBlank(date)) {
							String[] temp = { arr[0], arr[1], date };
							params.add(temp);
						}
					}
				}
			}
		}
		return params;
	}
	
	//
	/**
	 * 扩大1000位
	 */
	private Long toThirdNum(String val){
	    String[] temp = val.split("\\.");
	    val = temp[0]+((temp[1] + "000").substring(0, 3));
	    return Long.valueOf(val);
	}
	/**
	 *  小数点左移3位
	 */
	
	private Double toDoubleNum(Long num){
	    String temp = "000"+num.toString();
	    temp = temp.substring(0,temp.length()-3)+"."+temp.substring(temp.length()-3);
	    
	    return Double.valueOf(temp);
	}
	
	/**
	 * 格式化数据，小数位3位
	 * 
	 * @param val
	 * @return
	 */
	private String formNum(String val) {
		String[] temp = val.split("\\.");
		if (temp.length == 2) {
			val = temp[0] + "." + ((temp[1] + "000").substring(0, 3));
		} else {
			val = val + ".000";
		}
		return val;
	}
	/**
	 * 乘法计算
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public  ActionForward numberMultiply(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String v1=request.getParameter("v1");
			String v2=request.getParameter("v2");
			String scale=request.getParameter("scale");
			Map jsonMap = new HashMap();
			jsonMap.put("data", com.winchannel.base.utils.NumberUtility.multiply(v1, v2, Integer.parseInt(scale) ));

			JsonConfig jsonConfig = new JsonConfig();

			JSONObject json = JSONObject.fromObject(jsonMap, jsonConfig);
			response.setContentType("text/html;CHARSET=GBK");
			response.setCharacterEncoding("GBK");
			response.getOutputStream().print(
					new String(json.toString().getBytes("GBK"), "ISO-8859-1"));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	public  ActionForward numberAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String v1=request.getParameter("v1");
			String v2=request.getParameter("v2");
			 
			Map jsonMap = new HashMap();
			jsonMap.put("data", com.winchannel.base.utils.NumberUtility.add(v1, v2  ));

			JsonConfig jsonConfig = new JsonConfig();

			JSONObject json = JSONObject.fromObject(jsonMap, jsonConfig);
			response.setContentType("text/html;CHARSET=GBK");
			response.setCharacterEncoding("GBK");
			response.getOutputStream().print(
					new String(json.toString().getBytes("GBK"), "ISO-8859-1"));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	//ajax判断是否有相同的。
	public ActionForward getDeliveryDays(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response){
		
		List<MdmProdGroupDeliveryDays> mdmProdGroupDeliveryDays= new ArrayList<MdmProdGroupDeliveryDays>();
		String itemBrandId = request.getParameter("itemBrandId");
		Date orderDate = DateUtility.strToDate(request.getParameter("orderDate"));
		Date queryDate = DateUtility.strToDate(request.getParameter("queryDate"));
		if(itemBrandId!=null&&!itemBrandId.equals("")){
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT DELIVERY_DAYS as deliveryDays FROM MDM_PROD_GROUP_DELIVERY_DAYS WHERE PROD_STRU_ID="+itemBrandId);
			mdmProdGroupDeliveryDays = mdmProdGroupDeliveryDaysManager.executeSqlQuery(MdmProdGroupDeliveryDays.class, sql.toString());
			if(mdmProdGroupDeliveryDays.size() > 0){
				  Date odate = new Date(orderDate.getTime() + (1000L * 60 * 60 * 24 * mdmProdGroupDeliveryDays.get(0).getDeliveryDays()));
				  //如送货日期大于物料组规定的送货天数
				  if(queryDate.getTime()>odate.getTime()){
					  this.renderXML(response, DateUtility.dateToStr(odate));
				  }
				  if(queryDate.getTime() < orderDate.getTime()){
					  this.renderXML(response, "orderDate");
				  }
			}else{
				this.renderXML(response, "");
			}
		}
		return null;
	}
}
