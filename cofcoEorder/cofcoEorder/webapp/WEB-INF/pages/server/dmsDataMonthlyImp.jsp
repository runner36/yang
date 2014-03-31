<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>${mr['page.customize.title.uploadDataOfMonthCheck']}</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>
		<script language="javaScript" src="${ctx}/scripts/dms.js"></script>
		<script>
		function save() {
			if (dmsDataMonthlyForm.clientId.value == '') {
				alert('${mr['page.common.mess.selectClient']}');
				return;
			}
			mess.style.display = 'inline';
			dmsDataMonthlyForm.submit();
		}
		function selectClient() {
			var form = document.dmsDataMonthlyForm;
			var v = openClientTree('${ctx}', '1');
			if (v) {
				form.clientId.value = v.id;
				form.clientName.value = v.text;
			}
		}
		</script>
	</head>
	<body onload="WindowSollAuto()">
		<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/server/dmsDataMonthly.do?method=imp" method="post" enctype="multipart/form-data">
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						${mr['page.customize.title.uploadDataOfMonthCheck']}
					</h4>
					<div class='MenuList'>
						<a href="${ctx}/templet/月校验数据模板.xls">${mr['page.common.button.template']}</a>
						<a href="javascript:save();">${mr['page.common.button.upload']}</a>
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
										<td class="formTable" style="width:80px">${mr['page.common.monthyuefen']}</td>
										<td align="left" colspan="3">
											<html:select property="year" style="width:60px">
												<html:option value="2005">2005</html:option>
												<html:option value="2006">2006</html:option>
												<html:option value="2007">2007</html:option>
												<html:option value="2008">2008</html:option>
												<html:option value="2009">2009</html:option>
												<html:option value="2010">2010</html:option>
												<html:option value="2011">2011</html:option>
												<html:option value="2012">2012</html:option>
												<html:option value="2013">2013</html:option>
												<html:option value="2014">2014</html:option>
												<html:option value="2015">2015</html:option>
												<html:option value="2016">2016</html:option>
												<html:option value="2017">2017</html:option>
												<html:option value="2018">2018</html:option>
												<html:option value="2019">2019</html:option>
												<html:option value="2020">2020</html:option>
											</html:select>${mr['page.common.year']}　
											<html:select property="month" style="width:40px">
												<html:option value="1">1</html:option>
												<html:option value="2">2</html:option>
												<html:option value="3">3</html:option>
												<html:option value="4">4</html:option>
												<html:option value="5">5</html:option>
												<html:option value="6">6</html:option>
												<html:option value="7">7</html:option>
												<html:option value="8">8</html:option>
												<html:option value="9">9</html:option>
												<html:option value="10">10</html:option>
												<html:option value="11">11</html:option>
												<html:option value="12">12</html:option>
											</html:select>${mr['page.common.month']}
										</td>
									</tr>
									<tr>
										<td class="formTable">${mr['page.common.clientName']}</td>
										<td align="left" colspan="3"><html:hidden property="clientId"/><html:text property="clientName" styleClass="select_but" onclick="selectClient()" readonly="true"/></td>
									</tr>
									<tr>
										<td class="formTable">${mr['page.common.excelFile']}</td>
										<td align="left" colspan="3"><input type="file" name="file" style="width:220px"></td>
									</tr>
									<tr>
										<td class="formTable" colspan="4"></td>
									</tr>
									<tr id="mess" style="display:none">
										<td class="formTable" colspan="4">${mr['page.common.mess.wait']}......</td>
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
		</html:form>
	</body>
</html>
