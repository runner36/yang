package com.winchannel.base.utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
public class MapCache {
	private static String date;//日期标示
	private static MapCache mapCache;
	private static Map data=new HashMap();
	private static LinkedHashMap<String, LinkedHashMap<String,String>> linkHashMap = new LinkedHashMap<String, LinkedHashMap<String,String>>();

	
	private MapCache(){}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public static MapCache getInstance(){
		if(mapCache==null)
		{	
			mapCache =  new MapCache();
			data = new HashMap();
			linkHashMap=new LinkedHashMap<String, LinkedHashMap<String,String>>();
		}
		
		
			return mapCache;
	}
	
	public Map getData() {
		return data;
	}

	public void setData(Map data) {
		this.data = data;
	}

	public static LinkedHashMap<String, LinkedHashMap<String, String>> getLinkHashMap() {
		return linkHashMap;
	}

	public static void setLinkHashMap(
			LinkedHashMap<String, LinkedHashMap<String, String>> linkHashMap) {
		MapCache.linkHashMap = linkHashMap;
	}

	public void put(String key, Object value) {
		data.put(key, value);
	}
	public boolean containsKey(String key) {
		return data.containsKey(key);
	}
	
	public Object get(String key){
		return data.get(key);
	}
	public void destroy(){
		//mapCache=null;
		//data =null;
		System.gc();
	}
}
