<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="java.util.Date" %>
<%@page import="java.text.SimpleDateFormat" %>
<%request.setAttribute("date",request.getParameter("queryDate")==null?new SimpleDateFormat("yyyy-MM-dd").format(new Date()):request.getParameter("queryDate")); %>
<%@ include file="/commons/taglibs.jsp"%>
	<script>
		function selectDict(dictName, id, name) {
			var v = openDictTree('${ctx}', dictName, 0, 2);
			if (v) {
				id.value = v.id;
				name.value = v.text;
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
		function selectClient() {
			var form = document.listReportForm;
			var v = openClientTree('${ctx}', '1');
			if (v) {
				form.clientId.value = v.id;
				form.clientName.value = v.text;
			}
		}
		function selectLevelTree() {
			var form = document.listReportForm;
			var v = openLevelTree('${ctx}','0','2','4');
			if (v) {
				form.queryOrgIds.value = v.id;
				form.orgName.value = v.text;
			}
		}
		
		function selectProdTree(){
			var form = document.listReportForm;
			var v = openProdTree('${ctx}','0','0');
			if (v) {
				if(v.leaf=='1'){
					form.prodCode.value = v.prodCode;
					form.targetProdName.value = v.text;
					form.brandId.value = "";
				}else{
					form.brandId.value = v.id;
					form.prodCode.value = "";
					form.targetProdName.value = v.text;
				}
			}
		} 
		showQuery_('query_A');
	</script>
	<tr>
		<td width="1%">${mr['page.common.orgName']}</td>
		<td width="1%">
			<html:hidden property="queryOrgIds"/>
			<html:text property="orgName" styleClass="select_but" onclick="selectLevelTree()" readonly="true"/>
		</td>
		<td width="1%">${mr['page.common.geographicArea']}</td>
		<td width="1%">
			<html:hidden property="queryGeoIds"/><html:text property="geoName" readonly="true" styleClass="select_but" onclick="selectDict('geography',form.queryGeoIds,form.geoName)"/>
		</td>
		<td width="1%">
			产品：
		</td>
		<td width="1%">
			<html:hidden property="brandId"/>
			<html:hidden property="prodCode"/>
			<html:text property="targetProdName" styleClass="select_but" onclick="selectProdTree()" readonly="true"/>
		</td>
		<td width="1%">
			日期：
		</td>
		<td>
			<html:text name="ec" property="queryDate" value="${date}" onfocus="setday(this)" readonly="true" styleClass="date_but"/>
		</td>
	</tr>
