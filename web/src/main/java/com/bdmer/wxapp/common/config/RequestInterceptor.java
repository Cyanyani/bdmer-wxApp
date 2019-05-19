package com.bdmer.wxapp.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求拦截 - 拦截类
 *
 * @since 2019-04-24
 * @author gongdelang
 */
public class RequestInterceptor extends HandlerInterceptorAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(RequestInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LOG.info("【请求拦截器】 - 请求执行开始");
        //ip
        LOG.info("ip = {}", request.getRemoteAddr());
        //url
        LOG.info("url = {}", request.getRequestURL());
        //method
        LOG.info("method = {}", request.getMethod());
        //param
        LOG.info("param = {}", request.getParameterNames());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        LOG.info("【请求拦截器】 - 请求执行结束");
    }
}