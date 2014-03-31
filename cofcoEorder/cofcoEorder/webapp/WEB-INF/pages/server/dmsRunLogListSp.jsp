<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>${mr['page.customize.title.runlogsp']}</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/widgets/extremecomponents/extremecomponents.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script language="javaScript" src="${ctx}/scripts/mdm.js"></script>
		<script language="javaScript" src="${ctx}/scripts/dms.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/DateControl.js"></script>
		<script>
	    function query() {
			resetec();
			var form = document.dmsRunLogForm;
			form.submit();
		}
		
		function detailQuery(){
			window.location.href="${ctx}/server/dmsRunLog.do?method=list&first=1";
			
		}
		function add() {
		    location='dmsRunLog.do?method=add';
		}
		function selectDist() {
			var form = document.dmsRunLogForm;
			var v = openDistTree('${ctx}', '', '1', form.distId.value);
			if (v) {
				form.distId.value = v.id;
				form.distName_.value = v.text;
			}
		}
		function selectOrg() {
			var form = document.dmsRunLogForm;
			form.orgSubCode.value = '';
			form.orgName_.value = '';
			var v = openOrgTree('${ctx}');
			if (v) {
				form.orgSubCode.value = v.subCode;
				form.orgName_.value = v.text;
			}
		}
		function viewMessage(logId) {
			window.showModelessDialog("dmsRunLog.do?method=viewMessage&logId=" + logId + "&rand=" + Math.random(), "", "dialogHeight:400px;dialogWidth:600px;scroll=yes;help:no;status:no");
		}
		function viewNoUpload() {
			window.showModelessDialog("dmsRunLog.do?method=viewNoUpload&rand=" + Math.random(), "", "dialogHeight:400px;dialogWidth:600px;scroll=yes;help:no;status:no");
		}
		function viewNoUpload3() {
			window.showModelessDialog("dmsRunLog.do?method=viewNoUpload3&rand=" + Math.random(), "", "dialogHeight:400px;dialogWidth:600px;scroll=yes;help:no;status:no");
		}
		
		function viewCurrentNoUpload() {
			window.showModelessDialog("dmsRunLog.do?method=viewCurrentNoUpload&rand=" + Math.random(), "", "dialogHeight:400px;dialogWidth:600px;scroll=yes;help:no;status:no");
		}			
		</script>
		<style>
			#fudongtag ul{
				list-style:none;
				padding:0px;
				margin: 0px;
			}
			#fudongtag ul li{
			line-height:20px;
			text-indent:0px;
			margin:0px;
			padding:0px;
			margin-top:2px;
			}
			.closediv{
			float:right;
			width:18px;
			height:15px;
			cursor:pointer;
			}
			
			#fudongtag{
			width:150px;
			hight:120px;
			position:absolute;
			background:#f7f6f5 url(../images/fudongdiv.jpg) repeat-x;
			display:block;
			border:1px solid #FF9900;
			bottom:1px;
			right:5px; 
			padding: 20px 3px 3px 3px;
			white-space:nowrap;
			/*filter:alpha(opacity=80, finishOpacity=160, style=1, startX=30); */  
			}
		</style>
	</head>
	<body onload="WindowSollAuto()">
		<jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
		
		
		
	</body>
</html>
