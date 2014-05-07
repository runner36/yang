package com.yangjunshuai.yang.util;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPool {
	private static JedisPool pool = null;
	
	public static JedisPool getInstance() {
		
	   if (pool == null) {
		   JedisPoolConfig config = new JedisPoolConfig();
            //控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
            //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
            config.setMaxActive(500);
            //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
            config.setMaxIdle(5);
            //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
//            config.setMaxWait(1000 * 100);
            //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
            config.setTestOnBorrow(true);
            pool = new JedisPool(config, "localhost", 6379);
//            pool = new JedisPool(config,"192.168.2.191", 8888);
        }
	   	return pool;
		
	}
	
		
	public static Jedis getResource(){
		return  getInstance().getResource();
	}
	
	public static void releaseResource(Jedis r){
		getInstance().returnResource(r);
	}
	
	public static void main(String[] args) {
		Jedis r = RedisPool.getResource();
		if(r!=null){
			r.set("abc","12312312312312");
			System.out.println(r.get("abc"));
			releaseResource(r);
			
		}
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
