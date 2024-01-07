package com.cny.configuration;

import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Configuration;

/**
 * 全局负载均衡策略配置
 */
@Configuration
@RibbonClients(defaultConfiguration = RibbonConfiguration.class)
public class DefaultRibbonConfiguration {
}
