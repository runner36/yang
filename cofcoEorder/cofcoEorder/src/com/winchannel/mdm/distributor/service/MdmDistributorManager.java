package com.winchannel.mdm.distributor.service;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.beanutils.PropertyUtils;

import com.winchannel.base.conf.BaseConfigurator;
import com.winchannel.base.model.BaseDictItem;
import com.winchannel.base.model.BaseEmployee;
import com.winchannel.base.model.BaseOrg;
import com.winchannel.base.model.BaseRole;
import com.winchannel.base.model.BaseUser;
import com.winchannel.base.model.BaseUserRole;
import com.winchannel.base.service.BaseDictManager;
import com.winchannel.base.service.BaseOrgManager;
import com.winchannel.base.service.BaseUserManager;
import com.winchannel.core.dao.HibernateEntityDao;
import com.winchannel.core.exception.BusinessException;
import com.winchannel.core.utils.Page;
import com.winchannel.mdm.distributor.bean.DistOrgCheck;
import com.winchannel.mdm.distributor.model.MdmDistributor;
import com.winchannel.mdm.distributor.model.MdmDistributorLog;
import com.winchannel.mdm.util.date.DateUtils;
import com.winchannel.mdm.util.string.StringUtils;

public class MdmDistributorManager extends HibernateEntityDao<MdmDistributor> {

	private BaseDictManager baseDictManager;
	private BaseOrgManager baseOrgManager;
	private BaseUserManager baseUserManager;

	public BaseDictManager getBaseDictManager() {
		return baseDictManager;
	}

	public void setBaseDictManager(BaseDictManager baseDictManager) {
		this.baseDictManager = baseDictManager;
	}

	public BaseUserManager getBaseUserManager() {
		return baseUserManager;
	}

	public void setBaseUserManager(BaseUserManager baseUserManager) {
		this.baseUserManager = baseUserManager;
	}

	public BaseOrgManager getBaseOrgManager() {
		return baseOrgManager;
	}

	public void setBaseOrgManager(BaseOrgManager baseOrgManager) {
		this.baseOrgManager = baseOrgManager;
	}
	
	public void updateRemarkByCode(String code, String remark){
		MdmDistributor dist = this.getMdmDistributorByProdCode(code);
		dist.setRemark(remark);
		this.save(dist);
	}

	@Override
	public List<MdmDistributor> query(Page page) {
		List<MdmDistributor> list = super.query(page);

		Map<String, DistOrgCheck> map = new HashMap<String, DistOrgCheck>();
		List<DistOrgCheck> checkList = this.getCheckOrgList(page);
		for (DistOrgCheck doc : checkList) {
			map.put(doc.getDistId(), doc);
		}
		for (MdmDistributor dist : list) {
			dist.setDistOrgCheck(map.get(dist.getDistId().toString()));
		}
		return list;
	}

	public void save(Object object) {
		if (!this.isUnique(object, "distCode")) {
			throw new BusinessException("base","common.codeDuplication");//编码重复，保存失败
		}
		makeSubCode(object);
		// 增加保存MdmDistributorLog的控制
		MdmDistributor dist = (MdmDistributor) object;
		boolean saveLog = false;
		if (dist.getDistId() == null) {
			saveLog = true;
		}
		super.save(object);
		
		if (null==dist.getBaseEmployee()){
			BaseEmployee baseEmployee=(BaseEmployee)super.findUniqueEntity(BaseEmployee.class, "from BaseEmployee where empCode=?", dist.getDistCode());
			//添加人员
			if(null==baseEmployee){
				baseEmployee=new BaseEmployee();
			}
			baseEmployee.setEmpName(dist.getDistName());
			baseEmployee.setEmpCode(dist.getDistCode());
			baseEmployee.setState(dist.getState());
			baseEmployee.setIsEmployee("1");		
			//所属组织			
			baseEmployee.setBaseOrg(dist.getBaseOrg());					
			baseEmployee.setBaseDictItem(null);		
			baseEmployee.setBaseDictItemByEmpTypeId(null);		
			//保存地理区域			
			baseEmployee.setBaseDictItemGeo(dist.getBaseDictItem());			
			//职务
			BaseDictItem baseDictItem=null;
			baseDictItem=(BaseDictItem)this.findUniqueEntity(BaseDictItem.class, "from BaseDictItem a where a.baseDict.dictId='duty' and a.itemCode=?", "DS");
			baseEmployee.setBaseDictItem(baseDictItem);		
			
			baseEmployee.setCreatedBy(dist.getCreatedBy());
			baseEmployee.setCreated(new Date());			
			baseEmployee.setUpdatedBy(dist.getUpdatedBy());
			baseEmployee.setUpdated(new Date());
			super.save(baseEmployee);
			dist.setBaseEmployee(baseEmployee);
			//end 添加人员
			//添加用户
			BaseUser baseUser=new BaseUser();
			baseUser.setBaseEmployee(baseEmployee);
			baseUser.setUserAccount(dist.getDistCode());
			baseUser.setUserPassword(dist.getDistCode());
			baseUser.setUserType("1");
			baseUser.setState("1");		
			baseUser.setBaseResource(null);			
			
			baseUser.setCreatedBy(dist.getCreatedBy());
			baseUser.setCreated(new Date());			
			baseUser.setUpdatedBy(dist.getUpdatedBy());
			baseUser.setUpdated(new Date());
			baseUserManager.save(baseUser);
			//为用户指定角色
			BaseUserRole baseUserRole=new BaseUserRole();
			BaseRole baseRole=this.findUniqueEntity(BaseRole.class,"from BaseRole where roleName=?", BaseConfigurator.getInstance().getCreateDistUserdefaultRoleName());
			baseUserRole.setBaseRole(baseRole);
			baseUserRole.setBaseUser(baseUser);
			super.save(baseUserRole);
			this.save(dist);
		}else{
			//修改人员、用户
			BaseEmployee baseEmployee=dist.getBaseEmployee();
			baseEmployee.setEmpName(dist.getDistName());
			baseEmployee.setEmpCode(dist.getDistCode());
			baseEmployee.setState(dist.getState());
			//所属组织					
			baseEmployee.setBaseOrg(dist.getBaseOrg());									
			//地理区域					
			baseEmployee.setBaseDictItemGeo(dist.getBaseDictItem());		
			baseEmployee.setUpdatedBy(dist.getUpdatedBy());
			baseEmployee.setUpdated(new Date());
			super.save(baseEmployee);
			BaseUser baseUser=this.findUniqueEntity(BaseUser.class, " from BaseUser where baseEmployee.empId=?", baseEmployee.getEmpId());
			if(null!=baseUser){
				baseUser.setState(dist.getState());
				baseUser.setUpdatedBy(dist.getUpdatedBy());
				baseUser.setUpdated(new Date());
				this.baseUserManager.save(baseUser);
			}else{
				//添加用户
				baseUser=new BaseUser();
				baseUser.setBaseEmployee(baseEmployee);
				baseUser.setUserAccount(dist.getDistCode());
				baseUser.setUserPassword(dist.getDistCode());
				baseUser.setUserType("1");
				baseUser.setState(dist.getState());		
				baseUser.setBaseResource(null);						
				baseUser.setCreatedBy(dist.getCreatedBy());
				baseUser.setCreated(new Date());			
				baseUser.setUpdatedBy(dist.getUpdatedBy());
				baseUser.setUpdated(new Date());
				this.baseUserManager.save(baseUser);
				//为用户指定角色
				BaseUserRole baseUserRole=new BaseUserRole();
				BaseRole baseRole=this.findUniqueEntity(BaseRole.class,"from BaseRole where roleName=?", BaseConfigurator.getInstance().getCreateDistUserdefaultRoleName());
				baseUserRole.setBaseRole(baseRole);
				baseUserRole.setBaseUser(baseUser);
				super.save(baseUserRole);
			}
		}

		if (saveLog) {
			this.saveMdmDistributorLog((MdmDistributor) object);
		}
		//DmsClientCache.update((MdmDistributor) object);
	}

	/**
	 * 得到组织表，通过组织code
	 * 
	 * @param baseOrgManager
	 * @param code
	 * @return
	 */
	public BaseOrg getOrgByProdCode(String code) {
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
		return findUniqueEntity("from MdmDistributor where distCode=?", code);
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
	 * 得到排序号
	 * 
	 * @return
	 */
	public long getSort() {
		String sql = "select max(SORT) from  MDM_DISTRIBUTOR ";
		List list = this.executeSqlQuery(sql);
		long sort = 1;
		if (list != null && list.size() > 0) {
			Object[] obj = (Object[]) list.get(0);
			if (obj[0] != null) {
				sort = ((java.math.BigDecimal) obj[0]) == null ? 1 : ((java.math.BigDecimal) obj[0]).longValue() + 1;
			}
		}
		return sort;
	}

	/**
	 * 导入Excel
	 * 
	 * @param f
	 * @param optUser
	 * @param baseDictManager
	 * @param baseOrgManager
	 * @param mdmDistributorManager
	 * @return
	 * @throws Exception
	 */
	public List saveExcel(InputStream f, String optUser, BaseDictManager baseDictManager, BaseOrgManager baseOrgManager) throws Exception {
		ArrayList errMsgList = new ArrayList();
		ArrayList beanList = new ArrayList();
		int sort = (int) getSort(); // 导入记数
		try {
			Workbook wb = Workbook.getWorkbook(f);
			Sheet sheet = wb.getSheet(0);
			int rows = sheet.getRows();
			for (int i = 1; i < rows; i++) {
				MdmDistributor mdmDistributor = new MdmDistributor();
				String errMsgStr = "";
				// 上级经销商
				if (!StringUtils.isNull(sheet.getCell(0, i).getContents().trim())) {
					MdmDistributor obj = getMdmDistributorByProdCode(StringUtils.trim(sheet.getCell(0, i).getContents().trim()));
					if (obj != null && obj.getDistId().longValue() > 0)
						mdmDistributor.setMdmDistributor(obj);
					else
						errMsgStr += "(" + i + ",0)：<font color='#FF0000'>上级经销商没有关联&nbsp;&nbsp;&nbsp;&nbsp;</font>";
				}
				// // 组织
				// if (!StringUtils.isNull(sheet.getCell(1,
				// i).getContents().trim())) {
				// BaseOrg obj =
				// getOrgByProdCode(StringUtils.trim(sheet.getCell(1,
				// i).getContents().trim()));
				// if (obj != null && obj.getOrgId().longValue() > 0)
				// mdmDistributor.setBaseOrg(obj);
				// else
				// errMsgStr += "(" + i +
				// ",1)：<font color='#FF0000'>组织没有关联&nbsp;&nbsp;&nbsp;&nbsp;</font>";
				// } else
				// errMsgStr += "(" + i +
				// ",1)：<font color='#FF0000'>组织不能为空&nbsp;&nbsp;&nbsp;&nbsp;</font>";
				// 地理区域
				if (!StringUtils.isNull(sheet.getCell(2, i).getContents().trim())) {
					BaseDictItem obj = getDistByProdCode(StringUtils.trim(sheet.getCell(2, i).getContents().trim()), "geography");
					if (obj != null && obj.getDictItemId().longValue() > 0)
						mdmDistributor.setBaseDictItem(obj);
					else
						errMsgStr += "(" + i + ",2)：<font color='#FF0000'>地理区域在字典表没有关联&nbsp;&nbsp;&nbsp;&nbsp;</font>";
				}
				// 经销商名称
				if (!StringUtils.isNull(sheet.getCell(3, i).getContents().trim())) {
					mdmDistributor.setDistName(sheet.getCell(3, i).getContents().trim());
				}
				// 经销商编码
				if (!StringUtils.isNull(sheet.getCell(4, i).getContents().trim())) {
					mdmDistributor.setDistCode(sheet.getCell(4, i).getContents().trim());
				} else {
					errMsgStr += "(" + i + ",4)：<font color='#FF0000'>经销商编码不能为空&nbsp;&nbsp;&nbsp;&nbsp;</font>";
				}
				// 经销商地址
				if (!StringUtils.isNull(sheet.getCell(5, i).getContents().trim())) {
					mdmDistributor.setDistAddr(sheet.getCell(5, i).getContents().trim());
				}
				// 经销商电话
				if (!StringUtils.isNull(sheet.getCell(6, i).getContents().trim())) {
					mdmDistributor.setDistTel(sheet.getCell(6, i).getContents().trim());
				}
				// 经销商邮编
				if (!StringUtils.isNull(sheet.getCell(7, i).getContents().trim())) {
					mdmDistributor.setDistPost(sheet.getCell(7, i).getContents().trim());
				}
				// 负责人姓名
				if (!StringUtils.isNull(sheet.getCell(8, i).getContents().trim())) {
					mdmDistributor.setMgrName(sheet.getCell(8, i).getContents().trim());

				}
				// 负责人电话
				if (!StringUtils.isNull(sheet.getCell(9, i).getContents().trim())) {
					mdmDistributor.setMgrTel(sheet.getCell(9, i).getContents().trim());
				}
				// 联系人姓名
				if (!StringUtils.isNull(sheet.getCell(10, i).getContents().trim())) {
					mdmDistributor.setLinkmanName(sheet.getCell(10, i).getContents().trim());
				}
				// 联系人电话
				if (!StringUtils.isNull(sheet.getCell(11, i).getContents().trim())) {
					mdmDistributor.setLinkmanTel(sheet.getCell(11, i).getContents().trim());
				}
				// 安装日期
				if (!StringUtils.isNull(sheet.getCell(12, i).getContents().trim())) {
					if (DateUtils.isValidDate(sheet.getCell(12, i).getContents().trim(), "yyyy-MM-dd"))
						mdmDistributor.setInstDate(DateUtils.strToDate(sheet.getCell(12, i).getContents().trim(), "yyyy-MM-dd"));
					else
						errMsgStr += "(" + i + ",12)：<font color='#FF0000'>安装日期不合法&nbsp;&nbsp;&nbsp;&nbsp;</font>";
				}
				// 核对日期
				if (!StringUtils.isNull(sheet.getCell(13, i).getContents().trim())) {
					if (DateUtils.isValidDate(sheet.getCell(13, i).getContents().trim(), "yyyy-MM-dd"))
						mdmDistributor.setCheckDate(DateUtils.strToDate(sheet.getCell(13, i).getContents().trim(), "yyyy-MM-dd"));
					else
						errMsgStr += "(" + i + ",13)：<font color='#FF0000'>核对日期不合法&nbsp;&nbsp;&nbsp;&nbsp;</font>";
				}
				// 匹配日期
				if (!StringUtils.isNull(sheet.getCell(14, i).getContents().trim())) {
					if (DateUtils.isValidDate(sheet.getCell(14, i).getContents().trim(), "yyyy-MM-dd"))
						mdmDistributor.setMappingDate(DateUtils.strToDate(sheet.getCell(14, i).getContents().trim(), "yyyy-MM-dd"));
					else
						errMsgStr += "(" + i + ",14)：<font color='#FF0000'>匹配日期不合法&nbsp;&nbsp;&nbsp;&nbsp;</font>";
				}
				mdmDistributor.setMemo1(sheet.getCell(15, i).getContents().trim());
				mdmDistributor.setMemo2(sheet.getCell(16, i).getContents().trim());
				mdmDistributor.setMemo3(sheet.getCell(17, i).getContents().trim());
				mdmDistributor.setMemo4(sheet.getCell(18, i).getContents().trim());
				mdmDistributor.setMemo5(sheet.getCell(19, i).getContents().trim());
				mdmDistributor.setMemo6(sheet.getCell(20, i).getContents().trim());
				mdmDistributor.setRemark(sheet.getCell(21, i).getContents().trim());
				// 状态
				mdmDistributor.setState("1");
				// 排序
				mdmDistributor.setSort((long) (sort++));
				// 创建人
				mdmDistributor.setUpdatedBy(optUser);
				mdmDistributor.setUpdated(com.winchannel.core.utils.DateUtils.getDate());
				mdmDistributor.setCreatedBy(optUser);
				mdmDistributor.setCreated(com.winchannel.core.utils.DateUtils.getDate());
				// 如果没有那么导入
				if (!StringUtils.isNull(errMsgStr))
					errMsgList.add(errMsgStr + "<br>");
				else {
					beanList.add(mdmDistributor);
					// this.save(mdmProduct);
				}
			}
			// 如果没有错误，那么再写入数据库
			if (errMsgList == null || errMsgList.size() <= 0) {
				for (int i = 0; i < beanList.size(); i++) {
					MdmDistributor mdmProductExl = (MdmDistributor) beanList.get(i);
					// 按产品编码得到对象，修改这个对象否则添加
					MdmDistributor mdmDistributorDb = getMdmDistributorByProdCode(mdmProductExl.getDistCode());
					if (mdmDistributorDb != null && mdmDistributorDb.getDistId().longValue() > 0) {
						Long prodId = mdmDistributorDb.getDistId();
						PropertyUtils.copyProperties(mdmDistributorDb, mdmProductExl);
						mdmDistributorDb.setDistId(prodId);
						this.save(mdmDistributorDb);
					} else {
						this.save(mdmProductExl);
					}
				}
				errMsgList.add("<font color='#FF0000'>导入成功 <br></font>");
			}
		} catch (Exception e) {
			errMsgList.add("<font color='#FF0000'>打开的模板不对 <br></font>");
		}
		return errMsgList;
	}

	public List<DistOrgCheck> getCheckOrgList(Page page) {
		StringBuffer sql = new StringBuffer(700);
		sql
				.append("SELECT CAST(dist.DIST_ID AS VARCHAR)+'_'+CAST(isnull(orgInit.ORG_ID,0) AS VARCHAR) AS flag,")
				.append("dist.DIST_ID,dist.DIST_NAME,orgInit.ORG_ID AS initOrgId,orgInit.ORG_NAME AS initOrgName,")
				.append("bog.ORG_ID AS sugOrgId,org.ORG_NAME AS sugOrgName,bog.GEO_ID,item.ITEM_NAME AS geoFullName,dist.DIST_CODE,")
				.append("lg.org_name,lg.START_DATE,lg.END_DATE,lg.UPDATED ")
				.append("FROM MDM_DISTRIBUTOR dist LEFT JOIN BASE_ORG_GEO bog ON dist.GEO_ID=bog.GEO_ID ")
				.append("INNER JOIN BASE_ORG validOrg ON validOrg.ORG_ID=bog.ORG_ID  AND validOrg.STATE='1' ")
				.append("LEFT JOIN BASE_GEO_MLIST item ON item.DICT_ITEM_ID=bog.GEO_ID ")
				.append("LEFT JOIN BASE_ORG_MLIST org ON org.ORG_ID=bog.ORG_ID ")
				.append("LEFT JOIN BASE_ORG_MLIST orgInit ON orgInit.ORG_ID=dist.org_id ")
				.append(
						"LEFT JOIN (SELECT a.DIST_ID,CONVERT(varchar(10),a.START_DATE,120) AS START_DATE,CONVERT(varchar(10),a.END_DATE,120) AS END_DATE,CONVERT(varchar(10),a.UPDATED,120) AS UPDATED,s.org_name ")
				.append("FROM MDM_DISTRIBUTOR_LOG a INNER JOIN BASE_ORG_MLIST s ON a.ORG_ID=s.org_id ").append(
						"WHERE ID=(SELECT max(id) FROM MDM_DISTRIBUTOR_LOG b WHERE a.DIST_ID=b.dist_id) ").append(") lg ON lg.DIST_ID=dist.DIST_ID ")
				.append("where 1=1 ");
		if (com.winchannel.core.utils.StringUtils.isNotBlank(page.get("$lk_distCode"))) {
			sql.append(" AND dist.DIST_CODE LIKE '%" + page.get("$lk_distCode") + "%'");
		}
		if (com.winchannel.core.utils.StringUtils.isNotBlank(page.get("$lk_distName"))) {
			sql.append(" AND dist.DIST_NAME LIKE '%" + page.get("$lk_distName") + "%'");
		}
		List<Object[]> objList = this.executeSqlQuery(sql.toString());
		List<DistOrgCheck> list = new ArrayList<DistOrgCheck>(objList.size());
		for (Object[] obj : objList) {
			DistOrgCheck doc = new DistOrgCheck();
			doc.setFlag(obj[0] + "");
			doc.setDistId(obj[1] + "");
			doc.setDistName(obj[2] + "");
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

			doc.setDistCode(obj[9] + "");

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

	private List<DistOrgCheck> dealCheckOrgList(List<DistOrgCheck> list) {
		List<DistOrgCheck> result = new ArrayList<DistOrgCheck>();
		Map<String, DistOrgCheck> map = new HashMap<String, DistOrgCheck>();
		for (DistOrgCheck doc : list) {
			if (map.get(doc.getFlag()) == null) {
				DistOrgCheck org = new DistOrgCheck();
				org.setSugOrgId(doc.getSugOrgId());
				org.setSugOrgName(doc.getSugOrgName());
				doc.getSugOrgList().add(org);
				map.put(doc.getFlag(), doc);
				result.add(doc);
			} else {
				DistOrgCheck temp = map.get(doc.getFlag());
				DistOrgCheck org = new DistOrgCheck();
				org.setSugOrgId(doc.getSugOrgId());
				org.setSugOrgName(doc.getSugOrgName());
				temp.getSugOrgList().add(org);
			}
		}
		return result;
	}

	public void saveCheckOrg(List<String[]> params) {
//		Map<String, MdmDistributor> distMap = new HashMap<String, MdmDistributor>();
//		List<MdmDistributor> distList = this.getAll();
//		for (MdmDistributor dist : distList) {
//			distMap.put(dist.getDistId().toString(), dist);
//		}
		try {
			for (String[] arr : params) {
//				MdmDistributor dist = distMap.get(arr[0]);
				MdmDistributor dist = this.get(Long.parseLong(arr[0]));
				// if (dist.getBaseOrg() == null ||
				// !arr[1].equals(dist.getBaseOrg().getOrgId().toString())) {//
				// 经销商所属组织发生改变
				MdmDistributorLog distLog = new MdmDistributorLog();
				PropertyUtils.copyProperties(distLog, dist);
				BaseOrg org = new BaseOrg();
				org.setOrgId(Long.parseLong(arr[1]));
				distLog.setBaseOrg(org);
				dealDistLog(distLog, arr[2]);
				// }
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

	private void dealDistLog(MdmDistributorLog distLog, String startDate) {
		// String startDate = com.winchannel.core.utils.DateUtils.format(new
		// Date(), "yyyy-MM-dd");
		Date yesterday = com.winchannel.core.utils.DateUtils.getYesterdayByDate(startDate, "yyyy-MM-dd");
		// 先删除没有生效的记录
		this.deleteAll("from MdmDistributorLog where distId=" + distLog.getDistId() + " and startDate>'" + startDate + "'");
		// 把已经生效的最后一条记录的结束时间设置为today的前一天
		List<MdmDistributorLog> list = this.findEntity(MdmDistributorLog.class,
				"from MdmDistributorLog where id=(select max(id) from MdmDistributorLog where distId=" + distLog.getDistId() + ")");
		MdmDistributorLog lastDistLog = list.get(0);
		lastDistLog.setEndDate(yesterday);
		super.save(lastDistLog);
		distLog.setStartDate(com.winchannel.core.utils.DateUtils.parse(startDate, "yyyy-MM-dd"));
		// 把本次保存的记录终止时间保存2099-12-31
		distLog.setEndDate(com.winchannel.core.utils.DateUtils.getEndDate());
		distLog.setUpdated(new Date());
		super.save(distLog);
	}

	public void saveMdmDistributorLog(MdmDistributor dist) {
		MdmDistributorLog distLog = new MdmDistributorLog();
		Date today = new Date();
		try {
			PropertyUtils.copyProperties(distLog, dist);
			distLog.setCreated(today);
			distLog.setStartDate(today);
			distLog.setEndDate(com.winchannel.core.utils.DateUtils.getEndDate());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		super.save(distLog);
	}
	public MdmDistributor getDmsDistributorByOrgSubCode(String subcode){
		return null;
	}
	
	/**
	 * 根据人员Id得到客户信息
	 * 
	 * @param empId
	 * @return
	 */
	public MdmDistributor getMdmDistributorByEmployeeId(Long empId){
	    
	    return this.findUniqueEntity("from MdmDistributor where baseEmployee.empId = ?", empId);
	}
}
