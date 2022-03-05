package com.platform.vehicle_repair.dao;

import com.platform.vehicle_repair.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,Integer> {
    List<OrderDetail> findByOrderId(String orderId);

}
