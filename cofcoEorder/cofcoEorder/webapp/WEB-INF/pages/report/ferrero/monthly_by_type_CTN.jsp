<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ page import="com.winchannel.base.utils.Constants" %>
<%@ page import="com.winchannel.base.model.BaseUser" %>
<%
	String first = String.valueOf(request.getParameter("first"));
	//取到当前登录用户组织
	BaseUser userInfo = (BaseUser)session.getAttribute(Constants.SESSION_USER_KEY);
	String orgSubCode = (null==userInfo.getBaseEmployee().getBaseOrg()) ? "0" : userInfo.getBaseEmployee().getBaseOrg().getSubCode();
%>

<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>


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
	function selectChannel() {
		var form = document.treeForm;
		var v = openDictTree('${ctx}', 'storeType', 2, 2, form.channelId.value);
		if (v) {
			form.channelId.value = v.id;
			form.channelName.value = v.text;
		}
	}
	function selectBanner() {
		var form = document.treeForm;
		var v = openDictTree('${ctx}', 'storeCorp', 2, 2, form.bannerId.value);
		if (v) {
			form.bannerId.value = v.id;
			form.banner.value = v.text;
		}
	}
	function selectType() {
		var v = openDictTree('${ctx}', 'storeNature', 2, 2, document.treeForm.typeId.value);
		if (v) {
			document.treeForm.typeId.value = v.id;
			document.treeForm.typeName.value = v.text;
		}
	}	
	function selectCity(){
		var v = openOrgTree('${ctx}', 2, 2, document.treeForm.orgId.value);
		if(v){
			document.treeForm.orgId.value = v.id;
			document.treeForm.orgName.value = v.text;
		}
	}
	function onSubmit() {
		var form = document.treeForm;
		if (form.Year.value == '') {
			alert('请填写年份！');
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
	function selectBrand() {
		var form = document.treeForm;
		var v = openDictTree('${ctx}', 'prodBrand', 2, 2, form.brandId.value);
		if (v) {
			form.brandId.value = v.id;
			form.brand.value = v.text;
		}
	}
	function onLoad(){
		var now = new Date();
		var nowYear = now.getYear();
		if(document.treeForm.Year.value == ''){
			document.treeForm.Year.value = nowYear;
		}
		setFiscalYear();
		if('1' == <%=first%>){
			//loading();
			//treeForm.submit();			
		}
	}
	//设置财政年份说明
	function setFiscalYear(){
		var yy=document.treeForm.Year.value;
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
	<td> <html:text name="ec" property="Year" maxlength="20" styleClass="date_but" onfocus="WdatePicker({dateFmt:'yyyy'});setFiscalYear();" readonly="true"/><span id="YY"></span></td>
	<td width="1%" align="right">Distributor：</td>
	<td>
		<html:hidden name="ec" property="orgSubCode"/>
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
	<td width="1%" align="right">Channel：</td>
	<td> 
		<html:hidden name="ec" property="channelId"/>
		<html:text name="ec" property="channelName" styleClass="select_but" onclick="selectChannel()" readonly="true"/>	
	</td>
	<td width="1%" align="right">Type：</td>
	<td> 
		<html:hidden name="ec" property="typeId" styleId="type_id" />
		<html:text name="ec" property="typeName" styleClass="select_but" onclick="selectType()" readonly="true"/>	
	</td>	
</tr>
<tr>
	<td width="1%" align="right">Banner：</td>
	<td> 
		<html:hidden name="ec" property="bannerId"/>
		<html:text name="ec" property="banner" styleClass="select_but" onclick="selectBanner()" readonly="true"/>	
	</td>
	<td width="1%" align="right">Outlet：</td>
	<td> 
		<html:text name="ec" property="storeName" />	
	</td>
	<td width="1%" align="right">Brand：</td>
	<td> 
		<html:hidden name="ec" property="brandId" styleId="brand_id" />
		<html:text name="ec" property="brand" styleClass="select_but" onclick="selectBrand()" readonly="true"/>	
	</td>
	<td width="1%" align="right">SKU：</td>
	<td> 
		<html:text name="ec" property="prodCode" />	
	</td>
	<td width="1%" align="right">Unit：</td>
	<td> 
		<html:select name="ec" property="Unit">   
			<html:option value="339">CTN（箱）</html:option> 
        </html:select>   	 
	</td>		
</tr>
<script>
document.treeForm.orgSubCode.value = '<%=orgSubCode%>';
onLoad();
</script>

