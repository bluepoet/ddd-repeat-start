package com.myshop.common.event;

import com.myshop.eventstore.api.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Mac on 2016. 6. 27..
 */
@Component
public class EventStoreHandler implements EventHandler<Object> {
    @Autowired
    private EventStore eventStore;

    @Override
    public void handle(Object event) {
        eventStore.save(event);
    }

    @Autowired
    public void setEventStore(EventStore eventStore) {
        this.eventStore = eventStore;
    }
}
