package com.winchannel.base.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.validator.LazyValidatorForm;

import com.winchannel.base.model.BaseDictItem;
import com.winchannel.base.model.BaseMenu;
import com.winchannel.base.model.BaseMenuI18n;
import com.winchannel.base.service.BaseDictManager;
import com.winchannel.base.service.BaseMenuManager;
import com.winchannel.base.service.BaseResourceManager;
import com.winchannel.core.utils.Page;
import com.winchannel.core.web.StrutsEntityAction;

public class BaseMenuAction extends StrutsEntityAction<BaseMenu, BaseMenuManager> {
	
	private BaseResourceManager baseResourceManager;
	private BaseDictManager baseDictManager;
	private  BaseMenuManager baseMenuManager;
	
	
	public BaseMenuManager getBaseMenuManager() {
		return baseMenuManager;
	}

	public void setBaseMenuManager(BaseMenuManager baseMenuManager) {
		this.baseMenuManager = baseMenuManager;
	}

	public void setBaseDictManager(BaseDictManager baseDictManager) {
		this.baseDictManager = baseDictManager;
	}

	public void setBaseResourceManager(BaseResourceManager baseResourceManager) {
		this.baseResourceManager = baseResourceManager;
	}
	
	@Override
	public void init() {
		this.setSuccessView(EDIT);
	}
	
	@Override
	protected void onInitList(ActionForm form, HttpServletRequest request, List list, Page page) {
	}
	
	@Override
	protected void onInitForm(ActionForm form, HttpServletRequest request, BaseMenu baseMenu) {
		LazyValidatorForm baseMenuForm = (LazyValidatorForm) form;
		
		BaseMenu parentBaseMenu = baseMenu.getBaseMenu();
		if (StringUtils.isNotBlank(request.getParameter("parentId"))) {
			parentBaseMenu = entityManager.get(new Long(request.getParameter("parentId")));
		}
		if (parentBaseMenu != null) {
			baseMenuForm.set("parentId", parentBaseMenu.getMenuId().toString());
			baseMenuForm.set("parentName", parentBaseMenu.getMenuName());
		}
		
		if (baseMenu.getBaseResource() != null) {
			baseMenuForm.set("resId", baseMenu.getBaseResource().getResId().toString());
			baseMenuForm.set("resName", baseMenu.getBaseResource().getResName().toString());
		}
		if (baseMenu.getMenuId() == null) {
			baseMenuForm.set("state", "1");
		}
	}
	
	@Override
	protected void onInitEntity(ActionForm form, HttpServletRequest request, BaseMenu baseMenu) {
		if (StringUtils.isNotBlank(request.getParameter("parentId"))) {
			baseMenu.setBaseMenu(entityManager.get(new Long(request.getParameter("parentId"))));
		}
		else {
			baseMenu.setBaseMenu(null);
		}
		if (StringUtils.isNotBlank(request.getParameter("resId"))) {
			baseMenu.setBaseResource(baseResourceManager.get(new Long(request.getParameter("resId"))));
		}
		else {
			baseMenu.setBaseResource(null);
		}
	}
	
	@Override
	protected void refrenceData(HttpServletRequest request) {

		List<BaseDictItem> list=baseDictManager.getItems("menuI18n");
		String menuId=request.getParameter("menuId");
		if (menuId == null || menuId.length() == 0) {
			menuId = request.getParameter("id");
		}
		if (menuId != null && menuId.length() > 0) {
			
			List<BaseMenuI18n> nowList = this.entityManager.findEntity(
					BaseMenuI18n.class,
					"from BaseMenuI18n where baseMenu.menuId=?", Long.valueOf(menuId));
			for (BaseDictItem basedictitem : list) {
				for (BaseMenuI18n basemenui18n : nowList) {
					if (basemenui18n.getMenuType().getDictItemId() == basedictitem
							.getDictItemId()) {
						basedictitem.setRemark(basemenui18n.getMenuName());
						break;
					}
				}
			}
		}
		request.setAttribute("list", list);

	}
	/**
	 * 在保存entity之后的一些其它操作，如保存子对象等
	 */
	protected void onSaveEntity(ActionForm form, HttpServletRequest request, BaseMenu baseMenu){
		//删除在保存菜单国际化表
		String[] typeIds=request.getParameterValues("typeId");
		String[] values = request.getParameterValues("val");
		this.baseMenuManager.addMenuI18N(typeIds, values, baseMenu);
	}
}
