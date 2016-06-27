package com.myshop.order.command.domain;

/**
 * Created by Mac on 2016. 6. 16..
 */
public enum OrderState {
    PAYMENT_WAITING, PREPARING, SHIPPED, DELIVERING, DELIVERY_COMPLETED, CANCELED;
}
