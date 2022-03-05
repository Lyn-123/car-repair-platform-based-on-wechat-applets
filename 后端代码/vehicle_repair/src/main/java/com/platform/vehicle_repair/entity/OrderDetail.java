package com.platform.vehicle_repair.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
public class OrderDetail {
    //详情单ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer orderDetailId;
    //维修单号
    String orderId;
    // 数量
    Integer number;
    //单价
    BigDecimal price;
    // 配件or服务ID
    Integer goodsId;
    //商品种类：0-服务，1-配件
    Integer type;
}
