<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<%@ include file="/commons/meta.jsp" %>
		<title>${mr['page.customize.title.afsProdMastDataImport']}</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/widgets/extremecomponents/extremecomponents.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/dms.js"></script>
		<script>
	    function query() {
			resetec();
			var form = document.mdmAfsProductForm;
			loading();
			form.submit();
		}
		function add() {
		    location='mdmProduct.do?method=add';
		}
		</script>
	</head>
	<body onload="WindowSollAuto()" onresize="WindowSollAuto()"><jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/mdm/mdmAfsProduct.do?method=list" method="post">
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						<!--TitleStrat-->
						${mr['page.customize.title.afsProdMastDataImport']}
						<!--TitleStrat-->
					</h4>
					<div class='MenuList'>
						<!--MenuStrat-->
						<a href="javascript:showQuery_('query_A');" id="showQuery_A">${mr['page.common.button.setquery']}</a>
						<a href="javascript:hideQuery_('query_A');" id="hideQuery_A">${mr['page.common.button.close']}</a>
						<a href="javascript:query()" id="query_A">${mr['page.common.button.query']}</a>
						<a class="Im80" href="javascript:openImportForm('${ctx}', 'MdmAfsProduct');">${mr['page.common.button.importAfs']}</a>
						<a class="Im80" href="javascript:openImportForm('${ctx}', 'AfsMdmProduct');">${mr['page.common.button.importMdm']}</a>
						<a class="Im80" href="javascript:openImportForm('${ctx}', 'ProdType');">${mr['page.common.button.importProdType']}</a>
						<a class="Im80" href="javascript:openImportForm('${ctx}', 'ProdBrand');">${mr['page.common.button.importProdBrand']}</a>
						<a class="Im80" href="javascript:openImportForm('${ctx}', 'ProdOther');">${mr['page.common.button.importProdSeries']}</a>
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
										<td width="1%">
											materialNumber：
										</td>
										<td width="1%">
											<html:text name="ec" property="$lk_materialNumber"/>
										</td>
										
										<td width="1%">
											styleNumber：
										</td>
										<td width="1%">
											<html:text name="ec" property="$lk_styleNumber"/>
										</td>
										<td width="1%">
											colorNumber：
										</td>
										<td width="1%">
											<html:text name="ec" property="$lk_colorNumber"/>
										</td>
										<td width="1%">
											size：
										</td>
										<td >
											<html:text name="ec" property="$lk_size"/>
										</td>
										</tr>
										
								</table>
							</div>
								<ec:table items="list" var="item" onInvokeAction="query()"
									form="mdmAfsProductForm" retrieveRowsCallback="limit"
									style="width:100%" tableId="ec"
									action="mdmAfsProduct.do?method=list">
									<ec:exportXls fileName="${mr['page.common.afsProdMastData']}.xls" tooltip="${mr['page.common.exportExcel']}" view="xls" />
									<ec:exportCsv fileName="${mr['page.common.afsProdMastData']}.csv" tooltip="${mr['page.common.exportCVS']}" view="csv" />
									<ec:row highlightRow="true">
										<ec:column property="materialNumber" title="materialNumber"  />
										<ec:column property="styleNumber" title="styleNumber" />
										<ec:column property="colorNumber" title="colorNumber"/>
										<ec:column property="longMaterialNameEng" title="longMaterialNameEng"  />
										<ec:column property="longColorNameEnglish" title="longColorNameEnglish"  />
										<ec:column property="color" title="color"/>
										<ec:column property="localPrimaryClrDesc" title="localPrimaryClrDesc"/>
										<ec:column property="seasonCode" title="seasonCode"  />
										<ec:column property="seasonYear" title="seasonYear"/>
										<ec:column property="lifeCycleDesc" title="lifeCycleDesc" />
										<ec:column property="lifeCycle" title="lifeCycle" />
										<ec:column property="genderAgeDescription" title="genderAgeDescription" />
										<ec:column property="genderAge" title="genderAge" />
										<ec:column property="genderAgeSummary" title="genderAgeSummary" />
										<ec:column property="bizOrgName" title="bizOrgName"  />
										<ec:column property="sapCatDesc" title="sapCatDesc" />
										<ec:column property="sapSubCatDesc" title="sapSubCatDesc"  />
										<ec:column property="sportActivity" title="sportActivity" />
										<ec:column property="segmentationDescription" title="segmentationDescription"  />
										<ec:column property="silhouetteType" title="silhouetteType"  />
										<ec:column property="silhouetteName" title="silhouetteName" />
										<ec:column property="primaryMaterialContentDesc" title="primaryMaterialContentDesc"  />
										<ec:column property="size" title="size" />
										<ec:column property="sugRetPrice" title="sugRetPrice" />
										<ec:column property="wsPrice" title="wsPrice" />
										<ec:column property="firstProductOfferDate" title="firstProductOfferDate"  cell="date" format="yyyy-MM-dd" />
										<ec:column property="endProductOfferDate" title="endProductOfferDate"  cell="date" format="yyyy-MM-dd" />
										<ec:column property="endFutureOfferDate" title="endFutureOfferDate"  cell="date" format="yyyy-MM-dd" />
										<ec:column property="glblCatSummName" title="glblCatSummName" />
										<ec:column property="glblCatCorefocusName" title="glblCatCorefocusName" />
										<ec:column property="technology" title="technology" />
										<ec:column property="equipmentbssaflag" title="equipmentbssaflag" />
										<ec:column property="footwearSilo" title="footwearSilo" />
										<ec:column property="divisionDescription" title="divisionDescription" />
										<ec:column property="msc1" title="msc1" />
										<ec:column property="msc2" title="msc2" />
										<ec:column property="msc3" title="msc3" />
										<ec:column property="fabricationDesc" title="fabricationDesc" />
										<ec:column property="construction" title="construction" />
										<ec:column property="createdDate" title="createdDate" cell="date" format="yyyy-MM-dd" />
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
