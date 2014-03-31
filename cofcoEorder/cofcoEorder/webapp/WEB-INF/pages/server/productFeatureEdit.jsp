<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>产品特征维护</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/dms.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/jquery-1.2.6.min.js"></script>
		<script>
		function save() {
			
			if(validateFeatureForm(featureForm))
			{
				var id=document.getElementsByName("id")[0];
		    	var typeId=document.getElementsByName("typeId")[0];
		    	var featureName=document.getElementsByName("featureName")[0];
	    		$.ajax({
			       type: "POST",
			       url:  "${ctx}/server/productFeature.do?method=checkFeatureName",
			       data: {	      
			    	   id:id.value,
			    	   typeId:typeId.value,
			    	   featureName:featureName.value
			       },
			       async: false,
			       dataType: "HTML",
			       success: function(data){
			         if(data=='SUCCESS'){
			        	 featureForm.submit();
			         }else{
						alert(data);
			         }
			       },
			       error: function(msg){ 
					 alert(msg);
				   }
			    });	
			}
		}
		
		function changeFeatureType(opt){
			document.getElementsByName("typeName")[0].value=opt.options[opt.selectedIndex].text;
		}
		
		</script>
	</head>
	<body onload="WindowSollAuto()" onresize="WindowSollAuto()">
		<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/server/productFeature.do?method=save" method="post">
			<html:hidden property="id" />
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						产品特征维护
					</h4>
					<div class='MenuList'>
						<a href="javascript:save();">保存</a>
						<a href="productFeature.do?method=list">取消</a>
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
											<html:select name="featureForm" property="typeId" onchange="changeFeatureType(this)" style="margin-bottom: 5px;">
												<html:option   value="">请选择</html:option>
												<html:options  collection="featureTypes" property="id" labelProperty="typeName"/>
											</html:select>
											<html:hidden property="typeName" />
										</td>
										<td class="formTable"></td>
										<td align="left">
										</td>
									</tr>
								    <tr>
										<td class="formTable">特征名称<font color="#FF0000">＊</font></td>
										<td align="left">
											<html:text property="featureName" maxlength="20" style="background-color: #F0F0F0"/>
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
		
		
		<html:javascript formName="featureForm" staticJavascript="false" dynamicJavascript="true" cdata="false" />
		<script type="text/javascript" src="${ctx}/scripts/validator.jsp"></script>
	</body>
</html>
	
