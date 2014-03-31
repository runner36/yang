<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>
<script language="javaScript" src="${ctx}/scripts/jquery-1.2.6.min.js"></script>
<script>
function selectDist() {
  var v = openDistTree('${ctx}','2','1',listReportForm.distId.value);
  if (v) {
	 listReportForm.distId.value = v.id;
	 listReportForm.distName.value = v.text;
	}
}
function onSubmit() {
    var form = document.listReportForm;
    if(form.beginDate.value==''){
        alert("请选择期初日期！");
        return false;
    }
    if(form.endDate.value==''){
        alert("请选择期末日期！");
        return false;
    }
    if(form.endDate.value<form.beginDate.value){
       alert("期初日期不能大于期末日期！");
       return false;
    }
    return true;
}
</script>
<script language="javaScript" src="${ctx}/scripts/thickbox.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/styles/thickbox.css">
<tr>
	<td width="1%">经销商：</td>
	<td width="1%">
	  <html:hidden name="ec" property="distId"/>
	  <html:text name="ec" property="distName" styleClass="select_but" onclick="selectDist()" readonly="true"/>
	</td>
	<td width="1%">${mr['page.common.initialDate']}</td>
	<td width="1%"><html:text name="ec" property="beginDate" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/></td>
	<td width="1%">${mr['page.common.endDate']}</td>
	<td width="1%"><html:text name="ec" property="endDate" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/></td>
	
	<td width="1%">${mr['page.common.distProdCode']}</td>
	<td ><html:text name="ec" property="prodCode"  />
	</td>
</tr>
