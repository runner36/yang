<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>SAP订单</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/dms.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/DateControl.js"></script>
		<script>
		function back() {
			window.history.go(-1);
		}
		function onTime(){
			var orderDate = document.getElementById("orderDate").value;
			var orderDateTime = orderDate.toString().substr(0,10);
			if(orderDate != ''){
				document.getElementById("orderDate").value = orderDateTime;
			}
		}		
		</script>
	</head>
<body onLoad="WindowSollAuto();onTime()" onResize="WindowSollAuto()">
		<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/order/orderSap.do?method=save" method="post">
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						SAP订单
					</h4>
					<div class='MenuList'>
						<a href="javascript:back();">返回</a>
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
								<table class="list_add">
									<tr>
									    <html:hidden property="id" />
									    <html:hidden property="orderCode" />
									    
										<td class="formTable">销售组织</td>
										<td align="left"><html:text property="saleOrg" readonly="true" style="background-color: #F0F0F0"/></td>
										<td class="formTable">大区</td>
										<td align="left"><html:text property="province" readonly="true" style="background-color: #F0F0F0"/></td>
										</tr>
								    <tr>
								    	<td class="formTable">客户</td>
										<td align="left"><html:text property="custCode" styleId="orderDate" readonly="true" style="background-color: #F0F0F0"/></td>
										<td class="formTable">客户名称</td>
										<td align="left"><html:text property="custName" readonly="true" style="background-color: #F0F0F0"/></td>
									</tr>
								    <tr>
										<td class="formTable">销售订单日期</td>
										<td align="left"><html:text property="orderDate" readonly="true" style="background-color: #F0F0F0"/></td>
										<td class="formTable">客户意向单</td>
										<td align="left"><html:text property="orderCode" readonly="true" style="background-color: #F0F0F0"/></td>
								    </tr>
								    <tr>
								   	 	<td class="formTable">大品类描述</td>
										<td align="left"><html:text property="prodBigClass" readonly="true" style="background-color: #F0F0F0"/></td>
										<td class="formTable">订单号</td>
										<td align="left"><html:text property="custWillCode" readonly="true" style="background-color: #F0F0F0"/></td>
								    </tr>
								    <tr>
										<td class="formTable">物料号</td>
										<td align="left"><html:text property="skuCode" readonly="true" style="background-color: #F0F0F0"/></td>
								   		<td class="formTable">物料名称</td>
										<td align="left"><html:text property="skuName" readonly="true" style="background-color: #F0F0F0"/></td>
								    </tr>
								    <tr>
								    	<td class="formTable">单价</td>
										<td align="left"><html:text property="price" readonly="true" style="background-color: #F0F0F0"/></td>
										<td class="formTable">按箱计订单数量</td>
										<td align="left"><html:text property="orderQua" readonly="true" style="background-color: #F0F0F0"/></td>
									</tr>
									<tr>
								    	<td class="formTable">工厂名称</td>
										<td align="left"><html:text property="factoryName" readonly="true" style="background-color: #F0F0F0"/></td>
										<td class="formTable">库存名称</td>
										<td align="left"><html:text property="stockName" readonly="true" style="background-color: #F0F0F0"/></td>
									</tr>
									<tr>
										<td class="formTable">关闭原因描述</td>
										<td align="left"><html:text property="refuseMemo" readonly="true" style="background-color: #F0F0F0"/></td>
										<td class="formTable">按箱计客户延期数量</td>
										<td align="left"><html:text property="khyqsl" readonly="true" style="background-color: #F0F0F0"/></td>
									</tr>
									<tr>
										<td class="formTable">按箱计信用冻结数量</td>
										<td align="left"><html:text property="xydjsl" readonly="true" style="background-color: #F0F0F0"/></td>
								    	<td class="formTable">按箱计价格冻结数量</td>
										<td align="left"><html:text property="jgdjsl" readonly="true" style="background-color: #F0F0F0"/></td>
								    </tr>
								    <tr>
								   		<td class="formTable">按箱计库存未满足数量</td>
										<td align="left"><html:text property="stockNoQua" readonly="true" style="background-color: #F0F0F0"/></td>
										<td class="formTable">按箱计交货数量</td>
										<td align="left"><html:text property="planGoodsQua" readonly="true" style="background-color: #F0F0F0"/></td>
						    	   </tr>
								    <tr>
										<td class="formTable">按箱计未交货数量</td>
										<td align="left"><html:text property="noGoodsQua" readonly="true" style="background-color: #F0F0F0"/></td>
								  		<td class="formTable">按箱计已过帐数量</td>
										<td align="left"><html:text property="factGoodsQua" readonly="true" style="background-color: #F0F0F0"/></td>
								    </tr>
								    <tr>
								     	<td class="formTable">按箱计未过帐数量</td>
										<td align="left"><html:text property="noAccountQua" readonly="true" style="background-color: #F0F0F0"/></td>
								   		<td class="formTable">按箱计开票数量</td>
										<td align="left"><html:text property="sysBillQua" readonly="true" style="background-color: #F0F0F0"/></td>
									
								    </tr>
								    <tr>
										<td class="formTable">按箱计未开票数量</td>
										<td align="left"><html:text property="nfkimgC" readonly="true" style="background-color: #F0F0F0"/></td>
								   		<td class="formTable">数据更新日期</td>
										<td align="left"><html:text property="dataDownLoadDate" readonly="true" style="background-color: #F0F0F0"/></td>
								    </tr>
								    
								</table>				
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
		<script type="text/javascript" src="${ctx}/scripts/validator.jsp"></script>
	</body>
</html>
