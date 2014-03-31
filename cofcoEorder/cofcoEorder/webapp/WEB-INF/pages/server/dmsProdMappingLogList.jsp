<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<%@ include file="/commons/meta.jsp" %>
		<title>${mr['page.customize.title.prodMapMaint']}</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/widgets/extremecomponents/extremecomponents.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/dms.js"></script>
		<script language="javaScript" src="${ctx}/scripts/jquery-1.2.6.min.js"></script>
		<script type="text/javascript" src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
		<script>
	    function query() {
			resetec();
			var form = document.dmsProdMappingLogForm;
			loading();
			form.submit();
		}
		function selectClient() {
			var form = document.dmsProdMappingLogForm;
			var v = openClientTree('${ctx}', '1','1',form.clientId_.value);
			if (v) {
				form.clientId_.value = v.id;
				form.clientName_.value = v.text;
			}
		}
		</script>
	</head>
	<body onload="WindowSollAuto()" onresize="WindowSollAuto()"><jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
	
		<html:form action="/server/dmsProdMappingLog.do?method=list" method="post">
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						<!--TitleStrat-->
						${mr['page.customize.title.prodMapMaint']}
						<!--TitleStrat-->
					</h4>
					<div class='MenuList'>
						<!--MenuStrat-->
						<a href="javascript:showQuery_('query_A');" id="showQuery_A" >${mr['page.common.button.setquery']}</a>
						<a href="javascript:hideQuery_('query_A');" id="hideQuery_A" >${mr['page.common.button.close']}</a>
						<a href="javascript:dmsProdMappingLogForm.ec_p.value=1;query()" id="query_A">${mr['page.common.button.query']}</a>
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
								</table>
								</div>
								<ec:table items="list" var="item" onInvokeAction="query()"
									form="dmsProdMappingLogForm" retrieveRowsCallback="limit"
									style="width:100%" tableId="ec" sortRowsCallback="limit"
									action="dmsProdMappingLog.do?method=list">
									<ec:exportXls fileName="${mr['page.customize.title.prodMapMaint']}.xls" tooltip="${mr['page.common.exportExcel']}" view="xls" />
									<ec:exportCsv fileName="${mr['page.customize.title.prodMapMaint']}.csv" tooltip="${mr['page.common.exportCVS']}" view="csv" />
										<ec:row highlightRow="true" >
										<ec:column property="dmsClient.clientCode" title="${mr['page.common.clientCode']}" alias="clientCode"></ec:column>
										<ec:column property="distProdCode" escapeAutoFormat="true" title="${mr['page.common.distProdCode']}"/>
										<ec:column property="distProdName" title="${mr['page.common.distProdName']}"/>
										<ec:column property="distProdUnit" title="${mr['page.common.distProdUnit']}"/>
										<ec:column property="targetProdCode" escapeAutoFormat="true" title="${mr['page.common.pordStandardCode']}"/>
										<ec:column property="targetUnit.itemName"  title="${mr['page.common.pordStandardUnit']} " alias="unitName">
										</ec:column>
										<ec:column property="created" title="${mr['page.common.mappingDate']}" cell="date" format="yyyy-MM-dd"/>
										<ec:column property="updated" title="${mr['page.common.lastChangeDate']}" cell="date" format="yyyy-MM-dd"/>
										<ec:column property="updatedBy" title="${mr['page.common.updatedBy']}"  />
										<ec:column property="deleteTime" title="${mr['page.common.deleteTime']}" cell="date" format="yyyy-MM-dd hh:mm:ss"/>
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
