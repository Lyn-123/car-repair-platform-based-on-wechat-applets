package com.platform.vehicle_repair.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RepairDTO {
    private String repairId;
    private int storeId;
    private String userId;
    private Integer state;

    private Timestamp establishTime;
    private Timestamp updateTime;

    List<RepairDetailDTO> repairDetailDTOList;
}
