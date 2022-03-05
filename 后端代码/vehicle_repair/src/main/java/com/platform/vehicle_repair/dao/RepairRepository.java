package com.platform.vehicle_repair.dao;

import com.platform.vehicle_repair.entity.Repair;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RepairRepository extends JpaRepository<Repair,String> {
    List<Repair> findByStateIn(List<Integer> stateList);

    Page<Repair> findByUserId(String userId,Pageable pageable);

    Page<Repair> findAll(Pageable pageable);


}
