<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>${mr['page.customize.title.prodMastdataMaint']}</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/dms.js"></script>
		<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>
		<script language="javaScript" src="${ctx}/scripts/jquery-1.2.6.min.js"></script>
		<script>
		var contSubmitFlag=true;
		var editIsSubmit=true;
		function save(){
			if($("#prodCode").val()==""){
				alert("${mr['page.common.prodCodeRequire']}！");
				return;
			}
			if($("#prodName").val()==""){
				alert("${mr['page.common.prodNameRequire']}");
				return;
			}
			if($("#brandName").val()==""){
				alert("产品品牌为必填项");
				return;
			}
			
			if($("#prodSTRUName").val()==""){
				alert("产品结构为必填项");
				return;
			}
			if($("#prodUnitId").val()=="" || $("#prodUnitId").val()==null ){
				alert("${mr['page.common.StatisticalUnitRequire']}！");
				return;
			}
			mdmProductForm.submit();
			
		}
		//基本单位发生变化后判断哪些单位转换需要修改
		function baseUnitChange(){
			if($("#prodBaseUnitId").val()!=""){			
		     	for(var i = 0; i < document.getElementsByName("convUnit1Name").length; i++) {  
					var convUnit1Val=document.getElementsByName("convUnit1Name")[i].value;
					var convUnit2Val=document.getElementsByName("convUnit2Name")[i].value;
					if(convUnit1Val!=$("#prodBaseUnitId").val() && $("#prodBaseUnitId").val()!=convUnit2Val){
						$("#sel_"+convUnit1Val+"_"+convUnit2Val).empty();
						$("#sel_"+convUnit1Val+"_"+convUnit2Val).append("<span>${mr['page.common.basicUnitChageConverChange']}！<span>");
						newInitEdit(convUnit1Val+"_"+convUnit2Val);
					}else{
						$("#sel_"+convUnit1Val+"_"+convUnit2Val).empty();
					}
				}
		    }
		}
	//判断所有转换关系是否已经点击了确定按钮
	function editSubmit(){
		$("input[name=eidSubmit]").each(function(i){
			if($(this).attr("view")=='Y'){
				editIsSubmit=false;
				return false;
			}
			else{	
				editIsSubmit=true;
			}
		});
	}
	//统计单位发生变化
	function contUnitChange(){
		var countUnit=0;
		var prodUnitId=getselValue('prodUnitId');
		var prodBaseUnitId=getselValue('prodBaseUnitId');
		if(prodUnitId!="" && prodBaseUnitId!=""){			
		 	for(var i = 0; i < document.getElementsByName("convUnit1Name").length; i++) {  
				var convUnit1Val=document.getElementsByName("convUnit1Name")[i].value;
				var convUnit2Val=document.getElementsByName("convUnit2Name")[i].value;
				if((convUnit1Val==prodUnitId && convUnit2Val==prodBaseUnitId) || (convUnit2Val==prodUnitId &&  convUnit1Val==prodBaseUnitId)){
					countUnit=0;
					$("#contUnitmsg").empty();
						 break;
					}
					else{
						countUnit=countUnit+1;
					}
				}
		 }
		if(countUnit==0){
			contSubmitFlag=true;
		}else{
			$("#contUnitmsg").empty();
			$("#contUnitmsg").append("${mr['page.common.createStatisUnitToBasicUnit']}！");
				contSubmitFlag=false;
			}
		} 
	function  isSame(convId){
		var count=0;
	     for(var i = 0; i < document.getElementsByName("convUnit1Name").length; i++){  
			var convUnit1Val=document.getElementsByName("convUnit1Name")[i].value;
			var convUnit2Val=document.getElementsByName("convUnit2Name")[i].value;
			if((convId=="" || convId==null)){
				if(($("#Unit1Name").val()==convUnit1Val && $("#Unit2Name").val()==convUnit2Val) ||  ($("#Unit2Name").val()==convUnit1Val && $("#Unit1Name").val()==convUnit2Val)){
					return false;
				} 
			}
			else{
				if(($("#convUnit1Name_"+convId).val()==convUnit1Val && $("#convUnit2Name_"+convId).val()==convUnit2Val ) || ($("#convUnit1Name_"+convId).val()==convUnit2Val && $("#convUnit2Name_"+convId).val()==convUnit1Val) ){
					count=count+1;
				}
			}
		 } 
	     if(convId!=null || convId!="")
    	 {
    	 	if(count>1)
    	 		return false;
    	 }
		return true;  
	}
//添加单一条单位转换关系	
function addConv(){
	if($("#convValue2").val()!=""){
		if(isNaN($("#convValue2").val())){
			alert("${mr['page.common.inputAnumber']}！");
			$("#convValue2").focus();
			return false;
		}else{
			var obj = document.getElementById("Unit1Name");
			var strsel1 = obj.options[obj.selectedIndex].text;
			var obj1 = document.getElementById("Unit2Name");
			var strsel2 = obj1.options[obj1.selectedIndex].text;
			if(strsel1==strsel2 && parseFloat($("#convValue1").val()) !=parseFloat($("#convValue2").val())){
				alert("${mr['page.common.sameUnitSameValue']}！");
				$("#convValue2").focus();
			}
			else{
				var baseUnitId=$("#prodBaseUnitId").val();
				var countUnitId=$("#prodUnitId").val();
				if(baseUnitId==null){
					alert("${mr['page.common.selectBasicUnit']}！");
					$("#prodBaseUnitId").focus();
					return false;
				}
				else{
					if(countUnitId==null){
						alert("${mr['page.common.selectstatisticalUnit']}！");
					}
					else{
						if($("#Unit1Name").val()!=baseUnitId && $("#Unit2Name").val()!=baseUnitId){
							alert("${mr['page.common.mustHaveaBasicUnit']}！");
							return false;
						}	
						else{
							if(isSame('')){
								var convid=$("#Unit1Name").val()+"_"+$("#Unit2Name").val();
								var str="";
						 		str+="<tr id="+convid+">"
								str+="<td  width='1%'>";
								str+="<input type='text' disabled='disabled' style='width:50px;' value='1' id='Unit1Val_"+convid+"' />&nbsp;";
								str+=getSelStr(convid,$("#Unit1Name").val(),'convUnit1Name');
								str+="=";
								str+="<input type='text' readonly='readonly' style='width:50px;' onkeyup=\"validateValue('"+convid+"')\"  name='Unit2Val' value='"+$("#convValue2").val()+"' id='Unit2Val_"+convid+"' />&nbsp;&nbsp;";
								str+=getSelStr(convid,$("#Unit2Name").val(),'convUnit2Name');
								str+=" <input type='button' value='${mr['page.common.button.edit']}' onclick=\"newInitEdit('"+convid+"')\" id='btEdit_"+convid+"'  style='width:40px;' />";
								str+=" <input type='button' name='eidSubmit' value='确定' onclick=\"editConv('"+convid+"')\" id='btSave_"+convid+"' view='N'  style='width:40px;display:none;' />";
					 			str+=" <input type='button' value='${mr['page.common.button.delete']}' onclick=\"delConv('"+convid+"')\"   style='width:40px;' />";
					 			str+="</td>"
					 			str+="</tr>";
					 			$("#tableConv").append(str);
					 			$("#convValue1").attr("value",1);
								$("#convValue2").attr("value","");
							}
							else{
								alert("${mr['page.common.conversionAlreadyExists']}！");
								return false;
							}
						}
					}
				}
		   }
		 }
	 }
	 else
	 {
	 	alert("${mr['page.common.conversionIsRequire']}！");
	 	$("#convValue2").focus();
	 }
}

function delConv(convId){
	$("#"+convId).remove();
}
function validateValue(id){
	if(isNaN($("#Unit2Val_"+id).val())){
		alert('${mr['page.common.inputAnumber']}');
		$("#Unit2Val_"+id).attr("value","");
		editIsSubmit=false;
	}
}
	function newInitEdit(convId){
		$("#btEdit_"+convId).css("display","none");
		$("#btSave_"+convId).attr("view","Y");
		$("#btSave_"+convId).css("display","");
		$("#convUnit1Name_"+convId).attr("disabled","");
		$("#Unit2Val_"+convId).attr("readonly","");
		$("#convUnit2Name_"+convId).attr("disabled","");
	}

function editConv(convId){
		if(trim($("#Unit2Val_"+convId).val())==''){
			alert("${mr['page.common.valueIsRequire']} !");
			return false;
		}
		var obj = document.getElementById("convUnit1Name_"+convId);
		var strsel1 = obj.options[obj.selectedIndex].text;
		var obj1 = document.getElementById("convUnit2Name_"+convId);
		var strsel2 = obj1.options[obj1.selectedIndex].text;
		if(strsel1==strsel2 && parseFloat($("#Unit1Val_"+convId).val()) !=parseFloat($("#Unit2Val_"+convId).val())){
			alert("${mr['page.common.sameUnitSameValue']}！");
			$("#Unit2Val_"+convId).focus();
			return false;
		}
		else{
			    var baseUnitId=$("#prodBaseUnitId").val();
				var countUnitId=$("#prodUnitId").val();
				if(baseUnitId==null){
					alert("${mr['page.common.selectBasicUnit']}！");
					$("#prodBaseUnitId").focus();
					return false;
				}else if(countUnitId==null){
						alert("${mr['page.common.selectstatisticalUnit']}！");
						return false;
				}else if($("#convUnit1Name_"+convId).val()!=baseUnitId && $("#convUnit2Name_"+convId).val()!=baseUnitId){
							alert("${mr['page.common.mustHaveaBasicUnit']}！");
							return false;
				}else if(isSame(convId)){
								$("#btEdit_"+convId).css("display","");
								$("#btSave_"+convId).attr("view","N");
								$("#btSave_"+convId).css("display","none");
								$("#convUnit1Name_"+convId).attr("disabled","none");
								$("#Unit2Val_"+convId).attr("readonly","true");
								$("#convUnit2Name_"+convId).attr("disabled","none");
								$("#sel_"+convId).empty();
							}else{
								alert("${mr['page.common.conversionAlreadyExists']}！");
								return false;
							}
						}
}
	function selectDict(dictName, id, name) {
		var v = openDictTree('${ctx}', dictName,'2','0');
		if (v) {
			id.value = v.id;
			name.value = v.text;
		}
	}
	function selectProd(id, name) {
		var v = openProdTree('${ctx}','1','0', id.value);
		if (v) {
			id.value = v.id;
			name.value = v.text;
		}
	}

	function getSelStr(selIdStr,selValue,convUnit1Name){
		var str="";
		 var sel=document.getElementById("Unit1Name");
		 str=str+"<select id='"+convUnit1Name+"_"+selIdStr+"' name='"+convUnit1Name+"' disabled='disabled' style='width:100px;' >";
		 for(var i=0;i<sel.length;i++){
		  str+="<option value='"+sel[i].value+"'";
		  if(sel[i].value==selValue){
		  	str+=" selected "
		  }
		  str+=" >"+sel[i].text+"</option>";
		 }
		 str+="</select>";
		return str;
	}
	function  trim(obj){ 
	 	return   obj.replace(/(^\s*)|(\s*$)/g,"");   
	} 
	function getselValue(sid){
		 var sel=document.getElementById(sid);
		  for(var i=0;i<sel.options.length;i++){
			  if(sel.options[i].selected){
				  return sel.options[i].value;
				  break;
			  }
		  }
	}
		</script>
	</head>
	<body onload="WindowSollAuto()" onresize="WindowSollAuto()">
		<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/mdm/mdmProduct.do?method=save" method="post">
			<html:hidden property="prodId" />
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						${mr['page.customize.title.prodMastdataMaint']}
					</h4>
					<div class='MenuList'>
						<a  href="#" onclick="javascript:save();" >${mr['page.common.button.save']}</a>
						<a href="mdmProduct.do?method=list">${mr['page.common.button.cancel']}</a>
					</div>
					<span class="right"></span>
				</div>
				<div class="bosom_side">
					<div class="casing">
						<div class="caput">
							<span class="left"></span><span class="right"></span>
						</div>
						<div class="viscera" id="SollAuto">
							<div class="sheet_div">
								<table class="list_add">
									<%-- <tr>
										<td class="formTable">${mr['page.common.parentProduct']}</td>
										<td align="left">
										    <html:hidden property="parentProdId"/>
											<html:text property="parentProdName" readonly="true" styleClass="select_but" onclick="selectProd(mdmProductForm.parentProdId,mdmProductForm.parentProdName)"/>
										</td>
										<td class="formTable"></td>
										<td align="left"></td>
									</tr> --%>
								    <tr>
										<td class="formTable">${mr['page.common.mastProdCode']}<font color="#FF0000">＊</font></td>
										<td align="left">
										    <html:text property="prodCode" styleId="prodCode" maxlength="25"/>
										    
										</td>
										<td class="formTable">${mr['page.common.externalCode']}</td>
										<td align="left">
										   <html:text property="extCode" maxlength="25"/>
										</td>
									</tr>
						    	    <tr>
										<td class="formTable">${mr['page.common.mastProdName']}<font color="#FF0000">＊</font></td>
										<td align="left">
										     <html:text property="prodName" styleId="prodName" maxlength="100"/>
										 </td>
										<td class="formTable">${mr['page.common.mastProdEngName']}</td>
										<td align="left"><html:text property="prodNameEn" maxlength="100"/></td>
									</tr>
									<tr>
										<td class="formTable">${mr['page.common.mastProdAlias']}</td>
										<td align="left">
										   <html:text property="prodAlias" maxlength="100"/>
										</td>
										<td class="formTable">产品价格/价格单位</td>
										<td align="left"><html:text property="prodPrice"  styleId="prodPrice" style="width:80px;" maxlength="12"/>
										 / <html:select property="prodPricUnitId" style="width:90px;" styleId="prodPricUnitId">
											 <logic:present name="utilList" >
											 	<html:option value=""></html:option>
												 <logic:iterate id="selitem" name="utilList">
													<html:option value="${selitem.dictItemId}">
														${selitem.itemName}
													</html:option>
												 </logic:iterate>
											 </logic:present>
										  </html:select>
										</td>
									</tr>
								    <tr>
										<td class="formTable">${mr['page.common.productBarcode']}</td>
										<td align="left"><html:text property="prodBarcode" maxlength="25"/></td>
										<td class="formTable">${mr['page.common.productPcBarcode']}</td>
										<td align="left"><html:text property="prodPcBarcode" maxlength="25"/></td>
									</tr>
									<tr>
										<td class="formTable">${mr['page.common.mastProdBrand']}<font color="#FF0000">＊</font></td>
										<td align="left"><html:hidden property="brandId"/><html:text property="brandName" styleId="brandName" readonly="true" styleClass="select_but" onclick="selectDict('prodBrand',mdmProductForm.brandId,mdmProductForm.brandName)"/></td>
										<td class="formTable">${mr['page.common.mastProdSeries']}</td>
										<td align="left"><html:hidden property="typeId"/><html:text property="typeName" readonly="true" styleClass="select_but" onclick="selectDict('prodType',mdmProductForm.typeId,mdmProductForm.typeName)"/></td>
									</tr>
									<tr>
										<td class="formTable">产品包装</td>
										<td align="left"><html:hidden property="packId"/><html:text property="packName" readonly="true" styleClass="select_but" onclick="selectDict('prodPack',mdmProductForm.packId,mdmProductForm.packName)"/></td>
										<td class="formTable">${mr['page.common.prodLine']}</td>
										<td align="left"><html:hidden property="otherId"/><html:text property="otherName" readonly="true" styleClass="select_but" onclick="selectDict('prodOther',mdmProductForm.otherId,mdmProductForm.otherName)"/></td>
									</tr>
									<tr>
										<td class="formTable">${mr['page.common.prodStructure']} <font color="#FF0000">＊</font></td>
										<td align="left"><html:hidden property="prodSTRUId"/><html:text property="prodSTRUName" readonly="true" styleId="prodSTRUName" styleClass="select_but" onclick="selectDict('prodSTRU',mdmProductForm.prodSTRUId,mdmProductForm.prodSTRUName)"/></td>
										<td class="formTable">${mr['page.common.lifecycleState']}</td>
										<td align="left">${mr['page.common.yes']}<html:radio property="state" styleClass="Choose_input" value="1"/>${mr['page.common.no']}<html:radio property="state" styleClass="Choose_input" value="0"/></td>
									</tr>
									<tr>
										<td class="formTable">${mr['page.common.packageSpecification']}</td>
										<td align="left"><html:text property="prodSpec" maxlength="100"/></td>
										<td class="formTable">${mr['page.common.sort']}</td>
										<td align="left"><html:text property="sort" maxlength="10"/></td>
									</tr>
									<tr>
										<td class="formTable">${mr['page.common.prodStaticsUnit']}<font color="#FF0000">＊</font></td>
										<td align="left">
												<html:select property="prodUnitId" style="width:185px;"  styleId="prodUnitId">
													<html:option value=""></html:option>
													<logic:present name="utilList" >
													 <logic:iterate id="selitem" name="utilList">
														<html:option value="${selitem.dictItemId}">
															${selitem.itemName}
														</html:option>
													 </logic:iterate>
													</logic:present>
												</html:select>
										</td>
						                <td class="formTable">${mr['page.common.baseUnit']}</td>
										<td align="left">
											<html:select property="prodBaseUnitId" onchange="baseUnitChange();"  style="width:185px;"  styleId="prodBaseUnitId">
												<html:option value=""></html:option>
												<logic:present name="utilList" >
													 <logic:iterate id="selitem" name="utilList">
														<html:option value="${selitem.dictItemId}">
															${selitem.itemName}
														</html:option>
													 </logic:iterate>
												</logic:present>
											</html:select>
										</td>
								    </tr>
									<tr>
										<td class="formTable">重量/重量单位</td>
										<td align="left"><html:text property="prodWeight" styleId="prodWeight" style="width:80px;" maxlength="12"/>
										 /<html:select property="prodWeightUnitId" style="width:90px;" styleId="prodWeightUnitId">
											 <logic:present name="utilList" >
											 	<html:option value=""></html:option>
												 <logic:iterate id="selitem" name="utilList">
													<html:option value="${selitem.dictItemId}">
														${selitem.itemName}
													</html:option>
												 </logic:iterate>
											 </logic:present>
										  </html:select>
										</td>
										<%-- <td class="formTable">${mr['page.common.smallUnitOfWeight']}</td>
										<td align="left">
										   <html:text property="prodMinWeight" maxlength="12"/>
										</td> --%>
										
										<td class="formTable">体积/体积单位</td>
										<td align="left"><html:text property="prodVolum" styleId="prodVolum" style="width:80px;" maxlength="12"/>
										 /<html:select property="prodVolehUnitId" style="width:90px;" styleId="prodVolehUnitId">
											 <logic:present name="utilList" >
											 	<html:option value=""></html:option>
												 <logic:iterate id="selitem" name="utilList">
													<html:option value="${selitem.dictItemId}">
														${selitem.itemName}
													</html:option>
												 </logic:iterate>
											 </logic:present>
										  </html:select>
										</td>
									</tr>
									<tr>
										<td class="formTable">${mr['page.common.transPackage']}</td>
										<td align="left">
										    <html:text property="prodContent" maxlength="12"/>
										 </td>
										<td class="formTable">${mr['page.common.minPackage']}</td>
										<td align="left">
										    <html:text property="prodMinContent" maxlength="12"/>
										 </td>
									</tr>
									<logic:present name="utilList" >
									<tr style="display:none">
										<td class="formTable">${mr['page.common.unitRelationShipConvert']}</td>
										<td align="left" colspan="3">
										</td>
									</tr>
									<tr style="display:none">
										<td align="left" colspan="4">
											<table id="tableConv" border="0"  style="width:100%;margin-left: 135px;" >
												<logic:present name="convList" >
												<logic:iterate id="item" name="convList" indexId="index">
													<tr id="${item.convUnit1Id.dictItemId}_${item.convUnit2Id.dictItemId}">
														<td>
															<input type="hidden" value="${item.convId}" name="convId"   /> 
															<input type="text" disabled="disabled" style="width:50px;" name="Unit1Val"  id="Unit1Val_${item.convUnit1Id.dictItemId}_${item.convUnit2Id.dictItemId}" value="${item.convUnit1Val}" />
															<select id="convUnit1Name_${item.convUnit1Id.dictItemId}_${item.convUnit2Id.dictItemId}" name="convUnit1Name" style="width:100px;"  disabled="disabled">
																 <logic:iterate id="selitem" name="utilList" >
																 		<option value="${selitem.dictItemId}" <c:if test="${item.convUnit1Id.dictItemId==selitem.dictItemId}" >selected</c:if>>${selitem.itemName}</option>
																 </logic:iterate>
														   </select>=
														   <input type="text"  style="width:50px;" readonly="true" name="Unit2Val" onkeyup="validateValue('${item.convUnit1Id.dictItemId}_${item.convUnit2Id.dictItemId}')"   id="Unit2Val_${item.convUnit1Id.dictItemId}_${item.convUnit2Id.dictItemId}" value="${item.convUnit2Val}" />
														   <select id="convUnit2Name_${item.convUnit1Id.dictItemId}_${item.convUnit2Id.dictItemId}" style="width:100px;" name="convUnit2Name" disabled="disabled" >
																 <logic:iterate id="selitem" name="utilList" >
																 		<option value="${selitem.dictItemId}" <c:if test="${item.convUnit2Id.dictItemId==selitem.dictItemId}" >selected</c:if>>${selitem.itemName}</option>
																 </logic:iterate>
														   </select>
														   <input type="button" value="${mr['page.common.button.edit']}" onclick="newInitEdit('${item.convUnit1Id.dictItemId}_${item.convUnit2Id.dictItemId}')" id="btEdit_${item.convUnit1Id.dictItemId}_${item.convUnit2Id.dictItemId}"  style="width:40px;" /> <input type="button"  value="确定" name='eidSubmit' id="btSave_${item.convUnit1Id.dictItemId}_${item.convUnit2Id.dictItemId}" onclick="editConv('${item.convUnit1Id.dictItemId}_${item.convUnit2Id.dictItemId}');" view='N'  style="width:40px;display:none;" />&nbsp;&nbsp;<input type="button" onclick="delConv('${item.convUnit1Id.dictItemId}_${item.convUnit2Id.dictItemId}')"  style="width:40px;" value="${mr['page.common.button.delete']}" />
															<span style="color: red;" id="sel_${item.convUnit1Id.dictItemId}_${item.convUnit2Id.dictItemId}"></span>
														</td>
													</tr>
												</logic:iterate>
												</logic:present>
											</table>
													<div style="margin-left: 147px;">
													<html:text property="convValue1" styleId="convValue1" readonly="true" style="width:50px;" value="1" maxlength="10"></html:text>
													   <select id="Unit1Name"  name="Unit1Name"   style="width:100px;"  >
													   	<logic:present name="utilList" >
															 <logic:iterate id="selitem" name="utilList">
															 		<option value="${selitem.dictItemId}">${selitem.itemName}</option>
															 </logic:iterate>
														 </logic:present>
													  </select>=
													 <html:text property="convValue2" styleId="convValue2"    onkeyup="value=value.replace(/[^\\+\\-\\d\.]/g,'');" style="width:50px;"  ></html:text>
													   <select id="Unit2Name"    name="Unit2Name" style="width:100px;" >
													   	<logic:present name="utilList" >
															 <logic:iterate id="selitem" name="utilList">
															 		<option value="${selitem.dictItemId}" >${selitem.itemName}</option>
															 </logic:iterate>
														 </logic:present>
													  </select>
													 <input type="button"  style="width:40px;" id="convadd" onclick="addConv();" value="${mr['page.common.button.add']}" />
													 <span id="contUnitmsg" style="color: red;"></span>
													 </div>
										</td>
									</tr>
									</logic:present>
									<tr>
										<td class="formTable">${mr['page.common.property']}</td>
										<td align="left">
											<html:select style="width:185px;"  property="prodType">
												<html:option value="0">${mr['page.common.button.pleaseSelect']}</html:option>
												<html:option value="1">SKU</html:option>
												<html:option value="2">竞品</html:option>
											</html:select>
										</td>
										<td class="formTable">${mr['page.common.mastProdType']}</td>
										<td align="left">
											<html:select property="prodTypeId"  style="width:185px;"  >
												<html:option value=""></html:option>
												<logic:present name="prodTypeList" >
													 <logic:iterate id="selitem" name="prodTypeList">
														<html:option value="${selitem.dictItemId}">
															${selitem.itemName}
														</html:option>
													 </logic:iterate>
												</logic:present>
											</html:select>
										</td>
									</tr>
									<tr>
										<td class="formTable">级别</td>
										<td align="left">
											<html:select property="memo1Id"  style="width:185px;"  >
												<html:option value=""></html:option>
												<logic:present name="prodAttr1List" >
													 <logic:iterate id="selitem" name="prodAttr1List">
														<html:option value="${selitem.dictItemId}">
															${selitem.itemName}
														</html:option>
													 </logic:iterate>
												</logic:present>
											</html:select>
										</td>
										<td class="formTable">种类口味</td>
										<td align="left">
											<html:select property="memo2Id"  style="width:185px;"  >
												<html:option value=""></html:option>
												<logic:present name="prodAttr2List" >
													 <logic:iterate id="selitem" name="prodAttr2List">
														<html:option value="${selitem.dictItemId}">
															${selitem.itemName}
														</html:option>
													 </logic:iterate>
												</logic:present>
											</html:select>
										</td>
									</tr>
									<tr>
										<td class="formTable">物料组</td>
										<td align="left">
											<html:select property="memo4Id"  style="width:185px;"  >
												<html:option value=""></html:option>
												<logic:present name="prodAttr4List" >
													 <logic:iterate id="selitem" name="prodAttr4List">
														<html:option value="${selitem.dictItemId}">
															${selitem.itemName}
														</html:option>
													 </logic:iterate>
												</logic:present>
											</html:select>
										</td>
										<td class="formTable">属性3</td>
										<td align="left">
											<html:select property="memo3Id"  style="width:185px;"  >
												<html:option value=""></html:option>
												<logic:present name="prodAttr3List" >
													 <logic:iterate id="selitem" name="prodAttr3List">
														<html:option value="${selitem.dictItemId}">
															${selitem.itemName}
														</html:option>
													 </logic:iterate>
												</logic:present>
											</html:select>
										</td>
									</tr>
									<tr>
										<td class="formTable">${mr['page.common.property']}5</td>
										<td align="left">
											<html:select property="memo5Id"  style="width:185px;"  >
												<html:option value=""></html:option>
												<logic:present name="prodAttr5List" >
													 <logic:iterate id="selitem" name="prodAttr5List">
														<html:option value="${selitem.dictItemId}">
															${selitem.itemName}
														</html:option>
													 </logic:iterate>
												</logic:present>
											</html:select>
										</td>
										<td class="formTable">${mr['page.common.property']}6</td>
										<td align="left">
											<html:select property="memo6Id"  style="width:185px;"  >
												<html:option value=""></html:option>
												<logic:present name="prodAttr6List" >
													 <logic:iterate id="selitem" name="prodAttr6List">
														<html:option value="${selitem.dictItemId}">
															${selitem.itemName}
														</html:option>
													 </logic:iterate>
												</logic:present>
											</html:select>
										</td>
								    </tr>
								    	<tr>
									    <td class="formTable">${mr['page.common.memo']}</td>
									    <td align="left" colspan="3"><html:textarea property="remark" rows="3" cols="80"></html:textarea></td>
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
		<html:javascript formName="mdmProductForm" staticJavascript="false" dynamicJavascript="true" cdata="false" />
		<script type="text/javascript" src="${ctx}/scripts/validator.jsp"></script>
		<script type="text/javascript">
		//selByName('convUnit1Name',document.forms[0].prodUnit.value);
		//selByName('convUnit2Name',document.forms[0].prodBaseUnitName.value);
		function selByName(selId,val)
		{
			 var sel=document.getElementById(selId);
			 for(var i=0;i<sel.length;i++)
			 {
			  if(sel[i].text==val)
			   sel.selectedIndex=i;
			 }
		}
		function selById(selId,val)
		{
			 var sel=document.getElementById(selId);
			 for(var i=0;i<sel.length;i++)
			 {
			  if(sel[i].value==val)
			   sel.selectedIndex=i;
			 }
		}
		</script>
	</body>
</html>
