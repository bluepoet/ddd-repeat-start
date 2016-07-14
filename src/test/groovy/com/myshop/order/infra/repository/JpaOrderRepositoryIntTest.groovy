package com.myshop.order.infra.repository

import com.myshop.SpringIntTestConfig
import com.myshop.common.jpaspec.Specs
import com.myshop.order.command.domain.OrderNo
import com.myshop.order.command.domain.OrderRepository
import com.myshop.order.command.domain.OrderSpecs
import com.myshop.order.command.domain.OrdererSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import java.sql.Date
import java.time.LocalDateTime
import java.time.ZoneId

/**
 * Created by bluepoet on 2016. 7. 12..
 */
@SpringIntTestConfig
@Transactional
class JpaOrderRepositoryIntTest extends Specification {

    @Autowired
    def OrderRepository orderRepository

    def "주문자 아이디(user1)로 주문정보을 확인한다."() {
        given:
        def ordererId = "user1"
        def startRow = 0
        def fetchSize = 10

        when:
        def orders = orderRepository.findByOrdererId(ordererId, startRow, fetchSize)

        then:
        orders.size() == 2
        orders.get(0).getNumber().getNumber() == "ORDER-002"
        orders.get(1).getNumber().getNumber() == "ORDER-001"
        orders.get(1).getOrderLines().size() == 2
    }

    def "주문번호로 조회해 해당주문의 상품갯수를 확인한다."() {
        given:
        def orderNo = "ORDER-002"

        when:
        def order = orderRepository.findById(new OrderNo(orderNo))

        then:
        order.getOrderLines().size() == 1
    }

    def "Specification(OrderSpec)을 이용해 주문정보를 확인한다."() {
        given:
        def spec = new OrdererSpec("user1")

        when:
        def orders = orderRepository.findAll(spec, "number.number desc")

        then:
        orders.size() == 2
        orders.get(1).getOrderLines().size() == 2
    }

    def "두개 Specification을 and로 묶어 주문정보를 확인한다."() {
        given:
        def specs = createSpecification()

        when:
        def orders = orderRepository.findAll(specs, "orderer.memberId.id asc", "number.number desc")

        then:
        orders.size() == 1
    }

    def "Specification으로 검색한 결과를 카운트한다."() {
        given:
        def specs = createSpecification()

        when:
        def counts = orderRepository.counts(specs)

        then:
        counts.longValue() == 1L
    }

    def createSpecification() {
        def fromTime = LocalDateTime.of(2016, 1, 1, 0, 0, 0);
        def toTime = LocalDateTime.of(2016, 1, 2, 0, 0, 0);
        return Specs.and(
                OrderSpecs.orderer("user1"),
                OrderSpecs.between(
                        Date.from(fromTime.atZone(ZoneId.systemDefault()).toInstant()),
                        Date.from(toTime.atZone(ZoneId.systemDefault()).toInstant())
                )
        )
    }

    def "모든 주문목록을 카운트한다."() {
        when:
        def counts = orderRepository.countsAll()

        then:
        counts.longValue() == 3
    }
}