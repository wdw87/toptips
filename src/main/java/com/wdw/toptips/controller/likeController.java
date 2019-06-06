package com.wdw.toptips.controller;

import com.wdw.toptips.model.EntityType;
import com.wdw.toptips.model.Hostholder;
import com.wdw.toptips.service.LikeService;
import com.wdw.toptips.service.NewsService;
import com.wdw.toptips.util.ToutiaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @Author: Wudw
 * @Date: 2019/6/6 10:29
 * @Version 1.0
 */
@Controller
public class likeController {

    @Autowired
    LikeService likeService;

    @Autowired
    Hostholder hostholder;

    @Autowired
    NewsService newsService;

    @RequestMapping(path = {"/like"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String like(@RequestParam(value = "newsId") int newsId) {
        if(hostholder.getUser() != null) {
            int userId = hostholder.getUser().getId();
            long likeCount = likeService.like(userId, EntityType.ENTITY_NEWS, newsId);
            newsService.updateLikeCount((int) likeCount, newsId);
            return ToutiaoUtil.getJSONString(0, String.valueOf(likeCount));
        }else{
            return ToutiaoUtil.getJSONString(1, "请先登录");
        }
    }
    @RequestMapping(path = {"/dislike"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String dislike(@RequestParam(value = "newsId") int newsId) {
        if(hostholder.getUser() != null) {
            int userId = hostholder.getUser().getId();
            long likeCount = likeService.dislike(userId, EntityType.ENTITY_NEWS, newsId);
            newsService.updateLikeCount((int) likeCount, newsId);
            return ToutiaoUtil.getJSONString(0, String.valueOf(likeCount));
        }else{
            return ToutiaoUtil.getJSONString(1, "请先登录");
        }
    }

}
