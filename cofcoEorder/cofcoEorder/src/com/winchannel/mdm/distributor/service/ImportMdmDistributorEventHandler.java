/**
 * 
 */
package com.winchannel.mdm.distributor.service;

import java.util.Date;
import java.util.Map;

import com.winchannel.base.conf.BaseConfigurator;
import com.winchannel.base.model.BaseDictItem;
import com.winchannel.base.model.BaseEmployee;
import com.winchannel.base.model.BaseRole;
import com.winchannel.base.model.BaseUser;
import com.winchannel.base.model.BaseUserRole;
import com.winchannel.base.service.BaseDictManager;
import com.winchannel.base.service.BaseEmployeeManager;
import com.winchannel.base.service.BaseOrgManager;
import com.winchannel.base.service.BaseRoleManager;
import com.winchannel.base.service.BaseUserManager;
import com.winchannel.base.service.BaseUserRoleManager;
import com.winchannel.core.importer.ImpEventHandler;
import com.winchannel.core.utils.StringUtils;
import com.winchannel.core.utils.ZipUtils;
import com.winchannel.mdm.distributor.model.MdmDistributor;

/**
 * @author Administrator
 *
 */
public class ImportMdmDistributorEventHandler extends ImpEventHandler {
	private BaseConfigurator configurator = BaseConfigurator.getInstance();

	/* (non-Javadoc)
	 * @see com.winchannel.core.importer.ImpEventHandler#start()
	 */
	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.winchannel.core.importer.ImpEventHandler#startRow(java.util.Map)
	 */
	@Override
	public void startRow(Map<String, String> row) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.winchannel.core.importer.ImpEventHandler#endRow(java.util.Map, java.lang.Object)
	 */
	@Override
	public <T> void endRow(Map<String, String> row, T bean) {
		// TODO Auto-generated method stub
		MdmDistributor mdmDistributor=(MdmDistributor)bean;
		//添加人员
		BaseEmployee baseEmployeeTemp=(BaseEmployee)super.getPersister().findUniqueEntity(BaseEmployee.class, "from BaseEmployee where empCode=?", mdmDistributor.getDistCode());
		if(baseEmployeeTemp==null){
			BaseEmployee baseEmployee=new BaseEmployee();
			baseEmployee.setEmpName(mdmDistributor.getDistName());
			baseEmployee.setEmpCode(mdmDistributor.getDistCode());
			baseEmployee.setState("1");
			baseEmployee.setIsEmployee("1");		
			//所属组织
			baseEmployee.setBaseOrg(mdmDistributor.getBaseOrg());			
			baseEmployee.setBaseDictItem(null);		
			baseEmployee.setBaseDictItemByEmpTypeId(null);		
			//保存地理区域		
			baseEmployee.setBaseDictItemGeo(mdmDistributor.getBaseDictItem());	
			baseEmployee.setCreatedBy(mdmDistributor.getCreatedBy());
			baseEmployee.setCreated(new Date());
			//保存职位
			BaseDictItem baseDictItem=null;
			baseDictItem=(BaseDictItem)super.getPersister().findUniqueEntity(BaseDictItem.class, "from BaseDictItem a where a.baseDict.dictId='duty' and a.itemCode=?", "DS");
			baseEmployee.setBaseDictItem(baseDictItem);
			
			baseEmployee.setUpdatedBy(mdmDistributor.getUpdatedBy());
			baseEmployee.setUpdated(new Date());
			super.getPersister().save(baseEmployee);
			mdmDistributor.setBaseEmployee(baseEmployee);
			super.getPersister().save(mdmDistributor);
			//end 添加人员
			//添加用户
			BaseUser baseUser=new BaseUser();
			baseUser.setBaseEmployee(baseEmployee);
			baseUser.setUserAccount(mdmDistributor.getDistCode());
			if(mdmDistributor == null || mdmDistributor.getDistCode()==null){
				System.out.println("mdmDistributor is null");
			}
			baseUser.setUserPassword(ZipUtils.encryptPass(mdmDistributor.getDistCode(), configurator.getPassEncryKey()));
			baseUser.setUserType("1");
			baseUser.setState("1");		
			baseUser.setBaseResource(null);			
			
			baseUser.setCreatedBy(mdmDistributor.getCreatedBy());
			baseUser.setCreated(new Date());
			
			baseUser.setUpdatedBy(mdmDistributor.getUpdatedBy());
			baseUser.setUpdated(new Date());
			super.getPersister().save(baseUser);
			//为用户指定角色
			BaseUserRole baseUserRole=new BaseUserRole();
			BaseRole baseRole=(BaseRole)super.getPersister().findUniqueEntity(BaseRole.class, "from BaseRole where roleName=?", BaseConfigurator.getInstance().getCreateDistUserdefaultRoleName());
			baseUserRole.setBaseRole(baseRole);
			baseUserRole.setBaseUser(baseUser);	
			super.getPersister().save(baseUserRole);
		}else{
			mdmDistributor.setBaseEmployee(baseEmployeeTemp);
			super.getPersister().save(mdmDistributor);
			//修改人员、用户
			baseEmployeeTemp.setEmpName(mdmDistributor.getDistName());
			baseEmployeeTemp.setEmpCode(mdmDistributor.getDistCode());
			baseEmployeeTemp.setState(mdmDistributor.getState());
			//所属组织					
			baseEmployeeTemp.setBaseOrg(mdmDistributor.getBaseOrg());									
			//地理区域					
			baseEmployeeTemp.setBaseDictItemGeo(mdmDistributor.getBaseDictItem());		
			baseEmployeeTemp.setUpdatedBy(mdmDistributor.getUpdatedBy());
			baseEmployeeTemp.setUpdated(new Date());
			super.getPersister().save(baseEmployeeTemp);
			BaseUser baseUser=super.getPersister().findUniqueEntity(BaseUser.class, " from BaseUser where baseEmployee.empId=?", baseEmployeeTemp.getEmpId());
			if(null!=baseUser){
				baseUser.setState(mdmDistributor.getState());
				baseUser.setUpdatedBy(mdmDistributor.getUpdatedBy());
				baseUser.setUpdated(new Date());
				super.getPersister().save(baseUser);
			}else{
				//添加用户
				baseUser=new BaseUser();
				baseUser.setBaseEmployee(baseEmployeeTemp);
				baseUser.setUserAccount(mdmDistributor.getDistCode());
				baseUser.setUserPassword(ZipUtils.encryptPass(mdmDistributor.getDistCode(), configurator.getPassEncryKey()));
				baseUser.setUserType("1");
				baseUser.setState(mdmDistributor.getState());		
				baseUser.setBaseResource(null);						
				baseUser.setCreatedBy(mdmDistributor.getCreatedBy());
				baseUser.setCreated(new Date());			
				baseUser.setUpdatedBy(mdmDistributor.getUpdatedBy());
				baseUser.setUpdated(new Date());
				super.getPersister().save(baseUser);
				//为用户指定角色
				BaseUserRole baseUserRole=new BaseUserRole();
				BaseRole baseRole=super.getPersister().findUniqueEntity(BaseRole.class,"from BaseRole where roleName=?", BaseConfigurator.getInstance().getCreateDistUserdefaultRoleName());
				baseUserRole.setBaseRole(baseRole);
				baseUserRole.setBaseUser(baseUser);
				super.getPersister().save(baseUserRole);
			}		
		}
	}

	/* (non-Javadoc)
	 * @see com.winchannel.core.importer.ImpEventHandler#end()
	 */
	@Override
	public void end() {
		// TODO Auto-generated method stub

	}

}
