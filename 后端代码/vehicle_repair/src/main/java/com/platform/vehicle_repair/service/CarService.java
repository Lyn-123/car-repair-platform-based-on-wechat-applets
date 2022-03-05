package com.platform.vehicle_repair.service;

import com.platform.vehicle_repair.VO.ResultVO;
import com.platform.vehicle_repair.entity.Car;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CarService {

    /**
     * 查找自己的车
     */
    List<Car> findByUserId(String userId);

    /**
     * 查找自己默认的车
     */
    Car findByUserIdAndIsDefault(String userId);

    /**
     * 修改默认车辆
     */
    boolean modifyDefaultCar(String userId,String carId);

    /**
     * 删除车辆信息
     */
    void deleteById(String carId) ;

    /**
     * 添加车辆信息
     */
    boolean addCar(Car car);

    /**
     * 修改车辆具体信息
     */
    public boolean modifyCarDetail(Car car);

    /**
     * 查看车辆详情
     */
    public Car findCarDetail(String carId);
}
