<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>${mr['page.customize.title.custMasterDataMaint']}</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>
		<script type="text/javascript" src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
		<script>
		function save() {
			if(mdmStoreForm.firstItemDate.value!=''&&mdmStoreForm.lastItemDate.value!=''){
				if(mdmStoreForm.lastItemDate.value.substring(0,10)<mdmStoreForm.firstItemDate.value.substring(0,10)){
					alert('${mr['base.mess.orderDateCheck']}');
					return;
				}
			}
 			 mdmStoreForm.submit();
		}
		function selectDict(dictName, id, name) {
			var v = openDictTree('${ctx}', dictName);
			if (v) {
				id.value = v.id;
				name.value = v.text;
			}
		}
		function selectOrg(orgName, id, name) {
			var v = openOrgTree('${ctx}', orgName);
			if (v) {
				id.value = v.id;
				name.value = v.text;
			}
		}
		function selectStore(storeName, id, name) {
			var v = openStoreTree('${ctx}', storeName);
			if (v) {
				id.value = v.id;
				name.value = v.text;
			}
		}
		function selectDistri(distriName, id, name) {
			var v = openDistTree('${ctx}','2','0','1', distriName);
			if (v) {
				id.value = v.id;
				name.value = v.text;
			}
		}
		</script>
	</head>
	<body onLoad="WindowSollAuto()" onResize="WindowSollAuto()">
		<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/mdm/mdmStore.do?method=save" method="post">
			<html:hidden property="storeId" />
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						${mr['page.customize.title.custMasterDataMaint']}
					</h4>
					<div class='MenuList'>
						<a href="javascript:save();">${mr['page.common.button.save']}</a>
						<a href="mdmStore.do?method=list">${mr['page.common.button.cancel']}</a>
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
										<td class="formTable">${mr['page.common.storeCode']}<font color="#FF0000">＊</font></td>
										<td align="left">
										    <html:text property="storeCode" maxlength="25"/>
										 </td>
										<td class="formTable">${mr['page.common.externalCode']}</td>
										<td align="left">
										   <html:text property="extCode" maxlength="25"/>
										</td>
									</tr>
								    <tr>
										<td class="formTable">${mr['page.common.storeName']}<font color="#FF0000">＊</font></td>
										<td align="left">
										     <html:text property="storeName" maxlength="100"/>
										 </td>
										<td class="formTable">${mr['page.common.storeAlias']}</td>
										<td align="left"><html:text property="storeAlias" maxlength="100"/></td>
									</tr>
									<tr>
										<td class="formTable">${mr['page.common.parentStore']}</td>
										<td align="left"><html:hidden property="parentStoreId"/><html:text property="parentStoreName" readonly="true" styleClass="select_but" onclick="selectStore('storeTree',mdmStoreForm.parentStoreId,mdmStoreForm.parentStoreName)"/></td>
									
										<td class="formTable">${mr['page.common.organization']}<font color="#FF0000">＊</font></td>
										<td align="left"><html:hidden property="orgId"/><html:text property="orgName" readonly="true" styleClass="select_but" onclick="selectOrg('orgTree',mdmStoreForm.orgId,mdmStoreForm.orgName)"/></td>
										<%--<td class="formTable">${mr['page.common.organization']}</td>
										<td align="left"><html:hidden property="orgId"/><html:text property="orgName" readonly="true" /></td>--%>
									</tr>
									<tr>
										<td class="formTable">${mr['page.common.geographicArea']}<font color="#FF0000">＊</font></td>
										<td align="left"><html:hidden property="geoId"/><html:text property="geoName" readonly="true" styleClass="select_but" onclick="selectDict('geography',mdmStoreForm.geoId,mdmStoreForm.geoName)"/></td>
										<td class="formTable">${mr['page.common.storeType']}</td>
										<td align="left"><html:hidden property="typeId"/><html:text property="typeName" readonly="true" styleClass="select_but" onclick="selectDict('storeType',mdmStoreForm.typeId,mdmStoreForm.typeName)"/></td>
									</tr>
									
									<tr>
										<td class="formTable">${mr['page.common.storeProperty']}</td>
										<td align="left"><html:hidden property="natureId"/><html:text property="natureName" readonly="true" styleClass="select_but" onclick="selectDict('storeNature',mdmStoreForm.natureId,mdmStoreForm.natureName)"/></td>
										<td class="formTable">${mr['page.common.storeOrg']}</td>
										<td align="left"><html:hidden property="corpId"/><html:text property="corpName" readonly="true" styleClass="select_but" onclick="selectDict('storeCorp',mdmStoreForm.corpId,mdmStoreForm.corpName)"/></td>
									</tr>
									
									<tr>
										<td class="formTable">${mr['page.common.otherCategories']}</td>
										<td align="left"><html:hidden property="otherId"/><html:text property="otherName" readonly="true" styleClass="select_but" onclick="selectDict('storeOther',mdmStoreForm.otherId,mdmStoreForm.otherName)"/></td>
										<td class="formTable">${mr['page.common.storeChannel']}</td>
										<td align="left"><html:hidden property="channelId"/><html:text property="channelName" readonly="true" styleClass="select_but" onclick="selectDict('storeChannel',mdmStoreForm.channelId,mdmStoreForm.channelName)"/></td>
									</tr>
									
									<tr>
										<td class="formTable">${mr['page.common.supplier']}</td>
										<td align="left"><html:hidden property="distId"/><html:text property="distName" readonly="true" styleClass="select_but" onclick="selectDistri('distriTree',mdmStoreForm.distId,mdmStoreForm.distName)"/></td>
										<td class="formTable"></td>
										<td align="left"></td>
										
									</tr>
									<tr>
										<td class="formTable">${mr['page.common.storeAddress']}</td>
										<td align="left"><html:text property="storeAddr" maxlength="100"/></td>
										<td class="formTable">${mr['page.common.postcode']}</td>
										<td align="left"><html:text property="zipCode" maxlength="10"/></td>
									</tr>
									
									<tr>
										<td class="formTable">${mr['page.common.contact']}</td>
										<td align="left"><html:text property="linkman" maxlength="100"/></td>
										<td class="formTable">${mr['page.common.telephone']}</td>
										<td align="left"><html:text property="linktel" maxlength="100"/></td>
									</tr>
									
									<tr>
										<td class="formTable">${mr['page.common.firstOrderTime']}</td>
										<td align="left"><html:text property="firstItemDate" maxlength="20" styleClass="date_but" onfocus="WdatePicker()" readonly="true"/></td>
										<td class="formTable">${mr['page.common.lastOrderTime']}</td>
										<td align="left"><html:text property="lastItemDate" maxlength="20" styleClass="date_but" onfocus="WdatePicker()" readonly="true"/></td>
									</tr>
									<tr>
										<td class="formTable">${mr['page.common.custLevel']}</td>
										<td align="left"><html:text property="storeLevel" maxlength="100"/></td>
										<td class="formTable">${mr['page.common.custRelaIsValid']}</td>
										<td align="left">${mr['page.common.yes']}<html:radio property="storeRelationState" styleClass="Choose_input" value="1"/>${mr['page.common.no']}<html:radio property="storeRelationState" styleClass="Choose_input" value="0"/></td>
									</tr>
									<tr>
										<td class="formTable">${mr['page.common.sort']}</td>
										<td align="left"><html:text property="sort" maxlength="10"/></td>
										<td class="formTable">${mr['page.common.status']}</td>
										<td align="left">${mr['page.common.yes']}<html:radio property="state" styleClass="Choose_input" value="1"/>${mr['page.common.no']}<html:radio property="state" styleClass="Choose_input" value="0"/></td>
								    </tr>
									<tr>
										<td class="formTable">${mr['page.common.memo']} 1</td>
										<td align="left"><html:text property="memo1" maxlength="100"/></td>
										<td class="formTable">${mr['page.common.memo']} 2</td>
										<td align="left"><html:text property="memo2" maxlength="100"/></td>
									</tr>
									<tr>
										<td class="formTable">${mr['page.common.memo']} 3</td>
										<td align="left"><html:text property="memo3" maxlength="100"/></td>
										<td class="formTable">${mr['page.common.memo']} 4</td>
										<td align="left"><html:text property="memo4" maxlength="100"/></td>
									</tr>
									<tr>
										<td class="formTable">${mr['page.common.memo']} 5</td>
										<td align="left"><html:text property="memo5" maxlength="100"/></td>
										<td class="formTable">${mr['page.common.memo']} 6</td>
										<td align="left"><html:text property="memo6" maxlength="100"/></td>
									</tr>
									
								    	  <tr>
									    <td class="formTable">${mr['page.common.memo']}</td>
									    <td align="left" colspan="3"><html:textarea property="remark" rows="3" cols="80"></html:textarea></td>
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
		<html:javascript formName="mdmStoreForm" staticJavascript="false" dynamicJavascript="true" cdata="false" />
		<script type="text/javascript" src="${ctx}/scripts/validator.jsp"></script>
	</body>
</html>
