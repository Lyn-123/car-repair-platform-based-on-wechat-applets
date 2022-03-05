package com.platform.vehicle_repair.dto;

import com.platform.vehicle_repair.entity.Parts;
import com.platform.vehicle_repair.entity.Service;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDetailDTO {
    //详情单ID
    Integer orderDetailId;
    //维修单号
    String orderId;
    // 数量
    Integer number;
    //单价
    BigDecimal price;
    //商品
    Parts parts;
    Service service;
}
