package com.platform.vehicle_repair.service.Impl;

import com.platform.vehicle_repair.dao.ServiceRepository;
import com.platform.vehicle_repair.entity.Service;
import com.platform.vehicle_repair.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    /**
     * 按页显示全部服务
     */
    @Override
    public Page<Service> findAll(Pageable pageable) {
        return serviceRepository.findAll(pageable);
    }

    /**
     * 按照店铺显示全部服务
     */
    @Override
    public List<Service> findByStoreId(Integer storeId) {
        return serviceRepository.findByStoreId(storeId);
    }

    /**
     * 查询单个服务信息
     */
    @Override
    public Service findOne(Integer serviceId) {
        return serviceRepository.findById(serviceId).get();
    }

    /**
     * 模糊查询服务名
     */
    @Override
    public Page<Service> findByServiceNameLike(String serviceName, PageRequest request) {
        return serviceRepository.findByServiceNameLike(serviceName,request);
    }

    /**
     * 按照分类查找服务
     */
    public List<Service> findServiceByType(Integer serviceType){
        return serviceRepository.findByServiceType(serviceType);
    }
}
