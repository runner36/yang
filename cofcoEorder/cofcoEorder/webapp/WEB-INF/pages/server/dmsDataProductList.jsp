<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<%@ include file="/commons/meta.jsp" %>
		<title>${mr['page.customize.title.dmsDataProduct']}</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/widgets/extremecomponents/extremecomponents.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>
		<script language="javaScript" src="${ctx}/scripts/dms.js"></script>
		<script type="text/javascript" src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
		<script>
	    function query(str) {
			resetec();
			var form = document.dmsDataProductForm;
			form.$eq_state.disabled=false;
			loading();
			form.submit();
		}
		function selectDist() {
			var form = document.dmsDataProductForm;
			var v = openDistTree('${ctx}', '', '1', form.$in_dmsClient_mdmDistributor_distId.value);
			if (v) {
				form.$in_dmsClient_mdmDistributor_distId.value = v.id;
				form.distName_.value = v.text;
			}
		}
		function selectClient() {
			var form = document.dmsDataProductForm;
			var v = openClientTree('${ctx}', '1');
			if (v) {
				form.$eq_dmsClient_clientId.value = v.id;
				form.clientName.value = v.text;
			}
		}
		function selectOrg() {
			var form = document.dmsDataProductForm;
			var v = openOrgTree('${ctx}');
			if (v) {
				form.$lk_dmsClient_mdmDistributor_baseOrg_subCode.value = v.subCode;
				form.orgName_.value = v.text;
			}
		}
		function changeChangeState(){
			var form=document.dmsDataProductForm;
			if(form.$nl_deleteTime.value==""){
				form.$eq_state.disabled=false;
				form.$ge_deleteTime.disabled=false;
				form.$le_deleteTime.disabled=false;
			}else{
				form.$eq_state.selectedIndex=2;
				form.$eq_state.disabled=true;
				form.$ge_deleteTime.value="";
				form.$ge_deleteTime.disabled=true;
				form.$le_deleteTime.value="";
				form.$le_deleteTime.disabled=true;
			}			
		}
		function disabledInit(){
			var form=document.dmsDataProductForm;
			if(form.$nl_deleteTime.value=='1'){				
				form.$eq_state.disabled=true;
			}
		}
  		</script>
	</head>
	<body onload="WindowSollAuto();disabledInit();"><jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/server/dmsDataProduct.do?method=list" method="post">
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						<!--TitleStrat-->
						${mr['page.customize.title.dmsDataProduct']}
						<!--TitleStrat-->
					</h4>
					<div class='MenuList'>
						<!--MenuStrat-->
						<a href="javascript:showQuery_('query_A');" id="showQuery_A" >${mr['page.common.button.setquery']}</a>
						<a href="javascript:hideQuery_('query_A');" id="hideQuery_A" >${mr['page.common.button.close']}</a>
						<a href="javascript:dmsDataProductForm.ec_p.value=1;query()" id="query_A">${mr['page.common.button.query']}</a>
						<a href="javascript:location.reload();">${mr['page.common.button.refresh']}</a>
						<!--MenuEnd-->
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
								<div class="searchbox" id="searchbox">
								<table>
									<tr>
										<td width="1%">${mr['page.common.orgName']}</td>
										<td width="1%">
											<html:hidden name="ec" property="$lk_dmsClient_mdmDistributor_baseOrg_subCode"/>
											<html:text name="ec" property="orgName_" styleClass="select_but" onclick="selectOrg()" readonly="true"/>
										</td>
										<td width="1%">
											${mr['page.common.distName']}
										</td>
										<td width="1%">
											<html:hidden name="ec" property="$in_dmsClient_mdmDistributor_distId"/>
											<html:text name="ec" property="distName_" styleClass="select_but" onclick="selectDist()" readonly="true"/>
										</td>
										<td width="1%">
											${mr['page.common.distCode']}
										</td>
										<td width="1%">
											<html:text name="ec" property="$eq_dmsClient_mdmDistributor_distCode"/>
										</td>
										<td width="1%">
											${mr['page.common.updatedTime']}
										</td>
										<td colspan="2">
											<html:text name="ec" property="$ge_updateTime" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>-<html:text name="ec" property="$le_updateTime" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>
										</td>
										
									</tr>
									<tr>
										<td width="1%">
											${mr['page.common.distProdName']}
										</td>
										<td width="1%">
											<html:text name="ec" property="$lk_prodName"/>
										</td>
										<td width="1%">
											${mr['page.common.distProdCode']}
										</td>
										<td width="1%">
											<html:text name="ec" property="$lk_prodCode"/>
										</td>
										<td width="1%">
											${mr['page.common.productBarcode']}
										</td>
										<td>
											<html:text name="ec" property="$lk_prodBarcode"/>
										</td>
										<td width="1%">
											${mr['page.common.deleteTime']}
										</td>
										<td>
											<html:text name="ec" property="$ge_deleteTime" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>-<html:text name="ec" property="$le_deleteTime" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>
										</td>
										
									</tr>
									<tr>
										<td width="1%">
											${mr['page.common.clientName']}
										</td>
										<td width="1%">
											<html:hidden name="ec" property="$eq_dmsClient_clientId"/>
											<html:text name="ec" property="clientName" styleClass="select_but" onclick="selectClient()" readonly="true"/>
										</td>
										<!--<td width="1%">
											${mr['page.common.clientCode']}
										</td>
										<td width="1%">
											<html:text name="ec" property="$eq_dmsClient_clientCode"/>
										</td>
										-->
										<td width="1%">
											${mr['page.common.isValid']}
										</td>
										<td>
											<html:select property="$eq_state" style="width: 104px">
												<html:option value=""></html:option>
												<html:option value="1">${mr['page.common.yes']}</html:option>
												<html:option value="0">${mr['page.common.no']}</html:option>
											</html:select>
										</td>
										<td width="1%">
											${mr['page.common.onlyDefectiveData']}
										</td>
										<td colspan="5">
											<html:select  name="ec" property="$nl_deleteTime" style="width: 105px" onchange="changeChangeState();">
												<html:option value="">${mr['page.common.no']}</html:option>
												<html:option value="1">${mr['page.common.yes']}</html:option>												
											</html:select>
										</td>
									</tr>
									
								</table>
								</div>
								<ec:table items="list" var="item" onInvokeAction="query()"
										form="dmsDataProductForm" retrieveRowsCallback="limit"
										style="width:100%" tableId="ec"
										action="dmsDataProduct.do?method=list">
									<ec:exportXls fileName="${mr['page.customize.title.dmsDataProduct']}.xls" tooltip="${mr['page.common.exportExcel']}" view="xls" />
									<ec:exportCsv fileName="${mr['page.customize.title.dmsDataProduct']}.csv" tooltip="${mr['page.common.exportCVS']}" view="csv" />
									<ec:row highlightRow="true">
										<ec:column property="dmsClient.mdmDistributor.baseOrg.pnall" title="${mr['page.common.orgName']}" alias="orgName"/>
										<ec:column property="dmsClient.mdmDistributor.distName" title="${mr['page.common.distName']}" alias="distName"/>
										<ec:column property="dmsClient.mdmDistributor.distCode" title="${mr['page.common.distCode']}" alias="distCode"/>
										<ec:column property="dmsClient.clientCode" title="${mr['page.common.clientCode']}" alias="clientCode"/>
										<ec:column property="1" title="${mr['page.common.clientName']}" alias="clientName"  sortable="false">
											${item.dmsClient.mdmDistributor.distName}(${item.dmsClient.clientCode})
										</ec:column>
										<ec:column property="prodName" title="${mr['page.common.distProdName']}" />
										<ec:column property="prodCode" title="${mr['page.common.distProdCode']}" />
										<ec:column property="prodBarcode" title="${mr['page.common.productBarcode']}"/>
										<ec:column property="prodSpec" title="${mr['page.common.distProdSpeci']}"/>
										<ec:column property="prodUnit" title="${mr['page.common.distProdUnit']}" />
										<ec:column property="prodPrice" title="${mr['page.common.distProdPrice']}" cell="number" format="0.000" style="text-align:right"/>
										<ec:column property="prodNum" title="${mr['page.common.prodLotNumber']}" />
										<ec:column property="prodState" title="${mr['page.common.mappingStatus']}" />
										<ec:column property="remark" title="${mr['page.common.memo']}" />
										<ec:column property="createTime" title="${mr['page.common.createdTime']}" cell="date" format="yyyy-MM-dd"/>
										<ec:column property="updateTime" title="${mr['page.common.updatedTime']}" cell="date" format="yyyy-MM-dd HH:mm"/>
										<ec:column property="deleteTime" title="${mr['page.common.deleteTime']}" cell="date" format="yyyy-MM-dd HH:mm"/>
										<ec:column property="state" value="${item.state==1 ? '有效':'无效'}" title="${mr['page.common.status']}" />
									</ec:row>
								</ec:table>
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
