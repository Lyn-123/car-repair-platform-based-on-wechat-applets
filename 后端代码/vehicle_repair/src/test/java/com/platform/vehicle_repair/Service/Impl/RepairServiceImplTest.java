package com.platform.vehicle_repair.Service.Impl;

import com.platform.vehicle_repair.dto.RepairDTO;
import com.platform.vehicle_repair.entity.Repair;
import com.platform.vehicle_repair.service.Impl.RepairServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
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
@Slf4j
class RepairServiceImplTest {

    @Autowired
    private RepairServiceImpl repairService;

    @Test
    void findOne() throws Exception {
        RepairDTO result = repairService.findOne("123");
        log.info("【查询单个订单】 result={}",result);
        Assert.assertNotEquals(null,result);
        /*Repair result = repairService.findOne("56");
        Assert.assertNotEquals(null,result);*/
    }

    @Test
    void findList(){
        PageRequest pageRequest = PageRequest.of(1,2);
        Page<RepairDTO> result = repairService.findList("123",pageRequest);
        //log.info("【查询用户订单列表】result={}",result);
        Assert.assertNotEquals(0,result.getTotalElements());
    }

    @Test
    void findAll() throws Exception {
        PageRequest request = PageRequest.of(1,2);
        /*Page<Repair> repairPage = repairService.findAll(request);
        System.out.println(repairPage.getTotalElements());
    */}
    /*void findAll() throws Exception {
        List<Repair> repairList = repairService.findAll();
        Assert.assertNotEquals(0,repairList.size());
    }*/


    @Test
    void findByStateIn() throws Exception {
        List<Repair> repairList = repairService.findByStateIn(Arrays.asList(0,1,2,3));
        Assert.assertNotEquals(0,repairList.size());

    }


}