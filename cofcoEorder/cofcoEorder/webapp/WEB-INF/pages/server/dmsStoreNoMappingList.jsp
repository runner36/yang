<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>${mr['page.customize.title.noMappingCustQuery']}</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/widgets/extremecomponents/extremecomponents.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/dms.js"></script>
		<script language="javaScript" src="${ctx}/scripts/jquery-1.2.6.min.js"></script>
		<script>
		var xmlhttp;
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
			var form = document.dmsStoreMappingForm;
			form.submit();
		}
		function selectClient() {
			var form = document.dmsStoreMappingForm;
			var v = openClientTree('${ctx}', '1','1',form.clientId_.value);
			if (v) {
				form.clientId_.value = v.id;
				form.clientName_.value = v.text;
			}
		}
		function changeState(clientId, storeCode, state) {
			if (window.confirm("确认修改门店状态！")) {
				createXMLHttpRequest();
				xmlhttp.open("post", encodeURI("${ctx}/server/dmsStoreMapping.do?method=changeState&clientId=" + clientId + "&storeCode=" + storeCode + "&state=" + state), true);
				xmlhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
				xmlhttp.send(null);
			}
		}

		function sethidenValue(clientId,clientName,distStoreCode,distStoreName,distStoreAddr)
		{
			$("#clientId").attr("value",clientId);
			$("#clientName").attr("value",clientName);
			$("#distStoreCode").attr("value",distStoreCode);
			$("#distStoreName").attr("value",distStoreName);
			$("#distStoreAddr").attr("value",distStoreAddr);
			$("#heditForm").submit();
		}
		</script>
	</head>
	<body onload="WindowSollAuto()" onresize="WindowSollAuto()"><jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/server/dmsStoreMapping.do?method=noMappingList" method="post">
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						<!--TitleStrat-->
						${mr['page.customize.title.noMappingCustQuery']}
						<!--TitleStrat-->
					</h4>
					<div class='MenuList'>
						<!--MenuStrat-->
						<a href="javascript:showQuery_('query_A');" id="showQuery_A" >${mr['page.common.button.setquery']}</a>
						<a href="javascript:hideQuery_('query_A');" id="hideQuery_A" >${mr['page.common.button.close']}</a>
						<a href="javascript:dmsStoreMappingForm.ec_p.value=1;query()" id="query_A">${mr['page.common.button.query']}</a>
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
							<iframe style="width:98%;height:80px;filter:alpha(opacity=0);-moz-opacity:0; top: expression(this.offsetParent.scrollTop);left: expression(this.parentElement.scrollLeft); position:absolute;"></iframe>
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
										<td width="1%">客户状态：</td>
										<td>
											<html:select name="ec" property="state_" style="width: 84px">
												<html:option value=""></html:option>
												<html:option value="1">正常</html:option>
												<html:option value="9">其它</html:option>
											</html:select>
										</td>
									</tr>
								</table>
								</div>
							
								<ec:table items="list" var="item" onInvokeAction="query()"
									form="dmsStoreMappingForm" retrieveRowsCallback="limit"
									style="width:100%" tableId="ec" sortRowsCallback="limit"
									action="dmsStoreMapping.do?method=noMappingList">
									<ec:exportXls fileName="${mr['page.customize.title.noMappingCustQuery']}.xls" tooltip="${mr['page.common.exportExcel']}" view="xls" />
									<ec:row highlightRow="true">
										<%--<ec:column property="0" title="${mr['page.common.orgName']}"/>--%>
										<ec:column property="1" title="${mr['page.common.distCode']}"/>
										<ec:column property="2" title="${mr['page.common.distName']}"/>
										<%--<ec:column property="3" title="${mr['page.common.clientId']}"/>--%>
										<ec:column property="4" title="${mr['page.common.clientCode']}"/>
										<ec:column property="99" title="${mr['page.common.clientName']}" alias="clientName">
											${item[2]}(${item[4]})
										</ec:column>
										<ec:column property="5" escapeAutoFormat="true" title="${mr['page.common.distStoreCode']}"/>
										<ec:column property="7" title="${mr['page.common.distStoreName']}"/>
										<ec:column property="8" title="${mr['page.common.distStoreAddress']}"/>
										<ec:column property="9" title="${mr['page.common.createdTime']}" cell="date" format="yyyy-MM-dd"/>

										<ec:column property="a" title="客户状态" viewsDenied="html">${item[10]==1 ? "正常" : "其它"}</ec:column>
										<ec:column property="10" title="*客户状态" viewsAllowed="html" style="text-align:center">
											<html:select property="state_" value="${item[10]}" style="width:52px" onchange="changeState(${item[3]},'${item[5]}',this.value)">
												<html:option value="1">正常</html:option>
												<html:option value="9">其它</html:option>
											</html:select>
										</ec:column>
										<ec:column property="null" width="1%" title="${mr['page.common.button.oper']}" sortable="false" viewsDenied="xls">
											<center>
												<a  onclick="sethidenValue('${item[3]}','${item[2]}(${item[4]})','${item[5]}','${item[7]}','${item[8]}')">
													<img src="${ctx}/images/edit.gif" border="0" alt="添加mapping" />
												</a>
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
		<form action="${ctx}/server/dmsStoreMapping.do?method=edit&flag=1" method="post" id="heditForm" >
			<input type="hidden" value="" name="clientId"  id="clientId"  />
			<input type="hidden" value="" name="clientName"  id="clientName"  />
			<input type="hidden" value="" name="distStoreCode" id="distStoreCode" />
			<input type="hidden" value="" name="distStoreName"  id="distStoreName"/>
			<input type="hidden" value="" name="distStoreAddr"  id="distStoreAddr"/>
		</form>
	</body>
</html>
