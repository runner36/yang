<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html>
<head>
<title>反馈信息查询</title>
<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/widgets/extremecomponents/extremecomponents.css">
<script type="text/javascript"
	src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
<script language="javaScript" src="${ctx}/scripts/base.js"></script>
<script>
	function query() {
		resetec();
		var form = document.baseOpinionsForm;

		form.submit();
	}
	function selectMessType() {
		baseOpinionsForm.$eq_baseDictItemByMessType_dictItemId.value = '';
		baseOpinionsForm.messTypeName.value = '';
		var v = openDictTree('${ctx}', 'message');
		if (v) {
			baseOpinionsForm.$eq_baseDictItemByMessType_dictItemId.value = v.id;
			baseOpinionsForm.messTypeName.value = v.text;
		}
	}

	function time() {

		var today = new Date();
		today.setDate(today.getDate());//today.setDate(today.getDate() - 1);
		var year = today.getYear().toString();
		var month = (today.getMonth() + 1).toString();
		month = month.length == 1 ? '0' + month : month;
		var day = (today.getDate()).toString();
		day = day.length == 1 ? '0' + day : day;
		document.getElementById("$ge_pubDate").value = year + '-' + month + '-'
				+ day;
		document.getElementById("$le_pubDate").value = year + '-' + month + '-'
				+ day;

	}
</script>
</head>
<body onLoad="WindowSollAuto()" onResize="WindowSollAuto()">
	<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
	<html:form action="/base/baseOpinions.do?method=list&flag=${flag}" method="post">
		<div class="bosom_one">
			<div class="bosom_top">
				<span class="left"></span>
				<h4>
					<!--TitleStrat-->
					反馈信息查询
					<!--TitleStrat-->
				</h4>

				<div class='MenuList'>
						<!--MenuStrat-->
						<a href="javascript:showQuery_('query_A');" id="showQuery_A"
							>设置查询条件</a>
						<a href="javascript:hideQuery_('query_A');" id="hideQuery_A"
							>关闭</a>
						<a href="javascript:query()" id="query_A">查询</a>
						<c:if test="${flag==1}">
							<a href="baseOpinions.do?method=edit">新增</a>
						</c:if>					
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
									<td width="1%">主题：</td>
									<td ><html:text name="ec" property="$eq_title" />
								<c:if test="${flag!=1}">
								 <td width="1%">发布用户：</td>
								<td ><html:text name="ec" property="$eq_created" />
								</td>
								</c:if>
						  </table>
								<!--
								  <tr>
									<td width="1%">发布日期：</td>
									<td width="1%">
									<html:text name="ec" property="$ge_pubDate" styleClass="date_but" onfocus="WdatePicker()" readonly="true"/> 
							    	<html:text name="ec" property="$le_pubDate" styleClass="date_but" onfocus="WdatePicker()" readonly="true"/>
							   </tr> -->
							</div>
							<ec:table items="list" var="item" onInvokeAction="query()"
								form="baseOpinionsForm" retrieveRowsCallback="limit"
								style="width:100%" tableId="ec"
								action="baseOpinions.do?method=list">
								<ec:row highlightRow="true">
								
									<ec:column property="null" width="1%" title="操作" sortable="false" viewsDenied="xls">
										<center>
											<c:if test="${flag==1}">
												<html:link
													page="/base/baseOpinions.do?method=edit&flag=1&id=${item.id}">
													<img src="${ctx}/images/edit.gif" border="0" alt="编辑" title="编辑" />
												</html:link>
												&nbsp;
												<html:link
													page="/base/baseOpinions.do?method=delete&flag=1&id=${item.id}"
													onclick="return confirm('确认要删除该记录吗')">
													<img src="${ctx}/images/delete.gif" border="0" alt="删除" title="删除" />
												</html:link>
											</c:if>
											<c:if test="${flag!=1}">
												<c:if test="${item.fileItemsEmpty!=true}">
													<html:link page="/base/baseOpinions.do?method=download&id=${item.id}">
														附件下载
													</html:link>
												</c:if>									
											</c:if>
										</center>
									</ec:column>	
									
									<ec:column property="title" title="主题" />
									<ec:column property="created" title="发布用户" />
									<ec:column property="content" title="内容" cell="com.winchannel.extension.extremecomponents.LengthOmissionCell"/>
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
