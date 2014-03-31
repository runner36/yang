package com.winchannel.core.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForward;
import org.extremecomponents.table.core.PreferencesConstants;

import com.winchannel.base.model.BaseDataAuth;
import com.winchannel.base.model.BaseUser;
import com.winchannel.base.utils.Constants;

/**
 * 
 * @author xianghui
 * 
 */
@SuppressWarnings({"serial", "unchecked"})
public class Page extends HashMap<String, String> {

	public static String DEFAULT_PAGENAME = "ec";
	
	public static String DEFAULT_PAGESIZE = "10";
	
	public static String DEFAULT_EXPORTSIZE = "60000";
	
	static {
		Properties props = new Properties();
		try {
			InputStream ips = new BufferedInputStream(new FileInputStream(Page.class.getResource("/config/extremetable.properties").getFile()));
			props.load(ips);
			DEFAULT_PAGESIZE = props.getProperty(PreferencesConstants.TABLE_ROWS_DISPLAYED);
			if (StringUtils.isNotBlank(props.getProperty("table.rowsExported"))) {
				DEFAULT_EXPORTSIZE = props.getProperty("table.rowsExported");
			}
			ips.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String DEFAULT_PAGE = "1";

	public static String PAGE_NO = "pageNo";

	public static String PAGE_SIZE = "pageSize";

	public static String TOTAL_COUNT = "totalCount";

	public static String TOTAL_PAGE_COUNT = "totalPageCount";

	public static String HAS_NEXT_PAGE = "hasNextPage";

	public static String NEXT_PAGE = "nextPage";

	public static String HAS_PRE_PAGE = "hasPrePage";

	public static String PRE_PAGE = "prePage";

	public static String SORT = "sort";

	public static String DEFAULT_SORT = "defaultSort";
	
	public static String USER_ID_KEY = "_userId";
	public static String EMP_ID_KEY = "_empId";
	public static String EMP_SUBCODE_KEY = "_empSubCode";
	public static String EMP_LEVELCODE_KEY = "_empLevelCode";
	public static String PARENT_EMP_ID_KEY = "_parentEmpId";
	public static String PARENT_EMP_SUBCODE_KEY = "_parentEmpSubCode";
	public static String PARENT_EMP_lEVELCODE_KEY = "_parentEmpLevelCode";
	public static String ORG_ID_KEY = "_orgId";
	public static String ORG_SUBCODE_KEY = "_orgSubCode";
	public static String ORG_LEVELCODE_KEY = "_orgLevelCode";
	public static String PARENT_ORG_ID_KEY = "_parentOrgId";
	public static String PARENT_ORG_SUBCODE_KEY = "_parentOrgSubCode";
	public static String PARENT_ORG_LEVELCODE_KEY = "_parentOrgLevelCode";
	
	public static String EMP_AUTH_KEY = "_empAuth";
	public static String EMP_CASCADE_AUTH_KEY = "_empCascadeAuth";
	public static String ORG_AUTH_KEY = "_orgAuth";
	public static String PROD_AUTH_KEY = "_prodAuth";
	public static String DATA_AUTH = "_dataAuth";
	public static String DATA_AUTH_LEVEL = "_dataAuthLevel";
	
	public static String AUTH_PREFIX = "_authPrefix";

	protected String pageName;
	protected boolean paging = true;

	public Page() {
		this.pageName = DEFAULT_PAGENAME;
		initParams();
	}

	public Page(HttpServletRequest request) {
		this(request, DEFAULT_PAGENAME);
	}

	public Page(HttpServletRequest request, String pageName) {
		this.pageName = pageName;
		initParams(request);
		initParams();
	}

	protected void initParams(HttpServletRequest request) {
		if ("1".equals(request.getParameter("first"))) {
			request.getSession().removeAttribute(pageName);
		}

		if (request.getSession().getAttribute(pageName) != null) {
			this.putAll((Map<String, String>) request.getSession().getAttribute(pageName));
		}
		else {
			BaseUser user = (BaseUser) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
			this.put(USER_ID_KEY, user.getUserId().toString());
			if (user.getBaseEmployee() != null) {
				this.put(EMP_ID_KEY, user.getBaseEmployee().getEmpId().toString());
				this.put(EMP_SUBCODE_KEY, user.getBaseEmployee().getSubCode());
				this.put(EMP_LEVELCODE_KEY, user.getBaseEmployee().getLevelCode()==null?null:user.getBaseEmployee().getLevelCode().toString());
				/*if (user.getBaseEmployee().getBaseEmployee() != null) {
					this.put(PARENT_EMP_ID_KEY, user.getBaseEmployee().getBaseEmployee().getEmpId().toString());
					this.put(PARENT_EMP_SUBCODE_KEY, user.getBaseEmployee().getBaseEmployee().getSubCode());
					this.put(PARENT_EMP_lEVELCODE_KEY, user.getBaseEmployee().getBaseEmployee().getLevelCode().toString());
				}
				if (user.getBaseEmployee().getBaseOrg() != null) {
					this.put(ORG_ID_KEY, user.getBaseEmployee().getBaseOrg().getOrgId().toString());
					this.put(ORG_SUBCODE_KEY, user.getBaseEmployee().getBaseOrg().getSubCode());
					this.put(ORG_LEVELCODE_KEY, user.getBaseEmployee().getBaseOrg().getLevelCode().toString());
					if (user.getBaseEmployee().getBaseOrg().getBaseOrg() != null) {
						this.put(PARENT_ORG_ID_KEY, user.getBaseEmployee().getBaseOrg().getBaseOrg().getOrgId().toString());
						this.put(PARENT_ORG_SUBCODE_KEY, user.getBaseEmployee().getBaseOrg().getBaseOrg().getSubCode());
						this.put(PARENT_ORG_LEVELCODE_KEY, user.getBaseEmployee().getBaseOrg().getBaseOrg().getLevelCode().toString());
					}
				}*/
			}
			
			this.put(ORG_ID_KEY, request.getSession().getAttribute(Constants.SESSION_ORG_ID_KEY).toString());
			this.put(ORG_SUBCODE_KEY, request.getSession().getAttribute(Constants.SESSION_ORG_SUBCODE_KEY).toString());

			BaseDataAuth dataAuth = (BaseDataAuth) request.getSession().getAttribute(Constants.SESSION_DATA_AUTH_KEY);
			this.put(DATA_AUTH, dataAuth.getDataAuthExp());
			this.put(DATA_AUTH_LEVEL, dataAuth.getDataAuthLevel().toString());
			this.put(EMP_AUTH_KEY, user.getEmpAuth());
			this.put(EMP_CASCADE_AUTH_KEY, user.getEmpCascadeAuth());
			this.put(ORG_AUTH_KEY, user.getOrgAuth());
			this.put(PROD_AUTH_KEY, user.getProdAuth());
		}

		Enumeration names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement().toString();
			this.put(name, request.getParameter(name).replaceAll("'", "''"));
		}
		request.getSession().setAttribute(pageName, this);
//		ActionContext.getContext().getValueStack().push(this);
	}

	protected void initParams() {
		this.put(PAGE_NO, DEFAULT_PAGE);
		this.put(PAGE_SIZE, DEFAULT_PAGESIZE);
	}
	
	public void compute() {
		int totalCount = this.getInt(TOTAL_COUNT);
		int pageSize = this.getInt(PAGE_SIZE);
		this.put(TOTAL_PAGE_COUNT, totalCount % pageSize == 0 ? String.valueOf(totalCount / pageSize) : String.valueOf(totalCount / pageSize + 1));
	}
	
	public int getInt(String name) {
		return this.get(name) != null ? Integer.parseInt(this.get(name)) : 0;
	}

	public long getLong(String name) {
		return this.get(name) != null ? Long.parseLong(this.get(name)) : 0;
	}
	
	public ActionForward initForward(ActionForward forward) {
    	return forward;
    }

	public static void setValue(HttpServletRequest request, String pageName, String name, String value) {
		Map<String, String> page = (Map<String, String>) request.getSession().getAttribute(pageName);
		if (page != null) {
			page.put(name, value);
		}
	}

	public static void setValue(HttpServletRequest request, String name, String value) {
		setValue(request, DEFAULT_PAGENAME, name, value);
	}

	public static String getValue(HttpServletRequest request, String pageName, String name) {
		Map<String, String> page = (Map<String, String>) request.getSession().getAttribute(pageName);
		if (page != null) {
			return page.get(name);
		}
		return null;
	}

	public static String getValue(HttpServletRequest request, String name) {
		return getValue(request, DEFAULT_PAGENAME, name);
	}

	public boolean isPaging() {
		return paging;
	}

	public void setPaging(boolean paging) {
		this.paging = paging;
	}


}
