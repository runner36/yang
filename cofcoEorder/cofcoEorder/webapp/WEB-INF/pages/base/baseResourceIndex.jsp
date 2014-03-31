<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>${mr['page.customize.title.ResourcesManage']}</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/sidecss.css">
		<script language="JavaScript" src="${ctx}/scripts/WinSollAuto.js"></script>
	</head>
	<body onload="WindowSollAuto()" onresize="WindowSollAuto()">
		<div class="bosom_one">
			<div class="bosom_top">
				<span class="left"></span>
				<h4>
					<!--TitleStrat-->
					${mr['page.customize.title.ResourcesManage']}
					<!--TitleStrat-->
				</h4>
				<div class='MenuList'>
					<!--MenuStrat-->
					<a href="javascript:content.add();">${mr['page.common.button.add']}</a>
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
									<td style="display:table-cell !important; display:block" width="180" height="100%" valign="top" id="leftmenu1" style="display:block">
										<iframe src="${ctx}/tree/baseTree.do?method=dictTree&dictId=resource&target=content&action=${ctx}/base/baseResource.do?method=list%26first=1" name="tree" width="180" marginwidth="0" height="100%" style="height:100%"
											marginheight="0" align="top" scrolling="auto" frameborder="0"></iframe>
									</td>
									<td height="100%" align="left" valign="top">
										<iframe src="baseResource.do?method=list&first=1" name="content" width="100%" marginwidth="0" height="100%"
											marginheight="0" align="top" scrolling="no" frameborder="0"></iframe>
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
