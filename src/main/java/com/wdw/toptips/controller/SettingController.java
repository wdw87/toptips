package com.wdw.toptips.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
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
