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
 * 优先同版本调用 规则
 */
@Slf4j
public class NacosVersionRule extends AbstractLoadBalancerRule {

    @Autowired
    NacosServiceManager nacosServiceManager;
    @Autowired
    NacosDiscoveryProperties nacosDiscoveryProperties;


    @SneakyThrows
    @Override
    public Server choose(Object key) {
        //1.获取到负载均衡器操作对象
        DynamicServerListLoadBalancer loadBalancer = (DynamicServerListLoadBalancer) getLoadBalancer();
        NamingService namingService = nacosServiceManager.getNamingService(nacosDiscoveryProperties.getNacosProperties());

        String serviceName = loadBalancer.getName();
        String clusterName = nacosDiscoveryProperties.getClusterName();
        String version = nacosDiscoveryProperties.getMetadata().get("version");

        List<Instance> instances = namingService.selectInstances(serviceName, true);
        if (CollectionUtils.isEmpty(instances)) {
            log.warn("没有找到对应的服务节点 {}", serviceName);
            return null;
        }

        List<Instance> sameVersionInstances = instances.stream().filter(instance -> version.equals(instance.getMetadata().get("version"))).collect(Collectors.toList());
        if (sameVersionInstances.isEmpty()) {
            log.warn("未找到相同版本的服务实例 version：{}, serviceName：{}", version, serviceName);
            return null;
        }

        List<Instance> chooseInstances = sameVersionInstances;
        if (StringUtils.isNotBlank(clusterName)) {
            List<Instance> sameClusterInstances = sameVersionInstances.stream().filter(instance -> clusterName.equals(instance.getClusterName())).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(sameClusterInstances)) {
                log.warn("没有同集群的服务节点 {}， 需要跨集群访问", serviceName);
            } else {
                chooseInstances = sameClusterInstances;
            }
        }

        Instance instance = NacosBalancer.getHostByRandomWeight2(chooseInstances);
        return new NacosServer(instance);
    }


    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }
}
