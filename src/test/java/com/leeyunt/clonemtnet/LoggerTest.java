package com.leeyunt.clonemtnet;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class LoggerTest {
    /*private final Logger logger = LoggerFactory.getLogger(LoggerTest.class);*/

    @Test
    public void test1() {
        String name = "admin";
        String password = "123456";
        log.debug("debug...");
        log.info("name: " + name + ", password: " + password);
        log.info("name: {}, password: {}", name ,password);
        log.error("error...");
    }
}
