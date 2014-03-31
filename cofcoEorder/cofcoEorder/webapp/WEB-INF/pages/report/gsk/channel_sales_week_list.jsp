<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ page import="java.util.HashMap"%>

<%
	HashMap mp = (HashMap<String, String>)request.getSession().getAttribute("ec");
	String selectVal = (null==mp.get("Week")) ? "" : mp.get("Week").toString();
%>

<script>
showQuery_('query_A');
	
//组织
/**
function selectOrg() {
  	var v = openLevelTree('${ctx}', 2, 2, 4, listReportForm.orgId.value);
  	if (v) {
		listReportForm.orgId.value = v.id;
		listReportForm.orgName.value = v.text;
	}
}*/
function selectOrg() {
	var form = document.listReportForm;
  	var v = openOrgTree('${ctx}', 2, 2, form.orgId.value);
  	if (v) {
  		form.orgId.value = v.id;
		form.orgName.value = v.text;
	}
}
//地理区域
function selectGeo(dictName, id, name) {
	var v = openDictTree('${ctx}', dictName, 2, 2, id.value);
	if (v) {
		id.value = v.id;
		name.value = v.text;
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
</script>

<tr>
	<td width="1%" align="right">开始年月：</td>
	<td width="1%"><html:text name="ec" styleId="YearMon" property="YearMon" maxlength="20" styleClass="date_but" onclick="showTable(this)" readonly="true"/></td>
	<td width="1%">${mr['page.common.orgName']}</td>
	<td width="1%">
	   <html:hidden name="ec" property="orgId"/>
	   <html:text name="ec" property="orgName" readonly="true" styleClass="select_but" onclick="selectOrg()"/>
	</td>
	<td width="1%">${mr['page.common.geographicArea']}</td>
	<td width="1%">
	   <html:hidden name="ec" property="geoId"/>
	   <html:text name="ec" property="geoName" readonly="true" styleClass="select_but" onclick="selectDict('geography',listReportForm.geoId,listReportForm.geoName)"/>
	</td>
	<td width="1%">渠道：</td>
	<td >
	   <html:hidden name="ec" property="channelId"/>
	   <html:text name="ec" property="channelName" readonly="true" styleClass="select_but" onclick="selectDict('storeChannel',listReportForm.channelId,listReportForm.channelName)"/>
	</td>
</tr>
<script>
//初始化年份 = 当前年份
function onLoad(){
	var now = new Date();
	var nowYear = now.getYear();
	var nowMonth = now.getMonth()+1;
	if(nowMonth < 10){
		nowMonth="0"+nowMonth;
	}
	if(document.listReportForm.YearMon.value == ''){
		document.listReportForm.YearMon.value = nowYear+'-'+nowMonth;
	}
}	
onLoad();
</script>