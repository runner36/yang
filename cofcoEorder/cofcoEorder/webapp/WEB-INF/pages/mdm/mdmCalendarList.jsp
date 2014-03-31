<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<%@ include file="/commons/meta.jsp" %>
		<title>${mr['page.customize.title.businessCalendarMaint']}</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/widgets/extremecomponents/extremecomponents.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/dms.js"></script>
		<script>
	    function query() {
			resetec();
			var form = document.mdmCalendarForm;
			loading();
			form.submit();
		}
		function add() {
		    location='mdmCalendar.do?method=add';
		}
		</script>
	</head>
	<body onload="WindowSollAuto()" onresize="WindowSollAuto()"><jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/mdm/mdmCalendar.do?method=list" method="post">
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						<!--TitleStrat-->
						${mr['page.customize.title.businessCalendarMaint']}
						<!--TitleStrat-->
					</h4>
					<div class='MenuList'>
						<!--MenuStrat-->
						<a href="javascript:showQuery_('query_A');" id="showQuery_A"
							>${mr['page.common.button.setquery']}</a>
						<a href="javascript:hideQuery_('query_A');" id="hideQuery_A"
							>${mr['page.common.button.close']}</a>
						<a href="javascript:query()" id="query_A">${mr['page.common.button.query']}</a>
						<a href="mdmCalendar.do?method=add">${mr['page.common.button.add']}</a>
						<a href="mdmCalendar.do?method=importProd">${mr['page.common.button.import']}</a>
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
											${mr['page.common.year']}
										</td>
										<td width="1%">
											<html:text name="ec" property="$lk_calYear"/>
										</td>
										
										<td width="1%">
											${mr['page.common.season']}
										</td>
										<td width="1%">
											<html:text name="ec" property="$lk_calQuarter"/>
										</td>
										<td width="1%">
											${mr['page.common.month']}
										</td>
										<td width="1%">
											<html:text name="ec" property="$lk_calMonth"/>
										</td>
										<td width="1%">
											${mr['page.common.week']}
										</td>
										<td width="1%">
											<html:text name="ec" property="$lk_calWeek"/>
										</td>
										<td width="1%">
											${mr['page.common.weekMonth']}
										</td>
										<td width="1%">
											<html:text name="ec" property="$lk_calWeekOfMonth"/>
										</td>
										<td width="1%">
											${mr['page.common.day']}
										</td>
										<td>
											<html:text name="ec" property="$lk_calDay"/>
										</td>
										</tr>
										
								</table>
							</div>
								<ec:table items="list" var="item" onInvokeAction="query()"
									form="mdmCalendarForm" retrieveRowsCallback="limit"
									style="width:100%" tableId="ec"
									action="mdmCalendar.do?method=list">
									<ec:exportXls fileName="${mr['page.customize.title.businessCalendarMaint']}.xls" tooltip="${mr['page.common.exportExcel']}" view="xls" />
									<ec:exportCsv fileName="${mr['page.customize.title.businessCalendarMaint']}.csv" tooltip="${mr['page.common.exportCVS']}" view="csv" />
									<ec:row highlightRow="true">
										<ec:column property="calYear" title="${mr['page.common.year']}"/>
										<ec:column property="calQuarter" title="${mr['page.common.season']}"/>
										<ec:column property="calMonth" title="${mr['page.common.month']}"/>
										<ec:column property="calWeek" title="${mr['page.common.week']}"/>
										<ec:column property="calWeekOfMonth" title="${mr['page.common.weekMonth']}"/>
										<ec:column property="calDay" title="${mr['page.common.day']}"/>
										<ec:column property="calDate" title="${mr['page.common.date']}" cell="date" format="yyyy-MM-dd "/>
										<ec:column property="null" width="1%" title="${mr['page.common.button.oper']}" sortable="false" viewsDenied="xls,csv">
											<center>
												<html:link page="/mdm/mdmCalendar.do?method=edit&calId=${item.calId}">
													<img src="${ctx}/images/edit.gif" border="0" alt="${mr['page.common.button.edit']}" title="${mr['page.common.button.edit']}" />
												</html:link>
												&nbsp;
												<html:link page="/mdm/mdmCalendar.do?method=delete&calId=${item.calId}" onclick="return confirm('${mr['page.common.mess.delete']}')">
													<img src="${ctx}/images/delete.gif" border="0" alt="${mr['page.common.button.delete']}" title="${mr['page.common.button.delete']}" />
												</html:link>
											</center>
										</ec:column>
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
