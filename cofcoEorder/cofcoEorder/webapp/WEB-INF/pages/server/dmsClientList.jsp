<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>${mr['page.customize.title.clientManage']}</title>
		<link rel="stylesheet" type="text/css"
			href="${ctx}/styles/sidecss.css">
		<link rel="stylesheet" type="text/css"
			href="${ctx}/widgets/extremecomponents/extremecomponents.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script type="text/javascript" src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
		<script>
	    function query() {
			resetec();
			var form = document.dmsClientForm;
			form.submit();
		}
		function add() {
		    location='dmsClient.do?method=add';
		}
		</script>
	</head>
	<body onload="WindowSollAuto()">
		<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/server/dmsClient.do?method=list" method="post">
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						${mr['page.customize.title.clientManage']}
					</h4>
					<div class='MenuList'>
						<!--MenuStrat-->
						<a href="javascript:showQuery_('query_A');" id="showQuery_A" >${mr['page.common.button.setquery']}</a>
						<a href="javascript:hideQuery_('query_A');" id="hideQuery_A" >${mr['page.common.button.close']}</a>
						<a href="javascript:dmsClientForm.ec_p.value=1;query()" id="query_A">${mr['page.common.button.query']}</a>
						<a href="dmsClient.do?method=add">${mr['page.common.button.add']}</a>
						<a href="javascript:location.reload();">${mr['page.common.button.refresh']}</a>
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
											${mr['page.common.clientCode']}
										</td>
										<td width="1%">
											<html:text name="ec" property="$eq_clientCode"/>
										</td>
										<td width="1%">
											${mr['page.common.clientName']}
										</td>
										<td width="1%">
											<html:text name="ec" property="$lk_clientName"/>
										</td>
										<td width="1%">
											${mr['page.common.softwareName']}
										</td>
										<td width="1%">
											<html:text name="ec" property="$lk_dmsSoftware_softName"/>
										</td>
										<td width="1%">
											${mr['page.common.distName']}
										</td>
										<td>
											<html:text name="ec" property="$lk_mdmDistributor_distName"/>
										</td>
									</tr>
								</table>
								</div>
							
								<ec:table items="list" var="item" onInvokeAction="query()"
									form="dmsClientForm" retrieveRowsCallback="limit"
									style="width:100%" tableId="ec"
									action="dmsClient.do?method=list">
									<ec:exportXls fileName="${mr['page.customize.title.clientManage']}.xls" tooltip="${mr['page.common.exportExcel']}" view="xls" />
									<ec:row highlightRow="true">
										<ec:column property="clientCode" title="${mr['page.common.clientCode']}"/>
										<ec:column property="clientName" title="${mr['page.common.clientName']}"/>
										<ec:column property="currVersion" title="${mr['page.common.currentVersion']}"/>
										<ec:column property="dmsSoftware.softName" title="${mr['page.common.softwareName']}" alias="softName"/>
										<ec:column property="mdmDistributor.distName" title="${mr['page.common.distName']}" alias="distName"><html:link page="/server/dmsClient.do?method=edit&clientId=${item.clientId}" style="font-size:12px;">${item.mdmDistributor.distName}</html:link></ec:column>
										<ec:column property="extractTime" title="${mr['page.common.extractTime']}"/>
										<ec:column property="dataDays" title="${mr['page.common.dataCycle']}"/>
										<ec:column property="dbDriver" title="DB_DRIVER"/>
										<ec:column property="dbUrl" title="DB_URL"/>
										
										<ec:column property="params" title="${mr['page.common.Parameter']}"/>
										<ec:column property="remark" title="${mr['page.common.memo']}" viewsDenied="html"/>
										<ec:column property="created" title="${mr['page.common.createdTime']}"/>
										<ec:column property="isreturnData" title="${mr['page.common.isReturn']}"  value="${item.isreturnData==1 ? '是':'否'}"/>
										
										<ec:column property="null" width="1%" title="${mr['page.common.button.oper']}" sortable="false" viewsDenied="xls">
											<center>
												<html:link page="/server/dmsClient.do?method=edit&clientId=${item.clientId}">
													<img src="${ctx}/images/edit.gif" border="0" alt="${mr['page.common.button.edit']}" title="${mr['page.common.button.edit']}" />
												</html:link>
												&nbsp;
												<html:link page="/server/dmsClient.do?method=delete&clientId=${item.clientId}" onclick="return confirm('${mr['page.common.mess.delete']}')">
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
