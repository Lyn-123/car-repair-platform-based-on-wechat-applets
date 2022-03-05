package com.platform.vehicle_repair.dao;

import com.platform.vehicle_repair.entity.RepairDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepairDetailRepository extends JpaRepository<RepairDetail,Integer> {
    List<RepairDetail> findByRepairId(String repairId);

}
