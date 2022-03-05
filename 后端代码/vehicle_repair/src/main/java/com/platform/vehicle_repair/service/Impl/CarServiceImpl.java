package com.platform.vehicle_repair.service.Impl;

import com.platform.vehicle_repair.dao.CarRepository;
import com.platform.vehicle_repair.dao.CustomerRepository;
import com.platform.vehicle_repair.entity.Car;
import com.platform.vehicle_repair.entity.Customer;
import com.platform.vehicle_repair.service.CarService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * 查找自己的车
     */
    public List<Car> findByUserId(String userId){
        return carRepository.findByUserId(userId);
    }

    /**
     * 自己默认的车
     */
    public Car findByUserIdAndIsDefault(String userId){
        return carRepository.findByUserIdAndIsDefault(userId,1);
    }

    /**
     * 修改默认车辆
     */
    public boolean modifyDefaultCar(String userId,String carId){
        //查找已有默认车辆
        Car car = carRepository.findByUserIdAndIsDefault(userId,1);
        if(car==null){
            //默认车辆不存在，设置该车辆为默认车辆
            int result = carRepository.updateIsDefault(carId,1);
            return (result!=0);
        }else{
            //默认车辆存在，如旧有默认车辆和待设置默认车辆不相同，则将旧有车辆默认标志置0，待设置车辆默认标志置1
            String oldCarId = car.getCarId();
            if(!oldCarId.equals(carId)){
                int result1 = carRepository.updateIsDefault(oldCarId,0);
                int result2 = carRepository.updateIsDefault(carId,1);
                return (result1!=0&&result2!=0);
            }
            return true;
        }
    }

    /**
     * 删除车辆信息
     */
    public void deleteById(String carId) {
        Car car = carRepository.findById(carId).get();
        List<Car> carList = carRepository.findByUserId(car.getUserId());
        if(carList.size()!=1&&car.getIsDefault()==1){
            for(Car c:carList){
                if(c.getIsDefault()!=1){
                    carRepository.updateIsDefault(c.getCarId(),1);
                    carRepository.updateIsDefault(carId,0);
                    break;
                }
            }

        }
        carRepository.deleteById(carId);
    }

    /**
     * 添加车辆信息
     */
    public boolean addCar(Car car){
        String userId = car.getUserId();
        Customer customer = customerRepository.findById(userId).get();
        car.setCarUser(customer.getUserName());
        List<Car> carList = carRepository.findByUserId(userId);
        if(!CollectionUtils.isEmpty(carList)){
            Car oldDefault = carRepository.findByUserIdAndIsDefault(userId,1);
            if(oldDefault!=null){
                carRepository.updateIsDefault(oldDefault.getCarId(),0);
            }
        }
        car.setIsDefault(1);
        Car result = carRepository.save(car);
        if(result==null){
            return false;
        }else{
            return true;
        }
    }

    /**
     * 修改车辆具体信息
     */
    public boolean modifyCarDetail(Car car) {
        int result = carRepository.updateCar(car.getCarId(),
                car.getManufacturer(),car.getModel(),
                car.getCapacity(),car.getYear());
        if(result==0){
            return false;
        }else {
            return true;
        }
    }

    /**
     * 查看车辆详情
     */
    public Car findCarDetail(String carId){
        Optional<Car> carOptional = carRepository.findById(carId);
        if(carOptional.isPresent()){
            return carOptional.get();
        }else{
            return null;
        }
    }
}
