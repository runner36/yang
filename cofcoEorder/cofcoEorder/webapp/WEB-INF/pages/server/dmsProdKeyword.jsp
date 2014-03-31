<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>${mr['page.customize.title.pordKeywordMaint']}</title>
		<link rel="stylesheet" type="text/css"
			href="${ctx}/styles/sidecss.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script>
		function save(){
		   var v=document.getElementById("fileupload").value;
	          if(jsAllTerm(v) ==''){
		      		alert("${mr['page.common.mess.selectExcelFile']}");
		      		return;
		  	  }
			mess.style.display = 'inline';
			impForm.submit();
		}
		  
		function jsAllTerm(strInput){   
            return   strInput.replace(/[   \t]+/g,"");   
        }
		</script>
	</head>

	<body onLoad="WindowSollAuto()" onResize="WindowSollAuto()">
		<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<div class="bosom_one">
			<div class="bosom_top">
				<span class="left"></span>
				<h4>
					<!--PageTitleStrat-->
					${mr['page.customize.title.pordKeywordMaint']}
					<!--PageTitleEnd-->
				</h4>
				<div class='MenuList'>
					<a href="${ctx}/server/matcher.do?method=exportSkuKeywords">${mr['page.common.button.export']}</a>
					<a href="javascript:save();">${mr['page.common.button.import']}</a>
					<!-- 
					<a class="Im80"
						href="${ctx}/server/matcher.do?method=downProdKeywordTemplate">${mr['page.common.button.template']}</a>
					 -->
				</div>
				<span class="right"></span>
			</div>
			<div id="mess"
				style="position: absolute; display: none; font-size: 14px; font-weight: bold; text-align: center; height: 800px; width: 100%; line-height: 400px; z-index: 1000; background: #fff; filter: Alpha(Opacity =   70);">
				${mr['page.common.mess.wait']}......
			</div>
		</div>
		<div class="bosom_side">
			<div class="casing">
				<div class="caput">
					<span class="left"></span><span class="right"></span>
				</div>
				<div class="viscera" id="SollAuto">
					<div align="left">
					</div>
					<div class="sheet_div">
						<div align="left">
						</div>
						<form name="impForm" id="impForm"
							action="${ctx}/server/matcher.do?method=importSkuKeywords"
							method="post" enctype="multipart/form-data">
							<table cellpadding="0" cellspacing="0" class="list_add">
								<tr>
								<tr>
									<td width="1%">
										${mr['page.common.excelFile']}
										<font color="#FF0000">＊</font>
									</td>
									<td>
										<input type="file" name="fileupload" id="fileupload"
											style="width: 245px">
									</td>
								</tr>
								<tr>
							</table>
						</form>
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


