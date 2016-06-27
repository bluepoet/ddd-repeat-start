package com.myshop.order.command.domain;

import com.myshop.common.event.Event;

/**
 * Created by Mac on 2016. 6. 26..
 */
public class OrderCanceledEvent extends Event {
    private String orderNumber;

    public OrderCanceledEvent(String orderNumber) {
        super();
        this.orderNumber = orderNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }
}
