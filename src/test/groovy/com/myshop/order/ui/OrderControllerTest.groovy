package com.myshop.order.ui

import com.myshop.SpringIntTestConfig
import com.myshop.catalog.domain.product.Product
import com.myshop.member.domain.MemberId
import com.myshop.order.command.application.OrderRequest
import com.myshop.order.command.domain.Orderer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view

/**
 * Created by bluepoet on 2016. 7. 19..
 */
@SpringIntTestConfig
@WebAppConfiguration
class OrderControllerTest extends Specification {
    @Autowired
    WebApplicationContext context

    MockMvc mockMvc

    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build()
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(new User("user1", "", Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))), ""))
    }

    def "주문한 상품을 확인한다."() {
        when:
        def result = mockMvc.perform(post("/orders/orderConfirm")
                .param("orderProducts[0].productId", "prod-001").param("orderProducts[0].quantity", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()

        then:
        def orderReq = (OrderRequest) result.getModelAndView().getModel().get("orderReq")
        orderReq.getOrderProducts().get(0).getProductId() == "prod-001"
        orderReq.getOrderProducts().get(0).getQuantity() == 1
        orderReq.getOrderer() == new Orderer(new MemberId("user1"), "사용자1")

        confirmProducts(result)
    }

    def "새로운 상품을 주문한다."() {
        when:
        def result = mockMvc.perform(post("/orders/order")
                .param("orderProducts[0].productId", "prod-001").param("orderProducts[0].quantity", "1")
                .param("shippingInfo.receiver.name", "수취인").param("shippingInfo.receiver.phone", "00011112222")
                .param("shippingInfo.address.zipCode", "55777")
                .param("shippingInfo.address.address1", "서울 어디구 어디동")
                .param("shippingInfo.address.address2", "자세한 주소"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("order/orderComplete"))
                .andReturn()

        then:
        def orderNo = (String) result.getModelAndView().getModel().get("orderNo")
        orderNo != null && orderNo != ""
    }

    def "주문시 배송지가 빠졌을 때, 에러결과를 확인한다."() {
        when:
        def result = mockMvc.perform(post("/orders/order")
                .param("orderProducts[0].productId", "prod-001").param("orderProducts[0].quantity", "1")
                .param("shippingInfo.receiver.name", "수취인").param("shippingInfo.receiver.phone", "00011112222")
                .param("shippingInfo.address.zipCode", "")
                .param("shippingInfo.address.address1", "")
                .param("shippingInfo.address.address2", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("order/confirm"))
                .andReturn()

        then:
        confirmProducts(result)
    }

    def confirmProducts(result) {
        def products = (List<Product>) result.getModelAndView().getModel().get("products")
        products != null
        products.get(0).getId().getId() == "prod-001"
    }

    void cleanup() {
        SecurityContextHolder.clearContext()
    }
}