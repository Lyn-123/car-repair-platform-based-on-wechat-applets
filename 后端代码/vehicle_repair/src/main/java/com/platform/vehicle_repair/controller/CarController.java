package com.platform.vehicle_repair.controller;

import com.platform.vehicle_repair.VO.ResultVO;
import com.platform.vehicle_repair.entity.Car;
import com.platform.vehicle_repair.entity.Customer;
import com.platform.vehicle_repair.enums.ResultEnum;
import com.platform.vehicle_repair.exception.ServiceException;
import com.platform.vehicle_repair.service.CarService;
import com.platform.vehicle_repair.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController {

    @Autowired
    private CarService carService;

    /**
     * 查找自己的车
     */
    @GetMapping("/my")
    public ResultVO<List<Car>> findMyCar(@RequestParam("userId") String userId){
        if(userId==null){
            return ResultVOUtil.error(ResultEnum.USER_NOT_LOGIN.getCode(),
                    ResultEnum.USER_NOT_LOGIN.getMessage());
        }
        List<Car> carList = carService.findByUserId(userId);
        return ResultVOUtil.success(carList);
    }

    /**
     * 查看车辆详情
     */
    @GetMapping("/detail")
    public ResultVO<Car> CarDetail(@RequestParam("carId")String carId){
        Car car  = carService.findCarDetail(carId);
        if(car==null){
            return ResultVOUtil.error(ResultEnum.CAR_NOT_EXIT.getCode(),
                    ResultEnum.CAR_NOT_EXIT.getMessage());
        }else{
            return ResultVOUtil.success(car);
        }
    }

    /**
     * 自己默认的车
     */
    @GetMapping("/myDefault")
    public ResultVO<Car> findMyDefaultCar(@RequestParam("userId") String userId) {
        Car car = carService.findByUserIdAndIsDefault(userId);
        return ResultVOUtil.success(car);
    }

    /**
     *修改默认车辆
     */
    @PostMapping("/modifyDefault")
    public ResultVO modifyMyDefault(@RequestParam("userId") String userId,
                                      @RequestParam("carId") String carId){
        boolean result = carService.modifyDefaultCar(userId,carId);
        if(result){
            return ResultVOUtil.success();
        }else{
            return ResultVOUtil.error(ResultEnum.FAIL_TO_MODIFY.getCode(),
                    ResultEnum.FAIL_TO_MODIFY.getMessage());
        }
    }

    /**
     * 删除车辆信息
     */
    @PostMapping("/delete")
    public ResultVO deleteCar(@RequestParam("carId")String carId){
        try{
            carService.deleteById(carId);
        }catch(Exception e){
            return ResultVOUtil.error(ResultEnum.FAIL_TO_DELETE.getCode(),
                    ResultEnum.FAIL_TO_DELETE.getMessage());
        }
        return ResultVOUtil.success();
    }

    /**
     * 添加车辆信息
     */
    @PostMapping("/add")
    public ResultVO addCar(@RequestBody Car car){
        if(carService.addCar(car)){
            return ResultVOUtil.success();
        }else{
            return ResultVOUtil.error(ResultEnum.FAIL_TO_INSERT.getCode(),
                    ResultEnum.FAIL_TO_INSERT.getMessage());
        }

    }

    /**
     * 修改车辆信息
     */
    @PostMapping("/modify")
    public ResultVO modifyCar(@RequestBody Car car){
        if(carService.modifyCarDetail(car)){
            return ResultVOUtil.success();
        }else{
            return ResultVOUtil.error(ResultEnum.FAIL_TO_MODIFY.getCode(),
                    ResultEnum.FAIL_TO_MODIFY.getMessage());
        }

    }
}
