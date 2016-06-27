package com.myshop.eventstore.api;

import java.util.List;

/**
 * Created by Mac on 2016. 6. 27..
 */
public interface EventStore {
    void save(Object event);
    List<EventEntry> get(long offset, long limit);
}
