package com.myshop.order.command.application

import com.myshop.catalog.domain.product.Product
import com.myshop.catalog.domain.product.ProductId
import com.myshop.catalog.domain.product.ProductRepository
import com.myshop.common.model.Address
import com.myshop.common.model.Money
import com.myshop.member.domain.MemberId
import com.myshop.order.command.domain.*
import org.mockito.ArgumentCaptor
import spock.lang.Specification

import static java.util.Collections.emptyList
import static java.util.Collections.singletonList
import static org.mockito.Mockito.*

/**
 * Created by bluepoet on 2016. 8. 25..
 */
class PlaceOrderServiceTest extends Specification {
    PlaceOrderService svc
    ProductRepository mockProductRepository
    OrderRepository mockOrderRepository

    void setup() {
        svc = new PlaceOrderService()
        mockProductRepository = mock(ProductRepository)
        mockOrderRepository = mock(OrderRepository)
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

    def "없는상품을 주문했을때 결과를 검증한다."() {
        given:
        def productId = new ProductId("P001")
        def noProductId = new ProductId("NO_P001")
        when(mockProductRepository.findById(productId)).thenReturn(new Product(productId, "Product 001", new Money(1000), "detail", emptyList()))
        when(mockOrderRepository.nextOrderNo()).thenReturn(new OrderNo("mock.number"))
        when(mockProductRepository.findById(noProductId)).thenReturn(null)
        def orderRequest = createRequest()

        when:
        orderRequest.setOrderProducts(singletonList(new OrderProduct(noProductId.getId(), 2)))
        svc.placeOrder(orderRequest)

        then:
        thrown(NoOrderProductException)

        when:
        orderRequest.setOrderProducts(Arrays.asList(
                new OrderProduct(productId.getId(), 2),
                new OrderProduct(noProductId.getId(), 2)
        ))
        svc.placeOrder(orderRequest)

        then:
        thrown(NoOrderProductException)
    }

    def "정상적인 주문요청일 경우 결과를 검증한다."() {
        given:
        def productId = new ProductId("P001")
        when(mockProductRepository.findById(productId)).thenReturn(new Product(productId, "Product 001", new Money(1000), "detail", emptyList()))
        when(mockOrderRepository.nextOrderNo()).thenReturn(new OrderNo("mock.number"))
        def orderRequest = createRequest()

        when:
        svc.placeOrder(orderRequest)

        then:
        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class)
        verify(mockOrderRepository).save(orderCaptor.capture())

        Order createdOrder = orderCaptor.getValue()
        createdOrder != null
    }

    def createRequest() {
        OrderRequest orderRequest = new OrderRequest()
        orderRequest.setOrderer(new Orderer(new MemberId("orderer"), "주문자"))
        orderRequest.setOrderProducts(singletonList(new OrderProduct("P001", 2)))
        orderRequest.setShippingInfo(new ShippingInfo(new Address("12345", "addr1", "addr2"), "message", new Receiver("수취인", "010-1111-2222")))
        return orderRequest
    }
}