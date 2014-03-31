<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@page import="com.winchannel.core.utils.ECPage"%>
<%@page import="com.winchannel.core.utils.Page"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<%
	ECPage ecPage = (ECPage)session.getAttribute(Page.DEFAULT_PAGENAME);
	int pageIndexBase=(ecPage.getInt(Page.PAGE_NO)-1)*ecPage.getInt(Page.PAGE_SIZE);
	%>
	<head>
		<%@ include file="/commons/meta.jsp"%>
		<title>产品预匹配</title>
		<link rel="stylesheet" type="text/css"
			href="${ctx}/styles/sidecss.css">
		<link rel="stylesheet" type="text/css"
			href="${ctx}/widgets/extremecomponents/extremecomponents.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/dms.js"></script>
		<script language="javaScript" src="${ctx}/scripts/jquery-1.2.6.min.js"></script>
		<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>

		<script>
		
	    function query() {
			resetec();
			var form = document.matcherForm;
			loading();
			form.submit();
		}
		
	    function checkedBox(boxName){
	    	var checkBoxs=document.getElementsByName(boxName);
	    	var checkedBoxs=new Array();
	    	for(var i=0;i<checkBoxs.length;i++){
	    		if(checkBoxs[i].checked){
	    			checkedBoxs.push(checkBoxs[i]);
	    		}
	    	}
	    	return checkedBoxs;
	    }
	    
	    function remove(){
	    	var dppmIds=doGetChekBoxHiddenValues("cid","flag");
	    	if(dppmIds.length<=0){
	    		alert("请选择数据!");
				return;
	    	}
	    	if(!confirm("确定移除选择记录?")){
				return;
			}
	    	mess.style.display = 'inline';
	    	
	    	$.ajax({
		       type: "POST",
		       url:  "${ctx}/server/matcher.do?method=removeDmsProdPreparedMappingById",
		       data: {	      
		    	   flag:dppmIds
		       },
		       async: false,
		       dataType: "HTML",
		       success: function(data){
		         if(data=='SUCCESS'){
		       		alert("删除成功!");
		       		document.matcherForm.submit();
		         }else{
					alert(data);
					mess.style.display = 'none';
		         }
		       },
		       error: function(msg){ 
				 alert("删除失败!");
			   }
		    });	
	    }
	    
		function Skuselect(){
			var form = document.matcherForm;
			var cidArr = document.getElementsByName("cid");
			var flagArr = document.getElementsByName("flag");
			var flag=true;
			var chkNum=0;
			for(var i=0; i<cidArr.length; i++){
				if(cidArr[i].checked==true){
					for(var j=i+1; j<cidArr.length; j++){
						if(cidArr[j].checked==true){
							if(cidArr[i].value==cidArr[j].value){
								alert("第"+(<%=pageIndexBase%>+i+1)+"条数据和"+(<%=pageIndexBase%>+j+1)+"条数据重复");
								flag=false;
							}
						}				
					}
					chkNum=chkNum+1;
				}
			}
			if(chkNum==0){
				alert("请选择数据！");
				return;
			}
			
			if(flag && chkNum>0){
				if(!confirm("确定匹配所选数据吗?")){
					return;
				}
				mess.style.display = 'inline';
				
			    $.ajax({
			       type: "POST",
			       url: "${ctx}/server/matcher.do?method=matcherSkuequals",
			       data: {	      
			        flag:doGetChekBoxHiddenValues("cid","flag"),
			        clientId:doGetChekBoxHiddenValues("cid","clientId"),
			        distProdCode:doGetChekBoxHiddenValues("cid","distProdCode"),
			        distProdUnit:doGetChekBoxHiddenValues("cid","distProdUnit"),
			        distProdNames:doGetChekBoxHiddenValues("cid","distProdNames")
			       },
			       async: false,
			       dataType: "HTML",
			       success: function(data){
			         if(data=='SUCCESS'){
			         
			       		alert("产品匹配成功!");
			       		 //window.location='${ctx}/server/matcher.do?method=toSkuPreMappingPage'
			       		document.matcherForm.submit();
			         }else{
						alert(data);
						mess.style.display = 'none';
			         }
			       },
			       error: function(msg){ 
					 alert("产品匹配失败!");
				   }
			    });			
			}
		}
		function button(){
			$.ajax()
		}
		
		function doGetChekBoxHiddenValues(name,hiddenName){
			var checkBoxs=doGetCheckedBox(name);
			var result=new Array();
			for(var i=0;i<checkBoxs.length;i++){
				result.push(doGetChekBoxBrother(checkBoxs[i],hiddenName));
			}
			return result;
		}
		
		
		function doGetChekBoxBrother(checkBox,hiddenName){
			for(var i=0;i<checkBox.parentNode.childNodes.length;i++){
				if(checkBox.parentNode.childNodes[i].name==hiddenName){
					return checkBox.parentNode.childNodes[i].value;
				}
			}
			return null;
		}
		
		function doGetCheckedBox(name){
			var eles=document.getElementsByName(name);
			var result=new Array();
			for(var i=0;i<eles.length;i++){
				if(eles[i].checked){
					result.push(eles[i]);
				}
			}
			return result;
		}
		
		function creatPreData(){
			document.getElementById("searchbox").style.display = "none";
			document.getElementById("hideQuery_A").style.display = "none";
			//document.getElementById("query_A").style.display = "block";
			//document.getElementById("showQuery_A").style.display = "block";
			//document.getElementById("createDataBox").style.display = "block";
			document.getElementById("query_A").style.display = "none";
			document.getElementById("showQuery_A").style.display = "none";
			document.getElementById("cpd").style.display = "none";
			//document.getElementById("matchData").style.display = "none";
			document.getElementById("startMatch").style.display = "block";
			//document.getElementById("continueMatch").style.display = "block";
			//document.getElementById("stopMatch").style.display = "block";
			document.getElementById("closecpd").style.display = "block";
			
			document.getElementById("clientCodeQuery").style.display = "none";
			document.getElementById("distProdNameQuery").style.display = "none";
			document.getElementById("distProdNameQueryInput").style.display = "none";
			document.getElementById("clientCodeQueryInput").style.display = "none";
			
			var strs=["enableMatcher4KeywordCheck","enableMatcher4KeywordCheckInput",
			          "enableMatcher4UpcCheck","enableMatcher4UpcCheckInput", 
			          "enableMatcher4PurchaseCheck","enableMatcher4PurchaseCheckInput"
			         ];
			blockSty(strs);
		}
		
		function blockSty(strs){
			for(var i=0;i<strs.length;i++){
				document.getElementById(strs[i]).style.display = "block";
			}
		}
		
		function noneSty(strs){
			for(var i=0;i<strs.length;i++){
				document.getElementById(strs[i]).style.display = "none";
			}
		}
		
		function closePreData(){
			document.getElementById("closecpd").style.display = "none";
			document.getElementById("startMatch").style.display = "none";
			//document.getElementById("continueMatch").style.display = "none";
			//document.getElementById("stopMatch").style.display = "none";
			//document.getElementById("matchData").style.display = "block";
			document.getElementById("cpd").style.display = "block";
			document.getElementById("query_A").style.display = "block";
			document.getElementById("showQuery_A").style.display = "block";
			document.getElementById("createDataBox").style.display = "none";
			
			var strs=["enableMatcher4KeywordCheck","enableMatcher4KeywordCheckInput",
			          "enableMatcher4UpcCheck","enableMatcher4UpcCheckInput", 
			          "enableMatcher4PurchaseCheck","enableMatcher4PurchaseCheckInput"
			         ];
			noneSty(strs);
		}
		
		function showQuery_(consoleElt){
			document.getElementById("createDataBox").style.display = "none";
			var obj = document.getElementById("searchbox");
			obj.style.display = "block";
			if(consoleElt == null)
				consoleElt = "query_A,queryXls_A";
			try{
				var elt = consoleElt.split(",");
				for(var i = 0; i < elt.length; i++){
					$$(elt[i]).style.display = "";
				}
				$$("hideQuery_A").style.display = "block";
				$$("showQuery_A").style.display = "none";
				
				document.getElementById("clientCodeQuery").style.display = "block";
				document.getElementById("distProdNameQuery").style.display = "block";
				document.getElementById("distProdNameQueryInput").style.display = "block";
				document.getElementById("clientCodeQueryInput").style.display = "block";
				
			}catch(e){}
		}
		
		function selectDist() {
			var form = document.matcherForm;
			var v = openDistTree('${ctx}', '', '1', form.mdmDistributor_distId.value);
			if (v) {
				form.mdmDistributor_distId.value = v.id;
				form.distName_.value = v.text;
			}
		}
		
		function doStartMatch(){
			if(document.getElementById("mdmDistributor_distId").value==''){
				alert("请选择经销商!");
				return;
			}
			
		
			if(!confirm("确定开始?")){
				return;
			}
			
			mess.style.display = 'inline';
			$.ajax({
		       type: "POST",
		       url: "${ctx}/server/matcher.do?method=matcherSku",
		       data: {	      
		      	  mdmDistributor_distId:document.getElementById("mdmDistributor_distId").value
		      	  ,enableMatcher4Keyword:document.getElementsByName("enableMatcher4Keyword")[0].checked
		      	  ,enableMatcher4Upc:document.getElementsByName("enableMatcher4Upc")[0].checked
		      	  ,enableMatcher4Purchase:document.getElementsByName("enableMatcher4Purchase")[0].checked
		      	  ,purchasePeriodStart:document.getElementsByName("purchasePeriodStart")[0].value
		       },
		       async: true,
		       dataType: "HTML",
		       timeout: 2100000,//35 minutes
		       success: function(data){
		         if(data=='SUCCESS'){
		       		 alert("预匹配数据生成结束!");
		         }else{
					 alert(data);
		         }
		         //window.location='${ctx}/server/matcher.do?method=toSkuPreMappingPage'
		         document.matcherForm.submit();
		       },
		       error: function(msg,isout){ 
		    	 if(isout=='timeout'){
		    		 alert("连接超时!");
		    	 }else{
		    		 alert("预匹配数据生成失败!"+msg);
		    	 }
				 //window.location='${ctx}/server/matcher.do?method=toSkuPreMappingPage';
				 document.matcherForm.submit();
			   }
		    });			   
		}
		
		function doContinueMatch(){
			if(!confirm("确定继续？")){
					return;
				}
			mess.style.display = 'inline';
				$.ajax({
			       type: "POST",
			       url: "${ctx}/server/matcher.do?method=continueMatcherSku",
			       data: {	      
			      	  mdmDistributor_distId:document.getElementById("mdmDistributor_distId").value
			       },
			       async: false,
			       dataType: "HTML",
			       success: function(data){
			         if(data=='SUCCESS'){
			       		 alert("继续生成预匹配数据结束!");
			       		 window.location='${ctx}/server/matcher.do?method=toSkuPreMappingPage';
			         }else{
						 alert(data);
			         }
			       },
			       error: function(msg){ 
						alert("继续生成预匹配数据失败!");
				   }
			    });	
		}
		
		
		function doStopMatch(){
			if(!confirm("确定停止？")){
					return;
				}
			mess.style.display = 'inline';
				$.ajax({
			       type: "POST",
			       url: "${ctx}/server/matcher.do",
			       data: {	      
			      		 method:"stopMatcherSku"
			       },
			       async: false,
			       dataType: "HTML",
			       success: function(data){
			         if(data=='SUCCESS'){
			       		 alert("成功停止生成预匹配数据!");
			       		 window.location='${ctx}/server/matcher.do?method=toSkuPreMappingPage';
			         }else{
						 alert(data);
			         }
			       },
			       error: function(msg){ 
						alert("未能成功强制停止预匹配数据!");
				   }
			    });	
		}
		
		function hideQuery_(consoleElt){
			var obj = document.getElementById("searchbox");
			obj.style.display="none";
			if(consoleElt == null)
				consoleElt = "query_A,queryXls_A";
			try{
				var elt = consoleElt.split(",");
				for(var i = 0; i < elt.length; i++){
					$$(elt[i]).style.display = "none";
				}
				$$("hideQuery_A").style.display = "none";
				$$("showQuery_A").style.display = "block";
			}catch(e){}
			
			document.getElementById("clientCodeQuery").style.display = "none";
			document.getElementById("distProdNameQuery").style.display = "none";
			document.getElementById("distProdNameQueryInput").style.display = "none";
			document.getElementById("clientCodeQueryInput").style.display = "none";
		}
		
		
		function removeByDists(){
			if(document.getElementById("mdmDistributor_distId").value==''){
				if(!confirm("确定不选择经销商?\n移除全部预匹配数据?")){
					//alert("请选择经销商!");
					return;
				}
			}else{
				if(!confirm("确定移除所选经销商下的全部预匹配记录?")){
					return;
				}
			}
		
			//if(!confirm("确定移除所选经销商下的全部预匹配记录?")){
			//	return;
			//}
			mess.style.display = 'inline';
	    	$.ajax({
		       type: "POST",
		       url:  "${ctx}/server/matcher.do?method=removeDmsProdPreparedMappingByDist",
		       data: {	      
		    	   mdmDistributor_distId:document.getElementById("mdmDistributor_distId").value
		       },
		       async: false,
		       dataType: "HTML",
		       success: function(data){
		         if(data=='SUCCESS'){
		       		alert("删除成功!");
		       		document.matcherForm.submit();
		         }else{
					alert(data);
					mess.style.display = 'none';
		         }
		       },
		       error: function(msg){ 
				 alert("删除失败!");
			   }
		    });	
			
		}
		
		document.onkeydown = function(e) {
			var event = window.event || e;
			if(mess.style.display == 'inline'){
				return;
			}
			//Ctrl+Alt+D
		    if (event.ctrlKey && event.altKey && event.keyCode == 68) {
		    	removeByDists();
			}else if (event.ctrlKey && event.keyCode == 13 && document.getElementById("startMatch").style.display == "none") {
				Skuselect();
			}else if (event.keyCode == 13) {
				if(document.getElementById("startMatch").style.display == "block"){
					doStartMatch();
				}else{
					query();
				}
				
			}
		};

			
		function updateTargetProdUnit(unitId,recordId){
			mess.style.display = 'inline';
			$.ajax({
		       type: "POST",
		       url: "${ctx}/server/matcher.do?method=updateTargetProdUnit",
		       data: {	      
		    	   id:recordId,
		    	   unitId:unitId
		       },
		       async: true,
		       dataType: "HTML",
		       timeout: 900000,
		       success: function(data){
		         if(data!='SUCCESS'){
		        	 alert(data);
		         }
		         mess.style.display = 'none';
		       },
		       error: function(msg,isout){ 
		    	 if(isout=='timeout'){
		    		 alert("连接超时!");
		    	 }else{
		    		 alert("设置失败!"+msg);
		    	 }
		    	 mess.style.display = 'none';
			   }
		    });
			
		}
		
		</script>
	</head>
	<body onload="WindowSollAuto()" onresize="WindowSollAuto()"><jsp:include
			page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/server/matcher.do?method=toSkuPreMappingPage" method="post">
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						<!--TitleStrat-->
						${mr['page.customize.title.productPreMatch']}
						<!--TitleStrat-->
					</h4>
					<div class='MenuList'>

						<a href="javascript:showQuery_('query_A');" id="showQuery_A"
							>${mr['page.common.button.setquery']}</a>

					 
						<a href="javascript:hideQuery_('query_A');" id="hideQuery_A"
							>${mr['page.common.button.close']}</a>
						 
							
						<a href="javascript:query();" id="query_A" title="Enter">${mr['page.common.button.query']}</a>
					
						<a style="background-image: url(<%=request.getContextPath()%>/images/SMenuBut_03.gif ) !important; width: 80px !important;"
							id="cpd" href="javascript:creatPreData()">${mr['page.common.button.generatedData']}</a>
					
						<a href=javascript:closePreData(); id="closecpd" 
							style="display: none">${mr['page.common.button.close']}</a>
						<a href="javascript:doStartMatch()" style="display: none"
							id="startMatch" title="Enter">${mr['page.common.button.start']}</a>
						<!-- 
						<a href="javascript:doContinueMatch()" style="display: none"
							id="continueMatch">继续</a>
						<a href="javascript:doStopMatch()" style="display: none"
							id="stopMatch">停止</a>
						--> 
						<a href="javascript:Skuselect();" id="matchData" title="Ctrl+Enter">${mr['page.common.button.match']}</a>
						<!-- 
						<a href="javascript:window.location='${ctx}/server/matcher.do?method=toSkuPreMappingPage';">${mr['page.common.button.refresh']}</a>
						 -->
						<a href="javascript:remove();" id="matchData" title="Ctrl+Alt+D:按经销商移除记录">${mr['page.common.button.remove']}</a>
					</div>
					
					<span class="right"></span>
				</div>
				<div id="mess"
						style="position: absolute; display: none; font-size: 14px; font-weight: bold; text-align: center; height: 800px; width: 100%; line-height: 400px; z-index: 1000; background: #fff; filter: Alpha(Opacity =   70);">
							${mr['page.common.mess.wait']}......
					</div>
				<div class="bosom_side">
					<div class="casing">
						<div class="caput">
							<span class="left"></span><span class="right"></span>
						</div>
						<div class="viscera" id="SollAuto">
							<div class="sheet_div">
								<div class="searchbox">
								<table>
									<tr>
										<td style="width: 5%">
											${mr['page.common.distName']}
										</td>
										<td align="left">
											<html:hidden name="ec" property="mdmDistributor_distId"/>
											<html:text name="ec" property="distName_" styleClass="select_but" onclick="selectDist()" readonly="true"/>
											<!-- 
											<input type="hidden" name="mdmDistributor_distId"
												id="mdmDistributor_distId">
											<input type="text" name="distName_" value=""
												onclick="selectDist()" readonly="readonly"
												class="select_but">
											 -->
										</td>
										<td id="clientCodeQuery" style="width: 5%">
											${mr['page.common.clientCode']}
										</td>
										<td id="clientCodeQueryInput">
											<html:text property="clientCode" />
										</td>
										<td id="distProdNameQuery" style="width: 5%">
											${mr['page.common.distProdName']}
										</td>
										<td id="distProdNameQueryInput">
											<html:text property="distProdName" />
										</td>
										
										<td id="enableMatcher4KeywordCheck" style="width: 5%;display: none" align="left">
											多结果:
										</td>
										<td id="enableMatcher4KeywordCheckInput" style="display: none" align="left">
											<html:checkbox name="ec" property="enableMatcher4Keyword"  style="width: 18px;"/>
										</td>
										<td id="enableMatcher4UpcCheck" style="width: 5%;display: none" align="left">
											UPC：
										</td>
										<td id="enableMatcher4UpcCheckInput" style="display: none" align="left">
											<html:checkbox name="ec" property="enableMatcher4Upc"  style="width: 18px;"/>
										</td>
										
										<td id="enableMatcher4PurchaseCheck" style="width: 5%;display: none" align="left">
											发货进货&在途天数：
										</td>
										<td id="enableMatcher4PurchaseCheckInput" style="display: none" align="left">
											<html:checkbox name="ec" property="enableMatcher4Purchase" style="width: 18px;"/>
											<html:select  name="ec" property="purchasePeriodStart">
												<html:option value="90"></html:option>
												<html:option value="60"></html:option>
												<html:option value="30"></html:option>
												<html:option value="20"></html:option>
												<html:option value="10"></html:option>
												<html:option value="120"></html:option>
											</html:select>
										</td>
									</tr>
								</table>
								</div>
								
								<div class="searchbox" id="createDataBox" style="display: none">
								</div>
							
								<div class="searchbox" id="searchbox">
									<table>
										<tr>
											<!-- 
											<td>
												${mr['page.common.distCode']}
											</td>
											<td>
												<html:text name="ec" property="distCode" />
											</td>
											
											<td>
												${mr['page.common.clientCode']}
											</td>
											<td>
												<html:text name="ec" property="clientCode" />
											</td>
											<td>
												${mr['page.common.distProdName']}
											</td>
											<td>
												<html:text name="ec" property="distProdName" />
											</td>
 											-->
										</tr>
									</table>
								</div>
								
								
								
								<span style="font-style: italic ;font: bold;font: bolder;font-size: larger"></span>
								<% 
									String isNoStyle="";
								%>
								<bean:define id="no1Str" value=""/> 
								<ec:table items="list" var="item" onInvokeAction="query()"
									form="matcherForm" retrieveRowsCallback="limit"
									style="width:100%" tableId="ec"
									action="matcher.do?method=toSkuPreMappingPage"  >
									<ec:exportXls fileName="${mr['page.customize.title.productPreMatch']}.xls" tooltip="${mr['page.common.exportExcel']}" view="xls" />
									<ec:exportCsv fileName="${mr['page.customize.title.productPreMatch']}.csv" tooltip="${mr['page.common.exportCVS']}" view="csv" />
									
									<ec:row highlightRow="true" style="<%=isNoStyle%>" >
									
										<ec:column property="null" width="1%" title="${mr['page.common.button.oper']}"
											sortable="false" viewsDenied="xls">
											<center>
												<input type="checkbox" name="cid"
													value="${item.clientId}${item.distProdCode}${item.distProdUnit}${item.distProdName}">

												<input type="hidden" name="flag" value="${item.id}" />

												<input type="hidden" name="clientId"
													value="${item.clientId}" />
												<input type="hidden" name="distProdCode"
													value="${item.distProdCode}" />
												<input type="hidden" name="distProdUnit"
													value="${item.distProdUnit}" />
												<input type="hidden" name="distProdNames"
													value="${item.distProdName}" />
											</center>
										</ec:column>
										
										<ec:column property="${mr['page.common.number']}" cell="com.winchannel.extension.extremecomponents.RowNumberCell" sortable="false"/> 
										
										<bean:define id="codeUnit" value="${item.clientId}${item.distProdCode}${item.distProdUnit}${item.distProdName}"/> 
										<c:choose>
											   <c:when test="${codeUnit != no1Str}" >
											   		<c:set var="no1Str" value="${codeUnit}" />
											   		<%
											   		isNoStyle="font: bold;font: bolder;font-size: larger" ;
											   		%>
											   </c:when>
											   <c:otherwise>
											   		<%
											   		isNoStyle="" ;
											   		%>
											   </c:otherwise>
										</c:choose>
										<ec:column property="distProdUnit" title="${mr['page.common.distProdUnit']}"  sortable="false">
											<span style="<%=isNoStyle %>">
												${item.distProdUnit}
											</span>
										</ec:column>
										<ec:column property="targetProdUnitName" title="${mr['page.common.pordStandardUnit']}" sortable="false">
											<span style="<%=isNoStyle %>">
												<logic:empty property="targetProdPrice" name="item">
													<html:select name="item" property="targetProdUnit" onchange="updateTargetProdUnit(this.value,'${item.id}')" style="margin-bottom:0px;width:100%;height:100%">
														<html:option value="">${mr['page.common.button.pleaseSelect']}</html:option>
														<!-- dicts -->
														<bean:define id="targetProdUnits" name="item" property="targetProdUnits"/> 
														<html:options collection="targetProdUnits" property="dictItemId" labelProperty="itemName"/>
													</html:select>
												</logic:empty>
												<logic:notEmpty property="targetProdPrice" name="item">
													${item.targetProdUnitName}
												</logic:notEmpty>
											</span>
										</ec:column>
										
										<ec:column property="distProdName" title="${mr['page.common.distProdName']}" sortable="false">
											<span style="<%=isNoStyle %>">
												${item.distProdName}
											</span>
										</ec:column>
										
										<ec:column property="spec" title="规格" sortable="false">
											<span style="<%=isNoStyle %>">
												${item.spec}
											</span>
										</ec:column>
										
										<ec:column property="targetProdName" title="${mr['page.common.pordStandardName']}" sortable="false">
											<span style="<%=isNoStyle %>">
												${item.targetProdName}
											</span>
										</ec:column>
										<ec:column property="targetProdCode" title="${mr['page.common.pordStandardCode']}" sortable="false">
											<span style="<%=isNoStyle %>">
												${item.targetProdCode}
											</span>
										</ec:column>
										<ec:column property="keywords" title="${mr['page.common.pordStandardKey']}"  sortable="false">
											<span style="<%=isNoStyle %>">
												${item.keywords}
											</span>
										</ec:column>
										<ec:column property="distProdUpc" title="${mr['page.common.distProdUPC']}"  sortable="false">
											<span style="<%=isNoStyle %>">
												${item.distProdUpc}
											</span>
										</ec:column>
										<ec:column property="targetProdUpc" title="${mr['page.common.pordStandardUPC']}" sortable="false">
											<span style="<%=isNoStyle %>">
												${item.targetProdUpc}
											</span>
										</ec:column>
										
										
										<ec:column property="purchaseBillDate" title="${mr['page.common.distProdPurchaseDate']}" sortable="false">
											<span style="<%=isNoStyle %>">
												${item.purchaseBillDate}
											</span>
										</ec:column>
										<ec:column property="distProdPrice" title="${mr['page.common.distProdPrice']}"  sortable="false">
											<span style="<%=isNoStyle %>">
												${item.distProdPrice}
											</span>
										</ec:column>
										<ec:column property="distPurchasePrice" title="${mr['page.common.distProdPurchasePrice']}" sortable="false">
											<span style="<%=isNoStyle %>">
												${item.distPurchasePrice}
											</span>
										</ec:column>
										<ec:column property="distSalePrice" title="${mr['page.common.distProdSalePrice']}" sortable="false">
											<span style="<%=isNoStyle %>">
												${item.distSalePrice}
											</span>
										</ec:column>
										<ec:column property="targetProdPrice" title="${mr['page.common.pordStandardPrice']}"  sortable="false">
											<span style="<%=isNoStyle %>">
												${item.targetProdPriceFormat}
											</span>
										</ec:column>

										<ec:column property="distProdCode" title="${mr['page.common.distProdCode']}"  sortable="false">
											<span style="<%=isNoStyle %>">
												${item.distProdCode}
											</span>
										</ec:column>
										<ec:column property="distCode" title="${mr['page.common.distCode']}"  sortable="false">
											<span style="<%=isNoStyle %>">
												${item.distCode}
											</span>
										</ec:column>
										
										<ec:column property="distName" title="${mr['page.common.distName']}"  sortable="false">
											<span style="<%=isNoStyle %>">
												${item.distName}
											</span>
										</ec:column>
										
										<ec:column property="clientCode" title="${mr['page.common.clientCode']}"  sortable="false">
											<span style="<%=isNoStyle %>">
												${item.clientCode}
											</span>
										</ec:column>
										<ec:column property="clientId" title="${mr['page.common.clientId']}" sortable="false">
											<span style="<%=isNoStyle %>">
												${item.clientId}
											</span>
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

