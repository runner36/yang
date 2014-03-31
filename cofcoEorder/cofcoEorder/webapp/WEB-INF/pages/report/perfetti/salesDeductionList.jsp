<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/dms.js"></script>
		<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>
<tr>
	<td >
		流水号：
	</td>
	<td width="1%">
		<html:text name="ec" property="billsNum"/>
	</td>
	<td >
		回传日期：
	</td>
	<td >
		 <html:text name="ec" property="backDate1" onfocus="setday(this)" readonly="true" styleClass="date_but"/>-<html:text name="ec" property="backDate2" onfocus="setday(this)" readonly="true" styleClass="date_but"/>
	
	</td>
	
</tr>
<tr>
	<td width="1%">
		回传状态：
	</td>
	<td>
		<html:select name="ec" property="isLoad"> 
			<html:option value=""></html:option>
			<html:option value="0">未回传</html:option>
			<html:option value="1">已回传</html:option>
		</html:select> 
	</td>
	<td width="1%">
		录入日期：
	</td>
	<td>
	  <html:text name="ec" property="billDate1" onfocus="setday(this)" readonly="true" styleClass="date_but"/>-<html:text name="ec" property="billDate2" onfocus="setday(this)" readonly="true" styleClass="date_but"/>
	</td>
</tr>
<tr>	
	<td width="1%">
		PVMC产品编码：
	</td>
	<td >
		<html:text name="ec" property="proCode"/>
	</td>
	<td width="1%">
		PVMC产品名称：
	</td>
	<td>
		<html:text name="ec" property="proName"/>
	</td>
</tr>
<tr>	
	<td width="1%">
		PVMC客户编码：
	</td>
	<td >
		<html:text name="ec" property="storeCode"/>
	</td>
	<td width="1%">
		PVMC客户名称：
	</td>
	<td>
		<html:text name="ec" property="storeName"/>
	</td>
</tr>

<tr>	
	<td width="1%">
		经销商编码：
	</td>
	<td >
		<html:text name="ec" property="distCode"/>
	</td>
	<td width="1%">
		经销商名称：
	</td>
	<td>
		<html:text name="ec" property="distName"/>
	</td>
</tr>
