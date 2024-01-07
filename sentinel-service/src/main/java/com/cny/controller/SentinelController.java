package com.cny.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.cny.pojo.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class SentinelController {

    /**
     * 流控规则——测试
     *
     * @return String
     * @throws InterruptedException
     */
    @GetMapping("/flow_rule_test")
    public String flowRuleTest() throws InterruptedException {
        log.info("flow_rule_test SUCCESS");
        Thread.sleep(1);
        return "success";
    }

    /**
     * 降级规则-测试
     *
     * @return
     */
    @GetMapping("/slow_test")
    public String slow_test() {
        try {
            Thread.sleep(1001);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "slow";
    }

    @GetMapping("/exception_test")
    public String exception_test() {
        int i = 1/0;
        return "exception";
    }


    /**
     * 热点规则-测试
     * @param key1
     * @param key2
     * @return
     */
    @GetMapping("/hotkey_test")
    @SentinelResource(value = "hotkey",blockHandler = "hotkeyTestBlockHandler",fallback = "hotkeyTestFallbackHandler")
    public ResultBean hotkey_test(@RequestParam(value = "key1", required = false) String key1, @RequestParam(value = "key2", required = false) String key2) {
        //int i = 1/0;
        return new ResultBean(200, "success", null);
    }

    public ResultBean hotkeyTestBlockHandler(String key1, String key2, BlockException e) {
        return new ResultBean(500, "当前访问人数太多了", null);
    }

    public ResultBean hotkeyTestFallbackHandler(String key1, String key2, Throwable e) {
        return new ResultBean(500, "出现了其他异常", null);
    }


}
