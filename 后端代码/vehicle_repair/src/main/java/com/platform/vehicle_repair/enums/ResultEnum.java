package com.platform.vehicle_repair.enums;

import lombok.Getter;

/**
 * 维修状态
 */
@Getter
public enum ResultEnum {
    PARAM_ERROR(1,"参数不正确"),
    NOT_EXIST(10,"商品不存在"),
    REPAIR_NOT_EXIT(11,"订单不存在"),
    DETAIL_NOT_EXIT(12,"订单详情不存在"),
    REPAIR_STATUS_ERROR(13,"订单状态异常"),
    PERMISSION_ERROR(14,"权限错误"),
    USER_NOT_EXIT(15,"用户不存在"),
    FILE_NOT_EXIT(16,"文件不存在"),
    USER_NOT_LOGIN(21,"用户未登录"),

    CAR_NOT_EXIT(3,"汽车不存在"),

    SIZE_TOO_BIG(4,"文件过大"),

    ORDER_NOT_EXIT(5,"预约保养单不存在"),
    ORDER_DETAIL_NOT_EXIT(51,"预约保养单详情不存在"),

    CART_DETAIL_NOT_EXIT(6,"该购物车项不存在"),

    FAIL_TO_MODIFY(300,"修改失败"),
    FAIL_TO_DELETE(400,"删除失败"),
    FAIL_TO_INSERT(500,"添加失败"),
    FAIL_TO_TRANSFER(600,"文件复制失败"),
    ;

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }


}
