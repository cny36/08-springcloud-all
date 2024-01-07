package com.cny.rule;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.DynamicServerListLoadBalancer;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 基于Nacos权重的随机负载均衡策略
 */
@Slf4j
public class NacosWeightRule extends AbstractLoadBalancerRule {

    @Autowired
    NacosServiceManager nacosServiceManager;
    @Autowired
    NacosDiscoveryProperties nacosDiscoveryProperties;


    @Override
    public Server choose(Object key) {
        //1.获取到负载均衡器操作对象
        DynamicServerListLoadBalancer loadBalancer = (DynamicServerListLoadBalancer) getLoadBalancer();
        //2.获取要访问的服务名称
        String serviceName = loadBalancer.getName();
        //3.获取NamingService对象
        NamingService namingService = nacosServiceManager.getNamingService(nacosDiscoveryProperties.getNacosProperties());
        //4.基于服务名称获取到对应的服务实例
        try {
            Instance instance = namingService.selectOneHealthyInstance(serviceName);
            if (instance == null) {
                log.warn("{} 找不到的对应的服务实例", serviceName);
                return null;
            }
            log.info("instance {}", instance);
            return new NacosServer(instance);
        } catch (NacosException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }
}
