package com.winchannel.core.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.springframework.util.ReflectionUtils;

import com.winchannel.base.conf.BaseConfigurator;
import com.winchannel.core.dao.HibernateEntityDao;
import com.winchannel.core.exception.BaseException;
import com.winchannel.core.exception.BusinessException;
import com.winchannel.core.utils.BeanUtils;
import com.winchannel.core.utils.ECPage;
import com.winchannel.core.utils.GenericsUtils;
import com.winchannel.core.utils.Page;

/**
 * Action实体抽向类，适用于一个模块的增删改查,需定义泛型参数<T, M>
 * @author xianghui
 *
 * @param <T> Hibernate实体对象
 * @param <M> Hibernate实体对象对应的Manager
 */
@SuppressWarnings("unchecked")
public abstract class StrutsEntityAction<T, M extends HibernateEntityDao<T>> extends StrutsBaseAction {

	protected Class<T> entityClass;
	protected M entityManager;
	protected String idName;
	protected Class idClass;
	
	protected void initEntityAction() {
		entityClass = GenericsUtils.getSuperClassGenricType(getClass());
		initEntityManager();
		try {
			idName = entityManager.getIdName(entityClass);
			idClass = BeanUtils.getPropertyType(entityClass, idName);
		} 
		catch (Exception e) {
			e.printStackTrace();
//			ReflectionUtils.handleReflectionException(e);
		}
	}
	
	protected void initEntityManager() {
		Class managerClass = GenericsUtils.getSuperClassGenricType(getClass(), 1);
		String className = managerClass.getSimpleName();
		entityManager = (M) getBean(className.substring(0, 1).toLowerCase() + className.substring(1));
	}
	
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
									 HttpServletResponse response) {
		return index(mapping, form, request, response);
	}
	
	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
							   HttpServletResponse response) {
		onInitIndex(form, request);
		return getForward(mapping, request, INDEX);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
							  HttpServletResponse response) {
		ECPage page = new ECPage(request, this.getPageName());
		page.put(Page.AUTH_PREFIX, this.getAuthPrefix());
		onInitPage(form, request, page);
		
		List list = null;
		if (!isFirstQuery() && "1".equals(request.getParameter("first"))) {
			list = new ArrayList();
		}
		else {
			list = doListEntity(page);
		}
		
		initList(form, request, list, page);
		return page.initForward(mapping.findForward(LIST));
	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
								HttpServletResponse response) {
		return edit(mapping, form, request, response);
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
							  HttpServletResponse response) {
		saveToken(request);
		T object = null;
		if ((request.getParameter(idName) != null && request.getParameter(idName).length()>0) || (request.getParameter("id") != null &&  request.getParameter("id").length()>0) ) {
			object = doGetEntity(form, request);
			if (object == null) {
				saveError(request, "entity.missing");
				return getForward(mapping, request, getSuccessView());
			}
		} 
		else {
			try {
				object = entityClass.newInstance();
			} 
			catch (InstantiationException e) {
				log.error(e);
			} 
			catch (IllegalAccessException e) {
				log.error(e);
			}
		}
		initForm(form, request, object);
		if (getErrors(request).size() > 0) {
			return getForward(mapping, request, getSuccessView());
		}
		return getForward(mapping, request, EDIT);
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
							  HttpServletResponse response) {
		T object = doGetEntity(form, request);
		if (object == null) {
			saveError(request, "entity.missing");
			return getForward(mapping, request, LIST);
		}
		request.setAttribute("object", object);
		return getForward(mapping, request, VIEW);
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
							  HttpServletResponse response) {
		if (isCancelled(request)) {
			return getForward(mapping, request, getSuccessView());
		}
		if (!isTokenValid(request)) {
//			saveDirectlyError(request, "此操作无效，请不要重复提交");
			saveError(request, "mess.duplSubmit");
			return getForward(mapping, request, getSuccessView());
		}
		
		ActionMessages errors = form.validate(mapping, request);
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return getForward(mapping, request, EDIT);
		}

		T object = null;
		if (StringUtils.isNotBlank(request.getParameter(idName))) {
//			object = (T) request.getSession().getAttribute(this.getEntityName());
			object = doGetEntity(form, request);
			if (object == null) {
				saveError(request, "entity.missing");
				resetToken(request);
				return getForward(mapping, request, getSuccessView());
			}
		} 
		else {
			object = doNewEntity(form, request);
		}
		
		try {
			
			initEntity(form, request, object);
			
			if (getErrors(request).size() > 0) {
				return getForward(mapping, request, EDIT);
			}
			
			doSaveEntity(form, request, object);
			savedMessage(request, object);
		} 
		catch (BusinessException e) {
			if (StringUtils.isNotBlank(e.getKey())) {
				saveError(request, e.getBundle(), e.getKey(), e.getValues());
			}
			else {
				saveDirectlyError(request, e.getMessage());
			}
			return getForward(mapping, request, EDIT);
		}
		catch (Exception e) {
			if (e instanceof BaseException) {
				BaseException e1 = (BaseException) e;
				if (StringUtils.isNotBlank(e1.getKey())) {
					saveError(request, e1.getBundle(), e1.getKey(), e1.getValues());
				}
				else {
					saveDirectlyError(request, e.getMessage());
				}
			}
			else {
				saveDirectlyError(request, e.getMessage());
			}
			return getForward(mapping, request, EDIT);
		}
		
		resetToken(request);
		return getForward(mapping, request, getSuccessView());
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
								HttpServletResponse response) {
		try {
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

		return getForward(mapping, request, getSuccessView());
	}
	
	protected ActionForward getForward(ActionMapping mapping, HttpServletRequest request, String viewName) {
		if (viewName.equals(EDIT) || viewName.equals(VIEW)) {
			refrenceData(request);
		}
		return mapping.findForward(viewName);
	}

	protected void initList(ActionForm form, HttpServletRequest request, List list, Page page) {
		int totalRows = page.getInt(Page.TOTAL_COUNT);
		if (totalRows == 0) {
			totalRows = list.size();
		}
		request.setAttribute("totalRows", totalRows);
		request.setAttribute("list", list);
		onInitList(form, request, list, page);
	}

	protected void initEntity(ActionForm form, HttpServletRequest request, T object) {
		if (BaseConfigurator.getInstance().isDataLog()) {
			bindEntity(form, object, request);
		}
		else {
			bindEntity(form, object);
		}
		onInitEntity(form, request, object);
	}

	protected void initForm(ActionForm form, HttpServletRequest request, T object) {
		bindForm(form, object);
		onInitForm(form, request, object);
	}

	protected List doListEntity(Page page) {
		return entityManager.query(page);
	}
	
	protected T doNewEntity(ActionForm form, HttpServletRequest request) {
		T object = null;
		try {
			object = entityClass.newInstance();
		} 
		catch (Exception e) {
			log.error("Can't new Instance of entity.", e);
		}
		return object;
	}

	protected T doGetEntity(ActionForm form, HttpServletRequest request) {
		Serializable id = getEntityId(request);
		T object = entityManager.get(id);
//		request.getSession().setAttribute(this.getEntityName(), object);
		return object;
	}

	protected void doSaveEntity(ActionForm form, HttpServletRequest request, T object) {
		entityManager.save(object);
		/*try {
			((LazyValidatorForm) form).set(idName, PropertyUtils.getProperty(object, idName));
		}
		catch (Exception e) {
			ReflectionUtils.handleReflectionException(e);
		}*/
		onSaveEntity(form, request, object);
	}

	protected void doDeleteEntity(ActionForm form, HttpServletRequest request) {
		Serializable id = getEntityId(request);
		entityManager.deleteById(id);
	}

	protected Serializable getEntityId(HttpServletRequest request) {
		String idString = request.getParameter(idName) == null ? request.getParameter("id") : request.getParameter(idName);
		try {
			return (Serializable) ConvertUtils.convert(idString, idClass);
		}
		catch (NumberFormatException e) {
			throw new IllegalArgumentException("Wrong when get id from request");
		}
	}
	/**
	 * 返回消息
	 * 
	 * @param request
	 * @param message
	 */
	protected void doMessage(HttpServletRequest request, String message) {
		ActionMessages msgs = new ActionMessages();
		msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message", message));
		saveMessages(request, msgs);
	}
	/**
	 * 初始化INDEX页面（如果有的话）所需的参考对象注入.如选择列表等
	 */
	protected void onInitIndex(ActionForm form, HttpServletRequest request) {
	}

	/**
	 * 初始化Page参数
	 */
	protected void onInitPage(ActionForm form, HttpServletRequest request, Page page) {
	}

	/**
	 * 初始化一些列表参数及查询参数
	 */
	protected void onInitList(ActionForm form, HttpServletRequest request, List list, Page page) {
	}

	/**
	 * 在删除entity之前的一些其它操作，如删除子对象等
	 */
	protected void onDeleteEntity(ActionForm form, HttpServletRequest request) {
	}
	
	/**
	 * 对form进行一些其它的初始化操作,可以为Form对象添加更多属性
	 */
	protected void onInitForm(ActionForm form, HttpServletRequest request, T object) {
	}

	/**
	 * 在保存entity之前对entity的一些初始化操作，如通过id初始化entity中的父对象等
	 */
	protected void onInitEntity(ActionForm form, HttpServletRequest request, T object) {
	}
	
	/**
	 * 在保存entity之后的一些其它操作，如保存子对象等
	 */
	protected void onSaveEntity(ActionForm form, HttpServletRequest request, T object) {
	}
	
	/**
	 * add、edit、view界面所需的参考对象注入.如选择列表等
	 */
	protected void refrenceData(HttpServletRequest request) {
	}

}
