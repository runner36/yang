package com.winchannel.base.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.LazyValidatorForm;

import com.winchannel.core.bean.ListColumn;
import com.winchannel.core.conf.ReportConfigurator;
import com.winchannel.core.utils.Page;

public class ListReportAction extends ReportAction {
	
	@Override
	protected void init() {
		this.setColumnStartIndex(0);
		super.init();
	}

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		super.unspecified(mapping, form, request, response);
		ReportConfigurator configurator = ReportConfigurator.getInstance(Page.getValue(request, "reportName"));
		
		if ("1".equals(request.getParameter("first"))) {
			LazyValidatorForm listForm = (LazyValidatorForm) form;
			StringBuilder colIds = new StringBuilder();
			ListColumn[] columns = configurator.getColumns();
			for (ListColumn col : columns) {
				if (colIds.length() > 0) {
					colIds.append(",");
				}
				colIds.append(col.getProperty());
			}
			listForm.set("colIds", colIds.toString());
		}
		
		if (configurator.getColumns() == null || configurator.getColumns().length == 0) {
			return new ActionForward("/WEB-INF/pages/report/" + Page.getValue(request, "reportName") + ".jsp");
		}
		else {
			return mapping.findForward(VIEW);
		}
	}

}
