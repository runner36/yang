<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
<head>
<title>信息发布</title>
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
		var form = document.baseMessagesForm;

		form.submit();
	}
	function selectMessType() {
		baseMessagesForm.$eq_baseDictItemByMessType_dictItemId.value = '';
		baseMessagesForm.messTypeName.value = '';
		var v = openDictTree('${ctx}', 'message');
		if (v) {
			baseMessagesForm.$eq_baseDictItemByMessType_dictItemId.value = v.id;
			baseMessagesForm.messTypeName.value = v.text;
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
	<html:form action="/base/baseMessages.do?method=list" method="post">
		<div class="bosom_one">
			<div class="bosom_top">
				<span class="left"></span>
				<h4>
					<!--TitleStrat-->
					公告栏信息发布
					<!--TitleStrat-->
				</h4>

				<div class='MenuList'>
						<!--MenuStrat-->
						<a href="javascript:showQuery_('query_A');" id="showQuery_A"
							>设置查询条件</a>
						<a href="javascript:hideQuery_('query_A');" id="hideQuery_A"
							>关闭</a>
						<a href="javascript:query()" id="query_A">查询</a>
						<a href="baseMessages.do?method=edit">新增</a>
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
							<div id="searchbox" class="searchbox">
							<table>
									<td width="1%">公告类型： </td>
									<td ><html:select name="ec" property="$eq_type"
											style="width: 84px">
											<html:option value=""></html:option>
											<html:option value="0">公告</html:option>
											<html:option value="1">帮助</html:option>
											
										</html:select>
									</td>
									<td width="1%">主题：</td>
									<td><html:text name="ec" property="$eq_title" />
								 <td width="1%">发布人：</td>
								<td><html:text name="ec" property="$eq_created" />
								</td>
								</table>
								<!--  <tr>
									<td width="1%">发布日期：</td>
									<td width="1%">
									<html:text name="ec" property="$ge_pubDate" styleClass="date_but" onfocus="WdatePicker()" readonly="true"/> 
							    	<html:text name="ec" property="$le_pubDate" styleClass="date_but" onfocus="WdatePicker()" readonly="true"/>
							   </tr> -->
							</div>
							<ec:table items="list" var="item" onInvokeAction="query()"
								form="baseMessagesForm" retrieveRowsCallback="limit"
								style="width:100%" tableId="ec"
								action="baseMessages.do?method=list">
								<ec:row highlightRow="true">
									<ec:column property="null" width="1%" title="操作"
										sortable="false" viewsDenied="xls">
										<center>
											<html:link
												page="/base/baseMessages.do?method=edit&newId=${item.newId}">
												<img src="${ctx}/images/edit.gif" border="0" alt="编辑" title="编辑" />
											</html:link>
											&nbsp;
											<html:link
												page="/base/baseMessages.do?method=delete&newId=${item.newId}"
												onclick="return confirm('确认要删除该记录吗')">
												<img src="${ctx}/images/delete.gif" border="0" alt="删除" title="删除" />
											</html:link>
										</center>
									</ec:column>	
									<ec:column property="type" title="类型" alias="type">
									<c:if test="${item.type==0}">
									  公告
									</c:if>
									<c:if test="${item.type==1}">
									帮助
									</c:if>
									</ec:column>	
									<ec:column property="title" title="主题" />
									<ec:column property="created" title="发布人" />
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
