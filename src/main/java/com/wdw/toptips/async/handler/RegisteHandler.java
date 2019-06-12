package com.wdw.toptips.async.handler;

import com.wdw.toptips.async.EventHandler;
import com.wdw.toptips.async.EventModel;
import com.wdw.toptips.async.EventType;
import com.wdw.toptips.util.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Wudw
 * @Date: 2019/6/11 21:14
 * @Version 1.0
 */
@Component
public class RegisteHandler implements EventHandler {

    @Autowired
    MailSender mailSender;


    @Override
    public void doHandler(EventModel model) {

        Map<String,Object> map = new HashMap<>();
        map.put("username",model.getExt("username"));
        mailSender.sendWithHtmlTemplate(model.getExt("email"),"注册成功","/mails/registe.ftl",map);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.REGISTE);
    }
}
