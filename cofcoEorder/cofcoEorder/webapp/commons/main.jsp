<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
<head>
	<title></title>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/stylecss.css"/>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title></title>
<link href="../style/stylecss.css" rel="stylesheet" type="text/css">
<script type="text/JavaScript">
<!--
function MM_openBrWindow(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
}
function WindowSollAuto(minusWidth) {
	var myWidth = 0, myHeight = 0;
	
	if( typeof( window.innerWidth ) == 'number' ) {
		//Non-IE
		myWidth = window.innerWidth;
		myHeight = window.innerHeight;
		
	} else if( document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight ) ) {
		//IE 6+ in 'standards compliant mode'
		myWidth = document.documentElement.clientWidth;
		myHeight = document.documentElement.clientHeight;
		
	} else if( document.body && ( document.body.clientWidth || document.body.clientHeight ) ) {
		//IE 4 compatible
		myWidth = document.body.clientWidth;
		myHeight = document.body.clientHeight;
		
	}
	document.getElementById("SollAuto").style.height = myHeight-minusWidth+'px';	
	document.getElementById("homeOne").style.height = document.getElementById("homeTwo").style.height = myHeight-minusWidth-5+'px';
	return([myWidth,myHeight]);
}
//-->
</script>
</head>
<body onresize="WindowSollAuto(50)" onload="WindowSollAuto(50);">
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="300" height="100%" valign="top"><!--middle left-right -->
      <div class="hit_side"  id="hit_side_left">
        <h5><span>业务数据窗</span></h5>
        <div class="side_our" id="homeOne">
          <h3>MTD 销量 <a href="#"><img src="../images/panter_14.gif" border="0" /></a> 12.5%</h3>
          <b><img src="../images/Luck/Luck_4.jpg" />
          <img src="../images/Luck/Luck_1.jpg" />
          <img src="../images/Luck/Luck_2.jpg" />
          <img src="../images/Luck/Luck_dot.jpg" />
          <img src="../images/Luck/Luck_3.jpg" />              
          <img src="../images/Luck/Luck_4.jpg" />
          <img src="../images/Luck/Luck_6.jpg" />
          <img src="../images/Luck/Luck_dot.jpg" />
          <img src="../images/Luck/Luck_7.jpg" />
          <img src="../images/Luck/Luck_8.jpg" />
          <img src="../images/Luck/Luck_9.jpg" />
          </b>
          <h3>YTD 销量 <a href="#"><img src="../images/panter_23.gif" border="0" /></a> 11.5%</h3>
          <b>
              <img src="../images/Luck/Luck_9.jpg" />
              <img src="../images/Luck/Luck_1.jpg" />
              <img src="../images/Luck/Luck_2.jpg" />
              <img src="../images/Luck/Luck_dot.jpg" />
              <img src="../images/Luck/Luck_3.jpg" />
              <img src="../images/Luck/Luck_4.jpg" />
              <img src="../images/Luck/Luck_6.jpg" />
              <img src="../images/Luck/Luck_dot.jpg" />
              <img src="../images/Luck/Luck_7.jpg" />
              <img src="../images/Luck/Luck_8.jpg" />
              <img src="../images/Luck/Luck_8.jpg" />
          </b>
          <h3>销售团队工作状况</h3>
          <div class="side_list">
            <marquee onmouseover="this.stop()" onmouseout="this.start()" height="150" scrollamount="1" scrolldelay="50" direction="up">
            <ul>
              <li class="side_title">North</li>
              <li>No.of SR in field <span>230</span></li>
              <li>No.of call planed	<span>450</span></li>
              <li>No.of call actual	<span>602</span></li>
              <li class="side_title">East</li>
              <li>No.of SR in field <span>586</span></li>
              <li>No.of call planed <span>457</span></li>
              <li>No.of call actual <span>908</span></li>
              <li class="side_title">South</li>
              <li>No.of SR in field <span>458</span></li>
              <li>No.of call planed <span>869</span></li>
              <li>No.of call actual <span>684</span></li>
              <li class="side_title">West</li>
              <li>No.of SR in field <span>285</span></li>
              <li>No.of call planed <span>658</span></li>
              <li>No.of call actual <span>785</span></li>
            </ul>
             
            </marquee>
          </div>
        </div>
        <dl>
        </dl>
      </div>
      <!--middle left-right -->
        <!--company info-->
    <!--company info--></td>
    <td height="100%" valign="top"><div class="hit_liner">
	<div id="top"><span id="top_left"></span><span id="top_right"></span></div>
          
        <div class="line_side">
          <h2>公司信息</h2>
          <div class="ScrollCon" id="SollAuto">
            <ul>
              <li><a href="javascript:MM_openBrWindow('ListDetail.html','_blank','scrollbars=yes,resizable=yes,width=600,height=436')">赢销通公司计划收购汇源果汁集团<img src="../images/new.gif" width="19" height="9" /></a><span>2008-12-20</span></li>
              <li class="line_span"><a href="javascript:MM_openBrWindow('ListDetail.html','_blank','scrollbars=yes,resizable=yes,width=600,height=436')">赢销通奥运畅爽地带奥运群星闪耀<img src="../images/new.gif" width="19" height="9" border="0" /></a><span>2008-12-20</span></li>
              <li><a href="javascript:MM_openBrWindow('ListDetail.html','_blank','scrollbars=yes,resizable=yes,width=600,height=436')">姚明、詹姆斯聚首赢销通奥运畅爽地带<img src="../images/new.gif" width="19" height="9" border="0" /></a><span>2008-12-20</span></li>
              <li class="line_span">
              <a href="javascript:MM_openBrWindow('ListDetail.html','_blank','scrollbars=yes,resizable=yes,width=600,height=436')">奥林匹克教育家裴东光荣获赢销通公司<img src="../images/new.gif" width="19" height="9" border="0" /></a><span>2008-12-20</span>
              
              </li>
              <li><a href="javascript:MM_openBrWindow('ListDetail.html','_blank','scrollbars=yes,resizable=yes,width=600,height=436')">赢销通与篮球</a><span>2008-12-20</span></li>
              <li class="line_span"><a href="javascript:MM_openBrWindow('ListDetail.html','_blank','scrollbars=yes,resizable=yes,width=600,height=436')">赢销通与一级方程式赛车</a><span>2008-12-20</span></li>
              <li><a href="javascript:MM_openBrWindow('ListDetail.html','_blank','scrollbars=yes,resizable=yes,width=600,height=436')">赢销通东西群音绘八方音乐创作者访谈</a><span>2008-12-20</span></li>
              <li class="line_span"><a href="javascript:MM_openBrWindow('ListDetail.html','_blank','scrollbars=yes,resizable=yes,width=600,height=436')">赢销通东西群音绘八方设计者、音乐创作简介</a><span>2008-12-20</span></li>
              <li><a href="javascript:MM_openBrWindow('ListDetail.html','_blank','scrollbars=yes,resizable=yes,width=600,height=436')">纪念章交换中心说明</a><span>2008-12-20</span></li>
              <li class="line_span"><a href="javascript:MM_openBrWindow('ListDetail.html','_blank','scrollbars=yes,resizable=yes,width=600,height=436')">赢销通纪念章与北京2008奥运会　</a><span>2008-12-20</span></li>
              <li><a href="javascript:MM_openBrWindow('ListDetail.html','_blank','scrollbars=yes,resizable=yes,width=600,height=436')">赢销通与2008北京奥运会火炬接力</a><span>2008-12-20</span></li>
              <li class="line_span"><a href="javascript:MM_openBrWindow('ListDetail.html','_blank','scrollbars=yes,resizable=yes,width=600,height=436')">赢销通纪念章与北京2008奥运会　</a><span>2008-12-20</span></li>
              
              <li style="background-image: none"><a href="ListMore.html" target="main" style="float:right; padding-right:10px;">更多信息...</a></li>
            </ul>
          
          <ul>
            <h2 class="line_span">待办事项</h2>
          </ul>
          <ul>
            <li class="marks"><a href="#">新增售点申请</a>(17)</li>
            <li class="marks" style="background-color:#fff" ><a href="#">售点信息修改申请</a>(15)</li>
          </ul>
		  </div>
        </div>
            <div id="bottom"><span id="bottom_left"></span><span id="bottom_right"></span></div>
</div>
</td>
    <td width="300" height="100%" valign="top"><div id="hit_side_right" class="hit_side">
      <h5><span>促销信息</span></h5>
      <div class="side_our side_right" id="homeTwo">
        <h3><a href="javascript:MM_openBrWindow('ListDetail.html','_blank','scrollbars=yes,resizable=yes,width=600,height=436')">“零度赢销通”北京赠饮活动</a></h3>
        <p>
        	活动区域：北京<br />
          4月28日起，在北京部分高校、写字楼、电影院等地，您将有机会免费抢先体验[零度赢销通]，感受来自“零度依然赢销通”的极感受来自“零度依然赢销通”的极感受来自“</p>
        <h6><a href="ListMore.html" target="main">更多信息...</a></h6>
        <h3><a href="javascript:MM_openBrWindow('ListDetail.html','_blank','scrollbars=yes,resizable=yes,width=600,height=436')">“零度赢销通”北京赠饮活动</a></h3>
        <p>（2008年8月23日，中国，北京）今天，群星云集赢销通奥运畅爽地带，点燃了北京奥运闭幕前最后的欢庆激情。杨威、程菲、邹凯、陈一冰、肖钦、王楠和张湘祥等本届奥运会冠军，今晚迎来最高潮时分。 </p>
        <h6><a href="ListMore.html" target="main">更多信息...</a></h6>
      </div>
      <dl>
      </dl>
    </div></td>
  </tr>
</table>
</body>
</html>