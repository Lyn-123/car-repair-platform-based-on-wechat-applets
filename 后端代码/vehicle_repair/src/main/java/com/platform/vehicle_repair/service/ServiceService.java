package com.platform.vehicle_repair.service;

import com.platform.vehicle_repair.VO.ResultVO;
import com.platform.vehicle_repair.entity.Parts;
import com.platform.vehicle_repair.entity.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ServiceService {

    /**
     * 按页显示全部服务
     */
    Page<Service> findAll(Pageable pageable);

    /**
     * 按照店铺显示全部服务
     */
    List<Service> findByStoreId(Integer storeId);

    /**
     * 查询单个服务信息
     */
    Service findOne(Integer serviceId);

    /**
     * 模糊查询服务名
     */
    Page<Service> findByServiceNameLike(String serviceName, PageRequest request);

    /**
     * 按照分类查找服务
     */
    List<Service> findServiceByType(Integer serviceType);
}
