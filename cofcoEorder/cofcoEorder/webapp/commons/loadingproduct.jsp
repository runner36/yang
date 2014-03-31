<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-store"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<%--<script src="<c:url value="/scripts/prototype.js"/>" type="text/javascript"></script>--%>

<style>
.loading_wrap{ width:100%; height:500px; position:absolute; z-index:10000;}
.loading_bg{ width:100%; height:100%; background:#fff; filter: Alpha(Opacity=50); }
.text_loading{width:150px; background:#fff; border: solid 1px #ccc; margin:180px 40%; padding:10px;position:absolute; z-index:10001;}
.text_cls{ color:#999; font-size:12px; text-align: center; padding-top:5px;}
.loading_cls{ background:url(${pageContext.request.contextPath}/images/loading.gif) no-repeat center; width:100px; height:9px; margin:0 auto;}
</style>
<script>
function loading() {
	loading_wrap.style.display='inline';
}

function loadingfinish() {
	loading_wrap.style.display='none';
}
</script>
<div class="loading_wrap" id="loading_wrap" style="display:none">
	<div class="text_loading">
		<div class="loading_cls"></div>
		<div class="text_cls">物料数据加载中...</div>
	</div>
	<div class="loading_bg"></div>
</div>
