package com.myshop.order.query.dao;

import com.myshop.common.jpaspec.Specification;
import com.myshop.order.query.dto.OrderSummary;
import com.myshop.order.query.dto.OrderSummary_;

/**
 * Created by Mac on 2016. 7. 6..
 */
public interface OrderSummarySpecs {
    static Specification<OrderSummary> ordererId(String ordererId) {
        return ((root, cb) -> cb.equal(
                root.get(OrderSummary_.ordererId), ordererId));
    }
}
