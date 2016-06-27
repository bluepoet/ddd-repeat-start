package com.myshop.order.command.domain;

/**
 * Created by Mac on 2016. 6. 18..
 */
public interface OrderRepository {
    Order findById(OrderNo id);

    void save(Order order);

    OrderNo nextOrderNo();
}
