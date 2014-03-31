<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>${mr['page.customize.title.prodMapImport']}</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/dms.js"></script>
		<script>
		function save() {
			if (impForm.clientId.value == '') {
				alert('${mr['page.common.mess.selectClient']}');
				return;
			}
			mess.style.display = 'inline';
			impForm.submit();
		}
		function selectClient() {
			var v = openClientTree('${ctx}', '1');
			if (v) {
				impForm.clientId.value = v.id;
				impForm.clientName.value = v.text;
			}
		}
		</script>
	</head>
	<body onload="WindowSollAuto()">
	<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<form name="impForm" action="${ctx}/server/dmsProdMapping.do?method=imp&save=1" method="post" enctype="multipart/form-data">
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						${mr['page.customize.title.prodMapImport']}
					</h4>
					<div class='MenuList'>
						<a href="javascript:save();">${mr['page.common.button.save']}</a>
						<a href="${ctx}/excel/产品匹配导入模板.xls">${mr['page.common.button.template']}</a>
						<a href="dmsProdMapping.do?method=list">${mr['page.common.button.return']}</a>
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
										<td class="formTable">${mr['page.common.clientName']}<font color="#FF0000">＊</font></td>
										<td align="left">
											<input type="hidden" name="clientId"/><input type="text" name="clientName" maxlength="20" class="select_but" onclick="selectClient()" readonly/>
										</td>
									</tr>
									<tr>
										<td class="formTable">Excel数据文件</td>
										<td align="left"><input type="file" name="file" style="width:220px"></td>
									</tr>
									<tr id="mess" style="display:none">
										<td class="formTable" colspan="2"><br><br><br><br>正在导入数据，导入需要一段时间，请耐心等候......</td>
									</tr>
									<tr>
										<td colspan="4"><font color="#ff0000">${mess}</font></td>
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
