<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
<head>
<title>信息发布</title>
<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
<script type="text/javascript"
	src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
<script language="javaScript" src="${ctx}/scripts/base.js"></script>

<script>
	/* String.prototype.trim = function() {
		return this.replace(/(^\s*)|(\s*$)/g, "");
	} */

	function save() {
		var strContent = document.getElementById("content").value;
		if (strContent.length > 500) {
			alert("输入内容不能超过500字符！");
			return;
		}
		document.baseMessagesForm.submit();

	}

	function selectOrg() {
		var v = openOrgTree('${ctx}', '2', '2', baseMessagesForm.orgIds.value);
		if (v) {
			baseMessagesForm.orgIds.value = v.id;
			baseMessagesForm.orgNames.value = v.text;
		}
	}
</script>
</head>
<body >
	<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
	<html:form action="/base/baseMessages.do?method=save" method="post"  enctype="multipart/form-data">
	<html:hidden property="newId" />
		<div class="bosom_one">
			<div class="bosom_top">
				<span class="left"></span>
				<h4>公告栏发布设置</h4>
				<div class='MenuList'>
					<a href="javascript:save();">保存</a> <a
						href="baseMessages.do?method=list">取消</a>
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
									<td class="formTable">接收组织<font color="#FF0000">＊</font>
									</td>
									<td align="left" colspan="3"><html:hidden
											property="orgIds" /> <input type="button" class="button"
										value="选择组织" onclick="selectOrg()" />
									</td>
								</tr>
								<tr>
									<td class="formTable">&nbsp;</td>
									<td align="left" colspan="3"><html:textarea
											property="orgNames" rows="6" readonly="true"  style="width: 520px"></html:textarea>
									</td>
								</tr>
								<tr>
									<td class="formTable">公告类型<font color="#FF0000">＊</font>
									</td>
									<td width="">		
							<c:choose>
							<c:when test="${flag=='1'}">
									<select name="ec" name="type"  style="width: 84px">
											<c:if test="${type==0}">
											<option value="0" selected="selected">公告</option>
											</c:if>
											<c:if test="${type==1 }">
											<option value="1">帮助</option>
											</c:if>
							</select>  
									</c:when>
									<c:otherwise>
									 <html:select name="ec" property="type"
											style="width: 84px">
											<html:option value="0">公告</html:option>
											<html:option value="1">帮助</html:option>
									</html:select>
									</c:otherwise>	
							</c:choose>
							
									</td>
								<tr>
									<td class="formTable">重要程度</td>
									<td width="">
									<html:select name="ec" property="isdel"
											style="width: 84px">
											<html:option value="1">重要</html:option>
											<html:option value="">一般</html:option>
									</html:select>
									</td>
								</tr>
								<tr>
									<td class="formTable">主 题<font color="#FF0000">＊</font>
									</td>
									<td align="left" colspan="3"><html:text
											property="title" maxlength="25" style="width: 520px" />
									</td>
								</tr>
								<tr>
									<td class="formTable">内 容<font color="#FF0000">＊</font><br>(最多输入500字符)</td>
									<td align="left" colspan="3"><html:textarea styleId="content" property="content" rows="16" cols="100"></html:textarea>
									</td>
								</tr>
								<tr>
									<td class="formTable">附件上传<font color="#FF0000"></font><br>(最大不能超过3M)</td>
									<td align="left" colspan="3"><input type="file"
										name="filename" style="width: 220px">
									</td>
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
	<html:javascript formName="baseMessagesForm" staticJavascript="false"
		dynamicJavascript="true" cdata="false" />
	<script type="text/javascript" src="${ctx}/scripts/validator.jsp"></script>
</body>
</html>
