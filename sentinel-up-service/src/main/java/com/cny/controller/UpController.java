package com.cny.controller;

import com.cny.pojo.ResultBean;
import com.cny.service.UpDownService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : chennengyuan
 */
@Slf4j
@RestController
public class UpController {

    @Autowired
    UpDownService upDownService;

    @GetMapping("/up")
    public ResultBean<String> updown(){
        log.info("上游开始处理中！");
        log.info("下游处理结果：{}", upDownService.down().toString());
        ResultBean<String> resultBean = new ResultBean<>();
        resultBean.setStatusCode(200);
        resultBean.setData("success");
        return resultBean;
    }
}
