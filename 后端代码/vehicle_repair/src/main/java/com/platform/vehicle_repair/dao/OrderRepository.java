package com.platform.vehicle_repair.dao;

import com.platform.vehicle_repair.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders,String> {

    List<Orders> findByUserId(String userId);

}
