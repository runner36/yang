<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ include file="/commons/taglibs.jsp"%>

<html>
<head>
<%@ include file="/commons/meta.jsp"%>
<base target=_self></base>
<title>${mr['page.common.button.pleaseSelect']}</title>
<link rel="stylesheet" type="text/css"
	href="${ctx}/widgets/xtree/xtree.css">
<script language="javaScript" src="${ctx}/widgets/xtree/xtree.js"></script>
<style>
body {
	font-size: 9pt;
	margin: 1px;
	font: normal 12px 宋体;
	scrollbar-face-color: #f3f3f3;
	scrollbar-highlight-color: #b9b9b9;
	scrollbar-shadow-color: #b9b9b9;
	scrollbar-arrow-color: #a2a2a2;
	scrollbar-track-color: #fcfcfc;
	scrollbar-3dlight-color: #ffffff;
	scrollbar-darkshadow-color: #ffffff;
}

.button {
	background: url(../images/bosomer_14.gif) no-repeat;
	margin-bottom: -5px !important;
	height: 24px;
	width: 60px !important;
	color: #FFFFFF;
	border-width: 0px !important;
	font-size: 12px;
}

#keyWord {
	border: 1px solid #ccc;
	width: 300px;
	height: 18px;
}

#buttonSearch {
	padding: 4px;
	border-bottom: 1px solid #ccc;
	margin-bottom: 2px;
}

#buttonBox {
	padding: 5px 0px;
	border-Top: 1px solid #ccc;
	margin-Top: 2px;
}
</style>
<script>
		var onlyLeaf = '${param.onlyLeaf}';
		var checkbox = '${param.checkbox}';
		var action = '${param.action}';
		var target = '${param.target}';
		var values = ',${param.values},';
		
		if (window.dialogArguments && window.dialogArguments != '') {
			values = "," + window.dialogArguments.values + ",";
		}
		
		var webFXTreeConfig = {
			rootIcon        : '${ctx}/widgets/xtree/images/table_obj.gif',
			openRootIcon    : '${ctx}/widgets/xtree/images/table_obj.gif',
			folderIcon      : '${ctx}/widgets/xtree/images/closedFolder.gif',
			openFolderIcon  : '${ctx}/widgets/xtree/images/fldr_obj.gif',
			fileIcon        : '${ctx}/widgets/xtree/images/file.gif',
			iIcon           : '${ctx}/widgets/xtree/images/I.png',
			lIcon           : '${ctx}/widgets/xtree/images/L.png',
			lMinusIcon      : '${ctx}/widgets/xtree/images/Lminus.png',
			lPlusIcon       : '${ctx}/widgets/xtree/images/Lplus.png',
			tIcon           : '${ctx}/widgets/xtree/images/T.png',
			tMinusIcon      : '${ctx}/widgets/xtree/images/Tminus.png',
			tPlusIcon       : '${ctx}/widgets/xtree/images/Tplus.png',
			blankIcon       : '${ctx}/widgets/xtree/images/blank.png',
			defaultText     : 'Tree Item',
			defaultAction   : (action ? action : 'javascript:void(0);'),
			defaultBehavior : 'classic',
			usePersistence	: true,
			
			defaultTarget   : target,
			onlyLeaf        : onlyLeaf,
			checkbox        : checkbox,
			values          : values
		};
	
		function getSelected() {
			return tree.getSelected();
		}
		
		function getId() {
			if (checkbox == '1' || checkbox == '2') {
				return tree.getParamArrays().id;
			}
			else {
				return tree.getSelected() != null ? tree.getSelected().oid : '';
			}
		}
		
		function getText() {
			if (checkbox == '1' || checkbox == '2') {
				return tree.getParamArrays().text;
			}
			else {
				return tree.getSelected() != null ? tree.getSelected().text : '';
			}
		}
		
		function getParamArray() {
			return getSelected().paramArray;
		}
		
		function getParam(paramName) {
			return eval('getParamArray().' + paramName);
		}
		
		function ok() {
			if (checkbox == '1' || checkbox == '2') {
				window.returnValue = tree.getParamArrays();
			}
			else {
				if (getSelected()) {
					window.returnValue = getSelected().paramArray;
				}
			}
			self.close();
		}
		
		function cancel() {
			self.close();
		}
		
		function remove() {
			ok();
			window.returnValue.id = '';
			window.returnValue.text = '';
			cancel();
		}
		
		var findValue = '';
		var findIndex = 0;
		//var links = null;
		function find() {
		
			if (keyWord.value != findValue || findIndex >= webFXTreeHandler.allId.length) {
				findIndex = 0;
			}
			findValue = keyWord.value;
			if (findValue == '') {
				return;
			}
			
			/*for (; findIndex < links.length; findIndex++) {
				if (links[findIndex].innerHTML.indexOf(findValue) != -1 && links[findIndex].href != '') {
					tree.expandAll()
					webFXTreeHandler.all[links[findIndex].id.replace('-anchor','')].focus();
					findIndex++;
					break;
				}
				
			}*/
			
			for (; findIndex < webFXTreeHandler.allId.length; findIndex++) {
				if (webFXTreeHandler.all[webFXTreeHandler.allId[findIndex]].text.indexOf(findValue) != -1) {
					webFXTreeHandler.all[webFXTreeHandler.allId[findIndex]].focus();
					findIndex++;
					break;
				}
			}
			
		}

		function init() {
			//links = document.getElementsByTagName("a");
			//修正弹出的树形选择框（例：经销商选择框）在firefox和ghrome下没有清除、确定、取消按钮的bug
			if (window.dialogHeight != null || window.opener != null) {
				buttonBox.style.display = 'block';
				buttonSearch.style.display = 'block';
				treeBox.style.height = '302px';
				treeBox.style.overflow = 'auto';
				treeBox.style.position = 'relative';
				buttonBox.style.height = '15%';
			}
			else {
				buttonSearch.style.display = 'block';
				keyWord.style.width = '86px';
			}
		}
	</script>
</head>
<body onload="init()">

	<div id="buttonSearch" style="width: 100%; display: none;">
		<input type="text" id="keyWord" /> <input type="button" class="button"
			value="${mr['page.common.button.search']}" onclick="find()" />
	</div>

	<div id="treeBox" style="width: 100%; height: 100%; font-size: 12px">
		<script>
	//tree格式：{sText, sParams, sAction, eParent, sIcon, sOpenIcon}
	//数组格式：{parentId, id, name, params, action, icon, openIcon}
	var tree = new WebFXTree("${mr['page.common.button.pleaseSelect']}...", "", "");
	<c:forEach items="${tree}" var="node">
		var parent = typeof(tree${node[0]}) != 'undefined' ? tree${node[0]} : tree;
		var tree${node[1]} = new WebFXTreeItem("${node[2]}", "${node[3]}", "${node[4]}", parent, "${node[5]}", "${node[6]}");
	</c:forEach>
	document.write(tree);
	tree.initValues();
</script>
	</div>

	<div id="buttonBox" align="center" style="width: 100%; display: none;">
		<input type="button" class="button" value="${mr['page.common.button.clear']}" onclick="remove()" /> 
		<input type="button" class="button" value="${mr['page.common.button.ok']}" onclick="ok()" /> 
		<input type="button" class="button" value="${mr['page.common.button.cancel']}" onclick="cancel()" />
	</div>
</body>
</html>

