package com.platform.vehicle_repair.service;

import com.platform.vehicle_repair.entity.Store;

import java.util.List;

public interface StoreService {
    /**
     * 展示全部店铺
     */
    List<Store> findAll();

    /**
     * 查找店铺详情
     */
    Store findOne(Integer storeId);

    /**
     * 根据名字模糊查找店铺
     */
    List<Store> findByStoreNameLike(String storeName);
}
