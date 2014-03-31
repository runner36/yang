package com.winchannel.core.web;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.FloatConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.actions.DispatchAction;
import org.springframework.util.ReflectionUtils;

import com.winchannel.base.model.BaseEmployee;
import com.winchannel.base.model.BaseOrg;
import com.winchannel.base.model.BaseUser;
import com.winchannel.base.service.BaseDataLogManager;
import com.winchannel.base.service.BaseDictManager;
import com.winchannel.base.utils.Constants;
import com.winchannel.core.utils.BeanUtils;
import com.winchannel.core.utils.DateConverter;
import com.winchannel.core.utils.ECPage;
import com.winchannel.core.utils.SpringContext;
import com.winchannel.core.utils.StringConverter;
import com.winchannel.core.utils.StringUtils;

/**
 * Action基类
 * @author xianghui
 *
 */
public class StrutsBaseAction extends DispatchAction {
	
	public static final String DIRECTLY_MESSAGE_KEY = "message";
	public static final String INDEX = "index";
	public static final String LIST = "list";
	public static final String EDIT = "edit";
	public static final String VIEW = "view";
	public static final String SUCCESS = "success";
	private String successView = SUCCESS;
	private String authPrefix;
	private String pageName;
	private String entityName;
	private boolean firstQuery = true;
	
	protected BaseDictManager baseDictManager;
	private BaseDataLogManager baseDataLogManager;
	public void setBaseDictManager(BaseDictManager baseDictManager) {
		this.baseDictManager = baseDictManager;
	}
	public void setBaseDataLogManager(BaseDataLogManager baseDataLogManager) {
		this.baseDataLogManager = baseDataLogManager;
	}


	static {
		registConverter();
	}

	/**
	 * 设置Struts 中数字<->字符串转换，字符串为空值时,数字默认为null，而不是0. 也可以在web.xml中设置struts的参数达到相同效果，在这里设置可以防止用户漏设web.xml.
	 */
	public static void registConverter() {
		ConvertUtils.register(new StringConverter(), String.class);
		ConvertUtils.register(new IntegerConverter(null), Integer.class);
		ConvertUtils.register(new LongConverter(null), Long.class);
		ConvertUtils.register(new FloatConverter(null), Float.class);
		ConvertUtils.register(new DoubleConverter(null), Double.class);
		ConvertUtils.register(new DateConverter("yyyy-MM-dd"), Date.class);
	}
	
	public void setServlet(ActionServlet actionServlet) {
		super.setServlet(actionServlet);
		initEntityAction();
		init();
	}
	
	protected void initEntityAction() {
	}
	
	protected void init() {
	}
	
	protected Object getBean(String name) {
		return SpringContext.getBean(name);
	}

	/**
	 * 生成保存成功的信息.
	 */
	protected void savedMessage(HttpServletRequest request, Object object) {
		saveMessage(request, "entity.saved");
	}
	
	/**
	 * 生成删除成功的信息.
	 */
	protected void deletedMessage(HttpServletRequest request) {
		saveMessage(request, "entity.deleted");
	}
	
	protected void bindEntity(ActionForm form, Object object) {
		if (form != null) {
			try {
				BeanUtils.copyProperties(object, form);
			} 
			catch (Exception e) {
				ReflectionUtils.handleReflectionException(e);
				
			}
		}
	}

	protected void bindEntity(ActionForm form, Object object, HttpServletRequest request) {
		if (form != null) {
			try {
				BaseUser user = this.getCurrentUser(request);
				String userName = user.getUserAccount() + ":" + user.getBaseEmployee().getEmpName();
				BeanUtilsBean utilsBean = BeanUtilsBean.getInstance();
	            DynaProperty[] origDescriptors = ((DynaBean) form).getDynaClass().getDynaProperties();
				for (int i = 0; i < origDescriptors.length; i++) {
					String columnName = origDescriptors[i].getName();
					if (utilsBean.getPropertyUtils().isReadable(form, columnName) && utilsBean.getPropertyUtils().isWriteable(object, columnName)) {
						String oldValue = utilsBean.getSimpleProperty(object, columnName) + "";
						String newValue = ((DynaBean) form).get(columnName) + "";
						if (!oldValue.equals(newValue)) {
							utilsBean.copyProperty(object, columnName, newValue);
							baseDataLogManager.saveLog(userName, object.getClass().getSimpleName(), columnName, oldValue, newValue);
						}
	                }
	            }
			} 
			catch (Exception e) {
				ReflectionUtils.handleReflectionException(e);
				
			}
		}
	}

	protected void bindForm(ActionForm form, Object object) {
		if (object != null) {
			try {
				BeanUtils.copyProperties(form, object);
			} 
			catch (Exception e) {
				ReflectionUtils.handleReflectionException(e);
			}
		}
	}

	protected void saveMessage(HttpServletRequest request, String key, Object... values) {
		ActionMessages msgs = new ActionMessages();
		msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(key, values));
		saveMessages(request, msgs);
	}

	protected void saveMessage(HttpServletRequest request, String bundle, String key, Object... values) {
		if (StringUtils.isNotBlank(bundle)) {
			request.setAttribute(Constants.REQUEST_BUNDLE_KEY, bundle);
		}
		ActionMessages msgs = new ActionMessages();
		msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(key, values));
		saveMessages(request, msgs);
	}

	protected void saveDirectlyMessage(HttpServletRequest request, String message) {
		ActionMessages msgs = new ActionMessages();
		msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(DIRECTLY_MESSAGE_KEY, message));
		saveMessages(request, msgs);
	}

	protected void saveError(HttpServletRequest request, String key, Object... values) {
		ActionMessages errors = new ActionMessages();
		errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(key, values));
		saveErrors(request, errors);
	}

	protected void saveError(HttpServletRequest request, String bundle, String key, Object... values) {
		if (StringUtils.isNotBlank(bundle)) {
			request.setAttribute(Constants.REQUEST_BUNDLE_KEY, bundle);
		}
		ActionMessages errors = new ActionMessages();
		errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(key, values));
		saveErrors(request, errors);
	}

	protected void saveDirectlyError(HttpServletRequest request, String message) {
		ActionMessages errors = new ActionMessages();
		errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(DIRECTLY_MESSAGE_KEY, message));
		saveErrors(request, errors);
	}
	
	protected void render(HttpServletResponse response, String text,String contentType) {
		try {
			response.setContentType(contentType);
			response.getWriter().write(text);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	protected void renderText(HttpServletResponse response, String text) {
		render(response,text,"text/plain;charset=UTF-8");
	}

	protected void renderHtml(HttpServletResponse response, String text) {
		render(response,text,"text/html;charset=UTF-8");
	}

	protected void renderXML(HttpServletResponse response, String text) {
		render(response,text,"text/xml;charset=UTF-8");
	}
	
	protected void printMessage(HttpServletResponse response, String message) {
		renderHtml(response, "<script>alert('" + message + "');</script>");
	}
	
	protected BaseUser getCurrentUser(HttpServletRequest request) {
		return (BaseUser) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
	}
	
	protected String getCurrentEmpSubCode(HttpServletRequest request) {
		String subCode = null;
		BaseEmployee baseEmployee = getCurrentUser(request).getBaseEmployee();
		if (baseEmployee != null) {
			subCode = baseEmployee.getSubCode();
		}
		return subCode;
	}
	
	protected String getCurrentOrgSubCode(HttpServletRequest request) {
		String subCode = null;
		BaseEmployee baseEmployee = getCurrentUser(request).getBaseEmployee();
		BaseOrg baseOrg = baseEmployee != null ? baseEmployee.getBaseOrg() : null;
		if (baseOrg != null) {
			subCode = baseOrg.getSubCode();
		}
		return subCode;
	}
	
	public String getSuccessView() {
		return successView;
	}

	public void setSuccessView(String successView) {
		this.successView = successView;
	}

	public String getAuthPrefix() {
		return authPrefix;
	}

	public void setAuthPrefix(String authPrefix) {
		this.authPrefix = authPrefix;
	}

	public String getPageName() {
		if (pageName == null) {
			pageName = ECPage.DEFAULT_PAGENAME;
		}
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getEntityName() {
		if (entityName == null) {
			entityName = getPageName() + "-entity";
		}
		return entityName;
	}
	public boolean isFirstQuery() {
		return firstQuery;
	}
	
	public void setFirstQuery(boolean firstQuery) {
		this.firstQuery = firstQuery;
	}

}
