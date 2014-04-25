<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/meta.jsp"%>
<title>客户信息维护</title>
<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/widgets/extremecomponents/extremecomponents.css">
<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
<script language="javaScript" src="${ctx}/scripts/base.js"></script>
<script language="javaScript" src="${ctx}/scripts/dms.js"></script>
<script type="text/javascript"
	src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery-1.2.6.min.js"></script>
<script>
	    function query() {
			resetec();
			var form = document.mdmDistributorForm;
			loading();
			form.submit();
		}
		function add() {
		    location='mdmDistributor.do?method=add';
		}
		function save() {
			var flag = true;
			var form = document.mdmDistributorForm;
			form.action = 'mdmDistributor.do?method=saveCheckOrg';
			var today = getNowFormatDate();
			$('.startDate').each(function(i,n){
				var value = $(n).val();
				var t = new Date(today.replace("-",",")).getTime() ;
				var s = new Date(value.replace("-",",")).getTime() ;
				if(t >= s && flag) {  
					flag = false;
					var name = $('.jxsId',$(n).parent().parent()).text();
					alert("${mr['page.common.distributor']}:"+name+" ${mr['page.common.orgEffectDate']}！");
				}
			});
			if (flag) {
				form.submit();
			}
		}
		function doit(value,distId) {
			if (value == '') {
				document.mdmDistributorForm("dist_"+distId).value = '';
			} else {
				document.mdmDistributorForm("dist_"+distId).value=distId+'_'+value;
			}
		}

		</script>
</head>
<body onLoad="WindowSollAuto()" onResize="WindowSollAuto()"><jsp:include
		page="/commons/messages.jsp" flush="true"></jsp:include>
	<html:form action="/mdm/mdmDistributor.do?method=list" method="post">
		<div class="bosom_one">
			<div class="bosom_top">
				<span class="left"></span>
				<h4>
					<!--TitleStrat-->
					客户信息维护
					<!--TitleStrat-->
				</h4>
				<div class='MenuList'>
					<!--MenuStrat-->
					<a href="javascript:showQuery_('query_A');" id="showQuery_A">${mr['page.common.button.setquery']}</a>
					<a href="javascript:hideQuery_('query_A');" id="showQuery_A">${mr['page.common.button.close']}</a>
					<a href="javascript:query()" id="query_A">${mr['page.common.button.query']}</a>
					<a href="mdmDistributor.do?method=add">${mr['page.common.button.add']}</a>
					<a href="javascript:openImportForm('${ctx}', 'MdmDistributor');">${mr['page.common.button.import']}</a>
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
										<td width="1%">客户编码</td>
										<td width="1%"><html:text name="ec"
												property="$lk_distCode" /></td>

										<td width="1%">客户名称</td>
										<td width="1%"><html:text name="ec"
												property="$lk_distName" /></td>
										<td width="1%">客户电话</td>
										<td width="1%"><html:text name="ec"
												property="$lk_distTel" /></td>
										<td width="1%">邮政邮编</td>
										<td width="1%"><html:text name="ec"
												property="$lk_distPost" /></td>
										<td width="1%">联系人</td>
										<td><html:text name="ec" property="$lk_linkmanName" /></td>
									</tr>
								</table>
							</div>
							<ec:table items="list" var="item" onInvokeAction="query()"
								form="mdmDistributorForm" retrieveRowsCallback="limit"
								style="width:100%" tableId="ec"
								action="mdmDistributor.do?method=list">
								<ec:exportXls fileName="${mr['page.common.distMastData']}.xls"
									tooltip="${mr['page.common.exportExcel']}" view="xls" />
								<ec:exportCsv fileName="${mr['page.common.distMastData']}.csv"
									tooltip="${mr['page.common.exportCVS']}" view="csv" />
								<ec:row highlightRow="true">
									<ec:column property="null" width="1%"
										title="${mr['page.common.button.oper']}" sortable="false"
										viewsDenied="xls,csv">
										<center>
											<html:link
												page="/mdm/mdmDistributor.do?method=edit&distId=${item.distId}">
												<img src="${ctx}/images/edit.gif" border="0"
													alt="${mr['page.common.button.edit']}" title="${mr['page.common.button.edit']}" />
											</html:link>
											&nbsp;
											<html:link
												page="/mdm/mdmDistributor.do?method=delete&distId=${item.distId}"
												onclick="return confirm('${mr['page.common.mess.delete']}')">
												<img src="${ctx}/images/delete.gif" border="0"
													alt="${mr['page.common.button.delete']}" title="${mr['page.common.button.delete']}" />
											</html:link>
										</center>
									</ec:column>
									<ec:column property="distCode"
										title="客户编码" />
									<ec:column property="distName" styleClass="jxsId"
										title="客户名称" />
										<ec:column property="linkmanName"
										title="联系人" />
									<ec:column property="linkmanTel"
										title="手  机" />
									<ec:column property="distTel"
										title="客户电话" />
									<ec:column property="distAddr"
										title="客户地址" />
									<ec:column property="distPost"
										title="邮政编码" />
									<ec:column property="mgrTel"
										title="客户传真 "/>									
									<ec:column property="ownergrpCode" styleClass="jxsId"
										title="客户收货限制" />
									<ec:column property="ownergrpName" styleClass="jxsId"
										title="客户类型" />																	
									<ec:column property="baseOrg.orgCode"
										title="${mr['page.common.orgCode']}" alias="orgCode" />
									<ec:column property="baseOrg.pnall"
										title="${mr['page.common.orgName']}" alias="orgName" />
									<ec:column property="baseDictItem.itemCode"
										title="${mr['page.common.geographicAreaCode']}"
										alias="geoCode" />
									<ec:column property="baseDictItem.itemName"
									title="${mr['page.common.geographicArea']}" alias="geoName" />
									
										
										
									<%-- <ec:column property="baseOrg.baseEmployee.empName"
										title="${mr['page.common.personInCharge']}" alias="empName" />
									<ec:column property="null"
										title="${mr['page.common.proposedOrg']}" sortable="false"
										alias="distOrgCheck" viewsDenied="xls,csv" style="width:20%">
										<c:if test="${fn:length(item.distOrgCheck.sugOrgList)==1}">
												${item.distOrgCheck.sugOrgName}
												<input type="hidden" name="dist_${item.distId}"
												value="${item.distId}_${item.distOrgCheck.sugOrgId}">
										</c:if>
										<c:if test="${fn:length(item.distOrgCheck.sugOrgList)>1}">
											<bean:define id="sugOrgList" name="item"
												property="distOrgCheck.sugOrgList" />
											<html:select name="ec" property="sOrgId" style="width:215px"
												onchange="doit(this.value,${item.distId});">
												<html:option value=""></html:option>
												<html:options collection="sugOrgList"
													labelProperty="sugOrgName" property="sugOrgId" />
											</html:select>
											<input type="hidden" name="dist_${item.distId}">
										</c:if>
									</ec:column>
									<ec:column property="null"
										title="${mr['page.common.effectDate']}" sortable="false"
										viewsDenied="xls,csv" alias="sxdate">
										<c:if test="${fn:length(item.distOrgCheck.sugOrgList)>0}">
											<html:text property="startDate_${item.distId}" size="12"
												styleClass="date_but startDate" onfocus="WdatePicker()"
												readonly="true" />
										</c:if>
									</ec:column>
									<ec:column property="distOrgCheck.updateDate" sortable="false"
										title="建议组织更新时间" alias="updateDate" />
									<ec:column property="distOrgCheck.lastLogOrgName"
										sortable="false" title="${mr['page.common.afterChangeOrg']}"
										alias="lastLogOrgName" />
									<ec:column property="distOrgCheck.startDate" sortable="false"
										title="${mr['page.common.effectTime']}" alias="startDate" />
									
									<ec:column property="mgrName"
										title="${mr['page.common.personInchargeName']}" />
									<ec:column property="subCode"
										title="${mr['page.common.segmengCode']}" />
									
									<ec:column property="mdmDistributor.distName"
										title="${mr['page.common.parentDist']}" alias="parentName" />
									<ec:column property="instDate"
										title="${mr['page.common.installDate']}" cell="date"
										format="yyyy-MM-dd " />
									<ec:column property="checkDate"
										title="${mr['page.common.checkDate']}" cell="date"
										format="yyyy-MM-dd " />
									<ec:column property="mappingDate"
										title="${mr['page.common.mappingDate']}" cell="date"
										format="yyyy-MM-dd " />
									<ec:column property="passBackDate"
										title="${mr['page.common.returnDate']}" cell="date"
										format="yyyy-MM-dd " />
									<ec:column property="endPassDate"
										title="${mr['page.common.lastreturnDate']}" cell="date"
										format="yyyy-MM-dd " />
									<ec:column property="passDataDate"
										title="${mr['page.common.dataStartDate']}" cell="date"
										format="yyyy-MM-dd " /> --%>
									<ec:column property="state" value="${item.state==1 ? '有效':'停用'}" title="${mr['page.common.status']}" />
									<ec:column property="createdBy"
										title="${mr['page.common.createdBy']}" />
									<ec:column property="created"
										title="${mr['page.common.createdTime']}" cell="date"
										format="yyyy-MM-dd " />
									<ec:column property="updatedBy"
										title="${mr['page.common.updatedBy']}" />
									<ec:column property="updated"
										title="更新时间" cell="date"
										format="yyyy-MM-dd " />
									
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
