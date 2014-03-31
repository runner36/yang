<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>
<script>
 function selectOrg(orgName, id, name) {
  var v = openOrgTree('${ctx}', orgName);
  if (v) {
	id.value = v.id;
	name.value = v.text;
	}
}
function selectDist() {
  var v = openDistTree('${ctx}', '1');
  if (v) {
	 treeForm.distId.value = v.id;
	 treeForm.distName.value = v.text;
	}
}
function onSubmit() {
  return(check(treeForm.beginDate.value,treeForm.endDate.value)) ;
}
//验证日期（判断结束日期是否大于开始日期）日期格式为YY—MM—DD   
function check(startTime,endTime){               
   if(startTime.length<=0 || endTime.length<=0)
   {
     alert("开始日期或结束日期不能为空"); 
     return false; 
   }
   if(startTime.length>0 && endTime.length>0){ 
       var startTmp=startTime.split("-");
       var endTmp=endTime.split("-");
       var sd=new Date(startTmp[0],startTmp[1],startTmp[2]);
       var ed=new Date(endTmp[0],endTmp[1],endTmp[2]); 
       if(sd.getTime()>ed.getTime()){
          alert("开始日期不能大于结束日期");  
         return false;
       }
    }
  return true;
}   
</script>
<tr>
	<td width="1%">组织机构：</td>
	<td width="1%">
	   <html:hidden name="ec" property="orgId"/>
	   <html:text name="ec" property="orgName" readonly="true" styleClass="select_but" onclick="selectOrg('orgTree',treeForm.orgId,treeForm.orgName)"/>
	</td>
	<td width="1%">经销商：</td>
	<td >
	  <html:hidden name="ec" property="distId"/>
	  <html:text name="ec" property="distName" styleClass="select_but" onclick="selectDist()" readonly="true"/>
	 </td>
</tr>
<tr>
	<td width="1%">销量计算期间：</td>
	<td width="1%"><html:text name="ec" property="beginDate" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/></td>
	<td width="1%">-</td>
	<td><html:text name="ec" property="endDate" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/></td>
</tr>
