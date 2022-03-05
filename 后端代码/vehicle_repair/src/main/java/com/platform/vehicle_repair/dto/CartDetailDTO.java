package com.platform.vehicle_repair.dto;

import com.platform.vehicle_repair.entity.Parts;
import com.platform.vehicle_repair.entity.Service;
import lombok.Data;

@Data
public class CartDetailDTO {
    Integer id;
    //数量
    Integer number;
    //购买店铺ID
    Integer storeId;
    String userId;
    //配件or服务
    Service service;
    Parts parts;
}
