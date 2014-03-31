package com.winchannel.order.service;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jxl.Sheet;
import jxl.Workbook;


import com.winchannel.base.model.BaseDictItem;
import com.winchannel.base.model.BaseEmployee;
import com.winchannel.base.model.BaseOrg;
import com.winchannel.base.model.BaseUser;
import com.winchannel.base.service.BaseDictManager;
import com.winchannel.base.service.BaseEmployeeManager;
import com.winchannel.base.service.BaseOrgManager;
import com.winchannel.base.utils.Constants;
import com.winchannel.core.dao.HibernateEntityDao;
import com.winchannel.core.dao.HibernatePersister;
import com.winchannel.core.exception.BusinessException;
import com.winchannel.core.utils.DateUtils;
import com.winchannel.core.utils.StringUtils;
import com.winchannel.base.utils.DateUtility;
import com.winchannel.mdm.distributor.model.MdmDistributor;
import com.winchannel.mdm.distributor.service.MdmDistributorManager;
import com.winchannel.order.bean.DistEmpProdgroup;
import com.winchannel.order.bean.DistOrderempProdgroup;
import com.winchannel.order.model.MdmDistEmpProdgroup;
import com.winchannel.order.model.MdmDistOrderempProdgroup;

/**
 * @author shijingru
 * @订单员_组织_产品组关系维护
 */
public class MdmDistOrderempProdgroupManager extends HibernateEntityDao< MdmDistOrderempProdgroup>{
	
	
	private BaseEmployeeManager baseEmployeeManager;
    private BaseOrgManager baseOrgManager;
	private BaseDictManager baseDictManager;
	
	
	//当先保存一条数据前要先查看数据库里有没有和组织、产品组、订单员编码和新添的这条数据的一样的 ，如果一样就将以前的那条数据的失效日期先填上然后在保存这条数据。
	public void saveMdmDistOrderempProdgroup(BaseEmployee employee,ActionForm form, HttpServletRequest request, MdmDistOrderempProdgroup mdmDistOrderempProdgroup){
		if(StringUtils.isNotBlank(request.getParameter("id"))){
			mdmDistOrderempProdgroup.setUpdatedByname(employee.getEmpName());
			mdmDistOrderempProdgroup.setUpdatedDate(DateUtils.getDate());  
			mdmDistOrderempProdgroup.setUpdatedByid(employee.getEmpId());
		}else{
			mdmDistOrderempProdgroup.setCreatedDate(DateUtils.getDate());
			mdmDistOrderempProdgroup.setCreatedByname(employee.getEmpName());
			mdmDistOrderempProdgroup.setCreatedByid(employee.getEmpId());
		}
		if (StringUtils.isNotBlank(request.getParameter("orgId"))) {
			mdmDistOrderempProdgroup.setBaseOrg(baseOrgManager.get(Long.valueOf(request.getParameter("orgId"))));
		} else {
			mdmDistOrderempProdgroup.setBaseOrg(null);
		}
		if (StringUtils.isNotBlank(request.getParameter("prodBrandId"))) {
			mdmDistOrderempProdgroup.setBaseDictItem(baseDictManager.get(Long.valueOf(request.getParameter("prodBrandId"))));
		} else {
			mdmDistOrderempProdgroup.setBaseDictItem(null);
		}
		if (StringUtils.isNotBlank(request.getParameter("empId"))) {
			mdmDistOrderempProdgroup.setBaseEmployee(baseEmployeeManager.get(Long.valueOf(request.getParameter("empId"))));
		} else {
			mdmDistOrderempProdgroup.setBaseDictItem(null);
		}
	     
		this.save(mdmDistOrderempProdgroup);
		
	}
	

	// 通过distCode得到客户里的相关信息
	public BaseOrg getBaseOrgByName(String orgCode){
		return baseOrgManager.getBaseOrgByCode(orgCode);	
	}
	// 通过CODE得到物料组相关信息
	public BaseDictItem getBaseDictItemId(String code) {
		return baseDictManager.findUniqueEntity("from BaseDictItem where baseDict.dictId = 'prodSTRU' and levelCode = 1 and itemCode = ?", code);
	}
	//通过empCode得到业代人员的相关信息
	public BaseEmployee getBaseEmployeeByCode(String code) {
		return baseEmployeeManager.getBaseEmployeeByCode(code);
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
		distOrderempProdgroups=this.executeSqlQuery(DistOrderempProdgroup.class, sql.toString());
		
		return distOrderempProdgroups;
	}
	SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd");
	String pattern = "[0-9]+(.[0-9]+)?";
	Pattern p = Pattern.compile(pattern);//验证数字类型
	
	//导入
	public String saveExcel(InputStream is,HttpServletRequest request) {
		
		StringBuilder messages = new StringBuilder("[提示信息]：<br>");
		List<MdmDistOrderempProdgroup> list = new ArrayList<MdmDistOrderempProdgroup>();
		boolean isSuccess = true;
		Date date = new Date();
		
		Workbook wb = null;
		try {
			wb = Workbook.getWorkbook(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Sheet sheet = wb.getSheet(0);
		int rows = sheet.getRows();
		for (int i = 1; i < rows; i++) {
			if (StringUtils.isBlank(StringUtils.trim(sheet.getCell(0, i).getContents().trim()))) {
				messages.append("第").append(i+1).append("行:").append("订单人编码 不能为空! <br>");
				isSuccess = false;
			}
			if (StringUtils.isBlank(StringUtils.trim(sheet.getCell(1, i).getContents().trim()))) {
				messages.append("第").append(i+1).append("行:").append("组织不能为空! <br>");
				isSuccess = false;
			}
			if (StringUtils.isBlank(StringUtils.trim(sheet.getCell(2, i).getContents().trim()))) {
				messages.append("第").append(i+1).append("行:").append("产品组不能为空! <br>");
				isSuccess = false;
			}
			if (StringUtils.isBlank(StringUtils.trim(sheet.getCell(3, i).getContents().trim()))) {
				messages.append("第").append(i+1).append("行:").append("生效日期不能为空! <br>");
				isSuccess = false;
			}
			if (StringUtils.isNotBlank(StringUtils.trim(sheet.getCell(3, i).getContents().trim()))) {
				try {
					sdf.parse(StringUtils.trim(sheet.getCell(3, i).getContents().trim()));
				} catch (ParseException e) {
					isSuccess = false;
					throw new BusinessException("[提示信息]：<br>第'"+(i+1)+"'行生效日期格式错误! ");
					}
			}
			if (StringUtils.isNotBlank(StringUtils.trim(sheet.getCell(4, i).getContents().trim()))) {
				try {
					sdf.parse(StringUtils.trim(sheet.getCell(4, i).getContents().trim()));
				} catch (ParseException e) {
					isSuccess = false;
					throw new BusinessException("[提示信息]：<br>第'"+(i+1)+"'行失效日期格式错误! ");
					}
			}
			if ((StringUtils.isNotBlank(StringUtils.trim(sheet.getCell(3, i).getContents().trim())))&&(StringUtils.isNotBlank(StringUtils.trim(sheet.getCell(4, i).getContents().trim())))) {
				Date eff=DateUtility.strToDate(StringUtils.trim(sheet.getCell(3, i).getContents().trim()));
				Date exp=DateUtility.strToDate(StringUtils.trim(sheet.getCell(4, i).getContents().trim()));
				if(exp.getTime()-eff.getTime()<=0){
					isSuccess = false;
					throw new BusinessException("[提示信息]：<br>第'"+(i+1)+"'行失效时间必须大于生效时间! ");
				}
			}
			if (isSuccess) {
				MdmDistOrderempProdgroup mdmDistOrderempProdgroup = new MdmDistOrderempProdgroup();
				//订单员编码
				BaseEmployee objBaseEmployee = getBaseEmployeeByCode(new String(StringUtils.trim(sheet.getCell(0,i).getContents().trim())));
				//组织
				BaseOrg objBaseOrg = getBaseOrgByName(new String(StringUtils.trim(sheet.getCell(1,i).getContents().trim())));
				//产品组
				BaseDictItem objBaseDictItem = getBaseDictItemId(StringUtils.trim(sheet.getCell(2, i).getContents().trim()));
				
				//客户编码
				if(objBaseEmployee==null){
					messages.append("第").append(i+1).append("行:").append("订单员编码不存在! ");
					isSuccess = false;
				}else{
					//组织
					if(objBaseOrg==null){
						messages.append("第").append(i+1).append("行:").append("组织名称不存在！");
						isSuccess=false;
					}else{
						//产品组
						if(objBaseDictItem==null){
							messages.append("第").append(i+1).append("行:").append("产品组不存在! ");
							isSuccess = false;
						}else{
							List isSames = getIsSame(objBaseOrg.getOrgId(),objBaseDictItem.getDictItemId(),objBaseEmployee.getEmpId(),StringUtils.trim(sheet.getCell(3, i).getContents().trim()),StringUtils.trim(sheet.getCell(4, i).getContents().trim()));
							if(isSames.size()>0){
								messages.append("第").append(i+1).append("行:").append("已经存在相同的订单员，组织，物料组的关系维护，不能重复添加！");
								isSuccess=false;
							}else{
								mdmDistOrderempProdgroup.setBaseEmployee(objBaseEmployee);
								mdmDistOrderempProdgroup.setBaseOrg(objBaseOrg);
								mdmDistOrderempProdgroup.setBaseDictItem(objBaseDictItem);
								try {
									mdmDistOrderempProdgroup.setEffectiveTime(sdf.parse(StringUtils.trim(sheet.getCell(3, i).getContents().trim())));
								} catch (ParseException e) {
									e.printStackTrace();
								}
								try {
									mdmDistOrderempProdgroup.setExpiryTime(sdf.parse(StringUtils.trim(sheet.getCell(4, i).getContents().trim())));
								} catch (ParseException e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
				
				mdmDistOrderempProdgroup.setMemo1(StringUtils.trim(sheet.getCell(5, i).getContents().trim()));
				mdmDistOrderempProdgroup.setMemo2(StringUtils.trim(sheet.getCell(6, i).getContents().trim()));
				mdmDistOrderempProdgroup.setMemo3(StringUtils.trim(sheet.getCell(7, i).getContents().trim()));
				
				BaseEmployee employee = ((BaseUser) request.getSession().getAttribute(Constants.SESSION_USER_KEY)).getBaseEmployee();
				mdmDistOrderempProdgroup.setCreatedDate(DateUtils.getDate());
				mdmDistOrderempProdgroup.setCreatedByid(employee.getEmpId());
				mdmDistOrderempProdgroup.setCreatedByname(employee.getEmpName());
				
				list.add(mdmDistOrderempProdgroup);
			}else {
				messages.append("<br>");
				}
		}
		
		if (isSuccess) {
			try{
				this.saveAll(list);
				return "导入成功，共导入数据" + (rows - 1) + "条";
			}catch(Exception e){
				throw new BusinessException("不能重复导入");
			}
			
		}else {
			return messages.toString();
		}
	}
	
	
	public BaseEmployeeManager getBaseEmployeeManager() {
		return baseEmployeeManager;
	}
	public void setBaseEmployeeManager(BaseEmployeeManager baseEmployeeManager) {
		this.baseEmployeeManager = baseEmployeeManager;
	}

	public BaseOrgManager getBaseOrgManager() {
		return baseOrgManager;
	}
	public void setBaseOrgManager(BaseOrgManager baseOrgManager) {
		this.baseOrgManager = baseOrgManager;
	}
	public BaseDictManager getBaseDictManager() {
		return baseDictManager;
	}
	public void setBaseDictManager(BaseDictManager baseDictManager) {
		this.baseDictManager = baseDictManager;
	}

	
	
}
