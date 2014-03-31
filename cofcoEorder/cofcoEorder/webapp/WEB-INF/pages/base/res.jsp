<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<%@ include file="/commons/meta.jsp" %>
		<base target=_self></base>
		<title>${mr['page.customize.title.columnInfoMaintenance']}</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/DateControl.js"></script>
		<script>
		function save() {
			if (validateData()) {
				resform.submit();
			}
		}
		function cancel() {
			self.close();
		}
		</script>
	</head>
	<body onload="WindowSollAuto();">
		<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<form name="resform" action="${ctx}/base/baseMessRes.do?method=save&reportName=${param.reportName}&func=${param.func}" method="post">
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						${mr['page.customize.title.columnInfoMaintenance']}
					</h4>
					<div class='MenuList'>
						<a href="javascript:save();">${mr['page.common.button.save']}</a>
						<a href="javascript:cancel();">${mr['page.common.button.cancel']}</a>
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
								
								<div class="AddTableCss"><br>
								<table>
									<tr>
										<th>${mr['page.common.name']}</th>
										<th>${mr['page.common.title']}</th>
										<th>${mr['page.common.newTitle']}</th>
										<th>${mr['page.common.queue']}</th>
										<th><a  onclick="checkall()">${mr['page.common.button.select']}</a></th>
									</tr>
									
									<c:forEach items="${columns}" var="col" varStatus="v">
									<tr>
										<td>${col.property}</td>
										<td>${col.title}</td>
										<td width="1">
											<input type="text" name="d_value_${col.property}" value="${col.newTitle}" maxlength="100" style="width:220px"/>
										</td>
										<td width="1">
											<input type="text" name="d_sort_${col.property}" value="${col.sort}" maxlength="8" style="width:30px"/>
										</td>
										<td width="50">
											<input type="checkbox" name="d_state_${col.property}" value="1" ${col.state!='0'?'checked':''}/>
										</td>
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
		</form>
	</body>
</html>
<script>
function validateData() {
	var reg = /^[-\+]?\d+(\.\d+)?$/;
	var elements = document.getElementsByTagName('input');
	for(var i = 0; i < elements.length; i++) {
		var name = elements[i].name;
	    if (name.indexOf('d_') == 0) {
	    	var str = name.split('_');
	    	//if (str[1] == 'value' && elements[i].value == '') {
	    	//	alert('名称不能为空');
	    	//	return false;
	    	//}
	    	if (str[1] == 'sort' && elements[i].value != '' && !reg.test(elements[i].value)) {
	    		alert('${mr['page.common.mess.orderMustNumber']}');
	    		return false;
	    	}
   		}
	}
	return true;
}
var state_checked = true;
function checkall() {
	state_checked = !state_checked;
	var elements = document.getElementsByTagName('input');
	for(var i = 0; i < elements.length; i++) {
		var name = elements[i].name;
	    if (name.indexOf('d_state_') == 0) {
	    	elements[i].checked = state_checked;
   		}
	}
}
</script>
