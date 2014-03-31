<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="java.util.Date" %>
<%@page import="java.text.SimpleDateFormat" %>
<%
request.setAttribute("date",request.getParameter("queryDate")==null?new SimpleDateFormat("yyyy-MM").format(new Date()):request.getParameter("queryDate"));
%>


<%@ include file="/commons/taglibs.jsp"%>
	<script>
		function selectDict(dictName, id, name) {
			var v = openDictTree('${ctx}', dictName, 0, 2);
			if (v) {
				id.value = v.id;
				name.value = v.text;
			}
		}
		
		function selectDist() {
			var form = document.listReportForm;
			var v = openDistTree('${ctx}', '', '1', form.distId.value);
			if (v) {
				form.distId.value = v.id;
				form.distName.value = v.text;
			}
		}
		function selectClient() {
			var form = document.listReportForm;
			var v = openClientTree('${ctx}', '1');
			if (v) {
				form.clientId.value = v.id;
				form.clientName.value = v.text;
			}
		}
		function selectLevelTree() {
			var form = document.listReportForm;
			var v = openLevelTree('${ctx}','0','2','4');
			if (v) {
				form.queryOrgIds.value = v.id;
				form.orgName.value = v.text;
			}
		}
		
		function selectProdTree(){
			var form = document.listReportForm;
			var v = openProdTree('${ctx}','0','0');
			if (v) {
				if(v.leaf=='1'){
					form.prodCode.value = v.prodCode;
					form.targetProdName.value = v.text;
					form.brandId.value = "";
				}else{
					form.brandId.value = v.id;
					form.prodCode.value = "";
					form.targetProdName.value = v.text;
				}
			}
		} 
		showQuery_('query_A');
	</script>
	<tr>
		<td width="1%" align="right">年份：</td>
		<td width="1%" align="left"><html:text name="ec" property="queryDate" styleId="queryDate" maxlength="20" styleClass="date_but" onclick="showYear(this)" readonly="true"/></td>
		<td width="1%">${mr['page.common.orgName']}</td>
		<td width="1%">
			<html:hidden property="queryOrgIds"/>
			<html:text property="orgName" styleClass="select_but" onclick="selectLevelTree()" readonly="true"/>
		</td>
		<td width="1%">${mr['page.common.geographicArea']}</td>
		<td width="1%">
			<html:hidden property="queryGeoIds"/><html:text property="geoName" readonly="true" styleClass="select_but" onclick="selectDict('geography',form.queryGeoIds,form.geoName)"/>
		</td>
		<!-- 
		<td width="1%">
			经销商级别：
		</td>
		<td width="1%">
			<html:select name="ec" property="levelCode">
				<html:option value=""></html:option>
				<html:option value="1">一级</html:option>
				<html:option value="2">二级</html:option>
			</html:select>
		</td>
		 -->
		<td width="1%">
			产品：
		</td>
		<td width="1%">
			<html:hidden property="brandId"/>
			<html:hidden property="prodCode"/>
			<html:text property="targetProdName" styleClass="select_but" onclick="selectProdTree()" readonly="true"/>
		</td>
		<td></td>
	</tr>
<script>
//初始化年份 = 当前年份
function onLoad(){
	/**
	var now = new Date();
	var nowYear = now.getYear();
	var nowMonth = now.getMonth()+1;
	if(nowMonth < 10){
		nowMonth="0"+nowMonth;
	}
	if(document.listReportForm.startYM.value == ''){
		document.listReportForm.startYM.value = nowYear+'-'+nowMonth;
	}
	
	if(document.listReportForm.endYM.value == ''){
		document.listReportForm.endYM.value = nowYear+'-'+nowMonth;
	}*/
	
	var now = new Date();
	var nowYear = now.getYear();
	if(document.listReportForm.queryDate.value == ''){
		document.listReportForm.queryDate.value = nowYear;
	}
}	
onLoad();
</script>
<script>
//document.writeln("<div id='mydate' style='position:absolute; z-index:1; visibility: hidden; filter:\"progid:DXImageTransform.Microsoft.Shadow(direction=135,color=#999999,strength=3)\"'></div>");
var b = null;

function showYear(InputBox) {
	var x, y;
	var DivContent;
	var o = InputBox;
	b = InputBox.name;
	//显示的位置   
	x = o.getBoundingClientRect().left;
	y = o.offsetTop;
	document.all.mydate.style.left =x+85;
	document.all.mydate.style.top = 43;
	document.all.mydate.style.visibility = "visible";
	
	DivContent = "<div style='width:180px; z-index: 1; border:1px solid #999999; background-color:#F7F7F7;filter:\"progid:DXImageTransform.Microsoft.Shadow(direction=135,color=#999999,strength=3)\"'>";
	DivContent += " <div>";
	DivContent += "   <div colspan='3' align='center' style='border-bottom:1px  solid #FF5555; background-color:#E1E1E1  ;font-family:Verdana;   font-size:12px'>";
	DivContent += "     年份<a  title='\u5411\u524d\u7ffb 1 \u5e74' onClick='minus()' ><strong>&lt; </strong></a>"
	DivContent += "		<input type='text' name='selectYear' style='width:40px;' value='' >"
	DivContent += "		<a  title='\u5411\u540e\u7ffb 1 \u5e74' onClick='add()'><strong> &gt; </strong></a>";
	DivContent += "		<a  onClick=\"setYear()\" onmouseover=\"this.style.backgroundColor='#FF5555'\" onmouseout=\"this.style.backgroundColor='#F7F7F7'\" class='month'>${mr['page.common.button.ok']}</a>";
	DivContent += "		<a  onClick=\"divClose()\" onmouseover=\"this.style.backgroundColor='#FF5555'\" onmouseout=\"this.style.backgroundColor='#F7F7F7'\" class='month'>${mr['page.common.button.close']}</a>";
	DivContent += "   </div>";
	DivContent += " </div>";
	DivContent += "</div>";
	
	document.all.mydate.innerHTML = DivContent;
	var divId = document.getElementById("mydate");
	divId.style.display = "block";
	init();
}
function show() {
	showTable();
	var divId = document.getElementById("mydate");
	divId.style.display = "block";
	init();
}
function init() {
	var now = new Date();
	var nowYear = now.getYear();
	document.all.selectYear.value = nowYear;
}
function minus() {
	var year = parseInt(document.all.selectYear.value) - 1;
	if (year < 1900) {
		alert("年份选择不合理");   
		return;
	}
	document.all.selectYear.value = year;
}
function add() {
	var year = parseInt(document.all.selectYear.value) + 1;
	if (year > 2100) {
		alert("年份选择不合理");   
		return;
	}
	document.all.selectYear.value = year;
}
function divClose() {
	document.getElementById("mydate").style.display = "none";
}
function setYear() {
	var y = document.all.selectYear.value;
	if (y.match(/\D/) != null) {
		if (y.match(/\D/)!=null){alert("年份输入参数不是数字！");divClose();return;}      
		divClose();
		return;
	}
	if (y < 1900 || y > 9999) {
		alert("年份输入不合理"); 
		divClose();
		return;
	}  
	document.listReportForm.queryDate.value = document.all.selectYear.value;
	divClose();
}
</script>