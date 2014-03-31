package com.winchannel.order.service;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.winchannel.base.utils.Constants;
import com.winchannel.core.dao.HibernateEntityDao;
import com.winchannel.core.exception.BusinessException;
import com.winchannel.core.utils.DateUtils;
import com.winchannel.core.utils.StringUtils;
import com.winchannel.base.utils.DateUtility;
import com.winchannel.mdm.distributor.model.MdmDistributor;
import com.winchannel.mdm.distributor.service.MdmDistributorManager;
import com.winchannel.order.model.MdmDistributorAddress;

/**
 * @author shijingru
 * @客户送达方信息维护
 */
public class MdmDistributorAddressManager extends HibernateEntityDao<MdmDistributorAddress>{
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

	//ajax判断是否有相同的客户，物料组,送达方编码一组数据
	public List<MdmDistributorAddress> getIsSame(Long distId, Long prodBrandId, String shiptoCode){
		List<MdmDistributorAddress> addr = new ArrayList<MdmDistributorAddress>();
		String hql = "from MdmDistributorAddress where mdmDistributor.distId=" + distId + " and baseDictItem.dictItemId=" + prodBrandId + " and shiptoCode='" + shiptoCode + "'";
		addr = this.find(hql);
		return addr;
	}
	public void updateIsSame(MdmDistributorAddress address){
		String sql = "UPDATE MDM_DISTRIBUTOR_ADDRESS SET SHIPTO_NAME='" + address.getShiptoName() + "', Status=" 
		+ address.getStatus() + ", SHIPTO_ADD='" + address.getShiptoAdd() + "', Contact='" + address.getContact() 
		+ "', TEL='" + address.getTel() + "', MOBILE='" + address.getMobile() + "', UPDATED_DATE='" 
		+ DateUtility.dateTimeToStr(address.getUpdatedDate()) + "', UPDATED_BYID=" + address.getUpdatedByid() + ", UPDATED_BYNAME='" 
		+ address.getUpdatedByname() + "', FACTORY_DELIVERY='" + address.getFactoryDelivery() 
		+ "' WHERE SHIPTO_CODE='" + address.getShiptoCode() + "' AND DIST_ID="
		+ address.getMdmDistributor().getDistId() + " AND DICT_ITEM_ID="+ address.getBaseDictItem().getDictItemId();
		this.executeSqlUpdate(sql);
	}
	//导入
	public String saveExcel(InputStream is,HttpServletRequest request) {
		StringBuilder messages = new StringBuilder("[提示信息]：<br>");
		List<MdmDistributorAddress> list = new ArrayList<MdmDistributorAddress>();
		boolean isSuccess = true;
		
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
				messages.append("第").append(i+1).append("行:").append("送达方编号不能为空! <br>");
				isSuccess = false;
			}
			if (StringUtils.isBlank(StringUtils.trim(sheet.getCell(3, i).getContents().trim()))) {
				messages.append("第").append(i+1).append("行:").append("送达方客户名称不能为空! <br>");
				isSuccess = false;
			}
			if (StringUtils.isBlank(StringUtils.trim(sheet.getCell(4, i).getContents().trim()))) {
				messages.append("第").append(i+1).append("行:").append("送达方地址不能为空! <br>");
				isSuccess = false;
			}
			if (StringUtils.isBlank(StringUtils.trim(sheet.getCell(5, i).getContents().trim()))) {
				messages.append("第").append(i+1).append("行:").append("联系人不能为空! <br>");
				isSuccess = false;
			}
			if (StringUtils.isBlank(StringUtils.trim(sheet.getCell(6, i).getContents().trim()))) {
				messages.append("第").append(i+1).append("行:").append("联系电话不能为空! <br>");
				isSuccess = false;
			}
			
			if (isSuccess) {
				MdmDistributorAddress mdmDistributorAddress = new MdmDistributorAddress();
				//客户编码
				MdmDistributor objMdmDistributor = getMdmDistManagerId(StringUtils.trim(sheet.getCell(0, i).getContents().trim()));
				//产品组
				BaseDictItem objBaseDictItem = getBaseDictItemId(StringUtils.trim(sheet.getCell(1, i).getContents().trim()));
				
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
						mdmDistributorAddress.setBaseDictItem(objBaseDictItem);
						mdmDistributorAddress.setMdmDistributor(objMdmDistributor);
					}
					
				}
				mdmDistributorAddress.setShiptoCode(StringUtils.trim(sheet.getCell(2, i).getContents().trim()));
				mdmDistributorAddress.setShiptoName(StringUtils.trim(sheet.getCell(3, i).getContents().trim()));
				mdmDistributorAddress.setShiptoAdd(StringUtils.trim(sheet.getCell(4, i).getContents().trim()));
				mdmDistributorAddress.setContact(StringUtils.trim(sheet.getCell(5, i).getContents().trim()));
				mdmDistributorAddress.setTel(StringUtils.trim(sheet.getCell(6, i).getContents().trim()));
				mdmDistributorAddress.setMobile(StringUtils.trim(sheet.getCell(7, i).getContents().trim()));
				mdmDistributorAddress.setFactoryDelivery(StringUtils.trim(sheet.getCell(8, i).getContents().trim()));
				if(StringUtils.isNotBlank(StringUtils.trim(sheet.getCell(9, i).getContents().trim()))){
					mdmDistributorAddress.setStatus(Long.valueOf(StringUtils.trim(sheet.getCell(9, i).getContents().trim())));
				}else{
					mdmDistributorAddress.setStatus(new Long(1));
				}
				BaseEmployee employee = ((BaseUser) request.getSession().getAttribute(Constants.SESSION_USER_KEY)).getBaseEmployee();
				
				List<MdmDistributorAddress> address = new ArrayList<MdmDistributorAddress>();
				address = getIsSame(objMdmDistributor.getDistId(), objBaseDictItem.getDictItemId(), StringUtils.trim(sheet.getCell(2, i).getContents().trim()));
				if(address.size()>0){
					mdmDistributorAddress.setUpdatedDate(DateUtils.getDate());
					mdmDistributorAddress.setUpdatedByid(employee.getEmpId());
					mdmDistributorAddress.setUpdatedByname(employee.getEmpName());
					for(MdmDistributorAddress addr : address){
						this.updateIsSame(mdmDistributorAddress);
					}
				}else{
					mdmDistributorAddress.setCreatedDate(DateUtils.getDate());
					mdmDistributorAddress.setCreatedByid(employee.getEmpId());
					mdmDistributorAddress.setCreatedByname(employee.getEmpName());
					this.save(mdmDistributorAddress);
				}
				
			}else {
				messages.append("<br>");
				}
		}
		
		if (isSuccess) {
			try{
				return "导入成功 ";
			}catch(Exception e){
				throw new BusinessException("不能重复导入");
			}
			
		}else {
			return messages.toString();
		}
	}
	
	/**
	 * 得到送货方信息
	 * 
	 * @param distId
	 * @param prodGroupId
	 * @return
	 */
	public List<MdmDistributorAddress> getMdmDistributorAddressByDistProdgroup(Long distId, Long prodGroupId){
	    BaseDictItem item = this.findUniqueEntity(BaseDictItem.class, "from BaseDictItem where dictItemId = ? ", prodGroupId);
	    return this.findEntity("from MdmDistributorAddress where mdmDistributor.distId = ? and baseDictItem.subCode like '"+item.getSubCode()+ "%' and status = 1", distId);
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
