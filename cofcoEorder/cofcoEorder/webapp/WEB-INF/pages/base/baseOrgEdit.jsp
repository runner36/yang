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
		location = 'baseOrg.do?method=add&parentId=' + parent.tree.getId();
	}
	function save() {
		if (validateBaseOrgForm(baseOrgForm)) {
			baseOrgForm.submit();
		}
		
	}
	function del() {
		if (baseOrgForm.orgId.value == '') {
			alert('${mr['base.mess.selectOrg']}');
			return;
		}
		if (confirm('${mr['page.common.mess.delete']}')) {
			location = 'baseOrg.do?method=delete&orgId=' + baseOrgForm.orgId.value;
		}
	}
	if ('save' == '${param.method}' || 'delete' == '${param.method}') {
		parent.tree.location = '${ctx}/tree/baseTree.do?method=orgTree&action=${ctx}/base/baseOrg.do?method=edit&target=content';
	}
	function selectOrg() {
		var form = baseOrgForm;
		var v = openOrgTree('${ctx}','','','');
		if (v) {
			form.parentId.value = v.id;
			form.parentName.value = v.text;
		}
	}
	function selectEmp() {
		var form = baseOrgForm;
		var v = openEmpList('${ctx}', '0');
		if (v) {
			form.empId.value = v.id;
			form.empName.value = v.text;
		}
	}
	function selectGeo() {
		var form = baseOrgForm;
		var v = openDictTree('${ctx}','geography',2,2,form.geoIds.value);
		if (v) {
			form.geoIds.value = v.id;
			form.geoNames.value = v.text;
		}
	}
	</script>
</head>
<body>
	<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
	<html:form action="/base/baseOrg.do?method=save" method="post">
	<html:hidden property="orgId"/>
	<table class="list_add">
	  <tr>
	    <td class="formTable">${mr['base.org.parentName']}</td>
	    <td align="left"><html:hidden property="parentId"/><html:text property="parentName" styleClass="select_but" onclick="selectOrg()" readonly="true"/></td>
	    <td class="formTable">${mr['base.org.orgType']}</td>
	    <td align="left">
	    	<html:select property="orgTypeId">
	    		<option value="">${mr['page.common.button.pleaseSelect']}</option>
				<html:options collection="orgTypes" labelProperty="itemName" property="dictItemId"/>
			</html:select>
		</td>
	  </tr>
	  <tr>
	    <td class="formTable">${mr['base.org.orgCode']}</td>
	    <td align="left"><html:text property="orgCode" maxlength="80"/></td>
	    <td class="formTable">${mr['base.org.extCode']}</td>
	    <td align="left"><html:text property="extCode" maxlength="30"/></td>
	  </tr>
	  <tr>
	    <td class="formTable">${mr['base.org.orgName']}<font color="#FF0000">＊</font></td>
	    <td align="left"><html:text property="orgName" maxlength="80"/></td>
	    <td class="formTable">${mr['base.org.orgAlias']}<font color="#FF0000">＊</font></td>
	    <td align="left"><html:text property="orgAlias" maxlength="80"/></td>
	  </tr>
	  <tr>
	    <td class="formTable">${mr['base.org.orgNameEn']}</td>
	    <td align="left"><html:text property="orgNameEn" maxlength="30"/></td>
	    <td class="formTable">${mr['base.org.principal']}</td>
	    <td align="left"><html:hidden property="empId"/><html:text property="empName" styleClass="select_but" onclick="selectEmp()" readonly="true"/></td>
	  </tr>
	  <tr>
	    <td class="formTable">${mr['base.org.phone']}</td>
	    <td align="left"><html:text property="phone" maxlength="30"/></td>
	    <td class="formTable">${mr['base.org.fax']}</td>
	    <td align="left"><html:text property="fax" maxlength="30"/></td>
	  </tr>
	  <tr>
	    <td class="formTable">${mr['base.org.orgAddr']}</td>
	    <td align="left"><html:text property="orgAddr" maxlength="60"/></td>
	    <td class="formTable">${mr['base.org.postCode']}</td>
	    <td align="left"><html:text property="postCode" maxlength="20"/></td>
	  </tr>
	  <tr>
	    <td class="formTable">${mr['base.org.email']}</td>
	    <td align="left"><html:text property="email" maxlength="30"/></td>
	    <td class="formTable">${mr['base.org.website']}</td>
	    <td align="left"><html:text property="website" maxlength="30"/></td>
	  </tr>
	  <tr>
	    <td class="formTable">${mr['base.org.memo1']}</td>
	    <td align="left"><html:text property="memo1" maxlength="20"/></td>
	    <td class="formTable">${mr['base.org.memo2']}</td>
	    <td align="left"><html:text property="memo2" maxlength="20"/></td>
	  </tr>
	  <tr>
	    <td class="formTable">${mr['base.org.memo3']}</td>
	    <td align="left" colspan="3"><html:text property="memo3" maxlength="20"/></td>
	  </tr>
	  <tr>
	    <td class="formTable">${mr['base.state']}</td>
	    <td align="left"><html:radio property="state" styleClass="Choose_input" value="1"/>${mr['base.valid']}　<html:radio property="state" styleClass="Choose_input" value="0"/>${mr['base.invalid']}</td>
	    <td class="formTable">${mr['base.sort']}</td>
	    <td align="left"><html:text property="sort" maxlength="20"/></td>
	  </tr>
	  <tr>
	    <td class="formTable">${mr['base.remark']}</td>
	    <td align="left" colspan="3"><html:textarea property="remark" rows="3" cols="80"></html:textarea></td>
	  </tr>
	  <tr>
	    <td class="formTable">${mr['base.org.geoNames']}</td>
	    <td align="left" colspan="3"><html:hidden property="geoIds"/><html:textarea property="geoNames" rows="3" cols="80" onclick="selectGeo()" readonly="true"></html:textarea></td>
	  </tr>
	  <tr>
	    <td class="formTable">${mr['base.createdBy']}</td>
	    <td align="left"><html:text property="createdBy" disabled="true"/></td>
	    <td class="formTable">${mr['base.created']}</td>
	    <td align="left"><html:text property="created" disabled="true"/></td>
	  </tr>
	  <tr>
	    <td class="formTable">${mr['base.updatedBy']}</td>
	    <td align="left"><html:text property="updatedBy" disabled="true"/></td>
	    <td class="formTable">${mr['base.updated']}</td>
	    <td align="left"><html:text property="updated" disabled="true"/></td>
	  </tr>
	</table>
	</html:form>
	<html:javascript formName="baseOrgForm" staticJavascript="false" dynamicJavascript="true" cdata="false"/>
	<script type="text/javascript" src="${ctx}/scripts/validator.jsp"></script>
</body>
</html>