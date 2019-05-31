package com.wdw.toptips.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Setting页面controller
 * 简单的权限判断演示，只有登录用户能够访问setting页面
 * 通过拦截器实现
 * @Author: Wudw
 * @Date: 2019/5/21 17:07
 * @Version 1.0
 */
@Controller
public class SettingController {

    @RequestMapping(path = {"/setting"})
    @ResponseBody
    public String setting() {
        return "Setting page";
    }

}
