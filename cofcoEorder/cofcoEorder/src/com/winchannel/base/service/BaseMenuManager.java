package com.winchannel.base.service;

import com.winchannel.base.model.BaseMenu;
import com.winchannel.base.model.BaseMenuI18n;
import com.winchannel.core.dao.HibernateEntityDao;
import com.winchannel.core.exception.BusinessException;

public class BaseMenuManager extends HibernateEntityDao<BaseMenu> {
	private BaseDictManager baseDictManager;
	
	public BaseDictManager getBaseDictManager() {
		return baseDictManager;
	}

	public void setBaseDictManager(BaseDictManager baseDictManager) {
		this.baseDictManager = baseDictManager;
	}

	public void save(Object object) {
		if (!this.isUnique(object, "menuCode")) {
			throw new BusinessException("base","common.codeDuplication");
		}
		makeSubCode(object);
		super.save(object);
	}

	public void addMenuI18N(String [] typeIds,String[] values,BaseMenu baseMenu) {
		deleteMemuI18n(baseMenu);
		if (typeIds != null && typeIds.length > 0) {
			for (int i = 0; i < typeIds.length; i++) {
				BaseMenuI18n obj = new BaseMenuI18n();
				obj.setMenuType(baseDictManager.get(Long.valueOf(typeIds[i])));
				obj.setMenuName(values[i]);
				obj.setBaseMenu(baseMenu);
				super.save(obj);
			}
		}
	}

	private void deleteMemuI18n(BaseMenu baseMenu) {
		String sql="delete from BASE_MENU_I18N where MENU_ID="+baseMenu.getMenuId();
		super.executeSqlUpdate(sql);
	}
}
