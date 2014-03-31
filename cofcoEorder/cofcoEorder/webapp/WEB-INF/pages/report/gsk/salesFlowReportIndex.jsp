<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/commons/meta.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns:v="urn:schemas-microsoft-com:vml">
  <head>
    <base href="<%=basePath%>">
    
    <title>水单流向报告</title>
    <style>
	v\:* {behavior: url(#default#VML);}
	</style>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script src="<%=request.getContextPath() %>/scripts/jquery-1.2.6.min.js" type="text/javascript"></script>
	<style type="text/css">
	body, textarea, table, tr, td, input
	 { font-family: verdana, 宋体, Seoul, Gulim;
	  font-size: 9pt;
	  scrollbar-3dlight-color: 616161;
			scrollbar-arrow-color: 888888;
			scrollbar-base-color: CFCFCF;
			scrollbar-face-color: CFCFCF;
			scrollbar-highlight-color: ffffff;
			scrollbar-shadow-color: 595959;
			scrollbar-darkshadow-color: FFFFFF;  
	  letter-spacing: 1px;
	  word-break: break-all
	}
	a:link {
	 font-size: 9pt; COLOR: #000000; text-decoration: none
	}
	a:visited {
	 font-size: 9pt; COLOR: #000000; text-decoration: none
	}
	a:hover {
	 font-size: 9pt; COLOR: #808080
	}
	td {
	 font-size: 12px; font-family: MS Shell Dlg; word-break: break-all
	}
	.jun {
	 line-height: 18px;
	 font-family: "宋体";
	 font-size: 12px;
	}
	.ff{
	font-family: "宋体"; font-size: 12px; color: #FFFFFF; width: 300px; FILTER: glow(color=#333333,strength=1)
	}
	.titlediv {
	 font-family: "Verdana", "Arial", "Helvetica", "sans-serif";
	 font-size: 12px;
	 font-weight: normal;
	 word-break:break-all;
	 filter: Alpha(Opacity=80, FinishOpacity=30, Style=1, StartX=1, StartY=1, FinishX=100, FinishY=100)
	}
	.divshow {
	 display: block;
	 position: absolute;
	 width: 150px; z-index:1
	}
	.divhidden {
	 display: none;
	 position: absolute;
	 width: 150px; z-index: 1
	}
	</style>
	<script>
	function showdiv(div){
	 div.className='divshow';
	 div.style.left=event.x+document.body.scrollLeft-div.width+225;
	 div.style.top=event.y+document.body.scrollTop+30;
	 }
	 function showdiv2(div){
	 div.className='divshow';
	 div.style.left=event.x+document.body.scrollLeft-div.width-5;
	 div.style.top=event.y+document.body.scrollTop+150;
	 }
	 function showdiv3(div){
	 div.className='divshow';
	 div.style.left=event.x+document.body.scrollLeft-div.width-5;
	 div.style.top=event.y+document.body.scrollTop+260;
	 }
	 function showdiv4(div){
	 div.className='divshow';
	 div.style.left=event.x+document.body.scrollLeft-div.width-5;
	 div.style.top=event.y+document.body.scrollTop+360;
	 }
	 function showdiv5(div){
	 div.className='divshow';
	 div.style.left=event.x+document.body.scrollLeft-div.width-5;
	 div.style.top=event.y+document.body.scrollTop+30;
	 }
	 
	 $(document).ready(function(){
			$('#_content').height($('body').height()-15); 
			$('#_content').width($('body').width()-10); 
	 });
	 
	 window.onresize = function(event) {
		 	$('#_content').height($('body').height()-15); 
			$('#_content').width($('body').width()-10); 
	 };
	 
	</script>
  </head>
  <body onload="WindowSollAuto()">
  <jsp:include page="/commons/messages.jsp" flush="true"></jsp:include>
   <form name="salesFlowReportForm" action="" method="post" enctype="multipart/form-data">
   <v:group id="graph" name="graph" style="position:relative;top:0;left:0;width:1280;height:1024;display:inline" CoordSize="1280,1024" CoordOrig="0,0">
   <div id="_content" style="WIDTH: 100%; HEIGHT: 80%;OVERFLOW: scroll; scrollbar-3dlight-color:#595959; scrollbar-arrow-color:#FFFFFF; scrollbar-base-color:#CFCFCF; scrollbar-darkshadow-color:#FFFFFF; scrollbar-face-color:#CFCFCF; scrollbar-highlight-color:#FFFFFF; scrollbar-shadow-color:#595959">
	<v:rect onmousemove="javascript:showdiv(div137);" onmouseout="javascript:div137.className='divhidden';" id="vml1" style="z-index: 1; left: 450; width: 180; position: absolute;
        top: 50; height: 60" fillcolor="#ccddec" strokecolor="#ccddec" strokeweight="3px">
        <v:shadow on="true" type="single" color="#ABABAB" offset="2px,2px"/>
            <v:textbox style="font-size:12px">
            <div align="center"><b>一级经销商Sell Out</b><br/></div> 
            <div align="center">(${oneSellOut.salesOutAmount})</div> 
			</v:textbox>
    </v:rect>
	<v:line class="startLine" StrokeColor="#4F81BD" strokeweight="3px" from="525,50" to="525,140"/>
	<v:PolyLine filled="false" StrokeColor="#4F81BD"  strokeweight="3px" Points="525,140 360,140 360,200"/>

	<v:RoundRect onmousemove="javascript:showdiv(onefg);" onmouseout="javascript:onefg.className='divhidden';" style="z-index: 1; left: 260; width: 180; position: absolute;
        top: 180; height: 60" fillcolor="#aac7e1" strokecolor="#aac7e1" strokeweight="3px">
	<v:shadow on="true" type="single" color="#b3b3b3" offset="2px,2px"/>
	<v:textbox style="font-size:12px">
	<div align="center"><b>覆盖</b><br /></div>
	<div align="center">(${oneCoverOut.salesOutAmount })<br>  (${oneCoverOut.percentage})</div>
	</v:textbox>
	</v:RoundRect> 
	<v:PolyLine filled="false" StrokeColor="#4F81BD" strokeweight="3px" Points="525,140 750,140 750,200"/>
	<v:RoundRect onmousemove="javascript:showdiv5(onezr);" onmouseout="javascript:onezr.className='divhidden';" style="z-index: 1; left: 680; width: 180; position: absolute;
        top: 180; height: 60" fillcolor="#aac7e1" strokecolor="#aac7e1" strokeweight="3px">
	<v:shadow on="true" type="single" color="#b3b3b3" offset="2px,2px"/>
	<v:textbox style="font-size:12px">
	<div align="center"><b>自然分销</b><br /></div>
	<div align="center">(${oneNaturalOut.salesOutAmount }) <br> (${oneNaturalOut.percentage})</div>
	</v:textbox>
	</v:RoundRect>
	<v:PolyLine filled="false" StrokeColor="#4BABC5"  strokeweight="3px" Points="360,200 360,280 100,280 100,350"/>
	<v:RoundRect onmousemove="javascript:showdiv(twodistsj);" onmouseout="javascript:twodistsj.className='divhidden';" style="z-index: 1; left: 20; width: 180; position: absolute;
        top: 340; height: 60" fillcolor="#99bad9" strokecolor="#99bad9" strokeweight="3px">
	<v:shadow on="true" type="single" color="#b3b3b3" offset="2px,2px"/>
	<v:textbox style="font-size:12px">
	<div align="center"><b>二级经销商</b><br /></div>
	<div align="center">(${twoDistIn.salesOutAmount})<br> 
	<c:choose>
		<c:when test="${twoDistIn.percentage==null or twoDistIn.percentage==''}">(0.00%)</c:when>
		<c:otherwise>(${twoDistIn.percentage })</c:otherwise>
	</c:choose>
	</div>
	</v:textbox>
	</v:RoundRect>
	<v:PolyLine filled="false" StrokeColor="#8BB040" strokeweight="3px" Points="100,360 100,610 200,610"/>

	<v:line class="startLine" StrokeColor="#4BABC5" strokeweight="3px" from="400,280" to="400,350"/>
	<v:RoundRect onmousemove="javascript:showdiv5(onehm);" onmouseout="javascript:onehm.className='divhidden';" style="z-index: 1; left: 290; width: 120; position: absolute;
        top: 340; height: 60" fillcolor="#99bad9" strokecolor="#99bad9" strokeweight="3px">
	<v:shadow on="true" type="single" color="#b3b3b3" offset="2px,2px"/>
	<v:textbox style="font-size:12px">
	<div align="center"><b>传统渠道(GT)</b><br /></div>
	<div align="center">
	<c:choose>
		<c:when test="${oneChannel.HM.salesOutAmount==null or oneChannel.HM.salesOutAmount==''}">(0)</c:when>
		<c:otherwise>(${oneChannel.HM.salesOutAmount })</c:otherwise>
	</c:choose><br />
	<c:choose>
		<c:when test="${oneChannel.HM.percentage==null or oneChannel.HM.percentage==''}">(0.00%)</c:when>
		<c:otherwise>(${oneChannel.HM.percentage })</c:otherwise>
	</c:choose>
	</div>
	</v:textbox>
	</v:RoundRect>
	<v:line class="startLine" StrokeColor="#4BABC5" strokeweight="3px" from="520,280" to="520,350"/>
	<v:RoundRect onmousemove="javascript:showdiv5(onebs);" onmouseout="javascript:onebs.className='divhidden';" style="z-index: 1; left: 420; width: 120; position: absolute;
        top: 340; height: 60" fillcolor="#99bad9" strokecolor="#99bad9" strokeweight="3px">
	<v:shadow on="true" type="single" color="#b3b3b3" offset="2px,2px"/>
	<v:textbox style="font-size:12px">
	<div align="center"><b>现代渠道(MT)</b><br /></div>
	<div align="center">
	<c:choose>
		<c:when test="${oneChannel.BS.salesOutAmount==null or oneChannel.BS.salesOutAmount==''}">(0)</c:when>
		<c:otherwise>(${oneChannel.BS.salesOutAmount })</c:otherwise>
	</c:choose><br />
	<c:choose>
		<c:when test="${oneChannel.BS.percentage==null or oneChannel.BS.percentage==''}">(0.00%)</c:when>
		<c:otherwise>(${oneChannel.BS.percentage })</c:otherwise>
	</c:choose>
	</div>
	</v:textbox>
	</v:RoundRect>
	<v:line class="startLine" StrokeColor="#4BABC5" strokeweight="3px" from="630,280" to="630,350"/>
	<v:RoundRect onmousemove="javascript:showdiv5(oness);" onmouseout="javascript:oness.className='divhidden';" style="z-index: 1; left: 550; width: 110; position: absolute;
        top: 340; height: 60" fillcolor="#99bad9" strokecolor="#99bad9" strokeweight="3px">
	<v:shadow on="true" type="single" color="#b3b3b3" offset="2px,2px"/>
	<v:textbox style="font-size:12px">
	<div align="center"><b>学校渠道</b><br /></div>
	<div align="center">
	<c:choose>
		<c:when test="${oneChannel.SS.salesOutAmount==null or oneChannel.SS.salesOutAmount==''}">(0)</c:when>
		<c:otherwise>(${oneChannel.SS.salesOutAmount })</c:otherwise>
	</c:choose><br />
	<c:choose>
		<c:when test="${oneChannel.SS.percentage==null or oneChannel.SS.percentage==''}">(0.00%)</c:when>
		<c:otherwise>(${oneChannel.SS.percentage })</c:otherwise>
	</c:choose>
	</div>
	</v:textbox>
	</v:RoundRect>
	<v:line class="startLine" StrokeColor="#4BABC5" strokeweight="3px" from="750,280" to="750,350"/>
	<v:RoundRect onmousemove="javascript:showdiv5(onepcs);" onmouseout="javascript:onepcs.className='divhidden';" style="z-index: 1; left: 670; width: 110; position: absolute;
        top: 340; height: 60" fillcolor="#99bad9" strokecolor="#99bad9" strokeweight="3px">
	<v:shadow on="true" type="single" color="#b3b3b3" offset="2px,2px"/>
	<v:textbox style="font-size:12px">
	<div align="center"><b>网吧渠道</b><br /></div>
	<div align="center">
	<c:choose>
		<c:when test="${oneChannel.PCS.salesOutAmount==null or oneChannel.PCS.salesOutAmount==''}">(0)</c:when>
		<c:otherwise>(${oneChannel.PCS.salesOutAmount })</c:otherwise>
	</c:choose><br />
	<c:choose>
		<c:when test="${oneChannel.PCS.percentage==null or oneChannel.PCS.percentage==''}">(0.00%)</c:when>
		<c:otherwise>(${oneChannel.PCS.percentage })</c:otherwise>
	</c:choose>
	</div>
	</v:textbox>
	</v:RoundRect>
	<v:line class="startLine" StrokeColor="#4BABC5" strokeweight="3px" from="870,280" to="870,350"/>
	<v:PolyLine filled="false" StrokeColor="#4BABC5"  strokeweight="3px" Points="360,280 1240,280 1240,350"/>
	<v:RoundRect onmousemove="javascript:showdiv5(twobs);" onmouseout="javascript:twobs.className='divhidden';" style="z-index: 1; left: 790; width: 110; position: absolute;
        top: 340; height: 60" fillcolor="#99bad9" strokecolor="#99bad9" strokeweight="3px">
	<v:shadow on="true" type="single" color="#b3b3b3" offset="2px,2px"/>
	<v:textbox style="font-size:12px">
	<div align="center"><b>交通渠道</b><br /></div>
	<div align="center">
	<c:choose>
		<c:when test="${oneChannel.other.salesOutAmount==null or oneChannel.other.salesOutAmount==''}">(0)</c:when>
		<c:otherwise>(${oneChannel.other.salesOutAmount })</c:otherwise>
	</c:choose><br />
	<c:choose>
		<c:when test="${oneChannel.other.percentage==null or oneChannel.other.percentage==''}">(0.00%)</c:when>
		<c:otherwise>(${oneChannel.other.percentage })</c:otherwise>
	</c:choose>
	</div>
	</v:textbox>
	</v:RoundRect>
	
	<v:PolyLine filled="false" StrokeColor="#4BABC5"  strokeweight="3px" Points="880,280 1000,280 1000,350"/>
	<v:RoundRect onmousemove="javascript:showdiv5(twohm);" onmouseout="javascript:twohm.className='divhidden';" style="z-index: 1; left: 910; width: 110; position: absolute;
        top: 340; height: 60" fillcolor="#99bad9" strokecolor="#99bad9" strokeweight="3px">
	<v:shadow on="true" type="single" color="#b3b3b3" offset="2px,2px"/>
	<v:textbox style="font-size:12px">
	<div align="center"><b>餐饮娱乐渠道</b><br /></div>
	<div align="center">
	<c:choose>
		<c:when test="${oneChannel.CVS.salesOutAmount==null or oneChannel.CVS.salesOutAmount==''}">(0)</c:when>
		<c:otherwise>(${oneChannel.CVS.salesOutAmount })</c:otherwise>
	</c:choose><br />
	<c:choose>
		<c:when test="${oneChannel.CVS.percentage==null or oneChannel.CVS.percentage==''}">(0.00%)</c:when>
		<c:otherwise>(${oneChannel.CVS.percentage })</c:otherwise>
	</c:choose>
	</div>
	</v:textbox>
	</v:RoundRect>
	
	<v:line class="startLine" StrokeColor="#4BABC5" strokeweight="3px" from="1120,280" to="1120,350"/>
	<v:PolyLine filled="false" StrokeColor="#4BABC5"  strokeweight="3px" Points="880,280 1000,280 1000,350"/>
	<v:RoundRect onmousemove="javascript:showdiv5(twoss);" onmouseout="javascript:twoss.className='divhidden';" style="z-index: 1; left: 1030; width: 110; position: absolute;
        top: 340; height: 60" fillcolor="#99bad9" strokecolor="#99bad9" strokeweight="3px">
	<v:shadow on="true" type="single" color="#b3b3b3" offset="2px,2px"/>
	<v:textbox style="font-size:12px">
	<div align="center"><b>中间商</b><br /></div>
	<div align="center">
	<c:choose>
		<c:when test="${oneChannel.zjs.salesOutAmount==null or oneChannel.zjs.salesOutAmount==''}">(0)</c:when>
		<c:otherwise>(${oneChannel.zjs.salesOutAmount })</c:otherwise>
	</c:choose><br />
	<c:choose>
		<c:when test="${oneChannel.zjs.percentage==null or oneChannel.zjs.percentage==''}">(0.00%)</c:when>
		<c:otherwise>(${oneChannel.zjs.percentage })</c:otherwise>
	</c:choose>
	</div>
	</v:textbox>
	</v:RoundRect>
	
	<v:PolyLine filled="false" StrokeColor="#4BABC5"  strokeweight="3px" Points="880,280 1000,280 1000,350"/>
	<v:RoundRect onmousemove="javascript:showdiv5(twobs);" onmouseout="javascript:twobs.className='divhidden';" style="z-index: 1; left: 1150; width: 110; position: absolute;
        top: 340; height: 60" fillcolor="#99bad9" strokecolor="#99bad9" strokeweight="3px">
	<v:shadow on="true" type="single" color="#b3b3b3" offset="2px,2px"/>
	<v:textbox style="font-size:12px">
	<div align="center"><b>其他</b><br /></div>
	<div align="center">
	<c:choose>
		<c:when test="${oneChannel.qt.salesOutAmount==null or oneChannel.qt.salesOutAmount==''}">(0)</c:when>
		<c:otherwise>(${oneChannel.qt.salesOutAmount })</c:otherwise>
	</c:choose><br />
	<c:choose>
		<c:when test="${oneChannel.qt.percentage==null or oneChannel.qt.percentage==''}">(0.00%)</c:when>
		<c:otherwise>(${oneChannel.qt.percentage })</c:otherwise>
	</c:choose>
	</div>
	</v:textbox>
	</v:RoundRect>
	
	<v:line class="startLine" StrokeColor="#8BB040" strokeweight="3px" from="100,490" to="200,490"/>
	<v:RoundRect onmousemove="javascript:showdiv(twodistfg);" onmouseout="javascript:twodistfg.className='divhidden';" style="z-index: 1; left: 150; width: 120; position: absolute;
        top: 460; height: 65" fillcolor="#659dc5" strokecolor="#659dc5" strokeweight="3px">
	<v:shadow on="true" type="single" color="#b3b3b3" offset="2px,2px"/>
	<v:textbox style="font-size:12px">
	<div align="center"><b>覆盖</b><br /></div>
	<div align="center">(${twoDistCoverOut.salesOutAmount})<br /> (${twoDistCoverOut.percentage})</div>
	</v:textbox>
	</v:RoundRect>
	<v:PolyLine filled="false" StrokeColor="#C0504D" strokeweight="3px" Points="240,490 305,490 305,540 340,540"/>
	
	<v:PolyLine filled="false" StrokeColor="#C0504D" strokeweight="3px" Points="305,490 305,440 340,440"/>
	<v:RoundRect onmousemove="javascript:showdiv2(twohm);" onmouseout="javascript:twohm.className='divhidden';" style="z-index: 1; left: 340; width: 120; position: absolute;
        top: 510; height: 60" fillcolor="#3274b2" strokecolor="#3274b2" strokeweight="3px">
	<v:shadow on="true" type="single" color="#b3b3b3" offset="2px,2px"/>
	<v:textbox style="font-size:12px">
	<div align="center"><b>传统渠道(GT)</b><br /></div>
	<div align="center">
	<c:choose>
		<c:when test="${twoChannel.HM.salesOutAmount==null or twoChannel.HM.salesOutAmount==''}">(0)</c:when>
		<c:otherwise>(${twoChannel.HM.salesOutAmount })</c:otherwise>
	</c:choose><br />
	<c:choose>
		<c:when test="${twoChannel.HM.percentage==null or twoChannel.HM.percentage==''}">(0.00%)</c:when>
		<c:otherwise>(${twoChannel.HM.percentage })</c:otherwise>
	</c:choose>
	</div>
	</v:textbox>
	</v:RoundRect>
	
	<v:PolyLine filled="false" StrokeColor="#C0504D" strokeweight="3px" Points="305,540 305,630 340,630"/>
	<v:RoundRect onmousemove="javascript:showdiv2(twobs);" onmouseout="javascript:twobs.className='divhidden';" style="z-index: 1; left: 340; width: 120; position: absolute;
        top: 420; height: 60" fillcolor="#3274b2" strokecolor="#3274b2" strokeweight="3px">
	<v:shadow on="true" type="single" color="#b3b3b3" offset="2px,2px"/>
	<v:textbox style="font-size:12px">
	<div align="center"><b>现代渠道(MT)</b><br /></div>
	<div align="center">
	<c:choose>
		<c:when test="${twoChannel.BS.salesOutAmount==null or twoChannel.BS.salesOutAmount==''}">(0)</c:when>
		<c:otherwise>(${twoChannel.BS.salesOutAmount })</c:otherwise>
	</c:choose><br />
	<c:choose>
		<c:when test="${twoChannel.BS.percentage==null or twoChannel.BS.percentage==''}">(0.00%)</c:when>
		<c:otherwise>(${twoChannel.BS.percentage })</c:otherwise>
	</c:choose>
	</div>
	</v:textbox>
	</v:RoundRect>
	
	<v:PolyLine filled="false" StrokeColor="#C0504D" strokeweight="3px" Points="305,630 305,720 340,720"/>
	<v:RoundRect onmousemove="javascript:showdiv3(twoss);" onmouseout="javascript:twoss.className='divhidden';" style="z-index: 1; left: 340; width: 110; position: absolute;
        top: 600; height: 60" fillcolor="#3274b2" strokecolor="#3274b2" strokeweight="3px">
	<v:shadow on="true" type="single" color="#b3b3b3" offset="2px,2px"/>
	<v:textbox style="font-size:12px">
	<div align="center"><b>学校渠道</b><br /></div>
	<div align="center">
	<c:choose>
		<c:when test="${twoChannel.SS.salesOutAmount==null or twoChannel.SS.salesOutAmount==''}">(0)</c:when>
		<c:otherwise>(${twoChannel.SS.salesOutAmount })</c:otherwise>
	</c:choose><br />
	<c:choose>
		<c:when test="${twoChannel.SS.percentage==null or twoChannel.SS.percentage==''}">(0.00%)</c:when>
		<c:otherwise>(${twoChannel.SS.percentage })</c:otherwise>
	</c:choose>
	</div>
	</v:textbox>
	</v:RoundRect>
	
	<v:PolyLine filled="false" StrokeColor="#C0504D" strokeweight="3px" Points="305,720 305,810 340,810"/>
	<v:RoundRect onmousemove="javascript:showdiv3(twopcs);" onmouseout="javascript:twopcs.className='divhidden';" style="z-index: 1; left: 340; width: 110; position: absolute;
        top: 690; height: 60" fillcolor="#3274b2" strokecolor="#3274b2" strokeweight="3px">
	<v:shadow on="true" type="single" color="#b3b3b3" offset="2px,2px"/>
	<v:textbox style="font-size:12px">
	<div align="center"><b>网吧渠道</b><br /></div>
	<div align="center">
	<c:choose>
		<c:when test="${twoChannel.PCS.salesOutAmount==null or twoChannel.PCS.salesOutAmount==''}">(0)</c:when>
		<c:otherwise>(${twoChannel.PCS.salesOutAmount })</c:otherwise>
	</c:choose><br />
	<c:choose>
		<c:when test="${twoChannel.PCS.percentage==null or twoChannel.PCS.percentage==''}">(0.00%)</c:when>
		<c:otherwise>(${twoChannel.PCS.percentage })</c:otherwise>
	</c:choose>
	</div>
	</v:textbox>
	</v:RoundRect>
	
	<v:PolyLine filled="false" StrokeColor="#C0504D" strokeweight="3px" Points="305,810 305,900 340,900"/>
	<v:RoundRect onmousemove="javascript:showdiv4(twoothers);" onmouseout="javascript:twoothers.className='divhidden';" style="z-index: 1; left: 340; width: 110; position: absolute;
        top: 780; height: 60" fillcolor="#3274b2" strokecolor="#3274b2" strokeweight="3px">
	<v:shadow on="true" type="single" color="#b3b3b3" offset="2px,2px"/>
	<v:textbox style="font-size:12px">
	<div align="center"><b>交通渠道</b><br /></div>
	<div align="center">
	<c:choose>
		<c:when test="${twoChannel.others.salesOutAmount==null or twoChannel.others.salesOutAmount==''}">(0)</c:when>
		<c:otherwise>(${twoChannel.others.salesOutAmount })</c:otherwise>
	</c:choose><br />
	<c:choose>
		<c:when test="${twoChannel.others.percentage==null or twoChannel.others.percentage==''}">(0.00%)</c:when>
		<c:otherwise>(${twoChannel.others.percentage })</c:otherwise>
	</c:choose>
	</div>
	</v:textbox>
	</v:RoundRect>

	<v:PolyLine filled="false" StrokeColor="#C0504D" strokeweight="3px" Points="305,900 305,990 340,990"/>
	<v:RoundRect onmousemove="javascript:showdiv4(twoothers);" onmouseout="javascript:twoothers.className='divhidden';" style="z-index: 1; left: 340; width: 110; position: absolute;
        top: 870; height: 60" fillcolor="#3274b2" strokecolor="#3274b2" strokeweight="3px">
	<v:shadow on="true" type="single" color="#b3b3b3" offset="2px,2px"/>
	<v:textbox style="font-size:12px">
	<div align="center"><b>餐饮娱乐渠道</b><br /></div>
	<div align="center">
	<c:choose>
		<c:when test="${twoChannel.CVS.salesOutAmount==null or twoChannel.CVS.salesOutAmount==''}">(0)</c:when>
		<c:otherwise>(${twoChannel.CVS.salesOutAmount })</c:otherwise>
	</c:choose><br />
	<c:choose>
		<c:when test="${twoChannel.CVS.percentage==null or twoChannel.CVS.percentage==''}">(0.00%)</c:when>
		<c:otherwise>(${twoChannel.CVS.percentage })</c:otherwise>
	</c:choose>
	</div>
	</v:textbox>
	</v:RoundRect>
	
	<v:PolyLine filled="false" StrokeColor="#C0504D" strokeweight="3px" Points="305,810 305,900 340,900"/>
	<v:RoundRect onmousemove="javascript:showdiv4(twoothers);" onmouseout="javascript:twoothers.className='divhidden';" style="z-index: 1; left: 340; width: 110; position: absolute;
        top: 960; height: 60" fillcolor="#3274b2" strokecolor="#3274b2" strokeweight="3px">
	<v:shadow on="true" type="single" color="#b3b3b3" offset="2px,2px"/>
	<v:textbox style="font-size:12px">
	<div align="center"><b>中间商</b><br /></div>
	<div align="center">
	<c:choose>
		<c:when test="${twoChannel.zjs.salesOutAmount==null or twoChannel.zjs.salesOutAmount==''}">(0)</c:when>
		<c:otherwise>(${twoChannel.zjs.salesOutAmount })</c:otherwise>
	</c:choose><br />
	<c:choose>
		<c:when test="${twoChannel.zjs.percentage==null or twoChannel.zjs.percentage==''}">(0.00%)</c:when>
		<c:otherwise>(${twoChannel.zjs.percentage })</c:otherwise>
	</c:choose>
	</div>
	</v:textbox>
	</v:RoundRect>
	
	<v:PolyLine filled="false" StrokeColor="#C0504D" strokeweight="3px" Points="305,900 305,990 340,990"/>
	<v:PolyLine filled="false" StrokeColor="#C0504D" strokeweight="3px" Points="305,990 305,1080 340,1080"/>
	<v:RoundRect onmousemove="javascript:showdiv4(twocvs);" onmouseout="javascript:twocvs.className='divhidden';" style="z-index: 1; left: 340; width: 110; position: absolute;
        top: 1050; height: 60" fillcolor="#3274b2" strokecolor="#3274b2" strokeweight="3px">
	<v:shadow on="true" type="single" color="#b3b3b3" offset="2px,2px"/>
	<v:textbox style="font-size:12px">
	<div align="center"><b>其它</b><br /></div>
	<div align="center">
	<c:choose>
		<c:when test="${twoChannel.qt.salesOutAmount==null or twoChannel.qt.salesOutAmount==''}">(0)</c:when>
		<c:otherwise>(${twoChannel.qt.salesOutAmount })</c:otherwise>
	</c:choose><br />
	<c:choose>
		<c:when test="${twoChannel.qt.percentage==null or twoChannel.qt.percentage==''}">(0.00%)</c:when>
		<c:otherwise>(${twoChannel.qt.percentage })</c:otherwise>
	</c:choose>
	</div>
	</v:textbox>
	</v:RoundRect>

	<v:RoundRect onmousemove="javascript:showdiv2(twodistzrfx);" onmouseout="javascript:twodistzrfx.className='divhidden';" style="z-index: 1; left: 150; width: 120; position: absolute;
        top: 580; height: 65" fillcolor="#659dc5" strokecolor="#659dc5" strokeweight="3px">
	<v:shadow on="true" type="single" color="#b3b3b3" offset="2px,2px"/>
	<v:textbox style="font-size:12px">
	<div align="center"><b>自然分销</b><br /></div>
	<div align="center">(${twoDistNaturalOut.salesOutAmount})<br />  (${twoDistNaturalOut.percentage})</div>
	</v:textbox>
	</v:RoundRect>
	</v:group>
	</form>
	<div id="div137" class="divhidden" width="220">
	      <table width="220" border="1" cellpadding="5" cellspacing="0" bordercolor="#666666" bgcolor="#e9e9e9" class="titlediv" style="border-collapse:collapse">
		      <tr bgcolor="#f1f1f1">
		      		<td>
		      			渠道	
		      		</td>
		      		<td>
		      			报备家数
		      		</td>
		      		<td>
		      		   	活跃客户数 
		      		</td>
		      </tr>
		      <c:forEach items="${oneStore}" var="st">
		      <tr bgcolor="#f1f1f1">
		      		<td>
		      		${st.channelName}
		      		</td>
		      		<td>
		      		${st.storeCount}
		      		</td>
		      		<td>
		      		${st.activeCount}
		      		</td>
		      </tr>
		      </c:forEach>
   		   </table>
      </div>
      <div id="twodistsj" class="divhidden" width="220">
	      <table width="220" border="1" cellpadding="5" cellspacing="0" bordercolor="#666666" bgcolor="#e9e9e9" class="titlediv" style="border-collapse:collapse">
		      <tr bgcolor="#f1f1f1">
		      		<td>
		      			渠道	
		      		</td>
		      		<td>
		      			报备家数
		      		</td>
		      		<td>
		      		   	活跃客户数 
		      		</td>
		      </tr>
		      <c:forEach items="${two}" var="st">
		      <tr bgcolor="#f1f1f1">
		      		<td>
		      		${st.channelName}
		      		</td>
		      		<td>
		      		${st.storeCount}
		      		</td>
		      		<td>
		      		${st.activeCount}
		      		</td>
		      </tr>
		      </c:forEach>
   		   </table>
      </div>
      <div id="onefg" class="divhidden" width="220">
	      <table width="220" border="1" cellpadding="5" cellspacing="0" bordercolor="#666666" bgcolor="#e9e9e9" class="titlediv" style="border-collapse:collapse">
		      <tr bgcolor="#f1f1f1">
		     		<td>
		      			渠道	
		      		</td>
		      		<td>
		      			报备家数
		      		</td>
		      		<td>
		      		   	活跃客户数 
		      		</td>
		      </tr>
		      <c:forEach items="${oneCover}" var="st">
		      <tr bgcolor="#f1f1f1">
		            <td>
		      		${st.channelName}
		      		</td>
		      		<td>
		      		${st.storeCount}
		      		</td>
		      		<td>
		      		${st.activeCount}
		      		</td>
		      </tr>
		      </c:forEach>
   		   </table>
      </div>
      <div id="onezr" class="divhidden" width="100">
	      <table width="100" border="1" cellpadding="5" cellspacing="0" bordercolor="#666666" bgcolor="#e9e9e9" class="titlediv" style="border-collapse:collapse">
		      <tr bgcolor="#f1f1f1">
		      		<td>
		      		   	活跃客户数 
		      		</td>
		      </tr>
		      <c:forEach items="${oneNatural}" var="st">
		      <tr bgcolor="#f1f1f1">
		      		<td>
		      		${st.activeCount}
		      		</td>
		      </tr>
		      </c:forEach>
   		   </table>
      </div>
      
      <div id="twodist" class="divhidden" width="160">
	      <table width="160" border="1" cellpadding="5" cellspacing="0" bordercolor="#666666" bgcolor="#e9e9e9" class="titlediv" style="border-collapse:collapse">
		      <tr bgcolor="#f1f1f1">
		      		<td>
		      			报备家数
		      		</td>
		      		<td>
		      		   	活跃客户数 
		      		</td>
		      </tr>
		      <c:forEach items="${one二级经销商}" var="st">
		      <tr bgcolor="#f1f1f1">
		      		<td>
		      		${st.storeCount}
		      		</td>
		      		<td>
		      		${st.activeCount}
		      		</td>
		      </tr>
		      </c:forEach>
   		   </table>
      </div>
      
      <div id="twodistfg" class="divhidden" width="220">
	      <table width="220" border="1" cellpadding="5" cellspacing="0" bordercolor="#666666" bgcolor="#e9e9e9" class="titlediv" style="border-collapse:collapse">
		      <tr bgcolor="#f1f1f1">
		            <td>
		      			渠道	
		      		</td>
		      		<td>
		      			报备家数
		      		</td>
		      		<td>
		      		   	活跃客户数 
		      		</td>
		      </tr>
		      <c:forEach items="${twoCover}" var="st">
		      <tr bgcolor="#f1f1f1">
		       		<td>
		      		${st.channelName}
		      		</td>
		      		<td>
		      		${st.storeCount}
		      		</td>
		      		<td>
		      		${st.activeCount}
		      		</td>
		      </tr>
		      </c:forEach>
   		   </table>
      </div>
      
      <div id="twodistzrfx" class="divhidden" width="100">
	      <table width="100" border="1" cellpadding="5" cellspacing="0" bordercolor="#666666" bgcolor="#e9e9e9" class="titlediv" style="border-collapse:collapse">
		      <tr bgcolor="#f1f1f1">
		      		<td>
		      		   	活跃客户数 
		      		</td>
		      </tr>
		      <c:forEach items="${twoNatural}" var="st">
		      <tr bgcolor="#f1f1f1">
		      		<td>
		      		${st.activeCount}
		      		</td>
		      </tr>
		      </c:forEach>
   		   </table>
      </div>
       
     <div id="onehm" class="divhidden" width="160">
	      <table width="160" border="1" cellpadding="5" cellspacing="0" bordercolor="#666666" bgcolor="#e9e9e9" class="titlediv" style="border-collapse:collapse">
		      <tr bgcolor="#f1f1f1">
		      		<td>
		      			报备家数
		      		</td>
		      		<td>
		      		   	活跃客户数 
		      		</td>
		      </tr>
		      <c:forEach items="${oneHM}" var="st">
		      <tr bgcolor="#f1f1f1">
		      		<td>
		      		${st.storeCount}
		      		</td>
		      		<td>
		      		${st.activeCount}
		      		</td>
		      </tr>
		      </c:forEach>
   		   </table>
      </div>
      <div id="onebs" class="divhidden" width="160">
	      <table width="160" border="1" cellpadding="5" cellspacing="0" bordercolor="#666666" bgcolor="#e9e9e9" class="titlediv" style="border-collapse:collapse">
		      <tr bgcolor="#f1f1f1">
		      		<td>
		      			报备家数
		      		</td>
		      		<td>
		      		   	活跃客户数 
		      		</td>
		      </tr>
		      <c:forEach items="${oneBS}" var="st">
		      <tr bgcolor="#f1f1f1">
		      		<td>
		      		${st.storeCount}
		      		</td>
		      		<td>
		      		${st.activeCount}
		      		</td>
		      </tr>
		      </c:forEach>
   		   </table>
      </div>
      <div id="oness" class="divhidden" width="160">
	      <table width="160" border="1" cellpadding="5" cellspacing="0" bordercolor="#666666" bgcolor="#e9e9e9" class="titlediv" style="border-collapse:collapse">
		      <tr bgcolor="#f1f1f1">
		      		<td>
		      			报备家数
		      		</td>
		      		<td>
		      		   	活跃客户数 
		      		</td>
		      </tr>
		      <c:forEach items="${oneSS}" var="st">
		      <tr bgcolor="#f1f1f1">
		      		<td>
		      		${st.storeCount}
		      		</td>
		      		<td>
		      		${st.activeCount}
		      		</td>
		      </tr>
		      </c:forEach>
   		   </table>
      </div>
      <div id="onepcs" class="divhidden" width="160">
	      <table width="160" border="1" cellpadding="5" cellspacing="0" bordercolor="#666666" bgcolor="#e9e9e9" class="titlediv" style="border-collapse:collapse">
		      <tr bgcolor="#f1f1f1">
		      		<td>
		      			报备家数
		      		</td>
		      		<td>
		      		   	活跃客户数 
		      		</td>
		      </tr>
		      <c:forEach items="${onePCS}" var="st">
		      <tr bgcolor="#f1f1f1">
		      		<td>
		      		${st.storeCount}
		      		</td>
		      		<td>
		      		${st.activeCount}
		      		</td>
		      </tr>
		      </c:forEach>
   		   </table>
      </div>
      <div id="oneother" class="divhidden" width="160">
	      <table width="160" border="1" cellpadding="5" cellspacing="0" bordercolor="#666666" bgcolor="#e9e9e9" class="titlediv" style="border-collapse:collapse">
		      <tr bgcolor="#f1f1f1">
		      		<td>
		      			报备家数
		      		</td>
		      		<td>
		      		   	活跃客户数 
		      		</td>
		      </tr>
		      <c:forEach items="${oneothers}" var="st">
		      <tr bgcolor="#f1f1f1">
		      		<td>
		      		${st.storeCount}
		      		</td>
		      		<td>
		      		${st.activeCount}
		      		</td>
		      </tr>
		      </c:forEach>
   		   </table>
      </div>
      <div id="onecvs" class="divhidden" width="160">
	      <table width="160" border="1" cellpadding="5" cellspacing="0" bordercolor="#666666" bgcolor="#e9e9e9" class="titlediv" style="border-collapse:collapse">
		      <tr bgcolor="#f1f1f1">
		      		<td>
		      			报备家数
		      		</td>
		      		<td>
		      		   	活跃客户数 
		      		</td>
		      </tr>
		      <c:forEach items="${oneCVS}" var="st">
		      <tr bgcolor="#f1f1f1">
		      		<td>
		      		${st.storeCount}
		      		</td>
		      		<td>
		      		${st.activeCount}
		      		</td>
		      </tr>
		      </c:forEach>
   		   </table>
      </div>
      <div id="twohm" class="divhidden" width="160">
	      <table width="160" border="1" cellpadding="5" cellspacing="0" bordercolor="#666666" bgcolor="#e9e9e9" class="titlediv" style="border-collapse:collapse">
		      <tr bgcolor="#f1f1f1">
		      		<td>
		      			报备家数
		      		</td>
		      		<td>
		      		   	活跃客户数 
		      		</td>
		      </tr>
		      <c:forEach items="${twoHM}" var="st">
		      <tr bgcolor="#f1f1f1">
		      		<td>
		      		${st.storeCount}
		      		</td>
		      		<td>
		      		${st.activeCount}
		      		</td>
		      </tr>
		      </c:forEach>
   		   </table>
      </div>
      <div id="twobs" class="divhidden" width="160">
	      <table width="160" border="1" cellpadding="5" cellspacing="0" bordercolor="#666666" bgcolor="#e9e9e9" class="titlediv" style="border-collapse:collapse">
		      <tr bgcolor="#f1f1f1">
		      		<td>
		      			报备家数
		      		</td>
		      		<td>
		      		   	活跃客户数 
		      		</td>
		      </tr>
		      <c:forEach items="${twoBS}" var="st">
		      <tr bgcolor="#f1f1f1">
		      		<td>
		      		${st.storeCount}
		      		</td>
		      		<td>
		      		${st.activeCount}
		      		</td>
		      </tr>
		      </c:forEach>
   		   </table>
      </div>
      <div id="twoss" class="divhidden" width="160">
	      <table width="160" border="1" cellpadding="5" cellspacing="0" bordercolor="#666666" bgcolor="#e9e9e9" class="titlediv" style="border-collapse:collapse">
		      <tr bgcolor="#f1f1f1">
		      		<td>
		      			报备家数
		      		</td>
		      		<td>
		      		   	活跃客户数 
		      		</td>
		      </tr>
		      <c:forEach items="${twoSS}" var="st">
		      <tr bgcolor="#f1f1f1">
		      		<td>
		      		${st.storeCount}
		      		</td>
		      		<td>
		      		${st.activeCount}
		      		</td>
		      </tr>
		      </c:forEach>
   		   </table>
      </div>
      <div id="twopcs" class="divhidden" width="160">
	      <table width="160" border="1" cellpadding="5" cellspacing="0" bordercolor="#666666" bgcolor="#e9e9e9" class="titlediv" style="border-collapse:collapse">
		      <tr bgcolor="#f1f1f1">
		      		<td>
		      			报备家数
		      		</td>
		      		<td>
		      		   	活跃客户数 
		      		</td>
		      </tr>
		      <c:forEach items="${twoPCS}" var="st">
		      <tr bgcolor="#f1f1f1">
		      		<td>
		      		${st.storeCount}
		      		</td>
		      		<td>
		      		${st.activeCount}
		      		</td>
		      </tr>
		      </c:forEach>
   		   </table>
      </div>
      <div id="twoothers" class="divhidden" width="160">
	      <table width="160" border="1" cellpadding="5" cellspacing="0" bordercolor="#666666" bgcolor="#e9e9e9" class="titlediv" style="border-collapse:collapse">
		      <tr bgcolor="#f1f1f1">
		      		<td>
		      			报备家数
		      		</td>
		      		<td>
		      		   	活跃客户数 
		      		</td>
		      </tr>
		      <c:forEach items="${twoothers}" var="st">
		      <tr bgcolor="#f1f1f1">
		      		<td>
		      		${st.storeCount}
		      		</td>
		      		<td>
		      		${st.activeCount}
		      		</td>
		      </tr>
		      </c:forEach>
   		   </table>
      </div>
      <div id="twocvs" class="divhidden" width="160">
	      <table width="160" border="1" cellpadding="5" cellspacing="0" bordercolor="#666666" bgcolor="#e9e9e9" class="titlediv" style="border-collapse:collapse">
		      <tr bgcolor="#f1f1f1">
		      		<td>
		      			报备家数
		      		</td>
		      		<td>
		      		   	活跃客户数 
		      		</td>
		      </tr>
		      <c:forEach items="${twoCVS}" var="st">
		      <tr bgcolor="#f1f1f1">
		      		<td>
		      		${st.storeCount}
		      		</td>
		      		<td>
		      		${st.activeCount}
		      		</td>
		      </tr>
		      </c:forEach>
   		   </table>
      </div>
</body>
</html>
