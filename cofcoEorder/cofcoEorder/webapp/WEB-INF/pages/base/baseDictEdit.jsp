<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
<head>
	<title></title>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
	<script language="javaScript" src="${ctx}/scripts/base.js"></script>
	<script>
	function add() {
		location = 'baseDict.do?method=add&parentId=' + parent.tree.getId() + '&dictId=' + baseDictForm.dictId.value;
	}
	function save() {
		if (validateBaseDictForm(baseDictForm)) {
			baseDictForm.submit();
		}
		
	}
	function del() {
		if (baseDictForm.dictItemId.value == '') {
			alert('请选择一个字典项');
			return;
		}
		if (confirm('${mr['page.common.mess.delete']}')) {
			location = 'baseDict.do?method=delete&dictItemId=' + baseDictForm.dictItemId.value + '&dictId=' + baseDictForm.dictId.value;
		}
	}
	function selectDict() {
		var form = baseDictForm;
		var v = openDictTree('${ctx}', form.dictId.value);
		if (v) {
			form.parentId.value = v.id;
			form.parentName.value = v.text;
		}
	}
	if ('save' == '${param.method}' || 'delete' == '${param.method}') {
		parent.tree.location.reload();
	}
	</script>
</head>
<body>
	<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
	<html:form action="/base/baseDict.do?method=save" method="post">
	<html:hidden property="dictId"/>
	<html:hidden property="dictItemId"/>
	<table border="0" cellpadding="2" cellspacing="0" class="list_add">
	  <tr>
	    <td class="formTable">${mr['page.common.parentItem']}</td>
	    <td align="left"><html:hidden property="parentId"/><html:text property="parentName" styleClass="select_but" onclick="selectDict()" readonly="true"/></td>
	    <td class="formTable"></td>
	    <td align="left"></td>
	  </tr>
	  <tr>
	    <td class="formTable">${mr['page.common.itemCode']}</td>
	    <td align="left"><html:text property="itemCode" maxlength="20"/></td>
	    <td class="formTable">${mr['page.common.externalCode']}</td>
	    <td align="left"><html:text property="extCode" maxlength="20"/></td>
	  </tr>
	  <tr>
	    <td class="formTable">${mr['page.common.itemName']}<font color="#FF0000">＊</font></td>
	    <td align="left"><html:text property="itemName" maxlength="50"/></td>
	    <td class="formTable">${mr['page.common.itemAlias']}</td>
	    <td align="left"><html:text property="itemAlias" maxlength="50"/></td>
	  </tr>
	  <tr>
	    <td class="formTable">${mr['page.common.memo']}1</td>
	    <td align="left"><html:text property="memo1" maxlength="50"/></td>
	    <td class="formTable">${mr['page.common.memo']}2</td>
	    <td align="left"><html:text property="memo2" maxlength="50"/></td>
	  </tr>
	  <tr>
	    <td class="formTable">${mr['page.common.memo']}3</td>
	    <td align="left"><html:text property="memo3" maxlength="50"/></td>
	    <td class="formTable">${mr['page.common.memo']}4</td>
	    <td align="left"><html:text property="memo4" maxlength="50"/></td>
	  </tr>
	  <tr>
	    <td class="formTable">${mr['page.common.valid']}</td>
	    <td align="left">${mr['page.common.yes']}<html:radio property="state" styleClass="Choose_input" value="1"/>${mr['page.common.no']}<html:radio property="state" styleClass="Choose_input" value="0"/></td>
	    <td class="formTable">${mr['page.common.sort']}</td>
	    <td align="left"><html:text property="sort" maxlength="20"/></td>
	  </tr>
	  <tr>
	    <td class="formTable">${mr['page.common.memo']}</td>
	    <td align="left" colspan="3"><html:textarea property="remark" rows="3" cols="80"></html:textarea></td>
	  </tr>
	  <tr>
	    <td class="formTable">${mr['page.common.createdBy']}</td>
	    <td align="left"><html:text property="createdBy" disabled="true"/></td>
	    <td class="formTable">${mr['page.common.createdTime']}</td>
	    <td align="left"><html:text property="created" disabled="true"/></td>
	  </tr>
	  <tr>
	    <td class="formTable">${mr['page.common.updatedBy']}</td>
	    <td align="left"><html:text property="updatedBy" disabled="true"/></td>
	    <td class="formTable">${mr['page.common.updatedTime']}</td>
	    <td align="left"><html:text property="updated" disabled="true"/></td>
	  </tr>
	</table>
	</html:form>
	<html:javascript formName="baseDictForm" staticJavascript="false" dynamicJavascript="true" cdata="false"/>
	<script type="text/javascript" src="${ctx}/scripts/validator.jsp"></script>
</body>
</html>