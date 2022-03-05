package com.platform.vehicle_repair.Service.Impl;

import com.platform.vehicle_repair.entity.Store;
import com.platform.vehicle_repair.service.Impl.StoreServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
class StoreServiceImplTest {
    @Autowired
    private StoreServiceImpl storeService;

    @Test
    void findAll() throws Exception {
        List<Store> result = storeService.findAll();
        Assert.assertNotEquals(null,result);
    }

    @Test
    void findOne() throws Exception {
        Store result = storeService.findOne(1);
        Assert.assertNotEquals(null,result);
    }

}