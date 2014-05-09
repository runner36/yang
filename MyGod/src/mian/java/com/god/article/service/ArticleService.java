package com.god.article.service;

import java.util.List;

import com.god.article.bean.ArticleBean;

public interface ArticleService {
	
	public String add(ArticleBean bean);
	public ArticleBean find(String key);
	public List<ArticleBean> list(ArticleBean bean);
	

}
