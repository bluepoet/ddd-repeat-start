package com.myshop.order.command.domain;

import java.util.List;

/**
 * Created by Mac on 2016. 6. 18..
 */
public interface OrderRepository {
    Order findById(OrderNo id);

    void save(Order order);

    OrderNo nextOrderNo();

    List<Order> findByOrdererId(String ordererId, int startRow, int fetchSize);

}
