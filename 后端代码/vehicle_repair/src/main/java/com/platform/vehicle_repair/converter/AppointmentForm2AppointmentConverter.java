package com.platform.vehicle_repair.converter;

import com.platform.vehicle_repair.dto.RepairDTO;
import com.platform.vehicle_repair.entity.Appointment;
import com.platform.vehicle_repair.entity.Repair;
import com.platform.vehicle_repair.enums.ResultEnum;
import com.platform.vehicle_repair.exception.ServiceException;
import com.platform.vehicle_repair.form.AppointmentForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.sql.Date;
import java.sql.Timestamp;

@Slf4j
public class AppointmentForm2AppointmentConverter {
    public static Appointment convert(AppointmentForm form){
        Appointment appointment = new Appointment();
        BeanUtils.copyProperties(form,appointment);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        appointment.setTime(now);
        try{
            Timestamp appointTime = new Timestamp(form.getAppointTime().getTime());
            appointment.setAppointTime(appointTime);
        }catch (Exception e){
            log.error("【创建订单】预约时间错误");
            throw new ServiceException(ResultEnum.PARAM_ERROR);
        }

        return appointment;
    }
}
