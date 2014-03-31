<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/meta.jsp"%>
<title>${mr['page.customize.title.manualCallJob']}</title>
<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
<script type="text/javascript"
	src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery-1.2.6.min.js"></script>
<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>
<script language="javaScript" src="${ctx}/scripts/base.js"></script>
<script>
	function selectDist() {
		var v = openDistTree('${ctx}', '2', '0', $("#distId").attr("value"));
		if (v) {
			$("#distId").attr("value", v.id);
			$("#distName").attr("value", v.text);
		}
	}
	function submit() {
		$("#create").attr("disabled", "disabled");
		loading();
		$.ajax({
			type : "POST",
			url : "${ctx}/server/dmsJob.do?method=callJob",
			data : "jobCode=" + $("#jobCode").attr("value") + "&statrDate="
					+ $("#statrDate").attr("value") + "&endDate="
					+ $("#endDate").attr("value") + "&distCode="
					+ $("#distCode").attr("value"),
			success : function(msg) {
				$("#loading_wrap").css("display", "none");
				$("#create").attr("disabled", "");
				alert(msg);
			}
		});

	}
	function subLoad() {
		$("#createLoad").attr("disabled", "disabled");
		loading();
		$.ajax({
			type : "POST",
			url : "${ctx}/server/dmsJob.do?method=loadJob",
			data : "loadJobCode=" + $("#loadJobCode").attr("value")
					+ "&loadStatrDate=" + $("#loadStatrDate").attr("value")
					+ "&fileName=" + $("#fileName").attr("value")
					+ "&loadEndDate=" + $("#loadEndDate").attr("value"),
			success : function(msg) {
				$("#loading_wrap").css("display", "none");
				$("#createLoad").attr("disabled", "");
				alert(msg);
			}
		});

	}

	function callJob() {
		var valstr = $("#jobCode").attr("value");
		if (valstr == 'eis_weeklyInventoryDeltaJob'
				|| valstr == 'eis_dailyDeltaJob') {
			$("#endSpan").css("display", '');
		} else {
			$("#endSpan").css("display", 'none');
		}
	}
</script>
</head>

<body onload="WindowSollAuto()" onresize="WindowSollAuto()">
	<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
	<div class="bosom_one">
		<div class="bosom_top">
			<span class="left"></span>
			<h4>
				<!--PageTitleStrat-->
				${mr['page.customize.title.manualCallJob']}
				<!--PageTitleEnd-->
			</h4>
			<div class='MenuList'></div>

			<span class="right"></span>
		</div>
		<div class="bosom_side">
			<div class="casing">
				<div class="caput">
					<span class="left"></span><span class="right"></span>
				</div>
				<div class="viscera" id="SollAuto">
					<div class="sheet_div" style="padding: 20px">
						<div class="searchbox" id="searchbox">
							<table>
								<tr>
									<td width="1%">回传对象：</td>
									<td width="1%"><select name="jobCode" id="jobCode"
										style="width: 200px" onchange="callJob()">
											<option>${mr['page.common.button.pleaseSelect']}</option>
											<option value="edifice">edifice</option>
											<option value="edificeSevenWeeks">edificeSevenWeeks</option>
											<option value="QlikviewJob-dailyJob">QlikviewJob-dailyJob</option>
											<option value="QlikviewJob-weeklyJob">QlikviewJob-weeklyJob</option>

											<option value="eis_weeklyInventoryDeltaJob">eis_weeklyInventoryDeltaJob</option>
											<option value="eis_dailyDeltaJob">eis_dailyDeltaJob</option>
									</select></td>

									<td width="1%">时间：</td>
									<td width="1%"><input type="text" name="statrDate"
										id="statrDate" onfocus="WdatePicker()" readonly="readonly"
										class="date_but" /> <span id="endSpan" style="display: none">-<input
											type="text" name="endDate" id="endDate"
											onfocus="WdatePicker()" readonly="readonly" class="date_but" />
									</span></td>

									<td width="1%">${mr['page.common.distCode']}</td>
									<td colspan="3"><input type="text" name="distCode"
										id="distCode" /> &nbsp;&nbsp;&nbsp;&nbsp;<input type="button"
										id="create" value="${mr['page.common.button.start']}" class="button"
										onclick="javascript:submit();" /></td>

								</tr>
								<tr>
									<td width="1%">Belle数据抽取：</td>
									<td width="1%"><select name="loadJobCode" id="loadJobCode"
										style="width: 200px">
											<option>${mr['page.common.button.pleaseSelect']}</option>
											<option value="loadBelleWeekData">周销售数据</option>
											<option value="loadBelleMonthlyData">月销售数据</option>
											<option value="loadBelleWeeklyStockData">周库存数据</option>
											<option value="loadBelleStockData">月库存数据</option>
									</select></td>
									<td width="1%">时间：</td>
									<td colspan="1"><input type="text" name="loadStatrDate"
										id="loadStatrDate" onfocus="WdatePicker()" readonly="true"
										class="date_but" /> <span id="spDate" style="display: none">-<input
											type="text" name="loadEndDate" id="loadEndDate"
											onfocus="WdatePicker()" readonly="true" class="date_but" />
									</span>
									</td>
									<td width="1%">文件名称：</td>
									<td colspan="1"><span id="spfileName"><input
											type="text" id="fileName" name="fileName" /> &nbsp;&nbsp;&nbsp;<input type="button" id="createLoad"
										value="${mr['page.common.button.start']}" class="button" onclick="javascript:subLoad();" />
									</span></td>
								</tr>
								<tr>
									<td colspan="4">Belle数据抽取使用说明：<br>
										周销售必须给文件名，例如“周库存报表2011.6.6-6.12.htm”。<br>
										如果其他类型的文件belle给的不对，则把不对的文件名填入“文件名称”中即可。<br>
										<table>
											<tr>
												<td>类型</td>
												<td>时间参数</td>
												<td>正确ftp文件名举例</td>
											</tr>
											<tr>
												<td>周销售数据</td>
												<td>必须为周日</td>
												<td>2011年06月/20110605-20110611.rar</td>
											</tr>
											<tr>
												<td>月销售数据</td>
												<td>必须为月的第一天</td>
												<td>20110501-20110531-销售.rar</td>
											</tr>
											<tr>
												<td>周库存数据</td>
												<td>想要加载到的库存日期</td>
												<td>周库存/周库存报表2011.6.6-6.12.htm</td>
											</tr>
											<tr>
												<td>月库存数据</td>
												<td>必须为月的最后一天</td>
												<td>20110531-库存.rar</td>
											</tr>
										</table></td>
								</tr>
							</table>
						</div>
						<br></br>
						<div></div>
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
</body>
</html>


