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

//二，三级销售客户名称
function openMultiStoreTree(ctx, onlyLeaf, selectType, values,channel,distId,type) {

	if (!onlyLeaf) onlyLeaf = '';
	if (!selectType) selectType = '';
	if (!values) values = '';
	values='';
	if (!type) type = '2';
	return window.showModalDialog(ctx + "/tree/multiStoreTree.do?method=multiStoreTree&title=" + encodeURI("选择销售客户") + "&state=1&first=1&onlyLeaf=" + onlyLeaf + "&checkbox=" + selectType + "&values=" + values +"&channel="+channel+"&distId="+distId+ "&rand=" + Math.random()+"&type="+type, "", "dialogHeight:420px;dialogWidth:420px;scroll=no;help:no;status:no");
}

//字典树,加备注做为筛选的条件
function openDictTree(ctx, dictId, onlyLeaf, selectType, values,filterField) {
	if (!onlyLeaf) onlyLeaf = '';
	if (!selectType) selectType = '';
	if (!values) values = '';
	if (!filterField) filterField = '';
	return window.showModalDialog(ctx + "/tree/baseTree.do?method=dictTree&title=" + encodeURI("请选择") + "&state=1&first=1&onlyLeaf=" + onlyLeaf + "&checkbox=" + selectType+"&$eq_memo1="+filterField+ "&values=" + values + "&dictId=" + dictId + "&rand=" + Math.random(), "", "dialogHeight:420px;dialogWidth:420px;scroll=no;help:no;status:no");
}