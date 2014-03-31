<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>${mr['page.customize.title.clientManage']}</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>
		<script language="javaScript" src="${ctx}/scripts/dms.js"></script>
		<script type="text/javascript" src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
		<script language="javaScript" src="${ctx}/scripts/jquery-1.2.6.min.js"></script>
		<script>
		function save_back() {
			if (validateDmsClientForm(dmsClientForm)) {
				if(dmsClientForm.billExtractStartdate.value!=''&&dmsClientForm.billExtractEnddate.value!=''){
					if(dmsClientForm.billExtractStartdate.value>=dmsClientForm.billExtractEnddate.value){
						alert('${mr['page.common.mess.extractDataRange']}');
						return;
					}
				}
				dmsClientForm.submit();
			}
		}
		
		function save() {
			if (validateDmsClientForm(dmsClientForm)) {
				if(dmsClientForm.billExtractStartdate.value!=''&&dmsClientForm.billExtractEnddate.value!=''){
					if(dmsClientForm.billExtractStartdate.value>=dmsClientForm.billExtractEnddate.value){
						alert('${mr['page.common.mess.extractDataRange']}');
						return;
					}	
				}
				if(dmsClientForm.billExtractStartdate.value!=''||dmsClientForm.billExtractEnddate.value!=''){
					var strClientCode=dmsClientForm.clientCode.value;
					var strDistId=dmsClientForm.distId.value;
					$.ajax({
					       type: "POST",
					       url:  "${ctx}/server/dmsClient.do?method=queryClientBillExtractTime",
					       data: {						        
						        clientCode:strClientCode,
						        distid:strDistId
						       },
					       async: false,
					       dataType: "HTML",
					       success: function(data){
					         if(data!='null'&&data!='error'){
					        	 if (confirm(data)){
					        		 document.dmsClientForm.submit();
					        	 }
					       		return;
					         }else{
					        	 if(data=='null'){
					        		 document.dmsClientForm.submit();
					        	 }
								return;
					         }
					       }
					    });
				}else{
					dmsClientForm.submit();
				}				
			}
		}
		
		function selectDist() {
			var v = openDistTree('${ctx}');
			if (v) {
				dmsClientForm.distId.value = v.id;
				dmsClientForm.distName.value = v.text;
			}
		}
		function selectSoft() {
			var v = openSoftTree('${ctx}');
			if (v) {
				dmsClientForm.softId.value = v.id;
				dmsClientForm.softName.value = v.text;
			}
		}
		function selectPretreatType() {
			var form = dmsClientForm;
			var v = openDictTree('${ctx}','dataPretreatType',2,2,form.dataPretreatTypeIDS.value);
			if (v) {
				form.dataPretreatTypeIDS.value = v.id;
				form.dataPretreatTypeNameS.value = v.text;
			}
		}
		</script>
	</head>
	<body onload="WindowSollAuto()">
		<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/server/dmsClient.do?method=save" method="post">
			<html:hidden property="clientId" />
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						${mr['page.customize.title.clientManage']}
					</h4>
					<div class='MenuList'>
						<a href="javascript:save();">${mr['page.common.button.save']}</a>
						<a href="dmsClient.do?method=list">${mr['page.common.button.cancel']}</a>
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
										<td class="formTable">${mr['page.common.softwareName']}<font color="#FF0000">＊</font></td>
										<td align="left">
											<html:hidden property="softId"/><html:text property="softName" maxlength="20" styleClass="select_but" onclick="selectSoft()" readonly="true"/>
										</td>
										<td class="formTable">${mr['page.common.distributor']}<font color="#FF0000">＊</font></td>
										<td align="left">
											<html:hidden property="distId"/><html:text property="distName" styleClass="select_but" onclick="selectDist()" readonly="true"/>
										</td>
									</tr>
									
								    <tr>
								    	<td class="formTable">${mr['page.common.clientCode']}<font color="#FF0000">＊</font></td>
										<td align="left"><html:text property="clientCode" maxlength="20"/></td>
										<td class="formTable">${mr['page.common.clientName']}</td>
										<td align="left"><html:text property="clientName" maxlength="20"/></td>
								    </tr>
								    
								    <tr>
										<td class="formTable">${mr['page.common.currentVersion']}</td>
										<td align="left"><html:text property="currVersion" maxlength="20" disabled="true"/></td>
								    	<td class="formTable">${mr['page.common.isReturn']}</td>
										<td align="left">
										${mr['page.common.yes']}<html:radio property="isreturnData" styleClass="Choose_input" value="1"/>${mr['page.common.no']}<html:radio property="isreturnData" styleClass="Choose_input" value="0"/>
										</td>
								    </tr>
									
									<tr>
										<td class="formTable">${mr['page.common.extractTime']}</td>
										<td align="left"><html:text property="extractTime" maxlength="20"/></td>
										<td class="formTable">${mr['page.common.dataCycle']}</td>
										<td align="left"><html:text property="dataDays" maxlength="20"/></td>
								    </tr>
								    
								    <tr>
										<td class="formTable">${mr['page.common.clientDataStartDate']}</td>
										<td align="left">
											<html:text property="billExtractStartdate" maxlength="20" styleClass="date_but" onfocus="WdatePicker()" readonly="true"/>
										</td>
										<td class="formTable">${mr['page.common.clientDataEndDate']}</td>
										<td align="left">
											<html:text property="billExtractEnddate" maxlength="20" styleClass="date_but" onfocus="WdatePicker()" readonly="true"/>
										</td>
								    </tr>
								    
								    <tr>
										<td class="formTable">${mr['page.common.targetdata']}</td>
										<td align="left" colspan="3">
											<html:select property="dataTarget" style="width:184px">
	  											<html:option value="0">管理服务器</html:option>
												<html:option value="1">正式服务器</html:option>
												<html:option value="10">全部</html:option>
											</html:select>
										</td>
								    </tr>
								    
								    <tr>
										<td class="formTable">${mr['page.common.pretreatmentConfigure']}</td>
										<td align="left" colspan="3"><html:hidden property="dataPretreatTypeIDS"/><html:textarea property="dataPretreatTypeNameS" rows="3" cols="80" onclick="selectPretreatType()" readonly="true"></html:textarea></td>
								    </tr>
									
								    <tr>
										<td class="formTable">DB_DRIVER</td>
										<td align="left" colspan="3"><html:text property="dbDriver" maxlength="200" style="width:616px"/></td>
								    </tr>
								    
								    <tr>
										<td class="formTable">DB_URL</td>
										<td align="left" colspan="3"><html:text property="dbUrl" maxlength="200" style="width:616px"/></td>
								    </tr>
								    
								    <tr>
										<td class="formTable">DB_USERNAME</td>
										<td align="left"><html:text property="dbUsername" maxlength="20"/></td>
										<td class="formTable">DB_PASSWORD</td>
										<td align="left"><html:password property="dbPassword" maxlength="20"/></td>
								    </tr>
								    
								    <tr>
										<td class="formTable">${mr['page.common.Parameter']}</td>
										<td align="left" colspan="3"><html:textarea property="params" rows="8" cols="120"></html:textarea></td>
								    </tr>
									
								    <tr>
										<td class="formTable">${mr['page.common.configuration']}</td>
										<td align="left" colspan="3"><html:textarea property="updateScript" rows="8" cols="120"></html:textarea></td>
								    </tr>
									
									<tr>
										<td class="formTable">${mr['page.common.memo']}</td>
										<td align="left" colspan="3"><html:textarea property="remark" rows="3" cols="120"></html:textarea></td>
									</tr>
									
									
									
									<tr>
										<td class="formTable">${mr['page.common.createdBy']}</td>
										<td align="left"><html:text property="createdBy" disabled="true"/></td>
										<td class="formTable">${mr['page.common.createdTime']}</td>
										<td align="left"><html:text property="created" disabled="true"/></td>
									</tr>
									<tr>
										<td class="formTable">${mr['page.common.updatedBy']}</td>
										<td align="left"><html:text property="updatedBy" disabled="true"/></td>
										<td class="formTable">${mr['page.common.updatedTime']}</td>
										<td align="left"><html:text property="updated" disabled="true"/></td>
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
		<html:javascript formName="dmsClientForm" staticJavascript="false" dynamicJavascript="true" cdata="false" />
		<script type="text/javascript" src="${ctx}/scripts/validator.jsp"></script>
	</body>
</html>
