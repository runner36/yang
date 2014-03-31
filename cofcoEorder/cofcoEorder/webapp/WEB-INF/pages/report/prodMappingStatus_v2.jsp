<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="java.util.Date" %>
<%@page import="java.text.SimpleDateFormat" %>

<%
request.setAttribute("date1",request.getParameter("start_date")==null?new SimpleDateFormat("yyyy-MM").format(new Date()):request.getParameter("start_date"));
request.setAttribute("date2",request.getParameter("end_date")==null?new SimpleDateFormat("yyyy-MM").format(new Date()):request.getParameter("end_date"));
%>
<%@ include file="/commons/taglibs.jsp"%>
<script language="javaScript" src="${ctx}/scripts/jquery-1.2.6.min.js"></script>

<script>
	function selectDist() {
	  var v = openDistTree('${ctx}','2','1',listReportForm.distIds.value);
	  if (v) {
		 listReportForm.distIds.value = v.id;
		 listReportForm.distName.value = v.text;
		}
	}
	
	function onSubmit() {
		if(listReportForm.distName.value=='' &&  listReportForm.distCode.value=='' ){
			alert('请选择经销商或填写经销商编码!');
			return false;
		}
		if(listReportForm.start_date.value=='' || listReportForm.end_date.value==''){
			alert('请选择期间开始与结束日期!');
			return false;
		}
	  return true;
	}
	

	Date.prototype.toString = function(){ 
		return this.getFullYear()+"-"+(this.getMonth()+1)+ "-"+this.getDate(); 
	};
	
	String.prototype.trim = function(){ 
		return this.replace(/\s|(&#160;)/g,""); 
	};
	
	String.prototype.processingSQLInjection = function(){
		return this.replace(/\[/g,"[[]");
	};
	
	function cleanFormValue(){
		
		document.listReportForm.distIds.value = '';
		document.listReportForm.distName.value = '';
		document.listReportForm.distCode.value = '';
		document.listReportForm.target_prod_name.value = '';
		document.listReportForm.prod_name.value = '';
		
		document.listReportForm.start_date.value = '';
		document.listReportForm.end_date.value = '';
		document.listReportForm.is_map.value = '';
		document.listReportForm.volume.value = '';
	}
	
	function openWindow(action,inputName,inputValue){
		var childWindow=window.open("${ctx}/commons/openPage.jsp");
		childWindow.document.write("");//保证汉字能带到子页面
		var childWindowForm=childWindow.document.createElement("form");
		childWindow.document.appendChild(childWindowForm);
		childWindowForm.setAttribute("name","childWindowForm");
		childWindowForm.setAttribute("action",action);
		childWindowForm.setAttribute("method","post");
		/* var input=childWindow.document.createElement("input");
		input.setAttribute("name",inputName);
		input.setAttribute("value",inputValue);
		childWindowForm.appendChild(input); */
		childWindowForm.submit();
	}
	
	var addAmountRateIndex=0;
	var arrivalQtyRateIndex=0;
	var distCodeIndex =0;
	var prodCodeIndex = 0;
	var prodNameIndex = 0;
	var clientCodeIndex = 0;
	
	function initIndex(){
		$(".tableHeader").each(function(index,object){
			if($(object).text()=='加价率'){
				addAmountRateIndex = index;
			}else if($(object).text()=='到货/发货'){
				arrivalQtyRateIndex = index;
			}else if($(object).text()=='经销商编码'){
				distCodeIndex = index;
			}else if($(object).text()=='经销商产品编码'){
				prodCodeIndex = index;
			}else if($(object).text()=='经销商产品名称'){
				prodNameIndex = index;
			}else if($(object).text()=='客户端编码'){
				clientCodeIndex = index;
			}
		});
	}
	
	function handlerTd(startDate,monthLastDay,className){
		$(className).each(function(j,m){
			var codeTd;
			var distCode;
			var prodCode;
			var prodName;
			var clientCode;
			$(m).find("td").each(function(index,object){
				if (index == distCodeIndex) { //21 更新算法之前的上一版本索引值
					distCode=$(object).text();
				}
				if (index == prodCodeIndex) { //0
					prodCode=$(object).text();
				}
				if (index == prodNameIndex) { //1
					prodName=$(object).text();
				
					//if($(object).text().trim()!='' && $(object).text().trim()!=' ' &&$(object).text() != '&#160;'){
						codeTd = $(object);
						codeTd.css('text-decoration','underline').css('cursor','pointer').click(function(){
							openWindow("${ctx}/server/dmsProdMapping.do?method=list&first=1&dist_code="+distCode+"&distProdCode_="+prodCode,"distProdName_","");
						});
					//}
				}
				
				if (index == clientCodeIndex) {//23
					clientCode=$(object).text();
				}
				
				if(index==addAmountRateIndex){ //加价率 10
					//if($(object).text().trim()!='' && $(object).text().trim()!=' ' &&$(object).text() != '&#160;'){
						codeTd = $(object);
						codeTd.css('text-decoration','underline').css('cursor','pointer').click(function(){
							openWindow("${ctx}/report/listReport.do?reportName=server/checkSaleList&distCode="+distCode+"&billDate1="+startDate+"&billDate2="+monthLastDay+"&state=1&prodCode="+prodCode+"&ec_crd=1000&colIds=&tableId=checkSaleList","prodName","");
						});
					//}
				}
				
				if(index==arrivalQtyRateIndex){ //到货比率 14
					//if($(object).text().trim()!='' && $(object).text().trim()!=' ' &&$(object).text() != '&#160;'){
						codeTd = $(object);
						codeTd.css('text-decoration','underline').css('cursor','pointer').click(function(){
							openWindow("${ctx}/report/listReport.do?reportName=prodMappingStatusArrivalRate&distCode="+distCode+"&startDate="+startDate+"&endDate="+monthLastDay+"&prodCode="+prodCode+"&ec_crd=1000&colIds=&tableId=prodMappingStatusArrivalRate","prodName","");
						});
					//}
				}
			});
		});
	}
	
	$(document).ready(function(){
		
		initIndex();
		
		var startDate="${date1}";
		var endDate	= "${date2}"; 
		//var endDate	= new Date("${date2}".replace("-", "/") ); 
		
		var monthNextFirstDay = endDate; 
		var monthLastDay = endDate; 
/* 		var monthNextFirstDay = new Date(endDate.getYear(),endDate.getMonth()+1,1); 
		var monthLastDay = new Date(monthNextFirstDay-86400000);  */
		
		//ectable奇数行的样式名称
		var ectableTrClassName = ".odd";
		handlerTd(startDate,monthLastDay,ectableTrClassName);

		//ectable偶数行的样式名称
		ectableTrClassName = ".even";
		handlerTd(startDate,monthLastDay,ectableTrClassName);
		
	});
</script>
 	    <tr>
			<td width="1%">经销商：</td>
			<td width="1%">
				<html:hidden property="distIds"/>
	  			<html:text property="distName" styleClass="select_but" onclick="selectDist()" readonly="true" style="width:210px"/>
	  		</td>
	  		<td width="1%">经销商编码：</td>
			<td width="1%"><html:text property="distCode"/></td>
			<td width="1%">产品标准名称：</td>
			<td width="1%"><html:text property="target_prod_name"/></td>
			<td width="1%">经销商产品名称：</td>
			<td width="1%"><html:text property="prod_name"/></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td width="1%">
				数据期间：
			</td>
			<td >
				<html:text styleId="start_date" property="start_date" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>~
				<html:text styleId="end_date" property="end_date" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>
			</td>
			<td width="1%">产品状态：</td>
			<td >
				<html:select property="is_map">
					<html:option value="">${mr['page.common.button.pleaseSelect']}</html:option>
					<html:option value=" AND mappingCreateTime IS NOT NULL ">${mr['page.common.hasBeenMapping']}</html:option><!-- 已匹配 -->
					<html:option value=" AND mappingCreateTime IS NULL AND distProdCode IS NOT NULL ">未匹配(经销商)</html:option><!-- 经销商产品未匹配 -->
					<html:option value=" AND mappingCreateTime IS NULL AND distProdCode IS NULL ">未匹配(发货)</html:option><!-- 发货产品未匹配  -->
				</html:select>
			</td>
			<td width="1%">有无货量：</td>
			<td width="1%">
				<html:select property="volume">
					<html:option value="0">有货量</html:option>
					<html:option value="2">全部</html:option>
					<html:option value="1">无货量</html:option>
				</html:select>
			</td>
			<td><input type="hidden" name="prodName" />&nbsp;</td>
		</tr>
<script>
</script>
