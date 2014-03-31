<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
<head>
<title>标题摘要</title>
<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
<link rel="stylesheet" type="text/css" href="${ctx}/styles/global.css">
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
	margin:50px auto;
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
	background:url(img/download_img.jpg) left top no-repeat;
	display:inline;
	float:right;
	clear:both;
	margin-top:10px;
	margin-right:10px;
	text-indent:-9999px;
	}
.download_img:hover{
	background:url(img/download_img_onhover.jpg) left top no-repeat;	
	}
.title_list{
	line-height:24px;
	list-style:none;
	font-size:12px;
	padding:0px;
	margin:0px;
	}
</style>
<script type="text/javascript">
//内容窗口
function openWindow(url, name, iWidth, iHeight) {
		var url; //转向网页的地址;
		var name; //网页名称，可为空;
		var iWidth; //弹出窗口的宽度;
		var iHeight; //弹出窗口的高度;
		var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
		var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
		window.open(url,name,'height='+iHeight+',,innerHeight='+iHeight+',width='+iWidth+',innerWidth='+iWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=yes,resizeable=no,location=no,status=no');
	}
	function openContent(newId) {
	openWindow("${ctx}/base/baseMessages.do?method=view&flag=0&newId="+newId, "", 600, 600);
	}
	function openOpnion(id){
		openWindow("${ctx}/base/baseMessages.do?method=view&flag=0&id="+id, "", 600, 600);	
	}
	</script>
</head>

<body>
	  <div class="open_window">
    	<ul class="title_list">
    	 <c:forEach items="${list}"  var="item">
                   <li><a  style="cursor:pointer;"  <c:choose> 
                   <c:when test="${state=='1'}"> onclick="openOpnion(${item.id})"</c:when> 
                   <c:otherwise>onclick="openContent(${item.id})"</c:otherwise></c:choose>
                   >${item.title}</a></li>  
                </c:forEach>
    		
        	
    	</ul>
    </div>
</body>
</html>