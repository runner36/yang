<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>产品特征类别维护</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/dms.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/jquery-1.2.6.min.js"></script>
		<script>
		function save() {
			if(validateFeatureTypeForm(featureTypeForm))
			{
				var id=document.getElementsByName("id")[0];
		    	var typeName=document.getElementsByName("typeName")[0];
		    	var states=document.getElementsByName("state");
		    	var state='1';
		    	for(var i=0;i<states.length;i++){
		    		if(states[i].checked){
		    			state=states[i].value;
		    		}
		    	}
		    	if(state=='1'){
		    		$.ajax({
				       type: "POST",
				       url:  "${ctx}/server/productFeatureType.do?method=checkTypeName",
				       data: {	      
				    	   id:id.value,
				    	   typeName:typeName.value
				       },
				       async: false,
				       dataType: "HTML",
				       success: function(data){
				         if(data=='SUCCESS'){
				        	 featureTypeForm.submit();
				         }else{
							alert(data);
				         }
				       },
				       error: function(msg){ 
						 alert(msg);
					   }
				    });	
		    	}else{
		    		featureTypeForm.submit();
		    	}
			}
		}
		
		</script>
	</head>
	<body onload="WindowSollAuto()" onresize="WindowSollAuto()">
		<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		<html:form action="/server/productFeatureType.do?method=save" method="post">
			<html:hidden property="id" />
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						产品特征类别维护
					</h4>
					<div class='MenuList'>
						<a href="javascript:save();">保存</a>
						<a href="productFeatureType.do?method=list">取消</a>
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
											<html:text property="typeName" maxlength="20" style="background-color: #F0F0F0"/>
										</td>
										<td class="formTable"></td>
										<td align="left">
										</td>
									</tr>
								    <tr>
										<td class="formTable">是否有效<font color="#FF0000">＊</font></td>
										<td align="left">
											<html:radio property="state" styleClass="Choose_input" value="1"/>有效　
											<html:radio property="state" styleClass="Choose_input" value="0"/>无效
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
		
		
		<html:javascript formName="featureTypeForm" staticJavascript="false" dynamicJavascript="true" cdata="false" />
		<script type="text/javascript" src="${ctx}/scripts/validator.jsp"></script>
	</body>
</html>
	
