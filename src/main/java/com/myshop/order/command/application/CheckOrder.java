package com.myshop.order.command.application;

import com.myshop.order.command.domain.NoOrderException;
import com.myshop.order.command.domain.Order;

/**
 * Created by Mac on 2016. 6. 26..
 */
public interface CheckOrder {
    static void checkNoOrder(Order order) {
        if(order == null) {
            throw new NoOrderException();
        }
    }
}
