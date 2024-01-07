package com.cny.controller;

import com.cny.entity.UserEntity;
import com.cny.feignclients.UserServiceFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;

@Slf4j
@RestController
public class OrderController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * 基于OpenFeign的方式调用
     */
    @Autowired
    UserServiceFeignClient userServiceFeignClient;

    @RequestMapping("create")
    public String createOrder() {
        String url = "http://user-service:8080/getAddress";
        String address = restTemplate.getForObject(url, String.class);
        return "create order success! address = " + address;
    }


    @RequestMapping("create2")
    public String createOrder2() {
        String url = getUrl("user-service") + "/getAddress";
        String address = restTemplate.getForObject(url, String.class);
        return "create order success! address = " + address;
    }

    public String getUrl(String serviceName) {
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);
        if (CollectionUtils.isEmpty(instances)) {
            return null;
        }
        int size = instances.size();
        int num = new Random().nextInt(size);
        return instances.get(num).getUri().toString();
    }


    /**
     * 使用 OpenFeign 发起调用
     *
     * @param userId
     * @return
     */
    @RequestMapping("/createFeign")
    public String createOrder(Long userId) {
        //调用用户服务，获取到用户的相关信息
        UserEntity user = userServiceFeignClient.getUser(userId);
        if (user != null) {
            log.info("user = {}", user);
            return "create order sucess! send to " + user.getAddress();
        }
        return "create order faild! user is null!";
    }

}
