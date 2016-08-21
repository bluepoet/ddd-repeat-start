package com.myshop.common.event

import com.myshop.SpringIntTestConfig
import com.myshop.eventstore.api.EventStore
import com.myshop.member.application.BlockMemberService
import com.myshop.order.command.application.CancelOrderService
import com.myshop.order.command.domain.Canceller
import com.myshop.order.command.domain.OrderNo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Specification

import java.util.concurrent.atomic.AtomicBoolean


/**
 * Created by bluepoet on 2016. 7. 31..
 */
@SpringIntTestConfig
@DirtiesContext
class EventResetProcessorTest extends Specification {
    @Autowired
    BlockMemberService blockMemberService

    @Autowired
    CancelOrderService cancelOrderService

    AtomicBoolean handled = new AtomicBoolean(false)

    @Autowired
    ApplicationContext context

    void setup() {
        context.getBean("eventStoreHandler", EventStoreHandler.class).setEventStore(Mock(EventStore))
        Events.handle({ TestEvent evt -> handled.set(true) })
    }

    def "사용자를 블록시키고 TestEvent를 처리할 핸들러가 있는지 검증한다."() {
        given:
        initSecurityContextForBlockMemeber()

        when:
        blockMemberService.block("user1")

        then:
        assertNoEventHandler()
    }

    def "주문을 취소하고 TestEvent를 처리할 핸들러가 있는지 검증한다."() {
        when:
        cancelOrderService.cancel(new OrderNo("ORDER-001"), new Canceller("user1"))

        then:
        assertNoEventHandler()
    }

    def assertNoEventHandler() {
        Events.raise(new TestEvent())
        handled.get() == false
    }

    def initSecurityContextForBlockMemeber() {
        SecurityContextHolder.setContext(new SecurityContext() {
            @Override
            Authentication getAuthentication() {
                return new UsernamePasswordAuthenticationToken("admin", "admin", Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")))
            }

            @Override
            void setAuthentication(Authentication authentication) {

            }
        })
    }
}