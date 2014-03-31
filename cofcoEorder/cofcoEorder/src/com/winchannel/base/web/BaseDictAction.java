package com.winchannel.base.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.LazyValidatorForm;

import com.winchannel.base.model.BaseDict;
import com.winchannel.base.model.BaseDictItem;
import com.winchannel.base.service.BaseDictManager;
import com.winchannel.core.utils.BeanUtils;
import com.winchannel.core.utils.Page;
import com.winchannel.core.web.StrutsEntityAction;
import com.winchannel.mdm.util.i18n.BeanMessage;

public class BaseDictAction extends StrutsEntityAction<BaseDictItem, BaseDictManager> {
	
	@Override
	public void init() {
		this.setSuccessView(EDIT);
	}
	
	@Override
	public void onInitIndex(ActionForm form, HttpServletRequest request) {
		request.setAttribute("dicts", entityManager.getAllDict());
	}
	
	@Override
	protected void onInitList(ActionForm form, HttpServletRequest request, List list, Page page) {
	}
	
	@Override
	protected void onInitForm(ActionForm form, HttpServletRequest request, BaseDictItem baseDictItem) {
		LazyValidatorForm baseDictForm = (LazyValidatorForm) form;
		
		BaseDictItem parentBaseDictItem = baseDictItem.getBaseDictItem();
		if (StringUtils.isNotBlank(request.getParameter("parentId"))) {
			parentBaseDictItem = entityManager.get(new Long(request.getParameter("parentId")));
		}
		if (parentBaseDictItem != null) {
			baseDictForm.set("parentId", parentBaseDictItem.getDictItemId().toString());
			baseDictForm.set("parentName", parentBaseDictItem.getItemName());
		}
		if (baseDictItem.getDictItemId() == null) {
			baseDictForm.set("state", "1");
		}
	}

	@Override
	protected void onInitEntity(ActionForm form, HttpServletRequest request, BaseDictItem baseDictItem) {
		String dictId = request.getParameter("dictId");
		baseDictItem.setBaseDict(entityManager.get(BaseDict.class, dictId));
		
		if (StringUtils.isNotBlank(request.getParameter("parentId"))) {
			baseDictItem.setBaseDictItem(entityManager.get(new Long(request.getParameter("parentId"))));
		}
		else {
			baseDictItem.setBaseDictItem(null);
		}
		
		String currEmpName = this.getCurrentUser(request).getBaseEmployee().getEmpName();
		if (baseDictItem.getDictItemId() == null) {
			baseDictItem.setCreatedBy(currEmpName);
			baseDictItem.setCreated(new Date());
		}
		baseDictItem.setUpdatedBy(currEmpName);
		baseDictItem.setUpdated(new Date());

	}
	
	@Override
	protected void refrenceData(HttpServletRequest request) {
	}
	
	
	
	
	
	
	
	
	
	public ActionForward dictsave(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		List<BaseDict> list = getAllDict();
		Map<String, BaseDict> dictMap = new HashMap<String, BaseDict>();
		for (Iterator it = list.iterator(); it.hasNext();) {
			BaseDict dict = (BaseDict) it.next();
			dictMap.put(dict.getDictId() + "", dict);
		}
		
		List<BaseDict> newList = new ArrayList<BaseDict>();
		Enumeration names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement().toString();
			if (name.indexOf("D~") == 0) {
				String[] n = name.split("~");
				String prop = n[1];
				String id = n[2];
				
				BaseDict dict = dictMap.get(id);
				if (dict == null) {
					dict = new BaseDict();
					dictMap.put(id, dict);
					newList.add(dict);
				}
				dict.setState("1");
				dict.setUpdate(true);
				try {
					BeanUtils.copyProperty(dict, prop, request.getParameter(name));
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		
		try {
			this.entityManager.dictsave(newList, list);
			this.savedMessage(request, null);
		}
		catch(Exception e) {
			e.printStackTrace();
			this.saveDirectlyMessage(request, BeanMessage.getLocaleMessage("i18n/messages", "common.savefailed", request)+":" + e.getMessage());
		}
		
		return dictedit(mapping, form, request, response);
	}

	public ActionForward dictedit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("dicts", getAllDict());
		return new ActionForward("/WEB-INF/pages/base/dict.jsp");
	}
	
	private List<BaseDict> getAllDict() {
		return this.entityManager.findEntity(BaseDict.class, "from BaseDict order by sort");
	}
	
}
