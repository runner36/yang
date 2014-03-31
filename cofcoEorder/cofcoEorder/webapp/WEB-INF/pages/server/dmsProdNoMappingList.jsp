<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>${mr['page.customize.title.noMapProdQuery']}</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/widgets/extremecomponents/extremecomponents.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/dms.js"></script>
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
			var form = document.dmsProdMappingForm;
			form.submit();
		}
		function selectClient() {
			var form = document.dmsProdMappingForm;
			var v = openClientTree('${ctx}', '1');
			if (v) {
				form.clientId_.value = v.id;
				form.clientName_.value = v.text;
			}
		}
		function changeState(clientId, prodCode, prodUnit, state) {
			if (window.confirm("${mr['page.common.mess.changeProdStatus']}！")) {
				createXMLHttpRequest();
				xmlhttp.open("post", encodeURI("${ctx}/server/dmsProdMapping.do?method=changeState&clientId=" + clientId + "&prodCode=" + prodCode + "&prodUnit=" + prodUnit + "&state=" + state), true);
				xmlhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
				xmlhttp.send(null);
			}
		}
		</script>
	</head>
	<body onload="WindowSollAuto()" onresize="WindowSollAuto()"><jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/server/dmsProdMapping.do?method=noMappingList" method="post">
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						<!--TitleStrat-->
						${mr['page.customize.title.noMapProdQuery']}
						<!--TitleStrat-->
					</h4>
					<div class='MenuList'>
						<!--MenuStrat-->
						<a href="javascript:showQuery_('query_A');" id="showQuery_A" >${mr['page.common.button.setquery']}</a>
						<a href="javascript:hideQuery_('query_A');" id="hideQuery_A" >${mr['page.common.button.close']}</a>
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
							<iframe style="width:98%;height:80px;filter:alpha(opacity=0);-moz-opacity:0; top: expression(this.offsetParent.scrollTop);left: expression(this.parentElement.scrollLeft); position:absolute;"></iframe>
								<div class="searchbox" id="searchbox">
								<table>
									<tr>
										<td width="1%">${mr['page.common.client']}</td>
										<td width="1%">
											<html:hidden name="ec" property="clientId_"/>
											<html:text name="ec" property="clientName_" styleClass="select_but" onclick="selectClient()" readonly="true"/>
										</td>
										<td width="1%">${mr['page.common.distProdCode']}</td>
										<td width="1%">
											<html:text name="ec" property="distProdCode_"/>
										</td>
										<td width="1%">${mr['page.common.distProdName']}</td>
										<td width="1%">
											<html:text name="ec" property="distProdName_"/>
										</td>
										<td width="1%">${mr['page.common.mappingStatus']}</td>
										<td>
											<html:select name="ec" property="state_" style="width: 84px">
												<html:option value=""></html:option>
												<html:option value="1">正常</html:option>
												<html:option value="2">赠品</html:option>
												<html:option value="3">停产</html:option>
												<html:option value="4">其它</html:option>
											</html:select>
										</td>
									</tr>
								</table>
								</div>
							
								<ec:table items="list" var="item" onInvokeAction="query()"
									form="dmsProdMappingForm" retrieveRowsCallback="limit"
									style="width:100%" tableId="ec" sortRowsCallback="limit"
									action="dmsProdMapping.do?method=noMappingList">
									<ec:exportXls fileName="${mr['page.customize.title.noMapProdQuery']}.xls" tooltip="${mr['page.common.exportExcel']}" view="xls" />
									<ec:row highlightRow="true">
										<%--<ec:column property="0" title="${mr['page.common.orgName']}"/>--%>
										<ec:column property="1" title="${mr['page.common.distCode']}"/>
										<ec:column property="2" title="${mr['page.common.distName']}"/>
										<%--<ec:column property="3" title="${mr['page.common.clientId']}"/>--%>
										<ec:column property="4" title="${mr['page.common.clientCode']}"/>
										<ec:column property="5" title="${mr['page.common.distProdCode']}"/>
										<ec:column property="6" title="${mr['page.common.productBarcode']}"/>
										<ec:column property="7" title="${mr['page.common.distProdName']}"/>
										<ec:column property="8" title="${mr['page.common.distProdUnit']}"/>
										<ec:column property="9" title="${mr['page.common.createdTime']}" cell="date" format="yyyy-MM-dd"/>
										
										<ec:column property="a" title="${mr['page.common.mappingStatus']}" viewsDenied="html">${item[10]==1 ? "正常" : (item[10]==2 ? "赠品" : (item[10]==3 ? "停产" : "其它"))}</ec:column>
										<ec:column property="10" title="*${mr['page.common.mappingStatus']}" viewsAllowed="html" style="text-align:center">
											<html:select property="state_" value="${item[10]}" style="width:52px" onchange="changeState(${item[3]},'${item[5]}','${item[8]}',this.value)">
												<html:option value="1">正常</html:option>
												<html:option value="2">赠品</html:option>
												<html:option value="3">停产</html:option>
												<html:option value="4">其它</html:option>
											</html:select>
										</ec:column>
										<ec:column property="null" width="1%" title="${mr['page.common.button.oper']}" sortable="false" viewsAllowed="html">
											<center>
												<a href="javascript:location=encodeURI('${ctx}/server/dmsProdMapping.do?method=edit&flag=1&clientId=${item[3]}&clientName=${item[2]}(${item[4]})&distProdCode=${item[5]}&distProdBarCode=${item[6]}&distProdName=${item[7]}&distProdUnit=${item[8]}')">
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
	</body>
</html>
