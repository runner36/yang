<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<%
	String first = String.valueOf(request.getParameter("first"));
%>

<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>


<script>
	showQuery_('query_A');
	
	function selectCity(){
		var v = openOrgTree('${ctx}', 2, 2, document.listReportForm.orgId.value);
		if(v){
			document.listReportForm.orgId.value = v.id;
			document.listReportForm.orgName.value = v.text;
		}
	}
	function selectDist() {
		var form = document.listReportForm;
		var v = openDistOrgTree('${ctx}', '1', '2');
		if (v) {
			form.distId.value = v.id;
			form.distName.value = v.text;
		}
	}
	function selectChannel() {
		var form = document.listReportForm;
		var v = openDictTree('${ctx}', 'storeType', 2, 2, form.channelId.value);
		if (v) {
			form.channelId.value = v.id;
			form.channelName.value = v.text;
		}
	}
	function selectBrand() {
		var v = openDictTree('${ctx}', 'prodBrand', 2, 2, document.listReportForm.brandId.value);
		if (v) {
			document.listReportForm.brandId.value = v.id;
			document.listReportForm.brandName.value = v.text;
		}
	}
	function selectBanner() {
		var form = document.listReportForm;
		var v = openDictTree('${ctx}', 'storeCorp', 2, 2, form.bannerId.value);
		if (v) {
			form.bannerId.value = v.id;
			form.banner.value = v.text;
		}
	}
	function onSubmit() {
		var form = document.listReportForm;
		if (form.Year.value == '') {
			alert('请填写年份！');
			return false;
		}
		return true;
	}
	
	//SimsCity
	function selectSimsCity() {
		var v = openOrgTree('${ctx}', 2, 2, document.listReportForm.simsOrgId.value);
		if (v) {
			document.listReportForm.simsOrgId.value = v.id;
			document.listReportForm.simsOrgName.value = v.text;
		}
	}
	//设置财政年份说明
	function setFiscalYear(){
		var yy=document.listReportForm.Year.value;
		var now = new Date(parseInt(yy),1,1);
		var nowYear = now.getFullYear();
		if(yy != ''){
			var nextYear = ""+(nowYear+1);
			var y1=yy.substring(2,4);
			var y2=nextYear.substring(2,4);
			var str = y1+y2;
			document.getElementById("YY").innerText="(Fiscal Year:"+str+")";
		}
	}
</script>
<tr>
	<td width="1%" align="right"><font color="#FF0000">＊</font>Fiscal Year：</td>
	<td><html:text name="ec" property="Year" maxlength="20" styleClass="date_but" onfocus="WdatePicker({dateFmt:'yyyy'});setFiscalYear();" readonly="true"/><span id="YY"></span></td>
	<td width="1%" align="right">Distributor：</td>
	<td>
		<html:hidden name="ec" property="distId"/>
		<html:text name="ec" property="distName" styleClass="select_but" onclick="selectDist()" readonly="true"/>
	</td>
	<%--
	<td width="1%" align="right">City：</td>
	<td> 
		<html:hidden name="ec" property="orgId" />
		<html:text name="ec" property="orgName" maxlength="20" styleClass="select_but" onclick="selectCity()" readonly="true" />
	</td>
	--%>
	<td width="1%" align="right">City：</td>
	<td> 
		<html:hidden name="ec" property="simsOrgId" />
		<html:text name="ec" property="simsOrgName" styleClass="select_but" onclick="selectSimsCity()" readonly="true" />
	</td>		
	<td>&nbsp;</td>
	<td>&nbsp;</td>	
</tr>
<tr>
	<td width="1%" align="right">Banner：</td>
	<td> 
		<html:hidden name="ec" property="bannerId"/>
		<html:text name="ec" property="banner" styleClass="select_but" onclick="selectBanner()" readonly="true"/>	
	</td>	
	<td width="1%" align="right">Channel：</td>
	<td> 
		<html:hidden name="ec" property="channelId"/>
		<html:text name="ec" property="channelName" styleClass="select_but" onclick="selectChannel()" readonly="true"/>	
	</td>
	<td width="1%" align="right">Brand：</td>
	<td> 
		<html:hidden name="ec" property="brandId" />
		<html:text name="ec" property="brandName" styleClass="select_but" onclick="selectBrand()" readonly="true"/>	
	</td>
	<td width="1%" align="right">Unit：</td>
	<td> 
		<html:select name="ec" property="Unit">   
			<html:option value="339">CTN（箱）</html:option>  
        </html:select>   	 
	</td>
</tr>
<script>
onLoad();
function onLoad(){
	var now = new Date();
	var nowYear = now.getYear();
	var form = document.listReportForm;
	if(form.Year.value == ''){
		form.Year.value = nowYear;
	}
	setFiscalYear();
	if('1' == <%=first%>){
		//loading();
		//listReportForm.submit();			
	}		
}
</script>

