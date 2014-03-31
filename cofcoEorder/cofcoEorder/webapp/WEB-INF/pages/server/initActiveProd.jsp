<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>手动刷新活动产品</title>
		<%@ include file="/commons/meta.jsp" %>
		<link rel="stylesheet" type="text/css"
			href="${ctx}/styles/sidecss.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/jquery-1.2.6.min.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/mdm.js"></script>
        <script>
    	$(document).ready(function(){
			$("#distName").click(function(){
				var v=openDistTree('${ctx}',2,0,$("#distId").attr("value"));	
				if (v) {
					$("#distId").attr("value",v.id);
					$("#distName").attr("value",v.text);
				}
			});
			$("#create").click(function(){
				loading();
				$.ajax({
					   type: "POST",
					    url: "${ctx}/server/dmsProdMapping.do?method=prodActiveSave",
					    data: "distId="+$("#distId").attr("value"),
					    success: function(msg){
					    	$("#loading_wrap").css("display","none");
					   		alert(msg);
					   }
					});
				
			});
			});
		</script>
	</head>

	<body onload="WindowSollAuto()" onresize="WindowSollAuto()">
		<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<div class="bosom_one">
			<div class="bosom_top">
				<span class="left"></span>
				<h4>
					<!--PageTitleStrat-->
					手动刷新活动产品
					<!--PageTitleEnd-->
				</h4>
               <div class='MenuList'>
               </div>

				<span class="right"></span>
			</div>
			<div class="bosom_side">
				<div class="casing">
					<div class="caput">
						<span class="left"></span><span class="right"></span>
					</div>
					<div class="viscera" id="SollAuto">
						<div class="sheet_div" style="padding:20px">
							<div><span id="lastCreate"></span></div>
							<br></br>
	             		  	<div>
	             		  		经销商：
	             		  		 <input type="hidden" id="distId" />
	             		  		  <input type="text" id="distName" readonly="true" class="select_but" />
	             		  		  <input type="button" id="create" value="${mr['page.common.button.start']}"   />
	             		  	</div>
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
		

	</body>
</html>


