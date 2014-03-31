<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@page import="java.util.Date" %>
<%@page import="java.text.SimpleDateFormat" %>
<html>
	<head>
		<%@ include file="/commons/meta.jsp" %>
		<title>活跃产品特征</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/widgets/extremecomponents/extremecomponents.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/dms.js"></script>
		<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>
		<script language="javaScript" src="${ctx}/scripts/jquery-1.2.6.min.js"></script>
		<script>
	    function query() {
	    	if(document.featureExtractionForm.ec_distIds.value==''){
	    		alert('请选择经销商!');
	    		return;
	    	}
			resetec();
			var form = document.featureExtractionForm;
			loading();
			form.submit();
		}
	    
	    function calculateExtraction(){
	    	if(document.featureExtractionForm.ec_distIds.value==''){
	    		alert('请选择经销商!');
	    		return;
	    	}
	    	var mess=document.getElementById("mess");
	    	mess.style.display = 'inline';
			$.ajax({
		       type: "POST",
		       url:  "${ctx}/server/activeProductFeatureExtraction.do?method=calculateExtraction",
		       data: {	      
		    	   distIds:document.featureExtractionForm.ec_distIds.value
		       },
		       async: true,
		       dataType: "HTML",
		       success: function(data){
		         if(data=='SUCCESS'){
		        	 featureExtractionForm.submit();
		         }else{
					alert(data);
					mess.style.display = 'none';
					featureExtractionForm.submit();    
		         }
		       },
		       error: function(msg){ 
				 alert(msg);
				 mess.style.display = 'none';
				 featureExtractionForm.submit();    
			   }
		    });	
	    }
	    
	    function selectDist() {
			var form = document.featureExtractionForm;
			var v = openDistTree('${ctx}', '', '1', form.ec_distIds.value);
			if (v) {
				form.ec_distIds.value = v.id;
				form.distName_.value = v.text;
			}
		}
		</script>
	</head>
	<body onload="WindowSollAuto()" onresize="WindowSollAuto()"><jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
	
		<html:form action="/server/activeProductFeatureExtraction.do?method=list" method="post">
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						<!--TitleStrat-->
						活跃产品特征
						<!--TitleStrat-->
					</h4>
					<div class='MenuList'>
						<!--MenuStrat-->
						<a href="javascript:showQuery_('query_A');" id="showQuery_A"
							>设置查询条件</a>
						<a href="javascript:hideQuery_('query_A');" id="hideQuery_A"
							>关闭</a>
						<a href="javascript:query();" id="query_A" >查询</a>
						<a href="javascript:calculateExtraction();" >提取</a>
						<!--MenuEnd-->
					</div>
				
					<span class="right"></span>
				</div>
				<div id="mess"
						style="position: absolute; display: none; font-size: 14px; font-weight: bold; text-align: center; height: 800px; width: 100%; line-height: 400px; z-index: 1000; background: #fff; filter: Alpha(Opacity =   70);">
							正在处理，需要一段时间，请耐心等候......
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
										<td style="width: 5%">
											经销商名称：
										</td>
										<td align="left">
											<html:hidden name="ec" property="ec_distIds"/>
											<html:text name="ec" property="distName_" styleClass="select_but" onclick="selectDist()" readonly="true"/>
										</td>
										
										<td  style="width: 5%">
											产品名称：
										</td>
										<td >
											<html:text name="ec" property="ec_name" />
										</td>
									</tr>
								</table>
								</div>
								<ec:table items="list" var="item" onInvokeAction="query()"
									form="featureExtractionForm" retrieveRowsCallback="limit"
									style="width:100%" tableId="ec" sortRowsCallback="limit"
									action="activeProductFeatureExtraction.do?method=list">
									<ec:exportXls fileName="活跃产品特征列表.xls" tooltip="导出EXCEL" view="xls" />
									<ec:exportCsv fileName="活跃产品特征列表.csv" tooltip="导出CSV" view="csv" />
										<ec:row highlightRow="true" >
										
										<ec:columns autoGenerateColumns="com.winchannel.dms.feature.FeatureExtraction"/>
										
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
