<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
	<script>
		function onSubmit() {
			var form = document.listReportForm;
			if (form.billDate1.value == '') {
				alert('请选择日期');
				return false;
			}
			return true;
		}
	</script>
	<tr>
		<td width="1%">
			产品名称：
		</td>
		<td width="1%">
			<html:text name="ec" property="prodName"/>
		</td>
		<td width="1%">
			销售日期：
		</td>
		<td>
			<html:text name="ec" property="billDate1" onfocus="setday(this)" readonly="true" styleClass="date_but"/>-<html:text name="ec" property="billDate2" onfocus="setday(this)" readonly="true" styleClass="date_but"/>
		</td>
	</tr>
