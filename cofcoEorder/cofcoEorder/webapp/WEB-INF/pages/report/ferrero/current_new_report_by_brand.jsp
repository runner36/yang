<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<tr>
	<td>month：</td>
	<td> <html:text name="ec" property="yyyyMm" maxlength="7" styleClass="date_but" onclick="showTable(this)" readonly="true"/></td>
	 <td>City：</td>
	<td><html:hidden name="ec" property="orgId" /><html:hidden name="ec" property="subCode" />
		<html:text name="ec" property="orgName" maxlength="20" styleClass="select_but" onclick="selectorg()" readonly="true" />
	 </td>
	 <td>Banner:	</td>
	 <td><html:hidden name="ec" property="bannerIds" />
		<html:text name="ec" property="bannerNames" maxlength="20" styleClass="select_but" onclick="selectBannerIds()" readonly="true" /></td>
</tr>
<script>
if(document.treeForm.yyyyMm){
	document.treeForm.yyyyMm.value = document.treeForm.yyyyMm.value.substring(0,7)
}
 
//城市树
function selectorg(){
var v = openOrgTree('${ctx}',0,0,document.treeForm.orgId.value);
if(v){
	//allPrpos(v)
	
	document.treeForm.orgId.value=v.id;
	document.treeForm.subCode.value=v.subCode;
	document.treeForm.orgName.value=v.text;
}
}

//门店组织树
function selectBannerIds(){
if(document.treeForm.bannerNames.disabled!="disabled"){
	var v = openDictTree('${ctx}','storeCorp',2,2,document.treeForm.bannerIds.value);
	if(v){
	//allPrpos(v)
	document.treeForm.bannerIds.value = v.id;
	document.treeForm.bannerNames.value=v.text;
}
}
}

</script>
