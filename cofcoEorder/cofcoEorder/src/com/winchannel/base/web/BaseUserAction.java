package com.winchannel.base.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.LazyValidatorForm;

import com.winchannel.base.model.BaseUser;
import com.winchannel.base.service.BaseEmployeeManager;
import com.winchannel.base.service.BaseOrgManager;
import com.winchannel.base.service.BaseResourceManager;
import com.winchannel.base.service.BaseUserManager;
import com.winchannel.base.service.BaseUserRoleManager;
import com.winchannel.base.utils.Constants;
import com.winchannel.core.utils.Page;
import com.winchannel.core.utils.StringUtils;
import com.winchannel.core.web.StrutsEntityAction;
import com.winchannel.mdm.util.i18n.BeanMessage;

public class BaseUserAction extends StrutsEntityAction<BaseUser, BaseUserManager> {
	
	private BaseUserRoleManager baseUserRoleManager;
	private BaseEmployeeManager baseEmployeeManager;
	private BaseOrgManager baseOrgManager;
	private BaseResourceManager baseResourceManager;
	public void setBaseUserRoleManager(BaseUserRoleManager baseUserRoleManager) {
		this.baseUserRoleManager = baseUserRoleManager;
	}
	public void setBaseEmployeeManager(BaseEmployeeManager baseEmployeeManager) {
		this.baseEmployeeManager = baseEmployeeManager;
	}
	public void setBaseOrgManager(BaseOrgManager baseOrgManager) {
		this.baseOrgManager = baseOrgManager;
	}
	public void setBaseResourceManager(BaseResourceManager baseResourceManager) {
		this.baseResourceManager = baseResourceManager;
	}
	
	public void init(){
		this.setAuthPrefix("baseEmployee");
	}
	
	@Override
	protected void onInitList(ActionForm form, HttpServletRequest request, List list, Page page) {
		request.setAttribute("dutys", baseDictManager.getItems(Constants.DICT_DUTY));
	}
	
	@Override
	protected void onInitForm(ActionForm form, HttpServletRequest request, BaseUser baseUser) {
		LazyValidatorForm baseEmployeeForm = (LazyValidatorForm) form;
		if (baseUser.getBaseEmployee() != null) {
			baseEmployeeForm.set("empId", baseUser.getBaseEmployee().getEmpId().toString());
			baseEmployeeForm.set("empName", baseUser.getBaseEmployee().getEmpName() + "");
		}
		if (baseUser.getBaseResource() != null) {
			baseEmployeeForm.set("resId", baseUser.getBaseResource().getResId().toString());
			baseEmployeeForm.set("resName", baseUser.getBaseResource().getResName() + "");
		}
		if (baseUser.getUserId() != null) {
			request.setAttribute("roleValues", baseUserRoleManager.getRoleIdByUserId(baseUser.getUserId()));
		}
		
		if (StringUtils.isNotBlank(baseUser.getEmpAuth())) {
			baseEmployeeForm.set("empAuthNames", baseEmployeeManager.getEmployeesByIds(baseUser.getEmpAuth()));
		}
		if (StringUtils.isNotBlank(baseUser.getEmpCascadeAuth())) {
			baseEmployeeForm.set("empCascadeAuthNames", baseEmployeeManager.getEmployeesByIds(baseUser.getEmpCascadeAuth()));
		}
		if (StringUtils.isNotBlank(baseUser.getOrgAuth())) {
			baseEmployeeForm.set("orgAuthNames", baseOrgManager.getOrgsByIds(baseUser.getOrgAuth()));
		}
		if (baseUser.getUserId() == null) {
			baseEmployeeForm.set("state", "1");
		}
		
	}

	@Override
	protected void onInitEntity(ActionForm form, HttpServletRequest request, BaseUser baseUser) {
		baseUser.setBaseEmployee(baseEmployeeManager.get(new Long(request.getParameter("empId"))));
		
		if (StringUtils.isNotBlank(request.getParameter("resId"))) {
			baseUser.setBaseResource(baseResourceManager.get(new Long(request.getParameter("resId"))));
		}
		else {
			baseUser.setBaseResource(null);
		}
		
		String currEmpName = this.getCurrentUser(request).getBaseEmployee().getEmpName();
		if (baseUser.getUserId() == null) {
			baseUser.setCreatedBy(currEmpName);
			baseUser.setCreated(new Date());
		}
		baseUser.setUpdatedBy(currEmpName);
		baseUser.setUpdated(new Date());
	}
	
	protected void onSaveEntity(ActionForm form, HttpServletRequest request, BaseUser baseUser) {
		baseUserRoleManager.saveRolesOfUser(baseUser, request.getParameter("roleId"));
	}
	
	@Override
	protected void refrenceData(HttpServletRequest request) {
	}

	public ActionForward resetPass(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String str = this.entityManager.resetPass(new Long(request.getParameter("userId")));
		this.renderHtml(response, "<script>alert('"+BeanMessage.getLocaleMessage("i18n/messages", "common.passResetOk", request) + str + "');history.go(-1);</script>");
		return null;
	}

}
