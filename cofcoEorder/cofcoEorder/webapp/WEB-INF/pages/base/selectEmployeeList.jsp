<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<%@ include file="/commons/meta.jsp" %>
		<base target=_self></base>
		<title>${mr['page.customize.title.selectPersonnel']}</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/widgets/extremecomponents/extremecomponents.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script>
			function query() {
				var form = document.selectEmployeeForm;
				form.submit();
			}
			
			var oid = '';
			var text = '';
			
			function ok() {
				if (oid != '') {
					window.returnValue = {id:oid,text:text};
					self.close();
					return;
				}
			
				var ids = selectEmployeeForm.ids.value;
				var texts = selectEmployeeForm.texts.value;
				
				var idsLen = ids.length;
				if (idsLen > 1) {
					ids = ids.substring(1,idsLen-1)
				}
				else {
					ids = '';
				}
				
				var textsLen = texts.length;
				if (textsLen > 1) {
					texts = texts.substring(1,textsLen-1)
				}
				else {
					texts = '';
				}
				
				window.returnValue = {id:ids,text:texts};
				self.close();
			}
			
			function cancel() {
				self.close();
			}
			
			function check(checked, id, text) {
				var ids = selectEmployeeForm.ids;
				var texts = selectEmployeeForm.texts;
				
				if (ids.value == '') {
					ids.value = ',';
					texts.value = ',';
				}
				
				if (checked) {
					ids.value += id + ',';
					texts.value += text + ',';
				}
				else {
					ids.value = ids.value.replace(',' + id + ',', ',');
					texts.value = texts.value.replace(',' + text + ',', ',');
				}
			}
			
			function init() {
				var ids = selectEmployeeForm.ids.value;
				
				var idArray = ids.split(',');
				for (var i = 0; i < idArray.length; i++) {
					var id = idArray[i];
					if (id != '') {
						eval('if (selectEmployeeForm.cbx' + id + ') selectEmployeeForm.cbx' + id + '.checked=true;');
					}
				}
				
			}
		</script>
	</head>
	<body onLoad="WindowSollAuto();init()" onResize="WindowSollAuto()">
		<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/base/selectEmployee.do?method=list" method="post">
		<html:hidden name="seleEmp" property="ids"/>
		<html:hidden name="seleEmp" property="texts"/> 
		<div class="bosom_one">
			<div class="bosom_top">
				<span class="left"></span>
				<h4>
					<!--TitleStrat-->
					${mr['page.customize.title.selectPersonnel']}
					<!--TitleStrat-->
				</h4>
				<div class='MenuList'>
					<!--MenuStrat-->
					<a href="javascript:selectEmployeeForm.seleEmp_p.value=1;query()">${mr['page.common.button.query']}</a>
					<a href="javascript:ok()">${mr['page.common.button.ok']}</a>
					<a href="javascript:cancel()">${mr['page.common.button.cancel']}</a>
					<!--MenuEnd-->
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
							<div class="searchbox" id="searchbox">
							<table>
							  	<tr>
								    <td width="1%">人员名称：</td>
								    <td width="1%"><html:text name="seleEmp" property="$lk_empName"/></td>
								    <td width="1%">人员编码：</td>
								    <td><html:text name="seleEmp" property="$lk_empCode"/></td>
							  	</tr>
							</table>
							</div>
							
							<ec:table items="list" var="item" onInvokeAction="query()" form="selectEmployeeForm"
							        retrieveRowsCallback="limit" style="width:100%" tableId="seleEmp"
								    action="selectEmployee.do?method=list">
								<ec:row highlightRow="true">
						
									<ec:column property="null" width="1%" title="${mr['page.common.button.select']}" sortable="false" viewsDenied="xls">
										<center>
											<c:if test="${seleEmp.checkbox!='1'}">
											<input type="radio" name="r1" onClick="oid='${item.empId}';text='${item.empName}'"/>
											</c:if>
											<c:if test="${seleEmp.checkbox=='1'}">
											<input type="checkbox" name="cbx${item.empId}" onClick="check(this.checked, '${item.empId}', '${item.empName}')"/>
											</c:if>
										</center>
									</ec:column>
								
									<ec:column property="empName" title="人员名称"/>
									<ec:column property="empNameEn" title="英文名称"/>
									<ec:column property="empCode" title="人员编码"/>
									<ec:column property="extCode" title="${mr['page.common.externalCode']}"/>
									<ec:column property="baseOrg.pnall" title="所属组织" alias="orgName"/>
									<ec:column property="sex" title="性别"/>
								</ec:row>
							</ec:table>
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
