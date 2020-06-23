package com.wdw.toptips.service;

import com.wdw.toptips.model.LikeRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LikeServiceTest {

    @Autowired
    LikeService likeService;
    @Test
    public void luaIncr() {
        LikeRecord likeRecord = new LikeRecord(1,1,1,new Date());
        String lua = likeService.luaIncr(1,1,likeRecord);
        System.out.println(lua);
    }

    @Test
    public void luaDecr() {
    }
}