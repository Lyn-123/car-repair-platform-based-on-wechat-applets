package com.platform.vehicle_repair.service;

import com.platform.vehicle_repair.entity.Customer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface CustomerService {
    /**
     * 判断用户是否存在
     */
    public boolean isPresent(String openId);

    /**
     * 获取个人信息
     */
    Customer findOne(String openId);

    boolean register(Customer customer);

    /**
     * 修改个人信息
     */
    boolean updateImage(String id,String image);
    boolean updateUserName(String id,String userName);
    boolean updateSex(String id,Integer sex);
    boolean updateTel(String id,String tel);
    boolean updateAddress(String id,String address);

}
