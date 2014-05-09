package com.god.article.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import com.god.article.bean.ArticleBean;
import com.god.redis.impl.RedisPool;

public class ArticleServiceImpl implements ArticleService {

	@Override
	public String add(ArticleBean bean) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(out);
			oos.writeObject(bean);
			oos.flush();
			oos.close();
		RedisPool.getResource().set(bean.getName().getBytes(),out.toByteArray());
		out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bean.getName();
	}

	@Override
	public ArticleBean find(String key) {
		ObjectInputStream ois = null;
		ByteArrayInputStream bi = null;
		ArticleBean bean = null;
		try {
			byte[] b = RedisPool.getResource().get(key.getBytes());
			bi = new ByteArrayInputStream(b,0,b.length);
			ois = new ObjectInputStream(bi);
			bean = (ArticleBean) ois.readObject();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally{
			try {
				bi.close();
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
		a.add(b );
		System.out.println(a.find("johnsa").getName());
	}
}
