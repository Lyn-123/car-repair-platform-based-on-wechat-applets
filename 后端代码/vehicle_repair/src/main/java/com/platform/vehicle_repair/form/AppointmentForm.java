package com.platform.vehicle_repair.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
public class AppointmentForm {
    //预约维修时间
    @NotNull(message = "时间不能为空")
    @Future(message = "维修时间不能过去")
    private Date appointTime;
    //车牌号
    @NotEmpty(message = "车牌号不能为空")
    private String carId;
    //车型
    @NotEmpty(message = "车型不能为空")
    private String model;
    //联系人
    @NotEmpty(message = "联系人不能为空")
    private String carUser;
    //联系电话
    @NotEmpty(message = "联系电话不能为空")
    private String userNumber;
    //预约详情
    @NotEmpty(message = "预约详情不能为空")
    private String details;
    //故障照片
    @NotEmpty(message = "请上传故障照片")
    private String picture;
    //店铺ID
    @NotNull(message = "请选定维修店铺")
    private Integer storeId;
    //用户ID
    private String userId;
}
