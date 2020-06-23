package com.wdw.toptips.schedule;

import com.wdw.toptips.dao.NewsDAO;
import com.wdw.toptips.dao.UserLikeNewsDao;
import com.wdw.toptips.model.LikeRecord;
import com.wdw.toptips.util.JedisAdapter;
import com.wdw.toptips.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Set;

@Configuration
@EnableScheduling
public class LikeScheduleTask {
    private static final Logger logger = LoggerFactory.getLogger(LikeScheduleTask.class);

    @Autowired
    JedisAdapter jedisAdapter;

    @Autowired
    NewsDAO newsDAO;

    @Autowired
    UserLikeNewsDao userLikeNewsDao;

    @Scheduled(cron = "0/60 * * * * ?")
    private void syncLikeInfo(){
        logger.info("redis sync started at " + new Date());
        long start = System.currentTimeMillis();
        String newsSetKey = RedisKeyUtil.getNewsSetKey();
        String newsIdStr = null;
        while((newsIdStr = jedisAdapter.spop(newsSetKey)) != null){
            int newsId = Integer.parseInt(newsIdStr);
            String userLikeSetKey = RedisKeyUtil.getUserLikeSetKey(newsId);
            String userIdStr = null;
            while((userIdStr = jedisAdapter.spop(userLikeSetKey)) != null){
                int userId = Integer.parseInt(userIdStr);
                String recordKey = RedisKeyUtil.getUserLikeRecordKey(newsId, userId);
                LikeRecord cacheRecord = jedisAdapter.getObject(recordKey, LikeRecord.class);
                LikeRecord dbRecord = userLikeNewsDao.selectByNewsIdAndUserId(newsId, userId);
                if(cacheRecord != null) {
                    if (dbRecord == null) {
                        userLikeNewsDao.addRecord(cacheRecord);
                    }
                    if (!cacheRecord.equals(dbRecord)) {
                        userLikeNewsDao.updateById(newsId, userId, cacheRecord.getStatus(), cacheRecord.getCreatedDate());
                    }
                }
                jedisAdapter.del(recordKey);
            }
            String countKey = RedisKeyUtil.getLikeCountKey(newsId);
            String countStr = jedisAdapter.get(countKey);
            if(countStr != null) {
                int dbCount = newsDAO.selectLikeCount(newsId);
                System.out.println(countStr + "-" + dbCount);
                newsDAO.updateNewsLikeCount(Integer.parseInt(countStr) + dbCount,newsId);
            }
            jedisAdapter.del(countKey);
        }
        long cost = System.currentTimeMillis() - start;
        logger.info("redis sync complete in " + cost + "ms !!");
    }
}
