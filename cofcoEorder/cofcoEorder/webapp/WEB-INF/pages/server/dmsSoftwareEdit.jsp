<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>${mr['page.customize.title.softMaintenance']}</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script>
		function save() {
			if (validateDmsSoftwareForm(dmsSoftwareForm)) {
				if (confirm('${mr['page.common.mess.reUploadXmlFileConfirm']}！')) {
					dmsSoftwareForm.submit();
				}
			}
		}
		function selectClass(){
			var v = openDictTree('${ctx}','softClass');
			if (v) {
				dmsSoftwareForm.classId.value = v.id;
				dmsSoftwareForm.className.value = v.text;
			}
		}
		</script>
	</head>
	<body onload="WindowSollAuto()">
		<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/server/dmsSoftware.do?method=save" method="post"  enctype="multipart/form-data">
			<html:hidden property="softId" />
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						${mr['page.customize.title.softMaintenance']}
					</h4>
					<div class='MenuList'>
						<a href="javascript:save();">${mr['page.common.button.save']}</a>
						<a href="dmsSoftware.do?method=list">${mr['page.common.button.cancel']}</a>
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
										<td class="formTable">${mr['page.common.softwareName']}<font color="#FF0000">＊</font></td>
										<td align="left"><html:text property="softName" maxlength="20"/></td>
										<td class="formTable">${mr['page.common.softwareCoding']}</td>
										<td align="left"><html:text property="softCode" maxlength="20"/></td>
									</tr>
									
								    <tr>
								    	<td class="formTable">${mr['page.common.softwareVersion']}<font color="#FF0000">＊</font></td>
										<td align="left"><html:text property="softVersion" maxlength="20"/></td>
								    	<td class="formTable">${mr['page.common.softwareCategory']}</td>
										<td align="left"><html:hidden property="classId"/><html:text property="className" maxlength="20" styleClass="select_but" onclick="selectClass()" readonly="true"/></td>
								    </tr>
									
									<tr>
										<td class="formTable">${mr['page.common.specialUseMethod']}</td>
										<td align="left" colspan="3"><html:text property="confInfo" maxlength="100"/></td>
									</tr>
									
									<tr>
										<td class="formTable">${mr['page.common.profileName']}</td>
										<td align="left"><html:text property="confFile" maxlength="20" readonly="true" style="background-color: #F0F0F0"/>&nbsp;<c:if test="${dmsSoftwareForm.map.softId!=null}"><a href="${ctx}/server/dmsSoftware.do?method=download&softId=${dmsSoftwareForm.map.softId}">下载</a></c:if></td>
										<td class="formTable">${mr['page.common.button.upload']}</td>
										<td align="left" colspan="3"><html:file property="file" style="width:220px"></html:file></td>
									</tr>
									
									<tr>
										<td class="formTable">${mr['page.common.lastUploadTime']}</td>
										<td align="left" colspan="3"><fmt:formatDate value="${dmsSoftwareForm.map.uploadTime}" pattern="yyyy-MM-dd HH:mm"/></td>
									</tr>
									
									
									<tr>
										<td class="formTable">${mr['page.common.memo']}</td>
										<td align="left" colspan="3"><html:textarea property="remark" rows="3" cols="120"></html:textarea></td>
									</tr>
								</table>
								
								<div class="AddTableCss">
								<h3>${mr['page.common.mess.reUploadXmlFile']}：</h3>
								<table>
									<tr>
										<th width="1%">${mr['page.common.button.select']}</th>
										<th>${mr['page.common.clientCode']}</th>
										<th>${mr['page.common.distName']}</th>
										<th>${mr['page.common.checkDate']}</th>
										<th>${mr['page.common.returnDate']}</th>
									</tr>
									<c:forEach items="${clients}" var="item">
									<tr>
										<td><input type="checkbox" name="cbx${item.clientId}" ${item.mdmDistributor.passBackDate==null?'checked':'disabled'}/></td>
										<td>${item.clientCode}</td>
										<td>${item.mdmDistributor.distName}</td>
										<td><fmt:formatDate value="${item.mdmDistributor.checkDate}"/></td>
										<td><fmt:formatDate value="${item.mdmDistributor.passBackDate}"/></td>
									</tr>
									</c:forEach>
								</table>
								</div>
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
		<html:javascript formName="dmsSoftwareForm" staticJavascript="false" dynamicJavascript="true" cdata="false" />
		<script type="text/javascript" src="${ctx}/scripts/validator.jsp"></script>
	</body>
</html>
