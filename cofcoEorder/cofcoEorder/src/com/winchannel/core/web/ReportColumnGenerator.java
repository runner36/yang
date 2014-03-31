package com.winchannel.core.web;

import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.core.AutoGenerateColumns;
import org.extremecomponents.table.core.TableModel;

import com.winchannel.base.service.BaseMessResManager;
import com.winchannel.base.web.BaseMessResAction;
import com.winchannel.core.bean.ListColumn;
import com.winchannel.core.utils.BeanUtils;
import com.winchannel.core.utils.ECPage;
import com.winchannel.core.utils.Page;
import com.winchannel.core.utils.SpringContext;
import com.winchannel.core.utils.StringUtils;

public class ReportColumnGenerator implements AutoGenerateColumns {

	public void addColumns(TableModel model) {
		/*
		 * String colIds = model.getContext().getParameter("colIds"); String[]
		 * columnIds = null; if (StringUtils.isNotBlank(colIds)) { columnIds =
		 * StringUtils.split(colIds, ","); }
		 * 
		 * ReportConfigurator configurator = (ReportConfigurator)
		 * model.getContext().getRequestAttribute("configurator");
		 * 
		 * 
		 * if (columnIds == null) { for (ListColumn column :
		 * configurator.getColumns()) { addColumn(model, column); } } else { for
		 * (String columnId : columnIds) { for (ListColumn column :
		 * configurator.getColumns()) { if
		 * (columnId.equals(column.getProperty())) { addColumn(model, column); }
		 * } } }
		 */

		BaseMessResManager manager = (BaseMessResManager) SpringContext
				.getBean("baseMessResManager");
		Page page = (Page) model.getContext().getSessionAttribute(
				ECPage.DEFAULT_PAGENAME);
		ListColumn[] columns = manager.getColumns(
				BaseMessResAction.DEFAULT_LANGUAGE, page.get("reportName"),
				page.get(BaseMessResAction.PARAM_NAME));
		for (ListColumn column : columns) {
			if (!"0".equals(column.getState())) {
				if (StringUtils.isNotBlank(column.getNewTitle())) {
					column.setTitle(column.getNewTitle());
				}
				addColumn(model, column);
			}
		}

	}

	private void addColumn(TableModel model, ListColumn column) {
		Column ecColumn = model.getColumnInstance();
		try {
			BeanUtils.copyProperties(ecColumn, column);
		} catch (Exception e) {
		}
		if (StringUtils.isNotBlank(column.getEscapeAutoFormat())) {
			ecColumn.setEscapeAutoFormat(new Boolean(column
					.getEscapeAutoFormat()));
		}
		if (StringUtils.isNotBlank(column.getSortable())) {
			ecColumn.setSortable(new Boolean(column.getSortable()));
		}

		if (StringUtils.isNotBlank(column.getFilterable())) {
			ecColumn.setFilterable(new Boolean(column.getFilterable()));
		}
		if (StringUtils.isNotBlank(column.getCalc())) {
			ecColumn.setCalc(column.getCalc());
		}
		if (StringUtils.isNotBlank(column.getCalcTitle())) {
			ecColumn.setCalcTitle(column.getCalcTitle());
		}
		if (StringUtils.isNotBlank(column.getViewsAllowed())) {
			ecColumn.setViewsAllowed(column.getViewsAllowed());
		}
		if (StringUtils.isNotBlank(column.getViewsDenied())) {
			ecColumn.setViewsDenied(column.getViewsDenied());
		}
		model.getColumnHandler().addAutoGenerateColumn(ecColumn);
	}

}
