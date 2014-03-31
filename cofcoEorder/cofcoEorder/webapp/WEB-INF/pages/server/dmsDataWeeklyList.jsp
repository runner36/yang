<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>${mr['page.customize.title.dmsDataValidationWeek']}</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/widgets/extremecomponents/extremecomponents.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>
		<script language="javaScript" src="${ctx}/scripts/dms.js"></script>
		<script type="text/javascript" src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
		<script>
	    function query() {
			resetec();
			var form = document.dmsDataWeeklyForm;
			form.submit();
		}
		function add() {
		    location='dmsDataWeekly.do?method=add';
		}
		function selectDist() {
			var form = document.dmsDataWeeklyForm;
			form.$eq_dmsClient_mdmDistributor_distId.value = '';
			form.distName.value = '';
			var v = openDistTree('${ctx}');
			if (v) {
				form.$eq_dmsClient_mdmDistributor_distId.value = v.id;
				form.distName.value = v.text;
			}
		}
		function selectOrg() {
			var form = document.dmsDataWeeklyForm;
			form.$lk_dmsClient_mdmDistributor_baseOrg_subCode.value = '';
			form.orgName_.value = '';
			var v = openOrgTree('${ctx}');
			if (v) {
				form.$lk_dmsClient_mdmDistributor_baseOrg_subCode.value = v.subCode;
				form.orgName_.value = v.text;
			}
		}
  		</script>
	</head>
	<body onload="WindowSollAuto()"><jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/server/dmsDataWeekly.do?method=list" method="post">
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						<!--TitleStrat-->
						${mr['page.customize.title.dmsDataValidationWeek']}
						<!--TitleStrat-->
					</h4>
					<div class='MenuList'>
						<!--MenuStrat-->
						<a href="javascript:showQuery_('query_A');" id="showQuery_A" >${mr['page.common.button.setquery']}</a>
						<a href="javascript:hideQuery_('query_A');" id="hideQuery_A" >${mr['page.common.button.close']}</a>
						<a href="javascript:dmsDataWeeklyForm.ec_p.value=1;query()" id="query_A">${mr['page.common.button.query']}</a>
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
											<html:hidden property="$lk_dmsClient_mdmDistributor_baseOrg_subCode"/>
											<html:text property="orgName_" styleClass="select_but" onclick="selectOrg()" readonly="true"/>
										</td>
										<td width="1%">
											${mr['page.common.saleDate']}
										</td>
										<td width="1%">
											<html:text name="ec" property="$ge_saleDate" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>-<html:text name="ec" property="$le_saleDate" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>
										</td>
										<td width="1%">
											${mr['page.common.distName']}
										</td>
										<td width="1%">                     
											<html:hidden property="$eq_dmsClient_mdmDistributor_distId"/>
											<html:text property="distName" styleClass="select_but" onclick="selectDist()" readonly="true"/>
										</td>
										<td width="1%">
											${mr['page.common.clientCode']}
										</td>
										<td>
											<html:text name="ec" property="$eq_dmsClient_clientCode"/>
										</td>
									</tr>
									<tr>
										<td width="1%">
											${mr['page.common.storeId']}
										</td>
										<td width="1%">
											<html:text name="ec" property="$lk_storeCode"/>
										</td>
										<td width="1%">
											${mr['page.common.uploadedDate']}
										</td>
										<td width="1%">
											<html:text name="ec" property="$ge_uploadTime" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>-<html:text name="ec" property="$le_uploadTime" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>
										</td>
										<td width="1%">
											${mr['page.common.differenceCase']}
										</td>
										<td>
											<html:select property="diff" style="width: 104px">
												<html:option value=""></html:option>
												<html:option value="0">无差异</html:option>
												<html:option value="1">有差异</html:option>
											</html:select>
										</td>
									</tr>
								</table>
								</div>
								<ec:table items="list" var="item" onInvokeAction="query()"
										form="dmsDataWeeklyForm" retrieveRowsCallback="limit"
										style="width:100%" tableId="ec"
										action="dmsDataWeekly.do?method=list">
									<ec:exportXls fileName="${mr['page.customize.title.dmsDataValidationWeek']}.xls" tooltip="${mr['page.common.exportExcel']}" view="xls" />
									<ec:row highlightRow="true">
										<ec:column property="dmsClient.mdmDistributor.baseOrg.orgName" title="${mr['page.common.orgName']}" alias="orgName"/>
										<ec:column property="dmsClient.mdmDistributor.distName" title="${mr['page.common.distName']}" alias="distName"/>
										<ec:column property="dmsClient.mdmDistributor.distCode" title="${mr['page.common.distCode']}" alias="distcode"/>
										<ec:column property="dmsClient.clientCode" title="${mr['page.common.clientCode']}" alias="clientcode"/>
										
										<ec:column property="storeCode" title="${mr['page.common.storeId']}"/>
										<ec:column property="saleDate" title="${mr['page.common.saleDate']}" cell="date" format="yyyy-MM-dd"/>
										<ec:column property="saleQty" title="${mr['page.common.saleQuantity']}" cell="number" format="0" style="text-align: right;"/>
										<ec:column property="saleAmt" title="${mr['page.common.saleAmount']}" cell="number" format="0.00" style="text-align: right;"/>
										<ec:column property="uploadTime" title="${mr['page.common.uploadTime']}" cell="date" format="yyyy-MM-dd HH:mm"/>
										
										<ec:column property="hisSaleQty" title="${mr['page.common.sysSaleQuantity']}" cell="number" format="0" style="text-align: right;"/>
										<ec:column property="hisSaleAmt" title="${mr['page.common.sysSaleAmount']}" cell="number" format="0.00" style="text-align: right;"/>
										<ec:column property="saleQtyDiff" title="${mr['page.common.saleQtyDiff']}" cell="number" format="0" style="text-align: right;"/>
										<ec:column property="saleAmtDiff" title="${mr['page.common.saleAmountDiff']}" cell="number" format="0.00" style="text-align: right;"/>
										<ec:column property="checkTime" title="${mr['page.common.checkTime']}" cell="date" format="yyyy-MM-dd HH:mm"/>
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
