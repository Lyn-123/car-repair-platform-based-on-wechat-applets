package com.platform.vehicle_repair.dao;

import com.platform.vehicle_repair.entity.Parts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PartsRepository extends JpaRepository<Parts,Integer>{

    @Override
    Page<Parts> findAll(Pageable pageable);

    Page<Parts> findByStoreId(Integer storeId,Pageable pageable);

    Page<Parts> findByPartsNameLike(String partsName,Pageable pageable);

}
