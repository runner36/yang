<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<%@ include file="/commons/meta.jsp" %>
		<title></title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/widgets/extremecomponents/extremecomponents.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>
		<script language="javaScript" src="${ctx}/scripts/dms.js"></script>
		<script type="text/javascript" src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/yearMonth.js"></script>
		<script>
			function query() {
				resetec();
				try{if (!onSubmit()) return;}catch(e){}
				//++++
				if (checkCondition('${mr['page.common.queryCondition']}','${mr['page.common.mustChooseCondition']}','${configurator.condition}')==false){
					return;
				}
				//++++
				loading();
				try{
					listReportForm.state.disabled=false;
					listReportForm.deleteTime1.disabled=false;
					listReportForm.deleteTime2.disabled=false;
				}catch(e){
					
				}
				listReportForm.submit();
			}
			function exportXls() {
				try{onSubmit()}catch(e){}
				var a = listReportForm.action;
				listReportForm.action = a + '?view=xls';
				listReportForm.submit();
				listReportForm.action = a;
			}
			function exportCsv() {
				try{onSubmit()}catch(e){}
				var a = listReportForm.action;
				listReportForm.action = a + '?view=csv';
				listReportForm.submit();
				listReportForm.action = a;
			}
			function selectColumn() {
				var v = window.showModalDialog("${ctx}/tree/baseTree.do?method=columnTree&title=" + encodeURI("选择列") + "&checkbox=2&reportName=${ec.reportName}&values=" + listReportForm.colIds.value + "&rand=" + Math.random(), "", "dialogHeight:420px;dialogWidth:420px;scroll=no;help:no;status:no");
				if (v) {
					listReportForm.colIds.value = v.id;
				}
			}
			function disabledInit(){
				try{
					var obj=document.getElementsByName("deleteTime3");
					if(obj[0]=='undefined'){
						
					}else{
						if(obj[0].value=='null'){
							listReportForm.state.disabled=true;
						}
					}
				}catch(e){					
				}
			}
			function setColumn() {
				window.showModalDialog("${ctx}/commons/iframe.jsp?target=/base/baseMessRes.do?method=edit%26reportName=${ec.reportName}%26func=${ec.func}&rand=" + Math.random(), "", "dialogHeight:480px;dialogWidth:600px;scroll=no;help:no;status:no");
			}
		</script>
	</head>
	<body onload="WindowSollAuto();disabledInit();" onresize="WindowSollAuto()">
		<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/report/listReport.do?tableId=${param.tableId}&title=${param.title}" method="post">
		<html:hidden property="colIds"/>
		<div class="bosom_one">
			<div class="bosom_top">
				<span class="left"></span>
				<h4>
					<!--TitleStrat-->
					${(ec.title == null || ec.title == '') ? configurator.title : ec.title}
					<!--TitleStrat-->
					
					
				</h4>
				<div class='MenuList'>
					<!--MenuStrat-->
					<a href="javascript:showQuery_('query_A');" id="showQuery_A" >${mr['page.common.button.setquery']}</a>
					<a href="javascript:hideQuery_('query_A');" id="hideQuery_A" >${mr['page.common.button.close']}</a>
					<a href="javascript:listReportForm.ec_p.value=1;query()" id="query_A">${mr['page.common.button.query']}</a>
					<c:if test="${configurator.showSort==true}">
						<a href="javascript:setColumn();">${mr['page.common.button.list']}</a>
					</c:if>
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
								  	<jsp:include page="/WEB-INF/pages/report/${configurator.reportName}.jsp"/>
								</table>
							</div>
							<ec:table items="list" var="item" onInvokeAction="query()" form="listReportForm"
							        retrieveRowsCallback="limit" sortRowsCallback="limit" style="width:100%" tableId="ec"
								    action="listReport.do">
								<ec:exportXls fileName="${configurator.title}.xls" tooltip="${mr['page.common.exportExcel']}" view="xls" /><ec:exportCsv fileName="${configurator.title}.csv" tooltip="${mr['page.common.exportCVS']}" view="csv" />
								<ec:row highlightRow="true">
									<ec:columns autoGenerateColumns="com.winchannel.core.web.ReportColumnGenerator"/> 
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
