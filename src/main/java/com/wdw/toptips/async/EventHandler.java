package com.wdw.toptips.async;

import java.util.List;

/**
 * 事件处理接口
 * @Author: Wudw
 * @Date: 2019/6/10 21:48
 * @Version 1.0
 */
public interface EventHandler {
    void doHandler(EventModel model);
    List<EventType> getSupportEventTypes();
}
