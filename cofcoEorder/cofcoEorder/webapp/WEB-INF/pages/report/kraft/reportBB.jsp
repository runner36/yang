<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
<link rel="stylesheet" type="text/css" href="${ctx}/widgets/extremecomponents/extremecomponents.css">
<script language="javaScript" src="${ctx}/scripts/common.js"></script>
<script>
	function onSubmit() {
		var ge_billDate = treeForm.ge_billDate.value;
		var le_billDate = treeForm.le_billDate.value;
		if(ge_billDate==''||le_billDate==null||le_billDate==''||le_billDate==null){
			alert("请选择日期！")
			return;
		}
		if(_compareDate(le_billDate,ge_billDate)>0){
			alert("开始日期不能大于结束日期！")
			return ;
		}
		if(_compareDate(_getCurrentDate(),le_billDate)>=0){
			alert("请选择今天以前的日期！")
			return ;
		}
		return true;
	}
	///组织  
	function selectOrg() {
		var v = openOrgTree('${ctx}','',2,treeForm.orgId.value);
			treeForm.distId.value ='';
			treeForm.distName.value = '';
		if (v) {
			treeForm.orgId.value = v.id;
			treeForm.orgName.value = v.text;
		}
	}
	//经销商
	function selectDist() {
		//var v = openCheckboxDistTree('${ctx}',treeForm.orgId.value,treeForm.distId.value);
		var v = openDistTreeByOrg('${ctx}','',2,treeForm.distId.value,'',1);
		if (v) {
			treeForm.distId.value = v.id;
			treeForm.distName.value = v.text;
		}
	}
	//客户组织
	function selectDict(){
		var v = openDictTree('${ctx}','storeCorp', '',2,treeForm.corpId.value);
		if (v) {
			treeForm.corpId.value = v.id;
			treeForm.corpName.value = v.text;
		}
	}
	//客户类型
	function selectType(){
		var v = openDictTree('${ctx}','storeType', '',2,treeForm.typeId.value);
		if (v) {
			treeForm.typeId.value = v.id;
			treeForm.typeName.value = v.text;
		}
	}
	//客户属性
	function selectChannel(){
	    var v = openDictTree('${ctx}','storeChannel', '',2,treeForm.channelId.value);
		if (v) {
			treeForm.channelId.value = v.id;
			treeForm.channelName.value = v.text;
		}
	}
</script>
  		
<tr>
	<td style="text-align: right;"><font color="#FF0000">＊</font>开始日期：</td>
	<td><html:text name="ec" property="ge_billDate" onfocus="setday(this)" readonly="true" styleClass="date_but"/></td>
	<td style="text-align: right;"><font color="#FF0000">＊</font>结束日期：</td>
	<td colspan="3">
		<html:text name="ec" property="le_billDate" onfocus="setday(this)" readonly="true" styleClass="date_but"/>
		<font color="#FF0000">＊(请选择今天以前的日期！)</font>
	</td>
</tr>
<tr>
	<td width="1%" style="text-align: right;">管理组织：</td>
	<td >
		<html:hidden name="ec" property="orgId"/>
		<html:text name="ec" property="orgName" styleClass="select_but" onclick="selectOrg()" readonly="true"/>
	</td>
	<td width="1%" style="text-align: right;">经销商名称：</td>
	<td colspan="3">                     
		<html:hidden name="ec" property="distId" />
		<html:text name="ec" property="distName" styleClass="select_but" onclick="selectDist()" readonly="true"/>
	</td>
	<%--
	<td width="1%" style="text-align: right;">组织架构日期：</td>
	<td>
		<html:text name="ec" property="orgDate" onfocus="setday(this)" readonly="true" styleClass="date_but"/>
	</td>
	--%>
</tr>
<tr>
	 <td width="1%" style="text-align: right;">客户组织：</td>
	 <td >
	     <html:hidden name="ec" property="corpId" />
	     <html:text name="ec" property="corpName" styleClass="select_but" onclick="selectDict()" readonly="true" />
	 </td>
	 <td width="1%" style="text-align: right;">客户类型：</td>
	 <td >
	     <html:hidden name="ec" property="typeId"/>
	     <html:text name="ec" property="typeName" styleClass="select_but" onclick="selectType()" readonly="true" />
	 </td>
	 <td width="1%" style="text-align: right;">客户属性：</td>
	 <td >
	     <html:hidden name="ec" property="channelId"/>
	     <html:text name="ec" property="channelName" styleClass="select_but" onclick="selectChannel()" readonly="true" />
	 </td>
</tr>