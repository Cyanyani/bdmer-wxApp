package com.bdmer.wxapp.common.aspect;

import com.bdmer.wxapp.common.tool.RedisHelper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class LoginAspect {
    private static final Logger LOG = LoggerFactory.getLogger(LoginAspect.class);

    @Autowired
    RedisHelper redisHelper;
    /*
     * 登陆AOP（获取cookie中的token）- start
     */
    @Pointcut("execution(public * com.bdmer.wxapp.controller.*.*(..)) && !execution(public * com.bdmer.wxapp.controller.AuthController.login(..))")
    public void checkLogin() {
    }

    //对使用到token的函数检查
    @Around("checkLogin()")
    public Object doBeforeLogin(ProceedingJoinPoint joinPoint) throws Throwable{
        LOG.info("登陆校验执行开始");

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //获取cookie中的token
        String token = "";
        Cookie[] cookies =  request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("token")){
                    token = cookie.getValue();
                }
            }
        }

        //检查登陆
        if("".equals(token)){
            LOG.error("token不在cookie中");
            throw new Exception("token不在cookie中");
        }

        //从reids中获取缓存的用户信息
        //Jscode2session jscode2session =  redisHelper.get(token, Jscode2session.class);
        /*if(jscode2session == null){
            LOG.error("token不在redis中");
            throw new Exception("token不在redis中");
        }*/

        //向使用token的Controler中传递token
        //获取目标方法的参数信息
        Object[] objs = joinPoint.getArgs();

        //第一个参数规定是token（String）
        objs[0] = token;

        return joinPoint.proceed(objs);
    }

    @After("checkLogin()")
    public void doAftereLogin(){
        LOG.info("登陆校验执行完毕");
    }

}
