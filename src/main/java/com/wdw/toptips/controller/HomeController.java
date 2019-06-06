package com.wdw.toptips.controller;

import com.wdw.toptips.model.News;
import com.wdw.toptips.model.ViewObject;
import com.wdw.toptips.service.NewsService;
import com.wdw.toptips.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    UserService userService;

    @Autowired
    NewsService newsService;

    /**
     * 获取主页要显示的新闻的信息，存放在一个List<ViewObject>中
     * @param userid 发新闻用户的id
     * @param offset 分页偏移
     * @param limit 每页现实的新闻数量
     * @return
     */
    private List<ViewObject> getNews(int userid, int offset, int limit) {
        List<ViewObject> vos = new ArrayList<>();
        List<News> newsList = newsService.getLatestNews(userid, offset, limit);
        for (News news : newsList) {
            ViewObject vo = new ViewObject();
            vo.set("news", news);
            vo.set("user", userService.getUser(news.getUserId()));
            vos.add(vo);
        }
        return vos;
    }

    /**
     * 主页Controller
     * vos为自建类的集合，内部存放页面显示所需的信息
     * @param model
     * @return
     */
    @RequestMapping(path = {"/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model) {
        model.addAttribute("vos", getNews(0, 0, 10));
        return "home";
    }

    /**
     * 用户主页Controller，只做测试，功能尚不完全
     * @param model
     * @param userId 用户id
     * @param pop 用于测试的请求参数
     * @return
     */
    @RequestMapping(path = {"/user/{userId}"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String userIindex(Model model, @PathVariable("userId") int userId,
                             @RequestParam(value = "pop", defaultValue = "0") String pop) {
        model.addAttribute("vos", getNews(userId, 0, 10));
        model.addAttribute("pop", pop);
        return "home";
    }
}
