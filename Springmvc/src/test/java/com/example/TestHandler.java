package com.example;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Date;
 
class TestHandler implements InvocationHandler {
	 // 被代理的对象
    private Object target;
 
    public TestHandler(Object target) {
        this.target = target;
    }
	
    @Override
    public Object invoke(Object proxy, Method method, Object[] args)throws Throwable {
    	
    	long d1 = System.currentTimeMillis();
        Object returnValue = method.invoke(target, args);
        long d2 = System.currentTimeMillis();
        System.out.println(method.getName()+"用时"+(d2 - d1)+"ms");
        
        return returnValue;
    }
}