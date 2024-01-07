package com.cny.feignclients;

import com.cny.entity.UserEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("user-service")
public interface UserServiceFeignClient {

    @RequestMapping("/getUser")
    UserEntity getUser(@RequestParam("userId") Long userId);
}
