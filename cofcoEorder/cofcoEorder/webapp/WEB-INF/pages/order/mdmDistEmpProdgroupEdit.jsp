<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
<head>
<title>客户_产品组_业代关系维护</title>
<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
<script language="javaScript" src="${ctx}/scripts/base.js"></script>
<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>
<script type="text/javascript"
	src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>

	<script src="${ctx}/scripts/jquery-1.2.6.min.js"></script>

<script>
	function save() {
		if (validateMdmDistEmpProdgroupForm(mdmDistEmpProdgroupForm)) {
			checkDistEmpProd();
		}
	} 
  	var loading = false;
	function checkDistEmpProd() {
		var form = document.mdmDistEmpProdgroupForm;
		var id = form.id.value;
		var distId1 = form.distId.value;
		var prodBrandId1 = form.prodBrandId.value;
		var empId1 = form.empId.value;
		var effectiveTime1 = form.effectiveTime.value;
		var expiryTime1 = form.expiryTime.value;
		
		createXMLHttpRequest();
		xmlhttp.open("post", "${ctx}/order/mdmDistEmpProdgroup.do?method=isSame&distId=" + distId1 + "&prodBrandId=" + prodBrandId1 +"&empId=" + empId1 + "&effectiveTime=" +effectiveTime1+ "&expiryTime=" +expiryTime1 + "&id=" + id, true);
		xmlhttp.onreadystatechange = function() {
			if(xmlhttp.readyState == 4) {
				if(xmlhttp.status == 200) {
					if(xmlhttp.responseText!=null&&xmlhttp.responseText!='') {
						form.check.value = xmlhttp.responseText;
						if(form.check.value == "0"){
							mdmDistEmpProdgroupForm.submit();
						}
						if(form.check.value == "1"){
							alert("已经存在相同的客户，业代，物料组的关系维护，不能重复添加 ");
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

	//经销商
	function selectDist() {
		var form = document.mdmDistEmpProdgroupForm;
		var v = openDistTree('${ctx}', '', '0', form.distId.value);
		if (v) {
			form.distId.value = v.id;
			form.distName.value = v.text;
			form.distCode.value = v.distCode;
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
		var form = document.mdmDistEmpProdgroupForm;
		var v = openProdTree('${ctx}', form.prodBrandId.value, '2', '0');
		if (v) {
			form.prodBrandId.value.value = v.id;
			form.prodBrandName.value = v.text;
		}
	}

	//人员
	function selectEmp() {
		var form = document.mdmDistEmpProdgroupForm;
		var v = openEmpTreeSR('${ctx}', '', '0', form.empId.value);
		if (v) {
			form.empId.value = v.id;
			form.empName.value = v.text;
			form.empCode.value = v.empCode;
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
	function getDistName() {
		var form = document.mdmDistEmpProdgroupForm;
		form.distName.value = '';
		var distCode = form.distCode.value;
		if (distCode != '') {
				createXMLHttpRequest();
				xmlhttp.open("post", "${ctx}/order/mdmDistEmpProdgroup.do?method=getDistName&distCode=" + distCode, true);
				xmlhttp.onreadystatechange = function() {
					if(xmlhttp.readyState == 4) {
						if(xmlhttp.status == 200) {
							if(xmlhttp.responseText!=null&&xmlhttp.responseText!='') {
								var s = xmlhttp.responseText.split(",");
								form.distId.value = s[0];
								form.distName.value = s[1];
							}
							else {
								form.distName.value = '';
								form.distCode.value = '';
								alert("编码不存在，请向总部申请新开客户 ");
								form.distName.disabled = false;
							}
						}
						else {
							form.distName.value = '';
							form.distCode.value = '';
							alert("编码不存在，请向总部申请新开客户 ");
							form.distName.disabled = false;
						}
					}
			};
			xmlhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
			xmlhttp.send(null);
		
		}
	}

	var loading = false;
	function getEmpName() {
		var form = document.mdmDistEmpProdgroupForm;
		form.empName.value = '';
		var empCode = form.empCode.value;
		if (empCode != '') {
				createXMLHttpRequest();
				xmlhttp.open("post", "${ctx}/order/mdmDistEmpProdgroup.do?method=getEmpName&empCode=" + empCode, true);
				xmlhttp.onreadystatechange = function() {
					if(xmlhttp.readyState == 4) {
						if(xmlhttp.status == 200) {
							if(xmlhttp.responseText!=null&&xmlhttp.responseText!='') {
								var s = xmlhttp.responseText.split(",");
								form.empId.value = s[0];
								form.empName.value = s[1];
							}
							else {
								form.empName.value = '';
								form.empCode.value = '';
								alert("编码不存在，请向总部申请新开业代 ");
								form.empName.disabled = false;
							}
						}
						else {
							form.empName.value = '';
							form.empCode.value = '';
							alert("编码不存在，请向总部申请新开业代 ");
							form.empName.disabled = false;
						}
					}
			};
			xmlhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
			xmlhttp.send(null);
		
		}
	}	
	
		function onTime(){
			var form = document.mdmDistEmpProdgroupForm;
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
	<html:form action="/order/mdmDistEmpProdgroup.do?method=save"
		method="post">
		<html:hidden property="id"/>
		<html:hidden property="check"/>
		<div class="bosom_one">
			<div class="bosom_top">
				<span class="left"></span>
				<h4>客户_产品组_业代关系维护</h4>
				<div class='MenuList'>
					<a href="javascript:save();">保存</a> <a
						href="mdmDistEmpProdgroup.do?method=list">取消</a>
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
							<c:if test="${flag=='0'}">
								<tr>
							    	<td class="formTable">客户编码： <font color="#FF0000">＊</font><html:hidden property="distId" /></td>
							    	<td align="left">
							    		<html:text property="distCode" styleId="distCode"  maxlength="15" onchange="getDistName()"/>
							    	</td>
							    	<td class="formTable">客户名称： <font color="#FF0000">＊</font></td>
									<td align="left">									
										<html:text property="distName" styleClass="select_but" styleId="distName" 
											onclick="selectDist()" readonly="true" />
									</td>
								</tr>
								<tr>
									<td class="formTable">业代编码： <font color="#FF0000">＊</font><html:hidden property="empId" /></td>
									<td align="left">
										<html:text property="empCode" styleId="empCode"  maxlength="15" onchange="getEmpName()"/>	
									</td>
									<td class="formTable">业代名称：</td>									
									<td align="left">							
										<html:text property="empName" styleClass="select_but" styleId="empName" 
											onclick="selectEmp()" readonly="true" />
									</td>
								</tr>
								</c:if>
								<c:if test="${flag=='1'}">
								<tr>
							    	<td class="formTable">客户编码： <font color="#FF0000">＊</font><html:hidden property="distId" /></td>
									<td align="left">
							    		<html:text property="distCode" styleId="distCode"  readonly="true" maxlength="15" style="background-color: #F0F0F0"/>
							    	</td>
									<td class="formTable">客户名称： <font color="#FF0000">＊</font></td>
									<td align="left">	
										<html:text property="distName" styleId="distName" readonly="true" style="background-color: #F0F0F0"/>
									</td>
								</tr>
								<tr>
									<td class="formTable">业代编码： <font color="#FF0000">＊</font><html:hidden property="empId" /></td>
									<td align="left">
										<html:text property="empCode" styleId="empCode"  readonly="true" maxlength="15" style="background-color: #F0F0F0"/>	
									</td>
									<td class="formTable">业代名称：</td>									
									<td align="left">	
										<html:text property="empName" styleId="empName"  readonly="true" style="background-color: #F0F0F0"/>
									</td>
								</tr>
								</c:if>
								<tr>
									<td class="formTable">物料组： <font color="#FF0000">＊</font>
									</td>
									<td align="left"><html:hidden property="prodBrandId" /> <html:text styleId="prodBrandName"
											property="prodBrandName" readonly="true"
											styleClass="select_but"
											onclick="selectDict('prodSTRU', mdmDistEmpProdgroupForm.prodBrandId, mdmDistEmpProdgroupForm.prodBrandName)" />
									</td>
									<td class="formTable">附加数据1:</td>
									<td align="left"><html:text property="memo1"
											maxlength="50" />
									</td>
								</tr>
								
                                <tr>
									<td class="formTable">生效日期:<font color="#FF0000">＊</font></td>
									<td align="left">
										<c:if test="${flag=='1'}">
											<html:text property="effectiveTime" readonly="true" maxlength="10" style="background-color: #F0F0F0"/>
										</c:if>
										<c:if test="${flag!='1'}">
											<html:text property="effectiveTime" maxlength="10" styleClass="date_but" 
											onfocus="WdatePicker()" readonly="true" />
										</c:if>
									</td>
									<td class="formTable">附加数据2:</td>
									<td align="left"><html:text property="memo2"
											maxlength="50" />
									</td>
								 </tr>
	                             <tr>
									<td class="formTable">失效日期:</td>
									<td align="left">
										<html:text property="expiryTime" maxlength="10" styleClass="date_but" 
										onfocus="WdatePicker()"  readonly="true" />
									</td>
									<td class="formTable">附加数据3:</td>
									<td align="left"><html:text property="memo3"
											maxlength="50" />
									</td>
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
	<html:javascript formName="mdmDistEmpProdgroupForm"
		staticJavascript="false" dynamicJavascript="true" cdata="false" />
	<script type="text/javascript" src="${ctx}/scripts/validator.jsp"></script>
</body>
</html>
