﻿<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script>
	function onSubmit() {
	    var form = document.listReportForm;
		var ge_billDate = form.ge_billDate.value;
		var le_billDate = form.le_billDate.value;
		if(ge_billDate==''||le_billDate==null||le_billDate==''||le_billDate==null){
			alert("请选择日期！")
			return;
		}
		if(_compareDate(le_billDate,ge_billDate)>0){
			alert("开始日期不能大于结束日期！")
			return ;
		}
		if(_compareDate(_getCurrentDate(),le_billDate)>=0){
			alert("请选择今天以前的日期！")
			return ;
		}
		return true;
	}
	
	//组织
	function selectOrg() {
        var form = document.listReportForm;
		var v = openOrgTree('${ctx}','2','2',form.orgId.value);
		if (v) {
			form.orgId.value = v.id
			form.orgName.value = v.text;
		}
	}
	//省份
	function selectGeo(){
		var form = document.listReportForm;
		var v = openDictTree('${ctx}',"geography",0,2,form.geoId.value);
		if (v) {
			form.geoId.value= v.id;
			form.geoName.value = v.text;
		}
	}
	//经销商
	function selectDist() {
		var form = document.listReportForm;
		var v = openDistTreeByOrg('${ctx}','',2, form.distId.value, '', 1);
		if (v) {
			form.distId.value = v.id;
			form.distName.value = v.text;
		}
	}
	//门店类型
	function selectStoreType() {
		var form = document.listReportForm;
		var v = openDictTree('${ctx}','storeType','2','2',form.typeId.value);
		if (v) {
			form.typeId.value = v.id;
			form.storeType.value = v.text;
		}
	}
	//门店属性
	function selectStoreNature(){
		var form = document.listReportForm;
		var v = openDictTree('${ctx}','storeChannel','2','2',form.natureId.value);
		if (v) {
			form.natureId.value = v.id;
			form.natureName.value = v.text;
		}	
	}
	//门店组织
	function selectStoreCorp(){
		var form = document.listReportForm;
		var v = openDictTree('${ctx}','storeCorp','2','2',form.corpId.value);
		if (v) {
			form.corpId.value = v.id;
			form.corpName.value = v.text;
		}	
	}
	//产品品牌
	function selectBrand() {
		var form = document.listReportForm;
		var v = openDictTree('${ctx}','prodBrand','2','2',form.brandId.value);
		if (v) {
			form.brandId.value = v.id;
			form.brandName.value = v.text;
		}
	}			
	//产品品类
	function selectCategory() {
		var form = document.listReportForm;
		var v = openDictTree('${ctx}','prodType','0','0',form.categoryId.value );
		if (v) {
			form.categoryId.value = v.id;
			form.categoryName.value = v.text;
		}
	}
  	</script>
  	
<tr>
	<td width="1%" style="text-align: right;"><font color="#FF0000">＊</font>开始日期：</td>
	<td>
		<html:text name="ec" property="ge_billDate" onfocus="setday(this)" readonly="true" styleClass="date_but" />
	</td>
	<td width="1%" style="text-align: right;"><font color="#FF0000">＊</font>结束日期：</td>
	<td>
		<html:text name="ec" property="le_billDate" onfocus="setday(this)" readonly="true" styleClass="date_but" />
	</td>
	<td width="1%">&nbsp;</td>
	<td>&nbsp;</td>
	<td width="1%">&nbsp;</td>
	<td>&nbsp;</td>
	<td width="1%">&nbsp;</td>
	<td>&nbsp;</td>
</tr>
<tr>
	<td width="1%" style="text-align: right;">管理组织：</td>
	<td>
		<html:hidden name="ec" property="orgId" />
		<html:text name="ec" property="orgName" styleClass="select_but" onclick="selectOrg()" readonly="true" />
	</td>
	<td width="1%" style="text-align: right;">省 份：</td>
	<td>
		<html:hidden name="ec" property="geoId" />
		<html:text name="ec" property="geoName" styleClass="select_but" onclick="selectGeo()" readonly="true" />
	</td>
	<td width="1%" style="text-align: right;">经销商名称：</td>
	<td>
		<html:hidden name="ec" property="distId" />
		<html:text name="ec" property="distName" styleClass="select_but" onclick="selectDist()" readonly="true" />
	</td>
	<td width="1%" style="text-align: right;">经销商代码：
	</td>
	<td><html:text name="ec" property="distCode" /></td>
	<td width="1%">&nbsp;</td>
	<td>&nbsp;</td>
</tr>
<tr>
	<td width="1%" style="text-align: right;">门店名称：</td>
	<td><html:text name="ec" property="sisName" /></td>
	<td width="1%" style="text-align: right;" >门店代码：</td>
	<td><html:text name="ec" property="sisCode" /></td>
	<td width="1%" style="text-align: right;">客户属性：</td>
	<td>
		<html:hidden name="ec" property="natureId" />
		<html:text name="ec" property="natureName" styleClass="select_but" onclick="selectStoreNature()" readonly="true" />
	</td>
	<td width="1%" style="text-align: right;">客户组织：</td>
	<td>
		<html:hidden name="ec" property="corpId" />
		<html:text name="ec" property="corpName" styleClass="select_but" onclick="selectStoreCorp()" readonly="true" />
	</td>
	<td width="1%" style="text-align: right;">客户类型：</td>
	<td>
		<html:hidden name="ec" property="typeId" />
		<html:text name="ec" property="storeType" styleClass="select_but" onclick="selectStoreType()" readonly="true" />
	</td>
</tr>
<tr>
	<td width="1%" style="text-align: right;">产品名称：</td>
	<td><html:text name="ec" property="prodName" /></td>
	<td width="1%" style="text-align: right;">产品代码：</td>
	<td><html:text name="ec" property="prodCode" /></td>
	<td width="1%" style="text-align: right;">产品品类：</td>
	<td>
		<html:hidden property="categoryId" />
		<html:text name="ec" property="categoryName" styleClass="select_but" onclick="selectCategory()" readonly="true" />
	</td>
	<td width="1%" style="text-align: right;">产品品牌：</td>
	<td colspan="7">
		<html:hidden property="brandId" />
		<html:text name="ec" property="brandName" styleClass="select_but" onclick="selectBrand()" readonly="true" />
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
	if(document.listReportForm.ge_billDate.value == ''){
		document.listReportForm.ge_billDate.value = nowYear+"-"+nowMonth+"-01";
	}
	
	if(document.listReportForm.le_billDate.value == ''){
		document.listReportForm.le_billDate.value = nowYear+"-"+nowMonth+"-"+nowDay;
	}
}
onLoad();
</script>