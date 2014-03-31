<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<%@ include file="/commons/meta.jsp" %>
		<base target=_self></base>
		<title>${mr['page.customize.title.dictMaintCategory']}</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script type="text/javascript" src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
		<script>
		function save() {
			if (validateData()) {
				dictform.submit();
			}
		}
		function cancel() {
			self.close();
		}
		</script>
	</head>
	<body onload="WindowSollAuto();">
		<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<form name="dictform" action="${ctx}/base/baseDict.do?method=dictsave" method="post">
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						${mr['page.customize.title.dictMaintCategory']}
					</h4>
					<div class='MenuList'>
						<a href="javascript:insertRow();">${mr['page.common.button.add']}</a>
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
								
								<div id="EBox" class="AddTableCss"><br>
								<table id="dictBox">
									<tr>
										<th>序号</th>
										<th>编码</th>
										<th>名称</th>
										<th>类型</th>
										<th>备注</th>
										<th>排序</th>
										<th>${mr['page.common.button.oper']}</th>
									</tr>
									
									<c:forEach items="${dicts}" var="dict" varStatus="v">
									<tr id="D${v.index}">
										<td>${v.index+1}</td>
										<td>
											<input type="text" name="D~dictId~${dict.dictId}" value="${dict.dictId}" maxlength="20" disabled="disabled"/>
										</td>
										<td>
											<input type="text" name="D~dictName~${dict.dictId}" value="${dict.dictName}" maxlength="50" />
										</td>
										<td>
											<input type="text" name="D~dictType~${dict.dictId}" value="${dict.dictType}" maxlength="2" style="width:30px"/>
										</td>
										<td>
											<input type="text" name="D~remark~${dict.dictId}" value="${dict.remark}" maxlength="200"/>
										</td>
										<td>
											<input type="text" name="D~sort~${dict.dictId}" value="${dict.sort}" maxlength="5" style="width:30px"/>
										</td>
										
										<td><input type="button" value="${mr['page.common.button.delete']}" onclick="deleteRow('D${v.index}')"/></td>
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
var tabId = 'dictBox';
function deleteRow(rowId) {
	if (confirm('确认要${mr['page.common.button.delete']}该行吗？')) {
		var tab = document.getElementById(tabId);
		var row = document.getElementById(rowId);
		tab.deleteRow(row.rowIndex);
	}
}
function insertRow() {
	var tab = document.getElementById(tabId);
	var row = tab.insertRow();
	var rowIndex = row.rowIndex;
	row.id = 'D' + rowIndex;

	var cell0 = row.insertCell();
	cell0.innerHTML = rowIndex;

	var cell1 = row.insertCell();
	cell1.innerHTML = '<input type="text" name="D~dictId~' + row.id + '" maxlength="20"/>';
	var cell2 = row.insertCell();
	cell2.innerHTML = '<input type="text" name="D~dictName~' + row.id + '" maxlength="50" />';
	var cell3 = row.insertCell();
	cell3.innerHTML = '<input type="text" name="D~dictType~' + row.id + '" maxlength="2" style="width:30px"/>';
	var cell4 = row.insertCell();
	cell4.innerHTML = '<input type="text" name="D~remark~' + row.id + '" maxlength="200"/>';
	var cell5 = row.insertCell();
	cell5.innerHTML = '<input type="text" name="D~sort~' + row.id + '" maxlength="5" style="width:30px"/>';
	
	var cell = row.insertCell();
	cell.innerHTML = "<input type=\"button\" value=\"${mr['page.common.button.delete']}\" onclick=\"deleteRow('" + row.id + "')\"/>";
	
}
function validateData() {
	var reg = /^[-\+]?\d+(\.\d+)?$/;
	var elements = document.getElementsByTagName('input');
	for(var i = 0; i < elements.length; i++) {
		var name = elements[i].name;
	    if (name.indexOf('D~') == 0) {
	    	var str = name.split('~');
	    	if ((str[1] == 'dictId' || str[1] == 'dictName') && elements[i].value == '') {
	    		alert('编码或名称不能为空');
	    		return false;
	    	}
	    	if (str[1] == 'sort' && !reg.test(elements[i].value)) {
	    		alert('排序必须为数字');
	    		return false;
	    	}
   		}
	}
	return true;
}
</script>
