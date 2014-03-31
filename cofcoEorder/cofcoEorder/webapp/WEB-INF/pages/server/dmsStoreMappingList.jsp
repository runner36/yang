<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>${mr['page.customize.title.storeMappingMaint']}</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/widgets/extremecomponents/extremecomponents.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/dms.js"></script>
		<script language="javaScript" src="${ctx}/scripts/jquery-1.2.6.min.js"></script>
		<script type="text/javascript" src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
		<script>
	    function query() {
			resetec();
			var form = document.dmsStoreMappingForm;
			form.submit();
		}
		function add() {
		    location='dmsStoreMapping.do?method=add';
		}
		function selectClient() {
			var form = document.dmsStoreMappingForm;
			var v = openClientTree('${ctx}', '1','1',form.clientId_.value);
			if (v) {
				form.clientId_.value = v.id;
				form.clientName_.value = v.text;
			}
		}
		</script>
	</head>
	<body onload="WindowSollAuto()" onresize="WindowSollAuto()"><jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/server/dmsStoreMapping.do?method=list" method="post">
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						<!--TitleStrat-->
						${mr['page.customize.title.storeMappingMaint']}
						<!--TitleStrat-->
					</h4>
					<div class='MenuList'>
						<!--MenuStrat-->
						<a href="javascript:showQuery_('query_A');" id="showQuery_A" >${mr['page.common.button.setquery']}</a>
						<a href="javascript:hideQuery_('query_A');" id="hideQuery_A" >${mr['page.common.button.close']}</a>
						<a href="javascript:dmsStoreMappingForm.ec_p.value=1;query()" id="query_A">${mr['page.common.button.query']}</a>
						<a href="javascript:openImportForm('${ctx}', 'DmsStoreMapping');">${mr['page.common.button.import']}</a>
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
										<td width="1%">${mr['page.common.clientName']}</td>
										<td width="1%">
											<html:hidden name="ec" property="clientId_"/>
											<html:text name="ec" property="clientName_" styleClass="select_but" onclick="selectClient()" readonly="true"/>
										</td>
										<td width="1%">${mr['page.common.distStoreCode']}</td>
										<td width="1%">
											<html:text name="ec" property="distStoreCode_"/>
										</td>
										<td width="1%">${mr['page.common.distStoreName']}</td>
										<td width="1%">
											<html:text name="ec" property="distStoreName_"/>
										</td>
											<td width="1%">${mr['page.common.lastChangeDate']}</td>
											<td>
											<html:text name="ec" property="updated_start" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>-<html:text name="ec" property="updated_end" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>
										</td>
										</tr>
										<tr>
											<td width="1%">${mr['page.common.mappingStatus']}</td>
											<td width="1%">
												<html:select name="ec" property="state_" style="width: 84px">
													<html:option value=""></html:option>
													<html:option value="1">${mr['page.common.hasBeenMapping']}</html:option>
													<html:option value="2">${mr['page.common.doesNotMapping']}</html:option>
												</html:select>
											</td>
											<td width="1%">${mr['page.common.custStandardCode']}</td>
											<td width="1%">
												<html:text name="ec" property="storeCode_"/>
											</td>
											<td width="1%">${mr['page.common.custStandardName']}</td>
											<td width="1%">
												<html:text name="ec" property="storeName_"/>
											</td>
											<td width="1%">
												${mr['page.common.createdTime']}
											</td>
											<td>
												<html:text name="ec" property="created_start" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>-<html:text name="ec" property="created_end" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>
											</td>
										</tr>
										<tr>
											<td width="1%">
											${mr['page.common.mappingDate']}
											</td>
											<td colspan="7">
												<html:text name="ec" property="mapping_start" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>-<html:text name="ec" property="mapping_end" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>
											</td>
										</tr>
								</table>
								</div>
								<ec:table items="list" var="item" onInvokeAction="query()"
									form="dmsStoreMappingForm" retrieveRowsCallback="limit"  sortRowsCallback="limit" 
									style="width:100%" tableId="ec"
									action="dmsStoreMapping.do?method=list">
									<ec:exportXls fileName="${mr['page.customize.title.storeMappingMaint']}.xls" tooltip="${mr['page.common.exportExcel']}" view="xls" />
									<ec:exportCsv fileName="${mr['page.customize.title.storeMappingMaint']}.csv" tooltip="${mr['page.common.exportCVS']}" view="csv" />
									<ec:row highlightRow="true">
										<ec:column property="13" title="ID"  />
										<ec:column property="5" title="${mr['page.common.clientCode']}"/>
										<ec:column property="2" title="${mr['page.common.distCode']}"/>
										<ec:column property="3" title="${mr['page.common.distName']}"/>
										<ec:column property="6" escapeAutoFormat="true" title="${mr['page.common.distStoreCode']}"/>
										<ec:column property="7" title="${mr['page.common.distStoreName']}"/>
										<ec:column property="15" title="${mr['page.common.contact']}" />
										<ec:column property="16" title="${mr['page.common.telephone']}" />
										<ec:column property="8" title="${mr['page.common.distStoreAddress']}" />
										<ec:column property="9" title="${mr['page.common.custStandardCode']}" />
										<ec:column property="10" title="${mr['page.common.custStandardName']}" />
										<ec:column property="" title="${mr['page.common.mappingStatus']}"  value="${item[0]!=null ? '已匹配':'未匹配'}" alias="prodstate" />
										<ec:column property="11" title="${mr['page.common.createdTime']}" cell="date" format="yyyy-MM-dd"/>
										<ec:column property="12" title="${mr['page.common.mappingDate']}" cell="date" format="yyyy-MM-dd"/>
										<ec:column property="14" title="${mr['page.common.lastChangeDate']}" cell="date" format="yyyy-MM-dd"/>
										<ec:column property="null" width="1%" title="${mr['page.common.button.oper']}" sortable="false"  viewsDenied="xls,csv">
											<left>
												<html:link page="/server/dmsStoreMapping.do?method=edit&activeId=${item[13]}&activeSId=${item[0]}">
													<img src="${ctx}/images/edit.gif" border="0" alt="${mr['page.common.button.edit']}" title="${mr['page.common.button.edit']}" />
												</html:link>
												&nbsp;
												<c:if test="${item[0]!=null}">
												<html:link
													page="/server/dmsStoreMapping.do?method=delete&activeSId=${item[0]}" onclick="return confirm('${mr['page.common.mess.delete']}')">
													<img src="${ctx}/images/delete.gif" border="0" alt="${mr['page.common.button.delete']}" title="${mr['page.common.button.delete']}" />
												</html:link>
												</c:if>
											</left>
										</ec:column>
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

