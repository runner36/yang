<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>订单员、组织、产品组关系维护</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/dms.js"></script>
		<script>
		function save()
		 {
		   var v=document.getElementById("fileupload").value;
           if(jsAllTerm(v) =='')
		    {
		      alert("请选择对应的EXCEL文件");
		      return;
		   }
		   	mess.style.display = 'inline';
			impForm.submit();
		}
		  function   jsAllTerm(strInput)   
          {   
                return   strInput.replace(/[   \t]+/g,"");   
           }
		
		</script>
	</head>
	<body onload="WindowSollAuto()">
	<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<form name="impForm" action="${ctx}/order/mdmDistOrderempProdgroup.do?method=saveimpAdd" method="post" enctype="multipart/form-data">
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						订单员、组织、产品组关系维护
					</h4>
					<div class='MenuList'>
						<a href="javascript:save();">保存</a>
						<a id="ImExcel" href="${ctx}/order/mdmDistOrderempProdgroup.do?method=downStoreImpAdd">模板下载</a>
						<a href="${ctx}/order/mdmDistOrderempProdgroup.do?method=list">返回</a>
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
								<table class="list_add">
									<tr>
										<td class="formTable">Excel数据文件</td>
										<td align="left"><input type="file" name="fileupload" id="fileupload" style="width:220px"></td>
									</tr>
									<tr id="mess" style="display:none">
										<td class="formTable" colspan="2"><br><br><br><br>正在导入数据，导入需要一段时间，请耐心等候......</td>
									</tr>
									<tr>
										<td colspan="4"><font color="#ff0000">${mess}</font></td>
									</tr>
								</table>				
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
		</form>
	</body>
</html>
