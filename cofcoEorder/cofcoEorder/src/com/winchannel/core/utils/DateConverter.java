package com.winchannel.core.utils;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 简易DateConverter. 供Apache BeanUtils 做转换,默认时间格式为yyyy-MM-dd,可由构造函数改变.
 */
public class DateConverter implements Converter {
	
	private static final Log log = LogFactory.getLog(DateConverter.class);

	private SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat format3 = new SimpleDateFormat("yyyy.MM.dd");
	private SimpleDateFormat format4 = new SimpleDateFormat("yyyy/MM/dd");
	private SimpleDateFormat format5 = new SimpleDateFormat("yyyyMMdd");

	public DateConverter(String formatPattern) {
//		if (StringUtils.isNotBlank(formatPattern)) {
//			format = new SimpleDateFormat(formatPattern);
//		}
	}

	public Object convert(Class arg0, Object value) {
		String dateStr = (String) value;
		if (StringUtils.isBlank(dateStr)) {
			return null;
		}
		try {
			return format1.parse(dateStr);
		}
		catch (Exception e) {
		}
		try {
			return format2.parse(dateStr);
		}
		catch (Exception e) {
		}
		try {
			return format3.parse(dateStr);
		}
		catch (Exception e) {
		}
		try {
			return format4.parse(dateStr);
		}
		catch (Exception e) {
		}
		if (dateStr.length() == 8) {
			try {
				return format5.parse(dateStr);
			}
			catch (Exception e) {
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		DateConverter c = new DateConverter("");
		System.out.println(((Date) c.convert(null, "20100228")).toLocaleString());
	}


}
