package redis;

import redis.clients.jedis.Jedis;

public class MainRedis {

	public static void main(String[] args) {
		
		Jedis jedis = new Jedis("localhost");
		jedis.set("a", "aaaa");
		jedis.set("b", "ccc");
		System.out.println(jedis.get("a"));
		System.out.println(jedis.get("b"));
//		Jedis j
	}
}
