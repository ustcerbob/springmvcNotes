package com.springmvc.dao.cache.impl;

import org.springframework.stereotype.Repository;

import com.springmvc.function.RedisFunction;

import redis.clients.jedis.Jedis;


@Repository
public class RedisDaoImpl extends AbstractRedisDaoImpl{

	private <R> R cacheTemplate(RedisFunction<Jedis, R> function){
		Jedis jedis = getJedis();
		R result = function.cacheOp(jedis);
		returnResource(jedis);
		return result;
	}
	
	@Override
	public String get(String key) {
		return cacheTemplate(new RedisFunction<Jedis, String>() {

			@Override
			public String cacheOp(Jedis t) {
				return t.get(key);
			}
			
		});
	}

	@Override
	public String set(String key, String value) {
		return cacheTemplate(new RedisFunction<Jedis, String>() {

			@Override
			public String cacheOp(Jedis t) {
				return t.set(key, value);
			}
			
		});
		
	}

	@Override
	public boolean exist(String key) {
		return cacheTemplate(new RedisFunction<Jedis, Boolean>() {

			@Override
			public Boolean cacheOp(Jedis t) {
				return t.exists(key);
			}
			
		});
	}

	@Override
	public boolean setnx(String key, String value) {
		return cacheTemplate(new RedisFunction<Jedis, Boolean>() {

			@Override
			public Boolean cacheOp(Jedis t) {
				return t.setnx(key, value) == 1;
			}
			
		});
	}

	@Override
	public Long expire(String key, int expire) {
		return cacheTemplate(new RedisFunction<Jedis, Long>() {

			@Override
			public Long cacheOp(Jedis t) {
				return t.expire(key, expire);
			}
			
		});
	}

	@Override
	public Long del(String key) {
		return cacheTemplate(new RedisFunction<Jedis, Long>() {

			@Override
			public Long cacheOp(Jedis t) {
				return t.del(key);
			}
		});
	}

}
