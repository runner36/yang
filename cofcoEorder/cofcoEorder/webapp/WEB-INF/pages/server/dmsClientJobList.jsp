<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>${mr['page.customize.title.clientTaskManage']}</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/widgets/extremecomponents/extremecomponents.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script type="text/javascript" src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
		<script>
	    function query() {
			resetec();
			var form = document.dmsClientJobForm;
			form.submit();
		}
		function add() {
		    location='dmsClientJob.do?method=add';
		}
		</script>
	</head>
	<body onload="WindowSollAuto()">
		<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/server/dmsClientJob.do?method=list" method="post">
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						${mr['page.customize.title.clientTaskManage']}
					</h4>
					<div class='MenuList'>
						<!--MenuStrat-->
						<a href="javascript:showQuery_('query_A');" id="showQuery_A" >${mr['page.common.button.setquery']}</a>
						<a href="javascript:hideQuery_('query_A');" id="hideQuery_A" >${mr['page.common.button.close']}</a>
						<a href="javascript:dmsClientJobForm.ec_p.value=1;query()" id="query_A">${mr['page.common.button.query']}</a>
						<a href="dmsClientJob.do?method=add">${mr['page.common.button.add']}</a>
						<a href="javascript:location.reload();">${mr['page.common.button.refresh']}</a>
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
											${mr['page.common.clientCode']}
										</td>
										<td width="1%">
											<html:text name="ec" property="$eq_dmsClient_clientCode"/>
										</td>
										<td width="1%">
											${mr['page.common.distName']}
										</td>
										<td  width="1%">
											<html:text name="ec" property="$lk_dmsClient_mdmDistributor_distName"/>
										</td>
										<td width="1%">
											${mr['page.common.taskType']}
										</td>
										<td >
											<html:select property="$eq_jobType" style="width: 84px">
												<html:option value=""></html:option>
												<html:option value="UserJob">${mr['page.common.userTask']}</html:option>
												<html:option value="SystemJob">${mr['page.common.systemTask']}</html:option>
												<html:option value="CheckJob">${mr['page.common.checkTask']}</html:option>
											</html:select>
										</td>
										</tr>
										<tr>
										<td width="1%">
											${mr['page.common.status']}
										</td>
										<td>
											<html:select property="$eq_state" style="width: 84px">
												<html:option value=""></html:option>
												<html:option value="0">${mr['page.common.waitForExecute']}</html:option>
												<html:option value="1">${mr['page.common.hasbeenexecute']}</html:option>
											</html:select>
										</td>
										<td width="1%">
											${mr['page.common.updatedTime']}
										</td>
										<td colspan="3">
											<html:text name="ec" property="$ge_updateTime" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>-<html:text name="ec" property="$le_updateTime" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>
										</td>
									</tr>
								</table>
								</div>
							
								<ec:table items="list" var="item" onInvokeAction="query()"
									form="dmsClientJobForm" retrieveRowsCallback="limit"
									style="width:100%" tableId="ec"
									action="dmsClientJob.do?method=list">
									<ec:exportXls fileName="${mr['page.customize.title.clientTaskManage']}.xls" tooltip="${mr['page.common.exportExcel']}" view="xls" />
									<ec:row highlightRow="true">
									
										<ec:column property="dmsClient.mdmDistributor.distName" title="${mr['page.common.distName']}" alias="distName"/>
										<ec:column property="dmsClient.clientCode" title="${mr['page.common.clientCode']}" alias="clientCode"/>
										<ec:column property="jobType" title="${mr['page.common.taskType']}">
											<c:if test="${item.jobType == 'UserJob'}">${mr['page.common.userTask']}</c:if>
											<c:if test="${item.jobType == 'SystemJob'}">${mr['page.common.systemTask']}</c:if>
											<c:if test="${item.jobType == 'CheckJob'}">${mr['page.common.checkTask']}</c:if>
										</ec:column>
										<ec:column property="updated" title="${mr['page.common.updatedBy']}"/>
										<ec:column property="updateTime" title="${mr['page.common.updatedTime']}" cell="date" format="yyyy-MM-dd HH:mm"/>
										<ec:column property="state" title="${mr['page.common.executiveResult']}">
											<c:if test="${item.state == '0'}">${mr['page.common.waitForExecute']}</c:if>
											<c:if test="${item.state == '1'}">${mr['page.common.hasbeenexecute']}</c:if>
										</ec:column>
										<ec:column property="exeDate" title="${mr['page.common.executiveTime']}" cell="date" format="yyyy-MM-dd HH:mm"/>
										<ec:column property="remark" title="${mr['page.common.memo']}"/>
										
										<ec:column property="null" width="1%" title="${mr['page.common.button.oper']}" sortable="false" viewsDenied="xls">
											<center>
												<html:link page="/server/dmsClientJob.do?method=edit&jobId=${item.jobId}">
													<img src="${ctx}/images/edit.gif" border="0" alt="${mr['page.common.button.edit']}" title="${mr['page.common.button.edit']}" />
												</html:link>
												&nbsp;
												<html:link page="/server/dmsClientJob.do?method=delete&jobId=${item.jobId}" onclick="return confirm('${mr['page.common.mess.delete']}')">
													<img src="${ctx}/images/delete.gif" border="0" alt="${mr['page.common.button.delete']}" title="${mr['page.common.button.delete']}" />
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
