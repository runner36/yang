package com.winchannel.examples.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.validator.LazyValidatorForm;

import com.winchannel.base.model.BaseResource;
import com.winchannel.base.service.BaseDictManager;
import com.winchannel.base.service.BaseResourceManager;
import com.winchannel.core.utils.Page;
import com.winchannel.core.web.StrutsEntityAction;

/**
 * 资源管理
 * @author xianghui
 * 增、删、改、查，Action的实现，以下的方法如果没有用到，可不写
 */
public class BaseResourceAction extends StrutsEntityAction<BaseResource, BaseResourceManager> {
	
	/**
	 * 其它相关业务对象
	 */
	private BaseDictManager baseDictManager;
	
	/**
	 * 其它相关业务对象注入方法
	 * @param baseDictManager
	 */
	public void setBaseDictManager(BaseDictManager baseDictManager) {
		this.baseDictManager = baseDictManager;
	}
	
	/**
	 * Action的初始化方法，相当于构造方法（只加载一次）
	 */
	@Override
	public void init() {
		// 设置操作成功所跳转的页面为EDIT（编辑）页面，默认是LIST（列表）页面
		// this.setSuccessView(EDIT);
	}
	
	/**
	 * 初始化index页面（如果有的话）的参考对象及查询参数.如下拉框
	 */
	@Override
	protected void onInitIndex(ActionForm form, HttpServletRequest request) {
	}
	
	/**
	 * 根据页面传递的参数，返回一个List,用于生成分页表格,黙认执行Manager的query方法，如果有特殊业务，可覆盖此方法
	 * @param page 页面对象
	 */
	/*@Override
	protected List doListEntity(Page page) {
		return entityManager.query(page);
	}*/

	/**
	 * 初始化列表(list)页面的参考对象及查询参数.如下拉框
	 */
	@Override
	protected void onInitList(ActionForm form, HttpServletRequest request, List list, Page page) {
	}
	
	/**
	 * 对form进行一些其它的初始化操作,可以为Form对象添加更多属性
	 */
	@Override
	protected void onInitForm(ActionForm form, HttpServletRequest request, BaseResource baseResource) {
		LazyValidatorForm baseDictForm = (LazyValidatorForm) form;
		if (baseResource.getBaseDictItem() != null) {
			baseDictForm.set("dictItemId", baseResource.getBaseDictItem().getDictItemId().toString());
			baseDictForm.set("dictItemName", baseResource.getBaseDictItem().getItemName());
		}
	}

	/**
	 * 在保存entity之前对entity的一些初始化操作，如通过id初始化entity中的父对象等
	 */
	@Override
	protected void onInitEntity(ActionForm form, HttpServletRequest request, BaseResource baseResource) {
		// 设置资源分类对象
		baseResource.setBaseDictItem(baseDictManager.get(new Long(request.getParameter("dictItemId"))));
	}
	
	/**
	 * 在保存entity之后的一些其它操作，如保存子对象等
	 */
	@Override
	protected void onSaveEntity(ActionForm form, HttpServletRequest request, BaseResource baseResource) {
	}
	
	/**
	 * add、edit、view界面所需的参考对象注入.如选择列表等
	 */
	@Override
	protected void refrenceData(HttpServletRequest request) {
		// 初始化选择列表
		// request.setAttribute("items", baseDictManager.getAllDict());
	}

	
}
