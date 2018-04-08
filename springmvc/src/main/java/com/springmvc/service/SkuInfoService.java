package com.springmvc.service;

import java.util.List;

import com.springmvc.bean.SkuInfo;

public interface SkuInfoService {

	List<SkuInfo> getAll();

	SkuInfo getById(String id);

	Integer updateById(SkuInfo skuInfo);

	void updateSkuNumForTransaction(String id);

	Integer updateSkuNumForOverSale(String id) throws InterruptedException;

	SkuInfo getByRedis(String id) throws Exception;

	
}
