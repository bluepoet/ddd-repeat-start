package com.myshop.order.command.application;

/**
 * Created by Mac on 2016. 6. 26..
 */
public class StartShippingRequest {
    private String orderNumber;
    private long version;

    protected StartShippingRequest() {}

    public StartShippingRequest(String orderNumber, long version) {
        this.orderNumber = orderNumber;
        this.version = version;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public long getVersion() {
        return version;
    }
}
