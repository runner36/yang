package com.winchannel.base.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.LazyValidatorForm;
import com.winchannel.base.bean.UserInfo;
import com.winchannel.base.conf.BaseConfigurator;
import com.winchannel.base.model.BaseOrg;
import com.winchannel.base.model.BaseUser;
import com.winchannel.base.service.BaseSecurityManager;
import com.winchannel.base.utils.Constants;
import com.winchannel.core.exception.SafetyException;
import com.winchannel.core.utils.MessageResourceUtils;
import com.winchannel.core.utils.StringUtils;
import com.winchannel.core.web.StrutsBaseAction;
import com.winchannel.mdm.distributor.model.MdmDistributor;
import com.winchannel.mdm.distributor.service.MdmDistributorManager;
import com.winchannel.mdm.util.i18n.BeanMessage;
import com.winchannel.order.model.OrderAgreementLog;
import com.winchannel.order.service.OrderAgreementLogManager;

public class InvalidateSessionAction extends StrutsBaseAction {
	public ActionForward invalidateSession(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		BaseUser baseUser=(BaseUser)session.getAttribute(Constants.SESSION_USER_KEY);
		EOrderActionListener.invalidateOtherSession(String.valueOf(baseUser.getUserId()),session.getId());
		return null;
	}	
}
