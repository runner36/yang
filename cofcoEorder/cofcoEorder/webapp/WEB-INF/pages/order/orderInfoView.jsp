<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%
%>
<html>
	<head>
	<title>订货意向单明细</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
	<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
	<script language="javaScript" src="${ctx}/scripts/base.js"></script>
	<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>
	<script type="text/javascript" src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
	<script language="javascript">
		function cancel(){
			var target = document.getElementById("orderInfoForm");
			target.action = "${ctx}/order/orderInfo.do?method=listSp";
			target.submit();
		}
		
		function sumTotal(){
			//合计项计算
			var tbl = document.getElementById("itemTable");
			var rowSize = tbl.rows.length;
			var sumNum = 0.000,sumAmount = 0.000;
			for(var i=1;i<rowSize;i++){
				var n = Number(tbl.rows[i].cells[3].innerHTML.replace(".",""));
				var t = Number(tbl.rows[i].cells[5].innerHTML.replace(".",""));
	
				if(!isNaN(n)){
					sumNum+=n;
				}
				if(!isNaN(t)){
					sumAmount+=t;
				}
			}
			if(sumNum>0){
				sumNum = sumNum.toString();
				sumNum = '0000'.substring(0, (4 - sumNum.toString().length)) + sumNum;
				document.getElementById("sumNumTd").innerHTML = sumNum.substring(0,sumNum.length-3)+"."+sumNum.substring(sumNum.length-3);
			}
			if(sumAmount>0){
				sumAmount = sumAmount.toString();
				sumAmount = '0000'.substring(0, (4 - sumAmount.toString().length)) + sumAmount;
				document.getElementById("sumAmountTd").innerHTML = sumAmount.substring(0,sumAmount.length-3)+"."+sumAmount.substring(sumAmount.length-3);
			}
		}
		
	</script>
	<style type="text/css">
			.list_add1{width:100%;}
			.list_add1 td { border-bottom: 1px solid #E5E5E5;padding:4px 10px; white-space: nowrap; }
			.list_add1 input, .list_add1 select { border: 1px solid #CCCCCC; width:150px;}
		</style>
	</head>
	<body onLoad="WindowSollAuto();sumTotal();" onResize="WindowSollAuto()">
		<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form styleId="orderInfoForm" action="/order/orderInfo.do?method=listSp" method="post">
			<input type="hidden" id="itemInput" name="orderItems" value=""/>
			<input type="hidden" name="itemBrandId" value="${itemBrandId}"/>
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						订货意向单
					</h4>
					<div class='MenuList'>
						<a href="javascript:cancel();">返回</a>
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
								<table class="list_add1"  style="table-layout:word-wrap:break-word;word-break:break-all">
								    <tr>
										<td class="formTable">订单平台订货号:</td>
										<td align="left">
										    <c:out value="${orderInfo.orderCode}"/>
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
											<c:out value="${orderInfo.invoiceType.itemName}" />&nbsp;
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
											<c:out value="${orderInfo.paymentType.itemName}" />&nbsp;
										 </td> 
									</tr>
									
									<tr>
										<td class="formTable">收货客户编码<font color="#FF0000">＊</font></td>
										<td align="left">
											<c:out value="${orderInfo.shiptoCode}" />&nbsp;
										</td>
										<td class="formTable">送货地址</td>
										<td align="left">
											<%-- <c:out value="${orderInfo.distributorAddress.shiptoAdd}"/>&nbsp; --%>
											<c:out value="${orderInfo.distributorAddress.shiptoAdd}"/>&nbsp;
										</td>
										<td class="formTable">运输方式<font color="#FF0000">＊</font></td>
										<td align="left" width="8%">
											<c:out value="${orderInfo.modeOfTransportType.itemName}"/>&nbsp;
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
										<td  class="formTable">
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
								<div style="width:100%;height:240px;overflow-y:scroll;">
									<table id="itemTable" border="0" cellspacing="1" cellpadding="0" bgcolor="#CCCCCC" width="100%" style="overflow-y:scroll">
										<tr align="center">
											<td class="tableHeader" bgcolor="#99CCFF" width="15%">物料编号</td>
											<td height="30" class="tableHeader" bgcolor="#99CCFF" width="18%">物料名称</td>
											<td class="tableHeader" bgcolor="#99CCFF" width="9%">单位</td>
											<td class="tableHeader" bgcolor="#99CCFF" width="10%">数量</td>
											<td class="tableHeader" bgcolor="#99CCFF" width="10%">含税单价</td>
											<td class="tableHeader" bgcolor="#99CCFF" width="14%">金额(元)</td>
											<td class="tableHeader" bgcolor="#99CCFF" width="20%">备注</td>
										</tr>
										<c:forEach  items="${voList}" var="info">
											<tr>
												<td bgcolor="#FFFFFF" height="30">${info.prodCode}</td>
												<td bgcolor="#FFFFFF">${info.prodName}</td>
												<td bgcolor="#FFFFFF">${info.prodUnit}</td>
												<td bgcolor="#FFFFFF">${info.prodNum}</td>
												<td bgcolor="#FFFFFF">${info.prodPrice}</td>
												<td bgcolor="#FFFFFF">${info.prodAmount }</td>
												<td bgcolor="#FFFFFF">${info.prodMemo}</td>
											</tr>
										</c:forEach>
									</table>
								</div>
								<div style="width:100%;margin-right:15px;">
									<table border="0" cellspacing="1" cellpadding="0" bgcolor="#CCCCCC" width="100%" >
										<tr align="center">
											<td height="30" class="tableHeader" bgcolor="#99CCFF" width="15%">&nbsp;</td>
											<td class="tableHeader" bgcolor="#99CCFF" width="18%">合计</td>
											<td class="tableHeader" bgcolor="#99CCFF" width="9%">&nbsp;</td>
											<td class="tableHeader" bgcolor="#99CCFF" width="10%" id="sumNumTd">&nbsp;</td>
											<td class="tableHeader" bgcolor="#99CCFF" width="10%">&nbsp;</td>
											<td class="tableHeader" bgcolor="#99CCFF" width="14%" id="sumAmountTd">&nbsp;</td>
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
