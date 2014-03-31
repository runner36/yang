<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>${mr['base.user.title']}</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script type="text/javascript" src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
		<script>
		function save() {
			var form = baseUserForm;
			if (form.userPassword && form.userPassword.value == '') {
				alert('${mr['base.mess.initPass']}');
				return;
			}
			if (validateBaseUserForm(form)) {
				form.roleId.value = tree.getId();
				form.submit();
			}
			
		}
		function selectEmp() {
			var v = openEmpTree('${ctx}');
			if (v) {
				baseUserForm.empId.value = v.id;
				baseUserForm.empName.value = v.text;
			}
		}
		function selectAuthEmp() {
			var form = baseUserForm;
			var v = openEmpTree('${ctx}', '', '1', form.empAuth.value);
			if (v) {
				form.empAuth.value = v.id;
				form.empAuthNames.value = v.text;
			}
		}
		function selectCascadeAuthEmp() {
			var form = baseUserForm;
			var v = openEmpTree('${ctx}', '', '1', form.empCascadeAuth.value);
			if (v) {
				form.empCascadeAuth.value = v.id;
				form.empCascadeAuthNames.value = v.text;
			}
		}
		function selectAuthOrg() {
			var form = baseUserForm;
			var v = openOrgTree('${ctx}', '', '1', form.orgAuth.value);
			if (v) {
				form.orgAuth.value = v.id;
				form.orgAuthNames.value = v.text;
			}
		}
		function selectAuthProd() {
			var form = baseUserForm;
		}
		function selectRes() {
			var v = openResTree('${ctx}', '1');
			if (v) {
				baseUserForm.resId.value = v.id;
				baseUserForm.resName.value = v.text;
			}
		}
		</script>
	</head>
	<body onload="WindowSollAuto()" onresize="WindowSollAuto()">
		<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/base/baseUser.do?method=save" method="post">
			<input type="hidden" name="roleId"/>
			<html:hidden property="userId" />
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						${mr['base.user.title']}
					</h4>
					<div class='MenuList'>
						<a href="javascript:save();">${mr['page.common.button.save']}</a>
						<a href="baseUser.do?method=list">${mr['page.common.button.cancel']}</a>
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
								<table class="listWarp">
									<tr>
										<td width="180" height="100%" align="left" valign="top" id="leftmenu1" style="display:block">
										    <iframe src="${ctx}/tree/baseTree.do?method=roleTree&checkbox=1&values=${roleValues}" name="tree" width="100%" 
												marginwidth="0" height="100%" style="height:100%" marginheight="0" align="top"
												scrolling="auto" frameborder="0"></iframe>
										</td>
										<td valign="top">
											<table class="list_add">
												<tr>
													<td class="formTable">${mr['base.emp.empName']}<font color="#FF0000">＊</font></td>
	    											<td align="left"><html:hidden property="empId"/><html:text property="empName" maxlength="20" styleClass="select_but" onclick="selectEmp()" readonly="true"/></td>
													<td class="formTable">${mr['base.user.userType']}<font color="#FF0000">＊</font></td>
													<td align="left">
														<html:select property="userType" style="width: 180px">
															<html:option value="1">系统用户</html:option>
															<html:option value="2">手机用户</html:option>
														</html:select>
													</td>
												</tr>
												<tr>
													<td class="formTable">${mr['base.user.userAccount']}<font color="#FF0000">＊</font></td>
													<td align="left"><html:text property="userAccount" maxlength="20"/></td>
													<c:if test="${baseUserForm.map.userId==null}">
													<td class="formTable">${mr['base.user.userPassword']}<font color="#FF0000">＊</font></td>
													<td align="left"><html:password property="userPassword" maxlength="20"/></td>
													</c:if>
												</tr>
												<tr>
													<td class="formTable">${mr['base.user.lastPassDate']}</td>
													<td align="left" colspan="3"><html:text property="lastPassDate" maxlength="20" styleClass="date_but" onfocus="WdatePicker()"/></td>
												</tr>
												<tr>
													<td class="formTable">${mr['base.user.homeRes']}</td>
	    											<td align="left"><html:hidden property="resId"/><html:text property="resName" maxlength="20" styleClass="select_but" onclick="selectRes()" readonly="true"/></td>
													<td class="formTable">${mr['base.user.homeParam']}</td>
													<td align="left"><html:text property="homeParam" maxlength="80"/></td>
												</tr>
												<tr>
													<td class="formTable">${mr['base.user.empAuth']}<html:hidden property="empAuth" /></td>
													<td align="left" colspan="3"><html:textarea property="empAuthNames" rows="3" cols="80" readonly="true"/><input type="button" class="button" value="${mr['page.common.button.select']}" onclick="selectAuthEmp()"/></td>
												</tr>
												<tr>
													<td class="formTable">${mr['base.user.empCascadeAuth']}<html:hidden property="empCascadeAuth" /></td>
													<td align="left" colspan="3"><html:textarea property="empCascadeAuthNames" rows="2" cols="80" readonly="true"/><input type="button" class="button" value="${mr['page.common.button.select']}" onclick="selectCascadeAuthEmp()"/></td>
												</tr>
												<tr>
													<td class="formTable">${mr['base.user.orgAuth']}<html:hidden property="orgAuth" /></td>
													<td align="left" colspan="3"><html:textarea property="orgAuthNames" rows="3" cols="80" readonly="true"/><input type="button" class="button" value="${mr['page.common.button.select']}" onclick="selectAuthOrg()"/></td>
												</tr>
												<%--<tr>
													<td class="formTable">产品授权<html:hidden property="prodAuth" /></td>
													<td align="left" colspan="3"><html:textarea property="prodAuthNames" rows="3" cols="80" readonly="true"/><input type="button" class="button" value="${mr['page.common.button.select']}" onclick="selectAuthProd()"/></td>
												</tr>--%>
												
												<tr>
												    <td class="formTable">${mr['base.state']}</td>
												    <td align="left"><html:radio property="state" styleClass="Choose_input" value="1"/>${mr['base.valid']}　<html:radio property="state" styleClass="Choose_input" value="0"/>${mr['base.invalid']}</td>
												</tr>
												<tr>
													<td class="formTable">${mr['base.sort']}</td>
													<td align="left"><html:text property="sort" maxlength="20"/></td>
												</tr>
												
												<tr>
													<td class="formTable">${mr['base.remark']}</td>
													<td align="left" colspan="3"><html:textarea property="remark" rows="3" cols="80"></html:textarea></td>
												</tr>
												<tr>
													<td class="formTable">${mr['base.createdBy']}</td>
													<td align="left"><html:text property="createdBy" disabled="true"/></td>
													<td class="formTable">${mr['base.created']}</td>
													<td align="left"><html:text property="created" disabled="true"/></td>
												</tr>
												<tr>
													<td class="formTable">${mr['base.updatedBy']}</td>
													<td align="left"><html:text property="updatedBy" disabled="true"/></td>
													<td class="formTable">${mr['base.updated']}</td>
													<td align="left"><html:text property="updated" disabled="true"/></td>
												</tr>
											</table>
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
		<html:javascript formName="baseUserForm" staticJavascript="false" dynamicJavascript="true" cdata="false" />
		<script type="text/javascript" src="${ctx}/scripts/validator.jsp"></script>
	</body>
</html>
