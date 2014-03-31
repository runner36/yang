<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page language="java" import="java.util.*, java.text.*" %>
<%@ include file="/commons/taglibs.jsp"%>
<%@ page import="com.winchannel.base.utils.Constants" %>
<%@ page import="com.winchannel.base.model.BaseUser" %>

<%
	String first = String.valueOf(request.getParameter("first"));
	//取到当前登录用户组织
	BaseUser userInfo = (BaseUser)session.getAttribute(Constants.SESSION_USER_KEY);
	String orgSubCode = (null==userInfo.getBaseEmployee().getBaseOrg()) ? "0" : userInfo.getBaseEmployee().getBaseOrg().getSubCode();
	String orgId = (null==userInfo.getBaseEmployee().getBaseOrg()) ? "" : userInfo.getBaseEmployee().getBaseOrg().getOrgId().toString();
	String orgName = (null==userInfo.getBaseEmployee().getBaseOrg()) ? "" : userInfo.getBaseEmployee().getBaseOrg().getOrgName();
	String levelCode = (null==userInfo.getBaseEmployee().getBaseOrg()) ? "" : userInfo.getBaseEmployee().getBaseOrg().getLevelCode().toString();
%>
<script>
	showQuery_('query_A');

	function selectCity(){
		var v = openOrgTree('${ctx}', 2, 0, document.treeForm.orgId.value);
		if(v){
			document.treeForm.orgId.value = v.id;
			document.treeForm.orgName.value = v.text;
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
	function selectType() {
		var v = openDictTree('${ctx}', 'storeNature', 2, 2, document.treeForm.typeId.value);
		if (v) {
			document.treeForm.typeId.value = v.id;
			document.treeForm.typeName.value = v.text;
		}
	}
	
	
	function onSubmit() {
		var form = document.treeForm;
		if (form.orgId.value == '') {
			alert('请选择一个城市！');
			return false;
		}
		return true;
	}
	
	function query() {
		try{if (!onSubmit()) return;}catch(e){}
		loading();
		treeForm.submit();
	}
	
	function simsQuery(){
		document.getElementById("sims").value='and dds.CLIENT_ID=3 and md.DIST_ID=4 and md.DIST_ID=store.DIST_ID';
		document.getElementById("org").value='';
		document.getElementById("brand_id").value='';
		document.getElementById("type_id").value='';
		document.treeForm.orgSubCode.value = '';
		var now = new Date();
		document.getElementById("yearId").value=now.getYear();
		document.getElementById("monthId").options[now.getMonth()].selected=true;
		
		loading();
		treeForm.submit();
		document.getElementById("sims").value='';
	}
</script>
<style>
.btn {
	height:25;
	BORDER-RIGHT: #7b9ebd 1px solid; PADDING-RIGHT: 2px; BORDER-TOP:
	#7b9ebd 1px solid; PADDING-LEFT: 2px; FONT-SIZE: 12px; FILTER:
	progid:DXImageTransform.Microsoft.Gradient(GradientType=0,
	StartColorStr=#000000, EndColorStr=#cecfde); BORDER-LEFT: #7b9ebd
	1px solid; CURSOR: hand; COLOR: black; PADDING-TOP: 2px;
	BORDER-BOTTOM: #7b9ebd 1px solid
}
</style>

  <tr>
	<td width="1%"><font color="#FF0000">＊</font>Year：</td>
	<td>
	  <html:text name="ec" property="Year" styleId="yearId" maxlength="20" styleClass="date_but" onfocus="WdatePicker({dateFmt:'yyyy'});" readonly="true"/>
	  <span id="YY"></span>
	</td>

	<td width="1%"><font color="#FF0000">＊</font>Month：</td>
	<td>
<%
Date d = new Date();
SimpleDateFormat fm = new SimpleDateFormat("MM");
String monthStr = fm.format(d);
String tmpStr =  (null == request.getParameter("Month")) ? "" : request.getParameter("Month");
if(!"".equals(tmpStr)){
	monthStr = tmpStr;
}
%>	
		<html:select name="ec" property="Month" styleId="monthId" >   
           <option value="01" <%=monthStr.equals("01") ? "selected" : ""%>>1</option>  
           <option value="02" <%=monthStr.equals("02") ? "selected" : ""%>>2</option>  
           <option value="03" <%=monthStr.equals("03") ? "selected" : ""%>>3</option>  
           <option value="04" <%=monthStr.equals("04") ? "selected" : ""%>>4</option>  
           <option value="05" <%=monthStr.equals("05") ? "selected" : ""%>>5</option>  
           <option value="06" <%=monthStr.equals("06") ? "selected" : ""%>>6</option>  
           <option value="07" <%=monthStr.equals("07") ? "selected" : ""%>>7</option>  
           <option value="08" <%=monthStr.equals("08") ? "selected" : ""%>>8</option>  
           <option value="09" <%=monthStr.equals("09") ? "selected" : ""%>>9</option>  
           <option value="10" <%=monthStr.equals("10") ? "selected" : ""%>>10</option>  
           <option value="11" <%=monthStr.equals("11") ? "selected" : ""%>>11</option>  
           <option value="12" <%=monthStr.equals("12") ? "selected" : ""%>>12</option>  
         </html:select>
	</td>
	<td width="1%"><font color="#FF0000">＊</font>City：</td>
	<td align="left"> 
		<html:hidden name="ec" property="Unit" />
		<html:hidden name="ec" property="orgSubCode"/>
		<html:hidden name="ec" property="orgId" styleId="org" />
		<html:text name="ec" property="orgName" maxlength="20" styleClass="select_but" onclick="selectCity()" readonly="true" />
	</td>
	<td width="1%" align="right">Brand：</td>
	<td> 
		<html:hidden name="ec" property="brandId" styleId="brand_id" />
		<html:text name="ec" property="brand" styleClass="select_but" onclick="selectBrand()" readonly="true"/>	
	</td>
	<td width="1%" align="right">Type：</td>
	<td> 
		<html:hidden name="ec" property="typeId" styleId="type_id" />
		<html:text name="ec" property="typeName" styleClass="select_but" onclick="selectType()" readonly="true"/>	
	</td>
	<td align="left"><html:button property="button" value='City Data' onclick="query()" styleClass='btn' style="cursor:pointer; color:#000000;"></html:button></td>

	<td align="left">
	  <html:button property="button" value='Sims Data' onclick="simsQuery()" styleClass='btn' style="cursor:pointer; color:#000000;"></html:button>
	  <html:hidden name="ec" property="simsContand" styleId="sims" />
	</td>
  </tr>
  
  
  
  <script>
	function onLoad(){
		var now = new Date();
		var nowYear = now.getYear();
		if(document.treeForm.Year.value == ''){
			document.treeForm.Year.value = nowYear;
		}
		//setFiscalYear();
		
		if('1' == '<%=first%>'){
			if('4' == '<%=levelCode%>'){
				document.treeForm.orgId.value ='<%=orgId%>';
				document.treeForm.orgName.value = '<%=orgName%>';
			}
			//loading();
			//treeForm.submit();			
		}	
	}
	document.treeForm.orgSubCode.value = '<%=orgSubCode%>';
	onLoad();
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
