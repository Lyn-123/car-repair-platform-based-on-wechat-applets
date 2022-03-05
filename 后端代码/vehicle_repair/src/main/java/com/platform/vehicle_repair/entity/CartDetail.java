package com.platform.vehicle_repair.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Entity
public class CartDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    //用户ID
    String userId;
    //配件or服务ID
    Integer goodsId;
    //商品种类：0-服务，1-配件
    Integer type;
    //数量
    Integer number;
    //购买店铺ID
    Integer storeId;
}
