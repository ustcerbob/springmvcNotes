package com.springmvc.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.springmvc.util.HttpUtils;

public class CacheCrashTest {

	public static void main(String[] args) {
		ExecutorService executorService = Executors.newFixedThreadPool(20);
		// 总共20个线程，请求数200个
		for (int j = 0; j < 200; j++) {
			executorService.submit(new Runnable() {

				@Override
				public void run() {

					HttpUtils.httpGet("http://localhost:8089/skuInfo/cacheCrash/1");

				}
			});
		}
		
		executorService.shutdown();

	}
}
