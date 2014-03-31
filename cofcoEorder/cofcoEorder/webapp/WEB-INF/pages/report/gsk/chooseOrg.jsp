<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>水单流向报告</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script type="text/javascript" src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
		<script>
		function save() {
		     var v=salesFlowReportForm.orgName.value;
		     if(v==null||v==''){
		        alert("请选择组织名称进行查询");
		        return ;
		     }
			 salesFlowReportForm.submit();
		}
		//组织
		function selectOrg() {
		  	var v = openOrgTree('${ctx}', 0, 0, salesFlowReportForm.orgId.value);
		  	if (v) {
				salesFlowReportForm.orgId.value = v.id;
				salesFlowReportForm.orgName.value = v.text;
			}
		}
		</script>
	</head>
	<body onload="WindowSollAuto()">
		<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<form name="salesFlowReportForm" action="${ctx}/report/salesFlowReportAction.do?method=reportIndex" method="post" enctype="multipart/form-data">
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						水单流向报告
					</h4>
					<div class='MenuList'>
						<a href="javascript:save();">${mr['page.common.button.query']}</a>
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
								<table class="list_add">
									<tr>
										<td align="left" colspan="3">
											组织名称：
											<input type="hidden" name="orgId"/>
		   									<input type="text" name="orgName" readonly="readonly" class="select_but" onclick="selectOrg()"/>
		   									${mr['page.common.billDate']}
										   <input type="text" name="beginDate" onfocus="WdatePicker()" readonly="readonly" class="date_but"/>-
										   <input type="text" name="endDate" onfocus="WdatePicker()" readonly="readonly" class="date_but"/>
										</td>
									</tr>
								</table>				
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
		</form>
	</body>
</html>
