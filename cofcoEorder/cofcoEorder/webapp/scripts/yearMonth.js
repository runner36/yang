 document.writeln("<div   id='mydate'   style='position:absolute;   z-index:1;   visibility:   hidden;   filter:\"progid:DXImageTransform.Microsoft.Shadow(direction=135,color=#999999,strength=3)\"'></div>");   
  var b = null;
  function   showTable(InputBox)   
  {   
  var   x,y;   
  var   DivContent;   
  var   o=InputBox;
  b =InputBox.name;
  //显示的位置   
  x=o.offsetLeft;   
  y=o.offsetTop;   
  while(o=o.offsetParent)   
  {   
  x+=o.offsetLeft;   
  y+=o.offsetTop;   
  }   
  document.all.mydate.style.left=getXX_(InputBox);
  //document.all.mydate.style.left=x+2;  
  document.all.mydate.style.top=y+20;   
  document.all.mydate.style.visibility="visible";   
  //document.writeln("<div   style='display:none'   id='mydate'>");   
  DivContent=" <table   width=150   border='0'   cellspacing='0'   style='border:1px   solid   #999999;   background-color:#F7F7F7;filter:\"progid:DXImageTransform.Microsoft.Shadow(direction=135,color=#999999,strength=3)\"'>";   
  DivContent+=" <tr>";   
  DivContent+=" <td   colspan='3'   align='center'     style='border-bottom:1px   solid   #FF5555;   background-color:#E1E1E1  ;font-family:Verdana;   font-size:12px'>";   
  
  DivContent+=" 年份：<a  title='向前翻 1 年' onClick='minus()' ><strong>&lt;</strong></a><input   type='text'   name='selectYear'   size='4'   value='' ><a  title='向后翻 1 年' onClick='add()'><strong>&gt;</strong></a>";   
 
 
  DivContent+=" </td>";   
  DivContent+=" <td   style='border-bottom:1px   solid   #FF5555;   background-color:#E1E1E1;   font-weight:bold;      font-size:16px;   padding-top:2px;   color:#4477FF;   cursor:hand'   align='center'    title='关闭'   onClick='javascript:divClose()'>";   
  DivContent+=" <font color='FF5555'>X</font>";
  DivContent+=" </td>";   
  DivContent+=" </tr>";   
  DivContent+=" <tr>";   
  DivContent+=" <td   align='center'   onClick=\"setYear('01')\"   onmouseover=\"this.style.backgroundColor='#FF5555'\"   onmouseout=\"this.style.backgroundColor='#F7F7F7'\"     class='month'>1月</td>";   
  DivContent+=" <td   align='center'   onClick=\"setYear('02')\"   onmouseover=\"this.style.backgroundColor='#FF5555'\"   onmouseout=\"this.style.backgroundColor='#F7F7F7'\"     class='month'>2月</td>";   
  DivContent+=" <td   align='center'   onClick=\"setYear('03')\"   onmouseover=\"this.style.backgroundColor='#FF5555'\"     onmouseout=\"this.style.backgroundColor='#F7F7F7'\"     class='month'>3月</td>";   
  DivContent+=" <td   align='center'   onClick=\"setYear('04')\"   onmouseover=\"this.style.backgroundColor='#FF5555'\"   onmouseout=\"this.style.backgroundColor='#F7F7F7'\"     class='month'>4月</td>";   
  DivContent+=" </tr>";   
  DivContent+=" <tr>";   
  DivContent+=" <td   align='center'   onClick=\"setYear('05')\"   onmouseover=\"this.style.backgroundColor='#FF5555'\"   onmouseout=\"this.style.backgroundColor='#F7F7F7'\"   class='month'>5月</td>";   
  DivContent+=" <td   align='center'   onClick=\"setYear('06')\"   onmouseover=\"this.style.backgroundColor='#FF5555'\"   onmouseout=\"this.style.backgroundColor='#F7F7F7'\"     class='month'>6月</td>";   
  DivContent+=" <td   align='center'   onClick=\"setYear('07')\"   onmouseover=\"this.style.backgroundColor='#FF5555'\"   onmouseout=\"this.style.backgroundColor='#F7F7F7'\"   class='month'>7月</td>";   
  DivContent+=" <td   align='center'   onClick=\"setYear('08')\"   onmouseover=\"this.style.backgroundColor='#FF5555'\"   onmouseout=\"this.style.backgroundColor='#F7F7F7'\"   class='month'>8月</td>";   
  DivContent+=" </tr>";   
  DivContent+=" <tr>";   
  DivContent+=" <td   align='center'   onClick=\"setYear('09')\"   onmouseover=\"this.style.backgroundColor='#FF5555'\"   onmouseout=\"this.style.backgroundColor='#F7F7F7'\"   class='month'>9月</td>";   
  DivContent+=" <td   align='center'   onClick=\"setYear('10')\"   onmouseover=\"this.style.backgroundColor='#FF5555'\"   onmouseout=\"this.style.backgroundColor='#F7F7F7'\"   class='month'>10月</td>";   
  DivContent+=" <td   align='center'   onClick=\"setYear('11')\"   onmouseover=\"this.style.backgroundColor='#FF5555'\"   onmouseout=\"this.style.backgroundColor='#F7F7F7'\"   class='month'>11月</td>";   
  DivContent+=" <td   align='center'   onClick=\"setYear('12')\"   onmouseover=\"this.style.backgroundColor='#FF5555'\"   onmouseout=\"this.style.backgroundColor='#F7F7F7'\"   class='month'>12月</td>";   
  DivContent+=" </tr>";   
  DivContent+=" </table>";   
  //document.writeln("</div>");   
  document.all.mydate.innerHTML=DivContent;   
    
    
  var   divId=document.getElementById("mydate");   
  divId.style.display="block";   
  init();   
  }   
  function   show()   
  {   
  showTable();   
  var   divId=document.getElementById("mydate");   
  divId.style.display="block";   
  init();   
  }   
  function   init()   
  {   
        var   now   =   new   Date();   
        var   nowYear   =   now.getFullYear();   
        document.all.selectYear.value   =   nowYear;   
  }   
  function   minus()   
  {   
          var   year   =   parseInt(document.all.selectYear.value)-1;   
  if(year<1900)   
  {   
          alert("年份选择不合理");   
  return;   
  }   
  document.all.selectYear.value=year;   
  }   
  function   add()   
  {   
          var   year   =   parseInt(document.all.selectYear.value)+1;   
  if(year>2100)   
  {   
          alert("年份选择不合理");   
  return;   
  }   
  document.all.selectYear.value=year;   
  }   
  function   divClose()   
  {   
          document.getElementById("mydate").style.display="none";   
  }   
  function   setYear(year)   
  {         
        var y = document.all.selectYear.value;
        if (y.match(/\D/)!=null){alert("年份输入参数不是数字！");divClose();return;}      

        if(y<1900 || y>9999)   
           {
              alert("年份输入不合理"); 
              divClose();   
              return;   
             }  
           document.getElementById(b).value=document.all.selectYear.value+"-"+year;  
           divClose();   
  }   
  
  
  function getXX_(el){
	var x=0;
	for(var e=el; e; e=e.offsetParent)
		x+=e.offsetLeft;
	for(e=el.parentNode; e&&e!=document.body; e=e.parentNode)
		if(e.scrollLeft)
			x-=e.scrollLeft;
	return x;
}
