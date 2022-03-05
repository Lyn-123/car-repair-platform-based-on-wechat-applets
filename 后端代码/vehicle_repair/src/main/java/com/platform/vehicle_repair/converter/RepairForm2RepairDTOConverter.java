package com.platform.vehicle_repair.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.platform.vehicle_repair.dto.RepairDTO;
import com.platform.vehicle_repair.entity.RepairDetail;
import com.platform.vehicle_repair.enums.ResultEnum;
import com.platform.vehicle_repair.exception.ServiceException;
import com.platform.vehicle_repair.form.RepairForm;
import com.platform.vehicle_repair.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class RepairForm2RepairDTOConverter {
    public static RepairDTO convert(RepairForm repairForm){
        RepairDTO repairDTO = new RepairDTO();
        Gson gson = new Gson();

        repairDTO.setRepairId(KeyUtil.geUniqueKey());
        repairDTO.setStoreId(repairForm.getStoreId());
        repairDTO.setUserId(repairForm.getUserId());
        //商品明细转换格式
        List<RepairDetail> repairDetailList = new ArrayList<>();
        try{
            repairDetailList = gson.fromJson(repairForm.getItems(),
                    new TypeToken<List<RepairDetail>>(){}.getType());
        }catch (Exception e){
            log.error("【对象转换】错误，string={}",repairForm.getItems());
            throw new ServiceException(ResultEnum.PARAM_ERROR);
        }
//        repairDTO.setRepairDetailDTOList(repairDetailList);
        return repairDTO;
    }
}
