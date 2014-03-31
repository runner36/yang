package com.winchannel.mdm.distributor.web;

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

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.LazyValidatorForm;

import com.winchannel.base.conf.BaseConfigurator;
import com.winchannel.base.model.BaseDictItem;
import com.winchannel.base.model.BaseEmployee;
import com.winchannel.base.model.BaseRole;
import com.winchannel.base.model.BaseUser;
import com.winchannel.base.model.BaseUserRole;
import com.winchannel.base.service.BaseEmployeeManager;
import com.winchannel.base.service.BaseOrgManager;
import com.winchannel.base.service.BaseRoleManager;
import com.winchannel.base.service.BaseUserManager;
import com.winchannel.base.service.BaseUserRoleManager;
import com.winchannel.core.exception.BaseException;
import com.winchannel.core.exception.BusinessException;
import com.winchannel.core.utils.DateUtils;
import com.winchannel.core.utils.StringUtils;
import com.winchannel.core.web.StrutsEntityAction;
import com.winchannel.mdm.distributor.model.MdmDistributor;
import com.winchannel.mdm.distributor.service.MdmDistributorManager;
import com.winchannel.mdm.util.i18n.BeanMessage;

public class MdmDistributorAction extends
		StrutsEntityAction<MdmDistributor, MdmDistributorManager> {

	private final static String DIST_PREFIX = "dist_";
	private final static String STARTDATE_PREFIX = "startDate_";
	private final static String SPLIT = "_";
	private MdmDistributorManager mdmDistributorManager;
	private BaseOrgManager baseOrgManager;
	private BaseUserManager baseUserManager;
	private BaseEmployeeManager baseEmployeeManager;
	private BaseUserRoleManager baseUserRoleManager;
	private BaseRoleManager baseRoleManager;

	public MdmDistributorManager getMdmDistributorManager() {
		return mdmDistributorManager;
	}

	public void setMdmDistributorManager(
			MdmDistributorManager mdmDistributorManager) {
		this.mdmDistributorManager = mdmDistributorManager;
	}

	public BaseUserManager getBaseUserManager() {
		return baseUserManager;
	}

	public BaseEmployeeManager getBaseEmployeeManager() {
		return baseEmployeeManager;
	}

	public BaseUserRoleManager getBaseUserRoleManager() {
		return baseUserRoleManager;
	}

	public BaseRoleManager getBaseRoleManager() {
		return baseRoleManager;
	}

	public void setBaseUserRoleManager(BaseUserRoleManager baseUserRoleManager) {
		this.baseUserRoleManager = baseUserRoleManager;
	}

	public void setBaseRoleManager(BaseRoleManager baseRoleManager) {
		this.baseRoleManager = baseRoleManager;
	}

	public void setBaseUserManager(BaseUserManager baseUserManager) {
		this.baseUserManager = baseUserManager;
	}

	public void setBaseEmployeeManager(BaseEmployeeManager baseEmployeeManager) {
		this.baseEmployeeManager = baseEmployeeManager;
	}

	public BaseOrgManager getBaseOrgManager() {
		return baseOrgManager;
	}

	public void setBaseOrgManager(BaseOrgManager baseOrgManager) {
		this.baseOrgManager = baseOrgManager;
	}

	protected void init() {
		//this.setFirstQuery(false);
	}

	public ActionForward updateRemark(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String code = request.getParameter("code");
		String remark = request.getParameter("remark");
		if (null != code && null != remark) {
			entityManager.updateRemarkByCode(code, remark);
			this.renderText(response, "success");
		} else {
			this.renderText(response, "error");
		}

		return null;
	}

	protected void onInitForm(ActionForm form, HttpServletRequest request,
			MdmDistributor mdmDistributor) {
		LazyValidatorForm form1 = (LazyValidatorForm) form;
		if (mdmDistributor.getState() == null) {
			form1.set("state", "1");
		}
		if (mdmDistributor.getMdmDistributor() != null) {
			form1.set("parentDistId", mdmDistributor.getMdmDistributor()
					.getDistId().toString());
			form1.set("parentDistName", mdmDistributor.getMdmDistributor()
					.getDistName());
		}
		if (mdmDistributor.getBaseOrg() != null) {
			form1.set("orgId", mdmDistributor.getBaseOrg().getOrgId()
					.toString());
			form1.set("orgName", mdmDistributor.getBaseOrg().getOrgName());
		}
		if (mdmDistributor.getBaseDictItem() != null) {
			form1.set("geoId", mdmDistributor.getBaseDictItem().getDictItemId()
					.toString());
			form1.set("geoName", mdmDistributor.getBaseDictItem().getItemName());
		}
		if (mdmDistributor.getInvoiceType()!= null) {
			form1.set("invoiceTypeId", mdmDistributor.getInvoiceType().getDictItemId()
					.toString());
		}
	}

	protected void onInitEntity(ActionForm form, HttpServletRequest request,
			MdmDistributor mdmDistributor) {
		if (StringUtils.isNotBlank(request.getParameter("parentDistId"))) {
			mdmDistributor.setMdmDistributor(mdmDistributorManager.get(Long
					.valueOf(request.getParameter("parentDistId"))));
		} else {
			mdmDistributor.setMdmDistributor(null);
		}
		if (StringUtils.isNotBlank(request.getParameter("orgId"))) {
			mdmDistributor.setBaseOrg(baseOrgManager.get(Long.valueOf(request
					.getParameter("orgId"))));
		} else {
			mdmDistributor.setBaseOrg(null);
		}
		if (StringUtils.isNotBlank(request.getParameter("geoId"))) {
			mdmDistributor.setBaseDictItem(baseDictManager.get(Long
					.valueOf(request.getParameter("geoId"))));
		} else {
			mdmDistributor.setBaseDictItem(null);
		}
		
		if (StringUtils.isNotBlank(request.getParameter("invoiceTypeId"))) {
			mdmDistributor.setInvoiceType(baseDictManager.get(Long
					.valueOf(request.getParameter("invoiceTypeId"))));
		} else {
			mdmDistributor.setInvoiceType(null);
		}

		BaseEmployee employee = this.getCurrentUser(request).getBaseEmployee();
		mdmDistributor.setUpdatedBy(employee.getEmpName());
		mdmDistributor.setUpdated(DateUtils.getDate());
		if (mdmDistributor.getDistId() == null) {
			mdmDistributor.setCreatedBy(employee.getEmpName());
			mdmDistributor.setCreated(DateUtils.getDate());
		}
	}

	public ActionForward importProd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("importDistri");
	}

	public ActionForward downProd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String mark = request.getParameter("mark");
		String mbPath = "/templet/经销商导入模版.xls";
		String fileName = "经销商导入模版";
		String path = request.getSession().getServletContext()
				.getRealPath(mbPath);
		File downFile = new File(path);
		InputStream in;
		try {
			in = new FileInputStream(downFile);
			OutputStream os = response.getOutputStream();
			response.setContentType("xls");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ new String((fileName + ".xls").getBytes("GBK"),
							"ISO-8859-1"));
			int count = 0;
			byte[] buf = new byte[8096];
			while ((count = in.read(buf, 0, buf.length)) != -1) {
				os.write(buf, 0, count);

			}
			os.flush(); // 刷新页面输出流
			in.close(); // 关闭文件流程
			os.close(); // 关闭输出流，完成下载
		} catch (Exception e) {
			request.setAttribute(
					"errMsgList",
					"<font color='#FF0000'>"
							+ BeanMessage.getLocaleMessage("i18n/messages",
									"common.templatePathError", request)
							+ "!</font>");
		}

		return null;
	}

	public ActionForward saveProd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String ExcelType = "application/vnd.ms-excel";
		String url = "/WEB-INF/pages/mdm/importDistributorList.jsp";

		LazyValidatorForm bizImpExcelForm = (LazyValidatorForm) form;

		FormFile F = (FormFile) bizImpExcelForm.get("fileupload");
		// 如果没有上传文件即上传文件大小为0，就跳转会上传页
		if (F.getFileSize() == 0) {
			request.setAttribute(
					"errMsgList",
					"<font color='#FF0000'>"
							+ BeanMessage.getLocaleMessage("i18n/messages",
									"common.importFail", request)
							+ "：</font><br>"
							+ BeanMessage.getLocaleMessage("i18n/messages",
									"common.importFileEmpty", request)
							+ ".....");
			return new ActionForward(url);
		}
		// 得到上传文件名和大小
		if (!ExcelType.equals(F.getContentType())) {
			request.setAttribute(
					"errMsgList",
					"<font color='#FF0000'>"
							+ BeanMessage.getLocaleMessage("i18n/messages",
									"common.importFail", request)
							+ "：</font><br>"
							+ BeanMessage.getLocaleMessage("i18n/messages",
									"common.isNotExcelFile", request) + "！");
			return new ActionForward(url);
		}

		// 获得上传文件输入流
		InputStream is = F.getInputStream();
		// ===============解析Excel
		BaseEmployee employee = this.getCurrentUser(request).getBaseEmployee();
		List list = mdmDistributorManager.saveExcel(is, employee.getEmpName(),
				baseDictManager, baseOrgManager);
		is.close();
		request.setAttribute("errMsgList", list);

		return new ActionForward(url);

	}

	// public ActionForward checkOrg(ActionMapping mapping, ActionForm form,
	// HttpServletRequest request, HttpServletResponse response) {
	// Page page = new ECPage(request);
	// List list = mdmDistributorManager.getCheckOrgList(page);
	// int totalRows = page.getInt(Page.TOTAL_COUNT);
	// if (totalRows == 0) {
	// totalRows = list.size();
	// }
	// request.setAttribute("list", list);
	// request.setAttribute("totalRows", totalRows);
	// return mapping.findForward("checkDistOrg");
	// }

	public ActionForward saveCheckOrg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		List<String[]> params = parseParams(request);
		entityManager.saveCheckOrg(params);
		this.savedMessage(request, null);
		return list(mapping, form, request, response);
	}

	private List<String[]> parseParams(HttpServletRequest request) {
		Enumeration parameters = request.getParameterNames();
		List<String[]> params = new ArrayList<String[]>();
		while (parameters.hasMoreElements()) {
			String name = (String) parameters.nextElement();
			if (name.startsWith(DIST_PREFIX)) {
				String value = request.getParameter(name);
				if (StringUtils.isNotBlank(value)) {
					String[] arr = value.split(SPLIT);
					if (arr.length == 2) {
						String date = request.getParameter(STARTDATE_PREFIX
								+ arr[0]);
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
	
	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,	HttpServletResponse response) {
		Long empId=0L;
		try {
			if ((request.getParameter(idName) != null && request.getParameter(idName).length()>0) || (request.getParameter("id") != null &&  request.getParameter("id").length()>0) ) {
				MdmDistributor mdmDistributor=(MdmDistributor)doGetEntity(form, request);
				if (mdmDistributor!=null&&mdmDistributor.getBaseEmployee()!=null){
					empId=mdmDistributor.getBaseEmployee().getEmpId();
				}				
			}			
			onDeleteEntity(form, request);
			if (getErrors(request).size() > 0) {
				return getForward(mapping, request, getSuccessView());
			}
			doDeleteEntity(form, request);
			deletedMessage(request);
		} 
		catch (BusinessException e) {
			if (StringUtils.isNotBlank(e.getKey())) {
				saveError(request, e.getBundle(), e.getKey(), e.getValues());
			}
			else {
				saveError(request, "mess.deleteFailure");
			}
		}
		catch (Exception e) {
				if (e instanceof BaseException) {
					BaseException e1 = (BaseException) e;
					if (StringUtils.isNotBlank(e1.getKey())) {
					saveError(request, e1.getBundle(), e1.getKey(), e1.getValues());
				}
				else {
					saveError(request, "mess.deleteFailure");
				}
			}
			else {
				saveError(request, "mess.deleteFailure");
			}
		}
		//删除该经销商相关用户、人员
		if(empId>0 && getErrors(request).size() <= 0) {
			mdmDistributorManager.executeSqlUpdate("delete from BASE_USER_ROLE where user_id in (select user_id from base_user where emp_id="+empId+")");
			mdmDistributorManager.executeSqlUpdate("delete from  base_user where emp_id="+empId);
			mdmDistributorManager.executeSqlUpdate("delete from  BASE_EMPLOYEE where emp_id="+empId);
		}
		
		
		return getForward(mapping, request, getSuccessView());
	}
	
	protected void refrenceData(HttpServletRequest request) {
		//发票属性
		request.setAttribute("invoiceTypeList", baseDictManager.getItems("invoiceType", "1"));
	}

}
