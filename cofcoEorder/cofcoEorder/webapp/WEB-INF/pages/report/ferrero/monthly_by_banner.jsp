<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>
<script>
showQuery_('query_A');
function onSubmit() {
			var form = document.listReportForm;
			if (form.tyear.value == '') {
				alert('请填写日期');
				return false;
			}else
			{
				var year=form.tyear.value;
				var upYear=parseInt(year)-1;
				var downYear=parseInt(year)-2;
				form.upYear.value=upYear;
				form.downYear.value=downYear;
				return true;
			}
			
		}
	 function selectDist() {
			var form = document.listReportForm;
			var v = openDistTree('${ctx}', '1');
			if (v) {
				form.distId.value = v.id;
				form.distName.value = v.text;
			}
		}
		//城市树
function selectorg(){
var v = openOrgTree('${ctx}',0,0,document.listReportForm.orgId.value);
if(v){
	//allPrpos(v)
	
	document.listReportForm.orgId.value=v.id;
	document.listReportForm.subCode.value=v.subCode;
	document.listReportForm.orgName.value=v.text;
}
}
function selectbanner() {
			var form = document.listReportForm;
			var v=  window.showModalDialog('${ctx}' + "/tree/tmpSourceData.do?method=bannerTree&title=" + encodeURI("region") + "&state=1&first=1&onlyLeaf=&values=" + form.bannerId.value + "&rand=" + Math.random(), "", "dialogHeight:420px;dialogWidth:420px;scroll=no;help:no;status:no");
			if (v) {
				form.bannerId.value = v.id;
				form.banner.value = v.text;
			}
		}
</script>
<tr>
<td width="1%">
		Year：
	</td>
	<td width="200">
	<html:select property="tyear">
		<html:option value="2010">2010</html:option>
		<html:option value="2009">2009</html:option>
		<html:option value="2008">2008</html:option>
		<html:option value="2007">2007</html:option>
	</html:select>
	<html:hidden name="ec"  property="upYear" />
	<html:hidden name="ec" property="downYear"/>
	</td>
	<td width="1%">
		Distributor：
	</td>
	<td width="200">
		<html:hidden property="distId" />
		<html:text property="distName" styleClass="select_but" onclick="selectDist()" readonly="true"/>
	</td>
	<td width="1%">City：</td>
	<td width="200">
		<html:hidden name="ec" property="orgId" />
		<html:hidden name="ec" property="subCode" />
		<html:text name="ec" property="orgName" maxlength="20" styleClass="select_but" onclick="selectorg()" readonly="true" />
	 </td>
	<td width="1%">Banner：</td>
	<td width="200"> 
		<html:hidden name="ec" property="bannerId"/>
		<html:text name="ec" property="banner" styleClass="select_but" onclick="selectbanner()" readonly="true"/>	
	</td>
</tr>




