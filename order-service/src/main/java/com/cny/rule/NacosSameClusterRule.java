package com.cny.rule;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.DynamicServerListLoadBalancer;
import com.netflix.loadbalancer.Server;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 优先调用同集群节点 规则
 */
@Slf4j
public class NacosSameClusterRule extends AbstractLoadBalancerRule {

    @Autowired
    NacosServiceManager nacosServiceManager;
    @Autowired
    NacosDiscoveryProperties nacosDiscoveryProperties;

    @SneakyThrows
    @Override
    public Server choose(Object key) {
        //1.获取到负载均衡器操作对象
        DynamicServerListLoadBalancer loadBalancer = (DynamicServerListLoadBalancer) getLoadBalancer();
        //2.获取要访问的服务名称
        String serviceName = loadBalancer.getName();
        //3.获取NamingService对象
        NamingService namingService = nacosServiceManager.getNamingService(nacosDiscoveryProperties.getNacosProperties());
        //4.基于服务名称获取到对应的服务实例列表
        List<Instance> instances = namingService.selectInstances(serviceName, true);
        if (CollectionUtils.isEmpty(instances)) {
            log.warn("没有找到对应的服务节点 {}", serviceName);
            return null;
        }
        //5.获取到集群名称
        String clusterName = nacosDiscoveryProperties.getClusterName();
        //6.筛选同集群的节点
        List<Instance> chooseInstanceList = instances;
        if (StringUtils.isNotBlank(clusterName)) {
            List<Instance> filterList = instances.stream().filter(i -> i.getClusterName().equals(clusterName)).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(filterList)) {
                log.warn("没有同集群的服务节点 {}， 需要跨集群访问", serviceName);
            } else {
                chooseInstanceList = filterList;
            }
        }

        //7.
        Instance instance = NacosBalancer.getHostByRandomWeight2(chooseInstanceList);
        return new NacosServer(instance);
    }

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }
}
