<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script language="javaScript" src="${ctx}/scripts/jquery-1.2.6.min.js"></script>


<script>
	
	function selectDist() {
		var v = openDistOrgTree('${ctx}','1','1',listReportForm.p_distId.value);
		if (v) {
			listReportForm.p_distId.value = v.id;
			listReportForm.p_distName.value = v.text;
		}
	}

	$(document).ready(function(){
		var isfirst;
		isfirst=${param.first}+'';
		if(isfirst==null){
			isfirst=0;
		}
		if(isfirst == 1){
			document.all.p_distId.value = window.parent.header.p_distId || '';
			document.all.p_distName.value = window.parent.header.p_distName || '';
			document.all.p_prodName.value = window.parent.header.p_prodName || '';
			document.all.p_billDate1.value = window.parent.header.p_billDate1 || '';
			document.all.p_billDate2.value = window.parent.header.p_billDate2 || '';
		}
		/*
		$('tr',$('.tableBody')).each(function(i,n){
			var p_distId = $('td',$(n)).eq(19).text();
			var p_distName = $('td',$(n)).eq(1).text();
			var p_prodName = $('td',$(n)).eq(3).text();
			var p_billDate1 = $('td',$(n)).eq(4).text().substring(0,10);
			var p_billDate2 = addDate(4,30,p_billDate1);
			
			
			
			$(n).css('cursor','pointer').click(function(){
				window.parent.header.p_distId = p_distId;
				window.parent.header.p_distName = p_distName;
				window.parent.header.p_prodName = p_prodName;
				window.parent.header.p_billDate1 = p_billDate1;
				window.parent.header.p_billDate2 = p_billDate2;
			});

			});
		*/
		//encodeURI('/DMS/server/dmsProdMapping.do?method=edit&clientId=2&clientName=廊坊市今朝商贸有限公司(lfsjzsm)&distProdCode=6907376101932&distProdBarCode=6907376101932&distProdName=娇爽纤巧棉柔安睡夜用8\'s&distProdUnit=包&targetProdCode=13716114&targetProdName=娇爽超吸收安睡夜用纤巧丝感棉柔8片&targetProdUnit=3&rate=0.04166667&mappingId=32&targetUnitId=&targetBarCode=')
	});
</script>
 	    <tr>
			<td width="1%">${mr['page.common.distName']}</td>
			<td>
				<html:hidden name="ec" property="p_distId"/>
	  			<html:text name="ec" property="p_distName" styleClass="select_but" onclick="selectDist()" readonly="true"/>
	  		</td>
			<td width="1%">${mr['page.common.distProdName']}</td>
			<td><html:text name="ec" property="p_prodName"/></td>
			<td width="1%">${mr['page.common.isValid']}</td>
			<td width="1%">
				<html:select name="ec" property="p_state" style="width: 105px">
					<html:option value="1">${mr['page.common.yes']}</html:option>
					<html:option value="0">${mr['page.common.no']}</html:option>
				</html:select>
			</td>
		</tr>
	    <tr>
			<td width="1%">${mr['page.common.billDate']}</td>
			<td>
				<html:text name="ec" property="p_billDate1" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>-<html:text name="ec" property="p_billDate2" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>
			</td>
			<td width="1%">${mr['page.common.createdTime']}</td>
			<td>
				<html:text name="ec" property="p_createTime1" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>-<html:text name="ec" property="p_createTime2" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>
			</td>
			<td width="1%">${mr['page.common.updatedTime']}</td>
			<td>
				<html:text name="ec" property="p_updateTime1" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>-<html:text name="ec" property="p_updateTime2" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>
			</td>
		</tr>
