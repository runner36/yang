package com.example.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test3/*")  // 父request请求url
public class TestController3 {

	@RequestMapping("login.do")  // 子request请求url，拼接后等价于/test3/login.do
	public String testLogin(String username, String password, int age) {
		if (!"admin".equals(username) || !"admin".equals(password) || age < 5) {
			return "loginError";
		}
		return "loginSuccess";
	}
}