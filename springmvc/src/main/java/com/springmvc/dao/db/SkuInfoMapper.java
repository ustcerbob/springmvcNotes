package com.springmvc.dao.db;

import java.util.List;

import com.springmvc.bean.SkuInfo;

public interface SkuInfoMapper {
	SkuInfo selectById(String id);
	List<SkuInfo> selectAll();
	Integer updateById(SkuInfo skuInfo);
	Integer updateSkuNumById(String id);
}
