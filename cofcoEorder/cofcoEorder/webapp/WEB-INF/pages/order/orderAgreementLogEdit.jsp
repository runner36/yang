<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>补充协议签署维护</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>
		<script type="text/javascript" src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
		<script>
		function save() {
  		    orderAgreementLogForm.submit();
		}
		
		</script>
	</head>
	<body onLoad="WindowSollAuto()" onResize="WindowSollAuto()">
		<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/order/orderAgreementLog.do?method=save" method="post">
			<html:hidden property="id" />
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						补充协议签署维护
					</h4>
					<div class='MenuList'>
						<a href="javascript:save();">${mr['page.common.button.save']}</a>
						<a href="orderAgreementLog.do?method=list">${mr['page.common.button.cancel']}</a>
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
										<td class="formTable">客户编码<font color="#FF0000"></font></td>
										<td align="left">
										    <html:text property="distCode"  maxlength="25"/>
										 </td>
										<td class="formTable">客户名称<font color="#FF0000"></font></td>
										<td align="left">
										   <html:text property="distName"  maxlength="100"/>
										</td>
									</tr>
									
									<tr>
										<td class="formTable">用户名<font color="#FF0000"></font></td>
										<td align="left">
										    <html:text property="userAccount"  maxlength="25"/>
										 </td>
										<td class="formTable">人员名称<font color="#FF0000"></font></td>
										<td align="left">
										   <html:text property="empName"  maxlength="100"/>
										</td>
									</tr>
									
									<tr>
										<td class="formTable">协议状态<font color="#FF0000"></font></td>
										<td align="left">
										    有效<html:radio property="status" styleClass="Choose_input" value="1"/>&nbsp;&nbsp;&nbsp;&nbsp;无效<html:radio property="status" styleClass="Choose_input" value="0"/>
										 </td>
										<td class="formTable">签署日期<font color="#FF0000"></font></td>
										<td align="left">
										   <html:text property="createDate" maxlength="20" styleClass="date_but" onfocus="WdatePicker()" readonly="true"/>
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
		<%-- <html:javascript formName="orderAgreementLogForm" staticJavascript="false" dynamicJavascript="true" cdata="false" />
		<script type="text/javascript" src="${ctx}/scripts/validator.jsp"></script> --%>
	</body>
</html>
