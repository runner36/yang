package com.winchannel.core.id;

import java.util.HashMap;
import java.util.Map;

public class IdGeneratorFactory {
	
	private static Map<String, IdGenerator> longTimeIdGenerator = new HashMap<String, IdGenerator>();
	public static IdGenerator getLongTimeIdGenerator(String name) {
		if (!longTimeIdGenerator.containsKey(name)) {
			longTimeIdGenerator.put(name, new LongTimeIdGenerator());
		}
		return longTimeIdGenerator.get(name);
	}
	
	private static Map<String, IdGenerator> stringTimeIdGenerator = new HashMap<String, IdGenerator>();
	public static IdGenerator getStringTimeIdGenerator(String name) {
		if (!stringTimeIdGenerator.containsKey(name)) {
			stringTimeIdGenerator.put(name, new StringTimeIdGenerator());
		}
		return stringTimeIdGenerator.get(name);
	}
	
	private static Map<String, IdGenerator> upperStringTimeIdGenerator = new HashMap<String, IdGenerator>();
	public static IdGenerator getUpperStringTimeIdGenerator(String name) {
		if (!upperStringTimeIdGenerator.containsKey(name)) {
			upperStringTimeIdGenerator.put(name, new UpperStringTimeIdGenerator());
		}
		return upperStringTimeIdGenerator.get(name);
	}
	
}
