package com.wdw.toptips.util;

import com.wdw.toptips.controller.CommentController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Author: Wudw
 * @Date: 2019/6/5 15:29
 * @Version 1.0
 */
@Component
public class JedisAdapter implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(JedisAdapter.class);

    private JedisPool pool= null;


    public static void print(int index,Object object){
        System.out.println(String.format("%d,%s",index, object.toString()));
    }
    public static void main(String[] args) {
        Jedis jedis = new Jedis();
        jedis.flushAll();
        jedis.set("hello","word");
        print(1,jedis.get("hello"));
        jedis.rename("hello","newhello");
        print(2,jedis.get("newhello"));
    }

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
}
