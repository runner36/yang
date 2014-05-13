package com.god.article.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import com.god.article.bean.ArticleBean;
import com.god.redis.impl.RedisPool;
import com.god.util.SerializeUtil;

public class ArticleServiceImpl0 implements ArticleService {

	@Override
	public String add(ArticleBean bean) {
		RedisPool.getResource().set(bean.getName().getBytes(),
				SerializeUtil.serialize(bean));
		return bean.getName();
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
		ArticleServiceImpl0 a = new ArticleServiceImpl0();
		ArticleBean b = new ArticleBean();
		b.setContent("aaaaa");
		b.setName("johnsa");
		b.setUrl("bbbbb");
		a.add(b);
		System.out.println(a.find("johnsa").getName());
	}
}
