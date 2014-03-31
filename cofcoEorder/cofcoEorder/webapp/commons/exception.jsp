<%@ page language="java" contentType="text/html; charset=UTF-8"
	isErrorPage="true" pageEncoding="UTF-8"%>
<%@ page import="org.apache.commons.logging.LogFactory"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="org.apache.struts.Globals" %>


<%
	response.setStatus(HttpServletResponse.SC_OK);
    System.out.println(request.getAttribute("org.apache.struts.action.EXCEPTION"));
%>
<html>
<head>
<title>Error Page</title>
</head>
<link rel="stylesheet" type="text/css" href="../styles/stylecss.css" />
<link rel="stylesheet" type="text/css" href="../styles/msg.css" />
<body>
	<center>
		<div  style="margin-top: 120px;">
			<div id="msg" class="msg5" style="text-align: left;">
			<i><a href="#this"> </a> </i><span></span>
			<p>${mr['page.common.mess.sysException']}：
				<c:if test="${requestScope['org.apache.struts.action.EXCEPTION'] != null}">
					<c:out
						value="${requestScope['org.apache.struts.action.EXCEPTION']}" />
				</c:if>
			</p>
		</div>
		</div>
</center>
</body>
</html>