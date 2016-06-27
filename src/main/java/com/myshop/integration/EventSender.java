package com.myshop.integration;

import com.myshop.eventstore.api.EventEntry;

/**
 * Created by Mac on 2016. 6. 27..
 */
public interface EventSender {
    void send(EventEntry event);
}
