package com.cny.configuration;

import com.cny.rule.NacosVersionRule;
import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RibbonConfiguration {

    @Bean
    public IRule ribbonRule(){
        //return new RandomRule();
        //return new NacosWeightRule();
        //return new NacosSameClusterRule();
        return new NacosVersionRule();
    }
}
