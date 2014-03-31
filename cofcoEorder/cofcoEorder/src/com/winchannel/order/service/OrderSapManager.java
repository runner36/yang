package com.winchannel.order.service;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.swing.JOptionPane;

import jxl.Sheet;
import jxl.Workbook;

import com.winchannel.base.model.BaseDictItem;
import com.winchannel.base.model.BaseEmployee;
import com.winchannel.base.model.BaseOrg;
import com.winchannel.base.model.BaseUser;
import com.winchannel.base.utils.Constants;
import com.winchannel.core.dao.HibernateEntityDao;
import com.winchannel.core.exception.BusinessException;
import com.winchannel.core.utils.DateUtils;
import com.winchannel.core.utils.ECPage;
import com.winchannel.core.utils.Page;
import com.winchannel.core.utils.StringUtils;
import com.winchannel.mdm.store.model.MdmStore;
import com.winchannel.order.model.OrderInfo;
import com.winchannel.order.model.OrderSap;
import com.winchannel.task.service.SmsSendManager;

public class OrderSapManager extends HibernateEntityDao<OrderSap> {
	private OrderInfoManager orderInfoManager;
	SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd");
	String pattern = "[0-9]+(.[0-9]+)?";
	Pattern p = Pattern.compile(pattern);//验证数字类型
	
	//通过orderCode,custWillCode得到SAP订单的相关信息
	public OrderSap getOrderSap(String orderCode, String custWillCode){
		return this.findUniqueEntity("from OrderSap where orderCode= ? and custWillCode = ?", orderCode,custWillCode);
	}
	//通过orderCode得到订单的相关信息
	public OrderInfo getOrderInfo(String code) {
		return orderInfoManager.findUniqueEntity("from OrderInfo where orderCode = ?", code);
	}
	//导入
	public String saveExcel(InputStream is, HttpServletRequest request) {
		
		StringBuilder messages = new StringBuilder("[提示信息]：<br>");
		List<OrderSap> list = new ArrayList<OrderSap>();
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
				messages.append("第").append(i+1).append("行:").append("客户意向单不能为空! ").append("<br>");
				isSuccess = false;
			}
			if (StringUtils.isBlank(StringUtils.trim(sheet.getCell(1, i).getContents().trim()))) {
				messages.append("第").append(i+1).append("行:").append("订单号不能为空! ").append("<br>");
				isSuccess = false;
			}
			if (StringUtils.isNotBlank(StringUtils.trim(sheet.getCell(4, i).getContents().trim()))) {
				try {
					sdf.parse(StringUtils.trim(sheet.getCell(4, i).getContents().trim()));
				} catch (ParseException e) {
					isSuccess = false;
					throw new BusinessException("[提示信息]：<br>第'"+(i+1)+"'行订单日期格式错误! ");
					}
			}
			if (StringUtils.isNotBlank(StringUtils.trim(sheet.getCell(11, i).getContents().trim()))) {
				if(!p.matcher(StringUtils.trim(sheet.getCell(11, i).getContents().trim())).matches()){
					isSuccess = false;
					messages.append("第").append(i+1).append("行:").append("客户订货数量不符合数字格式! ").append("<br>");
				}
			}
			if (StringUtils.isNotBlank(StringUtils.trim(sheet.getCell(12, i).getContents().trim()))) {
				if(!p.matcher(StringUtils.trim(sheet.getCell(12, i).getContents().trim())).matches()){
					isSuccess = false;
					messages.append("第").append(i+1).append("行:").append("SAP订单数量不符合数字格式! ").append("<br>");
				}
			}
			if (StringUtils.isNotBlank(StringUtils.trim(sheet.getCell(13, i).getContents().trim()))) {
				if(!p.matcher(StringUtils.trim(sheet.getCell(13, i).getContents().trim())).matches()){
					isSuccess = false;
					messages.append("第").append(i+1).append("行:").append("确认数量不符合数字格式! ").append("<br>");
				}
			}
			if (StringUtils.isNotBlank(StringUtils.trim(sheet.getCell(14, i).getContents().trim()))) {
				if(!p.matcher(StringUtils.trim(sheet.getCell(14, i).getContents().trim())).matches()){
					isSuccess = false;
					messages.append("第").append(i+1).append("行:").append("按箱计交货数量不符合数字格式! ").append("<br>");
				}
			}
			if (StringUtils.isNotBlank(StringUtils.trim(sheet.getCell(15, i).getContents().trim()))) {
				if(!p.matcher(StringUtils.trim(sheet.getCell(15, i).getContents().trim())).matches()){
					isSuccess = false;
					messages.append("第").append(i+1).append("行:").append("按箱计实际交货数量不符合数字格式! ").append("<br>");
				}
			}
			if (StringUtils.isNotBlank(StringUtils.trim(sheet.getCell(16, i).getContents().trim()))) {
				if(!p.matcher(StringUtils.trim(sheet.getCell(16, i).getContents().trim())).matches()){
					isSuccess = false;
					messages.append("第").append(i+1).append("行:").append("按箱计开票数量不符合数字格式! ").append("<br>");
				}
			}
			if (StringUtils.isNotBlank(StringUtils.trim(sheet.getCell(17, i).getContents().trim()))) {
				if(!p.matcher(StringUtils.trim(sheet.getCell(17, i).getContents().trim())).matches()){
					isSuccess = false;
					messages.append("第").append(i+1).append("行:").append(" 按箱计未确认数量不符合数字格式! ").append("<br>");
				}
			}
			if (StringUtils.isNotBlank(StringUtils.trim(sheet.getCell(18, i).getContents().trim()))) {
				if(!p.matcher(StringUtils.trim(sheet.getCell(18, i).getContents().trim())).matches()){
					isSuccess = false;
					messages.append("第").append(i+1).append("行:").append("按箱计未交货数量不符合数字格式! ").append("<br>");
				}
			}
			if (StringUtils.isNotBlank(StringUtils.trim(sheet.getCell(19, i).getContents().trim()))) {
				if(!p.matcher(StringUtils.trim(sheet.getCell(19, i).getContents().trim())).matches()){
					isSuccess = false;
					messages.append("第").append(i+1).append("行:").append("按箱计未过帐数量不符合数字格式! ").append("<br>");
				}
			}
			if (StringUtils.isNotBlank(StringUtils.trim(sheet.getCell(20, i).getContents().trim()))) {
				if(!p.matcher(StringUtils.trim(sheet.getCell(20, i).getContents().trim())).matches()){
					isSuccess = false;
					messages.append("第").append(i+1).append("行:").append("关闭数量不符合数字格式! ").append("<br>");
				}
			}
			if (StringUtils.isNotBlank(StringUtils.trim(sheet.getCell(21, i).getContents().trim()))) {
				if(!p.matcher(StringUtils.trim(sheet.getCell(21, i).getContents().trim())).matches()){
					isSuccess = false;
					messages.append("第").append(i+1).append("行:").append("单价不符合数字格式! ").append("<br>");
				}
			}
			if (isSuccess) {
				OrderInfo objOrderInfo = getOrderInfo(new String(StringUtils.trim(sheet.getCell(0,i).getContents().trim())));
				//客户意向单号
				if(null == objOrderInfo){
					messages.append("第").append(i+1).append("行:").append("客户意向单号不存在! ");
					isSuccess = false;
				}
				OrderSap orderSap = getOrderSap(StringUtils.trim(sheet.getCell(0, i).getContents().trim()),StringUtils.trim(sheet.getCell(1, i).getContents().trim()));
				if(null == orderSap){
					 orderSap = new OrderSap();
				}
				orderSap.setOrderCode(StringUtils.trim(sheet.getCell(0, i).getContents().trim()));
				orderSap.setCustWillCode(StringUtils.trim(sheet.getCell(1, i).getContents().trim()));
				orderSap.setCustCode(StringUtils.trim(sheet.getCell(2, i).getContents().trim()));
				orderSap.setCustName(StringUtils.trim(sheet.getCell(3, i).getContents().trim()));
				if (StringUtils.isNotBlank(StringUtils.trim(sheet.getCell(4, i).getContents().trim()))) {
					try {
						orderSap.setOrderDate(sdf.parse(StringUtils.trim(sheet.getCell(4, i).getContents().trim())));
						
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}else{
					orderSap.setOrderDate(null);
				}
				orderSap.setSkuCode(StringUtils.trim(sheet.getCell(5, i).getContents().trim()));
				orderSap.setSkuName(StringUtils.trim(sheet.getCell(6, i).getContents().trim()));
				orderSap.setChildClass(StringUtils.trim(sheet.getCell(7, i).getContents().trim()));
				orderSap.setDateFreeze(StringUtils.trim(sheet.getCell(8, i).getContents().trim()));
				orderSap.setQuotaFreeze(StringUtils.trim(sheet.getCell(9, i).getContents().trim()));
				orderSap.setPriceFreeze(StringUtils.trim(sheet.getCell(10, i).getContents().trim()));
				if (StringUtils.isNotBlank(StringUtils.trim(sheet.getCell(11, i).getContents().trim()))) {
					orderSap.setOrderQua(Double.parseDouble(StringUtils.trim(sheet.getCell(11, i).getContents().trim())));
				}
				if (StringUtils.isNotBlank(StringUtils.trim(sheet.getCell(12, i).getContents().trim()))) {
					orderSap.setSapOrderQua(Double.parseDouble(StringUtils.trim(sheet.getCell(12, i).getContents().trim())));
				}
				if (StringUtils.isNotBlank(StringUtils.trim(sheet.getCell(13, i).getContents().trim()))) {
					orderSap.setConfirmQua(Double.parseDouble(StringUtils.trim(sheet.getCell(13, i).getContents().trim())));
				}
				if (StringUtils.isNotBlank(StringUtils.trim(sheet.getCell(14, i).getContents().trim()))) {
					orderSap.setPlanGoodsQua(Double.parseDouble(StringUtils.trim(sheet.getCell(14, i).getContents().trim())));
				}
				if (StringUtils.isNotBlank(StringUtils.trim(sheet.getCell(15, i).getContents().trim()))) {
					orderSap.setFactGoodsQua(Double.parseDouble(StringUtils.trim(sheet.getCell(15, i).getContents().trim())));
				}
				if (StringUtils.isNotBlank(StringUtils.trim(sheet.getCell(16, i).getContents().trim()))) {
					orderSap.setSysBillQua(Double.parseDouble(StringUtils.trim(sheet.getCell(16, i).getContents().trim())));
				}
				if (StringUtils.isNotBlank(StringUtils.trim(sheet.getCell(17, i).getContents().trim()))) {
					orderSap.setNoConfirmQua(Double.parseDouble(StringUtils.trim(sheet.getCell(17, i).getContents().trim())));
				}
				if (StringUtils.isNotBlank(StringUtils.trim(sheet.getCell(18, i).getContents().trim()))) {
					orderSap.setNoGoodsQua(Double.parseDouble(StringUtils.trim(sheet.getCell(18, i).getContents().trim())));
				}
				if (StringUtils.isNotBlank(StringUtils.trim(sheet.getCell(19, i).getContents().trim()))) {
					orderSap.setNoAccountQua(Double.parseDouble(StringUtils.trim(sheet.getCell(19, i).getContents().trim())));
				}
				if (StringUtils.isNotBlank(StringUtils.trim(sheet.getCell(20, i).getContents().trim()))) {
					orderSap.setCloseQua(Double.parseDouble(StringUtils.trim(sheet.getCell(20, i).getContents().trim())));
				}
				if (StringUtils.isNotBlank(StringUtils.trim(sheet.getCell(21, i).getContents().trim()))) {
					orderSap.setPrice(Double.parseDouble(StringUtils.trim(sheet.getCell(21, i).getContents().trim())));
				}
				orderSap.setRefuseMemo(StringUtils.trim(sheet.getCell(22, i).getContents().trim()));
				orderSap.setStockName(StringUtils.trim(sheet.getCell(23, i).getContents().trim()));
				orderSap.setSaleOrg(StringUtils.trim(sheet.getCell(24, i).getContents().trim()));
				orderSap.setProvince(StringUtils.trim(sheet.getCell(25, i).getContents().trim()));
				orderSap.setInnnerOrderCode(StringUtils.trim(sheet.getCell(26, i).getContents().trim()));
				orderSap.setUseMemo(StringUtils.trim(sheet.getCell(27, i).getContents().trim()));
				orderSap.setStatus(0);
				
				BaseEmployee employee = ((BaseUser) request.getSession().getAttribute(Constants.SESSION_USER_KEY)).getBaseEmployee();
				orderSap.setCreatedDate(date);
				orderSap.setCreatedByid(employee.getEmpId());
				orderSap.setCreatedByname(employee.getEmpName());
				list.add(orderSap);
			}else {
				messages.append("<br>");
				}


		}
		
		if (isSuccess) {
			try{
				this.saveAll(list);
				return "导入成功!  ";
			}catch(Exception e){
				throw new BusinessException("导入失败!");
			}
			
		}else {
			return messages.toString();
		}
	}
	/**
	 *SAP订单列表（经销商）
	 *当前登录人所辖SAP订单
	 */
	public List distList(Page page, Long empId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT sap.ID,");
		sql.append("sap.SALE_ORG,");
		sql.append("sap.PROVINCE,");
		sql.append("dist.DIST_CODE,");
		sql.append("dist.DIST_NAME,");
		sql.append("sap.ORDER_DATE,");
		sql.append("sap.ORDER_CODE,");
		sql.append("sap.PROD_BIG_CLASS,"); 
		sql.append("sap.CUST_WILL_CODE,");
		sql.append("sap.SKU_CODE,");
		sql.append("sap.SKU_NAME,");
		sql.append("sap.PRICE,");
		sql.append("sap.ORDER_QUA,");
		sql.append("sap.FACTORY_NAME, ");
		sql.append("sap.STOCK_NAME,");
		sql.append("sap.REFUSE_MEMO,");
		sql.append("sap.KHYQSL, ");
		sql.append("sap.XYDJSL, ");
		sql.append("sap.JGDJSL, ");
		sql.append("sap.STOCK_NO_QUA,");
		sql.append("sap.PLAN_GOODS_QUA,");
		sql.append("sap.NO_GOODS_QUA,");
		sql.append("sap.FACT_GOODS_QUA,");
		sql.append("sap.NO_ACCOUNT_QUA,");
		sql.append("sap.SYS_BILL_QUA,");
		sql.append("sap.NFKIMG_C, ");
		sql.append("sap.data_download_date ");
		sql.append(" from ORDER_SAP sap ");	
		sql.append(" INNER JOIN MDM_DISTRIBUTOR dist ON sap.CUST_CODE = SUBSTRING(dist.DIST_CODE, 1, 8) ");
		sql.append(" INNER JOIN BASE_ORG org ON sap.VKGRP = LEFT(org.org_code,len(sap.vkgrp)) AND dist.ORG_ID = org.ORG_ID ");
		sql.append(" INNER JOIN MDM_PRODUCT prod ON sap.SKU_CODE = prod.PROD_CODE ");
		sql.append(" INNER JOIN BASE_DICT_ITEM dict ON dict.DICT_ITEM_ID = prod.PRODSTRU_ID AND dict.DICT_ID='prodSTRU' ");
		sql.append(" INNER JOIN mdm_org_prodgroup opr ON org.org_id = opr.org_id AND opr.groupid = dict.pi1 ");
		sql.append(" INNER JOIN MDM_DIST_EMP_PRODGROUP dpe ON dpe.DIST_ID = dist.DIST_ID AND dpe.DICT_ITEM_ID = dict.PI1 ");
		sql.append(" where 1=1 and ((dpe.EFFECTIVE_TIME<=getdate() and dpe.EXPIRY_TIME is null) or (dpe.EFFECTIVE_TIME<=getdate() and dpe.EXPIRY_TIME>getdate())) ");	
		sql.append(" and  dist.EMP_ID="+ empId);
	
		//客户意向单编号
		if (StringUtils.isNotBlank(page.get("orderCode_"))) {
			sql.append(" and sap.ORDER_CODE like '%" + page.get("orderCode_")+ "%' \n ");
		}
		//订单号
		if (StringUtils.isNotBlank(page.get("custWillCode_"))) {
			sql.append(" and sap.CUST_WILL_CODE like '%" + page.get("custWillCode_")+ "%' \n ");
		}
		//客户编码
		if (StringUtils.isNotBlank(page.get("custcode_"))) {
			sql.append(" and dist.DIST_CODE like '%" + page.get("custcode_") + "%' \n ");
		}
		//客户名称
		if (StringUtils.isNotBlank(page.get("custName_"))) {
			sql.append(" and sap.CUST_NAME like '%" + page.get("custName_") + "%' \n ");
		}
		//销售组织
		if (StringUtils.isNotBlank(page.get("saleOrg_"))) {
			sql.append(" and sap.SALE_ORG like '%" + page.get("saleOrg_") + "%' \n ");
		}
		//物料名称
		if (StringUtils.isNotBlank(page.get("skuName_"))) {
			sql.append(" and sap.SKU_NAME like '%" + page.get("skuName_") + "%' \n ");
		}
		//物料编号
		if (StringUtils.isNotBlank(page.get("skuCode_"))) {
			sql.append(" and sap.SKU_CODE like '%" + page.get("skuCode_") + "%' \n ");
		}
		//单价
		if (StringUtils.isNotBlank(page.get("price_"))) {
			sql.append(" and sap.PRICE like '%" + page.get("price_") + "%' \n ");
		}
		//订单日期
		if (StringUtils.isNotBlank(page.get("orderDate_start"))) {
			sql.append(" and sap.ORDER_DATE >='")
					.append(page.get("orderDate_start")).append("'");
		}
		if (StringUtils.isNotBlank(page.get("orderDate_end"))) {
			sql.append(" and sap.ORDER_DATE <='").append(page.get("orderDate_end"))
					.append(" 23:59:59'");
		}
		//排序
		if (StringUtils.isNotBlank(page.get(Page.SORT))) {
			sql.append(" order by " + page.get(Page.SORT));
		}
		return this.executeSqlQuery(sql.toString(), page);
	}
	/**
	 * SAP订单列表（订单岗）
	 * 查看所有
	 */
	public List orderMemberList(Page page, Long createdByid) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT sap.ID,");
		sql.append("sap.SALE_ORG,");
		sql.append("sap.PROVINCE,");
		sql.append("dist.DIST_CODE,");
		sql.append("dist.DIST_NAME,");
		sql.append("sap.ORDER_DATE,");
		sql.append("sap.ORDER_CODE,");
		sql.append("sap.PROD_BIG_CLASS,"); 
		sql.append("sap.CUST_WILL_CODE,");
		sql.append("sap.SKU_CODE,");
		sql.append("sap.SKU_NAME,");
		sql.append("sap.PRICE,");
		sql.append("sap.ORDER_QUA,");
		sql.append("sap.FACTORY_NAME, ");
		sql.append("sap.STOCK_NAME,");
		sql.append("sap.REFUSE_MEMO,");
		sql.append("sap.KHYQSL, ");
		sql.append("sap.XYDJSL, ");
		sql.append("sap.JGDJSL, ");
		sql.append("sap.STOCK_NO_QUA,");
		sql.append("sap.PLAN_GOODS_QUA,");
		sql.append("sap.NO_GOODS_QUA,");
		sql.append("sap.FACT_GOODS_QUA,");
		sql.append("sap.NO_ACCOUNT_QUA,");
		sql.append("sap.SYS_BILL_QUA,");
		sql.append("sap.NFKIMG_C, ");
		sql.append("sap.data_download_date ");
		sql.append(" from ORDER_SAP sap ");
		sql.append(" INNER JOIN MDM_DISTRIBUTOR dist ON sap.CUST_CODE = SUBSTRING(dist.DIST_CODE, 1, 8) ");
		sql.append(" INNER JOIN BASE_ORG org ON sap.VKGRP = LEFT(org.org_code,Len(sap.vkgrp)) AND dist.ORG_ID = org.ORG_ID ");
		sql.append(" where 1=1 ");	
//		sql.append(" where sap.CREATED_BYID = " + createdByid);	
		//客户意向单编号
		if (StringUtils.isNotBlank(page.get("orderCode_"))) {
			sql.append(" and sap.ORDER_CODE like '%" + page.get("orderCode_")+ "%' \n ");
		}
		//订单号
		if (StringUtils.isNotBlank(page.get("custWillCode_"))) {
			sql.append(" and sap.CUST_WILL_CODE like '%" + page.get("custWillCode_")+ "%' \n ");
		}
		//客户编码
		if (StringUtils.isNotBlank(page.get("custcode_"))) {
			sql.append(" and dist.DIST_CODE like '%" + page.get("custcode_") + "%' \n ");
		}
		//客户名称
		if (StringUtils.isNotBlank(page.get("custName_"))) {
			sql.append(" and sap.CUST_NAME like '%" + page.get("custName_") + "%' \n ");
		}
		//销售组织
		if (StringUtils.isNotBlank(page.get("saleOrg_"))) {
			sql.append(" and sap.SALE_ORG like '%" + page.get("saleOrg_") + "%' \n ");
		}
		//物料名称
		if (StringUtils.isNotBlank(page.get("skuName_"))) {
			sql.append(" and sap.SKU_NAME like '%" + page.get("skuName_") + "%' \n ");
		}
		//物料编号
		if (StringUtils.isNotBlank(page.get("skuCode_"))) {
			sql.append(" and sap.SKU_CODE like '%" + page.get("skuCode_") + "%' \n ");
		}
		//单价
		if (StringUtils.isNotBlank(page.get("price_"))) {
			sql.append(" and sap.PRICE like '%" + page.get("price_") + "%' \n ");
		}
		//订单日期
		if (StringUtils.isNotBlank(page.get("orderDate_start"))) {
			sql.append(" and sap.ORDER_DATE >='")
					.append(page.get("orderDate_start")).append("'");
		}
		if (StringUtils.isNotBlank(page.get("orderDate_end"))) {
			sql.append(" and sap.ORDER_DATE <='").append(page.get("orderDate_end"))
					.append(" 23:59:59'");
		}
		//排序
		if (StringUtils.isNotBlank(page.get(Page.SORT))) {
			sql.append(" order by " + page.get(Page.SORT));
		}
		return this.executeSqlQuery(sql.toString(), page);
	}
	
	public List orderMemberList_back(Page page, Long createdByid) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT sap.ID AS id, sap.ORDER_CODE AS orderCode, sap.CUST_WILL_CODE AS custWillCode, dist.DIST_CODE AS custCode");
		sql.append(" , dist.DIST_NAME AS custName, sap.ORDER_DATE AS orderDate, sap.SKU_CODE AS skuCode, sap.SKU_NAME AS skuName, sap.CHILD_CLASS AS childClass");
		sql.append(" , sap.DATE_FREEZE AS dateFreeze, sap.QUOTA_FREEZE AS quotaFreeze, sap.PRICE_FREEZE AS priceFreeze");
		sql.append(" , sap.ORDER_QUA AS orderQua, sap.SAP_ORDER_QUA AS sapOrderQua, sap.CONFIRM_QUA AS confirmQua");
		sql.append(" , sap.PLAN_GOODS_QUA AS planGoodsQua, sap.FACT_GOODS_QUA AS factGoodsQua, sap.SYS_BILL_QUA AS sysBillQua, sap.NO_CONFIRM_QUA AS noConfirmQua");
		sql.append(" , sap.NO_GOODS_QUA AS noGoodsQua, sap.NO_ACCOUNT_QUA AS noAccountQua, sap.CLOSE_QUA AS closeQua, sap.PRICE AS price");
		sql.append(" , sap.REFUSE_MEMO AS refuseMemo, sap.STOCK_NAME AS stockName, sap.SALE_ORG AS saleOrg");
		sql.append(" , sap.PROVINCE AS province, sap.INNNER_ORDER_CODE AS innnerOrderCode, sap.USE_MEMO AS useMemo, sap.Status AS status");
		sql.append(" , sap.CREATED_DATE AS createdDate, sap.CREATED_BYID AS createdByid, sap.CREATED_BYNAME AS createdByname");
		sql.append(" , sap.UPDATED_DATE AS updatedDate, sap.UPDATED_BYID AS updatedByid, sap.UPDATED_BYNAME AS updatedByname");
		sql.append(" from ORDER_SAP sap ");
		sql.append(" INNER JOIN MDM_DISTRIBUTOR dist ON sap.CUST_CODE = SUBSTRING(dist.DIST_CODE, 1, 8) ");
		sql.append(" INNER JOIN BASE_ORG org ON sap.VKGRP = org.ORG_CODE AND dist.ORG_ID = org.ORG_ID ");
		sql.append(" where 1=1 ");	
//		sql.append(" where sap.CREATED_BYID = " + createdByid);	
		//客户意向单编号
		if (StringUtils.isNotBlank(page.get("orderCode_"))) {
			sql.append(" and sap.ORDER_CODE like '%" + page.get("orderCode_")+ "%' \n ");
		}
		//订单号
		if (StringUtils.isNotBlank(page.get("custWillCode_"))) {
			sql.append(" and sap.CUST_WILL_CODE like '%" + page.get("custWillCode_")+ "%' \n ");
		}
		//内部订单编号
		if (StringUtils.isNotBlank(page.get("innnerOrderCode_"))) {
			sql.append(" and sap.INNNER_ORDER_CODE like '%" + page.get("innnerOrderCode_") + "%' \n ");
		}
		//客户名称
		if (StringUtils.isNotBlank(page.get("custName_"))) {
			sql.append(" and sap.CUST_NAME like '%" + page.get("custName_") + "%' \n ");
		}
		//销售组织
		if (StringUtils.isNotBlank(page.get("saleOrg_"))) {
			sql.append(" and sap.SALE_ORG like '%" + page.get("saleOrg_") + "%' \n ");
		}
		//省份
		if (StringUtils.isNotBlank(page.get("province_"))) {
			sql.append(" and sap.PROVINCE like '%" + page.get("province_") + "%' \n ");
		}
		//物料编号
		if (StringUtils.isNotBlank(page.get("skuCode_"))) {
			sql.append(" and sap.SKU_CODE like '%" + page.get("skuCode_") + "%' \n ");
		}
		//单价
		if (StringUtils.isNotBlank(page.get("price_"))) {
			sql.append(" and sap.PRICE like '%" + page.get("price_") + "%' \n ");
		}
		//订单日期
		if (StringUtils.isNotBlank(page.get("orderDate_start"))) {
			sql.append(" and sap.ORDER_DATE >='")
					.append(page.get("orderDate_start")).append("'");
		}
		if (StringUtils.isNotBlank(page.get("orderDate_end"))) {
			sql.append(" and sap.ORDER_DATE <='").append(page.get("orderDate_end"))
					.append(" 23:59:59'");
		}
		//排序
		if (StringUtils.isNotBlank(page.get(Page.SORT))) {
			sql.append(" order by " + page.get(Page.SORT));
		}
		return this.executeSqlQuery(sql.toString(), page);
	}
	/**
	 * SAP订单列表（区域）
	 * 当前登录人所属组织范围内的所有经销商的sap订单
	 */
	public List orgList(Page page, String subCode) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT sap.ID,");
		sql.append("sap.SALE_ORG,");
		sql.append("sap.PROVINCE,");
		sql.append("dist.DIST_CODE,");
		sql.append("dist.DIST_NAME,");
		sql.append("sap.ORDER_DATE,");
		sql.append("sap.ORDER_CODE,");
		sql.append("sap.PROD_BIG_CLASS,"); 
		sql.append("sap.CUST_WILL_CODE,");
		sql.append("sap.SKU_CODE,");
		sql.append("sap.SKU_NAME,");
		sql.append("sap.PRICE,");
		sql.append("sap.ORDER_QUA,");
		sql.append("sap.FACTORY_NAME, ");
		sql.append("sap.STOCK_NAME,");
		sql.append("sap.REFUSE_MEMO,");
		sql.append("sap.KHYQSL, ");
		sql.append("sap.XYDJSL, ");
		sql.append("sap.JGDJSL, ");
		sql.append("sap.STOCK_NO_QUA,");
		sql.append("sap.PLAN_GOODS_QUA,");
		sql.append("sap.NO_GOODS_QUA,");
		sql.append("sap.FACT_GOODS_QUA,");
		sql.append("sap.NO_ACCOUNT_QUA,");
		sql.append("sap.SYS_BILL_QUA,");
		sql.append("sap.NFKIMG_C, ");
		sql.append("sap.data_download_date ");
		sql.append(" from ORDER_SAP sap ");
		sql.append(" INNER JOIN MDM_DISTRIBUTOR dist ON sap.CUST_CODE = SUBSTRING(dist.DIST_CODE, 1, 8) ");
		sql.append(" INNER JOIN BASE_ORG org ON sap.VKGRP = org.ORG_CODE AND dist.ORG_ID = org.ORG_ID ");
		sql.append(" INNER JOIN BASE_DICT_ITEM geo ON dist.GEO_ID=geo.DICT_ITEM_ID AND geo.DICT_ID = 'geography'");
		sql.append(" INNER JOIN MDM_PRODUCT prod ON sap.SKU_CODE = prod.PROD_CODE ");
		sql.append(" INNER JOIN BASE_DICT_ITEM dict ON dict.DICT_ITEM_ID = prod.PRODSTRU_ID AND dict.DICT_ID='prodSTRU' ");
		sql.append(" INNER JOIN MDM_DIST_EMP_PRODGROUP dpe ON dpe.DIST_ID = dist.DIST_ID AND dpe.DICT_ITEM_ID = dict.PI1 ");
		sql.append(" where 1=1 and ((dpe.EFFECTIVE_TIME<=getdate() and dpe.EXPIRY_TIME is null) or (dpe.EFFECTIVE_TIME<=getdate() and dpe.EXPIRY_TIME>getdate())) ");	
		
		sql.append(" and org.SUB_CODE like '" + subCode+ "%' \n ");
		
		//客户意向单编号
		if (StringUtils.isNotBlank(page.get("orderCode_"))) {
			sql.append(" and sap.ORDER_CODE like '%" + page.get("orderCode_")+ "%' \n ");
		}
		//订单号
		if (StringUtils.isNotBlank(page.get("custWillCode_"))) {
			sql.append(" and sap.CUST_WILL_CODE like '%" + page.get("custWillCode_")+ "%' \n ");
		}
		//客户编码
		if (StringUtils.isNotBlank(page.get("custcode_"))) {
			sql.append(" and dist.DIST_CODE like '%" + page.get("custcode_") + "%' \n ");
		}
		//客户名称
		if (StringUtils.isNotBlank(page.get("custName_"))) {
			sql.append(" and sap.CUST_NAME like '%" + page.get("custName_") + "%' \n ");
		}
		//销售组织
		if (StringUtils.isNotBlank(page.get("saleOrg_"))) {
			sql.append(" and sap.SALE_ORG like '%" + page.get("saleOrg_") + "%' \n ");
		}
		//物料名称
		if (StringUtils.isNotBlank(page.get("skuName_"))) {
			sql.append(" and sap.SKU_NAME like '%" + page.get("skuName_") + "%' \n ");
		}
		//物料编号
		if (StringUtils.isNotBlank(page.get("skuCode_"))) {
			sql.append(" and sap.SKU_CODE like '%" + page.get("skuCode_") + "%' \n ");
		}
		//单价
		if (StringUtils.isNotBlank(page.get("price_"))) {
			sql.append(" and sap.PRICE like '%" + page.get("price_") + "%' \n ");
		}
		//订单日期
		if (StringUtils.isNotBlank(page.get("orderDate_start"))) {
			sql.append(" and sap.ORDER_DATE >='")
					.append(page.get("orderDate_start")).append("'");
		}
		if (StringUtils.isNotBlank(page.get("orderDate_end"))) {
			sql.append(" and sap.ORDER_DATE <='").append(page.get("orderDate_end"))
					.append(" 23:59:59'");
		}
		//排序
		if (StringUtils.isNotBlank(page.get(Page.SORT))) {
			sql.append(" order by " + page.get(Page.SORT));
		}
		return this.executeSqlQuery(sql.toString(), page);
	}
	

	/**
	 * SAP订单列表（业代）
	 * 根据当前登录业代，在已经维护好的“客户_产品组_业代关系维护”中查询出该业代人员负责的经销商所下的对应物料组的sap订单
	 */
	public List salesmanList(Page page, Long empId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT sap.ID,");
		sql.append("sap.SALE_ORG,");
		sql.append("sap.PROVINCE,");
		sql.append("dist.DIST_CODE,");
		sql.append("dist.DIST_NAME,");
		sql.append("sap.ORDER_DATE,");
		sql.append("sap.ORDER_CODE,");
		sql.append("sap.PROD_BIG_CLASS,"); 
		sql.append("sap.CUST_WILL_CODE,");
		sql.append("sap.SKU_CODE,");
		sql.append("sap.SKU_NAME,");
		sql.append("sap.PRICE,");
		sql.append("sap.ORDER_QUA,");
		sql.append("sap.FACTORY_NAME, ");
		sql.append("sap.STOCK_NAME,");
		sql.append("sap.REFUSE_MEMO,");
		sql.append("sap.KHYQSL, ");
		sql.append("sap.XYDJSL, ");
		sql.append("sap.JGDJSL, ");
		sql.append("sap.STOCK_NO_QUA,");
		sql.append("sap.PLAN_GOODS_QUA,");
		sql.append("sap.NO_GOODS_QUA,");
		sql.append("sap.FACT_GOODS_QUA,");
		sql.append("sap.NO_ACCOUNT_QUA,");
		sql.append("sap.SYS_BILL_QUA,");
		sql.append("sap.NFKIMG_C, ");
		sql.append("sap.data_download_date ");
		sql.append(" from ORDER_SAP sap ");	
		sql.append(" INNER JOIN MDM_DISTRIBUTOR dist ON sap.CUST_CODE = SUBSTRING(dist.DIST_CODE, 1, 8) ");
		sql.append(" INNER JOIN BASE_ORG org ON sap.VKGRP =  LEFT(org.org_code,len(sap.vkgrp)) AND dist.ORG_ID = org.ORG_ID ");
		sql.append(" INNER JOIN MDM_PRODUCT prod ON sap.SKU_CODE = prod.PROD_CODE ");
		sql.append(" INNER JOIN BASE_DICT_ITEM dict ON dict.DICT_ITEM_ID = prod.PRODSTRU_ID AND dict.DICT_ID='prodSTRU' ");
		sql.append("INNER JOIN mdm_org_prodgroup opr ON org.ORG_ID = opr.org_id AND opr.groupid = dict.PI1 ");
		sql.append(" INNER JOIN MDM_DIST_EMP_PRODGROUP dpe ON dpe.DIST_ID = dist.DIST_ID AND dpe.DICT_ITEM_ID = dict.PI1 ");
		sql.append(" where 1=1 and ((dpe.EFFECTIVE_TIME<=getdate() and dpe.EXPIRY_TIME is null) or (dpe.EFFECTIVE_TIME<=getdate() and dpe.EXPIRY_TIME>getdate())) ");	
		sql.append(" and  dpe.EMP_ID="+ empId);
		//客户意向单编号
		if (StringUtils.isNotBlank(page.get("orderCode_"))) {
			sql.append(" and sap.ORDER_CODE like '%" + page.get("orderCode_")+ "%' \n ");
		}
		//订单号
		if (StringUtils.isNotBlank(page.get("custWillCode_"))) {
			sql.append(" and sap.CUST_WILL_CODE like '%" + page.get("custWillCode_")+ "%' \n ");
		}
		//客户编码
		if (StringUtils.isNotBlank(page.get("custcode_"))) {
			sql.append(" and dist.DIST_CODE like '%" + page.get("custcode_") + "%' \n ");
		}
		//客户名称
		if (StringUtils.isNotBlank(page.get("custName_"))) {
			sql.append(" and sap.CUST_NAME like '%" + page.get("custName_") + "%' \n ");
		}
		//销售组织
		if (StringUtils.isNotBlank(page.get("saleOrg_"))) {
			sql.append(" and sap.SALE_ORG like '%" + page.get("saleOrg_") + "%' \n ");
		}
		//物料名称
		if (StringUtils.isNotBlank(page.get("skuName_"))) {
			sql.append(" and sap.SKU_NAME like '%" + page.get("skuName_") + "%' \n ");
		}
		//物料编号
		if (StringUtils.isNotBlank(page.get("skuCode_"))) {
			sql.append(" and sap.SKU_CODE like '%" + page.get("skuCode_") + "%' \n ");
		}
		//单价
		if (StringUtils.isNotBlank(page.get("price_"))) {
			sql.append(" and sap.PRICE like '%" + page.get("price_") + "%' \n ");
		}
		//订单日期
		if (StringUtils.isNotBlank(page.get("orderDate_start"))) {
			sql.append(" and sap.ORDER_DATE >='")
					.append(page.get("orderDate_start")).append("'");
		}
		if (StringUtils.isNotBlank(page.get("orderDate_end"))) {
			sql.append(" and sap.ORDER_DATE <='").append(page.get("orderDate_end"))
					.append(" 23:59:59'");
		}
		//排序
		if (StringUtils.isNotBlank(page.get(Page.SORT))) {
			sql.append(" order by " + page.get(Page.SORT));
		}
		return this.executeSqlQuery(sql.toString(), page);
	}
	
	
	public OrderInfoManager getOrderInfoManager() {
		return orderInfoManager;
	}
	public void setOrderInfoManager(OrderInfoManager orderInfoManager) {
		this.orderInfoManager = orderInfoManager;
	}
	
	
	
}
