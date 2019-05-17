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

    public Map<String,Object> register(String username,String passsword){
        Map<String,Object> map = new HashMap<>();
        if(StringUtils.isBlank(username)){
            map.put("msgname","用户名不能为空");
            return map;
        }
        if(StringUtils.isBlank(passsword)){
            map.put("msgpwd","密码不能为空");
            return map;
        }

        User user = userDAO.selectByName(username);

        if(user != null){
            map.put("msgname","用户名已存在");
            return map;
        }

        user = new User();
        user.setName(username);
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        user.setPassword(ToutiaoUtil.MD5(passsword + user.getSalt()));
        userDAO.addUser(user);

        //登陆
        map.put("ticket",addLoginTicket(user.getId()));
        return map;
    }


    public Map<String,Object> login(String username,String passsword){
        Map<String,Object> map = new HashMap<>();
        if(StringUtils.isBlank(username)){
            map.put("msgname","用户名不能为空");
            return map;
        }
        if(StringUtils.isBlank(passsword)){
            map.put("msgpwd","密码不能为空");
            return map;
        }

        User user = userDAO.selectByName(username);

        if(user == null){
            map.put("msgname","用户名不存在");
            return map;
        }
        if(ToutiaoUtil.MD5(passsword+ user.getSalt()) != user.getPassword()){
            map.put("msgpwd","密码错误");
            return map;
        }

        //add ticket
        //登陆
        map.put("ticket",addLoginTicket(user.getId()));
        return map;
    }

    /**
     * 根据userId生成ticket并将其写入数据库
     * @param userId
     * @return
     */
    private String addLoginTicket(int userId){
        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(userId);
        Date date = new Date();
        date.setTime(date.getTime() + 1000*3600*24);
        ticket.setExpired(date);
        ticket.setStatus(0);
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-",""));
        loginTicketDAO.addTicket(ticket);
        return ticket.getTicket();
    }
    public User getUser(int id){
        return userDAO.selectById(id);
    }

}
