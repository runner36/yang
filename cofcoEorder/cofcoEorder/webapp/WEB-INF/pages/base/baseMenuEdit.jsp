<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
<head>
	<title></title>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
	<script type="text/javascript" src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="${ctx}/scripts/base.js"></script>
	<script>
	function add() {
		location = 'baseMenu.do?method=add&parentId=' + parent.tree.getId();
	}
	function save() {
		if (validateBaseMenuForm(baseMenuForm)) {
			baseMenuForm.submit();
		}
		
	}
	
	function del() {
		if (baseMenuForm.menuId.value == '') {
			alert('请选择一个菜单');
			return;
		}
		if (confirm('${mr['page.common.mess.delete']}')) {
			location = 'baseMenu.do?method=delete&menuId=' + baseMenuForm.menuId.value;
		}
	}
	
	function selectRes() {
		var v = openResTree('${ctx}', '1');
		if (v) {
			baseMenuForm.resId.value = v.id;
			baseMenuForm.resName.value = v.text;
		}
	}
	function selectMenu() {
		var form = baseMenuForm;
		var v = openMenuTree('${ctx}');
		if (v) {
			form.parentId.value = v.id;
			form.parentName.value = v.text;
		}
	}
	if ('save' == '${param.method}' || 'delete' == '${param.method}') {
		parent.tree.location = '${ctx}/tree/baseTree.do?method=menuTree&action=${ctx}/base/baseMenu.do?method=edit&target=content';
	}
	</script>
</head>
<body>
	<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
	<html:form action="/base/baseMenu.do?method=save" method="post">
	<html:hidden property="menuId"/>
	<table class="list_add">
	  <tr>
	    <td class="formTable">${mr['page.common.parentMenu']}</td>
	    <td align="left"><html:hidden property="parentId"/><html:text property="parentName" styleClass="select_but" onclick="selectMenu()" readonly="true"/></td>
	    <td class="formTable"></td>
	    <td align="left"></td>
	  </tr>
	  <tr>
	    <td class="formTable">${mr['page.common.menuCode']}</td>
	    <td align="left"><html:text property="menuCode" maxlength="20"/></td>
	    <td class="formTable">${mr['page.common.menuName']}<font color="#FF0000">＊</font></td>
	    <td align="left"><html:text property="menuName" maxlength="20"/></td>
	  </tr>
	  <tr>
	    <td class="formTable">${mr['page.common.Resources']}</td>
	    <td align="left" colspan="3"><html:hidden property="resId"/><html:text property="resName" maxlength="20" styleClass="select_but" onclick="selectRes()" readonly="true"/></td>
	    <%--<td class="formTable">${mr['page.common.ResourcesParameters']}</td>
	    <td align="left"><html:text property="resParams" maxlength="80"/></td>--%>
	  </tr>
	  <tr>
	    <td class="formTable">${mr['page.common.valid']}</td>
	    <td align="left">${mr['page.common.yes']}<html:radio property="state" styleClass="Choose_input" value="1"/>${mr['page.common.no']}<html:radio property="state" styleClass="Choose_input" value="0"/></td>
	    <td class="formTable">${mr['page.common.sort']}</td>
	    <td align="left"><html:text property="sort" maxlength="20"/></td>
	  </tr>
	  <tr>
	    <td class="formTable">${mr['page.common.ResourcesParameters']}</td>
	    <td align="left" colspan="3"><html:textarea property="resParams" rows="3" cols="80"></html:textarea></td>
	  </tr>
	  <tr>
	    <td class="formTable">${mr['page.common.memo']}</td>
	    <td align="left" colspan="3"><html:textarea property="remark" rows="3" cols="80"></html:textarea></td>
	  </tr>


	  <logic:present name="list" >
	  <tr>
	    <td align="left" colspan="34">
				 <logic:iterate id="selitem" name="list">
				 	<input type="hidden" name="typeId" value="${selitem.dictItemId}" />${selitem.itemName}
				 	<input type="text" name="val" value="${selitem.remark}" />
				 	<br/>
				 </logic:iterate>
	    </td>
	  </tr>
	  </logic:present>


	</table>
	</html:form>
	<html:javascript formName="baseMenuForm" staticJavascript="false" dynamicJavascript="true" cdata="false"/>
	<script type="text/javascript" src="${ctx}/scripts/validator.jsp"></script>
</body>
</html>