<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<%@ include file="/commons/meta.jsp" %>
		<title>${mr['page.customize.title.afsLocMapMaint']}</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/widgets/extremecomponents/extremecomponents.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/dms.js"></script>
		<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>
		<script type="text/javascript" src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/jquery-1.2.6.min.js"></script>
		<script>
	    function query() {
			resetec();
			var form = document.dmsLocationMappingForm;
			loading();
			form.submit();
		}
		function selectClient() {
			var form = document.dmsLocationMappingForm;
			var v = openClientTree('${ctx}', '1','1',form.clientId_.value);
			if (v) {
				form.clientId_.value = v.id;
				form.clientName_.value = v.text;
			}
		}
		function selectDict(dictName, id, name) {
			var v = openDictTree('${ctx}', dictName,'0','1',id.value);
			if (v) {
				id.value = v.id;
				name.value = v.text;
			}
		}
		
		</script>
	</head>
	<body onload="WindowSollAuto()" onresize="WindowSollAuto()"><jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/server/dmsLocationMapping.do?method=list" method="post">
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						<!--TitleStrat-->
						${mr['page.customize.title.afsLocMapMaint']}
						<!--TitleStrat-->
					</h4>
					<div class='MenuList'>
						<!--MenuStrat-->
						<a href="javascript:showQuery_('query_A');" id="showQuery_A" >${mr['page.common.button.setquery']}</a>
						<a href="javascript:hideQuery_('query_A');" id="hideQuery_A" >${mr['page.common.button.close']}</a>
						<a href="javascript:query();" id="query_A">${mr['page.common.button.query']}</a>
						<a href="javascript:openImportForm('${ctx}', 'DmsLocationMapping');">${mr['page.common.button.import']}</a>
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
											<td width="1%">上级Location名称：</td>
											<td><html:text name="ec" property="upStoceName_"/></td>
											<td width="1%">${mr['page.common.createdTime']}</td>
											<td><html:text name="ec" property="create_start" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>-<html:text name="ec" property="create_end" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/></td>
										
										</tr>
										<tr>
											<td width="1%">	${mr['page.common.distCode']}</td>
											<td width="1%"><html:text name="ec" property="distCode_"/></td>
											<td width="1%">	${mr['page.common.distName']}</td>
											<td width="1%"><html:text name="ec" property="distName_"/></td>
											<td width="1%">${mr['page.common.mappingDate']}</td>
											<td><html:text name="ec" property="mapCreate_start" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>-<html:text name="ec" property="mapCreate_end" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/></td>
										</tr>
										<tr>
											<td width="1%">Location标准名称：</td>
											<td ><html:text name="ec" property="targetStorName_" /></td>
											<td width="1%">Location标准编码：</td>
											<td width="1%"><html:text name="ec" property="targetStorCode_" /></td>
											<td width="1%">${mr['page.common.lastChangeDate']}</td>
											<td ><html:text name="ec" property="mapUpdate_start" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>-<html:text name="ec" property="mapUpdate_end" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/></td>
										</tr>
										<tr>
											<td width="1%">Location类型：</td>
											<td align="left"><html:hidden name="ec"   property="typeId_"/><html:text name="ec" property="typeName_" readonly="true" styleClass="select_but" onclick="selectDict('storeType',dmsLocationMappingForm.typeId_,dmsLocationMappingForm.typeName_)"/></td>
											<td width="1%">Location城市：</td>
											<td align="left"><html:hidden name="ec" property="geoId_"/><html:text  name="ec" property="geoName_" readonly="true" styleClass="select_but" onclick="selectDict('geography',dmsLocationMappingForm.geoId_,dmsLocationMappingForm.geoName_)"/></td>
											<td width="1%">Location最近更新日期：</td>
											<td><html:text name="ec" property="storeUpdate_start" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>-<html:text name="ec" property="storeUpDate_end" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/></td>
										</tr>
										<tr>
											<td width="1%">经销商Location编码：</td>
											<td align="left"><html:text name="ec" property="distStoreCode_" /></td>
											<td width="1%">经销商Location名称：</td>
											<td align="left"><html:text name="ec" property="distStoreName_" /></td>
											<td width="1%">Location性质：</td>
											<td ><html:hidden property="natureId_" styleId="natureId_" /><html:text property="natureName_" styleId="natureName_" readonly="true" styleClass="select_but" onclick="selectDict('storeNature',dmsLocationMappingForm.natureId_,dmsLocationMappingForm.natureName_)"/></td>
										</tr>
										<tr>
											
											<td width="1%">状  态：</td>
											<td>
												<html:select name="ec" property="state_" style="width: 84px">
													<html:option value=""></html:option>
													<html:option value="1">精确匹配</html:option>
													<html:option value="2">模糊匹配</html:option>
													<html:option value="3">${mr['page.common.doesNotMapping']}</html:option>
												</html:select>
											</td>
											<td width="1%">${mr['page.common.isReturn']}</td>
											<td>
												<html:select name="ec" property="returnData_" style="width: 84px">
													<html:option value=""></html:option>
													<html:option value="1">${mr['page.common.yes']}</html:option>
													<html:option value="0">${mr['page.common.no']}</html:option>
												</html:select>
											</td>
											<td width="1%">待处理条件：</td>
											<td colspan="1">
												<html:select  name="ec" property="sel_" style="width:330px;">
													<html:option value=""></html:option>
													<html:option value="1">超过2天未确认城市编码待匹配客户</html:option>
													<html:option value="2">超过30天未确认客户标准编码待匹配客户</html:option>
													<html:option value="3">超过7天未确认客户标准编码待匹配客户</html:option>
													<html:option value="4">未匹配nike客户主数据</html:option>
													<html:option value="5">超过7天未确认soldto待匹配客户</html:option>
													<html:option value="6">超过30天未确认soldto待匹配客户</html:option>
												</html:select>
											</td>
										</tr>
										
								</table>
								</div>
							
								<ec:table items="list" var="item" onInvokeAction="query()"
									form="dmsLocationMappingForm" retrieveRowsCallback="limit"  sortRowsCallback="limit" 
									style="width:100%" tableId="ec"
									action="dmsLocationMapping.do?method=list">
									<ec:exportXls fileName="${mr['page.customize.title.afsLocMapMaint']}.xls" tooltip="${mr['page.common.exportExcel']}" view="xls" />
									<ec:exportCsv fileName="${mr['page.customize.title.afsLocMapMaint']}.csv" tooltip="${mr['page.common.exportCVS']}" view="csv" />
									<ec:row highlightRow="true">
										<ec:column property="1" title="${mr['page.common.orgName']}"/>
										<ec:column property="2" title="${mr['page.common.distCode']}"/>
										<ec:column property="3" title="${mr['page.common.distName']}"/>
										<ec:column property="5" title="${mr['page.common.clientCode']}"/>
										<ec:column property="" title="${mr['page.common.clientName']}" alias="clientName" value="${item[3] }(${item[5] })">
										</ec:column>
										<ec:column property="6" title="经销商Location编码" />
										<ec:column property="7" title="经销商Location名称" />
										<ec:column property="8" title="经销商Location地址" />
										<ec:column property="11" title="Location城市名称" />
										<ec:column property="25" title="Location城市编码" />
										<ec:column property="12" title="Location名称地址中是否包含确认的城市" />
										<ec:column property="13" title="Location渠道" />
										<ec:column property="14" title="Location性质" />
										<ec:column property="15" title="Location类型" />
										<ec:column property="22" title="建议Location标准编码" />
										<ec:column property="9" title="Location标准编码" />
										<ec:column property="10" title="Location标准名称" />
										<ec:column property="16" title="上级Location名称" />
										<ec:column property="26" title="上级Location编码" />
										<ec:column property="17" title="主数据Location类型" />
										<ec:column property="18" title="Location最近更新日期" cell="date" format="yyyy-MM-dd" />
										<ec:column property="23" title="${mr['page.common.createdTime']}" cell="date" format="yyyy-MM-dd" />
										<ec:column property="20" title="回顾日期" cell="date" format="yyyy-MM-dd" />
										<ec:column property="21" title="${mr['page.common.lastChangeDate']}" cell="date" format="yyyy-MM-dd" />
										<ec:column property="24" title="${mr['page.common.isReturn']}"  value="${item[24]==null ? '':item[24]==1 ? '是':'否'}"/>
										<ec:column property="19" title="${mr['page.common.mappingDate']}" cell="date" format="yyyy-MM-dd"/>
										<ec:column property="" title="${mr['page.common.status']}"  value="${item[27]==1 ? '精确匹配':item[27]==2 ? '模糊匹配':'${mr[page.common.doesNotMapping]}'}"  alias="storeState" />
										<ec:column property="null" width="1%" title="${mr['page.common.button.oper']}" sortable="false" viewsDenied="xls,csv">
											<center>
												<html:link page="/server/dmsLocationMapping.do?method=edit&activeId=${item[28]}&activeSId=${item[0]}">
													<img src="${ctx}/images/edit.gif" border="0" alt="${mr['page.common.button.edit']}" title="${mr['page.common.button.edit']}" />
												</html:link>
												&nbsp;
												<c:if test="${item[0]!=null}">
												<html:link page="/server/dmsLocationMapping.do?method=delete&activeSId=${item[0]}" onclick="return confirm('${mr['page.common.mess.delete']}')">
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

