package com.winchannel.base.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.winchannel.base.bean.UserInfo;
import com.winchannel.base.conf.BaseConfigurator;
import com.winchannel.base.model.BaseDataAuth;
import com.winchannel.base.model.BaseMenu;
import com.winchannel.base.model.BaseMenuI18n;
import com.winchannel.base.model.BaseOrg;
import com.winchannel.base.model.BasePasswordLog;
import com.winchannel.base.model.BaseRole;
import com.winchannel.base.model.BaseUser;
import com.winchannel.core.dao.HibernateBaseDao;
import com.winchannel.core.exception.SafetyException;
import com.winchannel.core.exception.SafetyRuntimeException;
import com.winchannel.core.utils.DateUtils;
import com.winchannel.core.utils.StringUtils;
import com.winchannel.core.utils.ZipUtils;

/**
 * @author xianghui
 *
 */
public class BaseSecurityManager extends HibernateBaseDao {
	
	private BaseConfigurator configurator = BaseConfigurator.getInstance();
	
	/**
	 * 返回授权的下级菜单
	 * @param parentId
	 * @return
	 */
	public List<BaseMenu> getAuthSubMenus(Long parentMenuId, Long userId,String localeString) {
		String queryString = 
			"select auth.baseResource.resId " +
			"from BaseAuth auth where auth.baseResource.state='1' and auth.baseRole.roleId in " +
			"	(select ur.baseRole.roleId from BaseUserRole ur where ur.baseUser.userId=" + userId + ")";
		List resources = this.find(queryString);
		String resIds = "";
		for (int i = 0; i < resources.size(); i++) {
			String resId = String.valueOf(resources.get(i));
			if (!resIds.equals("")) {
				resIds += ",";
			}
			resIds += resId;
		}

		List<BaseMenu> result = new ArrayList<BaseMenu>();
		if (!resIds.equals("")) {
			queryString = "from BaseMenu where state='1' and " + (parentMenuId == null ? "baseMenu is null" : "baseMenu.menuId=" + parentMenuId) + " order by levelCode,sort";
			List<BaseMenu> menus = this.findEntity(BaseMenu.class, queryString);
			for (BaseMenu menu : menus) {
				Long n = this.findLong("select count(*) from BaseMenu where subCode like '" + menu.getSubCode() + "%' and baseResource.resId in (" + resIds + ")");
				if (n != null && n > 0) {
					//增加根据locatString取得国际化名称
					String menuI18nName=getMenuI18n(localeString, menu.getMenuId());
					if(menuI18nName!=null && menuI18nName.length()>0)
						menu.setMenuName(menuI18nName);
					result.add(menu);
				}
			}
		}
		return result;

		/*String queryString = 
			"from BaseMenu m " +
			"where " + (parentMenuId == null ? "m.baseMenu is null" : "m.baseMenu.menuId=" + parentMenuId) + " and " +
			"	exists " +
			"	(select m1.menuId from BaseMenu m1 where m1.subCode like m.subCode||'%' and m1.baseResource.resId in " +
			"		(" +
			"		select auth.baseResource.resId from BaseAuth auth where auth.baseRole.roleId in " +
			"			(" +
			"			select ur.baseRole.roleId from BaseUserRole ur where ur.baseUser.userId=" + userId +
			"			)" +
			"		)" +
			"	)" +
			"order by m.levelCode,m.sort";
		
		return this.findEntity(BaseMenu.class, queryString);*/
	}

	private String getMenuI18n(String localeString,long menuId) {
		BaseMenuI18n baseMenuI18n=this.findUniqueEntity(BaseMenuI18n.class, "from BaseMenuI18n where baseMenu.menuId=? and menuType.itemCode=?", menuId,localeString);
		if(baseMenuI18n!=null && baseMenuI18n.getMenuName()!=null && baseMenuI18n.getMenuName().length()>0)
		 return baseMenuI18n.getMenuName();
		return null;
	}
	
	/**
	 * 返回授权的二三级菜单数组
	 * @param parentId
	 * @return
	 */
	public List<Object[]> getAuthMenuArray(String parentMenuId, Long userId,String localeString) {
		List<BaseMenu> twoMenuList = getAuthSubMenus(new Long(parentMenuId), userId,localeString);
		List<Object[]> menus = new ArrayList<Object[]>();
		for (BaseMenu twoMenu : twoMenuList) {
			
			Object[] twoMenuArray = new Object[2];
			//增加取得MENUI18N
			String twoMenuI18nName=getMenuI18n(localeString, twoMenu.getMenuId());
			if(twoMenuI18nName!=null && twoMenuI18nName.length()>0)
				twoMenu.setMenuName(twoMenuI18nName);
			
			twoMenuArray[0] = twoMenu.getMenuName();
			
			List<BaseMenu> threeMenus = getAuthSubMenus(twoMenu.getMenuId(), userId,localeString);
			List<Object[]> subMenus = new ArrayList<Object[]>();
			for (BaseMenu threeMenu : threeMenus) {
				Object[] threeMenuArray = new Object[2];
				//增加取得MENUI18N
				String threeMenuI18nName=getMenuI18n(localeString, threeMenu.getMenuId());
				if(threeMenuI18nName!=null && threeMenuI18nName.length()>0)
					threeMenu.setMenuName(threeMenuI18nName);
				threeMenuArray[0] = threeMenu.getMenuName();
				
				String url = "javascript:void(0)";
				if (threeMenu.getBaseResource() != null) {
					url = threeMenu.getBaseResource().getResUri();
					if (StringUtils.isNotBlank(threeMenu.getResParams())) {
						String s = url.indexOf("?") == -1 ? "?" : "&";
						url += s + threeMenu.getResParams();
					}
				}
				
				threeMenuArray[1] = url;
				subMenus.add(threeMenuArray);
			}
			
			twoMenuArray[1] = subMenus;
			
			menus.add(twoMenuArray);
		}
		
		return menus;
	}
	
	/**
	 * 返回一个存在的用户
	 * @param account
	 * @param password
	 * @return
	 */
	public BaseUser getValidUser(String account, String password, String userType, int loginNum) throws SafetyException {
		String hql = "from BaseUser where userAccount='" + account + "'";
		if (StringUtils.isNotBlank(userType)) {
			hql += " and userType like '" + userType + "%'";
		}
		BaseUser user = this.findUniqueEntity(BaseUser.class, hql);
		if (user == null) {
			//SafetyException e = new SafetyException("用户名或密码错误");
			SafetyException e = new SafetyException("base","common.usnameOrPassErr");
			e.setExCode(SafetyException.EXCODE_USER_PASSWORD_ERROR);
			throw e;
		}
		else if (!user.getUserPassword().equals(this.encryptPass(password))) {
			if (configurator.getLoginFailureNum() == loginNum) {
				user.setState("0");
				this.save(user);
				SafetyException e = new SafetyException("用户名或密码错误，登陆失败超过" + configurator.getLoginFailureNum() + "次，用户已被锁定");
				e.setExCode(SafetyException.EXCODE_LOGIN_NUM_LOCKED);
				throw e;
			}
			else {
				SafetyException e = new SafetyException("base","common.usnameOrPassErr");
				e.setExCode(SafetyException.EXCODE_USER_PASSWORD_ERROR);
				throw e;
			}
		}
		return user;
	}
	
	/**
	 * 验证用户有效性
	 * @param user
	 * @return
	 */
	public void verifyUser(BaseUser user) throws SafetyException {
		if (!"1".equals(user.getState())) {
			//SafetyException e = new SafetyException("用户已被锁定");
			SafetyException e = new SafetyException("base","common.userLock");
			e.setExCode(SafetyException.EXCODE_USER_LOCKED);
			throw e;
		}
	}
	
	/**
	 * 验证密码有效性
	 * @param user
	 * @return
	 */
	public void verifyPass(BaseUser user) throws SafetyException {
		if (user.getLastPassDate() != null && user.getLastPassDate().getTime() < System.currentTimeMillis()) {
			//SafetyException e = new SafetyException("密码已过期，请修改密码");
			SafetyException e = new SafetyException("base","common.passExpired");
			e.setExCode(SafetyException.EXCODE_PASSWORD_EXPIRED);
			throw e;
		}
		if (configurator.isPassFirstChange() && user.getLoginCount() == null) {
			//SafetyException e = new SafetyException("首次登陆，请修改密码");
			SafetyException e = new SafetyException("base","common.firstLoginChangePass");
			e.setExCode(SafetyException.EXCODE_FIRST_LOGIN);
			throw e;
		}
	}
	
	/**
	 * 返回用户所拥有的所有有效角色
	 * @param userId
	 * @return
	 */
	public List<BaseRole> getRoleByUserId(Long userId) {
		return this.findEntity(BaseRole.class, "select ur.baseRole from BaseUserRole ur where ur.baseUser.userId=? and ur.baseRole.state='1'", userId);
	}
	
	/**
	 * 返回用户所拥有的角色下的所有有效权限CODE
	 * @return
	 */
	public Map<String, String> getResCodeByRoleIds(String roleIds) {
		Map<String, String> resMap = new HashMap<String, String>();
		if (StringUtils.isNotBlank(roleIds)) {
			String queryString = "select auth.baseResource.resCode from BaseAuth auth where auth.baseRole.roleId in (" + roleIds + ")";
			List resCodes = this.find(queryString);
			for (int i = 0; i < resCodes.size(); i++) {
				resMap.put((String)resCodes.get(i), "");
			}
		}
		return resMap;
	}
	
	/**
	 * 返回所有资源
	 * @return
	 */
	public Map<String, String[]> getAllResourecs() {
		Map<String, String[]> resMap = new HashMap<String, String[]>();
		String queryString = "select resCode,resName,resUri from BaseResource where state='1'";
		Iterator iter = this.find(queryString).iterator();
		while (iter.hasNext()) {
			Object[] obj = (Object[]) iter.next();
			resMap.put(obj[2] + "", new String[]{obj[0] + "", obj[1] + ""});
		}
		return resMap;
	}
	
	/**
	 * 用户登陆
	 * @param userAccount
	 * @param userPassword
	 * @param newPassword
	 */
	public UserInfo login(String userAccount, String userPassword, String userType, int loginNum) throws SafetyException {
		BaseUser user = getValidUser(userAccount, userPassword, userType, loginNum);
		verifyUser(user);
		verifyPass(user);
				
		String roleIds = "";
		String roleNames = "";
		BaseDataAuth dataAuth = null;
		List<BaseRole> roles = getRoleByUserId(user.getUserId());
		if (roles.size() == 0) {
			//SafetyException e = new SafetyException("还没有为该用户指派角色");
			SafetyException e = new SafetyException("base","common.notAssignRoles");
			e.setExCode(SafetyException.EXCODE_NO_ROLE);
			throw e;
		}
		for (BaseRole role : roles) {
			if (role.getBaseDataAuth() != null) {
				if (dataAuth == null || dataAuth.getDataAuthLevel() > role.getBaseDataAuth().getDataAuthLevel()) {
					dataAuth = role.getBaseDataAuth();
				}
			}
			
			if (!roleIds.equals("")) {
				roleIds += ",";
				roleNames += ",";
			}
			roleIds += role.getRoleId();
			roleNames += role.getRoleName();
		}
		
		if (dataAuth != null) {
			dataAuth.getDataAuthExp();
		}

		String orgIds = "";
		String orgNames = "";
		String orgSubCodes = "";
		if (user.getBaseEmployee() != null) {
			user.getBaseEmployee().getEmpName();
			if (user.getBaseEmployee().getBaseDictItem() != null) {
				user.getBaseEmployee().getBaseDictItem().getItemName();
			}
			if (user.getBaseEmployee().getBaseEmployee() != null) {
				user.getBaseEmployee().getBaseEmployee().getEmpName();
			}
			
			if (user.getBaseEmployee().getBaseOrg() != null) {
				orgIds = user.getBaseEmployee().getBaseOrg().getOrgId().toString();
				orgNames = user.getBaseEmployee().getBaseOrg().getOrgName();
				orgSubCodes = user.getBaseEmployee().getBaseOrg().getSubCode();
				
				/*if (user.getBaseEmployee().getBaseOrg().getBaseOrg() != null) {
					user.getBaseEmployee().getBaseOrg().getBaseOrg().getOrgName();
				}*/
			}
			Set orgs = user.getBaseEmployee().getBaseOrgs();
			for (Iterator it = orgs.iterator(); it.hasNext();) {
				BaseOrg org = (BaseOrg) it.next();
				if (!orgIds.equals("")) {
					orgIds += ",";
					orgNames += ",";
					orgSubCodes += ",";
				}
				orgIds += org.getOrgId();
				orgNames += org.getOrgName();
				orgSubCodes += org.getSubCode();
			}
			
			
		}
		
		if (StringUtils.isNotBlank(user.getEmpCascadeAuth())) {
			String empCascadeAuth = "";
			Iterator it = this.find("select subCode from BaseEmployee where empId in (" + user.getEmpCascadeAuth() + ")").iterator();
			if (it.hasNext()) {
				empCascadeAuth = (String) it.next();
			}
			while (it.hasNext()) {
				empCascadeAuth += "," + it.next();
			}
			this.evict(user);
			user.setEmpCascadeAuth(empCascadeAuth);
		}
		
		//记录登录次数 2011-08-30
		if(configurator.isRecordLoginCount()){
			user.setLastLogin(new Date());
			user.setLoginCount(user.getLoginCount() != null ? (user.getLoginCount() + 1) : 0);
			this.save(user);			
		}
		
		return new UserInfo(user, orgIds, orgNames, orgSubCodes, roleIds, roleNames, dataAuth);
	}
	
	/**
	 * 修改密码
	 * @param userAccount
	 * @param userPassword
	 * @param newPassword
	 * @throws SafetyException 
	 */
	public void changePass(String userAccount, String userPassword, String newPassword, String userType) throws SafetyException {
		BaseUser user = null;
		
		user = getValidUser(userAccount, userPassword, userType, 1);
		verifyUser(user);
		
		String oldPass = user.getUserPassword();
		setUserPass(user, newPassword);
		
		if (oldPass.equals(user.getUserPassword())) {
			SafetyRuntimeException e = new SafetyRuntimeException("base","common.passNotChange");
			e.setExCode(SafetyRuntimeException.EXCODE_PASSWORD_NO_CHANGE);
			throw e;
		}
		
		if (user.getLoginCount() == null) {
			user.setLoginCount(0L);
		}
		
		this.save(user);
	}
	
	public void setUserPass(BaseUser user, String str) {
		if (str.length() < configurator.getPassLen()) {
			SafetyRuntimeException e = new SafetyRuntimeException("密码长度不能小于" + configurator.getPassLen() + "个字符");
			e.setExCode(SafetyRuntimeException.EXCODE_PASSWORD_LENGTH);
			throw e;
		}
		int lowerNum = 0;
		int upperNum = 0;
		int digitNum = 0;
		int specialNum = 0;
		
		StringBuffer sb = new StringBuffer(str);
		for (int i = 0; i < sb.length(); i++) {
			String s = sb.substring(i, i+1);
			if (configurator.getPassLowerChars().indexOf(s) != -1) {
				lowerNum++;
			}
			else if (configurator.getPassUpperChars().indexOf(s) != -1) {
				upperNum++;
			}
			else if (configurator.getPassDigitChars().indexOf(s) != -1) {
				digitNum++;
			}
			else if (configurator.getPassSpecialChars().indexOf(s) != -1) {
				specialNum++;
			}
		}
		
		
		if (lowerNum < configurator.getPassLowerNum()) {
			SafetyRuntimeException e = new SafetyRuntimeException("密码必须包含至少" + configurator.getPassLowerNum() + "个小写字母");
			e.setExCode(SafetyRuntimeException.EXCODE_PASSWORD_LOWER);
			throw e;
		}
		if (upperNum < configurator.getPassUpperNum()) {
			SafetyRuntimeException e = new SafetyRuntimeException("密码必须包含至少" + configurator.getPassUpperNum() + "个大写字母");
			e.setExCode(SafetyRuntimeException.EXCODE_PASSWORD_UPPER);
			throw e;
		}
		if (digitNum < configurator.getPassDigitNum()) {
			SafetyRuntimeException e = new SafetyRuntimeException("密码必须包含至少" + configurator.getPassDigitNum() + "个数字字符");
			e.setExCode(SafetyRuntimeException.EXCODE_PASSWORD_DIGIT);
			throw e;
		}
		if (specialNum < configurator.getPassSpecialNum()) {
			SafetyRuntimeException e = new SafetyRuntimeException("密码必须包含至少" + configurator.getPassSpecialNum() + "个特殊字符");
			e.setExCode(SafetyRuntimeException.EXCODE_PASSWORD_SPECIAL);
			throw e;
		}

		
		// 前N次密码是否可用 2011-08-30
		if(configurator.isBeforeAvailable()){
			String sql = " select top "+configurator.getBeforeUserNum()+" USER_PASSWORD as userPassword from BASE_PASSWORD_LOG where USER_ACCOUNT='"+user.getUserAccount()+"' order by CREATED desc ";
			List<BasePasswordLog> pass = this.executeSqlQuery(BasePasswordLog.class, sql);
			boolean flag = false;
			if(pass.size() > 0){
				for(BasePasswordLog log : pass){
					if(encryptPass(str).equals(log.getUserPassword())){
						flag = true;
						break;
					}
				}
			}
			if(flag){
				SafetyRuntimeException e = new SafetyRuntimeException("不可以使用前" + configurator.getBeforeUserNum() + "次用过的密码");
				e.setExCode(SafetyRuntimeException.EXCODE_PASSWORD_SPECIAL);
				throw e;				
			}
			// 保存历史密码
			BasePasswordLog passLog = new BasePasswordLog();
			passLog.setUserAccount(user.getUserAccount());
			passLog.setUserPassword(encryptPass(str));
			passLog.setCreatedBy(user.getUserAccount());
			passLog.setCreated(new Date());
			this.save(passLog);
		}

		
		user.setUserPassword(encryptPass(str));
		
		if (configurator.getPassChangeCycle() > 0) {
			user.setLastPassDate(new Date(DateUtils.getDate().getTime() + (86400000L * configurator.getPassChangeCycle())));
		}
	}
	
	public void setEncryptPass(BaseUser user, String str) {
		if(configurator.isBeforeAvailable()){
			// 保存历史密码
			BasePasswordLog passLog = new BasePasswordLog();
			passLog.setUserAccount(user.getUserAccount());
			passLog.setUserPassword(encryptPass(str));
			passLog.setCreatedBy(user.getUserAccount());
			passLog.setCreated(new Date());
			this.save(passLog);
		}
		
		user.setUserPassword(encryptPass(str));
		
		if (configurator.getPassChangeCycle() > 0) {
			user.setLastPassDate(new Date(DateUtils.getDate().getTime() + (86400000L * configurator.getPassChangeCycle())));
		}
	}
	
	public String encryptPass(String str) {
		if (configurator.isPassEncryption()) {
			str = ZipUtils.encryptPass(str, configurator.getPassEncryKey());
		}
		return str;
	}
	
	public String resetPass(Long userId) {
		BaseUser user = this.get(BaseUser.class, userId);
		int len = configurator.getPassLen() == 0 ? 4 : configurator.getPassLen();
		String str = (Math.random() + "").substring(2, len + 2);
//		this.setUserPass(user, str);
		user.setUserPassword(this.encryptPass(str));
		this.save(user);
		return str;
	}
	
	public long getPassValidDays(BaseUser user) {
		if (user.getLastPassDate() != null) {
			return (user.getLastPassDate().getTime() - System.currentTimeMillis()) / 86400000L + 1;
		}
		return 0;
	}
}
