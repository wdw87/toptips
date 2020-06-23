package com.wdw.toptips.util;

import com.wdw.toptips.model.LoginTicket;
import com.wdw.toptips.model.User;

/**
 * 生成redis的key
 * @Author: Wudw
 * @Date: 2019/6/6 9:54
 * @Version 1.0
 */
public class RedisKeyUtil {
    private static String SPLIT = ";";
    private static String BIZ_LIKE = "LIKE";

    private static String BIZ_DISLIKE = "DISLIKE";
    private static String BIZ_EVENT = "EVENT";
    private static String TICKET = "TICKET";

    private static String SET = "SET";
    private static String MAP = "MAP";

    public static String getEventQueueKey(){
        return BIZ_EVENT;
    }

    public static String getLikeKey(int entityType,int entityId){
        return BIZ_LIKE + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }
    public static String getDislikeKey(int entityType,int entityId){
        return BIZ_DISLIKE + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }

    public static String getTicketKey(String ticket){
        return TICKET + SPLIT + ticket;
    }

    /**
     * news点赞数量变动过
     * @return
     */
    public static String getNewsSetKey(){
        return BIZ_LIKE + SPLIT + "NEWS_SET";
    }

    /**
     * 谁给这条news点了赞
     * @param newsId
     * @return
     */
    public static String getUserLikeSetKey(int newsId){
        return BIZ_LIKE + SPLIT + "NEWS_USER_SET" + SPLIT + newsId;
    }

    /**
     * 点赞的具体信息
     * @param newsId
     * @param userId
     * @return
     */
    public static String getUserLikeRecordKey(int newsId, int userId){
        return BIZ_LIKE + SPLIT + "LIKE_RECORD" + SPLIT + newsId + SPLIT + userId;
    }

    /**
     * news的点赞计数器
     * @param newsId
     * @return
     */
    public static String getLikeCountKey(int newsId){
        return BIZ_LIKE + SPLIT + "COUNTER" + SPLIT + newsId;
    }

}
