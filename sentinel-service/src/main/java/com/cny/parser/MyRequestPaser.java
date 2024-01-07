package com.cny.parser;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : chennengyuan
 */
@Slf4j
@Component
public class MyRequestPaser implements RequestOriginParser {

    @Override
    public String parseOrigin(HttpServletRequest request) {
        String origin = request.getParameter("origin");
        if (StringUtils.isNotBlank(origin)) {
            return origin;
        }
        log.warn("请求未携带参数 origin");
        return "";
        //throw new IllegalArgumentException("origin parameter 未设置");
    }
}
