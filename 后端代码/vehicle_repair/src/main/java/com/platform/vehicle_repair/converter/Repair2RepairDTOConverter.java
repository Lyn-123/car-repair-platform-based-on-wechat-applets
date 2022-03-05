package com.platform.vehicle_repair.converter;

import com.platform.vehicle_repair.dto.RepairDTO;
import com.platform.vehicle_repair.dto.RepairDetailDTO;
import com.platform.vehicle_repair.entity.Parts;
import com.platform.vehicle_repair.entity.Repair;
import com.platform.vehicle_repair.entity.RepairDetail;
import com.platform.vehicle_repair.entity.Service;
import com.platform.vehicle_repair.service.PartService;
import com.platform.vehicle_repair.service.ServiceService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class Repair2RepairDTOConverter {
    public static RepairDTO convert(Repair repair){
        RepairDTO repairDTO = new RepairDTO();
        BeanUtils.copyProperties(repair,repairDTO);
        return repairDTO;
    }
    public static List<RepairDTO> convert(List<Repair> repairList){
        return repairList.stream().map(e ->
                convert(e)
        ).collect(Collectors.toList());
    }

    private static ServiceService serviceService;
    private static PartService partService;

    @Autowired
    public void setServiceService(ServiceService serviceService){
        Repair2RepairDTOConverter.serviceService = serviceService;
    }
    @Autowired
    public void setPartService(PartService partService){
        Repair2RepairDTOConverter.partService = partService;
    }

    public static RepairDetailDTO convert(RepairDetail repairDetail){
        RepairDetailDTO repairDetailDTO = new RepairDetailDTO();
        BeanUtils.copyProperties(repairDetail,repairDetailDTO);
        Integer partsId = repairDetail.getPartsId();
        Integer serviceId = repairDetail.getServiceId();
        if(partsId!=null&&!partsId.equals(0)){
            Parts parts = partService.findOne(partsId);
            repairDetailDTO.setParts(parts);
            repairDetailDTO.setPrice(parts.getPrice());
        }else if(serviceId!=null&&!serviceId.equals(0)){
            Service service = serviceService.findOne(serviceId);
            repairDetailDTO.setService(service);
            repairDetailDTO.setPrice(service.getPrice());
        }
        return repairDetailDTO;
    }

}
