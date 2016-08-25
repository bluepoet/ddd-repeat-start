package com.myshop.order.command.application

import com.myshop.catalog.domain.product.ProductRepository
import com.myshop.common.model.Address
import com.myshop.member.domain.MemberId
import com.myshop.order.command.domain.OrderRepository
import com.myshop.order.command.domain.Orderer
import com.myshop.order.command.domain.Receiver
import com.myshop.order.command.domain.ShippingInfo
import spock.lang.Specification

import static java.util.Collections.emptyList
import static java.util.Collections.singletonList

/**
 * Created by bluepoet on 2016. 8. 25..
 */
class PlaceOrderServiceTest extends Specification {
    PlaceOrderService svc
    ProductRepository mockProductRepository
    OrderRepository mockOrderRepository

    void setup() {
        svc = new PlaceOrderService()
        mockProductRepository = Mock(ProductRepository)
        mockOrderRepository = Mock(OrderRepository)
        svc.setProductRepository(mockProductRepository)
        svc.setOrderRepository(mockOrderRepository)
    }

    def "주문요청이 잘못된 경우를 검증한다."() {
        when:
        svc.placeOrder(orderRequest)

        then:
        thrown(expectedException)

        where:
        orderRequest                                 | expectedException
        null                                         | IllegalArgumentException
        new OrderRequest(orderer: null)              | IllegalArgumentException
        new OrderRequest(orderProducts: null)        | IllegalArgumentException
        new OrderRequest(orderProducts: emptyList()) | IllegalArgumentException
    }

    def createRequest() {
        OrderRequest orderRequest = new OrderRequest()
        orderRequest.setOrderer(new Orderer(new MemberId("orderer"), "주문자"))
        orderRequest.setOrderProducts(singletonList(new OrderProduct("P001", 2)))
        orderRequest.setShippingInfo(new ShippingInfo(new Address("12345", "addr1", "addr2"), "message", new Receiver("수취인", "010-1111-2222")))
        return orderRequest
    }
}