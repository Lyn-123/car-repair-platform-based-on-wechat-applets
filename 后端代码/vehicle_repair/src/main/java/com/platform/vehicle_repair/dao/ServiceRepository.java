package com.platform.vehicle_repair.dao;

import com.platform.vehicle_repair.entity.Parts;
import com.platform.vehicle_repair.entity.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service,Integer> {

    @Override
    Page<Service> findAll(Pageable pageable);

    List<Service> findByStoreId(Integer storeId);

    Page<Service> findByServiceNameLike(String serviceName,Pageable pageable);

    List<Service> findByServiceType(Integer serviceType);

}
