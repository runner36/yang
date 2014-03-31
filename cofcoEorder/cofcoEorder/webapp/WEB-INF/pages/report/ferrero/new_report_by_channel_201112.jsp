<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script>
	showQuery_('query_A');
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
	function selectChannel() {
		var form = document.treeForm;
		var v=  window.showModalDialog('${ctx}' + "/tree/tmpSourceData.do?method=channelTree&title=" + encodeURI("region") + "&state=1&first=1&onlyLeaf=&values=" + form.ChannelId.value + "&rand=" + Math.random(), "", "dialogHeight:420px;dialogWidth:420px;scroll=no;help:no;status:no");
		if (v) {
			form.ChannelId.value = v.id;
			form.Channel.value = v.text;
		}
	}
	function selectCity() {
		var form = document.treeForm;
		var v=  openDictTree('${ctx}' + "/tree/tmpSourceData.do?method=cityTree&title=" + encodeURI("region") + "&state=1&first=1&onlyLeaf=2&checkbox=2&values=" + form.cityId.value + "&rand=" + Math.random(), "", "dialogHeight:420px;dialogWidth:420px;scroll=no;help:no;status:no");
		if (v) {
			form.cityId.value = v.id;
			form.city.value = v.text;
		}
	}
</script>
<tr>
	<td width="1%">Channel：</td>
	<td> 
		<html:hidden name="ec" property="ChannelId"/>
		<html:text name="ec" property="Channel" styleClass="select_but" onclick="selectChannel()" readonly="true"/>	
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

