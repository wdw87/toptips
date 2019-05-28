package com.wdw.toptips.interceptor;

import com.wdw.toptips.dao.LoginTicketDAO;
import com.wdw.toptips.dao.UserDAO;
import com.wdw.toptips.model.Hostholder;
import com.wdw.toptips.model.LoginTicket;
import com.wdw.toptips.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 拦截器
 *
 * @Author: Wudw
 * @Date: 2019/5/20 20:38
 * @Version 1.0
 */
@Component
public class PassportInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginTicketDAO loginTicketDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private Hostholder hostholder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ticket = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("ticket")) {
                    ticket = cookie.getValue();
                    break;
                }
            }
        }
        if (ticket == null) {
            return true;
        } else {
            LoginTicket loginTicket = loginTicketDAO.selectByTicket(ticket);
            if (loginTicket == null ||
                    loginTicket.getExpired().before(new Date()) ||
                    loginTicket.getStatus() != 0 ||
                    userDAO.selectById(loginTicket.getUserId()) == null) {
                return true;
            }
            User user = userDAO.selectById(loginTicket.getUserId());
            hostholder.setUser(user);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //与前端页面交互
        if (modelAndView != null && hostholder.getUser() != null) {
            modelAndView.addObject("user", hostholder.getUser());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostholder.clear();
    }
}
