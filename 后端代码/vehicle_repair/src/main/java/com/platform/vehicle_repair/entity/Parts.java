package com.platform.vehicle_repair.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Entity
public class Parts {
    //零件ID
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer partsId;
    //零件名
    String partsName;
    //车型
    String model;
    //价格
    BigDecimal price;
    //库存
    Integer stock;
    //介绍详情
    String details;
    //图片
    String picture;
    //店铺ID
    Integer storeId;
}
