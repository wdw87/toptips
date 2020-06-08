package com.wdw.toptips.util;

import com.alibaba.fastjson.JSON;
import com.wdw.toptips.controller.CommentController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

import java.util.List;

/**
 * @Author: Wudw
 * @Date: 2019/6/5 15:29
 * @Version 1.0
 */
@Component
public class JedisAdapter implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(JedisAdapter.class);

    private JedisPool pool= null;


//    public static void print(int index,Object object){
//        System.out.println(String.format("%d,%s",index, object.toString()));
//    }
//    public static void main(String[] args) {
//        Jedis jedis = new Jedis();
//        jedis.flushAll();
//        jedis.set("hello","word");
//        print(1,jedis.get("hello"));
//        jedis.rename("hello","newhello");
//        print(2,jedis.get("newhello"));
//    }

    @Override
    public void afterPropertiesSet() throws Exception {
        pool = new JedisPool("localhost",6379);
    }
    public long sadd(String key,String value){
        Jedis jedis = new Jedis();
        try {
            jedis = pool.getResource();
            return jedis.sadd(key,value);
        }catch (Exception e){
            logger.error("Jedis发生异常 " + e.getMessage());
            return 0;
        }finally{
            if(jedis != null){
                jedis.close();
            }
        }
    }

    public String set(String key, String value, long time){
        Jedis jedis = new Jedis();
        try{
            jedis = pool.getResource();
            SetParams setParams = new SetParams();
            setParams.px(time);
            return jedis.set(key, value, setParams);
        }finally {
            jedis.close();
        }
    }

    public long del(String key){
        Jedis jedis = new Jedis();
        try{
            jedis = pool.getResource();
            return jedis.del(key);
        }finally {
            jedis.close();
        }
    }


    public long srem(String key,String value){
        Jedis jedis = new Jedis();
        try {
            jedis = pool.getResource();
            return jedis.srem(key,value);
        }catch (Exception e){
            logger.error("Jedis发生异常 " + e.getMessage());
            return 0;
        }finally{
            if(jedis != null){
                jedis.close();
            }
        }
    }
    public boolean sismember(String key,String value){
        Jedis jedis = new Jedis();
        try {
            jedis = pool.getResource();
            return jedis.sismember(key,value);
        }catch (Exception e){
            logger.error("Jedis发生异常 " + e.getMessage());
            return false;
        }finally{
            if(jedis != null){
                jedis.close();
            }
        }
    }
    public long scard(String key){
        Jedis jedis = new Jedis();
        try {
            jedis = pool.getResource();
            return jedis.scard(key);
        }catch (Exception e){
            logger.error("Jedis发生异常 " + e.getMessage());
            return 0;
        }finally{
            if(jedis != null){
                jedis.close();
            }
        }
    }

    public long lpush(String key,String value){
        Jedis jedis = new Jedis();
        try {
            jedis = pool.getResource();
            return jedis.lpush(key,value);
        }catch (Exception e){
            logger.error("Jedis发生异常 " + e.getMessage());
            return 0;
        }finally{
            if(jedis != null){
                jedis.close();
            }
        }
    }

    public List<String> brpop(String key, int timeout){
        Jedis jedis = new Jedis();
        try {
            jedis = pool.getResource();
            return jedis.brpop(timeout,key);
        }catch (Exception e){
            logger.error("Jedis发生异常 " + e.getMessage());
            return null;
        }finally{
            if(jedis != null){
                jedis.close();
            }
        }
    }

    public void set(String key,String val){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.set(key,val);
        }catch (Exception e){
            logger.error("Redis 发生异常 " + e.getMessage() );
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }
    public String get(String key){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.get(key);
        }catch (Exception e){
            logger.error("Redis 发生异常 " + e.getMessage() );
            return null;
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }
    public void setObject(String key,Object obj){
        set(key, JSON.toJSONString(obj));
    }
    public <T> T getObject(String key,Class<T> cls){
        String val = get(key);
        if(val != null){
            return JSON.parseObject(val,cls);
        }
        return null;
    }


}
