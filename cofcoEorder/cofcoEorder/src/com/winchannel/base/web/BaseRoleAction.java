package com.winchannel.base.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.validator.LazyValidatorForm;

import com.winchannel.base.model.BaseRole;
import com.winchannel.base.service.BaseAuthManager;
import com.winchannel.base.service.BaseDataAuthManager;
import com.winchannel.base.service.BaseRoleManager;
import com.winchannel.core.utils.Page;
import com.winchannel.core.web.StrutsEntityAction;

public class BaseRoleAction extends StrutsEntityAction<BaseRole, BaseRoleManager> {
	
	private BaseAuthManager baseAuthManager;
	private BaseDataAuthManager baseDataAuthManager;
	public void setBaseAuthManager(BaseAuthManager baseAuthManager) {
		this.baseAuthManager = baseAuthManager;
	}
	public void setBaseDataAuthManager(BaseDataAuthManager baseDataAuthManager) {
		this.baseDataAuthManager = baseDataAuthManager;
	}
	
	@Override
	protected void onInitList(ActionForm form, HttpServletRequest request, List list, Page page) {
	}
	
	@Override
	protected void onInitForm(ActionForm form, HttpServletRequest request, BaseRole baseRole) {
		LazyValidatorForm baseRoleForm = (LazyValidatorForm) form;
		if (baseRole.getRoleId() != null) {
			request.setAttribute("resValues", baseAuthManager.getResIdByRoleId(baseRole.getRoleId()));
		}
		if (baseRole.getBaseDataAuth() != null) {
			baseRoleForm.set("dataAuthId", baseRole.getBaseDataAuth().getDataAuthId().toString());
		}
		if (baseRole.getRoleId() == null) {
			baseRoleForm.set("state", "1");
		}
	}

	@Override
	protected void onInitEntity(ActionForm form, HttpServletRequest request, BaseRole baseRole) {
		if (request.getParameter("dataAuthId") != null) {
			baseRole.setBaseDataAuth(baseDataAuthManager.get(new Long(request.getParameter("dataAuthId"))));
		}
	}
	
	protected void onSaveEntity(ActionForm form, HttpServletRequest request, BaseRole baseRole) {
		baseAuthManager.saveResourcesOfRole(baseRole, request.getParameter("resId"));
	}
	
	@Override
	protected void refrenceData(HttpServletRequest request) {
		request.setAttribute("dataAuths", baseDataAuthManager.getAll());
	}

	
}
