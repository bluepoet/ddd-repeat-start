package com.myshop.integration.infra;

import com.myshop.eventstore.api.EventEntry;
import com.myshop.integration.EventSender;
import org.springframework.stereotype.Component;

/**
 * Created by Mac on 2016. 6. 27..
 */
@Component
public class SysoutEventSender implements EventSender {
    @Override
    public void send(EventEntry event) {
        System.out.println("EventSender send event : " + event.getPayload());
    }
}
