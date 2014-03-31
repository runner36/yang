package com.winchannel.base.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winchannel.base.utils.Constants;
import com.winchannel.core.web.StrutsBaseAction;

public class LogoutAction extends StrutsBaseAction {
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
        HttpSession session = request.getSession();
		session.removeAttribute(Constants.SESSION_USER_KEY);
		session.removeAttribute(Constants.SESSION_ROLE_KEY);
		session.removeAttribute(Constants.SESSION_AUTH_KEY);
		session.removeAttribute(Constants.SESSION_DATA_AUTH_KEY);
		session.removeAttribute(Constants.SESSION_MENU_KEY);
		
		if (EOrderActionListener.getOnLineMap().containsKey(session.getId())) {
			EOrderActionListener.getOnLineMap().remove(session.getId());
        }
		
        session.invalidate();
        return mapping.findForward("login");
	}

}
