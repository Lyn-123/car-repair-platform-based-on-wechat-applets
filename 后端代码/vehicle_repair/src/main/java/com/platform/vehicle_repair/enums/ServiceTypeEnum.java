package com.platform.vehicle_repair.enums;

import lombok.Getter;

@Getter
public enum ServiceTypeEnum {
    CAR_REPAIR(1,"汽车维修"),
    CAR_MODIFY(2,"汽车改装"),
    TIRE_SERVICE(3,"轮胎服务"),
    CAR_WASH(4,"洗车美容"),
    CAR_MAINTAIN(5,"车辆保养"),
    ;

    private Integer code;
    private String message;

    ServiceTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


}
