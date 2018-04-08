package com.springmvc.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springmvc.bean.SkuInfo;
import com.springmvc.exception.IllegalArgException;
import com.springmvc.exception.OverSaleException;
import com.springmvc.service.SkuInfoService;


@Controller
@RequestMapping("/skuInfo")
public class SkuInfoHandler {

	@Autowired
	private SkuInfoService skuInfoService;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<SkuInfo>> getAll() {

		return ResponseEntity.ok(skuInfoService.getAll());
	}

	@ResponseBody
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<SkuInfo> getById(@PathVariable String id) {
		if (id.equals("a")) {
			throw new IllegalArgException("id 不能为a，模拟统一处理异常[IllegalArgsException]");
		}
		if (id.equals("b")) {
			throw new OverSaleException("id 不能为b，模拟统一处理异常[OverSaleException]");
		}
		if (id.equals("c")) {
			throw new RuntimeException("id 不能为c，模拟统一处理异常[OtherException]");
		}
		return ResponseEntity.ok(skuInfoService.getById(id));
	}

	/**
	 * 验证了ResponseBody注解可以加在void方法上，但是没有实际意义，开发中不会用
	 * 验证了高并发下通过sku_num>0的条件可以解决oversale问题。
	 * 一个线程在写商品数量，另一个线程只能等待，即使A线程已经写完，在空等待，事务没有提交，B线程的写操作也得等待。
	 * @param id
	 * @throws InterruptedException
	 */
	/*
	 * 启动了20个线程同时扣减商品，第一个进去后锁定商品，对于写操作时排斥的，（用的悲观锁），但对于读操作时可以并发的，用的乐观锁。
	 * 
	 * http-bio-8089-exec-18请求商品数量-1
	http-bio-8089-exec-18减商品前的数量:2
	http-bio-8089-exec-18进行update操作等待了1ms，成功扣减商品
	http-bio-8089-exec-16请求商品数量-1
	http-bio-8089-exec-12请求商品数量-1
	http-bio-8089-exec-16减商品前的数量:2
	http-bio-8089-exec-12减商品前的数量:2
	http-bio-8089-exec-19请求商品数量-1
	http-bio-8089-exec-13请求商品数量-1
	http-bio-8089-exec-19减商品前的数量:2
	http-bio-8089-exec-13减商品前的数量:2
	http-bio-8089-exec-15请求商品数量-1
	http-bio-8089-exec-15减商品前的数量:2
	http-bio-8089-exec-14请求商品数量-1
	http-bio-8089-exec-14减商品前的数量:2*/
	@ResponseBody
	@RequestMapping(value = "/overSale/{id}", method = RequestMethod.GET)
	public Integer solveOverSale(@PathVariable String id) throws InterruptedException {
		return skuInfoService.updateSkuNumForOverSale(id);
	}

	@ResponseBody
	@RequestMapping(value = "/transaction/{id}", method = RequestMethod.GET)
	public void checkTransaction(@PathVariable String id) {
		skuInfoService.updateSkuNumForTransaction(id);
		
	}
	
	/**
	 * 缓存雪崩
	 * 从redis中取商品信息，没拿到从数据库取，但是某一时刻多个线程读取redis，都没拿到，都去数据库取，造成了雪崩
	 * @param id
	 * setnx适用于只让多个线程有一个能够抢到锁执行数据库操作
	 * 对于秒杀适合用redis事务，因为多个线程都要执行redis操作，只是让他们串行执行
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/cacheCrash/{id}", method = RequestMethod.GET)
	public SkuInfo cacheCrash(@PathVariable String id) throws Exception {
		return skuInfoService.getByRedis(id);
		
	}

}
