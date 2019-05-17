package com.wdw.toptips.controller;

import com.wdw.toptips.service.ToutiaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//@Controller
public class IndexController {
    @Autowired
    private ToutiaoService toutiaoService;

    @RequestMapping(path = {"/"})
    @ResponseBody
    public String index(){
        return "Hello wdw87" + toutiaoService.say();
    }
}
