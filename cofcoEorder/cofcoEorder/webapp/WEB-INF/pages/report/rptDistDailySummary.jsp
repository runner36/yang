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
		function changeChangeState(){
			var form=document.listReportForm;
			if(form.deleteTime3.value=="not null"){
				//form.state.disabled=false;
			}else{
				form.state.selectedIndex=2;
				//form.state.disabled=true;
			}			
		}	

	</script>
	<tr>
		<td width="1%">
			${mr['page.common.distName']}
		</td>
		<td width="1%">
			<html:hidden name="ec" property="distId"/>
			<html:text name="ec" property="distName" styleClass="select_but" onclick="selectDist()" readonly="true"/>
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
		<td width="1%">
			${mr['page.common.updatedTime']}
		</td>
		<td>
			<html:text name="ec" property="updateTime1" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>-<html:text name="ec" property="updateTime2" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>
		</td>
		<td></td><td></td>
	</tr>
	
