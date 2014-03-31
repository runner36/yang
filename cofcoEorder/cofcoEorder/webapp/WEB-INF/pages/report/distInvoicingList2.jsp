<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script src="${ctx}/scripts/mdm.js"></script>
<script src="${ctx}/scripts/jquery-1.2.6.min.js"></script>
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
	
	if($('#checkproxy').attr('checked')){
		$('input[name="force"]').val('1');
	}else{
		$('input[name="force"]').val('0');
	}
    return true;
}
	$(document).ready(function(){
		var currDate = new Date();
		var endDate = new Date(currDate.getFullYear(), currDate.getMonth(), 0);
		var startDate = new Date(currDate.getFullYear(), currDate.getMonth()-3, 1);
		startDate = startDate.getFullYear()+'-'+((startDate.getMonth()+1)<10?'0'+(startDate.getMonth()+1):(startDate.getMonth()+1))+'-'+(startDate.getDate()<10?'0'+startDate.getDate():startDate.getDate());
		endDate = endDate.getFullYear()+'-'+((endDate.getMonth()+1)<10?'0'+(endDate.getMonth()+1):(endDate.getMonth()+1))+'-'+(endDate.getDate()<10?'0'+endDate.getDate():endDate.getDate());
		
		
		if($('input[name="beginDate"]').val()==''){
			$('input[name="beginDate"]').val(startDate);
		}
		if($('input[name="endDate"]').val()==''){
			$('input[name="endDate"]').val(endDate);
		}
		if($('input[name="g_gross"]').val()==''){
			$('input[name="g_gross"]').val('-0.05');
		}
		if($('input[name="l_gross"]').val()==''){
			$('input[name="l_gross"]').val('0.5');
		}
		if($('input[name="g_s_days"]').val()==''){
			$('input[name="g_s_days"]').val('7');
		}
		if($('input[name="l_s_days"]').val()==''){
			$('input[name="l_s_days"]').val('90');
		}
		if($('input[name="g_s_p"]').val()==''){
			$('input[name="g_s_p"]').val('0.75');
		}
		if($('input[name="l_s_p"]').val()==''){
			$('input[name="l_s_p"]').val('1.25');
		}
		if($('input[name="g_p_d"]').val()==''){
			$('input[name="g_p_d"]').val('0.75');
		}
		if($('input[name="l_p_d"]').val()==''){
			$('input[name="l_p_d"]').val('1.25');
		}
		
		
		if($('input[name="force"]').val()=='1'){
			$('#checkproxy').attr('checked','true');
		}

		
		$(".odd,.even").each(function(j,m){
			var codeTd;
			var distCode;
			var istartDate;
			var iendDate;
			var instartDate;
			var inendDate;
			$(m).find("td").each(function(i,n){
				switch(i){
					case 0:
						distCode=$(n).text();
						codeTd = $(n);
						break;
					case 18:
						$(n).css('text-decoration','underline').css('cursor','pointer').click(function(){
							window.open('${ctx}/report/listReport.do?reportName=inout_condition&first=1&ec_crd=1000&dist_code='+distCode+'&ship_date='+istartDate+'&purchase_date='+iendDate,'');
						});
						break;
					case 12:
						$(n).css('text-decoration','underline').css('cursor','pointer').click(function(){
							window.open('${ctx}/report/listReport.do?reportName=balanceList&first=1&ec_crd=1000&distCode='+distCode+'&beginDate='+istartDate+'&endDate='+iendDate,'');
						});
						break;
					case 2:
						istartDate=$(n).text();
						instartDate=istartDate;
						break;
					case 3:
						iendDate=$(n).text();
						inendDate=iendDate;
						codeTd.css('text-decoration','underline').css('cursor','pointer').click(function(){
							window.open('${ctx}/report/listReport.do?reportName=prodMappingStatus_v2&first=1&ec_crd=1000&distCode='+distCode+'&start_date='+instartDate+'&end_date='+inendDate+'&is_map=%20AND mappingCreateTime IS NOT NULL%20','');
						});
						break;
					case 5:
						var stat = $(n).text();
						$(n).text('');
						if(stat=='正常'){
							$(n).append('<img src="${ctx}/images/alarm_0.jpg" />');
						}else if(stat=='异常'){
							$(n).append('<img src="${ctx}/images/alarm_5.jpg" />');
						}
						break;
					case 24:
						$(n).click(function(){
							if($('input',$(this)).size()==0){
								var text = $(this).text();
								$(this).text('');
								var remarkInput = $('<input type="text" value="'+text+'" />');
								var thisDom = $(this);
								
								remarkInput.keydown(function(event){
									if(13==event.keyCode){
										var text = $(this).val();
										$(this).remove();
										updateRemark(distCode,text);
										thisDom.text(text);
									}
								});
								$(this).append(remarkInput);
								remarkInput.focus();
							}
						});
						break;
				}
			});
		});
	});
	
	function updateRemark(code, remark){
		$.ajax({
			   type: 'POST',
			   url: '${ctx}/mdm/mdmDistributor.do?method=updateRemark',
			   data: 'code='+code+'&remark='+remark
			}); 
	}
</script>
<style>
.tableBody td{
	text-align:right;
}
</style>
<link rel="stylesheet" type="text/css" href="${ctx}/styles/thickbox.css">

<tr>
	<td width="1%" style="text-align:right">经销商：</td>
	<td width="1%">
	  <html:hidden property="distId"/>
	  <html:text property="distName" styleClass="select_but" onclick="selectDist()" readonly="true"/>
	</td>
	<td width="1%" style="text-align:right">期初日期：</td>
	<td width="1%"><html:text property="beginDate" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/></td>
	<td width="1%" style="text-align:right">期末日期：</td>
	<td width="1%"><html:text property="endDate" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/></td>
	<td width="1%" style="text-align:right">固定日期</td>
	<td>
		<input type="checkbox" id="checkproxy" value="1" style="border:none;width:18px;position:relative;left:-7px" />
		<html:hidden property="force" />
	</td>
</tr>
<tr>
	<td width="1%" style="text-align:right">正常毛利范围：</td>
	<td width="1%"><html:text property="g_gross" style="width:48px;text-align:right" />-<html:text property="l_gross" style="width:48px;text-align:right" /></td>
	<td width="1%" style="text-align:right">库存天数范围：</td>
	<td width="1%"><html:text property="g_s_days" style="width:48px;text-align:right" />-<html:text property="l_s_days" style="width:48px;text-align:right" /></td>
	<td width="1%" style="text-align:right">销售/到货范围：</td>
	<td width="1%"><html:text property="g_s_p" style="width:48px;text-align:right" />-<html:text property="l_s_p" style="width:48px;text-align:right" /></td>
	<td width="1%" style="text-align:right">到货/发货范围：</td>
	<td><html:text property="g_p_d" style="width:48px;text-align:right" />-<html:text property="l_p_d" style="width:48px;text-align:right" /></td>
</tr>