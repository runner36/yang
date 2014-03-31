package com.winchannel.mdm.empstore.service;

import java.util.ArrayList;
import java.util.List;

import com.winchannel.base.model.BaseEmployee;
import com.winchannel.base.service.BaseDictManager;
import com.winchannel.base.service.BaseEmployeeManager;
import com.winchannel.base.service.BaseOrgManager;
import com.winchannel.core.dao.HibernateEntityDao;
import com.winchannel.mdm.distributor.service.MdmDistributorManager;
import com.winchannel.mdm.empstore.model.MdmEmpStoreItem;
import com.winchannel.mdm.store.model.MdmStore;

public class MdmEmpStoreItemManager extends HibernateEntityDao<MdmEmpStoreItem> {
	private BaseDictManager baseDictManager;
	private BaseOrgManager baseOrgManager;
	private BaseEmployeeManager baseEmployeeManager;
	private MdmDistributorManager mdmDistributorManager;

	public BaseDictManager getBaseDictManager() {
		return baseDictManager;
	}

	public void setBaseDictManager(BaseDictManager baseDictManager) {
		this.baseDictManager = baseDictManager;
	}

	public BaseOrgManager getBaseOrgManager() {
		return baseOrgManager;
	}

	public void setBaseOrgManager(BaseOrgManager baseOrgManager) {
		this.baseOrgManager = baseOrgManager;
	}

	public MdmDistributorManager getMdmDistributorManager() {
		return mdmDistributorManager;
	}

	public void setMdmDistributorManager(MdmDistributorManager mdmDistributorManager) {
		this.mdmDistributorManager = mdmDistributorManager;
	}
	

	public void setBaseEmployeeManager(BaseEmployeeManager baseEmployeeManager) {
		this.baseEmployeeManager = baseEmployeeManager;
	}

	/**
	 * 根据emp_id得到人员可以负责的门店
	 * */
	public List<MdmStore> getMdmStoresByEmpId(String empId) {
		List<MdmStore> list = new ArrayList<MdmStore>();
		BaseEmployee emp = this.baseEmployeeManager.get(empId);
		if (emp.getBaseOrg() != null) {
			String hql = "from MdmStore where storeGeo.dictItemId in (select baseDictItem.dictItemId from BaseOrgGeo where baseOrg.orgId="+emp.getBaseOrg().getOrgId()+")";
			list = this.find(hql);
		}
		return list;
	}
	
}
