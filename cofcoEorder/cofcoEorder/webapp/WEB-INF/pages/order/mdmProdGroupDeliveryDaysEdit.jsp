<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>物料组_送货日期维护</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>
		<script type="text/javascript" src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
		<script>
		function save() {
			checkProdDelivery();
		}
		
	  	var loading = false;
		function checkProdDelivery() {
			var form = document.mdmProdGroupDeliveryDaysForm;
			var id = document.getElementById("id").value;
			var prodBrandId = form.prodBrandId.value;
			if (prodBrandId != '') {
					createXMLHttpRequest();
					xmlhttp.open("post", "${ctx}/order/mdmProdGroupDeliveryDays.do?method=getProdDelivery&id=" + id +"&prodBrandId=" + prodBrandId, true);
					xmlhttp.onreadystatechange = function() {
						if(xmlhttp.readyState == 4) {
							if(xmlhttp.status == 200) {
								if(xmlhttp.responseText!=null&&xmlhttp.responseText!='') {
									form.prodBrandId.value ='';
									form.prodBrandName.value ='';
									alert("该物料组已经存在送货天数 ");
								}else{
						  		    if (validateMdmProdGroupDeliveryDaysForm(mdmProdGroupDeliveryDaysForm)) {
										form.submit();
									}
								}
							}
						}
				};
				xmlhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
				xmlhttp.send(null);
			
			}else{
				alert("物料组为必填项  ");
			}
		}
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
		//物料组列表
		function selectDict(dictName, id, name){
			var v = openDictTree('${ctx}',dictName,2,0,'','',1);
			if(v){
				if(v.id!=''){
					id.value = v.id;
					name.value = v.text;
				}else{
					id.value = "";
					name.value ="";
				}
			}
		}
		</script>
	</head>
	<body onLoad="WindowSollAuto()" onResize="WindowSollAuto()">
		<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/order/mdmProdGroupDeliveryDays.do?method=save" method="post">
			<html:hidden property="id" />
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						物料组_送货日期维护
					</h4>
					<div class='MenuList'>
						<a href="javascript:save();">${mr['page.common.button.save']}</a>
						<a href="mdmProdGroupDeliveryDays.do?method=list">${mr['page.common.button.cancel']}</a>
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
									<tr><html:hidden property="id" styleId="id"/>
										<td class="formTable">物料组： <font color="#FF0000">＊</font></td>
										<td align="left"><html:hidden property="prodBrandId" /> <html:text styleId="prodBrandName"
												property="prodBrandName" readonly="true"
												styleClass="select_but"
												onclick="selectDict('prodSTRU', mdmProdGroupDeliveryDaysForm.prodBrandId, mdmProdGroupDeliveryDaysForm.prodBrandName)" />
										</td>
										<td class="formTable">送货天数<font color="#FF0000">＊</font></td>
										<td align="left">
										   <html:text property="deliveryDays"  maxlength="5"/>
										</td>
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
		 <html:javascript formName="mdmProdGroupDeliveryDaysForm" staticJavascript="false" dynamicJavascript="true" cdata="false" />
		<script type="text/javascript" src="${ctx}/scripts/validator.jsp"></script>
	</body>
</html>
