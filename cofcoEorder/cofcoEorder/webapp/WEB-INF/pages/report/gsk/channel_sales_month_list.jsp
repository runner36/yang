<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>


<script>
showQuery_('query_A');

function onSubmit() {
	var form = document.listReportForm;
	if (form.Year.value == '') {
		alert('请填写年份！');
		return false;
	}
	return true;
}
	
//组织
/**
function selectOrg() {
  	var v = openLevelTree('${ctx}', 2, 2, 4, listReportForm.orgId.value);
  	if (v) {
		listReportForm.orgId.value = v.id;
		listReportForm.orgName.value = v.text;
	}
}*/
function selectOrg() {
	var form = document.listReportForm;
  	var v = openOrgTree('${ctx}', 2, 2, form.orgId.value);
  	if (v) {
  		form.orgId.value = v.id;
		form.orgName.value = v.text;
	}
}

//渠道
function selectDict(dictName, id, name) {
	var v = openDictTree('${ctx}', dictName, 2, 2, id.value);
	if (v) {
		id.value = v.id;
		name.value = v.text;
	}
}
</script>

<tr>
	<td width="1%">年份：</td>
	<td width="1%"><html:text name="ec" styleId="Year" property="Year" maxlength="20" styleClass="date_but" onclick="showYear(this)" readonly="true"/></td>
	<td width="1%">${mr['page.common.orgName']}</td>
	<td width="1%">
	   <html:hidden name="ec" property="orgId"/>
	   <html:text name="ec" property="orgName" readonly="true" styleClass="select_but" onclick="selectOrg()"/>
	</td>
	<td width="1%">${mr['page.common.geographicArea']}</td>
	<td width="1%">
	   <html:hidden name="ec" property="geoId"/>
	   <html:text name="ec" property="geoName" readonly="true" styleClass="select_but" onclick="selectDict('geography',listReportForm.geoId,listReportForm.geoName)"/>
	</td>
	<td width="1%">渠道：</td>
	<td width="1%">
	   <html:hidden name="ec" property="channelId"/>
	   <html:text name="ec" property="channelName" readonly="true" styleClass="select_but" onclick="selectDict('storeChannel',listReportForm.channelId,listReportForm.channelName)"/>
	</td>
	<td width="1%">&nbsp;</td>
	<td>&nbsp;</td>
</tr>
<script>
//初始化年份 = 当前年份
function onLoad(){
	var now = new Date();
	var nowYear = now.getYear();
	if(document.listReportForm.Year.value == ''){
		document.listReportForm.Year.value = nowYear;
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
	x = o.offsetLeft;
	y = o.offsetTop;
	while (o = o.offsetParent) {
		x += o.offsetLeft;
		y += o.offsetTop;
	}
	document.all.mydate.style.left =155;
	document.all.mydate.style.top = 42;
	document.all.mydate.style.visibility = "visible";
	
	DivContent = "<div style='width:180px; z-index: 1; border:1px solid #999999; background-color:#F7F7F7;filter:\"progid:DXImageTransform.Microsoft.Shadow(direction=135,color=#999999,strength=3)\"'>";
	DivContent += " <div>";
	DivContent += "   <div colspan='3' align='center' style='border-bottom:1px  solid #FF5555; background-color:#E1E1E1  ;font-family:Verdana;   font-size:12px'>";
	DivContent += "     年份<a  title='\u5411\u524d\u7ffb 1 \u5e74' onClick='minus()' ><strong>&lt; </strong></a>"
	DivContent += "		<input type='text' name='selectYear' style='width:40px;' value='' >"
	DivContent += "		<a  title='\u5411\u540e\u7ffb 1 \u5e74' onClick='add()'><strong> &gt; </strong></a>";
	DivContent += "		<a  onClick=\"setYear('01')\" onmouseover=\"this.style.backgroundColor='#FF5555'\" onmouseout=\"this.style.backgroundColor='#F7F7F7'\" class='month'>${mr['page.common.button.ok']}</a>";
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
function setYear(year) {
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
	document.getElementById(b).value = document.all.selectYear.value;
	divClose();
}
</script>