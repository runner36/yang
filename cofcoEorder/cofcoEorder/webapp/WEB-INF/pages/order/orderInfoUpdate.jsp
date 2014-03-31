<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="com.winchannel.order.model.MdmDistributorLinkman"%>
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
		
		function save() {
			if(!checkDatas()){
				return;
			}
			var tbl = document.getElementById("itemTable");
			var size = tbl.rows.length;
			var data = "{'dataInfo':["
			for(var i=1;i<size;i++){
				var tr = tbl.rows[i];
				var info = "{'prodName':'"+tr.cells[1].innerHTML+"','prodCode':'"+tr.cells[2].innerHTML+"','prodUnit':'"+tr.cells[3].innerHTML+"','prodNum':'"+tr.cells[4].innerHTML+"','prodPrice':'"+tr.cells[5].innerHTML+"','prodAmount':'"+tr.cells[6].innerHTML+"','prodMemo':'"+tr.cells[7].innerHTML+"'},";
				data+=info;
			}
			data = data.substring(0,data.length-1);
			data+="]}";
			
			var target = document.getElementById("orderForm");
			target.action = "${ctx}/order/orderInfo.do?method=updateSp&tempFlag=true";
			document.getElementById("itemInput").value=data;
			target.submit();
		}
		
		function sendOrder() {
			if(!checkDatas()){
				return;
			}
			var tbl = document.getElementById("itemTable");
			var size = tbl.rows.length;
			var data = "{'dataInfo':["
			for(var i=1;i<size;i++){
				var tr = tbl.rows[i];
				var info = "{'prodName':'"+tr.cells[1].innerHTML+"','prodCode':'"+tr.cells[2].innerHTML+"','prodUnit':'"+tr.cells[3].innerHTML+"','prodNum':'"+tr.cells[4].innerHTML+"','prodPrice':'"+tr.cells[5].innerHTML+"','prodAmount':'"+tr.cells[6].innerHTML+"','prodMemo':'"+tr.cells[7].innerHTML+"'},";
				data+=info;
			}
			data = data.substring(0,data.length-1);
			data+="]}";
			
			var target = document.getElementById("orderForm");
			target.action = "${ctx}/order/orderInfo.do?method=updateSp";
			document.getElementById("itemInput").value=data;
			target.submit();
		}
		
		function checkDatas(){
			var code = document.getElementById("shiptoCode").value;
			if(code==""){
				alert("送货方编码不能为空！");
				return false;
			}
			
			//送货日期判断
			var date = document.getElementById("queryDate").value;
			var dateTmp = date.split("-");
			var shipDate = new Date(dateTmp[0],dateTmp[1]-1,dateTmp[2]);
			var date1 = new Date();
			if(shipDate.getTime()<date1.getTime()){
				alert("送货日期不能小于今天");
				 
				return false;
			}
			//发票类型
			date = document.getElementById("invoiceType").value;
			if(date==''){
				alert("请选择发票类型");
				document.getElementById("invoiceType").focus();
				return false;
			}
		
			//客户联系人
			date = document.getElementById("distLink").value;
			if(date==''){
				alert("请选择客户联系人");
				document.getElementById("distLink").focus();
				return false;
			}
			//付款方式
			date = document.getElementById("paymentType").value;
			if(date==''){
				alert("请选择付款方式");
				document.getElementById("paymentType").focus();
				return false;
			}
			//收货客户
			date = document.getElementById("shiptoName").value;
			if(date==''){
				alert("请选择收货客户");
				document.getElementById("shiptoName").click();
				return false;
			}
			//运输方式
			date = document.getElementById("modeOfTransportType").value;
			if(date==''){
				alert("请选择运输方式");
				document.getElementById("modeOfTransportType").focus();
				return false;
			}
			//业代姓名
			date = document.getElementById("distEmpName").value;
			if(date==''){
				alert("请选择业代姓名");
				document.getElementById("distEmpName").click();
				return false;
			}
			
			var size = document.getElementById("itemTable").rows.length;
			if(size==1){
				alert("请添加订单信息！");
				return false;
			}
			if(!checkLastRowData()){
				alert("填写数据不符合规则！");
				return false;
			}
			return true;
		}
		
		
		//发货方树型列表
		function selectShipAddress(prodGroupId){
			var v = openDistAddressGroupCodeTree('${ctx}', 2,0,'',prodGroupId);
			//shiptoCode shiptoAddr shiptoLink shiptoTel
			//linkAddrTD linkmanTD linktelTD
			if(v){
				var orderTable = document.getElementById("orderTable");
				if(v.id!=''){
					document.getElementById("shiptoId").value=v.id;
					document.getElementById("shiptoCode").value=v.shiptoCode;
					document.getElementById("shiptoName").value=v.shiptoCode;
					document.getElementById("linkAddrTD").innerHTML=v.shiptoAddr;
					document.getElementById("linkmanTD").innerHTML=v.shiptoContact;
					document.getElementById("linktelTD").innerHTML=v.shiptoTel;
					orderTable.rows[3].cells[3].innerHTML=v.shiptoAddr;
					 
				}else{
					document.getElementById("shiptoId").value="";
					document.getElementById("shiptoCode").value="";
					document.getElementById("shiptoName").value="";
					document.getElementById("linkAddrTD").innerHTML="";
					document.getElementById("linkmanTD").innerHTML="";
					document.getElementById("linktelTD").innerHTML="";
					orderTable.rows[3].cells[3].innerHTML="";
				}
			}
		}
		
		//业代树型列表
		function selectDistEmp(prodGroupId){
			var v = openDistEmpTree('${ctx}', 2,0,'',prodGroupId);
			//distEmpTel distEmpName
			if(v){
				var orderTable = document.getElementById("orderTable");
				if(v.id!=''){
					document.getElementById("distEmpId").value=v.id;
					document.getElementById("distEmpName").value=v.empName;
					document.getElementById("distEmpTel").value=v.officePhone;
					orderTable.rows[4].cells[3].innerHTML=v.officePhone;
				}else{
					document.getElementById("distEmpId").value="";
					document.getElementById("distEmpName").value="";
					document.getElementById("distEmpTel").value="";
					orderTable.rows[4].cells[3].innerHTML="";
				}
			}
		}
		
		//添加行	
		function addRow(){
			if(!checkLastRowData()){
				return;
			}
			var tbl = document.getElementById("itemTable");
			var size = tbl.rows.length;
			var newtr = tbl.insertRow(1);
			if(size%2==0){
				newtr.style.className = 'odd';
			}else{
				newtr.style.className = 'even';
			}
			newtr.style.height="30px";
			newtr.style.borderBottomColor="#CCCCCC";
			if(size%2==0){
				newtr.style.className = 'odd';
			}else{
				newtr.style.className = 'even';
			}
			
			newtd = newtr.insertCell(0);
			newtd.bgColor="#FFFFFF";
			newtd.align="center";
			newtd.innerHTML = "<input type='checkbox' name='selectOrder'/>";
			
			newtd1 = newtr.insertCell(1);
			newtd1.bgColor="#FFFFFF";
			newtd1.onclick=function (){
				selectProdTree(this,'${itemBrandId }');
			}
			
			newtd = newtr.insertCell(2);
			newtd.bgColor="#FFFFFF";
			
			newtd = newtr.insertCell(3);
			newtd.bgColor="#FFFFFF";
	
			newtd = newtr.insertCell(4);
			newtd.bgColor="#FFFFFF";
			newtd.onclick=function (){
				CreateTextBox(this);
			}
			
	
			newtd = newtr.insertCell(5);
			newtd.bgColor="#FFFFFF";
			newtd.onclick=function (){
				CreateTextBox(this);
			}
	
			newtd = newtr.insertCell(6);
			newtd.bgColor="#FFFFFF";
	
			newtd = newtr.insertCell(7);
			newtd.bgColor="#FFFFFF";
			newtd.onclick=function (){
				CreateTextBox(this);
			}
	 		selectProdTree(newtd1,'${itemBrandId }');
			
		}
		
		//多选添加行	shijingru 20120206
		function addProdTree(prodGroupId){
			var v = prodBrandWhereProdSTRUTree('${ctx}', 2,1,'',prodGroupId);
			if(v){
				if(v.id!=''){
					var s = v.text.split(",");
					for(var i=0; i<s.length; i++){
						var prods = s[i].split("|");
						addProdRow(prods[0],prods[1],prods[3]);
					}
				}
			}
		}
		//多选添加行	shijingru 20120206
		function addProdRow(prodCode, prodName, countUnitName){
			var tbl = document.getElementById("itemTable");
			var size = tbl.rows.length;
			var j = 0;
			/* 可重复添加同一物料(原判断 if(size<1||j<1)) liuguangshuai_20120904
			if(size>1){
				for(var i=1; i<size; i++){
					if(tbl.rows[i].cells[2].innerHTML == prodCode){
						j++;
					}
				}
			}
			*/
			if(size<1||j>=0){
				var newtr = tbl.insertRow(1);
					if(size%2==0){
						newtr.style.className = 'odd';
					}else{
						newtr.style.className = 'even';
					}
					newtr.style.height="30px";
					newtr.style.borderBottomColor="#CCCCCC";
					if(size%2==0){
						newtr.style.className = 'odd';
					}else{
						newtr.style.className = 'even';
					}
					newtd = newtr.insertCell(0);
					newtd.bgColor="#FFFFFF";
					newtd.align="center";
					newtd.innerHTML = "<input type='checkbox' name='selectOrder'/>";
					
					newtd1 = newtr.insertCell(1);
					newtd1.bgColor="#FFFFFF";
					newtd1.innerHTML=prodName;
					newtd1.onclick=function (){
						addProdTree('${itemBrandId}');
					}
					
					newtd = newtr.insertCell(2);
					newtd.bgColor="#FFFFFF";
					newtd.innerHTML=prodCode;
					
					newtd = newtr.insertCell(3);
					newtd.bgColor="#FFFFFF";
					newtd.innerHTML=countUnitName;
					
					newtd = newtr.insertCell(4);
					newtd.bgColor="#FFFFFF";
					newtd.onclick=function (){
						CreateTextBox(this);
					}
					newtd = newtr.insertCell(5);
					newtd.bgColor="#FFFFFF";
					newtd.onclick=function (){
						CreateTextBox(this);
					}
					newtd = newtr.insertCell(6);
					newtd.bgColor="#FFFFFF";
			
					newtd = newtr.insertCell(7);
					newtd.bgColor="#FFFFFF";
					newtd.onclick=function (){
						CreateTextBox(this);
					}
			}
			
		}
		//物料树型列表
		function selectProdTree(element,prodGroupId){
			//shiptoCode shiptoAddr shiptoLink shiptoTel
			var v = prodBrandWhereProdSTRUTree('${ctx}', 2,1,'',prodGroupId);
			if(v){
				var tr = element.parentNode;
				if(v.id!=''){
					element.innerHTML = v.prodName;
					tr.cells[2].innerHTML = v.prodCode;
					tr.cells[3].innerHTML = v.countUnitName;
					//alert(v.countUnitName);
				}else{
					element.innerHTML = "";
					tr.cells[2].innerHTML = "";
				}
			}else{
				var tbl = document.getElementById("itemTable");
				var size = tbl.rows.length;
				var newtr = tbl.deleteRow(1);
			}
		}
		
		var distLinkMap = new Map(); 
		<%
		 List<MdmDistributorLinkman> distLinks=(ArrayList)request.getAttribute("distLinks");
		 for( MdmDistributorLinkman link:distLinks){
		 %>
		distLinkMap.put('<%=link.getId()%>','<%=link.getLinkmanPhonenum()%>');
		<%}%>
		
		//选择客户联系人
		function onchangeDistLink(linkId){
			var orderTable = document.getElementById("orderTable");
		 	if(distLinkMap.get(linkId)){
				orderTable.rows[2].cells[3].innerHTML=distLinkMap.get(linkId);
			}else{
				orderTable.rows[2].cells[3].innerHTML="";
			}
		}
		
		//创建录入框
		function CreateTextBox(element){
			var editState = element.getAttribute("EditState");
			if(editState!="true"){
				var textBox = document.createElement("INPUT");
				textBox.type = "text";
			    textBox.maxLength =20;
				textBox.value=element.innerHTML;
				element.innerHTML="";
				element.appendChild(textBox);
				 
				//失去焦点时处理
				textBox.onblur = function (){
					this.value=this.value.trim();
					var vl = this.value;
					if('' != vl){
						if(element.cellIndex==4||element.cellIndex==5){
							vl = formNum(vl);
						}
					}
					element.innerHTML = vl;
					element.setAttribute("EditState", "false");
					
					//计算金额
					var amount = calculate(element.parentNode.cells[5].innerHTML,element.parentNode.cells[4].innerHTML);
					element.parentNode.cells[6].innerHTML=amount;
					
					sumTotal();
				}
				textBox.style.width="100%";
				textBox.style.height="100%";
				textBox.focus();
				textBox.select();
				element.setAttribute("EditState", "true");
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
				sumNum = '0000'.substring(0, (4 - sumNum.toString().length)) + sumNum;
				document.getElementById("sumNumTd").innerHTML = sumNum.substring(0,sumNum.length-3)+"."+sumNum.substring(sumNum.length-3);
			}
			if(sumAmount>0){
				sumAmount = sumAmount.toString();
				sumAmount = '0000'.substring(0, (4 - sumAmount.toString().length)) + sumAmount;
				document.getElementById("sumAmountTd").innerHTML = sumAmount.substring(0,sumAmount.length-3)+"."+sumAmount.substring(sumAmount.length-3);
			}
		}
		
		function calculate(num,price){
			if('' != num){
				num = formNum(num).replace(".","");
			}
			if('' != price){
				price = formNum(price).replace(".","");
			}
			var amount = num*price;
			
			if(amount>0){
				//amount = amount.substring(0,amount.length-6)+"."+amount.substring(amount.length-6,3);
				amount = amount.toString();
                amount = '0000000'.substring(0, (7 - amount.toString().length)) + amount;
				amount = amount.substring(0,amount.length-6)+"."+amount.substring(amount.toString().length-6);;
			}
			amount = amount.toString();
			if(0 <= amount){
			 	amount = formNum(amount)
			}else{
			 	amount = '';
			}
			return amount;
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
		//添加或保存时校验数据
		function checkLastRowData(){
			var tbl = document.getElementById("itemTable");
			var size = tbl.rows.length;
			var j = 0;
			 if(size>1){ 
				for(var i=1; i<size; i++){
					var tr = tbl.rows[i];
					var c= tr.cells[1].innerHTML;
					if(c==""){
						flag = false;
						//alert("[物料名称]不能为空！");
						tr.cells[1].style.background ="#ffcccc" ;
						j++;
					}
					var c= tr.cells[4].innerHTML;
					if(c==""){
						//alert("[数量]不能为空");
						tr.cells[4].style.background="#ffcccc" ;
						j++;
					}
					if(isNaN(c)){
						//alert("[数量]输入不正确！");
						tr.cells[4].style.background="#ffcccc" ;
						j++;
					}
					if(c<=0){
						//alert("[数量]必须为大于0的正数！");
						tr.cells[4].style.background="#ffcccc" ;
						j++;
					}
					if(!checkNum(c,10)){
						//alert("[数量]长度不能超过10位！");
						//obj.focus();
						tr.cells[4].style.background="#ffcccc" ;
						j++;
					}
					var c= tr.cells[5].innerHTML;
					if(c==""){
						//alert("[单价]不能为空");
						tr.cells[5].style.background="#ffcccc" ;
						j++;
					}
					if(isNaN(c)){
						//alert("[单价]输入不正确！");
						tr.cells[5].style.background="#ffcccc" ;
						j++;
					}
					if(c<0){
						//alert("[单价]必须为大于0的正数！");
						tr.cells[5].style.background="#ffcccc" ;
						j++;
					}
					if(!checkNum(c,10)){
						//alert("[单价]长度不能超过10位！");
						//obj.focus();
						tr.cells[5].style.background="#ffcccc" ;
						j++;
					}
				}
			 } 
			 if(j<1){
			 	return true;
			 }else{
			 	return false;
			 }
			
		}
	function checkNum(obj,maxlength){
			var temp = obj.split(".");
			if(temp.length==2){
				if(temp[0].length>maxlength){
					return false;
				}
			}else{
				if(temp[0].trim().length>maxlength){
					return false;
				}
			}
			return true;
		}
	

		
		//检查数量和单价
		function checkCellData(obj){
			var td = obj.parentNode;
			var celNum = td.cellIndex;
			if(celNum==4){
				if(obj.value==""){
					alert("[数量]不能为空！");
					 
					return false;
				}
				if(isNaN(obj.value)){
					alert("[数量]输入不正确，请重新输入！");
					 
					return false;
				}
				if(obj.value<=0){
					alert("[数量]必须大于0的正数！");
					 
					return false;
				}
				 
				if(!checkNum(obj.value,10)){
					alert("[数量]整数长度不能超过10位！");
					//obj.focus();
					return false;
				}
				
			}
			if(celNum==5){
				if(obj.value==""){
					alert("[单价]不能为空！");
					 
					return false;
				}
				if(isNaN(obj.value)){
					alert("[单价]输入不正确，请重新输入！");
					 
					return false;
				}
				if(obj.value<=0){
					alert("[单价]必须大于0的正数！");
					 
					return false;
				}
				 
				if(!checkNum(obj.value,10)){
					alert("[单价]整数长度不能超过10位！");
					//obj.focus();
					return false;
				}
				
			}
			
			return true;
		}
		
		//删除行
		function delRow(){
			var tbl = document.getElementById("itemTable");
			var test = document.all.selectOrder;
			if(test){
				if(!test.length){
					if(test.checked){
						tbl.deleteRow(test.parentNode.parentNode.rowIndex);
					}else{
						alert("请至少选择一条记录！");
					}
				}else{
					var size = test.length;
					var snum = 0;
					for(var i=size-1;i>=0;i--){
						if(test[i].checked){
							tbl.deleteRow(test[i].parentNode.parentNode.rowIndex);
							snum++;
						}
					}
					if(snum==0){
						alert("请至少选择一条记录！");
					}
					
				}
			}
			sumTotal();
			
		}
		
		//全选按钮点击处理
		function selectAll(){
			var flag = document.getElementById("checkAll").checked;
			var test = document.all.selectOrder;
			
			if(test){
				if(!test.length){
					if(flag){
						test.checked=true;
					}else{
						test.checked=false;
					}
				}else{
					var size = test.length;
					if(flag){
						for(var i=0;i<size;i++){
							test[i].checked=true;
						}
					}else{
						for(var i=0;i<size;i++){
							test[i].checked=false;
						}
					}
					
				}
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
		</script>
		<style type="text/css">
			.list_add1{width:100%;}
			.list_add1 td { border-bottom: 1px solid #E5E5E5;padding:4px 10px; white-space: nowrap; }
			.list_add1 input, .list_add1 select { border: 1px solid #CCCCCC; width:150px;word-break:break-all}
		</style>
	</head>
	<body onLoad="WindowSollAuto();onLoad1()" onResize="WindowSollAuto()">
		<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form styleId="orderForm" action="/order/orderInfo.do?method=saveSp" method="post">
			<input type="hidden" id="itemInput" name="orderItems" value=""/>			
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						订货意向单
					</h4>
					<div class='MenuList'>
						<a href="javascript:addProdTree('${itemBrandId}');" id="query_A">添加</a>
						<a href="javascript:delRow();" id="query_A">删除</a>
						<a href="javascript:save();" id="query_A">草稿</a>
						<a href="javascript:sendOrder();" id="query_A">提交</a>
						<a href="javascript:showInfo();" id="showOrderHeader">隐藏</a>
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
								<table class="list_add1" id="orderTable" style="display:block">
									<input type="hidden" id="orderId" name="orderId" value="${orderId}"/>
									<input type="hidden"   id="distCode" value="${dist.distCode}"/>
									<input type="hidden"   id="distName" value="${dist.distName}"/>
									<input type="hidden"  id="linkmanTel"  value="${dist.linkmanTel}"/>
									<input type="hidden" id="shiptoId" name="shiptoId" value="${address.id }"/>
									<input type="hidden" id="shiptoCode" name="shiptoCode" value="${address.shiptoCode }"/>
									<input type="hidden" id="shiptoAddr" name="shiptoAddr"   value="${address.shiptoAdd }"/>
									<input type="hidden" id="shiptoLink" name="shiptoLink"  value="${address.contact }"/>
									<input type="hidden" id="shiptoTel" name="shiptoTel"  value="${address.tel }"/>
									<input type="hidden" id="distEmpTel" name="distEmpTel"  value="${distEmpTel}"/>
									<input type="hidden" id="distEmpId" name="distEmpId"  value="${distEmpId}"/>
									<input type="hidden" id="itemBrandId" name="itemBrandId" value="${itemBrandId}"/>
									<input type="hidden" id="distEmpTel" name="distEmpTel" value="${distEmpTel}"/>
									 
								    <tr>
										<td class="formTable">订单平台订货号<font color="#FF0000">＊</font></td>
										<td align="left">
										    <input type="text" id="orderCodeInput" name="orderCode" readonly style="background-color: #F0F0F0" value="${orderCode}"/>
										</td>
										<td class="formTable">订货日期</td>
										<td align="left">
										   <input type="text" name="orderDate" id="orderDate" style="background-color: #F0F0F0" readonly value="<%=curDateTime %>"/>
										</td>
										<td class="formTable">要求送货日期<font color="#FF0000">＊</font></td>
										<td align="left">
										   <input type="text" id="queryDate" name="queryDate" onfocus="setDay(this)" readonly value="${shiptoDate }" class="date_but" onchange="getDeliveryDays()"/>
										</td>
									</tr>
								    <tr>
										<td class="formTable">客户编码</td>
										<td align="left">
										     ${dist.distCode}
										 </td>
										<td class="formTable">客户名称</td>
										<td align="left">${dist.distName}</td>
										<td class="formTable" width="4%">发票类型<font color="#FF0000">＊</font></td>
										<td align="left" > 
											<select name="invoiceType" id="invoiceType" style='width:155px;'>
												<option value=""></option>
												<c:forEach items="${invoiceTypes}" var="intype">
													<option value="${intype.dictItemId}" <c:if test="${intype.dictItemId == invoiceType}">selected</c:if>>${intype.itemName }</option>
												</c:forEach>
											</select>
										</td>
									</tr>
									<tr>
										<td class="formTable" width="4%">客户联系人<font color="#FF0000">＊</font></td>
										<td align="left" width="8%"> 
 											<select name="distLink" id="distLink" style='width:155px;' onchange="onchangeDistLink(this.value);">
												<option value=""></option>
												<c:forEach items="${distLinks}" var="dlink">
													<option value="${dlink.id }" <c:if test="${dlink.linkmanName == distLink.linkmanName }">selected</c:if>>${dlink.linkmanName }</option>
												</c:forEach>
											</select>
									    </td>
										<td class="formTable">联系电话</td>
										<td align="left">${distLink.linkmanPhonenum}</td>
										<td class="formTable" width="4%">付款方式<font color="#FF0000">＊</font></td>
										<td align="left" > 
											<select id="paymentType" name="paymentType"style='width:155px;' >
												<option value=""></option>
												<c:forEach items="${paymentTypes}" var="payType">
													<option value="${payType.dictItemId }" <c:if test="${payType.dictItemId == paymentType}">selected</c:if>>${payType.itemName }</option>
												</c:forEach>
											</select>
										</td> 
									</tr>
									<tr>
										<td class="formTable">收货客户编码<font color="#FF0000">＊</font></td>
										<td align="left">
										     <input type="text" id="shiptoName" readonly class="select_but" onclick="selectShipAddress('${itemBrandId}')" value="${address.shiptoCode}"/>
										     <!-- <input type="text" readonly class="select_but" onclick="selectShipAddress(${prodGroupId})"/> -->
										 </td>
										<td class="formTable">送货地址</td>
										<td align="left" id="linkAddrTD">${address.shiptoAdd }</td>
										<td class="formTable">运输方式<font color="#FF0000">＊</font></td>
										<td align="left" width="8%"> 
											<select id="modeOfTransportType" name="modeOfTransportType"style='width:155px;' >
												<option value=""></option>
												<c:forEach items="${modeOfTransportTypes}" var="modeType">
													<option value="${modeType.dictItemId }" <c:if test="${modeType.dictItemId == modeOfTransportType }">selected</c:if>>${modeType.itemName }</option>
												</c:forEach>
											</select>
										</td> 
									</tr>
									<!--  tr>
										<td class="formTable">送货联系人</td>
										<td align="left" id="linkmanTD">${address.contact }</td>
										<td class="formTable">联系电话</td>
										<td align="left" id="linktelTD">${address.tel }</td>
										<td >&nbsp;</td>
										<td rowspan="2">
											<textarea rows="4" cols="25" name="memo">${memo }</textarea>
										</td>
									</tr -->
									<tr>
										<td class="formTable">业代姓名</td>
										<td align="left">
											<input type="text" id="distEmpName" name="distEmpName" readonly class="select_but" onclick="selectDistEmp('${itemBrandId }')" value="${distEmpName}"/>
										</td>
										<td class="formTable">业代手机号</td>
										<td align="left">${distEmpTel }</td>
										<td class="formTable">订单备注</td>
										<td rowspan="2">
											<textarea rows="4" cols="25" name="memo" onkeyup="this.value = this.value.substring(0, 200)" maxlength=200>${memo }</textarea>
										</td>
									</tr>
								</table>
								<div id="itemDiv" style="width:100%;height:180px;overflow-y:scroll">
									<table id="itemTable" border="0" cellspacing="1" cellpadding="0" bgcolor="#CCCCCC" width="100%" style="overflow-y:scroll">
										<tr align="center">
											<td width="3%" bgcolor="#99CCFF"><input type='checkbox' onclick='selectAll()' value='' name='checkboxAll'  id='checkAll'/></td>
											<td height="30" class="tableHeader" bgcolor="#99CCFF" width="18%"><b>物料名称</b></td>
											<td class="tableHeader" bgcolor="#99CCFF" width="15%"><b>物料编号</b></td>
											<td class="tableHeader" bgcolor="#99CCFF" width="9%"><b>单位</b></td>
											<td class="tableHeader" bgcolor="#99CCFF" width="10%"><b>数量</b></td>
											<td class="tableHeader" bgcolor="#99CCFF" width="10%"><b>含税单价</b></td>
											<td class="tableHeader" bgcolor="#99CCFF" width="14%"><b>金额(元)</b></td>
											<td class="tableHeader" bgcolor="#99CCFF" width="20%"><b>备注</b></td>
										</tr>
										<c:forEach  items="${voList}" var="info">
											<tr>
												<td bgcolor="#FFFFFF" align="center"><input type="checkbox" name="selectOrder" /></td>
												<td height="30" bgcolor="#FFFFFF" onclick="addProdTree('${itemBrandId}');">${info.prodName}</td>
												<td bgcolor="#FFFFFF">${info.prodCode}</td>
												<td bgcolor="#FFFFFF">${info.prodUnit}</td>
												<td bgcolor="#FFFFFF" onclick="CreateTextBox(this);">${info.prodNum}</td>
												<td bgcolor="#FFFFFF" onclick="CreateTextBox(this);">${info.prodPrice}</td>
												<td bgcolor="#FFFFFF">${info.prodAmount }</td>
												<td bgcolor="#FFFFFF" onclick="CreateTextBox(this);">${info.prodMemo}</td>
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
											<td class="tableHeader" bgcolor="#99CCFF" width="9%">&nbsp;</td>
											<td class="tableHeader" bgcolor="#99CCFF" width="10%" id="sumNumTd">&nbsp;</td>
											<td class="tableHeader" bgcolor="#99CCFF" width="10%">&nbsp;</td>
											<td class="tableHeader" bgcolor="#99CCFF" width="14%" id="sumAmountTd">&nbsp;</td>
											<td class="tableHeader" bgcolor="#99CCFF" width="20%">&nbsp;</td>
										</tr>
									</table>
								</div>	
								<!-- <div style="width:100%;text-align:right;padding-right:50px;padding-top:10px;padding-bottom:10px;">
									<input type="button" style="width:50px;border:1px solid #99CCFF" onclick="javascript:addRow()" value="添加"/>
									<input type="button" style="width:50px;border:1px solid #99CCFF" onclick="javascript:delRow()" value="删除"/>
									<input type="button" style="width:50px;border:1px solid #99CCFF" onclick="javascript:save();" value="暂存"/>
									<input type="button" style="width:50px;border:1px solid #99CCFF" onclick="javascript:sendOrder();" value="发送"/>
								</div> -->
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
		
		<script type="text/javascript">
			//初始化年份 = 当前年份
			function onLoad1(){
				var now = new Date();
				var nowYear = now.getYear();
				var nowMonth = now.getMonth()+1;
				if(nowMonth < 10){
					nowMonth="0"+nowMonth;
				}
				var nowDay = now.getDate();
				if(nowDay < 10){
					nowDay="0"+nowDay;
				}
				if(document.getElementById("queryDate").value == ''){
					//alert('update');
					//document.getElementById("queryDate").value = nowYear+'-'+nowMonth+"-"+nowDay;
				}
				sumTotal();
				if(document.getElementById("orderCodeInput").value == ''){
					document.getElementById("orderCodeInput").value = '编码自动生成';
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
			var loading = false;
			function getDeliveryDays(){
				var itemBrandId = document.getElementById("itemBrandId").value;
				var orderDate = document.getElementById("orderDate").value;
				var queryDate = document.getElementById("queryDate").value;
				//送货日期判断
				var dateTmp = queryDate.split("-");
				var shipDate = new Date(dateTmp[0],dateTmp[1]-1,dateTmp[2]);					
				var date1 = new Date();
				if(shipDate.getTime()<date1.getTime()){
					document.getElementById("queryDate").value = '';
					alert("送货日期不能小于今天");
				}else{
					if(queryDate != ''){
						createXMLHttpRequest();
						xmlhttp.open("post", "${ctx}/order/orderInfo.do?method=getDeliveryDays&itemBrandId=" + itemBrandId + "&orderDate=" + orderDate + "&queryDate=" + queryDate, true);
						xmlhttp.onreadystatechange = function() {
							if(xmlhttp.readyState == 4) {
								if(xmlhttp.status == 200) {
									if(xmlhttp.responseText!=null&&xmlhttp.responseText!='') {
										var odate = xmlhttp.responseText;
										document.getElementById("queryDate").value = '';
										if(odate == "orderDate"){
											alert("送货日期应大于等于订单日期");
										}else{
											alert("送货日期应在 "+odate+" 之前");
										}										
									}
								}
							}
						};
						xmlhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
						xmlhttp.send(null);
					}
				}
			}

		</script>
	</body>
</html>
