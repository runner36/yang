package com.winchannel.base.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

import com.winchannel.base.bean.EmpOrgCheck;
import com.winchannel.base.model.BaseEmployee;
import com.winchannel.base.model.BaseEmployeeLog;
import com.winchannel.base.model.BaseOrg;
import com.winchannel.core.dao.HibernateEntityDao;
import com.winchannel.core.exception.BusinessException;
import com.winchannel.core.utils.Page;
import com.winchannel.core.utils.StringUtils;

public class BaseEmployeeManager extends HibernateEntityDao<BaseEmployee> {

	public static final String BLANK_EMP = "空缺";

	public void save(Object object) {
		if (!this.isUnique(object, "empCode")) {
			//throw new BusinessException("编码重复，保存失败");
			throw new BusinessException("base","common.codeDuplication");
		}
		if (!this.isUnique(object, "extCode")) {
			//throw new BusinessException("编码重复，保存失败");
			throw new BusinessException("base","common.codeDuplication");
		}
		this.makeSubCode(object);
		super.save(object);
		this.makeParentInfo(object, "empName");
	}

	public void save(BaseEmployee baseEmp, String oper, String targetEmpId) {
		BaseEmployee operEmp = this.get(new Long(targetEmpId));

		// 单人汇报
		if ("1".equals(oper)) {
			BaseEmployee blankEmp = new BaseEmployee();
			blankEmp.setEmpName(BLANK_EMP);
			blankEmp.setBaseEmployee(baseEmp.getBaseEmployee());
			blankEmp.setSubCode(baseEmp.getSubCode());
			blankEmp.setLevelCode(baseEmp.getLevelCode());
			blankEmp.setSort(baseEmp.getSort());
			blankEmp.setState(baseEmp.getState());
			this.save(blankEmp);

			Iterator<BaseEmployee> it = this.getSubEmployees(baseEmp.getEmpId()).iterator();
			while (it.hasNext()) {
				BaseEmployee subEmp = it.next();
				subEmp.setBaseEmployee(blankEmp);
				this.save(subEmp);
			}

			baseEmp.setBaseEmployee(operEmp);
			this.save(baseEmp);
		}
		// 批量汇报
		else if ("2".equals(oper)) {
			if (operEmp.getSubCode().indexOf(baseEmp.getSubCode()) == 0) {
				throw new BusinessException("base","common.canNotReportToLower");
			}

			if (this.getSubEmployees(baseEmp.getEmpId()).size() == 0) {
				throw new BusinessException("base","common.canNotChooseThisOpt");
			}
			baseEmp.setBaseEmployee(operEmp);
			this.save(baseEmp);
		}
		// 单人填补
		else if ("3".equals(oper)) {
			if (!BLANK_EMP.equals(operEmp.getEmpName())) {
				throw new BusinessException("保存失败，只能填补" + BLANK_EMP);
			}

			BaseEmployee baseParentEmp = baseEmp.getBaseEmployee();
			String baseSubCode = baseEmp.getSubCode();
			Long baseLevelCode = baseEmp.getLevelCode();
			Long baseSort = baseEmp.getSort();
			String baseState = baseEmp.getState();
			Iterator<BaseEmployee> baseChild = this.getSubEmployees(baseEmp.getEmpId()).iterator();

			BaseEmployee operParentEmp = operEmp.getBaseEmployee();
			String operSubCode = operEmp.getSubCode();
			Long operLevelCode = operEmp.getLevelCode();
			Long operSort = operEmp.getSort();
			String operState = operEmp.getState();
			Iterator<BaseEmployee> operChild = this.getSubEmployees(operEmp.getEmpId()).iterator();

			baseEmp.setBaseEmployee(operParentEmp.getEmpId().longValue() == baseEmp.getEmpId().longValue() ? operEmp : operParentEmp);
			baseEmp.setSubCode(operSubCode);
			baseEmp.setLevelCode(operLevelCode);
			baseEmp.setSort(operSort);
			baseEmp.setState(operState);

			operEmp.setBaseEmployee(baseParentEmp.getEmpId().longValue() == operEmp.getEmpId().longValue() ? baseEmp : baseParentEmp);
			operEmp.setSubCode(baseSubCode);
			operEmp.setLevelCode(baseLevelCode);
			operEmp.setSort(baseSort);
			operEmp.setState(baseState);

			super.save(baseEmp);
			super.save(operEmp);

			while (operChild.hasNext()) {
				BaseEmployee subEmp = operChild.next();
				if (subEmp.getEmpId().longValue() == baseEmp.getEmpId().longValue()) {
					continue;
				}
				subEmp.setBaseEmployee(baseEmp);
				super.save(subEmp);
			}

			while (baseChild.hasNext()) {
				BaseEmployee subEmp = baseChild.next();
				if (subEmp.getEmpId().longValue() == operEmp.getEmpId().longValue()) {
					continue;
				}
				subEmp.setBaseEmployee(operEmp);
				super.save(subEmp);
			}

			this.save(baseEmp);
			this.save(operEmp);

		}

	}

	public List<BaseEmployee> getEmployees(String parentSubCode, String state) {
		StringBuffer queryString = new StringBuffer("from BaseEmployee where 1=1");
		if (StringUtils.isNotBlank(parentSubCode)) {
			queryString.append(" and subCode like '" + parentSubCode + "%'");
		}
		if (StringUtils.isNotBlank(state)) {
			queryString.append(" and state=" + state);
		}
		queryString.append(" order by levelCode,sort");
		return this.findEntity(queryString.toString());
	}

	public List<BaseEmployee> query(Page page) {
		/*
		 * StringBuffer queryString = new
		 * StringBuffer("from BaseEmployee where 1=1"); if
		 * (StringUtils.isNotBlank(page.get("state"))) {
		 * queryString.append(" and state=" + page.get("state")); } if
		 * (StringUtils.isNotBlank(page.get("empTypeId"))) {queryString.append(
		 * " and (baseDictItemByEmpTypeId is null or baseDictItemByEmpTypeId.dictItemId="
		 * + page.get("empTypeId") + ")"); }
		 * queryString.append(this.makeQueryConditions(page, null));
		 * queryString.append(" order by levelCode,sort"); return
		 * this.findEntity(queryString.toString());
		 */

		StringBuffer sb = new StringBuffer("from " + entityClass.getSimpleName() + " where 1=1");
		sb.append(makeQueryConditions(entityClass, page, "", page.get(Page.AUTH_PREFIX)));

		if (StringUtils.isNotBlank(page.get("empTypeId"))) {
			sb.append(" and (baseDictItemByEmpTypeId is null or baseDictItemByEmpTypeId.dictItemId=" + page.get("empTypeId") + ")");
		}

		sb.append(makeQuerySort(entityClass, page, ""));

		List<BaseEmployee> list = findPage(entityClass, page, sb.toString());

		Map<String, EmpOrgCheck> map = new HashMap<String, EmpOrgCheck>();
		List<EmpOrgCheck> checkList = this.getCheckOrgList(page);
		for (EmpOrgCheck doc : checkList) {
			map.put(doc.getEmpId(), doc);
		}
		for (BaseEmployee emp : list) {
			emp.setEmpOrgCheck(map.get(emp.getEmpId().toString()));
		}
		return list;

	}

	public List<BaseEmployee> getSubEmployees(Long parentId) {
		return this.findEntity("from BaseEmployee where baseEmployee.empId=?", parentId);
	}

	public String getEmployeesByIds(String empIds) {
		StringBuffer empNames = new StringBuffer();
		if (StringUtils.isNotBlank(empIds)) {
			Iterator it = this.find("select empName from BaseEmployee where empId in (" + empIds + ")").iterator();
			while (it.hasNext()) {
				empNames.append(it.next()).append(",");
			}
		}
		return empNames.toString();
	}

	public List<EmpOrgCheck> getCheckOrgList(Page page) {
		StringBuffer sql = new StringBuffer(700);
		sql.append("SELECT CAST(emp.EMP_ID AS VARCHAR)+'_'+CAST(isnull(orgInit.ORG_ID,0) AS VARCHAR) AS flag,").append(
				"emp.EMP_ID,emp.EMP_NAME,orgInit.ORG_ID AS initOrgId,orgInit.ORG_NAME AS initOrgName,").append(
				"bog.ORG_ID AS sugOrgId,org.ORG_NAME AS sugOrgName,bog.GEO_ID,item.ITEM_NAME AS geoFullName,emp.EMP_CODE, ").append(
				"lg.org_name,lg.START_DATE,lg.END_DATE,lg.UPDATED ").append(
				"FROM BASE_EMPLOYEE emp LEFT JOIN BASE_ORG_GEO bog ON emp.GEO_ID=bog.GEO_ID ").append(
				"INNER JOIN BASE_ORG validOrg ON validOrg.ORG_ID=bog.ORG_ID  AND validOrg.STATE='1' ").append(
				"LEFT JOIN BASE_GEO_MLIST item ON item.DICT_ITEM_ID=bog.GEO_ID ").append(
				"LEFT JOIN BASE_ORG_MLIST org ON org.ORG_ID=bog.ORG_ID ").append(
				"LEFT JOIN BASE_ORG_MLIST orgInit ON orgInit.ORG_ID=emp.org_id ").append(
				"LEFT JOIN (SELECT a.EMP_ID,CONVERT(varchar(10),a.START_DATE,120) AS START_DATE,CONVERT(varchar(10),a.END_DATE,120) AS END_DATE,CONVERT(varchar(10),a.UPDATED,120) AS UPDATED,s.org_name ").append(
				"FROM BASE_EMPLOYEE_LOG a INNER JOIN BASE_ORG_MLIST s ON a.ORG_ID=s.org_id ").append(
				"WHERE ID=(SELECT max(id) FROM BASE_EMPLOYEE_LOG b WHERE a.EMP_ID=b.EMP_ID) ").append(
				") lg ON lg.EMP_ID=emp.EMP_ID ").append(
				"where 1=1 ");
		if (com.winchannel.core.utils.StringUtils.isNotBlank(page.get("$lk_empCode"))) {
			sql.append(" AND emp.EMP_CODE LIKE '%" + page.get("$lk_empCode") + "%'");
		}
		if (com.winchannel.core.utils.StringUtils.isNotBlank(page.get("$lk_empName"))) {
			sql.append(" AND emp.EMP_NAME LIKE '%" + page.get("$lk_empName") + "%'");
		}
		List<Object[]> objList = this.executeSqlQuery(sql.toString());
		List<EmpOrgCheck> list = new ArrayList<EmpOrgCheck>(objList.size());
		for (Object[] obj : objList) {
			EmpOrgCheck doc = new EmpOrgCheck();
			doc.setFlag(obj[0] + "");
			doc.setEmpId(obj[1] + "");
			doc.setEmpName(obj[2] + "");
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
			doc.setEmpCode(obj[9] + "");
			
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
		// 将存在建议组织的经销商记录合并
		return this.dealCheckOrgList(list);
	}

	private List<EmpOrgCheck> dealCheckOrgList(List<EmpOrgCheck> list) {
		List<EmpOrgCheck> result = new ArrayList<EmpOrgCheck>();
		Map<String, EmpOrgCheck> map = new HashMap<String, EmpOrgCheck>();
		for (EmpOrgCheck doc : list) {
			if (map.get(doc.getFlag()) == null) {
				EmpOrgCheck org = new EmpOrgCheck();
				org.setSugOrgId(doc.getSugOrgId());
				org.setSugOrgName(doc.getSugOrgName());
				doc.getSugOrgList().add(org);
				map.put(doc.getFlag(), doc);
				result.add(doc);
			} else {
				EmpOrgCheck temp = map.get(doc.getFlag());
				EmpOrgCheck org = new EmpOrgCheck();
				org.setSugOrgId(doc.getSugOrgId());
				org.setSugOrgName(doc.getSugOrgName());
				temp.getSugOrgList().add(org);
			}
		}
		return result;
	}

	public void saveCheckOrg(List<String[]> params) {
//		Map<String, BaseEmployee> distMap = new HashMap<String, BaseEmployee>();
//		List<BaseEmployee> empList = this.getAll();
//		for (BaseEmployee emp : empList) {
//			distMap.put(emp.getEmpId().toString(), emp);
//		}
		try {
			for (String[] arr : params) {
//				BaseEmployee emp = distMap.get(arr[0]);
				BaseEmployee emp = this.get(Long.parseLong(arr[0]));
//				if (emp.getBaseOrg() == null || !arr[1].equals(emp.getBaseOrg().getOrgId().toString())) {// 经销商所属组织发生改变
					BaseEmployeeLog empLog = new BaseEmployeeLog();
					PropertyUtils.copyProperties(empLog, emp);
					BaseOrg org = new BaseOrg();
					org.setOrgId(Long.parseLong(arr[1]));
					empLog.setBaseOrg(org);
					dealEmpLog(empLog, arr[2]);
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

	private void dealEmpLog(BaseEmployeeLog empLog, String startDate) {
		// String startDate = com.winchannel.core.utils.DateUtils.format(new
		// Date(), "yyyy-MM-dd");
		Date yesterday = com.winchannel.core.utils.DateUtils.getYesterdayByDate(startDate, "yyyy-MM-dd");
		// 先删除没有生效的记录
		this.deleteAll("from BaseEmployeeLog where empId=" + empLog.getEmpId() + " and startDate>'" + startDate + "'");
		// 把已经生效的最后一条记录的结束时间设置为today的前一天
		List<BaseEmployeeLog> list = this.findEntity(BaseEmployeeLog.class,
				"from BaseEmployeeLog where id=(select max(id) from BaseEmployeeLog where empId=" + empLog.getEmpId() + ")");
		BaseEmployeeLog lastEmpLog = list.get(0);
		lastEmpLog.setEndDate(yesterday);
		super.save(lastEmpLog);
		empLog.setStartDate(com.winchannel.core.utils.DateUtils.parse(startDate, "yyyy-MM-dd"));
		// 把本次保存的记录终止时间保存2099-12-31
		empLog.setEndDate(com.winchannel.core.utils.DateUtils.getEndDate());
		empLog.setUpdated(new Date());
		super.save(empLog);
	}

	public void saveBaseEmployeeLog(BaseEmployee emp) {
		BaseEmployeeLog empLog = new BaseEmployeeLog();
		Date today = new Date();
		try {
			PropertyUtils.copyProperties(empLog, emp);
			empLog.setCreated(today);
			empLog.setStartDate(today);
			empLog.setEndDate(com.winchannel.core.utils.DateUtils.getEndDate());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		super.save(empLog);
	}
	/**
	 * 得到人员
	 * 
	 * @param baseEmployee
	 * @param  code
	 * @return
	 */
	public BaseEmployee getBaseEmployeeByCode(String code) {
		return findUniqueEntity("from BaseEmployee where  empCode=?",code);
	}

}
