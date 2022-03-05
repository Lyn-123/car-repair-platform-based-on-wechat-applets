package com.platform.vehicle_repair.dao;

import com.platform.vehicle_repair.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store,Integer> {
    @Override
    List<Store> findAll();

    List<Store> findByStoreNameLike(String StoreName);
}
