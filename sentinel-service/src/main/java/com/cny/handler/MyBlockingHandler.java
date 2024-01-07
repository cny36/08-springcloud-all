package com.cny.handler;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.cny.pojo.ResultBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义Sentinel异常handler
 */
@Slf4j
@Component
public class MyBlockingHandler implements BlockExceptionHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {
        log.info("MyBlockingHandler handle ..........");

        //针对五种不同的异常（各种规则）做不同的提示
        ResultBean resultBean = null;
        if (e instanceof FlowException) {
            resultBean = ResultBean.builder().statusCode(601).message("接口被限流了").build();
        } else if (e instanceof DegradeException) {
            resultBean = ResultBean.builder().statusCode(602).message("服务被降级了").build();
        } else if (e instanceof ParamFlowException) {
            resultBean = ResultBean.builder().statusCode(603).message("热点参数限流了").build();
        } else if (e instanceof SystemBlockException) {
            resultBean = ResultBean.builder().statusCode(604).message("触发系统保护规则，限流了").build();
        } else if (e instanceof AuthorityException) {
            resultBean = ResultBean.builder().statusCode(605).message("授权规则不通过，限流了").build();
        }

        response.setStatus(500);
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getWriter(), resultBean);
    }

}
