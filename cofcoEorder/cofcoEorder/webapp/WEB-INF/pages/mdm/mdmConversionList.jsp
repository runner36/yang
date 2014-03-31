<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<%@ include file="/commons/meta.jsp" %>
		<title>${mr['page.customize.title.unitRelationConv']}</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/widgets/extremecomponents/extremecomponents.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/dms.js"></script>
		<script>
	    function query() {
			resetec();
			var form = document.mdmConversionForm;
			loading();
			form.submit();
		}
		function add() {
		}
		</script>
	</head>
	<body onload="WindowSollAuto()" onresize="WindowSollAuto()"><jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/mdm/mdmConversion.do?method=convList" method="post">
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						<!--TitleStrat-->
						${mr['page.customize.title.unitRelationConv']}
						<!--TitleStrat-->
					</h4>
					<div class='MenuList'>
						<!--MenuStrat-->
						<a href="javascript:showQuery_('query_A');" id="showQuery_A"
							>${mr['page.common.button.setquery']}</a>
						<a href="javascript:hideQuery_('query_A');" id="hideQuery_A"
							>${mr['page.common.button.close']}</a>
						<a href="javascript:query()" id="query_A">${mr['page.common.button.query']}</a>
						<a href="${ctx}/mdm/mdmConversion.do?method=importConv">${mr['page.common.button.import']}</a>
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
										<td width="1%">
											${mr['page.common.mastProdCode']}
										</td>
										<td>
											<html:text name="ec" property="prodCode_"/>
										</td>
										</tr>
								</table>
							</div>
								<ec:table items="list" var="item" onInvokeAction="query()"
									form="mdmConversionForm" retrieveRowsCallback="limit"
									style="width:100%" tableId="ec" sortRowsCallback="limit"
									action="mdmConversion.do?method=convList">
									<ec:exportXls fileName="${mr['page.common.unitRelationShipConvert']}.xls" tooltip="${mr['page.common.exportExcel']}" view="xls" />
									<ec:exportCsv fileName="${mr['page.common.unitRelationShipConvert']}.csv" tooltip="${mr['page.common.exportCVS']}" view="csv" />
									<ec:row highlightRow="true">
										<ec:column property="0" escapeAutoFormat="true" title="${mr['page.common.mastProdCode']}"/>
										<ec:column property="1" title="${mr['page.common.mastProdName']}" />
										<ec:column property="2" title="${mr['page.common.value']}1"/>
										<ec:column property="3" title="${mr['page.common.unit']}1" />
										<ec:column property="4" title="${mr['page.common.value']}2"/>
										<ec:column property="5" title="${mr['page.common.unit']}2" />
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
