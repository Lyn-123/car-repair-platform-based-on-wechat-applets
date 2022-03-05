package com.platform.vehicle_repair.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
public class RepairDetail {
    @Id
    Integer repairDetailId;
    String repairId;
    Integer partsId;
    Integer serviceId;
    Integer number;
    BigDecimal price;
}
