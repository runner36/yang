<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>客户订单状态调整(管理员)</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>
		<script type="text/javascript" src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
		<script>
		function sumTotal(){
			//合计项计算
			var tbl = document.getElementById("itemTable");
			var rowSize = tbl.rows.length;
			var sumNum = 0.000,sumAmount = 0.000;
			for(var i=1;i<rowSize;i++){
				var n = Number(tbl.rows[i].cells[4].innerHTML.replace(".",""));
				var t = Number(tbl.rows[i].cells[6].innerHTML.replace(".",""));
	
				if(!isNaN(n)){
					sumNum+=n;
				}
				if(!isNaN(t)){
					sumAmount+=t;
				}
			}
			if(sumNum>0){
				sumNum = sumNum.toString();
				document.getElementById("sumNumTd").innerHTML = sumNum.substring(0,sumNum.length-3)+"."+sumNum.substring(sumNum.length-3);
			}
			if(sumAmount>0){
				sumAmount = sumAmount.toString();
				document.getElementById("sumAmountTd").innerHTML = sumAmount.substring(0,sumAmount.length-3)+"."+sumAmount.substring(sumAmount.length-3);
			}
		}
		function sub(){
			//合计项计算
			var tbl = document.getElementById("itemTable");
			var rowSize = tbl.rows.length;
			for(var i=1;i<rowSize;i++){
				tbl.rows[i].cells[4].innerHTML = formNum(tbl.rows[i].cells[4].innerHTML);
				tbl.rows[i].cells[5].innerHTML = formNum(tbl.rows[i].cells[5].innerHTML);
				tbl.rows[i].cells[6].innerHTML = formNum(tbl.rows[i].cells[6].innerHTML);
			}
			sumTotal();
		}
		//将数字保留3位小数的数字
		function formNum(val){
			var temp = val.split(".");
			if(temp.length==2){
				val = temp[0]+"."+(temp[1]+"000").substring(0,3);
			}else{
				val = val+".000";
			}
			return val;
		}
		</script>
	</head>
	<body onLoad="WindowSollAuto();sub()" onResize="WindowSollAuto()">
		<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form styleId="orderForm" action="/order/orderSuperAbolishedLog.do?method=save" method="post">
			<html:hidden property="id" />
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						客户订单状态调整(管理员)
					</h4>
					<div class='MenuList'>
						<a href="orderSuperAbolishedLog.do?method=list">返回</a>
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
									<td class="formTable">订单编号：<font color="#FF0000">＊</font></td>
									<td align="left">
										 <c:out value="${orderInfo.orderCode}"/>
									</td>
									<td class="formTable">更新状态：<font color="#FF0000">＊</font></td>
									<td align="left">
										<c:if test="${orderSuperAbolishedLog.newStatus=='20'}">请订单中心接收</c:if>
										<c:if test="${orderSuperAbolishedLog.newStatus=='30'}">已接收</c:if>
										<c:if test="${orderSuperAbolishedLog.newStatus=='40'}">订单有误已作废</c:if>
									</td>
								</tr>
								<tr>
									<td class="formTable">备注：</td>
									<td align="left">
									<c:out value="${orderSuperAbolishedLog.memo}"></c:out>
									</td>
									<td class="formTable">&nbsp;</td>
									<td align="left">&nbsp;</td>
								</tr>
							</table>				
							<table style="table-layout:word-wrap:break-word;word-break:break-all" name="tble" id="tble">
								    <tr>
										<td class="formTable">订单状态:</td>
										<td align="left">
										    <c:if test="${orderInfo.status=='20'}">请订单中心接收</c:if>
											<c:if test="${orderInfo.status=='30'}">已接收</c:if>
											<c:if test="${orderInfo.status=='40'}">订单有误已作废</c:if>
										</td>
										<td class="formTable">订货日期:</td>
										<td align="left">
										   <fmt:formatDate value="${orderInfo.orderDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
										</td>
										<td class="formTable">要求送货日期:</td>
										<td align="left">
										   <fmt:formatDate value="${orderInfo.shiptoDate}" pattern="yyyy-MM-dd"/>
										</td>
									</tr>
								    <tr>
										<td class="formTable" width="4%">客户编码:</td>
										<td align="left" width="8%">
										    <c:out value="${orderInfo.custCode}" />&nbsp;
										 </td>
										<td class="formTable" width="4%">客户名称:</td>
										<td align="left" width="8%">
											 <c:out value="${orderInfo.custName}" />&nbsp;					 
										</td>
										<td class="formTable" width="4%">发票类型:</td>
										<td align="left" > 
											<c:out value="${orderInfo.invoiceType.itemName}" />&nbsp;
										</td>
									 </tr>
									  <tr>
										<td class="formTable" width="4%">客户联系人:</td>
										<td align="left" width="8%"> 
											<c:out value=" ${orderInfo.custLinkman}" />&nbsp;
										<td class="formTable" width="4%">联系电话:</td>
										<td align="left"> 
											<c:out value=" ${orderInfo.custTell}" />&nbsp;
										</td>
										<td class="formTable" width="4%">付款方式:</td>
										<td align="left" >
											<c:out value="${orderInfo.paymentType.itemName}" />&nbsp;
										 </td> 
									</tr>
									
									<tr>
										<td class="formTable">收货客户编码:</td>
										<td align="left">
											<c:out value="${orderInfo.shiptoCode}" />&nbsp;
										</td>
										<td class="formTable">送货地址:</td>
										<td align="left">
											<c:out value="${orderInfo.distributorAddress.shiptoAdd}"/>&nbsp;
										</td>
										<td class="formTable">运输方式:</td>
										<td align="left" width="8%">
											<c:out value="${orderInfo.modeOfTransportType.itemName}"/>&nbsp;
										</td> 
									</tr>
									<tr>
										<td class="formTable">业代姓名:</td>
										<td align="left">
											<c:out value="${orderInfo.industryName}"/>&nbsp;
										</td>
										<td class="formTable">业代手机号:</td>
										<td align="left">
											<c:out value="${orderInfo.industryMobile}" />&nbsp;
										</td>
										<td class="formTable">订单备注:</td>
										<td  class="formTable">
											<c:out value="${orderInfo.memo}" />&nbsp;
										</td>
									</tr>
								</table>
								<div id="itemDiv" style="width:100%;height:180px;overflow-y:scroll">
									<table id="itemTable" border="0" cellspacing="1" cellpadding="0" bgcolor="#CCCCCC" width="100%" style="overflow-y:scroll">
										<tr align="center">
											<td width="3%" bgcolor="#99CCFF">序号</td>
											<td height="30" class="tableHeader" bgcolor="#99CCFF" width="18%">物料名称</td>
											<td class="tableHeader" bgcolor="#99CCFF" width="15%">物料编号</td>
											<td class="tableHeader" bgcolor="#99CCFF" width="10%">单位</td>
											<td class="tableHeader" bgcolor="#99CCFF" width="14%">数量</td>
											<td class="tableHeader" bgcolor="#99CCFF" width="15%">含税单价</td>
											<td class="tableHeader" bgcolor="#99CCFF" width="15%">金额(元)</td>
											<td class="tableHeader" bgcolor="#99CCFF" width="25%">备注</td>
										</tr>
																						
										<c:forEach  items="${orderInfo.orderItems}" var="info" varStatus="stauts">
											<tr>
												<td bgcolor="#FFFFFF" align="center" >${stauts.index+1}</td>
												<td height="30" bgcolor="#FFFFFF">${info.mdmProduct.prodName}</td>
												<td bgcolor="#FFFFFF">${info.mdmProduct.prodCode}</td>
												<td bgcolor="#FFFFFF">${info.unit}</td>
												<td bgcolor="#FFFFFF" onclick="CreateTextBox(this);">${info.quantity}</td>
												<td bgcolor="#FFFFFF" onclick="CreateTextBox(this);">${info.taxPrice}</td>
												<td bgcolor="#FFFFFF">${info.amount }</td>
												<td bgcolor="#FFFFFF" onclick="CreateTextBox(this);">${info.memo}</td>
											</tr>
										</c:forEach>
									</table>
								</div>
								<div style="width:100%;margin-right:15px;">
									<table border="0" cellspacing="1" cellpadding="0" bgcolor="#CCCCCC" width="100%" >
										<tr align="center">
											<td height="30" class="tableHeader" bgcolor="#99CCFF" width="3%">&nbsp;</td>
											<td height="30" class="tableHeader" bgcolor="#99CCFF" width="18%">&nbsp;</td>
											<td class="tableHeader" bgcolor="#99CCFF" width="15%">合计</td>
											<td class="tableHeader" bgcolor="#99CCFF" width="10%">&nbsp;</td>
											<td class="tableHeader" bgcolor="#99CCFF" width="14%" id="sumNumTd">&nbsp;</td>
											<td class="tableHeader" bgcolor="#99CCFF" width="15%" id="sumPriceTd">&nbsp;</td>
											<td class="tableHeader" bgcolor="#99CCFF" width="15%" id="sumAmountTd">&nbsp;</td>
											<td class="tableHeader" bgcolor="#99CCFF" width="20%">&nbsp;</td>
										</tr>
									</table>
								</div>	
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
