<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="java.util.Date" %>
<%@page import="java.text.SimpleDateFormat" %>

<%
request.setAttribute("date1",request.getParameter("stat_date1")==null?new SimpleDateFormat("yyyy-MM").format(new Date()):request.getParameter("stat_date1"));
request.setAttribute("date2",request.getParameter("stat_date2")==null?new SimpleDateFormat("yyyy-MM").format(new Date()):request.getParameter("stat_date2"));
%>
<%@ include file="/commons/taglibs.jsp"%>


<script>
	function selectDist() {
	  var v = openDistTree('${ctx}','2','1',listReportForm.distId.value);
	  if (v) {
		 listReportForm.distId.value = v.id;
		 listReportForm.distName.value = v.text;
		}
	}
	
	function onSubmit() {
		if(listReportForm.distName.value=='' && listReportForm.clientId.value == ''){
			alert('请选择经销商或者客户端！');
			return false;
		}
	  return true;
	}

	function selectClient() {
		var form = document.listReportForm;
		var v = openClientTree('${ctx}', '1');
		if (v) {
			form.clientId.value = v.id;
			form.clientName.value = v.text;
		}
	}

</script>
 	    <tr>
			<td width="1%">${mr['page.common.distCode']}</td>
			<td width="1%"><html:text property="dist_code"/></td>
			<td width="1%">${mr['page.common.distName']}</td>
			<td width="1%">
				<html:hidden property="distId"/>
	  			<html:text property="distName" styleClass="select_but" onclick="selectDist()" readonly="true"/>
	  		</td>
			<td width="1%">${mr['page.common.pordStandardName']}</td>
			<td width="1%"><html:text property="target_prod_name"/></td>
			<td width="1%">${mr['page.common.distProdName']}</td>
			<td width="1%"><html:text property="prod_name"/></td>
			<td width="1%">
				${mr['page.common.date']}
			</td>
			<td>
				<html:text styleId="stat_date1" property="stat_date1" value="${date1}" onfocus="showTable(this)" readonly="true" styleClass="date_but"/>~
				<html:text styleId="stat_date2" property="stat_date2" value="${date2}" onfocus="showTable(this)" readonly="true" styleClass="date_but"/>
			</td>
			<td>&nbsp;</td>
		</tr>
<script>
</script>
