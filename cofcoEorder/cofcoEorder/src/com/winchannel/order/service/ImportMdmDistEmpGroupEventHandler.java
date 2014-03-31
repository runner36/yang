package com.winchannel.order.service;

import java.util.Date;
import java.util.Map;

import com.winchannel.base.model.BaseDictItem;
import com.winchannel.base.model.BaseOrg;
import com.winchannel.core.exception.BusinessException;
import com.winchannel.core.importer.ImpEventHandler;
import com.winchannel.base.utils.DateUtility;
import com.winchannel.mdm.distributor.model.MdmDistributor;
import com.winchannel.order.model.MdmDistEmpProdgroup;

/**
 * @author shijingru
 * @客户_产品组_业代关系维护导入扩展类
 */
public class ImportMdmDistEmpGroupEventHandler extends ImpEventHandler{

	private MdmDistEmpProdgroupManager mdmDistEmpProdgroupManager;
	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> void endRow(Map<String, String> row, T bean) {
		MdmDistEmpProdgroup mdmDistEmpProdgroup=(MdmDistEmpProdgroup) bean;
		Long id = mdmDistEmpProdgroup.getId();
		String distCode=row.get("1");
		String itemCode=row.get("2");
		String effectiveTime=row.get("4");
		MdmDistributor objMdmDistributor = getMdmDistributorByProdCode(distCode);
		BaseDictItem objBaseDictItem = getBaseDictItemId(itemCode);
		updateExpiryTime(objMdmDistributor.getDistId(), objBaseDictItem.getDictItemId(), effectiveTime, id);
	}
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
	}
	@Override
	public void startRow(Map<String, String> row) {
		String effectiveTime=row.get("4");
		String expiryTime=row.get("5");
		Date now = DateUtility.strToDate(DateUtility.getCurrentDate());
		String effect[] = effectiveTime.split("/");
		Date eff = DateUtility.strToDate(effect[2]+"-"+effect[1]+"-"+effect[0]);
//		Date eff = DateUtility.strToDate(effectiveTime);
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
			}
		}else{
			row.put("5", null);
		}
		
		
	}
	/**
	 * @param code
	 * @return
	 */

	public MdmDistributor getMdmDistributorByProdCode(String code) {
		return this.getPersister().findUniqueEntity(MdmDistributor.class,"from MdmDistributor where distCode=?", code);
	}
	
	// 通过CODE得到物料组相关信息
	public BaseDictItem getBaseDictItemId(String code) {
		return this.getPersister().findUniqueEntity(BaseDictItem.class, "from BaseDictItem where baseDict.dictId = 'prodSTRU' and levelCode = 1 and itemCode = ?", code);
	}
	//相同客户，物料组导入不同业代，将上一个客户物料组对应的业代的生效日期放置失效日期
	public void updateExpiryTime(Long distId, Long prodBrandId, String effectiveTime, Long id){
		String sql = "UPDATE MDM_DIST_EMP_PRODGROUP SET EXPIRY_TIME='"+effectiveTime+"' WHERE DIST_ID="+distId+" AND DICT_ITEM_ID="+prodBrandId
		+" and ((EXPIRY_TIME IS Null) or"
		+" (EXPIRY_TIME IS not Null and '"+effectiveTime+"'<EXPIRY_TIME))"
		+" and id !=" + id;
		this.getPersister().executeSqlUpdate(sql);
	}
	
	
	
	public MdmDistEmpProdgroupManager getMdmDistEmpProdgroupManager() {
		return mdmDistEmpProdgroupManager;
	}
	public void setMdmDistEmpProdgroupManager(
			MdmDistEmpProdgroupManager mdmDistEmpProdgroupManager) {
		this.mdmDistEmpProdgroupManager = mdmDistEmpProdgroupManager;
	}

	
}
