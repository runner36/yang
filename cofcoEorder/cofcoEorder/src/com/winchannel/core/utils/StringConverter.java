package com.winchannel.core.utils;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.Converter;

public final class StringConverter implements Converter {

	public StringConverter() {
	}

	public Object convert(Class type, Object value) {
		if (value == null || "".equals(value.toString())) {
			return (String) null;
		} 
		else if (value instanceof Date) {
			return new SimpleDateFormat("yyyy-MM-dd").format(value);
		}
		else {
			return value.toString();
		}
	}
}

