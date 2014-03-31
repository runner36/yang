package com.winchannel.mdm.empstore.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.LazyValidatorForm;

import com.winchannel.base.model.BaseEmployee;
import com.winchannel.base.service.BaseOrgManager;
import com.winchannel.core.utils.DateUtils;
import com.winchannel.core.utils.StringUtils;
import com.winchannel.core.web.StrutsEntityAction;
import com.winchannel.mdm.distributor.model.MdmDistributor;
import com.winchannel.mdm.distributor.service.MdmDistributorManager;
import com.winchannel.mdm.empstore.model.MdmEmpStore;
import com.winchannel.mdm.empstore.service.MdmEmpStoreManager;

public class MdmEmpStoreAction extends StrutsEntityAction<MdmEmpStore, MdmEmpStoreManager> {
	
	private BaseOrgManager baseOrgManager;

	public BaseOrgManager getBaseOrgManager() {
		return baseOrgManager;
	}

	public void setBaseOrgManager(BaseOrgManager baseOrgManager) {
		this.baseOrgManager = baseOrgManager;
	}

	protected void onInitForm(ActionForm form, HttpServletRequest request, MdmEmpStore mdmEmpStore) {
		LazyValidatorForm form1 = (LazyValidatorForm) form;
//		if (mdmEmpStore.getState() == null) {
//			form1.set("state", "1");
//		}
//		if (mdmEmpStore.getMdmDistributor() != null) {
//			form1.set("parentDistId", mdmEmpStore.getMdmDistributor().getDistId().toString());
//			form1.set("parentDistName", mdmEmpStore.getMdmDistributor().getDistName());
//		}
//		if (mdmEmpStore.getBaseOrg() != null) {
//			form1.set("orgId", mdmEmpStore.getBaseOrg().getOrgId().toString());
//			form1.set("orgName", mdmEmpStore.getBaseOrg().getOrgName());
//		}
//		if (mdmEmpStore.getBaseDictItem() != null) {
//			form1.set("geoId", mdmEmpStore.getBaseDictItem().getDictItemId().toString());
//			form1.set("geoName", mdmEmpStore.getBaseDictItem().getItemName());
//		}
	}

	protected void onInitEntity(ActionForm form, HttpServletRequest request, MdmEmpStore mdmEmpStore) {
//		if (StringUtils.isNotBlank(request.getParameter("parentDistId"))) {
//			mdmEmpStore.setMdmDistributor(mdmDistributorManager.get(Long.valueOf(request.getParameter("parentDistId"))));
//		} else {
//			mdmEmpStore.setMdmDistributor(null);
//		}
//		if (StringUtils.isNotBlank(request.getParameter("orgId"))) {
//			mdmEmpStore.setBaseOrg(baseOrgManager.get(Long.valueOf(request.getParameter("orgId"))));
//		} else {
//			mdmEmpStore.setBaseOrg(null);
//		}
//		if (StringUtils.isNotBlank(request.getParameter("geoId"))) {
//			mdmEmpStore.setBaseDictItem(baseDictManager.get(Long.valueOf(request.getParameter("geoId"))));
//		} else {
//			mdmEmpStore.setBaseDictItem(null);
//		}
//
//		BaseEmployee employee = this.getCurrentUser(request).getBaseEmployee();
//		mdmEmpStore.setUpdatedBy(employee.getEmpName());
//		mdmEmpStore.setUpdated(DateUtils.getDate());
//		if (mdmEmpStore.getDistId() == null) {
//			mdmEmpStore.setCreatedBy(employee.getEmpName());
//			mdmEmpStore.setCreated(DateUtils.getDate());
//		}
//		mdmDistributorManager.save(mdmEmpStore);
	}

//	public ActionForward importProd(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
//		return mapping.findForward("importDistri");
//	}
//
//	public ActionForward downProd(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
//		String mark = request.getParameter("mark");
//		String mbPath = "/templet/经销商导入模版.xls";
//		String fileName = "经销商导入模版";
//		String path = request.getSession().getServletContext().getRealPath(mbPath);
//		File downFile = new File(path);
//		InputStream in;
//		try {
//			in = new FileInputStream(downFile);
//			OutputStream os = response.getOutputStream();
//			response.setContentType("xls");
//			response.setHeader("Content-Disposition", "attachment; filename=" + new String((fileName + ".xls").getBytes("GBK"), "ISO-8859-1"));
//			int count = 0;
//			byte[] buf = new byte[8096];
//			while ((count = in.read(buf, 0, buf.length)) != -1) {
//				os.write(buf, 0, count);
//
//			}
//			os.flush(); // 刷新页面输出流
//			in.close(); // 关闭文件流程
//			os.close(); // 关闭输出流，完成下载
//		} catch (Exception e) {
//			request.setAttribute("errMsgList", "<font color='#FF0000'>模板路径不正确!</font>");
//		}
//
//		return null;
//	}
//
//	public ActionForward saveProd(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		String ExcelType = "application/vnd.ms-excel";
//		String url = "/WEB-INF/pages/mdm/importDistributorList.jsp";
//
//		LazyValidatorForm bizImpExcelForm = (LazyValidatorForm) form;
//
//		FormFile F = (FormFile) bizImpExcelForm.get("fileupload");
//		// 如果没有上传文件即上传文件大小为0，就跳转会上传页
//		if (F.getFileSize() == 0) {
//			request.setAttribute("errMsgList", "<font color='#FF0000'>导入失败：</font><br>导入文件为空.....");
//			return new ActionForward(url);
//		}
//		// 得到上传文件名和大小
//		if (!ExcelType.equals(F.getContentType())) {
//			request.setAttribute("errMsgList", "<font color='#FF0000'>导入失败：</font><br>文件不是Excel类型的或文件处于打开状态！");
//			return new ActionForward(url);
//		}
//
//		// 获得上传文件输入流
//		InputStream is = F.getInputStream();
//		// ===============解析Excel
//		BaseEmployee employee = this.getCurrentUser(request).getBaseEmployee();
//		List list = mdmDistributorManager.saveExcel(is, employee.getEmpName(), baseDictManager, baseOrgManager);
//		is.close();
//		request.setAttribute("errMsgList", list);
//
//		return new ActionForward(url);
//
//	}

}
