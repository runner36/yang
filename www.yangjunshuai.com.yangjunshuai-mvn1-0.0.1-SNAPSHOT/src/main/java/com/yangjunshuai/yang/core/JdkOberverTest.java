package com.yangjunshuai.yang.core;


public class JdkOberverTest {
	
	public static void main(String[] args) {
		
		SimpleObserable o = new SimpleObserable(1);
		o.addObserver(new SimpleListener());
		o.addObserver(new SimpleListener());
		System.out.println(o.countObservers());
		System.out.println(o.hasChanged());
		o.setState(2);
		o.notifyObservers(o);
		
	}

}
