<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>${mr['page.customize.title.systemLog']}</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/widgets/extremecomponents/extremecomponents.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script type="text/javascript" src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
		<script>
			function query() {
				resetec();
				var form = document.baseLogForm;
				form.submit();
			}
		</script>
	</head>
	<body onload="WindowSollAuto()" onresize="WindowSollAuto()">
		<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/base/baseLog.do?method=list" method="post">
		<div class="bosom_one">
			<div class="bosom_top">
				<span class="left"></span>
				<h4>
					<!--TitleStrat-->
					${mr['page.customize.title.systemLog']}
					<!--TitleStrat-->
				</h4>
				<div class='MenuList'>
					<!--MenuStrat-->
					<a href="javascript:showQuery_('query_A');" id="showQuery_A"  >${mr['page.common.button.setquery']}</a>
					 <a href="javascript:hideQuery_('query_A');" id="showQuery_A"  >${mr['page.common.button.close']}</a>
					<a href="javascript:baseLogForm.ec_p.value=1;query()" id="query_A">${mr['page.common.button.query']}</a>
					<a href="javascript:location.reload();">${mr['page.common.button.refresh']}</a>
					<!--MenuEnd-->
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
						<div class="searchbox" id="searchbox">
							<table>
							  	<tr>
								    <td width="1%">${mr['page.common.username']}</td>
								    <td width="1%"><html:text name="ec" property="$lk_userName"/></td>
								    <td width="1%">${mr['page.common.accessUrl']}</td>
								    <td width="1%"><html:text name="ec" property="$lk_resUri"/></td>
								    <td width="1%">${mr['page.common.accessIp']}</td>
								    <td width="1%"><html:text name="ec" property="$lk_clientIp"/></td>
								    <td width="1%">${mr['page.common.accessDate']}</td>
									<td>
										<html:text name="ec" property="$ge_startTime" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>-<html:text name="ec" property="$le_startTime" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>
									</td>
							  	</tr>
							</table>
							</div>
							<ec:table items="list" var="item" onInvokeAction="query()" form="baseLogForm"
							        retrieveRowsCallback="limit" style="width:100%" tableId="ec"
								    action="baseLog.do?method=list">
								<ec:exportXls fileName="logs.xls" tooltip="${mr['page.common.exportExcel']}" view="xls" />
								<ec:row highlightRow="true">
								
									<ec:column property="userName" title="${mr['page.common.username']}"/>
									<ec:column property="resUri" title="${mr['page.common.accessResources']}"/>
									<ec:column property="resName" title="${mr['page.common.ResourcesName']}"/>
									<ec:column property="startTime" title="${mr['page.common.startTime']}" cell="date" format="yyyy-MM-dd HH:mm:ss"/>
									<ec:column property="endTime" title="${mr['page.common.endTime']}" cell="date" format="yyyy-MM-dd HH:mm:ss"/>
									<ec:column property="usedTime" title="${mr['page.common.spentTotalTime']}"/>
									<ec:column property="clientIp" title="${mr['page.common.clientIp']}"/>
									<ec:column property="clientName" title="${mr['page.common.clientHostname']}"/>
						
								</ec:row>
							</ec:table>
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
	</body>
</html>
