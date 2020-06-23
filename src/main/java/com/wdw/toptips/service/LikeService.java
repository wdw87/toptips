package com.wdw.toptips.service;

import com.alibaba.fastjson.JSON;
import com.wdw.toptips.dao.NewsDAO;
import com.wdw.toptips.dao.UserLikeNewsDao;
import com.wdw.toptips.model.Hostholder;
import com.wdw.toptips.model.LikeRecord;
import com.wdw.toptips.util.JedisAdapter;
import com.wdw.toptips.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: Wudw
 * @Date: 2019/6/6 9:51
 * @Version 1.0
 */
@Service
public class LikeService {
    Logger logger = LoggerFactory.getLogger(LikeService.class);
    @Autowired
    JedisAdapter jedisAdapter;

    @Autowired
    Hostholder hostholder;

    @Autowired
    UserLikeNewsDao userLikeNewsDao;

    @Autowired
    NewsDAO newsDAO;

    public long like(int userId, int entityType, int entityId) {
        //1. 在news_user_set中查找是否已经点赞
        String key = RedisKeyUtil.getUserLikeSetKey(entityId);
        if (jedisAdapter.sismember(key, String.valueOf(userId))) {
            key = RedisKeyUtil.getUserLikeRecordKey(entityId, userId);
            LikeRecord likeRecord = jedisAdapter.getObject(key, LikeRecord.class);
            if (likeRecord == null) {
                logger.warn("Redis 缓存不一致 key: [" + key + "]");
            } else if (likeRecord.getStatus() == 0) {
                likeRecord.setStatus(1);
//                //3.1 newsId写入变动set   newsSet
//                key = RedisKeyUtil.getNewsSetKey();
//                jedisAdapter.sadd(key, String.valueOf(entityId));
//                //3.2 向news_user_set中添加userId，表示这个用户点过或者取消过赞
//                key = RedisKeyUtil.getUserLikeSetKey(entityId);
//                jedisAdapter.sadd(key, String.valueOf(userId));
//                //3.3 记录点赞详细信息
//                key = RedisKeyUtil.getUserLikeRecordKey(entityId, userId);
//                jedisAdapter.setObject(key, likeRecord);
//                //3.4 点赞计数器
//                key = RedisKeyUtil.getLikeCountKey(entityId);
//                jedisAdapter.incr(key);
                jedisAdapter.eval(getIncrScript(), getKeys(entityId, userId, likeRecord), getArgv(entityId, userId, likeRecord));
            }
            int countInDB = newsDAO.selectLikeCount(entityId);
            key = RedisKeyUtil.getLikeCountKey(entityId);
            String countStr = jedisAdapter.get(key);
            int countInCache = countStr == null ? 0 : Integer.parseInt(jedisAdapter.get(key));
            return countInDB + countInCache;
        } else {
            LikeRecord likeRecord = userLikeNewsDao.selectByNewsIdAndUserId(entityId, userId);
            if (likeRecord != null && likeRecord.getStatus() == 0) {
                likeRecord.setStatus(1);
//                //3.1 newsId写入变动set   newsSet
//                key = RedisKeyUtil.getNewsSetKey();
//                jedisAdapter.sadd(key, String.valueOf(entityId));
//                //3.2 向news_user_set中添加userId，表示这个用户点过或者取消过赞
//                key = RedisKeyUtil.getUserLikeSetKey(entityId);
//                jedisAdapter.sadd(key, String.valueOf(userId));
//                //3.3 记录点赞详细信息
//                key = RedisKeyUtil.getUserLikeRecordKey(entityId, userId);
//                jedisAdapter.setObject(key, likeRecord);
//                //3.4 点赞计数器
//                key = RedisKeyUtil.getLikeCountKey(entityId);
//                jedisAdapter.incr(key);
                jedisAdapter.eval(getIncrScript(), getKeys(entityId, userId, likeRecord), getArgv(entityId, userId, likeRecord));
            } else if (likeRecord == null) {
                likeRecord = new LikeRecord(entityId, userId, 1, new Date());
//                //3.1 newsId写入变动set   newsSet
//                key = RedisKeyUtil.getNewsSetKey();
//                jedisAdapter.sadd(key, String.valueOf(entityId));
//                //3.2 向news_user_set中添加userId，表示这个用户点过或者取消过赞
//                key = RedisKeyUtil.getUserLikeSetKey(entityId);
//                jedisAdapter.sadd(key, String.valueOf(userId));
//                //3.3 记录点赞详细信息
//                key = RedisKeyUtil.getUserLikeRecordKey(entityId, userId);
//                jedisAdapter.setObject(key, likeRecord);
//                //3.4 点赞计数器
//                key = RedisKeyUtil.getLikeCountKey(entityId);
//                jedisAdapter.incr(key);
                jedisAdapter.eval(getIncrScript(), getKeys(entityId, userId, likeRecord), getArgv(entityId, userId, likeRecord));
            }
            int countInDB = newsDAO.selectLikeCount(entityId);
            key = RedisKeyUtil.getLikeCountKey(entityId);
            String countStr = jedisAdapter.get(key);
            int countInCache = countStr == null ? 0 : Integer.parseInt(jedisAdapter.get(key));
            return countInDB + countInCache;
        }
    }

    public long dislike(int userId, int entityType, int entityId) {

        //1. 在news_user_set中查找是否已经点赞
        String key = RedisKeyUtil.getUserLikeSetKey(entityId);
        if (jedisAdapter.sismember(key, String.valueOf(userId))) {
            key = RedisKeyUtil.getUserLikeRecordKey(entityId, userId);
            LikeRecord likeRecord = jedisAdapter.getObject(key, LikeRecord.class);
            if (likeRecord == null) {
                logger.warn("Redis 缓存不一致 key: [" + key + "]");
            } else if (likeRecord.getStatus() == 1) {
                likeRecord.setStatus(0);
//                //3.1 newsId写入变动set   newsSet
//                key = RedisKeyUtil.getNewsSetKey();
//                jedisAdapter.sadd(key, String.valueOf(entityId));
//                //3.2 向news_user_set中添加userId，表示这个用户点过或者取消过赞
//                key = RedisKeyUtil.getUserLikeSetKey(entityId);
//                jedisAdapter.sadd(key, String.valueOf(userId));
//                //3.3 记录点赞详细信息
//                key = RedisKeyUtil.getUserLikeRecordKey(entityId, userId);
//                jedisAdapter.setObject(key, likeRecord);
//                //3.4 点赞计数器
//                key = RedisKeyUtil.getLikeCountKey(entityId);
//                jedisAdapter.decr(key);
                jedisAdapter.eval(getDecrScript(), getKeys(entityId, userId, likeRecord), getArgv(entityId, userId, likeRecord));
            }
            int countInDB = newsDAO.selectLikeCount(entityId);
            key = RedisKeyUtil.getLikeCountKey(entityId);
            String countStr = jedisAdapter.get(key);
            int countInCache = countStr == null ? 0 : Integer.parseInt(jedisAdapter.get(key));
            return countInDB + countInCache;
        } else {
            LikeRecord likeRecord = userLikeNewsDao.selectByNewsIdAndUserId(entityId, userId);
            if (likeRecord != null && likeRecord.getStatus() == 1) {
                likeRecord.setStatus(0);
//                //3.1 newsId写入变动set   newsSet
//                key = RedisKeyUtil.getNewsSetKey();
//                jedisAdapter.sadd(key, String.valueOf(entityId));
//                //3.2 向news_user_set中添加userId，表示这个用户点过或者取消过赞
//                key = RedisKeyUtil.getUserLikeSetKey(entityId);
//                jedisAdapter.sadd(key, String.valueOf(userId));
//                //3.3 记录点赞详细信息
//                key = RedisKeyUtil.getUserLikeRecordKey(entityId, userId);
//                jedisAdapter.setObject(key, likeRecord);
//                //3.4 点赞计数器
//                key = RedisKeyUtil.getLikeCountKey(entityId);
//                jedisAdapter.decr(key);
                jedisAdapter.eval(getDecrScript(), getKeys(entityId, userId, likeRecord), getArgv(entityId, userId, likeRecord));
            } else if (likeRecord == null) {

            }
            int countInDB = newsDAO.selectLikeCount(entityId);
            key = RedisKeyUtil.getLikeCountKey(entityId);
            String countStr = jedisAdapter.get(key);
            int countInCache = countStr == null ? 0 : Integer.parseInt(jedisAdapter.get(key));
            return countInDB + countInCache;
        }
    }

    private List<String> getKeys(int newsId, int userId, LikeRecord likeRecord) {
        List<String> keys = new ArrayList<>();
        keys.add(RedisKeyUtil.getNewsSetKey());
        keys.add(RedisKeyUtil.getUserLikeSetKey(newsId));
        keys.add(RedisKeyUtil.getUserLikeRecordKey(newsId, userId));
        keys.add(RedisKeyUtil.getLikeCountKey(newsId));
        return keys;
    }

    private List<String> getArgv(int newsId, int userId, LikeRecord likeRecord) {
        List<String> argv = new ArrayList<>();
        argv.add(String.valueOf(newsId));
        argv.add(String.valueOf(userId));
        argv.add(JSON.toJSONString(likeRecord));
        return argv;
    }

    private String getIncrScript(){
        return "redis.call('SADD', KEYS[1], ARGV[1]);" +
                "redis.call('SADD', KEYS[2], ARGV[2]);" +
                "redis.call('SET', KEYS[3], ARGV[3]);" +
                "redis.call('INCR', KEYS[4]);" +
                "return 1;";
    }
    private String getDecrScript(){
        return "redis.call('SADD', KEYS[1], ARGV[1]);" +
                "redis.call('SADD', KEYS[2], ARGV[2]);" +
                "redis.call('SET', KEYS[3], ARGV[3]);" +
                "redis.call('DECR', KEYS[4]);" +
                "return 1;";
    }
    public String luaIncr(int newsId, int userId, LikeRecord likeRecord) {
        String key1 = RedisKeyUtil.getNewsSetKey();
        String key2 = RedisKeyUtil.getUserLikeSetKey(newsId);
        String key3 = RedisKeyUtil.getUserLikeRecordKey(newsId, userId);
        String key4 = RedisKeyUtil.getLikeCountKey(newsId);
        String arg1 = String.valueOf(newsId);
        String arg2 = String.valueOf(userId);
        String arg3 = JSON.toJSONString(likeRecord);
        return "eval \"" +
                "redis.call('SADD', KEYS[1], ARGV[1]);" +
                "redis.call('SADD', KEYS[2], ARGV[2]);" +
                "redis.call('SET', KEYS[3], ARGV[3]);" +
                "redis.call('INCR', KEYS[4]);" +
                "return 1;" +
                "\" 4 " + key1 + " " + key2 + " " + key3 + " " + key4 + " " + arg1 + " " + arg2 + " " + "'" + arg3 + "'";
    }

    public String luaDecr(int newsId, int userId, LikeRecord likeRecord) {
        String key1 = RedisKeyUtil.getNewsSetKey();
        String key2 = RedisKeyUtil.getUserLikeSetKey(newsId);
        String key3 = RedisKeyUtil.getUserLikeRecordKey(newsId, userId);
        String key4 = RedisKeyUtil.getLikeCountKey(newsId);
        String arg1 = String.valueOf(newsId);
        String arg2 = String.valueOf(userId);
        String arg3 = JSON.toJSONString(likeRecord);
        return "eval \"" +
                "redis.call('SADD', KEYS[1], ARGV[1]);" +
                "redis.call('SADD', KEYS[2], ARGV[2]);" +
                "redis.call('SET', KEYS[3], ARGV[3]);" +
                "redis.call('DECR', KEYS[4]);" +
                "return 1;" +
                "\" 4 " + key1 + " " + key2 + " " + key3 + " " + key4 + " " + arg1 + " " + arg2 + " " + "'" + arg3 + "'";
    }

}
