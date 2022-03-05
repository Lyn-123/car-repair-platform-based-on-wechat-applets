package com.platform.vehicle_repair.controller;

import com.platform.vehicle_repair.VO.ResultVO;
import com.platform.vehicle_repair.entity.Store;
import com.platform.vehicle_repair.service.StoreService;
import com.platform.vehicle_repair.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @GetMapping("/all")
    public ResultVO<List<Store>> findAllStore(){
        return ResultVOUtil.success(storeService.findAll());
    }

    @GetMapping("/detail")
    public ResultVO<Store> findStore(@RequestParam("storeId") Integer storeId){

        return ResultVOUtil.success(storeService.findOne(storeId));
    }

    //搜索店铺名
    @GetMapping("/find")
    public ResultVO<List<Store>> findByStoreName(@RequestParam("storeName") String storeName){
        //搜索字符串为空，返回空
        if(storeName == null ||storeName.length()<=0){
            return ResultVOUtil.success();
        }else{
            //字符串不为空，返回搜索结果
            List<Store> storeList = storeService.findByStoreNameLike("%"+storeName+"%");
            return ResultVOUtil.success(storeList);
        }
    }

    
}
