<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>
<script language="javaScript" src="${ctx}/scripts/jquery-1.2.6.min.js"></script>
<script language="javaScript" src="${ctx}/scripts/thickbox.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/styles/thickbox.css">

<script>


	function addDate(type,NumDay,vdate){
         var date=new   Date(Date.parse(vdate.replace(/-/g,   "/")));   
         
        type = parseInt(type) //类型 
         var lIntval = parseInt(NumDay)//间隔
            switch(type){
                 case 6 ://年
                  date.setYear(date.getYear() + lIntval)
                  break;
                 case 7 ://季度
                  date.setMonth(date.getMonth() + (lIntval * 3) )
                  break;
                 case 5 ://月
                  date.setMonth(date.getMonth() + lIntval)
                  break;
                 case 4 ://天
                  date.setDate(date.getDate() + lIntval)
                  break
                 case 3 ://时
                  date.setHours(date.getHours() + lIntval)
                  break
                 case 2 ://分
                  date.setMinutes(date.getMinutes() + lIntval)
                  break
                 case 1 ://秒
                  date.setSeconds(date.getSeconds() + lIntval)
                  break;
                 default:
            
          } 
    return ChangeDateToString(date);
    //return date.getYear() +'-' +  (date.getMonth()+1) + '-' +date.getDate()+ ' '+ date.getHours()+':'+date.getMinutes()+':'+date.getSeconds()
  } 

	function ChangeDateToString(DateIn)
	{
	 var Year=0;
	 var Month=0;
	 var Day=0;
	 var CurrentDate="";
	 //初始化时间
	 Year = DateIn.getYear();
	 Month = DateIn.getMonth()+1;
	 Day = DateIn.getDate();

	 CurrentDate = Year + "-";
	 if (Month >= 10 )
	 {
	 CurrentDate = CurrentDate + Month + "-";
	 }
	 else
	 {
	 CurrentDate = CurrentDate + "0" + Month + "-";
	 }
	 if (Day >= 10 )
	 {
	 CurrentDate = CurrentDate + Day ;
	 }
	 else
	 {
	 CurrentDate = CurrentDate + "0" + Day ;
	 }
	 
	return CurrentDate;
	}

	
	function selectDist() {
	  var v = openDistTree('${ctx}','2','1',listReportForm.distId.value);
	  if (v) {
		 listReportForm.distId.value = v.id;
		 listReportForm.distName.value = v.text;
		}
	}
	
	$(document).ready(function(){
		$('tr',$('.tableBody')).each(function(i,n){
			var p_distId = $('td',$(n)).eq(24).text();
			var p_distName = $('td',$(n)).eq(1).text();
			var p_prodName = $('td',$(n)).eq(5).text();
			var p_billDate1 = $('td',$(n)).eq(6).text().substring(0,10);
			var p_billDate2 = addDate(4,30,p_billDate1);
			
			
			
			$(n).css('cursor','pointer').click(function(){
				window.parent.header.p_distId = p_distId;
				window.parent.header.p_distName = p_distName;
				window.parent.header.p_prodName = p_prodName;
				window.parent.header.p_billDate1 = p_billDate1;
				window.parent.header.p_billDate2 = p_billDate2;
			});

			});
		
	});
	
	
	function onSubmit() {
		if(listReportForm.distName.value=='' && listReportForm.clientId.value == '' && listReportForm.dist_code.value==''){
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
			<td><html:text property="dist_code"/></td>
			<td width="1%">${mr['page.common.distName']}</td>
			<td>
				<html:hidden property="distId"/>
	  			<html:text property="distName" styleClass="select_but" onclick="selectDist()" readonly="true"/></td>
			<td width="1%">${mr['page.common.pordStandardCode']}</td>
			<td><html:text property="target_prod_code"/></td>
			<td width="1%">${mr['page.common.pordStandardName']}</td>
			<td><html:text property="target_prod_name"/></td>
		</tr>
	    <tr>
			<td width="1%">${mr['page.common.distProdCode']}</td>
			<td><html:text property="prod_code"/></td>
			<td width="1%">${mr['page.common.distProdName']}</td>
			<td><html:text property="prod_name"/></td>
			<td width="1%">发货开始日期：</td>
			<td><html:text property="ship_date" onfocus="setday(this)" readonly="true" styleClass="date_but"/></td>
			<td width="1%">发货结束日期：</td>
			<td><html:text property="purchase_date" onfocus="setday(this)" readonly="true" styleClass="date_but"/></td>
		</tr>
		<tr>
			<td width="1%">${mr['page.common.clientName']}</td>
			<td width="1%">
				<html:hidden property="clientId"/>
				<html:text property="clientName" styleClass="select_but" onclick="selectClient()" readonly="true"/>
			</td>
			<td width="1%"></td>
			<td></td>
			<td width="1%"></td>
			<td></td>
			<td width="1%"></td>
			<td></td>
		</tr>
		<input type="hidden" value="Johnson" name="client_code" />

<script>
</script>
