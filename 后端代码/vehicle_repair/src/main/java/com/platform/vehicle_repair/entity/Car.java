package com.platform.vehicle_repair.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;


@Data
@Entity
public class Car {
    //车牌号
    @Id
    private String carId;
    //生产厂家（品牌）
    private String manufacturer;
    //汽车型号
    private String model;
    //发动机排量
    private String capacity;
    //生产年份
    private Integer year;
    //车主姓名
    private String carUser;
    //主人的id
    private String userId;
    //是否默认车辆
    private Integer isDefault;
}
