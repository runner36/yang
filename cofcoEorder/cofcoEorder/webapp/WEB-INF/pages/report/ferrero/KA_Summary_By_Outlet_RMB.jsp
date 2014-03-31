<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ page language="java" import="java.util.*, java.text.*;" %>
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
	function selectBanner() {
		var form = document.listReportForm;
		var v=  window.showModalDialog('${ctx}' + "/tree/tmpSourceData.do?method=bannerDictTree&title=" + encodeURI("Banner") + "&state=1&first=1&onlyLeaf=2&checkbox=2&values=" + form.bannerId.value + "&rand=" + Math.random(), "", "dialogHeight:420px;dialogWidth:420px;scroll=no;help:no;status:no");
		if (v) {
			form.bannerId.value = v.id;
			form.banner.value = v.text;
		}
	}
	function onSubmit() {
		var form = document.listReportForm;
		if (form.banner.value == '') {
			alert('请选择KA！');
			return false;
		}		
		return true;
	}
	function onLoad(){
		var now = new Date();
		var nowYear = now.getYear();
		if(document.listReportForm.Year.value == ''){
			document.listReportForm.Year.value = nowYear;
		}
		setFiscalYear();
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
           <option value="1"  <%=monthStr.equals("1") ? "selected" : ""%>>1</option>  
           <option value="2" <%=monthStr.equals("2") ? "selected" : ""%>>2</option>  
           <option value="3" <%=monthStr.equals("3") ? "selected" : ""%>>3</option>  
           <option value="4" <%=monthStr.equals("4") ? "selected" : ""%>>4</option>  
           <option value="5" <%=monthStr.equals("5") ? "selected" : ""%>>5</option>  
           <option value="6" <%=monthStr.equals("6") ? "selected" : ""%>>6</option>  
           <option value="7" <%=monthStr.equals("7") ? "selected" : ""%>>7</option>  
           <option value="8" <%=monthStr.equals("8") ? "selected" : ""%>>8</option>  
           <option value="9" <%=monthStr.equals("9") ? "selected" : ""%>>9</option>  
           <option value="10" <%=monthStr.equals("10") ? "selected" : ""%>>10</option>  
           <option value="11" <%=monthStr.equals("11") ? "selected" : ""%> >11</option>  
           <option value="12" <%=monthStr.equals("12") ? "selected" : ""%>>12</option>  
         </html:select>
         <html:hidden name="ec" property="Mon" />
	</td>
	
	<td width="1%" align="right"><font color="#FF0000">＊</font>KA：</td>
	<td> 
		<html:hidden name="ec" property="bannerId"/>
		<html:text name="ec" property="banner" styleClass="select_but" onclick="selectBanner()" readonly="true"/>	
	</td>
			
	<td width="1%" align="right">City：</td>
	<td> 
		<html:hidden name="ec" property="orgId" />
		<html:text name="ec" property="orgName" maxlength="20" styleClass="select_but" onclick="selectCity()" readonly="true" />
	</td>
			
	<td width="1%" align="right">Unit：</td>
	<td> 
		<html:select name="ec" property="Unit">   
			<html:option value="3">RMB（金额）</html:option>
         </html:select>   	 
	</td>		
</tr>
<script>
onLoad();
</script>

