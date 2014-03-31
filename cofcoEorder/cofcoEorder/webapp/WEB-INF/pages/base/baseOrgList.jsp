<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<%@ include file="/commons/meta.jsp" %>
		<title>${mr['page.customize.title.orgInfQuery']}</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/widgets/extremecomponents/extremecomponents.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/dms.js"></script>
		<script>
	    function query() {
			resetec();
			var form = document.baseOrgForm;
			loading();
			form.submit();
		}
		</script>
	</head>
	<body onload="WindowSollAuto()" onresize="WindowSollAuto()"><jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/base/baseOrg.do?method=list" method="post">
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						<!--TitleStrat-->
						${mr['page.customize.title.orgInfQuery']}
						<!--TitleStrat-->
					</h4>
					<div class='MenuList'>
						<!--MenuStrat-->
						<a href="javascript:showQuery_('query_A');" id="showQuery_A"
							>${mr['page.common.button.setquery']}</a>
						<a href="javascript:hideQuery_('query_A');" id="hideQuery_A"
							>${mr['page.common.button.close']}</a>
						<a href="javascript:query()" id="query_A">${mr['page.common.button.query']}</a>
						<a href="javascript:openImportForm('${ctx}', 'BaseOrg');">${mr['page.common.button.import']}</a>
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
							<div class="searchbox" id="searchbox">
								<table>
								  	<tr>
									    <td width="1%">${mr['page.common.parentOrgCode']}</td>
									    <td width="1%"><html:text name="ec" property="$lk_baseOrg_orgCode"/></td>
									    <td width="1%">${mr['page.common.parentOrgName']}</td>
									    <td width="1%"><html:text name="ec" property="$lk_baseOrg_orgName"/></td>
									    <td width="1%">${mr['page.common.orgCode']}</td>
									    <td ><html:text name="ec" property="$lk_orgCode"/></td>
								  	</tr>
								  	<tr>
								  		 <td width="1%">${mr['page.common.orgName']}</td>
										<td>
										<html:text name="ec" property="$lk_orgName"/>
										</td>
										 <td width="1%">${mr['page.common.geographicAreaCode']}</td>
										<td colspan="3" >
											<html:text name="ec" property="$lk_geoAll"/>
										</td>
								  	</tr>
								</table>
							</div>
								<ec:table items="list" var="item" onInvokeAction="query()"
									form="baseOrgForm" retrieveRowsCallback="limit"
									style="width:100%" tableId="ec"
									action="baseOrg.do?method=list">
									<ec:exportXls fileName="${mr['page.common.orgList']}.xls" tooltip="${mr['page.common.exportExcel']}" view="xls" />
									<ec:row highlightRow="true">
										<ec:column property="baseOrg.orgCode" title="${mr['page.common.parentOrgCode']}" alias="upOrgCode"/>
										<ec:column property="baseOrg.orgName" title="${mr['page.common.parentOrgName']}" alias="upOrgName"/>
										<ec:column property="orgCode" title="${mr['page.common.orgCode']}"/>
										<ec:column property="orgName" title="${mr['page.common.orgName']}"/>
										<ec:column property="orgAlias" title="${mr['page.common.orgAlias']}"/>
										<ec:column property="levelCode" title="${mr['page.common.orgLevel']}"/>
										<ec:column property="extCode" title="${mr['page.common.externalCode']}"/>
										<ec:column property="baseDictItem.itemName" title="${mr['page.common.orgType']}" alias="orgtype"/>
										<ec:column property="orgNameEn" title="${mr['page.common.orgEngName']}"/>
										<ec:column property="baseEmployee.empName" title="${mr['page.common.personInCharge']}" alias="empName" />
										<ec:column property="phone" title="${mr['page.common.telephone']}"/>
										<ec:column property="email" title="${mr['page.common.email']}"/>
										<ec:column property="fax" title="${mr['page.common.fax']}"/>
										<ec:column property="orgAddr" title="${mr['page.common.address']}"/>
										<ec:column property="postCode" title="${mr['page.common.postcode']}"/>
										<ec:column property="website" title="${mr['page.common.website']}"/>
										<ec:column property="state" value="${item.state==1 ? '有效':'无效' }" title="${mr['page.common.status']}"/>
										<ec:column property="sort" title="${mr['page.common.sort']}"/>
										<ec:column property="memo1" title="${mr['page.common.memo']}1"/>
										<ec:column property="memo2" title="${mr['page.common.memo']}2"/>
										<ec:column property="memo3" title="${mr['page.common.memo']}3"/>
										<ec:column property="geoAll" title="${mr['page.common.geographicAreaCode']}"/>
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
