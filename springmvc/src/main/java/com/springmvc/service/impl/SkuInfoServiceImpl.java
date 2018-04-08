package com.springmvc.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.springmvc.bean.SkuInfo;
import com.springmvc.dao.cache.CacheDao;
import com.springmvc.dao.db.SkuInfoMapper;
import com.springmvc.service.SkuInfoService;
import com.springmvc.util.JacksonUtil;
import com.springmvc.util.JedisPoolUtil;

import redis.clients.jedis.Jedis;

@Service
public class SkuInfoServiceImpl implements SkuInfoService{

	@Autowired
	private SkuInfoMapper skuInfoMapper;
	
	@Autowired
	private CacheDao cacheDao;
	
	@Override
	public List<SkuInfo> getAll() {
		return skuInfoMapper.selectAll();
	}

	@Override
	public SkuInfo getById(String id) {
		return skuInfoMapper.selectById(id);
	}

	@Override
	public Integer updateById(SkuInfo skuInfo) {
		return skuInfoMapper.updateById(skuInfo);
	}

	@Override
	public void updateSkuNumForTransaction(String id) {
		skuInfoMapper.updateSkuNumById(id);
		skuInfoMapper.updateSkuNumById(id);
		if (true) {
			throw new RuntimeException("检测事务抛出的运行时异常");
		}
		skuInfoMapper.updateSkuNumById(id);
		
	}

	@Override
	public Integer updateSkuNumForOverSale(String id) throws InterruptedException {
		System.out.println(Thread.currentThread().getName() + "请求商品数量-1");
		SkuInfo skuInfo = skuInfoMapper.selectById(id);
		System.out.println(Thread.currentThread().getName() + "减商品前的数量:"+skuInfo.getSkuNum());
		long start = System.currentTimeMillis();
		int result = skuInfoMapper.updateSkuNumById(id);
		long end = System.currentTimeMillis();
		System.out.println(Thread.currentThread().getName() + "进行update操作等待了"+(end-start)+"ms，"+(result==0?"没有":"")+"成功扣减商品");
		Thread.sleep(10*1000);
		System.out.println(Thread.currentThread().getName() + "请求处理完毕");
		return result;
	}

	@Override
	public SkuInfo getByRedis(String id) throws Exception {
		
		String key = "sku_info:sku_id:"+id;
		String skuInfoCache = cacheDao.get(key);
		if(skuInfoCache!=null && !skuInfoCache.equals("")){
			System.out.println(Thread.currentThread().getName()+"从cache中取出skuInfo" + skuInfoCache);
			return JacksonUtil.json2Object(skuInfoCache, SkuInfo.class);
		}
		//只允许一个线程获得锁，只操作一次数据库
		if(cacheDao.setnx("mutex","mutex_value")){
			cacheDao.expire("mutex",1);
			System.out.println(Thread.currentThread().getName()+"获得mutex锁");
			SkuInfo skuInfo = skuInfoMapper.selectById(id);
			System.out.println("从db中取出skuInfo" + skuInfo);
			cacheDao.set(key, JacksonUtil.object2Json(skuInfo));
			System.out.println("放入缓存");
			cacheDao.del("mutex");
			System.out.println("释放mutex锁");
			return skuInfo;
		}else{
			//休息一会，一直重试，直到从缓存拿到数据
			while(true){
				Thread.sleep(100);
				System.out.println(Thread.currentThread().getName()+"没有获得锁，重试");
				return getById(id);
			}
		}
		
	}

}
