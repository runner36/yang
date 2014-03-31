package com.winchannel.order.service;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import jxl.Sheet;
import jxl.Workbook;

import com.winchannel.base.model.BaseDictItem;
import com.winchannel.base.model.BaseEmployee;
import com.winchannel.base.model.BaseUser;
import com.winchannel.base.service.BaseDictManager;
import com.winchannel.base.utils.Constants;
import com.winchannel.core.dao.HibernateEntityDao;
import com.winchannel.core.exception.BusinessException;
import com.winchannel.core.utils.DateUtils;
import com.winchannel.core.utils.StringUtils;
import com.winchannel.mdm.distributor.model.MdmDistributor;
import com.winchannel.mdm.distributor.service.MdmDistributorManager;
import com.winchannel.order.model.MdmDistributorLinkman;
/**
 * @author shijingru
 * @客户联系人信息维护
 */
public class MdmDistributorLinkmanManager extends HibernateEntityDao<MdmDistributorLinkman> {

	private MdmDistributorManager mdmDistributorManager;
	private BaseDictManager baseDictManager;

	// 通过distCode得到客户里的相关信息
	public MdmDistributor getMdmDistManagerId(String code){
		return mdmDistributorManager.getMdmDistributorByProdCode(code);		
	}
	// 通过CODE得到物料组相关信息
	public BaseDictItem getBaseDictItemId(String code) {
		return baseDictManager.findUniqueEntity("from BaseDictItem where baseDict.dictId = 'prodSTRU' and levelCode = 1 and itemCode = ?", code);
	}


	//导入
	public String saveExcel(InputStream is,HttpServletRequest request) {
		
		StringBuilder messages = new StringBuilder("[提示信息]：<br>");
		List<MdmDistributorLinkman> list = new ArrayList<MdmDistributorLinkman>();
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
			if (StringUtils.isBlank(StringUtils.trim(sheet.getCell(2, i).getContents().trim()))) {
				messages.append("第").append(i+1).append("行:").append("物料组 不能为空! <br>");
				isSuccess = false;
			}
			if (StringUtils.isBlank(StringUtils.trim(sheet.getCell(3, i).getContents().trim()))) {
				messages.append("第").append(i+1).append("行:").append("联系人姓名不能为空! <br>");
				isSuccess = false;
			}
			if (StringUtils.isBlank(StringUtils.trim(sheet.getCell(4, i).getContents().trim()))) {
				messages.append("第").append(i+1).append("行:").append("手机号码不能为空! <br>");
				isSuccess = false;
			}
			if (StringUtils.isNotBlank(StringUtils.trim(sheet.getCell(4, i).getContents().trim()))) {
				Boolean b = Pattern.matches( "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$", StringUtils.trim(sheet.getCell(4, i).getContents().trim())); 
				if(!b){
					messages.append("第").append(i+1).append("行:").append(StringUtils.trim(sheet.getCell(4, i).getContents().trim())).append("手机号码不合法! <br>");
					isSuccess = false;
				}
				
			}
			
			if (isSuccess) {
				MdmDistributorLinkman mdmDistributorLinkman = new MdmDistributorLinkman();
				//客户编码
				MdmDistributor objMdmDistributor = getMdmDistManagerId(StringUtils.trim(sheet.getCell(0, i).getContents().trim()));
				//物料组
				BaseDictItem objBaseDictItem = getBaseDictItemId(StringUtils.trim(sheet.getCell(2, i).getContents().trim()));
				
				//客户编码
				if(objMdmDistributor==null){
					messages.append("第").append(i+1).append("行:").append("客户编号不存在! ");
					isSuccess = false;
				}else{
					mdmDistributorLinkman.setMdmDistributor(objMdmDistributor);
				}
				//物料组
				if(objBaseDictItem==null){
					messages.append("第").append(i+1).append("行:").append("物料组不存在! ");
					isSuccess = false;
				}else{
					mdmDistributorLinkman.setBaseDictItem(objBaseDictItem);
				}
				
				mdmDistributorLinkman.setLinkmanName(StringUtils.trim(sheet.getCell(3, i).getContents().trim()));
				mdmDistributorLinkman.setLinkmanPhonenum(StringUtils.trim(sheet.getCell(4, i).getContents().trim()));
				mdmDistributorLinkman.setLinkmanTel(StringUtils.trim(sheet.getCell(5, i).getContents().trim()));
				mdmDistributorLinkman.setStatus(1L);
				
				BaseEmployee employee = ((BaseUser) request.getSession().getAttribute(Constants.SESSION_USER_KEY)).getBaseEmployee();
				mdmDistributorLinkman.setCreatedDate(DateUtils.getDate());
				mdmDistributorLinkman.setCreatedByid(employee.getEmpId());
				mdmDistributorLinkman.setCreatedByname(employee.getEmpName());
				
				list.add(mdmDistributorLinkman);
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
