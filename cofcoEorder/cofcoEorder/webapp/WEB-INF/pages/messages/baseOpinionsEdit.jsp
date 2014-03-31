<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
<head>
<title>反馈信息填写</title>
<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
<script type="text/javascript"
	src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
<script language="javaScript" src="${ctx}/scripts/base.js"></script>

<script>
	String.prototype.trim = function() {
		return this.replace(/(^\s*)|(\s*$)/g, "");
	}

	function save() {
		var content = document.getElementById("content").value.trim();
		if (content.length > 500) {
			alert("输入内容不能超过500字符！")
			return;
		}
		baseOpinionsForm.submit();

	}

</script>
</head>
<body >
	<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
	<html:form action="/base/baseOpinions.do?method=save&flag=1" method="post"  enctype="multipart/form-data">
	<html:hidden property="id" />
		<div class="bosom_one">
			<div class="bosom_top">
				<span class="left"></span>
				<h4>反馈信息填写</h4>
				<div class='MenuList'>
					<a href="javascript:save();">保存</a> <a
						href="baseOpinions.do?method=list&flag=1">取消</a>
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
									<td class="formTable">主 题<font color="#FF0000">＊</font>
									</td>
									<td align="left" colspan="3"><html:text
											property="title" maxlength="25" style="width: 520px" />
									</td>
								</tr>
								<tr>
									<td class="formTable">内 容<font color="#FF0000">＊</font><br>(最多输入500字符)</td>
									<td align="left" colspan="3"><html:textarea
											property="content" rows="16" cols="100"></html:textarea>
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
	<html:javascript formName="baseOpinionsForm" staticJavascript="false"
		dynamicJavascript="true" cdata="false" />
	<script type="text/javascript" src="${ctx}/scripts/validator.jsp"></script>
</body>
</html>
