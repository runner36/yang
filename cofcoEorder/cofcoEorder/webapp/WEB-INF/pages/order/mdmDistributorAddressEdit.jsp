<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>客户送达方维护</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>
		<script type="text/javascript" src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
		<script>
		function save() {
			checkDistShiptoCodeProd();
		}
	  	var loading = false;
		function checkDistShiptoCodeProd() {
			var form = document.mdmDistributorAddressForm;
			var id = form.id.value;
			var distId1 = form.distId.value;
			var prodBrandId1 = form.prodBrandId.value;
			var shiptoCode1 = form.shiptoCode.value;
			
			createXMLHttpRequest();
			xmlhttp.open("post", "${ctx}/order/mdmDistributorAddress.do?method=isSame&distId=" + distId1 + "&prodBrandId=" + prodBrandId1 +"&shiptoCode=" + shiptoCode1 + "&id=" + id, true);
			xmlhttp.onreadystatechange = function() {
				if(xmlhttp.readyState == 4) {
					if(xmlhttp.status == 200) {
						if(xmlhttp.responseText!=null&&xmlhttp.responseText!='') {
							if(xmlhttp.responseText == "0"){
								if(checkMobile()){
						  		     mdmDistributorAddressForm.submit();
						  		}
							}
							if(xmlhttp.responseText == "1"){
								alert("已经存在相同的客户，物料组,送达方编码的关系维护，不能重复添加 ");
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
				var form = document.mdmDistributorAddressForm;
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
	var loading = false;
	function getDistName() {
		var form = document.mdmDistributorAddressForm;
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

		//产品
		function selectProd(id, name) {
		var form = document.mdmDistributorAddressForm;
			var v = openProdTree('${ctx}',form.prodBrandId.value, '2','0');
			if (v) {
				form.prodBrandId.value.value = v.id;
				form.prodBrandName.value = v.text;
			}
		}
		
				//验证手机号码
		 function checkMobile(){
		 	 var form = document.mdmDistributorAddressForm;
			 var reg = /^1[358]\d{9}$/;
			 var mob = form.mobile.value;
			 var flag = "true";
			 if('' != mob){
			 	if(!mob.match(reg)) {
	                 alert("请输入正确的手机号码");
	                 flag = false;
            	 }
			 }
             return flag;
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
		</script>
	</head>
	<body onLoad="WindowSollAuto()" onResize="WindowSollAuto()">
		<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/order/mdmDistributorAddress.do?method=save" method="post">
			<html:hidden property="id" />
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						客户送达方维护
					</h4>
					<div class='MenuList'>
						<a href="javascript:save();">保存</a>
						<a href="mdmDistributorAddress.do?method=list">取消</a>
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
									<td class="formTable">物料组：<font color="#FF0000">＊</font></td>
									<td align="left">
										<html:hidden property="prodBrandId" />
										<html:text property="prodBrandName" readonly="true"
											styleClass="select_but"
											onclick="selectDict('prodSTRU', mdmDistributorAddressForm.prodBrandId, mdmDistributorAddressForm.prodBrandName)" />
									</td>
									<td class="formTable">是否有效：</td>
              						<td align="left"><html:radio property="status" styleClass="Choose_input" value="1"/>是<html:radio property="status" styleClass="Choose_input" value="0"/>否</td>
								</tr>
							 	<tr>
									<td class="formTable">送达方编码：<font color="#FF0000">＊</font></td>
									<td align="left"><html:text property="shiptoCode" maxlength="50"/></td>
									<td class="formTable">送达方名称：<font color="#FF0000">＊</font></td>
									<td align="left"><html:text property="shiptoName" maxlength="50"/></td>
								</tr>
								<tr>
									<td class="formTable">送达方地址：<font color="#FF0000">＊</font></td>
									<td align="left"><html:text property="shiptoAdd" maxlength="100"/></td>
									<td class="formTable">联系人<font color="#FF0000">＊</font></td>
									<td align="left"><html:text property="contact" maxlength="50"/></td>
									
								</tr>
								<tr>
									<td class="formTable">联系电话<font color="#FF0000">＊</font></td>
									<td align="left"><html:text property="tel" maxlength="50"/></td>
									<td class="formTable">手机</td>
									<td align="left"><html:text property="mobile" maxlength="50"/></td>
									</tr>
								<tr>
									<td class="formTable">出货仓库</td>
									<td align="left"><html:text property="factoryDelivery" maxlength="50"/></td>
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
	<html:javascript formName="mdmDistributorAddressForm" staticJavascript="false" dynamicJavascript="true" cdata="false" />
		<script type="text/javascript" src="${ctx}/scripts/validator.jsp"></script>
	</body>
</html>
