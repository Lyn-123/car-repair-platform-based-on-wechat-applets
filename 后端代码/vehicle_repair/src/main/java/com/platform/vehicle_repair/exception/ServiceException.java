package com.platform.vehicle_repair.exception;

import com.platform.vehicle_repair.enums.ResultEnum;

public class ServiceException extends RuntimeException{
    private Integer code;

    public ServiceException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
    public ServiceException(ResultEnum resultEnum,String message) {
        super(message);
        this.code = resultEnum.getCode();
    }
}
