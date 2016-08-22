package com.myshop.common.event

import com.myshop.SpringIntTestConfig
import com.myshop.order.command.application.CancelOrderService
import com.myshop.order.command.domain.Canceller
import com.myshop.order.command.domain.OrderCanceledEvent
import com.myshop.order.command.domain.OrderNo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Specification

import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference


/**
 * Created by bluepoet on 2016. 8. 23..
 */
@SpringIntTestConfig
@DirtiesContext
class EventStoreHandlerAspectTest extends Specification {
    @Autowired
    CancelOrderService cancelOrderService

    AtomicBoolean handled = new AtomicBoolean(false)

    @Autowired
    ApplicationContext context
    EventStoreHandler stubHandler

    AtomicReference<Object> handledEvent = new AtomicReference<>()

    void setup() {
        stubHandler = new EventStoreHandler() {
            @Override
            public void handle(Object event) {
                handledEvent.set(event)
            }
        }
        context.getBean("eventStoreHandlerAspect", EventStoreHandlerAspect.class).setEventStoreHandler(stubHandler)
        Events.handle({ TestEvent event -> handled.set(true) })
    }

    def "주문을 취소하고 발생한 이벤트를 잡아 검증한다."() {
        when:
        cancelOrderService.cancel(new OrderNo("ORDER-001"), new Canceller("user1"))

        then:
        def capturedEvent = handledEvent.get()
        capturedEvent instanceof OrderCanceledEvent
    }
}