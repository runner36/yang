<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript">
	//组织
	/**
	function selectOrg() {
	  	var v = openLevelTree('${ctx}', 2, 2, 4, listReportForm.orgId.value);
	  	if (v) {
			listReportForm.orgId.value = v.id;
			listReportForm.orgName.value = v.text;
		}
	}
	*/
	function selectOrg() {
		var form = document.listReportForm;
	  	var v = openOrgTree('${ctx}', 2, 2, form.orgId.value);
	  	if (v) {
	  		form.orgId.value = v.id;
			form.orgName.value = v.text;
		}
	}
	//渠道
	function selectDict(dictName, id, name) {
		var v = openDictTree('${ctx}', dictName, 2, 2, id.value);
		if (v) {
			id.value = v.id;
			name.value = v.text;
		}
	}
	//性质
	 function selectStoreNature(dictName, id, name){
		 var v = openDictTree('${ctx}', dictName, 2, 0, id.value);
			if (v) {
				id.value = v.id;
				name.value = v.text;
			}
	 }

showQuery_('query_A');
</script>

<tr>
	<td width="1%">
		组织名称：
	</td>
	<td width="1%">
	   <html:hidden name="ec" property="orgId"/>
	   <html:text name="ec" property="orgName" readonly="true" styleClass="select_but" onclick="selectOrg()"/>
	</td>
	<td width="1%">
		${mr['page.common.storeProperty']}
	</td>
	<td width="1%">
	   <html:hidden name="ec" property="natureId"/>
	   <html:text name="ec" property="natureName" readonly="true" styleClass="select_but" onclick="selectStoreNature('storeNature',listReportForm.natureId,listReportForm.natureName)"/>
	</td>
	<td width="1%">
		渠&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;道：
	</td>
	<td width="1%">
	   <html:hidden name="ec" property="channelId"/>
	   <html:text name="ec" property="channelName" readonly="true" styleClass="select_but" onclick="selectDict('storeChannel',listReportForm.channelId,listReportForm.channelName)"/>
	</td>
	<td width="1%">
		门店状态：
	</td>
	<td>
		<html:select property="storeState">
		  <html:option value="">  </html:option>
		  <html:option value="1">活动</html:option>
		  <html:option value="0">非活动</html:option>
		</html:select>
	</td>
</tr>