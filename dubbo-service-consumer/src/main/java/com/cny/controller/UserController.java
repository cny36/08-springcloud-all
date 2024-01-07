package com.cny.controller;

import com.cny.api.UserService;
import com.cny.entity.User;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @DubboReference
    private UserService userService;

    @RequestMapping("getUser")
    public User getUser() {
        return userService.getUser(1L);
    }
}
