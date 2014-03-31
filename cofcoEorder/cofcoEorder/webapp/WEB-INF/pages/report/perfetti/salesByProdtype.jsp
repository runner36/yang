<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
	<script>
		function selectOrg() {
			var form = document.treeForm;
			var v = openOrgTree('${ctx}');
			if (v) {
				form.orgSubCode.value = v.subCode;
				form.orgName.value = v.text;
			}
		}
		function onSubmit() {
			var form = document.treeForm;
			if (form.billDate1.value == '') {
				alert('请选择日期');
				return false;
			}
			return true;
		}
		function selectDist() {
		  var form = document.treeForm;
          var v = openDistTree('${ctx}','2','1',form.distId.value);
          if (v) {
	       form.distId.value = v.id;
	       form.distName.value = v.text;
	     }
       }
	</script>
	<tr>
		<td width="1%">组织名称：</td>
		<td width="1%">
			<html:hidden name="ec" property="orgSubCode"/>
			<html:text name="ec" property="orgName" styleClass="select_but" onclick="selectOrg()" readonly="true"/>
		</td>
		<td width="1%">经销商：</td>
	    <td width="1%">
	      <html:hidden name="ec" property="distId"/>
	      <html:text name="ec" property="distName" styleClass="select_but" onclick="selectDist()" readonly="true"/>
	    </td>
		<td width="1%">
			单据日期：
		</td>
		<td>
			<html:text name="ec" property="billDate1" onfocus="setday(this)" readonly="true" styleClass="date_but"/>-<html:text name="ec" property="billDate2" onfocus="setday(this)" readonly="true" styleClass="date_but"/>
		</td>
	</tr>
