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

public class LoginAction extends StrutsBaseAction {

	private BaseSecurityManager baseSecurityManager;
	private MdmDistributorManager mdmDistributorManager;
	private OrderAgreementLogManager orderAgreementLogManager;
	public void setOrderAgreementLogManager( OrderAgreementLogManager orderAgreementLogManager) {
		this.orderAgreementLogManager = orderAgreementLogManager;
	}
	public void setMdmDistributorManager(MdmDistributorManager mdmDistributorManager) {
		this.mdmDistributorManager = mdmDistributorManager;
	}
	public void setBaseSecurityManager(BaseSecurityManager baseSecurityManager) {
		this.baseSecurityManager = baseSecurityManager;
	}
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LazyValidatorForm loginForm = (LazyValidatorForm) form;
		HttpSession session = request.getSession();
		if (session.getAttribute(Constants.SESSION_USER_KEY) != null) {
			return mapping.findForward("index");
		}
		if (loginForm.get("rand") == null) {
			return mapping.findForward("login");
		}

		if (!loginForm.get("rand").equals(session.getAttribute("rand"))) {
			// this.saveDirectlyError(request, "验证码输入错误");
			this.saveError(request, "common.Verificationcode");
			return mapping.findForward("login");
		}

		UserInfo userInfo = null;
		String key = "LN_" + request.getParameter("userAccount");
		try {
			Integer loginNum = 1;
			if (session.getAttribute(key) != null) {
				loginNum = ((Integer) session.getAttribute(key)) + 1;
			}
			session.setAttribute(key, loginNum);
			userInfo = baseSecurityManager.login(
					request.getParameter("userAccount"),
					request.getParameter("userPassword"), "1", loginNum);
		} catch (Exception e) {
			if (e instanceof SafetyException) {
				SafetyException e1 = (SafetyException) e;
				if (StringUtils.isNotBlank(e1.getKey())) {
					saveError(request, e1.getBundle(), e1.getKey(),
							e1.getValues());
				} else {
					saveDirectlyError(request, e.getMessage());
				}
			} else {
				saveDirectlyError(request, e.getMessage());
			}
			return mapping.findForward("login");
		}
		session.removeAttribute(key);

		long passValidDays = baseSecurityManager.getPassValidDays(userInfo
				.getBaseUser());
		if (passValidDays > 0 && passValidDays < 15) {
			userInfo.setRoleNames(userInfo.getRoleNames()
					+ "　　　　"
					+ BeanMessage.getLocaleMessage("i18n/messages",
							"common.changePassPrompt1", request)
					+ passValidDays
					+ BeanMessage.getLocaleMessage("i18n/messages",
							"common.changePassPrompt2", request));
		}

		String home = null;
		if (userInfo.getBaseUser().getBaseResource() != null) {
			home = userInfo.getBaseUser().getBaseResource().getResUri();
			if (StringUtils.isNotBlank(userInfo.getBaseUser().getHomeParam())) {
				String s = home.indexOf("?") == -1 ? "?" : "&";
				home += s + userInfo.getBaseUser().getHomeParam();
			}
		} else {
			home = BaseConfigurator.getInstance().getDefaultHome();
		}
		session.setAttribute(Constants.USER_HAS_LOGIN, EOrderActionListener.hasLoginOn(String.valueOf(userInfo.getBaseUser().getUserId())));
		EOrderActionListener.getOnLineMap().put(session.getId(), session);
		
		session.setAttribute(Constants.SESSION_HOME_KEY, home);
		session.setAttribute(Constants.SESSION_USER_KEY, userInfo.getBaseUser());
		session.setAttribute(Constants.SESSION_ROLE_KEY,
				userInfo.getRoleNames());
		session.setAttribute(Constants.SESSION_AUTH_KEY,
				baseSecurityManager.getResCodeByRoleIds(userInfo.getRoleIds()));
		session.setAttribute(Constants.SESSION_DATA_AUTH_KEY,
				userInfo.getDataAuth());
		session.setAttribute(Constants.SESSION_MENU_KEY, baseSecurityManager
				.getAuthSubMenus(null, userInfo.getBaseUser().getUserId(),
						request.getLocale().toString()));
		session.setAttribute(Constants.SESSION_MESSAGE_KEY,
				new MessageResourceUtils(session));
		session.setAttribute(Constants.SESSION_ORG_KEY, userInfo.getOrgNames());
		session.setAttribute(Constants.SESSION_ORG_ID_KEY, userInfo.getOrgIds());
		session.setAttribute(Constants.SESSION_ORG_SUBCODE_KEY,
				userInfo.getOrgSubCodes());
		session.setAttribute("waitToDealOrderCoutRefreshInterval",  BaseConfigurator.getInstance().getWaitToDealOrderCoutRefreshInterval());
		//经销商用户签署补充协议
		if(userInfo.getBaseUser().getBaseEmployee().getBaseDictItem().getItemCode().equals("DS")){
			MdmDistributor mdist = mdmDistributorManager.getMdmDistributorByEmployeeId(userInfo.getBaseUser().getBaseEmployee().getEmpId());
			OrderAgreementLog orderAgreementLog=this.orderAgreementLogManager.findUniqueEntity(" from OrderAgreementLog where mdmDistributor.distId=? and status=1", mdist.getDistId());
			if(orderAgreementLog!=null){
				session.setAttribute(Constants.ORDER_AGREEMENTLOG,"1");
			}else{
				session.setAttribute(Constants.ORDER_AGREEMENTLOG,"0");
			}
		}
		// 控制用户报表的权限 2011-08-30
		BaseOrg baseOrg = (null == userInfo.getBaseUser().getBaseEmployee()
				.getBaseOrg()) ? new BaseOrg() : userInfo.getBaseUser()
				.getBaseEmployee().getBaseOrg();
		session.setAttribute(Constants.SESSION_ORG_CHILD_NAME_KEY,
				getOrgChildName(baseOrg.getSubCode()));
		session.setAttribute(Constants.SESSION_ORG_CHILD_SUBCODE_KEY,
				getOrgChildSubCode(baseOrg.getSubCode()));
		return mapping.findForward("index");
	}

	/**
	 * 控制用户报表的权限 2011-08-30
	 * 
	 * @param subCode
	 * @return 当前登录人所属组织（包含下属的）
	 */
	public String getOrgChildSubCode(String subCode) {
		String childSubCode = "";
		String hsql = " SELECT ORG_ID FROM BASE_ORG WHERE SUB_CODE LIKE '"
				+ subCode + "%' AND STATE='1' ";
		List<Object[]> objList = baseSecurityManager.executeSqlQuery(hsql
				.toString());

		Object[] obj = null;
		if (objList != null && objList.size() > 1) {
			obj = objList.get(1);
			childSubCode = obj[0].toString();
			for (int i = 2; i < objList.size(); i++) {
				obj = objList.get(i);
				childSubCode += "," + obj[0];
			}
		} else {
			if (objList != null && objList.size() > 0) {
				obj = objList.get(0);
				childSubCode = obj[0].toString();
			}
		}
		return childSubCode;
	}

	public String getOrgChildName(String subCode) {
		String chilName = "";
		String hsql = " SELECT ORG_NAME FROM BASE_ORG WHERE SUB_CODE LIKE '"
				+ subCode + "%' AND STATE='1' ";
		List<Object[]> objList = baseSecurityManager.executeSqlQuery(hsql
				.toString());

		Object[] obj = null;
		if (objList != null && objList.size() > 1) {
			obj = objList.get(1);
			chilName = obj[0].toString();
			for (int i = 2; i < objList.size(); i++) {
				obj = objList.get(i);
				chilName += "," + obj[0];
			}
		} else {
			if (objList != null && objList.size() > 0) {
				obj = objList.get(0);
				chilName = obj[0].toString();
			}
		}
		return chilName;
	}
}
