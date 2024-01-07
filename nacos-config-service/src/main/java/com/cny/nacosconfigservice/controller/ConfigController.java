package com.cny.nacosconfigservice.controller;

import com.cny.nacosconfigservice.config.CustomConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author : chennengyuan
 */
@RestController
//@RefreshScope
public class ConfigController {

    //@Value("${custom.database}")
    //private String database;

    @Resource
    private CustomConfig customConfig;

    @GetMapping("/config")
    public String getDatabase() {
        return "database=" + customConfig.getDatabase();
    }
}
