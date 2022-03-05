package com.platform.vehicle_repair.service.Impl;

import com.platform.vehicle_repair.dao.PartsRepository;
import com.platform.vehicle_repair.entity.Parts;
import com.platform.vehicle_repair.service.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PartServiceImpl implements PartService {

    @Autowired
    private PartsRepository partsRepository;

    /**
     * 按页显示全部配件
     */
    @Override
    public Page<Parts> findAll(Pageable pageable) {
        return partsRepository.findAll(pageable);
    }
    /**
     * 按照店铺显示全部配件
     */
    @Override
    public Page<Parts> findByStoreId(Integer storeId, Pageable pageable) {
        return partsRepository.findByStoreId(storeId,pageable);
    }
    /**
     * 查询单个配件信息
     */
    @Override
    public Parts findOne(Integer partsId) {
        return partsRepository.findById(partsId).get();
    }

    /**
     * 根据名字查询配件
     */
    @Override
    public Page<Parts> findByPartsNameLike(String partsName,Pageable pageable){
        return partsRepository.findByPartsNameLike(partsName,pageable);
    }

}
