package com.winchannel.core.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;


public class Constants {
	
	public static final Map<String, String> QUERY_EXP = new HashMap<String, String>();
	static {
		QUERY_EXP.put("$eq", "='?'");
		QUERY_EXP.put("$ne", "<>'?'");
		QUERY_EXP.put("$lt", "<'?'");
		QUERY_EXP.put("$gt", ">'?'");
		QUERY_EXP.put("$le", "<='?'");
		QUERY_EXP.put("$ge", ">='?'");
		QUERY_EXP.put("$lk", " like '%?%'");
		QUERY_EXP.put("$lkl", " like '?%'");
		QUERY_EXP.put("$lkr", " like '%?'");
		QUERY_EXP.put("$in", " in (?)");
		QUERY_EXP.put("$nl", " is null");
	}
	
	public final static Pattern PATTERN_DATE = Pattern.compile("[0-9]{4}\\-[0-9]{1,2}\\-[0-9]{1,2}");

}
