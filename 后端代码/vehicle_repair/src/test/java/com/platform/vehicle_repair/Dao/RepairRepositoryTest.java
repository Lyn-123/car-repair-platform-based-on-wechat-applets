package com.platform.vehicle_repair.dao;


import com.platform.vehicle_repair.entity.Repair;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RepairRepositoryTest {

    @Autowired
    private RepairRepository repository;

    private final String OpenId = "123";

    @Test
    public void findOneTest(){
        Repair repair = repository.findById("123").get();
        System.out.println(repair.toString());

    }

    @Test
    @Transactional
    public void saveTest(){
        Repair repair = new Repair();
        repair.setRepairId("4352345");
        repair.setState(3);
        Repair result = repository.save(repair);
        Assert.assertNotNull(result);

    }

    @Test
    public void findByStateInTest(){
        List<Integer> list = Arrays.asList(1,2,3);

        List<Repair> result = repository.findByStateIn(list);
        Assert.assertNotEquals(0,result.size());
    }

   @Test
    public void findByUserId(){
        PageRequest request = PageRequest.of(0,3);
        Page<Repair> result = repository.findByUserId(OpenId,request);
        System.out.println(result.getTotalElements());
    }


}