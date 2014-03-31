package com.winchannel.mdm.conversion.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.LazyValidatorForm;

import com.winchannel.base.model.BaseEmployee;
import com.winchannel.core.utils.ECPage;
import com.winchannel.core.utils.Page;
import com.winchannel.core.web.StrutsEntityAction;
import com.winchannel.mdm.conversion.model.MdmUnitConversion;
import com.winchannel.mdm.conversion.service.MdmConversionManager;
import com.winchannel.mdm.product.service.MdmProductManager;
import com.winchannel.mdm.util.i18n.BeanMessage;

public class MdmConversionAction extends StrutsEntityAction<MdmUnitConversion, MdmConversionManager>{
	public static final String list_VIEW = "/WEB-INF/pages/mdm/mdmConversionList.jsp";
	private MdmConversionManager mdmConversionManager;
	private MdmProductManager mdmProductManager;
	public MdmConversionManager getMdmConversionManager() {   
		return mdmConversionManager;
	}
	public void setMdmConversionManager(MdmConversionManager mdmConversionManager) {
		this.mdmConversionManager = mdmConversionManager;   
	}

	public MdmProductManager getMdmProductManager() {
		return mdmProductManager;
	}
	public void setMdmProductManager(MdmProductManager mdmProductManager) {
		this.mdmProductManager = mdmProductManager;
	}
	
	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,HttpServletResponse response)
	{
		MdmUnitConversion conversionObj=new MdmUnitConversion();
		if(request.getParameter("convId")!=null && request.getParameter("convId").length()>0)
		{
			conversionObj.setConvId(Long.valueOf(request.getParameter("convId")));
		}
		conversionObj.setConvUnit1Val(Long.valueOf(request.getParameter("convValue1")));
		conversionObj.setConvUnit2Val(Double.valueOf(request.getParameter("convValue2")));
		conversionObj.setProdCode(request.getParameter("prodCode"));
		conversionObj.setConvUnit1Id((baseDictManager.get(Long.valueOf(request.getParameter("convValue1Id")))));
		conversionObj.setConvUnit2Id(baseDictManager.get(Long.valueOf(request.getParameter("convValue2Id"))));
		conversionObj.setRemark(request.getParameter("convUnit1Name")+" ("+request.getParameter("convValue1")+request.getParameter("convUnit1Name")+"="+request.getParameter("convValue2")+request.getParameter("convUnit2Name")+")");
		int flag=mdmConversionManager.saveConversion(conversionObj);
		try {
			if(flag==-1)
			{
				response.getWriter().println(-1);
			}else
			{
				response.getWriter().println(conversionObj.getConvId());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		//return  new ActionForward("/mdm/mdmConversion.do?method=convList&prodCode="+request.getParameter("prodCode"));
	}
	public  ActionForward del(ActionMapping mapping, ActionForm form, HttpServletRequest request,HttpServletResponse response)
	{
		mdmConversionManager.delConvById(Long.valueOf(request.getParameter("convId")));
		try {
			response.getWriter().println("1");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  null;
	}
	public ActionForward initEdit(ActionMapping mapping, ActionForm form, HttpServletRequest request,HttpServletResponse response)
	{
		MdmUnitConversion convObj = mdmConversionManager.getConvById(Long.valueOf(request.getParameter("convId")));
		request.setAttribute("convObj", convObj);
		return mapping.findForward("initedit");
	}
	public ActionForward convList(ActionMapping mapping, ActionForm form, HttpServletRequest request,HttpServletResponse response)
	{
		ECPage page = new ECPage(request);
		List list =  mdmConversionManager.getConvertList(page);
		int totalRows = page.getInt(Page.TOTAL_COUNT);
		if (totalRows == 0) {
			totalRows = list.size();
		}
		request.setAttribute("totalRows", totalRows);
		request.setAttribute("list", list);
		return new ActionForward(list_VIEW);
		
	/*	
		ECPage page = new ECPage(request, this.getPageName());
		page.put(Page.AUTH_PREFIX, this.getAuthPrefix());
		onInitPage(form, request, page);
		
		List list = null;
		if (!isFirstQuery() && "1".equals(request.getParameter("first"))) {
			list = new ArrayList();
		}
		else {
			list = mdmConversionManager.getConvertList(page);
		}
		
		initList(form, request, list, page);
		return page.initForward(mapping.findForward(LIST));*/
		
	}
	
	public ActionForward downConv(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
	{
		String mark = request.getParameter("mark");
		String mbPath = "/excel/单位转换导入模板.xls";
		String fileName = "单位转换导入模板";
		String path = request.getSession().getServletContext().getRealPath(mbPath);
		File downFile = new File(path);
		InputStream in;
		try
		{
			in = new FileInputStream(downFile);
			OutputStream os = response.getOutputStream();
			response.setContentType("xls");
			response.setHeader("Content-Disposition", "attachment; filename="+ new String((fileName + ".xls").getBytes("GBK"),"ISO-8859-1"));
			int count = 0;
			byte[] buf = new byte[8096];
			while ((count = in.read(buf, 0, buf.length)) != -1)
			{
				os.write(buf, 0, count);

			}
			os.flush(); // 刷新页面输出流
			in.close(); // 关闭文件流程
			os.close(); // 关闭输出流，完成下载
		} catch (Exception e)
		{
			request.setAttribute("errMsgList",
					"<font color='#FF0000'>"+BeanMessage.getLocaleMessage("i18n/messages", "common.templatePathError", request)+"!</font>");
		}

		return null;
	}
	
	public ActionForward saveConv(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception
	{
    	String ExcelType = "application/vnd.ms-excel";
		String url = "/WEB-INF/pages/mdm/importConvList.jsp";
	
		LazyValidatorForm bizImpExcelForm = (LazyValidatorForm) form;
		
		FormFile F = (FormFile) bizImpExcelForm.get("fileupload");
		// 如果没有上传文件即上传文件大小为0，就跳转会上传页
		if (F.getFileSize() == 0)
		{
			request.setAttribute("errMsgList","<font color='#FF0000'>"+BeanMessage.getLocaleMessage("i18n/messages", "common.importFail", request)+"：</font><br>"+BeanMessage.getLocaleMessage("i18n/messages", "common.importFileEmpty", request)+".....");
			return new ActionForward(url);
		}
		// 得到上传文件名和大小
		if (!ExcelType.equals(F.getContentType()))
		{
		    request.setAttribute("errMsgList","<font color='#FF0000'>"+BeanMessage.getLocaleMessage("i18n/messages", "common.importFail", request)+"：</font><br>"+BeanMessage.getLocaleMessage("i18n/messages", "common.isNotExcelFile", request)+"！");
			return new ActionForward(url);
		}
		// 获得上传文件输入流
		InputStream is = F.getInputStream();
		// ===============解析Excel
		BaseEmployee employee = this.getCurrentUser(request).getBaseEmployee();
		List list=mdmConversionManager.saveExcel(is,employee.getEmpName());
		is.close();
		request.setAttribute("errMsgList",list);
		return new ActionForward(url);
	}
	
	public ActionForward importConv(ActionMapping mapping, ActionForm form, HttpServletRequest request,HttpServletResponse response)
    {
		return mapping.findForward("importConv");
	}
	
}
