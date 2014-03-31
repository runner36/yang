package com.winchannel.mdm.store.web;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.LazyValidatorForm;

import com.winchannel.base.model.BaseEmployee;
import com.winchannel.base.service.BaseOrgManager;
import com.winchannel.core.exception.BusinessException;
import com.winchannel.core.utils.DateUtils;
import com.winchannel.core.utils.StringUtils;
import com.winchannel.core.web.StrutsEntityAction;
import com.winchannel.mdm.distributor.service.MdmDistributorManager;
import com.winchannel.mdm.store.model.MdmStore;
import com.winchannel.mdm.store.service.MdmStoreManager;
import com.winchannel.mdm.util.i18n.BeanMessage;

public class MdmStoreAction extends StrutsEntityAction<MdmStore, MdmStoreManager> {
	private final static String STORE_PREFIX = "store_";
	private final static String STARTDATE_PREFIX = "startDate_";
	private final static String SPLIT = "_";

	private MdmStoreManager mdmStoreManager;
	private BaseOrgManager baseOrgManager;
	private MdmDistributorManager mdmDistributorManager;

	public MdmDistributorManager getMdmDistributorManager() {
		return mdmDistributorManager;
	}

	public void setMdmDistributorManager(MdmDistributorManager mdmDistributorManager) {
		this.mdmDistributorManager = mdmDistributorManager;
	}

	public MdmStoreManager getMdmStoreManager() {
		return mdmStoreManager;
	}

	public void setMdmStoreManager(MdmStoreManager mdmStoreManager) {
		this.mdmStoreManager = mdmStoreManager;
	}

	public BaseOrgManager getBaseOrgManager() {
		return baseOrgManager;
	}

	public void setBaseOrgManager(BaseOrgManager baseOrgManager) {
		this.baseOrgManager = baseOrgManager;
	}
	protected void init() {
		this.setFirstQuery(false);
	}
	protected void onInitForm(ActionForm form, HttpServletRequest request, MdmStore mdmStore) {
		LazyValidatorForm form1 = (LazyValidatorForm) form;
		if (mdmStore.getStoreRelationState() == null) {
			form1.set("storeRelationState", "1");
		}
		if (mdmStore.getState() == null) {
			form1.set("state", "1");
		}
		if (mdmStore.getMdmStore() != null) {
			form1.set("parentStoreId", mdmStore.getMdmStore().getStoreId().toString());
			form1.set("parentStoreName", mdmStore.getMdmStore().getStoreName());
		}
		if (mdmStore.getMdmStore() != null) {
			form1.set("parentStoreId", mdmStore.getMdmStore().getStoreId().toString());
			form1.set("parentStoreName", mdmStore.getMdmStore().getStoreName());
		}
		if (mdmStore.getBaseOrg() != null) {
			form1.set("orgId", mdmStore.getBaseOrg().getOrgId().toString());
			form1.set("orgName", mdmStore.getBaseOrg().getOrgName());
		}
		if (mdmStore.getStoreGeo() != null) {
			form1.set("geoId", mdmStore.getStoreGeo().getDictItemId().toString());
			form1.set("geoName", mdmStore.getStoreGeo().getItemName());
		}
		//
		if (mdmStore.getStoreType() != null) {
			form1.set("typeId", mdmStore.getStoreType().getDictItemId().toString());
			form1.set("typeName", mdmStore.getStoreType().getItemName());
		}
		if (mdmStore.getStoreNature() != null) {
			form1.set("natureId", mdmStore.getStoreNature().getDictItemId().toString());
			form1.set("natureName", mdmStore.getStoreNature().getItemName());
		}
		if (mdmStore.getStoreCorp() != null) {
			form1.set("corpId", mdmStore.getStoreCorp().getDictItemId().toString());
			form1.set("corpName", mdmStore.getStoreCorp().getItemName());
		}
		if (mdmStore.getStoreOther() != null) {
			form1.set("otherId", mdmStore.getStoreOther().getDictItemId().toString());
			form1.set("otherName", mdmStore.getStoreOther().getItemName());
		}
		if (mdmStore.getStoreChannel() != null) {
			form1.set("channelId", mdmStore.getStoreChannel().getDictItemId().toString());
			form1.set("channelName", mdmStore.getStoreChannel().getItemName());
		}
		if (mdmStore.getMdmDistributor() != null) {
			form1.set("distId", mdmStore.getMdmDistributor().getDistId().toString());
			form1.set("distName", mdmStore.getMdmDistributor().getDistName());
		}
	}

	protected void onInitEntity(ActionForm form, HttpServletRequest request, MdmStore mdmStore) {
		if (StringUtils.isNotBlank(request.getParameter("parentStoreId"))) {
			mdmStore.setMdmStore(mdmStoreManager.get(Long.valueOf(request.getParameter("parentStoreId"))));
		} else {
			mdmStore.setMdmStore(null);
		}
		if (StringUtils.isNotBlank(request.getParameter("orgId"))) {
			mdmStore.setBaseOrg(baseOrgManager.get(Long.valueOf(request.getParameter("orgId"))));
		} else {
			mdmStore.setBaseOrg(null);
		}
		if (StringUtils.isNotBlank(request.getParameter("geoId"))) {
			mdmStore.setStoreGeo(baseDictManager.get(Long.valueOf(request.getParameter("geoId"))));
		} else {
			mdmStore.setStoreGeo(null);
		}
		if (StringUtils.isNotBlank(request.getParameter("typeId"))) {
			mdmStore.setStoreType(baseDictManager.get(Long.valueOf(request.getParameter("typeId"))));
		} else {
			mdmStore.setStoreType(null);
		}
		if (StringUtils.isNotBlank(request.getParameter("natureId"))) {
			mdmStore.setStoreNature(baseDictManager.get(Long.valueOf(request.getParameter("natureId"))));
		} else {
			mdmStore.setStoreNature(null);
		}
		if (StringUtils.isNotBlank(request.getParameter("corpId"))) {
			mdmStore.setStoreCorp(baseDictManager.get(Long.valueOf(request.getParameter("corpId"))));
		} else {
			mdmStore.setStoreCorp(null);
		}
		if (StringUtils.isNotBlank(request.getParameter("otherId"))) {
			mdmStore.setStoreOther(baseDictManager.get(Long.valueOf(request.getParameter("otherId"))));
		} else {
			mdmStore.setStoreOther(null);
		}
		if (StringUtils.isNotBlank(request.getParameter("channelId"))) {
			mdmStore.setStoreChannel(baseDictManager.get(Long.valueOf(request.getParameter("channelId"))));
		} else {
			mdmStore.setStoreChannel(null);
		}
		if (StringUtils.isNotBlank(request.getParameter("distId"))) {
			mdmStore.setMdmDistributor(mdmDistributorManager.get(Long.valueOf(request.getParameter("distId"))));
		} else {
			mdmStore.setMdmDistributor(null);
		}

		BaseEmployee employee = this.getCurrentUser(request).getBaseEmployee();
		mdmStore.setUpdatedBy(employee.getEmpName());
		mdmStore.setUpdated(DateUtils.getDate());
		if (mdmStore.getStoreId() == null) {
			mdmStore.setCreatedBy(employee.getEmpName());
			mdmStore.setCreated(DateUtils.getDate());
		}else
		{
			mdmStoreManager.getSession().evict(mdmStore);
			String oldStoreCode=mdmStoreManager.getMdmStoreCode(mdmStore.getStoreId());
			if(oldStoreCode!=null && !(oldStoreCode.equals(mdmStore.getStoreCode()))){
				if(mdmStoreManager.countMapping(oldStoreCode)>0)
					throw new BusinessException(BeanMessage.getLocaleMessage("i18n/messages", "common.storeWasMapped", request));
			}
		}
		
		mdmStoreManager.save(mdmStore);
	}
	
	public ActionForward saveCheckOrg(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		List<String[]> params = parseParams(request);
		entityManager.saveCheckOrg(params);
		this.savedMessage(request, null);
		return list(mapping, form, request, response);
	}
	
	private List<String[]> parseParams(HttpServletRequest request) {
        Enumeration<?> parameters = request.getParameterNames();
        List<String[]> params = new ArrayList<String[]>();
        while (parameters.hasMoreElements()) {
        	String name = (String) parameters.nextElement();
        	if (name.startsWith(STORE_PREFIX)) {
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
	
	/**
	 * 在删除entity之前的一些其它操作，如删除子对象等
	 */
	protected void onDeleteEntity(ActionForm form, HttpServletRequest request) {
		String storeId=request.getParameter("storeId");
		if(StringUtils.isNotBlank(storeId)){
			MdmStore obj = mdmStoreManager.getMdmStoreById(Long.valueOf(storeId));
			if(mdmStoreManager.countMapping(obj.getStoreCode())>0)
				throw new BusinessException(BeanMessage.getLocaleMessage("i18n/messages", "common.referenceThisStoreMap", request));
		}
	}
	
}
