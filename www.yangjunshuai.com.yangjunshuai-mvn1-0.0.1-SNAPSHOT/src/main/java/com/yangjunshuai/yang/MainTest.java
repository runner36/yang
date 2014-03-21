package com.yangjunshuai.yang;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainTest {

	
	public static void main(String[] args) {
		ApplicationContext  cxt = new ClassPathXmlApplicationContext("ApplicationContext.xml");
		System.out.println(cxt.getBean("appBean").toString());
	}
}
