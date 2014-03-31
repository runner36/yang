package com.winchannel.order.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.LazyValidatorForm;

import com.winchannel.base.model.BaseUser;
import com.winchannel.core.utils.ECPage;
import com.winchannel.core.utils.Page;
import com.winchannel.core.utils.StringUtils;
import com.winchannel.core.web.StrutsEntityAction;
import com.winchannel.mdm.distributor.model.MdmDistributor;
import com.winchannel.mdm.distributor.service.MdmDistributorManager;
import com.winchannel.order.model.MdmDistEmpProdgroup;
import com.winchannel.order.model.MdmDistOrderempProdgroup;
import com.winchannel.order.model.OrderSap;
import com.winchannel.order.service.OrderSapManager;

public class OrderSapAction extends StrutsEntityAction<OrderSap, OrderSapManager> {
	
	public static final String DIST_LIST = "/WEB-INF/pages/order/distSapList.jsp";
	public static final String ORDER_MEMBER_LIST = "/WEB-INF/pages/order/orderMemberSapList.jsp";
	public static final String ORG_LIST = "/WEB-INF/pages/order/orgSapList.jsp";
	public static final String SALES_MAN_LIST = "/WEB-INF/pages/order/salesmanSapList.jsp";

	private OrderSapManager orderSapManager;
	private MdmDistributorManager mdmDistributorManager;
	
	public OrderSapManager getOrderSapManager() {
		return orderSapManager;
	}

	public void setOrderSapManager(OrderSapManager orderSapManager) {
		this.orderSapManager = orderSapManager;
	}
	public MdmDistributorManager getMdmDistributorManager() {
		return mdmDistributorManager;
	}

	public void setMdmDistributorManager(MdmDistributorManager mdmDistributorManager) {
		this.mdmDistributorManager = mdmDistributorManager;
	}

	//经销商
	public ActionForward distList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		ECPage page = new ECPage(request);
		BaseUser baseUser = this.getCurrentUser(request);
		Long empId = baseUser.getBaseEmployee().getEmpId();

		List list = this.entityManager.distList(page, empId);
		int totalRows = page.getInt(Page.TOTAL_COUNT);
		if (totalRows == 0) {
			totalRows = list.size();
		}
		request.setAttribute("totalRows", totalRows);
		request.setAttribute("list", list);
		return new ActionForward(DIST_LIST);
	}
	//订单员
	public ActionForward orderMemberList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		ECPage page = new ECPage(request);
		
		BaseUser baseUser = this.getCurrentUser(request);
		Long createdByid = baseUser.getBaseEmployee().getEmpId();
		List list = this.entityManager.orderMemberList(page,createdByid);
		int totalRows = page.getInt(Page.TOTAL_COUNT);
		if (totalRows == 0) {
			totalRows = list.size();
		}
		request.setAttribute("totalRows", totalRows);
		request.setAttribute("list", list);
		return new ActionForward(ORDER_MEMBER_LIST);
	}
	//区域
	public ActionForward orgList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		ECPage page = new ECPage(request);
		
		BaseUser baseUser = this.getCurrentUser(request);
		String subCode = baseUser.getBaseEmployee().getBaseOrg().getSubCode();
		
		List list = this.entityManager.orgList(page, subCode);
		int totalRows = page.getInt(Page.TOTAL_COUNT);
		if (totalRows == 0) {
			totalRows = list.size();
		}
		request.setAttribute("totalRows", totalRows);
		request.setAttribute("list", list);
		return new ActionForward(ORG_LIST);
	}
	//业代
	public ActionForward salesmanList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		ECPage page = new ECPage(request);
		
		BaseUser baseUser = this.getCurrentUser(request);
		//经销商
		Long  empId = baseUser.getBaseEmployee().getEmpId();
		
		List list = this.entityManager.salesmanList(page, empId);
		int totalRows = page.getInt(Page.TOTAL_COUNT);
		if (totalRows == 0) {
			totalRows = list.size();
		}
		request.setAttribute("totalRows", totalRows);
		request.setAttribute("list", list);
		return new ActionForward(SALES_MAN_LIST);
	}
	
	
	
	@Override
	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		LazyValidatorForm orderSapForm = (LazyValidatorForm) form;
		OrderSap orderSap = orderSapManager.get(new Long(request.getParameter("id")));
		request.setAttribute("orderSapForm", orderSap);
		
		return mapping.findForward(VIEW);
	}

	//模版下载
	public ActionForward downStoreImpAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		String mbPath = "/templet/SAP订单模版.xls";
		String fileName = "SAP订单模版";
		String path = request.getSession().getServletContext().getRealPath(mbPath);
		File downFile = new File(path);
		InputStream in;
		try {
			in = new FileInputStream(downFile);
			OutputStream os = response.getOutputStream();
			response.setContentType("xls");
			response.setHeader("Content-Disposition", "attachment; filename=" + new String((fileName + ".xls").getBytes("GBK"), "ISO-8859-1"));
			int count = 0;
			byte[] buf = new byte[8096];
			while ((count = in.read(buf, 0, buf.length)) != -1) {
				os.write(buf, 0, count);

			}
			os.flush(); // 刷新页面输出流
			in.close(); // 关闭文件流程
			os.close(); // 关闭输出流，完成下载
		} catch (Exception e) {
			request.setAttribute("mess", "<font color='#FF0000'>模板路径不正确!</font>");
		}

		return mapping.findForward("imp");
	}

	public ActionForward importCon(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		return mapping.findForward("imp");
	} 
	//批量导入
	public ActionForward saveimpAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		String ExcelType = "application/vnd.ms-excel";
		
		LazyValidatorForm bizImpExcelForm = (LazyValidatorForm) form;
		FormFile F = (FormFile) bizImpExcelForm.get("fileupload");
		
		// 如果没有上传文件即上传文件大小为0，就跳转会上传页
		if (F.getFileSize() == 6656) {
			request.setAttribute("mess", "<font color='#FF0000'>导入失败：</font><br>导入文件为空.....");
			return mapping.findForward("imp");
		}
		// 得到上传文件名和大小
		if (!ExcelType.equals(F.getContentType())) {
			request.setAttribute("mess", "<font color='#FF0000'>导入失败：</font><br>文件不是Excel类型的或文件处于打开状态！");
			return mapping.findForward("imp");
		}
		// 获得上传文件输入流
		InputStream is = null;
		try {
			is = F.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		try {
			String mess = entityManager.saveExcel(is, request);
			request.setAttribute("mess", mess);
			
		} catch (Exception e) {
			this.saveDirectlyError(request, e.getMessage());
		}
		return mapping.findForward("imp");
	}
	
}
