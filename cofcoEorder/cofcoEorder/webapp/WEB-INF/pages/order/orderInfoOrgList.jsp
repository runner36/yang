<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>订单列表--区域</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/widgets/extremecomponents/extremecomponents.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/dms.js"></script>
		<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/DateControl.js"></script>
		<script type="text/javascript" src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
		<script language="javaScript" src="${ctx}/scripts/jquery-1.2.6.min.js"></script>
		<script>
	    function query() {
			resetec();
			var form = document.orderInfoOrgForm;
			form.submit();
		}
		//客户
		 function selectDist() {
			var form = document.orderInfoOrgForm;
			var v = openDistTreeForOrder('${ctx}', '', '1', form.inDistId_.value,'DP');
			if (v) {
				form.inDistId_.value = v.id;
				form.distName.value = v.text;
			}
		}
		//地区
	    function selectDict(dictName, id, name) {
	   	 	var form = document.orderInfoOrgForm;
			var v = openDictTree('${ctx}', dictName);
			if (v) {
				if(v.id){
					form.geoId_.value = v.id;
					form.geoName.value = v.text;
					form.geoCode_.value = v.subCode;
				}else{
					form.geoId_.value = '';
					form.geoName.value = '';
					form.geoCode_.value = '';
				}
			}
		}
		//物料组
		function selectMaterials(){
		var form = document.orderInfoOrgForm;
			var v = openDictTree('${ctx}','prodSTRU',2,0,form.brandId_.value, '',1);
			//var v = openOrderMaterialsTree('${ctx}',2,0,'',2);
			if(v){
				form.brandId_.value = v.id;
				form.brandName.value = v.text;
			}
		}
		//组织
		function selectOrg() {
			var form = document.orderInfoOrgForm;
			var v = openOrgTreeForEorder('${ctx}','','','','DP');
			if (v) {
				if(v.id){
					form.orgId_.value = v.id;
					form.orgName.value = v.text;
					form.orgCode_.value = v.subCode;
				}else{
					form.orgId_.value = '';
					form.orgName.value = '';
					form.orgCode_.value = '';
				}
			}
		}
		</script>
	</head>
	<body onload="WindowSollAuto()" onresize="WindowSollAuto()">
		<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/order/orderInfoOrg.do?method=list" method="post">
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						<!--TitleStrat-->
						订单列表
						<!--TitleStrat-->
					</h4>
					<div class='MenuList'>
						<!--MenuStrat-->
						<a href="javascript:showQuery_('query_A');" id="showQuery_A" >设置查询条件</a>
						<a href="javascript:hideQuery_('query_A');" id="showQuery_A" >关闭</a>
						<a href="javascript:orderInfoOrgForm.ec_p.value=1;query()" id="query_A">查询</a>
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
										<td width="1%">订货编号</td>
										<td width="1%">
											<html:text name="ec" property="orderCode_" style="width:110px"/>
										</td>
										<td width="1%">订货客户编码</td>
										<td width="1%">
											<html:text name="ec" property="distCode_"/>
										</td>
										<td width="1%">订货客户名称</td>
										<td >
											<html:hidden name="ec" property="inDistId_"/>
											<html:text name="ec" property="distName" styleClass="select_but" onclick="selectDist()" readonly="true"/>
										</td>
										<td width="1%">
											订货时间
										</td>
										<td>
											<html:text name="ec" property="orderDate_start" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>-<html:text name="ec" property="orderDate_end" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>
										</td>
									</tr>
									<tr>
											<td width="1%">物料组</td>
											<td width="1%">
											<html:hidden property="brandId_"/><html:text property="brandName" readonly="true" styleClass="select_but" onclick="selectMaterials();"/>
											</td>
											<td width="1%">地&nbsp;&nbsp;&nbsp;&nbsp;区</td>
											<td ><html:hidden property="geoCode_"/>
												<html:hidden property="geoId_"/><html:text property="geoName" readonly="true" styleClass="select_but" onclick="selectDict('geography',orderInfoOrgForm.geoId_,orderInfoOrgForm.geoName)"/>
											</td>
											<td width="1%">组&nbsp;&nbsp;&nbsp;&nbsp;织</td>
											<td ><html:hidden property="orgCode_"/>
												<html:hidden property="orgId_"/><html:text property="orgName" readonly="true" styleClass="select_but" onclick="selectOrg();"/>
											</td>
											<td width="1%">送货日期</td>
											<td>
												<html:text name="ec" property="shiptoDate_start" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>-<html:text name="ec" property="shiptoDate_end" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>
											</td>
									</tr>
									<tr>
											<td width="1%">订单状态</td>
											<td width="1%">
												<html:select name="ec" property="status_" style="width: 110px">
													<html:option value=""></html:option>
													<html:option value="20">请订单中心接收</html:option>
													<html:option value="30">已接收</html:option>
													<html:option value="40">订单有误已作废</html:option>
												</html:select>
											</td>
											<td width="1%" colspan="6"></td>
											
									</tr>
								</table>
								</div>

							<ec:table items="list" var="item" onInvokeAction="query()"
									form="orderInfoOrgForm" retrieveRowsCallback="limit"  sortRowsCallback="limit" 
									style="width:100%" tableId="ec"
									action="orderInfoOrg.do?method=list">
									<ec:exportXls fileName="订单列表--区域.xls" tooltip="导出EXCEL" view="xls" />
									<ec:exportCsv fileName="订单列表--区域.csv" tooltip="导出CSV" view="csv" />
									
								<ec:row highlightRow="true">
										<ec:column property="null" width="1%" title="操作" sortable="false" viewsDenied="xls,csv">
											<center>
												<html:link page="/order/orderInfoOrg.do?method=view&orderId=${item[0]}">
													<img src="${ctx}/images/find.gif" border="0" alt="查看" title="查看" />
												</html:link>
											</center>	
											
										</ec:column> 
										<ec:column property="1" title="地区"  />
										<ec:column property="2" title="省区"  />
										<ec:column property="3" title="城市"  />
										<ec:column property="4" title="订货编号"/>
										<ec:column property="13" title="SAP订单号"/>
										<ec:column property="5" title="客户编码"/>
										<ec:column property="6" title="订货客户名称"/>
										<ec:column property="7" title="订货日期"  cell="date" format="yyyy-MM-dd HH:mm"/>
										<ec:column property="8" title="要求送货日期"  cell="date" format="yyyy-MM-dd"/>
										<ec:column property="9" title="订货数量" cell="number" format="0.000" style="text-align:right"/>
										<ec:column property="10" title="订货金额" cell="number" format="0.000" style="text-align:right"/>
										<ec:column property="12" title="订单状态"  sortable="false"/>
										<ec:column property="11" title="订单审批日期"  cell="date" format="yyyy-MM-dd HH:mm"/>
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

