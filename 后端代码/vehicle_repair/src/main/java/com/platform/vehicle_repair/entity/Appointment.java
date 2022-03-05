package com.platform.vehicle_repair.entity;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Data
@Entity
public class Appointment {
    //预约单号
    @Id
    private String appointId;
    //对应维修单单号
    private String repairId;
    //下单时间
    private Timestamp time;
    //预约维修时间
    private Timestamp appointTime;
    //车牌号
    private String carId;
    //车型
    private String model;
    //联系人
    private String carUser;
    //联系电话
    private String userNumber;
    //预约详情
    private String details;
    //故障照片
    private String picture;
    //店铺ID
    private Integer storeId;
    //用户ID
    private String userId;
    //指派人
    private String assign;
}
