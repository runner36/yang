<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<%@ include file="/commons/meta.jsp"%>
		<title>订单员_组织_产品组关系维护</title>
		<link rel="stylesheet" type="text/css"
			href="${ctx}/styles/sidecss.css">
		<link rel="stylesheet" type="text/css"
			href="${ctx}/widgets/extremecomponents/extremecomponents.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/dms.js"></script>
		<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>
		<script type="text/javascript"
			src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript"
			src="${ctx}/scripts/jquery-1.2.6.min.js"></script>
		<script>
	    function query() {
			resetec();
			var form = document.mdmDistOrderempProdgroupForm;
			loading();
			form.submit();
		}
		function add() {
		    location='mdmDistOrderempProdgroup.do?method=add';
		}
	
		//经销商
  // function selectDist() {
		//	var form = document.mdmDistOrderempProdgroupForm;
		//	var v = openDistTree('${ctx}','','1', mdmDistOrderempProdgroupForm.$eq_baseOrg_orgId.value);
		//	if (v) {
		//		mdmDistOrderempProdgroupForm.$eq_baseOrg_orgId.value = v.id;
		//		mdmDistOrderempProdgroupForm.distName.value = v.text;
		//	}
	//	}


   //组织
	function selectOrg() {
		var v = openOrgTree('${ctx}');
		if (v) {
			mdmDistOrderempProdgroupForm.orgId_.value = v.id;
			mdmDistOrderempProdgroupForm.orgName_.value = v.text;
			mdmDistOrderempProdgroupForm.$lk_baseOrg_subCode.value = v.subCode;
		}
	}

	//渠道
	function selectDict(dictName, id, name) {
		var v = openDictTree('${ctx}', dictName, 2, 2, id.value,'',1);
		if (v) {
			id.value = v.id;
			name.value = v.text;
		}
	}
	
	
	//产品
	function selectProd(id, name) {
	var form = document.mdmDistOrderempProdgroupForm;
		var v = openProdTree('${ctx}',form.prodBrandId.value, '2','2');
		if (v) {
			form.prodBrandId.value.value = v.id;
			form.prodBrandName.value = v.text;
		}
	}
	
	//人员
	   function selectEmp() {
			var form = document.mdmDistOrderempProdgroupForm;
			var v = openEmpTreeOM('${ctx}', '', '2', form.$eq_baseEmployee_empId.value);
			if (v) {
				form.$eq_baseEmployee_empId.value = v.id;
				form.empName_.value = v.text;
			}
		}
	
		</script>
	</head>
	<body onLoad="WindowSollAuto()" onResize="WindowSollAuto()"><jsp:include
			page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/order/mdmDistOrderempProdgroup.do?method=list"
			method="post">
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						<!--TitleStrat-->
						订单员_组织_产品组关系维护
						<!--TitleStrat-->
					</h4>
					<div class='MenuList'>
						<!--MenuStrat-->
						<a href="javascript:showQuery_('query_A');" id="showQuery_A" >设置查询条件</a>
						<a href="javascript:hideQuery_('query_A');" id="showQuery_A" >关闭</a>
						<a href="javascript:mdmDistOrderempProdgroupForm.ec_p.value=1;query()" id="query_A">查询</a>
						<a href="mdmDistOrderempProdgroup.do?method=add">新增</a>
						<a href="javascript:openImportForm('${ctx}', 'MdmDistOrderempProdgroup');">${mr['page.common.button.import']}</a>
	                 <!--   <a href="${ctx}/order/mdmDistOrderempProdgroup.do?method=importCon">导入</a>-->
						<a href="javascript:location.reload();">刷新</a>
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
											<td width="1%" style="text-align: right">
												 组织：
											</td>
											<td width="1%"><html:hidden property="$lk_baseOrg_subCode" />
												<html:hidden property="orgId_" />
												<html:text property="orgName_" styleClass="select_but"
													onclick="selectOrg()" readonly="true" />
											</td>
											<td width="1%">物料组：</td>
											<td width="1%">
												<html:hidden property="$eq_baseDictItem_dictItemId" />
												<html:text property="itemName" readonly="true"
													styleClass="select_but"
													onclick="selectDict('prodSTRU', mdmDistOrderempProdgroupForm.$eq_baseDictItem_dictItemId, mdmDistOrderempProdgroupForm.itemName)" />
											</td>
											<td width="1%" style="text-align: right;">
												订单员：
											</td>
											<td>
												<html:hidden property="$eq_baseEmployee_empId" />
												<html:text property="empName_" readonly="true"
													styleClass="select_but"
													onclick="selectEmp()" />
											</td>
										</tr>
									</table>
								</div>
								<ec:table items="list" var="item" onInvokeAction="query()"
									form="mdmDistOrderempProdgroupForm" retrieveRowsCallback="limit"
									style="width:100%" tableId="ec"
									action="mdmDistOrderempProdgroup.do?method=list">
									<ec:exportXls fileName="订单员、组织、产品组关系维护.xls" tooltip="导出EXCEL"
										view="xls" />
									<ec:exportCsv fileName="订单员、组织、产品组关系维护.csv" tooltip="导出CSV" view="csv" />
									<ec:row highlightRow="true">
									<ec:column property="null" width="1%" title="操作"
											sortable="false" viewsDenied="xls,csv">
											<center>
												<html:link
													page="/order/mdmDistOrderempProdgroup.do?method=edit&id=${item.id}">
													<img src="${ctx}/images/edit.gif" border="0" alt="编辑" title="编辑" />
												</html:link>
												&nbsp;
												<html:link
													page="/order/mdmDistOrderempProdgroup.do?method=delete&id=${item.id}"
													onclick="return confirm('确认要删除该记录吗')">
													<img src="${ctx}/images/delete.gif" border="0" alt="删除" title="删除" />
												</html:link>
											</center>
										</ec:column>
									 <ec:column property="baseOrg.orgName" title="组织" alias="distName"/>
										<ec:column property="baseDictItem.itemName" title="物料组" alias="itemName"/>
										<ec:column property="baseEmployee.empName" title="订单员" alias="empName"/>
										<ec:column property="effectiveTime" title="生效日期"  cell="date" format="yyyy-MM-dd"/>
										<ec:column property="expiryTime" title="失效日期"  cell="date" format="yyyy-MM-dd"/>
										<ec:column property="memo1" title="附加数据1" />
										<ec:column property="memo2" title="附加数据2" />
										<ec:column property="memo3" title="附加数据3" />
										<ec:column property="createdByname" title="创建人" />
									    <ec:column property="createdDate" title="创建时间" cell="date" format="yyyy-MM-dd"/>
										<ec:column property="updatedByname" title="更新人" />
										<ec:column property="updatedDate" title="更新时间" cell="date" format="yyyy-MM-dd"/>										
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
