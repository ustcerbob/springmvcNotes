package com.springmvc.dao.cache.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.springmvc.dao.cache.CacheDao;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public abstract class AbstractRedisDaoImpl implements CacheDao {

	@Autowired
	private JedisPool jedisPool;

	public Jedis getJedis() {
		return jedisPool.getResource();
	}

	public void returnResource(Jedis jedis) {
		if (jedis != null) {
			jedis.close();
		}
	}

}
