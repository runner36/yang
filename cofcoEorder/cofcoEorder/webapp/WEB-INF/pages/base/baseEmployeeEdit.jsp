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
		location = 'baseEmployee.do?method=add&parentId=' + parent.tree.getId();
	}
	function save() {
		if (validateBaseEmployeeForm(baseEmployeeForm)) {
			if (baseEmployeeForm.oper && baseEmployeeForm.oper.value != '') {
				if (baseEmployeeForm.targetId.value == '') {
					alert('${mr['base.mess.selectPost']}');
					return;
				}
				if (baseEmployeeForm.targetId.value == baseEmployeeForm.empId.value) {
					alert('${mr['base.mess.selectSelf']}');
					return;
				}
			}
			if(baseEmployeeForm.dimission.value!=''&&baseEmployeeForm.employment.value!=''){
				if(baseEmployeeForm.dimission.value<=baseEmployeeForm.employment.value){
					alert('${mr['base.mess.dimissionTime']}');
					return;
				}
			}
			if(baseEmployeeForm.birthday.value!=''&&baseEmployeeForm.dimission.value!=''){
				if(baseEmployeeForm.dimission.value<=baseEmployeeForm.birthday.value){
					alert('${mr['base.mess.birthdayTime2']}');
					return;
				}
			}
			if(baseEmployeeForm.birthday.value!=''&&baseEmployeeForm.employment.value!=''){
				if(baseEmployeeForm.employment.value<=baseEmployeeForm.birthday.value){
					alert('${mr['base.mess.birthdayTime']}');
					return;
				}
			}
			
			baseEmployeeForm.submit();
		}
	}
	function selectOrg() {
		var v = openOrgTree('${ctx}');
		if (v) {
			baseEmployeeForm.orgId.value = v.id;
			baseEmployeeForm.orgName.value = v.text;
		}
	}
	function del() {
		if (baseEmployeeForm.empId.value == '') {
			alert('${mr['base.mess.selectEmp']}');
			return;
		}
		if (confirm('${mr['page.common.mess.delete']}')) {
			location = 'baseEmployee.do?method=delete&empId=' + baseEmployeeForm.empId.value;
		}
	}
	function selectEmp() {
		var form = baseEmployeeForm;
		var v = openEmpTree('${ctx}');
		if (v) {
			form.targetId.value = v.id;
			form.targetName.value = v.text;
		}
	}
	if ('save' == '${param.method}' || 'delete' == '${param.method}') {
		parent.tree.location.reload();
	}
	function selectGeo() {
		var form = baseEmployeeForm;
		var v = openDictTree('${ctx}','geography',0,0,form.geoId.value);
		if (v) {
			form.geoId.value = v.id;
			form.geoName.value = v.text;
		}
	}
	function confirmOrg() {
		//location = 'baseEmployee.do?method=add&parentId=' + parent.tree.getId();
		window.open("${ctx}/base/baseEmployee.do?method=list&first=1&rand=" + Math.random(),"人员所属组织确认","left=260,top=40,height=600,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
	</script>
</head>
<body>
	<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
	<html:form action="/base/baseEmployee.do?method=save" method="post">
	<html:hidden property="empId"/>
	<table border="0" cellpadding="2" cellspacing="0" class="list_add">
	  <c:if test="${baseEmployeeForm.map.empId!=null}">
	  <tr>
	    <td class="formTable">${mr['base.emp.selectPost']}</td>
	    <td align="left"><html:hidden property="targetId"/><html:text property="targetName" styleClass="select_but" onclick="selectEmp()" readonly="true"/></td>
	    <td class="formTable">${mr['base.emp.action']}</td>
	    <td align="left">
			<html:select property="oper">
				<html:option value="">岗位无变化</html:option>
				<html:option value="1">单人汇报给该岗位</html:option>
				<html:option value="2">批量汇报给该岗位</html:option>
				<html:option value="3">单人填补该岗位</html:option>
			</html:select>
		</td>
	  </tr>
	  <tr><td colspan="4"></td></tr>
	  </c:if>
	  <tr>
	    <td class="formTable">${mr['base.emp.parentName']}</td>
	    <td align="left"><html:hidden property="parentId"/><html:text property="parentName" style="background-color: #F0F0F0" readonly="true" ondblclick="baseEmployeeForm.parentId.value='';this.value='';"/></td>
	    <td class="formTable">${mr['base.emp.orgName']}<font color="#FF0000"></font></td>
	    <td align="left"><html:hidden property="orgId"/><html:text property="orgName" maxlength="20" styleClass="select_but" onclick="selectOrg()" readonly="true"/></td>
		<%--<td class="formTable">${mr['base.emp.orgName']}</td>
	    <td align="left"><html:hidden property="orgId"/><html:text property="orgName" maxlength="20" readonly="true"/></td>--%>
	  </tr>
	  <tr>
	    <td class="formTable">${mr['base.emp.empCode']}</td>
	    <td align="left"><html:text property="empCode" maxlength="20"/></td>
	    <td class="formTable">${mr['page.common.geographicArea']}<font color="#FF0000">＊</font></td>
	    <td align="left"><html:hidden property="geoId"/><html:text property="geoName" maxlength="20" styleClass="select_but" onclick="selectGeo()" readonly="true"/></td>
	  </tr>
	  <tr>
	    <td class="formTable">${mr['base.emp.extCode']}</td>
	    <td align="left"><html:text property="extCode" maxlength="20"/></td>
		<td class="formTable">${mr['base.emp.isEmployee']}</td>
	    <td align="left"><html:radio property="isEmployee" styleClass="Choose_input" value="1"/>${mr['page.common.yes']}　<html:radio property="isEmployee" styleClass="Choose_input" value="0"/>${mr['page.common.no']}</td>
	  </tr>
	  <tr>
	    <td class="formTable">${mr['base.emp.empName']}<font color="#FF0000">＊</font></td>
	    <td align="left"><html:text property="empName" maxlength="20"/></td>
	    <td class="formTable">${mr['base.emp.empNameEn']}</td>
	    <td align="left"><html:text property="empNameEn" maxlength="20"/></td>
	  </tr>
	  <tr>
	    <td class="formTable">${mr['base.emp.sex']}</td>
	    <td align="left"><html:radio property="sex" styleClass="Choose_input" value="男"/>${mr['page.common.male']}　<html:radio property="sex" styleClass="Choose_input" value="女"/>${mr['page.common.female']}</td>
	    <td class="formTable">${mr['base.emp.idCard']}</td>
	    <td align="left"><html:text property="idCard" maxlength="20"/></td>
	  </tr>
	  <tr>
	    <td class="formTable">${mr['base.emp.nation']}</td>
	    <td align="left"><html:text property="nation" maxlength="20"/></td>
	    <td class="formTable">${mr['base.emp.birthday']}</td>
	    <td align="left"><html:text property="birthday" maxlength="20" styleClass="date_but" onfocus="WdatePicker()" readonly="true"/></td>
	  </tr>
	  <tr>
	    <td class="formTable">${mr['base.emp.homeplace']}</td>
	    <td align="left"><html:text property="homeplace" maxlength="20"/></td>
	    <td class="formTable">${mr['base.emp.empAddr']}</td>
	    <td align="left"><html:text property="empAddr" maxlength="20"/></td>
	  </tr>
	  <tr>
	    <td class="formTable">${mr['base.emp.educate']}</td>
	    <td align="left"><html:text property="educate" maxlength="20"/></td>
	    <td class="formTable">${mr['base.emp.school']}</td>
	    <td align="left"><html:text property="school" maxlength="20"/></td>
	  </tr>
	  <tr>
	    <td class="formTable">${mr['base.emp.subject']}</td>
	    <td align="left"><html:text property="subject" maxlength="20"/></td>
	    <td class="formTable">${mr['base.emp.duty']}</td>
	    <td align="left">
	    	<html:select property="dutyId">
				<html:options collection="dutys" labelProperty="itemName" property="dictItemId"/>
			</html:select>
		</td>
	  </tr>
	  <tr>
	    <td class="formTable">${mr['base.emp.employment']}</td>
	    <td align="left"><html:text property="employment" maxlength="20" styleClass="date_but" onfocus="WdatePicker()" readonly="true"/></td>
	    <td class="formTable">${mr['base.emp.dimission']}</td>
	    <td align="left"><html:text property="dimission" maxlength="20" styleClass="date_but" onfocus="WdatePicker()" readonly="true"/></td>
	  </tr>
	  <tr>
	    <td class="formTable">${mr['base.emp.officePhone']}</td>
	    <td align="left"><html:text property="officePhone" maxlength="20"/></td>
	    <td class="formTable">${mr['base.emp.mobilePhone']}</td>
	    <td align="left"><html:text property="mobilePhone" maxlength="20"/></td>
	  </tr>
	  <tr>
	    <td class="formTable">${mr['base.emp.email']}</td>
	    <td align="left"><html:text property="email" maxlength="80"/></td>
	    <td class="formTable">${mr['base.emp.empType']}</td>
	    <td align="left">
	    	<html:select property="empTypeId">
	    		<html:option value=""></html:option>
				<html:options collection="empTypes" labelProperty="itemName" property="dictItemId"/>
			</html:select>
	    </td>
	    
	  </tr>
	  <tr>
	    <td class="formTable">${mr['base.emp.memo1']}</td>
	    <td align="left"><html:text property="memo1" maxlength="50"/></td>
	    <td class="formTable">${mr['base.emp.memo2']}</td>
	    <td align="left"><html:text property="memo2" maxlength="50"/></td>
	  </tr>
	  <tr>
	    <td class="formTable">${mr['base.emp.memo3']}</td>
	    <td align="left"><html:text property="memo3" maxlength="50"/></td>
	    <td class="formTable">${mr['base.emp.memo4']}</td>
	    <td align="left"><html:text property="memo4" maxlength="50"/></td>
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
	<html:javascript formName="baseEmployeeForm" staticJavascript="false" dynamicJavascript="true" cdata="false"/>
	<script type="text/javascript" src="${ctx}/scripts/validator.jsp"></script>
</body>
</html>