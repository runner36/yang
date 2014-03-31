package com.winchannel.mdm.calendar.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;

import com.winchannel.base.model.BaseDictItem;
import com.winchannel.base.service.BaseDictManager;
import com.winchannel.core.dao.HibernateEntityDao;
import com.winchannel.mdm.calendar.model.MdmCalendar;
import com.winchannel.mdm.util.date.DateUtils;
import com.winchannel.mdm.util.string.StringUtils;

public class MdmCalendarManager extends HibernateEntityDao<MdmCalendar> {
	/**
	 * 通过code得到字典表里的相关的id
	 * @param baseDictManager
	 * @param code
	 * @return
	 */
	public BaseDictItem getMdmStoreByProdCode(BaseDictManager baseDictManager,String code) {
		return baseDictManager.findUniqueEntity("from BaseDictItem where itemCode=?", code);
	}
	/**
     * 根据code得到产品
     * @param prodCode
     * @return
     */
	public MdmCalendar getProductByProdCode(String prodCode) {
		return findUniqueEntity("from MdmCalendar where prodCode=?", prodCode);
	}
	/**
	 * 得到排序号
	 * @return
	 */
	public long getSort() 
	{
		String sql = "select max(SORT) from  MDM_PRODUCT ";
		List list = this.executeSqlQuery(sql);
		long sort=1;
		if (list !=null && list.size() > 0) {
			Object[] obj = (Object[]) list.get(0);
			if (obj[0] != null) {
				sort =((java.math.BigDecimal)obj[0])==null?1:((java.math.BigDecimal)obj[0]).longValue()+1;
			}
		}
		return sort;
	}
	/**
	 * 导入Excel
	 * @param f
	 * @param optUser
	 * @param baseDictManager
	 * @return
	 * @throws Exception
	 */
	public List saveExcel(InputStream f,String optUser,BaseDictManager baseDictManager) throws Exception
	 {
		 ArrayList errMsgList = new ArrayList();
		 ArrayList beanList = new ArrayList();
		 int sort =(int)getSort(); // 导入记数
		 try {
			 Workbook wb =  Workbook.getWorkbook(f);
			 Sheet sheet = wb.getSheet(0);
			int rows = sheet.getRows();
			for (int i = 1; i < rows; i++)
			{
				MdmCalendar mdmCalendar=new MdmCalendar();
				String errMsgStr="";
				//年
				if(!StringUtils.isNull(sheet.getCell(0, i).getContents().trim())){
					if(StringUtils.isNumericByPattern(sheet.getCell(0, i).getContents().trim())){
						mdmCalendar.setCalYear(new Long(StringUtils.trimSpace(sheet.getCell(0, i).getContents().trim())));
					}else{
					  errMsgStr+="("+i+",0)：<font color='#FF0000'>年必须为数字&nbsp;&nbsp;&nbsp;&nbsp;</font>";
					}
				}
				//季
				if(!StringUtils.isNull(sheet.getCell(1, i).getContents().trim())){
					if(StringUtils.isNumericByPattern(sheet.getCell(1, i).getContents().trim())){
						mdmCalendar.setCalQuarter(new Long(StringUtils.trimSpace(sheet.getCell(1, i).getContents().trim())));
					}else{
						  errMsgStr+="("+i+",1)：<font color='#FF0000'>季必须为数字&nbsp;&nbsp;&nbsp;&nbsp;</font>";
					}
				}
				//月
				if(!StringUtils.isNull(sheet.getCell(2, i).getContents().trim())){
					if(StringUtils.isNumericByPattern(sheet.getCell(2, i).getContents().trim())){
						mdmCalendar.setCalMonth(new Long(StringUtils.trimSpace(sheet.getCell(2, i).getContents().trim())));
					}else{
						  errMsgStr+="("+i+",2)：<font color='#FF0000'>月必须为数字&nbsp;&nbsp;&nbsp;&nbsp;</font>";
					}
				}
				//周
				if(!StringUtils.isNull(sheet.getCell(3, i).getContents().trim())){
					if(StringUtils.isNumericByPattern(sheet.getCell(3, i).getContents().trim())){
						mdmCalendar.setCalWeek(new Long(StringUtils.trimSpace(sheet.getCell(3, i).getContents().trim())));
					}else{
						  errMsgStr+="("+i+",3)：<font color='#FF0000'>周必须为数字&nbsp;&nbsp;&nbsp;&nbsp;</font>";
					}
				}
				//月周
				if(!StringUtils.isNull(sheet.getCell(4, i).getContents().trim())){
					if(StringUtils.isNumericByPattern(sheet.getCell(4, i).getContents().trim())){
						mdmCalendar.setCalWeekOfMonth(new Long(StringUtils.trimSpace(sheet.getCell(4, i).getContents().trim())));
					}else{
						  errMsgStr+="("+i+",4)：<font color='#FF0000'>月周必须为数字&nbsp;&nbsp;&nbsp;&nbsp;</font>";
					}
				}
				//日
				if(!StringUtils.isNull(sheet.getCell(5, i).getContents().trim())){
					if(StringUtils.isNumericByPattern(sheet.getCell(5, i).getContents().trim())){
						mdmCalendar.setCalDay(new Long(StringUtils.trimSpace(sheet.getCell(5, i).getContents().trim())));
					}else{
						  errMsgStr+="("+i+",5)：<font color='#FF0000'>日必须为数字&nbsp;&nbsp;&nbsp;&nbsp;</font>";
					}
				}
				//日期
				if(!StringUtils.isNull(sheet.getCell(6, i).getContents().trim())){
					if(DateUtils.isValidDate(sheet.getCell(6, i).getContents().trim(),"yyyy-MM-dd"))
						mdmCalendar.setCalDate(DateUtils.strToDate(sheet.getCell(6, i).getContents().trim(),"yyyy-MM-dd"));
					else
					      errMsgStr+="("+i+",6)：<font color='#FF0000'>日期不合法&nbsp;&nbsp;&nbsp;&nbsp;</font>";
				}
				//如果没有那么导入
				if(!StringUtils.isNull(errMsgStr))
				   errMsgList.add(errMsgStr+"<br>");
				else{
				   beanList.add(mdmCalendar);
					//this.save(mdmProduct);
				}
			}
			//如果没有错误，那么再写入数据库
			if(errMsgList==null || errMsgList.size()<=0){
				for(int i=0;i<beanList.size();i++)
				{
					MdmCalendar mdmProductExl=(MdmCalendar)beanList.get(i);
					this.save(mdmProductExl);
				}
				errMsgList.add("<font color='#FF0000'>导入成功 <br></font>");
			}
		 } catch (Exception e) {
			 errMsgList.add("<font color='#FF0000'>打开的模板不对 <br></font>");
			}
			return errMsgList;
		}
}
