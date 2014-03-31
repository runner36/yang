package com.winchannel.base.web;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.extremecomponents.table.context.HttpServletRequestContext;
import org.extremecomponents.table.core.TableConstants;
import org.extremecomponents.table.filter.ExportFilter;
import org.extremecomponents.table.filter.ExportFilterUtils;

/**
 * Ectable导出的filter<br>
 * 1.加入导出时的日志功能,便于系统性能监控<br>
 * 2.
 * @author lidongbo
 * @date   2011-08-10
 * */
public class EctableExportFilter extends ExportFilter {

	private static final Log log = LogFactory.getLog("EctableExport");
	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequestContext context = new HttpServletRequestContext((HttpServletRequest) request);
		boolean isExported = ExportFilterUtils.isExported(context);
		if (isExported) {
			log.info("Ectable Export\tFileName:"+ExportFilterUtils.getExportFileName(context)+"\tFileType:"+request.getParameter(TableConstants.EXTREME_COMPONENTS+"_"+TableConstants.EXPORT_VIEW));
		}
		super.doFilter(request, response, chain);
	}

}
