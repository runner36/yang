<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>我的SAP订单</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/widgets/extremecomponents/extremecomponents.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/dms.js"></script>
		<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/DateControl.js"></script>
		<script type="text/javascript" src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
		<script language="javaScript" src="${ctx}/scripts/jquery-1.2.6.min.js"></script>
		<script>
	    function query() {
			resetec();
			var form = document.orderSapForm;
			form.submit();
		}
		
		</script>
	</head>
	<body onload="WindowSollAuto()" onresize="WindowSollAuto()">
		<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/order/orderSap.do?method=list" method="post">
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						<!--TitleStrat-->
						SAP订单
						<!--TitleStrat-->
					</h4>
					<div class='MenuList'>
						<!--MenuStrat-->
						<a href="javascript:showQuery_('query_A');" id="showQuery_A" >设置查询条件</a>
						<a href="javascript:hideQuery_('query_A');" id="showQuery_A" >关闭</a>
						<a href="javascript:orderSapForm.ec_p.value=1;query()" id="query_A">查询</a>
						<a href="${ctx}/order/orderSap.do?method=importCon">导入</a>
						<a href="javascript:location.reload();">刷新</a>
						<!--MenuEnd-->
					</div>
					<span class="right"></span>
				</div>
				<div class="bosom_side">
					<div class="casing">
						<div class="caput">
							<span class="left"></span><span class="right"></span>
						</div>
						<div class="viscera" id="SollAuto">
							<div class="sheet_div">
								<div class="searchbox" id="searchbox">
								<table>
									<tr>
										<td width="1%">订单号：</td>
										<td width="1%">
											<html:text name="ec" property="$lk_custWillCode"  maxlength="50"/>
										</td>
										<td width="1%">客户意向单编号：</td>
										<td width="1%">
											<html:text name="ec" property="$lk_orderCode"  maxlength="50"/>
										</td>
										<td width="1%">内部订单编号：</td>
										<td width="1%">
											<html:text name="ec" property="$lk_innnerOrderCode"  maxlength="50"/>
										</td>
										<td width="1%">客户名称：</td>
										<td>
											<html:text name="ec" property="$lk_custName"  maxlength="200"/>
										</td>
										<td width="1%">销售组织：</td>
										<td>
											<html:text name="ec" property="$lk_saleOrg"  maxlength="200"/>
										</td>
									</tr>
									<tr>
										<td width="1%">省份：</td>
										<td>
											<html:text name="ec" property="$lk_province"  maxlength="200"/>
										</td>
										<td width="1%">物料编号：</td>
										<td>
											<html:text name="ec" property="$lk_skuCode"  maxlength="200"/>
										</td>
										<td width="1%">单价：</td>
										<td>
											<html:text name="ec" property="$lk_price"  maxlength="200"/>
										</td>
										<td width="1%">订单日期：</td>
										<td colspan="5">
											<html:text name="ec" property="$ge_orderDate" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>-<html:text name="ec" property="$le_orderDate" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>
										</td>
									</tr>
								</table>
								</div>

							<ec:table items="list" var="item" onInvokeAction="query()"
									form="orderSapForm" retrieveRowsCallback="limit"  sortRowsCallback="limit" 
									style="width:100%" tableId="ec"
									action="orderSap.do?method=list">
									<ec:exportXls fileName="我的SAP订单列表.xls" tooltip="导出EXCEL" view="xls" />
									<ec:exportCsv fileName="我的SAP订单列表.csv" tooltip="导出CSV" view="csv" />
									
								<ec:row highlightRow="true">
										<ec:column property="null" width="1%" title="操作" sortable="false" viewsDenied="xls,csv">
											<center>
												<html:link
													page="/order/orderSap.do?method=view&id=${item.id}">
													<img src="${ctx}/images/find.gif" border="0" alt="查看" title="查看" />
												</html:link>
											</center>	
										</ec:column> 
										<ec:column property="orderCode" title="客户意向单"/>
										<ec:column property="custWillCode" title="订单号"/>
										<ec:column property="custCode" title="客户编码"/>
										<ec:column property="custName" title="客户名称"/>
										<ec:column property="orderDate" title="订单日期" cell="date" format="yyyy-MM-dd"/>
										<ec:column property="skuCode" title="物料编号"/>
										<ec:column property="skuName" title="物料名称"/>
										<ec:column property="childClass" title="子品类"/>
										<ec:column property="dateFreeze" title="账期冻结"/>
										<ec:column property="quotaFreeze" title="账额冻结" />
										<ec:column property="priceFreeze" title="价格冻结"/>
										<ec:column property="orderQua" title="客户订货数量"/>
										<ec:column property="sapOrderQua" title="SAP订单数量"/>
										<ec:column property="confirmQua" title="确认数量"/>
										<ec:column property="planGoodsQua" title="按箱计交货数量"/>
										<ec:column property="factGoodsQua" title="按箱计实际交货数量"/>
										<ec:column property="sysBillQua" title="按箱计开票数量"/>
										<ec:column property="noConfirmQua" title="按箱计未确认数量"/>
										<ec:column property="noGoodsQua" title=" 按箱计未交货数量"/>
										<ec:column property="noAccountQua" title="按箱计未过帐数量"/>
										<ec:column property="closeQua" title="关闭数量（箱）"/>
										<ec:column property="price" title="单价"/>
										<ec:column property="refuseMemo" title="拒绝原因描述"/>
										<ec:column property="stockName" title="库存名称"/>
										<ec:column property="saleOrg" title="销售地区"/>
										<ec:column property="province" title="省"/>
										<ec:column property="innnerOrderCode" title="内部订单号"/>
										<ec:column property="28" title="使用原因"/>
									</ec:row>
								</ec:table>

							</div>
						</div>
						<div class="trail">
							<span class="fleft"></span><span class="fright"></span>
						</div>
					</div> 
				</div>
				<div class="bosom_bottom">
					<span class="left"></span><span class="right"></span>
				</div>
			</div>
		</html:form>
	</body>
</html>

