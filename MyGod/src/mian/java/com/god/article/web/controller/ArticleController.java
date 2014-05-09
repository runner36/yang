package com.god.article.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.god.article.bean.ArticleBean;

@Controller
@RequestMapping("/article/*")
public class ArticleController {

	@RequestMapping("list.do")
	// @ResponseBody
	public ModelAndView testLogin(ModelAndView mod) {
		List<ArticleBean> list = new ArrayList<ArticleBean>();
		ArticleBean a = null;
		for (int i = 0; i < 10; i++) {
			a = new ArticleBean();
			a.setName("name_00" + i);
			a.setContent("aaaaaaa" + i);
			a.setUrl("url_00" + i);
			list.add(a);
		}
//		Map<String, List<ArticleBean>> map = new HashMap<String, List<ArticleBean>>();
//		map.put("articles", list);
//		mod.setViewName("/article/articleList");
//		mod.addObject(list);
		return new ModelAndView("/article/articleList","list",list);
	}


}