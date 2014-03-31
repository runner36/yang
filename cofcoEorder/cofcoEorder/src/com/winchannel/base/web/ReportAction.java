package com.winchannel.base.web;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winchannel.base.service.BaseReportManager;
import com.winchannel.base.utils.Constants;
import com.winchannel.core.bean.ExpParam;
import com.winchannel.core.bean.ListColumn;
import com.winchannel.core.bean.ListCondition;
import com.winchannel.core.conf.ReportConfigurator;
import com.winchannel.core.exporter.Exporter;
import com.winchannel.core.exporter.ExporterFactory;
import com.winchannel.core.utils.ECPage;
import com.winchannel.core.utils.MessageResourceUtils;
import com.winchannel.core.utils.Page;
import com.winchannel.core.utils.StringUtils;
import com.winchannel.core.web.StrutsBaseAction;

public abstract class ReportAction extends StrutsBaseAction {

	private static final Log log = LogFactory.getLog("ReportAction");
	
	private int columnStartIndex = 0;

	private BaseReportManager baseReportManager;

	public void setBaseReportManager(BaseReportManager baseReportManager) {
		this.baseReportManager = baseReportManager;
	}

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		Page page = null;
		String tableId = request.getParameter("tableId");
		if (StringUtils.isNotBlank(tableId)) {
			page = new ECPage(request, tableId);
		}
		else {
			page = new ECPage(request);
		}
		ReportConfigurator configurator = getConfigurator(request, page);
		request.setAttribute("configurator", configurator);
		if (StringUtils.isNotBlank(request.getParameter("view"))) {
			log.info("平台框架导出\t文件名称:"+configurator.getTitle()+"\t文件类型:"+request.getParameter("view"));
		}
		
		
		this.initPage(page, configurator, request);
		this.onInitPage(page, configurator, request);
		List data = this.initData(page, configurator, request);
		this.onInitData(page, configurator, request, data);
		
		if (StringUtils.isNotBlank(request.getParameter("view"))) {
			this.export(request.getParameter("view"), data, columnStartIndex, configurator, response);
			return null;
		}
		if (StringUtils.isNotBlank(configurator.getForword())) {
			return new ActionForward(configurator.getForword());
		}
		return mapping.findForward(VIEW);
	}

	protected void initPage(Page page, ReportConfigurator configurator, HttpServletRequest request) {
		if ("1".equals(request.getParameter("first"))) {
			Iterator<ExpParam> it = configurator.getParams().iterator();
			while (it.hasNext()) {
				ExpParam param = it.next();
				if (StringUtils.isNotBlank(param.getInitValue())) {
					page.put(param.getId(), param.getInitValue());
				}
			}
		}
	}
	
	protected void onInitPage(Page page, ReportConfigurator configurator,
			HttpServletRequest request) {
	}

	protected List initData(Page page, ReportConfigurator configurator,
			HttpServletRequest request) {
		List list = null;
		if ("1".equals(request.getParameter("first"))
				&& !configurator.isFirstQuery()) {
			list = new ArrayList();
		} else {
			list = baseReportManager.query(page, configurator,request);
		}
		int totalRows = page.getInt(Page.TOTAL_COUNT);
		if (totalRows == 0) {
			totalRows = list.size();
		}
		request.setAttribute("totalRows", totalRows);
		request.setAttribute("list", list);

		return list;
	}

	protected void onInitData(Page page, ReportConfigurator configurator,
			HttpServletRequest request, List list) {
	}

	protected void export(String type, List data, int startIndex,
			ReportConfigurator configurator, HttpServletResponse response) {
		Exporter exp = null;
		if ("xls".equals(type)) {
			exp = ExporterFactory.createXlsExporter(configurator.getColumns());
		} else if ("csv".equals(type)) {
			exp = ExporterFactory.createCsvExporter(configurator.getColumns());
		}
		exp.createTitle(configurator.getTitle());
		exp.createHeader();
		exp.createBody(data, startIndex, 0);
		try {
			exp.writeZip(response, configurator.getTitle());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getColumnStartIndex() {
		return columnStartIndex;
	}

	public void setColumnStartIndex(int columnStartIndex) {
		this.columnStartIndex = columnStartIndex;
	}

	
	protected ReportConfigurator getConfigurator(HttpServletRequest request, Page page) {
		String reportName = page.get("reportName");
		ServletContext ctx = request.getSession().getServletContext();
		String path = "/WEB-INF/pages/report/" + reportName + ".xml";
		
		ReportConfigurator configurator = null;
		try {
			String realPath = ctx.getRealPath(path);
			File file = null;
			if (realPath != null && (file = new File(realPath)).exists()) {
				configurator = ReportConfigurator.getInstance(reportName, file);
			}
			else {
				configurator = ReportConfigurator.getInstance(reportName);
				if (configurator == null) {
					configurator = ReportConfigurator.getInstance(reportName, ctx.getResourceAsStream(path));
				}
			}

		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		ListColumn[] columns = configurator.getColumns();
		MessageResourceUtils mr = (MessageResourceUtils) request.getSession().getAttribute(Constants.SESSION_MESSAGE_KEY);
		for (ListColumn col : columns) {
			if (!"".equals(col.getKey())) {
				col.setTitle((String) mr.get(col.getBundle() + "." + col.getKey()));
			}
		}
		// Title国际化
		if (!"".equals(configurator.getTitleKey())) {
			configurator.setTitle((String) mr.get(configurator.getTitleBundle()
					+ "." + configurator.getTitleKey()));
		}
		// 必要条件选择国际化
		StringBuffer strCondition = new StringBuffer();
		ListCondition[] conditions = configurator.getConditions();
		if (conditions != null) {
			for (ListCondition condi : conditions) {
				if (!"".equals(condi.getKey())) {
					condi.setTitle((String) mr.get(condi.getBundle() + "."
							+ condi.getKey()));
				}
				strCondition.append("#" + condi.getTitle() + "|"
						+ condi.getProperty());
			}
			configurator.setCondition(strCondition.substring(1));
		}
		return configurator;
		
	}/*
	protected void initConfigurator(HttpServletRequest request,
			ReportConfigurator configurator) {
		ListColumn[] columns = configurator.getColumns();
		MessageResourceUtils mr = (MessageResourceUtils) request.getSession()
				.getAttribute(Constants.SESSION_MESSAGE_KEY);
		for (ListColumn col : columns) {
			if (!"".equals(col.getKey())) {
				col.setTitle((String) mr.get(col.getBundle() + "."
						+ col.getKey()));
			}
		}
		// Title国际化
		if (!"".equals(configurator.getTitleKey())) {
			configurator.setTitle((String) mr.get(configurator.getTitleBundle()
					+ "." + configurator.getTitleKey()));
		}
		// 必要条件选择国际化
		StringBuffer strCondition = new StringBuffer();
		ListCondition[] conditions = configurator.getConditions();
		if (conditions != null) {
			for (ListCondition condi : conditions) {
				if (!"".equals(condi.getKey())) {
					condi.setTitle((String) mr.get(condi.getBundle() + "."
							+ condi.getKey()));
				}
				strCondition.append("#" + condi.getTitle() + "|"
						+ condi.getProperty());
			}
			configurator.setCondition(strCondition.substring(1));
		}
	}*/

}
