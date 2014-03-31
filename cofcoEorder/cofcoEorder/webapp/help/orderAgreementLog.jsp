<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="java.io.*" %>
<%@ include file="/commons/taglibs.jsp"%>
<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma", "no-cache"); 
response.setDateHeader("Expires",-1); 
%> 
<%
	/* StringBuffer txt=new StringBuffer();
	InputStream is=null;
	BufferedReader br=null;
		try{
		 	 is=this.getClass().getResourceAsStream("/orderAgreementLog.txt");  
		 	if(is!=null)
		 	{
		         br=new BufferedReader(new InputStreamReader(is,"utf-8"));  
		        String s="";   
		        while((s=br.readLine())!=null)   
		            txt.append(s).append("<br />");   
	         }
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally{
		  	if(is!=null)
		  	{
		  		is.close();
		  	}
		  	if(br!=null)
		  	{
		  		br.close();
		  	}
		} */
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
	<script language="javaScript" src="${ctx}/scripts/jquery-1.2.6.min.js"></script>
	<script>
		function save() {
			if(document.getElementById("checkAgree").checked){
				$.ajax({
				       type: "POST",
				       url:  "${ctx}/order/orderAgreementLog.do?method=add",
				       data: {	      
				    	   flag:1
				       },
				       async: false,
				       dataType: "HTML",
				       success: function(data){
				         if(data=='SUCCESS'){
				        	 window.parent.location=window.parent.location;
				         }else{
							alert("补充协议签署失败！");
				         }
				       },
				       error: function(msg){ 
						 alert("补充协议签署失败！");
					   }
				    });
			}else{
				alert("请同意签署补充协议！");
			}
			
		}
		
		function cancel(){
			//orderAgreementLogForm.reset();
			document.getElementById("checkAgree").checked=false;
		}
		
		</script>
  </head>
  <body>
  
	<body>
		<%-- <form action="${ctx}/order/orderAgreementLog.do?method=add" method="get" id="orderAgreementLogForm"> --%>
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						客户补充协议签订
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
							<div class="sheet_div" style="padding-left:15%;padding-right: 15%">
								<table width="100%" border="0">
									<tr><td>&nbsp;</td>
									<tr align="center">
										<td align="center" width="100%"><p style="color:#000066;font-size:100px;"><b>客户补充协议签订</b></p></td>
									</tr>
									<tr><td>&nbsp;</td></tr>
								</table>
								<table width="100%" height="15%" border="1"  style="table-layout:fixed;">									
									<tr>
										<td height="230px" >
											<div id="divSubjects" style="overflow-y:auto;width:100%; height:230px;" >
                                    			${orderAgreementLogTxt}
                                			</div>                   
										</td>
									</tr>
								</table>
								<table width="100%" border="0">
									<tr><td>&nbsp;</td></tr>
									<tr align="center">
										<td align="left" width="100%"><input type="checkbox" value="1" name="checkAgree" id="checkAgree" />我同意签署上述补充协议</td>
									</tr>
									<tr>
									<td align="center" width="100%">
										<input type="button" style="width:50px;border:1px solid #FOFOFO" onclick="javascript:save();" value="确定"/>
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
		<!-- </form> -->
	</body>
  	
</html>
