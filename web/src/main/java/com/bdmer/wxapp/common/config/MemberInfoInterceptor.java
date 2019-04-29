package com.bdmer.wxapp.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * 拦截器 - 请求拦截
 *
 * @since 2019-04-24
 * @author gongdelang
 */
public class MemberInfoInterceptor extends HandlerInterceptorAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(MemberInfoInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LOG.info("请求执行开始");
        //ip
        LOG.info("ip={}", request.getRemoteAddr());
        //url
        LOG.info("url={}", request.getRequestURL());
        //method
        LOG.info("method={}", request.getMethod());
        //param
        LOG.info("method={}", request.getParameterNames());
        //class
        LOG.info("class={}", handler.getClass());

        return true;
    }
}