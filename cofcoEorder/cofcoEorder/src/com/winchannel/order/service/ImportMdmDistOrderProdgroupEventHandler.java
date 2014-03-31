package com.winchannel.order.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.winchannel.base.model.BaseDictItem;
import com.winchannel.base.model.BaseEmployee;
import com.winchannel.base.model.BaseOrg;
import com.winchannel.core.exception.BusinessException;
import com.winchannel.core.importer.ImpEventHandler;
import com.winchannel.core.utils.StringUtils;
import com.winchannel.base.utils.DateUtility;
import com.winchannel.mdm.distributor.model.MdmDistributor;
import com.winchannel.order.bean.DistOrderempProdgroup;
import com.winchannel.order.model.MdmDistEmpProdgroup;

/**
 * @author shijingru
 * @订单员_组织_产品组关系维护导入扩展类
 */
public class ImportMdmDistOrderProdgroupEventHandler extends ImpEventHandler{

	private MdmDistEmpProdgroupManager mdmDistEmpProdgroupManager;
	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> void endRow(Map<String, String> row, T bean) {
	}
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
	}
	@Override
	public void startRow(Map<String, String> row) {
		String empCode=row.get("1");
		String itemCode=row.get("2");
		String orgCode=row.get("3");
		String effectiveTime=row.get("4");
		String expiryTime=row.get("5");
		Date now = DateUtility.strToDate(DateUtility.getCurrentDate());
		String effect[] = effectiveTime.split("/");
		Date eff = DateUtility.strToDate(effect[2]+"-"+effect[1]+"-"+effect[0]);
		if(effectiveTime!=null){
			if(now.getTime()-eff.getTime()>0){
				throw new BusinessException("生效日期必须大于等于当前日期!  生效日期"+effectiveTime);
			}else{
				row.put("4", DateUtility.dateToStr(eff));
			}
		}else{
			row.put("4", null);
		}
		if(expiryTime!=null){
			String expiry[] = expiryTime.split("/");
			Date exp = DateUtility.strToDate(expiry[2]+"-"+expiry[1]+"-"+expiry[0]);
//			Date exp = DateUtility.strToDate(expiryTime);
			if(exp.getTime()-eff.getTime()<=0){
				throw new BusinessException("失效时间必须大于生效时间! 生效日期"+effectiveTime+"失效日期"+expiryTime);
			}else{
				row.put("5", DateUtility.dateToStr(exp));
				BaseEmployee objBaseEmployee = getBaseEmployeeByCode(empCode);
				BaseDictItem objBaseDictItem = getBaseDictItemId(itemCode);
				BaseOrg objBaseOrg = getBaseOrgByCode(orgCode);
				List isSames = getIsSame(objBaseOrg.getOrgId(), objBaseDictItem.getDictItemId(), objBaseEmployee.getEmpId(), DateUtility.dateToStr(eff), DateUtility.dateToStr(exp));
				if(isSames.size()>0){
					throw new BusinessException("已经存在相同的订单员，组织，物料组的关系维护，不能重复添加！");
				}
			}
		}else{
			row.put("5", null);
		}
		
	}
	
	// 通过CODE得到订单岗相关信息
	public BaseEmployee getBaseEmployeeByCode(String code) {
		return this.getPersister().findUniqueEntity(BaseEmployee.class, "from BaseEmployee where  empCode=?",code);
	}
	
	// 通过CODE得到物料组相关信息
	public BaseDictItem getBaseDictItemId(String code) {
		return this.getPersister().findUniqueEntity(BaseDictItem.class, "from BaseDictItem where baseDict.dictId = 'prodSTRU' and levelCode = 1 and itemCode = ?", code);
	}
	
	// 通过CODE得到组织相关信息
	public BaseOrg getBaseOrgByCode(String orgCode) {
		return this.getPersister().findUniqueEntity(BaseOrg.class, "from BaseOrg where orgCode=?", orgCode);
	}
	
	public List getIsSame(Long orgId, Long prodBrandId, Long empId, String effectiveTime, String expiryTime){
		List<DistOrderempProdgroup> distOrderempProdgroups= new ArrayList<DistOrderempProdgroup>();
		if(expiryTime==null||expiryTime.equals("")){
			expiryTime="2999-12-30";
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT id as id FROM MDM_DIST_ORDEREMP_PRODGROUP WHERE ORG_ID=" + orgId + " AND DICT_ITEM_ID = " + prodBrandId + " AND EMP_ID =" +empId
					+" and ((EXPIRY_TIME IS Null and EFFECTIVE_TIME <'"+expiryTime+"') or"
					+" (EXPIRY_TIME IS not Null and not('"+effectiveTime+"'>=EXPIRY_TIME or '"+expiryTime+"'<=+EFFECTIVE_TIME))"
					+")");
		distOrderempProdgroups=this.getPersister().executeSqlQuery(DistOrderempProdgroup.class, sql.toString());
		
		return distOrderempProdgroups;
	}
	
	
	
	public MdmDistEmpProdgroupManager getMdmDistEmpProdgroupManager() {
		return mdmDistEmpProdgroupManager;
	}
	public void setMdmDistEmpProdgroupManager(
			MdmDistEmpProdgroupManager mdmDistEmpProdgroupManager) {
		this.mdmDistEmpProdgroupManager = mdmDistEmpProdgroupManager;
	}

	
}
