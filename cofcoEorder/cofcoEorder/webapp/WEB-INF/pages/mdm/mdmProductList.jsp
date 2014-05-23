<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<%@ include file="/commons/meta.jsp" %>
		<title>${mr['page.customize.title.prodMastdataMaint']}</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/widgets/extremecomponents/extremecomponents.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
	<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/dms.js"></script>
		<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>
		<script type="text/javascript" src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
		<script>
	    function query() {
			resetec();
			var form = document.mdmProductForm;
			loading();
			form.submit();
		}
		function add() {
		    location='mdmProduct.do?method=add';
		}
		function selectDict(dictName, id, name) {
			var v = openDictTree('${ctx}', dictName,'2','1',id.value);
			if (v) {
				id.value = v.id;
				name.value = v.text;
			}
		}
		</script>
	</head>
	<body onload="WindowSollAuto()" onresize="WindowSollAuto()"><jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/mdm/mdmProduct.do?method=list" method="post">
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						<!--TitleStrat-->
						${mr['page.customize.title.prodMastdataMaint']}
						<!--TitleStrat-->
					</h4>
					<div class='MenuList'>
						<!--MenuStrat-->
						<a href="javascript:showQuery_('query_A');" id="showQuery_A"
							>${mr['page.common.button.setquery']}</a>
						<a href="javascript:hideQuery_('query_A');" id="hideQuery_A"
							>${mr['page.common.button.close']}</a>
						<a href="javascript:query()" id="query_A">${mr['page.common.button.query']}</a>
						<a href="mdmProduct.do?method=add">${mr['page.common.button.add']}</a>
						<a href="javascript:openImportForm('${ctx}', 'MdmProduct');">${mr['page.common.button.import']}</a>
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
											${mr['page.common.mastProdCode']}
										</td>
										<td width="1%">
											<html:text name="ec" property="$lk_prodCode"/>
										</td>
										
										<td width="1%">
											${mr['page.common.mastProdName']}
										</td>
										<td width="1%">
											<html:text name="ec" property="$lk_prodName"/>
										</td>
										<td width="1%">
											${mr['page.common.productBarcode']}
										</td>
										<td width="1%">
											<html:text name="ec" property="$lk_prodBarcode"/>
										</td>
										<td width="1%">${mr['page.common.createdTime']}</td>
											<td>
												<html:text name="ec" property="$ge_created" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>
												-<html:text name="ec" property="$le_created" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>
											</td>
									</tr>
										<tr>
										<td width="1%">
											${mr['page.common.mastProdBrand']}
										</td>
										<td>
											<html:hidden name="ec" property="$in_itemBrand_dictItemId"/>
											<html:text name="ec" property="brandName_" readonly="true" styleClass="select_but" onclick="selectDict('prodBrand',mdmProductForm.$in_itemBrand_dictItemId,mdmProductForm.brandName_)"/>
										</td>
										<td width="1%">
											${mr['page.common.mastProdType']}
										</td>
										<td width="1%">
											<html:select name="ec" property="$eq_dictProdType_dictItemId"  >
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
											${mr['page.common.mastProdSeries']}
										</td>
										<td width="1%">
											<html:hidden name="ec" property="$in_itemType_dictItemId"/>
											<html:text name="ec" property="typeName_" readonly="true" styleClass="select_but" onclick="selectDict('prodType',mdmProductForm.$in_itemType_dictItemId,mdmProductForm.typeName_)"/>
										</td>
										<td width="1%">
											${mr['page.common.updatedTime']}
										</td>
										<td >
											<html:text name="ec" property="$ge_updated" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>-<html:text name="ec" property="$le_updated" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>
										</td>
										</tr>
								</table>
							</div>
								<ec:table items="list" var="item" onInvokeAction="query()"
									form="mdmProductForm" retrieveRowsCallback="limit"
									style="width:100%" tableId="ec"
									action="mdmProduct.do?method=list">
									<ec:exportXls fileName="物料主数据.xls" tooltip="${mr['page.common.exportExcel']}" view="xls" />
									<ec:exportCsv fileName="物料主数据.csv" tooltip="${mr['page.common.exportCVS']}" view="csv" />
									<ec:row highlightRow="true">
										<ec:column property="null" width="1%" title="${mr['page.common.button.oper']}" sortable="false" viewsDenied="xls,csv">
											<center>
												<html:link page="/mdm/mdmProduct.do?method=edit&prodId=${item.prodId}">
													<img src="${ctx}/images/edit.gif" border="0" alt="${mr['page.common.button.edit']}" title="${mr['page.common.button.edit']}" />
												</html:link>
												&nbsp;
												<html:link page="/mdm/mdmProduct.do?method=delete&prodId=${item.prodId}" onclick="return confirm('${mr['page.common.mess.delete']}')">
													<img src="${ctx}/images/delete.gif" border="0" alt="${mr['page.common.button.delete']}" title="${mr['page.common.button.delete']}" />
												</html:link>
											</center>
										</ec:column>
										<ec:column property="prodCode" escapeAutoFormat="true" title="${mr['page.common.mastProdCode']}"/>
										<ec:column property="prodName" title="${mr['page.common.mastProdName']}"/>
										<ec:column property="itemBrand.itemName" title="${mr['page.common.mastProdBrand']}" alias="brandName"/>
										
										<ec:column property="prodSTRU.itemName" title="${mr['page.common.prodStructure']}" alias="prodSTRUName"/>
										<ec:column property="prodCountUnit.itemName" title="${mr['page.common.prodStaticsUnit']}" alias="prodCountUnitName"/>
										<ec:column property="state" title="产品状态"/>
										<%-- <ec:column property="prodNameEn" title="${mr['page.common.mastProdEngName']}"/>
										<ec:column property="prodAlias" title="${mr['page.common.mastProdAlias']}"/> --%>										
										<%-- <ec:column property="extCode" escapeAutoFormat="true" title="${mr['page.common.externalCode']}"/> --%>
										<ec:column property="prodPrice" title="${mr['page.common.mastUnitPrice']}"/>
										<ec:column property="prodPriceUnit.itemName" title="${mr['page.common.mastTheUnitOfPrice']}" alias="prodPriceUnit" />
										<%-- <ec:column property="prodBarcode" escapeAutoFormat="true" title="${mr['page.common.productBarcode']}"/>
										<ec:column property="prodPcBarcode" escapeAutoFormat="true" title="${mr['page.common.productPcBarcode']}"/> --%>
										<ec:column property="itemPack.itemName" title="${mr['page.common.prodPackage']}" alias="packName"/>
										
										<ec:column property="memo1.itemName" title="产品级别" alias="memo1name"/>
										<ec:column property="memo2.itemName" title="种类口味" alias="memo2name"/>
										<ec:column property="memo3.itemName" title="物料组" alias="memo3name"/>
										
										<ec:column property="prodWeight" title="${mr['page.common.netWeight']}"/>
										<ec:column property="prodweightUnit.itemName" title="${mr['page.common.weightUnit']}" alias="prodweightUnitName"/>
										<ec:column property="prodVolum" title="体积"/>
										<ec:column property="prodVolehUnit.itemName" title="体积单位" alias="prodVolehUnitName"/>
										<ec:column property="itemType.itemName" title="${mr['page.common.mastProdSeries']}" alias="typeName"/>
										<ec:column property="dictProdType.itemName" title="${mr['page.common.mastProdType']}" alias="prodTypeName"/>
										
										<ec:column property="itemOther.itemName" title="${mr['page.common.prodLine']}" alias="otherName"/>
										
										<ec:column property="prodSpec" title="${mr['page.common.prodSpeci']}"/>
										
										<ec:column property="prodBaseUnit.itemName" title="${mr['page.common.baseUnit']}" alias="prodBaseUnitName" />
										
										<ec:column property="prodMinWeight" title="${mr['page.common.smallUnitOfWeight']}"/>
										<ec:column property="prodContent" title="${mr['page.common.transPackage']}"/>
										<ec:column property="prodMinContent" title="${mr['page.common.minPackage']}"/>
										<ec:column property="createdBy" title="${mr['page.common.createdBy']}"/>
										<ec:column property="created" title="${mr['page.common.createdTime']}" cell="date" format="yyyy-MM-dd "/>
										<ec:column property="updatedBy" title="${mr['page.common.updatedBy']}"/>
										<ec:column property="updated" title="${mr['page.common.updatedTime']}" cell="date" format="yyyy-MM-dd "/>
										
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
