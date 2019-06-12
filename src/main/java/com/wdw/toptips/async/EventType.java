package com.wdw.toptips.async;

/**
 * 表示发生的事件的类型
 * @Author: Wudw
 * @Date: 2019/6/10 21:36
 * @Version 1.0
 */
public enum EventType {
    LIKE(0),
    COMMENT(1),
    LOGIN(2),
    REGISTE(3),
    MAIL(4);

    private int val;

    EventType(int val){
        this.val = val;
    }
    public int getValue(){
        return val;
    }


}
