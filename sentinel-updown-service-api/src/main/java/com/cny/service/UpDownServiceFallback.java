package com.cny.service;

import com.cny.pojo.ResultBean;
import org.springframework.stereotype.Component;

/**
 * @author : chennengyuan
 */
@Component
public class UpDownServiceFallback implements UpDownService {

    @Override
    public ResultBean down() {
        System.out.println("UpDownServiceFallback 触发兜底逻辑");
        ResultBean resultBean = new ResultBean();
        resultBean.setStatusCode(500);
        resultBean.setData("触发兜底逻辑 下游服务繁忙中。。。");
        return resultBean;
    }
}
