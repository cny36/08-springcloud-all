package com.cny.controller;

import com.cny.pojo.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : chennengyuan
 */
@Slf4j
@RestController
public class DownController {

    @GetMapping("/down")
    public ResultBean down() {
        log.info("下游开始处理中！");
        ResultBean resultBean = new ResultBean();
        resultBean.setStatusCode(200);
        resultBean.setData("下游处理 success");
        return resultBean;
    }
}
