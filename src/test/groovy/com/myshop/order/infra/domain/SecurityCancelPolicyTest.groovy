package com.myshop.order.infra.domain

import com.myshop.SecurityContextUtil
import com.myshop.SpringIntTestConfig
import com.myshop.member.domain.MemberId
import com.myshop.order.command.domain.CancelPolicy
import com.myshop.order.command.domain.Canceller
import com.myshop.order.command.domain.Order
import com.myshop.order.command.domain.Orderer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import spock.lang.Specification

/**
 * Created by Mac on 2016. 7. 5..
 */
@SpringIntTestConfig
class SecurityCancelPolicyTest extends Specification {
    @Autowired
    CancelPolicy cancelPolicy

    void cleanup() {
        SecurityContextHolder.clearContext()
    }

    def "어드민롤을 가지면 주문취소 권한을 가진다."() {
        when:
        SecurityContextUtil.setAuthentication("admin", "ROLE_ADMIN")

        then:
        cancelPolicy.hasCancellationPermission(createOrder(), new Canceller("admin")) == true
    }

    def createOrder() {
        def order = Mock(Order)
        order.getOrderer() >> new Orderer(new MemberId("user1"), "")
        return order;
    }
}