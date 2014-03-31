<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<%@ include file="/commons/meta.jsp" %>
		<title>物料组_送货日期维护</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/widgets/extremecomponents/extremecomponents.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>
		<script language="javaScript" src="${ctx}/scripts/dms.js"></script>
		<script language="javaScript" src="${ctx}/scripts/jquery-1.2.6.min.js"></script>
		<script type="text/javascript" src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
		<script>
	    function query() {
			resetec();
			var form = document.mdmProdGroupDeliveryDaysForm;
			form.action="mdmProdGroupDeliveryDays.do?method=list"
			loading();
			form.submit();
		}
		
		//物料组
		function selectDict(dictName, id, name) {
			var v = openDictTree('${ctx}', dictName, 2, 2, id.value,'',1);
			if (v) {
				id.value = v.id;
				name.value = v.text;
			}
		}
		
		</script>
	</head>
	<body onload="WindowSollAuto()" onresize="WindowSollAuto()"><jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
	
		<html:form action="/order/mdmProdGroupDeliveryDays.do?method=list" method="post">
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						<!--TitleStrat-->
						物料组_送货日期维护
						<!--TitleStrat-->
					</h4>
					<div class='MenuList'>
						<!--MenuStrat-->
						<a href="javascript:showQuery_('query_A');" id="showQuery_A" >${mr['page.common.button.setquery']}</a>
						<a href="javascript:hideQuery_('query_A');" id="hideQuery_A" >${mr['page.common.button.close']}</a>
						<a href="javascript:mdmProdGroupDeliveryDaysForm.ec_p.value=1;query()" id="query_A">${mr['page.common.button.query']}</a>
						<a href="mdmProdGroupDeliveryDays.do?method=add">${mr['page.common.button.add']}</a>
						<a href="javascript:location.reload();">${mr['page.common.button.refresh']}</a>
						<!--MenuEnd-->
					</div>
					<span class="right"></span>
				</div>
				<div id="mess"
						style="position: absolute; display: none; font-size: 14px; font-weight: bold; text-align: center; height: 800px; width: 100%; line-height: 400px; z-index: 1000; background: #fff; filter: Alpha(Opacity =   70);">
							${mr['page.common.mess.wait']}......
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
										<td width="1%">物料组：</td>
										<td width="1%">
											<html:hidden property="$eq_itemProdStru_dictItemId" />
											<html:text property="itemName" readonly="true"
												styleClass="select_but"
												onclick="selectDict('prodSTRU', mdmProdGroupDeliveryDaysForm.$eq_itemProdStru_dictItemId, mdmProdGroupDeliveryDaysForm.itemName)" />
										</td>
										<td width="1%">送货天数：</td>
										<td width="1%">
											<html:text property="$eq_deliveryDays" maxlength="5"/>
										</td>
										<td width="1%">
										</td>
										<td width="1%">
										</td>
									</tr>									
								</table>
								</div>
								<ec:table items="list" var="item" onInvokeAction="query()"
									form="mdmProdGroupDeliveryDaysForm" retrieveRowsCallback="limit"
									style="width:100%" tableId="ec" sortRowsCallback="limit"
									action="mdmProdGroupDeliveryDays.do?method=list">
									<ec:exportXls fileName="物料组_送货日期维护列表.xls" tooltip="${mr['page.common.exportExcel']}" view="xls" />
									<ec:exportCsv fileName="物料组_送货日期维护列表.csv" tooltip="${mr['page.common.exportCVS']}" view="csv" />
									<ec:row highlightRow="true" >
										<ec:column property="null" width="1%" title="${mr['page.common.button.oper']}" sortable="false" viewsDenied="xls,csv">
											<center>												
												<html:link page="/order/mdmProdGroupDeliveryDays.do?method=edit&id=${item.id}">
													<img src="${ctx}/images/edit.gif" border="0" alt="${mr['page.common.button.edit']}" title="${mr['page.common.button.edit']}" />
												</html:link>											
												
												<html:link page="/order/mdmProdGroupDeliveryDays.do?method=delete&id=${item.id}">
													<img src="${ctx}/images/delete.gif" border="0" alt="${mr['page.common.button.delete']}" title="${mr['page.common.button.delete']}" onclick="return confirm('${mr['page.common.mess.delete']}')" />
												</html:link>
												
												&nbsp;
											</center>
										</ec:column>
									 	<ec:column property="id" title="ID"  />
									 	<ec:column property="itemProdStru.itemName" title="物料组" alias="distCode"/>
										<ec:column property="deliveryDays" title="送货天数"  />
										<ec:column property="createdBy" title="创建人" />
										<ec:column property="created" title="创建时间"  cell="date" format="yyyy-MM-dd"/>
										<ec:column property="updatedBy" title="更新人" />										
										<ec:column property="updated" title="更新时间"  cell="date" format="yyyy-MM-dd"/>	
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