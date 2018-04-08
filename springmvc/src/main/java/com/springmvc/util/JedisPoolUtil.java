package com.springmvc.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolUtil {
	private static volatile JedisPool jedisPool = null;
 

	private JedisPoolUtil() {
	}

	private static JedisPool getJedisPoolInstance() {
		if (null == jedisPool) {
			synchronized (JedisPoolUtil.class) {
				if (null == jedisPool) {
					JedisPoolConfig poolConfig = new JedisPoolConfig();
					poolConfig.setMaxTotal(10);
					poolConfig.setMaxIdle(3);
					poolConfig.setMaxWaitMillis(100*1000);
					poolConfig.setBlockWhenExhausted(true);
					poolConfig.setTestOnBorrow(true);
				 
					jedisPool = new JedisPool(poolConfig, "192.168.153.147", 6379, 60000);
			 
				}
			}
		}
		return jedisPool;
	}

	public static void release(Jedis jedis) {
		if (null != jedis) {
			getJedisPoolInstance().close();
		}
	}
	
	public static Jedis getJedis(){
		return getJedisPoolInstance().getResource();
	}
	
	public static void main(String[] args) {
		/*for(int i = 0; i<20; i++){
			Jedis jedis = getJedis();
			System.out.println("第"+i+"个jedis实例，"+jedis);
			jedis.close();
		}*/
		
		ExecutorService threadPool = Executors.newFixedThreadPool(5);
		for(int i = 0; i<20; i++){
			threadPool.submit(new Runnable() {
				
				@Override
				public void run() {
					Jedis jedis = getJedis();
					System.out.println(Thread.currentThread().getName()+"--jedis实例，"+jedis);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					jedis.close();
				}
			});
			
		}
		threadPool.shutdown();
		/*Jedis jedis = new Jedis("192.168.153.147",6379);
		System.out.println(jedis.ping());*/
	}
	
	

}
