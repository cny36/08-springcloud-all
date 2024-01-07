package com.cny.userservice.controller;

import com.cny.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
public class UserController {

    @RequestMapping("/getAddress")
    public String getAddress() {
        System.out.println("8080");
        return "8848 SZ";
    }


    @RequestMapping("/getUser")
    public UserEntity getUser(Long userId) {
        log.info("UserService");
        UserEntity user = null;
        if (userId == 101) {
            user = new UserEntity(101L, "小明", "13899899889", "SZ");
        } else if (userId == 102) {
            user = new UserEntity(102L, "小红", "13899899999", "BJ");
        }
        return user;
    }


    @RequestMapping("/filter")
    public String springcloudgateway_filtertest(HttpServletRequest request) {
        System.out.println("X-Request-red=" + request.getHeader("X-Request-red"));
        System.out.println("color=" + request.getParameter("color"));
        return "filter success";
    }
}
