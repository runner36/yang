package com.winchannel.base.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.LazyValidatorForm;

import com.winchannel.base.model.BaseEmployee;
import com.winchannel.base.model.BaseUser;
import com.winchannel.base.service.BaseDictManager;
import com.winchannel.base.service.BaseEmployeeManager;
import com.winchannel.base.service.BaseOrgManager;
import com.winchannel.base.utils.Constants;
import com.winchannel.core.utils.Page;
import com.winchannel.core.web.StrutsEntityAction;

public class BaseEmployeeAction extends StrutsEntityAction<BaseEmployee, BaseEmployeeManager> {
	private final static String EMP_PREFIX = "emp_";
	private final static String STARTDATE_PREFIX = "startDate_";
	private final static String SPLIT = "_";
	
	private BaseOrgManager baseOrgManager;
	private BaseDictManager baseDictManager;
	private BaseEmployeeManager baseEmployeeManager;
	public void setBaseOrgManager(BaseOrgManager baseOrgManager) {
		this.baseOrgManager = baseOrgManager;
	}
	 
	public BaseDictManager getBaseDictManager() {
		return baseDictManager;
	}

	public void setBaseDictManager(BaseDictManager baseDictManager) {
		this.baseDictManager = baseDictManager;
	}

	
	public BaseEmployeeManager getBaseEmployeeManager() {
		return baseEmployeeManager;
	}

	public void setBaseEmployeeManager(BaseEmployeeManager baseEmployeeManager) {
		this.baseEmployeeManager = baseEmployeeManager;
	}

	@Override
	public void init() {
		this.setSuccessView(EDIT);
	}
	
	@Override
	protected void onInitList(ActionForm form, HttpServletRequest request, List list, Page page) {
		request.setAttribute("dutys", baseDictManager.getItems(Constants.DICT_DUTY));
	}
	/**
	 * 订单管理-个人信息修改-查看
	 * author shijingru
	 * @param mapping
	 *
	 */
	public ActionForward editPersonalInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		saveToken(request);
		BaseUser baseUser = this.getCurrentUser(request);
		BaseEmployee baseEmployee = baseEmployeeManager.get(baseUser.getBaseEmployee().getEmpId());
		LazyValidatorForm baseEmployeeForm = (LazyValidatorForm) form;
		bindForm(baseEmployeeForm,baseEmployee);
		baseEmployeeForm.set("orgId", baseEmployee.getBaseOrg().getOrgId().toString());
		baseEmployeeForm.set("orgName", baseEmployee.getBaseOrg().getOrgName());
		baseEmployeeForm.set("geoId", baseEmployee.getBaseDictItemGeo().getDictItemId().toString());
		baseEmployeeForm.set("geoName",baseDictManager.get(baseEmployee.getBaseDictItemGeo().getDictItemId()).getItemName());
		baseEmployeeForm.set("dutyId", baseEmployee.getBaseDictItem().getDictItemId().toString());
		return new ActionForward("/WEB-INF/pages/base/editPersonalInfo.jsp");
	}
	/**
	 * 订单管理-个人信息修改-保存
	 * author shijingru
	 * @param mapping
	 *
	 */
	public ActionForward PersonalInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			  HttpServletResponse response) {
		BaseEmployee baseEmployee = baseEmployeeManager.get(new Long(request.getParameter("empId")));
		if (StringUtils.isNotBlank(request.getParameter("orgId"))) {
			baseEmployee.setBaseOrg(baseOrgManager.get(new Long(request.getParameter("orgId"))));
		}
		if (StringUtils.isNotBlank(request.getParameter("dutyId"))) {
			baseEmployee.setBaseDictItem(baseDictManager.get(new Long(request.getParameter("dutyId"))));
		}
		//保存地理区域
		if (StringUtils.isNotBlank(request.getParameter("geoId"))) {
			baseEmployee.setBaseDictItemGeo(baseDictManager.get(new Long(request.getParameter("geoId"))));
		} else {
			baseEmployee.setBaseDictItemGeo(null);
		}
		this.save(mapping, form, request, response);
		return new ActionForward("/base/baseEmployee.do?method=editPersonalInfo");
	}
	
	
	@Override
	protected void onInitForm(ActionForm form, HttpServletRequest request, BaseEmployee baseEmployee) {
		LazyValidatorForm baseEmployeeForm = (LazyValidatorForm) form;
		
		BaseEmployee parentBaseEmployee = baseEmployee.getBaseEmployee();
		if (StringUtils.isNotBlank(request.getParameter("parentId"))) {
			parentBaseEmployee = entityManager.get(new Long(request.getParameter("parentId")));
		}
		if (parentBaseEmployee != null) {
			baseEmployeeForm.set("parentId", parentBaseEmployee.getEmpId().toString());
			baseEmployeeForm.set("parentName", parentBaseEmployee.getEmpName());
		}
		
		if (baseEmployee.getBaseOrg() != null) {
			baseEmployeeForm.set("orgId", baseEmployee.getBaseOrg().getOrgId().toString());
			baseEmployeeForm.set("orgName", baseEmployee.getBaseOrg().getOrgName().toString());
		}
		if (baseEmployee.getBaseDictItem() != null) {
			baseEmployeeForm.set("dutyId", baseEmployee.getBaseDictItem().getDictItemId().toString());
		}
		if (baseEmployee.getBaseDictItemByEmpTypeId() != null) {
			baseEmployeeForm.set("empTypeId", baseEmployee.getBaseDictItemByEmpTypeId().getDictItemId().toString());
		}

		if (baseEmployee.getEmpId() == null) {
			baseEmployeeForm.set("state", "1");
			baseEmployeeForm.set("isEmployee", "1");
		}
		
		if (baseEmployee.getBaseDictItemGeo() != null) {
			//显示地理区域
			baseEmployeeForm.set("geoId", baseEmployee.getBaseDictItemGeo().getDictItemId());
			baseEmployeeForm.set("geoName", baseEmployee.getBaseDictItemGeo().getItemName());
		}
	}

	@Override
	protected void onInitEntity(ActionForm form, HttpServletRequest request, BaseEmployee baseEmployee) {
		if (StringUtils.isNotBlank(request.getParameter("parentId"))) {
			baseEmployee.setBaseEmployee(entityManager.get(new Long(request.getParameter("parentId"))));
		}
		else {
			baseEmployee.setBaseEmployee(null);
		}
		if (StringUtils.isNotBlank(request.getParameter("orgId"))) {
			baseEmployee.setBaseOrg(baseOrgManager.get(new Long(request.getParameter("orgId"))));
		}
		
		if (StringUtils.isNotBlank(request.getParameter("dutyId"))) {
			baseEmployee.setBaseDictItem(baseDictManager.get(new Long(request.getParameter("dutyId"))));
		}
		if (StringUtils.isNotBlank(request.getParameter("empTypeId"))) {
			baseEmployee.setBaseDictItemByEmpTypeId(baseDictManager.get(new Long(request.getParameter("empTypeId"))));
		}
		else {
			baseEmployee.setBaseDictItemByEmpTypeId(null);
		}
		
		//保存地理区域
		if (StringUtils.isNotBlank(request.getParameter("geoId"))) {
			baseEmployee.setBaseDictItemGeo(baseDictManager.get(new Long(request.getParameter("geoId"))));
		} else {
			baseEmployee.setBaseDictItemGeo(null);
		}
		
		String currEmpName = this.getCurrentUser(request).getBaseEmployee().getEmpName();
		if (baseEmployee.getEmpId() == null) {
			baseEmployee.setCreatedBy(currEmpName);
			baseEmployee.setCreated(new Date());
		}
		baseEmployee.setUpdatedBy(currEmpName);
		baseEmployee.setUpdated(new Date());
	}
	
	@Override
	protected void refrenceData(HttpServletRequest request) {
		request.setAttribute("dutys", baseDictManager.getItems(Constants.DICT_DUTY));
		request.setAttribute("empTypes", baseDictManager.getItems(Constants.DICT_EMP_TYPE));
	}
	
	@Override
	protected void doSaveEntity(ActionForm form, HttpServletRequest request, BaseEmployee baseEmp) {
		super.doSaveEntity(form, request, baseEmp);
		
		String oper = request.getParameter("oper");
		String targetEmpId = request.getParameter("targetId");
		if (StringUtils.isNotBlank(oper) && StringUtils.isNotBlank(targetEmpId)) {
			entityManager.save(baseEmp, oper, targetEmpId);
		}

	}

	public ActionForward saveCheckOrg(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
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
        	if (name.startsWith(EMP_PREFIX)) {
        		String value = request.getParameter(name);
        		if (StringUtils.isNotBlank(value)) {
        			String[] arr = value.split(SPLIT);
        			if (arr.length == 2) {
        				String date = request.getParameter(STARTDATE_PREFIX+arr[0]);
        				if (StringUtils.isNotBlank(date)) {
        					String[] temp = {arr[0], arr[1], date};
            				params.add(temp);
        				}
        			}
        		}
        	}
        }
        return params;
	}
	
	public ActionForward delete1(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			  HttpServletResponse response) {
		this.delete(mapping, form, request, response);
		return mapping.findForward(SUCCESS);
	}
	public ActionForward add1(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			  HttpServletResponse response) {
		return edit1(mapping, form, request, response);
	}
	
	public ActionForward edit1(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			  HttpServletResponse response) {
		this.edit(mapping, form, request, response);
		return new ActionForward("/WEB-INF/pages/base/baseEmployeeEdit1.jsp");
	}

	public ActionForward save1(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			  HttpServletResponse response) {
		this.save(mapping, form, request, response);
		if (getErrors(request).size() > 0) {
			return new ActionForward("/WEB-INF/pages/base/baseEmployeeEdit1.jsp");
		}
		return mapping.findForward(SUCCESS);
	}
}
