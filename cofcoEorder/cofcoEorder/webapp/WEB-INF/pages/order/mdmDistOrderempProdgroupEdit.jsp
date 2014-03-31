<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
<head>
<title>订单员_组织_产品组关系维护</title>
<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
<script language="javaScript" src="${ctx}/scripts/base.js"></script>
<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>
<script type="text/javascript"
	src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
	<script src="${ctx}/scripts/jquery-1.2.6.min.js"></script>
<script>
	function save() {
		if (validateMdmDistOrderempProdgroupForm(mdmDistOrderempProdgroupForm)) {
			checkDistEmpProd();
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
	function checkDistEmpProd() {
		var form = document.mdmDistOrderempProdgroupForm;
		var id = form.id.value;
		var orgId1 = form.orgId.value;
		var prodBrandId1 = form.prodBrandId.value;
		var empId1 = form.empId.value;
		var effectiveTime1 = form.effectiveTime.value;
		var expiryTime1 = form.expiryTime.value;
		
		createXMLHttpRequest();
		xmlhttp.open("post", "${ctx}/order/mdmDistOrderempProdgroup.do?method=isSame&orgId=" + orgId1 + "&prodBrandId=" + prodBrandId1 +"&empId=" + empId1 + "&effectiveTime=" + effectiveTime1 + "&expiryTime=" +expiryTime1 + "&id=" + id, true);
		xmlhttp.onreadystatechange = function() {
			if(xmlhttp.readyState == 4) {
				if(xmlhttp.status == 200) {
					if(xmlhttp.responseText!=null&&xmlhttp.responseText!='') {
						form.check.value = xmlhttp.responseText;
						if(form.check.value == "0"){
								mdmDistOrderempProdgroupForm.submit();
							}
						if(form.check.value == "2"){
							alert("失效时间必须大于生效时间");
							}
						if(form.check.value == "3"){
							alert("生效时间必须大于等于当前时间");
							}
					}
				}
			}
		};
		xmlhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
		xmlhttp.send(null);
		
	}
	
	 //组织
	function selectOrg() {
		var v = openOrgTree('${ctx}');
		if (v) {
			mdmDistOrderempProdgroupForm.orgId.value = v.id;
			mdmDistOrderempProdgroupForm.orgName.value = v.text;
		}
	}
	//物料组列表
	function selectDict(dictName, id, name){
		var v = openDictTree('${ctx}',dictName,2,0,'','',1);
		if(v){
			if(v.id!=''){
				id.value = v.id;
				name.value = v.text;
			}else{
				id.value = "";
				name.value ="";
			}
		}
	}

	//产品
	function selectProd(id, name) {
		var form = document.mdmDistOrderempProdgroupForm;
		var v = openProdTree('${ctx}', form.prodBrandId.value, '2', '0');
		if (v) {
			form.prodBrandId.value.value = v.id;
			form.prodBrandName.value = v.text;
		}
	}

	//人员
	function selectEmp() {
		var form = document.mdmDistOrderempProdgroupForm;
		var v = openEmpTreeOM('${ctx}', '', '0', form.empId.value);
		if (v) {
			form.empId.value = v.id;
			form.empName.value = v.text;
		}
	}
	
	function onTime(){
			var form = document.mdmDistOrderempProdgroupForm;
			var effectiveTime = form.effectiveTime.value.toString().substr(0,10);
			var expiryTime = form.expiryTime.value.toString().substr(0,10);
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
			if(form.effectiveTime.value == ''){
				form.effectiveTime.value = nowYear+'-'+nowMonth+"-"+nowDay;
			}
			if(form.effectiveTime.value != ''){
				form.effectiveTime.value = effectiveTime;
			}
			if(form.expiryTime.value != ''){
				form.expiryTime.value = expiryTime;
			}
		}		
</script>
</head>
<body onLoad="WindowSollAuto();onTime()" onResize="WindowSollAuto()">
	<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
	<html:form action="/order/mdmDistOrderempProdgroup.do?method=save"
		method="post">
		<html:hidden property="id"/>
		<html:hidden property="check"/>
		<div class="bosom_one">
			<div class="bosom_top">
				<span class="left"></span>
				<h4>订单员_组织_产品组关系维护</h4>
				<div class='MenuList'>
					<a href="javascript:save();">保存</a> <a
						href="mdmDistOrderempProdgroup.do?method=list">取消</a>
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
									<td class="formTable">组织： <font color="#FF0000">＊</font>
									</td>
									<td align="left"><html:hidden property="orgId" /> <html:text
											property="orgName" styleClass="select_but"
											onclick="selectOrg()" readonly="true" /></td>
									<td class="formTable">物料组： <font color="#FF0000">＊</font>
									</td>
									<td align="left"><html:hidden property="prodBrandId" /> <html:text
											property="prodBrandName" readonly="true"
											styleClass="select_but"
											onclick="selectDict('prodSTRU', mdmDistOrderempProdgroupForm.prodBrandId, mdmDistOrderempProdgroupForm.prodBrandName)" />
									</td>
								</tr>
								<tr>
									<td class="formTable">订单员： <font color="#FF0000">＊</font></td>
									<td align="left">
										<html:hidden property="empId" /> 
										<html:text property="empName" styleClass="select_but"
											onclick="selectEmp()" readonly="true" />
									</td>
									<td class="formTable">生效日期:<font color="#FF0000">＊</font></td>
									<td align="left">
										<c:if test="${flag=='1'}">
											<html:text property="effectiveTime" readonly="true" style="background-color: #F0F0F0"/>
										</c:if>
										<c:if test="${flag!='1'}">
											<html:text property="effectiveTime" maxlength="20" styleClass="date_but" 
											onfocus="WdatePicker()" readonly="true" />
										</c:if>
									</td>
							   </tr>
                               <tr>
                                 	<td class="formTable">失效日期:</td>
									<td align="left">
										<html:text property="expiryTime"maxlength="20" styleClass="date_but" 
										onfocus="WdatePicker()" readonly="true" />
									</td>
									<td class="formTable">附加数据1:</td>
									<td align="left"><html:text property="memo1" maxlength="50" /></td>
							  </tr>
							  <tr>
									<td class="formTable">附加数据2:</td>
									<td align="left"><html:text property="memo2" maxlength="50" /></td>
									<td class="formTable">附加数据3:</td>
									<td align="left"><html:text property="memo3" maxlength="50" /></td>
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
	<html:javascript formName="mdmDistOrderempProdgroupForm"
		staticJavascript="false" dynamicJavascript="true" cdata="false" />
	<script type="text/javascript" src="${ctx}/scripts/validator.jsp"></script>
</body>
</html>
