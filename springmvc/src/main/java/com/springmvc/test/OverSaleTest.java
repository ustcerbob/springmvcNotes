package com.springmvc.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.springmvc.util.HttpUtils;

public class OverSaleTest {

	public static void main(String[] args) {
		ExecutorService executorService = Executors.newFixedThreadPool(20);
		//总共20个线程，并发为4
		for (int j = 0; j < 20; j++) {
			executorService.submit(new Runnable() {

				@Override
				public void run() {
					for (int i = 0; i < 10; i++) {
						// 每个线程发10次数量减一请求
						HttpUtils.httpGet("http://localhost:8089/skuInfo/overSale/1");
					}

				}
			});
		}

	}
}
