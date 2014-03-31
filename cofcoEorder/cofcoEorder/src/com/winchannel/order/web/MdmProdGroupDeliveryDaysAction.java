package com.winchannel.order.web;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.LazyValidatorForm;

import com.winchannel.base.model.BaseEmployee;
import com.winchannel.core.utils.DateUtils;
import com.winchannel.core.utils.StringUtils;
import com.winchannel.core.web.StrutsEntityAction;
import com.winchannel.base.utils.DateUtility;
import com.winchannel.order.bean.DistEmpProdgroup;
import com.winchannel.order.model.MdmDistEmpProdgroup;
import com.winchannel.order.model.MdmProdGroupDeliveryDays;
import com.winchannel.order.service.MdmProdGroupDeliveryDaysManager;

public class MdmProdGroupDeliveryDaysAction extends StrutsEntityAction<MdmProdGroupDeliveryDays, MdmProdGroupDeliveryDaysManager> {
		
	private MdmProdGroupDeliveryDaysManager mdmProdGroupDeliveryDaysManager;

	/**
	 * 编辑页查看
	 * 
	 */		
	protected void onInitForm(ActionForm form, HttpServletRequest request, MdmProdGroupDeliveryDays mdmProdGroupDeliveryDays) {
		LazyValidatorForm form1 = (LazyValidatorForm) form;
		if (mdmProdGroupDeliveryDays.getItemProdStru() != null) {
			form1.set("prodBrandId", mdmProdGroupDeliveryDays.getItemProdStru().getDictItemId().toString());
			form1.set("prodBrandName", mdmProdGroupDeliveryDays.getItemProdStru().getItemName());
		}
	}

	
	/**
	 * 
	 * 新增和修改
	 * 
	 */
	protected void onInitEntity(ActionForm form, HttpServletRequest request, MdmProdGroupDeliveryDays mdmProdGroupDeliveryDays) {
		BaseEmployee employee = this.getCurrentUser(request).getBaseEmployee();
		if(StringUtils.isNotBlank(request.getParameter("id"))){
			mdmProdGroupDeliveryDays.setId(new Long(request.getParameter("id")));
			mdmProdGroupDeliveryDays.setUpdatedBy(employee.getEmpName());
			mdmProdGroupDeliveryDays.setUpdated(DateUtils.getDate());
		}else{
			mdmProdGroupDeliveryDays.setCreated(DateUtils.getDate());
			mdmProdGroupDeliveryDays.setCreatedBy(employee.getEmpName());
		}
		if (StringUtils.isNotBlank(request.getParameter("prodBrandId"))) {
			mdmProdGroupDeliveryDays.setItemProdStru(baseDictManager.get(Long.valueOf(request.getParameter("prodBrandId"))));
		} else {
			mdmProdGroupDeliveryDays.setItemProdStru(null);
		}
		mdmProdGroupDeliveryDaysManager.save(mdmProdGroupDeliveryDays);
	}
	
	public ActionForward getProdDelivery(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String prodBrandId = request.getParameter("prodBrandId");
		List<MdmProdGroupDeliveryDays> mdmProdGroupDeliveryDays = new ArrayList<MdmProdGroupDeliveryDays>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT id as id FROM MDM_PROD_GROUP_DELIVERY_DAYS WHERE PROD_STRU_ID="+prodBrandId);
		if(StringUtils.isNotBlank(request.getParameter("id"))){
			sql.append(" and ID != " + request.getParameter("id"));
		}
		mdmProdGroupDeliveryDays= this.entityManager.executeSqlQuery(MdmProdGroupDeliveryDays.class, sql.toString());
		if (mdmProdGroupDeliveryDays.size() > 0) {
			this.renderXML(response, mdmProdGroupDeliveryDays.get(0).getDeliveryDays().toString());
		}else{
			this.renderXML(response,"");
		}
		return null;
	}
	
	
	
	
	public MdmProdGroupDeliveryDaysManager getMdmProdGroupDeliveryDaysManager() {
		return mdmProdGroupDeliveryDaysManager;
	}

	public void setMdmProdGroupDeliveryDaysManager(
			MdmProdGroupDeliveryDaysManager mdmProdGroupDeliveryDaysManager) {
		this.mdmProdGroupDeliveryDaysManager = mdmProdGroupDeliveryDaysManager;
	}
	
	

		

}