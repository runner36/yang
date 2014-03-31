<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>

<%@ page import="com.winchannel.base.utils.Constants" %>
<%@ page import="com.winchannel.base.model.BaseUser" %>

<%
	String first = String.valueOf(request.getParameter("first"));
	//取到当前登录用户组织
	BaseUser userInfo = (BaseUser)session.getAttribute(Constants.SESSION_USER_KEY);
	String orgSubCode = (null==userInfo.getBaseEmployee().getBaseOrg()) ? "0" : userInfo.getBaseEmployee().getBaseOrg().getSubCode();
%>
<script>
	showQuery_('query_A');
	function selectDist() {
		var form = document.listReportForm;
		var v = openDistOrgTree('${ctx}', '1', '2');
		if (v) {
			form.distId.value = v.id;
			form.distName.value = v.text;
		}
	}
	function onSubmit() {
		var form = document.listReportForm;
		if(form.startdate.value=='' || form.enddate.value=='')
		{
			alert('请选择日期！')
			return false;
		}
		return true;
	}
	function selectSKU() {
		var form = document.listReportForm;
		var v=  window.showModalDialog('${ctx}' + "/tree/tmpSourceData.do?method=skuNameTree&title=" + encodeURI("SKU") + "&state=1&first=1&onlyLeaf=2&checkbox=2&values=" + form.skuId.value + "&rand=" + Math.random(), "", "dialogHeight:420px;dialogWidth:420px;scroll=no;help:no;status:no");
		if (v) {
			form.skuId.value = (v.id).replace("*","'");
			form.skuName.value = (v.text).replace("*","'");
		}
	}
	function selectSimsCity() {
		//var v = openDictTree('${ctx}', 'geography', 2, 2, document.treeForm.simsOrgName.value);
		var v = openOrgTree('${ctx}', 2, 2, document.listReportForm.simsOrgId.value);
		if (v) {
			document.listReportForm.simsOrgId.value = v.id;
			document.listReportForm.simsOrgName.value = v.text;
		}
	}
		
	function selectCity(){
		var v = openOrgTree('${ctx}', 2, 2, document.listReportForm.orgId.value);
		if(v){
			document.listReportForm.orgId.value = v.id;
			document.listReportForm.orgName.value = v.text;
		}
	}
</script>

<tr>
	<td width="1%" align="right">date：</td>
	<td>
		<html:text name="ec" property="startdate" onfocus="setday(this)" readonly="true" styleClass="date_but"/>-<html:text name="ec" property="enddate" onfocus="setday(this)" readonly="true" styleClass="date_but"/>&nbsp;&nbsp;
	</td>
	<td width="1%" align="right">Distributor：</td>
	<td>
		<html:hidden name="ec" property="orgSubCode"/>
		<html:hidden property="distId" />
		<html:text property="distName" styleClass="select_but" onclick="selectDist()" readonly="true"/>&nbsp;&nbsp;
	</td>
	<%--
	<td width="1%">City：</td>
	<td> 
		<html:hidden name="ec" property="orgId" />
		<html:text name="ec" property="orgName" maxlength="20" styleClass="select_but" onclick="selectCity()" readonly="true" />
	</td>
	--%>
	<td width="1%" align="right">City：</td>
	<td> 
		<html:hidden name="ec" property="simsOrgId" />
		<html:text name="ec" property="simsOrgName" styleClass="select_but" onclick="selectSimsCity()" readonly="true" />
	</td>
	<td width="1%" align="right">SKU：</td>
	<td>
		<html:hidden name="ec" property="skuId"/>
		<html:text name="ec" property="skuName" styleClass="select_but" onclick="selectSKU()" readonly="true"/>
	</td>
</tr>
<script>
document.listReportForm.orgSubCode.value = '<%=orgSubCode%>';
</script>

