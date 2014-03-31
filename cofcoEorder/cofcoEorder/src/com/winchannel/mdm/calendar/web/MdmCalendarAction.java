package com.winchannel.mdm.calendar.web;

import java.io.File;
import java.io.FileInputStream;
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
import com.winchannel.core.utils.DateUtils;
import com.winchannel.core.utils.StringUtils;
import com.winchannel.core.web.StrutsEntityAction;
import com.winchannel.mdm.calendar.model.MdmCalendar;
import com.winchannel.mdm.calendar.service.MdmCalendarManager;
import com.winchannel.mdm.product.model.MdmProduct;
import com.winchannel.mdm.product.service.MdmProductManager;
import com.winchannel.mdm.util.i18n.BeanMessage;

public class MdmCalendarAction extends StrutsEntityAction<MdmCalendar, MdmCalendarManager> {
	private MdmCalendarManager mdmCalendarManager;


	public MdmCalendarManager getMdmCalendarManager() {
		return mdmCalendarManager;
	}

	public void setMdmCalendarManager(MdmCalendarManager mdmCalendarManager) {
		this.mdmCalendarManager = mdmCalendarManager;
	}

	protected void onInitForm(ActionForm form, HttpServletRequest request, MdmProduct mdmProduct) {
		LazyValidatorForm form1 = (LazyValidatorForm) form;
		
	}
	
	protected void onInitEntity(ActionForm form, HttpServletRequest request, MdmProduct mdmProduct) 
	{
		
	}
	
	public ActionForward importProd(ActionMapping mapping, ActionForm form, HttpServletRequest request,HttpServletResponse response)
    {
		return mapping.findForward("importCalendar");
	}
	public ActionForward downProd(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
	{
		String mark = request.getParameter("mark");
		String mbPath = "/templet/企业日历导入模版.xls";
		String fileName = "企业日历导入模版";
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
	public ActionForward saveProd(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception
	{
    	String ExcelType = "application/vnd.ms-excel";
		String url = "/WEB-INF/pages/mdm/importCalendarList.jsp";
	
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
		List list=mdmCalendarManager.saveExcel(is,employee.getEmpName(),baseDictManager);
		is.close();
		request.setAttribute("errMsgList",list);

		return new ActionForward(url);

	}
	
}
