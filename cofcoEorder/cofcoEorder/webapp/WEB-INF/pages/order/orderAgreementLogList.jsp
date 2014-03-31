<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<%@ include file="/commons/meta.jsp" %>
		<title>经销商补充协议签署列表</title>
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
			var form = document.orderAgreementLogForm;
			form.action="orderAgreementLog.do?method=list"
			loading();
			form.submit();
		}
		//客户
		 function selectDist() {
			var form = document.orderAgreementLogForm;
			var v = openDistTreeForOrder('${ctx}', '', '1', form.$lk_mdmDistributor_distId.value,'OM');
			if (v) {
				form.$lk_mdmDistributor_distId.value = v.id;
				form.distName_.value = v.text;
			}
		}
		//地区
	    function selectDict(dictName, id, name) {
	   	 	var form = document.orderAgreementLogForm;
			var v = openDictTree('${ctx}', dictName);
			if (v) {
				if(v.id){
					form.geoId_.value = v.id;
					form.geoName.value = v.text;
					form.geoCode_.value = v.subCode;
				}else{
					form.geoId_.value = '';
					form.geoName.value = '';
					form.geoCode_.value = '';
				}
			}
		}
		
		//组织
		function selectOrg() {
			var form = document.orderAgreementLogForm;
			var v = openOrgTreeForEorder('${ctx}','','','','OM');
			if (v) {
				if(v.id){
					form.orgId_.value = v.id;
					form.orgName.value = v.text;
					form.orgCode_.value = v.subCode;
				}else{
					form.orgId_.value = '';
					form.orgName.value = '';
					form.orgCode_.value = '';
				}
			}
		}
		
		</script>
	</head>
	<body onload="WindowSollAuto()" onresize="WindowSollAuto()"><jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
	
		<html:form action="/order/orderAgreementLog.do?method=list" method="post">
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						<!--TitleStrat-->
						经销商补充协议签署列表
						<!--TitleStrat-->
					</h4>
					<div class='MenuList'>
						<!--MenuStrat-->
						<a href="javascript:showQuery_('query_A');" id="showQuery_A" >${mr['page.common.button.setquery']}</a>
						<a href="javascript:hideQuery_('query_A');" id="hideQuery_A" >${mr['page.common.button.close']}</a>
						<a href="javascript:orderAgreementLogForm.ec_p.value=1;query()" id="query_A">${mr['page.common.button.query']}</a>
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
										<td width="1%">客户编码</td>
										<td width="1%">
											<html:text name="ec" property="$lk_mdmDistributor_distCode"/>
										</td>
										<td width="1%">客户名称</td>
										<td width="1%">
											<html:hidden name="ec" property="$lk_mdmDistributor_distId"/>
											<html:text name="ec" property="distName_" styleClass="select_but" onclick="selectDist()" readonly="true"/>
										</td>
										<td width="1%">人员名称</td>
										<td width="1%">
											<html:text name="ec" property="$lk_baseEmployee_empName"/>
										</td>
										<td width="1%">用户名</td>
										<td width="1%">
											<html:text name="ec" property="$lk_baseUser_userAccount"/>
										</td>
										<td width="1%">
											签署时间
										</td>
										<td >
											<html:text name="ec" property="$ge_createDate" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>-<html:text name="ec" property="$le_createDate" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>
										</td>
									</tr>									
								</table>
								</div>
								<ec:table items="list" var="item" onInvokeAction="query()"
									form="orderAgreementLogForm" retrieveRowsCallback="limit"
									style="width:100%" tableId="ec" sortRowsCallback="limit"
									action="orderAgreementLog.do?method=list">
									<ec:exportXls fileName="补充协议签署列表.xls" tooltip="${mr['page.common.exportExcel']}" view="xls" />
									<ec:exportCsv fileName="补充协议签署列表.csv" tooltip="${mr['page.common.exportCVS']}" view="csv" />
									<ec:row highlightRow="true" >
										<ec:column property="null" width="1%" title="${mr['page.common.button.oper']}" sortable="false" viewsDenied="xls,csv">
											<center>												
												<html:link page="/order/orderAgreementLog.do?method=edit&id=${item.id}">
													<img src="${ctx}/images/edit.gif" border="0" alt="${mr['page.common.button.edit']}" title="${mr['page.common.button.edit']}" />
												</html:link>											
												
												<html:link page="/order/orderAgreementLog.do?method=delete&id=${item.id}">
													<img src="${ctx}/images/delete.gif" border="0" alt="${mr['page.common.button.delete']}" title="${mr['page.common.button.delete']}" onclick="return confirm('${mr['page.common.mess.delete']}')" />
												</html:link>
												
												&nbsp;
											</center>
										</ec:column>
										
									 	<ec:column property="id" title="ID"  />
									 	<ec:column property="mdmDistributor.distCode" title="客户编码" alias="distCode"/>
										<ec:column property="mdmDistributor.distName" title="客户名称" alias="distName"/>										
										<ec:column property="baseUser.userAccount" title="用户名" alias="userAccount"/>
										<ec:column property="baseEmployee.empName" title="人员名称" alias="empName"/>																			
										<ec:column property="createDate" title="签署日期"  cell="date" format="yyyy-MM-dd HH:mm"/>
										<ec:column property="status" value="${item.status==1 ? '有效':'无效'}" title="状态"/>										
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