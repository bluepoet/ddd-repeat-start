package com.myshop.order.infra.dao

import com.myshop.SpringIntTestConfig
import com.myshop.order.query.dao.OrderSummaryDao
import com.myshop.order.query.dao.OrderSummarySpecs
import com.myshop.order.query.dto.OrderSummary
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

/**
 * Created by bluepoet on 2016. 7. 6..
 */
@SpringIntTestConfig
class JpaOrderSummaryDaoIntTest extends Specification {
    @Autowired
    OrderSummaryDao orderSummaryDao

    def "user1 주문자의 주문목록을 확인한다."() {
        when:
        def orders = orderSummaryDao.selectByOrderer("user1")

        then:
        orders.size() == 2
        def orderView = orders.get(0)
        orderView.getNumber() == "ORDER-002"
        orderView.getVersion() == 2L
        orderView.getOrdererId() == "user1"
        orderView.getOrdererName() == "사용자1"
        orderView.getProductId() == "prod-001"
        orderView.getProductName() == "라즈베리파이3 모델B"
        orderView.getTotalAmounts() == 5000
    }

    def "검색조건(ordererId)에 따른 주문갯수를 확인한다."(com.myshop.common.jpaspec.Specification specification, Long resultCount) {
        expect:
        orderSummaryDao.counts(specification) == resultCount

        where:
        specification                        | resultCount
        null                                 | 3L
        OrderSummarySpecs.ordererId("user1") | 2L
        OrderSummarySpecs.ordererId("user2") | 1L
    }

    def "검색조건(ordererId, firstRow, maxResults)에 따른 주문갯수를 확인한다."(com.myshop.common.jpaspec.Specification<OrderSummary> spec, int firstRow, int maxResults, int resultSize) {
        expect:
        orderSummaryDao.select(spec, firstRow, maxResults).size() == resultSize

        where:
        spec                                 | firstRow | maxResults | resultSize
        null                                 | 0        | 10         | 3
        OrderSummarySpecs.ordererId("user1") | 0        | 10         | 2
        OrderSummarySpecs.ordererId("user2") | 0        | 10         | 1
    }
}