package com.winchannel.core.id;

class LongTimeIdGenerator extends IdGenerator {
	
	private long next = System.currentTimeMillis();

	public String generate() {
		return String.valueOf(next++);
	}

}
