package com.myshop.order.infra.repository

import com.myshop.SpringIntTestConfig
import com.myshop.order.command.domain.OrderNo
import com.myshop.order.command.domain.OrderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification


/**
 * Created by bluepoet on 2016. 7. 12..
 */
@SpringIntTestConfig
@Transactional
class JpaOrderRepositoryIntTest extends Specification {

    @Autowired
    def OrderRepository orderRepository

    def "user1 사용자의 주문목록을 확인한다."() {
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
}