package com.winchannel.mdm.empstore.service;

import com.winchannel.base.service.BaseDictManager;
import com.winchannel.base.service.BaseOrgManager;
import com.winchannel.core.dao.HibernateEntityDao;
import com.winchannel.mdm.distributor.service.MdmDistributorManager;
import com.winchannel.mdm.empstore.model.MdmEmpStore;


public class MdmEmpStoreManager extends HibernateEntityDao<MdmEmpStore> {
	private BaseDictManager baseDictManager;
	private BaseOrgManager baseOrgManager;
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

	
}
