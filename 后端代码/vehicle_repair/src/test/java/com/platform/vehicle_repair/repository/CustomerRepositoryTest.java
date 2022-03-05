package com.platform.vehicle_repair.repository;

import com.platform.vehicle_repair.entity.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.platform.vehicle_repair.dao.CustomerRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    @Test
    public void findOneTest(){
        Customer customer  = repository.findById("123").get();
        //System.out.println(customer.toString());
    }


}