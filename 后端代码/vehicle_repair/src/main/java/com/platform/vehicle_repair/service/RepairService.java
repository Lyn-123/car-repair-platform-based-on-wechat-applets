package com.platform.vehicle_repair.service;

import com.platform.vehicle_repair.dto.RepairDTO;
import com.platform.vehicle_repair.entity.Repair;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RepairService {
    /*查询单个订单*/
    RepairDTO findOne(String repairId);

    /*批量查询订单*/
    Page<RepairDTO> findList(String userId,Pageable pageable);

    /*取消订单*/
    boolean cancelRepair(String repairId);

    /*完成服务*/
    boolean finishRepair(String repairId);

    List<Repair> findByStateIn(List<Integer> stateList);

}
