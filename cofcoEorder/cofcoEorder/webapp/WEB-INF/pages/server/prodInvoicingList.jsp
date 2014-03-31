<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
	<%@ include file="/commons/meta.jsp" %>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/widgets/extremecomponents/extremecomponents.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>
		<script language="javaScript" src="${ctx}/scripts/dms.js"></script>
		<script>
		function query() {
			loading();
			dmsDataProductForm.submit();
		}
		function exportXls() {
			try{onSubmit()}catch(e){}
			var a = dmsDataProductForm.action;
			dmsDataProductForm.action = a + '?view=xls';
			dmsDataProductForm.submit();
			dmsDataProductForm.action = a;
		}
		function exportCsv() {
			try{onSubmit()}catch(e){}
			var a = dmsDataProductForm.action;
			dmsDataProductForm.action = a + '?view=csv';
			dmsDataProductForm.submit();
			dmsDataProductForm.action = a;
		}
  		</script>
	</head>
	<body onload="WindowSollAuto();disabledInit();"><jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/server/dmsDataProduct.do?method=dateSum" method="post">
			<div class="bosom_one">
				<div class="bosom_side">
					<div class="casing">
						<div class="caput">
							<span class="left"></span><span class="right"></span>
						</div>
						<div class="viscera" id="SollAuto">
							<div class="sheet_div">
								<ec:table items="list" var="item" onInvokeAction="query()"
										form="dmsDataProductForm" retrieveRowsCallback="limit"
										style="width:100%" tableId="ec_sale"
										 sortRowsCallback="limit"
										action="dmsDataProduct.do?method=dateSum">
								<ec:exportXls fileName="进销存产品.xls" tooltip="${mr['page.common.exportExcel']}" view="xls" />
								<ec:exportCsv fileName="进销存产品.csv" tooltip="${mr['page.common.exportCVS']}" view="csv" />
									<ec:row highlightRow="true">
										<ec:column property="0" title="${mr['page.common.distCode']}" alias="DISTCODE" />
										<ec:column property="1" title="${mr['page.common.distName']}" alias="DISTNAME"/>
										<ec:column property="2"  title="产品编码"  alias="TARGETPRODCODE"/>
										<ec:column property="3" title="${mr['page.common.distProdName']}" alias="PRODNAME"/>
										<ec:column property="4" title="期初日期" alias="STARTDATE"/>
										<ec:column property="5" title="期末日期" alias="ENDDATE"/>
										<ec:column property="6" title="期初库存金额" alias="STARTSTOCK" style="text-align:right;" cell="currency" format="#,##0.00" />
										<ec:column property="7" title="期间进货金额"  alias="PURCHASEGROSS" style="text-align:right;" cell="currency" format="#,##0.00" />
										<ec:column property="8" title="期间销售金额" alias="SALEGORSS" style="text-align:right;" cell="currency" format="#,##0.00" />
										<ec:column property="9" title="期末库存金额" alias="ENDSTOCK" style="text-align:right;" cell="currency" format="#,##0.00" />
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
