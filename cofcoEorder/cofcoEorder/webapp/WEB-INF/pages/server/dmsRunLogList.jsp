<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>${mr['page.customize.title.runlog']}</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/widgets/extremecomponents/extremecomponents.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>
		<script language="javaScript" src="${ctx}/scripts/dms.js"></script>
		<script type="text/javascript" src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
		<script>
	    function query() {
			resetec();
			var form = document.dmsRunLogForm;
			form.submit();
		}
		function add() {
		    location='dmsRunLog.do?method=add';
		}
		function selectDist() {
			var form = document.dmsRunLogForm;
			var v = openDistTree('${ctx}', '', '1', form.$in_dmsClient_mdmDistributor_distId.value);
			if (v) {
				form.$in_dmsClient_mdmDistributor_distId.value = v.id;
				form.distName_.value = v.text;
			}
		}
		function selectOrg() {
			var form = document.dmsRunLogForm;
			form.$lk_dmsClient_mdmDistributor_baseOrg_subCode.value = '';
			form.orgName_.value = '';
			var v = openOrgTree('${ctx}');
			if (v) {
				form.$lk_dmsClient_mdmDistributor_baseOrg_subCode.value = v.subCode;
				form.orgName_.value = v.text;
			}
		}
		function viewMessage(logId) {
			window.showModalDialog("dmsRunLog.do?method=viewMessage&logId=" + logId + "&rand=" + Math.random(), "", "dialogHeight:400px;dialogWidth:600px;scroll=yes;help:no;status:no");
		}
		function viewNoUpload() {
			window.showModalDialog("dmsRunLog.do?method=viewNoUpload&rand=" + Math.random(), "", "dialogHeight:400px;dialogWidth:600px;scroll=yes;help:no;status:no");
		}
		function viewNoUpload3() {
			window.showModalDialog("dmsRunLog.do?method=viewNoUpload3&rand=" + Math.random(), "", "dialogHeight:400px;dialogWidth:600px;scroll=yes;help:no;status:no");
		}
		function viewPretreatMessage(logId) {
			window.showModalDialog("dmsRunLog.do?method=viewPretreatMessage&logId=" + logId + "&rand=" + Math.random(), "", "dialogHeight:400px;dialogWidth:600px;scroll=yes;help:no;status:no");
		}
		function viewUploadFailMessage(logId) {
			window.showModalDialog("dmsRunLog.do?method=viewUploadFailMessage&logId=" + logId + "&rand=" + Math.random(), "", "dialogHeight:400px;dialogWidth:600px;scroll=yes;help:no;status:no");
		}
		
		</script>
		<style>
			#fudongtag ul{
				list-style:none;
				padding:0px;
				margin: 0px;
			}
			#fudongtag ul li{
			line-height:20px;
			text-indent:0px;
			margin:0px;
			padding:0px;
			margin-top:2px;
			}
			.closediv{
			float:right;
			width:18px;
			height:15px;
			cursor:pointer;
			}
			
			#fudongtag{
			width:150px;
			hight:120px;
			position:absolute;
			background:#f7f6f5 url(../images/fudongdiv.jpg) repeat-x;
			display:block;
			border:1px solid #FF9900;
			bottom:1px;
			right:5px; 
			padding: 20px 3px 3px 3px;
			white-space:nowrap;
			/*filter:alpha(opacity=80, finishOpacity=160, style=1, startX=30); */  
			}
		</style>
	</head>
	<body onload="WindowSollAuto();">
		<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/server/dmsRunLog.do?method=list" method="post">
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						<!--TitleStrat-->
						${mr['page.customize.title.runlog']}
						<!--TitleStrat-->
					</h4>
					<div class='MenuList'>
						<!--MenuStrat-->
						<a href="javascript:showQuery_('query_A');" id="showQuery_A" >${mr['page.common.button.setquery']}</a>
						<a href="javascript:hideQuery_('query_A');" id="hideQuery_A" >${mr['page.common.button.close']}</a>
						<a href="javascript:dmsRunLogForm.ec_p.value=1;query()" id="query_A">${mr['page.common.button.query']}</a>
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
										<td width="1%" style="text-align:right">${mr['page.common.orgName']}</td>
										<td width="1%">
											<html:hidden property="$lk_dmsClient_mdmDistributor_baseOrg_subCode"/>
											<html:text property="orgName_" styleClass="select_but" onclick="selectOrg()" readonly="true"/>
										</td>
										<td width="1%" style="text-align:right">${mr['page.common.distName']}</td>
										<td width="1%">
											<html:hidden property="$in_dmsClient_mdmDistributor_distId"/>
											<html:text property="distName_" styleClass="select_but" onclick="selectDist()" readonly="true"/>
										</td>
										<td width="1%" style="text-align:right">${mr['page.common.distCode']}</td>
										<td width="1%">
											<html:text property="$eq_distCode" />
										</td>
										<td width="1%" style="text-align:right">${mr['page.common.clientCode']}</td>
										<td width="1%">
											<html:text property="$lk_clientCode" />
										</td>
										<td width="1%" style="text-align:right"></td>
										<td></td>
									</tr>
									<tr>
										<td width="1%" style="text-align:right">${mr['page.common.softwareName']}</td>
										<td width="1%">
											<html:text property="$lk_softName" />
										</td>
										<td width="1%" style="text-align:right">${mr['page.common.uploadResult']}</td>
										<td width="1%">
											<html:select property="$eq_uploadResult" style="width:104px">
												<html:option value=""></html:option>
												<html:option value="成功">${mr['page.common.success']}</html:option>
												<html:option value="失败">${mr['page.common.fail']}</html:option>
											</html:select>
										</td>
										<td width="1%" style="text-align:right">${mr['page.common.loadedResults']}</td>
										<td width="1%">
											<html:select property="$eq_loadResult" style="width: 104px">
												<html:option value=""></html:option>
												<html:option value="成功">${mr['page.common.success']}</html:option>
												<html:option value="失败">${mr['page.common.fail']}</html:option>
											</html:select>
										</td>
										<td width="1%" style="text-align:right">
											${mr['page.common.uploadedDate']}
										</td>
										<td colspan="3">
											<html:text name="ec" property="$ge_uploadTime" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>-<html:text name="ec" property="$le_uploadTime" onfocus="WdatePicker()" readonly="true" styleClass="date_but"/>
										</td>
									</tr>
								</table>
								<div style="margin:10px">
									<fieldset>
										<legend><strong>${mr['page.common.todayClientUploadInfo']}</strong></legend>
										<div style="float:left">
											<div style="padding-left:10px;padding-righg:10px">${mr['page.common.clientTotal']}：<font color="red">${clientTotal}</font></div>
											<div style="padding-left:10px;padding-righg:10px">${mr['page.common.todayClientTotal']}：<font color="red">${result[0]}</font></div>
										</div>
										<div style="float:left">
											<div style="padding-left:10px;padding-righg:10px">${mr['page.common.TodayClientUpSuccess']}：<font color="red">${result[1]}</font></div>
											<div style="padding-left:10px;padding-righg:10px">${mr['page.common.TodayClientUpFail']}：<font color="red">${result[0]-result[1]}</font></div>
										</div>
										<div style="float:left">
											<div style="padding-left:10px;padding-righg:10px"><a style="color: red" href="javascript:viewNoUpload()">${mr['page.common.hasNotBeenUploaded']}：</a><font color="red">${notUploadNum}</font></div>
											<div style="padding-left:10px;padding-righg:10px"><a style="color: red" href="javascript:viewNoUpload3()">${mr['page.common.threeDaysNoUpload']}：</a><font color="red">${notUpload3Num}</font></div>
										</div>
									</fieldset>
								</div>
								</div>
								
								<ec:table items="list" var="item" onInvokeAction="query()"
										form="dmsRunLogForm" retrieveRowsCallback="limit"
										style="width:100%" tableId="ec"
										action="dmsRunLog.do?method=list" bufferView="true">
									<ec:exportXls fileName="${mr['page.customize.title.runlog']}.xls" tooltip="${mr['page.common.exportExcel']}" view="xls" />
									<ec:row highlightRow="true">
										<ec:column property="logId" title="ID"/>
										<ec:column property="dmsClient.mdmDistributor.baseOrg.pnall" title="${mr['page.common.orgName']}" alias="orgName"/>
										<ec:column property="distName" title="${mr['page.common.distName']}"/>
										<ec:column property="distCode" title="${mr['page.common.distCode']}"/>
										<ec:column property="clientCode" title="${mr['page.common.clientCode']}"/>
										<ec:column property="softName" title="${mr['page.common.softwareName']}"/>
										<ec:column property="softVersion" title="${mr['page.common.softwareVersion']}"/>
										<ec:column property="softCode" title="${mr['page.common.softwareCoding']}"/>
										<ec:column property="expectedTime" title="${mr['page.common.expectedUploadTime']}"/>
										<ec:column property="actualTime" title="${mr['page.common.uploadStartTime']}" cell="date" format="yyyy-MM-dd HH:mm"/>
										<ec:column property="uploadTime" title="${mr['page.common.uploadEndTime']}" cell="date" format="yyyy-MM-dd HH:mm"/>
										<ec:column property="loadStart" title="${mr['page.common.loadStartTime']}" cell="date" format="yyyy-MM-dd HH:mm"/>
										<ec:column property="loadEnd" title="${mr['page.common.endoftheloadtime']}" cell="date" format="yyyy-MM-dd HH:mm"/>
										<ec:column property="uploadIp" title="${mr['page.common.uploadIP']}"/>
										<ec:column property="uploadResult" title="${mr['page.common.uploadResult']}"><c:if test="${item.uploadResult=='失败'}"><a href="javascript:viewUploadFailMessage(${item.logId})" style="color:red;font-size:12px">${item.uploadResult}</a></c:if><c:if test="${item.uploadResult!='失败'}">${item.uploadResult}</c:if></ec:column>
										<ec:column property="pretreatResult" title="${mr['page.common.pretResult']}"><c:if test="${item.pretreatResult=='失败'}"><a href="javascript:viewPretreatMessage(${item.logId})" style="color:red;font-size:12px">${item.pretreatResult}</a></c:if><c:if test="${item.pretreatResult!='失败'}">${item.pretreatResult}</c:if></ec:column>
										<ec:column property="loadResult" title="${mr['page.common.loadedResults']}"><c:if test="${item.loadResult=='失败'}"><a href="javascript:viewMessage(${item.logId})" style="color:red;font-size:12px">${item.loadResult}</a></c:if><c:if test="${item.loadResult!='失败'}">${item.loadResult}</c:if></ec:column>
										<ec:column property="jobId" title="${mr['page.common.taskID']}"/>
										<ec:column property="saleRow" title="${mr['page.common.rowsOfSaleUp']}"/>
										<ec:column property="saleErrRow" title="${mr['page.common.rowsOfSaleErroUp']}"/>
										<ec:column property="saleErrRate" sortable="false" title="${mr['page.common.salesErrRate']}" />
										<ec:column property="saleUpdRow" title="${mr['page.common.rowsOfSaleUpdateUp']}"/>
										<ec:column property="saleDelRow" title="${mr['page.common.saleDelRow']}"/>
										<ec:column property="stockRow" title="${mr['page.common.stockRow']}"/>
										<ec:column property="stockErrRow" title="${mr['page.common.stockErrRow']}"/>
										<ec:column property="stockErrRate" sortable="false" title="${mr['page.common.stockErrRate']}"/>
										<ec:column property="clientLoadStart" title="${mr['page.common.clientExtrStartTime']}" cell="date" format="yyyy-MM-dd HH:mm:ss"/>
										<ec:column property="clientLoadEnd" title="${mr['page.common.clientExtrEndTime']}" cell="date" format="yyyy-MM-dd HH:mm:ss"/>
										<ec:column property="_1" title="${mr['page.common.extractionCycle']}" sortable="false">
											${item.dmsClient.dataDays }
										</ec:column>
										<ec:column property="router" title="${mr['page.common.isForward']}" sortable="false" />
										<ec:column property="null"  width="1%" title="${mr['page.common.button.oper']}" sortable="false" viewsDenied="xls,csv">
											<center>
												<c:if test="${item.loadResult=='失败'}">											
														<a href="${ctx}/server/dmsRunLog.do?method=reLoadFailFile&logid=${item.logId}" style="color:red;font-size:12px">${mr['page.common.button.reload']}</a>
												</c:if>
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
