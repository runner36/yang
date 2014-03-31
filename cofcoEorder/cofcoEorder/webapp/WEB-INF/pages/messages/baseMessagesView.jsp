<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<title>标题摘要</title>
<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
<script type="text/javascript"
	src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
<script language="javaScript" src="${ctx}/scripts/base.js"></script>
<style type="text/css" >

.open_window{
	width:580px;
	height:580px;
	font-size:14px;
	font-family:Arial, Helvetica, sans-serif;
	margin:0px auto;
	padding:10px;
	overflow:hidden;
	}
.open_window span{
	line-height:22px;
	color:#666;
	}
.open_window h3{
	font-weight:bold;
	font-size:16px;
	line-height:30px;
	text-align:center;
	text-indent:0px;
	}
.download_img{
	width:100px;
	height:30px;
	line-height:30px;
	background:url(${ctx}/images/download_img.jpg) left top no-repeat;
	display:inline;
	float:right;
	clear:both;
	margin-top:10px;
	margin-right:10px;
	text-indent:-9999px;
	}
.download_img:hover{
	background:url(${ctx}/images/download_img_onhover.jpg) left top no-repeat;	
	}
</style>
</head>
<body >
	<div class="open_window">
		 <c:forEach items="${list}" var="item">
             <h3>${item.title }</h3>
             <c:if test="${fn:length(item.fileurl)>0}">
                 <div><a class="download_img" style="cursor:pointer;"
                 <c:choose> 
                 <c:when test="${state=='0'}"> 
                 href="${ctx}/base/baseMessages.do?method=download&newId=${item.id}"
                 </c:when> 
                 <c:otherwise>
                 href="${ctx}/base/baseOpinions.do?method=download&id=${item.id}"
                 </c:otherwise>
                 </c:choose>
                  >附件下载</a></div>
             </c:if>
             <span>${item.content}</span>
	    </c:forEach>
    </div>
</body>
</html>
