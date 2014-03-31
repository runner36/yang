<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
<head>
	<title></title>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/widgets/extremecomponents/extremecomponents.css">
	<script language="javaScript" src="${ctx}/scripts/base.js"></script>
	<script>
	function query() {
		resetec();
		var form = document.baseResourceForm;
		form.submit();
	}
	function add() {
		if ('' == '${ec.id}') {
			alert('${mr['base.mess.selectResClass']}');
			return;
		}
	    location='baseResource.do?method=add';
	} 
	</script>
</head>
<body>
	<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
	<html:form action="/base/baseResource.do?method=list" method="post">
	<div class="sheet_div">
	<div class="searchbox" id="searchbox">
	<table>
	  	<tr>
		    <td width="1%">${mr['page.common.ResourcesName']}</td>
		    <td><html:text name="ec" property="$lk_resName" onchange="baseResourceForm.ec_p.value=1;query()"/></td>
	  	</tr>
	</table>
	</div>	
	<ec:table items="list" var="item" onInvokeAction="query()" form="baseResourceForm"
	        retrieveRowsCallback="limit" style="width:100%" tableId="ec"
		    action="baseResource.do?method=list">
		<ec:exportXls fileName="resources.xls" tooltip="${mr['page.common.exportExcel']}" view="xls" />
		<ec:row highlightRow="true">
		
			<ec:column property="resName" title="${mr['page.common.ResourcesName']}"/>
			<ec:column property="resCode" title="${mr['page.common.ResourcesCode']}"/>
			<ec:column property="resUri" title="${mr['page.common.Resources']}"/>
			<ec:column property="state" title="${mr['base.state']}" value="${item.state == '1' ? mr['base.valid'] : mr['base.invalid']}"/>
			<ec:column property="sort" title="${mr['base.sort']}"/>

			<ec:column property="null" width="1%" title="${mr['page.common.button.oper']}" sortable="false" viewsDenied="xls">
				<center>
					<html:link page="/base/baseResource.do?method=edit&resId=${item.resId}">
						<img src="${ctx}/images/edit.gif" border="0" alt="${mr['page.common.button.edit']}" title="${mr['page.common.button.edit']}"/>
					</html:link>&nbsp;
					<html:link page="/base/baseResource.do?method=delete&resId=${item.resId}" onclick="return confirm('${mr['page.common.mess.delete']}')">
						<img src="${ctx}/images/delete.gif" border="0" alt="${mr['page.common.button.delete']}" title="${mr['page.common.button.delete']}"/>
					</html:link>
				</center>
			</ec:column>
		</ec:row>
	</ec:table>
	</div>
	</html:form>
	<%-- <div class="view_but"><a href="javascript:add();">${mr['page.common.button.add']}</a></div> --%>
</body>
</html>