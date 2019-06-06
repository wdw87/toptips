package com.wdw.toptips.util;

/**
 * 生成redis的key
 * @Author: Wudw
 * @Date: 2019/6/6 9:54
 * @Version 1.0
 */
public class RedisKeyUtil {
    private static String SPLIT = ";";
    private static String BLZ_LIKE = "LIKE";
    private static String BLZ_DISLIKE = "DISLIKE";

    public static String getLikeKey(int entityType,int entityId){
        return BLZ_LIKE + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }
    public static String getDislikeKey(int entityType,int entityId){
        return BLZ_DISLIKE + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }


}
