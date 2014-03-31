package com.winchannel.base.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.LazyValidatorForm;

import com.winchannel.base.service.BaseSecurityManager;
import com.winchannel.core.web.StrutsBaseAction;

public class ChangePassAction extends StrutsBaseAction {
	
	private BaseSecurityManager baseSecurityManager;
	public void setBaseSecurityManager(BaseSecurityManager baseSecurityManager) {
		this.baseSecurityManager = baseSecurityManager;
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LazyValidatorForm changePassForm = (LazyValidatorForm) form;
		
		if (!changePassForm.get("newPass").equals(changePassForm.get("newPass2"))) {
			this.saveError(request, "common.newPassinconsistent");
			return mapping.findForward("changePass");
		}
		
		try {
			baseSecurityManager.changePass(request.getParameter("userAccount"), request.getParameter("userPassword"), request.getParameter("newPass"), "1");
			this.saveError(request, "common.passChangeSuccess");
		}
		catch (Exception e) {
			this.saveDirectlyError(request, e.getMessage());
		}
		return mapping.findForward("changePass");
	}


}
