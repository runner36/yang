package com.winchannel.core.bean;

import java.util.Date;

import com.winchannel.core.utils.DateUtils;
import com.winchannel.core.utils.NumberUtils;
import com.winchannel.core.utils.StringUtils;


public class ListColumn implements java.io.Serializable {

    private String alias;
    private String calc;
    private String calcTitle;
    private String cell;
    private String filterOptions;
    private String escapeAutoFormat;
    private String filterable;
    private String filterCell;
    private String filterClass;
    private String filterStyle;
    private String format;
    private String headerCell;
    private String headerClass;
    private String headerStyle;
    private String interceptor;
    private String parse;
    private String property;
    private String sortable;
    private String style;
    private String styleClass;
    private String title;
    private String value;
    private String viewsAllowed;
    private String viewsDenied;
    private String width;
    private String align;
    
    private String bundle;
    private String key;
    
    private String newTitle;
    private Long sort;
    private String state;
    
	public ListColumn() {
		super();
		this.alias = "";
		this.calc = "";
		this.calcTitle = "";
		this.cell = "";
		this.filterOptions = "";
		this.escapeAutoFormat = "";
		this.filterable = "";
		this.filterCell = "";
		this.filterClass = "";
		this.filterStyle = "";
		this.format = "";
		this.headerCell = "";
		this.headerClass = "";
		this.headerStyle = "";
		this.interceptor = "";
		this.parse = "";
		this.property = "";
		this.sortable = "";
		this.style = "";
		this.styleClass = "";
		this.title = "";
		this.value = "";
		this.viewsAllowed = "";
		this.viewsDenied = "";
		this.width = "";
		this.align = "";
		this.bundle = "";
		this.key = "";
	}
	
	public ListColumn(String property, String title) {
		super();
		this.alias = "";
		this.calc = "";
		this.calcTitle = "";
		this.cell = "";
		this.filterOptions = "";
		this.escapeAutoFormat = "";
		this.filterable = "";
		this.filterCell = "";
		this.filterClass = "";
		this.filterStyle = "";
		this.format = "";
		this.headerCell = "";
		this.headerClass = "";
		this.headerStyle = "";
		this.interceptor = "";
		this.parse = "";
		this.property = property;
		this.sortable = "";
		this.style = "";
		this.styleClass = "";
		this.title = title;
		this.value = "";
		this.viewsAllowed = "";
		this.viewsDenied = "";
		this.width = "";
		this.align = "";
		this.bundle = "";
		this.key = "";
	}
	
	public ListColumn(String property, String title, String alias, String cell, String format) {
		super();
		this.alias = "";
		this.calc = "";
		this.calcTitle = "";
		this.cell = "";
		this.filterOptions = "";
		this.escapeAutoFormat = "";
		this.filterable = "";
		this.filterCell = "";
		this.filterClass = "";
		this.filterStyle = "";
		this.format = "";
		this.headerCell = "";
		this.headerClass = "";
		this.headerStyle = "";
		this.interceptor = "";
		this.parse = "";
		this.property = property;
		this.sortable = "";
		this.style = "";
		this.styleClass = "";
		this.title = title;
		this.value = "";
		this.viewsAllowed = "";
		this.viewsDenied = "";
		this.width = "";
		this.align = "";
		this.bundle = "";
		this.key = "";
	}
	
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getCalc() {
		return calc;
	}
	public void setCalc(String calc) {
		this.calc = calc;
	}
	public String getCalcTitle() {
		return calcTitle;
	}
	public void setCalcTitle(String calcTitle) {
		this.calcTitle = calcTitle;
	}
	public String getCell() {
		return cell;
	}
	public void setCell(String cell) {
		this.cell = cell;
	}
	public String getEscapeAutoFormat() {
		return escapeAutoFormat;
	}
	public void setEscapeAutoFormat(String escapeAutoFormat) {
		this.escapeAutoFormat = escapeAutoFormat;
	}
	public String getFilterable() {
		return filterable;
	}
	public void setFilterable(String filterable) {
		this.filterable = filterable;
	}
	public String getFilterCell() {
		return filterCell;
	}
	public void setFilterCell(String filterCell) {
		this.filterCell = filterCell;
	}
	public String getFilterClass() {
		return filterClass;
	}
	public void setFilterClass(String filterClass) {
		this.filterClass = filterClass;
	}
	public String getFilterOptions() {
		return filterOptions;
	}
	public void setFilterOptions(String filterOptions) {
		this.filterOptions = filterOptions;
	}
	public String getFilterStyle() {
		return filterStyle;
	}
	public void setFilterStyle(String filterStyle) {
		this.filterStyle = filterStyle;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getHeaderCell() {
		return headerCell;
	}
	public void setHeaderCell(String headerCell) {
		this.headerCell = headerCell;
	}
	public String getHeaderClass() {
		return headerClass;
	}
	public void setHeaderClass(String headerClass) {
		this.headerClass = headerClass;
	}
	public String getHeaderStyle() {
		return headerStyle;
	}
	public void setHeaderStyle(String headerStyle) {
		this.headerStyle = headerStyle;
	}
	public String getInterceptor() {
		return interceptor;
	}
	public void setInterceptor(String interceptor) {
		this.interceptor = interceptor;
	}
	public String getParse() {
		return parse;
	}
	public void setParse(String parse) {
		this.parse = parse;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getSortable() {
		return sortable;
	}
	public void setSortable(String sortable) {
		this.sortable = sortable;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getStyleClass() {
		return styleClass;
	}
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getViewsAllowed() {
		return viewsAllowed;
	}
	public void setViewsAllowed(String viewsAllowed) {
		this.viewsAllowed = viewsAllowed;
	}
	public String getViewsDenied() {
		return viewsDenied;
	}
	public void setViewsDenied(String viewsDenied) {
		this.viewsDenied = viewsDenied;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getAlign() {
		return align;
	}
	public void setAlign(String align) {
		this.align = align;
	}
	
	public String format(Object value) {
    	String result = StringUtils.toString(value);
		if (!"".equals(this.getFormat())) {
			String cell = this.getCell().toLowerCase();
			if ("number".equals(cell)) {
				try {
					result = NumberUtils.format(Double.parseDouble(result), this.getFormat());
				}
				catch(Exception e) {
				}
			}
			else if ("date".equals(cell)) {
				result = DateUtils.format((Date)value, this.getFormat());
			}
		}
		return result;
    }

	public String getBundle() {
		return bundle;
	}

	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getNewTitle() {
		return newTitle;
	}

	public void setNewTitle(String newTitle) {
		this.newTitle = newTitle;
	}

}
