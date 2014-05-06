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

@Controller  //绫讳技Struts鐨凙ction

public class TestController {

	@RequestMapping("test/login.do")  // 璇锋眰url鍦板潃鏄犲皠锛岀被浼糞truts鐨刟ction-mapping
	@ResponseBody
	public String testLogin(@RequestParam(value="username")String username, String password, HttpServletRequest request) {
		// @RequestParam鏄寚璇锋眰url鍦板潃鏄犲皠涓繀椤诲惈鏈夌殑鍙傛暟(闄ら潪灞炴�required=false)
		// @RequestParam鍙畝鍐欎负锛欯RequestParam("username")
		if (!"admin".equals(username) || !"admin".equals(password)) {
			return "loginError"; // 璺宠浆椤甸潰璺緞锛堥粯璁や负杞彂锛夛紝璇ヨ矾寰勪笉闇�鍖呭惈spring-servlet閰嶇疆鏂囦欢涓厤缃殑鍓嶇紑鍜屽悗缂�		
			}
		return "loginSuccess";
	}

	@RequestMapping("/test/login2.do")
	public ModelAndView testLogin2(String username, String password, int age){
		// request鍜宺esponse涓嶅繀闈炶鍑虹幇鍦ㄦ柟娉曚腑锛屽鏋滅敤涓嶄笂鐨勮瘽鍙互鍘绘帀
		// 鍙傛暟鐨勫悕绉版槸涓庨〉闈㈡帶浠剁殑name鐩稿尮閰嶏紝鍙傛暟绫诲瀷浼氳嚜鍔ㄨ杞崲
		
		if (!"admin".equals(username) || !"admin".equals(password) || age < 5) {
			return new ModelAndView("loginError"); // 鎵嬪姩瀹炰緥鍖朚odelAndView瀹屾垚璺宠浆椤甸潰锛堣浆鍙戯級锛屾晥鏋滅瓑鍚屼簬涓婇潰鐨勬柟娉曡繑鍥炲瓧绗︿覆
		}
		return new ModelAndView(new RedirectView("../index.jsp"));  // 閲囩敤閲嶅畾鍚戞柟寮忚烦杞〉闈�		// 閲嶅畾鍚戣繕鏈変竴绉嶇畝鍗曞啓娉�		// return new ModelAndView("redirect:../index.jsp");
	}

	@RequestMapping("/test/login3.do")
	public ModelAndView testLogin3(User user) {
		// 鍚屾牱鏀寔鍙傛暟涓鸿〃鍗曞璞★紝绫讳技浜嶴truts鐨凙ctionForm锛孶ser涓嶉渶瑕佷换浣曢厤缃紝鐩存帴鍐欏嵆鍙�		
		String username = user.getUsername();
		String password = user.getPassword();
		int age = user.getAge();
		
		if (!"admin".equals(username) || !"admin".equals(password) || age < 5) {
			return new ModelAndView("loginError");
		}
		return new ModelAndView("loginSuccess");
	}

	@Resource(name = "loginService")  // 鑾峰彇applicationContext.xml涓璪ean鐨刬d涓簂oginService鐨勶紝骞舵敞鍏�	
	private LoginService loginService;  //绛変环浜巗pring浼犵粺娉ㄥ叆鏂瑰紡鍐檊et鍜宻et鏂规硶锛岃繖鏍风殑濂藉鏄畝娲佸伐鏁达紝鐪佸幓浜嗕笉蹇呰寰椾唬鐮�
	
	
	@RequestMapping("/test/login4.do")
	public String testLogin4(User user) {
		if (loginService.login(user) == false) {
			return "loginError";
		}
		return "loginSuccess";
	}
}