package com.cny.service;

import com.cny.pojo.ResultBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author : chennengyuan
 */
@FeignClient(value = "sentinel-down-service", fallback = UpDownServiceFallback.class)
public interface UpDownService {

    @GetMapping("/down")
    ResultBean down();

}
