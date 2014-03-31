package com.winchannel.base.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.winchannel.base.model.BaseDictItem;
import com.winchannel.base.model.BaseOrg;
import com.winchannel.base.model.BaseOrgGeo;
import com.winchannel.core.dao.HibernateEntityDao;
import com.winchannel.core.exception.BusinessException;
import com.winchannel.core.utils.Page;
import com.winchannel.core.utils.StringUtils;
import com.winchannel.mdm.distributor.model.MdmDistributor;

public class BaseOrgManager extends HibernateEntityDao<BaseOrg> {

	private BaseDictManager baseDictManager;
	
	
	public BaseDictManager getBaseDictManager() {
		return baseDictManager;
	}

	public void setBaseDictManager(BaseDictManager baseDictManager) {
		this.baseDictManager = baseDictManager;
	}

	public void save(Object object) {
		if (!this.isUnique(object, "orgCode")) {
			throw new BusinessException("base","common.orgCodeDuplication");
		}
		if (!this.isUnique(object, "extCode")) {
			throw new BusinessException("base","common.externalCodeDuplication");
		}
		this.makeSubCode(object);
		super.save(object);
		this.makeParentInfo(object, "orgName");
	}

	public List<BaseOrg> getOrgs(String parentSubCode, String state) {
		StringBuffer queryString = new StringBuffer("from BaseOrg where 1=1");
		if (StringUtils.isNotBlank(parentSubCode)) {
			queryString.append(" and subCode like '" + parentSubCode + "%'");
		}
		if (StringUtils.isNotBlank(state)) {
			queryString.append(" and state=" + state);
		}
		queryString.append(" order by levelCode,sort");
		return this.findEntity(queryString.toString());
	}

	public List<BaseOrg> getOrgs(Page page) {
		StringBuffer queryString = new StringBuffer("from BaseOrg where 1=1");
		if (StringUtils.isNotBlank(page.get("state"))) {
			queryString.append(" and state=" + page.get("state"));
		}
		queryString.append(this.makeQueryConditions(page, null));
		queryString.append(" order by levelCode,sort");
		return this.findEntity(queryString.toString());
	}

	public String getOrgsByIds(String orgIds) {
		StringBuffer orgNames = new StringBuffer();
		if (StringUtils.isNotBlank(orgIds)) {
			Iterator it = this.find("select orgName from BaseOrg where orgId in (" + orgIds + ")").iterator();
			while (it.hasNext()) {
				orgNames.append(it.next()).append(",");
			}
		}
		return orgNames.toString();
	}

	public void saveOrgGeos(BaseOrg baseOrg, String geoIds) {
		// 删除之前的保存记录
		this.deleteAll("from BaseOrgGeo where baseOrg.orgId=?", baseOrg.getOrgId());
		StringBuffer geoNameAll=new StringBuffer();
		// 保存新记录
		if (StringUtils.isNotBlank(geoIds)) {
			String[] arr = geoIds.split(",");
			List<BaseOrgGeo> saveList = new ArrayList<BaseOrgGeo>(arr.length);
			for (String str : arr) {
				BaseOrgGeo bog = new BaseOrgGeo();
				bog.setBaseOrg(baseOrg);
				bog.setBaseDictItem(baseDictManager.get(Long.parseLong(str)));
				saveList.add(bog);
				super.save(bog);
				geoNameAll.append(bog.getBaseDictItem().getItemCode()).append(",");
			}
			baseOrg.setGeoAll(geoNameAll.toString());
		}else {
			baseOrg.setGeoAll(null);
		}
	}

	public String[] getOrgGeosByOrgId(String orgId) {
		StringBuffer orgGeoIds = new StringBuffer();
		StringBuffer orgGeoNames = new StringBuffer();
		String[] result = new String[2];
		if (StringUtils.isNotBlank(orgId)) {
			String sql = "SELECT item.DICT_ITEM_ID,item.ITEM_NAME "
					+ "FROM BASE_ORG_GEO bog INNER JOIN BASE_DICT_ITEM item ON bog.GEO_ID=item.DICT_ITEM_ID " + "WHERE bog.ORG_ID=" + orgId
					+ " ORDER BY item.SORT ASC";
			List<Object[]> list = this.executeSqlQuery(sql);
			for (Object[] obj : list) {
				orgGeoIds.append(obj[0]).append(",");
				orgGeoNames.append(obj[1]).append(",");
			}
			result[0] = orgGeoIds.toString();
			result[1] = orgGeoNames.toString();
		}
		return result;
	}
	
	/**
	 * 得到组织
	 * 
	 * @param baseOrgManager
	 * @param name
	 * @return
	 */
	public BaseOrg getBaseOrgByCode(String orgCode) {
		return findUniqueEntity("from BaseOrg where orgCode=?", orgCode);
	}
}
