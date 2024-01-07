package com.cny.service;

import com.cny.api.UserService;
import com.cny.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

@Slf4j
@DubboService
public class UserServiceImpl implements UserService {

    @Override
    public User getUser(Long userId) {
        User user = new User();
        user.setUserId(userId);
        user.setName("spring cloud alibaba dubbo");
        return user;
    }
}
