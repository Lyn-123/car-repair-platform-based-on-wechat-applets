package com.platform.vehicle_repair.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class RepairVO {

    @JsonProperty("id")
    private String repairId;

    private String userId;
    
    private Integer state=1;

    private Integer adminId=1;


}
