package com.winchannel.order.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Expression;

import com.winchannel.base.model.BaseOrg;
import com.winchannel.core.dao.HibernateEntityDao;
import com.winchannel.core.exception.BusinessException;
import com.winchannel.core.utils.Page;
import com.winchannel.core.utils.StringUtils;
import com.winchannel.order.model.OrderInfo;
import com.winchannel.order.model.OrderItem;
import com.winchannel.order.web.OrderItemVO;
import com.winchannel.task.bean.SmsInfoBean;
import com.winchannel.task.service.SmsSendManager;

public class OrderInfoManager extends HibernateEntityDao<OrderInfo> {
	private static Logger log = LogManager.getLogger(OrderInfoManager.class);
	private SmsSendManager smsSendManager;

	public SmsSendManager getSmsSendManager() {
		return smsSendManager;
	}

	public void setSmsSendManager(SmsSendManager smsSendManager) {
		this.smsSendManager = smsSendManager;
	}

	@Override
	public List<OrderInfo> query(Page page) {
		List<OrderInfo> list = super.query(page);

		return list;
	}

	public void batchApproveById(String orderIds) throws Exception {
		// List<OrderInfo> orderLst=this.executeSqlQuery("SELECT * FROM
		// ORDER_INFO WHERE ORDER_ID IN("+orderIds+")");
		String[] arrOrderIds = orderIds.split(",");
		Long[] arrOrderIdsL = new Long[arrOrderIds.length];
		int i = 0;
		for (String orderid : arrOrderIds) {
			arrOrderIdsL[i++] = Long.parseLong(orderid);
		}
		List<OrderInfo> orderLst = this.getSession().createCriteria(
				OrderInfo.class).add(Expression.in("orderId", arrOrderIdsL))
				.add(Expression.eq("status", new Integer(20))).list();
		for (OrderInfo orderInfo : orderLst) {
			orderInfo.setStatus(30);
			super.save(orderInfo);
			SmsInfoBean smsInfo = new SmsInfoBean();
			smsInfo.setStatus(String.valueOf(orderInfo.getStatus().intValue()));
			smsInfo.setCustName(orderInfo.getCustName());
			smsInfo.setOrderCode(orderInfo.getOrderCode());
			smsInfo.setOrderNumber(orderInfo.getQuantity().toString());
			smsInfo.setPhoneNum(orderInfo.getMdmDistributorLinkman()
					.getLinkmanPhonenum());
			smsInfo.setSalesName(orderInfo.getIndustryName());
			try {
				smsSendManager.sendMsg(smsInfo);
			} catch (Exception e) {
				log.error("短信发送失败!订单号为：" + orderInfo.getOrderCode(), e);
			}
		}
		this.getSession().flush();
	}

	public OrderInfo getJSONOrderInfoByCode(String orderCode) {
		OrderInfo rtn = new OrderInfo();
		rtn = super.findUniqueEntity("from OrderInfo where status<>'10' and orderCode = ?",orderCode);
		if(null != rtn){
			rtn.setInvoiceName(rtn.getInvoiceType().getItemName());
			super.evict(rtn);// 取消托管
			List<OrderItem> items = this.getOrderItemList(rtn.getOrderId());
			for (OrderItem orderItem : items) {
				orderItem.setOrderInfo(null);
			}
			rtn.setDistributorAddress(null);
			rtn.setIndustryEmp(null);
			rtn.setMdmDistributor(null);
			rtn.setMdmDistributorLinkman(null);
			rtn.setOrderItems(null);
			rtn.setPaymentType(null);
			rtn.setProdGroup(null);
			rtn.setInvoiceType(null);
			rtn.setModeOfTransportType(null);
		}
		return rtn;
	}

	public List<OrderItemVO> getJSONOrderItemByCode(String orderCode) {
		OrderInfo rtn = super.findUniqueEntity("from OrderInfo where status<>'10' and orderCode = ?",orderCode);
		
		List<OrderItemVO> voList = new ArrayList<OrderItemVO>();
		OrderItemVO vo = null;
		if(null != rtn){
			List<OrderItem> items = this.getOrderItemList(rtn.getOrderId());
			for (OrderItem orderItem : items) {
				vo = new OrderItemVO();
				vo.setProdCode(orderItem.getMdmProduct().getProdCode());
				vo.setProdName(orderItem.getMdmProduct().getProdName());
				vo.setProdNum(orderItem.getQuantity().toString());
				vo.setProdPrice(orderItem.getTaxPrice().toString());
				vo.setProdAmount(orderItem.getAmount().toString());
				vo.setProdMemo(orderItem.getMemo());
				vo.setProdUnit(orderItem.getUnit());
				voList.add(vo);
			}
		}
		return voList;
	}
	
	public synchronized void save(Object object) {
		OrderInfo info = (OrderInfo) object;
		//取消此处更新订货时间字段 liuguangshuai 2012-9-8
		//info.setOrderDate(Calendar.getInstance().getTime());
		if (info.getOrderId() == null) {
			// 大区
			BaseOrg dq = null;
			if (info.getMdmDistributor().getBaseOrg() != null
					&& info.getMdmDistributor().getBaseOrg().getPi2() != null) {
				dq = this.findUniqueEntity(BaseOrg.class,
						"from BaseOrg where orgId = ? ", info
								.getMdmDistributor().getBaseOrg().getPi2());
			} else {
				throw new BusinessException("经销商所属大区不存在！");
			}
			if (dq == null) {
				throw new BusinessException("经销商所属大区不存在！");
			}
			String prex = dq.getOrgCode();
			String date = new SimpleDateFormat("yyMMdd").format(new Date());

			// Long size = this.findLong("select count(id) from OrderInfo where
			// orderCode like '"+date+"%'");
			Object[] seqNo = this
					.executeUniqueSqlQuery("SELECT SEQ_NO FROM ORDER_SEQUENCE WHERE ORDER_DATE=convert(VARCHAR(10),getdate(),23)");
			Long size = null;
			if (seqNo != null) {
				size = ((BigDecimal) seqNo[0]).longValue() + 1;
				this
						.executeSqlUpdate("UPDATE ORDER_SEQUENCE SET SEQ_NO = SEQ_NO+1 WHERE ORDER_DATE=convert(VARCHAR(10),getdate(),23)");
			} else {
				size = new Long(1);
				this
						.executeSqlUpdate("INSERT INTO ORDER_SEQUENCE(ORDER_DATE,SEQ_NO) VALUES(convert(VARCHAR(10),getdate(),23),"
								+ size + ")");
			}

			info.setOrderCode(prex + date + generateCode(size));

			// }else{
			// this.executeSqlUpdate("update ORDER_ITEM set ORDER_ID = NULL
			// where ORDER_ID = "+info.getOrderId());
		}
		this.flush();
		super.save(info);

	}

	private String generateCode(Long num) {
		String s = num.toString();
		String code = "";
		for (int i = 0; i < 6 - s.length(); i++) {
			code += "0";
		}
		code += s;
		return code;
	}

	/**
	 * 订单列表（区域） 根据当前登录区域经理的所属组织,查询出该组织内的经销商所下订单
	 */
	public List queryOrderListForOrg(Page page) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT o.ORDER_ID,org.PN2,org.PN4,org.PN5,o.ORDER_CODE,d.DIST_CODE,d.DIST_NAME,o.ORDER_DATE,o.SHIPTO_DATE ");
		sql.append(" ,o.QUANTITY, o.AMOUNT, o.ORDER_APPROVEDATE ");
		sql.append(" ,CASE o.STATUS WHEN 10 THEN '未提交' WHEN 20 THEN '请订单中心接收' WHEN 30 THEN '已接收' WHEN 40 THEN '订单有误已作废' END ");
		sql.append(" ,(select TOP 1 CUST_WILL_CODE FROM ORDER_SAP WHERE ORDER_CODE=o.ORDER_CODE) AS CUST_WILL_CODE ");
		sql.append(" FROM ORDER_INFO o ");
		sql.append(" INNER JOIN MDM_DISTRIBUTOR d ON o.DIST_ID=d.DIST_ID");
		sql.append(" INNER JOIN BASE_ORG_LEVEL org ON d.ORG_ID=org.ORG_ID");
		sql.append(" LEFT JOIN BASE_DICT_ITEM geo ON d.GEO_ID=geo.DICT_ITEM_ID AND geo.DICT_ID = 'geography'");
		sql.append(" INNER JOIN MDM_DIST_EMP_PRODGROUP dpe ON dpe.DIST_ID = d.DIST_ID AND dpe.EMP_ID = o.INDUSTRY_ID AND dpe.DICT_ITEM_ID = o.GROUP_ID");
		sql.append(" where o.STATUS<>'10' ");
		sql.append(" and org.SUB_CODE like '" + page.get("subCode") + "%' \n ");
		// 订单编号
		if (StringUtils.isNotBlank(page.get("orderCode_"))) {
			sql.append(" and o.ORDER_CODE like '%" + page.get("orderCode_")
					+ "%' \n ");
		}
		// 订货客户编码
		if (StringUtils.isNotBlank(page.get("distCode_"))) {
			sql.append(" and d.DIST_CODE like '%" + page.get("distCode_")
					+ "%' \n ");
		}
		// 订货客户名称
		if (StringUtils.isNotBlank(page.get("inDistId_"))) {
			sql.append(" and d.DIST_ID in (" + page.get("inDistId_") + ") \n ");
		}
		// 订单日期
		if (StringUtils.isNotBlank(page.get("orderDate_start"))) {
			sql.append(" and o.ORDER_DATE >='").append(
					page.get("orderDate_start")).append("'");
		}
		if (StringUtils.isNotBlank(page.get("orderDate_end"))) {
			sql.append(" and o.ORDER_DATE <='").append(
					page.get("orderDate_end")).append(" 23:59:59'");
		}
		// 订单状态
		if (StringUtils.isNotBlank(page.get("status_"))) {
			sql.append(" and o.STATUS =" + page.get("status_") + " \n ");
		}
		// 地区
		if (StringUtils.isNotBlank(page.get("geoCode_"))) {
			// sql.append(" and geo.DICT_ITEM_ID in (" + page.get("geoId_") + ")
			// \n ");
			sql.append(" and geo.SUB_CODE like '" + page.get("geoCode_")
					+ "%' \n ");
		}
		// 物料组
		if (StringUtils.isNotBlank(page.get("brandId_"))) {
			sql.append(" and dpe.DICT_ITEM_ID in (" + page.get("brandId_")
					+ ") \n ");
		}
		// 组织
		if (StringUtils.isNotBlank(page.get("orgCode_"))) {
			// sql.append(" and org.ORG_ID in (" + page.get("orgId_") + ") \n
			// ");
			sql.append(" and org.SUB_CODE like '" + page.get("orgCode_")
					+ "%' \n ");
		}
		// 订单日期
		if (StringUtils.isNotBlank(page.get("shiptoDate_start"))) {
			sql.append(" and o.SHIPTO_DATE >='").append(
					page.get("shiptoDate_start")).append("'");
		}
		if (StringUtils.isNotBlank(page.get("shiptoDate_end"))) {
			sql.append(" and o.SHIPTO_DATE <='").append(
					page.get("shiptoDate_end")).append(" 23:59:59'");
		}
		// 排序
		if (StringUtils.isNotBlank(page.get(Page.SORT))) {
			sql.append(" order by " + page.get(Page.SORT));
		}else{
			sql.append(" order by o.ORDER_DATE desc");
		}
		return this.executeSqlQuery(sql.toString(), page);
	}

	/**
	 * 订单列表（业代）
	 * 根据当前登录业务代表的subCode，在已经维护好的“客户_产品组_业代关系维护”中查询出该业代人员负责的经销商所下的对应物料组的订单
	 */
	public List queryOrderListForSalesman(Page page) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT o.ORDER_ID,org.PN2,org.PN4,org.PN5,o.ORDER_CODE,d.DIST_CODE,d.DIST_NAME,o.ORDER_DATE,o.SHIPTO_DATE ");
		sql.append(" ,o.QUANTITY, o.AMOUNT, o.ORDER_APPROVEDATE ");
		sql.append(" ,CASE o.STATUS WHEN 10 THEN '未提交' WHEN 20 THEN '请订单中心接收' WHEN 30 THEN '已接收' WHEN 40 THEN '订单有误已作废' END ");
		sql.append(" ,(select TOP 1 CUST_WILL_CODE FROM ORDER_SAP WHERE ORDER_CODE=o.ORDER_CODE) AS CUST_WILL_CODE ");
		sql.append(" FROM ORDER_INFO o ");
		sql.append(" INNER JOIN MDM_DISTRIBUTOR d ON o.DIST_ID=d.DIST_ID");
		sql.append(" INNER JOIN BASE_ORG org ON d.ORG_ID=org.ORG_ID");
		sql.append(" left JOIN BASE_DICT_ITEM geo ON d.GEO_ID=geo.DICT_ITEM_ID AND geo.DICT_ID = 'geography'");
		sql.append(" INNER JOIN (SELECT e.*,m.DIST_CODE FROM mdm_dist_emp_prodgroup e LEFT JOIN MDM_DISTRIBUTOR m ON e.DIST_ID = m.DIST_ID) dpe ON LEFT( dpe.DIST_CODE,8) = LEFT(d.dist_code,8) AND dpe.EMP_ID = o.INDUSTRY_ID AND dpe.DICT_ITEM_ID = o.GROUP_ID");
		sql.append(" where o.STATUS<>'10' and ((dpe.EFFECTIVE_TIME<=getdate() and dpe.EXPIRY_TIME is null) or (dpe.EFFECTIVE_TIME<=getdate() and dpe.EXPIRY_TIME>getdate())) ");
		sql.append(" and  dpe.EMP_ID=" + page.get("empId"));
		// 订单编号
		if (StringUtils.isNotBlank(page.get("orderCode_"))) {
			sql.append(" and o.ORDER_CODE like '%" + page.get("orderCode_")
					+ "%' \n ");
		}
		// 订货客户编码
		if (StringUtils.isNotBlank(page.get("distCode_"))) {
			sql.append(" and d.DIST_CODE like '%" + page.get("distCode_")
					+ "%' \n ");
		}
		// 订货客户名称
		if (StringUtils.isNotBlank(page.get("inDistId_"))) {
			sql.append(" and d.DIST_ID in (" + page.get("inDistId_") + ") \n ");
		}
		// 订单日期
		if (StringUtils.isNotBlank(page.get("orderDate_start"))) {
			sql.append(" and o.ORDER_DATE >='").append(
					page.get("orderDate_start")).append("'");
		}
		if (StringUtils.isNotBlank(page.get("orderDate_end"))) {
			sql.append(" and o.ORDER_DATE <='").append(
					page.get("orderDate_end")).append(" 23:59:59'");
		}
		// 订单状态
		if (StringUtils.isNotBlank(page.get("status_"))) {
			sql.append(" and o.STATUS =" + page.get("status_") + " \n ");
		}
		// 地区
		if (StringUtils.isNotBlank(page.get("geoCode_"))) {
			// sql.append(" and geo.DICT_ITEM_ID in (" + page.get("geoId_") + ")
			// \n ");
			sql.append(" and geo.SUB_CODE like '" + page.get("geoCode_")
					+ "%' \n ");
		}
		// 物料组
		if (StringUtils.isNotBlank(page.get("brandId_"))) {
			sql.append(" and dpe.DICT_ITEM_ID in (" + page.get("brandId_")
					+ ") \n ");
		}
		// 组织
		if (StringUtils.isNotBlank(page.get("orgCode_"))) {
			// sql.append(" and org.ORG_ID in (" + page.get("orgId_") + ") \n
			// ");
			sql.append(" and org.SUB_CODE like '" + page.get("orgCode_")
					+ "%' \n ");
		}
		// 订单日期
		if (StringUtils.isNotBlank(page.get("shiptoDate_start"))) {
			sql.append(" and o.SHIPTO_DATE >='").append(
					page.get("shiptoDate_start")).append("'");
		}
		if (StringUtils.isNotBlank(page.get("shiptoDate_end"))) {
			sql.append(" and o.SHIPTO_DATE <='").append(
					page.get("shiptoDate_end")).append(" 23:59:59'");
		}
		// 排序
		if (StringUtils.isNotBlank(page.get(Page.SORT))) {
			sql.append(" order by " + page.get(Page.SORT));
		}else{
			sql.append(" order by o.ORDER_DATE desc");
		}
		return this.executeSqlQuery(sql.toString(), page);
	}

	public Integer qryWaitToDealOrderCount(Long orderEmpId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT count(distinct (o.ORDER_ID))");
		sql.append(" FROM ORDER_INFO o ");
		sql.append(" INNER JOIN MDM_DISTRIBUTOR d ON o.DIST_ID=d.DIST_ID");
		sql.append(" INNER JOIN BASE_ORG org ON d.ORG_ID=org.ORG_ID");
		
		sql.append(" LEFT JOIN mdm_org_prodgroup mop ON org.ORG_ID = mop.org_id");
		//sql.append(" INNER JOIN MDM_DIST_ORDEREMP_PRODGROUP dpo ON dpo.ORG_ID = d.ORG_ID AND dpo.DICT_ITEM_ID = o.GROUP_ID ");
		//sql.append(" where o.STATUS=20 ");
		//sql.append(" and   o.STATUS<>'10'  and ((dpo.EFFECTIVE_TIME<=getdate() and dpo.EXPIRY_TIME is null) or (dpo.EFFECTIVE_TIME<=getdate() and dpo.EXPIRY_TIME>getdate())) ");
		sql.append(" INNER JOIN (");
		sql.append(" SELECT dpo.*,o.SUB_CODE FROM ");
		sql.append(" MDM_DIST_ORDEREMP_PRODGROUP dpo ");
		sql.append(" INNER JOIN BASE_ORG o ON o.ORG_ID=dpo.ORG_ID");
		sql.append(" and ((dpo.EFFECTIVE_TIME<=getdate() and dpo.EXPIRY_TIME is null) or (dpo.EFFECTIVE_TIME<=getdate() and dpo.EXPIRY_TIME>getdate()))"); 
		sql.append(" ) dpo ON  org.SUB_CODE  LIKE dpo.SUB_CODE+'%'  AND dpo.DICT_ITEM_ID = o.GROUP_ID ");// AND mop.groupId = dpo.dict_item_id
		sql.append(" where o.STATUS='20' ");
		sql.append(" and  dpo.EMP_ID=" + orderEmpId);
		System.out.println("qryWaitToDealOrderCount:"+sql.toString());
		List<Object[]> lst = this.executeSqlQuery(sql.toString());
		Object[] objArray = lst.get(0);
		return (Integer) objArray[0];
	}

	/**
	 * 订单列表（订单岗）
	 * 根据当前登录订单员的subCode，在已经维护好的“订单员_组织_产品关系维护”中查询出该订单员对应组织下的经销商所下对应物料组的订单
	 */
	public List queryOrderListForOrderMember(Page page) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT distinct o.ORDER_ID,org.PN2,org.PN4,org.PN5,o.ORDER_CODE,d.DIST_CODE,d.DIST_NAME,o.ORDER_DATE,o.SHIPTO_DATE ");
		sql.append(" ,o.QUANTITY, o.AMOUNT ");
		sql.append(" ,CASE o.STATUS WHEN 10 THEN '未提交' WHEN 20 THEN '请订单中心接收' WHEN 30 THEN '已接收' WHEN 40 THEN '订单有误已作废' END ");
		sql.append(" ,(select TOP 1 CUST_WILL_CODE FROM ORDER_SAP WHERE ORDER_CODE=o.ORDER_CODE) AS CUST_WILL_CODE ");
		sql.append(" FROM ORDER_INFO o ");
		sql.append(" INNER JOIN MDM_DISTRIBUTOR d ON o.DIST_ID=d.DIST_ID");
		sql.append(" INNER JOIN BASE_ORG org ON d.ORG_ID=org.ORG_ID");
		sql.append(" LEFT JOIN mdm_org_prodgroup mop ON org.ORG_ID = mop.org_id");
		sql.append(" left JOIN BASE_DICT_ITEM geo ON d.GEO_ID=geo.DICT_ITEM_ID AND geo.DICT_ID = 'geography'");
		
//		sql.append(" INNER JOIN MDM_DIST_ORDEREMP_PRODGROUP dpo ON dpo.ORG_ID = d.ORG_ID AND dpo.DICT_ITEM_ID = o.GROUP_ID ");
//		sql.append(" where o.STATUS<>'10'  and ((dpo.EFFECTIVE_TIME<=getdate() and dpo.EXPIRY_TIME is null) or (dpo.EFFECTIVE_TIME<=getdate() and dpo.EXPIRY_TIME>getdate())) ");
		sql.append(" INNER JOIN (");
		sql.append(" SELECT dpo.*,o.SUB_CODE FROM ");
		sql.append(" MDM_DIST_ORDEREMP_PRODGROUP dpo ");
		sql.append(" INNER JOIN BASE_ORG o ON o.ORG_ID=dpo.ORG_ID");
		sql.append(" and ((dpo.EFFECTIVE_TIME<=getdate() and dpo.EXPIRY_TIME is null) or (dpo.EFFECTIVE_TIME<=getdate() and dpo.EXPIRY_TIME>getdate()))"); 
		sql.append(" ) dpo ON  org.SUB_CODE  LIKE dpo.SUB_CODE+'%'  AND dpo.DICT_ITEM_ID = o.GROUP_ID    "); //--AND mop.groupId = dpo.dict_item_id
		sql.append(" where o.STATUS<>'10' ");
		
		sql.append(" and  dpo.EMP_ID=" + page.get("empId"));
		// 订单编号
		if (StringUtils.isNotBlank(page.get("orderCode_"))) {
			sql.append(" and o.ORDER_CODE like '%" + page.get("orderCode_")
					+ "%' \n ");
		}
		// 订货客户编码
		if (StringUtils.isNotBlank(page.get("distCode_"))) {
			sql.append(" and d.DIST_CODE like '%" + page.get("distCode_")
					+ "%' \n ");
		}
		// 订货客户名称
		if (StringUtils.isNotBlank(page.get("inDistId_"))) {
			sql.append(" and d.DIST_ID in (" + page.get("inDistId_") + ") \n ");
		}
		// 订单日期
		if (StringUtils.isNotBlank(page.get("orderDate_start"))) {
			sql.append(" and o.ORDER_DATE >='").append(
					page.get("orderDate_start")).append("'");
		}
		if (StringUtils.isNotBlank(page.get("orderDate_end"))) {
			sql.append(" and o.ORDER_DATE <='").append(
					page.get("orderDate_end")).append(" 23:59:59'");
		}
		// 订单状态
		if (StringUtils.isNotBlank(page.get("status_"))) {
			sql.append(" and o.STATUS =" + page.get("status_") + " \n ");
		}
		// 地区
		if (StringUtils.isNotBlank(page.get("geoCode_"))) {
			// sql.append(" and geo.DICT_ITEM_ID in (" + page.get("geoId_") + ")
			// \n ");
			sql.append(" and geo.SUB_CODE like '" + page.get("geoCode_")
					+ "%' \n ");
		}
		// 物料组
		if (StringUtils.isNotBlank(page.get("brandId_"))) {
			sql.append(" and dpo.DICT_ITEM_ID in (" + page.get("brandId_")
					+ ") \n ");
		}
		// 组织
		if (StringUtils.isNotBlank(page.get("orgCode_"))) {
			// sql.append(" and org.ORG_ID in (" + page.get("orgId_") + ") \n
			// ");
			sql.append(" and org.SUB_CODE like '" + page.get("orgCode_")
					+ "%' \n ");
		}
		// 订单日期
		if (StringUtils.isNotBlank(page.get("shiptoDate_start"))) {
			sql.append(" and o.SHIPTO_DATE >='").append(
					page.get("shiptoDate_start")).append("'");
		}
		if (StringUtils.isNotBlank(page.get("shiptoDate_end"))) {
			sql.append(" and o.SHIPTO_DATE <='").append(
					page.get("shiptoDate_end")).append(" 23:59:59'");
		}
		// 排序
		if (StringUtils.isNotBlank(page.get(Page.SORT))) {
			sql.append(" order by " + page.get(Page.SORT));
		}else{
			sql.append(" order by o.ORDER_DATE desc");
		}
		//System.out.println("queryOrderListForOrderMember:"+sql.toString());
		return this.executeSqlQuery(sql.toString(), page);
	}

	/**
	 * 订单明细列表（订单岗）
	 * 
	 */
	public List queryOrderItemListForOrderMember(Page page) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT distinct o.ORDER_ID,org.PN2,org.PN4,org.PN5,o.ORDER_CODE,d.DIST_CODE,d.DIST_NAME,o.ORDER_DATE,o.SHIPTO_DATE,CASE o.Status WHEN 10 THEN '未提交' WHEN 20 THEN '请订单中心接收' WHEN 30 THEN '已接收' WHEN 40 THEN '订单有误已作废' END,");
		sql.append(" p.PROD_CODE,p.PROD_NAME,oi.QUANTITY,oi.TAX_PRICE,oi.AMOUNT,oi.MEMO,");
		sql.append(" oi.unit,o.shipto_code,o.shipto_name,o.memo mainMemo,oi.ID");
		sql.append(" FROM ORDER_INFO o INNER JOIN ORDER_ITEM oi ON o.ORDER_ID=oi.ORDER_ID");
		sql.append(" INNER JOIN MDM_PRODUCT p ON oi.PROD_ID=p.PROD_ID");
		sql.append(" INNER JOIN MDM_DISTRIBUTOR d ON o.DIST_ID=d.DIST_ID");
		sql.append(" INNER JOIN BASE_ORG org ON d.ORG_ID=org.ORG_ID");
		sql.append(" LEFT JOIN mdm_org_prodgroup mop ON org.ORG_ID = mop.org_id");
		sql.append(" left JOIN BASE_DICT_ITEM geo ON d.GEO_ID=geo.DICT_ITEM_ID AND geo.DICT_ID = 'geography'");
//		sql.append(" INNER JOIN MDM_DIST_ORDEREMP_PRODGROUP dpo ON dpo.ORG_ID = d.ORG_ID AND dpo.DICT_ITEM_ID = o.GROUP_ID ");
//		sql.append(" where o.STATUS<>'10'  and ((dpo.EFFECTIVE_TIME<=getdate() and dpo.EXPIRY_TIME is null) or (dpo.EFFECTIVE_TIME<=getdate() and dpo.EXPIRY_TIME>getdate())) ");
		sql.append(" INNER JOIN (");
		sql.append(" SELECT dpo.*,o.SUB_CODE FROM ");
		sql.append(" MDM_DIST_ORDEREMP_PRODGROUP dpo ");
		sql.append(" INNER JOIN BASE_ORG o ON o.ORG_ID=dpo.ORG_ID");
		sql.append(" and ((dpo.EFFECTIVE_TIME<=getdate() and dpo.EXPIRY_TIME is null) or (dpo.EFFECTIVE_TIME<=getdate() and dpo.EXPIRY_TIME>getdate()))"); 
		sql.append(" ) dpo ON  org.SUB_CODE LIKE dpo.SUB_CODE+'%'  AND dpo.DICT_ITEM_ID = o.GROUP_ID ");
		sql.append(" where o.STATUS<>'10' ");
		
		sql.append(" and  dpo.EMP_ID=" + page.get("empId"));

		// 订单编号
		if (StringUtils.isNotBlank(page.get("orderCode_"))) {
			sql.append(" and o.ORDER_CODE like '%" + page.get("orderCode_")
					+ "%' \n ");
		}
		// 订货客户编码
		if (StringUtils.isNotBlank(page.get("distCode_"))) {
			sql.append(" and d.DIST_CODE like '%" + page.get("distCode_")
					+ "%' \n ");
		}
		// 订货客户名称
		if (StringUtils.isNotBlank(page.get("inDistId_"))) {
			sql.append(" and d.DIST_ID in (" + page.get("inDistId_") + ") \n ");
		}
		// 订单日期
		if (StringUtils.isNotBlank(page.get("orderDate_start"))) {
			sql.append(" and o.ORDER_DATE >='").append(
					page.get("orderDate_start")).append("'");
		}
		if (StringUtils.isNotBlank(page.get("orderDate_end"))) {
			sql.append(" and o.ORDER_DATE <='").append(
					page.get("orderDate_end")).append(" 23:59:59'");
		}
		// 订单状态
		if (StringUtils.isNotBlank(page.get("status_"))) {
			sql.append(" and o.STATUS =" + page.get("status_") + " \n ");
		}
		// 地区
		if (StringUtils.isNotBlank(page.get("geoCode_"))) {
			// sql.append(" and geo.DICT_ITEM_ID in (" + page.get("geoId_") + ")
			// \n ");
			sql.append(" and geo.SUB_CODE like '" + page.get("geoCode_")
					+ "%' \n ");
		}
		// 物料组
		if (StringUtils.isNotBlank(page.get("brandId_"))) {
			sql.append(" and dpo.DICT_ITEM_ID in (" + page.get("brandId_")
					+ ") \n ");
		}
		// 组织
		if (StringUtils.isNotBlank(page.get("orgCode_"))) {
			// sql.append(" and org.ORG_ID in (" + page.get("orgId_") + ") \n
			// ");
			sql.append(" and org.SUB_CODE like '" + page.get("orgCode_")
					+ "%' \n ");
		}
		// 送货日期
		if (StringUtils.isNotBlank(page.get("shiptoDate_start"))) {
			sql.append(" and o.SHIPTO_DATE >='").append(
					page.get("shiptoDate_start")).append("'");
		}
		if (StringUtils.isNotBlank(page.get("shiptoDate_end"))) {
			sql.append(" and o.SHIPTO_DATE <='").append(
					page.get("shiptoDate_end")).append(" 23:59:59'");
		}
		// 排序
		if (StringUtils.isNotBlank(page.get(Page.SORT))) {
			sql.append(" order by " + page.get(Page.SORT));
		}else{
			sql.append(" order by o.ORDER_DATE desc,oi.ID desc");
		}
		//System.out.println("queryOrderItemListForOrderMember:"+sql.toString());
		return this.executeSqlQuery(sql.toString(), page);
	}

	public OrderInfo getOrderInfo(Long orderId) {
		String queryString = "from OrderInfo where orderId=" + orderId;
		return this.findUniqueEntity(queryString);
	}

	public List<OrderItem> getOrderItemList(Long orderId) {
		String queryString = "from OrderItem where orderInfo.orderId="
				+ orderId + " order by id";
		return this.find(queryString);
	}
}
