package com.cny.predicate;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.GatewayPredicate;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author : chennengyuan
 * 自定义谓词工厂
 */
@Component
public class TimeRoutePredicateFactory extends AbstractRoutePredicateFactory<TimeRoutePredicateFactory.Config> {

    public TimeRoutePredicateFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("startTime", "endTime");
    }

    @Override
    public Predicate<ServerWebExchange> apply(TimeRoutePredicateFactory.Config config) {
        LocalTime startTime = config.getStartTime();
        LocalTime endTime = config.getEndTime();
        return new GatewayPredicate() {
            @Override
            public boolean test(ServerWebExchange serverWebExchange) {
                LocalTime now = LocalTime.now();
                return now.isAfter(startTime) && now.isBefore(endTime);
            }
        };
    }

    public static class Config {
        private LocalTime startTime;
        private LocalTime endTime;

        public LocalTime getStartTime() {
            return startTime;
        }

        public void setStartTime(LocalTime startTime) {
            this.startTime = startTime;
        }

        public LocalTime getEndTime() {
            return endTime;
        }

        public void setEndTime(LocalTime endTime) {
            this.endTime = endTime;
        }
    }


}
