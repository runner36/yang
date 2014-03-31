<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<%@ include file="/commons/meta.jsp" %>
		<title>${mr['page.customize.title.prodMapMaint']}</title>
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
			var form = document.dmsProdMappingForm;
			loading();
			form.submit();
		}
		function selectClient() {
			var form = document.dmsProdMappingForm;
			var v = openClientTree('${ctx}', '1','1',form.clientId_.value);
			if (v) {
				form.clientId_.value = v.id;
				form.clientName_.value = v.text;
			}
		}
		</script>
	</head>
	<body onload="WindowSollAuto()" onresize="WindowSollAuto()"><jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
	
		<html:form action="/server/dmsProdMapping.do?method=list" method="post">
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						<!--TitleStrat-->
						${mr['page.customize.title.prodMapMaint']}
						<!--TitleStrat-->
					</h4>
					<div class='MenuList'>
						<!--MenuStrat-->
						<a href="javascript:showQuery_('query_A');" id="showQuery_A" >${mr['page.common.button.setquery']}</a>
						<a href="javascript:hideQuery_('query_A');" id="hideQuery_A" >${mr['page.common.button.close']}</a>
						<a href="javascript:openImportForm('${ctx}', 'DmsProdMapping');">${mr['page.common.button.import']}</a>
						<a href="javascript:dmsProdMappingForm.ec_p.value=1;query()" id="query_A">${mr['page.common.button.query']}</a>
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
										<td width="1%">${mr['page.common.distProdCode']}</td>
										<td width="1%">
											<html:text name="ec" property="distProdCode_"/>
										</td>
										<td width="1%">${mr['page.common.distProdName']}</td>
										<td >
											<html:text name="ec" property="distProdName_"/>
										</td>
										<td width="1%">
											${mr['page.common.mappingDate']}
										</td>
										<td>
											<html:text name="ec" property="mapping_start" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>-<html:text name="ec" property="mapping_end" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>
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
											<td width="1%">${mr['page.common.pordStandardCode']}</td>
											<td width="1%">
												<html:text name="ec" property="prodCode_"/>
											</td>
											<td width="1%">${mr['page.common.pordStandardName']}</td>
											<td><html:text name="ec" property="prodName_"/>
											</td>
											<td width="1%">${mr['page.common.lastChangeDate']}</td>
											<td>
											<html:text name="ec" property="updated_start" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>-<html:text name="ec" property="updated_end" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>
										</td>
									</tr>
									<tr>
										<td width="1%">${mr['page.common.distCode']}</td>
										<td width="1%">
											<html:text name="ec" property="dist_code"/>
										</td>
										<td width="1%">${mr['page.common.distName']}</td>
										<td width="1%">
											<html:text name="ec" property="dist_name"/>
										</td>
										<td width="1%">${mr['page.common.mastProdType']}</td>
										<td width="1%">
											<html:select name="ec" property="prodTypeId_"  >
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
										<td width="1%">
											${mr['page.common.createdTime']}
										</td>
										<td>
											<html:text name="ec" property="created_start" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>-<html:text name="ec" property="created_end" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>
										</td>
									</tr>
									
									<tr>
										<td width="1%">${mr['page.common.activeProdSource']}</td>
										<td width="1%">
											<html:select name="ec" property="active_Prod_Source" style="width: 84px">
												<html:option value=""></html:option>
												<html:option value="SP">${mr['page.common.invoicing']}</html:option>
												<html:option value="IO">${mr['page.common.onlyInventory']}</html:option>
											</html:select>
										</td>
										<td width="1%"></td>
										<td width="1%">											
										</td>
										<td width="1%"></td>
										<td width="1%">											
										</td>
										<td width="1%">											
										</td>
										<td>											
										</td>
									</tr>
								</table>
								</div>
								<ec:table items="list" var="item" onInvokeAction="query()"
									form="dmsProdMappingForm" retrieveRowsCallback="limit"
									style="width:100%" tableId="ec" sortRowsCallback="limit"
									action="dmsProdMapping.do?method=list">
									<ec:exportXls fileName="${mr['page.customize.title.prodMapMaint']}.xls" tooltip="${mr['page.common.exportExcel']}" view="xls" />
									<ec:exportCsv fileName="${mr['page.customize.title.prodMapMaint']}.csv" tooltip="${mr['page.common.exportCVS']}" view="csv" />
										<ec:row highlightRow="true" >
										<ec:column property="18" title="ID"  />
									 	<ec:column property="0" title="${mr['page.common.orgName']}"  />
										<ec:column property="1" title="${mr['page.common.distCode']}"/>
										<ec:column property="2" title="${mr['page.common.distName']}"/>
										<ec:column property="3" title="${mr['page.common.clientCode']}"/>
										<ec:column property="dmsClient" title="${mr['page.common.clientName']}" alias="clientName">
										</ec:column>
										<ec:column property="5" escapeAutoFormat="true" title="${mr['page.common.distProdCode']}"/>
										<ec:column property="6" escapeAutoFormat="true" title="${mr['page.common.distProdBarcode']}"/>
										<ec:column property="7" title="${mr['page.common.distProdName']}"/>
										<ec:column property="8" title="${mr['page.common.distProdUnit']}"/>
										<ec:column property="9" escapeAutoFormat="true" title="${mr['page.common.pordStandardCode']}"/>
										<ec:column property="10" title="${mr['page.common.pordStandardName']}"/>
										<ec:column property="11" title="${mr['page.common.prodStandardBarcode']}" />
										<ec:column property="12" title="${mr['page.common.mastProdType']}"/>
										<ec:column property="13" title="${mr['page.common.pordStandardUnit']}"/>
										<ec:column property="14" title="${mr['page.common.createdTime']}" cell="date" format="yyyy-MM-dd"/>
										<ec:column property="15" title="${mr['page.common.mappingDate']}" cell="date" format="yyyy-MM-dd"/>
										<ec:column property="16" title="${mr['page.common.lastChangeDate']}" cell="date" format="yyyy-MM-dd"/>
										<ec:column property="17" title="${mr['page.common.updatedBy']}"  />
										<ec:column property="20" title="${mr['page.common.activeProdSource']}"  sortable="false"/>
										<ec:column property="" title="${mr['page.common.mappingStatus']}"  value="${item[19]!=null ? '已匹配':'未匹配' }" alias="prodstate" />
										<ec:column property="null" width="1%" title="${mr['page.common.button.oper']}" sortable="false" viewsAllowed="html">
											<center>
												<html:link page="/server/dmsProdMapping.do?method=edit&activeId=${item[18]}&activePId=${item[19]}">
													<img src="${ctx}/images/edit.gif" border="0" alt="${mr['page.common.button.edit']}" title="${mr['page.common.button.edit']}" />
												</html:link>
												&nbsp;
													<c:if test="${item[19]!=null}">
														<html:link
															page="/server/dmsProdMapping.do?method=delete&activePId=${item[19]}" onclick="return confirm('${mr['page.common.mess.delete']}')">
															<img src="${ctx}/images/delete.gif" border="0" alt="${mr['page.common.button.delete']}" title="${mr['page.common.button.delete']}" />
														</html:link>
													</c:if>
											</center>
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
