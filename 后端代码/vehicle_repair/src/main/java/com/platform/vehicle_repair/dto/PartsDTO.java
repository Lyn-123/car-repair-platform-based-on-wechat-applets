package com.platform.vehicle_repair.dto;

import com.platform.vehicle_repair.entity.Store;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PartsDTO {
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
    //店铺ID获取store
    Store store;
}
