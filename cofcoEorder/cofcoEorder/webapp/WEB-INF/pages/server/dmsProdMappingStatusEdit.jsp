<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>经销商产品匹配情况</title>
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
			if(validatedmsProdMappingStatusForm(dmsProdMappingStatusForm))
			{
				dmsProdMappingStatusForm.submit();
			}
		}
		
		function list() {
			if ('${param.flag}' == '1') {
				location = 'dmsProdMappingStatus.do?method=noMappingList';
			}
			else {
				location = 'dmsProdMappingStatus.do?method=list';
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
			var form = dmsProdMappingStatusForm;
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
								alert("供应商产品编码不存在！");
								form.targetProdName.value = '';
								form.targetProdCode.value = '';
								clearValue();
							}
						}
						else {
							alert("供应商产品编码不存在！")
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

		function getProdUnit()
		{
			if ($("#targetProdCode").val()!='') {
				$.ajax({
					   type: "POST",
					   url: '${ctx}'+"/server/dmsProdMappingStatus.do?method=getProdJsonConvList",
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
		<html:form action="/server/dmsProdMappingStatus.do?method=save" method="post">
			<html:hidden property="activePId" />
			<html:hidden property="flag" />
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						经销商产品匹配情况维护
					</h4>
					<div class='MenuList'>
						<a href="javascript:save();">保存</a>
						<a href="javascript:list();">取消</a>
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
										<td class="formTable">客户端名称<font color="#FF0000">＊</font></td>
										<td align="left">
											<html:hidden property="clientId"/><html:text property="clientName" maxlength="20" style="background-color: #F0F0F0" readonly="true"/>
										</td>
										<td class="formTable"></td>
										<td align="left">
										</td>
									</tr>
								    <tr>
										<td class="formTable">经销商产品编码<font color="#FF0000">＊</font></td>
										<td align="left"><html:text property="distProdCode"  readonly="true" style="background-color: #F0F0F0" maxlength="20"  /></td>
										<td class="formTable">产品标准编码<font color="#FF0000">＊</font></td>
										<td align="left"><html:text property="targetProdCode" styleId="targetProdCode" maxlength="50" onchange="getProdName();"/></td>
								    </tr>
								    <tr>
										<td class="formTable">经销商产品名称</td>
										<td align="left"><html:text property="distProdName" readonly="true" style="background-color: #F0F0F0"/></td>
										<td class="formTable">产品标准名称</td>
										<td align="left"><html:text property="targetProdName" readonly="true" style="background-color: #F0F0F0"/></td>
									 </tr>
									 <tr>
										<td class="formTable" valign="top">产品关键字</td>
										<td align="left">
											<logic:iterate id="keyword" name="keywords">
												${keyword.content}<BR>
											</logic:iterate>
										</td>
									   	<td class="formTable"></td>
										<td align="left"></td>
									 </tr>
									 <tr>
										<td class="formTable">经销商产品条码</td>
										<td align="left"><html:text property="distProdBarCode" readonly="true" style="background-color: #F0F0F0"/></td>
									   	<td class="formTable">产品条码(箱)</td>
										<td align="left"><html:text property="targetBarCode" readonly="true" style="background-color: #F0F0F0"/></td>
									 </tr>
									 <tr>
										<td class="formTable"></td>
										<td align="left"></td>
									   	<td class="formTable">产品条码(PC)</td>
										<td align="left"><html:text property="targetProdPcBarcode" readonly="true" style="background-color: #F0F0F0"/></td>
									 </tr>
									 <tr>
										<td class="formTable"></td>
										<td align="left"></td>
									   	<td class="formTable">条码检查状态</td>
									   	<td align="left">
									   		<html:text property="prodBarCodeCheckStatus" readonly="true" style="background-color: #F0F0F0"/>
										<td align="left">
										</td>
									 </tr>
									 
									 
								     <tr>
										<td class="formTable" valign="top">经销商产品单位</td>
										<td align="left" valign="top"><html:text property="distProdUnit" readonly="true" style="background-color: #F0F0F0" /></td>
									   	<td class="formTable"  valign="top">产品标准单位<font color="#FF0000">＊</font></td>
										<td align="left">
											<html:select property="dictItemId" styleId="convId" >
												<logic:present name="convList" >
													<html:option value=""></html:option>
													<logic:iterate id="item" name="convList">
														<html:option value="${item[0]}">${item[1]}</html:option>
										    		</logic:iterate>
									    		</logic:present>
								    		</html:select>
								    		<div id="allMsg" >
								    		
								    		</div>
								    	 </td>
									 </tr>
									 
									 
									 <tr>
										<td class="formTable" valign="top">最近三月平均销售单价</td>
										<td align="left" valign="top">
										${distSalePrice}
										</td>
									   	<td class="formTable" valign="top">产品标准价格</td>
									   	<td align="left" valign="top">
									   		<span style='color:red;' >
										   		<logic:iterate id="unitPrice" name="unitPrices">
													${unitPrice.unitName}:${unitPrice.price}<BR>
									    		</logic:iterate>
								    		</span>
								    		<logic:iterate id="otherPrice" name="otherPrices">
												${otherPrice.unitName}:${otherPrice.price}<BR>
								    		</logic:iterate>
										<td align="left">
										</td>
									 </tr>
								 	 <tr>
										<td class="formTable"></td>
										<td align="left"></td>
									   	<td class="formTable">加价率</td>
									   	<td align="left">
									   		${addPriceRate}
										<td align="left">
										</td>
									 </tr>
									 
									 <tr>
										<td class="formTable">最近到货日期</td>
										<td align="left">${lastPurchaseDate }</td>
									   	<td class="formTable"></td>
									   	<td align="left">
										<td align="left">
										</td>
									 </tr>
									 <tr>
										<td class="formTable">首次销售日期</td>
										<td align="left">${firstSaleDate }</td>
									   	<td class="formTable"></td>
									   	<td align="left">
										<td align="left">
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
		
		
		<html:javascript formName="dmsProdMappingStatusForm" staticJavascript="false" dynamicJavascript="true" cdata="false" />
		<script type="text/javascript" src="${ctx}/scripts/validator.jsp"></script>
		<script type="text/javascript">
			if($("#targetProdCode").val()!="")
			{
				getall();
			}
			
		</script>
	</body>
</html>
	
