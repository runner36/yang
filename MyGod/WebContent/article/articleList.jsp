<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/header.jsp"%>
<body>
ok ,article list~~~~!
       
<table>
<c:forEach var ="i" items="${list}" > 
	<tr>
		<td>${i.name}</td><td>${i.content}</td><td>${i.url}</td>
	</tr>
</c:forEach>  
</table>

</body>

</html>