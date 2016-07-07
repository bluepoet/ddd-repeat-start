package com.myshop.order.infra.dao

import com.myshop.SpringIntTestConfig
import com.myshop.catalog.domain.product.ProductId
import com.myshop.member.domain.MemberId
import com.myshop.order.query.dao.OrderViewDao
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification


/**
 * Created by Mac on 2016. 7. 8..
 */
@SpringIntTestConfig
class JpaOrderViewDaoIntTest extends Specification {
    @Autowired
    OrderViewDao orderViewDao

    def "user1 사용자의 주문정보를 확인한다."() {
        when:
        def orders = orderViewDao.selectByOrderer("user1")

        then:
        orders.size() == 2
        def orderView = orders.get(0)
        orderView.getMember().getId() == new MemberId("user1")
        orderView.getProduct().getId() == new ProductId("prod-001")
    }
}