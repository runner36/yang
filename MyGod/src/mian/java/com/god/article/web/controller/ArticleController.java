package com.god.article.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.god.article.bean.ArticleBean;
import com.god.article.service.ArticleService;

@Controller
@RequestMapping("/article/*")
public class ArticleController {
	@Autowired
	private ArticleService articleService;

	@RequestMapping("list.do")
	public ModelAndView list(ModelAndView mod) {
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


	@RequestMapping("add.do")
	public ModelAndView add(ArticleBean article) {
		
		ArticleBean  bean = new ArticleBean();
		bean.setName("china");
		bean.setContent("china is a good country");
		articleService.add(bean);
		return new ModelAndView("/article/articleList");
	}

}