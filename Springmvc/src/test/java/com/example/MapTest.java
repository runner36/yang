package com.example;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MapTest implements ITest {
	Map tmap = null;
	Map hmap = null;

	public MapTest() {
		super();
		this.tmap = new TreeMap();
		this.hmap = new HashMap();
	}
	@Override
	public void putT(int len) {
		for (int i = 0; i < len; i++) {
			tmap.put(i, i);
		}
	}

	@Override
	public void putH(int len) {
		for (int i = 0; i < len; i++) {
			tmap.put(i, i);
		}
	}
	
	
	public void getH(int len) {
		for (int i = 0; i < len; i++) {
			hmap.get(i);
		}
	}
	@Override
	public void getT(int len) {
		for (int i = 0; i < len; i++) {
			tmap.get(i);
		}
		
	}

	

}
