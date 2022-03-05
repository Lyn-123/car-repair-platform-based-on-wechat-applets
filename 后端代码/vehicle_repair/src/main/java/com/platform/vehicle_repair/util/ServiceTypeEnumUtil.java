package com.platform.vehicle_repair.util;

import com.platform.vehicle_repair.enums.ServiceTypeEnum;

public class ServiceTypeEnumUtil {

    public static String getCode(Integer code) {
        ServiceTypeEnum[] msg = ServiceTypeEnum.values();
        String message = msg[code-1].getMessage();
        return message;
    }

}