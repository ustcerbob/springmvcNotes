package com.springmvc.dao.cache;

public interface CacheDao {

	String get(String key);
	
	String set(String key, String value);
	
	boolean exist(String key);

	boolean setnx(String key, String value);

	Long expire(String key, int expire);

	Long del(String key);
}
