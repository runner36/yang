<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>${mr['page.customize.title.softMaintenance']}</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/widgets/extremecomponents/extremecomponents.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script>
	    function query() {
			resetec();
			var form = document.dmsSoftwareForm;
			form.submit();
		}
		function add() {
		    location='dmsSoftware.do?method=add';
		}
		</script>
	</head>
	<body onload="WindowSollAuto()">
		<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/server/dmsSoftware.do?method=list" method="post">
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						<!--TitleStrat-->
						${mr['page.customize.title.softMaintenance']}
						<!--TitleStrat-->
					</h4>
					<div class='MenuList'>
						<!--MenuStrat-->
						<a href="javascript:showQuery_('query_A');" id="showQuery_A" >${mr['page.common.button.setquery']}</a>
						<a href="javascript:hideQuery_('query_A');" id="hideQuery_A" >${mr['page.common.button.close']}</a>
						<a href="javascript:dmsSoftwareForm.ec_p.value=1;query()" id="query_A">${mr['page.common.button.query']}</a>
						<a href="dmsSoftware.do?method=add">${mr['page.common.button.add']}</a>
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
											${mr['page.common.softwareName']}
										</td>
										<td width="1%">
											<html:text name="ec" property="$lk_softName"/>
										</td>
										<td width="1%">
											${mr['page.common.softwareVersion']}
										</td>
										<td width="1%">
											<html:text name="ec" property="$lk_softVersion"/>
										</td>
										<td width="1%">
											${mr['page.common.specialUseMethod']}
										</td>
										<td>
											<html:text name="ec" property="$lk_confInfo"/>
										</td>
									</tr>
								</table>
								</div>
							
								<ec:table items="list" var="item" onInvokeAction="query()"
									form="dmsSoftwareForm" retrieveRowsCallback="limit"
									style="width:100%" tableId="ec"
									action="dmsSoftware.do?method=list">
									<ec:exportXls fileName="${mr['page.customize.title.softMaintenance']}.xls" tooltip="${mr['page.common.exportExcel']}" view="xls" />
									<ec:row highlightRow="true">
										<ec:column property="itemClass.itemName" title="${mr['page.common.softwareCategory']}" alias="itemName"/>
										<ec:column property="softName" title="${mr['page.common.softwareName']}"/>
										<ec:column property="softCode" title="${mr['page.common.softwareCoding']}"/>
										<ec:column property="softVersion" title="${mr['page.common.softwareVersion']}"/>
										<ec:column property="confInfo" title="${mr['page.common.specialUseMethod']}"/>
										<ec:column property="confFile" title="${mr['page.common.profileName']}"/>
										<ec:column property="uploadTime" title="${mr['page.common.lastUploadTime']}" cell="date" format="yyyy-MM-dd HH:mm"/>
										<ec:column property="remark" title="${mr['page.common.memo']}" viewsAllowed="xls" />
										
										<ec:column property="null" width="1%" title="${mr['page.common.button.oper']}" sortable="false" viewsDenied="xls">
											<center>
												<html:link page="/server/dmsSoftware.do?method=edit&softId=${item.softId}">
													<img src="${ctx}/images/edit.gif" border="0" alt="${mr['page.common.button.edit']}" title="${mr['page.common.button.edit']}" />
												</html:link>
												&nbsp;
												<html:link page="/server/dmsSoftware.do?method=delete&softId=${item.softId}"
													onclick="return confirm('${mr['page.common.mess.delete']}')">
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
