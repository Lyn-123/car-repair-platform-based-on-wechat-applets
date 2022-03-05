package com.platform.vehicle_repair.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
public class Service {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer serviceId;
    private String serviceName;
    private BigDecimal price;
    private Integer storeId;
    private Integer serviceType;
    private BigDecimal rating;
}
