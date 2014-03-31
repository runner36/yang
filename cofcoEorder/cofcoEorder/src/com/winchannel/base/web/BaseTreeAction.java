package com.winchannel.base.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winchannel.base.model.BaseDictItem;
import com.winchannel.base.model.BaseEmployee;
import com.winchannel.base.model.BaseMenu;
import com.winchannel.base.model.BaseOrg;
import com.winchannel.base.model.BaseOrgLog;
import com.winchannel.base.model.BaseResource;
import com.winchannel.base.model.BaseRole;
import com.winchannel.base.model.BaseUser;
import com.winchannel.base.service.BaseDictManager;
import com.winchannel.base.service.BaseEmployeeManager;
import com.winchannel.base.service.BaseMenuManager;
import com.winchannel.base.service.BaseOrgManager;
import com.winchannel.base.service.BaseResourceManager;
import com.winchannel.base.service.BaseRoleManager;
import com.winchannel.base.service.BaseSecurityManager;
import com.winchannel.base.utils.Constants;
import com.winchannel.core.bean.ListColumn;
import com.winchannel.core.conf.ReportConfigurator;
import com.winchannel.core.utils.Page;
import com.winchannel.core.web.StrutsBaseAction;
import com.winchannel.mdm.util.i18n.BeanMessage;

/**
 * ͨ����
 * @author xianghui
 * 数组格式:�����ʽ��{parentId, id, name, params, action, icon, openIcon}
 * 
 */
public class BaseTreeAction extends StrutsBaseAction {

	public static final String TREE = "tree";
	
	private BaseOrgManager baseOrgManager;
	private BaseEmployeeManager baseEmployeeManager;
	private BaseDictManager baseDictManager;
	private BaseResourceManager baseResourceManager;
	private BaseRoleManager baseRoleManager;
	private BaseMenuManager baseMenuManager;
	private BaseSecurityManager baseSecurityManager;

	public void setBaseOrgManager(BaseOrgManager baseOrgManager) {
		this.baseOrgManager = baseOrgManager;
	}
	public void setBaseEmployeeManager(BaseEmployeeManager baseEmployeeManager) {
		this.baseEmployeeManager = baseEmployeeManager;
	}
	public void setBaseDictManager(BaseDictManager baseDictManager) {
		this.baseDictManager = baseDictManager;
	}
	public void setBaseResourceManager(BaseResourceManager baseResourceManager) {
		this.baseResourceManager = baseResourceManager;
	}
	public void setBaseRoleManager(BaseRoleManager baseRoleManager) {
		this.baseRoleManager = baseRoleManager;
	}
	public void setBaseMenuManager(BaseMenuManager baseMenuManager) {
		this.baseMenuManager = baseMenuManager;
	}
	public void setBaseSecurityManager(BaseSecurityManager baseSecurityManager) {
		this.baseSecurityManager = baseSecurityManager;
	}

	/**
	 * 组织树For E_order
	 */
	public ActionForward orgTreeForEorder(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		BaseUser userInfo = (BaseUser) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		String orgTreeType=request.getParameter("orgTreeType");
		StringBuffer strBuffer=new StringBuffer();
		List<String[]> tree = new ArrayList<String[]>();
		if(orgTreeType!=null&&orgTreeType.length()>0){
			if(userInfo.getBaseEmployee().getBaseDictItem().getItemCode().equals("SM")){
				//系统管理员
				strBuffer.append("SELECT org_id,parent_org_id,org_code,org_name,sub_code FROM BASE_ORG ORDER BY LEVEL_CODE,sort") ;
			}else if(orgTreeType.equals("OM")){
				String strTemp="SELECT DISTINCT org_id FROM MDM_DIST_ORDEREMP_PRODGROUP WHERE EMP_ID="+userInfo.getBaseEmployee().getEmpId();
				strBuffer.append("SELECT org_id,parent_org_id,org_code,org_name,sub_code FROM BASE_ORG WHERE ORG_ID IN(SELECT b.pi1 FROM (")
						.append("SELECT DISTINCT pi1 FROM BASE_ORG WHERE ORG_ID IN(SELECT org_id FROM BASE_ORG WHERE ORG_ID IN(").append(strTemp).append("))")
						.append(" UNION ")
						.append("SELECT DISTINCT pi2 FROM BASE_ORG WHERE ORG_ID IN(SELECT org_id FROM BASE_ORG WHERE ORG_ID IN(").append(strTemp).append("))")
						.append(" UNION ")
						.append("SELECT DISTINCT pi3 FROM BASE_ORG WHERE ORG_ID IN(SELECT org_id FROM BASE_ORG WHERE ORG_ID IN(").append(strTemp).append("))")
						.append(" UNION ")
						.append("SELECT DISTINCT pi4 FROM BASE_ORG WHERE ORG_ID IN(SELECT org_id FROM BASE_ORG WHERE ORG_ID IN(").append(strTemp).append("))")
						.append(" UNION ")
						.append("SELECT DISTINCT pi5 FROM BASE_ORG WHERE ORG_ID IN(SELECT org_id FROM BASE_ORG WHERE ORG_ID IN(").append(strTemp).append("))")
						.append(" UNION ")
						.append("SELECT DISTINCT pi6 FROM BASE_ORG WHERE ORG_ID IN(SELECT org_id FROM BASE_ORG WHERE ORG_ID IN(").append(strTemp).append("))")
						.append(" UNION ")
						.append("SELECT DISTINCT pi7 FROM BASE_ORG WHERE ORG_ID IN(SELECT org_id FROM BASE_ORG WHERE ORG_ID IN(").append(strTemp).append("))")
						.append(" UNION ")
						.append("SELECT DISTINCT pi8 FROM BASE_ORG WHERE ORG_ID IN(SELECT org_id FROM BASE_ORG WHERE ORG_ID IN(").append(strTemp).append("))")
						.append(") as b) ORDER BY LEVEL_CODE,sort");				
			}else if(orgTreeType.equals("SR")){
				//业代
				String strTemp="SELECT distinct dist.ORG_ID FROM MDM_DISTRIBUTOR dist INNER JOIN MDM_DIST_EMP_PRODGROUP de ON dist.DIST_ID=de.DIST_ID WHERE de.EMP_ID="+userInfo.getBaseEmployee().getEmpId();
				strBuffer.append("SELECT org_id,parent_org_id,org_code,org_name,sub_code FROM BASE_ORG WHERE ORG_ID IN(SELECT b.pi1 FROM (")
						.append("SELECT DISTINCT pi1 FROM BASE_ORG WHERE ORG_ID IN(SELECT org_id FROM BASE_ORG WHERE ORG_ID IN(").append(strTemp).append("))")
						.append(" UNION ")
						.append("SELECT DISTINCT pi2 FROM BASE_ORG WHERE ORG_ID IN(SELECT org_id FROM BASE_ORG WHERE ORG_ID IN(").append(strTemp).append("))")
						.append(" UNION ")
						.append("SELECT DISTINCT pi3 FROM BASE_ORG WHERE ORG_ID IN(SELECT org_id FROM BASE_ORG WHERE ORG_ID IN(").append(strTemp).append("))")
						.append(" UNION ")
						.append("SELECT DISTINCT pi4 FROM BASE_ORG WHERE ORG_ID IN(SELECT org_id FROM BASE_ORG WHERE ORG_ID IN(").append(strTemp).append("))")
						.append(" UNION ")
						.append("SELECT DISTINCT pi5 FROM BASE_ORG WHERE ORG_ID IN(SELECT org_id FROM BASE_ORG WHERE ORG_ID IN(").append(strTemp).append("))")
						.append(" UNION ")
						.append("SELECT DISTINCT pi6 FROM BASE_ORG WHERE ORG_ID IN(SELECT org_id FROM BASE_ORG WHERE ORG_ID IN(").append(strTemp).append("))")
						.append(" UNION ")
						.append("SELECT DISTINCT pi7 FROM BASE_ORG WHERE ORG_ID IN(SELECT org_id FROM BASE_ORG WHERE ORG_ID IN(").append(strTemp).append("))")
						.append(" UNION ")
						.append("SELECT DISTINCT pi8 FROM BASE_ORG WHERE ORG_ID IN(SELECT org_id FROM BASE_ORG WHERE ORG_ID IN(").append(strTemp).append("))")
						.append(") as b) ORDER BY LEVEL_CODE,sort");							
			}else if(orgTreeType.equals("DP")){
				//区域管理人员
				strBuffer.append("SELECT org_id,parent_org_id,org_code,org_name,sub_code FROM BASE_ORG where sub_code like'").append(userInfo.getBaseEmployee().getBaseOrg().getSubCode()).append("%' ORDER BY LEVEL_CODE,sort") ;
			}
			
			List<Object[]> lstOrg = this.baseOrgManager.executeSqlQuery(strBuffer.toString());		
			for (int i = 0; i < lstOrg.size(); i++) {
				Object[] obj = (Object[]) lstOrg.get(i);
				String parentId =obj[1]==null?"":obj[1].toString();
				String id = obj[0].toString();
				String params = "id=" + id + "&subCode=" + obj[4];
				String[] node = new String[] {parentId, id,(String)obj[3], params, "", "", ""};
				tree.add(node);
			}
		}
		
		request.setAttribute("tree", tree);
		return mapping.findForward(TREE);
	}
	
	/**
	 * 组织树��֯��
	 */
	public ActionForward orgTree(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		Page page = new Page(request, "orgTree");
		page.setPaging(false);
		page.put("$eq_state", request.getParameter("state"));
		page.put(Page.SORT, "levelCode,sort");
		List<BaseOrg> orgs = baseOrgManager.query(page);
		
		List<String[]> tree = new ArrayList<String[]>();
		for (BaseOrg org : orgs) {
			String parentId = org.getBaseOrg() == null ? "" : org.getBaseOrg().getOrgId().toString();
			String id = org.getOrgId().toString();
			String params = "id=" + id + "&subCode=" + org.getSubCode();
			String[] node = new String[] {parentId, id, org.getOrgName(), params, "", "", ""};
			tree.add(node);
		}
		request.setAttribute("tree", tree);
		return mapping.findForward(TREE);
	}

	/**
	 * 组织树��֯��
	 */
	public ActionForward hisOrgTree(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		Page page = new Page(request, "hisOrgTree");
		page.setPaging(false);
		
		String date = request.getParameter("date");
		page.put("$le_startDate", date);
		page.put("$ge_endDate", date);
		page.put(Page.SORT, "levelCode,sort");
		
		List<BaseOrgLog> orgs = baseOrgManager.query(BaseOrgLog.class, page);
		
		List<String[]> tree = new ArrayList<String[]>();
		for (BaseOrgLog org : orgs) {
			String parentId = org.getBaseOrg() == null ? "" : org.getBaseOrg().getOrgId().toString();
			String id = org.getOrgId().toString();
			String params = "id=" + id + "&subCode=" + org.getSubCode();
			String[] node = new String[] {parentId, id, org.getOrgName(), params, "", "", ""};
			tree.add(node);
		}
		request.setAttribute("tree", tree);
		return mapping.findForward(TREE);
	}

	/**
	 * 人员树��֯��
	 */
	public ActionForward empTree(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		Page page = new Page(request, "empTree");
		page.setPaging(false);
		page.put("$eq_state", request.getParameter("state"));
		page.put("$eq_baseDictItem.itemCode", request.getParameter("baseDictItem.itemCode"));
		page.put(Page.SORT, "levelCode,empName");
		List<BaseEmployee> emps = baseEmployeeManager.query(page);
		
		List<String[]> tree = new ArrayList<String[]>();
		for (BaseEmployee emp : emps) {
			String parentId = emp.getBaseEmployee() == null ? "" : emp.getBaseEmployee().getEmpId().toString();
			String id = emp.getEmpId().toString();
			String params = "id=" + id + "&subCode=" + emp.getSubCode() + "&empCode=" + emp.getEmpCode();
			String[] node = new String[] {parentId, id, emp.getEmpName()+ "(" + emp.getEmpCode() + ")", params, "", "", ""};
			tree.add(node);
		}
		request.setAttribute("tree", tree);
		return mapping.findForward(TREE);
	}
	
	/**
	 * 人员组织树��֯��
	 */
	public ActionForward empOrgTree(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		Page page = new Page(request, "empTree");
		page.setPaging(false);
		page.put("$eq_state", request.getParameter("state"));
		page.put(Page.SORT, "levelCode,sort");
		List<BaseEmployee> emps = baseEmployeeManager.query(page);
		
		List<String[]> tree = new ArrayList<String[]>();
		for (BaseEmployee emp : emps) {
			String parentId = emp.getBaseEmployee() == null ? "" : emp.getBaseEmployee().getEmpId().toString();
			String id = emp.getEmpId().toString();
			String params = "id=" + id + "&subCode=" + emp.getSubCode();
			String[] node = new String[] {parentId, id, (emp.getBaseOrg()==null?"":emp.getBaseOrg().getOrgName() + "－") + (emp.getBaseDictItem()==null?"":emp.getBaseDictItem().getItemName() + "－") + emp.getEmpName(), params, "", "", ""};
			tree.add(node);
		}
		request.setAttribute("tree", tree);
		return mapping.findForward(TREE);
	}
	
	/**
	 * 字典树��֯��
	 */
	public ActionForward dictTree(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		Page page = new Page(request, "dictItemTree");
		page.setPaging(false);
		page.put("$eq_state", request.getParameter("state"));
		page.put("$eq_baseDict_dictId", request.getParameter("dictId"));
		page.put("$le_levelCode", request.getParameter("levelCode"));
		page.put(Page.SORT, "levelCode,itemCode,sort");
		List<BaseDictItem> items = baseDictManager.query(page);
		
		List<String[]> tree = new ArrayList<String[]>();
		for (BaseDictItem item : items) {
			String parentId = item.getBaseDictItem() == null ? "" : item.getBaseDictItem().getDictItemId().toString();
			String id = item.getDictItemId().toString();
			String params = "first=1&id=" + id + "&subCode=" + item.getSubCode() + "&$eq_baseDictItem_dictItemId=" + id + "&dictId=" + item.getBaseDict().getDictId() + "&levelCode=" + item.getLevelCode();
			String[] node = new String[] {parentId, id, item.getItemName(), params, "", "", ""};
			tree.add(node);
		}
		request.setAttribute("tree", tree);
		return mapping.findForward(TREE);
	}

	/**
	 * 资源树��֯��
	 */
	public ActionForward resTree(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		Collection<BaseDictItem> items = baseDictManager.getItems(Constants.DICT_RESOURCE, request.getParameter("state"));
		List<String[]> tree = new ArrayList<String[]>();
		for (BaseDictItem item : items) {
			String parentId = item.getBaseDictItem() == null ? "" : "a" + item.getBaseDictItem().getDictItemId().toString();
			String id = "a" + item.getDictItemId().toString();
			String params = "id=" + id;
			String[] node = new String[] {parentId, id, item.getItemName(), params, "", "", ""};
			tree.add(node);
		}
		
		List<BaseResource> resources = baseResourceManager.getAll("sort");
		for (BaseResource res : resources) {
			String parentId = "a" + res.getBaseDictItem().getDictItemId().toString();
			String id = res.getResId().toString();
			String params = "leaf=1&id=" + id;
			String[] node = new String[] {parentId, id, res.getResName(), params, "", "", ""};
			tree.add(node);
		}
		request.setAttribute("tree", tree);
		return mapping.findForward(TREE);
	}

	/**
	 * 角色树��֯��
	 */
	public ActionForward roleTree(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		List<BaseRole> roles = baseRoleManager.getAll();
		List<String[]> tree = new ArrayList<String[]>();
		for (BaseRole role : roles) {
			String parentId = "";
			String id = role.getRoleId().toString();
			String params = "id=" + id;
			String[] node = new String[] {parentId, id, role.getRoleName(), params, "", "", ""};
			tree.add(node);
		}
		request.setAttribute("tree", tree);
		return mapping.findForward(TREE);
	}
	
	/**
	 * 菜单树��֯��
	 */
	public ActionForward menuTree(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		List<BaseMenu> menus = baseMenuManager.getAll("levelCode,sort");
		List<String[]> tree = new ArrayList<String[]>();
		for (BaseMenu menu : menus) {
			String parentId = menu.getBaseMenu() == null ? "" : menu.getBaseMenu().getMenuId().toString();
			String id = menu.getMenuId().toString();
			String params = "id=" + id;
			String[] node = new String[] {parentId, id, menu.getMenuName(), params, "", "", ""};
			tree.add(node);
		}
		request.setAttribute("tree", tree);
		return mapping.findForward(TREE);
	}

	/**
	 * 授权的普通菜单��֯��
	 */
	public ActionForward authMenu(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		Long userId = ((BaseUser) request.getSession().getAttribute("_user")).getUserId();
		request.setAttribute("menus", baseSecurityManager.getAuthMenuArray(request.getParameter("parentMenuId"), userId,request.getLocale().toString()));
		return new ActionForward("/commons/menu.jsp");
	}
	
	public ActionForward columnTree(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ReportConfigurator configurator = ReportConfigurator.getInstance(request.getParameter("reportName"));
		List<String[]> tree = new ArrayList<String[]>();
		tree.add(new String[] {null, "colroot", BeanMessage.getLocaleMessage("i18n/messages", "common.all", request), "id=colroot", "", "", ""});
		
		for (ListColumn col : configurator.getColumns()) {
			String params = "id=" + col.getProperty();
			String[] node = new String[] {"colroot", col.getProperty(), col.getTitle(), params, "", "", ""};
			tree.add(node);
		}
		request.setAttribute("tree", tree);
		return mapping.findForward(TREE);
	}
	
}
