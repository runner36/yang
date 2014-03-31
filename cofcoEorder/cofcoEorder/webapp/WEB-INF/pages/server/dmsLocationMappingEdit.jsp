<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>${mr['page.customize.title.afsLocMapMaint']}</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/dms.js"></script>
		<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>
		<script type="text/javascript" src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/jquery-1.2.6.min.js"></script>
		<script>
		function save() {
			if ($("#subFlag").attr("value")==1) {
				dmsLocationMappingForm.submit();
			}
			else
			{
				alert("标准编码未验证完毕……");
			}
		}
		function back() {
			window.history.go(-1);
		}
		function selectDict(dictName, id, name) {
			var v = openDictTree('${ctx}', dictName);
			if (v) {
				if(dictName=='geography')
				{
					var cityName=v.text;
					if($("#distStoreName").val().indexOf(cityName)!=-1 || $("#distStoreAddr").val().indexOf(cityName)!=-1 )
					{
						$("#isIndexCity").attr("value","是");
					}
					else{
						$("#isIndexCity").attr("value","否");
					}
				}
				id.value = v.id;
				name.value = v.text;
			}
		}
		function validateStorCode()
		{
			if($("#targetStoreCode").val()!="")
			{
				$.ajax({
					 type: "POST",
					 url: "${ctx}/server/dmsLocationMapping.do?method=getJsonStore",
					 data:   "storeCode="+$("#targetStoreCode").val(),
					 success: function(msg){
					 	 if(msg!=null &&  msg!='null')
					 	 { 
					 		 var obj = eval('(' + msg + ')');
					 		 if(obj.storeName!=null){
					 		 	$("#targetStoreName").attr("value",obj.storeName);
					 		 }
					 		 if(obj.updateTime!=null){
					 			$("#storeUpdateTime").attr("value",obj.updateTime); 
					 		 }
				 			if(obj.typeName!=null)
				 		 	{
				 		 		$("#customerType").attr("value",obj.typeName);
				 		 	}
					 		 if($("#activeSId").attr("value")==null || $("#activeSId").attr("value")==""){
					 		 
					 		 	if(obj.channelId!=null){
					 		 		$("#channelId").attr("value",obj.channelId);
					 		 	}
					 		 	if(obj.channelName!=null){
					 		 		$("#channelName").attr("value",obj.channelName);
					 		 	}
					 		 	if(obj.natureId!=null){
					 		 		$("#natureId").attr("value",obj.natureId);
					 		 	}
					 		 	if(obj.natureName!=null){
					 		 		$("#natureName").attr("value",obj.natureName);
					 		 	}
					 		 	if(obj.cityId!=null){
					 		 		$("#geoId").attr("value",obj.cityId);
					 		 	}
					 		 	if(obj.cityName!=null){
					 		 		$("#geoName").attr("value",obj.cityName);
					 		 	}
					 		 }
					 	 }
					 	 else
					 	 {
					 	 	alert("无此编码数据");
					 	 	$("#targetStoreCode").attr("value","");
					 	 	$("#storeUpdateTime").attr("value","");
					 	 	//$("#upStoreName").attr("value","");
					 	 	$("#targetStoreName").attr("value","");
					 	 	$("#customerType").attr("value","");
					 	 	$("#targetStoreName").attr("value","");
					 	 	 if($("#activeSId").attr("value")==null || $("#activeSId").attr("value")==""){
					 		 	$("#channelId").attr("value","");
					 		 	$("#channelName").attr("value","");
					 		 	$("#natureId").attr("value","");
					 		 	$("#natureName").attr("value","");
					 		 	$("#geoId").attr("value","");
					 		 	$("#geoName").attr("value","");
					 		 	$("#typeName").attr("value","");
					 		 	
					 		 }
					 	 }
					 	 $("#subFlag").attr("value","1");
				 } 
				}); 
			}
			else
			{
				$("#subFlag").attr("value","1");
			}
		}
		function storeCodeFocus()
		{
			//客户标准编码得到焦点 就把提交标志置为否，失去焦点 在置为TRUE
			$("#subFlag").attr("value","0");
		}
		function selectLocation(storeName, id, name) {
			var v = openLocationTree('${ctx}', storeName);
			if (v) {
				id.value = v.id;
				name.value = v.text;
			}
		}
		</script>
	</head>
	<body onload="WindowSollAuto()" onresize="WindowSollAuto()">
		<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/server/dmsLocationMapping.do?method=save" method="post">
			<html:hidden property="subFlag" styleId="subFlag" value="1"/>
			<html:hidden property="activeSId" styleId="mappingId" />
			<html:hidden property="clientId" />
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						${mr['page.customize.title.afsLocMapMaint']}
					</h4>
					<div class='MenuList'>
						<a href="javascript:save();" id="aaa">${mr['page.common.button.save']}</a>
						<a href="${ctx}/server/dmsLocationMapping.do?method=list">${mr['page.common.button.cancel']}</a>
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
									<tr>
										<html:hidden property="distStoreCode" />
										<td class="formTable">
											经销商Location名称
										</td>
										<td align="left">
											<html:text property="distStoreName" styleId="distStoreName" readonly="true"
												style="background-color: #F0F0F0" />
										</td>
										<td class="formTable">
											Location标准编码
										</td>
										<td align="left">
											<html:text property="targetStoreCode"  styleId="targetStoreCode" onfocus="storeCodeFocus();" onblur="validateStorCode();"  />
										</td>
									</tr>
									<tr>
										<td class="formTable">
											经销商Location地址
										</td>
										<td>
											<html:text property="distStoreAddr" styleId="distStoreAddr" readonly="true"
												style="background-color: #F0F0F0" />
										</td>
										<td class="formTable">
											Location标准名称
										</td>
										<td align="left">
											<html:text property="targetStoreName" styleId="targetStoreName" readonly="true"  style="background-color: #F0F0F0" />
										</td>
									</tr>
									<tr>
										<td class="formTable">
											上级Location
										</td>
										<td align="left">
											<html:hidden property="parentStoreId"/><html:text property="parentStoreName" readonly="true" styleClass="select_but" onclick="selectLocation('storeTree',dmsLocationMappingForm.parentStoreId,dmsLocationMappingForm.parentStoreName)"/>
										</td>
										<td class="formTable">
											主数据Location类型
										</td>
										<td align="left">
											<html:text property="customerType" readonly="true"  styleId="customerType" style="background-color: #F0F0F0" />
										</td>
									</tr>
									<tr>
										<td class="formTable">
											Location城市
										</td>
										<td align="left">
											<html:hidden property="geoId" styleId="geoId"/>
											<html:text property="geoName" readonly="true" styleId="geoName"
												styleClass="select_but"
												onclick="selectDict('geography',dmsLocationMappingForm.geoId,dmsLocationMappingForm.geoName)" />
										</td>
										<td class="formTable">
											Location名称地址中是否包含确认的城市
										</td>
										<td align="left">
											<html:text property="isIndexCity" styleId="isIndexCity" readonly="true" style="background-color: #F0F0F0" />
										</td>
									</tr>
									<tr>
										<td class="formTable">
											Location渠道
										</td>
										<td align="left">
											<html:hidden property="channelId" styleId="channelId" /><html:text property="channelName" styleId="channelName" readonly="true" styleClass="select_but" onclick="selectDict('storeChannel',dmsLocationMappingForm.channelId,dmsLocationMappingForm.channelName)"/>
										</td>
										<td class="formTable">
											建议Location标准编码
										</td>
										<td align="left">
											<html:text property="recommendStoreCode" readonly="true"  styleId="recommendStoreCode" style="background-color: #F0F0F0" />
										</td>
										
									</tr>
									<tr>
										<td class="formTable">
											Location类型
										</td>
										<td align="left">
										<html:hidden property="typeId" styleId="typeId" /><html:text property="typeName" styleId="typeName" readonly="true" styleClass="select_but" onclick="selectDict('storeType',dmsLocationMappingForm.typeId,dmsLocationMappingForm.typeName)"/>
										</td>
										<td class="formTable">
											Location主数据最近更新日期
										</td>
										<td align="left">
											<html:text property="storeUpdateTime" readonly="true"  styleId="storeUpdateTime" style="background-color: #F0F0F0" />
										</td>
									</tr>
									<tr>
										<td class="formTable">Location性质</td>
										<td align="left">
											<html:hidden property="natureId" styleId="natureId" /><html:text property="natureName" styleId="natureName" readonly="true" styleClass="select_but" onclick="selectDict('storeNature',dmsLocationMappingForm.natureId,dmsLocationMappingForm.natureName)"/>
										</td>
										<td class="formTable">
											回顾日期
										</td>
										<td align="left">
											<html:text  property="reviewDate" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>
										</td>
									</tr>
										<tr>
										<td class="formTable">${mr['page.common.isReturn']}</td>
										<td align="left">
											是<html:radio property="isreturnData" styleClass="Choose_input" value="1"/>${mr['page.common.no']}<html:radio property="isreturnData" styleClass="Choose_input" value="0"/>
										</td>
										<td class="formTable">
										</td>
										<td align="left">
										</td>
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
	</body>
</html>
