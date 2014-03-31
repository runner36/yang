<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>${mr['base.user.title']}</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/widgets/extremecomponents/extremecomponents.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script>
			function query() {
				resetec();
				var form = document.baseUserForm;
				form.submit();
			}
			function resetPass(userId) {
				if (confirm('${mr['base.mess.resetPass']}')) {
					location = 'baseUser.do?method=resetPass&userId=' + userId;
				}
			}
		</script>
	</head>
	<body onload="WindowSollAuto()" onresize="WindowSollAuto()">
		<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/base/baseUser.do?method=list" method="post">
		<div class="bosom_one">
			<div class="bosom_top">
				<span class="left"></span>
				<h4>
					<!--TitleStrat-->
					${mr['base.user.title']}
					<!--TitleStrat-->
				</h4>
				<div class='MenuList'>
					<!--MenuStrat-->
					<a href="javascript:showQuery_('query_A');" id="showQuery_A"  >${mr['page.common.button.setquery']}</a>
					 <a href="javascript:hideQuery_('query_A');" id="showQuery_A"  >${mr['page.common.button.close']}</a>
					<a href="javascript:baseUserForm.ec_p.value=1;query()" id="query_A">${mr['page.common.button.query']}</a>
					<a href="baseUser.do?method=add">${mr['page.common.button.add']}</a>
					<a href="javascript:openImportForm('${ctx}', 'BaseUser');">${mr['page.common.button.import']}</a>
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
								    <td width="1%">${mr['base.user.userAccount']}</td>
								    <td width="1%"><html:text name="ec" property="$lk_userAccount"/></td>
								    <td width="1%">${mr['base.emp.empName']}</td>
								    <td width="1%"><html:text name="ec" property="$lk_baseEmployee_empName"/></td>
								    <td width="1%">所属人员职务</td>
									<td >
										<html:select property="$eq_baseEmployee_baseDictItem_dictItemId" >
											<html:option value=""></html:option>
											<html:options collection="dutys" labelProperty="itemName" property="dictItemId"/>
										</html:select> 
									</td>
								    
								    <%-- <td width="1%">${mr['base.user.userType']}</td>
								    <td>
										<html:select name="ec" property="$eq_userType" style="width:84px">
											<html:option value=""></html:option>
											<html:option value="1">系统用户</html:option>
											<html:option value="2">手机用户</html:option>
										</html:select>
								    </td> --%>
							  	</tr>
							</table>
						</div>
							<ec:table items="list" var="item" onInvokeAction="query()" form="baseUserForm"
							        retrieveRowsCallback="limit" style="width:100%" tableId="ec"
								    action="baseUser.do?method=list">
								<ec:exportXls fileName="users.xls" tooltip="${mr['page.common.exportExcel']}" view="xls" />
								<ec:row highlightRow="true">
									<ec:column property="null" width="1%" title="${mr['page.common.button.oper']}" sortable="false" viewsDenied="xls">
										<center>
											<html:link page="/base/baseUser.do?method=edit&userId=${item.userId}">
												<img src="${ctx}/images/edit.gif" border="0" alt="${mr['page.common.button.edit']}" title="${mr['page.common.button.edit']}" />
											</html:link>&nbsp;
											<html:link page="/base/baseUser.do?method=delete&userId=${item.userId}" onclick="return confirm('${mr['page.common.mess.delete']}')">
												<img src="${ctx}/images/delete.gif" border="0" alt="${mr['page.common.button.delete']}" title="${mr['page.common.button.delete']}" />
											</html:link>
											<a href="javascript:resetPass(${item.userId});"><img src="${ctx}/images/pwd.gif" border="0" alt="${mr['base.user.resetPass']}" title="${mr['base.user.resetPass']}"/></a>
										</center>
									</ec:column>
									<ec:column property="userAccount" title="${mr['base.user.userAccount']}"/>
									<ec:column property="baseEmployee.empName" title="${mr['base.emp.empName']}" alias="empName"/>
									<ec:column property="baseEmployee.baseDictItem.itemName" title="人员职务" alias="empName"/>
									<ec:column property="roleNames" title="所属角色"/>
									<ec:column property="userType" title="${mr['base.user.userType']}" value="${item.userType == '1' ? '系统用户' : '手机用户'}"/>
									<ec:column property="state" title="${mr['base.state']}" value="${item.state == '1' ? mr['base.valid'] : mr['base.invalid']}"/>
									<ec:column property="sort" title="${mr['base.sort']}"/>
						
									
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
