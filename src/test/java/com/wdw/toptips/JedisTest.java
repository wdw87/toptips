package com.wdw.toptips;

import com.wdw.toptips.model.User;
import com.wdw.toptips.util.JedisAdapter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: Wudw
 * @Date: 2019/5/24 15:36
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JedisTest {

    @Autowired
    private JedisAdapter jedisAdapter;

    @Test
    public void jedisTest() {

        User user = new User();
        user.setSalt("salt");
        user.setName("wdwtest");
        user.setPassword("123123");
        user.setId(1);
        user.setHeadUrl("http://www.wdw87.club");

        jedisAdapter.setObject("userxxxx",user);
        User u = jedisAdapter.getObject("userxxxx",User.class);

    }
}
