package com.wdw.toptips.controller;

import com.wdw.toptips.async.EventModel;
import com.wdw.toptips.async.EventProducer;
import com.wdw.toptips.async.EventType;
import com.wdw.toptips.model.*;
import com.wdw.toptips.service.CommentService;
import com.wdw.toptips.service.NewsService;
import com.wdw.toptips.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 评论中心Controller
 * 包括对新闻的评论和对评论的评论（目前未实现）
 * @Author: Wudw
 * @Date: 2019/5/31 16:42
 * @Version 1.0
 */
@Controller
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    NewsService newsService;

    @Autowired
    UserService userService;

    @Autowired
    CommentService commentService;

    @Autowired
    Hostholder hostholder;

    @Autowired
    EventProducer eventProducer;
    /**
     * 添加新闻评论，由前端向"/addComment"发起请求
     * @param newsId  新闻id
     * @param content 评论内容
     * @return
     */
    @RequestMapping(path = "/addComment", method = {RequestMethod.POST})
    public String addComment(@RequestParam("newsId") int newsId,
                             @RequestParam("content") String content) {
        try {
//            Comment comment = new Comment();
//            comment.setUserId(hostholder.getUser().getId());
//            comment.setEntityId(newsId);
//            comment.setEntityType(EntityType.ENTITY_NEWS);
//            comment.setCreatedDate(new Date());
//            comment.setContent(content);
//            comment.setStatus(0);
//
//
//
//            commentService.addComment(comment);
//
//            //获取评论数量，对评论数量进行更新，后续需实现异步更新
//            int commentCount = commentService.getCommentCount(newsId, EntityType.ENTITY_NEWS);
//            newsService.updateCommentCount(commentCount,newsId);
//            //........

            eventProducer.fireEvent(new EventModel(EventType.COMMENT)
                    .setActorId(hostholder.getUser().getId())
                    .setEntityId(newsId)
                    .setEntityType(EntityType.ENTITY_NEWS)
                    .setEntityOwnerId(newsId)
                    .setExt("content", content)
                    .setExt("date", new Date().toString())
                    .setExt("status", "0")
            );
        }catch (Exception e){
            logger.error("添加评论失败 " + e.getMessage());
        }
        //重定向到主页
        return "redirect:/news/" + String.valueOf(newsId);
    }

    /**
     * 显示评论详情页面
     * 将前端要显示的内容通过List<ViewObject>，反馈至前端
     * @param newsId
     * @param model
     * @return
     */
    @RequestMapping(path = {"/news/{newsId}"}, method = {RequestMethod.GET})
    public String addComment(@PathVariable("newsId") int newsId, Model model) {
        News news = newsService.getNewsById(newsId);
        if (news != null) {
            //将要显示的内容添加到List<ViewObject>中，方便将数据反馈到前端
            List<Comment> comments = commentService.getCommentByEntity(news.getId(), EntityType.ENTITY_NEWS);
            List<ViewObject> commentVOs = new ArrayList<>();
            for (Comment comment : comments) {
                ViewObject vo = new ViewObject();
                vo.set("comment", comment);//放入评论
                vo.set("user", userService.getUser(comment.getUserId()));//放入评论人的id
                commentVOs.add(vo);
            }
            model.addAttribute("commentVOs", commentVOs);
        }
        model.addAttribute("news", news);
        model.addAttribute("owner", userService.getUser(news.getUserId()));//放入发新闻的用户id
        return "detail";
    }
}
