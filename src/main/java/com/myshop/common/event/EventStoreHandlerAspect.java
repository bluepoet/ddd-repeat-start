package com.myshop.common.event;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by Mac on 2016. 6. 27..
 */
@Aspect
@Order(1)
@Component
public class EventStoreHandlerAspect {
    @Autowired
    private EventStoreHandler eventStoreHandler;

    @Before("execution(public * com.myshop..*Service.*(..))")
    public void registerEventStoreHandler() throws Throwable {
        Events.handle(eventStoreHandler);
    }

    @Autowired
    public void setEventStoreHandler(EventStoreHandler eventStoreHandler) {
        this.eventStoreHandler = eventStoreHandler;
    }
}
