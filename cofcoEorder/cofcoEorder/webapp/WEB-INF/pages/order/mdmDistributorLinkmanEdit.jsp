<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>客户联系人信息维护</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>
		<script type="text/javascript" src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
		<script>
		function save() {
			if(checkMobile()){
				mdmDistributorLinkmanForm.submit();
			}
  		    
		}

		//经销商
			function selectDist() {
				var form = document.mdmDistributorLinkmanForm;
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
		var form = document.mdmDistributorLinkmanForm;
			var v = openProdTree('${ctx}',form.prodBrandId.value, '2','0');
			if (v) {
				form.prodBrandId.value = v.id;
				form.prodBrandName.value = v.text;
			}
		}
		//验证手机号码
		 function checkMobile(){
		 	 var form = document.mdmDistributorLinkmanForm;
			 var reg = /^1[358]\d{9}$/;
			 var mob = form.linkmanPhonenum.value;
			 var flag = "true";
             if(!mob.match(reg))
             {
                 alert("请输入正确的手机号码");
                 flag = false;
             }
             return flag;
    
  }
  
  	var loading = false;
	function getDistName() {
		var form = document.mdmDistributorLinkmanForm;
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
		<html:form action="/order/mdmDistributorLinkman.do?method=save" method="post">
			<html:hidden property="id" />
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						客户联系人信息维护
					</h4>
					<div class='MenuList'>
						<a href="javascript:save();">保存</a>
						<a href="mdmDistributorLinkman.do?method=list">取消</a>
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
											onclick="selectDict('prodSTRU', mdmDistributorLinkmanForm.prodBrandId, mdmDistributorLinkmanForm.prodBrandName)" />
									</td>
									<td class="formTable">联系人姓名<font color="#FF0000">＊</font></td>
									<td align="left"><html:text property="linkmanName" maxlength="50"/></td>
								</tr>
								<tr>
									<td class="formTable">手机号码<font color="#FF0000">＊</font></td>
									<td align="left"><html:text property="linkmanPhonenum" maxlength="12" onchange="checkMobile();"/></td>
									<td class="formTable">联系电话</td>
									<td align="left"><html:text property="linkmanTel" maxlength="12"/></td>
								</tr>
								<tr>
									<td class="formTable">是否发送短信：</td>
              						<td align="left"><html:radio property="isSms" styleClass="Choose_input" value="1"/>是
              						&nbsp;&nbsp;
              						<html:radio property="isSms" styleClass="Choose_input" value="0"/>否</td>
									<td class="formTable">联系人状态：</td>
              						<td align="left"><html:radio property="status" styleClass="Choose_input" value="1"/>有效
              						&nbsp;&nbsp;
              						<html:radio property="status" styleClass="Choose_input" value="0"/>停用</td>
              						<td class="formTable"></td>
									<td align="left"></td>
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
		<html:javascript formName="mdmDistributorLinkmanForm" staticJavascript="false" dynamicJavascript="true" cdata="false" />
		<script type="text/javascript" src="${ctx}/scripts/validator.jsp"></script>
	</body>
</html>
