package com.wdw.toptips.service;

import com.wdw.toptips.model.Hostholder;
import com.wdw.toptips.util.JedisAdapter;
import com.wdw.toptips.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Wudw
 * @Date: 2019/6/6 9:51
 * @Version 1.0
 */
@Service
public class LikeService {
    @Autowired
    JedisAdapter jedisAdapter;

    @Autowired
    Hostholder hostholder;

    public int getLikeStatus(int userId ,int entityType,int entityId){
        if(jedisAdapter.sismember(RedisKeyUtil.getLikeKey(entityType,entityId),String.valueOf(userId))){
            return 1;
        }else if(jedisAdapter.sismember(RedisKeyUtil.getDislikeKey(entityType,entityId),String.valueOf(userId))){
            return -1;
        }else{
            return 0;
        }
    }
    public long like(int userId ,int entityType,int entityId){
        String likeKey = RedisKeyUtil.getLikeKey(entityType,entityId);
        String dislikeKey = RedisKeyUtil.getDislikeKey(entityType,entityId);
        jedisAdapter.sadd(likeKey,String.valueOf(hostholder.getUser().getId()));
        jedisAdapter.srem(dislikeKey,String.valueOf(hostholder.getUser().getId()));
        return jedisAdapter.scard(likeKey);
    }
    public long dislike(int userId ,int entityType,int entityId){
        String likeKey = RedisKeyUtil.getLikeKey(entityType,entityId);
        String dislikeKey = RedisKeyUtil.getDislikeKey(entityType,entityId);
        jedisAdapter.sadd(dislikeKey,String.valueOf(hostholder.getUser().getId()));
        jedisAdapter.srem(likeKey,String.valueOf(hostholder.getUser().getId()));
        return jedisAdapter.scard(likeKey);
    }

}
