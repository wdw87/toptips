package com.wdw.toptips.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


//@aspect注释对于在类路径中自动检测是不够的：为了达到这个目的，您需要添加一个单独的@component注解---来自官方文档
@Aspect
@Component

public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Before("execution(* com.wdw.toptips.controller.IndexController.*(..))")
    public void beforeMethod(JoinPoint joinPoint){
        logger.info("before method");
    }
    @After("execution(* com.wdw.toptips.controller.IndexController.*(..))")
    public void afterMethod(JoinPoint joinPoint){
        logger.info("after method");
    }
}
