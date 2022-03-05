package com.platform.vehicle_repair;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoggerTest {

    private final Logger logger = LoggerFactory.getLogger(LoggerTest.class);

    @Test
    public void Test(){
        String name = "user_name";
        String password = "123456";
        logger.debug("debug...");
        logger.info("name:"+name+",password:"+password);
        logger.info("name:{}，password:{}",name,password);
        logger.error("error...");

    }
}
