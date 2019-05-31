package com.wdw.toptips.model;

import org.springframework.stereotype.Component;

/**
 * @Author: Wudw
 * @Date: 2019/5/20 20:47
 * @Version 1.0
 */
@Component
public class Hostholder {
    //线程本地变量，对于同一个static ThreadLocal
    // 不同线程只能从中 get set remove 自己的变量，而不会影响其他线程的变量
    //用于存储本次访问的用户是谁
    private static ThreadLocal<User> users = new ThreadLocal<>();

    public User getUser() {
        return users.get();
    }

    public void setUser(User user) {
        users.set(user);
    }

    public void clear() {
        users.remove();
    }
}
