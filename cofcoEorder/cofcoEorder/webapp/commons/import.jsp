<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<%@ include file="/commons/meta.jsp" %>
		<base target=_self></base>
		<title>${mr['page.customize.title.dataImport']}</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script>
		function save() {
			loading();
			impForm.submit();
		}
		function test() {
			loading();
			impForm.test.value = '1';
			impForm.submit();
		}
		function template() {
			temp.location = '${ctx}/imp/import.do?save=1&impName=${param.impName}&type=xls&template=1';
		}
		</script>
	</head>
	<body onload="WindowSollAuto()">
	<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<form name="impForm" action="${ctx}/imp/import.do?save=1&impName=${param.impName}" method="post" enctype="multipart/form-data">
		<input type="hidden" name="test"><input type="hidden" name="testFile" value="${testFile}">
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						${mr['page.customize.title.dataImport']}
					</h4>
					<div class='MenuList'>
						<a href="javascript:save();">${mr['page.common.button.import']}</a>
						<a href="javascript:test();">${mr['page.common.button.test']}</a>
						<a href="javascript:template();">${mr['page.common.button.template']}</a>
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
										<td class="formTable">${mr['page.common.fileType']}</td>
										<td align="left">
											<select name="type">
												<option value="xls" ${type=='xls' ? 'selected' : ''}>EXCEL</option>
												<option value="," ${type==',' ? 'selected' : ''}>${mr['page.common.textFile']}(","${mr['page.common.split']})</option>
												<option value="|" ${type=='|' ? 'selected' : ''}>${mr['page.common.textFile']}("|"${mr['page.common.split']})</option>
											</select>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<select name="zip">
												<option>--${mr['page.common.compressionType']}--</option>
												<option value="1" ${type=='1' ? 'selected' : ''}>${mr['page.common.zipArchive']}</option>
												<option value="2" ${type=='2' ? 'selected' : ''}>${mr['page.common.gzArchive']}</option>
											</select>
										</td>
									</tr>
									<tr>
										<td class="formTable">${mr['page.common.dataFile']}</td>
										<td align="left"><input type="file" name="file" style="width:320px"></td>
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
<iframe id="temp" name="temp" height="0px" width="0px"></iframe>