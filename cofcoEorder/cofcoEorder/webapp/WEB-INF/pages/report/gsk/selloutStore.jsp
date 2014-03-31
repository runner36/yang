<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script>
	//渠道
	function selectDict(dictName, id, name) {
		var v = openDictTree('${ctx}', dictName, 2, 2, id.value);
		if (v) {
			id.value = v.id;
			name.value = v.text;
		}
	}
	//产品
	function selectProdTree(){
	var form = document.listReportForm;
	var v = openProdTree('${ctx}',2,2,form.prodId.value);
	if (v) {
		form.prodId.value = v.id;
		form.prodName.value = v.text;
		}
	} 
	//性质
	 function selectStoreNature(dictName, id, name){
		 var v = openDictTree('${ctx}', dictName, 2, 0, id.value);
			if (v) {
				id.value = v.id;
				name.value = v.text;
			}
	 }
showQuery_('query_A');
</script>
<tr>
	<td width="1%">
		时&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;间：
	</td>
	<td width="1%">
		<html:text name="ec" property="billDate" onfocus="setDay(this)" readonly="true" styleClass="date_but" />
	</td>
	<td width="1%">
		经销商性质：
	</td>
	<td width="1%">
		<html:select property="storeLevelCode">
		  <html:option value="">全部</html:option>
		  <html:option value="1">一级</html:option>
		  <html:option value="2">二级</html:option>
		</html:select>
	</td>
	<td width="1%">
		购买性质：
	</td>
	<td  >
	   <html:hidden name="ec" property="natureId"/>
	   <html:text name="ec" property="natureName" readonly="true" styleClass="select_but" onclick="selectStoreNature('storeNature',listReportForm.natureId,listReportForm.natureName)"/>
	</td>	
	 
</tr>

<tr>
	<td width="1%">
		${mr['page.common.geographicArea']}
	</td>
	<td width="1%">
	   <html:hidden name="ec" property="cityCode"/>
	   <html:text name="ec" property="cityName" readonly="true" styleClass="select_but" onclick="selectDict('geography',listReportForm.cityCode,listReportForm.cityName)"/>
	</td>
	<td width="1%">
		渠&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;道：
	</td>
	<td width="1%">
	   <html:hidden name="ec" property="channelId"/>
	   <html:text name="ec" property="channelName" readonly="true" styleClass="select_but" onclick="selectDict('storeChannel',listReportForm.channelId,listReportForm.channelName)"/>
	</td>
	<td width="1%">
		产&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;品：
	</td>
	<td colspan="3">
		<html:hidden property="prodId"/>
		<html:text property="prodName" styleClass="select_but" onclick="selectProdTree()" readonly="true"/>
	</td>
	
</tr>

<script type="text/javascript">
function onLoad(){
	var now = new Date();
	var nowYear = now.getYear();
	var nowMonth = now.getMonth()+1;
	var nowDay = now.getDate();
	
	if(nowMonth < 10){
		nowMonth="0"+nowMonth;
	}
	if(nowDay < 10){
		nowDay="0"+nowDay;
	}
	if(document.listReportForm.billDate.value == ''){
		document.listReportForm.billDate.value = nowYear+'-'+nowMonth+'-'+nowDay;
	}
}
onLoad();
</script>
