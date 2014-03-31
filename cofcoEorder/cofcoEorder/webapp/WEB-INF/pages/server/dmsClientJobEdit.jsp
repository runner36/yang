<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>${mr['page.customize.title.clientManage']}</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/dms.js"></script>
		<script type="text/javascript" src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
		<script>
		function save() {
			if (validateDmsClientJobForm(dmsClientJobForm)) {
				dmsClientJobForm.submit();
			}
		}
		function selectClient() {
			var v = openClientTree('${ctx}');
			if (v) {
				dmsClientJobForm.clientId.value = v.id;
				dmsClientJobForm.clientName.value = v.text;
				location = 'dmsClientJob.do?method=add&clientId=' + v.id;
			}
		}
		</script>
	</head>
	<body onload="WindowSollAuto();if (dmsClientJobForm.clientName.value != '') dmsClientJobForm.clientName.disabled = true;">
		<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/server/dmsClientJob.do?method=save" method="post">
			<html:hidden property="jobId" />
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						${mr['page.customize.title.clientManage']}
					</h4>
					<div class='MenuList'>
						<c:if test="${dmsClientJobForm.map.jobType == null || dmsClientJobForm.map.jobType=='UserJob'}"><a href="javascript:save();">${mr['page.common.button.save']}</a></c:if>
						<a href="dmsClientJob.do?method=list">${mr['page.common.button.cancel']}</a>
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
										<td class="formTable" width="60">${mr['page.common.client']}<font color="#FF0000">＊</font></td>
										<td align="left" colspan="3">
											<html:hidden property="clientId"/><html:text property="clientName" maxlength="20" styleClass="select_but" onclick="selectClient()" readonly="true"/>
										</td>
										<td class="formTable" width="100">${mr['page.common.proxyClientCode']}</td>
										<td align="left" colspan="3">
											<html:text property="proxyClient" maxlength="50"/>
										</td>
									</tr>
								</table>
								<br/>
								<table border="0">
									<tr>
										<th>&nbsp;&nbsp;${mr['page.common.button.select']}</th>
										<th>${mr['page.common.taskName']}</th>
										<th>${mr['page.common.taskParameter']}</th>
									</tr>
									<c:forEach items="${jobList}" var="job" varStatus="i">
									<tr>
										<td>&nbsp;&nbsp;<input type="checkbox" name="select_${job[1]}" ${job[0]=='1'?'checked':''}/></td>
										<td width="120" align="center">${job[2]}</td>
										<td><input type="text" name="param_${job[1]}" size="100" value="${job[3]}"/></td>
									</tr>
									</c:forEach>
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
		<html:javascript formName="dmsClientJobForm" staticJavascript="false" dynamicJavascript="true" cdata="false" />
		<script type="text/javascript" src="${ctx}/scripts/validator.jsp"></script>
	</body>
</html>
