package com.cny;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZonedDateTime;

@SpringBootTest
class SpringcloudGatewayServeApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(ZonedDateTime.now());
    }

}
