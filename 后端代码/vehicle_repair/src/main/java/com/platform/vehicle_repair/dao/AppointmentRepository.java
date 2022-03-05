package com.platform.vehicle_repair.dao;

import com.platform.vehicle_repair.entity.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface AppointmentRepository extends JpaRepository<Appointment,String> {

    Page<Appointment> findByUserId(String userId,Pageable pageable);

    @Transactional
    void deleteById(String appointId);

}
