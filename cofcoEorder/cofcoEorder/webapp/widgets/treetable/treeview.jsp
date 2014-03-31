<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<%@ include file="/commons/meta.jsp" %>
		<title></title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>
		<script language="javaScript" src="${ctx}/scripts/dms.js"></script>
		<script type="text/javascript" src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/yearMonth.js"></script>
		<script>
			function query() {
				try{if (!onSubmit()) return;}catch(e){}
				loading();
				treeForm.submit();
			}
			function exportXls() {
				try{onSubmit()}catch(e){}
				var a = treeForm.action;
				treeForm.action = a + '?view=xls';
				treeForm.submit();
				treeForm.action = a;
			}
			function exportCsv() {
				try{onSubmit()}catch(e){}
				var a = treeForm.action;
				treeForm.action = a + '?view=csv';
				treeForm.submit();
				treeForm.action = a;
			}
			function exportDetail() {
				try{onSubmit()}catch(e){}
				var a = treeForm.action;
				treeForm.action = a + '?view=xls&detail=1';
				treeForm.submit();
				treeForm.action = a;
			}
		</script>
	</head>
	<body onload="WindowSollAuto()" onresize="WindowSollAuto()">
		<div class="bosom_one">
			<div class="bosom_top">
				<span class="left"></span>
				<h4>
					<!--TitleStrat-->
					${configurator.title}
					<!--TitleStrat-->
				</h4>
				<div class='MenuList'>
					<!--MenuStrat-->
					<a href="javascript:showQuery_('query_A');" id="showQuery_A" >${mr['page.common.button.setquery']}</a>
					<a href="javascript:hideQuery_('query_A');" id="hideQuery_A" >${mr['page.common.button.close']}</a>
					<a href="javascript:query()" id="query_A">${mr['page.common.button.query']}</a>
					<a href="javascript:exportXls();">EXCEL</a>
					<a href="javascript:exportCsv();">CSV</a>
					<a href="javascript:exportDetail();">${mr['page.common.button.detail']}</a>
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
							<form name="treeForm" action="${ctx}/report/treeReport.do" method="post">
							  	<jsp:include page="/WEB-INF/pages/report/${configurator.reportName}.jsp"/>
							</form>	
							</table>
							</div>
							<jsp:include page="/widgets/treetable/treetable.jsp"/>
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
	</body>
</html>
