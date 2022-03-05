package com.platform.vehicle_repair;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LoggerTest2 {

    //private final Logger logger = LoggerFactory.getLogger(LoggerTest2.class);

    @Test
    public void Test2(){
        log.debug("debug...");
        log.info("info...");
        log.error("error...");

    }
}
