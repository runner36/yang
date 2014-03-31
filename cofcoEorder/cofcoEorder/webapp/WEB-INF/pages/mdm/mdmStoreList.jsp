<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<%@ include file="/commons/meta.jsp" %>
		<title>${mr['page.customize.title.custMasterDataMaint']}</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/widgets/extremecomponents/extremecomponents.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/dms.js"></script>
		<script type="text/javascript" src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/jquery-1.2.6.min.js"></script>
		<script>
	    function query() {
			resetec();
			var form = document.mdmStoreForm;
			loading();
			form.submit();
		}
		function add() {
		    location='mdmStore.do?method=add';
		}
		function save() {
			var flag = true;
			var form = document.mdmStoreForm;
			form.action = 'mdmStore.do?method=saveCheckOrg';
			var today = getNowFormatDate();
			$('.startDate').each(function(i,n){
				var value = $(n).val();
				var t = new Date(today.replace("-",",")).getTime() ;
				var s = new Date(value.replace("-",",")).getTime() ;
				if(t >= s && flag) {  
					flag = false;
					var name = $('.jxsId',$(n).parent().parent()).text();
					alert("${mr['page.common.store']}:"+name+" ${mr['page.common.orgEffectDate']}！");
				}
			});
			if (flag) {
				form.submit();
			}
		}

		function doit(value,storeId) {
			if (value == '') {
				document.mdmStoreForm("store_"+storeId).value = '';
			} else {
				document.mdmStoreForm("store_"+storeId).value=storeId+'_'+value;
			}
		}
		function selectDict(dictName, id, name) {
			var v = openDictTree('${ctx}', dictName);
			if (v) {
				id.value = v.id;
				name.value = v.text;
			}
		}

		</script>
	</head>
	<body onLoad="WindowSollAuto()" onResize="WindowSollAuto()"><jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/mdm/mdmStore.do?method=list" method="post">
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						<!--TitleStrat-->
						${mr['page.customize.title.custMasterDataMaint']}
						<!--TitleStrat-->
					</h4>
					<div class='MenuList'>
						<!--MenuStrat-->
						<a href="javascript:showQuery_('query_A');" id="showQuery_A"  >${mr['page.common.button.setquery']}</a>
						<a href="javascript:hideQuery_('query_A');" id="showQuery_A"  >${mr['page.common.button.close']}</a>
						<a href="javascript:query()" id="query_A">${mr['page.common.button.query']}</a>
						<a href="mdmStore.do?method=add">${mr['page.common.button.add']}</a>
						<a href="javascript:save()">${mr['page.common.button.save']}</a>
						<a href="javascript:openImportForm('${ctx}', 'MdmStore');">${mr['page.common.button.import']}</a>
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
										<td width="1%">
											${mr['page.common.storeCode']}
										</td>
										<td width="1%">
											<html:text name="ec" property="$lk_storeCode"/>
										</td>
										
										<td width="1%">
											${mr['page.common.storeName']}
										</td>
										<td width="1%">
											<html:text name="ec" property="$lk_storeName"/>
										</td>
										<td width="1%">
											${mr['page.common.parentStoreName']}
										</td>
										<td  width="1%">
											<html:text name="ec" property="$lk_mdmStore_storeName"/>
										</td>
										<td width="1%">${mr['page.common.createdTime']}</td>
											<td>
												<html:text name="ec" property="$ge_created" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>
												-<html:text name="ec" property="$le_created" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>
											</td>
										</tr>
										<tr>
										<td width="1%">
											${mr['page.common.geographicArea']}
										</td>
										<td >
											<html:hidden name="ec" property="$eq_storeGeo_dictItemId"/>
											<html:text name="ec" property="geo_Name" readonly="true" styleClass="select_but" onclick="selectDict('geography',mdmStoreForm.$eq_storeGeo_dictItemId,mdmStoreForm.geo_Name)"/>
										</td>
										<td width="1%">
											${mr['page.common.storeType']}
										</td>
										<td width="1%">
											<html:hidden name="ec" property="$eq_storeType_dictItemId"/>
											<html:text name="ec"  property="type_Name" readonly="true" styleClass="select_but" onclick="selectDict('storeType',mdmStoreForm.$eq_storeType_dictItemId,mdmStoreForm.type_Name)"/>
										</td>
										<td width="1%">
											${mr['page.common.storeProperty']}
										</td>
										<td width="1%">
											<html:hidden name="ec" property="$eq_storeNature_dictItemId"/>
											<html:text name="ec" property="nature_Name" readonly="true" styleClass="select_but" onclick="selectDict('storeNature',mdmStoreForm.$eq_storeNature_dictItemId,mdmStoreForm.nature_Name)"/>
										</td>
										<td width="1%">
											${mr['page.common.updatedTime']}
										</td>
										<td >
											<html:text name="ec" property="$ge_updated" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>-<html:text name="ec" property="$le_updated" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>
										</td>
										</tr>
										
								</table>
							</div>
								<ec:table items="list" var="item" onInvokeAction="query()"
									form="mdmStoreForm" retrieveRowsCallback="limit"
									style="width:100%" tableId="ec"
									action="mdmStore.do?method=list">
									<ec:exportXls fileName="${mr['page.common.custMastdata']}.xls" tooltip="导出EXCEL，大于6万请导出CSV格式" view="xls" />
									<ec:exportCsv fileName="${mr['page.common.custMastdata']}.csv" tooltip="${mr['page.common.exportCVS']}" view="csv" />
									<ec:row highlightRow="true">
										<ec:column property="storeName" styleClass="jxsId" title="${mr['page.common.storeName']}"/>
										<ec:column property="storeAlias" title="${mr['page.common.storeAlias']}"/>
										<ec:column property="storeCode" title="${mr['page.common.storeCode']}"/>
										<ec:column property="extCode" title="${mr['page.common.externalCode']}"/>
										<ec:column property="subCode" title="${mr['page.common.segmengCode']}"/>
										<ec:column property="mdmStore.storeCode" title="${mr['page.common.parentStoreCode']}" alias="parentStoreCode"/>
										<ec:column property="mdmStore.storeName" title="${mr['page.common.parentStoreName']}" alias="parentStoreName"/>
										<ec:column property="baseOrg.pnall" title="${mr['page.common.orgName']}" alias="orgName"/>
										<ec:column property="baseOrg.baseEmployee.empName" title="${mr['page.common.personInCharge']}" alias="empName"/>
										
										<ec:column property="null" title="${mr['page.common.proposedOrg']}" sortable="false" style="width:20%" viewsDenied="xls,csv"  alias="storeOrgCheck">
											<c:if test="${fn:length(item.storeOrgCheck.sugOrgList)==1}">
												${item.storeOrgCheck.sugOrgName}
												<input type="hidden" name="store_${item.storeId}" value="${item.storeId}_${item.storeOrgCheck.sugOrgId}" >
											</c:if> 
											<c:if test="${fn:length(item.storeOrgCheck.sugOrgList)>1}">
												<bean:define id="sugOrgList" name="item" property="storeOrgCheck.sugOrgList" />
												<html:select name="ec" property="sOrgId" style="width:215px" onchange="doit(this.value,${item.storeId});">
													<html:option value=""></html:option>
													<html:options collection="sugOrgList" labelProperty="sugOrgName" property="sugOrgId"/>
												</html:select>
												<input type="hidden" name="store_${item.storeId}" >
											</c:if> 
										</ec:column>
										<ec:column property="null" title="${mr['page.common.effectDate']}" sortable="false" alias="sxdate" viewsDenied="xls,csv" >
											<c:if test="${fn:length(item.storeOrgCheck.sugOrgList)>0}">
												<html:text property="startDate_${item.storeId}" size="12" styleClass="date_but startDate" onfocus="WdatePicker()" readonly="true"/>
											</c:if> 
										</ec:column>
										<ec:column property="storeOrgCheck.updateDate" title="${mr['page.common.updatedDate']}" alias="updateDate"/>
										<ec:column property="storeOrgCheck.lastLogOrgName" title="${mr['page.common.afterChangeOrg']}" alias="lastLogOrgName"/>
										<ec:column property="storeOrgCheck.startDate" title="${mr['page.common.effectTime']}" alias="startDate"/>
										<ec:column property="storeGeo.itemName" title="${mr['page.common.geographicArea']}" alias="geoName"/>
										<ec:column property="storeType.itemName" title="${mr['page.common.storeType']}" alias="typeName"/>
										<ec:column property="storeNature.itemName" title="${mr['page.common.storeProperty']}" alias="natureName"/>
										<ec:column property="storeCorp.itemName" title="${mr['page.common.storeOrg']}" alias="corpName"/>
										<ec:column property="storeOther.itemName" title="${mr['page.common.otherCategories']}" alias="otherName"/>
										<ec:column property="storeChannel.itemName" title="${mr['page.common.storeChannel']}" alias="channelName"/>
										<ec:column property="mdmDistributor.distName" title="${mr['page.common.distName']}" alias="distName"/>
										<ec:column property="storeAddr" title="${mr['page.common.storeAddress']}"/>
										<ec:column property="linkman" title="${mr['page.common.contact']}"/>
										<ec:column property="linktel" title="${mr['page.common.telephone']}"/>
										<ec:column property="storeLevel" title="${mr['page.common.custLevel']}"/>
										<ec:column property="firstItemDate" title="${mr['page.common.firstOrderTime']}" cell="date" format="yyyy-MM-dd "/>
										<ec:column property="lastItemDate" title="${mr['page.common.lastOrderTime']}" cell="date" format="yyyy-MM-dd "/>
										
										<ec:column property="baseOrg.orgCode" title="${mr['page.common.orgCode']}" alias="orgCode"/>
										<ec:column property="storeGeo.itemCode" title="${mr['page.common.geographicAreaCode']}" alias="geoCode"/>
										
										
										<ec:column property="createdBy" title="${mr['page.common.createdBy']}"/>
										<ec:column property="created" title="${mr['page.common.createdTime']}" cell="date" format="yyyy-MM-dd "/>
										<ec:column property="updatedBy" title="${mr['page.common.updatedBy']}"/>
										<ec:column property="updated" title="${mr['page.common.updatedTime']}" cell="date" format="yyyy-MM-dd "/>
										
										<ec:column property="null"  width="1%" title="${mr['page.common.button.oper']}" sortable="false" viewsDenied="xls,csv">
											<center>
												<html:link page="/mdm/mdmStore.do?method=edit&storeId=${item.storeId}">
													<img src="${ctx}/images/edit.gif" border="0" alt="${mr['page.common.button.edit']}" title="${mr['page.common.button.edit']}" />
												</html:link>
												&nbsp;
												<html:link page="/mdm/mdmStore.do?method=delete&storeId=${item.storeId}" onclick="return confirm('${mr['page.common.mess.delete']}')">
													<img src="${ctx}/images/delete.gif" border="0" alt="${mr['page.common.button.delete']}" title="${mr['page.common.button.delete']}" />
												</html:link>
											</center>
										</ec:column>
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
