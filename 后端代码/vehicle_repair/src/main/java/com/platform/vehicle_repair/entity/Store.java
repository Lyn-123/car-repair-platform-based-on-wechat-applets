package com.platform.vehicle_repair.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Store {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer storeId ;
    String storeName;
    String address;
    String tel;
    String storeDetail;
    String storePicture;
}
