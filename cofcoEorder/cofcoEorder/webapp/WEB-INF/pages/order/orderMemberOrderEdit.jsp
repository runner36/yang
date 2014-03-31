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
		String.prototype.trim = function() {      
		   //return this.replace(/[(^\s+)(\s+$)]/g,"");//會把字符串中間的空白符也去掉      
		   //return this.replace(/^\s+|\s+$/g,""); //      
		    return this.replace(/(^\s*)|(\s*$)/g, "");
		} 
		function save() {			
			var tbl = document.getElementById("itemTable");
			var size = tbl.rows.length;
			var data = "{'orderItemInfo':["
			for(var i=1;i<size;i++){
				var tr = tbl.rows[i];
				var info = "{'id':'"+tr.cells[9].innerHTML+"','memoOrder':'"+tr.cells[8].innerHTML+"'},";
				data+=info;
			}
			data = data.substring(0,data.length-1);
			data+="]}";
			
			var target = document.getElementById("orderMemberOrderForm");
			document.getElementById("itemInput").value=data;
			target.submit();
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
		
		function showInfo(){
			var tbl = document.getElementById("orderTable");
			if(tbl.style.display!='block'){
				tbl.style.display = 'block';
				document.getElementById("showOrderHeader").innerText = "隐藏";
				document.getElementById("itemDiv").style.height="180px";
			}else{
				tbl.style.display = 'none';
				document.getElementById("showOrderHeader").innerText = "显示";
				document.getElementById("itemDiv").style.height="380px";
			}
		}
		
		//创建录入框
		function CreateTextBox(element){
			var editState = element.getAttribute("EditState");
			if(editState!="true"){
				var textBox = document.createElement("INPUT");
				textBox.type = "text";
				textBox.value=element.innerHTML;
				element.innerHTML="";
				element.appendChild(textBox);
				
				//失去焦点时处理
				textBox.onblur = function (){
					if(checkCellData(this)){
						var vl = this.value;
						if(element.cellIndex==5||element.cellIndex==6){
							vl = formNum(vl);
						}
						element.innerHTML = vl;
						element.setAttribute("EditState", "false");
						
						//计算金额
						//var amount = calculate(element.parentNode.cells[5].innerHTML,element.parentNode.cells[4].innerHTML);
						//element.parentNode.cells[6].innerHTML=amount;						
						//sumTotal();
						
					}
				}
				textBox.style.width="100%";
				textBox.style.height="100%";
				textBox.focus();
				textBox.select();
				element.setAttribute("EditState", "true");
			}
		}
		
		function checkCellData(obj){
			//预留			
			return true;
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
		<html:form styleId="orderMemberOrderForm" action="/order/orderMemberOrder.do?method=save" method="post">
			<input type="hidden" id="itemInput" name="orderItems" value=""/>
			<input type="hidden" id="orderId" name="orderId" value="${orderInfo.orderId}"/>
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						订货意向单
					</h4>
					<div class='MenuList'>
						<a href="javascript:save();">保存</a>
						<a href="javascript:showInfo();" id="showOrderHeader">隐藏</a>
						<a href="${actionPath}">返回</a>						
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
								<table class="list_add1"  id="orderTable" style="display:block">
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
										<td align="left">&nbsp;
										<textarea name="xx" rows="1" readonly   onkeyup="this.value = this.value.substring(0, 200)" maxlength=200  style="width:100%;background-color:'#f0f0f0'">${orderInfo.memo}</textarea>
										</td>
									</tr>
									<tr>
										<td class="formTable">订单状态</td>
										<td align="left">											
											<select name="status" <c:if test="${orderInfo.status!='20'}"> disabled="disabled" </c:if>  style="width: 110px" id="status" >
												<%-- <option value="10" <c:if test="${orderInfo.status=='10'}">selected="true"</c:if>>草稿</option> --%>
												<option value="20" <c:if test="${orderInfo.status=='20'}">selected="true"</c:if>>请订单中心接收</option>
												<option value="30" <c:if test="${orderInfo.status=='30'}">selected="true"</c:if>>已接收</option>
												<option value="40" <c:if test="${orderInfo.status=='40'}">selected="true"</c:if>>订单有误已作废</option>
											</select>
										</td>
										<td class="formTable">作废备注:</td>
										<td align="left"><textarea name="memoApp" rows="2" onkeyup="this.value = this.value.substring(0, 200)" maxlength=200 <c:if test="${(orderInfo.status=='30')||(orderInfo.status=='40')}"> style="background-color:'#f0f0f0'" readonly </c:if> style="width:100%">${orderInfo.memoApp}</textarea></td>
										<td class="formTable">审批备注:</td>
										<td align="left"><textarea name="memoOrder" rows="2" onkeyup="this.value = this.value.substring(0, 200)" maxlength=200  style="width:130%">${orderInfo.memoOrder}</textarea></td>
									</tr>
								</table>
								<div  id="itemDiv" style="width:100%;height:180px;overflow-y:scroll">
									<table id="itemTable" border="0" cellspacing="1" cellpadding="0" bgcolor="#CCCCCC" width="100%" style="overflow-y:scroll">
										<tr align="center">
											<td class="tableHeader" bgcolor="#99CCFF" width="3%">序号</td>
											<td class="tableHeader" bgcolor="#99CCFF" width="8%">物料编号</td>
											<td height="30" class="tableHeader" bgcolor="#99CCFF" width="15%">物料名称</td>
											<td class="tableHeader" bgcolor="#99CCFF" width="3%">单位</td>
											<td class="tableHeader" bgcolor="#99CCFF" width="5%">数量</td>
											<td class="tableHeader" bgcolor="#99CCFF" width="9%">含税单价</td>
											<td class="tableHeader" bgcolor="#99CCFF" width="9%">金额(元)</td>
											<td class="tableHeader" bgcolor="#99CCFF" width="20%">备注</td>
											<td class="tableHeader" bgcolor="#99CCFF" width="20%">审批备注</td>
											<td class="tableHeader" bgcolor="#99CCFF" width="1px" style="display:none">id</td>
										</tr>
										<c:forEach  items="${voList}" var="info" varStatus="stauts">
											<tr>
												<td bgcolor="#FFFFFF" align="center" >${stauts.index+1}</td>
												<td bgcolor="#FFFFFF" height="30">${info.prodCode}</td>
												<td bgcolor="#FFFFFF">${info.prodName}</td>
												<td bgcolor="#FFFFFF">${info.prodUnit}</td>
												<td bgcolor="#FFFFFF">${info.prodNum}</td>
												<td bgcolor="#FFFFFF">${info.prodPrice}</td>
												<td bgcolor="#FFFFFF">${info.prodAmount }</td>
												<td bgcolor="#FFFFFF">${info.prodMemo}</td>
												<td bgcolor="#FFFFFF" onclick="CreateTextBox(this);">${info.memoOrder}</td>
												<td style="display:none">${info.id}</td>
											</tr>
										</c:forEach>
									</table>
								</div>
								<div style="width:100%;margin-right:15px;">
									<table border="0" cellspacing="1" cellpadding="0" bgcolor="#CCCCCC" width="100%" >
										<tr align="center">
											<td class="tableHeader" bgcolor="#99CCFF" width="3%">&nbsp;</td>
											<td class="tableHeader" bgcolor="#99CCFF" width="8%">&nbsp;</td>
											<td height="30" class="tableHeader" bgcolor="#99CCFF" width="15%">合计</td>
											<td class="tableHeader" bgcolor="#99CCFF" width="3%">&nbsp;</td>
											<td class="tableHeader" bgcolor="#99CCFF" width="5%" id="sumNumTd"></td>
											<td class="tableHeader" bgcolor="#99CCFF" width="9%">&nbsp;</td>
											<td class="tableHeader" bgcolor="#99CCFF" width="9%" id="sumAmountTd"></td>
											<td class="tableHeader" bgcolor="#99CCFF" width="20%">&nbsp;</td>
											<td class="tableHeader" bgcolor="#99CCFF" width="20%">&nbsp;</td>
											<td class="tableHeader" bgcolor="#99CCFF" width="1px" style="display:none">id</td>
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
