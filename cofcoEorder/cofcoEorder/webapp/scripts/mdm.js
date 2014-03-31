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

//经销商树
function openDistTreeForOrder(ctx, onlyLeaf, selectType, values,distTreeType) {
	if (!onlyLeaf) onlyLeaf = '';
	if (!selectType) selectType = '';
	if (!values) values = '';
	return window.showModalDialog(ctx + "/tree/mdmTree.do?method=distTreeForOrder&title=" + encodeURI("选择经销商") + "&state=1&first=1&onlyLeaf=" + onlyLeaf + "&checkbox=" + selectType + "&values=" + values + "&rand=" + Math.random()+"&distTreeType="+distTreeType, "", "dialogHeight:420px;dialogWidth:420px;scroll=no;help:no;status:no");
}

//经销商树
function openDistTree(ctx, onlyLeaf, selectType, values) {
	if (!onlyLeaf) onlyLeaf = '';
	if (!selectType) selectType = '';
	if (!values) values = '';
	return window.showModalDialog(ctx + "/tree/mdmTree.do?method=distTree&title=" + encodeURI("选择经销商") + "&state=1&first=1&onlyLeaf=" + onlyLeaf + "&checkbox=" + selectType + "&values=" + values + "&rand=" + Math.random(), "", "dialogHeight:420px;dialogWidth:420px;scroll=no;help:no;status:no");
}
//带组织经销商树
function openDistOrgTree(ctx, onlyLeaf, selectType, values) {
	if (!onlyLeaf) onlyLeaf = '';
	if (!selectType) selectType = '';
	if (!values) values = '';
	return window.showModalDialog(ctx + "/tree/mdmTree.do?method=distOrgTree&title=" + encodeURI("选择经销商") + "&state=1&first=1&onlyLeaf=" + onlyLeaf + "&checkbox=" + selectType + "&values=" + values + "&rand=" + Math.random(), "", "dialogHeight:420px;dialogWidth:420px;scroll=no;help:no;status:no");
}

//门店树
function openStoreTree(ctx, onlyLeaf, selectType, values) {
	if (!onlyLeaf) onlyLeaf = '';
	if (!selectType) selectType = '';
	if (!values) values = '';
	return window.showModalDialog(ctx + "/tree/mdmTree.do?method=storeTree&title=" + encodeURI("选择门店") + "&state=1&first=1&onlyLeaf=" + onlyLeaf + "&checkbox=" + selectType + "&values=" + values + "&rand=" + Math.random(), "", "dialogHeight:420px;dialogWidth:420px;scroll=no;help:no;status:no");
}

//产品树
function openProdTree(ctx, onlyLeaf, selectType, values) {
	if (!onlyLeaf) onlyLeaf = '';
	if (!selectType) selectType = '';
	if (!values) values = '';
	return window.showModalDialog(ctx + "/tree/mdmTree.do?method=prodTree&title=" + encodeURI("选择产品") + "&state=1&first=1&onlyLeaf=" + onlyLeaf + "&checkbox=" + selectType + "&values=" + values + "&rand=" + Math.random(), "", "dialogHeight:420px;dialogWidth:420px;scroll=no;help:no;status:no");
}

//产品组对应产品树
function openProdGroupTree(ctx, onlyLeaf, selectType, values, prodGroupId) {
	if (!onlyLeaf) onlyLeaf = '';
	if (!selectType) selectType = '';
	if (!values) values = '';
	if(!prodGroupId){
		alert("产品物料组不能为空！");
		return;
	}
	return window.showModalDialog(ctx + "/tree/mdmTree.do?method=prodgroupTree&prodGroupId="+prodGroupId+"&title=" + encodeURI("选择产品") + "&state=1&first=1&onlyLeaf=" + onlyLeaf + "&checkbox=" + selectType + "&values=" + values + "&rand=" + Math.random(), "", "dialogHeight:420px;dialogWidth:420px;scroll=no;help:no;status:no");
}
//产品结构对应产品树
function openProdSTRUTree(ctx, onlyLeaf, selectType, values, prodGroupId) {
	if (!onlyLeaf) onlyLeaf = '';
	if (!selectType) selectType = '';
	if (!values) values = '';
	if(!prodGroupId){
		alert("产品物料组不能为空！");
		return;
	}
	return window.showModalDialog(ctx + "/tree/mdmTree.do?method=prodSTRUTree&prodGroupId="+prodGroupId+"&title=" + encodeURI("选择产品") + "&state=1&first=1&onlyLeaf=" + onlyLeaf + "&checkbox=" + selectType + "&values=" + values + "&rand=" + Math.random(), "", "dialogHeight:420px;dialogWidth:420px;scroll=no;help:no;status:no");
}
//根据产品物料组查询产品，按照产品品牌展现
function prodBrandWhereProdSTRUTree(ctx, onlyLeaf, selectType, values, prodGroupId) {
	if (!onlyLeaf) onlyLeaf = '';
	if (!selectType) selectType = '';
	if (!values) values = '';
	if(!prodGroupId){
		alert("产品物料组不能为空！");
		return;
	}
	return window.showModalDialog(ctx + "/tree/mdmTree.do?method=prodBrandWhereProdSTRUTree&prodGroupId="+prodGroupId+"&title=" + encodeURI("选择产品") + "&state=1&first=1&onlyLeaf=" + onlyLeaf + "&checkbox=" + selectType + "&values=" + values + "&rand=" + Math.random(), "", "dialogHeight:430px;dialogWidth:420px;scroll=no;help:no;status:no;resizable:yes");
	//return window.open(ctx + "/tree/mdmTree.do?method=prodBrandWhereProdSTRUTree&prodGroupId="+prodGroupId+"&title=" + encodeURI("选择产品") + "&state=1&first=1&onlyLeaf=" + onlyLeaf + "&checkbox=" + selectType + "&values=" + values + "&rand=" + Math.random(), "", "dialogHeight:420px;dialogWidth:420px;scroll=no;help:no;status:no");
}

//Location soldto树
function openLocationTree(ctx, onlyLeaf, selectType, values) {
	if (!onlyLeaf) onlyLeaf = '';
	if (!selectType) selectType = '';
	if (!values) values = '';
	return window.showModalDialog(ctx + "/tree/mdmTree.do?method=storeTree&title=" + encodeURI("选择Location") + "&state=1&storeTypeCode=account&first=1&onlyLeaf=" + onlyLeaf + "&checkbox=" + selectType + "&values=" + values + "&rand=" + Math.random(), "", "dialogHeight:420px;dialogWidth:420px;scroll=no;help:no;status:no");
}

//送货方树
function openDistAddressTree(ctx, onlyLeaf, selectType, values, prodGroupId) {
	if (!onlyLeaf) onlyLeaf = '';
	if (!selectType) selectType = '';
	if (!values) values = '';
	if(!prodGroupId){alert("产品组不能为空！");return;}
	
	return window.showModalDialog(ctx + "/tree/mdmTree.do?method=distAddressTree&prodGroupId="+prodGroupId+"&title=" + encodeURI("选择送货方") + "&state=1&first=1&onlyLeaf=" + onlyLeaf + "&checkbox=" + selectType + "&values=" + values + "&rand=" + Math.random(), "", "dialogHeight:420px;dialogWidth:420px;scroll=no;help:no;status:no");
}
//送货方树
function openDistAddressTreeByDist(ctx, onlyLeaf, selectType, values) {
	if (!onlyLeaf) onlyLeaf = '';
	if (!selectType) selectType = '';
	if (!values) values = '';
	 
	
	return window.showModalDialog(ctx + "/tree/mdmTree.do?method=distAddressTreeByDist&title=" + encodeURI("选择送货方") + "&state=1&first=1&onlyLeaf=" + onlyLeaf + "&checkbox=" + selectType + "&values=" + values + "&rand=" + Math.random(), "", "dialogHeight:420px;dialogWidth:420px;scroll=no;help:no;status:no");
}
//送货地址树 根据code踢重
function openDistAddressGroupCodeTree(ctx, onlyLeaf, selectType, values, prodGroupId) {
	if (!onlyLeaf) onlyLeaf = '';
	if (!selectType) selectType = '';
	if (!values) values = '';
	if(!prodGroupId){alert("产品组不能为空！");return;}
	
	return window.showModalDialog(ctx + "/tree/mdmTree.do?method=distAddressTreeGroupCode&prodGroupId="+prodGroupId+"&title=" + encodeURI("选择送货方") + "&state=1&first=1&onlyLeaf=" + onlyLeaf + "&checkbox=" + selectType + "&values=" + values + "&rand=" + Math.random(), "", "dialogHeight:420px;dialogWidth:420px;scroll=no;help:no;status:no");
}
//业代树
function openDistEmpTree(ctx, onlyLeaf, selectType, values, prodGroupId) {
	if (!onlyLeaf) onlyLeaf = '';
	if (!selectType) selectType = '';
	if (!values) values = '';
	if(!prodGroupId){alert("产品组不能为空！");return;}
	
	return window.showModalDialog(ctx + "/tree/mdmTree.do?method=distEmpTree&prodGroupId="+prodGroupId+"&title=" + encodeURI("选择业代树") + "&state=1&first=1&onlyLeaf=" + onlyLeaf + "&checkbox=" + selectType + "&values=" + values + "&rand=" + Math.random(), "", "dialogHeight:420px;dialogWidth:420px;scroll=no;help:no;status:no");
}

//物料组树
function openOrderMaterialsTree(ctx, onlyLeaf, selectType, values, levelCode) {
	if (!onlyLeaf) onlyLeaf = '';
	if (!selectType) selectType = '';
	var obj = new Object();
	obj.values = values || "";
	if(!levelCode) levelCode = '';
	return window.showModalDialog(ctx + "/tree/mdmTree.do?method=orderMaterialsTree&levelCode="+levelCode+"&title=" + encodeURI("请选择") + "&state=1&first=1&onlyLeaf=" + onlyLeaf + "&checkbox=" + selectType + "&values=" + values + "&rand=" + Math.random(), obj, "dialogHeight:420px;dialogWidth:420px;scroll=no;help:no;status:no");
}