package com.winchannel.mdm.store.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

import com.winchannel.base.model.BaseDictItem;
import com.winchannel.base.model.BaseOrg;
import com.winchannel.base.service.BaseDictManager;
import com.winchannel.base.service.BaseOrgManager;
import com.winchannel.core.dao.HibernateEntityDao;
import com.winchannel.core.exception.BusinessException;
import com.winchannel.core.utils.Page;
import com.winchannel.mdm.distributor.model.MdmDistributor;
import com.winchannel.mdm.distributor.service.MdmDistributorManager;
import com.winchannel.mdm.store.bean.StoreOrgCheck;
import com.winchannel.mdm.store.model.MdmStore;
import com.winchannel.mdm.store.model.MdmStoreLog;

public class MdmStoreManager extends HibernateEntityDao<MdmStore> {
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

	@Override
	public List<MdmStore> query(Page page) {
		List<MdmStore> list = super.query(page);

		Map<String, StoreOrgCheck> map = new HashMap<String, StoreOrgCheck>();
		List<StoreOrgCheck> checkList = this.getCheckOrgList(page);
		for (StoreOrgCheck doc : checkList) {
			map.put(doc.getStoreId(), doc);
		}
		for (MdmStore store : list) {
			store.setStoreOrgCheck(map.get(store.getStoreId().toString()));
		}
		return list;
	}
	
	public void save(Object object) {
		if (!this.isUnique(object, "storeCode")) {
			throw new BusinessException("base","common.codeDuplication");
		}
		if (!this.isUnique(object, "extCode")) {
			throw new BusinessException("base","common.codeDuplication");
		}
		makeSubCode(object);
		
		// 增加保存MdmDistributorLog的控制
		MdmStore store = (MdmStore) object;
		boolean saveLog = false;
		if (store.getStoreId() == null) {
			saveLog = true;
		}
		
		super.save(object);
		
		if (saveLog) {
			this.saveMdmStoreLog((MdmStore) object);
		}
	}

	/**
	 * 得到组织表，通过组织code
	 * 
	 * @param baseOrgManager
	 * @param code
	 * @return
	 */
	public BaseOrg getBaseOrgByProdCode(String code) {
		return baseOrgManager.findUniqueEntity("from BaseOrg where orgCode=?", code);
	}

	/**
	 * 得到经销商
	 * 
	 * @param mdmDistributorManager
	 * @param code
	 * @return
	 */
	public MdmDistributor getMdmDistributorByProdCode(String code) {
		return mdmDistributorManager.findUniqueEntity("from MdmDistributor where distCode=?", code);
	}

	/**
	 * 通过code得到字典表里的相关的id
	 * 
	 * @param baseDictManager
	 * @param code
	 * @return
	 */
	public BaseDictItem getDistByProdCode(String code, String dictId) {
		return baseDictManager.findUniqueEntity("from BaseDictItem where itemCode='" + code + "' and baseDict.dictId='" + dictId + "'");
	}

	/**
	 * 通过code得到门店
	 * 
	 * @param prodCode
	 * @return
	 */
	public MdmStore getMdmStoreByStoreCode(String code) {
		return findUniqueEntity("from MdmStore where storeCode=?", code);
	}

	/**
	 * 通过外部code得到门店
	 * 
	 * @param prodCode
	 * @return
	 */
	public MdmStore getMdmStoreByExtCode(String code, String extCode) {
		String hql = "";
		if (code != null && !"".equals(code))
			hql += " storeCode='" + code + "'";
		if (extCode != null && !"".equals(extCode))
			hql += " extCode='" + extCode + "'";
		return findUniqueEntity("from MdmStore where " + hql);
	}

	/**
	 * 得到排序号
	 * 
	 * @return
	 */
	public long getSort() {
		String sql = "select max(SORT) from  MDM_STORE ";
		List<?> list = this.executeSqlQuery(sql);
		long sort = 1;
		if (list != null && list.size() > 0) {
			Object[] obj = (Object[]) list.get(0);
			if (obj[0] != null) {
				sort = ((java.math.BigDecimal) obj[0]) == null ? 1 : ((java.math.BigDecimal) obj[0]).longValue() + 1;
			}
		}
		return sort;
	}

	public List<StoreOrgCheck> getCheckOrgList(Page page) {
		StringBuffer sql = new StringBuffer(700);
		sql.append("SELECT CAST(store.STORE_ID AS VARCHAR)+'_'+CAST(isnull(orgInit.ORG_ID,0) AS VARCHAR) AS flag,").append(
				"store.STORE_ID,store.STORE_NAME,orgInit.ORG_ID AS initOrgId,orgInit.ORG_NAME AS initOrgName,").append(
				"bog.ORG_ID AS sugOrgId,org.ORG_NAME AS sugOrgName,bog.GEO_ID,item.ITEM_NAME AS geoFullName,store.STORE_CODE, ").append(
				"lg.org_name,lg.START_DATE,lg.END_DATE,lg.UPDATED ").append(
				"FROM MDM_STORE store LEFT JOIN BASE_ORG_GEO bog ON store.GEO_ID=bog.GEO_ID ").append(
				"INNER JOIN BASE_ORG validOrg ON validOrg.ORG_ID=bog.ORG_ID  AND validOrg.STATE='1' ").append(
				"LEFT JOIN BASE_GEO_MLIST item ON item.DICT_ITEM_ID=bog.GEO_ID ").append(
				"LEFT JOIN BASE_ORG_MLIST org ON org.ORG_ID=bog.ORG_ID ").append(
				"LEFT JOIN BASE_ORG_MLIST orgInit ON orgInit.ORG_ID=store.org_id ").append(
				"LEFT JOIN (SELECT a.STORE_ID,CONVERT(varchar(10),a.START_DATE,120) AS START_DATE,CONVERT(varchar(10),a.END_DATE,120) AS END_DATE,CONVERT(varchar(10),a.UPDATED,120) AS UPDATED,s.org_name ").append(
				"FROM MDM_STORE_LOG a INNER JOIN BASE_ORG_MLIST s ON a.ORG_ID=s.org_id ").append(
				"WHERE ID=(SELECT max(id) FROM MDM_STORE_LOG b WHERE a.STORE_ID=b.STORE_ID) ").append(
				") lg ON lg.STORE_ID=store.STORE_ID ").append(
				"where 1=1 ");
		if (com.winchannel.core.utils.StringUtils.isNotBlank(page.get("$lk_storeCode"))) {
			sql.append(" AND store.STORE_CODE LIKE '%" + page.get("$lk_storeCode") + "%'");
		}
		if (com.winchannel.core.utils.StringUtils.isNotBlank(page.get("$lk_storeName"))) {
			sql.append(" AND store.STORE_NAME LIKE '%" + page.get("$lk_storeName") + "%'");
		}
		List<Object[]> objList = this.executeSqlQuery(sql.toString());
		List<StoreOrgCheck> list = new ArrayList<StoreOrgCheck>(objList.size());
		for (Object[] obj : objList) {
			StoreOrgCheck doc = new StoreOrgCheck();
			doc.setFlag(obj[0] + "");
			doc.setStoreId(obj[1] + "");
			doc.setStoreName(obj[2] + "");
			if (obj[3] != null) {
				doc.setInitOrgId(obj[3] + "");
			}
			if (obj[4] != null) {
				doc.setInitOrgName(obj[4] + "");
			}
			if (obj[5] != null) {
				doc.setSugOrgId(obj[5] + "");
			}
			if (obj[6] != null) {
				doc.setSugOrgName(obj[6] + "");
			}
			if (obj[7] != null) {
				doc.setGeoId(obj[7] + "");
			}
			if (obj[8] != null) {
				doc.setGeoFullName(obj[8] + "");
			}
			doc.setStoreCode(obj[9] + "");
			
			if (obj[10] != null) {
				doc.setLastLogOrgName(obj[10] + "");
			}
			if (obj[11] != null) {
				doc.setStartDate(obj[11] + "");
			}
			if (obj[13] != null) {
				doc.setUpdateDate(obj[13] + "");
			}
			list.add(doc);
		}
		// 将存在建议组织的门店记录合并
		return this.dealCheckOrgList(list);
	}

	private List<StoreOrgCheck> dealCheckOrgList(List<StoreOrgCheck> list) {
		List<StoreOrgCheck> result = new ArrayList<StoreOrgCheck>();
		Map<String, StoreOrgCheck> map = new HashMap<String, StoreOrgCheck>();
		for (StoreOrgCheck doc : list) {
			if (map.get(doc.getFlag()) == null) {
				StoreOrgCheck org = new StoreOrgCheck();
				org.setSugOrgId(doc.getSugOrgId());
				org.setSugOrgName(doc.getSugOrgName());
				doc.getSugOrgList().add(org);
				map.put(doc.getFlag(), doc);
				result.add(doc);
			} else {
				StoreOrgCheck temp = map.get(doc.getFlag());
				StoreOrgCheck org = new StoreOrgCheck();
				org.setSugOrgId(doc.getSugOrgId());
				org.setSugOrgName(doc.getSugOrgName());
				temp.getSugOrgList().add(org);
			}
		}
		return result;
	}

	public void saveCheckOrg(List<String[]> params) {
//		Map<String, MdmStore> storeMap = new HashMap<String, MdmStore>();
//		List<MdmStore> storeList = this.getAll();
//		for (MdmStore store : storeList) {
//			storeMap.put(store.getStoreId().toString(), store);
//		}
		try {
			for (String[] arr : params) {
//				MdmStore store = storeMap.get(arr[0]);
				MdmStore store = this.get(Long.parseLong(arr[0]));
//				if (store.getBaseOrg() == null || !arr[1].equals(store.getBaseOrg().getOrgId().toString())) {// 经销商所属组织发生改变
					MdmStoreLog storeLog = new MdmStoreLog();
					PropertyUtils.copyProperties(storeLog, store);
					BaseOrg org = new BaseOrg();
					org.setOrgId(Long.parseLong(arr[1]));
					storeLog.setBaseOrg(org);
					dealStoreLog(storeLog, arr[2]);
//				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	private void dealStoreLog(MdmStoreLog storeLog, String startDate) {
		// String startDate = com.winchannel.core.utils.DateUtils.format(new
		// Date(), "yyyy-MM-dd");
		Date yesterday = com.winchannel.core.utils.DateUtils.getYesterdayByDate(startDate, "yyyy-MM-dd");
		// 先删除没有生效的记录
		this.deleteAll("from MdmStoreLog where storeId=" + storeLog.getStoreId() + " and startDate>'" + startDate + "'");
		// 把已经生效的最后一条记录的结束时间设置为today的前一天
		List<MdmStoreLog> list = this.findEntity(MdmStoreLog.class,
				"from MdmStoreLog where id=(select max(id) from MdmStoreLog where storeId=" + storeLog.getStoreId() + ")");
		MdmStoreLog lastStoreLog = list.get(0);
		lastStoreLog.setEndDate(yesterday);
		super.save(lastStoreLog);
		storeLog.setStartDate(com.winchannel.core.utils.DateUtils.parse(startDate, "yyyy-MM-dd"));
		// 把本次保存的记录终止时间保存2099-12-31
		storeLog.setEndDate(com.winchannel.core.utils.DateUtils.getEndDate());
		storeLog.setUpdated(new Date());
		super.save(storeLog);
	}

	public void saveMdmStoreLog(MdmStore store) {
		MdmStoreLog storeLog = new MdmStoreLog();
		Date today = new Date();
		try {
			PropertyUtils.copyProperties(storeLog, store);
			storeLog.setCreated(today);
			storeLog.setStartDate(today);
			storeLog.setEndDate(com.winchannel.core.utils.DateUtils.getEndDate());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		super.save(storeLog);
	}
	public int countMapping(String storeCode){
		int count=0;
		 Object[] obj=this.executeUniqueSqlQuery("SELECT count(d.ACTIVE_S_ID) FROM DMS_STORE_MAPPING d WHERE d.TARGET_STORE_CODE='"+storeCode+"'");
		  if(obj!=null){
			  count=Integer.valueOf(obj[0].toString());
		  }
		return count;
	}
	public  String  getMdmStoreCode(long storeId) {
		Object[] obj=this.executeUniqueSqlQuery("SELECT d.STORE_CODE FROM MDM_STORE d WHERE d.STORE_ID="+storeId);
		  if(obj!=null){
			  return obj[0].toString();
		  }
		return null;
	}
	public MdmStore getMdmStoreById(long storeId)
	{
		return this.get(storeId);
	}
	
}
