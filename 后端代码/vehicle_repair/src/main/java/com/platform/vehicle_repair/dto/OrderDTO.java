package com.platform.vehicle_repair.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
public class OrderDTO {
    //服务订单号
    String orderId;
    //订单状态(默认0)
    Integer state;
    //店铺ID
    Integer storeId;
    //下单用户ID
    String  userId;
    //创建时间
    Timestamp establishTime;
    //更新时间
    Timestamp updateTime;
    //用户名
    String userName;
    //订单总价
    BigDecimal price;
    //商品列表
    List<OrderDetailDTO> orderDetailDTOList;
}
