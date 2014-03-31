package com.winchannel.core.id;

import com.winchannel.core.utils.StringUtils;

public abstract class IdGenerator {
	
	public abstract String generate();
	
	public String generate(int length, boolean alignRight, char fill) {
		return format(generate(), length, alignRight, fill);
	}
	
	public String generate(int length, boolean alignRight) {
		return format(generate(), length, alignRight, '0');
	}
	
	public String generate(int length, char fill) {
		return format(generate(), length, false, fill);
	}
	
	public String generate(int length) {
		return format(generate(), length, false, '0');
	}
	
	private String format(String id, int length, boolean alignRight, char fill) {
		if (id.length() < length) {
			return StringUtils.format(id, length, false, fill);
		}
		return id;
	}
	
}
