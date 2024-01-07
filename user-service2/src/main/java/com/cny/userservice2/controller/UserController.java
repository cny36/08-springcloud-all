package com.cny.userservice2.controller;

import com.cny.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {

    @RequestMapping("/getAddress")
    public String getAddress() {
        System.out.println("8082");
        return "8082 BJ";
    }

    @RequestMapping("/getUser")
    public UserEntity getUser(Long userId) {
        log.info("UserService2");
        UserEntity user = null;
        if (userId == 101) {
            user = new UserEntity(101L, "小明", "13899899889", "SZ");
        } else if (userId == 102) {
            user = new UserEntity(102L, "小红", "13899899999", "BJ");
        }
        return user;
    }
}
