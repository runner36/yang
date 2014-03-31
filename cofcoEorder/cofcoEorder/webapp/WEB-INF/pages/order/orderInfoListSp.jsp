<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<%@ include file="/commons/meta.jsp" %>
		<title>订单信息列表</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/widgets/extremecomponents/extremecomponents.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/dms.js"></script>
		<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>
		<script type="text/javascript" src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/jquery-1.2.6.min.js"></script>
		<script>
	    function query() {			
			resetec();
			var form = document.orderInfoForm;
			form.action="orderInfo.do?method=listSp"
			loading();
			form.submit();
		}
		function add() {
		    location='orderInfo.do?method=addSp';
		}

		function selectDict(dictName, id, name) {
			var v = openDictTree('${ctx}', dictName, 2, 2);
			if (v) {
				id.value = v.id;
				name.value = v.text;
			}
		}
		
		function selectDistri(distriName, id, name) {
			var v = openDistTree('${ctx}','2','0','', distriName);
			if (v) {
				id.value = v.id;
				name.value = v.text;
			}
		}
		//组织
		function selectOrg() {
		  	var v = openGskStoreOrgTree('${ctx}', 2, 2, orderInfoForm.$in_baseOrg_orgId.value);
		  	if (v) {
				orderInfoForm.$in_baseOrg_orgId.value = v.id;
				orderInfoForm.orgName1.value = v.text;
			}
		}

		//将数字保留3位小数的数字
		function formNum(val){
			var temp = val.split(".");
			if(temp.length==2){
				val = temp[0]+"."+(temp[1]+"000").substring(0,3);
			}else{
				val = val+".000";
			}
			return val;
		}
		//发货方树型列表
		function selectShipAddress(addIdObj,shiptoName){
			var v = openDistAddressTreeByDist('${ctx}', 2,0,'');
			//shiptoCode shiptoAddr shiptoLink shiptoTel
			//linkAddrTD linkmanTD linktelTD
			if(v){
			 
				if(v.id!=''){
					 
					addIdObj.value=v.id;
					shiptoName.value=v.shiptoAddr+"("+v.shiptoCode+")";
				}else{
					addIdObj.value="";
					shiptoName.value="";
					
				}
			}
		}
		
		$(function(){ 
			$("#checkAll").click(function(){
			if(this.checked==true){
				$("input:checkbox").checkCbx(1);
				}else{ 
				$("input:checkbox").checkCbx(0);
				}
			}) 
		}) 
		
		$.fn.checkCbx = function(i){ 
				if(i==1){
					return this.each(function(){ 
					this.checked = true; 
				}); 
			}else{
					return this.each(function(){ 
					this.checked = false;
				});
			}
		}
		
		function exportFile(v1,v2){
			var checkboxs=document.getElementsByTagName("input"); 
			var checkboxItems = "";
			var i; 
			var flag =false;
			for(i=0;i<checkboxs.length;i++) 
			{ 
				if(checkboxs[i].type=='checkbox' && checkboxs[i].name!='checkboxAll') 
				{ 
					if(checkboxs[i].checked==true){
					flag = true;
					checkboxItems += checkboxs[i].value+",";
					}
				} 
			} 
			if(!flag){
				alert("请选择需打印的记录！");
			return ;
			}
	    	
			uir="orderMemberOrder.do?method=downOrderInfoTemplet&checkboxItems="+checkboxItems;
			orderInfoForm.action=uir;
			orderInfoForm.submit();
			
	    }
		</script>
	</head>
	<body onLoad="WindowSollAuto()" onResize="WindowSollAuto()"><jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/order/orderInfo.do?method=listSp" method="post">
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						<!--TitleStrat-->
						订单信息列表
						<!--TitleStrat-->
					</h4>
					<div class='MenuList'>
						<!--MenuStrat-->
						<a id="showQuery_A" onClick="javascript:showQuery_('query_A');" >设置查询条件</a>
						<a id="hideQuery_A" onClick="javascript:hideQuery_('query_A');" >关闭</a>
						<a href="javascript:query()" id="query_A">查询</a>
						<a href="javascript:exportFile('query_A','1')"  id="query_A">打印</a>
						<!-- 
							<a href="orderInfo.do?method=addSp">新增</a>
							<a href="javascript:save()">保存</a>
						 -->
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
										<td width="1%" >
											订单编号：
										</td>
										<td width="1%" >
											<html:text name="ec" property="$eq_orderCode"/>
										</td>
										<td width="1%" >
											客户联系人：
										</td>
										<td width="1%">
										   <html:text name="ec" property="$lk_custLinkman"/>
										</td>
										<td width="1%" >
											送货方编码：
										</td>
										<td width="1%" >										 
											<html:hidden name="ec" property="$eq_distributorAddress_id"/>										     
										    <html:text name="ec"   property="shiptoName" readonly="true" styleClass="select_but" onclick="selectShipAddress(orderInfoForm.$eq_distributorAddress_id,orderInfoForm.shiptoName)"/>
										    
										</td>
										<td >
											订货时间：
										</td>
										<td  colspan="2"><html:text name="ec" property="$ge_orderDate" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>-<html:text name="ec" property="$le_orderDate" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/></td>

									</tr>
									<tr>
										<td width="1%">订单状态</td>
											<td width="1%">
												<html:select name="ec" property="$eq_status" style="width: 110px">
													<html:option value=""></html:option>
													<html:option value="10">未提交</html:option>
													<html:option value="20">已提交</html:option>
													<html:option value="30">已接收</html:option>
													<html:option value="40">作废</html:option>
												</html:select>
										</td>
										<td  >
											送货时间：
										</td>
										<td colspan="2"  >
											<html:text name="ec" property="$ge_shiptoDate" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>
											-<html:text name="ec" property="$le_shiptoDate" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>
										</td>
										<td></td><td></td><td></td><td></td>
									</tr>
								</table>
							</div>
								<ec:table items="list" var="item" onInvokeAction="query()"
									form="orderInfoForm" 
									style="width:100%;" tableId="ec"
									action="orderInfo.do?method=listSp">
									<ec:exportXls fileName="客户订单.xls" tooltip="导出EXCEL" view="xls" />
									<ec:exportCsv fileName="客户订单.csv" tooltip="导出CSV" view="csv" />
									<ec:row highlightRow="true">
										<ec:column property="0" width="1%" title="<input type='checkbox' value='' name='checkboxAll'  id='checkAll'/>"
											sortable="false" viewsDenied="xls,csv">
											<center>
												<input type="checkbox" value="${item.orderId}" name="checkboxItem"/>
											</center>
										</ec:column>
										<ec:column property="null" width="1%" title="操作" sortable="false" viewsDenied="xls,csv">
												<html:link page="/order/orderInfo.do?method=view&orderId=${item.orderId}">
													<img src="${ctx}/images/find.gif" border="0" alt="查看" title="查看" />
												</html:link>
												<c:if test="${item.status!='10'}">
												<html:link page="/order/orderInfo.do?method=updateInit&orderId=${item.orderId}">
													<img src="${ctx}/images/findreplay.gif" border="0" alt="复制" title="复制"/>
												</html:link>
												</c:if>
												<c:if test="${item.status=='10'}">
													<html:link page="/order/orderInfo.do?method=updateInit&orderId=${item.orderId}">
														<img src="${ctx}/images/edit.gif" border="0" alt="修改" title="修改" />
													</html:link>
												</c:if>
												
										</ec:column>
										<ec:column property="orderCode" title="订单编号"/>
										<ec:column property="custCode" title="客户编码" alias="distCode"/>
										<ec:column property="custName" title="客户名称" alias="distName"/>
										<ec:column property="custLinkman" title="客户联系人" alias="linkman"/>
										<ec:column property="custTell" title="联系人电话" alias="linktel"/>
										
										<ec:column property="shiptoCode" title="送货方编号" alias="distCode"/>
										<ec:column property="distributorAddress.shiptoAdd" title="送货地址" alias="shiptoAdd"/>
										<ec:column property="shiptoLinkman" title="送货联系人" alias="contact"/>
										<ec:column property="shiptoTell" title="送货电话" alias="tel"/>
										<ec:column property="quantity" title="数量" alias="quantity">
											<fmt:formatNumber value="${item.quantity}" pattern="0.000"/>
										</ec:column>
										<ec:column property="amount" title="金额" alias="amount">
											<fmt:formatNumber value="${item.amount}" pattern="0.000"/>
										</ec:column>
										
										<ec:column property="orderDate" title="订货时间" cell="date" format="yyyy-MM-dd HH:mm:ss"/>
										<ec:column property="shiptoDate" title="送货时间" cell="date" format="yyyy-MM-dd"/>
										
										<ec:column property="status" title="状态">
											<c:choose>
												<c:when test="${item.status=='10'}">未提交</c:when>
												<c:when test="${item.status=='20'}">已提交</c:when>
												<c:when test="${item.status=='30'}">已接收</c:when>
												<c:when test="${item.status=='40'}">作废</c:when>
												<c:otherwise>未知</c:otherwise>
											</c:choose>
										</ec:column>
										<ec:column property="orderApproveDate" title="订单审批时间" cell="date" format="yyyy-MM-dd HH:mm:ss"/>
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
