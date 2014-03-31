<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>${mr['page.customize.title.prodAutoMapping']}</title>
		<link rel="stylesheet" type="text/css"
			href="${ctx}/styles/sidecss.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/jquery-1.2.6.min.js"></script>
        <script>
        	var xmlhttp;
		function createXMLHttpRequest() {
			try{
				xmlhttp=new ActiveXObject("Msxml2.XMLHTTP");
			}
			catch (e) {
				try{
					xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
				}
				catch(e) {
					try {
						xmlhttp=new XMLHttpRequest();
					}
					catch(e) {
						alert("failure");
					}
				}
			}
		}
        	function create()
        	{
        		$("#waiting").css("display","");
        		$("#message").css("display","none");
        		$("#create").attr("disabled","disabled");
        		createXMLHttpRequest();
					xmlhttp.open("post", "${ctx}/server/dmsProdMapping.do?method=autoMappingJob", true);
					xmlhttp.onreadystatechange = function() {
						if(xmlhttp.readyState == 4) {
							if(xmlhttp.status == 200) {
								if(xmlhttp.responseText!=null&&xmlhttp.responseText!='')
								{
								  $("#message").empty();
								  $("#message").append(xmlhttp.responseText);
								  $("#waiting").css("display","none");
								  $("#message").css("display","");
								  $("#create").attr("disabled","");
								}
							}
						}
					};
					xmlhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
					xmlhttp.send(null);
        	}
		</script>
	</head>

	<body onload="WindowSollAuto()" onresize="WindowSollAuto()">
		<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<div class="bosom_one">
			<div class="bosom_top">
				<span class="left"></span>
				<h4>
					<!--PageTitleStrat-->
					${mr['page.customize.title.prodAutoMapping']}
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
							<div>${mr['page.customize.title.prodAutoMapping']}：<span id="lastCreate"></span></div>
							<br></br>
	             		  	<div>
	             		  		<input type="button" id="create" value="${mr['page.common.button.start']}"  onclick="javascript:create();" />&nbsp;&nbsp;&nbsp;&nbsp;
	             		  		<span id="waiting" style="display:none"><img src="${ctx}/images/spinner_18_18.gif" /><span style="position:relative;bottom:4px">&nbsp;&nbsp;数据生成中......</span></span>
	             		  		<span id="message" style="display:none;position:relative;bottom:4px"></span>
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


