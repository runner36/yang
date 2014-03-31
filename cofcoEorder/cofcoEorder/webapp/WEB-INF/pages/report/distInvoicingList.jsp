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
$(document).ready(function(){
		var distCode;
		var startDate;
		var endDate;
		$(".odd").each(function(j,m){
			var codeTd;
			$(m).find("td").each(function(i,n){
				if (i == 0) {
					distCode=$(n).text();
					codeTd = $(n);
				}
				if(i==2){
					startDate=$(n).text();
				}
				if(i==3){
					endDate=$(n).text();
				}
			});
			
			codeTd.css('text-decoration','underline').css('cursor','pointer').click(function(){
				var a = "${ctx}/server/dmsDataProduct.do?method=dateSum&distCode=" + distCode+"&startDate="+startDate+"&endDate="+endDate+"&TB_iframe=true&height="+($('body').height()-100)+"&width="+($('body').width()-100);
				var g = false;
				tb_show('产品明细',a,g);
				this.blur();
				return false;
				
			});		
			
			//window.open("${ctx}/server/dmsDataProduct.do?method=dateSum&distCode=" + distCode+"&startDate="+startDate+"&endDate="+endDate, "", "dialogHeight:420px;dialogWidth:520px;scroll=no;help:no;status:no");
		});
		
		$(".even").each(function(j,m){
			var codeTd;
			$(m).find("td").each(function(i,n){
				if (i == 0) {
					distCode=$(n).text();
					codeTd = $(n);
				}
				if(i==2){
					startDate=$(n).text();
				}
				if(i==3){
					endDate=$(n).text();
				}
			});
			
			codeTd.css('text-decoration','underline').css('cursor','pointer').click(function(){
				var a = "${ctx}/server/dmsDataProduct.do?method=dateSum&distCode=" + distCode+"&startDate="+startDate+"&endDate="+endDate+"&TB_iframe=true&height="+($('body').height()-100)+"&width="+($('body').width()-100);
				var g = false;
				tb_show('产品明细',a,g);
				this.blur();
				return false;
				
			});		
			
			//window.open("${ctx}/server/dmsDataProduct.do?method=dateSum&distCode=" + distCode+"&startDate="+startDate+"&endDate="+endDate, "", "dialogHeight:420px;dialogWidth:520px;scroll=no;help:no;status:no");
		});
	});
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
	<td width="1%">期末日期</td>
	<td><html:text name="ec" property="endDate" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/></td>
</tr>
