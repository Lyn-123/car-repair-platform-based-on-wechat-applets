package com.platform.vehicle_repair.service;

import com.platform.vehicle_repair.dto.OrderDTO;

import java.util.List;


public interface OrderService {
    /**
     * 查询个人全部预约保养单列表
     */
    List<OrderDTO> findByUserId(String userId);

    /**
     * 查询预约保养单详情
     */
    OrderDTO findOne(String orderId);

    /**
     * 取消预约保养单
     */
    boolean cancelOrders(String orderId);

    /**
     * 结束预约保养单
     */
    boolean finishOrders(String orderId);

    /**
     * 新建预约保养单
     */
    String addOrders(OrderDTO orderDTO);
}
