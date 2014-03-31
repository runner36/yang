<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
	<head>
		<title>${mr['.sys.displayName']}</title>
		<link href="${ctx}/styles/login_css.css" rel="stylesheet" type="text/css" />
		<script>
			<html:javascript dynamicJavascript="false" staticJavascript="true"/>
			function login() {
		var loginForm = document.forms.loginForm;

		if (validateLoginForm(loginForm)) {
			loginForm.submit();
		}
	}
	
	document.onkeydown = function(e) {
		var event = window.event || e;
		if (event.keyCode == 13) {
			login();
		}
	};
			function changePass() {
				window.showModalDialog("${ctx}/changePass.jsp", "", "dialogHeight:260px;dialogWidth:420px;scroll=no;help:no;status:no");
			}
		</script>
		
	</head>
	<body scroll="auto" id="LoginBody" onload="loginForm.userAccount.focus()">
		<html:form action="/login.do" method="post" onsubmit="return validateLoginForm(this)">
		<div id="wrap">
			<div id="login_wrap"><jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
				<ul id="TextUser">
					<li>用户名：</li>
					<li>密　码：</li>
					<li>验证码：</li>
				</ul>
				<ul>
					<li><html:text property="userAccount" value=""/></li>
					<li><html:password property="userPassword" value=""/></li>
					<li><html:text property="rand" style="width:55px; margin-right:4px;" maxlength="4"/>
						<img src="${ctx}/commons/image.jsp" align="absmiddle" alt="看不清，换一张" onclick="this.src='${ctx}/commons/image.jsp';loginForm.rand.focus()"/></li>
					<li class="login_on"><a href="#this" onclick="javascript:login()"></a></li>
				</ul>
			</div>

			<span id="epage_help">  <a href="javascript:changePass()">修改密码</a></span>
            
            <!--flash部分-->
			<div id="flash01">
				<embed src="images/lunkuo.swf" width="120" height="240" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" wmode="transparent"	menu="false"></embed>
			</div>
            
			<div id="flash02">
				<embed src="images/login.swf" width="150" height="149" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" wmode="transparent"	menu="false"></embed>
			</div>
            
			<div id="flash03">
				<embed src="images/tiao.swf" width="578" height="40" quality="high"	pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" wmode="transparent"	menu="false"></embed>
			</div>
            
			<div id="flash04">
				<embed src="images/login.swf" width="105" height="104" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" wmode="transparent"	menu="false"></embed>
			</div>
            
			<div id="flash05">
				<embed src="images/jian.swf" width="105" height="104" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" wmode="transparent"	menu="false"></embed>
			</div>
		</div>
		</html:form>
		<html:javascript formName="loginForm" staticJavascript="false" dynamicJavascript="true" cdata="false"/>
	</body>
</html>
