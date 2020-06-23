package com.wdw.toptips.dao;

import com.wdw.toptips.model.LikeRecord;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserLikeNewsDaoTest extends TestCase {

    @Autowired
    private UserLikeNewsDao userLikeNewsDao;
    public void testSelectRecordCount() {
    }

    public void testSelectByUserId() {
    }

    public void testSelectByNewsId() {
    }

    @Test
    public void testAddRecord() {
        LikeRecord record = new LikeRecord(3,3, 1, new Date());
        userLikeNewsDao.addRecord(record);

        record = userLikeNewsDao.selectByNewsIdAndUserId(3,3);

        assertNotNull(record);

    }
}