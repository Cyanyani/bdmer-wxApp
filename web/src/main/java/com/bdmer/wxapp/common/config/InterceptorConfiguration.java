package com.bdmer.wxapp.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * 拦截器配置(通过实现WebMvcConfigurer) - 配置类
 */
@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestInterceptor()).addPathPatterns("/**");
    }
}
