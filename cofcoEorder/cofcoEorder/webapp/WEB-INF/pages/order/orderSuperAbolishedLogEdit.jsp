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
		function save() {
  		     orderSuperAbolishedLogForm.submit();
		}
		
		var loading = false;
		function getOrderCode() {
			var form = orderSuperAbolishedLogForm;
			var orderCode = form.orderCode.value;
			if (orderCode != '') {
				createXMLHttpRequest();
				xmlhttp.open("post", "${ctx}/order/orderSuperAbolishedLog.do?method=getOrderCode&orderCode=" + orderCode , true);
				xmlhttp.onreadystatechange = function() {
					if(xmlhttp.readyState == 4) {
						if(xmlhttp.status == 200) {
							if(xmlhttp.responseText!=null&&xmlhttp.responseText!='') {
								xmlData=xmlhttp.responseText;
								eval("var result="+xmlData+";");
								
								if(result.orderInfo.status == '20'){
									form.orderCode.value = '';
									alert(" 此订单还未审批！");	
								}else{
								//得到更新状态
								 	var obj=document.getElementById('newStatus');
							 		if(result.orderInfo.status == '30'){
							 			obj.options.add(new Option("请订单中心接收","20")); 
							 			obj.options.add(new Option("订单有误已作废","40")); 
							 		}
							 		if(result.orderInfo.status == '40'){
							 			obj.options.add(new Option("请订单中心接收","20")); 
							 			obj.options.add(new Option("已接收","30"));
							 		}
									
								//得到订单信息	
									var tb=document.getElementById('tble');
									if(result.orderInfo.status == '30'){
										tb.rows[0].cells[1].innerHTML="已接收";
									}
									if(result.orderInfo.status == '40'){
										tb.rows[0].cells[1].innerHTML="订单有误已作废";
									}
									tb.rows[0].cells[3].innerHTML=(result.orderInfo.orderDate.year+1900)+"-"+(result.orderInfo.orderDate.month+1)+"-"+(result.orderInfo.orderDate.date);
									tb.rows[0].cells[5].innerHTML=(result.orderInfo.shiptoDate.year+1900)+"-"+(result.orderInfo.shiptoDate.month+1)+"-"+(result.orderInfo.shiptoDate.date);
									tb.rows[1].cells[1].innerHTML=result.orderInfo.custCode;
									tb.rows[1].cells[3].innerHTML=result.orderInfo.custName;
									tb.rows[1].cells[5].innerHTML=result.orderInfo.invoiceName;
									tb.rows[2].cells[1].innerHTML=result.orderInfo.custLinkman;
									tb.rows[2].cells[3].innerHTML=result.orderInfo.custTell;
									tb.rows[2].cells[5].innerHTML=result.orderInfo.paymentName;
									tb.rows[3].cells[1].innerHTML=result.orderInfo.shiptoCode;
									tb.rows[3].cells[3].innerHTML=result.orderInfo.shiptoName;
									tb.rows[3].cells[5].innerHTML=result.orderInfo.modeOfTransportName;
									tb.rows[4].cells[1].innerHTML=result.orderInfo.industryName;
									tb.rows[4].cells[3].innerHTML=result.orderInfo.industryMobile;
									tb.rows[4].cells[5].innerHTML=result.orderInfo.memo;
									
								//得到订单明细
									var tbl = document.getElementById("itemTable");
									for(var i=1; i<result.items.length+1; i++){
										var newtr = tbl.insertRow(i);
										if(i%2==0){
											newtr.style.className = 'odd';
										}else{
											newtr.style.className = 'even';
										}
										newtr.style.height="30px";
										newtr.style.borderBottomColor="#CCCCCC";
										if(i%2==0){
											newtr.style.className = 'odd';
										}else{
											newtr.style.className = 'even';
										}
										newtd = newtr.insertCell(0);
										newtd.bgColor="#FFFFFF";
										newtd.innerHTML = i;
										
										newtd = newtr.insertCell(1);
										newtd.bgColor="#FFFFFF";
										newtd.innerHTML = result.items[i-1].prodName;
										
										newtd = newtr.insertCell(2);
										newtd.bgColor="#FFFFFF";
										newtd.innerHTML = result.items[i-1].prodCode;
										
										newtd = newtr.insertCell(3);
										newtd.bgColor="#FFFFFF";
										newtd.innerHTML = result.items[i-1].prodUnit;
										
										newtd = newtr.insertCell(4);
										newtd.bgColor="#FFFFFF";
										newtd.innerHTML = result.items[i-1].prodNum;
								
										newtd = newtr.insertCell(5);
										newtd.bgColor="#FFFFFF";
										newtd.innerHTML = result.items[i-1].prodPrice;
								
										newtd = newtr.insertCell(6);
										newtd.bgColor="#FFFFFF";
										newtd.innerHTML = result.items[i-1].prodAmount;
								
										newtd = newtr.insertCell(7);
										newtd.bgColor="#FFFFFF";
										newtd.innerHTML = result.items[i-1].prodMemo;
									}
									sub();
								}
								
							}
							else {
								form.orderCode.value = '';
								alert("订单编号不存在！")
							}
						}
						else {
							form.orderCode.value = '';
								alert("订单编号不存在！")
						}
					}
				};
				xmlhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
				xmlhttp.send(null);
			}
		}
		var xmlhttp;
		function createXMLHttpRequest() {
			try{
				xmlhttp=new ActiveXObject("Msxml2.XMLHTTP");
			}
			catch (e) {
				try{
					xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
				}
				catch(e) {
					try {
						xmlhttp=new XMLHttpRequest();
					}
					catch(e) {
						alert("failure");
					}
				}
			}
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
		</script>
	</head>
	<body onLoad="WindowSollAuto();getOrderCode()" onResize="WindowSollAuto()">
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
						<a href="javascript:save();">保存</a>
						<a href="orderSuperAbolishedLog.do?method=list">取消</a>
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
										<html:hidden property="orderId"/>
									    <html:text property="orderCode" onchange="getOrderCode()"/>
									</td>
									<td class="formTable">更新状态：<font color="#FF0000">＊</font></td>
									<td align="left">
										<select id="newStatus" name="newStatus"style='width:155px;'>
									   		<option value=""></option>
									    </select>
									</td>
								</tr>
								<tr>
									<td class="formTable">备注：</td>
									<td align="left">
										<html:textarea property="memo" rows="2" cols="35" onkeyup="this.value = this.value.substring(0, 200)" ></html:textarea>
									</td>
									<td class="formTable">&nbsp;</td>
									<td align="left">&nbsp;</td>
								</tr>
							</table>				
							<table style="table-layout:word-wrap:break-word;word-break:break-all" name="tble" id="tble">
								    <tr>
										<td class="formTable">订单状态:</td>
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
										<td class="formTable" width="4%">客户编码:</td>
										<td align="left" width="8%">
										    <c:out value="${orderInfo.custCode}" />&nbsp;
										 </td>
										<td class="formTable" width="4%">客户名称:</td>
										<td align="left" width="8%">
																	 
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
												<td bgcolor="#FFFFFF" align="center" ></td>
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
	<html:javascript formName="orderSuperAbolishedLogForm" staticJavascript="false" dynamicJavascript="true" cdata="false" />
		<script type="text/javascript" src="${ctx}/scripts/validator.jsp"></script>
	</body>
</html>
