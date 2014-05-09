package com.god.redis.impl;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPool {
	public static class RedisPoolHolder{
		private static final JedisPool pool ;
		static{
				   JedisPoolConfig config = new JedisPoolConfig();
		            //����һ��pool�ɷ�����ٸ�jedisʵ����ͨ��pool.getResource()����ȡ��
		            //�����ֵΪ-1�����ʾ�����ƣ����pool�Ѿ�������maxActive��jedisʵ�������ʱpool��״̬Ϊexhausted(�ľ�)��
		            config.setMaxTotal(500);
		            //����һ��pool����ж��ٸ�״̬Ϊidle(���е�)��jedisʵ����
		            config.setMaxIdle(5);
		            //��ʾ��borrow(����)һ��jedisʵ��ʱ�����ĵȴ�ʱ�䣬��������ȴ�ʱ�䣬��ֱ���׳�JedisConnectionException��
		            config.setMaxWaitMillis(1000 * 100);
		            //��borrowһ��jedisʵ��ʱ���Ƿ���ǰ����validate���������Ϊtrue����õ���jedisʵ�����ǿ��õģ�
		            config.setTestOnBorrow(true);
		            pool = new JedisPool(config, "localhost", 6379);
		}
		
	}
	
	public static JedisPool getInstance() {
	   	return RedisPoolHolder.pool;
		
	}
	public static Jedis getResource(){
		return  getInstance().getResource();
	}
	
	public static void releaseResource(Jedis r){
		getInstance().returnResource(r);
	}
	
	public static void main(String[] args) {
		Jedis r = RedisPool.getResource();
		Executor e = Executors.newCachedThreadPool();
		int j =0;
		while(j <100){
			
			e.execute(new Runnable() {
				public void run() {
					for (int i = 0; i < 1000; i++) {
						Jedis r = RedisPool.getResource();
						
						if(r!=null){
							r.set("abc",i+"");
							System.out.println(r.get("abc"));
							releaseResource(r);
						}	
					}
				}
			});
			j++;
		}
		
	}
}
