<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>
<%@ page import="com.winchannel.base.utils.Constants" %>
<%@ page import="com.winchannel.base.model.BaseUser" %>

<%
	String first = String.valueOf(request.getParameter("first"));
	//取到当前登录用户组织
	BaseUser userInfo = (BaseUser)session.getAttribute(Constants.SESSION_USER_KEY);
	String orgSubCode = (null==userInfo.getBaseEmployee().getBaseOrg()) ? "0" : userInfo.getBaseEmployee().getBaseOrg().getSubCode();
%>
<script>
	showQuery_('query_A');
	
	function selectDist() {
		var form = document.treeForm;
		var v = openDistOrgTree('${ctx}', '1', '2');
		if (v) {
			form.distId.value = v.id;
			form.distName.value = v.text;
		}
	}	
	function selectBrand() {
		var form = document.treeForm;
		var v = openDictTree('${ctx}', 'prodBrand', 2, 2, form.brandId.value);
		if (v) {
			form.brandId.value = v.id;
			form.brand.value = v.text;
		}
	}
	function selectSKU() {
		var form = document.treeForm;
		var v=  window.showModalDialog('${ctx}' + "/tree/tmpSourceData.do?method=skuNameTree&title=" + encodeURI("SKU") + "&state=1&first=1&onlyLeaf=2&checkbox=2&values=" + form.skuId.value + "&rand=" + Math.random(), "", "dialogHeight:420px;dialogWidth:420px;scroll=no;help:no;status:no");
		if (v) {
			form.skuId.value = (v.id).replace("*","'");
			form.skuName.value = (v.text).replace("*","'");
		}
	}
		
	function onSubmit() {
		var form = document.treeForm;
		if (form.startDate.value == '' || form.endDate.value == '') {
			alert('请填写日期！');
			return false;
		}

		return true;
	}
	function selectSimsCity() {
		var v = openOrgTree('${ctx}', 2, 2, document.treeForm.simsOrgId.value);
		if (v) {
			document.treeForm.simsOrgId.value = v.id;
			document.treeForm.simsOrgName.value = v.text;
		}
	}
		
	function selectCity(){
		var v = openOrgTree('${ctx}', 2, 2, document.treeForm.orgId.value);
		if(v){
			document.treeForm.orgId.value = v.id;
			document.treeForm.orgName.value = v.text;
		}
	}
</script>

<tr>
	<td width="1%"><font color="#FF0000">＊</font>Date：</td>
	<td>
		<html:text name="ec" property="startDate" onfocus="setday(this)" readonly="true" styleClass="date_but"/>
		<%--  
		-
		<html:text name="ec" property="endDate" onfocus="setday(this)" readonly="true" styleClass="date_but"/>
		--%>
	</td>
	
	<td width="1%" align="right">Distributor：</td>
	<td>
		<html:hidden name="ec" property="orgSubCode"/>
		<html:hidden name="ec" property="distId"/>
		<html:text name="ec" property="distName" styleClass="select_but" onclick="selectDist()" readonly="true"/>
	</td>
	<td width="1%" align="right">City：</td>
	<td> 
		<html:hidden name="ec" property="orgId" />
		<html:text name="ec" property="orgName" maxlength="20" styleClass="select_but" onclick="selectCity()" readonly="true" />
	</td>	
</tr>

<tr>
	<td width="1%" align="right">Brand：</td>
	<td> 
		<html:hidden name="ec" property="brandId"/>
		<html:text name="ec" property="brand" styleClass="select_but" onclick="selectBrand()" readonly="true"/>	
	</td>
	<td width="1%" align="right">SKU：</td>
	<td>
		<html:hidden name="ec" property="skuId"/>
		<html:text name="ec" property="skuName" styleClass="select_but" onclick="selectSKU()" readonly="true"/>
	</td>
	<td width="1%" align="right">Unit：</td>
	<td> 
		<html:select name="ec" property="Unit">   
			<html:option value="341">Qli（100公斤）</html:option> 
        </html:select>   	 
	</td>	
</tr>
<script>
document.treeForm.orgSubCode.value = '<%=orgSubCode%>';
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
	if(document.treeForm.startDate.value == ''){
		document.treeForm.startDate.value = nowYear+'-'+nowMonth+'-'+nowDay;
	}
}	
onLoad();
</script>

