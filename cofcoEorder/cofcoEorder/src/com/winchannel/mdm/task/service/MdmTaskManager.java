package com.winchannel.mdm.task.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.winchannel.core.dao.HibernateBaseDao;
import com.winchannel.mdm.util.date.DateUtils;


public class MdmTaskManager extends HibernateBaseDao {
	
	public static final Log log = LogFactory.getLog("MdmTaskManager");
	
	/**
	 * 查询当天经销商Log表中start_date是当天的数据，把组织保存到真正的经销商表中
	 */
	public void saveDistOrg(String today) {
		log.info("processSaveDistOrg start ...");
		String sql = "UPDATE MDM_DISTRIBUTOR SET ORG_ID=b.org_id " +
					 "FROM MDM_DISTRIBUTOR_LOG b " +
					 "WHERE MDM_DISTRIBUTOR.DIST_ID=b.DIST_ID " +
					 "AND b.START_DATE='"+today+"'";
		this.executeSqlUpdate(sql);
		log.info("processSaveDistOrg end");
	}
	
	/**
	 * 查询当天经门店Log表中start_date是当天的数据，把组织保存到真正的门店表中
	 */
	public void saveMdmStoreOrg(String today) {
		log.info("processSaveMdmStoreOrg start ...");
		String sql = "UPDATE MDM_STORE SET ORG_ID=b.org_id " +
					 "FROM MDM_STORE_LOG b " +
					 "WHERE MDM_STORE.STORE_ID=b.STORE_ID " +
					 "AND b.START_DATE='"+today+"'";
		this.executeSqlUpdate(sql);
		log.info("processSaveMdmStoreOrg end");
	}
	
	/**
	 * 查询当天人员Log表中，start_date是当天的数据，把组织保存到真正的人员表中
	 */
	public void saveEmpOrg(String today) {
		log.info("processSaveEmpOrg start ...");
		String sql = "UPDATE BASE_EMPLOYEE SET ORG_ID=b.org_id " +
			 		 "FROM BASE_EMPLOYEE_LOG b " +
					 "WHERE BASE_EMPLOYEE.EMP_ID=b.EMP_ID " +
					 "AND b.START_DATE='"+today+"'";
		this.executeSqlUpdate(sql);
		log.info("processSaveEmpOrg end");
	}

	/**
	 * 门店人员关系日结
	 */
	public void processStoreEmpData(String date) {
		log.info("processStoreEmpData start ...");
		
		this.executeSqlUpdate("delete from rpt_store_emp where bill_date='" + date + "'");
		Map<Object, Object> empMap = initEmpMap();
		List<Object[]> list = this.executeSqlQuery("select esi.dist_id,esi.client_id,esi.store_code,es.emp_id from biz_emp_store es,biz_emp_store_item esi where es.emp_store_id=esi.emp_store_id");
		for (Object[] obj : list) {
			String isReal = "1";
			while (obj[3] != null) {
				this.executeSqlUpdate("insert into rpt_store_emp (dist_id,client_id,store_code,bill_date,emp_id,is_real) values(" + obj[0] + "," + obj[1] + ",'" + obj[2] + "','" + date + "'," + obj[3] + "," + isReal + ")");
				obj[3] = empMap.get(obj[3]);
				isReal = "0";
			}
		}
		
		log.info("processStoreEmpData end");
	}

	/**
	 * 人员组织关系日结
	 */
	public void processEmpOrgData() {
		log.info("processEmpOrgData start ...");
		this.executeSqlUpdate("delete from rpt_emp_org");
		Map<Object, Object> orgMap = initOrgMap();
		List<Object[]> distList = this.executeSqlQuery("select emp_id,org_id from base_employee");
		for (Object[] obj : distList) {
			while (obj[1] != null) {
				this.executeSqlUpdate("insert into rpt_emp_org (emp_id,org_id) values(" + obj[0] + "," + obj[1] + ")");
				obj[1] = orgMap.get(obj[1]);
			}
		}
		log.info("processEmpOrgData end");
	}
	
	public void processEmpOrgLogData(String date) {
		log.info("processEmpOrgLogData start ...");
		//如果是第一次插入数据，把date修改成1970-01-01
		List<Object[]> list = this.executeSqlQuery("SELECT count(*) FROM RPT_EMP_ORG_LOG");
		if (Integer.parseInt(list.get(0)[0]+"") == 0) {
			date = "1970-01-01";
		}
		//1、把当前的rpt_emp_org_log表中查询这些ID对应的记录的end_date改成date的前一天
		String sql = "SELECT DISTINCT org_id FROM RPT_EMP_ORG WHERE EMP_ID IN (" +
				"SELECT emp_id FROM RPT_EMP_ORG WHERE ORG_ID IN (" +
				"SELECT org_id FROM BASE_ORG_LOG WHERE start_date >='"+date+"' AND START_DATE < end_date))";
		this.executeSqlUpdate("UPDATE RPT_EMP_ORG_LOG SET END_DATE='"+DateUtils.addDays(date, -1)+"' WHERE ORG_ID IN ("+sql+")");
		
		//2、从当前的rpt_emp_org表中查询这些ID对应的记录的start_date改成date，end_date改成2099-12-31
		this.executeSqlUpdate("INSERT INTO RPT_EMP_ORG_LOG (emp_id,org_id,start_date,end_date) " +
		"SELECT emp_id,org_id,'"+date+"','2099-12-31' FROM RPT_EMP_ORG where org_id in ("+sql+")");
		
		//重新执行一下.processEmpOrgData();保证下面第三步插入当前的组织人员信息
		this.processEmpOrgData();
		
		//3、把在rpt_emp_org表中  不在当前的rpt_emp_org_log表中，的记录插入到rpt_emp_org_log表中
		this.executeSqlUpdate("INSERT INTO RPT_EMP_ORG_LOG (emp_id,org_id,start_date,end_date) " +
				"SELECT emp_id,org_id,'"+date+"','2099-12-31' FROM RPT_EMP_ORG rdo " +
				"WHERE NOT EXISTS (SELECT 'X' FROM RPT_EMP_ORG_LOG lg WHERE lg.EMP_ID=rdo.EMP_ID AND lg.ORG_ID=rdo.ORG_ID)");
		log.info("processEmpOrgLogData end");
	}
	

	/**
	 * 经销商组织关系日结
	 */
	public void processDistOrgData() {
		log.info("processDistOrgData start ...");
		this.executeSqlUpdate("delete from rpt_dist_org");
		Map<Object, Object> orgMap = initOrgMap();
//		List<Object[]> distList = this.executeSqlQuery("select dist_id,org_id from dms_distributor WHERE MAPPING_END='1' AND STOCK_DATE IS NOT NULL AND STOCK_DATE < getdate() and state='1'");
		List<Object[]> distList = this.executeSqlQuery("select dist_id,org_id from mdm_distributor");
		for (Object[] obj : distList) {
			while (obj[1] != null) {
				this.executeSqlUpdate("insert into rpt_dist_org (dist_id,org_id) values(" + obj[0] + "," + obj[1] + ")");
				obj[1] = orgMap.get(obj[1]);
			}
		}
		log.info("processDistOrgData end");
	}
	
	public void processDistOrgLogData(String date) {
//		date = "2010-09-30";
		log.info("processDistOrgLogData start ...");
		
		//如果是第一次插入数据，把date修改成1970-01-01
		List<Object[]> list = this.executeSqlQuery("SELECT count(*) FROM RPT_DIST_ORG_LOG");
		if (Integer.parseInt(list.get(0)[0]+"") == 0) {
			date = "1970-01-01";
		}
		
		//查询date下组织结构有变化的组织ID
//		List<Object[]> list = this.executeSqlQuery("SELECT count(*) FROM BASE_ORG_LOG WHERE start_date >='"+date+"' AND START_DATE < end_date");
//		if (Integer.parseInt(list.get(0)[0]+"") > 0) {
			//1、把当前的rpt_dist_org_log表中查询这些ID对应的记录的end_date改成date的前一天
			String sql = "SELECT DISTINCT org_id FROM RPT_DIST_ORG WHERE DIST_ID IN (" +
					"SELECT dist_id FROM RPT_DIST_ORG WHERE ORG_ID IN (" +
					"SELECT org_id FROM BASE_ORG_LOG WHERE start_date >='"+date+"' AND START_DATE < end_date))";
			this.executeSqlUpdate("UPDATE RPT_DIST_ORG_LOG SET END_DATE='"+DateUtils.addDays(date, -1)+"' WHERE ORG_ID IN ("+sql+")");
			
			//2、从当前的rpt_dist_org表中查询这些ID对应的记录的start_date改成date，end_date改成2099-12-31
			this.executeSqlUpdate("INSERT INTO RPT_DIST_ORG_LOG (dist_id,org_id,start_date,end_date) " +
			"SELECT dist_id,org_id,'"+date+"','2099-12-31' FROM RPT_DIST_ORG where org_id in ("+sql+")");
			
			//重新执行一下.processDistOrgData();保证下面第三步插入当前的组织经销商信息
			this.processDistOrgData();
			
			//3、把在rpt_dist_org表中  不在当前的rpt_dist_org_log表中，的记录插入到rpt_dist_org_log表中
			this.executeSqlUpdate("INSERT INTO RPT_DIST_ORG_LOG (dist_id,org_id,start_date,end_date) " +
					"SELECT dist_id,org_id,'"+date+"','2099-12-31' FROM RPT_DIST_ORG rdo " +
					"WHERE NOT EXISTS (SELECT 'X' FROM RPT_DIST_ORG_LOG lg WHERE lg.DIST_ID=rdo.DIST_ID AND lg.ORG_ID=rdo.ORG_ID)");
//		}
		log.info("processDistOrgLogData end");
	}
	
	/**
	 * 门店组织关系日结
	 */
	public void processStoreOrgData() {
		log.info("processStoreOrgData start ...");
		this.executeSqlUpdate("delete from rpt_store_org");
		Map<Object, Object> storeMap = initStoreMap();
		List<Object[]> storeList = this.executeSqlQuery("select store_id,org_id from mdm_store");
		for (Object[] obj : storeList) {
			while (obj[1] != null) {
				this.executeSqlUpdate("insert into rpt_store_org (store_id,org_id) values(" + obj[0] + "," + obj[1] + ")");
				obj[1] = storeMap.get(obj[1]);
			}
		}
		log.info("processStoreOrgData end");
	}
	
	public void processStoreOrgLogData(String date) {
		log.info("processStoreOrgLogData start ...");
		//如果是第一次插入数据，把date修改成1970-01-01
		List<Object[]> list = this.executeSqlQuery("SELECT count(*) FROM RPT_STORE_ORG_LOG");
		if (Integer.parseInt(list.get(0)[0]+"") == 0) {
			date = "1970-01-01";
		}
		//1、把当前的rpt_store_org_log表中查询这些ID对应的记录的end_date改成date的前一天
		String sql = "SELECT DISTINCT org_id FROM RPT_STORE_ORG WHERE STORE_ID IN (" +
				"SELECT store_id FROM RPT_STORE_ORG WHERE ORG_ID IN (" +
				"SELECT org_id FROM BASE_ORG_LOG WHERE start_date >='"+date+"' AND START_DATE < end_date))";
		this.executeSqlUpdate("UPDATE RPT_STORE_ORG_LOG SET END_DATE='"+DateUtils.addDays(date, -1)+"' WHERE ORG_ID IN ("+sql+")");
		
		//2、从当前的rpt_store_org表中查询这些ID对应的记录的start_date改成date，end_date改成2099-12-31
		this.executeSqlUpdate("INSERT INTO RPT_STORE_ORG_LOG (store_id,org_id,start_date,end_date) " +
		"SELECT store_id,org_id,'"+date+"','2099-12-31' FROM RPT_STORE_ORG where org_id in ("+sql+")");
		
		//重新执行一下.processStoreOrgData();保证下面第三步插入当前的组织门店信息
		this.processStoreOrgData();
		
		//3、把在rpt_store_org表中  不在当前的rpt_store_org_log表中，的记录插入到rpt_store_org_log表中
		this.executeSqlUpdate("INSERT INTO RPT_STORE_ORG_LOG (store_id,org_id,start_date,end_date) " +
				"SELECT store_id,org_id,'"+date+"','2099-12-31' FROM RPT_STORE_ORG rdo " +
				"WHERE NOT EXISTS (SELECT 'X' FROM RPT_STORE_ORG_LOG lg WHERE lg.STORE_ID=rdo.STORE_ID AND lg.ORG_ID=rdo.ORG_ID)");
		log.info("processStoreOrgLogData end");
	}
	
	private Map<Object, Object> initOrgMap() {
		List<Object[]> orgList = this.executeSqlQuery("select org_id,parent_org_id from base_org");
		Map<Object, Object> orgMap = new HashMap<Object, Object>();
		for (Object[] obj : orgList) {
			orgMap.put(obj[0], obj[1]);
		}
		return orgMap;
	}
	
	private Map<Object, Object> initEmpMap() {
		List<Object[]> orgList = this.executeSqlQuery("select emp_id,parent_emp_id from base_employee");
		Map<Object, Object> orgMap = new HashMap<Object, Object>();
		for (Object[] obj : orgList) {
			orgMap.put(obj[0], obj[1]);
		}
		return orgMap;
	}
	
	private Map<Object, Object> initStoreMap() {
		List<Object[]> orgList = this.executeSqlQuery("select store_id,parent_store_id from mdm_store");
		Map<Object, Object> storeMap = new HashMap<Object, Object>();
		for (Object[] obj : orgList) {
			storeMap.put(obj[0], obj[1]);
		}
		return storeMap;
	}

}
