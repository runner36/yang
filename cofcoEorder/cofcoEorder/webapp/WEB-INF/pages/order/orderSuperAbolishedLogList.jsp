<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<%@ include file="/commons/meta.jsp"%>
		<title>客户订单状态调整(管理员)</title>
		<link rel="stylesheet" type="text/css"
			href="${ctx}/styles/sidecss.css">
		<link rel="stylesheet" type="text/css"
			href="${ctx}/widgets/extremecomponents/extremecomponents.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/dms.js"></script>
		<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>
		<script type="text/javascript"
			src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript"
			src="${ctx}/scripts/jquery-1.2.6.min.js"></script>
		<script>
		    function query() {
				resetec();
				var form = document.orderSuperAbolishedLogForm;
				loading();
				form.submit();
			}
			function add() {
			    location='orderSuperAbolishedLog.do?method=add';
			}
		</script>
	</head>
	<body onLoad="WindowSollAuto()" onResize="WindowSollAuto()"><jsp:include
			page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/order/orderSuperAbolishedLog.do?method=list"
			method="post">
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						<!--TitleStrat-->
						客户订单状态调整(管理员)
						<!--TitleStrat-->
					</h4>
					<div class='MenuList'>
						<!--MenuStrat-->
						<a href="javascript:showQuery_('query_A');" id="showQuery_A" >设置查询条件</a>
						<a href="javascript:hideQuery_('query_A');" id="showQuery_A" >关闭</a>
						<a href="javascript:orderSuperAbolishedLogForm.ec_p.value=1;query()" id="query_A">查询</a>
						<a href="orderSuperAbolishedLog.do?method=add">新增</a>
						<a href="javascript:location.reload();">刷新</a>
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
												订单编号：
											</td>
											<td width="1%">
												<html:text name="ec" property="$lk_orderInfo_orderCode" />
											</td>
											<td width="1%" style="text-align: right">
												原来状态：
											</td>
											<td width="1%">
												<html:select name="ec" property="$eq_oldStatus" style="width: 110px">
													<html:option value=""></html:option>
													<html:option value="20">请订单中心接收</html:option>
													<html:option value="30">已接收</html:option>
													<html:option value="40">订单有误已作废</html:option>
												</html:select>
											</td>
											<td width="1%" style="text-align: right;">
												更新状态：
											</td>
											<td>
												<html:select name="ec" property="$eq_newStatus" style="width: 110px">
													<html:option value=""></html:option>
													<html:option value="20">请订单中心接收</html:option>
													<html:option value="30">已接收</html:option>
													<html:option value="40">订单有误已作废</html:option>
												</html:select>
											</td>

										</tr>
									</table>
								</div>
								<ec:table items="list" var="item" onInvokeAction="query()"
									form="orderSuperAbolishedLogForm" retrieveRowsCallback="limit"
									style="width:100%" tableId="ec"
									action="orderSuperAbolishedLog.do?method=list">
									<ec:exportXls fileName="客户订单状态调整(管理员).xls" tooltip="导出EXCEL"
										view="xls" />
									<ec:exportCsv fileName="客户订单状态调整(管理员).csv" tooltip="导出CSV" view="csv" />
									<ec:row highlightRow="true">
									<ec:column property="null" width="1%" title="操作"
											sortable="false" viewsDenied="xls,csv">
											<center>
												<html:link
													page="/order/orderSuperAbolishedLog.do?method=view&id=${item.id}">
													<img src="${ctx}/images/find.gif" border="0" alt="查看" title="查看" />
												</html:link>
											</center>
										</ec:column>
										<ec:column property="orderInfo.orderCode" styleClass="jxsId" title="订单编号" alias="orderCode" />
										<ec:column property="1" title="原来状态" sortable="false" ><c:if test="${item.oldStatus == 20}">请订单中心接收</c:if><c:if test="${item.oldStatus == 30}">已接收 </c:if><c:if test="${item.oldStatus == 40}">订单有误已作废 </c:if></ec:column>
										<ec:column property="2" title="更新状态" sortable="false" ><c:if test="${item.newStatus == 20}">请订单中心接收</c:if><c:if test="${item.newStatus == 30}">已接收 </c:if><c:if test="${item.newStatus == 40}">订单有误已作废 </c:if></ec:column>
										<ec:column property="createdByname" title="创建人" />
										<ec:column property="createdDate" title="创建时间"  cell="date" format="yyyy-MM-dd"/>
										<ec:column property="memo" title="备注" >
											${fn:length(item.memo)>6 ? fn:substring(item.memo,0,6) : item.memo}${fn:length(item.memo)>6 ? '...' : ''}
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
