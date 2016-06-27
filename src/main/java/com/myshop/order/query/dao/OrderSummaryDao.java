package com.myshop.order.query.dao;

import com.myshop.order.query.dto.OrderSummary;
import com.myshop.common.jpaspec.Specification;

import java.util.List;

/**
 * Created by Mac on 2016. 6. 23..
 */
public interface OrderSummaryDao {
    List<OrderSummary> selectByOrderer(String ordererId);
    List<OrderSummary> select(Specification<OrderSummary> spec, int firstRow, int maxResults);
    long counts(Specification<OrderSummary> spec);
}
