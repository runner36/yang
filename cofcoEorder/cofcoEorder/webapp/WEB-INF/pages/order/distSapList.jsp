<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<%@ include file="/commons/meta.jsp" %>
		<title>SAP订单（经销商）</title>
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
		<html:form action="/order/orderSap.do?method=distList" method="post">
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
						<a href="javascript:showQuery_('query_A');" id="showQuery_A" >${mr['page.common.button.setquery']}</a>
						<a href="javascript:hideQuery_('query_A');" id="hideQuery_A" >${mr['page.common.button.close']}</a>
						<a href="javascript:orderSapForm.ec_p.value=1;query()" id="query_A">查询</a>
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
										<td width="1%">客户意向单编号：</td>
										<td width="1%">
											<html:text name="ec" property="orderCode_"  maxlength="50"/>
										</td>
										<td width="1%">订单号：</td>
										<td width="1%">
											<html:text name="ec" property="custWillCode_"  maxlength="50"/>
										</td>										
										<td width="1%">客户编码：</td>
										<td width="1%">
											<html:text name="ec" property="custcode_"  maxlength="50"/>
										</td>
										<td width="1%">客户名称：</td>
										<td>
											<html:text name="ec" property="custName_"  maxlength="200"/>
										</td>
										<td width="1%">订单日期：</td>
										<td colspan="5">
											<html:text name="ec" property="orderDate_start" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>-<html:text name="ec" property="orderDate_end" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>
										</td>
									</tr>
									<tr>
										<td width="1%">销售组织：</td>
										<td>
											<html:text name="ec" property="saleOrg_"  maxlength="200"/>
										</td>
										<td width="1%">物料编号：</td>
										<td>
											<html:text name="ec" property="skuCode_"  maxlength="200"/>
										</td>
										<td width="1%">物料名称</td>
										<td>
											<html:text name="ec" property="skuName_"  maxlength="200"/>
										</td>
										
										<td width="1%">单价：</td>
										<td>
											<html:text name="ec" property="price_"  maxlength="200"/>
										</td>
										<td></td>
										<td></td>
									</tr>
								</table>
								</div>

							<ec:table items="list" var="item" onInvokeAction="query()"
									form="orderSapForm" retrieveRowsCallback="limit"  sortRowsCallback="limit" 
									style="width:100%" tableId="ec"
									action="orderSap.do?method=distList">
									<ec:exportXls fileName="SAP订单（经销商）列表.xls" tooltip="导出EXCEL" view="xls" />
									<ec:exportCsv fileName="SAP订单（经销商）列表.csv" tooltip="导出CSV" view="csv" />
									
							<ec:row highlightRow="true">
										<ec:column property="null" width="1%" title="操作" sortable="false" viewsDenied="xls,csv">
											<center>
												<html:link
													page="/order/orderSap.do?method=view&id=${item[0]}">
													<img src="${ctx}/images/find.gif" border="0" alt="查看" title="查看" />
												</html:link>
											</center>	
										</ec:column> 
										<ec:column property="1" title="销售组织"/>
										<ec:column property="2" title="大区"/>
										<ec:column property="3" title="客户"/>
										<ec:column property="4" title="客户名称"/>
										<ec:column property="5" title="销售订单日期" cell="date" format="yyyy-MM-dd"/>
										<ec:column property="6" title="客户意向单"/>
										<ec:column property="7" title="大品类描述"/>
										<ec:column property="8" title="订单号"/>
										<ec:column property="9" title="物料号"/>
										<ec:column property="10" title="物料名称" />
										<ec:column property="11" title="单价" cell="number" format="0.000" style="text-align:right"/>
										<ec:column property="12" title="按箱计订单数量" cell="number" format="0.000" style="text-align:right"/>
										<ec:column property="13" title="工厂名称" />
										<ec:column property="14" title="库存名称" />
										<ec:column property="15" title="关闭原因描述" />
										<ec:column property="16" title="按箱计客户延期数量" cell="number" format="0.000" style="text-align:right"/>
										<ec:column property="17" title="按箱计信用冻结数量" cell="number" format="0.000" style="text-align:right"/>
										<ec:column property="18" title="按箱计价格冻结数量" cell="number" format="0.000" style="text-align:right"/>
										<ec:column property="19" title="按箱计库存未满足数量" cell="number" format="0.000" style="text-align:right"/>
										<ec:column property="20" title="按箱计交货数量" cell="number" format="0.000" style="text-align:right"/>
										<ec:column property="21" title="按箱计未交货数量" cell="number" format="0.000" style="text-align:right"/>
										<ec:column property="22" title="按箱计已过帐数量" cell="number" format="0.000" style="text-align:right"/>
										<ec:column property="23" title="按箱计未过帐数量" cell="number" format="0.000" style="text-align:right"/>
										<ec:column property="24" title="按箱计开票数量" cell="number" format="0.000" style="text-align:right"/>
										<ec:column property="25" title="按箱计未开票数量" cell="number" format="0.000" style="text-align:right"/>
										<ec:column property="26" title="数据更新日期" cell="date" format="yyyy-MM-dd"/>										
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

