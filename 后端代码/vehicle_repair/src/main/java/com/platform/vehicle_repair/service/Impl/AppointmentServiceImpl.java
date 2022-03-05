package com.platform.vehicle_repair.service.Impl;

import com.mysql.cj.util.StringUtils;
import com.platform.vehicle_repair.dao.AppointmentRepository;
import com.platform.vehicle_repair.entity.Appointment;
import com.platform.vehicle_repair.service.AppointmentService;
import com.platform.vehicle_repair.util.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    /**
     * 创建或修改预约订单
     */
    @Override
    public Appointment createOrUpdate(Appointment appointment) {
        if(StringUtils.isNullOrEmpty(appointment.getAppointId())){
            appointment.setAppointId(KeyUtil.geUniqueKey());
        }
        Appointment result = appointmentRepository.save(appointment);
        return result;
    }

    /**
     * 查询单个预约订单
     */
    @Override
    public Appointment findOne(String appointId) {
        return appointmentRepository.findById(appointId).get();
    }

    /**
     * 批量查询预约订单
     */
    @Override
    public Page<Appointment> findByUserId(String userId, Pageable pageable) {
        return appointmentRepository.findByUserId(userId,pageable);
    }

    /**
     * 删除预约维修订单
     */
    @Override
    public void deleteByAppointId(String appointId) {
        appointmentRepository.deleteById(appointId);
    }
}
