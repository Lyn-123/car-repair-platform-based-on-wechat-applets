package com.platform.vehicle_repair.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity
@DynamicUpdate
@DynamicInsert
public class Orders {
    //服务订单号
    @Id
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
}
