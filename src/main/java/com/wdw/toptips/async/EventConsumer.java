package com.wdw.toptips.async;

import com.alibaba.fastjson.JSON;
import com.wdw.toptips.util.JedisAdapter;
import com.wdw.toptips.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Wudw
 * @Date: 2019/6/10 22:10
 * @Version 1.0
 */
@Service
public class EventConsumer implements InitializingBean, ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(EventConsumer.class);
    //将所有事件类型和它对应的handler关联起来
    private Map<EventType,List<EventHandler>> config = new HashMap<>();
    private ApplicationContext applicationContext;

    @Autowired
    JedisAdapter jedisAdapter;

    @Override
    public void afterPropertiesSet() throws Exception {
        //获取所有实现了EventHandler的类
        Map<String,EventHandler> beans = applicationContext.getBeansOfType(EventHandler.class);
        if(beans != null){
            for(Map.Entry<String,EventHandler> entry : beans.entrySet()){
                //获取该EventHandler需要处理的事件类型
                List<EventType> eventTypeList = entry.getValue().getSupportEventTypes();
                for(EventType type : eventTypeList){
                    if(!config.containsKey(type)){
                        config.put(type,new ArrayList<EventHandler>());
                    }
                    //建立一个类似路由表的map，将所有事件类型及需要处理它的handler关联来，放在一个map中
                    config.get(type).add(entry.getValue());
                }
            }
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    String key = RedisKeyUtil.getEventQueueKey();
                    List<String> events = jedisAdapter.brpop(key,0);
                    for(String event : events){
                        if(event.equals(key)){
                            continue;
                        }
                        EventModel eventModel = JSON.parseObject(event,EventModel.class);
                        if(!config.containsKey(eventModel.getEventType())){
                            logger.error("不能识别的事件");
                            continue;
                        }
                        for(EventHandler handler : config.get(eventModel.getEventType())){
                            //对不同的事件调用相应的handler
                            handler.doHandler(eventModel);
                        }
                    }
                }
            }
        });
        thread.start();

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
