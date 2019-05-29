package com.wdw.toptips.service;

import com.wdw.toptips.dao.CommentDao;
import com.wdw.toptips.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Wudw
 * @Date: 2019/5/28 21:36
 * @Version 1.0
 */
@Service
public class CommentService {
    @Autowired
    private CommentDao commentDao;

    public List<Comment> getCommentByEntity(int entityId ,int entityType){
        return commentDao.selectByEntity(entityId,entityType);
    }

    public int addComment(Comment comment){
        return commentDao.addComment(comment);
    }

    public int getCommentCount(int entityId ,int entityType){
        return commentDao.getCommentCount(entityId,entityType);
    }
}
