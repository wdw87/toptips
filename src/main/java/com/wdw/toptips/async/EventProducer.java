package com.wdw.toptips.async;

import com.alibaba.fastjson.JSONObject;
import com.wdw.toptips.util.JedisAdapter;
import com.wdw.toptips.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Wudw
 * @Date: 2019/6/10 21:52
 * @Version 1.0
 */
@Service
public class EventProducer {
    private static final Logger logger = LoggerFactory.getLogger(EventProducer.class);

    @Autowired
    JedisAdapter jedisAdapter;

    /**
     * 将事件序列化为json串，添加到事件队列中
     * @param model
     * @return
     */
    public boolean fireEvent(EventModel model){
        try {
            String json = JSONObject.toJSONString(model);
            String key = RedisKeyUtil.getEventQueueKey();
            jedisAdapter.lpush(key,json);
            return true;
        }catch (Exception e){
            logger.error("redis事件队列异常 " + e.getMessage());
            return false;
        }
    }

}
