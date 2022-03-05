package com.platform.vehicle_repair.dto;

import com.platform.vehicle_repair.entity.Store;
import com.platform.vehicle_repair.enums.ServiceTypeEnum;
import com.platform.vehicle_repair.util.ServiceTypeEnumUtil;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
public class ServiceDTO {
    //服务ID
    private Integer serviceId;
    //服务名称
    private String serviceName;
    //服务价格
    private BigDecimal price;
    //店铺 通过店铺ID查找
    private Store store;
    //服务类型
    private String serviceType;
    //评分
    private BigDecimal rating;

}
