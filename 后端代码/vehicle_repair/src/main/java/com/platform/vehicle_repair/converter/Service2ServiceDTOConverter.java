package com.platform.vehicle_repair.converter;

import com.platform.vehicle_repair.dto.ServiceDTO;
import com.platform.vehicle_repair.entity.Service;
import com.platform.vehicle_repair.entity.Store;
import com.platform.vehicle_repair.service.StoreService;
import com.platform.vehicle_repair.util.ServiceTypeEnumUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Service2ServiceDTOConverter {


    private static StoreService storeService;

    @Autowired
    public void setStoreService(StoreService service){
        Service2ServiceDTOConverter.storeService = service;
    }

    public static ServiceDTO converter(Service service){

        ServiceDTO serviceDTO  =  new ServiceDTO();

        serviceDTO.setServiceId(service.getServiceId());
        serviceDTO.setServiceName(service.getServiceName());
        serviceDTO.setPrice(service.getPrice());
        serviceDTO.setRating(service.getRating());

        int serviceType_int = service.getServiceType();
        String serviceType_String  = ServiceTypeEnumUtil.getCode(serviceType_int);
        serviceDTO.setServiceType(serviceType_String);

        Store store =  storeService.findOne(service.getStoreId());
        serviceDTO.setStore(store);

        return serviceDTO;
    }

    public static List<ServiceDTO> converter(List<Service> serviceList){
        return serviceList.stream().map(e ->
                converter(e)
        ).collect(Collectors.toList());
    }
}
