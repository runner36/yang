package com.winchannel.task.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.winchannel.base.model.BaseDict;
import com.winchannel.base.model.BaseDictItem;
import com.winchannel.base.service.BaseDictManager;
import com.winchannel.core.dao.HibernateBaseDao;
import com.winchannel.base.utils.DateUtility;
import com.winchannel.mdm.distributor.model.MdmDistributor;
import com.winchannel.mdm.distributor.service.MdmDistributorManager;
import com.winchannel.mdm.product.service.MdmProductCache;
import com.winchannel.mdm.product.service.MdmProductManager;
import com.winchannel.task.model.InterfaceLog;

/**
 * 
 * 数据同步
 * 
 * @name DataBaseTaskManager
 * @creation 2011-11-24下午03:05:52
 * @author LiXin
 * @TODO
 * 
 */
public class DataBaseTaskManager extends HibernateBaseDao {
	public static final Log	log	= LogFactory.getLog("DataBaseTaskManager");
	 
	private MdmDistributorManager mdmDistributorManager;
	private MdmProductManager mdmProductManager;
	
	public MdmProductManager getMdmProductManager() {
		return mdmProductManager;
	}

	public void setMdmProductManager(MdmProductManager mdmProductManager) {
		this.mdmProductManager = mdmProductManager;
	}

	private BaseDictManager baseDictManager;
	 
	public BaseDictManager getBaseDictManager() {
		return baseDictManager;
	}

	public void setBaseDictManager(BaseDictManager baseDictManager) {
		this.baseDictManager = baseDictManager;
	}

	public MdmDistributorManager getMdmDistributorManager() {
		return mdmDistributorManager;
	}

	public void setMdmDistributorManager(MdmDistributorManager mdmDistributorManager) {
		this.mdmDistributorManager = mdmDistributorManager;
	}

	/**
	 * 
	 * 字典同步
	 * 
	 * @creation 2011-11-24下午03:34:30
	 * @author LiXin
	 * @TODO
	 */
	 
	public void init() {
		log.info("字典同步开始:" + DateUtility.getCurrentDateTime());
		StringBuffer sql = new StringBuffer();
		 
		try {
		
			// 产品相关字典项
			// 品牌
			sql.append(" SELECT DISTINCT MVGR1 AS code ,'prodBrand' AS id  FROM INTERFACE_SKU  v  ");
			sql.append(" LEFT JOIN BASE_DICT_ITEM d ON d.ITEM_CODE=v.MVGR1 AND d.DICT_ID='prodBrand' ");
			sql.append(" WHERE v.MVGR1<>''   AND   v.MVGR1   IS NOT NULL  AND d.DICT_ITEM_ID IS NULL  ");
			// 产品结构
			sql.append("  UNION ALL  ");
			sql.append(" SELECT DISTINCT PRDHA AS code ,'prodSTRU' AS id  FROM INTERFACE_SKU  v  ");
			sql.append(" LEFT JOIN BASE_DICT_ITEM d ON d.ITEM_CODE=v.PRDHA AND d.DICT_ID='prodSTRU' ");
			sql.append(" WHERE v.PRDHA<>''   AND   v.PRDHA   IS NOT NULL  AND d.DICT_ITEM_ID IS NULL  ");
			// 统计单位
			sql.append("  UNION ALL  ");
			sql.append(" SELECT DISTINCT UNIT AS code ,'prodUnit' AS id  FROM INTERFACE_SKU  v  ");
			sql.append(" LEFT JOIN BASE_DICT_ITEM d ON d.ITEM_CODE=v.UNIT AND d.DICT_ID='prodUnit' ");
			sql.append(" WHERE v.UNIT<>''   AND   v.UNIT   IS NOT NULL  AND d.DICT_ITEM_ID IS NULL  ");
			// 产品包装
			sql.append("  UNION ALL  ");
			sql.append(" SELECT DISTINCT MVGR3 AS code ,'prodPack' AS id  FROM INTERFACE_SKU  v  ");
			sql.append(" LEFT JOIN BASE_DICT_ITEM d ON d.ITEM_CODE=v.MVGR3 AND d.DICT_ID='prodPack' ");
			sql.append(" WHERE MVGR3<>''   AND   MVGR3   IS NOT NULL  AND d.DICT_ITEM_ID IS NULL  ");
			// 级别 属性1
			sql.append("  UNION ALL  ");
			sql.append(" SELECT DISTINCT MVGR2 AS code ,'prodAttr1' AS id  FROM INTERFACE_SKU  v  ");
			sql.append(" LEFT JOIN BASE_DICT_ITEM d ON d.ITEM_CODE=MVGR2 AND d.DICT_ID='prodAttr1' ");
			sql.append(" WHERE MVGR2<>''   AND   MVGR2   IS NOT NULL  AND d.DICT_ITEM_ID IS NULL  ");
			// 种类口味 属性2
			sql.append("  UNION ALL  ");
			sql.append(" SELECT DISTINCT MVGR4 AS code ,'prodAttr2' AS id  FROM INTERFACE_SKU  v  ");
			sql.append(" LEFT JOIN BASE_DICT_ITEM d ON d.ITEM_CODE=MVGR4 AND d.DICT_ID='prodAttr2' ");
			sql.append(" WHERE MVGR4<>''   AND   MVGR4   IS NOT NULL  AND d.DICT_ITEM_ID IS NULL  ");
			// 预留 属性3
			sql.append("  UNION ALL  ");
			sql.append(" SELECT DISTINCT MVGR5 AS code ,'prodAttr3' AS id  FROM INTERFACE_SKU  v  ");
			sql.append(" LEFT JOIN BASE_DICT_ITEM d ON d.ITEM_CODE=MVGR5 AND d.DICT_ID='prodAttr3' ");
			sql.append(" WHERE MVGR5<>''   AND   MVGR5   IS NOT NULL  AND d.DICT_ITEM_ID IS NULL  ");
			// 物料组 属性4
			sql.append("  UNION ALL  ");
			sql.append(" SELECT DISTINCT MATKL AS code ,'prodAttr4' AS id  FROM INTERFACE_SKU  v  ");
			sql.append(" LEFT JOIN BASE_DICT_ITEM d ON d.ITEM_CODE=MATKL AND d.DICT_ID='prodAttr4' ");
			sql.append(" WHERE MATKL<>''   AND   MATKL   IS NOT NULL  AND d.DICT_ITEM_ID IS NULL  ");
			// 体积单位
			sql.append("  UNION ALL  ");
			sql.append(" SELECT DISTINCT MATKL AS code ,'prodVolehUnit' AS id  FROM INTERFACE_SKU  v  ");
			sql.append(" LEFT JOIN BASE_DICT_ITEM d ON d.ITEM_CODE=VOLEH AND d.DICT_ID='prodVolehUnit' ");
			sql.append(" WHERE MATKL<>''   AND   MATKL   IS NOT NULL  AND d.DICT_ITEM_ID IS NULL  ");
			// 重量单位
			sql.append("  UNION ALL  ");
			sql.append(" SELECT DISTINCT MATKL AS code ,'prodWeightUnit' AS id  FROM INTERFACE_SKU  v  ");
			sql.append(" LEFT JOIN BASE_DICT_ITEM d ON d.ITEM_CODE=GEWEI AND d.DICT_ID='prodWeightUnit' ");
			sql.append(" WHERE MATKL<>''   AND   MATKL   IS NOT NULL  AND d.DICT_ITEM_ID IS NULL  ");
			
			List<Object[]> list=this.executeSqlQuery(sql.toString());
			for(Object[] o:list  ){
				BaseDictItem dictItem = new BaseDictItem();
				dictItem.setItemCode(o[0] .toString());
				dictItem.setItemName(dictItem.getItemCode());
				dictItem.setState("1");
				
				//dictItem.setSubCode("001");
				dictItem.setLevelCode(Long.valueOf(1));
				dictItem.setBaseDict(this.get(BaseDict.class, o[1] .toString()));
				makeSubCode(BaseDictItem.class, dictItem);
				this.save(dictItem);
			}
		} catch (Throwable ex) {
			log.error("字典同步时发生错误：" + ex.getMessage(), ex);

			this.saveDblog("字典", ex.getMessage());

		} 
		log.info("字典同步结束:" + DateUtility.getCurrentDateTime());
		this.saveDblog("字典", "字典同步结束");

		 
	}

	/**
	 * 保存log
	 * 
	 * @param model
	 * @param message
	 */
	public void saveDblog(String model, String message) {

		 
		try {

			InterfaceLog intLog = new InterfaceLog();
			intLog.setCreateTime(new Date());
			intLog.setMessage(message);
			intLog.setModel(model);
			this.save(intLog);
		} catch (Throwable ex) {
			log.error(" log 失败：" + ex.getMessage(), ex);
		}  
	}

	/**
	 * 产品主数据同步
	 */
	public void transferProducData() {
		log.info(" 产品主数据同步开始:" + DateUtility.getCurrentDateTime());
		 
	 
		int upNum = 0;
		String mes = "";
		StringBuffer sql = new StringBuffer();

		try {		 

			// sql.append(" INSERT INTO MDM_PRODUCT (PROD_NAME,PROD_CODE,BRAND_ID,PRODSTRU_ID,PROD_UNIT,memo1,memo2,memo3,memo4,prod_Weight ,PRODWEIGHT_UNIT_ID,PROD_PRICE)");
			sql.append(" INSERT INTO MDM_PRODUCT (	PROD_NAME,PROD_CODE,  BRAND_ID ,  PRODSTRU_ID,PROD_UNIT,PACK_ID");
			sql.append(" ,STATE,MEMO1,MEMO2,MEMO3,prod_VOLUM,prod_Voleh_Unit ,PROD_WEIGHT,PRODWEIGHT_UNIT_ID,MEMO4,PROD_PRICE, CREATED_BY,CREATED ) ");
			sql.append("  SELECT  SKU_NAME,SKU_CODE,prodBrand.DICT_ITEM_ID,prodSTRU.DICT_ITEM_ID");
			sql.append(" ,prodUnit.DICT_ITEM_ID,prodPack.DICT_ITEM_ID,CASE v.STATE WHEN 'X' THEN '0'  WHEN 'x' THEN '0' ELSE '1' END ,prodAttr1.DICT_ITEM_ID");
			sql.append("  ,prodAttr2.DICT_ITEM_ID,prodAttr3.DICT_ITEM_ID,VOLUM,VOLEH.DICT_ITEM_ID,v.BRGEW,prodWeightUnitId.DICT_ITEM_ID");
			sql.append(" ,prodAttr4.DICT_ITEM_ID,prodPrice, '接口同步',getdate()");
			sql.append("  FROM INTERFACE_SKU v");
			sql.append("  LEFT JOIN MDM_PRODUCT  p ON p.PROD_CODE=v.SKU_CODE");
			sql.append("  LEFT JOIN BASE_DICT_ITEM prodBrand ON prodBrand.ITEM_CODE=v.MVGR1 AND prodBrand.DICT_ID='prodBrand' ");
			sql.append("  LEFT JOIN BASE_DICT_ITEM prodSTRU ON prodSTRU.ITEM_CODE=v.PRDHA AND prodSTRU.DICT_ID='prodSTRU' ");
			sql.append("  LEFT JOIN BASE_DICT_ITEM prodUnit ON prodUnit.ITEM_CODE=v.UNIT AND prodUnit.DICT_ID='prodUnit' ");
			sql.append("  LEFT JOIN BASE_DICT_ITEM prodPack ON prodPack.ITEM_CODE=v.MVGR3 AND prodPack.DICT_ID='prodPack' ");
			sql.append("  LEFT JOIN BASE_DICT_ITEM prodAttr1 ON prodAttr1.ITEM_CODE=v.MVGR2 AND prodAttr1.DICT_ID='prodAttr1' ");
			sql.append("  LEFT JOIN BASE_DICT_ITEM prodAttr2 ON prodAttr2.ITEM_CODE=v.MVGR4 AND prodAttr2.DICT_ID='prodAttr2' ");
			sql.append("  LEFT JOIN BASE_DICT_ITEM prodAttr3 ON prodAttr3.ITEM_CODE=v.MVGR5 AND prodAttr3.DICT_ID='prodAttr3'");
			sql.append("  LEFT JOIN BASE_DICT_ITEM VOLEH ON VOLEH.ITEM_CODE=v.VOLEH AND VOLEH.DICT_ID='prodVolehUnit' 		");
			sql.append("  LEFT JOIN BASE_DICT_ITEM prodWeightUnitId ON prodWeightUnitId.ITEM_CODE=v.GEWEI AND prodWeightUnitId.DICT_ID='prodWeightUnit' ");
			sql.append("  LEFT JOIN BASE_DICT_ITEM prodAttr4 ON prodAttr4.ITEM_CODE=v.MATKL AND prodAttr4.DICT_ID='prodAttr4' ");

			sql.append("   WHERE p.PROD_ID IS null ");
			upNum =this.executeSqlUpdate(sql.toString());
			 
			mes += "共添加" + upNum + "产品资料";
			log.info("共添加" + upNum + "产品资料");
			// 更新
			upNum = 0;
			sql.delete(0, sql.length());
			sql.append(" UPDATE MDM_PRODUCT SET PROD_NAME=t.SKU_NAME ,prod_code=SKU_CODE,BRAND_ID=brand,PRODSTRU_ID=stru ");
			sql.append("  ,PROD_UNIT=unit, PACK_ID=pack,state=states,MEMO1=prodAttr1 ");
			sql.append("  ,MEMO2=prodAttr2,MEMO3=prodAttr3,PROD_VOLUM=VOLUM,PROD_VOLEH_UNIT=volehUnit ");
			sql.append("  ,MEMO4=prodAttr4,PROD_PRICE=prodPrice,UPDATED_BY='接口同步',UPDATED=getdate() ");
			sql.append(" FROM ( ");
			sql.append(" SELECT SKU_NAME,SKU_CODE,prodBrand.DICT_ITEM_ID AS brand,prodSTRU.DICT_ITEM_ID AS stru ");
			sql.append("   ,prodUnit.DICT_ITEM_ID AS unit ,prodPack.DICT_ITEM_ID AS pack ,v.STATE AS states ,prodAttr1.DICT_ITEM_ID AS prodAttr1 ");
			sql.append("   ,prodAttr2.DICT_ITEM_ID AS prodAttr2 ,prodAttr3.DICT_ITEM_ID AS prodAttr3,VOLUM,VOLEH.DICT_ITEM_ID AS volehUnit ");
			sql.append("   ,v.BRGEW,prodWeightUnitId.DICT_ITEM_ID AS weightUnit ");
			sql.append("   ,prodAttr4.DICT_ITEM_ID AS prodAttr4,prodPrice ,p.PROD_ID AS pid ");
			sql.append(" FROM INTERFACE_SKU v ");
			sql.append(" LEFT JOIN MDM_PRODUCT  p ON p.PROD_CODE=v.SKU_CODE ");
			sql.append(" LEFT JOIN BASE_DICT_ITEM prodBrand ON prodBrand.ITEM_CODE=v.MVGR1 AND prodBrand.DICT_ID='prodBrand'  ");
			sql.append(" LEFT JOIN BASE_DICT_ITEM prodSTRU ON prodSTRU.ITEM_CODE=v.PRDHA AND prodSTRU.DICT_ID='prodSTRU'  ");
			sql.append(" LEFT JOIN BASE_DICT_ITEM prodUnit ON prodUnit.ITEM_CODE=v.UNIT AND prodUnit.DICT_ID='prodUnit'  ");
			sql.append(" LEFT JOIN BASE_DICT_ITEM prodPack ON prodPack.ITEM_CODE=v.MVGR3 AND prodPack.DICT_ID='prodPack' "); 
			sql.append(" LEFT JOIN BASE_DICT_ITEM prodAttr1 ON prodAttr1.ITEM_CODE=v.MVGR2 AND prodAttr1.DICT_ID='prodAttr1' "); 
			sql.append(" LEFT JOIN BASE_DICT_ITEM prodAttr2 ON prodAttr2.ITEM_CODE=v.MVGR4 AND prodAttr2.DICT_ID='prodAttr2' "); 
			sql.append(" LEFT JOIN BASE_DICT_ITEM prodAttr3 ON prodAttr3.ITEM_CODE=v.MVGR5 AND prodAttr3.DICT_ID='prodAttr3' "); 
			sql.append(" LEFT JOIN BASE_DICT_ITEM VOLEH ON VOLEH.ITEM_CODE=v.VOLEH AND VOLEH.DICT_ID='prodVolehUnit' "); 			  
			sql.append(" LEFT JOIN BASE_DICT_ITEM prodWeightUnitId ON prodWeightUnitId.ITEM_CODE=v.GEWEI AND prodWeightUnitId.DICT_ID='prodWeightUnit' "); 	
			sql.append(" LEFT JOIN BASE_DICT_ITEM prodAttr4 ON prodAttr4.ITEM_CODE=v.MATKL AND prodAttr4.DICT_ID='prodAttr4'  ");
			sql.append(" WHERE p.CREATED  <= CONVERT(varchar(100), GETDATE(), 23)  ");
			sql.append(" ) t  ");
			sql.append(" WHERE  t.pid=PROD_ID ");
			upNum =this.executeSqlUpdate(sql.toString());
			 
			mes += ";共更新" + upNum + "产品资料";
			log.info("共更新" + upNum + "产品资料");
		 

		} catch (Throwable ex) {

			log.error(" 产品主数据同步时发生错误：" + ex.getMessage(), ex);
			this.saveDblog("产品主数据", ex.getMessage());

		}  
		log.info(" 产品主数据同步结束:" + DateUtility.getCurrentDateTime());
		this.saveDblog("产品主数据", mes);

	}
	/**
	 * 经销商主数据同步
	 * 
	 * @creation  2011-12-2上午10:33:36
	 * @author    LiXin
	 * @TODO
	 */
	public void transferDistData() {
		log.info("经销商主数据同步开始:" + DateUtility.getCurrentDateTime());
		 
		String mes = "";
		StringBuffer sql = new StringBuffer();
		int upNum = 0;
		try {
			//新增经销商
			
			//客户编码、客户名称、 组织、地理、邮编编码	、客户地址、联系人、手机、客户电话、客户传真、客户收货限制、客户类型、状态
			//经销商接口中暂无：“发票类型编码” 
			sql.append(" INSERT INTO MDM_DISTRIBUTOR  (DIST_CODE,DIST_NAME,org_id,geo_id,DIST_POST,DIST_ADDR,LINKMAN_NAME,LINKMAN_TEL,DIST_TEL,MGR_TEL,OWNERGRP_CODE,OWNERGRP_NAME,STATE) ");
			sql.append(" SELECT CUST_CODE,CUST_NAME,o.ORG_ID,g.DICT_ITEM_ID,i.DIST_POST,CUST_ADDR,i.LINKMAN_NAME,i.LINKMAN_TEL,i.DIST_TEL,i.MGR_TEL,i.OWNERGRP_CODE,i.OWNERGRP_NAME,CASE i.STATE  WHEN '01' THEN '0'  ELSE '1' END");
			sql.append(" FROM INTERFACE_DIST i  ");
			sql.append(" LEFT JOIN MDM_DISTRIBUTOR d ON d.DIST_CODE=i.CUST_CODE ");
			sql.append(" LEFT JOIN BASE_ORG o ON o.ORG_CODE=i.ORG_CODE ");
			sql.append(" LEFT JOIN BASE_DICT_ITEM  g ON g.ITEM_CODE=i.GEO_CODE AND g.DICT_ID='geography' "); 
			sql.append(" WHERE d.DIST_ID IS NULL  ");
			upNum =this.executeSqlUpdate(sql.toString());
			 
			mes += ";共添加" + upNum + "经销商资料";
			log.info("共添加" + upNum + "经销商资料");
			
			// 更新经销商
			upNum = 0;
			sql.delete(0, sql.length());
			sql.append("  UPDATE MDM_DISTRIBUTOR SET DIST_NAME=t.CUST_NAME,org_id=t.ORG_ID,geo_id=t.DICT_ITEM_ID,DIST_POST=t.DIST_POST");
			sql.append("  ,DIST_ADDR=t.CUST_ADDR,LINKMAN_NAME=t.LINKMAN_NAME,LINKMAN_TEL=t.LINKMAN_TEL,DIST_TEL=t.DIST_TEL,MGR_TEL=t.MGR_TEL");
			sql.append("  ,OWNERGRP_CODE=t.OWNERGRP_CODE,OWNERGRP_NAME=t.OWNERGRP_NAME,STATE=t.STATE ,UPDATED_BY='接口同步',UPDATED=getdate()");
			sql.append("  FROM ( ");
			sql.append("  SELECT CUST_CODE,CUST_NAME,o.ORG_ID,g.DICT_ITEM_ID");
			sql.append("  ,v.DIST_POST,CUST_ADDR,v.LINKMAN_NAME,v.LINKMAN_TEL,v.DIST_TEL,v.MGR_TEL");
			sql.append("  ,v.OWNERGRP_CODE,v.OWNERGRP_NAME,  CASE v.STATE  WHEN '01' THEN '0'  ELSE '1' END AS STATE");  
			sql.append("  ,dist.DIST_ID AS distId ");
			sql.append("  FROM INTERFACE_DIST v ");
			sql.append("  LEFT JOIN MDM_DISTRIBUTOR  dist ON dist.DIST_CODE=v.CUST_CODE"); 
			sql.append("  LEFT JOIN BASE_ORG o ON o.ORG_CODE=v.ORG_CODE ");
			sql.append("  LEFT JOIN BASE_DICT_ITEM  g ON g.ITEM_CODE=v.GEO_CODE AND g.DICT_ID='geography'");    
			sql.append("  where  dist.CREATED <= CONVERT(varchar(100), GETDATE(), 23) ) t  ");
			sql.append("  WHERE  t.distId=DIST_ID");  
			upNum =this.executeSqlUpdate(sql.toString());
			 
			mes += ";共更新" + upNum + "客户资料";
			log.info("共更新" + upNum + "客户资料");
			
			//更新或新增 经销商人员、用户、指定用户默认角色
			int newUserRows=0,updateUserRows=0;
			List<MdmDistributor> lstMdmDistributor=this.findEntity(MdmDistributor.class, " from MdmDistributor order by distId");
			for(MdmDistributor mdmDistributor:lstMdmDistributor){
				mdmDistributorManager.save(mdmDistributor);
			updateUserRows++;
			}
			/*for(MdmDistributor mdmDistributor:lstMdmDistributor){
				//新增人员、用户、指定角色
				if(null==mdmDistributor.getBaseEmployee()){
					//添加人员
					BaseEmployee baseEmployee=new BaseEmployee();
					baseEmployee.setEmpName(mdmDistributor.getDistName());
					baseEmployee.setEmpCode(mdmDistributor.getDistCode());
					baseEmployee.setState(mdmDistributor.getState());
					baseEmployee.setIsEmployee("1");		
					//所属组织					
					baseEmployee.setBaseOrg(mdmDistributor.getBaseOrg());									
					//地理区域					
					baseEmployee.setBaseDictItemGeo(mdmDistributor.getBaseDictItem());		
					//职务
					BaseDictItem baseDictItem=null;
					baseDictItem=(BaseDictItem)this.findUniqueEntity(BaseDictItem.class, "from BaseDictItem a where a.baseDict.dictId='duty' and a.itemCode=?", "DS");
					baseEmployee.setBaseDictItem(baseDictItem);
					baseEmployee.setCreatedBy("接口同步");
					baseEmployee.setCreated(new Date());					
					baseEmployee.setUpdatedBy("接口同步");
					baseEmployee.setUpdated(new Date());
					this.save(baseEmployee);
					mdmDistributor.setBaseEmployee(baseEmployee);
					this.save(mdmDistributor);
					//添加用户
					BaseUser baseUser=new BaseUser();
					baseUser.setBaseEmployee(baseEmployee);
					baseUser.setUserAccount(mdmDistributor.getDistCode());
					baseUser.setUserPassword(mdmDistributor.getDistCode());
					baseUser.setUserType("1");
					baseUser.setState(mdmDistributor.getState());		
					baseUser.setBaseResource(null);						
					baseUser.setCreatedBy("接口同步");
					baseUser.setCreated(new Date());			
					baseUser.setUpdatedBy("接口同步");
					baseUser.setUpdated(new Date());
					this.baseUserManager.save(baseUser);
					//为用户指定角色
					BaseUserRole baseUserRole=new BaseUserRole();
					BaseRole baseRole=this.findUniqueEntity(BaseRole.class,"from BaseRole where roleName=?", BaseConfigurator.getInstance().getCreateDistUserdefaultRoleName());
					baseUserRole.setBaseRole(baseRole);
					baseUserRole.setBaseUser(baseUser);
					this.save(baseUserRole);
					newUserRows++;
				}else{
					//修改人员、用户
					BaseEmployee baseEmployee=mdmDistributor.getBaseEmployee();
					baseEmployee.setEmpName(mdmDistributor.getDistName());
					baseEmployee.setEmpCode(mdmDistributor.getDistCode());
					baseEmployee.setState(mdmDistributor.getState());
					//所属组织					
					baseEmployee.setBaseOrg(mdmDistributor.getBaseOrg());									
					//地理区域					
					baseEmployee.setBaseDictItemGeo(mdmDistributor.getBaseDictItem());		
					baseEmployee.setUpdatedBy("接口同步");
					baseEmployee.setUpdated(new Date());
					this.save(baseEmployee);
					BaseUser baseUser=this.findUniqueEntity(BaseUser.class, " from BaseUser where baseEmployee.empId=?", baseEmployee.getEmpId());
					if(null!=baseUser){
						baseUser.setState(mdmDistributor.getState());
						baseUser.setUpdatedBy("接口同步");
						baseUser.setUpdated(new Date());
						this.save(baseUser);
					}else{
						//添加用户
						baseUser=new BaseUser();
						//baseUser.setBaseEmployee(baseEmployee);
				 
						baseUser.setUserAccount(mdmDistributor.getDistCode());
						baseUser.setUserPassword(mdmDistributor.getDistCode());
						baseUser.setUserType("1");
						baseUser.setState(mdmDistributor.getState());		
						baseUser.setBaseResource(null);						
						baseUser.setCreatedBy("接口同步");
						baseUser.setCreated(new Date());			
						baseUser.setUpdatedBy("接口同步");
						baseUser.setUpdated(new Date());
					 
						this.baseUserManager.save(baseUser);
						//为用户指定角色
						BaseUserRole baseUserRole=new BaseUserRole();
						BaseRole baseRole=this.findUniqueEntity(BaseRole.class,"from BaseRole where roleName=?", BaseConfigurator.getInstance().getCreateDistUserdefaultRoleName());
						baseUserRole.setBaseRole(baseRole);
						baseUserRole.setBaseUser(baseUser);
						this.save(baseUserRole);
					}
					updateUserRows++;
				}
			}*/
			
			mes += ";共添加" + newUserRows + "经销商人员、用户信息";
			log.info("共添加" + newUserRows + "经销商人员、用户信息");
			
			mes += ";共更新" + updateUserRows + "经销商人员、用户信息";
			log.info("共更新" + updateUserRows + "经销商人员、用户信息");
			
		} catch (Throwable ex) {
			log.error("经销商主数据同步时发生错误：" + ex.getMessage(), ex);
			this.saveDblog("经销商主数据", ex.getMessage());
		} 
		log.info("经销商主数据同步结束:" + DateUtility.getCurrentDateTime());
		this.saveDblog("经销商主数据", mes);

	}
	/**
	 * 经销商送货地址主数据同步
	 * 
	 * @creation  2011-12-2上午10:33:36
	 * @author    LiXin
	 * @TODO
	 */
	public void transferDistAddData() {
		log.info("经销商送货地址主数据同步开始:" + DateUtility.getCurrentDateTime());
		 
		String mes = "";
		StringBuffer sql = new StringBuffer();
		int upNum = 0;
		try {
			sql.append(" INSERT INTO MDM_DISTRIBUTOR_ADDRESS (DIST_ID,DICT_ITEM_ID,Status,SHIPTO_CODE,SHIPTO_NAME");       
			sql.append(" ,SHIPTO_ADD,Contact ,TEL,MOBILE,FACTORY_DELIVERY,CREATED_DATE,CREATED_BYID,CREATED_BYNAME  )");  
			sql.append(" SELECT d.DIST_ID,i.DICT_ITEM_ID,a.Status,a.SHIPTO_CODE,a.SHIPTO_NAME");  
			sql.append(" ,a.SHIPTO_ADD,a.CONTACT,a.TEL,a.MOBILE,a.FACTORY_DELIVERY,getdate(),1,'系统同步任务'");  
			sql.append(" FROM INTERFACE_DISTADD a");  
			sql.append(" LEFT JOIN MDM_DISTRIBUTOR d ON d.DIST_CODE=a.CUST_CODE");  
			sql.append(" LEFT JOIN BASE_DICT_ITEM i ON i.ITEM_CODE=convert(VARCHAR(10),a.DICT_ITEM_ID  ) AND i.DICT_ID='prodSTRU'");  
			sql.append(" LEFT JOIN MDM_DISTRIBUTOR_ADDRESS aa ON aa.DIST_ID=d.DIST_ID AND aa.DICT_ITEM_ID=i.DICT_ITEM_ID AND aa.SHIPTO_CODE=a.SHIPTO_CODE");  
			sql.append(" WHERE aa.ID IS NULL ");  
			sql.append("  AND d.DIST_ID IS NOT NULL   AND a.SHIPTO_CODE IS NOT NULL  AND i.DICT_ITEM_ID IS NOT   NULL  ");  
			sql.append("  GROUP BY d.DIST_ID,i.DICT_ITEM_ID,a.Status,a.SHIPTO_CODE,a.SHIPTO_NAME   ,a.SHIPTO_ADD,a.CONTACT,a.TEL,a.MOBILE,a.FACTORY_DELIVERY ");  

			upNum =this.executeSqlUpdate(sql.toString());
			 
			mes += "共添加" + upNum + "经销商送货地址主数据资料";
			log.info("共添加" + upNum + "经销商送货地址主数据资料");
			
			// 更新
		 
			sql.delete(0, sql.length());
			sql.append(" UPDATE MDM_DISTRIBUTOR_ADDRESS   SET  ");
			sql.append("   DICT_ITEM_ID=t.DICT_ITEM_ID ,Status=t.Status,SHIPTO_CODE=t.SHIPTO_CODE,SHIPTO_NAME=t.SHIPTO_NAME ");
			sql.append("  ,SHIPTO_ADD=t.SHIPTO_ADD ,Contact=t.Contact,TEL=t.TEL,MOBILE=t.MOBILE,FACTORY_DELIVERY=t.FACTORY_DELIVERY ");
			sql.append(" ,UPDATED_DATE =getdate() ,UPDATED_BYNAME='系统同步任务',UPDATED_BYID=1 ");
			sql.append(" FROM  ");
			sql.append(" ( SELECT  d.DIST_ID AS id,i.DICT_ITEM_ID,a.Status,a.SHIPTO_CODE,a.SHIPTO_NAME  ");
			sql.append(" 		  ,a.SHIPTO_ADD,a.CONTACT,a.TEL,a.MOBILE,a.FACTORY_DELIVERY ");
			sql.append(" 	FROM INTERFACE_DISTADD a ");
			sql.append(" 	LEFT JOIN MDM_DISTRIBUTOR d ON d.DIST_CODE=a.CUST_CODE ");
			sql.append(" 	LEFT JOIN MDM_DISTRIBUTOR_ADDRESS  i ON i.DIST_ID=d.DIST_ID ");
			sql.append(" 	LEFT JOIN BASE_DICT_ITEM prodSTRU ON prodSTRU.ITEM_CODE=convert(VARCHAR(10),a.DICT_ITEM_ID  ) AND prodSTRU.DICT_ID='prodSTRU' ");
			sql.append(" 	WHERE i.CREATED_DATE <= CONVERT(varchar(100), GETDATE(), 23)    ");
			sql.append(" ) t ");
			sql.append(" WHERE t.id=	 DIST_ID	  ");
			upNum =this.executeSqlUpdate(sql.toString());
			mes += "共更新" + upNum + "经销商送货地址主数据资料";
			log.info("共更新" + upNum + "经销商送货地址主数据资料");
		} catch (Throwable ex) {
			log.error("经销商送货地址主数据同步时发生错误：" + ex.getMessage(), ex);
			this.saveDblog("经销商送货地址主数据", ex.getMessage());
		} 
		log.info("经销商送货地址主数据同步结束:" + DateUtility.getCurrentDateTime());
		this.saveDblog("经销商送货地址主数据 ", mes);

	}
	
	public void initMdmProductCache(){
		log.info("初始化产品缓存开始.");
		MdmProductCache.init(mdmProductManager, baseDictManager);
		MdmProductCache.initNewProduct(mdmProductManager, baseDictManager);
		MdmProductCache.initDistRecentProduct(mdmProductManager);
		log.info("初始化产品缓存结束.");
	}
	
	public void transferSapOrderAll(){
		log.info("SAP订单数据同步_获取未加载数据批次:" + DateUtility.getCurrentDateTime());
		int upNum = 0;
		StringBuffer mes = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		try{
			sql.append("SELECT data_batch_id FROM INTERFACE_ORDER_SAP_BATCH_LIST WHERE is_Success_Load='0' and download_isFinish='1' ORDER BY data_batch_id");
			List<Object[]> lstBatchList=this.executeSqlQuery(sql.toString());
			if(lstBatchList!=null &&lstBatchList.size()>0){
				for(Object[] obj:lstBatchList){
					BigDecimal dataBatchId=(BigDecimal)obj[0];
					sql.delete(0, sql.length());
					sql.append("SELECT min(AUDAT) audat_start_date,max(AUDAT) audat_end_date FROM INTERFACE_ORDER_SAP_RENDER WHERE DATA_BATCH_ID="+dataBatchId.toPlainString());
					List<Object[]> orderDateStartAndEnd=this.executeSqlQuery(sql.toString());
					if(orderDateStartAndEnd!=null&&orderDateStartAndEnd.size()>0){
						Object[] objDate=orderDateStartAndEnd.get(0);
						sql.delete(0, sql.length());
						sql.append("DELETE FROM ORDER_SAP WHERE ORDER_DATE BETWEEN '"+objDate[0].toString().substring(0, 10)+"' AND '"+objDate[1].toString().substring(0, 10) +" 23:59:59'");
						log.info(sql.toString());
						this.executeSqlUpdate(sql.toString());
						sql.delete(0, sql.length());
						sql.append("INSERT INTO ORDER_SAP ");
						sql.append("(data_batch_id,data_download_date,SALE_ORG,PROVINCE,CUST_CODE,CUST_NAME,ORDER_DATE,ORDER_CODE,PROD_BIG_CLASS,CUST_WILL_CODE,SKU_CODE,SKU_NAME,PRICE,ORDER_QUA,FACTORY_NAME,STOCK_NAME,REFUSE_MEMO,KHYQSL,XYDJSL,JGDJSL,STOCK_NO_QUA,PLAN_GOODS_QUA,NO_GOODS_QUA,FACT_GOODS_QUA,NO_ACCOUNT_QUA,SYS_BILL_QUA,NFKIMG_C,Status,CREATED_DATE,CREATED_BYID,CREATED_BYNAME,UPDATED_DATE,UPDATED_BYID,UPDATED_BYNAME,VKGRP,VKGRP_T) ");
						sql.append("SELECT m.data_batch_id,m.data_download_date, VKORG,BZIRK,KUNNR,NAME1,AUDAT,BSTKD,VTEXT1,VBELN,MATNR,MAKTX,NETPR,KWMENG_C,NAME2,LGOBE,VKAUS_BEZEI,KHYQSL,XYDJSL,JGDJSL,KCWMZSL,LFIMG_C,NFIMG_C,SFIMG_C,OFIMG_C,FKIMG_C,NFKIMG_C,1,getdate(),1,'admin',getdate(),1,'admin',VKGRP,VKGRP_T  ");
						sql.append("FROM INTERFACE_ORDER_SAP_RENDER D INNER JOIN INTERFACE_ORDER_SAP_BATCH_LIST M ON d.DATA_BATCH_ID=m.data_batch_id  ");
						//sql.append("LEFT JOIN ORDER_INFO o ON D.BSTKD=o.ORDER_CODE ");
						sql.append("LEFT JOIN MDM_DISTRIBUTOR dist ON d.KUNNR=DIST.DIST_CODE ");
						sql.append("WHERE d.data_batch_id="+dataBatchId.toPlainString());
						//sql.append("AND o.order_id IS NOT NULL ");
						sql.append("AND m.is_Success_Load='0' and m.download_isFinish='1' ");
						sql.append("AND dist.dist_id IS NOT NULL ");
						upNum=this.executeSqlUpdate(sql.toString());
						sql.delete(0, sql.length());
						sql.append("UPDATE INTERFACE_ORDER_SAP_BATCH_LIST SET load_type='A',is_success_load='1' WHERE data_batch_id="+dataBatchId.toPlainString());
						this.executeSqlUpdate(sql.toString());
						mes.delete(0, mes.length());
						mes.append( "共更新" + upNum + " 条sap 订单数据");
						log.info("共更新" + upNum + " 条sap 订单数据");
						this.saveDblog("sap 订单数据同步", mes.toString());
					}
				}
			}
		}catch (Throwable ex) {
			log.info(ex.toString());
		} 
		
		log.info("SAP订单数据同步结束:" + DateUtility.getCurrentDateTime());		 
	}
	
	public void transferSapOrderByBatchId(Long dataBatchId){
		log.info("SAP订单数据同步开始:" + DateUtility.getCurrentDateTime());
		 
		String mes = "";
		StringBuffer sql = new StringBuffer();
		int upNum = 0;
		
		try {
			sql.append("truncate table ORDER_SAP");
			this.executeSqlUpdate(sql.toString());
			      
			
			//upNum =this.executeSqlUpdate(sql.toString());
			 
			mes += "共添加" + upNum + "条SAP订单数据";
			log.info("共添加" + upNum + "条SAP订单数据");
		} catch (Throwable ex) {
			log.error("SAP订单数据同步时发生错误：" + ex.getMessage(), ex);
			this.saveDblog("SAP订单数据", ex.getMessage());
		} 
		log.info("SAP订单数据同步结束:" + DateUtility.getCurrentDateTime());
		this.saveDblog("SAP订单数据", mes);
	}
	
	public void transferSapOrder_old(){
		log.info("SAP订单数据同步开始:" + DateUtility.getCurrentDateTime());
		 
		String mes = "";
		StringBuffer sql = new StringBuffer();
		int upNum = 0;
		
		try {
			sql.append("truncate table ORDER_SAP");
			this.executeSqlUpdate(sql.toString());
			sql.delete(0, sql.length());
			sql.append("INSERT INTO ORDER_SAP (");
			sql.append("SALE_ORG,");
			sql.append("PROVINCE,");
			sql.append("CUST_CODE,");
			sql.append("CUST_NAME,");
			sql.append("ORDER_DATE,");
			sql.append("ORDER_CODE,");
			sql.append("PROD_BIG_CLASS,");
			sql.append("CUST_WILL_CODE,");
			sql.append("SKU_CODE,");
			sql.append("SKU_NAME,");
			sql.append("PRICE,");
			sql.append("ORDER_QUA,");
			sql.append("FACTORY_NAME,");
			sql.append("STOCK_NAME,");
			sql.append("REFUSE_MEMO,");
			sql.append("KHYQSL,");
			sql.append("XYDJSL,");
			sql.append("JGDJSL,");
			sql.append("STOCK_NO_QUA,");
			sql.append("PLAN_GOODS_QUA,");
			sql.append("NO_GOODS_QUA,");
			sql.append("FACT_GOODS_QUA,");
			sql.append("NO_ACCOUNT_QUA,");
			sql.append("SYS_BILL_QUA,");
			sql.append("NFKIMG_C,");
			sql.append("Status,");
			sql.append("CREATED_DATE,");
			sql.append("CREATED_BYID,");
			sql.append("CREATED_BYNAME,");
			sql.append("UPDATED_DATE,");
			sql.append("UPDATED_BYID,");
			sql.append("UPDATED_BYNAME)");
			
			sql.append("SELECT  VKORG,");
			sql.append("BZIRK,");
			sql.append("KUNNR,");
			sql.append("NAME1,");
			sql.append("AUDAT,");
			sql.append("BSTKD,");
			sql.append("VTEXT1,");
			sql.append("VBELN,");
			sql.append("MATNR,");
			sql.append("MAKTX,");
			sql.append("NETPR,");
			sql.append("KWMENG_C,");
			sql.append("NAME2,");
			sql.append("LGOBE,");
			sql.append("VKAUS_BEZEI,");
			sql.append("KHYQSL,");
			sql.append("XYDJSL,");
			sql.append("JGDJSL,");
			sql.append("KCWMZSL,");
			sql.append("LFIMG_C,");
			sql.append("NFIMG_C,");
			sql.append("SFIMG_C,");
			sql.append("OFIMG_C,");
			sql.append("FKIMG_C,");
			sql.append("NFKIMG_C,");
			sql.append("1,");
			sql.append("getdate(),");
			sql.append("1,");
			sql.append("'admin',");
			sql.append("getdate(),");
			sql.append("1,");
			sql.append("'admin' ");
			sql.append("FROM INTERFACE_ORDER_SAP_RENDER");       
			
			upNum =this.executeSqlUpdate(sql.toString());
			 
			mes += "共添加" + upNum + "条SAP订单数据";
			log.info("共添加" + upNum + "条SAP订单数据");
		} catch (Throwable ex) {
			log.error("SAP订单数据同步时发生错误：" + ex.getMessage(), ex);
			this.saveDblog("SAP订单数据", ex.getMessage());
		} 
		log.info("SAP订单数据同步结束:" + DateUtility.getCurrentDateTime());
		this.saveDblog("SAP订单数据", mes);
	}
}
