package com.example;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class MainTest {
	public static void main(String[] args) {
		ITest test = new MapTest();
		InvocationHandler h = new TestHandler(test);
		Class[] interfaces = {ITest.class};
		test  = (ITest) Proxy.newProxyInstance(MapTest.class.getClassLoader(),
				MapTest.class.getInterfaces(), h);
		
		int len =10000000;
		test.putH(len);
		test.putT(len);
		test.getH(len);
		test.getT(len); //10000000 -- 
	}

}
