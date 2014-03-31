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
		
		function selectChannel(channelName, id, name) {
		
		var form = document.listReportForm;
			var v = openDictTree('${ctx}', 'storeChannel', '2','1',form.channelId.value);
			if (v) {
				form.channelId.value = v.id;
				form.channelName.value = v.text;
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
		<td width="1%">经销商：</td>
	    <td width="1%">
	      <html:hidden  name="ec" property="distId" />
	      <html:text  name="ec" property="distName" styleClass="select_but" onclick="selectDist()" readonly="true"/>
	    </td>
		<td width="1%">
		渠道：
		</td>
		<td >
		<html:hidden property="channelId" name="ec"/>
		<html:text property="channelName"  name="ec" styleClass="select_but" onclick="selectChannel()" readonly="true"/>
		</td>
		
	</tr>
	
	
