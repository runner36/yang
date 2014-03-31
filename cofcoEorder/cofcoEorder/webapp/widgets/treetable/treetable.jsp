<%@ page contentType="text/html; charset=GB2312" %>
<%@ include file="/commons/taglibs.jsp"%>
<jsp:directive.page import="com.winchannel.core.conf.ReportConfigurator"/>
<jsp:directive.page import="com.winchannel.core.bean.ListColumn"/>
<%
	ReportConfigurator configurator = (ReportConfigurator) request.getAttribute("configurator");
	ListColumn[] columns = configurator.getColumns();
	int hier = 0;
	int totalWidth = 1;
	for (int i = 0; i < columns.length; i++) {
		int len = columns[i].getTitle().split(",").length;
		if (hier < len) {
	hier = len;
		}
		totalWidth = totalWidth + Integer.parseInt(columns[i].getWidth()) + 1;
	}
%>
<html>
<head>
	<title></title>
	<script language="javaScript" src="${ctx}/widgets/treetable/treetable.js"></script>
	<style>
		#kkk A { color:#000000; font-size:12px; text-decoration: none; }
		#kkk A:link { color:#000000; font-size:12px; text-decoration: none; }
		#kkk A:visited { color:#000000; font-size:12px; text-decoration: none; }
		#kkk A:hover { color: #000000;; font-size:12px; text-decoration: none;}
		#kkk A:active {	color: #000000;	font-size:12px; text-decoration: none;}
		
		#kkk table{background:#fff;}
		#kkk table,.t4 {border-collapse:collapse;WIDTH: <%=totalWidth%>px;font-size:12px; border:solid #e5e5e5; border-width:0px 0px 1px 0px; padding:0px;}

		.t1 {background:#fafafa;}/* 第一行的背景色 */
		.t2,.t4 {background:#f7f7f7;}/* 第二行的背景色 */
		.t3 {background:#fff1f5;}/* 鼠标经过时的背景色 */
		.t4 th,#kkk table td{ border:solid #e5e5e5;border-width:0px 1px 1px 0px;}
		.t4 th {padding:4px 0px;font-weight: normal;}

		#titleSoll {position:relative;top: expression(this.offsetParent.scrollTop);}

	</style>
	<script>
		function getData(data, index, scale, per) {
			var value = data.split('&')[index];
			var perStr = (per != null && per == 'true') ? '%' : '';
			/*if (value == null || value == 'null' || value == '') {
				if (scale == null || scale == 'null' || scale == '') {
					value = '';
				}
				else {
					value = '0';
				}
			}
			else {
				if (scale != null && scale != 'null' && scale != '') {
					value = Math.round(value * Math.pow(10, scale)) / Math.pow(10, scale);
				}
			}*/
			return value + perStr;
		}
	</script>
	<script>
	</script>
</head>
<body>
<table class='t4' id="titleSoll" border="0" cellspacing="0" cellpadding="0">
<%
	for (int i = 0; i < hier; i++) {
		String headName = "";
		String width = "";
		int colspan = 1;
		int rowspan = 1;
		boolean isLast = false;
		out.println("<tr>");
		for (int j = 0; j < columns.length; j++) {
			ListColumn column = columns[j];
			String[] name = column.getTitle().split(",");
			
			if ((i + 1 > name.length || !name[i].equals(headName)) && !headName.equals("")) {
				String widthStr = "";
				if (isLast) {
			widthStr = "width='" + width + "' ";
				}
				out.println("<th " + widthStr + "rowspan='" + rowspan + "' colspan='" + colspan + "'>" + headName + "</th>");
				colspan = 1;
				rowspan = 1;
			}
			else {
				if (!headName.equals("")) {
			colspan++;
				}
			}
			
			if (i + 1 <= name.length) {
				headName = name[i];
				width = column.getWidth();
				isLast = (i == name.length -1);
				if (isLast) {
			rowspan = hier - i;
				}
			}
			else {
				headName = "";
			}
		}
		
		if (!headName.equals("")) {
			String widthStr = "";
			if (isLast) {
				widthStr = "width='" + width + "' ";
			}
			out.println("<th " + widthStr + "rowspan='" + rowspan + "' colspan='" + colspan + "'>" + headName + "</th>");
		}
		
		out.println("</tr>");
	}
%>
</table>
<div id="kkk"></div>
	<script language="javascript" type="text/javascript">
	var MzTreeViewTH = "<table border='0' cellspacing='0' cellpadding='0' id='ContentTab'><tr onmouseover=\"this.className='t3'\" onmouseout=\"this.className=''\"><td width='<%=columns[0].getWidth()%>'>";
	var MzTreeViewTD = "\"</td>" + 
		<%for (int i = 1; i < columns.length; i++) {%>
			"<td width='<%=columns[i].getWidth()%>' align='<%=columns[i].getAlign()%>'>\"+getData(data,<%=(i-1)%>,'','')+\"</td>" +
		<%}%>
		"</tr></table>\"";
	
	window.tree = new MzTreeView("tree");
	tree.setIconPath("${ctx}/widgets/treetable/images/");
	//tree.N["0_999999999"] = "T:";
	<%	java.util.List list = (java.util.List) request.getAttribute("list");
		for (int i = 0; i < list.size(); i++) {
			Object[] node = (Object[]) list.get(i);
	%>
			tree.N["<%=node[0]==null?"0":node[0]%>_<%=node[1]%>"] = "T:<%=node[2]%>;data:<%for (int j = 3; j < node.length; j++) out.print(columns[j-2].format(node[j])+"&");%>";
	<%	}%>

	//tree.setURL("#");
	tree.wordLine = false;
	//tree.setTarget("main");
	document.getElementById("kkk").innerHTML=tree.toString();
	//tree.expandAll();
	</script>
</body>
</html>
