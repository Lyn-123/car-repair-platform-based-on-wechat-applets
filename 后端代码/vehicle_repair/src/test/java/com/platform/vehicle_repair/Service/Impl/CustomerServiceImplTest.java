package com.platform.vehicle_repair.Service.Impl;

import com.platform.vehicle_repair.entity.Customer;
import com.platform.vehicle_repair.service.Impl.CustomerServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class CustomerServiceImplTest {
    @Autowired
    private CustomerServiceImpl customerService;

    @Test
    void register() {
        Customer customer = new Customer();
        customer.setId("2333");
        customer.setUserName("test1");
        customer.setImage("http%3A%2F%2Fcdn.duitang.com%2Fuploads%2Fitem%2F201411%2F27%2F20141127112005_HSN8z.jpeg&refer=http%3A%2F%2Fcdn.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1622466862&t=27d4e4bae1ecb571b1e67e3ee01049cd");
        Boolean result = customerService.register(customer);
        Assert.assertEquals(true,result);
    }
}