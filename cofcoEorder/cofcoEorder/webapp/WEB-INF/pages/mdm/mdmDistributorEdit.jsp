<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
<head>
<title>${mr['page.customize.title.distMaint']}</title>
<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
<script language="javaScript" src="${ctx}/scripts/base.js"></script>
<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>
<script type="text/javascript"
	src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
<script>
	function save() {
		mdmDistributorForm.submit();
	}
	function selectDict(dictName, id, name) {
		var v = openDictTree('${ctx}', dictName);
		if (v) {
			id.value = v.id;
			name.value = v.text;
		}
	}
	function selectOrg(orgName, id, name) {
		var v = openOrgTree('${ctx}', '2', '0', '1', orgName);
		if (v) {
			id.value = v.id;
			name.value = v.text;
		}
	}
	function selectDistri(distriName, id, name) {
		var v = openDistTree('${ctx}', '2', '0', '1', distriName);
		if (v) {
			id.value = v.id;
			name.value = v.text;
		}
	}
</script>
</head>
<body onLoad="WindowSollAuto()" onResize="WindowSollAuto()">
	<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
	<html:form action="/mdm/mdmDistributor.do?method=save" method="post">
		<html:hidden property="distId" />
		<div class="bosom_one">
			<div class="bosom_top">
				<span class="left"></span>
				<h4>客户信息维护</h4>
				<div class='MenuList'>
					<a href="javascript:save();">${mr['page.common.button.save']}</a> <a
						href="mdmDistributor.do?method=list">${mr['page.common.button.cancel']}</a>
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
									<td class="formTable">客户编码<font color="#FF0000">＊</font>
									</td>
									<td align="left"><html:text property="distCode"
											maxlength="25" /></td>
									<td class="formTable">客户名称<font color="#FF0000">＊</font>
									</td>
									<td align="left"><html:text property="distName"
											maxlength="100" /></td>
								</tr>
								<tr>
									<td class="formTable">联系人</td>
									<td align="left"><html:text property="linkmanName"
											maxlength="100" />
									</td>
									<td class="formTable">手 机</td>
									<td align="left"><html:text property="linkmanTel"
											maxlength="16" />
									</td>
								</tr>

								<tr>
									<td class="formTable">邮政编码</td>
									<td align="left"><html:text property="distPost"
											maxlength="10" />
									</td>
									<td class="formTable">客户电话</td>
									<td align="left"><html:text property="distTel"
											maxlength="16" />
									</td>
								</tr>
								<tr>
									<td class="formTable">客户地址</td>
									<td align="left"><html:text property="distAddr"
											maxlength="40" />
									</td>
									<td class="formTable">客户传真</td>
									<td align="left"><html:text property="mgrTel"
											maxlength="16" />
									</td>
								</tr>

								<tr>
									<td class="formTable">${mr['page.common.organization']}<font
										color="#FF0000">＊</font>
									</td>
									<td align="left"><html:hidden property="orgId" />
										<html:text property="orgName" readonly="true"
											styleClass="select_but"
											onclick="selectOrg('orgTree',mdmDistributorForm.orgId,mdmDistributorForm.orgName)" />
									</td>
									<%--<td class="formTable">${mr['page.common.organization']}</td>
										<td align="left"><html:hidden property="orgId"/><html:text property="orgName" readonly="true"/></td>--%>
									<td class="formTable">${mr['page.common.geographicArea']}</td>
									<td align="left"><html:hidden property="geoId" />
										<html:text property="geoName" readonly="true"
											styleClass="select_but"
											onclick="selectDict('geography',mdmDistributorForm.geoId,mdmDistributorForm.geoName)" />
									</td>
								</tr>

								<tr>
									<td class="formTable">客户收货限制</td>
									<td align="left"><html:text property="ownergrpCode"
											maxlength="10" />
									</td>
									<td class="formTable">客户类型</td>
									<td align="left"><html:text property="ownergrpName"
											maxlength="200" />
									</td>
								</tr>

								<tr>
									<td class="formTable">发票类型</td>
									<td align="left"><html:select property="invoiceTypeId"
											style="width:185px;" styleId="invoiceTypeId">
											<html:option value=""></html:option>
											<logic:present name="invoiceTypeList">
												<logic:iterate id="selitem" name="invoiceTypeList">
													<html:option value="${selitem.dictItemId}">
															${selitem.itemName}
														</html:option>
												</logic:iterate>
											</logic:present>
										</html:select></td>
									<td class="formTable">客户属性1</td>
									<td align="left"><html:text property="memo1"
											maxlength="100" />
									</td>
								</tr>
								<tr>
									<td class="formTable">客户属性2</td>
									<td align="left"><html:text property="memo2"
											maxlength="100" />
									</td>
									<td class="formTable">客户属性3</td>
									<td align="left"><html:text property="memo3"
											maxlength="100" />
									</td>
								</tr>
								<tr>
									<td class="formTable">客户属性4</td>
									<td align="left"><html:text property="memo4"
											maxlength="100" />
									</td>
									<td class="formTable">客户属性5</td>
									<td align="left"><html:text property="memo5"
											maxlength="100" />
									</td>
								</tr>
								<tr>
									<td class="formTable">客户状态</td>
									<td align="left">有效<html:radio property="state"
											styleClass="Choose_input" value="1" />&nbsp;&nbsp;&nbsp;&nbsp;停用<html:radio
											property="state" styleClass="Choose_input" value="0" />
									</td>
									<td class="formTable">${mr['page.common.sort']}</td>
									<td align="left"><html:text property="sort" maxlength="10" />
									</td>
								</tr>

								<tr>
									<td class="formTable">${mr['page.common.personInchargeName']}</td>
									<td align="left"><html:text property="mgrName"
											maxlength="100" />
									</td>
									<td class="formTable">${mr['page.common.parentDist']}</td>
									<td align="left"><html:hidden property="parentDistId" />
										<html:text property="parentDistName" readonly="true"
											styleClass="select_but"
											onclick="selectDistri('distriTree',mdmDistributorForm.parentDistId,mdmDistributorForm.parentDistName)" />
									</td>
								</tr>

								<tr>
									<td class="formTable">${mr['page.common.installDate']}</td>
									<td align="left"><html:text property="instDate"
											maxlength="20" styleClass="date_but" onfocus="WdatePicker()"
											readonly="true" /></td>
									<td class="formTable">${mr['page.common.checkDate']}</td>
									<td align="left"><html:text property="checkDate"
											maxlength="20" styleClass="date_but" onfocus="WdatePicker()"
											readonly="true" /></td>
								</tr>
								<tr>
									<td class="formTable">${mr['page.common.mappingDate']}</td>
									<td align="left"><html:text property="mappingDate"
											maxlength="20" styleClass="date_but" onfocus="WdatePicker()"
											readonly="true" /></td>
									<td class="formTable">${mr['page.common.returnDate']}</td>
									<td align="left"><html:text property="passBackDate"
											maxlength="20" styleClass="date_but" onfocus="WdatePicker()"
											readonly="true" /></td>
								</tr>
								<tr>
									<td class="formTable">${mr['page.common.lastreturnDate']}</td>
									<td align="left"><html:text property="endPassDate"
											maxlength="20" styleClass="date_but" onfocus="WdatePicker()"
											readonly="true" /></td>
									<td class="formTable">${mr['page.common.dataStartDate']}</td>
									<td align="left"><html:text property="passDataDate"
											maxlength="20" styleClass="date_but" onfocus="WdatePicker()"
											readonly="true" /></td>
								</tr>

								<tr>
									<td class="formTable">${mr['page.common.memo']}</td>
									<td align="left" colspan="3"><html:textarea
											property="remark" rows="3" cols="80"></html:textarea>
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
	<html:javascript formName="mdmDistributorForm" staticJavascript="false"
		dynamicJavascript="true" cdata="false" />
	<script type="text/javascript" src="${ctx}/scripts/validator.jsp"></script>
</body>
</html>
