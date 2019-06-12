package com.wdw.toptips;

import com.wdw.toptips.async.EventModel;
import com.wdw.toptips.async.EventProducer;
import com.wdw.toptips.async.EventType;
import com.wdw.toptips.model.User;
import com.wdw.toptips.util.JedisAdapter;
import com.wdw.toptips.util.MailSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Wudw
 * @Date: 2019/5/24 15:36
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MailTest {

    @Autowired
    private EventProducer eventProducer;
    @Autowired
    MailSender mailSender;
    @Test
    public void mailTest() {

        Map<String,Object> map = new HashMap<>();
        map.put("username","mailTest");
        mailSender.sendWithHtmlTemplate("694439986@qq.com","注册成功","/mails/registe.ftl",map);
    }
}
