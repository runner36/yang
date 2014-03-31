function resetec(form) {
	if (!form) {
		form = document.forms[0];
	}
	if (form.ec_eti) {
		form.ec_eti.value = '';
	}
}
//选择树参数说明
//ctx:
//values: 初始化checkbox树的值
//onlyLeaf: 仅选择叶子结点--- 0选择全部 1仅选择程序指定的叶子结点 2仅选择自然的叶子结点 
//selectType: 选择类型--- 0单选 1非级联多选 2级联多选

//组织树 For Eorder
function openOrgTreeForEorder(ctx, onlyLeaf, selectType, values,orgTreeType) {
	if (!onlyLeaf) onlyLeaf = '';
	if (!selectType) selectType = '';
	var obj = new Object();
	obj.values = values || "";
	return window.showModalDialog(ctx + "/tree/baseTree.do?method=orgTreeForEorder&title=" + encodeURI("选择组织") + "&state=1&first=1&onlyLeaf=" + onlyLeaf + "&checkbox=" + selectType + "&rand=" + Math.random()+"&orgTreeType="+orgTreeType, obj, "dialogHeight:420px;dialogWidth:420px;scroll=no;help:no;status:no");
}

//组织树
function openOrgTree(ctx, onlyLeaf, selectType, values) {
	if (!onlyLeaf) onlyLeaf = '';
	if (!selectType) selectType = '';
	var obj = new Object();
	obj.values = values || "";
	return window.showModalDialog(ctx + "/tree/baseTree.do?method=orgTree&title=" + encodeURI("选择组织") + "&state=1&first=1&onlyLeaf=" + onlyLeaf + "&checkbox=" + selectType + "&rand=" + Math.random(), obj, "dialogHeight:420px;dialogWidth:420px;scroll=no;help:no;status:no");
}

//历史组织树
function openHisOrgTree(ctx, onlyLeaf, selectType, values, date) {
	if (!onlyLeaf) onlyLeaf = '';
	if (!selectType) selectType = '';
	var obj = new Object();
	obj.values = values || "";
	return window.showModalDialog(ctx + "/tree/baseTree.do?method=hisOrgTree&title=" + encodeURI("选择组织") + "&$eq_state=1&first=1&date=" + date + "&onlyLeaf=" + onlyLeaf + "&checkbox=" + selectType + "&rand=" + Math.random(), obj, "dialogHeight:420px;dialogWidth:420px;scroll=no;help:no;status:no");
}

//人员树
function openEmpTree(ctx, onlyLeaf, selectType, values) {
	if (!onlyLeaf) onlyLeaf = '';
	if (!selectType) selectType = '';
	var obj = new Object();
	obj.values = values || "";
	return window.showModalDialog(ctx + "/tree/baseTree.do?method=empTree&title=" + encodeURI("选择人员") + "&state=1&first=1&onlyLeaf=" + onlyLeaf + "&checkbox=" + selectType + "&rand=" + Math.random(), obj, "dialogHeight:420px;dialogWidth:420px;scroll=no;help:no;status:no");
}
//人员业代树
function openEmpTreeSR(ctx, onlyLeaf, selectType, values) {
	if (!onlyLeaf) onlyLeaf = '';
	if (!selectType) selectType = '';
	var obj = new Object();
	obj.values = values || "";
	return window.showModalDialog(ctx + "/tree/baseTree.do?method=empTree&baseDictItem.itemCode=SR&title=" + encodeURI("选择业代人员") + "&state=1&first=1&onlyLeaf=" + onlyLeaf + "&checkbox=" + selectType + "&rand=" + Math.random(), obj, "dialogHeight:420px;dialogWidth:420px;scroll=no;help:no;status:no");
}
//人员订单员树
function openEmpTreeOM(ctx, onlyLeaf, selectType, values) {
	if (!onlyLeaf) onlyLeaf = '';
	if (!selectType) selectType = '';
	var obj = new Object();
	obj.values = values || "";
	return window.showModalDialog(ctx + "/tree/baseTree.do?method=empTree&baseDictItem.itemCode=OM&title=" + encodeURI("选择订单员") + "&state=1&first=1&onlyLeaf=" + onlyLeaf + "&checkbox=" + selectType + "&rand=" + Math.random(), obj, "dialogHeight:420px;dialogWidth:420px;scroll=no;help:no;status:no");
}

//人员列表
function openEmpList(ctx, selectType, values) {
	if (!selectType) selectType = '';
	var obj = new Object();
	obj.values = values || "";
	return window.showModalDialog(ctx + "/base/selectEmployee.do?method=list&$eq_state=1&first=1&checkbox=" + selectType + "&rand=" + Math.random(), obj, "dialogHeight:520px;dialogWidth:640px;scroll=no;help:no;status:no");
}

//资源树
function openResTree(ctx, onlyLeaf, selectType, values) {
	if (!onlyLeaf) onlyLeaf = '';
	if (!selectType) selectType = '';
	var obj = new Object();
	obj.values = values || "";
	return window.showModalDialog(ctx + "/tree/baseTree.do?method=resTree&title=" + encodeURI("选择资源") + "&state=1&first=1&onlyLeaf=" + onlyLeaf + "&checkbox=" + selectType + "&rand=" + Math.random(), obj, "dialogHeight:420px;dialogWidth:420px;scroll=no;help:no;status:no");
}

//菜单树
function openMenuTree(ctx, onlyLeaf) {
	if (!onlyLeaf) onlyLeaf = '';
	return window.showModalDialog(ctx + "/tree/baseTree.do?method=menuTree&title=" + encodeURI("选择菜单") + "&state=1&first=1&onlyLeaf=" + onlyLeaf + "&rand=" + Math.random(), "", "dialogHeight:420px;dialogWidth:420px;scroll=no;help:no;status:no");
}

//字典树
function openDictTree(ctx, dictId, onlyLeaf, selectType, values, exp, levelCode) {
	if (!onlyLeaf) onlyLeaf = '';
	if (!selectType) selectType = '';
	var obj = new Object();
	obj.values = values || "";
	if (!exp) exp = '';
	if(!levelCode) levelCode = '';
	return window.showModalDialog(ctx + "/tree/baseTree.do?method=dictTree&levelCode="+levelCode+"&title=" + encodeURI("请选择") + "&state=1&first=1&onlyLeaf=" + onlyLeaf + "&checkbox=" + selectType + "&dictId=" + dictId + "&" + exp + "&rand=" + Math.random(), obj, "dialogHeight:420px;dialogWidth:420px;scroll=no;help:no;status:no");
}

//数据导入表单
function openImportForm(ctx, impName) {
	//window.showModalDialog(ctx + "/imp/import.do?impName=" + impName + "&rand=" + Math.random(), "", "dialogHeight:420px;dialogWidth:520px;scroll=no;help:no;status:no");
	window.open(ctx + "/imp/import.do?impName=" + impName + "&rand=" + Math.random(), "", "dialogHeight:420px;dialogWidth:520px;scroll=no;help:no;status:no");

}

//数据查询必选条件检查
function checkCondition(infoA,infoB,strCondition){
	if(strCondition==''){
		return true;
	}
	var objArray=strCondition.split("#");
	var haveCondition=false;
	var strInfo="";
	for(var i=0;i<objArray.length;i++ ){
		var ctr=objArray[i].split("|");
		strInfo=strInfo+ctr[0]+"\n\r";
		var oField=ctr[1].split(",");
		var oFieldHaveValue=true;
		for(var j=0;j<oField.length;j++){
			var obj=document.getElementsByName(oField[j]);
			if(obj[0].value==''){
				oFieldHaveValue=false;
				break;
			}
		}
		if(oFieldHaveValue){
			haveCondition=true;
			break;
		}
	}
	if(!haveCondition){
		alert(infoA+"：\n\r\n\r"+strInfo+"\n\r\n\r"+infoB+"！");
		return false;
	}
	return true;
}


