package com.winchannel.base.service;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.winchannel.base.conf.BaseConfigurator;
import com.winchannel.base.model.BaseRole;
import com.winchannel.base.model.BaseUser;
import com.winchannel.base.model.BaseUserRole;
import com.winchannel.core.exception.BusinessException;
import com.winchannel.core.importer.ImpEventHandler;
import com.winchannel.core.utils.ZipUtils;

public class ImportBaseUserEventHandler extends ImpEventHandler{
	private BaseConfigurator configurator = BaseConfigurator.getInstance();
	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> void endRow(Map<String, String> row, T bean) {
		BaseUser baseUser=(BaseUser) bean;
		String password=row.get("3");
		baseUser.setUserPassword(encryptPass(password));
		String userType=row.get("6");
		if(StringUtils.isNotBlank(userType)){
			if("手机用户".equals(userType)){
				baseUser.setUserType("2");
			}else if("系统用户".equals(userType)){
				baseUser.setUserType("1");
			}else{
				throw new BusinessException("不存在此用户类型："+userType);
			}
		}
		super.getPersister().save(baseUser);
		String rolename=row.get("5");
		if(StringUtils.isNotBlank(rolename)){
			deleteByUserId(baseUser.getUserId());
			String[] rolenames=rolename.split(",");
			for(String name:rolenames){
				BaseRole baserole=getRoleIdByName(name);
				if(baserole!=null){
					BaseUserRole userRole = new BaseUserRole();
					userRole.setBaseUser(baseUser);
					userRole.setBaseRole(baserole);
					super.getPersister().save(userRole);
				}else{
					throw new BusinessException("角色名称不存在："+name);
				}
			}
		}
	}
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
	}
	@Override
	public void startRow(Map<String, String> row) {
		
	}
	public String encryptPass(String str) {
		if (configurator.isPassEncryption()) {
			str = ZipUtils.encryptPass(str, configurator.getPassEncryKey());
		}
		return str;
	}
	public BaseRole getRoleIdByName(String roleName){
		return	super.getPersister().findUniqueEntity(BaseRole.class, "from BaseRole where roleName=?", roleName) ;
	}

	public void deleteByUserId(Long userId) {
		super.getPersister().deleteAll("from BaseUserRole where baseUser.userId=?", userId);
	}
}
