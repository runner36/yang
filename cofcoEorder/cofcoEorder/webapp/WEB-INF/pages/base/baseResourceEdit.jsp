<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
<head>
	<title></title>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
	<script language="javaScript" src="${ctx}/scripts/base.js"></script>
	<script>
	function save() {
		if (validateBaseResourceForm(baseResourceForm)) {
			baseResourceForm.submit();
		}
	}
	function selectResCate() {
		var v = openDictTree('${ctx}', 'resource');
		if (v) {
			baseResourceForm.dictItemId.value = v.id;
			baseResourceForm.dictItemName.value = v.text;
		}
	}
	function cancel() {
		location = 'baseResource.do?method=list';
	}
	</script>
</head>
<body>
	<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
	<html:form action="/base/baseResource.do?method=save" method="post">
	<html:hidden property="resId"/>
	<input type="hidden" name="dictItemId" value="${ec.id}"/>
	<table border="0" cellpadding="2" cellspacing="0" class="list_add">
	  <tr>
	    <td class="formTable">${mr['page.common.ResourceClassification']}</td>
	    <td align="left" colspan="3"><html:text property="dictItemName" maxlength="20" styleClass="select_but" onclick="selectResCate()" readonly="true"/></td>
	  </tr>
	  <tr>
	    <td class="formTable">${mr['page.common.ResourcesCode']}<font color="#FF0000">＊</font></td>
	    <td align="left"><html:text property="resCode" maxlength="25"/></td>
	    <td class="formTable">${mr['page.common.ResourcesName']}<font color="#FF0000">＊</font></td>
	    <td align="left"><html:text property="resName" maxlength="25"/></td>
	  </tr>
	  <tr>
	    <td class="formTable">${mr['page.common.Resources']}</td>
	    <td align="left" colspan="3"><html:text property="resUri" maxlength="80" style="width:616px"/></td>
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
	</table>
	<div class="view_but"><a href="javascript:save();">${mr['page.common.button.save']}</a><a href="javascript:cancel();">${mr['page.common.button.cancel']}</a></div>
	</html:form>
	<html:javascript formName="baseResourceForm" staticJavascript="false" dynamicJavascript="true" cdata="false"/>
	<script type="text/javascript" src="${ctx}/scripts/validator.jsp"></script>
</body>
</html>