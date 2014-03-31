<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<%@ include file="/commons/meta.jsp"%>
		<title>客户联系人信息维护</title>
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
			var form = document.mdmDistributorLinkmanForm;
			loading();
			form.submit();
		}
		function add() {
		    location='mdmDistributorLinkman.do?method=add';
		}
	
		//经销商
   function selectDist() {
			var form = document.mdmDistributorLinkmanForm;
			var v = openDistTree('${ctx}', '', '1', mdmDistributorLinkmanForm.$eq_mdmDistributor_distId.value);
			if (v) {
				form.$eq_mdmDistributor_distId.value = v.id;
				form.distName_.value = v.text;
			}
		}

	
	//物料组
	function selectDict(dictName, id, name) {
		var v = openDictTree('${ctx}', dictName, 2, 2, id.value,'',1);
		if (v) {
			id.value = v.id;
			name.value = v.text;
		}
	}
	
	
	//产品
	function selectProd(id, name) {
		var v = openProdTree('${ctx}',form.prodBrandId.value, '2','2');
		if (v) {
			id.value = v.id;
			name.value = v.text;
		}
	}

		</script>
	</head>
	<body onLoad="WindowSollAuto()" onResize="WindowSollAuto()"><jsp:include
			page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/order/mdmDistributorLinkman.do?method=list"
			method="post">
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						<!--TitleStrat-->
						客户联系人信息维护
						<!--TitleStrat-->
					</h4>
					<div class='MenuList'>
						<!--MenuStrat-->
						<a href="javascript:showQuery_('query_A');" id="showQuery_A" >${mr['page.common.button.setquery']}</a>
						<a href="javascript:hideQuery_('query_A');" id="hideQuery_A" >${mr['page.common.button.close']}</a>
						<a href="javascript:mdmDistributorLinkmanForm.ec_p.value=1;query()" id="query_A">查询</a>
						<a href="${ctx}/order/mdmDistributorLinkman.do?method=add">新增</a>
					<!--	<a href="${ctx}/order/mdmDistributorLinkman.do?method=importCon">导入</a>-->
						<a href="javascript:openImportForm('${ctx}', 'MdmDistributorLinkman');">${mr['page.common.button.import']}</a>
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
											<td width="1%">
												客户编码：
											</td>
											<td width="1%">
												<html:text name="ec" property="$eq_mdmDistributor_distCode" />
											</td>
											<td width="1%" style="text-align: right">
												客户名称：
											</td>
											<td width="1%">
												<html:hidden property="$eq_mdmDistributor_distId" />
												<html:text property="distName_" styleClass="select_but"
													onclick="selectDist()" readonly="true" />
											</td>
											<td width="1%" style="text-align: right;">
												物料组：
											</td>
											<td>
												<html:hidden property="$eq_baseDictItem_dictItemId" />
												<html:text property="itemName" readonly="true"
													styleClass="select_but"
													onclick="selectDict('prodSTRU', mdmDistributorLinkmanForm.$eq_baseDictItem_dictItemId, mdmDistributorLinkmanForm.itemName)" />
											</td>

										</tr>
									</table>
								</div>
								<ec:table items="list" var="item" onInvokeAction="query()"
									form="mdmDistributorLinkmanForm" retrieveRowsCallback="limit"
									style="width:100%" tableId="ec"
									action="mdmDistributorLinkman.do?method=list">
									<ec:exportXls fileName="客户联系人信息维护.xls" tooltip="导出EXCEL"
										view="xls" />
									<ec:exportCsv fileName="客户联系人信息维护.csv" tooltip="导出CSV" view="csv" />
									<ec:row highlightRow="true">
									<ec:column property="null" width="1%" title="操作"
											sortable="false" viewsDenied="xls,csv">
											<center>
												<html:link
													page="/order/mdmDistributorLinkman.do?method=edit&id=${item.id}">
													<img src="${ctx}/images/edit.gif" border="0" alt="编辑" title="编辑" />
												</html:link>
												&nbsp;
												<html:link
													page="/order/mdmDistributorLinkman.do?method=delete&id=${item.id}"
													onclick="return confirm('确认要删除该记录吗')">
													<img src="${ctx}/images/delete.gif" border="0" alt="删除" title="删除" />
												</html:link>
											</center>
										</ec:column>
										<ec:column property="mdmDistributor.distCode" styleClass="jxsId" title="客户编码" alias="distCode" />
										<ec:column property="mdmDistributor.distName" title="客户名称" alias="distName"/>
										<ec:column property="baseDictItem.itemName" title="物料组" alias="itemName"/>
										<ec:column property="linkmanName" title="联系人名称" />
										<ec:column property="linkmanTel" title="联系电话" />
										<ec:column property="linkmanPhonenum" title="手机号码" />
										<ec:column property="isSms" value="${item.isSms==1 ? '是':'否'}" title="是否发送短信" />
										<ec:column property="createdByname" title="创建人" />
										<ec:column property="createdDate" title="创建时间"  cell="date" format="yyyy-MM-dd"/>
										<ec:column property="updatedByname" title="更新人" />										
										<ec:column property="updatedDate" title="更新时间"  cell="date" format="yyyy-MM-dd"/>	
										<ec:column property="status" value="${item.status==1 ? '有效':'停用'}" title="状态" /> 									
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
