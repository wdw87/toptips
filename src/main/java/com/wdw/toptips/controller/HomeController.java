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
import org.springframework.web.servlet.tags.form.AbstractDataBoundFormElementTag;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    UserService userService;

    @Autowired
    NewsService newsService;

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

    @RequestMapping(path = {"/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model) {
        model.addAttribute("vos", getNews(0, 0, 10));
        return "home";
    }

    @RequestMapping(path = {"/user/{userId}"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String userIindex(Model model, @PathVariable("userId") int userId,
                             @RequestParam(value = "pop", defaultValue = "0") String pop) {
        model.addAttribute("vos", getNews(userId, 0, 10));
        model.addAttribute("pop", pop);
        return "home";
    }
}
