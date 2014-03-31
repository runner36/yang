<%@ page contentType="text/html;charset=UTF-8"
%><%@ include file="/commons/taglibs.jsp"%>
<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma", "no-cache"); 
response.setDateHeader("Expires",-1); 
%> 

		<script>
			var parentMenuName='';
			
			function opendiv(obj,luck, p1){
				window.open(p1, "main");
				$('a', $('.menu_top')).each(function(i,n){
					$(n).attr('class','');
				});
				$(obj).attr('class','over_menu');
				if(0==luck){
					$('#CloseSo a', $(window.frames['menu'].document)).attr('class','barbg');
					
					if ( !$.browser.msie || ($.browser.msie && $.browser.version>7)){
						$('.side').css('left','0px');
						$('.main').css('left',$('.side').width()+'px');
					}else{
						$('#CloseSo', $(window.frames['menu'].document)).css('left','auto');
						$('.side').css('width','200px');
					}
					$('.side').css('display','block');
				}else if(1==luck){
					$('.side').css('display','none');
					if ( !$.browser.msie || $.browser.version>7){
						$('.main').css('left','0px');
					}
				}
			}

			function showWaitToDealOrder(){
				$.ajax({
				       type: "POST",
				       url:  "${ctx}/order/orderMemberOrder.do?method=qryWaitToDealOrderCount",
				       async: false,
				       dataType: "HTML",
				       success: function(data){
				         if(data!='error'){
				        	 //$("<font>待处理订单数</font><b>&nbsp;:&nbsp; <font color=\"#FFFF00\">"+i+"</font></b>").appendTo("[id ='waitToDeal']");
				        	 document.getElementById("waitToDeal").innerHTML="待处理订单数<b>&nbsp;:&nbsp; <font color=\"#FFFF00\">"+data+"</font></b>";
				         }else{
				        	 document.getElementById("waitToDeal").innerHTML="获取待处理订单数失败...";
				         }
				       },
				       error: function(msg){ 
					   }
				    });					
				setTimeout("showWaitToDealOrder();",${waitToDealOrderCoutRefreshInterval});				
			}
			
			function invalidateOtherSession(){				
				$.ajax({
				       type: "POST",
				       url:  "${ctx}/base/invalidateSession.do?method=invalidateSession",
				       async: false,
				       dataType: "HTML",
				       success: function(data){		    	   
				       },
				       error: function(msg){
					   }
				    });
			}
			
			function multiMenuClick(dom, src, menuName){
				window.open(src, "menu");
				parentMenuName = menuName;
				opendiv(dom, 0, '${ctx}/commons/blank.jsp');
			}
			
			$(document).ready(function(){
				//$('#linker1').click();
				//$("#menuTop").hide();
				if("${_user.baseEmployee.baseDictItem.itemCode}"=="DS"&&"${order_agreementLog}"=="0"){
					$("#menuTop").hide();
					opendiv(this, 1, "${ctx}/order/orderAgreementLog.do?method=viewAgreementLog")
					return;
				}
				$('a',$('.menu_top')).eq(0).click();				
				if("${_user.baseEmployee.baseDictItem.itemCode}"=="OM"){
					//$("<font>待处理订单数</font><b>&nbsp;:&nbsp; <font color=\"#FFFF00\">9</font></b>").appendTo("[id ='waitToDeal']");
					showWaitToDealOrder();
				}
				
				if("${_userhaslogin}"=="true"){
					if(confirm("本用户已登录系统，您这是第二次登录，是否失效上次登录?")){
						invalidateOtherSession();
					}else{
						window.parent.location="${ctx}/logout.do";
					}
				}		
				
			});
			
			function getDateDisc(){
				var day="";
				var month="";
				var ampm="";
				var ampmhour="";
				var myweekday="";
				var year="";
				var mydate=new Date();
				myweekday=mydate.getDay();
				mymonth=mydate.getMonth()+1;
				myday=mydate.getDate();
				myyear=mydate.getYear();
				year=(myyear>200)? myyear : 1900+myyear;
				if(myweekday==0)
				weekday=" ${mr['.week.sunday']} ";
				else if(myweekday==1)
				weekday=" ${mr['.week.monday']} ";
				else if(myweekday==2)
				weekday=" ${mr['.week.tuesday']} ";
				else if(myweekday==3)
				weekday=" ${mr['.week.wednesday']} ";
				else if(myweekday==4)
				weekday=" ${mr['.week.thrusday']} ";
				else if(myweekday==5)
				weekday=" ${mr['.week.friday']} ";
				else if(myweekday==6)
				weekday=" ${mr['.week.saturday']} ";
				document.write(mymonth+"/"+myday+"/"+year+" "+weekday);
			}
		</script>
		<div class="header">
			<div class="head_left">
				<div class="head_che">
					<marquee width="300" scrolldelay="150">
						${_user.baseEmployee.empName} ${mr['page.customize.header.welcome']} <script type="text/JavaScript">getDateDisc();</script>
					</marquee>
				</div>
			</div>
			<div class="head_right"></div>
			<div class="head_menu">
				<a href="${ctx}/logout.do" target="_parent" onclick="return confirm('您正在使用系统，确认退出吗？');">${mr['page.customize.header.exit']}</a>
			</div>
		</div>
		<div class="head_wrap">
			<div class="menu_top" id="menuTop">
				<%-- <a href="#this" onclick="opendiv(this, 1, '${ctx}${_home}');" class="over_menu" id="linker1">${mr['page.customize.header.homePage']}</a>--%>
				<a href="#this" onclick="opendiv(this, 1, '${ctx}/base/baseMessages.do?method=showMess&first=1');" class="over_menu" id="linker1">${mr['page.customize.header.homePage']}</a> 
				<c:forEach items="${_rootmenus}" var="menu" varStatus="i">
					<a href="#this" onclick="javascript:multiMenuClick(this, '${ctx}/tree/baseTree.do?method=authMenu&parentMenuId=${menu.menuId}', '${menu.menuName}');">${menu.menuName}</a>
				</c:forEach>
			</div>
			<span>${mr['page.customize.header.userOrg']}&nbsp;<b>:</b>&nbsp;<c:if test="${_orgnames==null||_orgnames==''}">${mr['page.customize.header.unspecified']}</c:if>${_orgnames}&nbsp;&nbsp;&nbsp;&nbsp;${mr['page.customize.header.userRole']}&nbsp;<b>:</b>&nbsp;${_rolenames}</span>
			<span id="waitToDeal"></span>
		</div>
		