package com.platform.vehicle_repair.dao;

import com.platform.vehicle_repair.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

public interface CarRepository extends JpaRepository<Car,String> {

    List<Car> findByUserId(String userId);

    Car findByUserIdAndIsDefault(String userId,Integer isDefault);

    @Transactional
    @Modifying
    @Query(value = "update car c set c.is_default=?2 where c.car_id = ?1",nativeQuery = true)
    Integer updateIsDefault(String carId,Integer isDefault);

    @Transactional
    void deleteById(String carId);


    @Transactional
    @Modifying
    @Query(value = "update car c set c.manufacturer=?2, c.model=?3, c.capacity=?4, c.year=?5 where c.car_id = ?1",nativeQuery = true)
    Integer updateCar(String carId,String manufacturer,String model,String capacity,Integer year);
}
