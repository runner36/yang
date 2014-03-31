<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<%@ include file="/commons/meta.jsp" %>
		<title>${mr['page.customize.title.afsLocMastDataImport']}</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/widgets/extremecomponents/extremecomponents.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/dms.js"></script>
		<script type="text/javascript" src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/jquery-1.2.6.min.js"></script>
		<script>
	    function query() {
			resetec();
			var form = document.mdmAfsLocationForm;
			loading();
			form.submit();
		}
		function add() {
		    location='mdmStore.do?method=add';
		}
		</script>
	</head>
	<body onLoad="WindowSollAuto()" onResize="WindowSollAuto()"><jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/mdm/mdmAfsLocation.do?method=list" method="post">
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						<!--TitleStrat-->
						${mr['page.customize.title.afsLocMastDataImport']}
						<!--TitleStrat-->
					</h4>
					<div class='MenuList'>
						<!--MenuStrat-->
						<a href="javascript:showQuery_('query_A');" id="showQuery_A"  >${mr['page.common.button.setquery']}</a>
						<a href="javascript:hideQuery_('query_A');" id="showQuery_A"  >${mr['page.common.button.close']}</a>
						<a href="javascript:query()" id="query_A">${mr['page.common.button.query']}</a>
						<a class="Im80" href="javascript:openImportForm('${ctx}', 'MdmAfsLocation');">${mr['page.common.button.importAfs']}</a>
						<a class="Im80" href="javascript:openImportForm('${ctx}', 'AfsMdmStore');">${mr['page.common.button.importMdm']}</a>
						<a class="Im80" href="javascript:openImportForm('${ctx}', 'afsStoreType');">${mr['page.common.button.importStoreType']}</a>
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
											posId：
										</td>
										<td width="1%">
											<html:text name="ec" property="$lk_posId"/>
										</td>
										
										<td width="1%">
											name：
										</td>
										<td width="1%">
											<html:text name="ec" property="$lk_name"/>
										</td>
										<td width="1%">
											customerNumner：
										</td>
										<td >
											<html:text name="ec" property="$lk_customerNumner"/>
										</td>
										</tr>
										
								</table>
							</div>
								<ec:table items="list" var="item" onInvokeAction="query()"
									form="mdmAfsLocationForm" retrieveRowsCallback="limit"
									style="width:100%" tableId="ec"
									action="mdmAfsLocation.do?method=list">
									<ec:exportXls fileName="AfsLocation.xls" tooltip="${mr['page.common.exportExcel']}" view="xls" />
									<ec:exportCsv fileName="AfsLocation.csv" tooltip="${mr['page.common.exportCVS']}" view="csv" />
									<ec:row highlightRow="true">
										<ec:column property="posId" styleClass="jxsId" title="posId"/>
										<ec:column property="name" title="name"/>
										<ec:column property="customerNumner" title="customerNumner"/>
										<ec:column property="ownerGroup" title="ownerGroup"/>
										<ec:column property="shipTo" title="shipTo"/>
										<ec:column property="posChnAddress" title="posChnAddress" />
										<ec:column property="shopCode" title="shopCode" />
										<ec:column property="franchise" title="franchise" />
										<ec:column property="startDate" title="startDate"  cell="date" format="yyyy-MM-dd "/>
										<ec:column property="closeDate" title="closeDate" cell="date" format="yyyy-MM-dd " />
										<ec:column property="existingaFlag" title="existingaFlag" />
										<ec:column property="numberFiled" title="5180" />
										<ec:column property="bbg" title="bbg" />
										<ec:column property="openMonth" title="openMonth" />
										<ec:column property="earliestOpenDate" title="earliestOpenDate" cell="date" format="yyyy-MM-dd "/>
										<ec:column property="posStatusDesc" title="posStatusDesc" />
										<ec:column property="doorTypeDesc" title="doorTypeDesc"/>
										<ec:column property="doorRankDesc" title="doorRankDesc"/>
										<ec:column property="cityZhName" title="cityZhName"/>
										<ec:column property="hotspot" title="hotspot"/>
										<ec:column property="channelDesc" title="channelDesc"/>
										<ec:column property="projectType" title="projectType"/>
										<ec:column property="floorDesc" title="floorDesc"/>
										<ec:column property="nikeSpace" title="nikeSpace"/>
										<ec:column property="sellingSpace" title="sellingSpace"/>
										<ec:column property="createDate" title="createDate" cell="date" format="yyyy-MM-dd "/>
										<ec:column property="createBy" title="createBy"/>
										<ec:column property="discountFlag" title="discountFlag"/>
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
