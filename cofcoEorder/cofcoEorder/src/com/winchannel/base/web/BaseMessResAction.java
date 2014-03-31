package com.winchannel.base.web;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winchannel.base.model.BaseMessRes;
import com.winchannel.base.model.BaseMessResId;
import com.winchannel.base.service.BaseMessResManager;
import com.winchannel.core.utils.StringUtils;
import com.winchannel.core.web.StrutsBaseAction;
import com.winchannel.mdm.util.i18n.BeanMessage;

public class BaseMessResAction extends StrutsBaseAction {
	
	public static final String PARAM_NAME = "func";
	public static final String DEFAULT_LANGUAGE = ".";
	
	private BaseMessResManager baseMessResManager;
	
	public void setBaseMessResManager(BaseMessResManager baseMessResManager) {
		this.baseMessResManager = baseMessResManager;
	}
	
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		String language = DEFAULT_LANGUAGE;
		String reportName = request.getParameter("reportName");
		String param = request.getParameter(PARAM_NAME);
		
		String bundle = reportName;
		if (StringUtils.isNotBlank(param)) {
			bundle += "_" + param;
		}
		
		List<BaseMessRes> messRes = baseMessResManager.getMessRes(language, bundle);
		Map<BaseMessResId, BaseMessRes> messMap = new HashMap<BaseMessResId, BaseMessRes>();
		for (BaseMessRes res : messRes) {
			res.setState("0");
			messMap.put(res.getId(), res);
		}
		int stateCount=0;
		Enumeration names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement().toString();
			if (name.indexOf("d_") == 0) {
				String[] n = name.split("_");
				String prop = n[1];
				String messKey = n[2];
				String v = request.getParameter(name);
				
				BaseMessRes res = messMap.get(new BaseMessResId(language, bundle, messKey));
				if (res == null) {
					BaseMessResId id = new BaseMessResId(language, bundle, messKey);
					res = new BaseMessRes(id);
					res.setState("0");
					messMap.put(id, res);
				}
				if ("value".equals(prop)) {
					
					res.setMessValue(v);
				}
				else if ("sort".equals(prop)) {
					res.setSort(StringUtils.isNotBlank(v) ? new Long(v) : null);
				}
				else if ("state".equals(prop)) {
					if("1".equals(v))
						stateCount++;
					res.setState(v);
				}
			}
		}
		try {
			if(stateCount>0)
				this.baseMessResManager.saveAll(messMap.values());
			else
				this.baseMessResManager.deleteAll(messMap.values());
			this.savedMessage(request, null);
		}
		catch(Exception e) {
			e.printStackTrace();
			this.saveDirectlyMessage(request, BeanMessage.getLocaleMessage("i18n/messages", "common.savefailed", request)+"：" + e.getMessage());
		}
		
		return edit(mapping, form, request, response);
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		String language = DEFAULT_LANGUAGE;
		String reportName = request.getParameter("reportName");
		String param = request.getParameter(PARAM_NAME);
		request.setAttribute("columns", baseMessResManager.getColumns(language, reportName, param));
		return new ActionForward("/WEB-INF/pages/base/res.jsp");
	}

	
}
