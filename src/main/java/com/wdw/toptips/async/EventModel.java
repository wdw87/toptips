package com.wdw.toptips.async;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Wudw
 * @Date: 2019/6/10 21:36
 * @Version 1.0
 */
public class EventModel {

    private EventType eventType;
    private int actorId;
    private int entityId;
    private int entityType;
    private int entityOwnerId;
    private Map<String,String> exts = new HashMap<>();

    public EventModel(){

    }

    public EventModel(EventType type){
        this.eventType = type;
    }

    public EventModel setExt(String key,String value){
        exts.put(key, value);
        return this;
    }

    public String getExt(String key){
        return exts.get(key);
    }

    public EventType getEventType() {
        return eventType;
    }

    public EventModel setEventType(EventType eventType) {
        this.eventType = eventType;
        return this;
    }

    public int getActorId() {
        return actorId;
    }

    public EventModel setActorId(int actorId) {
        this.actorId = actorId;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public EventModel setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public EventModel setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityOwnerId() {
        return entityOwnerId;
    }

    public EventModel setEntityOwnerId(int entityOwnerId) {
        this.entityOwnerId = entityOwnerId;
        return this;
    }

    public Map<String, String> getExts() {
        return exts;
    }

    public EventModel setExts(Map<String, String> exts) {
        this.exts = exts;
        return this;
    }
}
