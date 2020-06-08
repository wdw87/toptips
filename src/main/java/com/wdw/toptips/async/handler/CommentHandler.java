package com.wdw.toptips.async.handler;

import com.wdw.toptips.async.EventHandler;
import com.wdw.toptips.async.EventModel;
import com.wdw.toptips.async.EventType;
import com.wdw.toptips.model.Comment;
import com.wdw.toptips.model.EntityType;
import com.wdw.toptips.service.CommentService;
import com.wdw.toptips.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class CommentHandler implements EventHandler {
    @Autowired
    CommentService commentService;

    @Autowired
    NewsService newsService;


    @Override
    public void doHandler(EventModel model) {

        int newsId = model.getEntityId();
        Comment comment = new Comment();
        comment.setUserId(model.getActorId());
        comment.setEntityId(newsId);
        comment.setEntityType(model.getEntityType());
        comment.setCreatedDate(new Date());
        comment.setContent(model.getExt("content"));
        comment.setStatus(Integer.parseInt(model.getExt("status")));

        commentService.addComment(comment);
        //获取评论数量，对评论数量进行更新，异步更新
        int commentCount = commentService.getCommentCount(newsId, EntityType.ENTITY_NEWS);
        newsService.updateCommentCount(commentCount,newsId);
        //........
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.COMMENT);
    }
}
