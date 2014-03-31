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
%>


<script>
	showQuery_('query_A');
	
	function onSubmit() {
		var form = document.treeForm;
		form.Mon.value = form.Month.value;
		if (form.Year.value == '') {
			alert('请填写年份！');
			return false;
		}
		if(form.Month.value == ''){
			alert('请填写月份!');
			return false;		
		}
		return true;
	}
	//SimsCity
	function selectSimsCity() {
		//var v = openDictTree('${ctx}', 'geography', 2, 2, document.treeForm.simsOrgName.value);
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
	function selectType() {
		var v = openDictTree('${ctx}', 'storeNature', 2, 2, document.treeForm.typeId.value);
		if (v) {
			document.treeForm.typeId.value = v.id;
			document.treeForm.typeName.value = v.text;
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
	function onLoad(){
		var now = new Date();
		var nowYear = now.getYear();
		if(document.treeForm.Year.value == ''){
			document.treeForm.Year.value = nowYear;
		}
		//setFiscalYear();
		if('1' == <%=first%>){
			//loading();
			//treeForm.submit();			
		}
	}
	function selectDist() {
		var form = document.treeForm;
		var v = openDistOrgTree('${ctx}', '1', '2');
		if (v) {
			form.distId.value = v.id;
			form.distName.value = v.text;
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
	<td width="1%" align="right"><font color="#FF0000">＊</font>Year：</td>
	<td><html:text name="ec" property="Year" maxlength="20" styleClass="date_but" onfocus="WdatePicker({dateFmt:'yyyy'});" readonly="true"/><span id="YY"></span></td>

	<td width="1%" align="right"><font color="#FF0000">＊</font>Month：</td>
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
		<html:select name="ec" property="Month">   
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
         <html:hidden name="ec" property="Mon" />
	</td>
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
	<td>&nbsp;</td>
	<td>&nbsp;</td>
</tr>

<tr>	
	<td width="1%" align="right">Type：</td>
	<td> 
		<html:hidden name="ec" property="typeId" styleId="type_id" />
		<html:text name="ec" property="typeName" styleClass="select_but" onclick="selectType()" readonly="true"/>	
	</td>	
	<td width="1%" align="right">Banner：</td>
	<td>
		<html:hidden name="ec" property="bannerId"/>
		<html:text name="ec" property="banner" styleClass="select_but" onclick="selectBanner()" readonly="true"/>	
	</td>
	<td width="1%" align="right">Brand：</td>
	<td> 
		<html:hidden name="ec" property="brandId" styleId="brand_id" />
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
  <tr>
 	<td width="1%" align="left" colspan="10"><font color="#FF0000">＊由于数据量太大，建议除必选条件外再选上其他查询条件，提高查询效率。</font></td> 
  </tr>

<script>
document.treeForm.orgSubCode.value = '<%=orgSubCode%>';
onLoad();
</script>

