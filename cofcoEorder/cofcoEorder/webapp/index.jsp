<%@ page contentType="text/html;charset=UTF-8"
%>
<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma", "no-cache"); 
response.setDateHeader("Expires",-1); 
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${mr['page.common.sys.displayName']}</title>
<script language="JavaScript" src="scripts/jquery-1.2.6.min.js"></script>
<link href="styles/index.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div class="top"><jsp:include page="commons/header.jsp"></jsp:include>
	</div>
	<div class="side">
		<iframe name="menu" frameborder="0" scrolling="no"></iframe>
	</div>
	<div class="main">
		<iframe name="main" frameborder="0" scrolling="no"></iframe>
	</div>
	<div class="bottom"><jsp:include page="commons/footer.jsp"></jsp:include>
	</div>
</body>
</html>