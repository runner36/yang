<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>${mr['page.customize.title.prodMapMaint']}</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/dms.js"></script>
		<script type="text/javascript" src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/jquery-1.2.6.min.js"></script>
		<script>
		function sel(selid,selText)
		{
			 var sel=document.getElementById(selid);
			 for(var i=0;i<sel.length;i++)
			 {
			  if(sel[i].text==selText)
			   sel.selectedIndex=i;
			 }
		}
		function save() {
			if(validateDmsProdMappingForm(dmsProdMappingForm))
			{
				dmsProdMappingForm.submit();
			}
		}
		function back() {
			window.history.go(-1);
		}
		function list() {
			if ('${param.flag}' == '1') {
				location = 'dmsProdMapping.do?method=noMappingList';
			}
			else {
				location = 'dmsProdMapping.do?method=list';
			}
		}
		function selectClient() {
			var v = openClientTree('${ctx}');
			if (v) {
				dmsProdMappingForm.clientId.value = v.id;
				dmsProdMappingForm.clientName.value = v.text;
			}
		}
		var xmlhttp;
		function createXMLHttpRequest() {
			try{
				xmlhttp=new ActiveXObject("Msxml2.XMLHTTP");
			}
			catch (e) {
				try{
					xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
				}
				catch(e) {
					try {
						xmlhttp=new XMLHttpRequest();
					}
					catch(e) {
						alert("failure");
					}
				}
			}
		}
		function getProdName() {
			var form = dmsProdMappingForm;
			var targetProdCode = form.targetProdCode.value;
			if (targetProdCode != '') {
				createXMLHttpRequest();
				xmlhttp.open("post", "${ctx}/server/dmsProdMapping.do?method=getProdName&targetProdCode=" + targetProdCode, true);
				xmlhttp.onreadystatechange = function() {
					if(xmlhttp.readyState == 4) {
						if(xmlhttp.status == 200) {
						if(xmlhttp.responseText!=null&&xmlhttp.responseText!='')
						{
							getProdUnit();
							form.targetProdName.value = xmlhttp.responseText;
							
						}else{
								alert("${mr['page.common.mess.supplyProdCodeNotFound']}！");
								form.targetProdName.value = '';
								form.targetProdCode.value = '';
								clearValue();
							}
						}
						else {
							alert("${mr['page.common.mess.supplyProdCodeNotFound']}！")
							form.targetProdName.value = '';
							form.targetProdCode.value = '';
							clearValue();
						}
					}
				};
				xmlhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
				xmlhttp.send(null);
			}
		}
		function clearValue()
		{
			sel("convId","");
			$("#convMsg").empty();
			$("#convId").empty();
  			$("#convId").append("<option value=''></option>");
  			$("#allMsg").empty();
		}
		function getProdRate() {
			var form = dmsProdMappingForm;
			var targetProdCode = form.targetProdCode.value;
			var targetProdUnit =form.targetProdUnit.value;
			if(targetProdUnit!='')
			{
				if (targetProdCode!= '') {
					createXMLHttpRequest();
					xmlhttp.open("post", "${ctx}/server/dmsProdMapping.do?method=getProdRate&targetProdCode=" + targetProdCode+"&targetProdUnit="+targetProdUnit, true);
					xmlhttp.onreadystatechange = function() {
						if(xmlhttp.readyState == 4) {
							if(xmlhttp.status == 200) {
								if(xmlhttp.responseText!=null&&xmlhttp.responseText!='')
								{
								  form.rate.value=xmlhttp.responseText
								}
								else
								{
								 	form.rate.value="";
								}
							}
						}
					};
					xmlhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
					xmlhttp.send(null);
				}else
				{
					alert("${mr['page.common.mess.fillOutSupplyProdCode']}！");
					return false;
				}
			}else
			{
				form.rate.value="";
			}
		}
		function getProdUnit()
		{
			if ($("#targetProdCode").val()!='') {
				$.ajax({
					   type: "POST",
					   url: '${ctx}'+"/server/dmsProdMapping.do?method=getProdJsonConvList",
					   data: "prodCode="+$("#targetProdCode").val(),
					   success: function(msg){
					   		 $("#convId").empty();
					   		 $("#convId").append("<option value=''></option>");
					   		var obj = eval('(' + msg + ')'); 
						 		if(obj==null || obj==""  || obj=='null' || obj.length==0 )
						 		{
						 			$("#convMsg").empty();
						 			$("#allMsg").empty();
						 			$("#convMsg").append("<span style='color:red;'>未找到转换关系,请先维护单位转换关系！</span>");
						 			return false;
						 		}
						 		else
						 		{
						 			$("#convMsg").empty();
						 			 getall(); 
						 			 for(i =0;i<obj.length;i++)
						 			 {
						 			 	$("#convId").append("<option value='"+obj[i][0]+"'>"+obj[i][1]+"</option>")
						 			 }   
						 			 
						 		}
					   }
					});	
			}
			else
			{
				$("#convMsg").empty();
				$("#convId").empty();
			    $("#convId").append("<option value=''></option>");
			}
		}
		function changeConv()
		{
			if($("#targetProdCode").val()!="")
			{
				if($("#convId").val()!="")
				{
					$.ajax({
						   type: "POST",
						   url: '${ctx}'+"/server/dmsProdMapping.do?method=getJsonConvList",
						   data: "prodCode="+$("#targetProdCode").val()+"&unitid="+$("#convId").val(),
						   success: function(msg){
						   		var obj = eval('(' + msg + ')'); 
						 		if(obj==null || obj==""  || obj=='null' || obj.length==0 )
						 		{
						 			$("#convMsg").empty();
						 			$("#convMsg").append("<span style='color:red;'>未找到转换关系</span> ");
						 			return false;
						 		}
						 		else
						 		{
						 			$("#convMsg").empty();
						 			 for(i =0;i<obj.length;i++)
						 			 {
						 			 	$("#convMsg").append("<span style='color:red;' >"+obj[i][0]+"</span> <br/>");
						 			 }   
						 			
						 		}
						   }
						});	
					}else
					{
						$("#convMsg").empty();
					}
			}
			else
			{
				alert("${mr['page.common.mess.fillOutSupplyProdCode']}");
				sel("convId","")
				return false;
			}
		}
		function getall()
		{
			$.ajax({
			   type: "POST",
			   url: '${ctx}'+"/server/dmsProdMapping.do?method=getProdJsonConvList1",
			   data: "prodCode="+$("#targetProdCode").val(),
			   success: function(msg){
			   		var obj = eval('(' + msg + ')'); 
			 		if(obj==null || obj==""  || obj=='null' || obj.length==0 )
			 		{
			 			return false;
			 		}
			 		else
			 		{
			 			$("#allMsg").empty();
			 			 for(i =0;i<obj.length;i++)
			 			 {
			 			    	$("#allMsg").append("<span style='color:red;' >"+obj[i][0]+obj[i][1]+"="+obj[i][2]+obj[i][3]+"</span> <br/>");
			 			 }   
			 			 
			 		}
			   }
			});	
		
		}
		
		</script>
	</head>
	<body onload="WindowSollAuto()" onresize="WindowSollAuto()">
		<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/server/dmsProdMapping.do?method=save" method="post">
			<html:hidden property="activePId" />
			<html:hidden property="activeId"/>
			<html:hidden property="flag" />
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						${mr['page.customize.title.prodMapMaint']}
					</h4>
					<div class='MenuList'>
						<a href="javascript:save();">${mr['page.common.button.save']}</a>
						<a href="javascript:list();">${mr['page.common.button.cancel']}</a>
					</div>
					<span class="right"></span>
				</div>
				<div class="bosom_side">
					<div class="casing">
						<div class="caput">
							<span class="left"></span><span  class="right"></span>
						</div>
						<div class="viscera" id="SollAuto">
							<div class="sheet_div">
								<table class="list_add">
									 <tr>
										<td class="formTable">${mr['page.common.clientName']}<font color="#FF0000">＊</font></td>
										<td align="left">
											<html:hidden property="clientId"/><html:text property="clientName" maxlength="20" style="background-color: #F0F0F0" readonly="true"/>
										</td>
										<td class="formTable"></td>
										<td align="left">
										</td>
									</tr>
								    <tr>
										<td class="formTable">${mr['page.common.distProdCode']}<font color="#FF0000">＊</font></td>
										<td align="left"><html:text property="distProdCode"  readonly="true" style="background-color: #F0F0F0" maxlength="20"  /></td>
										<td class="formTable">${mr['page.common.pordStandardCode']}<font color="#FF0000">＊</font></td>
										<td align="left"><html:text property="targetProdCode" styleId="targetProdCode" maxlength="50" onchange="getProdName();"/></td>
								    </tr>
								    <tr>
										<td class="formTable">${mr['page.common.distProdName']}</td>
										<td align="left"><html:text property="distProdName" readonly="true" style="background-color: #F0F0F0"/></td>
										<td class="formTable">${mr['page.common.pordStandardName']}</td>
										<td align="left"><html:text property="targetProdName" readonly="true" style="background-color: #F0F0F0"/></td>
									 </tr>
									  <tr>
										<td class="formTable">${mr['page.common.productBarcode']}</td>
										<td align="left"><html:text property="distProdBarCode" readonly="true" style="background-color: #F0F0F0"/></td>
									   	<td class="formTable">${mr['page.common.prodStandardBarcode']}</td>
										<td align="left"><html:text property="targetBarCode" readonly="true" style="background-color: #F0F0F0"/></td>
									 </tr>
								     <tr>
										<td class="formTable">${mr['page.common.distProdUnit']}<font color="#FF0000">＊</font></td>
										<td align="left"><html:text property="distProdUnit" readonly="true" style="background-color: #F0F0F0" /></td>
									   	<td class="formTable">${mr['page.common.pordStandardUnit']}<font color="#FF0000">＊</font></td>
										<td align="left">
											<html:select property="dictItemId" styleId="convId" >
												<logic:present name="convList" >
													<html:option value=""></html:option>
													<logic:iterate id="item" name="convList">
														<html:option value="${item[0]}">${item[1]}</html:option>
										    		</logic:iterate>
									    		</logic:present>
								    		</html:select>
								    		<div id="convMsg" ></div>
								    	 </td>
									 </tr>
								    <tr id="trProdUnit" style="display:none" >
										<td class="formTable"></td>
										<td align="left"></td>	
										<td class="formTable">${mr['page.common.distUnitConvert']}</td>
										<td align="left">
											<html:select property="targetProdUnit"  disabled="true" styleId="targetProdUnit" onchange="getProdRate();" style="width:184px">
	  											<html:option value=""></html:option>
												<html:option value="1">箱</html:option>
												<html:option value="2">过渡包装</html:option>
												<html:option value="3">最小包装</html:option>
												<html:option value="4">公斤</html:option>
												<html:option value="5">克</html:option>
												<html:option value="6">斤</html:option>
											</html:select>
										</td>													
								    </tr>
								   <tr id="trRate" style="display:none">
								   		<td class="formTable"></td>
										<td align="left"></td>	
										<td class="formTable">${mr['page.common.conversionRate']}</td>
										<td align="left">
											<html:text property="rate"  styleId="rate"   readonly="true" style="background-color: #F0F0F0"/>
										</td>
								    </tr>
								    <tr>
								    	<td class="formTable"></td>
										<td align="left"></td>	
										<td class="formTable"></td>
										<td align="left" >
											<div id="allMsg">
												
											</div>
										</td>
								    </tr>
								    <tr>
								    </tr>
								</table>				
							</div>
						</div>
						<div class="trail">
							<span class="fleft"></span><span class="fright"></span>
						</div>
					</div>
				</div>
				<div class="bosom_bottom">
					<span class="left"></span><span class="right"></span>
				</div>
			</div>
		</html:form>
		<html:javascript formName="dmsProdMappingForm" staticJavascript="false" dynamicJavascript="true" cdata="false" />
		<script type="text/javascript" src="${ctx}/scripts/validator.jsp"></script>
		<script type="text/javascript">
			if($("#rate").val()!="")
			{
				$("#trProdUnit").css("display","");
				$("#trRate").css("display","");
			}
			if($("#targetProdCode").val()!="")
			{
				getall();
			}
			//if($("#convId").val()!="" && $("#targetProdCode").val()!="" )
			//{
				//changeConv();
			//}
		</script>
	</body>
</html>
	
