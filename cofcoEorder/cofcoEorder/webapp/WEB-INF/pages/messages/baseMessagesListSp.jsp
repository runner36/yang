<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
<head>
<title></title>
<link href="${ctx}/styles/bulletin.css" rel="stylesheet"
	type="text/css">

<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
<script language="javaScript" src="${ctx}/scripts/base.js"></script>
<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>
<script language="javaScript" src="${ctx}/scripts/dms.js"></script>
<script type="text/javascript" src="${ctx}/widgets/My97DatePicker/WdatePicker.js"></script>
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
	function openOpinion(id) {
		openWindow("${ctx}/base/baseMessages.do?method=view&flag=0&id="+id, "", 600, 600);
		}
	function openAll(type) {
		openWindow("${ctx}/base/baseMessages.do?method=view&flag=1&type="+type, "", 500, 500);
		}
	function openAllOpinion() {
		openWindow("${ctx}/base/baseMessages.do?method=view&flag=2", "", 500, 500);
	}
	function opinionWrite(){
		openWindow("${ctx}/base/baseOpinions.do?method=list&first=1&flag=1", "", 900, 500);
	}
</script>
</head>
<body>
<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
	<html:form action="/base/baseOpinions.do?method=showMess" method="post">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr height="50%">
				<td valign="top"><!--part1-->
				      <div class="hit_liner">
				        	<div id="top"><span id="top_left"></span><span id="top_right"></span></div>
					        <div class="line_side">
						          <h2>公告</h2>
						          <span class="float_right"><a class="link_red"  href="javascript:openAll(0);">更多信息...</a></span>
						          <div class="clear"></div>
						          <ul>
						              <bean:define id="index" value="0"/>
						               <c:forEach items="${list}" var="item">   
						                    <c:if test="${item.type==0}">
							                     <c:set var="index" value="${index+1}"/>
							                 	 <c:if test="${index<=5}" >
							                  		<li class="content_01"><a class=""  href="javascript:openContent(${item.id});">${item .title}<img src="../images/new.gif" /></a><br />
							                     </li>
							                     </c:if>
						                     </c:if>
						               </c:forEach>
						          </ul>
						          <div class="clear"></div>
						    </div>
					        <div id="bottom"><span id="bottom_left"></span><span id="bottom_right"></span></div>
					 </div>
				     <div class="blank5"></div>
		      </td>
	  </tr>
	  		<tr height="50%">
				<td valign="top"><!--part1-->
				      <div class="hit_liner">
				        	<div id="top"><span id="top_left"></span><span id="top_right"></span></div>
					        <div class="line_side">
						          <h2>帮助</h2>
						          <span class="float_right"><a class="link_red"  href="javascript:openAll(1);">更多信息...</a></span>
						          <div class="clear"></div>
						          <ul>
						              <bean:define id="index" value="0"/>
						               <c:forEach items="${list}" var="item">   
						                    <c:if test="${item.type==1}">
							                     <c:set var="index" value="${index+1}"/>
							                 	 <c:if test="${index<=5}" >
							                  		<li class="content_01"><a class=""  href="javascript:openContent(${item.id});">${item .title}</a><br />
							                     </li>
							                     </c:if>
						                     </c:if>
						               </c:forEach>
						          </ul>
						          <div class="clear"></div>
						    </div>
					        <div id="bottom"><span id="bottom_left"></span><span id="bottom_right"></span></div>
					 </div>
				     <div class="blank5"></div>
		      </td>
	  </tr>
</table>
</html:form>
</html>
