package com.winchannel.base.model;


/**
 * BaseMenu generated by MyEclipse Persistence Tools
 */

public class BaseMenuI18n implements java.io.Serializable {

	// Fields

	private Long menuI18nId;
	private BaseMenu baseMenu;
	private BaseDictItem menuType;
	private String menuName;

	
	public Long getMenuI18nId() {
		return menuI18nId;
	}
	public void setMenuI18nId(Long menuI18nId) {
		this.menuI18nId = menuI18nId;
	}
	public BaseMenu getBaseMenu() {
		return baseMenu;
	}
	public void setBaseMenu(BaseMenu baseMenu) {
		this.baseMenu = baseMenu;
	}
	public BaseDictItem getMenuType() {
		return menuType;
	}
	public void setMenuType(BaseDictItem menuType) {
		this.menuType = menuType;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

}