package com.platform.vehicle_repair.service.Impl;

import com.platform.vehicle_repair.dao.CarRepository;
import com.platform.vehicle_repair.dao.CustomerRepository;
import com.platform.vehicle_repair.entity.Customer;
import com.platform.vehicle_repair.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CarRepository carRepository;

    /**
     * 判断用户是否存在
     */
    @Override
    public boolean isPresent(String openId) {
        return customerRepository.findById(openId).isPresent();
    }

    /**
     * 获取个人信息
     */
    public Customer findOne(String openId){
        return customerRepository.findById(openId).get();
    }

    /**
     * 用户注册
     */
    @Override
    public boolean register(Customer customer) {
        Customer result = customerRepository.save(customer);
        if(result==null){
            return false;
        }
        else{
            return true;
        }

    }

    /**
     * 修改个人信息
     */
    public boolean updateImage(String id,String image) {
        return customerRepository.updateImage(id,image)!=0;
    }
    public boolean updateUserName(String id,String userName){
        return customerRepository.updateUserName(id,userName)!=0;
    }
    public boolean updateSex(String id,Integer sex){
        return customerRepository.updateSex(id,sex)!=0;
    }
    public boolean updateTel(String id,String tel){
        return customerRepository.updateTel(id,tel)!=0;
    }
    public boolean updateAddress(String id,String address){
        return customerRepository.updateAddress(id,address)!=0;
    }
}
