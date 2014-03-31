<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>${mr['page.customize.title.rolManage']}</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script>
		function save() {
			if (validateBaseRoleForm(baseRoleForm)) {
				baseRoleForm.resId.value = tree.getId();
				baseRoleForm.submit();
			}
			
		}
		</script>
	</head>
	<body onload="WindowSollAuto()" onresize="WindowSollAuto()">
		<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/base/baseRole.do?method=save" method="post">
			<input type="hidden" name="resId"/>
			<html:hidden property="roleId" />
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						${mr['page.customize.title.rolManage']}
					</h4>
					<div class='MenuList'>
						<a href="javascript:save();">${mr['page.common.button.save']}</a>
						<a href="baseRole.do?method=list">${mr['page.common.button.cancel']}</a>
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
										    <iframe src="${ctx}/tree/baseTree.do?method=resTree&checkbox=1&onlyLeaf=1&values=${resValues}" name="tree" width="100%" 
												marginwidth="0" height="100%" style="height:100%" marginheight="0" align="top"
												scrolling="auto" frameborder="0"></iframe>
										</td>
										<td valign="top">
											<table class="list_add">
												<tr>
													<td class="formTable">${mr['page.common.roleName']}<font color="#FF0000">＊</font></td>
													<td align="left"><html:text property="roleName" maxlength="20"/></td>
													<td class="formTable">${mr['page.common.dataAccess']}</td>
												    <td align="left">
												    	<html:select property="dataAuthId">
															<html:options collection="dataAuths" labelProperty="dataAuthName" property="dataAuthId"/>
														</html:select>
													</td>
												</tr>
												<tr>
												    <td class="formTable">${mr['page.common.valid']}</td>
												    <td align="left">${mr['page.common.yes']}<html:radio property="state" styleClass="Choose_input" value="1"/>${mr['page.common.no']}<html:radio property="state" styleClass="Choose_input" value="0"/></td>
													<td class="formTable">${mr['page.common.sort']}</td>
													<td align="left"><html:text property="sort" maxlength="20"/></td>
												</tr>
												
												<tr>
													<td class="formTable">${mr['page.common.memo']}</td>
													<td align="left" colspan="3"><html:textarea property="remark" rows="3" cols="80"></html:textarea></td>
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
		<html:javascript formName="baseRoleForm" staticJavascript="false" dynamicJavascript="true" cdata="false" />
		<script type="text/javascript" src="${ctx}/scripts/validator.jsp"></script>
		<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
	</body>
</html>
