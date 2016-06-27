package com.myshop.common.event;

/**
 * Created by Mac on 2016. 6. 26..
 */
public abstract class Event {
    private long timestamp;

    public Event() {
        this.timestamp = System.currentTimeMillis();
    }

    public long getTimestamp() {
        return timestamp;
    }
}
