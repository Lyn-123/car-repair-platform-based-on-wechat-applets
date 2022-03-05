package com.platform.vehicle_repair.enums;

import lombok.Getter;

/**
 * 维修状态
 */
@Getter
public enum StateEnum {

    NEW(0,"待维修"),
    REPAIRING(1,"维修中"),
    REPAIRED(2,"待提车"),
    FINISH(3,"已结束"),
    CANCEL(4,"取消订单");

    private Integer code;
    private String message;

    StateEnum(Integer code,String message){
        this.code = code;
        this.message = message;
    }


}
