package com.god.redis.impl;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;

import redis.clients.jedis.Jedis;

class JedisFactory implements PooledObjectFactory<Jedis> {

	@Override
	public PooledObject<Jedis> makeObject() throws Exception {
		return null;
	}

	@Override
	public void destroyObject(PooledObject<Jedis> p) throws Exception {
		
	}

	@Override
	public boolean validateObject(PooledObject<Jedis> p) {
		return false;
	}

	@Override
	public void activateObject(PooledObject<Jedis> p) throws Exception {
		
	}

	@Override
	public void passivateObject(PooledObject<Jedis> p) throws Exception {
		
	}
	
}