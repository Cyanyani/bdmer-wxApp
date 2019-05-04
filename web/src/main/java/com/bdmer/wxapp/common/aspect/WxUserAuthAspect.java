package com.bdmer.wxapp.common.aspect;

import com.bdmer.wxapp.common.enums.ResponseEnum;
import com.bdmer.wxapp.common.exception.WxException;
import com.bdmer.wxapp.common.tool.WxUserHolder;
import com.bdmer.wxapp.common.tool.RedisUtil;
import com.bdmer.wxapp.common.tool.Util;
import com.bdmer.wxapp.dto.other.UserTokenDTO;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 微信用户请求验证 - 切面类
 *
 * @since 2019-05-03
 * @author gongdl
 */
@Aspect
@Component
public class WxUserAuthAspect {
    private static final Logger LOG = LoggerFactory.getLogger(WxUserAuthAspect.class);

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 登陆AOP(获取cookie中的token)
     */
    @Pointcut("execution(public * com.bdmer.wxapp.controller.wx.*.*(..)) && !execution(public * com.bdmer.wxapp.controller.wx.UserController.login(..))")
    public void checkLogin() {
    }

    /**
     * 对使用到token的函数检查
     * @throws Exception
     */
    @Before("checkLogin()")
    public void doBeforeLogin() throws Exception{
        LOG.info("【微信用户登陆验证拦截器】 - 登陆校验执行开始");

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 获取cookie中的token
        String token = "";
        Cookie[] cookies =  request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("token")){
                    token = cookie.getValue();
                }
            }
        }

        // 检查登陆
        if("".equals(token)){
            LOG.error("【微信用户登陆验证拦截器】- token不在cookie中");
            throw new WxException(ResponseEnum.ERROR_WX_USER_TOKEN);
        }

        // 向MemberHolder中存储用户信息
        UserTokenDTO userTokenDTO =  redisUtil.get(token, UserTokenDTO.class);
        if(Util.isNullOrEmpty(userTokenDTO)){
            LOG.error("【微信用户登陆验证拦截器】- token不在redis中");
            throw new WxException(ResponseEnum.ERROR_WX_USER_TOKEN);
        }
        WxUserHolder.setUserTokenDTO(userTokenDTO);

        return;
    }

}
