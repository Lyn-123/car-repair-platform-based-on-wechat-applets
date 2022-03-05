package com.platform.vehicle_repair.converter;

import com.platform.vehicle_repair.dto.PartsDTO;
import com.platform.vehicle_repair.dto.RepairDTO;
import com.platform.vehicle_repair.dto.ServiceDTO;
import com.platform.vehicle_repair.entity.Parts;
import com.platform.vehicle_repair.entity.Service;
import com.platform.vehicle_repair.entity.Store;
import com.platform.vehicle_repair.service.StoreService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Parts2PartsDTOConverter {
    private static StoreService storeService;

    @Autowired
    public void setStoreService(StoreService service){
        Parts2PartsDTOConverter.storeService = service;
    }

    public static PartsDTO converter(Parts parts){
        PartsDTO partsDTO = new PartsDTO();
        BeanUtils.copyProperties(parts,partsDTO);

        Store store = storeService.findOne(parts.getStoreId());
        partsDTO.setStore(store);

        return partsDTO;
    }

    public static List<PartsDTO> converter(List<Parts> partsList){
        return partsList.stream().map(e->converter(e)).collect(Collectors.toList());
    }


}
