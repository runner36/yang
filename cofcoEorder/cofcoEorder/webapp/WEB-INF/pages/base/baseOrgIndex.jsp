<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>${mr['base.org.title']}</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
		<script language="javaScript" src="${ctx}/scripts/base.js"></script>
	</head>
	<body onload="WindowSollAuto()" onresize="WindowSollAuto()">
		<div class="bosom_one">
			<div class="bosom_top">
				<span class="left"></span>
				<h4>
					<!--TitleStrat-->
					${mr['base.org.title']}
					<!--TitleStrat-->
				</h4>
				<div class='MenuList'>
					<!--MenuStrat-->
					<a href="javascript:content.save();">${mr['page.common.button.save']}</a>
					<a href="javascript:content.add();">${mr['page.common.button.add']}</a>
					<a href="javascript:content.del();">${mr['page.common.button.delete']}</a>
					<!-- 
					<a href="javascript:openImportForm('${ctx}', 'BaseOrg');">${mr['page.common.button.import']}</a>
					<a href="javascript:location='${ctx}/report/listReport.do?reportName=base/BaseOrg&view=xls';"${mr['page.common.button.export']}/a>
					 -->
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

							<!--ContentStrat-->
							<table border="0" cellpadding="0" cellspacing="0" height="100%" width="100%">
								<tr>
									<td style="display:table-cell !important; display:block" width="220" height="100%" valign="top" id="leftmenu1" style="display:block">
										<iframe src="${ctx}/tree/baseTree.do?method=orgTree&first=1&target=content&action=${ctx}/base/baseOrg.do?method=edit"
											name="tree" width="220" marginwidth="0" height="100%" style="height:100%" marginheight="0" align="top"
											scrolling="auto" frameborder="0"></iframe>
									</td>
									<td height="100%" align="left" valign="top">
										<iframe src="baseOrg.do?method=add" name="content" width="100%" marginwidth="0" height="100%" marginheight="0"
											align="top" scrolling="auto" frameborder="0"></iframe>
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
