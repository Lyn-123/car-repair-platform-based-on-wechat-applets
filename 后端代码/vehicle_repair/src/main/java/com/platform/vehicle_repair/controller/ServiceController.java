package com.platform.vehicle_repair.controller;

import com.platform.vehicle_repair.VO.ResultVO;
import com.platform.vehicle_repair.converter.Service2ServiceDTOConverter;
import com.platform.vehicle_repair.dto.ServiceDTO;
import com.platform.vehicle_repair.entity.Service;
import com.platform.vehicle_repair.enums.ResultEnum;
import com.platform.vehicle_repair.enums.ServiceTypeEnum;
import com.platform.vehicle_repair.exception.ServiceException;
import com.platform.vehicle_repair.service.ServiceService;
import com.platform.vehicle_repair.service.StoreService;
import com.platform.vehicle_repair.util.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/service")
@Slf4j
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private StoreService storeService;

    /**
     * 查找全部服务列表
     */
    @GetMapping("/all")
    public ResultVO<List<Service>> findAllService(@RequestParam(value = "page",defaultValue = "0") Integer page){
        PageRequest request = PageRequest.of(page,6);
        Page<Service> servicePage = serviceService.findAll(request);
        return ResultVOUtil.success(servicePage.getContent());
    }

    /**
     * 按照分类查找服务
     */
    @GetMapping("/type")
    public ResultVO<List<ServiceDTO>> findServiceByType(@RequestParam("serviceType")Integer serviceType){
        List<Service> serviceList = serviceService.findServiceByType(serviceType);
        List<ServiceDTO> serviceDTOList=Service2ServiceDTOConverter.converter(serviceList);
        return ResultVOUtil.success(serviceDTOList);
    }
    /**
     * 查找指定店铺的服务
     */
    @RequestMapping("/store")
    public ResultVO<List<Service>> findServiceByStore(@RequestParam(value = "storeId",defaultValue = "-1")
                                                                  Integer storeId){
        //判断店铺ID是否存在，不存在报错
        if(storeId==-1) {
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(),
                    ResultEnum.PERMISSION_ERROR.getMessage(),
                    "【查询配件】未选定店铺");
        }
        //查找该店铺的提供的全部服务
        List<Service> serviceList = serviceService.findByStoreId(storeId);
        //遍历查询到的服务列表，按照ServiceType分类,并将service转换为ServiceDTO
        HashMap<String,List<ServiceDTO>> serviceDTOMap = new HashMap<String,List<ServiceDTO>>();
        ServiceTypeEnum[] enums = ServiceTypeEnum.values();
        for(Service service : serviceList){
            int type = service.getServiceType();
            String serviceType = enums[type-1].getMessage();
            List<ServiceDTO> typeList = new ArrayList<ServiceDTO>();
            if(serviceDTOMap.containsKey(serviceType)){
                typeList = serviceDTOMap.get(serviceType);
            }
            ServiceDTO serviceDTO = Service2ServiceDTOConverter.converter(service);
            typeList.add(serviceDTO);
            serviceDTOMap.put(serviceType,typeList);
        }
        return ResultVOUtil.success(serviceDTOMap);
    }

    /**
     * 显示服务详情
     */
    @GetMapping("/detail")
    public ResultVO<ServiceDTO> findService(@RequestParam("serviceId") Integer serviceId){
        Service service = serviceService.findOne(serviceId);
        ServiceDTO result = Service2ServiceDTOConverter.converter(service);
        if(result==null){
            log.error("【查询服务】服务信息不存在 result={}",result.toString());
            throw new ServiceException(ResultEnum.NOT_EXIST);
        }
        return ResultVOUtil.success(result);
    }

    /**
     * 模糊查找服务名
     */
    @RequestMapping("/find")
    public ResultVO<List<Service>> findServiceByName(@RequestParam("serviceName") String serviceName,
                                                     @RequestParam(value = "page",defaultValue = "0") Integer page){
        if(serviceName == null ||serviceName.length()<=0){
            return ResultVOUtil.success();
        }else {
            PageRequest request = PageRequest.of(page, 6);
            Page<Service> servicePage = serviceService.findByServiceNameLike("%" + serviceName + "%", request);
            List<ServiceDTO> serviceDTOList = Service2ServiceDTOConverter.converter(servicePage.getContent());
            return ResultVOUtil.success(serviceDTOList);
        }
    }

}
