package com.wdw.toptips.configuration;


import com.wdw.toptips.interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: Wudw
 * @Date: 2019/5/20 21:12
 * @Version 1.0
 */
@Component
public class ToptipsWebConfiguration implements WebMvcConfigurer {

    @Autowired
    PassportInterceptor passportInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册定义好的拦截器passportInterceptor
        registry.addInterceptor(passportInterceptor);
    }
}
