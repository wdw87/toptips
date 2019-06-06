package com.wdw.toptips.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 内部自建一张HashMap，可以向其中添加键与值
 * Created by rainday on 16/6/30.
 */
public class ViewObject {
    private Map<String, Object> objs = new HashMap<String, Object>();

    public void set(String key, Object value) {
        objs.put(key, value);
    }

    public Object get(String key) {
        return objs.get(key);
    }
}
