<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>${mr['page.customize.title.businessCalendarMaint']}</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>
		<script type="text/javascript" src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
		<script>
		function save() {
  		   mdmCalendarForm.submit();
		}
		</script>
	</head>
	<body onload="WindowSollAuto()" onresize="WindowSollAuto()">
		<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/mdm/mdmCalendar.do?method=save" method="post">
			<html:hidden property="calId" />
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						${mr['page.customize.title.businessCalendarMaint']}
					</h4>
					<div class='MenuList'>
						<a href="javascript:save();">${mr['page.common.button.save']}</a>
						<a href="mdmCalendar.do?method=list">${mr['page.common.button.cancel']}</a>
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
										<td class="formTable">${mr['page.common.year']}</td>
										<td align="left"><html:text property="calYear" maxlength="4"/>
						
										</td>
										<td class="formTable">${mr['page.common.season']}</td>
										<td align="left"><html:text property="calQuarter" maxlength="1"/>
								
										</td>
									</tr>
									
									<tr>
										<td class="formTable">${mr['page.common.month']}</td>
										<td align="left"><html:text property="calMonth" maxlength="2"/>
								
										</td>
										<td class="formTable">${mr['page.common.week']}</td>
										<td align="left"><html:text property="calWeek" maxlength="2"/>
							
										</td>
									</tr>
									<tr>
										<td class="formTable">${mr['page.common.weekMonth']}</td>
										<td align="left"><html:text property="calWeekOfMonth" maxlength="2"/>
								
										</td>
										<td class="formTable">${mr['page.common.day']}</td>
										<td align="left"><html:text property="calDay" maxlength="2"/>
								
										</td>
									</tr>
									<tr>
										<td class="formTable">${mr['page.common.date']}</td>
										<td align="left" colspan="3"><html:text property="calDate" maxlength="20" styleClass="date_but" onfocus="WdatePicker()" readonly="true"/></td>
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
		<html:javascript formName="mdmCalendarForm" staticJavascript="false" dynamicJavascript="true" cdata="false" />
		<script type="text/javascript" src="${ctx}/scripts/validator.jsp"></script>
	</body>
</html>
