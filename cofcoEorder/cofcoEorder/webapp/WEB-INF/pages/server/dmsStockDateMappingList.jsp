<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>${mr['page.customize.title.distStockTime']}</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/widgets/extremecomponents/extremecomponents.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>
		<script language="javaScript" src="${ctx}/scripts/dms.js"></script>
		<script language="javaScript" src="${ctx}/scripts/jquery-1.2.6.min.js"></script>
		<script language="javaScript" src="${ctx}/scripts/yearMonth_Private.js"></script>
		<script language="javaScript" src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
		<script>
		var xmlhttp;
		var pid;
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
	    function query() {
			resetec();
			var form = document.dmsStockDateMappingForm;
			form.submit();
		}
		function selectDist() {
			var form = document.dmsStockDateMappingForm;
			var v = openDistTree('${ctx}', '', '1', form.distId_.value);
			if (v) {
				form.distId_.value = v.id;
				form.distName_.value = v.text;
			}
		}
		function selectClient() {
			var form = document.dmsStockDateMappingForm;
			var v = openClientTree('${ctx}', '1');
			if (v) {
				form.$eq_dmsClient_clientId.value = v.id;
				form.clientName.value = v.text;
			}
		}
		function selectOrg() {
			var form = document.dmsStockDateMappingForm;
			var v = openOrgTree('${ctx}');
			if (v) {
				form.$lk_dmsClient_mdmDistributor_baseOrg_subCode.value = v.subCode;
				form.orgName_.value = v.text;
			}
		}
		function changeStockDate(id,strDate) {
			//alert (id);
			createXMLHttpRequest();
			xmlhttp.open("post", encodeURI("${ctx}/server/dmsStockDateMapping.do?method=changeStockDate&id=" + id + "&strDate=" + strDate), true);
			xmlhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
			xmlhttp.send(null);			
			
		}
		
  		</script>
	</head>
	<body onload="WindowSollAuto()"><jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/server/dmsStockDateMapping.do?method=list" method="post">
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						<!--TitleStrat-->
						${mr['page.customize.title.distStockTime']}
						<!--TitleStrat-->
					</h4>
					<div class='MenuList'>
						<!--MenuStrat-->
						<a href="javascript:showQuery_('query_A');" id="showQuery_A" >${mr['page.common.button.setquery']}</a>
						<a href="javascript:hideQuery_('query_A');" id="hideQuery_A" >${mr['page.common.button.close']}</a>
						<a href="javascript:query()" id="query_A">${mr['page.common.button.query']}</a>
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
										<td width="1%">
											${mr['page.common.distName']}
										</td>
										<td width="1%">
											<html:hidden name="ec"  property="distId_"/>
											<html:text name="ec" property="distName_" styleClass="select_but" onclick="selectDist()" readonly="true"/>
										</td>
										<td width="1%">
											${mr['page.common.distCode']}
										</td>
										<td width="1%">
											<html:text name="ec" property="distCode_"/>
										</td>
										<td width="1%">
											${mr['page.common.billDate']}
											<!--<input type="text" onpropertychange="test()"  property="$ge_billDate" onfocus="WdatePicker()" readonly="true" Class="date_but"/>
										--></td>
										<td>
											
											<html:text name="ec" property="ge_billDate" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>-<html:text name="ec" property="le_billDate" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>
										</td>
									</tr>
									
								</table>
								</div>
								<ec:table items="list" var="item" onInvokeAction="query()"
										form="dmsStockDateMappingForm" retrieveRowsCallback="limit"
										style="width:100%" tableId="ec" sortRowsCallback="limit"
										action="dmsStockDateMapping.do?method=list">
									<ec:exportXls fileName="${mr['page.customize.title.distStockTime']}.xls" tooltip="${mr['page.common.exportExcel']}" view="xls" />
									<ec:exportCsv fileName="${mr['page.customize.title.distStockTime']}.csv" tooltip="${mr['page.common.exportCVS']}" view="csv" />
									<ec:row highlightRow="true">
										<ec:column property="1" title="${mr['page.common.distCode']}" />
										<ec:column property="2" title="${mr['page.common.distName']}" />										
										<ec:column property="3" title="${mr['page.common.billDate']}" cell="date" format="yyyy-MM-dd"/>
										
										<ec:column property="a" title="${mr['page.common.mappingStockDate']}" viewsDenied="html"> </ec:column>
										<ec:column property="4" title="${mr['page.common.mappingStockDate']}" viewsAllowed="html" style="text-align:center" >
											<html:text name="ec" property="" size="9"  onfocus="showTable(this,'${item[0]}')" value="${item[4]}"  readonly="true"  styleClass="date_but"/>
										</ec:column>
										
										<ec:column property="5" title="${mr['page.common.createdTime']}" cell="date" format="yyyy-MM-dd HH:mm"/>
										<ec:column property="6" title="${mr['page.common.updatedTime']}" cell="date" format="yyyy-MM-dd HH:mm"/>
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
