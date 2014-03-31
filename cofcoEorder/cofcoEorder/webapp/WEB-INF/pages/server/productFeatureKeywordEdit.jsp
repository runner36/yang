<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>产品特征关键字维护</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<style type="text/css">
		
		button.comboboxButton {width:2em;  margin-left: -1px; height: 1px}
		button.comboboxButton .ui-button-text {display:none; }
		.ui-autocomplete-input {margin:0;background-color: #F0F0F0 }
		
		</style>
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/dms.js"></script>
		
		<script type="text/javascript" src="${ctx}/scripts/jquery-combobox/js/jquery.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/jquery-combobox/js/jquery-ui.min.js" ></script>
		<script type="text/javascript" src="${ctx}/scripts/jquery-combobox/js/jquery-ui-i18n.js" ></script>
		<script type="text/javascript" src="${ctx}/scripts/jquery-combobox/js/jquery-combobox-min.js"></script>
		<link type="text/css" rel="stylesheet" href="${ctx}/scripts/jquery-combobox/css/smoothness/jquery-ui.css" />
		<script>
		function save() {
			var id=document.getElementsByName("id")[0];
	    	var feature=document.getElementsByName("featureId")[0];
	    	var keyword=document.getElementsByName("keyword")[0].value;
	    	
	    	var featureId=feature.value;
	    	if(featureId==''){
	    		alert("请选择特征名称!");
	    		return;
	    	}
	    	feature.parentNode.childNodes[1].value=feature.options[feature.selectedIndex].text;
	    	if(keyword==''){
	    		alert("特征关键字名称!");
	    		return;
	    	}
	    	
	    	document.getElementsByName("featureName")[0].value=feature.options[feature.selectedIndex].text;
    		
	    	setTimeout("",1000);
	    	
	    	if(!confirm("确定保存?")){
	    		return;
	    	}
	    	
	    	$.ajax({
		       type: "POST",
		       url:  "${ctx}/server/productFeatureKeyword.do?method=checkKeyword",
		       data: {	      
		    	   id:id.value,
		    	   featureId:featureId,
		    	   keyword:keyword
		       },
		       async: false,
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
		}
		</script>
	</head>
	<body onload="WindowSollAuto()" onresize="WindowSollAuto()">
		<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/server/productFeatureKeyword.do?method=save" method="post">
			<html:hidden property="id" />
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						产品特征关键字维护
					</h4>
					<div class='MenuList'>
						<a href="javascript:save();">保存</a>
						<a href="productFeatureKeyword.do?method=list">取消</a>
					</div>
					<span class="right"></span>
				</div>
				<div class="bosom_side">
					<div class="casing">
						<div class="caput">
							<span class="left"></span><span  class="right"></span>
						</div>
						<div class="viscera" id="SollAuto">
							<div class="sheet_div">
								<table class="list_add">
									<tr>
										<td class="formTable">类别名称<font color="#FF0000">＊</font></td>
										<td align="left">
											<html:select name="featureKeywordForm" property="featureId" style="margin-bottom: 5px;">
												<html:option   value="">请选择</html:option>
												<html:options  collection="features" property="id" labelProperty="typeNameAndFeatureName"/>
											</html:select>
											<html:hidden property="featureName" />
										</td>
										<td class="formTable"></td>
										<td align="left">
										</td>
									</tr>
								    <tr>
										<td class="formTable">特征名称<font color="#FF0000">＊</font></td>
										<td align="left">
											<html:text property="keyword" maxlength="20" style="background-color: #F0F0F0"/>
										</td>
										<td class="formTable"></td>
										<td align="left"></td>
								    </tr>
								    <tr>
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
		
		
		<html:javascript formName="featureKeywordForm" staticJavascript="false" dynamicJavascript="true" cdata="false" />
		<script type="text/javascript" src="${ctx}/scripts/validator.jsp"></script>
		<script type="text/javascript">
		
		$(document).ready(function(){
			//$('#featureId').combobox();
			$("[name= 'featureId'][0]").combobox();
		});
		</script>
	</body>
</html>
	
