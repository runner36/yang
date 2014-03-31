<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

	<script>
		function selectOrg() {
			var form = document.listReportForm;
			var v = openOrgTree('${ctx}');
			if (v) {
				form.orgSubCode.value = v.subCode;
				form.orgName.value = v.text;
			}
		}
		function selectDist() {
			var form = document.listReportForm;
			var v = openDistTree('${ctx}', '', '1', form.distId.value);
			if (v) {
				form.distId.value = v.id;
				form.distName.value = v.text;
			}
		}
		function onSubmit() {
			var form = document.listReportForm;
			
			return true;
		}
	</script>
	<tr>
		<td width="1%">组织名称：</td>
		<td width="1%">
			<html:hidden name="ec" property="orgSubCode"/>
			<html:text name="ec" property="orgName" styleClass="select_but" onclick="selectOrg()" readonly="true"/>
		</td>
		<td width="1%">
			经销商名称：
		</td>
		<td width="1%">
			<html:hidden name="ec" property="distId"/>
			<html:text name="ec" property="distName" styleClass="select_but" onclick="selectDist()" readonly="true"/>
		</td>
		<td width="1%">
			产品名称：
		</td>
		<td width="1%">
			<html:text name="ec" property="prodName"/>
		</td>
		<td width="1%">
			产品编码：
		</td>
		<td >
			<html:text name="ec" property="prodCode"/>
		</td>
		
	</tr>
	
	
