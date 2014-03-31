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
			var form = document.listReportForm;
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
			var form = document.listReportForm;
			//by luobin 2010-09-08 修改city查询条件为多选
			var v=  openDictTree('${ctx}' + "/tree/tmpSourceData.do?method=cityTree&title=" + encodeURI("region") + "&state=1&first=1&onlyLeaf=2&checkbox=2&values=" + form.cityId.value + "&rand=" + Math.random(), "", "dialogHeight:420px;dialogWidth:420px;scroll=no;help:no;status:no");
			//var v=  window.showModalDialog('${ctx}' + "/tree/tmpSourceData.do?method=cityTree&title=" + encodeURI("region") + "&state=1&first=1&onlyLeaf=&values=" + form.cityId.value + "&rand=" + Math.random(), "", "dialogHeight:420px;dialogWidth:420px;scroll=no;help:no;status:no");
			if (v) {
				form.cityId.value = v.id;
				form.city.value = v.text;
			}
		}
</script>
<tr>
	<td >Type：</td>
	<td> 
		<html:text name="ec" property="Type"/>	 
	</td>
	<td >Year：</td>
	<td> 
		<html:select name="ec" property="Year">   
           <html:option value="2010">2010</html:option>  
           <html:option value="2009">2009</html:option>  
           <html:option value="2008">2008</html:option>  
           <html:option value="2007">2007</html:option>  
         </html:select>   	 
	</td>

	<td>City：</td>
	<td> 
	<html:hidden name="ec" property="cityId"/>
		<html:text name="ec" property="city" styleClass="select_but" onclick="selectCity()" readonly="true"/>	
	</td>

	
</tr>

