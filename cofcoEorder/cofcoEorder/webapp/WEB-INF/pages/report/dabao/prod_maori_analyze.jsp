<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>

<script>

		//组织
		function selectOrg() {
			var form = document.listReportForm;
			var v = openOrgTree('${ctx}','2','2',form.orgId.value);
			if (v) {
			if(v.id == ''){
					alert("组织不允许为空");
				selectOrg();
				}
				form.orgId.value = v.id;
				form.orgName.value = v.text;
			}
		}
//经销商
function selectDist() {
  	var v = openDistTree('${ctx}', 2, 2);
  	if (v) {
	 	listReportForm.distId.value = v.id;
	 	listReportForm.distName.value = v.text;
	}
}
//渠道
function selectDict(dictName, id, name) {
	var v = openDictTree('${ctx}', dictName, 2, 2, id.value,'',1);
	if (v) {
		id.value = v.id;
		name.value = v.text;
	}
}
//产品
function selectProd(id, name) {
	var v = openProdTree('${ctx}',id.value, '2','2');
	if (v) {
		id.value = v.id;
		name.value = v.text;
	}
}

function onSubmit() {
	if(listReportForm.orgName.value==''){
	        alert("请选择组织！");
	        return false;
	 }
	return(check(listReportForm.startDate.value, listReportForm.endDate.value)) ;
}


//验证日期（判断结束日期是否大于开始日期）日期格式为YY—MM—DD   
function check(startTime, endTime){               
	if(startTime.length <= 0 || endTime.length <= 0){
		alert("开始时间或结束时间不能为空"); 
		return false; 
   	}
   	if(startTime.length>0 && endTime.length>0){ 
       	var startTmp=startTime.split("-");
       	var endTmp=endTime.split("-");
       	var sd=new Date(startTmp[0],startTmp[1],startTmp[2]);
       	var ed=new Date(endTmp[0],endTmp[1],endTmp[2]); 
       	if(sd.getTime()>ed.getTime()){
          	alert("开始时间不能大于结束时间");  
         	return false;
		}
	}
  	return true;
}   
</script>

<tr>
	<td width="1%" style="text-align: right;">开始时间：</td>
	<td ><html:text name="ec" property="startDate" onfocus="setday(this)" readonly="true" styleClass="date_but"/></td>
	<td width="1%" style="text-align: right;">结束时间：</td>
	<td ><html:text name="ec" property="endDate" onfocus="setday(this)" readonly="true" styleClass="date_but"/></td>
	<td width="1%"><font color="#FF0000">＊</font>组&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;织：</td>
	<td >
	   <html:hidden name="ec" property="orgId" value="${_orgchildsubcodes}"/>
	   <html:text name="ec" property="orgName" value="${_orgchildnames}" readonly="true" styleClass="select_but" onclick="selectOrg()"/>
	</td>
</tr>
<tr>
	<!-- <td width="1%">渠道：</td> -->
	<td width="1%" style="text-align: right;">客户销售分类：</td>
	<td >
	  <html:hidden property="channelId"/>
	  <html:text property="channelName" readonly="true" styleClass="select_but" onclick="selectDict('storeChannel', listReportForm.channelId, listReportForm.channelName)"/>
	</td>
	<!-- <td width="1%">门店类型：</td> -->
	<td width="1%" style="text-align: right;">渠道：</td>
	<td >
	  <html:hidden property="storeTypeId"/>
	  <html:text property="storeTypeName" readonly="true" styleClass="select_but" onclick="selectDict('storeType', listReportForm.storeTypeId, listReportForm.storeTypeName)"/>
	</td>
	<td width="1%" style="text-align: right;">经销商编码：</td>
	<td >
	  <html:text name="ec" property="distCode" />
	</td>
	<td width="1%" style="text-align: right;">经销商名称：</td>
	<td >
	  <html:hidden name="ec" property="distId"/>
	  <html:text name="ec" property="distName" styleClass="select_but" onclick="selectDist()" readonly="true"/>
	</td>
</tr>
<tr>
	<td width="1%" style="text-align: right;">产品品牌：</td>
	<td >
	  <html:hidden property="prodBrandId"/>
	  <html:text property="prodBrandName" readonly="true" styleClass="select_but" onclick="selectDict('prodBrand', listReportForm.prodBrandId, listReportForm.prodBrandName)"/>
	</td>
	<td width="1%" style="text-align: right;">产品品类：</td>
	<td >
	  <html:hidden property="prodTypeId"/>
	  <html:text property="prodTypeName" readonly="true" styleClass="select_but" onclick="selectDict('prodType', listReportForm.prodTypeId, listReportForm.prodTypeName)"/>
	</td>
	<td width="1%" style="text-align: right;">产品代码：</td>
	<td >
	  <html:text name="ec" property="prodCode" />
	</td>
	<td width="1%" style="text-align: right;">产品名称：</td>
	<td >
	  <html:text name="ec" property="prodId"/>
	</td>
</tr>



<script>
function onLoad(){
	var now = new Date();
	var nowYear = now.getYear();
	var nowMonth = now.getMonth()+1;
	var nowDay = now.getDate();
	
	var d=new Date();   
	d = new Date(d.getFullYear(), d.getMonth()+1, 0);
	lastDay=d.getDate();//本月最后一天
	
	if(nowMonth < 10){
		nowMonth="0"+nowMonth;
	}	
	if(nowDay < 10){
		nowDay="0"+nowDay;
	}
	if(document.listReportForm.startDate.value == ''){
		//document.listReportForm.startDate.value = nowYear+"-"+nowMonth+"-01";
		setYear(nowMonth);
	}
	
	if(document.listReportForm.endDate.value == ''){
		document.listReportForm.endDate.value = nowYear+"-"+nowMonth+"-"+nowDay;
	}
}
onLoad();
//暂时按年份写成死的，日后平台需要增加功能（从数据库中取得）
function setYear(nowMonth){
	var now = new Date();
	var nowY = now.getYear();
	var nowM = now.getMonth()+1;
	var nowD = now.getDate();
	if(nowM < 10){nowM="0"+nowM;}	
	if(nowD < 10){nowD="0"+nowD;}
	nowDate=nowY+'-'+nowM+'-'+nowD; 
	if(nowDate >= '2011-01-03' && nowDate <= '2011-01-30'){
		document.listReportForm.startDate.value = '2011-01-03';
	}else if(nowDate >= '2011-01-31' && nowDate <= '2011-02-27'){
		document.listReportForm.startDate.value = '2011-01-31';
	}else if(nowDate >= '2011-02-28' && nowDate <= '2011-04-03'){
		document.listReportForm.startDate.value = '2011-02-28';
	}else if(nowDate >= '2011-04-04' && nowDate <= '2011-05-01'){
		document.listReportForm.startDate.value = '2011-04-04';
	}else if(nowDate >= '2011-05-02' && nowDate <= '2011-05-29'){
		document.listReportForm.startDate.value = '2011-05-02';
	}else if(nowDate >= '2011-05-30' && nowDate <= '2011-07-03'){
		document.listReportForm.startDate.value = '2011-05-30';
	}else if(nowDate >= '2011-07-04' && nowDate <= '2011-07-31'){
		document.listReportForm.startDate.value = '2011-07-04';
	}else if(nowDate >= '2011-08-01' && nowDate <= '2011-08-28'){
		document.listReportForm.startDate.value = '2011-08-01';
	}else if(nowDate >= '2011-08-29' && nowDate <= '2011-10-02'){
		document.listReportForm.startDate.value = '2011-08-29';
	}else if(nowDate >= '2011-10-03' && nowDate <= '2011-10-30'){
		document.listReportForm.startDate.value = '2011-10-03';
	}else if(nowDate >= '2011-10-31' && nowDate <= '2011-11-27'){
		document.listReportForm.startDate.value = '2011-10-31';
	}else{
		document.listReportForm.startDate.value = '2011-11-28';
	}
}
</script>
