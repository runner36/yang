package com.example.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/test2/login.do")  // ָ��Ψһһ��*.do�����������Controller
public class TestController2 {
	
	@RequestMapping
	public String testLogin(String username, String password, int age) {
		// ��������κβ�������������/test2/login.doʱ����Ĭ��ִ�и÷���
		
		if (!"admin".equals(username) || !"admin".equals(password) || age < 5) {
			return "loginError";
		}
		return "loginSuccess";
	}

	@RequestMapping(params = "method=1", method=RequestMethod.POST)
	public String testLogin2(String username, String password) {
		// ����params�Ĳ���method��ֵ�����ֲ�ͬ�ĵ��÷���
		// ����ָ��ҳ������ʽ�����ͣ�Ĭ��Ϊget����
		
		if (!"admin".equals(username) || !"admin".equals(password)) {
			return "loginError";
		}
		return "loginSuccess";
	}
	
	@RequestMapping(params = "method=2")
	public String testLogin3(String username, String password, int age) {
		if (!"admin".equals(username) || !"admin".equals(password) || age < 5) {
			return "loginError";
		}
		return "loginSuccess";
	}
}