package com.god.article.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.god.article.bean.ArticleBean;
import com.god.article.dao.ArticleDao;
import com.god.redis.impl.RedisPool;
import com.god.util.SerializeUtil;

@Service
public class ArticleServiceImpl implements ArticleService {
	@Autowired
	ArticleDao articleDao;
	
	@Override
	public String add(ArticleBean bean) {
		return 	articleDao.add(bean);
	}

	@Override
	public ArticleBean find(String key) {
		ArticleBean bean = null;
		byte[] b = RedisPool.getResource().get(key.getBytes());
		bean = (ArticleBean) SerializeUtil.unserialize(b);
		return bean;
	}

	@Override
	public List<ArticleBean> list(ArticleBean bean) {
		return null;
	}

	public static void main(String[] args) {
		ArticleServiceImpl a = new ArticleServiceImpl();
		ArticleBean b = new ArticleBean();
		b.setContent("aaaaa");
		b.setName("johnsa");
		b.setUrl("bbbbb");
		a.add(b);
		System.out.println(a.find("johnsa").getName());
	}
}
