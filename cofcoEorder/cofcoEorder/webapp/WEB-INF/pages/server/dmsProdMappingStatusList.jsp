<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@page import="java.util.Date" %>
<%@page import="java.text.SimpleDateFormat" %>
<%
request.setAttribute("date1",request.getParameter("start_date")==null?new SimpleDateFormat("yyyy-MM").format(new Date()):request.getParameter("start_date"));
request.setAttribute("date2",request.getParameter("end_date")==null?new SimpleDateFormat("yyyy-MM").format(new Date()):request.getParameter("end_date"));
%>
<html>
	<head>
		<%@ include file="/commons/meta.jsp" %>
		<title>经销商产品匹配情况</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/widgets/extremecomponents/extremecomponents.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/dms.js"></script>
		<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>
		<script language="javaScript" src="${ctx}/scripts/jquery-1.2.6.min.js"></script>
		<script language="javaScript" src="${ctx}/scripts/yearMonth.js"></script>
		<script type="text/javascript" src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
		<script>
	    function query() {
			resetec();
			var form = document.dmsProdMappingStatusForm;
			loading();
			form.submit();
		}
		
	    
		function selectDist() {
			//alert(document.getElementsByName("distIds")[0].value)
			//alert(openDistTree)
			  var v = openDistTree('${ctx}','2','1',document.getElementsByName("distIds")[0].value);
			  if (v) {
				  document.getElementsByName("distIds")[0].value = v.id;
				  document.getElementsByName("distName")[0].value = v.text;
				}
		}
		
		function onSubmit() {
			if(document.getElementsByName("distName")[0].value==''){
				alert('请选择经销商!');
				return false;
			}
			if(document.getElementsByName("start_date")[0].value=='' || document.getElementsByName("end_date")[0].value==''){
				alert('请选择期间开始与结束日期!');
				return false;
			}
		  return true;
		}
	    
	    
		</script>
	</head>
	<body onload="WindowSollAuto()" onresize="WindowSollAuto()"><jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
	
		<html:form action="/server/dmsProdMappingStatus.do?method=list" method="post">
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						<!--TitleStrat-->
						经销商产品匹配情况
						<!--TitleStrat-->
					</h4>
					<div class='MenuList'>
						<!--MenuStrat-->
						<a href="javascript:showQuery_('query_A');" id="showQuery_A" >设置查询条件</a>
						<a href="javascript:hideQuery_('query_A');" id="showQuery_A" >关闭</a>
						<a href="javascript:dmsProdMappingStatusForm.ec_p.value=1;query()" id="query_A">查询</a>
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
										<td width="1%">经销商：</td>
										<td width="1%">
											<html:hidden property="distIds" />
								  			<html:text property="distName" styleClass="select_but" onclick="selectDist()" readonly="true"/>
								  		</td>
										<td width="1%">产品标准名称：</td>
										<td width="1%"><html:text property="target_prod_name"/></td>
										<td width="1%">经销商产品名称：</td>
										<td width="1%"><html:text property="prod_name"/></td>
										<td width="1%">产品状态：</td>
										<td width="1%">
											<html:select property="is_map">
												<html:option value="">请选择</html:option>
												<html:option value=" AND DPM.CREATED IS NOT NULL ">已匹配</html:option>
												<html:option value=" AND DPM.CREATED IS NULL ">未匹配</html:option>
											</html:select>
										</td>
										<td width="1%">
											数据期间：
										</td>
										<td>
											<html:text styleId="start_date" property="start_date" value="${date1}" onfocus="showTable(this)" readonly="true" styleClass="date_but"/>~
											<html:text styleId="end_date" property="end_date" value="${date2}" onfocus="showTable(this)" readonly="true" styleClass="date_but"/>
										</td>
										<td>&nbsp;</td>
									</tr>
								</table>
								</div>
								<ec:table items="list" var="item" onInvokeAction="query()"
									form="dmsProdMappingStatusForm" retrieveRowsCallback="limit"
									style="width:100%" tableId="ec" sortRowsCallback="limit"
									action="dmsProdMappingStatus.do?method=list">
									<ec:exportXls fileName="产品匹配列表.xls" tooltip="导出EXCEL" view="xls" />
									<ec:exportCsv fileName="产品匹配列表.csv" tooltip="导出CSV" view="csv" />
										<ec:row highlightRow="true" >
										
									 	<ec:column property="distName" title="经销商名称"/>
										<ec:column property="distProdCode" title="经销商产品编码"/>
										<ec:column property="distProdName" title="经销商处产品名称"/>
										<ec:column property="prodName" title="产品标准名称"/>
										<ec:column property="mappingStatus" title="匹配情况"/>
										<ec:column property="realSaleAmount" title="销售额(实际)"/>
										<ec:column property="costSaleAmount" title="销售额(标准价折算)"/>
										<ec:column property="addAmountRate" title="加价率"/>
										<ec:column property="countUnitName" title="统计单位"/>
										<ec:column property="deliveryQty" title="厂家发货数量"/>
										<ec:column property="arrivalQty" title="经销商到货数量"/>
										<ec:column property="arrivalQtyRate" title="到货比率"/>
										<ec:column property="distProdBarCode" title="经销商条码"/>
										<ec:column property="prodBarCodeCheckStatus" title="条码检查状态"/>
										<ec:column property="prodCode" title="产品标准编码"/>
										<ec:column property="remark" title="备注"/>
										<ec:column property="mappingCreateTime" title="匹配记录创建日期"/>
										<ec:column property="mappingLastUpdateTime" title="匹配记录最近更新日期"/>
										
										
										<ec:column property="null" width="1%" title="操作" sortable="false" viewsAllowed="html">
											<center>
												<html:link page="/server/dmsProdMappingStatus.do?method=toEdit&activeId=${item.activeId}&activePId=${item.activePId}">
													<img src="${ctx}/images/edit.gif" border="0" alt="编辑" title="编辑" />
												</html:link>
												&nbsp;
												<c:if test="${item.activePId!=null}">
													<html:link
														page="/server/dmsProdMappingStatus.do?method=delete&activePId=${item.activePId}" onclick="return confirm('确认要删除该记录吗')">
														<img src="${ctx}/images/delete.gif" border="0" alt="删除" title="删除" />
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
