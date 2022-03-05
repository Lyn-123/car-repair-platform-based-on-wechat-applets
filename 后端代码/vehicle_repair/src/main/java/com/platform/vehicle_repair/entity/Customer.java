package com.platform.vehicle_repair.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Customer {
    //openId
    @Id
    String id;
    //用户名
    String userName;
    //电话
    String tel;
    //性别 0男/1女
    Integer sex;
    //地址
    String address;
    //用户头像
    String image;

}
