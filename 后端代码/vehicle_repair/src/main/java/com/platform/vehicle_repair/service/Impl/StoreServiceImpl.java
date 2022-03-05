package com.platform.vehicle_repair.service.Impl;

import com.platform.vehicle_repair.dao.StoreRepository;
import com.platform.vehicle_repair.entity.Store;
import com.platform.vehicle_repair.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private StoreRepository storeRepository;

    /**
     * 展示全部店铺
     */
   public List<Store> findAll(){
        return storeRepository.findAll();
    }

    /**
     * 查找店铺详情
     */
    public Store findOne(Integer storeId){
        if(!storeRepository.findById(storeId).isPresent()){
            return null;
        }else{
            return storeRepository.findById(storeId).get();
        }
    }

    /**
     * 根据名字模糊查找店铺
     */
    public List<Store> findByStoreNameLike(String storeName){
        return storeRepository.findByStoreNameLike(storeName);
    }
}
