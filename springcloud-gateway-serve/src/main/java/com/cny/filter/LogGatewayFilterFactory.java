package com.cny.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * @author : chennengyuan
 * 自定义局部过滤器
 */
@Component
public class LogGatewayFilterFactory extends AbstractGatewayFilterFactory<LogGatewayFilterFactory.LogConfig> {

    public LogGatewayFilterFactory() {
        super(LogConfig.class);
    }

    @Override
    public GatewayFilter apply(LogConfig config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                if (config.log) {
                    System.out.println("有请求进入了 LogGatewayFilterFactory");
                }
                return chain.filter(exchange);
            }
        };
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("log");
    }

    public static class LogConfig {
        private boolean log;

        public boolean isLog() {
            return log;
        }

        public void setLog(boolean log) {
            this.log = log;
        }
    }
}
