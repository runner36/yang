<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<base target=_self></base>
		<title><bean:message bundle="page" key="common.changePass"/></title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script>
		<html:javascript dynamicJavascript="false" staticJavascript="true"/>
		function save() {
			if (validateChangePassForm(changePassForm)) {
				changePassForm.submit();
			}
		}
		</script>
	</head>
	<body onload="WindowSollAuto()" onresize="WindowSollAuto()">
		<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/changePass.do" method="post">
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						<bean:message bundle="page" key="common.changePass"/>
					</h4>
					<div class='MenuList'>
						<a href="javascript:save();"><bean:message bundle="page" key="common.button.save"/></a>
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
										<td class="formTable"><bean:message bundle="page" key="common.userAccount"/><font color="#FF0000">＊</font></td>
										<td align="left"><html:text property="userAccount" maxlength="20"/></td>
									</tr>
									
									 <tr>
										<td class="formTable"><bean:message bundle="page" key="common.userPassword"/><font color="#FF0000">＊</font></td>
										<td align="left"><html:password property="userPassword" maxlength="20"/></td>
									 </tr>
									
									 <tr>
										<td class="formTable"><bean:message bundle="page" key="common.newPass"/><font color="#FF0000">＊</font></td>
										<td align="left"><html:password property="newPass" maxlength="20"/></td>
									 </tr>
									
									 <tr>
										<td class="formTable"><bean:message bundle="page" key="common.repeatNewPass"/><font color="#FF0000">＊</font></td>
										<td align="left"><html:password property="newPass2" maxlength="20"/></td>
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
		<html:javascript formName="changePassForm" staticJavascript="false" dynamicJavascript="true" cdata="false"/>
	</body>
</html>
