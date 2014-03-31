<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script>
	function onSubmit() {
		var form = document.listReportForm;
		var stockDate = form.stockDate.value;
		if(stockDate==''||stockDate==null){
			alert("请选择日期！")
			return;
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
	
	//经销商
	function selectDist() {
		var form = document.listReportForm;
		var v = openDistTreeByOrg('${ctx}','',2, form.distId.value, '', 1);
		//var v = openDistTreeByOrg('${ctx}','',2, form.distId.value, form.orgId.value, 1);
		if (v) {
			form.distId.value = v.id;
			form.distName.value = v.text;
		}
	}		
	function selectBrand() {
		var form = document.listReportForm;
		var v = openDictTree('${ctx}','prodBrand','2','2',form.brandId.value);
		if (v) {
			form.brandId.value = v.id;
			form.brandName.value = v.text;
		}
	}	
	function selectGeo(){
		var form = document.listReportForm;
		var v = openDictTree('${ctx}',"geography",0,2,form.geoId.value);
		if (v) {
			form.geoId.value= v.id;
			form.geoName.value = v.text;
		}
	}	
	function selectCategory() {
		var form = document.listReportForm;
		var v = openDictTree('${ctx}','prodtype','0','0',form.categoryId.value );
		if (v) {
			form.categoryId.value = v.id;
			form.categoryName.value = v.text;
		}
	}
  	</script>
  	
<tr>
	<td width="1%" style="text-align: right;"><font color="#FF0000">＊</font>日 期 ：</td>
	<td>
		<html:text name="ec" property="stockDate" onfocus="setday(this)" readonly="true" styleClass="date_but" />
	</td>
	<td colspan="6"></td>
</tr>
<tr>
	<td width="1%" style="text-align: right;">管理组织：</td>
	<td>
		<html:hidden name="ec" property="orgId" />
		<html:text name="ec" property="orgName" styleClass="select_but" onclick="selectOrg()" readonly="true" />
	</td>
	<td width="1%" style="text-align: right;">地理结构：</td>
	<td>
		<html:hidden name="ec" property="geoId" />
		<html:text name="ec" property="geoName" styleClass="select_but" onclick="selectGeo()" readonly="true" />
	</td>
	<td width="1%" style="text-align: right;">经销商名称：</td>
	<td>
		<html:hidden name="ec" property="distId" />
		<html:text name="ec" property="distName" styleClass="select_but" onclick="selectDist()" readonly="true" />
	</td>
	<td width="1%" style="text-align: right;">经销商代码：</td>
	<td><html:text name="ec" property="distCode" /></td>
<tr>
	<td style="text-align: right;">产品名称：</td>
	<td><html:text name="ec" property="prodName" /></td>
	<td style="text-align: right;">产品代码：</td>
	<td><html:text name="ec" property="prodCode" /></td>
	<td style="text-align: right;">产品品类：</td>
	<td>
		<html:hidden property="categoryId" />
		<html:text name="ec" property="categoryName" styleClass="select_but" onclick="selectCategory()" readonly="true" />
	</td>
	<td style="text-align: right;">产品品牌：</td>
	<td>
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
	
	if(nowMonth < 10){
		nowMonth="0"+nowMonth;
	}	
	if(nowDay < 10){
		nowDay="0"+nowDay;
	}
	if(document.listReportForm.stockDate.value == ''){
		document.listReportForm.stockDate.value = nowYear+"-"+nowMonth+"-01";
	}

}
onLoad();
</script>