<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>人员管理</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/widgets/extremecomponents/extremecomponents.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script type="text/javascript" src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/jquery-1.2.6.min.js"></script>
		<script>
			function query() {
				resetec();
				var form = document.baseEmployeeForm;
				form.submit();
			}
			function save() {
				var flag = true;
				var form = document.baseEmployeeForm;
				form.action = 'baseEmployee.do?method=saveCheckOrg';
				var today = getNowFormatDate();
				$('.startDate').each(function(i,n){
					var value = $(n).val();
					var t = new Date(today.replace("-",",")).getTime() ;
					var s = new Date(value.replace("-",",")).getTime() ;
					if(t >= s && flag) {  
						flag = false;
						var name = $('.jxsId',$(n).parent().parent()).text();
						alert("人员:"+name+" 建议组织的生效日期必须在今天之后！");
					}
				});
				if (flag) {
					form.submit();
				}
			}


			function doit(value,empId) {
				if (value == '') {
					document.baseEmployeeForm("emp_"+empId).value = '';
				} else {
					document.baseEmployeeForm("emp_"+empId).value=empId+'_'+value;
				}
			}
			
			function selectOrg() {
				var v = openOrgTree('${ctx}');
				if (v) {
					baseEmployeeForm.orgId_.value = v.id;
					baseEmployeeForm.orgName_.value = v.text;
					baseEmployeeForm.$lk_baseOrg_subCode.value = v.subCode;
				}
			}

		</script>
	</head>
	<body onLoad="WindowSollAuto()" onResize="WindowSollAuto()">
		<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/base/baseEmployee.do?method=list" method="post">
		<div class="bosom_one">
			<div class="bosom_top">
				<span class="left"></span>
				<h4>
					<!--TitleStrat-->
					人员管理
					<!--TitleStrat-->
				</h4>
				<div class='MenuList'>
					<!--MenuStrat-->
						<a href="javascript:showQuery_('query_A');" id="showQuery_A"  >${mr['page.common.button.setquery']}</a>
						<a href="javascript:hideQuery_('query_A');" id="showQuery_A"  >${mr['page.common.button.close']}</a>
						<a href="javascript:query()" id="query_A">${mr['page.common.button.query']}</a>
						<a href="baseEmployee.do?method=add1">${mr['page.common.button.add']}</a>
						<a href="javascript:openImportForm('${ctx}', 'BaseEmployee');">${mr['page.common.button.import']}</a>
						<a href="javascript:location.reload();">${mr['page.common.button.refresh']}</a>
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
								    <td width="1%">${mr['page.common.orgName']}</td>
								    <td width="1%"><%-- <html:text name="ec" property="$lk_baseOrg_orgName"/> --%>
									    <html:hidden property="$lk_baseOrg_subCode" />
										<html:hidden property="orgId_" />
										<html:text property="orgName_" styleClass="select_but" onclick="selectOrg()" readonly="true" />
								    </td>
								    <td width="1%">人员姓名：</td>
								    <td width="1%"><html:text name="ec" property="$lk_empName"/></td>
								    <td width="1%">人员编码：</td>
								    <td width="1%"><html:text name="ec" property="$lk_empCode"/></td>
								    <%-- <td width="1%">${mr['page.common.externalCode']}</td>
								    <td><html:text name="ec" property="$lk_extCode"/></td> --%>
								    <td width="1%">人员职务</td>
									<td >
										<html:select property="$eq_baseDictItem_dictItemId" >
											<html:option value=""></html:option>
											<html:options collection="dutys" labelProperty="itemName" property="dictItemId"/>
										</html:select> 
									</td>
							  	</tr>
							</table>
							</div>
							<ec:table items="list" var="item" onInvokeAction="query()" form="baseEmployeeForm"
							        retrieveRowsCallback="limit" style="width:100%" tableId="ec"
								    action="baseEmployee.do?method=list">
								<ec:exportXls fileName="employee.xls" tooltip="${mr['page.common.exportExcel']}" view="xls" />
								<ec:row highlightRow="true">
									
								<ec:column property="null" width="1%" title="${mr['page.common.button.oper']}" sortable="false" viewsDenied="xls">
										<center>
											<html:link page="/base/baseEmployee.do?method=edit1&empId=${item.empId}">
												<img src="${ctx}/images/edit.gif" border="0" alt="${mr['page.common.button.edit']}" title="${mr['page.common.button.edit']}" />
											</html:link>&nbsp;
											<html:link page="/base/baseEmployee.do?method=delete1&empId=${item.empId}" onclick="return confirm('${mr['page.common.mess.delete']}')">
												<img src="${ctx}/images/delete.gif" border="0" alt="${mr['page.common.button.delete']}" title="${mr['page.common.button.delete']}" />
											</html:link>
										</center>
									</ec:column>
									<ec:column property="empCode" title="人员编码"/>
									<ec:column property="extCode" title="${mr['page.common.externalCode']}"/>
									<ec:column property="empName" styleClass="jxsId" title="人员名称"/>
									<ec:column property="baseDictItem.itemName"  title="职务" alias="duty"/> 
									<ec:column property="baseOrg.pnall" title="所属组织" alias="orgName"/>
									<%-- <ec:column property="baseOrg.baseEmployee.empName" title="${mr['page.common.personInCharge']}" alias="empName"/>
									<ec:column property="a" title="${mr['page.common.proposedOrg']}" sortable="false" style="width:20%" viewsAllowed="html">
										<c:if test="${fn:length(item.empOrgCheck.sugOrgList)==1}">
											${item.empOrgCheck.sugOrgName}
											<input type="hidden" name="emp_${item.empId}" value="${item.empId}_${item.empOrgCheck.sugOrgId}" >
										</c:if> 
										<c:if test="${fn:length(item.empOrgCheck.sugOrgList)>1}">
											<bean:define id="sugOrgList" name="item" property="empOrgCheck.sugOrgList" />
											<html:select name="ec" property="sOrgId" style="width:215px" onchange="doit(this.value,${item.empId});">
												<html:option value=""></html:option>
												<html:options collection="sugOrgList" labelProperty="sugOrgName" property="sugOrgId"/>
											</html:select>
											<input type="hidden" name="emp_${item.empId}" >
										</c:if> 
									</ec:column>
									<ec:column property="b" title="${mr['page.common.effectDate']}" sortable="false" viewsAllowed="html">
										<c:if test="${fn:length(item.empOrgCheck.sugOrgList)>0}">
											<html:text property="startDate_${item.empId}" size="12" styleClass="date_but startDate" onfocus="WdatePicker()" readonly="true"/>
										</c:if> 
									</ec:column>
									<ec:column property="empOrgCheck.updateDate" title="${mr['page.common.updatedTime']}" alias="updateDate"/>
									<ec:column property="empOrgCheck.lastLogOrgName" title="${mr['page.common.afterChangeOrg']}" alias="lastLogOrgName"/>
									<ec:column property="empOrgCheck.startDate" title="${mr['page.common.effectTime']}" alias="startDate"/> --%>
									<ec:column property="baseDictItemGeo.itemName" title="${mr['page.common.geographicArea']}" alias="geoName"/>

									<ec:column property="empNameEn" title="英文名称"/>
									<ec:column property="mobilePhone" title="手机"/>
									<ec:column property="sex" title="性别"/>
									<ec:column property="state" title="${mr['page.common.status']}" value="${item.state == '1' ? '有效' : '无效'}"/>
									<ec:column property="createdBy" title="${mr['page.common.createdBy']}"/>
									<ec:column property="created" title="${mr['page.common.createdTime']}" cell="date" format="yyyy-MM-dd HH:mm"/>
									<ec:column property="updatedBy" title="${mr['page.common.updatedBy']}"/>
									<ec:column property="updated" title="${mr['page.common.updatedTime']} " cell="date" format="yyyy-MM-dd HH:mm"/>
									<ec:column property="sort" title="${mr['page.common.sort']}"/>
						
									
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
