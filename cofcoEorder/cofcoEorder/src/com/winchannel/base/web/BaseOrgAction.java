package com.winchannel.base.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.validator.LazyValidatorForm;

import com.winchannel.base.model.BaseEmployee;
import com.winchannel.base.model.BaseOrg;
import com.winchannel.base.service.BaseOrgManager;
import com.winchannel.base.utils.Constants;
import com.winchannel.core.utils.Page;
import com.winchannel.core.web.StrutsEntityAction;

public class BaseOrgAction extends StrutsEntityAction<BaseOrg, BaseOrgManager> {

	@Override
	public void init() {
		this.setSuccessView(EDIT);
	}

	@Override
	protected void onInitList(ActionForm form, HttpServletRequest request, List list, Page page) {
	}

	@Override
	protected void onInitForm(ActionForm form, HttpServletRequest request, BaseOrg baseOrg) {
		LazyValidatorForm baseOrgForm = (LazyValidatorForm) form;

		BaseOrg parentBaseOrg = baseOrg.getBaseOrg();
		if (StringUtils.isNotBlank(request.getParameter("parentId"))) {
			parentBaseOrg = entityManager.get(new Long(request.getParameter("parentId")));
		}
		if (parentBaseOrg != null) {
			baseOrgForm.set("parentId", parentBaseOrg.getOrgId().toString());
			baseOrgForm.set("parentName", parentBaseOrg.getOrgName());
		}
		if (baseOrg.getBaseDictItem() != null) {
			baseOrgForm.set("orgTypeId", baseOrg.getBaseDictItem().getDictItemId().toString());
		}
		if (baseOrg.getOrgId() == null) {
			baseOrgForm.set("state", "1");
		} else {
			// 查询负责地理区域
			String[] orgGeos = entityManager.getOrgGeosByOrgId(baseOrg.getOrgId().toString());
			baseOrgForm.set("geoIds", orgGeos[0]);
			baseOrgForm.set("geoNames", orgGeos[1]);
		}

		if (baseOrg.getBaseEmployee() != null) {
			baseOrgForm.set("empId", baseOrg.getBaseEmployee().getEmpId());
			baseOrgForm.set("empName", baseOrg.getBaseEmployee().getEmpName());
		}
	}

	@Override
	protected void onInitEntity(ActionForm form, HttpServletRequest request, BaseOrg baseOrg) {
		if (StringUtils.isNotBlank(request.getParameter("parentId"))) {
			baseOrg.setBaseOrg(entityManager.get(new Long(request.getParameter("parentId"))));
		} else {
			baseOrg.setBaseOrg(null);
		}
		if (StringUtils.isNotBlank(request.getParameter("orgTypeId"))) {
			baseOrg.setBaseDictItem(baseDictManager.get(new Long(request.getParameter("orgTypeId"))));
		} else {
			baseOrg.setBaseDictItem(null);
		}
		if (StringUtils.isNotBlank(request.getParameter("empId"))) {
			baseOrg.setBaseEmployee(baseDictManager.get(BaseEmployee.class, new Long(request.getParameter("empId"))));
		} else {
			baseOrg.setBaseEmployee(null);
		}

		String currEmpName = this.getCurrentUser(request).getBaseEmployee().getEmpName();
		if (baseOrg.getOrgId() == null) {
			baseOrg.setCreatedBy(currEmpName);
			baseOrg.setCreated(new Date());
		}
		baseOrg.setUpdatedBy(currEmpName);
		baseOrg.setUpdated(new Date());
	}

	@Override
	protected void onSaveEntity(ActionForm form, HttpServletRequest request, BaseOrg baseOrg) {
		// 保存负责地理区域
//		if (StringUtils.isNotBlank(request.getParameter("geoIds"))) {
			entityManager.saveOrgGeos(baseOrg, request.getParameter("geoIds"));
//		}
				if(baseOrg!=null){
					BaseOrg porg = baseOrg.getBaseOrg();
						while(porg!=null){
							switch (porg.getLevelCode().intValue()) {
								case 4:
									baseOrg.setPn4(porg.getOrgName());
									baseOrg.setPi4(porg.getOrgId());
									break;
								case 3:
									baseOrg.setPn3(porg.getOrgName());
									baseOrg.setPi3(porg.getOrgId());
									break;
								case 2:
									baseOrg.setPn2(porg.getOrgName());
									baseOrg.setPi2(porg.getOrgId());
									break;
								case 1:
									baseOrg.setPn1(porg.getOrgName());
									baseOrg.setPi1(porg.getOrgId());
									break;
								default:
									break;
							}
							porg = porg.getBaseOrg();
						}
						
						switch (baseOrg.getLevelCode().intValue()) {
							case 5:
								baseOrg.setPn5(baseOrg.getOrgName());
								baseOrg.setPi5(baseOrg.getOrgId());
								baseOrg.setPnall(baseOrg.getPn1()+"-"+baseOrg.getPn2()+"-"+baseOrg.getPn3()+"-"+baseOrg.getPn4()+"-"+baseOrg.getPn5());
								break;
							case 4:
								baseOrg.setPn4(baseOrg.getOrgName());
								baseOrg.setPi4(baseOrg.getOrgId());
								baseOrg.setPnall(baseOrg.getPn1()+"-"+baseOrg.getPn2()+"-"+baseOrg.getPn3()+"-"+baseOrg.getPn4());
								break;
							case 3:
								baseOrg.setPn3(baseOrg.getOrgName());
								baseOrg.setPi3(baseOrg.getOrgId());
								baseOrg.setPnall(baseOrg.getPn1()+"-"+baseOrg.getPn2()+"-"+baseOrg.getPn3());
								break;
							case 2:
								baseOrg.setPn2(baseOrg.getOrgName());
								baseOrg.setPi2(baseOrg.getOrgId());
								baseOrg.setPnall(baseOrg.getPn1()+"-"+baseOrg.getPn2());
								break;
							case 1:
								baseOrg.setPn1(baseOrg.getOrgName());
								baseOrg.setPi1(baseOrg.getOrgId());
								baseOrg.setPnall(baseOrg.getPn1());
								break;
							default:
								break;
						}
				}
				entityManager.save(baseOrg);
	}

	@Override
	protected void refrenceData(HttpServletRequest request) {
		request.setAttribute("orgTypes", baseDictManager.getItems(Constants.DICT_ORG_TYPE, null));
	}

}
