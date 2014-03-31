<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@page import="java.util.Date" %>
<%@page import="java.text.SimpleDateFormat" %>
<html>
	<head>
		<%@ include file="/commons/meta.jsp" %>
		<title>特征关键字</title>
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
			var form = document.featureKeywordForm;
			loading();
			form.submit();
		}
	    
	    function toEdit(){
	    	loading();
			window.location="<%=request.getContextPath()%>/server/productFeatureKeyword.do?method=toEdit";
	    }
	    
	    function calculateOccurrenceNumber(){
	    	var mess=document.getElementById("mess");
	    	mess.style.display = 'inline';
			$.ajax({
		       type: "POST",
		       url:  "${ctx}/server/productFeatureKeyword.do?method=calculateOccurrenceNumber",
		       data: {	      
		    	  
		       },
		       async: true,
		       dataType: "HTML",
		       success: function(data){
		         if(data=='SUCCESS'){
		        	 featureKeywordForm.submit();
		         }else{
					alert(data);
		         }
		       },
		       error: function(msg){ 
				 alert(msg);
			   }
		    });	
			
			var form = document.featureKeywordForm;
			form.submit();    	
	    }
		</script>
	</head>
	<body onload="WindowSollAuto()" onresize="WindowSollAuto()"><jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
	
		<html:form action="/server/productFeatureKeyword.do?method=list" method="post">
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						<!--TitleStrat-->
						产品特征
						<!--TitleStrat-->
					</h4>
					<div class='MenuList'>
						<!--MenuStrat-->
						<a href="javascript:showQuery_('query_A');" id="showQuery_A"
							>设置查询条件</a>
						<a href="javascript:hideQuery_('query_A');" id="hideQuery_A"
							>关闭</a>
						<a href="javascript:query();" id="query_A" >查询</a>
						<a href="javascript:toEdit();" >新增</a>
						<a href="javascript:openImportForm('${ctx}', 'FeatureKeyword');">导入</a>
						<a href="javascript:calculateOccurrenceNumber();" title="计算出现次数">计算</a>
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
											类别名称：
										</td>
										<td >
											<html:text name="ec" property="ec_typeName" />
										</td>
										<td  style="width: 5%">
											特征名称：
										</td>
										<td >
											<html:text name="ec" property="ec_featureName" />
										</td>
										<td  style="width: 5%">
											特征关键字名称：
										</td>
										<td >
											<html:text name="ec" property="ec_keyword" />
										</td>
									</tr>
								</table>
								</div>
								<ec:table items="list" var="item" onInvokeAction="query()"
									form="featureKeywordForm" retrieveRowsCallback="limit"
									style="width:100%" tableId="ec" sortRowsCallback="limit"
									action="productFeatureKeyword.do?method=list">
									<ec:exportXls fileName="产品特征关键字列表.xls" tooltip="导出EXCEL" view="xls" />
									<ec:exportCsv fileName="产品特征关键字列表.csv" tooltip="导出CSV" view="csv" />
										<ec:row highlightRow="true" >
										
										<ec:column property="keyword" title="关键字名称" sortable="false"/>
										<ec:column property="typeName" title="类别名称" sortable="false"/>
										<ec:column property="featureName" title="特征名称" sortable="false"/>
										<ec:column property="mdmOccurrenceNumber" title="主数据中出现次数" sortable="false"/>
										<ec:column property="distOccurrenceNumber" title="经销商数据中出现次数" sortable="false"/>
										<ec:column property="created" title="创建时间"  cell="date" format="yyyy-MM-dd HH:mm:ss"  sortable="false"/>
										<ec:column property="updated" title="修改时间"  cell="date" format="yyyy-MM-dd HH:mm:ss" sortable="false"/>
										<ec:column property="occurrenceNumberTime" title="计算时间"  cell="date" format="yyyy-MM-dd HH:mm:ss" sortable="false"/>
										
										<ec:column property="null" width="1%" title="操作" sortable="false" viewsAllowed="html">
											<center>
												<html:link page="/server/productFeatureKeyword.do?method=toEdit&id=${item.id}">
													<img src="${ctx}/images/edit.gif" border="0" alt="编辑" title="编辑" />
												</html:link>
												&nbsp;
												<html:link
													page="/server/productFeatureKeyword.do?method=delete&ids=${item.id}" onclick="return confirm('确认要删除该记录吗')">
													<img src="${ctx}/images/delete.gif" border="0" alt="删除" title="删除" />
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
