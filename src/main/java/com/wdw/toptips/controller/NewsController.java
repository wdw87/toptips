package com.wdw.toptips.controller;

import com.wdw.toptips.model.*;
import com.wdw.toptips.service.CommentService;
import com.wdw.toptips.service.NewsService;
import com.wdw.toptips.service.QiniuService;
import com.wdw.toptips.service.UserService;
import com.wdw.toptips.util.ToutiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: Wudw
 * @Date: 2019/5/23 20:45
 * @Version 1.0
 */
@Controller
public class NewsController {

    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    NewsService newsService;

    @Autowired
    UserService userService;

    @Autowired
    QiniuService qiniuService;

    @Autowired
    CommentService commentService;

    @Autowired
    Hostholder hostholder;

    @RequestMapping(path = "/addComment", method = {RequestMethod.POST})
    public String addComment(@RequestParam("newsId") int newsId,
                             @RequestParam("content") String content) {
        try {
            Comment comment = new Comment();
            comment.setUserId(hostholder.getUser().getId());
            comment.setEntityId(newsId);
            comment.setEntityType(EntityType.ENTITY_NEWS);
            comment.setCreatedDate(new Date());
            comment.setContent(content);
            comment.setStatus(0);
            commentService.addComment(comment);

            int commentCount = commentService.getCommentCount(newsId, EntityType.ENTITY_NEWS);
            newsService.updateCommentCount(commentCount,newsId);

        }catch (Exception e){
            logger.error("添加评论失败 " + e.getMessage());
        }

        return "redirect:/news/" + String.valueOf(newsId);
    }

    @RequestMapping(path = {"/news/{newsId}"}, method = {RequestMethod.GET})
    public String addComment(@PathVariable("newsId") int newsId, Model model) {
        News news = newsService.getNewsById(newsId);
        if (news != null) {
            //评论
            List<Comment> comments = commentService.getCommentByEntity(news.getId(), EntityType.ENTITY_NEWS);
            List<ViewObject> commentVOs = new ArrayList<>();
            for (Comment comment : comments) {
                ViewObject vo = new ViewObject();
                vo.set("comment", comment);
                vo.set("user", userService.getUser(comment.getUserId()));
                commentVOs.add(vo);
            }
            model.addAttribute("commentVOs", commentVOs);
        }
        model.addAttribute("news", news);
        model.addAttribute("owner", userService.getUser(news.getUserId()));
        return "detail";
    }


    @RequestMapping(path = {"/image"}, method = {RequestMethod.GET})
    @ResponseBody
    public void getImg(@RequestParam("name") String name,
                       HttpServletResponse response) {
        //response.setContentType("image/jpg");
        response.setContentType("image/" + ToutiaoUtil.getFileExt(name));
        try {
            String path = ToutiaoUtil.IMG_DIR + name;
            StreamUtils.copy(new FileInputStream(new File(path)), response.getOutputStream());

        } catch (IOException e) {
            logger.error("图片读取失败" + e.getMessage());
        }

    }

    @RequestMapping(path = {"/uploadImage/"}, method = {RequestMethod.POST})
    @ResponseBody
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String fileUrl = qiniuService.saveImage(file);
            if (fileUrl == null) {
                return ToutiaoUtil.getJSONString(1, "上传失败");
            }
            return ToutiaoUtil.getJSONString(0, fileUrl);
        } catch (Exception e) {
            logger.error("上传图片失败" + e.getMessage());
            return ToutiaoUtil.getJSONString(1, "上传失败");
        }
    }

    @RequestMapping(path = {"/user/addNews"}, method = {RequestMethod.POST})
    @ResponseBody
    public String addNews(@RequestParam("image") String image,
                          @RequestParam("title") String title,
                          @RequestParam("link") String link) {
        try {
            News news = new News();
            news.setCreatedDate(new Date());
            news.setImage(image);
            news.setLink(link);
            news.setTitle(title);
            if (hostholder.getUser() == null) {
                //如果用户未登录
                news.setUserId(3);//3代表匿名用户
            } else {
                news.setUserId(hostholder.getUser().getId());
            }

            newsService.addNews(news);

            return ToutiaoUtil.getJSONString(0);
        } catch (Exception e) {
            logger.error("添加资讯失败" + e.getMessage());
            return ToutiaoUtil.getJSONString(1, "发布失败");
        }

    }


}
