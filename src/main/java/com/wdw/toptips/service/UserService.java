package com.wdw.toptips.service;

import com.wdw.toptips.dao.LoginTicketDAO;
import com.wdw.toptips.dao.UserDAO;
import com.wdw.toptips.model.LoginTicket;
import com.wdw.toptips.model.User;
import com.wdw.toptips.util.ToutiaoUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private LoginTicketDAO loginTicketDAO;

    /**
     * 封装了登陆过程对用户名和密码进行判断的相关代码：
     * 1、判断用户名和密码是否为空
     * 2、用户名是否已经被注册
     * 3、新用户信息写入数据库并对密码进行加密
     * 4、下发ticket，结合拦截器，实现单点登陆
     * @param username
     * @param passsword
     * @return
     */
    public Map<String, Object> register(String username, String passsword) {
        Map<String, Object> map = new HashMap<>();
        //1、判断用户名和密码是否为空
        if (StringUtils.isBlank(username)) {
            map.put("msgname", "用户名不能为空");
            return map;
        }
        if (StringUtils.isBlank(passsword)) {
            map.put("msgpwd", "密码不能为空");
            return map;
        }
        //2、用户名是否已经被注册
        User user = userDAO.selectByName(username);
        if (user != null) {
            map.put("msgname", "用户名已存在");
            return map;
        }
        //3、新用户信息写入数据库并对密码进行加密
        user = new User();
        user.setName(username);
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        user.setSalt(UUID.randomUUID().toString().substring(0, 5));
        //这里使用MD5和加salt的方式对用户密码进行了加密
        //在用户密码的基础上添加了一段随机字符串，然后在进行MD5加密
        user.setPassword(ToutiaoUtil.MD5(passsword + user.getSalt()));
        userDAO.addUser(user);
        //4、下发ticket，实现单点登陆
        map.put("ticket", addLoginTicket(user.getId()));
        map.put("userId", user.getId());
        return map;
    }

    /**
     * 封装了登录过程对用户名和密码进行判断的过程
     * @param username
     * @param passsword
     * @return
     */
    public Map<String, Object> login(String username, String passsword) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isBlank(username)) {
            map.put("msgname", "用户名不能为空");
            return map;
        }
        if (StringUtils.isBlank(passsword)) {
            map.put("msgpwd", "密码不能为空");
            return map;
        }
        User user = userDAO.selectByName(username);

        if (user == null) {
            map.put("msgname", "用户名不存在");
            return map;
        }
        if (!ToutiaoUtil.MD5(passsword + user.getSalt()).equals(user.getPassword())) {
            map.put("msgpwd", "密码错误");
            return map;
        }
        //add ticket
        //登陆
        map.put("ticket", addLoginTicket(user.getId()));
        return map;
    }

    /**
     * 登出的简单实现
     * 将用户获得的ticket状态置1，表示ticket过期，用户登出
     * @param ticket
     */
    public void logout(String ticket) {
        loginTicketDAO.updateStatus(ticket, 1);
    }

    /**
     * 根据userId生成ticket并将其写入数据库
     * @param userId
     * @return
     */
    private String addLoginTicket(int userId) {
        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(userId);
        Date date = new Date();
        date.setTime(date.getTime() + 1000 * 3600 * 24);
        ticket.setExpired(date);
        ticket.setStatus(0);
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
        loginTicketDAO.addTicket(ticket);
        return ticket.getTicket();
    }

    public User getUser(int id) {
        return userDAO.selectById(id);
    }

}
