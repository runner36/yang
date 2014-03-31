package com.winchannel.mdm.base.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.winchannel.base.model.BaseDictItem;
import com.winchannel.base.model.BaseEmployee;
import com.winchannel.base.model.BaseOrg;
import com.winchannel.base.model.BaseUser;
import com.winchannel.base.service.BaseOrgManager;
import com.winchannel.base.utils.Constants;
import com.winchannel.core.utils.Page;
import com.winchannel.core.utils.StringUtils;
import com.winchannel.core.web.StrutsBaseAction;
import com.winchannel.mdm.distributor.model.MdmDistributor;
import com.winchannel.mdm.distributor.service.MdmDistributorManager;
import com.winchannel.mdm.location.service.MdmAfsLocationManager;
import com.winchannel.mdm.product.model.MdmProduct;
import com.winchannel.mdm.product.service.MdmProductCache;
import com.winchannel.mdm.product.service.MdmProductManager;
import com.winchannel.mdm.store.model.MdmStore;
import com.winchannel.mdm.store.service.MdmStoreManager;
import com.winchannel.order.model.MdmDistributorAddress;
import com.winchannel.order.service.MdmDistEmpProdgroupManager;
import com.winchannel.order.service.MdmDistributorAddressManager;

public class MdmTreeAction extends StrutsBaseAction {
	public static final String TREE = "tree";
	private MdmDistributorManager mdmDistributorManager;
	private MdmStoreManager mdmStoreManager;
	private MdmProductManager mdmProductManager;
	private BaseOrgManager baseOrgManager;
	private MdmAfsLocationManager mdmAfsLocationManager;
	private MdmDistributorAddressManager mdmDistributorAddressManager;
	private MdmDistEmpProdgroupManager mdmDistEmpProdgroupManager;

	public ActionForward distTreeForOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		BaseUser userInfo = (BaseUser) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		String distTreeType=request.getParameter("distTreeType");
		String strSql="";
		List<String[]> tree = new ArrayList<String[]>();
		if(distTreeType!=null&&distTreeType.length()>0){
			if(userInfo.getBaseEmployee().getBaseDictItem().getItemCode().equals("SM")){
				//系统管理员
				strSql="select dist.DIST_ID,dist.DIST_CODE,dist.DIST_NAME from MDM_DISTRIBUTOR dist order by dist.dist_id" ;
			}else if(distTreeType.equals("OM")){
				//订单员
				strSql="select dist.DIST_ID,dist.DIST_CODE,dist.DIST_NAME from MDM_DISTRIBUTOR dist " +
						"where dist.ORG_ID in(select distinct ORG_ID from MDM_DIST_ORDEREMP_PRODGROUP where EMP_ID="+userInfo.getBaseEmployee().getEmpId()+" ) order by dist.dist_id";
			}else if(distTreeType.equals("SR")){
				//业代
				strSql="select dist.DIST_ID,dist.DIST_CODE,dist.DIST_NAME from MDM_DISTRIBUTOR dist " +
						"where dist.DIST_ID in(select distinct DIST_ID from MDM_DIST_EMP_PRODGROUP where EMP_ID="+userInfo.getBaseEmployee().getEmpId()+") order by dist.dist_id";
			}else if(distTreeType.equals("DP")){
				//区域管理人员
				strSql="select dist.DIST_ID,dist.DIST_CODE,dist.DIST_NAME from MDM_DISTRIBUTOR dist " +
						"where dist.ORG_ID in(select ORG_ID from BASE_ORG where sub_code like '"+userInfo.getBaseEmployee().getBaseOrg().getSubCode()+"%' ) order by dist.dist_id";
			}
			
			List<Object[]> lstMdmDist = this.mdmDistributorManager.executeSqlQuery(strSql);
			for (int i = 0; i < lstMdmDist.size(); i++) {
				Object[] obj = (Object[]) lstMdmDist.get(i);
				String parentId = "";
				String id = obj[0].toString();
				String params = "id=" + id + "&distCode=" + obj[1];
				String[] node = new String[] { parentId, id, obj[2] + "(" + obj[1] + ")", params, "", "", "" };
				tree.add(node);
			}
		}
				
		request.setAttribute("tree", tree);
		return mapping.findForward("tree");
	}
	
	public ActionForward distTree(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		List<String[]> tree = new ArrayList<String[]>();

		Page page1 = new Page(request, "distriTree1");
		page1.setPaging(false);// 设置不分页
		page1.put("$eq_state", "1");
		page1.put(Page.SORT, "sort");
		if (StringUtils.isNotBlank(request.getParameter("orgId"))) {
			page1.put("$in_baseOrg_orgId", request.getParameter("orgId"));
		}
		List<MdmDistributor> dists = this.mdmDistributorManager.query(page1);

		for (MdmDistributor distributor : dists) {
			String parentId = "";
			String id = distributor.getDistId().toString();
			String params = "id=" + id + "&distCode=" + distributor.getDistCode();
			String[] node = new String[] { parentId, id, distributor.getDistName() + "(" + distributor.getDistCode() + ")", params, "", "", "" };
			tree.add(node);
		}
		request.setAttribute("tree", tree);
		return mapping.findForward("tree");
	}

	public ActionForward distOrgTree(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		Page page = new Page(request, "distriTree");
		page.setPaging(false);
		page.put("$eq_state", request.getParameter("state"));
		page.put(Page.SORT, "levelCode,sort");
		List orgs = baseOrgManager.query(page);
		List tree = new ArrayList();
		String node[];
		for (Iterator iterator = orgs.iterator(); iterator.hasNext(); tree.add(node)) {
			BaseOrg org = (BaseOrg) iterator.next();
			String parentId = org.getBaseOrg() != null ? (new StringBuilder("a")).append(org.getBaseOrg().getOrgId().toString()).toString() : "";
			String id = (new StringBuilder("a")).append(org.getOrgId().toString()).toString();
			String params = (new StringBuilder("id=")).append(id).toString();
			node = (new String[] { parentId, id, org.getOrgName(), params, "", "", "" });
		}

		Page page1 = new Page(request, "distriTree1");
		page1.setPaging(false);
		page1.put("$eq_state", "1");
		page1.put(Page.SORT, "sort");
		List dists = mdmDistributorManager.query(page1);
		String node1[];
		for (Iterator iterator1 = dists.iterator(); iterator1.hasNext(); tree.add(node1)) {
			MdmDistributor distributor = (MdmDistributor) iterator1.next();
			String parentId = (new StringBuilder("a")).append(distributor.getBaseOrg().getOrgId().toString()).toString();
			String id = distributor.getDistId().toString();
			String params = (new StringBuilder("leaf=1&id=")).append(id).toString();
			node1 = (new String[] { parentId, id, distributor.getDistName(), params, "", "", "" });
		}

		request.setAttribute("tree", tree);
		return mapping.findForward("tree");
	}

	public ActionForward storeTree(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		Page page = new Page(request, "storeTree");
		page.setPaging(false);
		page.put("$eq_state", request.getParameter("state"));
		page.put(Page.SORT, "levelCode,sort");
		if (StringUtils.isNotBlank(request.getParameter("orgId"))) {
			page.put("$in_baseOrg_orgId", request.getParameter("orgId"));
		}
		if (StringUtils.isNotBlank(request.getParameter("storeTypeId"))) {
			page.put("$eq_storeType_dictItemCode", request.getParameter("storeTypeCode"));
		}
		List stores = this.mdmStoreManager.query(page);

		List tree = new ArrayList();
		for (int i = 0; i < stores.size(); i++) {
			MdmStore store = (MdmStore) stores.get(i);
			String parentId = (store.getMdmStore() == null) ? "" : store.getMdmStore().getStoreId().toString();
			String id = store.getStoreId().toString();

			String params = "id=" + id;
			String[] node = { parentId, id, store.getStoreName(), params, "", "", "" };
			tree.add(node);
		}
		request.setAttribute("tree", tree);
		return mapping.findForward("tree");
	}

	public ActionForward prodTree(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		Page page = new Page(request, "prodTree");
		page.setPaging(false);
		page.put("$eq_state", request.getParameter("state"));
		page.put(Page.SORT, "sort");
		// if(StringUtils.isNotBlank(request.getParameter("orgId"))){
		// page.put("$in_baseOrg_orgId", request.getParameter("orgId"));
		// }
		List tree = new ArrayList();
		List prods = this.mdmProductManager.query(page);
		List brands = this.mdmProductManager.findEntity(BaseDictItem.class, "from BaseDictItem where baseDict.dictId='prodBrand'");
		for (int i = 0; i < brands.size(); i++) {
			BaseDictItem item = (BaseDictItem) brands.get(i);
			String parentId = (item.getBaseDictItem() == null) ? "" : item.getBaseDictItem().getDictItemId().toString();
			String id = item.getDictItemId().toString();

			String params = "id=" + id;
			String[] node = { parentId, id, item.getItemName(), params, "", "", "" };
			tree.add(node);
		}

		for (int i = 0; i < prods.size(); i++) {
			MdmProduct prod = (MdmProduct) prods.get(i);
			if (prod.getItemBrand() != null) {
				String parentId = prod.getItemBrand().getDictItemId().toString();
				String id = prod.getProdId().toString();

				String params = "id=" + id + "&leaf=1";
				String[] node = { parentId, id, prod.getProdName(), params, "", "", "" };
				tree.add(node);
			}
		}
		request.setAttribute("tree", tree);
		return mapping.findForward("tree");
	}

	/**
	 * 送货地址树
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward distAddressTree(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		BaseUser userInfo = (BaseUser) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		Long empId = null;
		if (userInfo != null && userInfo.getBaseEmployee() != null) {
			empId = userInfo.getBaseEmployee().getEmpId();
		}

		MdmDistributor mdist = mdmDistributorManager.getMdmDistributorByEmployeeId(empId);

		String prodGroupId = request.getParameter("prodGroupId");

		List tree = new ArrayList();
		String node[];
		List<MdmDistributorAddress> list = mdmDistributorAddressManager.getMdmDistributorAddressByDistProdgroup(mdist.getDistId(), Long.valueOf(prodGroupId));
		if (list.size() > 0) {
			for (MdmDistributorAddress address : list) {
				String params = "id=" + address.getId() + "&shiptoCode=" + address.getShiptoCode() + "&shiptoAddr=" + address.getShiptoAdd() + "&shiptoContact=" + address.getContact() + "&shiptoTel="
						+ address.getTel() + "&shiptoMobile=" + address.getMobile();
				node = (new String[] { "", address.getId().toString(), address.getShiptoName() + "(" + address.getShiptoCode() + ")", params, "", "", "" });
				tree.add(node);
			}

		}

		request.setAttribute("tree", tree);
		return mapping.findForward("tree");
	}
	
	/**
	 * 送货地址树
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward distAddressTreeByDist(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		BaseUser userInfo = (BaseUser) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		Long empId = null;
		if (userInfo != null && userInfo.getBaseEmployee() != null) {
			empId = userInfo.getBaseEmployee().getEmpId();
		}

		MdmDistributor mdist = mdmDistributorManager.getMdmDistributorByEmployeeId(empId);

	 
		List tree = new ArrayList();
		String node[];
		List<MdmDistributorAddress> list = mdmDistributorAddressManager.findEntity(MdmDistributorAddress.class,"from MdmDistributorAddress where mdmDistributor=?" ,mdist);
		if (list.size() > 0) {
			for (MdmDistributorAddress address : list) {
				String params = "id=" + address.getId() + "&shiptoCode=" + address.getShiptoCode() + "&shiptoAddr=" + address.getShiptoAdd() + "&shiptoContact=" + address.getContact() + "&shiptoTel="
						+ address.getTel() + "&shiptoMobile=" + address.getMobile();
				node = (new String[] { "", address.getId().toString(), address.getShiptoName() + "(" + address.getShiptoCode() + ")", params, "", "", "" });
				tree.add(node);
			}

		}

		request.setAttribute("tree", tree);
		return mapping.findForward("tree");
	}
	/**
	 * 送货地址树 根据code踢重
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward distAddressTreeGroupCode(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		BaseUser userInfo = (BaseUser) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		Long empId = null;
		if (userInfo != null && userInfo.getBaseEmployee() != null) {
			empId = userInfo.getBaseEmployee().getEmpId();
		}
		MdmDistributor mdist = mdmDistributorManager.getMdmDistributorByEmployeeId(empId);
		String prodGroupId = request.getParameter("prodGroupId");
		List tree = new ArrayList();
		Map m = new HashMap();
		String node[];
		List<MdmDistributorAddress> list = mdmDistributorAddressManager.getMdmDistributorAddressByDistProdgroup(mdist.getDistId(), Long.valueOf(prodGroupId));
		if (list.size() > 0) {
			for (MdmDistributorAddress address : list) {

				if (m.get(address.getShiptoCode()) == null) {
					String params = "id=" + address.getId() + "&shiptoCode=" + address.getShiptoCode() + "&shiptoAddr=" + (address.getShiptoAdd()==null?"":address.getShiptoAdd()) + "&shiptoContact=" + (address.getContact()==null?"":address.getContact())
							+ "&shiptoTel=" + (address.getTel()==null?"":address.getTel()) + "&shiptoMobile=" + (address.getMobile()==null?"":address.getMobile());
					node = (new String[] { "", address.getId().toString(), address.getShiptoAdd() + "(" + address.getShiptoCode() + ")", params, "", "", "" });
					tree.add(node);
					m.put(address.getShiptoCode(), "");
				}
			}
		}
		request.setAttribute("tree", tree);
		return mapping.findForward("tree");
	}

	/**
	 * 业代树
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward distEmpTree(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		BaseUser userInfo = (BaseUser) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		Long empId = null;
		if (userInfo != null && userInfo.getBaseEmployee() != null) {
			empId = userInfo.getBaseEmployee().getEmpId();
		}

		MdmDistributor mdist = mdmDistributorManager.getMdmDistributorByEmployeeId(empId);

		String prodGroupId = request.getParameter("prodGroupId");

		List tree = new ArrayList();
		String node[];
		List<BaseEmployee> list = mdmDistEmpProdgroupManager.getEmpByDistIdProdGroup(mdist.getDistId(), Long.valueOf(prodGroupId));
		if (list.size() > 0) {
			for (BaseEmployee emp : list) {
				String params = "id=" + emp.getEmpId() + "&empCode=" + emp.getEmpCode() + "&empName=" + emp.getEmpName() + "&officePhone=" + emp.getMobilePhone();
				node = (new String[] { "", emp.getEmpId().toString(), emp.getEmpName(), params, "", "", "" });
				tree.add(node);
			}

		}

		request.setAttribute("tree", tree);
		return mapping.findForward("tree");
	}

	/**
	 * 根据产品组生成产品树
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward prodgroupTree(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		List tree = new ArrayList();

		String prodGroupId = request.getParameter("prodGroupId");
		if (prodGroupId == null || "".equals(prodGroupId.trim())) {
			doMessage(request, "产品组不能为空！");
			return mapping.findForward("tree");
		}
		BaseDictItem item = mdmProductManager.findUniqueEntity(BaseDictItem.class, "from BaseDictItem where dictItemId = ? ", Long.valueOf(prodGroupId));

		List<MdmProduct> prods = mdmProductManager.findEntity("from MdmProduct where itemBrand.subCode like '" + item.getSubCode() + "%' and state='1'");

		for (int i = 0; i < prods.size(); i++) {
			MdmProduct prod = (MdmProduct) prods.get(i);
			if (prod.getItemBrand() != null) {
				String parentId = prod.getItemBrand().getDictItemId().toString();
				String id = prod.getProdId().toString();

				String params = "id=" + id + "&leaf=1&prodName=" + prod.getProdName() + "&prodCode=" + prod.getProdCode() + "&countUnitId=" + prod.getProdCountUnit().getDictItemId()
						+ "&countUnitName=" + prod.getProdCountUnit().getItemName();
				String[] node = { parentId, id, prod.getProdCode() + "_" + prod.getProdName(), params, "", "", "" };
				tree.add(node);
			}
		}
		request.setAttribute("tree", tree);
		return mapping.findForward("tree");
	}

	/**
	 * 获取物料组信息（经销商产品组业代、经销商送货地址）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward orderMaterialsTree(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		BaseUser userInfo = (BaseUser) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		Long empId = null;
		if (userInfo != null && userInfo.getBaseEmployee() != null) {
			empId = userInfo.getBaseEmployee().getEmpId();
		}

		MdmDistributor mdist = mdmDistributorManager.getMdmDistributorByEmployeeId(empId);
		if (mdist == null) {
			doMessage(request, "您无权访问！");
			return mapping.findForward("tree");
		}
		String levelCode = request.getParameter("levelCode") == null ? "" : request.getParameter("levelCode");

		String hql1 = "select p.baseDictItem from MdmDistEmpProdgroup p where (p.expiryTime IS NULL or p.expiryTime >=getdate())";
		if (!"".equals(levelCode.trim())) {
			hql1 += " and p.baseDictItem.levelCode <= " + levelCode;
		}
		hql1 += " and p.baseDictItem.levelCode >= 1 ";
		hql1 += " and p.mdmDistributor.distId = " + mdist.getDistId()+" order by p.baseDictItem.itemCode";

		List<BaseDictItem> listSales = baseDictManager.findEntity(BaseDictItem.class, hql1);

		hql1 = "select a.baseDictItem from MdmDistributorAddress a where status=1 ";
		if (!"".equals(levelCode.trim())) {
			hql1 += " and a.baseDictItem.levelCode <= " + levelCode;
		}
		hql1 += " and a.baseDictItem.levelCode >= 1 ";
		hql1 += " and a.mdmDistributor.distId = " + mdist.getDistId()+" order by a.baseDictItem.itemCode";

		List<BaseDictItem> listAddress = baseDictManager.findEntity(BaseDictItem.class, hql1);

		Set<BaseDictItem> prodSet = new TreeSet<BaseDictItem>();
		if (listSales.size() > 0) {
			for (BaseDictItem item1 : listSales) {
				prodSet.add(item1);
			}
		}
		if (listAddress.size() > 0) {
			for (BaseDictItem item2 : listAddress) {
				prodSet.add(item2);
			}
		}
		
		List<String[]> tree = new ArrayList<String[]>();
		for (BaseDictItem item : prodSet) {
			String parentId = item.getBaseDictItem() == null ? "" : item.getBaseDictItem().getDictItemId().toString();
			String id = item.getDictItemId().toString();
			String params = "first=1&id=" + id + "&subCode=" + item.getSubCode() + "&dictId=" + item.getBaseDict().getDictId() + "&levelCode=" + item.getLevelCode();
			String[] node = new String[] { parentId, id, item.getItemName(), params, "", "", "" };
			tree.add(node);
		}
		request.setAttribute("tree", tree);
		return mapping.findForward(TREE);
	}
	/**
	 * 根据产品组生成产品树
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward prodSTRUTree(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		List tree = new ArrayList();

		String prodGroupId = request.getParameter("prodGroupId");
		if (prodGroupId == null || "".equals(prodGroupId.trim())) {
			doMessage(request, "产品物料组不能为空！");
			return mapping.findForward("tree");
		}
		BaseDictItem item = mdmProductManager.findUniqueEntity(BaseDictItem.class, "from BaseDictItem where dictItemId = ? ", Long.valueOf(prodGroupId));
		List<MdmProduct> prods = mdmProductManager.findEntity("from MdmProduct where prodSTRU.subCode like '" + item.getSubCode() + "%' and state='1'");

		for (int i = 0; i < prods.size(); i++) {
			MdmProduct prod = (MdmProduct) prods.get(i);
			if (prod.getProdSTRU() != null) {
				String parentId = prod.getItemBrand().getDictItemId().toString();
				String id = prod.getProdId().toString();

				String params = "id=" + id + "&leaf=1&prodName=" + prod.getProdName() + "&prodCode=" + prod.getProdCode() + "&countUnitId=" + prod.getProdCountUnit().getDictItemId()
						+ "&countUnitName=" + prod.getProdCountUnit().getItemName();
				String[] node = { parentId, id, prod.getProdCode() + "_" + prod.getProdName(), params, "", "", "" };
				tree.add(node);
			}
		}
		request.setAttribute("tree", tree);
		return mapping.findForward("tree");
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 根据产品物料组查询产品，按照产品品牌展现
	 */
	public ActionForward prodBrandWhereProdSTRUTree(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {		
		String prodGroupId = request.getParameter("prodGroupId");
		BaseUser userInfo = (BaseUser) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		//获取当前经销商
		MdmDistributor mdmDistributor=mdmDistributorManager.findUniqueEntity("from MdmDistributor where baseEmployee.empId=?", userInfo.getBaseEmployee().getEmpId());
		if(null!=mdmDistributor){
			request.setAttribute("tree", MdmProductCache.createProdTreeByBrandId(new Long(prodGroupId), mdmProductManager, baseDictManager));
			request.setAttribute("recentTree", MdmProductCache.createDistRecentProdTreeByBrandId(new Long(prodGroupId),mdmDistributor.getDistId(), mdmProductManager, baseDictManager));
			request.setAttribute("newRecentTree", MdmProductCache.createNewProdTreeByBrandId(new Long(prodGroupId), mdmProductManager, baseDictManager));		
			return new ActionForward("/widgets/xtree/xtreeSelectProduct.jsp");
		}
		return null;
	}
	
	
	public ActionForward prodBrandWhereProdSTRUTree_back(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		List tree = new ArrayList();
		List recentTree=new ArrayList();
		List newRecentTree=new ArrayList();
		
		BaseUser userInfo = (BaseUser) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		//获取当前经销商
		MdmDistributor mdmDistributor=mdmDistributorManager.findUniqueEntity("from MdmDistributor where baseEmployee.empId=?", userInfo.getBaseEmployee().getEmpId());
		
		//获取物料组(产品结构)
		String prodGroupId = request.getParameter("prodGroupId");
		if (prodGroupId == null || "".equals(prodGroupId.trim())) {
			doMessage(request, "产品物料组不能为空！");
			return mapping.findForward("tree");
		}
		BaseDictItem item = mdmProductManager.findUniqueEntity(BaseDictItem.class, "from BaseDictItem where dictItemId = ? ", Long.valueOf(prodGroupId));
		//获取该物料组下的所有产品主数据
		List<MdmProduct> prods = mdmProductManager.findEntity("from MdmProduct where prodSTRU.subCode like '" + item.getSubCode() + "%' and state='1'");
		//提取产品所属品牌(根子节点都进行提取)
		Map brandMap=new HashMap();
		for(MdmProduct prod :prods ) {
			if(prod.getItemBrand()!=null){
				brandMap.put(prod.getItemBrand().getItemCode(), prod.getItemBrand());
				if(prod.getItemBrand().getBaseDictItem()!=null){
					putProductBrand(  brandMap, prod.getItemBrand());
				}
			}
		}
		
		//产品品牌树的构建
		 Iterator brandIterator=brandMap.keySet().iterator();
		 if(brandMap.size()>0){
		     List<BaseDictItem >brandList=new   ArrayList();
			 StringBuffer sql=new StringBuffer("");
			while(brandIterator.hasNext()){
				sql.append(((BaseDictItem)brandMap.get(brandIterator.next())).getDictItemId()+",");
			}
			brandList = mdmProductManager.findEntity(BaseDictItem.class, "from BaseDictItem where   dictItemId in( "+sql.toString().substring(0, sql.length()-1)+" ) and baseDict.dictId= ?  order by levelCode ", "prodBrand");
			for (BaseDictItem b:brandList) {
				String parentId = "";
				if(b.getBaseDictItem()!=null)
					parentId=b.getBaseDictItem().getItemCode()+"P";
				String id = b.getItemCode()+"P";

				String params = "id=" + id + "&leaf=1&prodName=" + b.getItemName() + "&prodCode=" + b.getItemCode()  ;
				String[] node = { parentId, id, b.getItemCode() + "_" + b.getItemName(), params, "", "", "" };
				tree.add(node);
			}
		 }
		//产品树构建，置于相应品牌下面
		for (int i = 0; i < prods.size(); i++) {
			MdmProduct prod = (MdmProduct) prods.get(i);
			if (prod.getProdSTRU() != null) {
				String parentId ="";
				if(prod.getItemBrand()!=null)
				parentId=prod.getItemBrand().getItemCode()+"P";
				String id = prod.getProdId().toString();

				String params = "id=" + id + "&leaf=1&prodName=" + prod.getProdName() + "&prodCode=" + prod.getProdCode() + "&countUnitId=" + prod.getProdCountUnit().getDictItemId()
						+ "&countUnitName=" + prod.getProdCountUnit().getItemName();
				String[] node = { parentId, id, prod.getProdCode() + "_" + prod.getProdName(), params, "", "", "" };
				tree.add(node);
			}
		}
		request.setAttribute("tree", tree);
		//查找该经销商最近订购产品  按订购数量 top 30
		if(mdmDistributor!=null){
			String prodIds="0,";
			//List<MdmProduct> lstRcentlyProducts=mdmProductManager.findEntity("from MdmProduct a where a.prodId in(select distinct top 30  i.mdmProduct.prodId from OrderItem as i join i.orderInfo as o where i.orderInfo.orderId=o.orderId and o.mdmDistributor.baseEmployee.empId="+mdmDistributor.getBaseEmployee().getEmpId()+")");
			List<Object[]> lstObjProds=mdmProductManager.executeSqlQuery("select top 30 i.prod_id,count(i.QUANTITY) qcount " +
					"from order_item i " +
					"inner join order_info o on i.order_id=o.order_id " +
					"inner join MDM_PRODUCT mp on i.prod_id=mp.prod_id" +
					" where o.dist_id="+mdmDistributor.getDistId()+
					" and mp.PRODSTRU_ID in(select dict_item_id from BASE_DICT_ITEM where sub_Code like '" + item.getSubCode() + "%' and dict_id='prodSTRU') and state='1' "+
					" GROUP BY i.PROD_ID ORDER BY qcount DESC");
			for (int i = 0; i < lstObjProds.size(); i++) {
				prodIds=prodIds+(lstObjProds.get(i))[0]+",";
			}
			prodIds=prodIds.substring(0, prodIds.length()-1);
			List<Object[]> lstObj=mdmProductManager.executeSqlQuery("" +
					"select m.prod_id,m.PROD_CODE,m.prod_name,m.PRODSTRU_ID,ibrand.item_code,m.PROD_COUNTUNIT_ID,icountUnit.item_name from MDM_PRODUCT m " +
					"left join BASE_DICT_ITEM istru on m.PRODSTRU_ID=istru.dict_item_id " +
					"left join base_dict_item ibrand on m.BRAND_ID=ibrand.dict_item_id " +
					"left join base_dict_item icountUnit on m.PROD_COUNTUNIT_ID=icountUnit.dict_item_id" +
					"   where prod_id in("+prodIds+")");
			for (int i = 0; i < lstObj.size(); i++) {
				Object[] obj = (Object[]) lstObj.get(i);
				if (obj[3] != null) {
					String parentId ="";
					String prodName=((String)obj[2]).replace("\"", "");
					if(obj[4]!=null)
					parentId=(String)obj[4];
					String id = obj[0].toString();

					String params = "id=" + id + "&leaf=1&prodName=" + prodName + "&prodCode=" +  obj[1] + "&countUnitId=" +obj[5].toString()
							+ "&countUnitName=" + obj[6];
					String[] node = { parentId, id, obj[1] + "_" + prodName, params, "", "", "" };
					recentTree.add(node);
				}
			}
			request.setAttribute("recentTree", recentTree);
		}else{
			request.setAttribute("recentTree", null);
		}
		//最近一个月创建的新产品
			List<Object[]> lstNewObj=mdmProductManager.executeSqlQuery("" +
					"select m.prod_id,m.PROD_CODE,m.prod_name,m.PRODSTRU_ID,ibrand.item_code,m.PROD_COUNTUNIT_ID,icountUnit.item_name,count(i.QUANTITY) qcount from MDM_PRODUCT m " +
					"left join BASE_DICT_ITEM istru on m.PRODSTRU_ID=istru.dict_item_id " +
					"left join base_dict_item ibrand on m.BRAND_ID=ibrand.dict_item_id " +
					"left join base_dict_item icountUnit on m.PROD_COUNTUNIT_ID=icountUnit.dict_item_id " +
					"left join order_item i on i.prod_id=m.prod_id " +
					"where m.CREATED BETWEEN dateadd(mm,-1,getdate()) AND getdate() "+
					"GROUP BY m.prod_id,m.PROD_CODE,m.prod_name,m.PRODSTRU_ID,ibrand.item_code,m.PROD_COUNTUNIT_ID,icountUnit.item_name ORDER BY qcount DESC");
			for (int i = 0; i < lstNewObj.size(); i++) {
				Object[] obj = (Object[]) lstNewObj.get(i);
				if (obj[3] != null) {
					String parentId ="";
					String prodName=((String)obj[2]).replace("\"", "");
					if(obj[4]!=null)
					parentId=(String)obj[4];
					String id = obj[0].toString();

					String params = "id=" + id + "&leaf=1&prodName=" + prodName + "&prodCode=" +  obj[1] + "&countUnitId=" +obj[5].toString()
							+ "&countUnitName=" + obj[6];
					String[] node = { parentId, id, obj[1] + "_" + prodName, params, "", "", "" };
					newRecentTree.add(node);
				}
			}
			request.setAttribute("newRecentTree", newRecentTree);
			
		return new ActionForward("/widgets/xtree/xtreeSelectProduct.jsp");
	}
	private void putProductBrand(Map brandMap,BaseDictItem b){
			if(b.getBaseDictItem()!=null){
				brandMap.put(b.getBaseDictItem().getItemCode(), b.getBaseDictItem());
				putProductBrand(  brandMap,  b.getBaseDictItem());
			}
	}
	
	/**
	 * 返回消息
	 * 
	 * @param request
	 * @param message
	 */
	private void doMessage(HttpServletRequest request, String message) {
		ActionMessages msgs = new ActionMessages();
		msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message", message));
		saveMessages(request, msgs);
	}

	public MdmDistributorManager getMdmDistributorManager() {
		return mdmDistributorManager;
	}

	public void setMdmDistributorManager(MdmDistributorManager mdmDistributorManager) {
		this.mdmDistributorManager = mdmDistributorManager;
	}

	public MdmStoreManager getMdmStoreManager() {
		return mdmStoreManager;
	}

	public void setMdmStoreManager(MdmStoreManager mdmStoreManager) {
		this.mdmStoreManager = mdmStoreManager;
	}

	public BaseOrgManager getBaseOrgManager() {
		return baseOrgManager;
	}

	public void setBaseOrgManager(BaseOrgManager baseOrgManager) {
		this.baseOrgManager = baseOrgManager;
	}

	public MdmProductManager getMdmProductManager() {
		return mdmProductManager;
	}

	public void setMdmProductManager(MdmProductManager mdmProductManager) {
		this.mdmProductManager = mdmProductManager;
	}

	public MdmAfsLocationManager getMdmAfsLocationManager() {
		return mdmAfsLocationManager;
	}

	public void setMdmAfsLocationManager(MdmAfsLocationManager mdmAfsLocationManager) {
		this.mdmAfsLocationManager = mdmAfsLocationManager;
	}

	public void setMdmDistributorAddressManager(MdmDistributorAddressManager mdmDistributorAddressManager) {
		this.mdmDistributorAddressManager = mdmDistributorAddressManager;
	}

	public void setMdmDistEmpProdgroupManager(MdmDistEmpProdgroupManager mdmDistEmpProdgroupManager) {
		this.mdmDistEmpProdgroupManager = mdmDistEmpProdgroupManager;
	}

}