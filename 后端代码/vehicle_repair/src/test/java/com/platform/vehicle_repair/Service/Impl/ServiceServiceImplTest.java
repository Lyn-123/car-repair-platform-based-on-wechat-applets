package com.platform.vehicle_repair.Service.Impl;

import com.platform.vehicle_repair.entity.Repair;
import com.platform.vehicle_repair.entity.Service;
import com.platform.vehicle_repair.enums.ServiceTypeEnum;
import com.platform.vehicle_repair.service.Impl.RepairServiceImpl;
import com.platform.vehicle_repair.service.Impl.ServiceServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
class ServiceServiceImplTest {
    @Autowired
    private ServiceServiceImpl serviceService;

    @Test
    void findOne() throws Exception {
        Service result = serviceService.findOne(1);
        Assert.assertNotEquals(null,result);
    }

    @Test
    void findByStore(){
        List<Service> serviceList = serviceService.findByStoreId(1);
        HashMap<Integer,List<Service>> serviceMap = new HashMap<Integer,List<Service>>();
        for(Service service : serviceList){
            int type = service.getServiceType();
            List<Service> typeList = new ArrayList<Service>();
            if(serviceMap.containsKey(type)){
                typeList = serviceMap.get(type);
            }
            typeList.add(service);
            serviceMap.put(type,typeList);
        }
        System.out.println(serviceMap);

        HashMap<String,List<Service>> result = new HashMap<String,List<Service>>();
        ServiceTypeEnum[] enums = ServiceTypeEnum.values();
        for(Map.Entry<Integer,List<Service>> entry : serviceMap.entrySet()){
            int oldKey = entry.getKey();
            String newKey = enums[oldKey-1].getMessage();
            List<Service> value = entry.getValue();
            result.put(newKey,value);
        }

        System.out.println(result);
    }


}