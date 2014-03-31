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
import com.winchannel.base.model.BaseUser;
import com.winchannel.base.service.BaseDictManager;
import com.winchannel.base.service.BaseEmployeeManager;
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
import com.winchannel.order.model.MdmDistEmpProdgroup;

/**
 * @author shijingru
 * @客户_产品组_业代关系维护
 */
public class MdmDistEmpProdgroupManager extends HibernateEntityDao< MdmDistEmpProdgroup>{
	
	
	private BaseEmployeeManager baseEmployeeManager;
	private MdmDistributorManager mdmDistributorManager;
	private BaseDictManager baseDictManager;
	

	//当先保存一条数据前要先查看数据库里有没有和客户名称、产品组、业代编码和新添的这条数据的一样的 ，如果一样就将以前的那条数据的失效日期先填上然后在保存这条数据。
	public void saveMdmDistEmpProdgroup(BaseEmployee employee,ActionForm form, HttpServletRequest request, MdmDistEmpProdgroup mdmDistEmpProdgroup){
		if(StringUtils.isNotBlank(request.getParameter("id"))){
			mdmDistEmpProdgroup.setUpdatedByname(employee.getEmpName());
			mdmDistEmpProdgroup.setUpdatedDate(DateUtils.getDate());  
			mdmDistEmpProdgroup.setUpdatedByid(employee.getEmpId());
		}else{
			mdmDistEmpProdgroup.setCreatedDate(DateUtils.getDate());
			mdmDistEmpProdgroup.setCreatedByname(employee.getEmpName());
			mdmDistEmpProdgroup.setCreatedByid(employee.getEmpId());
		}
		if (StringUtils.isNotBlank(request.getParameter("distId"))) {
			mdmDistEmpProdgroup.setMdmDistributor(mdmDistributorManager.get(Long.valueOf(request.getParameter("distId"))));
		} else {
			mdmDistEmpProdgroup.setMdmDistributor(null);
		}
		if (StringUtils.isNotBlank(request.getParameter("prodBrandId"))) {
			mdmDistEmpProdgroup.setBaseDictItem(baseDictManager.get(Long.valueOf(request.getParameter("prodBrandId"))));
		} else {
			mdmDistEmpProdgroup.setBaseDictItem(null);
		}
		if (StringUtils.isNotBlank(request.getParameter("empId"))) {
			mdmDistEmpProdgroup.setBaseEmployee(baseEmployeeManager.get(Long.valueOf(request.getParameter("empId"))));
		} else {
			mdmDistEmpProdgroup.setBaseDictItem(null);
		}
	     
		this.save(mdmDistEmpProdgroup);
		
	}
	
	
	// 通过distCode得到客户里的相关信息
	public MdmDistributor getMdmDistManagerId(String code){
		return mdmDistributorManager.getMdmDistributorByProdCode(code);		
	}
	// 通过CODE得到物料组相关信息
	public BaseDictItem getBaseDictItemId(String code) {
		return baseDictManager.findUniqueEntity("from BaseDictItem where baseDict.dictId = 'prodSTRU' and levelCode = 1 and itemCode = ?", code);
	}
	//通过id得到业代人员的相关信息
	public BaseEmployee getBaseEmployee(String code){
		return baseEmployeeManager.getBaseEmployeeByCode(code);
	}
	public List getIsSame(Long distId, Long prodBrandId, Long empId, String effectiveTime, String expiryTime){
		
		List<DistEmpProdgroup> distEmpProdgroups= new ArrayList<DistEmpProdgroup>();
		if(expiryTime==null||expiryTime.equals("")){
			expiryTime="2099-12-30";
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT id as id FROM MDM_DIST_EMP_PRODGROUP WHERE DIST_ID="+distId+" AND DICT_ITEM_ID="+prodBrandId+" AND EMP_ID="+empId
					+" and ((EXPIRY_TIME IS Null and EFFECTIVE_TIME <'"+expiryTime+"') or"
					+" (EXPIRY_TIME IS not Null and not('"+effectiveTime+"'>=EXPIRY_TIME or '"+expiryTime+"'<=+EFFECTIVE_TIME))"
					+")");
		distEmpProdgroups=this.executeSqlQuery(DistEmpProdgroup.class, sql.toString());
		return distEmpProdgroups;
	}
	//查询相同客户，物料组截至导入生效日期仍然有效的关系
	public List getIsSameDistProd(Long distId, Long prodBrandId, String effectiveTime){
		
		List<DistEmpProdgroup> distEmpProdgroups= new ArrayList<DistEmpProdgroup>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT id as id FROM MDM_DIST_EMP_PRODGROUP WHERE DIST_ID="+distId+" AND DICT_ITEM_ID="+prodBrandId
					+" and ((EXPIRY_TIME IS Null) or"
					+" (EXPIRY_TIME IS not Null and not('"+effectiveTime+"'<EXPIRY_TIME))"
					+")");
		distEmpProdgroups=this.executeSqlQuery(DistEmpProdgroup.class, sql.toString());
		return distEmpProdgroups;
	}
	//相同客户，物料组导入不同业代，将上一个客户物料组对应的业代的生效日期放置失效日期
	public void updateExpiryTime(Long distId, Long prodBrandId, String effectiveTime){
		String sql = "UPDATE MDM_DIST_EMP_PRODGROUP SET EXPIRY_TIME='"+effectiveTime+"' WHERE DIST_ID="+distId+" AND DICT_ITEM_ID="+prodBrandId
		+" and ((EXPIRY_TIME IS Null) or"
		+" (EXPIRY_TIME IS not Null and '"+effectiveTime+"'<EXPIRY_TIME))";
		this.executeSqlUpdate(sql);
	}
	
	
	SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd");
	String pattern = "[0-9]+(.[0-9]+)?";
	Pattern p = Pattern.compile(pattern);//验证数字类型
	
	//导入
	public String saveExcel(InputStream is,HttpServletRequest request) {
		
		StringBuilder messages = new StringBuilder("[提示信息]：<br>");
		List<MdmDistEmpProdgroup> list = new ArrayList<MdmDistEmpProdgroup>();
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
				messages.append("第").append(i+1).append("行:").append("客户编号 不能为空! <br>");
				isSuccess = false;
			}
			if (StringUtils.isBlank(StringUtils.trim(sheet.getCell(1, i).getContents().trim()))) {
				messages.append("第").append(i+1).append("行:").append("产品组 不能为空! <br>");
				isSuccess = false;
			}
			if (StringUtils.isBlank(StringUtils.trim(sheet.getCell(2, i).getContents().trim()))) {
				messages.append("第").append(i+1).append("行:").append("业代编码不能为空! <br>");
				isSuccess = false;
			}
			if (StringUtils.isBlank(StringUtils.trim(sheet.getCell(3, i).getContents().trim()))) {
				messages.append("第").append(i+1).append("行:").append("生效日期不能为空! <br>");
				isSuccess = false;
			}
			if (StringUtils.isNotBlank(StringUtils.trim(sheet.getCell(3, i).getContents().trim()))) {
				try {
					sdf.parse(StringUtils.trim(sheet.getCell(3, i).getContents().trim()));
					if(DateUtility.strToDate(DateUtility.getCurrentDate()).getTime()-DateUtility.strToDate(StringUtils.trim(sheet.getCell(3, i).getContents().trim())).getTime()>0){
						messages.append("第").append(i+1).append("行:").append("生效日期必须大于等于当前日期! <br>");
						isSuccess = false;
					}
				} catch (ParseException e) {
					isSuccess = false;
					throw new BusinessException("[提示信息]：<br>第'"+(i+1)+"'行生效日期格式错误! ");
					}
			}
			Date expiryTime;
			if (StringUtils.isNotBlank(StringUtils.trim(sheet.getCell(4, i).getContents().trim()))) {
				try {
					expiryTime = sdf.parse(StringUtils.trim(sheet.getCell(4, i).getContents().trim()));
				} catch (ParseException e) {
					isSuccess = false;
					throw new BusinessException("[提示信息]：<br>第'"+(i+1)+"'行失效日期格式错误! ");
					}
			}else{
				expiryTime = null;
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
				MdmDistEmpProdgroup mdmDistEmpProdgroup = new MdmDistEmpProdgroup();
				//客户编码
				MdmDistributor objMdmDistributor = getMdmDistManagerId(StringUtils.trim(sheet.getCell(0, i).getContents().trim()));
				//产品组
				BaseDictItem objBaseDictItem = getBaseDictItemId(StringUtils.trim(sheet.getCell(1, i).getContents().trim()));
				//业代人员
				BaseEmployee objBaseEmployee = getBaseEmployee(new String(StringUtils.trim(sheet.getCell(2,i).getContents().trim())));
				
					//客户编码
				if(objMdmDistributor==null){
					messages.append("第").append(i+1).append("行:").append("客户编号不存在! ");
					isSuccess = false;
				}else{
					//产品组
					if(objBaseDictItem==null){
						messages.append("第").append(i+1).append("行:").append("产品组不存在! ");
						isSuccess = false;
					}else{
						if(objBaseEmployee==null){
							messages.append("第").append(i+1).append("行:").append("业代编码不存在！");
							isSuccess=false;
						}else{
							this.updateExpiryTime(objMdmDistributor.getDistId(),objBaseDictItem.getDictItemId(),StringUtils.trim(sheet.getCell(3, i).getContents().trim()));
							mdmDistEmpProdgroup.setMdmDistributor(objMdmDistributor);
							mdmDistEmpProdgroup.setBaseDictItem(objBaseDictItem);
							mdmDistEmpProdgroup.setBaseEmployee(objBaseEmployee);
							try {
								mdmDistEmpProdgroup.setEffectiveTime(sdf.parse(StringUtils.trim(sheet.getCell(3, i).getContents().trim())));
							} catch (ParseException e) {
								e.printStackTrace();
							}
							mdmDistEmpProdgroup.setExpiryTime(expiryTime);
						}
					}
				}
				
				
				mdmDistEmpProdgroup.setMemo1(StringUtils.trim(sheet.getCell(5, i).getContents().trim()));
				mdmDistEmpProdgroup.setMemo2(StringUtils.trim(sheet.getCell(6, i).getContents().trim()));
				mdmDistEmpProdgroup.setMemo3(StringUtils.trim(sheet.getCell(7, i).getContents().trim()));
				
				BaseEmployee employee = ((BaseUser) request.getSession().getAttribute(Constants.SESSION_USER_KEY)).getBaseEmployee();
				mdmDistEmpProdgroup.setCreatedDate(DateUtils.getDate());
				mdmDistEmpProdgroup.setCreatedByid(employee.getEmpId());
				mdmDistEmpProdgroup.setCreatedByname(employee.getEmpName());
				
				this.save(mdmDistEmpProdgroup);
			}else {
				messages.append("<br>");
				}


		}
		
		if (isSuccess) {
			try{
				return "导入成功，共导入数据" + (rows - 1) + "条";
			}catch(Exception e){
				throw new BusinessException("不能重复导入");
			}
			
		}else {
			return messages.toString();
		}
	}
	
	/**
	 * 根据经销商、产品组得到业务相关信息
	 * 
	 * @param distId
	 * @param prodgroupId
	 * @return
	 */
	public List<MdmDistEmpProdgroup> getMdmDistEmpProdgroupByProdgroup(Long distId, Long prodgroupId){
	    
	    return this.findEntity("from MdmDistEmpProdgroup where mdmDistributor.distId = ? and baseDictItem.dictItemId = ? and effectiveTime <=getdate() and (expiryTime IS NULL or expiryTime>=getdate()) ", distId, prodgroupId);
	}
	
	/**
	 * 得到业代信息
	 * 
	 * @param distId
	 * @param prodgroupId
	 * @return
	 */
	public List<BaseEmployee> getEmpByDistIdProdGroup(Long distId, Long prodgroupId){
	    return this.findEntity(BaseEmployee.class,"select p.baseEmployee from MdmDistEmpProdgroup p where p.mdmDistributor.distId = ?  and p.baseDictItem.dictItemId = ? and p.effectiveTime <=getdate() and (p.expiryTime IS NULL or p.expiryTime >=getdate()) ", distId, prodgroupId);
	}
	public MdmDistEmpProdgroup getMdmEmpProdgroupByDist(Long distId, Long prodgroupId){
	    
	    return this.findUniqueEntity("from MdmDistEmpProdgroup where dist_Id = ? and dict_Item_Id = ? and expiryTime IS NULL ", distId, prodgroupId);
	}
	
	
	public BaseEmployeeManager getBaseEmployeeManager() {
		return baseEmployeeManager;
	}
	public void setBaseEmployeeManager(BaseEmployeeManager baseEmployeeManager) {
		this.baseEmployeeManager = baseEmployeeManager;
	}
	public MdmDistributorManager getMdmDistributorManager() {
		return mdmDistributorManager;
	}
	public void setMdmDistributorManager(MdmDistributorManager mdmDistributorManager) {
		this.mdmDistributorManager = mdmDistributorManager;
	}
	
	public BaseDictManager getBaseDictManager() {
		return baseDictManager;
	}
	public void setBaseDictManager(BaseDictManager baseDictManager) {
		this.baseDictManager = baseDictManager;
	}

	
}
