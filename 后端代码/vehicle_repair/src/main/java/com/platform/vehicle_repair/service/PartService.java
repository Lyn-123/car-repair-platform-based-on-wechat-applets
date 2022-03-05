package com.platform.vehicle_repair.service;


import com.platform.vehicle_repair.entity.Parts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PartService {
    /**
     * 按页显示全部配件
     */
    Page<Parts> findAll(Pageable pageable);

    /**
     * 按照店铺显示全部配件
     */
    Page<Parts> findByStoreId(Integer storeId,Pageable pageable);

    /**
     * 查询单个配件信息
     */
    Parts findOne(Integer partsId);

    /**
     * 根据名字查询配件
     */
    Page<Parts> findByPartsNameLike(String partsName,Pageable pageable);

}
