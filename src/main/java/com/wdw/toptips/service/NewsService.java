package com.wdw.toptips.service;

import com.wdw.toptips.dao.NewsDAO;
import com.wdw.toptips.model.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {
    @Autowired
    private NewsDAO newsDao;

    public List<News> getLatestNews(int userId , int offset , int limit){
        return newsDao.selectByUserIdAndOffset(userId,offset,limit);
    }
}
