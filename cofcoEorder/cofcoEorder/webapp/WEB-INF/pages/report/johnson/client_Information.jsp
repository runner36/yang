<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>

<script>
		//组织
		function selectOrg() {
			var form = document.listReportForm;
			var v = openOrgTree('${ctx}','2','2',form.orgId.value);
			if (v) {
			if(v.id == ''){
					alert("组织不允许为空");
				selectOrg();
				}
				form.orgId.value = v.id;
				form.orgName.value = v.text;
			}
		}
//经销商
function selectDist() {
  	var v = openDistTree('${ctx}', 2, 2);
  	if (v) {
	 	listReportForm.distId.value = v.id;
	 	listReportForm.distName.value = v.text;
	}
}
//渠道
function selectDict(dictName, id, name) {
	var v = openDictTree('${ctx}', dictName, 2, 2, id.value,'',1);
	if (v) {
		id.value = v.id;
		name.value = v.text;
	}
}
		function onSubmit() {
			if(listReportForm.orgName.value==''){
			        alert("请选择组织！");
			        return false;
			 }
			return true ;
		}
 
</script>

<tr>
<td width="1%"><font color="#FF0000">＊</font>组&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;织：</td>
	<td >
	   <html:hidden name="ec" property="orgId" value="${_orgchildsubcodes}"/>
	   <html:text name="ec" property="orgName" value="${_orgchildnames}" readonly="true" styleClass="select_but" onclick="selectOrg()"/>
	</td>
	<td width="1%" style="text-align: right;">经销商编码：</td>
	<td >
	  <html:text name="ec" property="distCode" />
	</td>
	<td width="1%" style="text-align: right;">经销商名称：</td>
	<td >
	  <html:hidden name="ec" property="distId"/>
	  <html:text name="ec" property="distName" styleClass="select_but" onclick="selectDist()" readonly="true"/>
	</td>
	<td width="1%" style="text-align: right;">渠&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;道：</td>
	<td >
	  <html:hidden property="storeTypeId"/>
	  <html:text property="storeTypeName" readonly="true" styleClass="select_but" onclick="selectDict('storeType', listReportForm.storeTypeId, listReportForm.storeTypeName)"/>
	</td>
	<td width="1%" style="text-align: right;">客户销售分类：</td>
	<td >
	  <html:hidden property="channelId"/>
	  <html:text property="channelName" readonly="true" styleClass="select_but" onclick="selectDict('storeChannel', listReportForm.channelId, listReportForm.channelName)"/>
	</td>
</tr>
<tr>
	<td width="1%" style="text-align: right;">JDES II客户名称：</td>
	<td >
	  <html:text name="ec" property="customerName" />
	</td>
	<td width="1%" style="text-align: right;">JDES II客户代码：</td>
	<td >
	  <html:text name="ec" property="customerCode" />
	</td>
</tr>



