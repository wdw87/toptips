package com.wdw.toptips;

import com.wdw.toptips.dao.LoginTicketDAO;
import com.wdw.toptips.dao.NewsDAO;
import com.wdw.toptips.dao.UserDAO;
import com.wdw.toptips.model.LoginTicket;
import com.wdw.toptips.model.News;
import com.wdw.toptips.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql("/init-schema.sql")
public class InitDatabaseTests {
    @Autowired
    UserDAO userDAO;

    @Autowired
    NewsDAO newsDao;

    @Autowired
    LoginTicketDAO loginTicketDAO;

    @Test
    public void initData() {
        Random random = new Random();
        for (int i = 0; i < 11; ++i) {
            User user = new User();
            user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
            user.setName(String.format("USER%d", i));
            user.setPassword("");
            user.setSalt("");
            userDAO.addUser(user);

            News news = new News();

            news.setCommentCount(i);
            Date date = new Date();
            date.setTime(date.getTime() + 1000 * 3600 * 5 * i);
            news.setCreatedDate(date);
            news.setImage(String.format("http://images.nowcoder.com/head/%dm.png", random.nextInt(1000)));
            news.setLikeCount(i + 1);
            news.setUserId(i + 1);
            news.setTitle(String.format("TITLE: %d", i + 1));
            news.setLink(String.format("http://images.nowcoder.com/head/%dm.png", i));
            newsDao.addNews(news);

            user.setId(i + 1);
            user.setPassword("newpassword");
            userDAO.updatePassword(user);

            LoginTicket ticket = new LoginTicket();
            ticket.setStatus(0);
            ticket.setUserId(i + 1);
            ticket.setExpired(date);
            ticket.setTicket(String.format("TICKET%d", i + 1));
            loginTicketDAO.addTicket(ticket);

            loginTicketDAO.updateStatus(ticket.getTicket(), 1);
        }


        Assert.assertEquals("newpassword", userDAO.selectById(1).getPassword());

        userDAO.deleteById(1);
        Assert.assertNull(userDAO.selectById(1));

        Assert.assertEquals("TICKET2", loginTicketDAO.selectByTicket("TICKET2").getTicket());

    }

}
