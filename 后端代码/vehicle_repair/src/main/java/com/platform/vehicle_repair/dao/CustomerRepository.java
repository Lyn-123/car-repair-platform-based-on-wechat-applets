package com.platform.vehicle_repair.dao;

import com.platform.vehicle_repair.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface CustomerRepository extends JpaRepository<Customer,String> {

    @Transactional
    @Modifying
    @Query(value = "update customer c set c.image=?2 where c.id = ?1",nativeQuery = true)
    Integer updateImage(String id,String image);

    @Transactional
    @Modifying
    @Query(value = "update customer c set c.user_name=?2 where c.id = ?1",nativeQuery = true)
    Integer updateUserName(String id,String userName);


    @Transactional
    @Modifying
    @Query(value = "update customer c set c.sex=?2 where c.id = ?1",nativeQuery = true)
    Integer updateSex(String id,Integer sex);

    @Transactional
    @Modifying
    @Query(value = "update customer c set c.tel=?2 where c.id = ?1",nativeQuery = true)
    Integer updateTel(String id,String tel);

    @Transactional
    @Modifying
    @Query(value = "update customer c set c.address=?2 where c.id = ?1",nativeQuery = true)
    Integer updateAddress(String id,String address);







}
