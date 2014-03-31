<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script>
function selectDistributor() {
			var form = document.treeForm;
			var v=  window.showModalDialog('${ctx}' + "/tree/tmpSourceData.do?method=distributorTree&title=" + encodeURI("region") + "&state=1&first=1&onlyLeaf=&values=" + form.distId.value + "&rand=" + Math.random(), "", "dialogHeight:420px;dialogWidth:420px;scroll=no;help:no;status:no");
			if (v) {
				form.distId.value = v.id;
				form.distName.value = v.text;
			}
		}
			
		function selectregion() {
			var form = document.treeForm;
			var v=  window.showModalDialog('${ctx}' + "/tree/tmpSourceData.do?method=regionTree&title=" + encodeURI("region") + "&state=1&first=1&onlyLeaf=&values=" + form.regionId.value + "&rand=" + Math.random(), "", "dialogHeight:420px;dialogWidth:420px;scroll=no;help:no;status:no");
			if (v) {
				form.regionId.value = v.id;
				form.region.value = v.text;
			}
		}
		function selectbanner() {
			var form = document.treeForm;
			var v=  window.showModalDialog('${ctx}' + "/tree/tmpSourceData.do?method=bannerTree&title=" + encodeURI("region") + "&state=1&first=1&onlyLeaf=&values=" + form.bannerId.value + "&rand=" + Math.random(), "", "dialogHeight:420px;dialogWidth:420px;scroll=no;help:no;status:no");
			if (v) {
				form.bannerId.value = v.id;
				form.banner.value = v.text;
			}
		}
		function selectCity() {
			var form = document.treeForm;
			//by luobin 2010-09-08 修改city查询条件为多选
			//var v=  window.showModalDialog('${ctx}' + "/tree/tmpSourceData.do?method=cityTree&title=" + encodeURI("region") + "&state=1&first=1&onlyLeaf=&values=" + form.cityId.value + "&rand=" + Math.random(), "", "dialogHeight:420px;dialogWidth:420px;scroll=no;help:no;status:no");
			var v=  openDictTree('${ctx}' + "/tree/tmpSourceData.do?method=cityTree&title=" + encodeURI("region") + "&state=1&first=1&onlyLeaf=2&checkbox=2&values=" + form.cityId.value + "&rand=" + Math.random(), "", "dialogHeight:420px;dialogWidth:420px;scroll=no;help:no;status:no");
			
			if (v) {
				form.cityId.value = v.id;
				form.city.value = v.text;
			}
		}
</script>
<tr>
	<td width="1%">Banner：</td>
	<td> 
		<html:hidden name="ec" property="bannerId"/>
		<html:text name="ec" property="banner" styleClass="select_but" onclick="selectbanner()" readonly="true"/>	
	</td>
	<td width="1%">Region：</td>
	<td> 
		<html:hidden name="ec" property="regionId"/>
		<html:text name="ec" property="region" styleClass="select_but" onclick="selectregion()" readonly="true"/>		 
	</td>
	<td width="1%">City：</td>
	<td> 
	
	<html:hidden name="ec" property="cityId"/>
		<html:text name="ec" property="city" styleClass="select_but" onclick="selectCity()" readonly="true"/>	
	</td>
	<td width="1%">Distributor：</td>
	<td>
		<html:hidden name="ec" property="distId"/>
		<html:text name="ec" property="distName" styleClass="select_but" onclick="selectDistributor()" readonly="true"/>
	</td>
</tr>

