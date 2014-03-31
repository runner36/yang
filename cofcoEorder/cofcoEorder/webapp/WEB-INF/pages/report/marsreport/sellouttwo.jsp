<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>
	<script>
		function selectOrg() {
			var form = document.listReportForm;
			var v = openOrgTree('${ctx}');
			if (v) {
				form.orgSubCode.value = v.subCode;
				form.orgName.value = v.text;
			}
		}
		function onSubmit() {
			return checkDate();
		}
		 function checkDate() {
			var form = document.listReportForm;
			var month1= form.month1.value;
			
			var month2=form.month2.value;
			var weeks1=form.week1.value;
			var weeks2=form.week2.value;
			
			var year=form.year1.value;
			if(year==null || year==''){
				alert("请选择年!");
				return false;
			}
			if(parseInt(month1)>parseInt(month2)){
			  alert("开始段不能大于结束段"); 
			  return false;
			}
			if(form.month1.value==''||form.month2.value==''||form.week1.value==''||form.week2.value==''){
			   	alert("开始结束段、开始结束周不能为空！");
			  	return false;
			  }
			  if(form.month1.value==form.month2.value)
			{
			   if(form.week1.value>form.week2.value)
			   {
				   alert("如果段相等，则开始周必须小于结束周");
				   return false; 
			   } 
			}
			return true;
		}
		function selectDist() {
		  var form = document.listReportForm;
          var v = openDistTree('${ctx}','2','1',form.distId.value);
          if (v) {
	       form.distId.value = v.id;
	       form.distName.value = v.text;
	     }
       }
		
		function selectClient() {
			var form = document.listReportForm;
			var v = openClientTree('${ctx}', '1');
			if (v) {
				form.clientId.value = v.id;
				form.clientName.value = v.text;
			}
		}
	</script>
	<tr>
		<td id="year1" width="1%">
			<html:select name="ec" property="year1" >
				<html:option value="">请选择</html:option>
            	<html:option value="2008">2008</html:option>
            	<html:option value="2009">2009</html:option>
            	<html:option value="2010">2010</html:option>
            	<html:option value="2011">2011</html:option>
            	<html:option value="2012">2012</html:option>
            	<html:option value="2013">2013</html:option>
            	<html:option value="2014">2014</html:option>
            	<html:option value="2015">2015</html:option>
            	<html:option value="2016">2016</html:option>
            	<html:option value="2017">2017</html:option>
            	<html:option value="2018">2018</html:option>
            	<html:option value="2019">2019</html:option>
            	<html:option value="2020">2020</html:option>
			</html:select> 
		</td>
		<td width="1%">年</td>
		<td id="month1" width="1%" style="text-align:right">开始段：</td>
		<td width="1%">
			<html:select name="ec" property="month1" >
				<html:option value="">请选择</html:option>
            	<html:option value="1">1</html:option>
            	<html:option value="2">2</html:option>
            	<html:option value="3">3</html:option>
            	<html:option value="4">4</html:option>
            	<html:option value="5">5</html:option>
            	<html:option value="6">6</html:option>
            	<html:option value="7">7</html:option>
            	<html:option value="8">8</html:option>
            	<html:option value="9">9</html:option>
            	<html:option value="10">10</html:option>
            	<html:option value="11">11</html:option>
            	<html:option value="12">12</html:option>
                <html:option value="13">13</html:option>
			</html:select> 
		<td id="week1" width="1%" style="text-align:right">开始周：</td>
		<td width="1%">
			<html:select name="ec" property="week1" >
			    <html:option value="">请选择</html:option>
            	<html:option value="1">1</html:option>
            	<html:option value="2">2</html:option>
            	<html:option value="3">3</html:option>
            	<html:option value="4">4</html:option>
			</html:select> 
		</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td id="month2" width="1%" style="text-align:right">结束段：</td>
		<td width="1%">
			<html:select name="ec" property="month2" >
				<html:option value="">请选择</html:option>
            	<html:option value="1">1</html:option>
            	<html:option value="2">2</html:option>
            	<html:option value="3">3</html:option>
            	<html:option value="4">4</html:option>
            	<html:option value="5">5</html:option>
            	<html:option value="6">6</html:option>
            	<html:option value="7">7</html:option>
            	<html:option value="8">8</html:option>
            	<html:option value="9">9</html:option>
            	<html:option value="10">10</html:option>
            	<html:option value="11">11</html:option>
            	<html:option value="12">12</html:option>
                <html:option value="13">13</html:option>
			</html:select> 
		<td id="week2" width="1%" style="text-align:right">结束周：</td>
		<td width="1%">
			<html:select name="ec" property="week2" >
			    <html:option value="">请选择</html:option>
            	<html:option value="1">1</html:option>
            	<html:option value="2">2</html:option>
            	<html:option value="3">3</html:option>
            	<html:option value="4">4</html:option>
			</html:select> 
		</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		
		<td width="1%" style="text-align:right">组织名称：</td>
		<td width="1%">
			<html:hidden name="ec" property="orgSubCode"/>
			<html:text name="ec" property="orgName" styleClass="select_but" onclick="selectOrg()" readonly="true"/>
		</td>
	
		<td width="1%" style="text-align:right">经销商：</td>
	    <td width="1%">
	      <html:hidden name="ec" property="distId"/>
	      <html:text name="ec" property="distName" styleClass="select_but" onclick="selectDist()" readonly="true"/>
	    </td>
	    <td width="1%" style="text-align:right">经销商编码：</td>
	    <td>
	      <html:text name="ec" property="distCode" />
	    </td>
		 <td width="1%" style="text-align:right">MARS产品编码：</td>
	    <td>
	      <html:text name="ec" property="prodCode" />
	    </td>
	</tr>
	<tr>
		<td width="1%">客户端名称：</td>
		<td width="1%">
			<html:hidden property="clientId"/>
			<html:text property="clientName" styleClass="select_but" onclick="selectClient()" readonly="true"/>
		</td>
		<td width="1%"></td>
		<td></td>
		<td width="1%"></td>
		<td></td>
		<td width="1%"></td>
		<td></td>
	</tr>


