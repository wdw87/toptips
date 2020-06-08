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

}
