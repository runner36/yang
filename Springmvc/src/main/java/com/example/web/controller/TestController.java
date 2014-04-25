package com.example.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.example.bean.User;
import com.example.service.LoginService;

@Controller  //����Struts��Action

public class TestController {

	@RequestMapping("test/login.do")  // ����url��ַӳ�䣬����Struts��action-mapping
	@ResponseBody
	public String testLogin(@RequestParam(value="username")String username, String password, HttpServletRequest request) {
		// @RequestParam��ָ����url��ַӳ���б��뺬�еĲ���(��������required=false)
		// @RequestParam�ɼ�дΪ��@RequestParam("username")
		if (!"admin".equals(username) || !"admin".equals(password)) {
			return "loginError"; // ��תҳ��·����Ĭ��Ϊת��������·������Ҫ����spring-servlet�����ļ������õ�ǰ׺�ͺ�׺
		}
		
		
		return "loginSuccess";
	}

	@RequestMapping("/test/login2.do")
	public ModelAndView testLogin2(String username, String password, int age){
		// request��response���ط�Ҫ�����ڷ����У�����ò��ϵĻ�����ȥ��
		// ��������������ҳ��ؼ���name��ƥ�䣬�������ͻ��Զ���ת��
		
		if (!"admin".equals(username) || !"admin".equals(password) || age < 5) {
			return new ModelAndView("loginError"); // �ֶ�ʵ����ModelAndView�����תҳ�棨ת������Ч����ͬ������ķ��������ַ���
		}
		return new ModelAndView(new RedirectView("../index.jsp"));  // �����ض���ʽ��תҳ��
		// �ض�����һ�ּ�д��
		// return new ModelAndView("redirect:../index.jsp");
	}

	@RequestMapping("/test/login3.do")
	public ModelAndView testLogin3(User user) {
		// ͬ��֧�ֲ���Ϊ������������Struts��ActionForm��User����Ҫ�κ����ã�ֱ��д����
		String username = user.getUsername();
		String password = user.getPassword();
		int age = user.getAge();
		
		if (!"admin".equals(username) || !"admin".equals(password) || age < 5) {
			return new ModelAndView("loginError");
		}
		return new ModelAndView("loginSuccess");
	}

	@Resource(name = "loginService")  // ��ȡapplicationContext.xml��bean��idΪloginService�ģ���ע��
	private LoginService loginService;  //�ȼ���spring��ͳע�뷽ʽдget��set�����������ĺô��Ǽ�๤����ʡȥ�˲���Ҫ�ô���

	@RequestMapping("/test/login4.do")
	public String testLogin4(User user) {
		if (loginService.login(user) == false) {
			return "loginError";
		}
		return "loginSuccess";
	}
}