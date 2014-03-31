<%@ page language="java" contentType="text/html; charset=UTF-8"
	isErrorPage="true" pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	response.setStatus(HttpServletResponse.SC_OK);
%>
<html>
<head>
<title>404 ~ Page Not Found com</title>
</head>
<link rel="stylesheet" type="text/css" href="../styles/stylecss.css" />
<link rel="stylesheet" type="text/css" href="../styles/msg.css" />
<body>
	<center>
		<div style="margin-top: 120px;">
			<div id="msg" class="msg2" style="text-align: left;">
				<i><a href="#this"> </a> </i><span></span>
				<p>
				${mr['page.common.mess.urlInvalid']}……
				statusCode:<%=request.getAttribute("javax.servlet.error.status_code")%> <br/>
				message:<%=request.getAttribute("javax.servlet.error.message")%> 
				</p>
			</div>

		</div>
	</center>
</body>
</html>