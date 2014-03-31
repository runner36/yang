package com.winchannel.mdm.product.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.LazyValidatorForm;

import com.winchannel.base.model.BaseDictItem;
import com.winchannel.base.model.BaseEmployee;
import com.winchannel.base.service.BaseDictManager;
import com.winchannel.core.exception.BusinessException;
import com.winchannel.core.utils.DateUtils;
import com.winchannel.core.utils.Page;
import com.winchannel.core.utils.StringUtils;
import com.winchannel.core.web.StrutsEntityAction;
import com.winchannel.mdm.conversion.service.MdmConversionManager;
import com.winchannel.mdm.product.model.MdmProduct;
import com.winchannel.mdm.product.service.MdmProductManager;
import com.winchannel.mdm.util.i18n.BeanMessage;

public class MdmProductAction extends
		StrutsEntityAction<MdmProduct, MdmProductManager> {
	private MdmProductManager mdmProductManager;
	private MdmConversionManager mdmConversionManager;
	private BaseDictManager baseDictManager;

	public BaseDictManager getBaseDictManager() {
		return baseDictManager;
	}

	public void setBaseDictManager(BaseDictManager baseDictManager) {
		this.baseDictManager = baseDictManager;
	}

	public MdmConversionManager getMdmConversionManager() {
		return mdmConversionManager;
	}

	public void setMdmConversionManager(
			MdmConversionManager mdmConversionManager) {
		this.mdmConversionManager = mdmConversionManager;
	}

	public MdmProductManager getMdmProductManager() {
		return mdmProductManager;
	}

	public void setMdmProductManager(MdmProductManager mdmProductManager) {
		this.mdmProductManager = mdmProductManager;
	}
	protected void init() {
		//this.setFirstQuery(false);
	}
	protected void onInitForm(ActionForm form, HttpServletRequest request,
			MdmProduct mdmProduct) {
		LazyValidatorForm form1 = (LazyValidatorForm) form;
		if (mdmProduct.getState() == null) {
			form1.set("state", "1");
		}
		if (mdmProduct.getItemBrand() != null) {
			form1.set("brandId", mdmProduct.getItemBrand().getDictItemId()
					.toString());
			form1.set("brandName", mdmProduct.getItemBrand().getItemName());
		}
		
		if (mdmProduct.getProdSTRU()!= null) {
			form1.set("prodSTRUId", mdmProduct.getProdSTRU().getDictItemId()
					.toString());
			form1.set("prodSTRUName", mdmProduct.getProdSTRU().getItemName());
		}
		
		if (mdmProduct.getItemType() != null) {
			form1.set("typeId", mdmProduct.getItemType().getDictItemId()
					.toString());
			form1.set("typeName", mdmProduct.getItemType().getItemName());
		}
		if (mdmProduct.getItemPack() != null) {
			form1.set("packId", mdmProduct.getItemPack().getDictItemId()
					.toString());
			form1.set("packName", mdmProduct.getItemPack().getItemName());
		}
		if (mdmProduct.getItemOther() != null) {
			form1.set("otherId", mdmProduct.getItemOther().getDictItemId()
					.toString());
			form1.set("otherName", mdmProduct.getItemOther().getItemName());
		}
		if(mdmProduct.getDictProdType()!=null){
			form1.set("prodTypeId", mdmProduct.getDictProdType().getDictItemId());
		}
		if (mdmProduct.getProdBaseUnit() != null) {
			form1.set("prodBaseUnitId", mdmProduct.getProdBaseUnit()
					.getDictItemId());
			form1.set("prodBaseUnitName", mdmProduct.getProdBaseUnit()
					.getItemName());
		}
			if (mdmProduct.getParentProdId() != null) {
				MdmProduct tempMdmProduct = super.entityManager.get(mdmProduct
						.getParentProdId());
				form1.set("parentProdId", tempMdmProduct.getProdId());
				form1.set("parentProdName", tempMdmProduct.getProdName());
			}
			if (mdmProduct.getProdCountUnit() != null) {
				form1.set("prodUnitId", mdmProduct.getProdCountUnit()
						.getDictItemId());
				// form1.set("prodUnit",
				// mdmProduct.getProdCountUnit().getItemName());
			}
			if (mdmProduct.getProdPriceUnit() != null) {
				form1.set("prodPricUnitId", mdmProduct.getProdPriceUnit()
						.getDictItemId());
			}
			//净重单位
			if (mdmProduct.getProdweightUnit() != null) {
				form1.set("prodWeightUnitId", mdmProduct.getProdweightUnit().getDictItemId());
			}
			//体积单位
			if (mdmProduct.getProdVolehUnit() != null) {
				form1.set("prodVolehUnitId", mdmProduct.getProdVolehUnit().getDictItemId());
			}
			//属性1
			if (mdmProduct.getMemo1() != null) {
				form1.set("memo1Id", mdmProduct.getMemo1().getDictItemId());
			}
			//属性2
			if (mdmProduct.getMemo2() != null) {
				form1.set("memo2Id", mdmProduct.getMemo2().getDictItemId());
			}
			//属性3
			if (mdmProduct.getMemo3() != null) {
				form1.set("memo3Id", mdmProduct.getMemo3().getDictItemId());
			}
			//属性4
			if (mdmProduct.getMemo4() != null) {
				form1.set("memo4Id", mdmProduct.getMemo4().getDictItemId());
			}
			//属性5
			if (mdmProduct.getMemo5() != null) {
				form1.set("memo5Id", mdmProduct.getMemo5().getDictItemId());
			}
			//属性6
			if (mdmProduct.getMemo6() != null) {
				form1.set("memo6Id", mdmProduct.getMemo6().getDictItemId());
			}
			
	}
	/**
	 * 初始化一些列表参数及查询参数
	 */
	@SuppressWarnings("rawtypes")
	protected void onInitList(ActionForm form, HttpServletRequest request, List list, Page page) {
		List<BaseDictItem> prodTypeList=null;
		prodTypeList=baseDictManager.getItems("productType", "1");
		request.setAttribute("prodTypeList", prodTypeList);
	}
	
	protected void onInitEntity(ActionForm form, HttpServletRequest request,
			MdmProduct mdmProduct) {
		if (StringUtils.isNotBlank(request.getParameter("prodSTRUId"))) {
			mdmProduct.setProdSTRU(baseDictManager.get(Long.valueOf(request
					.getParameter("prodSTRUId"))));
		} else {
			mdmProduct.setProdSTRU(null);
		}
		
		if (StringUtils.isNotBlank(request.getParameter("brandId"))) {
			mdmProduct.setItemBrand(baseDictManager.get(Long.valueOf(request
					.getParameter("brandId"))));
		} else {
			mdmProduct.setItemBrand(null);
		}
		if (StringUtils.isNotBlank(request.getParameter("typeId"))) {
			mdmProduct.setItemType(baseDictManager.get(Long.valueOf(request
					.getParameter("typeId"))));
		} else {
			mdmProduct.setItemType(null);
		}
		if (StringUtils.isNotBlank(request.getParameter("packId"))) {
			mdmProduct.setItemPack(baseDictManager.get(Long.valueOf(request
					.getParameter("packId"))));
		} else {
			mdmProduct.setItemPack(null);
		}
		
		if (StringUtils.isNotBlank(request.getParameter("otherId"))) {
			mdmProduct.setItemOther(baseDictManager.get(Long.valueOf(request
					.getParameter("otherId"))));
		} else {
			mdmProduct.setItemOther(null);
		}
		
		if (StringUtils.isNotBlank(request.getParameter("prodBaseUnitId"))) {
			mdmProduct.setProdBaseUnit(baseDictManager.get(Long.valueOf(request
					.getParameter("prodBaseUnitId"))));
		}else{
			mdmProduct.setProdBaseUnit(null);
		}
		
		if (StringUtils.isNotBlank(request.getParameter("prodUnitId"))) {
			mdmProduct.setProdCountUnit(baseDictManager.get(Long
					.valueOf(request.getParameter("prodUnitId"))));
		}else{
			mdmProduct.setProdCountUnit(null);
		}
		
		if (StringUtils.isNotBlank(request.getParameter("prodPricUnitId"))) {
			mdmProduct.setProdPriceUnit(baseDictManager.get(Long
					.valueOf(request.getParameter("prodPricUnitId"))));
		}else{
			mdmProduct.setProdPriceUnit(null);
		}
		
		if(StringUtils.isNotBlank(request.getParameter("prodTypeId"))){
			mdmProduct.setDictProdType(baseDictManager.get(Long
					.valueOf(request.getParameter("prodTypeId"))));
		}else{
			mdmProduct.setDictProdType(null);
		}
		//净重单位 
		if(StringUtils.isNotBlank(request.getParameter("prodWeightUnitId"))){
			mdmProduct.setProdweightUnit(baseDictManager.get(Long
					.valueOf(request.getParameter("prodWeightUnitId"))));
		}
		//体积单位 
		if(StringUtils.isNotBlank(request.getParameter("prodVolehUnitId"))){
			mdmProduct.setProdVolehUnit(baseDictManager.get(Long
					.valueOf(request.getParameter("prodVolehUnitId"))));
		}
		//属性1
		if(StringUtils.isNotBlank(request.getParameter("memo1Id"))){
			mdmProduct.setMemo1(baseDictManager.get(Long.valueOf(request.getParameter("memo1Id"))));
		}else{
			mdmProduct.setMemo1(null);
		}
		//属性2
		if(StringUtils.isNotBlank(request.getParameter("memo2Id"))){
			mdmProduct.setMemo2(baseDictManager.get(Long.valueOf(request.getParameter("memo2Id"))));
		}else{
			mdmProduct.setMemo2(null);
		}
		//属性3
		if(StringUtils.isNotBlank(request.getParameter("memo3Id"))){
			mdmProduct.setMemo3(baseDictManager.get(Long.valueOf(request.getParameter("memo3Id"))));
		}else{
			mdmProduct.setMemo3(null);
		}
		//属性4
		if(StringUtils.isNotBlank(request.getParameter("memo4Id"))){
			mdmProduct.setMemo4(baseDictManager.get(Long.valueOf(request.getParameter("memo4Id"))));
		}else{
			mdmProduct.setMemo4(null);
		}
		//属性5
		if(StringUtils.isNotBlank(request.getParameter("memo5Id"))){
			mdmProduct.setMemo5(baseDictManager.get(Long.valueOf(request.getParameter("memo5Id"))));
		}else{
			mdmProduct.setMemo5(null);
		}
		//属性6
		if(StringUtils.isNotBlank(request.getParameter("memo6Id"))){
			mdmProduct.setMemo6(baseDictManager.get(Long.valueOf(request.getParameter("memo6Id"))));
		}else{
			mdmProduct.setMemo6(null);
		}
		BaseEmployee employee = this.getCurrentUser(request).getBaseEmployee();
		mdmProduct.setUpdatedBy(employee.getEmpName());
		mdmProduct.setUpdated(new Date());
		if (mdmProduct.getProdId() == null) {
			mdmProduct.setCreatedBy(employee.getEmpName());
			mdmProduct.setCreated(new Date());
		}else{
			if(mdmProduct.getParentProdId()!=null && mdmProduct.getParentProdId().longValue()==mdmProduct.getProdId().longValue()){
				throw new BusinessException(BeanMessage.getLocaleMessage("i18n/messages", "common.parentProdCanNotRefer", request));
			}
			String oldCode=mdmProductManager.getMdmPordCode(mdmProduct.getProdId());
			mdmProductManager.getSession().evict(mdmProduct);
			if(oldCode!=null && !oldCode.equals(mdmProduct.getProdCode())){
				if(mdmProductManager.countMapping(oldCode)>0)
					throw new BusinessException(BeanMessage.getLocaleMessage("i18n/messages", "common.prodWasMapped", request));
			}
		}
	}
	/**
	 * 在删除entity之前的一些其它操作，如删除子对象等
	 */
	protected void onDeleteEntity(ActionForm form, HttpServletRequest request) {
		String prodId=request.getParameter("prodId");
		if(StringUtils.isNotBlank(prodId)){
				MdmProduct obj = mdmProductManager.getProductById(Long.valueOf(prodId));
			if(mdmProductManager.countMapping(obj.getProdCode())>0)
				throw new BusinessException(BeanMessage.getLocaleMessage("i18n/messages", "common.referenceThisProdMap", request));
		}
	}
	/**
	 * 在保存entity之后的一些其它操作,如保存子对象等
	 */
	protected void onSaveEntity(ActionForm form, HttpServletRequest request, MdmProduct mdmPorduct) {
		
		
	    /*String unit1[] = request.getParameterValues("convUnit1Name");
		String unit2[] = request.getParameterValues("convUnit2Name");
		String convVal[] = request.getParameterValues("Unit2Val");
		//增加一个比较并更新dms_prod_mapping 表
		mdmConversionManager.compareUnitConvert(mdmPorduct.getProdCode(), unit1, convVal, unit2);
		mdmConversionManager.delConvByProdCode(mdmPorduct.getProdCode());
		mdmConversionManager.addConv(mdmPorduct.getProdCode(), unit1, convVal, unit2);	*/
	}

	protected void refrenceData(HttpServletRequest request) {
		List<?> unitList = null;
		//产品类型
		List<?> prodTypeList=null;
/*		if (request.getParameter("prodId") != null
				&& request.getParameter("prodId").length() > 0) {
			List<?> convList = null;
			convList = mdmConversionManager
					.getUnitConversionListByProdCode(mdmProductManager
							.getProductById(
									Long.valueOf(request.getParameter("prodId")))
							.getProdCode());
			request.setAttribute("convList", convList);
		}*/
		unitList = baseDictManager.getItems("prodUnit", "1");
		prodTypeList=baseDictManager.getItems("productType", "1");
		request.setAttribute("utilList", unitList);
		request.setAttribute("prodTypeList", prodTypeList);
		//产品属性1
		request.setAttribute("prodAttr1List", baseDictManager.getItems("prodAttr1", "1"));
		//产品属性2
		request.setAttribute("prodAttr2List", baseDictManager.getItems("prodAttr2", "1"));
		//产品属性3
		request.setAttribute("prodAttr3List", baseDictManager.getItems("prodAttr3", "1"));
		//产品属性4
		request.setAttribute("prodAttr4List", baseDictManager.getItems("prodAttr4", "1"));
		//产品属性5
		request.setAttribute("prodAttr5List", baseDictManager.getItems("prodAttr5", "1"));
		//产品属性6
		request.setAttribute("prodAttr6List", baseDictManager.getItems("prodAttr6", "1"));
	}

	public ActionForward getProdName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String targetProdCode = request.getParameter("targetProdCode");
		MdmProduct mdmProduct = entityManager
				.getProductByProdCode(targetProdCode);
		if (mdmProduct != null) {
			// this.renderXML(response, mdmProduct.getProdName());
		}
		return null;
	}

}
