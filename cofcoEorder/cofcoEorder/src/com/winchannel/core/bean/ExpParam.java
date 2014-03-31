package com.winchannel.core.bean;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.winchannel.core.utils.DateUtils;

public class ExpParam {
	
	private String id;
	private String name;
	private String template;
	private String initValue;
	private int offset;
	private String defaultValue;
	private String text;
	
	private String valueList;
	private String dataType;
	
	private String format;
	private String displayName;
	private String isDisplay;
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getValueList() {
		return valueList;
	}
	public void setValueList(String valueList) {
		this.valueList = valueList;
	}
	public String getInitValue() {
		String v = getValue(initValue, offset, format);
		if (v != null) {
			return v;
		}
		else {
			return initValue;
		}
	}
	public void setInitValue(String initValue) {
		this.initValue = initValue;
	}
	
	public String getDefaultValue() {
		String v = getValue(defaultValue, offset, format);
		if (v != null) {
			return v;
		}
		else {
			return defaultValue;
		}
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	
	
	public static String getValue(String v, int offset, String format) {
		long timeOffSet = 86400000 * offset;
		if (StringUtils.isBlank(format)) {
			format = "yyyy-MM-dd";
		}
		if ("_TODAY".equals(v)) {
			return DateUtils.format(new Date(new Date().getTime() + timeOffSet), format);
		}
		else if ("_YESTERDAY".equals(v)) {
			return DateUtils.format(new Date(DateUtils.yesterday().getTime() + timeOffSet), format);
		}
		else if ("_FIRST_DAY_OF_LAST_MONTH".equals(v)) {
			return DateUtils.format(new Date(DateUtils.getFirstDayOfLastMonth().getTime() + timeOffSet), format);
		}
		else if ("_FIRST_DAY_OF_MONTH".equals(v)) {
			return DateUtils.format(new Date(DateUtils.getFirstDayOfMonth(DateUtils.yesterday()).getTime() + timeOffSet), format);
		}
		else if ("_FIRST_DAY_OF_CURR_MONTH".equals(v)) {
			return DateUtils.format(new Date(DateUtils.getFirstDayOfMonth(new Date()).getTime() + timeOffSet), format);
		}
		else if ("_FIRST_DAY_OF_WEEK".equals(v)) {
			return DateUtils.format(new Date(DateUtils.getFirstDayOfWeek(DateUtils.yesterday()).getTime() + timeOffSet), format);
		}
		else if ("_FIRST_DAY_OF_CURR_WEEK".equals(v)) {
			return DateUtils.format(new Date(DateUtils.getFirstDayOfWeek(new Date()).getTime() + timeOffSet), format);
		}   
		else if ("_YEAR-MONTH".equals(v)) {
			return DateUtils.format(new Date(new Date().getTime() + timeOffSet), "yyyy-MM");
		}
		else {
			return null;
		}
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getIsDisplay() {
		return isDisplay;
	}
	public void setIsDisplay(String isDisplay) {
		this.isDisplay = isDisplay;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
