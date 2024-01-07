package com.cny.nacosconfigservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @author : chennengyuan
 */
@Configuration
@RefreshScope
public class CustomConfig {

    @Value("${custom.database}")
    private String database;

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }
}
