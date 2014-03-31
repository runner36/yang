<%@page import="java.util.ArrayList"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="com.winchannel.order.model.MdmDistributorLinkman"%>
 
<%@page import="java.text.SimpleDateFormat" %>
<%@ include file="/commons/taglibs.jsp"%>
<%
	String curDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()); 
%>
<html>
	<head>
	<title>订货意向单</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
	<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
	<script language="javaScript" src="${ctx}/scripts/base.js"></script>
	<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>
	<script language="javaScript" src="${ctx}/scripts/map.js"></script>
	<script type="text/javascript" src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
	<script>
		String.prototype.trim = function() {      
		   //return this.replace(/[(^\s+)(\s+$)]/g,"");//會把字符串中間的空白符也去掉      
		   //return this.replace(/^\s+|\s+$/g,""); //      
		    return this.replace(/(^\s*)|(\s*$)/g, "");
		} 
		

		
		//选择客户联系人
		function onchangeDistLink(linkId){
			var orderTable = document.getElementById("orderTable");
			orderTable.rows[2].cells[3].innerHTML=distLinkMap.get(linkId);
		 
		}
		

	
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
		//控制时间格式
		function onTime(){
			var orderDate = document.getElementById("orderDate").value;
			var shiptoDate = document.getElementById("shiptoDate").value;
			var orderTime = orderDate.substr(0,10);
			var shiptoTime = shiptoDate.substr(0,10);
			document.getElementById("orderDate").value = orderTime;
			document.getElementById("shiptoDate").value = shiptoTime;
		}	
		</script>
		<style type="text/css">
			.list_add1{width:100%;}
			.list_add1 td { border-bottom: 1px solid #E5E5E5;padding:4px 10px; white-space: nowrap; }
			.list_add1 input{ border: 1px solid #CCCCCC; width:150px;word-wrap: break-word; word-break: normal; }
		</style>
	</head>
	<body onLoad="WindowSollAuto();sub();onTime()" onResize="WindowSollAuto()">
		<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form styleId="orderForm" action="/order/orderInfo.do?method=saveSp" method="post">
			<input type="hidden" id="itemInput" name="orderItems" value=""/>
			<input type="hidden" name="itemBrandId" value="${itemBrandId}"/>
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						订货明细
					</h4>
					<div class='MenuList'>
						<a href="salesmanOrder.do?method=list">返回</a>
					</div>
					<span class="right"></span>
				</div>
				<div class="bosom_side">
					<div class="casing">
						<div class="caput">
							<span class="left"></span><span class="right"></span>
						</div>
						<div class="viscera" id="SollAuto">
							<div class="sheet_div" style="width:100%;">
								<table class="list_add1" id="orderTable" style="display:block">
							   		 <tr>
										<td class="formTable">订单平台订货号:</td>
										<td align="left">
										    <c:out value="${orderInfo.orderCode}"/>&nbsp;
										</td>
										<td class="formTable">订货日期:</td>
										<td align="left">
										   <fmt:formatDate value="${orderInfo.orderDate}" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;
										</td>
										<td class="formTable">要求送货日期:</td>
										<td align="left">
										   <fmt:formatDate value="${orderInfo.shiptoDate}" pattern="yyyy-MM-dd"/>&nbsp;
										</td>
									</tr>
								    <tr>
										<td class="formTable" width="4%">客户编码</td>
										<td align="left" width="8%">
										    <c:out value="${orderInfo.custCode}" />&nbsp;
										 </td>
										<td class="formTable" width="4%">客户名称</td>
										<td align="left" width="8%">
											<c:out value="${orderInfo.custName}"/>&nbsp;
										</td>
										<td class="formTable" width="4%">发票类型<font color="#FF0000">＊</font></td>
										<td align="left" > 
											<c:out value="${invoiceType.itemName}" />&nbsp;
										</td>
									 </tr>
									  <tr>
										<td class="formTable" width="4%">客户联系人<font color="#FF0000">＊</font></td>
										<td align="left" width="8%"> 
											<c:out value=" ${orderInfo.custLinkman}" />&nbsp;
										<td class="formTable" width="4%">联系电话</td>
										<td align="left"> 
											<c:out value=" ${orderInfo.custTell}" />&nbsp;
										</td>
										<td class="formTable" width="4%">付款方式<font color="#FF0000">＊</font></td>
										<td align="left" >
											<c:out value="${paymentType.itemName}" />&nbsp;
										 </td> 
									</tr>
									
									<tr>
										<td class="formTable">收货客户编码<font color="#FF0000">＊</font></td>
										<td align="left">
											<c:out value="${orderInfo.shiptoCode}" />&nbsp;
										</td>
										<td class="formTable">送货地址</td>
										<td align="left">
											<c:out value="${orderInfo.distributorAddress.shiptoAdd}"/>&nbsp;
										</td>
										<td class="formTable">运输方式<font color="#FF0000">＊</font></td>
										<td align="left" width="8%">
											<c:out value="${modeOfTransportType.itemName}"/>&nbsp;
										</td> 
									</tr>
	<!--								<tr>
										<td class="formTable">送货联系人</td>
										<td align="left">
											<c:out value="${address.contact}" />&nbsp;
										</td>
										<td class="formTable">联系电话</td>
										<td align="left">
											<c:out value="${address.tel}" />&nbsp;
										</td>
										<td>&nbsp;</td>
										<td rowspan="2">${orderInfo.memoApp}&nbsp;</td> 
									</tr>
	  -->
									<tr>
										<td class="formTable">业代姓名<font color="#FF0000">＊</font></td>
										<td align="left">
											<c:out value="${orderInfo.industryName}"/>&nbsp;
										</td>
										<td class="formTable">业代手机号</td>
										<td align="left">
											<c:out value="${orderInfo.industryMobile}" />&nbsp;
										</td>
										<td class="formTable">订单备注:</td>
										<td align="left">
										<textarea name="xx" rows="1" readonly   onkeyup="this.value = this.value.substring(0, 200)" maxlength=200  style="width:100%;background-color:'#f0f0f0'">${orderInfo.memo}</textarea>
										</td>
									</tr>
									<tr>
										<td class="formTable">订单状态</td>
										<td align="left">
											<c:if test="${orderInfo.status=='20'}">请订单中心接收</c:if>
											<c:if test="${orderInfo.status=='30'}">已接收</c:if>
											<c:if test="${orderInfo.status=='40'}">订单有误已作废</c:if>
										</td>
										<td class="formTable">作废备注:</td>
										<td align="left">
										<textarea name="xx" rows="1" readonly   onkeyup="this.value = this.value.substring(0, 200)" maxlength=200  style="width:100%;background-color:'#f0f0f0'">${orderInfo.memoApp}</textarea>
										</td>
										<td class="formTable">&nbsp;</td>
										<td align="left">&nbsp;</td>
									</tr>
								</table>
								<div id="itemDiv" style="width:100%;height:240px;overflow-y:scroll">
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
	</body>
</html>
