<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>${mr['page.customize.title.rolManage']}</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/widgets/extremecomponents/extremecomponents.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script>
			function query() {
				resetec();
				var form = document.baseRoleForm;
				form.submit();
			}
		</script>
	</head>
	<body onload="WindowSollAuto()" onresize="WindowSollAuto()">
		<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/base/baseRole.do?method=list" method="post">
		<div class="bosom_one">
			<div class="bosom_top">
				<span class="left"></span>
				<h4>
					<!--TitleStrat-->
					${mr['page.customize.title.rolManage']}
					<!--TitleStrat-->
				</h4>
				<div class='MenuList'>
					<!--MenuStrat-->
					<%--<a href="javascript:baseRoleForm.ec_p.value=1;query()">${mr['page.common.button.query']}</a>--%>
					<a href="baseRole.do?method=add">${mr['page.common.button.add']}</a>
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
							<ec:table items="list" var="item" onInvokeAction="query()" form="baseRoleForm"
							        retrieveRowsCallback="limit" style="width:100%" tableId="ec"
								    action="baseRole.do?method=list">
								<ec:exportXls fileName="role.xls" tooltip="${mr['page.common.exportExcel']}" view="xls" />
								<ec:row highlightRow="true">
								
									<ec:column property="roleName" title="${mr['page.common.roleName']}"/>
									<ec:column property="state" title="${mr['page.common.status']}" value="${item.state == '1' ? '有效' : '无效'}"/>
									<ec:column property="sort" title="${mr['page.common.sort']}"/>
						
									<ec:column property="null" width="1%" title="${mr['page.common.button.oper']}" sortable="false" viewsDenied="xls">
										<center>
											<html:link page="/base/baseRole.do?method=edit&roleId=${item.roleId}">
												<img src="${ctx}/images/edit.gif" border="0" alt="${mr['page.common.button.edit']}" title="${mr['page.common.button.edit']}" />
											</html:link>&nbsp;
											<html:link page="/base/baseRole.do?method=delete&roleId=${item.roleId}" onclick="return confirm('${mr['page.common.mess.delete']}')">
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
