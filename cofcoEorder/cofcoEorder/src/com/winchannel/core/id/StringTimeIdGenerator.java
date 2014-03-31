package com.winchannel.core.id;

import com.winchannel.core.utils.StringUtils;

class StringTimeIdGenerator extends IdGenerator {
	
	private long next = System.currentTimeMillis();

	public String generate() {
		return StringUtils.longToString(next++);
	}

}
