<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
	<script>
		function selectDist() {
			var form = document.listReportForm;
			var v = openDistTree('${ctx}', '', '1', form.distId.value);
			if (v) {
				form.distId.value = v.id;
				form.distName.value = v.text;
			}
		}
		function selectClient() {
			var form = document.listReportForm;
			var v = openClientTree('${ctx}', '1');
			if (v) {
				form.clientId.value = v.id;
				form.clientName.value = v.text;
			}
		}
		function selectOrg() {
			var form = document.listReportForm;
			var v = openOrgTree('${ctx}');
			if (v) {
				form.orgSubCode.value = v.subCode;
				form.orgName.value = v.text;
			}
		}
	</script>
	<tr>
		<td width="1%">${mr['page.common.orgName']}</td>
		<td width="1%">
			<html:hidden property="orgSubCode"/>
			<html:text property="orgName" styleClass="select_but" onclick="selectOrg()" readonly="true"/>
		</td>
		<td width="1%">
			${mr['page.common.distName']}
		</td>
		<td width="1%">
			<html:hidden property="distId"/>
			<html:text property="distName" styleClass="select_but" onclick="selectDist()" readonly="true"/>
		</td>
		<td width="1%">
			${mr['page.common.distCode']}
		</td>
		<td width="1%">
			<html:text name="ec" property="distCode"/>
		</td>
		<td width="1%">
			${mr['page.common.billDate']}
		</td>
		<td>
			<html:text name="ec" property="billDate1" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>-<html:text name="ec" property="billDate2" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>
		</td>
	</tr>
	<tr>
		<td width="1%">
			${mr['page.common.clientName']}
		</td>
		<td width="1%">
			<html:hidden property="clientId"/>
			<html:text property="clientName" styleClass="select_but" onclick="selectClient()" readonly="true"/>
		</td>
		<td width="1%">
			${mr['page.common.clientCode']}
		</td>
		<td width="1%">
			<html:text name="ec" property="clientCode"/>
		</td>
		<td width="1%">
			${mr['page.common.billNumber']}
		</td>
		<td width="1%">
			<html:text name="ec" property="billCode"/>
		</td>
		<td width="1%">
			${mr['page.common.updatedTime']}
		</td>
		<td colspan="2">
			<html:text name="ec" property="updateTime1" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>-<html:text name="ec" property="updateTime2" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>
		</td>
	</tr>
	<tr>
		<td width="1%">
			${mr['page.common.distProdName']}
		</td>
		<td width="1%">
			<html:text name="ec" property="prodName"/>
		</td>
		<td width="1%">
			${mr['page.common.distProdCode']}
		</td>
		<td width="1%">
			<html:text name="ec" property="prodCode"/>
		</td>
		<td width="1%">
			${mr['page.common.productBarcode']}
		</td>
		<td width="1%">
			<html:text name="ec" property="prodBarcode"/>
		</td>
		<td width="1%">
			${mr['page.common.returntime']}
		</td>
		<td colspan="2">
			<html:text name="ec" property="loadTime1" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>-<html:text name="ec" property="loadTime2" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>
		</td>
	</tr>
	
	<tr>
		<td width="1%">
			${mr['page.common.pordStandardName']}
		</td>
		<td width="1%">
			<html:text name="ec" property="prodNameCode"/>
		</td>
		<td width="1%">
			${mr['page.common.pordStandardCode']}
		</td>
		<td width="1%">
			<html:text name="ec" property="targetProdCode"/>
		</td>
		<td width="1%">
			
		</td>
		<td width="1%">
			
		</td>
		<td width="1%">
			
		</td>
		<td>
			
		</td>
	</tr>
