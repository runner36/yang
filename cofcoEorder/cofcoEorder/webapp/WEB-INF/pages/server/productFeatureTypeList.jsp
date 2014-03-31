<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@page import="java.util.Date" %>
<%@page import="java.text.SimpleDateFormat" %>
<html>
	<head>
		<%@ include file="/commons/meta.jsp" %>
		<title>产品特征类别</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/widgets/extremecomponents/extremecomponents.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/dms.js"></script>
		<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>
		<script language="javaScript" src="${ctx}/scripts/jquery-1.2.6.min.js"></script>
		<script>
	    function query() {
			resetec();
			var form = document.featureTypeForm;
			loading();
			form.submit();
		}
	    
	    function toEdit(){
	    	loading();
			window.location="<%=request.getContextPath()%>/server/productFeatureType.do?method=toEdit";
	    }
		</script>
	</head>
	<body onload="WindowSollAuto()" onresize="WindowSollAuto()"><jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
	
		<html:form action="/server/productFeatureType.do?method=list" method="post">
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						<!--TitleStrat-->
						产品特征类别
						<!--TitleStrat-->
					</h4>
					<div class='MenuList'>
						<!--MenuStrat-->
						<a href="javascript:toEdit();" >新增</a>
						<a href="javascript:featureTypeForm.ec_p.value=1;query()" id="query_A">刷新</a>
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
									</tr>
								</table>
								</div>
								<ec:table items="list" var="item" onInvokeAction="query()"
									form="featureTypeForm" retrieveRowsCallback="limit"
									style="width:100%" tableId="ec" sortRowsCallback="limit"
									action="productFeatureType.do?method=list">
									<ec:exportXls fileName="产品特征类别列表.xls" tooltip="导出EXCEL" view="xls" />
									<ec:exportCsv fileName="产品特征类别列表.csv" tooltip="导出CSV" view="csv" />
										<ec:row highlightRow="true" >
										<ec:column property="typeName" title="类别名称" sortable="false"/>
										<ec:column property="created" title="创建时间"  cell="date" format="yyyy-MM-dd HH:mm:ss"  sortable="false"/>
										<ec:column property="updated" title="修改时间"  cell="date" format="yyyy-MM-dd HH:mm:ss" sortable="false"/>
										<ec:column property="state" title="状态" sortable="false">
											<c:if test="${item.state=='1'}">
												<c:out value="有效"></c:out>
											</c:if>
											<c:if test="${item.state!='1'}">
												<c:out value="无效"></c:out>
											</c:if>
										</ec:column>
										
										<ec:column property="null" width="1%" title="操作" sortable="false" viewsAllowed="html">
											<center>
												<html:link page="/server/productFeatureType.do?method=toEdit&id=${item.id}">
													<img src="${ctx}/images/edit.gif" border="0" alt="编辑" title="编辑" />
												</html:link>
												&nbsp;
												<html:link
													page="/server/productFeatureType.do?method=delete&ids=${item.id}" onclick="return confirm('确认要删除该记录吗')">
													<img src="${ctx}/images/delete.gif" border="0" alt="删除" title="删除" />
												</html:link>
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
