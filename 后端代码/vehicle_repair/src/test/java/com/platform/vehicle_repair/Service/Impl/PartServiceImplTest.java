package com.platform.vehicle_repair.Service.Impl;

import com.platform.vehicle_repair.entity.Parts;
import com.platform.vehicle_repair.service.Impl.PartServiceImpl;
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

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class PartServiceImplTest {
    @Autowired
    private PartServiceImpl partService;

    @Test
    void findAll() {
        PageRequest request = PageRequest.of(0,6);
        Page<Parts> result = partService.findAll(request);
        System.out.println(result.getContent().toString());
        Assert.assertNotEquals(0,result.getTotalElements());

    }

    @Test
    void findByStoreId() {
        PageRequest request = PageRequest.of(0,6);
        Page<Parts> result = partService.findByStoreId(1,request);
        System.out.println(result.getContent().toString());
        Assert.assertNotEquals(0,result.getTotalElements());
    }

    @Test
    @Transactional
    void findOne() {
        Parts part = partService.findOne(1);
        System.out.println(part.toString());
        Assert.assertNotEquals(null,part);
    }
}