package com.winchannel.order.web;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.winchannel.base.model.BaseDictItem;
import com.winchannel.core.utils.ECPage;
import com.winchannel.core.utils.Page;
import com.winchannel.core.web.StrutsEntityAction;
import com.winchannel.base.utils.DateUtility;
import com.winchannel.order.model.OrderInfo;
import com.winchannel.order.model.OrderItem;
import com.winchannel.order.service.MdmDistEmpProdgroupManager;
import com.winchannel.order.service.OrderInfoManager;
import com.winchannel.order.service.OrderItemManager;
import com.winchannel.task.bean.SmsInfoBean;
import com.winchannel.task.service.SmsSendManager;

public class OrderMemberOrderAction extends	StrutsEntityAction<OrderInfo, OrderInfoManager> {		
	private OrderInfoManager orderInfoManager;
	private OrderItemManager orderItemManager;
	private MdmDistEmpProdgroupManager mdmDistEmpProdgroupManager;
	private SmsSendManager smsSendManager;
	
	// 列开始
	public static final int START_ROW = 0;

	// 行开始
	public static final int START_CLUM = 10;
	public OrderItemManager getOrderItemManager() {
		return orderItemManager;
	}
	public void setOrderItemManager(OrderItemManager orderItemManager) {
		this.orderItemManager = orderItemManager;
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
	public SmsSendManager getSmsSendManager() {
		return smsSendManager;
	}
	public void setSmsSendManager(SmsSendManager smsSendManager) {
		this.smsSendManager = smsSendManager;
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
		if ("1".equals(request.getParameter("first"))) {
			page.put("status_","20");
		}
		page.put("empId", getCurrentUser(request).getBaseEmployee().getEmpId().toString());		
		onInitPage(form, request, page);		
		List list = null;
		if (!isFirstQuery() && "1".equals(request.getParameter("first"))) {
			list = new ArrayList();
		}
		else {
			list = doListEntity(page);
		}
				
		initList(form, request, list, page);
		HttpSession session = request.getSession();
		session.setAttribute("actionPath", "orderMemberOrder.do?method=list");
		return page.initForward(mapping.findForward(LIST));
	}

	
	protected List doListEntity(Page page) {
		return entityManager.queryOrderListForOrderMember(page);
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
					//添加订单审批时间 liuguangshuai 2012-9-8
					if("30".equals(request.getParameter("status")) || "40".equals(request.getParameter("status"))){
						orderInfo.setOrderApproveDate(Calendar.getInstance().getTime());
					}
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
					if(null!=orderInfo.getMdmDistributorLinkman().getIsSms()&&orderInfo.getMdmDistributorLinkman().getIsSms()==1){
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
		//orderInfo.setOrderId(Long.valueOf(request.getParameter("orderId")));
		//orderInfo.setStatus(Integer.valueOf(request.getParameter("status")));
	}
	
	/**
	 * 根据Ids批量审批订单
	 */
	public ActionForward batchApproveById(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		String orderIds = request.getParameter("flag");

		response.setCharacterEncoding("UTF-8");
		PrintWriter wr = null;
		String exceptionMsg=null;
		try {
			wr = response.getWriter();
			orderInfoManager.batchApproveById(orderIds);
			wr.write("SUCCESS");
		} catch (Exception e) {
			exceptionMsg="批量审批订单失败!/n"+e.getMessage();
			log.error("批量审批订单失败!", e);
		}finally {
			if (wr != null) {
				if(StringUtils.isNotEmpty(exceptionMsg)){
					wr.write(exceptionMsg);
				}
				wr.flush();
				wr.close();
			}
		}
		return null;
	}
	/**
	 * 查询待处理订单数
	 */
	public ActionForward qryWaitToDealOrderCount(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		PrintWriter wr = null;
		String exceptionMsg=null;
		try {
			wr = response.getWriter();
			Integer orderCount=orderInfoManager.qryWaitToDealOrderCount(getCurrentUser(request).getBaseEmployee().getEmpId());
			wr.write(orderCount.toString());
		} catch (Exception e) {
			exceptionMsg="error";
			log.error("获取待处理订单失败!", e);
		}finally {
			if (wr != null) {
				if(StringUtils.isNotEmpty(exceptionMsg)){
					wr.write(exceptionMsg);
				}
				wr.flush();
				wr.close();
			}
		}
		return null;
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
	/**
	 * 打印导出模板
	 */
	public ActionForward downOrderInfoTemplet(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response){
		String orderIds=request.getParameter("checkboxItems");
		String Id[]=orderIds.split(",");
		String fileName="";
		Workbook book = null;
		WritableWorkbook wbook = null;
		WritableSheet wsheet0 = null;
		File file = null;
		File targetFile = null;
		try {
			String path = request.getSession().getServletContext().getRealPath("/templet/order-打印导出模板.xls");
			log.info("开始下载模板-->");
			fileName = "电子订单";
			String tempPath = getClientInformationExcelPath(request, fileName);
			file = new File(path); // 定义一个新的文件对象
			book = Workbook.getWorkbook(file); // 获取工作薄对象
			targetFile = new File(tempPath); // 创建输出流，往工作薄里写数据
			wbook = Workbook.createWorkbook(targetFile, book);
			WritableFont wf = new WritableFont(WritableFont.ARIAL, 11, WritableFont.NO_BOLD, false); // 创建小字体：Arial,大小为8号,非粗体，非斜体
			WritableFont wfBold = new WritableFont(WritableFont.ARIAL, 11, WritableFont.BOLD, false); // 创建小字体：Arial,大小为8号,非粗体，非斜体
			WritableCellFormat RwcfF = new jxl.write.WritableCellFormat(wf);
			RwcfF.setAlignment(Alignment.LEFT); // 居左
			RwcfF.setVerticalAlignment(VerticalAlignment.CENTRE); // 竖直居中
			WritableCellFormat wc = new WritableCellFormat(wf);
			WritableCellFormat wcBold = new WritableCellFormat(wfBold);
			wc.setBorder(Border.ALL, BorderLineStyle.THIN); // 设置边框线
			wcBold.setBorder(Border.ALL, BorderLineStyle.THIN); // 设置边框线
			if(Id.length>1){
				for(int i=1;i<Id.length;i++){
					wbook.copySheet(0, "模板", i);
				}
			}
			String imgPath = request.getSession().getServletContext().getRealPath("/images/order.png");
			File imgFile = new File(imgPath);
			if(imgFile.exists()){
				BufferedImage input = ImageIO.read(imgFile);
				File outputFile = new File(imgPath);
				ImageIO.write(input, "PNG", outputFile);
			if(outputFile.exists()&&outputFile.length()>0){
				
			for(int i=0;i<Id.length;i++){
				String orderId = Id[i];
				OrderInfo info = this.orderInfoManager.getOrderInfo(Long.valueOf(orderId));
				BaseDictItem baseitem = (BaseDictItem)this.entityManager.get(BaseDictItem.class, info.getInvoiceType().getDictItemId());
				List<OrderItem> itemList = this.orderInfoManager.getOrderItemList(Long.valueOf(orderId));
				
					wsheet0 = wbook.getSheet(i); // 目标写入sheet--i
					wsheet0.setName(info.getOrderCode());
					//写入图片
					WritableImage image = new WritableImage(7, 0, 1, 1, outputFile);
					wsheet0.addImage(image);
	
					// 写入主表信息
					wsheet0.addCell(new jxl.write.Label(1, 1, info.getMdmDistributor().getDistName(), wc));//订货客户名称
					wsheet0.addCell(new jxl.write.Label(3, 1, info.getMdmDistributor().getDistCode(), wc));//客户编号
					wsheet0.addCell(new jxl.write.Label(5, 1, info.getOrderCode(), wc));//订货编号
					wsheet0.addCell(new jxl.write.Label(1, 2, info.getShiptoCode()+"_"+info.getShiptoName(), wc));//收货客户名称
					wsheet0.addCell(new jxl.write.Label(3, 2, info.getModeOfTransportName(), wc));//运输方式
					wsheet0.addCell(new jxl.write.Label(5, 2, DateUtility.dateToStr(info.getOrderDate()), wc));//订货日期
					wsheet0.addCell(new jxl.write.Label(1, 3, info.getDistributorAddress().getShiptoAdd(), wc));//收货客户地址
					wsheet0.addCell(new jxl.write.Label(3, 3, info.getPaymentName(), wc));//结算方式
					wsheet0.addCell(new jxl.write.Label(5, 3, DateUtility.dateToStr(info.getShiptoDate()), wc));//要求送货日期
					wsheet0.addCell(new jxl.write.Label(1, 4, info.getShiptoLinkman()+info.getShiptoTell(), wc));//收货客户信息
					wsheet0.addCell(new jxl.write.Label(3, 4, baseitem.getItemName(), wc));//发票类型
					wsheet0.addCell(new jxl.write.Label(5, 4, "", wc));//发票传递
					
//					wsheet0.addCell(new jxl.write.Label(1, 5, info.getCustName(), wc));//开票客户名称
					wsheet0.addCell(new jxl.write.Label(1, 5, "", wc));//开票客户名称
//					wsheet0.addCell(new jxl.write.Label(3, 5, info.getDistributorAddress().getFactoryDelivery(), wc));//出库仓库
					wsheet0.addCell(new jxl.write.Label(3, 5, "", wc));//出库仓库
					wsheet0.addCell(new jxl.write.Label(1, 6, info.getMemo(), wc));//备   注
					wsheet0.addCell(new jxl.write.Label(3, 6, "", wc));//SAP订单号
					wsheet0.addCell(new jxl.write.Label(1, 7, info.getMemoApp(), wc));//主--作废备注
					wsheet0.addCell(new jxl.write.Label(1, 8, info.getMemoOrder(), wc));//主--审批明细备注
					
					int j = 0;
					for (OrderItem item:itemList) {
						wsheet0.addCell(new jxl.write.Label(START_ROW,START_CLUM + j, String.valueOf(j+1), wcBold)); //序号
						wsheet0.addCell(new jxl.write.Label(START_ROW+1,START_CLUM + j, item.getMdmProduct().getProdName(), wcBold)); //物料名称
						wsheet0.addCell(new jxl.write.Label(START_ROW+2,START_CLUM + j, item.getMdmProduct().getProdCode(), wcBold)); //物料号
						wsheet0.addCell(new jxl.write.Label(START_ROW+3,START_CLUM + j, String.valueOf(item.getQuantity()), wcBold)); //数量（箱）
						wsheet0.addCell(new jxl.write.Label(START_ROW+4,START_CLUM + j, String.valueOf(item.getTaxPrice()), wcBold)); //含税单价(元/箱)
						wsheet0.addCell(new jxl.write.Label(START_ROW+5,START_CLUM + j, String.valueOf(item.getAmount()), wcBold)); //金额（元）
						wsheet0.addCell(new jxl.write.Label(START_ROW+6,START_CLUM + j, item.getMemo(), wcBold));//子--订单明细备注
						wsheet0.addCell(new jxl.write.Label(START_ROW+7,START_CLUM + j, item.getMemoOrder(), wcBold));//子--审批明细备注
						
						wsheet0.addCell(new jxl.write.Label(START_ROW,START_CLUM + j+1, "总计", wcBold));
						wsheet0.addCell(new jxl.write.Label(START_ROW+1,START_CLUM + j+1, "", wcBold));
						wsheet0.addCell(new jxl.write.Label(START_ROW+2,START_CLUM + j+1, "", wcBold));
						wsheet0.addCell(new jxl.write.Label(START_ROW+4,START_CLUM + j+1, "", wcBold));
						
						wsheet0.addCell(new jxl.write.Label(START_ROW+3,START_CLUM + j+1, String.valueOf(info.getQuantity()), wcBold)); //总计：数量
						wsheet0.addCell(new jxl.write.Label(START_ROW+5,START_CLUM + j+1, String.valueOf(info.getAmount()), wcBold)); //总计：金额
						
						wsheet0.addCell(new jxl.write.Label(START_ROW+6,START_CLUM + j+1, "", wcBold));
						wsheet0.addCell(new jxl.write.Label(START_ROW+7,START_CLUM + j+1, "", wcBold));
						j++;
					}
	
				}
				}
			}
			
				wbook.write();
				wbook.close();
				} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// 关闭文件输出流
				try {
					book.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			InputStream in = null;
			OutputStream os = null;

			try {
				in = new FileInputStream(targetFile);
				os = response.getOutputStream();
				response.setContentType("xls");
				response.setHeader("Content-Disposition", "attachment; filename=" + new String((fileName + ".xls").getBytes("GBK"), "ISO-8859-1"));
				int count = 0;
				byte[] buf = new byte[8096];
				while ((count = in.read(buf, 0, buf.length)) != -1) {
					os.write(buf, 0, count);
				}
			} catch (Exception e) {
				e.printStackTrace();

			} finally {
				try {
					os.flush(); // 刷新页面输出流
					in.close(); // 关闭文件流程
					os.close(); // 关闭输出流，完成下载
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			log.info("下载模板结束-->");
		return null;
	}
	public String getClientInformationExcelPath(HttpServletRequest request, String fileName) throws Exception {
		
		Date date = new Date();
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
		String dateName = simpledateformat.format(date);
		String path = request.getSession().getServletContext().getRealPath("/logs/" + dateName + "_" + fileName + ".xls");
		return path;
	}
	
}
