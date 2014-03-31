<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
<head>
	<title></title>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles2/stylecss.css"/>
	<script language="javaScript" src="${ctx}/scripts/jquery-1.2.6.min.js"></script>
	<script type="text/JavaScript">
	function openWindow(url, name, iWidth, iHeight) {
		var url; //转向网页的地址;
		var name; //网页名称，可为空;
		var iWidth; //弹出窗口的宽度;
		var iHeight; //弹出窗口的高度;
		var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
		var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
		window.open(url,name,'height='+iHeight+',,innerHeight='+iHeight+',width='+iWidth+',innerWidth='+iWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=auto,resizeable=no,location=no,status=no');
	}
	function openView(messId) {
		return window.showModalDialog("${ctx}/server/dmsMessage.do?method=view&messId=" + messId, "", "dialogHeight:600px;dialogWidth:800px;scroll=no;help:no;status:no");
	}
	function openCorpMessList() {
		openWindow("${ctx}/server/dmsMessage.do?method=recList&first=1&messTypeCode=corp", "", 800, 600);
	}
	function openPromMessList() {
		openWindow("${ctx}/server/dmsMessage.do?method=recList&first=1&messTypeCode=prom", "", 800, 600);
	}
	function openMessList1(stateID) {
		//alert(stateID);
		//alert("${ctx}/business/bizMessage.do?method=recList&first=1&messType="+stateID);
		openWindow("${ctx}/server/dmsMessage.do?method=recListMoreInfo&first=1&messType="+stateID, "", 800, 600);
		//&$eq_baseDictItem_itemCode=prod
	}
	
	$(document).ready(function(){
		//$('#xml').show();
		$('#ScrollCon').height($('body').height()-45);
		$('#perc').height($('body').height()-212);
		$('#xml').focus();
		
		/**
		var im = $('<img/>');
		$(mtdDom).append(im.attr({src:'../images/Luck2/Luck_'+mtd.charAt(_i)+'.jpg',id:'_m'+_i}).css({position:'absolute',left:'21px',top:'1px'}));
		var leftPx = 97 + _i*20;
		$('#_m'+_i).animate({left:leftPx+'px'},200,splideRight);
		
		var im = $('<img/>');
		$(ytdDom).append(im.attr({src:'../images/Luck2/Luck_'+ytd.charAt(_j)+'.jpg',id:'_y'+_j}).css({position:'absolute',left:'21px',top:'1px'}));
		var leftPx = 77 + _j*20;
		$('#_y'+_j).animate({left:leftPx+'px'},200,splideRight1);
		**/
		
		
		//blink($('#_mtd_4'),1);
		//blink($('#_mtd_5'),1);
		//blink($('#_mtd_6'),3);
		//blink($('#_mtd_7'),5);
		//blink($('#_mtd_8'),4);
		//blink($('#_mtd_9'),7);
		//blink($('#_mtd_10'),8);
		//blink($('#_mtd_11'),2);
		
		$('#_mtd_div').animate({left:31},2000);
		$('#_ytd_div').animate({left:21},2000);
	});
	
	/**
	var mtd = '11354782';
	var ytd = '956389386';
	var mtdDom = '#mtd';
	var ytdDom = '#ytd';
	var _i = 7;
	var _j = 8;
	
	function splideRight(){
		_i--;
		if(_i<0){
			return;
		}
		var im = $('<img/>');
		$(mtdDom).append(im.attr({src:'../images/Luck2/Luck_'+mtd.charAt(_i)+'.jpg',id:'_m'+_i}).css({position:'absolute',left:'21px',top:'1px'}));
		var leftPx = 97 + _i*20;
		$('#_m'+_i).animate({left:leftPx+'px'},200,splideRight);
		
	}
	function splideRight1(){
		_j--;
		if(_j<0){
			return;
		}
		var im = $('<img/>');
		$(ytdDom).append(im.attr({src:'../images/Luck2/Luck_'+ytd.charAt(_j)+'.jpg',id:'_y'+_j}).css({position:'absolute',left:'21px',top:'1px'}));
		var leftPx = 77 + _j*20;
		$('#_y'+_j).animate({left:leftPx+'px'},200,splideRight1);
		
	}
	**/
	
	function blink(dom,num){
		$('#_in',dom).animate({top:-470},Math.random()*2000,function(){
			$('#_in',dom).css('top','0px');
			$('#_in',dom).animate({top:-47*num},Math.random()*2000);
		});
		
	}
	</script>
</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="300" valign="top"><!--middle left-right -->
      <div class="hit_side"  id="hit_side_left">
        <h5><span>进货数据窗</span></h5>
        <div class="side_our">
          <h3>MTD 销量</h3>
          <b id="mtd" style="position:relative;overflow:hidden">
          
	          	<img id="_mtd_div" style="left:203px" height="47" width="180" src="../images/Luck2/mtd.jpg"/>
          
          <!-- 
          	<div id="_mtd_2" style="position:absolute;left:34px;top:1px;overflow:hidden;height:47px;line-height:47px">
	          	<img id="_in" height="517" src="../images/Luck2/Luck_all.jpg" style="top:0px"/>
          	</div>
          	<div style="position:absolute;left:54px;top:1px;overflow:hidden;height:47px;line-height:47px">
	          	<img id="_in" height="47" src="../images/Luck2/Luck_dot.jpg" style="top:0px"/>
          	</div>
          	<div id="_mtd_3" style="position:absolute;left:62px;top:1px;overflow:hidden;height:47px;line-height:47px">
	          	<img id="_in" height="517" src="../images/Luck2/Luck_all.jpg" style="top:0px"/>
          	</div>
          	<div id="_mtd_4" style="position:absolute;left:82px;top:1px;overflow:hidden;height:47px;line-height:47px">
	          	<img id="_in" height="517" src="../images/Luck2/Luck_all.jpg" style="top:0px"/>
          	</div>
          	<div id="_mtd_5" style="position:absolute;left:102px;top:1px;overflow:hidden;height:47px;line-height:47px">
	          	<img id="_in" height="517" src="../images/Luck2/Luck_all.jpg" style="top:0px"/>
          	</div>
          	<div style="position:absolute;left:122px;top:1px;overflow:hidden;height:47px;line-height:47px">
	          	<img id="_in" height="47" src="../images/Luck2/Luck_dot.jpg" style="top:0px"/>
          	</div>
          	<div id="_mtd_6" style="position:absolute;left:130px;top:1px;overflow:hidden;height:47px;line-height:47px">
	          	<img id="_in" height="517" src="../images/Luck2/Luck_all.jpg" style="top:0px"/>
          	</div>
          	<div id="_mtd_7" style="position:absolute;left:150px;top:1px;overflow:hidden;height:47px;line-height:47px">
	          	<img id="_in" height="517" src="../images/Luck2/Luck_all.jpg" style="top:0px"/>
          	</div>
          	<div id="_mtd_8" style="position:absolute;left:170px;top:1px;overflow:hidden;height:47px;line-height:47px">
	          	<img id="_in" height="517" src="../images/Luck2/Luck_all.jpg" style="top:0px"/>
          	</div>
          	<div style="position:absolute;left:190px;top:1px;overflow:hidden;height:47px;line-height:47px">
	          	<img id="_in" height="47" src="../images/Luck2/Luck_dot.jpg" style="top:0px"/>
          	</div>
          	<div id="_mtd_9" style="position:absolute;left:198px;top:1px;overflow:hidden;height:47px;line-height:47px">
	          	<img id="_in" height="517" src="../images/Luck2/Luck_all.jpg" style="top:0px"/>
          	</div>
          	<div id="_mtd_10" style="position:absolute;left:218px;top:1px;overflow:hidden;height:47px;line-height:47px">
	          	<img id="_in" height="517" src="../images/Luck2/Luck_all.jpg" style="top:0px"/>
          	</div>
          	<div id="_mtd_11" style="position:absolute;left:238px;top:1px;overflow:hidden;height:47px;line-height:47px">
	          	<img id="_in" height="517" src="../images/Luck2/Luck_all.jpg" style="top:0px"/>
          	</div>
			 -->
          </b>
          <h3>YTD 销量</h3>
          <b id="ytd" style="position:relative;overflow:hidden">
          	<img id="_ytd_div" style="left:213px" height="47" width="200" src="../images/Luck2/ytd.jpg"/>
          </b>
          <h3></h3>
          <div>
  	<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
			id="perc" width="100%"
			codebase="http://fpdownload.macromedia.com/get/flashplayer/current/swflash.cab">
			<param name="movie" value="${ctx}/include/perc.swf" />
			<param name="quality" value="high" />
			<param name="bgcolor" value="#eeeeee" />
			<param name="allowScriptAccess" value="sameDomain" />
			<param name="flashVars" value="ctx=<%="http://" + request.getServerName()+":"+request.getServerPort() + "/"+ request.getContextPath()%>" />
			
			<embed src="${ctx}/include/perc.swf" quality="high" bgcolor="#869ca7"
				width="100%" height="100%" name="perc" align="middle"
				play="true"
				loop="false"
				quality="high"
				allowScriptAccess="sameDomain"
				type="application/x-shockwave-flash"
				pluginspage="http://www.adobe.com/go/getflashplayer">
			</embed>
	</object>
          </div>
        </div>
        <dl>
        </dl>
      </div>
      <!--middle left-right -->
        <!--company info-->
    <!--company info--></td>
    <td valign="top"><div class="hit_liner">
	<div id="top"><span id="top_left"></span><span id="top_right"></span></div>
          
        <div class="line_side">
          <h2>销售图表</h2>
          <div id="ScrollCon">
          	<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
			id="xml" width="100%" height="100%"
			codebase="http://fpdownload.macromedia.com/get/flashplayer/current/swflash.cab">
			<param name="movie" value="${ctx}/include/xml.swf" />
			<param name="quality" value="high" />
			<param name="bgcolor" value="#eeeeee" />
			<param name="allowScriptAccess" value="sameDomain" />
			<param name="wmode" value="transparent">
			 <param name="flashVars" value="ctx=<%="http://" + request.getServerName()+":"+request.getServerPort() + "/"+ request.getContextPath()%>" />
			<embed src="${ctx}/include/xml.swf" quality="high" bgcolor="#869ca7"
				width="100%" height="100%" name="xml" align="middle"
				play="true"
				loop="false"
				quality="high"
				allowScriptAccess="sameDomain"
				type="application/x-shockwave-flash"
				pluginspage="http://www.adobe.com/go/getflashplayer">
			</embed>
		</object>
          	
          </div>
        </div>
            <div id="bottom"><span id="bottom_left"></span><span id="bottom_right"></span></div>
</div>
</td>
  </tr>
</table>
</html>