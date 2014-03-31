<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@page import="java.util.Date" %>
<%@page import="java.text.SimpleDateFormat" %>
<html>
	<head>
		<%@ include file="/commons/meta.jsp" %>
		<title>产品主数据特征</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/widgets/extremecomponents/extremecomponents.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/dms.js"></script>
		<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>
		<script language="javaScript" src="${ctx}/scripts/jquery-1.2.6.min.js"></script>
		<script>
	    function query() {
			resetec();
			var form = document.featureExtractionForm;
			loading();
			form.submit();
		}
	    
	    function calculateExtraction(){
	    	var mess=document.getElementById("mess");
	    	mess.style.display = 'inline';
			$.ajax({
		       type: "POST",
		       url:  "${ctx}/server/mdmProductFeatureExtraction.do?method=calculateExtraction",
		       data: {	      
		    	  
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
		</script>
	</head>
	<body onload="WindowSollAuto()" onresize="WindowSollAuto()"><jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
	
		<html:form action="/server/mdmProductFeatureExtraction.do?method=list" method="post">
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						<!--TitleStrat-->
						产品主数据特征
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
									action="mdmProductFeatureExtraction.do?method=list">
									<ec:exportXls fileName="产品主数据特征列表.xls" tooltip="导出EXCEL" view="xls" />
									<ec:exportCsv fileName="产品主数据特征列表.csv" tooltip="导出CSV" view="csv" />
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
