<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>产品匹配导入删除</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>
		<script language="javaScript" src="${ctx}/scripts/dms.js"></script>
		<script>
		function save() {			
			var form=document.dmsProdMappingForm;
			var fileName=document.getElementById("file").value
			if(fileName==''){
				alert("${mr['.common.selectDataFile']}");
				return;
			}
			if(!confirm("确定对匹配关系进行“导入删除”操作吗?")){
				return;
			}
			mess.style.display = 'inline';
			form.submit();
		}
		</script>
	</head>
	<body onload="WindowSollAuto()">
		<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/server/dmsProdMapping.do?method=delMappingFromImp" method="post" enctype="multipart/form-data">
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						产品匹配导入删除
					</h4>
					<div class='MenuList'>
						<a href="${ctx}/templet/产品匹配导入删除模板.xls">${mr['page.common.button.template']}</a>
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
