//client树
function openClientTree(ctx, onlyLeaf, selectType, values) {
	if (!onlyLeaf) onlyLeaf = '';
	if (!selectType) selectType = '';
	if (!values) values = '';
	return window.showModalDialog(ctx + "/tree/dmsTree.do?method=clientTree&title=" + encodeURI("${mr['page.common.mess.selectClient']}") + "&state=1&first=1&onlyLeaf=" + onlyLeaf + "&checkbox=" + selectType + "&values=" + values + "&rand=" + Math.random(), "", "dialogHeight:420px;dialogWidth:420px;scroll=no;help:no;status:no");
}
//软件树
function openSoftTree(ctx, onlyLeaf, selectType, values) {
	if (!onlyLeaf) onlyLeaf = '';
	if (!selectType) selectType = '';
	if (!values) values = '';
	return window.showModalDialog(ctx + "/tree/dmsTree.do?method=softTree&title=" + encodeURI("请选择软件") + "&state=1&first=1&onlyLeaf=" + onlyLeaf + "&checkbox=" + selectType + "&values=" + values + "&rand=" + Math.random(), "", "dialogHeight:420px;dialogWidth:420px;scroll=no;help:no;status:no");
}
