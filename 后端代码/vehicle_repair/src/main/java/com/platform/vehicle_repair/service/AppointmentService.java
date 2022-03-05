package com.platform.vehicle_repair.service;

import com.platform.vehicle_repair.dto.RepairDTO;
import com.platform.vehicle_repair.entity.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AppointmentService {

    /**
     * 创建或修改预约订单
     */
    Appointment createOrUpdate(Appointment appointment);

    /**
     * 查询单个预约订单
     */
    Appointment findOne(String appointId);

    /**
     * 批量查询预约订单
     */
    Page<Appointment> findByUserId(String userId,Pageable pageable);

    /**
     * 删除预约维修订单
     */
    void deleteByAppointId(String appointId);

}
