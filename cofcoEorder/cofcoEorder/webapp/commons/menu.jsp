<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma", "no-cache"); 
response.setDateHeader("Expires",-1); 
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>menu</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/menucss.css"/>
	<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
	<script language="JavaScript" src="${ctx}/scripts/jquery-1.2.6.min.js"></script>
	<script>
		function switchSysBar(obj){
			if($(obj).attr('class')=='barbg'){
				$(obj).attr('class', 'barbg1');
				if ( !$.browser.msie || ($.browser.msie && $.browser.version>7)){
					$('.side', $(parent.document)).css('left','-194px');
					$('.main', $(parent.document)).css('left','6px');
				}else{
					$('.side', $(parent.document)).css('width','6px');
					$('#CloseSo').css('left','0px');
				}
			}else{
				$(obj).attr('class', 'barbg');
				if ( !$.browser.msie || $.browser.version>7){
					$('.side', $(parent.document)).css('left','0px');
					$('.main', $(parent.document)).css('left',$('.side', $(parent.document)).width()+'px');
				}else{
					$('.side', $(parent.document)).css('width','200px');
					$('#CloseSo').css('left','auto');
				}
			}
		}
		
		$(document).ready(function(){
			WindowSollAuto(62);
			$('#title').text(parent.parentMenuName);
			
			$(document).resize(function(){
				WindowSollAuto(62);
			}); 
		});
	</script>
</head>
<body>
	<div id="CloseSo">
		<a href="#this" onclick="switchSysBar(this);" class="barbg"></a>
	</div>
	<div class="menu_one">
		<div class="menu_top"><span class="left"></span><h4 id="title">${param.parentMenuName}</h4><span class="right"></span></div>
		<div class="menu_side" id="menutext">
			<div class="menuwrap" id="SollAuto">
			<c:forEach items="${menus}" var="menu" varStatus="i">
				<ul>
				  <li class="menu_gray" id="menubg${i.index+1}"><a href="javascript:menulink('menubg${i.index+1}','MenuPage_${i.index+1}')">${menu[0]}</a></li>
				</ul>
				<ul id="MenuPage_${i.index+1}">
					<c:forEach items="${menu[1]}" var="submenu" varStatus="i">
					<li><a href="${ctx}${submenu[1]}" onclick="alink(this)" target="main">${submenu[0]}</a></li>
					</c:forEach>
				</ul>
			</c:forEach>
			</div>
		</div>		
	    <div class="menu_bottom"><span class="left"></span><span class="right"></span></div>
	</div>
</body>
</html>
