<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="java.io.*" %>
<%@ include file="/commons/taglibs.jsp"%>
<%
	StringBuffer txt=new StringBuffer();
	InputStream is=null;
	BufferedReader br=null;
		try{
		 	 is=this.getClass().getResourceAsStream("/version.txt");  
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
		}
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
  </head>
  <body>
  
	<body>
			<div class="bosom_one">
				<div class="bosom_top">
					<span class="left"></span>
					<h4>
						${mr['page.customize.title.about']}
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
							<div class="sheet_div" >
									<%=txt.toString() %>		
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
	</body>
  	
</html>
