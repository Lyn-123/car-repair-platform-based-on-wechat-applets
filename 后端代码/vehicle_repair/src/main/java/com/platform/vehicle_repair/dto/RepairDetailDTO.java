package com.platform.vehicle_repair.dto;

import com.platform.vehicle_repair.entity.Parts;
import com.platform.vehicle_repair.entity.Service;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RepairDetailDTO {

    Integer repairDetailId;
    String repairId;

    Integer number;
    BigDecimal price;

    //根据id获取商品类信息
    Parts parts;
    Service service;
}
