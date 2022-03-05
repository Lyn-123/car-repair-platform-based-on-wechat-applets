package com.platform.vehicle_repair.entity;

import com.platform.vehicle_repair.enums.StateEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Data
@DynamicUpdate
@DynamicInsert
public class Repair {
    @Id
    private String repairId;
    private Timestamp establishTime;
    private Timestamp updateTime;
    private Integer storeId;
    private String userId;
    private Integer state = StateEnum.NEW.getCode();
}
