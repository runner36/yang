//scrollAuto_Start
function WindowSollAuto(minusWidth) {
	if (!minusWidth) {
		minusWidth = 69;
	}
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
	return([myWidth,myHeight]);
}
//scrollAuto_End

//Right_Menu_SearchBut_Start
function showQuery(){
	var obj = document.getElementById("searchbox");
	obj.style.display="block";
}
function hideQuery(){
	var obj = document.getElementById("searchbox");
	obj.style.display="none";
}

function showQuery_(consoleElt){
	var obj = document.getElementById("searchbox");
	obj.style.display = "block";
	if(consoleElt == null)
		consoleElt = "query_A,queryXls_A";
	try{
		var elt = consoleElt.split(",");
		for(var i = 0; i < elt.length; i++){
			$$(elt[i]).style.display = "";
		}
		$$("hideQuery_A").style.display = "block";
		$$("showQuery_A").style.display = "none";
	}catch(e){}
}
function $$(id){
	return document.getElementById(id);
}
function hideQuery_(consoleElt){
	var obj = document.getElementById("searchbox");
	obj.style.display="none";
	if(consoleElt == null)
		consoleElt = "query_A,queryXls_A";
	try{
		var elt = consoleElt.split(",");
		for(var i = 0; i < elt.length; i++){
			$$(elt[i]).style.display = "none";
		}
		$$("hideQuery_A").style.display = "none";
		$$("showQuery_A").style.display = "block";
	}catch(e){}
}
//Right_Menu_SearchBut_End


//left_menu_Start
function menulink(ename,ynone){
	var name = ename.substring(0,ename.length-1);
	var name1 = ynone.substring(0,ynone.length-1);
	var obj = document.getElementById(ename);
	var objTwo = document.getElementById(ynone);
	obj.className="menu_title";
	objTwo.style.display = "block";
	for(var i = 1 ;i< 20 ;i++){
		var obj_ = document.getElementById(name+i);
		var objTwo_ = document.getElementById(name1+i);
		if(obj_ != null && obj != obj_)
			obj_.className="menu_gray";
		if(objTwo_ != null && objTwo != objTwo_)	
			objTwo_.style.display = "none"
	}
}

function alink(obj) {
	var Ptr = document.getElementById("SollAuto").getElementsByTagName("a") ;
	for (i=0;i<Ptr.length;i++)
	{
		Ptr[i].style.color = "";
	}
		obj.style.color = "red";
}
//left_menu_End

//Table_Newline_BgColor
function MetreNewline() {
	var Ptr=document.getElementById("ListAddPage").getElementsByTagName("tr");
    for (i=0;i<Ptr.length;i++) { 
    Ptr[i].className = (i%2>0)?"BgColorNonce":"BgColorMetre"; 
    Ptr[i].onmouseover=function(){this.tmpClass=this.className;this.className = "BgColorOver";};
    Ptr[i].onmouseout=function(){this.className=this.tmpClass;};
    }
}
//Table_Newline_BgColor
var cktdkey = 0;
function ckTd(td) {
	var tb = document.getElementById("ec_table");
	if(!tb){
		  tb = document.getElementById("ec_sale_table");
	}
	var tbrows = tb.rows.length;
	var ckidline = cktdkey;
	cktdkey = td.cellIndex;
	for (var j = 1; j < tbrows; j++) {
		for (var i = 0; i < tb.rows(j).cells.length; i++) {
			if (i <= td.cellIndex) {
				if (j == 1) {
					tb.rows(j).cells(i).className = "HLockedTh";
             		//alert(tb.rows(j).cells(i).colSpan);
				} else {
					tb.rows(j).cells(i).className = "HLocked";
				}
			} else {
				if (i > ckidline) {
					break;
				}
				if (j == 1) {
					tb.rows(j).cells(i).className = "tableHeader";
				} else {
					tb.rows(j).cells(i).className = "td";
				}
			}
		}
	}
}