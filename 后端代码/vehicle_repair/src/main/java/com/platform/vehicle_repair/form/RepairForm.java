package com.platform.vehicle_repair.form;

import com.platform.vehicle_repair.entity.RepairDetail;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RepairForm {
    /**
     * 商店ID
     */
    @NotEmpty(message="商店不能为空")
    private int storeId;
    /**
     * 用户ID
     */
    @NotEmpty(message = "用户ID不能为空")
    private String userId;
    /**
     * 订单细节
     */
    @NotEmpty(message = "订单明细不能为空")
    private String items;

}
