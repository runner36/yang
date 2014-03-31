package com.winchannel.core.id;

import com.winchannel.core.utils.StringUtils;

class UpperStringTimeIdGenerator extends IdGenerator {
	
	private long next = System.currentTimeMillis();

	public String generate() {
		return StringUtils.longToUpperString(next++);
	}

}
