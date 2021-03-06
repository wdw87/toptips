package com.wdw.toptips.controller;


import com.wdw.toptips.async.EventModel;
import com.wdw.toptips.async.EventProducer;
import com.wdw.toptips.async.EventType;
import com.wdw.toptips.service.UserService;
import com.wdw.toptips.util.ToutiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private EventProducer eventProducer;

    /**
     * 注册Controller
     * @param model
     * @param username 注册用户名
     * @param password 注册密码
     * @param rememberme 是否记住登陆
     * @param response HttpServletResponse
     * @return
     */
    @RequestMapping(path = {"/reg"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String reg(Model model, @RequestParam("username") String username,
                      @RequestParam("password") String password,
                      @RequestParam(value = "rember", defaultValue = "0") int rememberme,
                      HttpServletResponse response) {
        try {
            Map<String, Object> map = userService.register(username, password);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");
                cookie.setHttpOnly(true);
                //如果记住登陆，设置cookie生存时间
                if (rememberme > 0) {
                    cookie.setMaxAge(3600 * 24 * 7);
                }
                response.addCookie(cookie);
                eventProducer.fireEvent(new EventModel(EventType.REGISTE)
                        .setActorId((int)map.get("userId"))
                        .setExt("username",username)
                        .setExt("email","694439986@qq.com")
                );
                return ToutiaoUtil.getJSONString(0, "注册成功");
            } else {
                //注册异常，code=1，这里的code涉及到与前端的交互，必须正确
                return ToutiaoUtil.getJSONString(1, map);
            }

        } catch (Exception e) {
            logger.error("注册异常" + e.getMessage());
            return ToutiaoUtil.getJSONString(1, "注册异常");
        }
    }


    @RequestMapping(path = {"/login"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String login(Model model, @RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam(value = "rember", defaultValue = "0") int rememberme,
                        HttpServletResponse response) {
        try {
            Map<String, Object> map = userService.login(username, password);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");
                //如果记住登陆，设置cookie生存时间
                if (rememberme > 0) {
                    cookie.setMaxAge(3600 * 24 * 7);
                }
                response.addCookie(cookie);
                return ToutiaoUtil.getJSONString(0, "登陆成功");
            } else {
                //注册异常，code=1，这里的code涉及到与前端的交互，必须正确
                return ToutiaoUtil.getJSONString(1, map);
            }

        } catch (Exception e) {
            logger.error("登陆成功" + e.getMessage());
            return ToutiaoUtil.getJSONString(1, "登陆成功");
        }
    }

    @RequestMapping(path = {"/logout"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String logout(@CookieValue("ticket") String ticket) {
        userService.logout(ticket);
        return "redirect:/";
    }


}
