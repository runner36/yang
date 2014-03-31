<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat" %>
<%@ include file="/commons/taglibs.jsp"%>
<%
	String curDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()); 
%>
<html>
	<head>
	<title>订货意向单</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
	<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
	<script language="javaScript" src="${ctx}/scripts/base.js"></script>
	<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>
	<script type="text/javascript" src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
	<script>
		String.prototype.trim = function() {      
		   //return this.replace(/[(^\s+)(\s+$)]/g,"");//會把字符串中間的空白符也去掉      
		   //return this.replace(/^\s+|\s+$/g,""); //      
		    return this.replace(/(^\s*)|(\s*$)/g, "");
		} 
		function save(){
			if(document.getElementById("brandIdInput").value==""){
				alert("请选择物料组");
				return;
			}
			var form1 = document.getElementById("orderForm");
			form1.action = "${ctx}/order/orderInfo.do?method=editSp";
			form1.submit()
		}
		//物料树型列表
		function selectDict(dictName){
			var v = openDictTree('${ctx}',dictName,2,0,'','',2);
			if(v){
				if(v.id!=''){
					document.getElementById("brandIdInput").value=v.id;
					document.getElementById("brandNameInput").value=v.text;
				}else{
					document.getElementById("brandIdInput").value="";
					document.getElementById("brandNameInput").value="";
				}
			}
		}
		
		function selectMaterials(){
			var v = openOrderMaterialsTree('${ctx}',2,0,'',1);
			if(v){
				if(v.id!=''){
					document.getElementById("brandIdInput").value=v.id;
					document.getElementById("brandNameInput").value=v.text;
				}else{
					document.getElementById("brandIdInput").value="";
					document.getElementById("brandNameInput").value="";
				}
			}
		}
		</script>
	</head>
	<body onLoad="WindowSollAuto()" onResize="WindowSollAuto()">
		<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form styleId="orderForm" action="/order/orderInfo.do?method=editSp" method="post">
			<input type="hidden" id="itemInput" name="orderItems" value=""/>
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						订货意向单
					</h4>
					<div class='MenuList'>
						<!-- <a href="javascript:addRow()">添加</a>
						<a href="javascript:delRow()">删除</a>
						<a href="javascript:save();">保存</a> -->
						<!-- <a href="mdmStore.do?method=list">${mr['page.common.button.cancel']}</a> -->
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
										<td width="1%"class="formTable">选择物料组<font color="#FF0000">＊</font></td>
										<td width="1%">
											<input type="hidden" id="brandIdInput" name="itemBrandId" value=""/><!-- selectDict('prodBrand') -->
											<input type="text" id="brandNameInput" name="brandName" readonly class="select_but" onclick="javascript:selectMaterials();"/>
										<td colspan="2"><input type="button" style="width:100px;" class="select" value="订货单" onclick="javascript:save();"/></td>
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
		</html:form>
	</body>
</html>
