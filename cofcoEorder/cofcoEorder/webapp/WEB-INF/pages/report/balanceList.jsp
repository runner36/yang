<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>
<script language="javaScript" src="${ctx}/scripts/jquery-1.2.6.min.js"></script>
<script>
function selectOrg(orgName, id, name) {
  var v = openOrgTree('${ctx}', '0','2',id.value);
  if (v) {
	id.value = v.id;
	name.value = v.text;
	}
}
function selectDist() {
  var v = openDistTree('${ctx}','2','1',listReportForm.distId.value);
  if (v) {
	 listReportForm.distId.value = v.id;
	 listReportForm.distName.value = v.text;
	}
}
function onSubmit() {
  return(check(listReportForm.beginDate.value,listReportForm.endDate.value,listReportForm.distId.value)) ;
}
//验证日期（判断结束日期是否大于开始日期）日期格式为YY—MM—DD   
function check(startTime,endTime,distId){               
   if(startTime.length<=0 || endTime.length<=0)
   {
     alert("开始日期或结束日期不能为空"); 
     return false; 
   }
   if(startTime.length>0 && endTime.length>0){ 
       var startTmp=startTime.split("-");
       var endTmp=endTime.split("-");
       var sd=new Date(startTmp[0],startTmp[1],startTmp[2]);
       var ed=new Date(endTmp[0],endTmp[1],endTmp[2]); 
       if(sd.getTime()>ed.getTime()){
          alert("开始日期不能大于结束日期");  
         return false;
       }
    }
  return true;
}

function selectSoft() {
	var v = openSoftTree('${ctx}');
	if (v) {
		listReportForm.softId.value = v.id;
		listReportForm.softName.value = v.text;
	}
}
/*
$(document).ready(function(){
	var window_height = document.documentElement.clientHeight||document.body.offsetHeight;
	var window_width = document.documentElement.clientWidth||document.body.offsetWidth;
	var iframe_height = window_height-100;
	var iframe_width = window_width-100;
	$('tr',$('.tableBody')).each(function(i,n){
		var dist_code = $('td',$(n)).eq(0).text();
		var prod_code = $('td',$(n)).eq(2).text();
		var start_date = $('td',$(n)).eq(15).text().substring(0,10);
		var end_date = $('td',$(n)).eq(16).text().substring(0,10);
		var purchase = $('td',$(n)).eq(7);
		var sale = $('td',$(n)).eq(9);
		var inout = $('td',$(n)).eq(11);


		purchase.html('<a class="thickbox" href="${ctx}/server/dmsDataPurchase.do?method=list&$eq_dmsClient_mdmDistributor_distCode='
				+dist_code
				+'&$lk_prodCode='
				+prod_code
				+'&$ge_updateTime='
				+start_date
				+'&$le_updateTime='
				+end_date
				+'&TB_iframe=true&height='+iframe_height+'&width='+iframe_width+'">'+purchase.text()+'</a>');
		sale.html('<a class="thickbox" href="${ctx}/server/dmsDataSale.do?method=list&$eq_dmsClient_mdmDistributor_distCode='
				+dist_code
				+'&$lk_prodCode='
				+prod_code
				+'&$ge_updateTime='
				+start_date
				+'&$le_updateTime='
				+end_date
				+'&TB_iframe=true&height='+iframe_height+'&width='+iframe_width+'">'+sale.text()+'</a>');
		inout.html('<a class="thickbox" href="${ctx}/server/dmsDataInout.do?method=list&$eq_dmsClient_mdmDistributor_distCode='
				+dist_code
				+'&$lk_prodCode='
				+prod_code
				+'&$ge_updateTime='
				+start_date
				+'&$le_updateTime='
				+end_date
				+'&TB_iframe=true&height='+iframe_height+'&width='+iframe_width+'">'+inout.text()+'</a>');
		});
	
});
*/
</script>
<script language="javaScript" src="${ctx}/scripts/thickbox.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/styles/thickbox.css">
<tr>
	<td width="1%">${mr['page.common.orgName']}</td>
	<td width="1%">
	   <html:hidden property="orgId"/>
	   <html:text property="orgName" readonly="true" styleClass="select_but" onclick="selectOrg('orgTree',listReportForm.orgId,listReportForm.orgName)"/>
	</td>
	<td width="1%">${mr['page.common.distName']}</td>
	<td width="1%">
	  <html:hidden property="distId"/>
	  <html:text property="distName" styleClass="select_but" onclick="selectDist()" readonly="true"/>
	</td>
	<td width="1%">${mr['page.common.softwareName']}</td>
	<td>
		<html:hidden property="softId"/><html:text property="softName" maxlength="20" styleClass="select_but" onclick="selectSoft()" readonly="true"/>
	</td>
	
</tr>
<tr>
	<td width="1%">${mr['page.common.initialDate']}</td>
	<td><html:text property="beginDate" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/></td>
	<td width="1%">${mr['page.common.endDate']}</td>
	<td width="1%"><html:text property="endDate" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/></td>
	<td width="1%">${mr['page.common.distCode']}</td>
	<td><html:text property="distCode" /></td>
</tr>