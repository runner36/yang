<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>${mr['page.customize.title.dictManage']}</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
		<script>
		function selectDict(dictId) {
			tree.location = '${ctx}/tree/baseTree.do?method=dictTree&dictId=' + dictId + '&target=content&action=${ctx}/base/baseDict.do?method=edit';
			content.location = 'baseDict.do?method=add&dictId=' + dictId;
		}
		function dictedit() {
			window.showModalDialog("${ctx}/base/baseDict.do?method=dictedit&rand=" + Math.random(), "", "dialogHeight:560px;dialogWidth:750px;scroll=no;help:no;status:no");
		}
		</script>
	</head>
	<body onload="WindowSollAuto();dictId.fireEvent('onchange');" onresize="WindowSollAuto()">
		<div class="bosom_one">
			<div class="bosom_top">
				<span class="left"></span>
				<h4>
					<!--TitleStrat-->
					${mr['page.customize.title.dictManage']}
					<!--TitleStrat-->
				</h4>
				<div class='MenuList'>
					<!--MenuStrat-->
					<!-- <a class="Im80" href="javascript:openImportForm('${ctx}', 'StoreOrg');">${mr['page.common.button.importStoreOrg']}</a> -->
					<a class="Im80" href="javascript:openImportForm('${ctx}', 'Geo');">${mr['page.common.button.importGeography']}</a>
					<a href="javascript:content.save();">${mr['page.common.button.save']}</a>
					<a href="javascript:content.add();">${mr['page.common.button.add']}</a>
					<a href="javascript:content.del();">${mr['page.common.button.delete']}</a>
					<a href="javascript:location.reload();">${mr['page.common.button.refresh']}</a>
					
					<!--MenuEnd-->
				</div>
				<span class="right"></span>
			</div>
			<div class="bosom_side">
				<div class="casing">
					<div class="caput">
						<span class="left"></span><span class="right"></span>
					</div>
					<div class="viscera" id="SollAuto">
						<div>
							<table border="0" cellpadding="0" cellspacing="0" height="100%" width="100%">
								<tr>
									<td style="display:table-cell !important; display:block" width="180" height="100%" valign="top" id="leftmenu1" style="display:block">
										&nbsp;
										<html:select name="baseDictForm" property="dictId" onchange="selectDict(this.value)" style="margin-bottom: 5px;">
											<html:options collection="dicts" property="dictId" labelProperty="dictName"/>
										</html:select>
										<input type="button" name="b1" value="+" style="margin-top:-25px" onclick="dictedit()">
										
										<iframe src="" name="tree" width="180" marginwidth="0" style="height:94%"
											marginheight="0" align="top" scrolling="auto" frameborder="0"></iframe>
									</td>
									<td height="100%" align="left" valign="top">
										<iframe src="" name="content" width="100%" marginwidth="0" height="100%"
											marginheight="0" align="top" scrolling="auto" frameborder="0"></iframe>
									</td>
								</tr>
							</table>
							<!--ContentEnd-->

						</div>
					</div>
					<div class="trail">
						<span class="fleft"></span><span class="fright"></span>
					</div>
				</div>
			</div>
			<div class="bosom_bottom">
				<span class="left"></span><span class="right"></span>
			</div>
		</div>
	</body>
</html>
