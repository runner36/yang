<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ page import="com.winchannel.base.utils.Constants" %>
<%@ page import="com.winchannel.base.model.BaseUser" %>

<%
	String first = String.valueOf(request.getParameter("first"));
	//取到当前登录用户组织
	BaseUser userInfo = (BaseUser)session.getAttribute(Constants.SESSION_USER_KEY);
	String orgSubCode = (null==userInfo.getBaseEmployee().getBaseOrg()) ? "0" : userInfo.getBaseEmployee().getBaseOrg().getSubCode();
%>

<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>


<script>
	showQuery_('query_A');
	
	function selectCity(){
		var v = openOrgTree('${ctx}', 2, 2, document.listReportForm.orgId.value);
		if(v){
			document.listReportForm.orgId.value = v.id;
			document.listReportForm.orgName.value = v.text;
		}
	}
	function selectDist() {
		var form = document.listReportForm;
		var v = openDistOrgTree('${ctx}', '1', '2');
		if (v) {
			form.distId.value = v.id;
			form.distName.value = v.text;
		}
	}
	function selectChannel() {
		var form = document.listReportForm;
		var v = openDictTree('${ctx}', 'storeType', 2, 2, form.channelId.value);
		if (v) {
			form.channelId.value = v.id;
			form.channelName.value = v.text;
		}
	}
	function selectBrand() {
		var v = openDictTree('${ctx}', 'prodBrand', 2, 2, document.listReportForm.brandId.value);
		if (v) {
			document.listReportForm.brandId.value = v.id;
			document.listReportForm.brandName.value = v.text;
		}
	}
	function selectBanner() {
		var form = document.listReportForm;
		var v = openDictTree('${ctx}', 'storeCorp', 2, 2, form.bannerId.value);
		if (v) {
			form.bannerId.value = v.id;
			form.banner.value = v.text;
		}
	}
	function selectType() {
		var form = document.listReportForm;
		var v = openDictTree('${ctx}', 'storeNature', 2, 2, form.typeId.value);
		if (v) {
			form.typeId.value = v.id;
			form.typeName.value = v.text;
		}
	}
	function onSubmit() {
		var form = document.listReportForm;
		if (form.startDate.value=="" || form.endDate.value=="") {
			alert('请选择查询日期');
			return false;
		}
		return true;
	}
	
	//SimsCity
	function selectSimsCity() {
		var v = openOrgTree('${ctx}', 2, 2, document.listReportForm.simsOrgId.value);
		if (v) {
			document.listReportForm.simsOrgId.value = v.id;
			document.listReportForm.simsOrgName.value = v.text;
		}
	}
</script>
<tr>
	<td width="1%" align="right"><font color="#FF0000">＊</font>Start Date：</td>
	<td><html:text name="ec" property="startDate" maxlength="20" styleClass="date_but" onfocus="WdatePicker()" readonly="true"/></td>
	<td width="1%" align="right"><font color="#FF0000">＊</font>End Date：</td>
	<td><html:text name="ec" property="endDate" maxlength="20" styleClass="date_but" onfocus="WdatePicker()" readonly="true"/></td>
	<td width="1%" align="right">Distributor：</td>
	<td>
		<html:hidden name="ec" property="orgSubCode"/>
		<html:hidden name="ec" property="distId"/>
		<html:text name="ec" property="distName" styleClass="select_but" onclick="selectDist()" readonly="true"/>
	</td>
	<td width="1%" align="right">City：<!-- 原Store City --></td>
	<td> 
		<html:hidden name="ec" property="simsOrgId" />
		<html:text name="ec" property="simsOrgName" maxlength="20" styleClass="select_but" onclick="selectSimsCity()" readonly="true" />
	</td>
</tr>
<tr>
	<td width="1%" align="right">Channel：</td>
	<td> 
		<html:hidden name="ec" property="channelId"/>
		<html:text name="ec" property="channelName" styleClass="select_but" onclick="selectChannel()" readonly="true"/>	
	</td>	
	<td width="1%" align="right">Type：</td>
	<td> 
		<html:hidden name="ec" property="typeId"/>
		<html:text name="ec" property="typeName" styleClass="select_but" onclick="selectType()" readonly="true"/>	
	</td>
	<td width="1%" align="right">Banner：</td>
	<td> 
		<html:hidden name="ec" property="bannerId"/>
		<html:text name="ec" property="banner" styleClass="select_but" onclick="selectBanner()" readonly="true"/>	
	</td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
</tr>
<tr>
	<td width="1%" align="right">Customer Code：</td>
	<td> 
		<html:text name="ec" property="storeCode" />	
	</td>
	<td width="1%" align="right">Customer Name：</td>
	<td> 
		<html:text name="ec" property="storeName" />	
	</td>
	<td width="1%" align="right">Brand：</td>
	<td> 
		<html:hidden name="ec" property="brandId" />
		<html:text name="ec" property="brandName" styleClass="select_but" onclick="selectBrand()" readonly="true"/>	
	</td>
	<td width="1%" align="right">SKU：</td>
	<td> 
		<html:text name="ec" property="prodCode" />	
	</td>	
</tr>
<script>
document.listReportForm.orgSubCode.value = '<%=orgSubCode%>';

//onLoad();
/**
function onLoad(){
	var now = new Date();
	var nowYear = now.getYear();
	var form = document.listReportForm;
	if(form.Year.value == ''){
		form.Year.value = nowYear;
	}	
}
*/
</script>

