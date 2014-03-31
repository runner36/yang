<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>${mr['page.customize.title.employeeManage']}</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script type="text/javascript" src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script>
		function save() {
			if (validateBaseEmployeeForm(baseEmployeeForm)) {
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
		function selectEmp() {
			var form = baseEmployeeForm;
			var v = openEmpTree('${ctx}');
			if (v) {
				form.targetId.value = v.id;
				form.targetName.value = v.text;
			}
		}
		function selectGeo() {
			var form = baseEmployeeForm;
			var v = openDictTree('${ctx}','geography',0,0,form.geoId.value);
			if (v) {
				form.geoId.value = v.id;
				form.geoName.value = v.text;
			}
		}
		</script>
	</head>
	<body onload="WindowSollAuto()">
	<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
	<html:form action="/base/baseEmployee.do?method=save1" method="post">
	<html:hidden property="empId"/>
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						${mr['page.customize.title.employeeManage']}
					</h4>
					<div class='MenuList'>
						<a href="javascript:save();">${mr['page.common.button.save']}</a>
						<a href="baseEmployee.do?method=list">${mr['page.common.button.cancel']}</a>
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
								<table class="list_add">
	  <tr>
	    <td class="formTable">${mr['base.emp.empCode']}<font color="#FF0000">＊</font></td>
	    <td align="left"><html:text property="empCode" maxlength="20"/></td>
	    <td class="formTable">${mr['base.emp.extCode']}</td>
	    <td align="left"><html:text property="extCode" maxlength="20"/></td>
	  </tr>
	  <tr>
	    <td class="formTable">${mr['base.emp.empName']}<font color="#FF0000">＊</font></td>
	    <td align="left"><html:text property="empName" maxlength="20"/></td>
	    <td class="formTable" style="display:none">${mr['base.emp.empNameEn']}</td>
	    <td align="left" style="display:none"><html:text property="empNameEn" maxlength="20"/></td>
	    
		<td class="formTable">${mr['base.emp.isEmployee']}</td>
	    <td align="left"><html:radio property="isEmployee" styleClass="Choose_input" value="1"/>${mr['page.common.yes']}　<html:radio property="isEmployee" styleClass="Choose_input" value="0"/>${mr['page.common.no']}</td>
	  </tr>
	  <tr>
	  	<td class="formTable">${mr['base.emp.orgName']}<font color="#FF0000">＊</font></td>
	    <td align="left"><html:hidden property="orgId"/><html:text property="orgName" maxlength="20" styleClass="select_but" onclick="selectOrg()" readonly="true"/></td>	  	
	  	<td class="formTable">${mr['page.common.geographicArea']}<font color="#FF0000">＊</font></td>
	    <td align="left"><html:hidden property="geoId"/><html:text property="geoName" maxlength="20" styleClass="select_but" onclick="selectGeo()" readonly="true"/></td>
	  	
	  </tr>
	  <tr>
	    <td class="formTable">${mr['base.emp.sex']}</td>
	    <td align="left"><html:radio property="sex" styleClass="Choose_input" value="男"/>男　<html:radio property="sex" styleClass="Choose_input" value="女"/>女</td>
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
	    <td class="formTable">${mr['base.emp.duty']}<font color="#FF0000">＊</font></td>
	    <td align="left">
	    	<html:select property="dutyId">
	    		<html:option value=""></html:option>
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
	    <td class="formTable">${mr['base.emp.mobilePhone']}<font color="#FF0000">＊</font></td>
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
		<html:javascript formName="baseEmployeeForm" staticJavascript="false" dynamicJavascript="true" cdata="false" />
		<script type="text/javascript" src="${ctx}/scripts/validator.jsp"></script>
	</body>
</html>
