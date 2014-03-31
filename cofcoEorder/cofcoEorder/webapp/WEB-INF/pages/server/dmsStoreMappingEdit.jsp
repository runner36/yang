<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>${mr['page.customize.title.custMappingMaint']}</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/dms.js"></script>
		<script type="text/javascript" src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
		<script>
		function save() {
			if (validateDmsStoreMappingForm(dmsStoreMappingForm)) {
				dmsStoreMappingForm.submit();
			}
		}
		function back() {
			window.history.go(-1);
		}
		function list() {
			if ('${param.flag}' == '1') {
				location = 'dmsStoreMapping.do?method=noMappingList';
			}
			else {
				location = 'dmsStoreMapping.do?method=list';
			}
		}
		function selectClient() {
			var v = openClientTree('${ctx}');
			if (v) {
				dmsStoreMappingForm.clientId.value = v.id;
				dmsStoreMappingForm.clientName.value = v.text;
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
		function getStoreName() {
			var form = dmsStoreMappingForm;
			form.targetStoreName.value = '';
			var clientId = form.clientId.value;
			var targetStoreCode = form.targetStoreCode.value;
			if (targetStoreCode != '') {
				createXMLHttpRequest();
				xmlhttp.open("post", "${ctx}/server/dmsStoreMapping.do?method=getStoreName&clientId=" + clientId + "&targetStoreCode=" + targetStoreCode, true);
				xmlhttp.onreadystatechange = function() {
					if(xmlhttp.readyState == 4) {
						if(xmlhttp.status == 200) {
							if(xmlhttp.responseText!=null&&xmlhttp.responseText!='') {
								form.targetStoreName.value = xmlhttp.responseText;
							}
							else {
								form.targetStoreName.value = '';
								form.targetStoreCode.value = '';
								alert("${mr['page.common.mess.targetStoreCodeNotFound']}！");
							}
						}
						else {
							form.targetStoreName.value = '';
							form.targetStoreCode.value = '';
							alert("${mr['page.common.mess.targetStoreCodeNotFound']}！");
						}
					}
				};
				xmlhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
				xmlhttp.send(null);
			}
		}
		</script>
	</head>
	<body onload="WindowSollAuto()" onresize="WindowSollAuto()">
		<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/server/dmsStoreMapping.do?method=save" method="post">
			<html:hidden property="activeSId" />
			<html:hidden property="activeId"/>
			<html:hidden property="flag" />
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						${mr['page.customize.title.custMappingMaint']}
					</h4>
					<div class='MenuList'>
						<a href="javascript:save();" id="aaa">${mr['page.common.button.save']}</a>
						<a href="javascript:list();">${mr['page.common.button.cancel']}</a>
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
										<td class="formTable">${mr['page.common.clientName']}<font color="#FF0000">＊</font></td>
										<td align="left">
											<html:hidden property="clientId"/><html:text property="clientName" maxlength="20" onchange="getStoreName()" readonly="true" style="background-color: #F0F0F0"/>
										</td>
										<td class="formTable"></td>
										<td align="left"></td>
									</tr>
								    <tr>
										<td class="formTable">${mr['page.common.distStoreCode']}<font color="#FF0000">＊</font></td>
										<td align="left"><html:text property="distStoreCode" readonly="true" style="background-color: #F0F0F0"/></td>
										<td class="formTable"></td>
										<td align="left"></td>
								    </tr>
								    <tr>
										<td class="formTable">${mr['page.common.distStoreName']}</td>
										<td align="left"><html:text property="distStoreName" readonly="true" style="background-color: #F0F0F0"/></td>
										<td class="formTable">${mr['page.common.distStoreAddress']}</td>
										<td align="left"><html:text property="distStoreAddr" readonly="true" style="background-color: #F0F0F0"/></td>
								    </tr>
								    <tr>
										<td class="formTable">${mr['page.common.custStandardCode']}<font color="#FF0000">＊</font></td>
										<td align="left"><html:text property="targetStoreCode" maxlength="50" onchange="getStoreName()"/></td>
										<td class="formTable">${mr['page.common.custStandardName']}</td>
										<td align="left"><html:text property="targetStoreName" readonly="true" style="background-color: #F0F0F0"/></td>
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
		<html:javascript formName="dmsStoreMappingForm" staticJavascript="false" dynamicJavascript="true" cdata="false" />
		<script type="text/javascript" src="${ctx}/scripts/validator.jsp"></script>
	</body>
</html>
