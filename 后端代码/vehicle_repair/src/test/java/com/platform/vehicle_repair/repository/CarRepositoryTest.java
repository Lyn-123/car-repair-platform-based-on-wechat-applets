package com.platform.vehicle_repair.repository;

import com.platform.vehicle_repair.dao.CarRepository;
import com.platform.vehicle_repair.entity.Car;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarRepositoryTest {

    @Autowired
    private CarRepository repository;

    @Test
    public void findOneTest(){

        System.out.println(repository.findById("123").isPresent());
//        Car car = repository.findById("123").get();
//        System.out.println(car.toString());

    }
    @Test
    public void findCarTest(){
        Car car = repository.findByUserIdAndIsDefault("ou5HXw0WuJNozUSo1EYtPR092Hpo",1);
        System.out.println(car.toString());

    }

    @Test
    public void update(){
        //Integer car = repository.updateIsDefault("湘A-F520",0);
        Integer car = repository.updateCar("湘A-520","路虎","小轿车","6.0T",1999);
        System.out.println(car);
    }

    @Test
    public void saveTest(){
        Car car = new Car();
        car.setCarId("2345");
        car.setCarUser("吃瓜群众110");
        repository.save(car);

    }

}